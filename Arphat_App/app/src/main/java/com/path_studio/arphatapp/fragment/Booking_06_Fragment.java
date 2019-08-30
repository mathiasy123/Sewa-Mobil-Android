package com.path_studio.arphatapp.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.path_studio.arphatapp.activitiy.HalamanUtamaActivity;
import com.path_studio.arphatapp.R;

public class Booking_06_Fragment extends Fragment implements View.OnClickListener{

    private TextView mBack, mKodeTransaksi;
    private SharedPreferences mSettings;
    private String kode_transaksi;

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

        mSettings = getActivity().getSharedPreferences("Booking_Data", getActivity().MODE_PRIVATE);
        kode_transaksi = mSettings.getString("Kode_Transaksi", "Missing Transaction Code");

        //tampilin kode transaksinya
        mKodeTransaksi = (TextView) view.findViewById(R.id.Text_Kode_Transaksi);
        if (!TextUtils.isEmpty(kode_transaksi)){
            mKodeTransaksi.setText(kode_transaksi);
        }

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