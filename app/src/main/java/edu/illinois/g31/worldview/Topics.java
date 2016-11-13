package edu.illinois.g31.worldview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
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
    String [] topics = {"Entertainment", "Politics", "Sports", "Technology", "World"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);

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