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

    // array of topics
    String [] topics = {"Politics",
            "Sports",
            "Tech",
            "Health",
            "Food",
            "World",
            "US",
            "Business",
            "Arts",
            "Style",
            "Donald Trump",
            "Hillary Clinton",
            "Barack Obama",
            "Presidency",
            "Senate",
            "Immigration",
            "Iraq",
            "Environment",
            "Weather",
            "Taxes",
            "Elections",
            "Education",
            "Children",
            "Energy",
            "History",
            "Science",
            "Real Estate",
            "Banks",
            "Stock Exchange",
            "Airlines",
            "Economy",
            "Automotive",
            "Soccer",
            "Hockey",
            "Racing",
            "Snowboarding",
            "Olympics",
            "NCAA",
            "Football",
            "NFL",
            "Baseball",
            "Tennis",
            "Running",
            "Swimming",
            "Golf",
            "MLB",
            "PGA",
            "NHL",
            "FIFA",
            "Video Games",
            "Music",
            "Books",
            "Internet",
            "TV",
            "Movies",
            "Clothes",
            "Home",
            "Work",
            "Animals",
            "Syria"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Select Topics");

        Bundle user_info =  getIntent().getExtras();
        final String cur_username = user_info.getString("username");
        final boolean topicsOnly = user_info.containsKey("topics_only");
        ArrayList<String> precheckedTopics = new ArrayList<>(Arrays.asList("Politics"));
        ArrayList<String> precheckedSources = new ArrayList<>();
        if(user_info.containsKey("topics"))
            precheckedTopics = user_info.getStringArrayList("topics");
        if(user_info.containsKey("sources"))
            precheckedSources = user_info.getStringArrayList("sources");
        final ArrayList<String> final_sources = precheckedSources;

        topicList = (ListView)findViewById(R.id.list);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, topics);

        topicList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        topicList.setAdapter(adapter);
        for(String topic : precheckedTopics)
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
                Intent activity;
                if(topicsOnly)
                    activity = new Intent(Topics.this, NewsFeed.class);
                else
                    activity = new Intent(Topics.this, Sources.class);
                activity.putExtra("username", cur_username);
                activity.putStringArrayListExtra("topics",checkedTopics);
                if(final_sources.size() != 0)
                    activity.putStringArrayListExtra("sources", final_sources);
                startActivity(activity);
            }
        });

    }

}