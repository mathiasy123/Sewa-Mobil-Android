package com.path_studio.arphatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class OurOfficeActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_office);

        mBack = (ImageView) findViewById(R.id.backButton);

        mBack.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.backButton:
                Intent i = new Intent(OurOfficeActivity.this, HalamanUtamaActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }
}
