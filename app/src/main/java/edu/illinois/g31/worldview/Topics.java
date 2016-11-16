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

        LayoutInflater inflater = this.getLayoutInflater();
        View nextButton = inflater.inflate(R.layout.activity_topics, null);

        topicList = (ListView)findViewById(R.id.list);
        next = (Button)nextButton.findViewById(R.id.button);
        next.setVisibility(View.VISIBLE);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, topics);
        topicList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        topicList.addFooterView(nextButton);
        topicList.setAdapter(adapter);
    }

    public void goToSources(View view){
        Intent activity = new Intent(this, Sources.class);
        startActivity(activity);
    }
}