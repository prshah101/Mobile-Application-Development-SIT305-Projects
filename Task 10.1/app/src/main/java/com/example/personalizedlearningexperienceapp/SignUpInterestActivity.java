package com.example.personalizedlearningexperienceapp;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.personalizedlearningexperienceapp.TopicAdapter;

import java.util.ArrayList;
import java.util.List;

public class SignUpInterestActivity extends AppCompatActivity {
    private RecyclerView rv1;
    private Button nextButton;
    private List<String> interestsList;
    private TopicAdapter topicsAdapter;

    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_interests);

        rv1 = findViewById(R.id.rv1);
        nextButton = findViewById(R.id.nextBtn);

        // Retrieve selected news ID and source information from SignUp page
        String username = getIntent().getStringExtra("username");

        // Get the data from DataSource class for topics
        ArrayList<String> topics1 = DataSource.geTopics1();
        ArrayList<String> topics2 = DataSource.geTopics2();

        // Initialize and set up the adapter for top stories RecyclerView
        linearLayoutManager = new LinearLayoutManager(SignUpInterestActivity.this, LinearLayoutManager.VERTICAL, false);
        topicsAdapter = new TopicAdapter(topics1, topics2);
        rv1.setLayoutManager(linearLayoutManager);
        rv1.setAdapter(topicsAdapter);

        DatabaseHelper databaseHelper = new DatabaseHelper(SignUpInterestActivity.this);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                // Print all the values in selectedIntrestsList
//                for (String interest : topicsAdapter.getSelectedIntrestsList()) {
//                    Toast.makeText(SignUpInterestActivity.this, interest, Toast.LENGTH_SHORT).show();
//                }

                // Print all the values in selectedIntrestsList
                for (String interest : topicsAdapter.getSelectedIntrestsList()) {
                    // Access the addOneLogin() method in the database to add the loginDetails to the database
                    boolean success = databaseHelper.addInterest(username, interest);
                }

                Toast.makeText(SignUpInterestActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(SignUpInterestActivity.this, MainActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }
}
