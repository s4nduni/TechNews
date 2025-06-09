package com.example.fotnews.ui.sport;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.fotnews.NewsDetailsActivity;
import com.example.fotnews.R;

public class SportFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the custom layout (make sure this matches your actual XML filename in res/layout/)
        View root = inflater.inflate(R.layout.fragment_sport, container, false);

        // Example of how to reference views
        LinearLayout newsContainer = root.findViewById(R.id.news_container);

        // Optionally add news cards dynamically
        addNewsCard(newsContainer, R.drawable.news1, "Lorem ipsum dolor sit amet. Sed omnis quam ex veniam omnis...");
        addNewsCard(newsContainer, R.drawable.news2, "Ex voluptas cumque eum consequuntur culpa et modi corrupti ut impedit aspernatur!");
        return root;
    }

    private void addNewsCard(LinearLayout container, int imageResId, String description) {
        View newsItem = LayoutInflater.from(getContext()).inflate(R.layout.news_item_layout, container, false);

        ImageView imageView = newsItem.findViewById(R.id.news_image);
        TextView textView = newsItem.findViewById(R.id.news_text);

        imageView.setImageResource(imageResId);
        textView.setText(description);

        // Set default values for the example
        String headline = "Example Headline";
        String date = "25 April 2025";
        String content = "Full article content for: " + description;

        // Add click listener
        newsItem.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), NewsDetailsActivity.class);
            intent.putExtra("imageResId", imageResId);
            intent.putExtra("headline", headline);
            intent.putExtra("date", date);
            intent.putExtra("content", content);
            startActivity(intent);
        });

        container.addView(newsItem);
    }

}
