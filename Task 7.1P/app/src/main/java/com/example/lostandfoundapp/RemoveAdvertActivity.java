package com.example.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class RemoveAdvertActivity extends AppCompatActivity {

    TextView postTypeItemTv2;
    TextView postItemTv2;
    TextView dateTv2;
    TextView locationTv2;
    Button removeBtn;

    Button backBtn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.remove_advert);

        // Retrieve selected news ID and source information from MainActivity page
        String name = getIntent().getStringExtra("Name");
        String lost = getIntent().getStringExtra("Lost");

        postTypeItemTv2 = findViewById(R.id.postTypeItemTv2);
        postItemTv2 = findViewById(R.id.postItemTv2);
        dateTv2 = findViewById(R.id.dateTv2);
        locationTv2 = findViewById(R.id.locationTv2);
        removeBtn = findViewById(R.id.removeBtn);
        backBtn3 = findViewById(R.id.backBtn3);

        postTypeItemTv2.setText(lost);
        postItemTv2.setText(name);

        DatabaseHelper databaseHelper = new DatabaseHelper(RemoveAdvertActivity.this);

        Advert selectedAdvert = databaseHelper.getAdvertByName(postItemTv2.getText().toString());
        locationTv2.setText(selectedAdvert.getLocation());

        Date selectedAdvertDate = selectedAdvert.getDate();
        Date currentDate = Calendar.getInstance().getTime();//getting current date

        // Calculate the difference in milliseconds
        long differenceMillis = currentDate.getTime() - selectedAdvertDate.getTime();
        // Convert milliseconds to days
        long differenceDays = differenceMillis / (1000 * 60 * 60 * 24);
        dateTv2.setText(String.valueOf(differenceDays) + " days ago");

        // Set OnClickListener for remove button
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Assuming you have a DatabaseHelper instance named dbHelper
                boolean removed = databaseHelper.removeAdvertByName(postItemTv2.getText().toString());
                if (removed) {
                    Toast.makeText(getApplicationContext(), "Advert removed successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to remove advert", Toast.LENGTH_SHORT).show();

                }
            }
        });

        // Set OnClickListener for back button
        backBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RemoveAdvertActivity.this, AllAdvertsActivity.class);
                startActivity(intent);
            }
        });
    }
}
