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
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
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
    User user;
    Article art;
    private TextView article_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_viewer);

        //set up the toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        myToolbar.setNavigationOnClickListener(new NavDrawerOnClickListener(mDrawerLayout));

        Bundle article_info = getIntent().getExtras();
        art = article_info.getParcelable("article");
        user = article_info.getParcelable("user");

        TextView title = (TextView)this.findViewById(R.id.article_title);
        title.setText(art.getTitle());

        TextView source = (TextView)this.findViewById(R.id.article_source);
        source.setText(art.getSource());

        TextView author = (TextView)this.findViewById(R.id.article_author);
        author.setText(art.getAuthor());

        TextView date = (TextView)this.findViewById(R.id.article_date);
        date.setText(art.getDate());

        String tags[] = art.getTags();
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


        article_text = (TextView)this.findViewById(R.id.article_text);
        article_text.setText(art.getFull_text());


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
        if (id == R.id.action_add_to_compare) {
            user.addToCompare(art, ArticleViewer.this);
            System.out.println(user.getCompare().toString());
            AlertDialog.Builder builder = new AlertDialog.Builder(ArticleViewer.this);
            builder.setTitle(R.string.added_to_compare);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        if(id == R.id.action_download) {
            user.addToSaved(art, ArticleViewer.this);
            AlertDialog.Builder builder = new AlertDialog.Builder(ArticleViewer.this);
            builder.setTitle(R.string.added_to_saved);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        float oneSp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 1, getResources().getDisplayMetrics());
        float curSize = article_text.getTextSize()/oneSp;
        if(id == R.id.action_sizedown)
        {
            if(curSize > 10) {
                article_text.setTextSize(curSize - 1);
            }
        }
        if(id == R.id.action_sizeup)
        {
            if(curSize < 20) {
                article_text.setTextSize(curSize + 1);
            }
        }
        return super.onOptionsItemSelected(item);
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
        Intent activity = new Intent(ArticleViewer.this, NewsFeed.class);
        activity.putExtra("user", user);
        startActivity(activity);
    }

    public void onItemClick(AdapterView parent, View view, int position, long id) {
        ListView v = (ListView) parent;
        mDrawerLayout.closeDrawers();
    }
}
