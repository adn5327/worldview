package edu.illinois.g31.worldview;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class CreateAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        final TextInputEditText usernameField = (TextInputEditText)findViewById(R.id.username);
        Button createAccount = (Button)findViewById(R.id.create_account);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameField.getText().toString();
                Intent topics = new Intent(CreateAccount.this, Topics.class);
                topics.putExtra("username", username);
                topics.putExtra("create", true);
                startActivity(topics);
            }
        });

    }
}
