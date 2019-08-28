package com.path_studio.arphatapp.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.path_studio.arphatapp.Inbox;
import com.path_studio.arphatapp.InboxData;
import com.path_studio.arphatapp.ListInboxAdapter;
import com.path_studio.arphatapp.R;

import java.util.ArrayList;

public class InboxFragment extends Fragment implements View.OnClickListener{

    private RecyclerView rvHeroes;
    private ArrayList<Inbox> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inbox, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
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
}
