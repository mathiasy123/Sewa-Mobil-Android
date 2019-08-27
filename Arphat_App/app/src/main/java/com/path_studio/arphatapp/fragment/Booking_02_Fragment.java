package com.path_studio.arphatapp.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.path_studio.arphatapp.R;
import com.path_studio.arphatapp.Adapter.slider_adapter_choose_car;

public class Booking_02_Fragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private slider_adapter_choose_car sliderAdapterChooseCar;
    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;

    private TextView[] mDosts;
    private int nCurrentPage;

    private Button mNext;
    private TextView mPrev;

    private Switch use_driver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking_02_, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        use_driver = (Switch) view.findViewById(R.id.include_driver);
        use_driver.setOnCheckedChangeListener(this);

        //keperluan pilih mobil
        mSlideViewPager = (ViewPager) view.findViewById(R.id.choose_car_slide);
        mDotLayout = (LinearLayout) view.findViewById(R.id.dotsLayoutPromotion);

        sliderAdapterChooseCar = new slider_adapter_choose_car(getActivity());
        mSlideViewPager.setAdapter(sliderAdapterChooseCar);

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);

        //inisialisasi tombol
        mNext = (Button) view.findViewById(R.id.pilihan_mobil_btn);
        mNext.setOnClickListener(this);

        mPrev = (TextView) view.findViewById(R.id.prev_booking_01);
        mPrev.setOnClickListener(this);
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
        final FragmentTransaction ft = getFragmentManager().beginTransaction();

        switch (view.getId()) {
            case R.id.prev_booking_01:
                ft.replace(R.id.fragment_container_booking, new Booking_01_Fragment(), "Back to First Page Booking");
                ft.commit();
                break;
            case R.id.pilihan_mobil_btn:
                //check apakah form sudah terisi
                if(nCurrentPage>(-1) && nCurrentPage<9){
                    //dapetin jenis mobilnya
                    SharedPreferences mSettings = getActivity().getSharedPreferences("Booking_data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putInt("jenis_mobil", nCurrentPage);
                    editor.apply();

                    //pindah ke halaman selanjutnya
                    ft.replace(R.id.fragment_container_booking, new Booking_03_Fragment(), "Halaman Ke 3 Booking");
                    ft.commit();
                }else{
                    Toast.makeText(getActivity(), "Pilihan tidak terdaftar", Toast.LENGTH_SHORT).show();
                }

                break;
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        SharedPreferences mSettings = getActivity().getSharedPreferences("Booking_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        if(isChecked){
            //switch is on
            editor.putBoolean("iclude_driver", true);
        }else{
            //switch is off
            editor.putBoolean("iclude_driver", false);
        }
        editor.apply();
    }
}
