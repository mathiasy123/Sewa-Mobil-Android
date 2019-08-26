package com.path_studio.arphatapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class check_internet_connection {

    private boolean connected = false;
    
    public boolean check_internet(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if(activeNetwork != null){
            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE || activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                //we are connected to a network
                connected = true;
            }
            else {
                connected = false;
            }
        }else{
            connected = false;
        }

        return connected;
    }

    private Object getSystemService(String connectivityService) {
        return connectivityService;
    }

}
