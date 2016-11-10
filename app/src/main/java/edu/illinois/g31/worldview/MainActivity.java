package edu.illinois.g31.worldview;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * TODO: Add Log-in page (auth?)
 * TODO: Add Create Account page
 * TODO: Add Top nav bar with the two menus and buttons
 * TODO: Add topics/sources/feeds
 * TODO: Add articles and update Article Viewer and News feed accordingly (article object and json parsing?)
 * TODO: Add comparisons and everything that goes with it
 * TODO: Add swiping to news feed articles and dull posts after viewing
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToNewsFeed(View view){
        Intent activity = new Intent(this, NewsFeed.class);
        startActivity(activity);
    }

    public void goToArticle(View view){
        Intent activity = new Intent(this, ArticleViewer.class);
        startActivity(activity);
    }

}
