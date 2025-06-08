package com.example.fotnews;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NewsDetailsActivity extends AppCompatActivity {

    ImageView newsImage;
    TextView newsHeadline, newsDate, newsContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        newsImage = findViewById(R.id.newsImage);
        newsHeadline = findViewById(R.id.newsHeadline);
        newsDate = findViewById(R.id.newsDate);
        newsContent = findViewById(R.id.newsContent);

        // Get data from Intent
        String headline = getIntent().getStringExtra("headline");
        int imageResId = getIntent().getIntExtra("imageResId", R.drawable.sample_news);
        String date = getIntent().getStringExtra("date");
        String content = getIntent().getStringExtra("content");

        // Set values to views
        newsHeadline.setText(headline);
        newsImage.setImageResource(imageResId);
        newsDate.setText(date);
        newsContent.setText(content);
    }
}