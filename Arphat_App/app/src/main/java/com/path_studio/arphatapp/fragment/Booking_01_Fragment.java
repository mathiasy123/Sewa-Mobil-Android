package com.path_studio.arphatapp.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.path_studio.arphatapp.R;


public class Booking_01_Fragment extends Fragment implements View.OnClickListener{

    private Button mNext;
    private EditText form_jumlah_penumpang;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking_01_, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mNext = (Button) view.findViewById(R.id.nextBtn_booking_01);
        form_jumlah_penumpang = (EditText) view.findViewById(R.id.form_banyak_penumpang);

        mNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nextBtn_booking_01:

                //check apakah form sudah terisi
                if(!TextUtils.isEmpty(form_jumlah_penumpang.getText()) && Integer.parseInt(form_jumlah_penumpang.getText().toString()) > 0){
                    //simpan data sementara di share pref
                    SharedPreferences mSettings = getActivity().getSharedPreferences("Booking_data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = mSettings.edit();
                    int tampung = Integer.parseInt(form_jumlah_penumpang.getText().toString());
                    editor.putInt("jumlah_penumpang", tampung);
                    editor.apply();

                    //lanjut ke halaman selanjutnya
                    final FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container_booking, new Booking_02_Fragment(), "Halaman Ke 2 Booking");
                    ft.commit();
                }else if(!TextUtils.isEmpty(form_jumlah_penumpang.getText()) && Integer.parseInt(form_jumlah_penumpang.getText().toString()) <= 0){
                    Toast.makeText(getActivity(), "Penumpang minimal berjumlah 1 orang", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Form is still empty...", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
