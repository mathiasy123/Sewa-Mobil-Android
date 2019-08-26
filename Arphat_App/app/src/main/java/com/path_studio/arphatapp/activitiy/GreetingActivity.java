package com.path_studio.arphatapp.activitiy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.path_studio.arphatapp.R;
import com.path_studio.arphatapp.slider_adapter;

public class GreetingActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;

    private TextView[] mDosts;

    private slider_adapter sliderAdapter;

    private int nCurrentPage;

    private Button nNextBtn;
    private Button nPrevBtn;

    private SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);

        // proses check apakah aplikasi first time run
        prefs = getSharedPreferences("com.android.application", MODE_PRIVATE);

        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);

        nNextBtn = (Button) findViewById(R.id.nextBtn);
        nPrevBtn = (Button) findViewById(R.id.prevBtn);

        sliderAdapter = new slider_adapter(this);
        mSlideViewPager.setAdapter(sliderAdapter );

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);

        //onclick listener
        nNextBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View view){
                if(nCurrentPage != mDosts.length-1){
                    mSlideViewPager.setCurrentItem(nCurrentPage + 1);
                }else{
                    //ke halaman selanjutnya
                    Intent i = new Intent(GreetingActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        });

        nPrevBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View view){
                mSlideViewPager.setCurrentItem(nCurrentPage - 1);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            prefs.edit().putBoolean("firstrun", false).commit();
        }else{
            //skip langsung ke halaman login
            Intent i = new Intent(GreetingActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
    }

    public void addDotsIndicator(int position){
        mDotLayout.removeAllViews();
        mDosts = new TextView[3];

        for(int i=0; i< mDosts.length; i++){
            mDosts[i] = new TextView(this);
            mDosts[i].setText(Html.fromHtml("&#8226;",0));
            mDosts[i].setTextSize(35);
            mDosts[i].setTextColor(ContextCompat.getColor(this, R.color.colorDarkBlue));

            mDotLayout.addView(mDosts[i]);
        }

        if(mDosts.length>0){
            mDosts[position].setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        }

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

            addDotsIndicator(i);

            nCurrentPage = i;

            if(i==0){
                nNextBtn.setEnabled(true);
                nPrevBtn.setEnabled(false);
                nPrevBtn.setVisibility(View.INVISIBLE);

                nNextBtn.setText("Next");
                nPrevBtn.setText("");

            }else if( i == mDosts.length-1){
                nNextBtn.setEnabled(true);
                nPrevBtn.setEnabled(true);
                nPrevBtn.setVisibility(View.VISIBLE);

                nNextBtn.setText("Finish");
                nPrevBtn.setText("Back");
            }else{
                nNextBtn.setEnabled(true);
                nPrevBtn.setEnabled(true);
                nPrevBtn.setVisibility(View.VISIBLE);

                nNextBtn.setText("Next");
                nPrevBtn.setText("Back");
            }

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}
