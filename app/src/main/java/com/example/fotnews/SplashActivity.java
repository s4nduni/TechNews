package com.example.fotnews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {

    private static final int SPLASH_DURATION = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash); // connects to the layout

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, SignupActivity.class);
            startActivity(intent);
            finish();
        }, 3000); // 3-second delay
    }
}