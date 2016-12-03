package edu.illinois.g31.worldview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class Topics extends AppCompatActivity {

    ListView topicList;
    Button next;
    User user;

    // array of topics
    String [] topics = {"Airlines", 
            "Animals",
            "Arts",
            "Automotive",
            "Banks",
            "Barack Obama",
            "Baseball",
            "Books",
            "Business",
            "Children",
            "Clothes",
            "Donald Trump",
            "Economy",
            "Education",
            "Elections",
            "Energy",
            "Environment",
            "Food",
            "Golf",
            "Health",
            "Hillary Clinton",
            "History",
            "Hockey",
            "Home",
            "Immigration",
            "Internet",
            "Iraq",
            "Movies",
            "Music",
            "NCAA",
            "NFL",
            "Olympics",
            "Politics",
            "Presidency",
            "Racing",
            "Real Estate",
            "Running",
            "Science",
            "Senate",
            "Snowboarding",
            "Soccer",
            "Sports",
            "Stock Exchange",
            "Style",
            "Swimming",
            "Syria",
            "TV",
            "Taxes",
            "Tech",
            "Tennis",
            "US",
            "Video Games",
            "Weather",
            "Work",
            "World"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Select Topics");

        Bundle user_info =  getIntent().getExtras();
        user = user_info.getParcelable("user");
        final boolean topicsOnly = user_info.containsKey("topics_only");

        topicList = (ListView)findViewById(R.id.list);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, topics);

        topicList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        topicList.setAdapter(adapter);
        for(String topic : user.getCurFeed().getTopics())
            topicList.setItemChecked(adapter.getPosition(topic),true);

        next = (Button)findViewById(R.id.button);
        next.setVisibility(View.VISIBLE);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray checkedArray = topicList.getCheckedItemPositions();
                ArrayList<String> checkedTopics = new ArrayList<>();
                for(int i = 0; i < checkedArray.size(); i++){
                    if(checkedArray.valueAt(i))
                        checkedTopics.add(adapter.getItem(checkedArray.keyAt(i)));
                }
                user.getCurFeed().setTopics(checkedTopics);
                Intent activity;
                if(topicsOnly)
                    activity = new Intent(Topics.this, NewsFeed.class);
                else
                    activity = new Intent(Topics.this, Sources.class);
                activity.putExtra("user", user);
                startActivity(activity);
            }
        });

    }

}