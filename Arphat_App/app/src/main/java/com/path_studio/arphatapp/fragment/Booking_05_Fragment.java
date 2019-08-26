package com.path_studio.arphatapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.path_studio.arphatapp.activitiy.HalamanUtamaActivity;
import com.path_studio.arphatapp.R;

public class Booking_05_Fragment extends Fragment implements View.OnClickListener{

    private TextView mPrev, mNext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking_05_, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mNext = (TextView) view.findViewById(R.id.submit_booking);
        mNext.setOnClickListener(this);

        mPrev = (TextView) view.findViewById(R.id.back_to_homePage);
        mPrev.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.submit_booking:
                ft.replace(R.id.fragment_container_booking, new Booking_06_Fragment(), "Next to Page 6 Booking");
                ft.commit();
                break;
            case R.id.back_to_homePage:
                Intent i = new Intent(getActivity(), HalamanUtamaActivity.class);
                startActivity(i);
                break;
        }
    }

}