package edu.illinois.g31.worldview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class CreateAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    protected void goToTopics(View view){
        Intent i = new Intent(this, Topics.class);

        //get username
        final EditText usernameField = (EditText) this.findViewById(R.id.username);
        String username = usernameField.getText().toString();
        if(username.length() == 0)
            username = "No name";
        i.putExtra("username", username);

        startActivity(i);
    }
}
