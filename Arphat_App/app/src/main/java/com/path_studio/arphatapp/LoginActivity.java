package com.path_studio.arphatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.auth.FacebookAuthProvider;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{

    public String status_login="";
    private Button login;
    private ImageView mTwitter, mGoogle, mFacebook;
    private TextView register;
    private EditText mEmail, mPassword;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    private DatabaseReference mDatabase;

    private View mProgressView;

    private static final int RC_SIGN_IN = 9001;
    public GoogleApiClient mGoogleApiClient;
    private CallbackManager mCallbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
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

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(user != null){
                    Intent i = new Intent(LoginActivity.this, MainPageActivity.class);
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

        mProgressView = findViewById(R.id.login_progress);


        register.setOnClickListener(this);
        login.setOnClickListener(this);
        mFacebook.setOnClickListener(this);
        mTwitter.setOnClickListener(this);
        mGoogle.setOnClickListener(this);

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

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Sign in Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                    } else {
                        // Google Sign In failed, update UI appropriately
                        updateUI(null);
                    }
                }

                break;
            case "twitter":
                break;
            case "facebook":
                mCallbackManager.onActivityResult(requestCode, resultCode, data);
                break;
        }

    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(this, MainPageActivity.class);
            startActivity(intent);
        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        //showProgressDialog();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //hideProgressDialog();
            }
        });
    }

    // auth_with_facebook
    private void handleFacebookAccessToken(AccessToken token) {
        //showProgressDialog();

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //hideProgressDialog();
            }
        });
    }

}
