package com.path_studio.arphatapp.fragment;

import android.content.SharedPreferences;
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
import com.path_studio.arphatapp.Inbox;
import com.path_studio.arphatapp.InboxData;
import com.path_studio.arphatapp.ListInboxAdapter;
import com.path_studio.arphatapp.R;
import com.path_studio.arphatapp.activitiy.SignUpActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InboxFragment extends Fragment implements View.OnClickListener{

    private RecyclerView rvHeroes;
    private ArrayList<Inbox> list = new ArrayList<>();

    private SharedPreferences mSettings;
    private String token;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inbox, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mSettings = getActivity().getSharedPreferences("Login_Data", getActivity().MODE_PRIVATE);
        token = mSettings.getString("Login_Token", "Missing Token");

        get_user();

        rvHeroes = view.findViewById(R.id.rv_heroes);
        rvHeroes.setHasFixedSize(true);

        list.addAll(InboxData.getListData());
        showRecyclerList();
    }

    private void showRecyclerList(){
        rvHeroes.setLayoutManager(new LinearLayoutManager(getActivity()));
        ListInboxAdapter listInboxAdapter = new ListInboxAdapter(list);
        rvHeroes.setAdapter(listInboxAdapter);

        listInboxAdapter.setOnItemClickCallback(new ListInboxAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Inbox data) {
                showSelectedInbox(data);
            }
        });

    }

    private void showSelectedInbox(Inbox inbox) {
        Toast.makeText(getActivity(), "You got message from " + inbox.getEmail(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_to_homePage:
                break;
        }
    }

    private void get_user(){
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        final String url = "http://10.0.2.2:5000/api/user";

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
