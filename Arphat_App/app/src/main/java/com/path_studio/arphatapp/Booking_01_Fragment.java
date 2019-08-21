package com.path_studio.arphatapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Booking_01_Fragment extends Fragment implements View.OnClickListener{

    private Button mNext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking_01_, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mNext = (Button) view.findViewById(R.id.nextBtn_booking_01);

        mNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nextBtn_booking_01:
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container_booking, new Booking_02_Fragment(), "Halaman Ke 2 Booking");
                ft.commit();
                break;
        }
    }
}
