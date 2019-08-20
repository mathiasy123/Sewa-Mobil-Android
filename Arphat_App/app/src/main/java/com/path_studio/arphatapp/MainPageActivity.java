package com.path_studio.arphatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

public class MainPageActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout mHome, mHistory, mPayment, mInbox, mAccount;
    public TextView tHome, tHistory, tPayment, tInbox, tAccount;
    public ImageView iHome, iHistory, iPayment, iInbox,iAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        mHome = (LinearLayout) findViewById(R.id.menuHome);
        mHistory = (LinearLayout) findViewById(R.id.menuHistory);
        mPayment = (LinearLayout) findViewById(R.id.menuPayment);
        mInbox = (LinearLayout) findViewById(R.id.menuInbox);
        mAccount = (LinearLayout) findViewById(R.id.menuAccount);

        tHome = (TextView) findViewById(R.id.textMenuHome);
        tHistory = (TextView) findViewById(R.id.textMenuHistory);
        tPayment = (TextView) findViewById(R.id.textMenuPayment);
        tInbox = (TextView) findViewById(R.id.textMenuInbox);
        tAccount = (TextView) findViewById(R.id.textMenuAccount);

        iHome = (ImageView) findViewById(R.id.iconHome);
        iHistory = (ImageView) findViewById(R.id.iconHistory);
        iPayment = (ImageView) findViewById(R.id.iconPayment);
        iInbox = (ImageView) findViewById(R.id.iconInbox);
        iAccount = (ImageView) findViewById(R.id.iconAccount);

        //------------------------------------------------------------------------------------------

        mHome.setOnClickListener(this);
        mHistory.setOnClickListener(this);
        mPayment.setOnClickListener(this);
        mInbox.setOnClickListener(this);
        mAccount.setOnClickListener(this);

        //set menu status home
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
        setMenuActive("Home");

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.menuHome:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                setMenuActive("Home");
                break;
            case R.id.menuHistory:
                setMenuActive("History");
                break;
            case R.id.menuPayment:
                setMenuActive("Payment");
                break;
            case R.id.menuInbox:
                setMenuActive("Inbox");
                break;
            case R.id.menuAccount:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AccountFragment()).commit();
                setMenuActive("Account");
                break;
        }
    }

    public void setMenuActive(String menu){

        switch (menu){
            case "Home":
                //home icon and text color skyblue
                tHome.setTextColor(getResources().getColor(R.color.colorSkyBlue));
                iHome.setImageResource(R.drawable.ic_home_skyblue);

                //set yang lain jadi gray
                tHistory.setTextColor(getResources().getColor(R.color.colorMenuGray));
                tPayment.setTextColor(getResources().getColor(R.color.colorMenuGray));
                tInbox.setTextColor(getResources().getColor(R.color.colorMenuGray));
                tAccount.setTextColor(getResources().getColor(R.color.colorMenuGray));

                iHistory.setImageResource(R.drawable.ic_history_gray);
                iPayment.setImageResource(R.drawable.ic_payment_gary);
                iInbox.setImageResource(R.drawable.ic_inbox_gray);
                iAccount.setImageResource(R.drawable.ic_account_circle_gray);

                break;
            case "History":

                //history icon and text color skyblue
                tHistory.setTextColor(getResources().getColor(R.color.colorSkyBlue));
                iHistory.setImageResource(R.drawable.ic_history_skyblue);

                //set yang lain jadi gray
                tHome.setTextColor(getResources().getColor(R.color.colorMenuGray));
                tPayment.setTextColor(getResources().getColor(R.color.colorMenuGray));
                tInbox.setTextColor(getResources().getColor(R.color.colorMenuGray));
                tAccount.setTextColor(getResources().getColor(R.color.colorMenuGray));

                iHome.setImageResource(R.drawable.ic_home_gray);
                iPayment.setImageResource(R.drawable.ic_payment_gary);
                iInbox.setImageResource(R.drawable.ic_inbox_gray);
                iAccount.setImageResource(R.drawable.ic_account_circle_gray);

                break;
            case "Payment":

                //payment icon and text color skyblue
                tPayment.setTextColor(getResources().getColor(R.color.colorSkyBlue));
                iPayment.setImageResource(R.drawable.ic_payment_skyblue);

                //set yang lain jadi gray
                tHome.setTextColor(getResources().getColor(R.color.colorMenuGray));
                tHistory.setTextColor(getResources().getColor(R.color.colorMenuGray));
                tInbox.setTextColor(getResources().getColor(R.color.colorMenuGray));
                tAccount.setTextColor(getResources().getColor(R.color.colorMenuGray));

                iHome.setImageResource(R.drawable.ic_home_gray);
                iHistory.setImageResource(R.drawable.ic_history_gray);
                iInbox.setImageResource(R.drawable.ic_inbox_gray);
                iAccount.setImageResource(R.drawable.ic_account_circle_gray);

                break;
            case "Inbox":

                //inbox icon and text color skyblue
                tInbox.setTextColor(getResources().getColor(R.color.colorSkyBlue));
                iInbox.setImageResource(R.drawable.ic_inbox_skyblue);

                //set yang lain jadi gray
                tHome.setTextColor(getResources().getColor(R.color.colorMenuGray));
                tHistory.setTextColor(getResources().getColor(R.color.colorMenuGray));
                tPayment.setTextColor(getResources().getColor(R.color.colorMenuGray));
                tAccount.setTextColor(getResources().getColor(R.color.colorMenuGray));

                iHome.setImageResource(R.drawable.ic_home_gray);
                iHistory.setImageResource(R.drawable.ic_history_gray);
                iPayment.setImageResource(R.drawable.ic_payment_gary);
                iAccount.setImageResource(R.drawable.ic_account_circle_gray);

                break;
            case "Account":

                //account icon and text color skyblue
                tAccount.setTextColor(getResources().getColor(R.color.colorSkyBlue));
                iAccount.setImageResource(R.drawable.ic_account_circle_skyblue);

                //set yang lain jadi gray
                tHome.setTextColor(getResources().getColor(R.color.colorMenuGray));
                tHistory.setTextColor(getResources().getColor(R.color.colorMenuGray));
                tPayment.setTextColor(getResources().getColor(R.color.colorMenuGray));
                tInbox.setTextColor(getResources().getColor(R.color.colorMenuGray));

                iHome.setImageResource(R.drawable.ic_home_gray);
                iHistory.setImageResource(R.drawable.ic_history_gray);
                iPayment.setImageResource(R.drawable.ic_payment_gary);
                iInbox.setImageResource(R.drawable.ic_inbox_gray);

                break;
        }

    }

}
