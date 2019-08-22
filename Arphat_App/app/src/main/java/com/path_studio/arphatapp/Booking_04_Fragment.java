package com.path_studio.arphatapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Booking_04_Fragment extends Fragment implements View.OnClickListener{

    private TextView mPrev, mNext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking_04_, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mNext = (TextView) view.findViewById(R.id.next_booking_05);
        mNext.setOnClickListener(this);

        mPrev = (TextView) view.findViewById(R.id.prev_booking_03);
        mPrev.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.next_booking_05:
                ft.replace(R.id.fragment_container_booking, new Booking_05_Fragment(), "Next to Page 5 Booking");
                ft.commit();
                break;
            case R.id.prev_booking_03:
                ft.replace(R.id.fragment_container_booking, new Booking_03_Fragment(), "Back to Page 3 Booking");
                ft.commit();
                break;
        }
    }

}