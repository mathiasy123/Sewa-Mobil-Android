package com.path_studio.arphatapp.activitiy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.path_studio.arphatapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OpeningActivity extends AppCompatActivity {

    private static int TIME_OUT = 5000; //Time to launch the another activity
    private String token_login = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        login_API();

        final View myLayout = findViewById(R.id.openingScreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(OpeningActivity.this, PermissionActivity.class);
                startActivity(i);
                finish();
            }
        }, TIME_OUT);

    }

    private void login_API() {
        //Getting values from edit texts
        final String username = "admin";
        final String password = "1234";
        //Creating a string request
        StringRequest request = new StringRequest(Request.Method.POST, "http://10.0.2.2:5000/api/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.equals(null)) {
                            Log.e("Your Array Response", response);

                            try {
                                JSONObject responeJsonObject = new JSONObject(response);
                                token_login = responeJsonObject.getString("Token");

                                //share nilai tokennya
                                SharedPreferences mSettings = OpeningActivity.this.getSharedPreferences("Login_Data", OpeningActivity.this.MODE_PRIVATE);
                                SharedPreferences.Editor editor = mSettings.edit();
                                editor.putString("Login_Token", token_login);
                                editor.apply();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Log.e("Your Array Response", "Data Null");
                            Toast.makeText(OpeningActivity.this, "Data null", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        Log.e("error is ", "" + error);
                        Toast.makeText(OpeningActivity.this, "The server unreachable", Toast.LENGTH_LONG).show();

                    }
                }) {

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+token_login);
                return params;
            }

            //Pass Your Parameters here
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("role", "admin");
                params.put("username", username);
                params.put("password", password);
                return params;
            }

        };

        //Adding the string request to the queue
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }

}
