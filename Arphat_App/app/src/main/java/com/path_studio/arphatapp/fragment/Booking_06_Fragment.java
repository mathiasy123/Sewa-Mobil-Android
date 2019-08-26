package com.path_studio.arphatapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.path_studio.arphatapp.activitiy.HalamanUtamaActivity;
import com.path_studio.arphatapp.R;

public class Booking_06_Fragment extends Fragment implements View.OnClickListener{

    private TextView mBack;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking_06_, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mBack = (TextView) view.findViewById(R.id.back_to_homePage);
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_to_homePage:
                Intent i = new Intent(getActivity(), HalamanUtamaActivity.class);
                startActivity(i);
                break;
        }
    }

}