package com.path_studio.arphatapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.path_studio.arphatapp.R;
import com.path_studio.arphatapp.activitiy.BookingActivity;
import com.path_studio.arphatapp.activitiy.MapsActivity;
import com.path_studio.arphatapp.activitiy.OurOfficeActivity;
import com.path_studio.arphatapp.Adapter.slider_adapter_promotion;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private slider_adapter_promotion sliderAdapterPromotion;
    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;

    private TextView[] mDosts;
    private int nCurrentPage;

    private ImageButton m1, m2, m3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mSlideViewPager = (ViewPager) view.findViewById(R.id.promotionSlider);
        mDotLayout = (LinearLayout) view.findViewById(R.id.dotsLayoutPromotion);

        sliderAdapterPromotion = new slider_adapter_promotion(getActivity());
        mSlideViewPager.setAdapter(sliderAdapterPromotion);

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);

        //tombol di menu, dimulai dari kiri-kanan
        m1 = (ImageButton) view.findViewById(R.id.btn_booking);
        m1.setOnClickListener(this);

        m2 = (ImageButton) view.findViewById(R.id.btn_location);
        m2.setOnClickListener(this);

        m3 = (ImageButton) view.findViewById(R.id.btn_office_menu);
        m3.setOnClickListener(this);

    }

    public void addDotsIndicator(int position){
        mDotLayout.removeAllViews();
        mDosts = new TextView[3];

        for(int i=0; i< mDosts.length; i++){
            mDosts[i] = new TextView(getActivity());
            mDosts[i].setText(Html.fromHtml("&#8226;",0));
            mDosts[i].setTextSize(35);
            mDosts[i].setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSolidGrey));

            mDotLayout.addView(mDosts[i]);
        }

        if(mDosts.length>0){
            mDosts[position].setTextColor(ContextCompat.getColor(getActivity(), R.color.colorDonker));
        }

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {
            //
        }

        @Override
        public void onPageSelected(int i) {

            addDotsIndicator(i);

            nCurrentPage = i;

        }

        @Override
        public void onPageScrollStateChanged(int i) {
            //
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_office_menu:
                Intent i = new Intent(getActivity(), OurOfficeActivity.class);
                startActivity(i);
                break;
            case R.id.btn_location:
                Intent i_1 = new Intent(getActivity(), MapsActivity.class);
                startActivity(i_1);
                break;
            case R.id.btn_booking:
                Intent i_2 = new Intent(getActivity(), BookingActivity.class);
                startActivity(i_2);
                break;
        }
    }

}
