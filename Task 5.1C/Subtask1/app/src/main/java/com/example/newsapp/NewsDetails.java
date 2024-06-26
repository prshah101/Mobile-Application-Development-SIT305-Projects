package com.example.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageView;

import java.util.ArrayList;

public class NewsDetails extends AppCompatActivity {
    RecyclerView rv;

    LinearLayoutManager linearLayoutManager;

    LinearLayoutManager linearLayoutManager2;
    RelatedTopStoriesAdapter relatedTopStoriesAdapter;

    RelatedNewsAdapter relatedNewsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_news_details);
        Button backtoMainBtn = findViewById(R.id.backBtn);

        // Declare and intialise the views to display selected news details
        TextView selectedDescriptionText = findViewById(R.id.selectedDescriptionText);
        TextView selectedTitleText = findViewById(R.id.selectedTitleText);
        ImageView selectedImageView = findViewById(R.id.newsImage);
        rv = findViewById(R.id.verticalRV);

        // Retrieve selected news ID and source information from MainActivity page
        int  id = getIntent().getIntExtra("selectedId", 0);
        boolean fromTopStories = getIntent().getBooleanExtra("fromTopStories", false);

        // Depending on the source of the news (top stories or regular news) Set up the main article details, and related articles details
        if (fromTopStories == true){
            //Set the values retrieved from MainActivity
            selectedDescriptionText.setText(DataSource.getDescriptionDataSource().get(id - 1)); // Adjusting index by 1 since IDs start from 1
            selectedTitleText.setText(DataSource.getTitleDataSource().get(id - 1));
            selectedImageView.setImageResource(DataSource.getImageDataSource().get(id - 1));

            // Get the data for related news from DataSource class
            ArrayList<Integer> newsId = DataSource.getId();
            ArrayList<Integer> image2DataSource = DataSource.getImage2DataSource();
            ArrayList<String> newsAgencyDataSource = DataSource.getNewsAgencyDataSource();
            ArrayList<String> newsDescriptionDataSource = DataSource.getNewsDescriptionDataSource();

            // Set up RecyclerView and adapter for related news
            linearLayoutManager = new LinearLayoutManager(NewsDetails.this, LinearLayoutManager.VERTICAL, false);
            relatedNewsAdapter = new RelatedNewsAdapter(newsId, image2DataSource, newsAgencyDataSource, newsDescriptionDataSource);
            rv.setLayoutManager(linearLayoutManager);
            rv.setAdapter(relatedNewsAdapter);
        }else{
            //Set the values retrieved from MainActivity
            selectedDescriptionText.setText(DataSource.getNewsDescriptionDataSource().get(id - 1)); // Adjusting index by 1 since IDs start from 1
            selectedTitleText.setText(DataSource.getNewsAgencyDataSource().get(id - 1)); // Adjusting index by 1
            selectedImageView.setImageResource(DataSource.getImage2DataSource().get(id - 1));

            // Get the data for related top stories from DataSource class
            ArrayList<Integer> topStoriesIds = DataSource.getId();
            ArrayList<String> titleDataSource = DataSource.getTitleDataSource();
            ArrayList<Integer> imageDataSource = DataSource.getImageDataSource();
            ArrayList<String> getDescriptionDataSource = DataSource.getDescriptionDataSource();

            // Set up RecyclerView and adapter for related news
            linearLayoutManager = new LinearLayoutManager(NewsDetails.this, LinearLayoutManager.VERTICAL, false);
            relatedTopStoriesAdapter = new RelatedTopStoriesAdapter(topStoriesIds, imageDataSource, titleDataSource, getDescriptionDataSource);
            rv.setLayoutManager(linearLayoutManager);
            rv.setAdapter(relatedTopStoriesAdapter);
        }

        //Back to Main page button set up
        backtoMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                v.getContext().startActivity(intent);

            }
        });

    }
}