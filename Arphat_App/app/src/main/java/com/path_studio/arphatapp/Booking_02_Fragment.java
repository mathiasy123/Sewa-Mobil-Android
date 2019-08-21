package com.path_studio.arphatapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Booking_02_Fragment extends Fragment implements View.OnClickListener{

    private slider_adapter_choose_car sliderAdapterChooseCar;
    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;

    private TextView[] mDosts;
    private int nCurrentPage;

    private Button mNext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking_02_, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        //keperluan pilih mobil
        mSlideViewPager = (ViewPager) view.findViewById(R.id.choose_car_slide);
        mDotLayout = (LinearLayout) view.findViewById(R.id.dotsLayoutPromotion);

        sliderAdapterChooseCar = new slider_adapter_choose_car(getActivity());
        mSlideViewPager.setAdapter(sliderAdapterChooseCar);

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);

//        mNext = (Button) view.findViewById(R.id.nextBtn_booking_01);
//
//        mNext.setOnClickListener(this);
    }

    public void addDotsIndicator(int position){
        mDotLayout.removeAllViews();
        mDosts = new TextView[5];

        for(int i=0; i< mDosts.length; i++){
            mDosts[i] = new TextView(getActivity());
            mDosts[i].setText(Html.fromHtml("&#8226;",0));
            mDosts[i].setTextSize(35);
            mDosts[i].setTextColor(ContextCompat.getColor(getActivity(), R.color.colorSolidGrey));

            mDotLayout.addView(mDosts[i]);
        }

        if(mDosts.length>0){
            if(position>=0 && position<2){
                //untuk slide 1 dan 2
                mDosts[position].setTextColor(ContextCompat.getColor(getActivity(), R.color.colorDonker));
            }else if(position>=2 && position<6){
                //untuk slide 3 dan 7
                mDosts[2].setTextColor(ContextCompat.getColor(getActivity(), R.color.colorDonker));
            }else if(position>=6 && position<9){
                //untuk slide 8 dan 9
                mDosts[position-4].setTextColor(ContextCompat.getColor(getActivity(), R.color.colorDonker));
            }

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
//            case R.id.nextBtn_booking_01:
//                final FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.replace(R.id.fragment_container_booking, new Booking_02_Fragment(), "Halaman Ke 2 Booking");
//                ft.commit();
//                break;
        }
    }
}
