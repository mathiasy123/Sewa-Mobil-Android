package com.path_studio.arphatapp.activitiy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.path_studio.arphatapp.R;
import com.path_studio.arphatapp.check_internet_connection;
import com.path_studio.arphatapp.fragment.Booking_01_Fragment;

public class BookingActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        //check Internet Connection
        check_internet_connection cic = new check_internet_connection();
        if(cic.check_internet(getApplicationContext()) == false){
            //direct ke halaman Disconnect
            Intent i = new Intent(BookingActivity.this, DisconnectActivity.class);
            startActivity(i);
            finish();
        }

        //------------------------------------------------------------------------------------------

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
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                Intent i = new Intent(BookingActivity.this, HalamanUtamaActivity.class);
                                startActivity(i);
                                finish();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);
                builder.setMessage("Are you sure you want to Back To Home Page?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

                break;
        }
    }
}
