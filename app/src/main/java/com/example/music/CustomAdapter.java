package com.example.music;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private MusicInfoPojo dataSet;

    public void setDataSet(MusicInfoPojo dataSet) {
        this.dataSet = dataSet;

    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.music_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.bindItem(dataSet.getItems().get(position).getMusicInfo());
    }

    @Override
    public int getItemCount() {
        return dataSet != null ? dataSet.getItems().size() : 0;
    }
}
