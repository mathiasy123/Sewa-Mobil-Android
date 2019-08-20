package com.path_studio.arphatapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;;import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private Button mLogout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mLogout = (Button) view.findViewById(R.id.signout_btn);

        //------------------------------------------------------------------------------------------
        mLogout.setOnClickListener(this);

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
