package com.path_studio.arphatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class BookingActivity extends AppCompatActivity implements View.OnClickListener {

    private String kapasitas = "";
    private String pickup_location = "";
    private String destination_location = "";
    private String take_off_date = "";
    private String take_off_time = "";
    private String return_date = "";
    private String return_time = "";
    private int banyak_penumpang = 0;
    private boolean use_driver = true;

    private ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        mBack = (ImageView) findViewById(R.id.backButton_booking);

        mBack.setOnClickListener(this);

        //set halaman fragment jadi halaman booking pertama
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_booking,
                new Booking_01_Fragment()).commit();

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backButton_booking:
                Intent i = new Intent(BookingActivity.this, MainPageActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }
}
