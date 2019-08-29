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
import android.widget.ImageView;
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
import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.google.firebase.auth.TwitterAuthProvider;

import com.path_studio.arphatapp.R;
import com.path_studio.arphatapp.check_internet_connection;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import com.google.firebase.auth.FacebookAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{

    public String status_login="";
    private Button login;
    private ImageView mTwitter, mGoogle, mFacebook;
    private TextView register;
    private EditText mEmail, mPassword;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private DatabaseReference mDatabase;

    private static final int RC_SIGN_IN = 9001;
    public GoogleApiClient mGoogleApiClient;
    private CallbackManager mCallbackManager;
    private TwitterLoginButton mLoginButton;

    private static LoginActivity app;

    private SharedPreferences mSettings;
    private String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //check Internet Connection
        check_internet_connection cic = new check_internet_connection();
        if(cic.check_internet(getApplicationContext()) == false){
            //direct ke halaman Disconnect
            Intent i = new Intent(LoginActivity.this, DisconnectActivity.class);
            startActivity(i);
            finish();
        }

        //------------------------------------------------------------------------------------------

        mSettings = LoginActivity.this.getSharedPreferences("Login_Data", LoginActivity.this.MODE_PRIVATE);
        Log.e("Token: ", mSettings.getString("Login_Token", "Missing Token"));
        token = mSettings.getString("Login_Token", "Missing Token");

        FacebookSdk.sdkInitialize(this.getApplicationContext());

        // Configure Twitter SDK
        Twitter.initialize(this);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(
                getString(R.string.twitter_consumer_key),
                getString(R.string.twitter_consumer_secret)
        );

        TwitterConfig.Builder builder=new TwitterConfig.Builder(this);
        builder.twitterAuthConfig(authConfig);
        Twitter.initialize(builder.build());

        setContentView(R.layout.activity_login);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");

        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                status_login = "facebook";
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                updateUI(null);
            }

            @Override
            public void onError(FacebookException error) {
                updateUI(null);
            }
        });


        // initialize_twitter_login
        mLoginButton = findViewById(R.id.button_twitter_login);
        mLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                status_login = "twitter";
                handleTwitterSession(result.data);
            }

            @Override
            public void failure(TwitterException exception) {
                updateUI(null);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(user != null){
                    Intent i = new Intent(LoginActivity.this, HalamanUtamaActivity.class);
                    startActivity(i);
                    finish();
                    return;
                }
            }
        };

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);

        register = (TextView) findViewById(R.id.register_btn);

        mTwitter = (ImageView) findViewById(R.id.twitter_btn);
        mGoogle = (ImageView) findViewById(R.id.google_btn);
        mFacebook = (ImageView) findViewById(R.id.fb_btn);

        login = (Button) findViewById(R.id.signin_btn);


        register.setOnClickListener(this);
        login.setOnClickListener(this);
        mFacebook.setOnClickListener(this);
        mTwitter.setOnClickListener(this);
        mGoogle.setOnClickListener(this);

        app = this;

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
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signin_btn:
                login();
                break;
            case R.id.register_btn:
                register();
                break;
            case R.id.twitter_btn:
                signIn_Twitter();
                break;
            case R.id.google_btn:
                signIn_Google();
                break;
        }
    }

    private void register(){
        Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(i);
        finish();
    }

    private void login(){
        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Sign in Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
            //give toast kalo email atau passwordnya kosong
            Toast.makeText(LoginActivity.this, "Email and Password Empty", Toast.LENGTH_SHORT).show();

        }else if(!TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
            //give toast kalo passwordnya kosong
            Toast.makeText(LoginActivity.this, "Password Empty", Toast.LENGTH_SHORT).show();

        }else if(TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            //give toast kalo Email kosong
            Toast.makeText(LoginActivity.this, "Email Empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void signIn_Twitter(){
        status_login = "twitter";
    }

    private void signIn_Google(){
        status_login = "google";

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (status_login){
            case "google":
                if (requestCode == RC_SIGN_IN) {
                    GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                    if (result.isSuccess()) {
                        // Google Sign In was successful, authenticate with Firebase
                        GoogleSignInAccount account = result.getSignInAccount();
                        firebaseAuthWithGoogle(account);
                        status_login = "google";
                    } else {
                        // Google Sign In failed, update UI appropriately
                        updateUI(null);
                    }
                }

                break;
            case "twitter":
                // Pass the activity result to the Twitter login button.
                mLoginButton.onActivityResult(requestCode, resultCode, data);

                break;
            case "facebook":
                mCallbackManager.onActivityResult(requestCode, resultCode, data);
                break;
        }

    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(this, HalamanUtamaActivity.class);
            startActivity(intent);
        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        showProgressDialog();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                DatabaseReference current_user_db = mDatabase.child("Users").child("Customers").child(mAuth.getCurrentUser().getUid());
                current_user_db.child("Username").setValue(mAuth.getCurrentUser().getDisplayName());
                current_user_db.child("Email").setValue(mAuth.getCurrentUser().getEmail());
                current_user_db.child("Password").setValue("No Password");
                current_user_db.child("PhoneNumber").setValue("-");
                current_user_db.child("Address").setValue("-");
                current_user_db.child("SignUp Method").setValue("Google Account");

                //masukin data ke database
                insert_user(mAuth.getCurrentUser().getUid(), mAuth.getCurrentUser().getEmail(), "No Password", "0", mAuth.getCurrentUser().getDisplayName(), "-" );

                hideProgressDialog();
            }
        });
    }

    // auth_with_facebook
    private void handleFacebookAccessToken(AccessToken token) {
        showProgressDialog();

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("Facebook Login", "signInWithCredential:onComplete:" + task.isSuccessful());
                if (!task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    updateUI(null);
                } else {
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                }
                hideProgressDialog();
            }
        });
    }

    // auth_with_twitter
    private void handleTwitterSession(TwitterSession session) {
        Log.d("Twitter Login", "handleTwitterSession:" + session);
        showProgressDialog();

        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret
        );

        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("Twitter Login", "signInWithCredential:onComplete:" + task.isSuccessful());
                if (!task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    updateUI(null);
                } else {
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                }
                hideProgressDialog();
            }
        });
    }

    private ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
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
