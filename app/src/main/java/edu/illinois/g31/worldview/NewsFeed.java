package edu.illinois.g31.worldview;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
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
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

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
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                //The groups that don't expand
                Intent i = new Intent();
                switch(groupPosition)  {
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
        final String finalCur_username = cur_username;  //temp variable for intent
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                if (childPosition == listDataChild.get(listDataHeader.get(groupPosition)).size()) {
                    Intent i = new Intent();
                    switch(groupPosition)  {
                        case 0:
                            i.setClass(NewsFeed.this, Sources.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.putExtra("username", finalCur_username);
                            startActivity(i);
                            break;

                        case 1:
                            i.setClass(NewsFeed.this, Topics.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.putExtra("username", finalCur_username);
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

        //Whole feed scroll
        ScrollView scroll = (ScrollView) findViewById(R.id.newsfeed);

        //Info from login
        Bundle article_info = getIntent().getExtras();
        String cur_username = "No name";
        String topics[] = {"Politics", "Style", "Donald Trump", "Baseball"};
        String sources[] = {"BBC", "NY Times", "Washington Post"};
        if(article_info != null) {
            cur_username = article_info.getString("username");
            if(article_info.containsKey("topics")){
                topics = article_info.getStringArray("topics");
                sources = article_info.getStringArray("sources");
            }
        }

        //init relevant info
        User user = new User(cur_username, topics, sources);
        System.out.println("Logging in user..");
        System.out.println(user);

        //JSON parsing
        String articlejson = JSONParser.loadJSONFromAsset(getBaseContext(),"articles.json");
        ArrayList<Article> articles = JSONParser.getArticles(user, articlejson);

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
                        article.putExtra("article_title", cur_article.title);
                        article.putExtra("article_source", cur_article.source);
                        article.putExtra("article_author", cur_article.author);
                        article.putExtra("article_date", cur_article.date);
                        article.putExtra("article_tags", cur_article.tags);
                        article.putExtra("article_text", cur_article.full_text);
                        startActivity(article);
                    }
                    @Override
                    public void onSwipeRight() {
                    }
                    @Override
                    public void onSwipeLeft() {
                        // 1. Instantiate an AlertDialog.Builder with its constructor
                        AlertDialog.Builder builder = new AlertDialog.Builder(NewsFeed.this);

                        // 2. Chain together various setter methods to set the dialog characteristics
                        builder.setMessage(R.string.remove_from_feed)
                                .setTitle(R.string.remove_title);

                        // Add the buttons
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
                        // 3. Get the AlertDialog from create()
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
        String title = article.title;
        String source = article.source;
        String preview_text = article.preview_text;
        String[] tags = article.tags;

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
        /*if (id == R.id.menu_item) {
            //do something
        }*/
        return super.onOptionsItemSelected(item);
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding parent group data
        String[] items = getResources().getStringArray(R.array.news_feed_menu_array);
        for (int i=0; i<items.length; i++)
            listDataHeader.add(items[i]);

        //TODO:  display all the topics/sources with boxes set appropriately
        // Adding child data
        List<String> topics = new ArrayList<String>();
        topics.add("Topic 1");
        topics.add("Topic 2");

        List<String> sources = new ArrayList<String>();
        sources.add("Source 1");
        sources.add("Source 2");

        //make empty lists for other menu options to avoid crashes
        List<String> emptyList = new ArrayList<String>();

        listDataChild.put(listDataHeader.get(0), sources); // Header, Child data
        listDataChild.put(listDataHeader.get(1), topics);
        listDataChild.put(listDataHeader.get(2), emptyList);
        listDataChild.put(listDataHeader.get(3), emptyList);
        listDataChild.put(listDataHeader.get(4), emptyList);

    }


}
