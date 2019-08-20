package com.path_studio.arphatapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.InputStream;
import java.net.URL;

public class AccountFragment extends Fragment implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private TextView mprofileName, mprofileEmail, mDisplayUsername, mDisplayEmail, mDisplayPhone, mDisplayAddress;
    private de.hdodenhof.circleimageview.CircleImageView mImageView;
    private Button mLogout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //bagian google
        mAuth = FirebaseAuth.getInstance();

        mprofileName = (TextView) view.findViewById(R.id.profileName);
        mprofileEmail = (TextView) view.findViewById(R.id.profileEmail);

        mDisplayUsername = (TextView) view.findViewById(R.id.display_username);
        mDisplayEmail = (TextView) view.findViewById(R.id.display_email);
        mDisplayPhone = (TextView) view.findViewById(R.id.display_phoneNumber);
        mDisplayAddress = (TextView) view.findViewById(R.id.display_address);

        mImageView = (de.hdodenhof.circleimageview.CircleImageView) view.findViewById(R.id.profileImg);

        mLogout = (Button) view.findViewById(R.id.signout_btn);

        //------------------------------------------------------------------------------------------
        mLogout.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                updateUI(user);
            }
        };

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            if (user.getPhotoUrl() != null) {
                new AccountFragment.DownloadImageTask().execute(user.getPhotoUrl().toString());
            }

            mprofileName.setText(user.getDisplayName());
            mDisplayUsername.setText(user.getDisplayName());

            mprofileEmail.setText(user.getEmail());
            mDisplayEmail.setText(user.getEmail());
        } else {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap mIcon = null;
            try {
                InputStream in = new URL(urls[0]).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                mImageView.getLayoutParams().width = (getResources().getDisplayMetrics().widthPixels / 100) * 24;
                mImageView.setImageBitmap(result);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signout_btn:
                logout();
                break;
        }
    }

    public void logout(){
        LoginActivity la = new LoginActivity();

        //sign out
        FirebaseAuth.getInstance().signOut();
        switch (la.status_login){
            case "google":
                la.Google_signOut();
                break;

            case "facebook":
                // Facebook sign out
                la.Facebook_signOut();
                break;

            case "twitter":
                la.Twitter_signOut();
                break;
        }

        //ganti status_login
        la.status_login = "";

        //ganti halaman
        Intent i = new Intent(getActivity(), LoginActivity.class);
        startActivity(i);
        return;
    }

}
