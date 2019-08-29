package com.path_studio.arphatapp.HistoryRecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.path_studio.arphatapp.InboxRecyclerView.Inbox;
import com.path_studio.arphatapp.R;

import java.util.ArrayList;

public class ListHistoryAdapter extends RecyclerView.Adapter<ListHistoryAdapter.ListViewHolder> {
    private ArrayList<History> listHistory;
    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }
    public ListHistoryAdapter(ArrayList<History> list) {
        this.listHistory = list;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_history, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        History history = listHistory.get(position);

        //tampilkan hasilnya
        holder.mHarga.setText(history.getHarga());
        holder.mStatus.setText(history.getStatus());
        holder.mLokasiStart.setText(history.getLokasi_start());
        holder.mLokasiEnd.setText(history.getLokasi_end());
        holder.mTanggal.setText(history.getTanggal());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listHistory.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listHistory.size();
    }


    class ListViewHolder extends RecyclerView.ViewHolder {
        TextView mHarga, mStatus, mLokasiStart, mLokasiEnd, mTanggal;

        ListViewHolder(View itemView) {
            super(itemView);
            mHarga = itemView.findViewById(R.id.history_harga);
            mStatus = itemView.findViewById(R.id.history_status);
            mLokasiStart = itemView.findViewById(R.id.history_lokasi_start);
            mLokasiEnd = itemView.findViewById(R.id.history_lokasi_end);
            mTanggal = itemView.findViewById(R.id.history_tanggal_booking);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(History data);
    }
}