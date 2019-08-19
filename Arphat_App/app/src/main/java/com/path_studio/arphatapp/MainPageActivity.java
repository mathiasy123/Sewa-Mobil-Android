package com.path_studio.arphatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainPageActivity extends AppCompatActivity {

    private Button mLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        mLogout = (Button) findViewById(R.id.signout_btn);

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sign out
                FirebaseAuth.getInstance().signOut();

                //ganti status_login
                LoginActivity la = new LoginActivity();
                la.status_login = "";

                //ganti halaman
                Intent i = new Intent(MainPageActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
                return;
            }
        });

    }
}
