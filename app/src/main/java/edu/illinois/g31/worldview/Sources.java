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

import java.util.ArrayList;
import java.util.Arrays;

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
        final ArrayList<String> topics = user_info.getStringArrayList("topics");
        ArrayList<String> precheckedSources = new ArrayList<>(Arrays.asList("NY Times","Guardian", "BBC"));
        final boolean create;
        if(user_info.containsKey("create"))
            create = true;
        else
            create = false;
        if(user_info.containsKey("sources"))
            precheckedSources = user_info.getStringArrayList("sources");

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
                ArrayList<String> checkedSources = new ArrayList<>();
                for(int i = 0; i < checkedArray.size(); i++){
                    if(checkedArray.valueAt(i))
                        checkedSources.add(adapter.getItem(checkedArray.keyAt(i)));
                }
                Intent newsfeed = new Intent(Sources.this, NewsFeed.class);
                newsfeed.putExtra("username", cur_username);
                newsfeed.putStringArrayListExtra("topics", topics);
                newsfeed.putStringArrayListExtra("sources", checkedSources);
                if(create)
                    newsfeed.putExtra("create", true);
                startActivity(newsfeed);
            }
        });

        sourceList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        sourceList.addFooterView(finishButton);
        sourceList.setAdapter(adapter);
        for(String source : precheckedSources)
            sourceList.setItemChecked(adapter.getPosition(source),true);

    }

}