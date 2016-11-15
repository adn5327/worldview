package edu.illinois.g31.worldview;

import android.app.SearchManager;
import android.content.Context;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ArticleViewer extends AppCompatActivity implements ListView.OnItemClickListener {

    //Nav drawer variables
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_viewer);

        //set up the toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp); // place icon in upper left
        setSupportActionBar(myToolbar);

        //set up the nav drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.article_drawer_layout);
        mDrawerListView = (ListView) findViewById(R.id.article_drawer_list_view);
        mDrawerListView.setOnItemClickListener(this);

        String[] items = getResources().getStringArray(R.array.article_menu_array);
        ListAdapter listAdapter = new ArrayAdapter<String>(this,
                R.layout.nav_drawer_list_item, items);
        mDrawerListView.setAdapter(listAdapter);

        myToolbar.setNavigationOnClickListener(new NavDrawerOnClickListener(mDrawerLayout));

        Bundle article_info = getIntent().getExtras();

        TextView title = (TextView)this.findViewById(R.id.article_title);
        title.setText(article_info.getString("article_title"));

        TextView source = (TextView)this.findViewById(R.id.article_source);
        source.setText(article_info.getString("article_source"));

        TextView author = (TextView)this.findViewById(R.id.article_author);
        author.setText(article_info.getString("article_author"));

        TextView date = (TextView)this.findViewById(R.id.article_date);
        date.setText(article_info.getString("article_date"));

        String tags[] = article_info.getStringArray("article_tags");
        RelativeLayout tagList = (RelativeLayout)this.findViewById(R.id.article_tags);
        TextView tagTexts[] = new TextView[tags.length];
        for(int j = 0; j < tags.length; j++){
            tagTexts[j] = new TextView(this);
            tagTexts[j].setId(View.generateViewId());
            RelativeLayout.LayoutParams tagTextLP = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            if(j > 0)
                tagTextLP.addRule(RelativeLayout.RIGHT_OF,tagTexts[j-1].getId());
            tagTexts[j].setLayoutParams(tagTextLP);
            tagTexts[j].setPadding(0,0,getResources().getDimensionPixelSize(R.dimen.feed_tag_horizontal_padding),getResources().getDimensionPixelSize(R.dimen.feed_tag_vertical_padding));
            tagTexts[j].setText(tags[j]);
            tagTexts[j].setTextSize(getResources().getDimensionPixelSize(R.dimen.feed_tag_text_size));
            tagTexts[j].setTextColor(getResources().getColor(R.color.tagColor));
            tagList.addView(tagTexts[j]);
        }


        TextView text = (TextView)this.findViewById(R.id.article_text);
        text.setText(article_info.getString("article_text"));


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_article_menu, menu);
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
