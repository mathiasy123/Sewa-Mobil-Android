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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.path_studio.arphatapp.R;

public class Booking_03_Fragment extends Fragment implements View.OnClickListener{

    private TextView mPrev, mNext;
    private EditText form_pickup, form_destination;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking_03_, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        form_pickup = (EditText) view.findViewById(R.id.pickup_text);
        form_destination = (EditText) view.findViewById(R.id.destination_text);

        mNext = (TextView) view.findViewById(R.id.next_booking_04);
        mNext.setOnClickListener(this);

        mPrev = (TextView) view.findViewById(R.id.prev_booking_02);
        mPrev.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.next_booking_04:
                if(!TextUtils.isEmpty(form_pickup.getText()) && !TextUtils.isEmpty(form_destination.getText())){
                    //tampung lokasinya
                    SharedPreferences mSettings = getActivity().getSharedPreferences("Booking_data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putString("pickup_location", form_pickup.getText().toString());
                    editor.putString("destination_location", form_destination.getText().toString());
                    editor.apply();

                    ft.replace(R.id.fragment_container_booking, new Booking_04_Fragment(), "Back to Page 2 Booking");
                    ft.commit();
                }else{
                    Toast.makeText(getActivity(), "Form is still empty...", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.prev_booking_02:
                ft.replace(R.id.fragment_container_booking, new Booking_02_Fragment(), "Next to Page 4 Booking");
                ft.commit();
                break;
        }
    }

}
