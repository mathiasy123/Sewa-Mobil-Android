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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.path_studio.arphatapp.activitiy.HalamanUtamaActivity;
import com.path_studio.arphatapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Booking_05_Fragment extends Fragment implements View.OnClickListener{

    private TextView mPrev, mNext;
    private static final String TAG = "SubmitBookingActivity";
    private SharedPreferences mSettings;
    private String token_customer, token_admin;
    private String user_id;

    private TextView mMerkMobil, mJumlahPenumpang, mSupir, mPickup, mDestination, mStart, mEnd;

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private FirebaseAuth mAuth;

    private int penggunaan_supir;
    private String mulai_sewa;
    private String akhir_sewa;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking_05_, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();

        mSettings = getActivity().getSharedPreferences("Login_Data", getActivity().MODE_PRIVATE);
        token_customer = mSettings.getString("Login_Token_Customer", "Missing Token");
        token_admin = mSettings.getString("Login_Token_Admin", "Missing Token");

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

        //------------------------------------------------------------------------------------------
        get_user_id();
    }

    @Override
    public void onClick(View view) {
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
                                            //kirim datanya
                                            post_booking_data();
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
                mMerkMobil.setText("Daihatsu Alya");
                break;
            case 1:
                mMerkMobil.setText("Toyota Yaris");
                break;
            case 2:
                mMerkMobil.setText("Toyota Avanza");
                break;
            case 3:
                mMerkMobil.setText("Toyota Calya");
                break;
            case 4:
                mMerkMobil.setText("Toyota Kijang Inova");
                break;
            case 5:
                mMerkMobil.setText("Daihatsu Xenia");
                break;
            case 6:
                mMerkMobil.setText("Honda Mobilio");
                break;
            case 7:
                mMerkMobil.setText("Isuzu Elf");
                break;
            case 8:
                mMerkMobil.setText("Toyota Hiace");
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

        String starting = mSettings.getString("takeOff_Date","0000-00-00")+ ", "+mSettings.getString("takeOff_time","00:00");
        mStart.setText(starting);

        String ending = mSettings.getString("return_Date","0000-00-00")+ ", "+mSettings.getString("return_time","00:00");
        mEnd.setText(ending);
    }

    private void generate_Booking_Code(){
        String booking_code = "R"; //R for rent
        String tanggal, bulan, tahun;
        String id_user = user_id;
        String random_code;


        //Mendapatkan current date
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd");
        tanggal = df.format(c);

        SimpleDateFormat df_1 = new SimpleDateFormat("MM");
        bulan = df_1.format(c);

        SimpleDateFormat df_2 = new SimpleDateFormat("yy");
        tahun = df_2.format(c);

        //--------------------------------------------------------------------------------------
        //Rangkai kodenya
        String full_code = booking_code+user_id+tanggal+bulan+tahun+randomAlphaNumeric(3);
        //exp: R01170819ARP

        //simpen ke share pref
        SharedPreferences mSetting = getActivity().getSharedPreferences("Booking_Data", getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = mSetting.edit();
        editor.putString("Kode_Transaksi", full_code);
        editor.apply();

    }

    private void get_user_id(){
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        final String url = "http://10.0.2.2:5000/api/user";

        // prepare the Request
        JsonArrayRequest jsArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray responseArray) {
                        try {
                            StringBuilder textViewData = new StringBuilder();
                            //Parse the JSON response array by iterating over it
                            for (int i = 0; i < responseArray.length(); i++) {
                                JSONObject response = responseArray.getJSONObject(i);

                                if((response.getString("email")).equalsIgnoreCase(mAuth.getCurrentUser().getEmail())){
                                    //jika user emailnya sama sama yang lagi login maka simpen data user id nya
                                    user_id = Integer.toString(response.getInt("id"));
                                    Log.e("User id :", user_id);
                                }

                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }) {

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + token_admin);
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        // add it to the RequestQueue
        queue.add(jsArrayRequest);
    }

    private static String randomAlphaNumeric(int count) {

        StringBuilder builder = new StringBuilder();

        while (count-- != 0) {

            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());

            builder.append(ALPHA_NUMERIC_STRING.charAt(character));

        }

        return builder.toString();

    }

    private void post_booking_data(){
        SharedPreferences mSettings = getActivity().getSharedPreferences("Booking_data", Context.MODE_PRIVATE);
        final int id_user_id = Integer.parseInt(user_id);
        final int id_jenis_mobil = mSettings.getInt("jenis_mobil", 0)+1; //karena mulai dari 0

        if(mSettings.getBoolean("iclude_driver", false) == true){
            penggunaan_supir = 1;
        }else{
            penggunaan_supir = 0;
        }

        mulai_sewa = mSettings.getString("takeOff_Date","0000-00-00")+ " "+mSettings.getString("takeOff_time","00:00")+":00";
        akhir_sewa = mSettings.getString("return_Date","0000-00-00")+ " "+mSettings.getString("return_time","00:00")+":00";

        final String lokasi_pickup = mSettings.getString("pickup_location","Missing Pick Up Location");
        final String lokasi_destinasi = mSettings.getString("destination_location","Missing Destination Location");


        //Creating a string request
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://10.0.2.2:5000/api/sewa";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        if (response!=null){
                            Log.d("Response", response);

                            //jika berhasil
                            generate_Booking_Code();

                            final FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.fragment_container_booking, new Booking_06_Fragment(), "Next to Page 6 Booking");
                            ft.commit();
                        }else{
                            Log.d("Response", "Null response");
                            Toast.makeText(getActivity(), "Booking Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error.Response", error.toString());
                        String json = null;
                        NetworkResponse response = error.networkResponse;
                        if(response != null && response.data != null){
                            switch(response.statusCode){
                                case 400:
                                    json = new String(response.data);
                                    System.out.println(json);
                                    break;
                                case 500:
                                    json = new String(response.data);
                                    System.out.println(json);
                                    break;
                            }
                            //Additional cases
                        }
                    }
                }
        ) {

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("Authorization", "Bearer "+ token_customer);
                return params;
            }

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("id_user",String.valueOf(id_user_id));
                params.put("id_jenis_mobil",String.valueOf(id_jenis_mobil));
                params.put("penggunaan_supir",String.valueOf(penggunaan_supir));
                params.put("mulai_sewa",mulai_sewa);
                params.put("akhir_sewa",akhir_sewa);
                params.put("lokasi_pickup",lokasi_pickup);
                params.put("lokasi_destinasi",lokasi_destinasi);

                return params;
            }

        };
        queue.add(postRequest);
    }

}