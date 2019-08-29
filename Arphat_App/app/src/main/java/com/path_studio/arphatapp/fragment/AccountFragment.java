package com.path_studio.arphatapp.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.path_studio.arphatapp.activitiy.LoginActivity;
import com.path_studio.arphatapp.R;

import java.io.InputStream;
import java.net.URL;

public class AccountFragment extends Fragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private DatabaseReference mDatabase;

    private TextView mprofileName, mprofileEmail, mDisplayUsername, mDisplayEmail, mDisplayPhone, mDisplayAddress;
    private de.hdodenhof.circleimageview.CircleImageView mImageView;
    private Button mLogout, mSetAddress, mSetPhone;

    private AlertDialog.Builder dialog;
    private LayoutInflater inflater;
    private View dialogView;
    private EditText m_txt_no_telp, m_txt_alamat;

    private ProgressDialog pDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Please wait...");

        mSetPhone = view.findViewById(R.id.set_phone_number);
        mSetAddress = view.findViewById(R.id.set_address);
        mDisplayPhone = view.findViewById(R.id.display_phoneNumber);
        mDisplayAddress = view.findViewById(R.id.display_address);

        mSetPhone.setOnClickListener(this);
        mSetAddress.setOnClickListener(this);

        //bagian google
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

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
                if(user != null){
                    updateUI(user);
                }else{
                    Intent i = new Intent(getActivity(), LoginActivity.class);
                    startActivity(i);
                    return;
                }
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

            showDialog();

            //ambil username dan phone number nya dari database
            DatabaseReference current_user_db = mDatabase.child("Users").child("Customers");

            current_user_db.orderByChild("Email").equalTo(mAuth.getCurrentUser().getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot datas: dataSnapshot.getChildren()){
                        String Username = datas.child("Username").getValue().toString();
                        String Email = datas.child("Email").getValue().toString();
                        String Address = datas.child("Address").getValue().toString();
                        String Phone = datas.child("PhoneNumber").getValue().toString();

                        //tampilkan hasilnya
                        mprofileName.setText(Username);
                        mDisplayUsername.setText(Username);

                        mprofileEmail.setText(Email);
                        mDisplayEmail.setText(Email);

                        mDisplayPhone.setText(Phone);
                        mDisplayAddress.setText(Address);

                        hideDialog();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                    hideDialog();
                }
            });

        } else {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getActivity(), "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signout_btn:
                logout();
                break;
            case R.id.set_phone_number:
                DialogForm_1();
                break;
            case R.id.set_address:
                DialogForm_2();
                break;
        }
    }

    public void logout(){

        //sign out

        if(!TextUtils.isEmpty(FirebaseAuth.getInstance().getCurrentUser().getProviderId())){
            for (UserInfo user: FirebaseAuth.getInstance().getCurrentUser().getProviderData()) {
                if (user.getProviderId().equals("facebook.com")) {
                    Facebook_signOut();
                }else if(user.getProviderId().equals("google.com")){
                    Google_signOut();
                }else if(user.getProviderId().equals("twitter.com")){
                    Twitter_signOut();
                }else{
                    Email_signout();
                }
            }
        }else{
            Email_signout();
        }

    }

    public void Twitter_signOut() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setMessage(R.string.logout);
        alert.setCancelable(false);
        alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                //Twitter.logOut();
                updateUI(null);
            }
        });
        alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.show();
    }

    public void Google_signOut() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setMessage(R.string.logout);
        alert.setCancelable(false);
        alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Firebase sign out
                FirebaseAuth.getInstance().signOut();
                updateUI(null);
            }
        });
        alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.show();
    }

    public void Facebook_signOut() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setMessage(R.string.logout);
        alert.setCancelable(false);
        alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Firebase sign out
                FirebaseAuth.getInstance().signOut();
                // Facebook sign out
                LoginManager.getInstance().logOut();
                updateUI(null);
            }
        });
        alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.show();
    }

    public void Email_signout(){
        // Firebase sign out
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setMessage(R.string.logout);
        alert.setCancelable(false);
        alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Firebase sign out
                FirebaseAuth.getInstance().signOut();
                updateUI(null);
            }
        });
        alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.show();
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

    private void DialogForm_1() {
        dialog = new AlertDialog.Builder(getActivity());
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.custom_alert_phone_number, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Update your Phone Number");

        m_txt_no_telp    = (EditText) dialogView.findViewById(R.id.form_no_telp);

        dialog.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                showDialog();
                String hasil    = m_txt_no_telp.getText().toString();
                String user_id = mAuth.getUid();

                //masukin ke database
                DatabaseReference current_user_db = mDatabase.child("Users").child("Customers").child(user_id);
                current_user_db.child("PhoneNumber").setValue(hasil);

                mDisplayPhone.setText(hasil);
                hideDialog();
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void DialogForm_2() {
        dialog = new AlertDialog.Builder(getActivity());
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.custom_alert_address, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Update your Phone Number");

        m_txt_alamat     = (EditText) dialogView.findViewById(R.id.form_alamat);

        dialog.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                showDialog();
                String hasil    = m_txt_alamat.getText().toString();
                String user_id = mAuth.getUid();

                //masukin ke database
                DatabaseReference current_user_db = mDatabase.child("Users").child("Customers").child(user_id);
                current_user_db.child("Address").setValue(hasil);

                mDisplayAddress.setText(hasil);
                hideDialog();
                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
