package com.path_studio.arphatapp.activitiy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.path_studio.arphatapp.R;
import com.path_studio.arphatapp.check_internet_connection;

public class OurOfficeActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_office);

        //check Internet Connection
        check_internet_connection cic = new check_internet_connection();
        if(cic.check_internet(getApplicationContext()) == false){
            //direct ke halaman Disconnect
            Intent i = new Intent(OurOfficeActivity.this, DisconnectActivity.class);
            startActivity(i);
            finish();
        }

        //------------------------------------------------------------------------------------------

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
