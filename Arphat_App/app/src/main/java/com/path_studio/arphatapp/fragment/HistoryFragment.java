package com.path_studio.arphatapp.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.path_studio.arphatapp.HistoryRecyclerView.History;
import com.path_studio.arphatapp.HistoryRecyclerView.HistoryData;
import com.path_studio.arphatapp.HistoryRecyclerView.ListHistoryAdapter;
import com.path_studio.arphatapp.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HistoryFragment extends Fragment implements View.OnClickListener{

    private SharedPreferences mSettings;
    private String token;

    private RecyclerView rvHistory;
    private ArrayList<History> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //get token login
        mSettings = getActivity().getSharedPreferences("Login_Data", getActivity().MODE_PRIVATE);
        token = mSettings.getString("Login_Token", "Missing Token");

        get_Booking();

        rvHistory = view.findViewById(R.id.rv_heroes);
        rvHistory.setHasFixedSize(true);

        list.addAll(HistoryData.getListData());
        showRecyclerList();
    }

    private void showRecyclerList(){
        rvHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        ListHistoryAdapter listHistoryAdapter = new ListHistoryAdapter(list);
        rvHistory.setAdapter(listHistoryAdapter);

        listHistoryAdapter.setOnItemClickCallback(new ListHistoryAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(History data) {
                showSelectedInbox(data);
            }
        });

    }

    private void showSelectedInbox(History history) {
        Toast.makeText(getActivity(), "Booking untuk tanggal: " + history.getTanggal(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_to_homePage:
                break;
        }
    }

    private void get_Booking(){
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        final String url = "http://10.0.2.2:5000/api/sewa";

        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
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
                params.put("Authorization", "Bearer " + token);
                return params;
            }
        };

        // add it to the RequestQueue
        queue.add(getRequest);
    }

}
