package com.path_studio.arphatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.path_studio.arphatapp.R;

public class LocationResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_result);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
