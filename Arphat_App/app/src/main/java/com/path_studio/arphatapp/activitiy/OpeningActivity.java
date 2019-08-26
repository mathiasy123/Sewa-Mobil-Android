package com.path_studio.arphatapp.activitiy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.path_studio.arphatapp.R;

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
                Intent i = new Intent(OpeningActivity.this, PermissionActivity.class);
                startActivity(i);
                finish();
            }
        }, TIME_OUT);

    }
}
