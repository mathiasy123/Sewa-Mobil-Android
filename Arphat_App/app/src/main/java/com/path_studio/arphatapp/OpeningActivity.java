package com.path_studio.arphatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class OpeningActivity extends AppCompatActivity {

    private static int TIME_OUT = 5000; //Time to launch the another activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        final View myLayout = findViewById(R.id.openingScreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(OpeningActivity.this, GreetingActivity.class);
                startActivity(i);
                finish();
            }
        }, TIME_OUT);

    }
}
