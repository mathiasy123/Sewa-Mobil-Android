package com.path_studio.arphatapp.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.path_studio.arphatapp.activitiy.HalamanUtamaActivity;
import com.path_studio.arphatapp.R;

public class Booking_05_Fragment extends Fragment implements View.OnClickListener{

    private TextView mPrev, mNext;
    private static final String TAG = "SubmitBookingActivity";

    private TextView mMerkMobil, mJumlahPenumpang, mSupir, mPickup, mDestination, mStart, mEnd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking_05_, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mMerkMobil = (TextView) view.findViewById(R.id.merk_mobil);
        mJumlahPenumpang = (TextView) view.findViewById(R.id.jumlah_penumpang);
        mSupir = (TextView) view.findViewById(R.id.Penggunaan_supir);
        mPickup = (TextView) view.findViewById(R.id.pickup_location);
        mDestination = (TextView) view.findViewById(R.id.destination_location);
        mStart = (TextView) view.findViewById(R.id.start_travel);
        mEnd = (TextView) view.findViewById(R.id.end_travel);

        UI_update();

        //------------------------------------------------------------------------------------------
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
                SafetyNet.getClient(getActivity()).verifyWithRecaptcha("6Lfd67QUAAAAAKMFFu0x76quje1nz8MJL9YS41mL")
                        .addOnSuccessListener( getActivity(),
                                new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                                    @Override
                                    public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                                        // Indicates communication with reCAPTCHA service was
                                        // successful.
                                        String userResponseToken = response.getTokenResult();
                                        if (!userResponseToken.isEmpty()) {
                                            // Validate the user response token using the
                                            // reCAPTCHA siteverify API.

                                            ft.replace(R.id.fragment_container_booking, new Booking_06_Fragment(), "Next to Page 6 Booking");
                                            ft.commit();

                                        }
                                    }
                                })
                        .addOnFailureListener( getActivity(), new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (e instanceof ApiException) {
                                    // An error occurred when communicating with the
                                    // reCAPTCHA service. Refer to the status code to
                                    // handle the error appropriately.
                                    ApiException apiException = (ApiException) e;
                                    int statusCode = apiException.getStatusCode();
                                    Log.d(TAG, "Error: " + CommonStatusCodes
                                            .getStatusCodeString(statusCode));
                                } else {
                                    // A different, unknown type of error occurred.
                                    Log.d(TAG, "Error: " + e.getMessage());
                                }
                            }
                        });
                break;
            case R.id.back_to_homePage:
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setMessage(R.string.booking_cancle_validation);
                alert.setCancelable(false);
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent i1 = new Intent(getActivity(), HalamanUtamaActivity.class);
                        startActivity(i1);
                    }
                });
                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alert.show();
                break;
        }
    }

    private void UI_update(){
        SharedPreferences mSettings = getActivity().getSharedPreferences("Booking_data", Context.MODE_PRIVATE);
        switch (mSettings.getInt("jenis_mobil", 0)){
            case 0:
                mMerkMobil.setText("Alya");
                break;
            case 1:
                mMerkMobil.setText("Yaris");
                break;
            case 2:
                mMerkMobil.setText("Avanza");
                break;
            case 3:
                mMerkMobil.setText("Calya");
                break;
            case 4:
                mMerkMobil.setText("Kijang Inova");
                break;
            case 5:
                mMerkMobil.setText("Xenia");
                break;
            case 6:
                mMerkMobil.setText("Mobilio");
                break;
            case 7:
                mMerkMobil.setText("Elf");
                break;
            case 8:
                mMerkMobil.setText("Hiace");
                break;
        }

        mJumlahPenumpang.setText(mSettings.getInt("jumlah_penumpang", 0) + " Penumpang");

        if(mSettings.getBoolean("iclude_driver", false) == true){
            mSupir.setText("Yes");
        }else{
            mSupir.setText("No");
        }

        mPickup.setText(mSettings.getString("pickup_location","Missing Pick Up Location"));
        mDestination.setText(mSettings.getString("destination_location","Missing Destination Location"));
    }

}