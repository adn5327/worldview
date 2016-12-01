package edu.illinois.g31.worldview;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.internal.NavigationMenu;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItem;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

//TODO add more articles to articles.json
//TODO link CreateAccount to the new user to fake new accounts
public class NewsFeed extends AppCompatActivity {

    //Nav drawer variables
    private DrawerLayout mDrawerLayout;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    User user;
    HashMap<String, List<String>> listDataChild;
    final int FEED = 0;
    final int SAVED = 1;
    final int COMPARE = 2;
    final int ADDFEED = 3333333;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        //Info from login
        Bundle article_info = getIntent().getExtras();
        user = article_info.getParcelable("user");
        System.out.println("Logging in user..");
        System.out.println(user);
        makeFeed(FEED);
    }
    public void makeFeed(int option){
        //set up the toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp); // place icon in upper left
        setSupportActionBar(myToolbar);

        //set up the nav drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //following nav drawer code adapted from http://www.androidhive.info/2013/07/android-expandable-list-view-tutorial/
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.newsfeed_drawer_list_view);

        // preparing list data
        prepareListData(user);

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {

                Intent i = new Intent();
                switch(groupPosition)  {
                    //Disable collapsing to avoid crash
                    case 0:
                        return true;
                    case 1:
                        return true;
                    //The groups that don't expand
                    case 2:
                        i.setClass(NewsFeed.this, AccountSettingsActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        break;
                    case 3:
                        i.setClass(NewsFeed.this, HelpActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        break;
                    case 4:
                        i.setClass(NewsFeed.this, LoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                //Do we need to do anything here?
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                if (childPosition == listDataChild.get(listDataHeader.get(groupPosition)).size()) {
                    Intent i = new Intent();
                    switch (groupPosition) {
                        case 0:
                            i.setClass(NewsFeed.this, Sources.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.putExtra("user", user);
                            startActivity(i);
                            break;

                        case 1:
                            i.setClass(NewsFeed.this, Topics.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.putExtra("user", user);
                            i.putExtra("topics_only", true);
                            startActivity(i);
                            break;

                        default:
                            break;
                    }
                }
                return false;
            }
        });

        myToolbar.setNavigationOnClickListener(new NavDrawerOnClickListener(mDrawerLayout));

        //Footer bug fix:  Initially open groups programmatically
        // I only had problems when "Topics" was
        // expanded before "Sources", so this should avoid the crash.
        // Still not sure what the root of the problem was, though.
        expListView.expandGroup(0);
        expListView.expandGroup(1);

        String header = "Worldview";
        ArrayList<Article> articles = new ArrayList<>();
        switch(option){
            case FEED:
                header = user.getCurFeed().getFeedname();
                //JSON parsing
                String articlejson = JSONParser.loadJSONFromAsset(getBaseContext(),"articles.json");
                articles = JSONParser.getArticles(user, articlejson);
                break;
            case SAVED:
                header = "Saved";
                articles = user.getSaved();
                break;
            case COMPARE:
                header = "Compare";
                articles = user.getCompare();
                break;
        }

        getSupportActionBar().setTitle(header);

        //Whole feed scroll
        ScrollView scroll = (ScrollView) findViewById(R.id.newsfeed);
        scroll.removeAllViews();


        //Make feed
        final LinearLayout newsFeed = new LinearLayout(this);
        newsFeed.setOrientation(LinearLayout.VERTICAL);
        RelativeLayout cur_layout;
        if(articles != null) {
            for (Article article : articles) {
                final Article cur_article = article;
                cur_layout = addArticleToFeed(cur_article);
                RelativeLayout.LayoutParams LP = new RelativeLayout.LayoutParams(
                        cur_layout.getLayoutParams().width,
                        cur_layout.getLayoutParams().height);

                cur_layout.setLayoutParams(LP);
                cur_layout.setOnTouchListener(new OnSwipeTouchListener(NewsFeed.this, cur_layout) {
                    @Override
                    public void onClick() {
                        Intent article = new Intent(NewsFeed.this, ArticleViewer.class);
                        article.putExtra("article", cur_article);
                        article.putExtra("user", user);
                        startActivity(article);
                    }
                    @Override
                    public void onSwipeRight() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(NewsFeed.this);

                        builder.setTitle(R.string.add_to_compare_swipe);

                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                user.addToCompare(cur_article, NewsFeed.this);
                            }
                        });
                        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                    @Override
                    public void onSwipeLeft() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(NewsFeed.this);

                        builder.setMessage(R.string.remove_from_feed)
                                .setTitle(R.string.remove_title);

                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                newsFeed.removeView(theLayout);
                            }
                        });
                        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });


                newsFeed.addView(cur_layout);
            }
        }
        scroll.addView(newsFeed);
    }

    protected RelativeLayout addArticleToFeed(Article article){
        String title = article.getTitle();
        String source = article.getSource();
        String preview_text = article.getPreview_text();
        String[] tags = article.getTags();

        RelativeLayout feed = new RelativeLayout(this);

        feed.setId(View.generateViewId());
        RelativeLayout.LayoutParams feedLP = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        feed.setLayoutParams(feedLP);
        feed.setBackground(getDrawable(R.drawable.feed_background));

        ImageView thumbnail = new ImageView(this);
        thumbnail.setId(View.generateViewId());
        thumbnail.setContentDescription("Thumbnail of article");
        RelativeLayout.LayoutParams thumbnailLP = new RelativeLayout.LayoutParams(
                getResources().getDimensionPixelSize(R.dimen.feed_thumbnail_width),
                getResources().getDimensionPixelSize(R.dimen.feed_thumbnail_height));
        thumbnail.setLayoutParams(thumbnailLP);
        thumbnail.setImageResource(R.mipmap.worldview);

        TextView titleText = new TextView(this);
        titleText.setId(View.generateViewId());
        RelativeLayout.LayoutParams titleTextLP = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        titleTextLP.addRule(RelativeLayout.ALIGN_TOP,thumbnail.getId());
        titleTextLP.addRule(RelativeLayout.RIGHT_OF,thumbnail.getId());
        titleText.setLayoutParams(titleTextLP);
        titleText.setPadding(0,getResources().getDimensionPixelSize(R.dimen.feed_title_vertical_padding),0,0);
        titleText.setText(title);
        titleText.setTextSize(getResources().getDimensionPixelSize(R.dimen.feed_title_text_size));
        titleText.setTextColor(getResources().getColor(R.color.titleColor));

        TextView sourceText = new TextView(this);
        sourceText.setId(View.generateViewId());
        RelativeLayout.LayoutParams sourceTextLP = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        sourceTextLP.addRule(RelativeLayout.BELOW,titleText.getId());
        sourceTextLP.addRule(RelativeLayout.RIGHT_OF,thumbnail.getId());
        sourceText.setLayoutParams(sourceTextLP);
        sourceText.setPadding(0,getResources().getDimensionPixelSize(R.dimen.feed_source_vertical_padding),0,0);
        sourceText.setText(source);
        sourceText.setTextSize(getResources().getDimensionPixelSize(R.dimen.feed_source_text_size));
        sourceText.setTextColor(getResources().getColor(R.color.sourceColor));

        TextView previewText = new TextView(this);
        previewText.setId(View.generateViewId());
        RelativeLayout.LayoutParams previewTextLP = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        previewTextLP.addRule(RelativeLayout.BELOW,sourceText.getId());
        previewTextLP.addRule(RelativeLayout.RIGHT_OF,thumbnail.getId());
        previewText.setLayoutParams(previewTextLP);
        previewText.setText(preview_text);
        previewText.setTextSize(getResources().getDimensionPixelSize(R.dimen.feed_preview_text_size));
        previewText.setTextColor(getResources().getColor(R.color.previewColor));

        RelativeLayout tagList = new RelativeLayout(this);
        tagList.setId(View.generateViewId());
        RelativeLayout.LayoutParams tagListLP = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        tagListLP.addRule(RelativeLayout.BELOW,previewText.getId());
        tagListLP.addRule(RelativeLayout.RIGHT_OF,thumbnail.getId());
        tagList.setLayoutParams(tagListLP);
        TextView tagTexts[] = new TextView[tags.length];
        for(int j = 0; j < tags.length; j++){
            tagTexts[j] = new TextView(this);
            tagTexts[j].setId(View.generateViewId());
            RelativeLayout.LayoutParams tagTextLP = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            tagTextLP.addRule(RelativeLayout.ALIGN_BOTTOM,thumbnail.getId());
            if(j > 0)
                tagTextLP.addRule(RelativeLayout.RIGHT_OF,tagTexts[j-1].getId());
            tagTexts[j].setLayoutParams(tagTextLP);
            tagTexts[j].setPadding(0,0,getResources().getDimensionPixelSize(R.dimen.feed_tag_horizontal_padding),getResources().getDimensionPixelSize(R.dimen.feed_tag_vertical_padding));
            tagTexts[j].setText(tags[j]);
            tagTexts[j].setTextSize(getResources().getDimensionPixelSize(R.dimen.feed_tag_text_size));
            tagTexts[j].setTextColor(getResources().getColor(R.color.tagColor));
            tagList.addView(tagTexts[j]);
        }



        feed.addView(thumbnail);
        feed.addView(titleText);
        feed.addView(sourceText);
        feed.addView(previewText);
        feed.addView(tagList);

        return feed;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_newsfeed_menu, menu);

        for(int i = 0; i < 2; i++) {
            MenuItem item = menu.getItem(i);
            SpannableString s = new SpannableString(item.getTitle());
            s.setSpan(new ForegroundColorSpan(Color.BLUE), 0, s.length(), 0);
            item.setTitle(s);
        }

        for(int i = 0; i < user.getFeeds().size(); i++)
            menu.add(0, i, 0, user.getFeeds().get(i).getFeedname()+ " feed");
        menu.add(0, ADDFEED, 0, "Add feed");
        MenuItem item = menu.getItem(menu.size()-1);
        SpannableString s = new SpannableString(item.getTitle());
        s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
        item.setTitle(s);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //TODO:  show the appropriate feed based on selection
        int id = item.getItemId();
        System.out.println(id);
        if (id == ADDFEED) {
            // 1. Instantiate an AlertDialog.Builder with its constructor
            AlertDialog.Builder builder = new AlertDialog.Builder(NewsFeed.this);

            // 2. Chain together various setter methods to set the dialog characteristics
            final EditText edittxt = new EditText(NewsFeed.this);
            builder.setTitle(R.string.alert_add_feed_title)
                    .setView(edittxt);

            // Add the buttons
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    user.addFeed(edittxt.getText().toString());
                    Intent activity = new Intent(NewsFeed.this, Topics.class);
                    activity.putExtra("user", user);
                    startActivity(activity);
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
            // 3. Get the AlertDialog from create()
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        if(id < user.getFeeds().size()){
            user.setCurFeed(id);
            makeFeed(FEED);
        }
        if(id == R.id.action_compare){
            makeFeed(COMPARE);
        }
        if(id == R.id.action_saved){
            makeFeed(SAVED);
        }
        return super.onOptionsItemSelected(item);
    }


    /*
     * Preparing the list data
     */
    private void prepareListData(User user) {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        // Adding parent group data
        String[] items = getResources().getStringArray(R.array.news_feed_menu_array);
        for(String item:items)
            listDataHeader.add(item);

        //make empty lists for other menu options to avoid crashes
        List<String> emptyList = new ArrayList<>();

        listDataChild.put(listDataHeader.get(0), user.getCurFeed().getSources()); // Header, Child data
        listDataChild.put(listDataHeader.get(1), user.getCurFeed().getTopics());
        listDataChild.put(listDataHeader.get(2), emptyList);
        listDataChild.put(listDataHeader.get(3), emptyList);
        listDataChild.put(listDataHeader.get(4), emptyList);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        
    }


}
