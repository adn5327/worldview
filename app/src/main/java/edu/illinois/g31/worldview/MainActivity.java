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
