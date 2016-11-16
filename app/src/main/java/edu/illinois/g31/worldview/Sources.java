package edu.illinois.g31.worldview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class Sources extends AppCompatActivity {

    ListView sourceList;
    Button finish;

    // array of sources
    String [] sources = { "NY Times",
            "Guardian",
            "BBC",
            "ESPN",
            "Chicago Tribune",
            "Washington Post",
            "Buzzfeed"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sources);

        Bundle user_info =  getIntent().getExtras();
        final String cur_username = user_info.getString("username");
        final String[] topics = user_info.getStringArray("topics");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Select Sources");

        LayoutInflater inflater = this.getLayoutInflater();
        View finishButton = inflater.inflate(R.layout.activity_sources, null);

        sourceList = (ListView)findViewById(R.id.list);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, sources);

        finish = (Button)finishButton.findViewById(R.id.button);
        finish.setVisibility(View.VISIBLE);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray checkedArray = sourceList.getCheckedItemPositions();
                String[] checkedSources = new String[checkedArray.size()];
                for(int i = 0; i < checkedArray.size(); i++){
                    checkedSources[i] = adapter.getItem(checkedArray.keyAt(i));
                }
                Intent newsfeed = new Intent(Sources.this, NewsFeed.class);
                newsfeed.putExtra("username", cur_username);
                newsfeed.putExtra("topics", topics);
                newsfeed.putExtra("sources", checkedSources);
                startActivity(newsfeed);
            }
        });

        sourceList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        sourceList.addFooterView(finishButton);
        sourceList.setAdapter(adapter);

    }

    public void goToNewsFeed(View view){
        Intent activity = new Intent(this, NewsFeed.class);

        //get username
        Bundle article_info = getIntent().getExtras();
        String cur_username = "No name";
        if(article_info.containsKey("username"))
            cur_username = article_info.getString("username");
        if(cur_username.length() == 0)
            cur_username = "No name";

        activity.putExtra("username", cur_username);
        startActivity(activity);
    }
}