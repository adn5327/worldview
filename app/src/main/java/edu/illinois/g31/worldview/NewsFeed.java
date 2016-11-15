package edu.illinois.g31.worldview;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.*;

import java.util.ArrayList;
import java.util.List;

//TODO add more articles to articles.json
//TODO link CreateAccount to the new user to fake new accounts
public class NewsFeed extends AppCompatActivity implements ListView.OnItemClickListener {

    //Nav drawer variables
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;

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
        mDrawerListView = (ListView) findViewById(R.id.newsfeed_drawer_list_view);
        mDrawerListView.setOnItemClickListener(this);
        
        String[] items = getResources().getStringArray(R.array.news_feed_menu_array);
        ListAdapter listAdapter = new ArrayAdapter<String>(this,
                R.layout.nav_drawer_list_item, items);
        mDrawerListView.setAdapter(listAdapter);

        myToolbar.setNavigationOnClickListener(new NavDrawerOnClickListener(mDrawerLayout));

        //Whole feed scroll
        ScrollView scroll = (ScrollView) findViewById(R.id.newsfeed);

        //Info from login
        Bundle article_info = getIntent().getExtras();
        String cur_username = "No name";
        if(article_info.containsKey("username"))
            cur_username = article_info.getString("username");
        if(cur_username.length() == 0)
            cur_username = "No name";


        //init relevant info
        String topics[] = {"Politics", "Style", "Donald Trump", "Baseball"};
        String sources[] = {"BBC", "NY Times", "Washington Post"};
        User user = new User(cur_username, topics, sources);
        System.out.println("Logging in user..");
        System.out.println(user);


        //JSON parsing
        String articlejson = JSONParser.loadJSONFromAsset(getBaseContext(),"articles.json");
        ArrayList<Article> articles = JSONParser.getArticles(user, articlejson);

        //Make feed
        LinearLayout newsFeed = new LinearLayout(this);
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
                cur_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent article = new Intent(NewsFeed.this, ArticleViewer.class);
                        article.putExtra("article_title", cur_article.title);
                        article.putExtra("article_source", cur_article.source);
                        article.putExtra("article_author", cur_article.author);
                        article.putExtra("article_date", cur_article.date);
                        article.putExtra("article_tags", cur_article.tags);
                        article.putExtra("article_text", cur_article.full_text);
                        startActivity(article);
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
        sourceTextLP.addRule(RelativeLayout.RIGHT_OF,titleText.getId());
        sourceText.setLayoutParams(sourceTextLP);
        sourceText.setPadding(getResources().getDimensionPixelSize(R.dimen.feed_source_horizontal_padding),getResources().getDimensionPixelSize(R.dimen.feed_source_vertical_padding),0,0);
        sourceText.setText(source);
        sourceText.setTextSize(getResources().getDimensionPixelSize(R.dimen.feed_source_text_size));
        sourceText.setTextColor(getResources().getColor(R.color.sourceColor));

        TextView previewText = new TextView(this);
        previewText.setId(View.generateViewId());
        RelativeLayout.LayoutParams previewTextLP = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        previewTextLP.addRule(RelativeLayout.BELOW,titleText.getId());
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
        tagListLP.addRule(RelativeLayout.ALIGN_BOTTOM,thumbnail.getId());
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
        int id = item.getItemId();
        /*if (id == R.id.menu_item) {
            //do something
        }*/
        return super.onOptionsItemSelected(item);
    }

    public void onItemClick(AdapterView parent, View view, int position, long id) {
        ListView v = (ListView) parent;
        mDrawerLayout.closeDrawers();
    }


}
