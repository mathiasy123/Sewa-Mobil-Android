package com.path_studio.arphatapp.activitiy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.path_studio.arphatapp.R;
import com.path_studio.arphatapp.check_internet_connection;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private Button register;
    private TextView login;
    private EditText mEmail, mPassword, mUsername;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private DatabaseReference mDatabase;
    private static final String TAG = "SignUpActivity";

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
                SafetyNet.getClient(this).verifyWithRecaptcha("6LeW2LQUAAAAAFreXTKjdwXoM-CCvir_Vjk_qk6I")
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
                                                        }
                                                    }
                                                });
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
}
