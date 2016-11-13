package edu.illinois.g31.worldview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class Sources extends AppCompatActivity {

    ListView sourceList;
    Button finish;

    // array of sources
    String [] sources = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sources);

        LayoutInflater inflater = this.getLayoutInflater();
        View finishButton = inflater.inflate(R.layout.activity_sources, null);

        sourceList = (ListView)findViewById(R.id.list);
        finish = (Button)finishButton.findViewById(R.id.button);
        finish.setVisibility(View.VISIBLE);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, sources);
        sourceList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        sourceList.addFooterView(finishButton);
        sourceList.setAdapter(adapter);
    }

    public void goToTopics(View view){
        Intent activity = new Intent(this, Topics.class);
        startActivity(activity);
    }
}