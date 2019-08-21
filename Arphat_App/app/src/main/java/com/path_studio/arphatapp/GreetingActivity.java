package com.path_studio.arphatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GreetingActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;

    private TextView[] mDosts;

    private slider_adapter sliderAdapter;

    private int nCurrentPage;

    private Button nNextBtn;
    private Button nprevBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);

        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);

        nNextBtn = (Button) findViewById(R.id.nextBtn);
        nprevBtn = (Button) findViewById(R.id.prevBtn);

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

        nprevBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View view){
                mSlideViewPager.setCurrentItem(nCurrentPage - 1);
            }
        });

    }

    public void addDotsIndicator(int position){
        mDotLayout.removeAllViews();
        mDosts = new TextView[3];

        for(int i=0; i< mDosts.length; i++){
            mDosts[i] = new TextView(this);
            mDosts[i].setText(Html.fromHtml("&#8226;",0));
            mDosts[i].setTextSize(35);
            mDosts[i].setTextColor(ContextCompat.getColor(this, R.color.colorSolidGrey));

            mDotLayout.addView(mDosts[i]);
        }

        if(mDosts.length>0){
            mDosts[position].setTextColor(ContextCompat.getColor(this, R.color.colorDonker));
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
                nprevBtn.setEnabled(false);
                nprevBtn.setVisibility(View.INVISIBLE);

                nNextBtn.setText("Next");
                nprevBtn.setText("");

            }else if( i == mDosts.length-1){
                nNextBtn.setEnabled(true);
                nprevBtn.setEnabled(true);
                nprevBtn.setVisibility(View.VISIBLE);

                nNextBtn.setText("Finish");
                nprevBtn.setText("Back");
            }else{
                nNextBtn.setEnabled(true);
                nprevBtn.setEnabled(true);
                nprevBtn.setVisibility(View.VISIBLE);

                nNextBtn.setText("Next");
                nprevBtn.setText("Back");
            }

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}
