package com.path_studio.arphatapp.activitiy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.path_studio.arphatapp.R;
import com.path_studio.arphatapp.check_internet_connection;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private Button register;
    private TextView login;
    private EditText mEmail, mPassword, mUsername;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private DatabaseReference mDatabase;
    private static final String TAG = "SignUpActivity";

    private ProgressDialog pDialog;
    private SharedPreferences mSettings;
    private String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //check Internet Connection
        check_internet_connection cic = new check_internet_connection();
        if(cic.check_internet(getApplicationContext()) == false){
            //direct ke halaman Disconnect
            Intent i = new Intent(SignUpActivity.this, DisconnectActivity.class);
            startActivity(i);
            finish();
        }

        //------------------------------------------------------------------------------------------

        pDialog = new ProgressDialog(this);
        mSettings = SignUpActivity.this.getSharedPreferences("Login_Data", SignUpActivity.this.MODE_PRIVATE);
        token = mSettings.getString("Login_Token_Admin", "Missing Token");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(user != null){
                    Intent i = new Intent(SignUpActivity.this, HalamanUtamaActivity.class);
                    startActivity(i);
                    finish();
                    return;
                }
            }
        };

        mUsername = (EditText) findViewById(R.id.username);
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);

        login = (TextView) findViewById(R.id.login_btn);
        register = (Button) findViewById(R.id.signup_btn);

        login.setOnClickListener(this);
        register.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.signup_btn:
                SafetyNet.getClient(this).verifyWithRecaptcha("6Lfd67QUAAAAAKMFFu0x76quje1nz8MJL9YS41mL")
                        .addOnSuccessListener( this,
                                new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                                    @Override
                                    public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                                        // Indicates communication with reCAPTCHA service was
                                        // successful.
                                        String userResponseToken = response.getTokenResult();
                                        if (!userResponseToken.isEmpty()) {
                                            // Validate the user response token using the
                                            // reCAPTCHA siteverify API.

                                            final String username = mUsername.getText().toString();
                                            final String email = mEmail.getText().toString();
                                            final String password = mPassword.getText().toString();

                                            //check apakah semua sudah terisi
                                            if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password) && TextUtils.isEmpty(username)){
                                                Toast.makeText(SignUpActivity.this, "Please Fill All Form before Submit", Toast.LENGTH_SHORT).show();
                                            }else if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(username)){
                                                //check apakah user sudah terdaftar
                                                check_user_in_database(email, password, "0", username ,"-");

                                            }else if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(username)){
                                                Toast.makeText(SignUpActivity.this, "Username, Email, or Password is Still Empty", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                })
                        .addOnFailureListener( this, new OnFailureListener() {
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
        }
    }

    private void check_user_in_database(String email, String password, String get_phone, String username, String address){
        //ambil username dan phone number nya dari database
        DatabaseReference current_user_db = mDatabase.child("Users").child("Customers");

        current_user_db.orderByChild("Email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){

                    for(DataSnapshot data: dataSnapshot.getChildren()){
                        if (data.child("Email").getValue() == email) {
                            //sudah terdaftar tinggal login
                            Toast.makeText(SignUpActivity.this, "Email ini sudah terdaftar", Toast.LENGTH_SHORT).show();
                        } else {
                            //belum terdaftar, maka daftarin dulu
                            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(!task.isSuccessful()){
                                        Toast.makeText(SignUpActivity.this, "Sign up Error", Toast.LENGTH_SHORT).show();
                                    }else{
                                        String user_id = mAuth.getCurrentUser().getUid();

                                        DatabaseReference current_user_db = mDatabase.child("Users").child("Customers").child(user_id);
                                        current_user_db.child("Username").setValue(username);
                                        current_user_db.child("Email").setValue(email);
                                        current_user_db.child("Password").setValue(password);
                                        current_user_db.child("PhoneNumber").setValue("-");
                                        current_user_db.child("Address").setValue("-");
                                        current_user_db.child("SignUp Method").setValue("Email&Password");

                                        //masukan juga di database
                                        insert_user(user_id, email, password, "0", username ,"-");

                                    }
                                }
                            });
                        }
                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void insert_user(String get_UID, String get_email, String get_password, String get_phone, String get_username, String get_address) {
        final String uid = get_UID;
        final String email = get_email;
        final String password = get_password;
        final String phone = get_phone;
        final String username = get_username;
        final String address = get_address;

        //Creating a string request
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:5000/api/user";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
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
                params.put("Authorization", "Bearer "+ token);
                return params;
            }

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("UID", uid);
                params.put("telepon", phone);
                params.put("username", username);
                params.put("password", password);
                params.put("email", email);
                params.put("alamat", address);

                return params;
            }
        };
        queue.add(postRequest);

    }

}
