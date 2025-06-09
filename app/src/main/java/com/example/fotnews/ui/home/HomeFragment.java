package com.example.fotnews.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.fotnews.NewsDetailsActivity;
import com.example.fotnews.R;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate the custom layout (make sure this matches your actual XML filename in res/layout/)
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Example of how to reference views
        LinearLayout newsContainer = root.findViewById(R.id.news_container);

        // Optionally add news cards dynamically
        addNewsCard(newsContainer, R.drawable.news1, "Lorem ipsum dolor sit amet. Sed omnis quam ex veniam omnis...");
        addNewsCard(newsContainer, R.drawable.news2, "Ex voluptas cumque eum consequuntur culpa et modi corrupti ut impedit aspernatur!");
        addNewsCard(newsContainer, R.drawable.news3, "Lorem ipsum dolor sit amet. Sed omnis quam ex veniam omnis...");
        addNewsCard(newsContainer, R.drawable.news4, "Ex voluptas cumque eum consequuntur culpa et modi corrupti ut impedit aspernatur!");
        addNewsCard(newsContainer, R.drawable.news1, "Lorem ipsum dolor sit amet. Sed omnis quam ex veniam omnis...");
        addNewsCard(newsContainer, R.drawable.news2, "Ex voluptas cumque eum consequuntur culpa et modi corrupti ut impedit aspernatur!");
        addNewsCard(newsContainer, R.drawable.news2, "Ex voluptas cumque eum consequuntur culpa et modi corrupti ut impedit aspernat");
        addNewsCard(newsContainer, R.drawable.news2, "Ex voluptas cumque eum consequuntur culpa et modi corrupti ut impedit aspernat");

        return root;
    }

    private void addNewsCard(LinearLayout container, int imageResId, String description) {
        View newsItem = LayoutInflater.from(getContext()).inflate(R.layout.news_item_layout, container, false);

        ImageView imageView = newsItem.findViewById(R.id.news_image);
        TextView textView = newsItem.findViewById(R.id.news_text);

        imageView.setImageResource(imageResId);
        textView.setText(description);

        // Set default values for the example
        String headline = "Trump congratulates Canada's Carney as they agree to meet in \n" +
                "'near future'\n" +
                "\n" ;
        String date = "25 April 2025";
        String content = "Lorem ipsum dolor sit amet. Et molestiae nihil est distinctio quos eos itaque recusandae a quisquam galisum. Aut galisum perferendis sit quidem recusandae et impedit explicabo et beatae dolores in fugit beatae. Sed deserunt voluptatem et ducimus fuga a suscipit illo ut deleniti praesentium qui molestias officia.\n" +
                "\n" +
                "Aut voluptas corrupti nam voluptatem libero et nihil doloremque sed aspernatur sint eum debitis quas. Hic rerum perferendis ut sunt tempore aut dignissimos pariatur est beatae magnam cum Quis assumenda in doloribus ipsum. In voluptatibus atque est accusantium esse et repellat aperiam sed excepturi assumenda. Vel itaque architecto quo minima voluptates sit voluptatem incidunt.\n" +
                "\n" +
                "Et impedit facere et dolores earum et vero ipsum. Est consequuntur error qui enim necessitatibus in quia necessitatibus et fugiat unde ex aliquam cupiditate in laboriosam animi. Aut nostrum quos sit doloremque recusandae qui aliquam nulla ab eaque delectus a blanditiis nostrum et natus architecto.\n" +
                "\n" +
                "Et impedit facere et dolores earum et vero ipsum. Est consequuntur error qui enim necessitatibus in quia necessitatibus et fugiat unde ex aliquam cupiditate in laboriosam animi.\n" +
                "\n" +
                "Lorem ipsum dolor sit amet. Et molestiae nihil est distinctio quos eos itaque recusandae a quisquam galisum. Aut galisum perferendis sit quidem recusandae et impedit explicabo et beatae dolores in fugit beatae. Sed deserunt voluptatem et ducimus fuga a suscipit illo ut deleniti praesentium qui molestias officia.\n" +
                "\n" +
                "assumenda in doloribus ipsum. In voluptatibus atque est accusantium esse et repellat aperiam sed excepturi assumenda. Vel itaque architecto quo minima voluptates sit voluptatem incidunt";

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