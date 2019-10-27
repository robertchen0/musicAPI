package com.example.music;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;


public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private MusicInfoPojo dataSet;
    private ActivityListener listener;
    String url;

    public void setDataSet(MusicInfoPojo dataSet, ActivityListener listener) {
        this.dataSet = dataSet;
        this.listener = listener;

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
        holder.bindItem(dataSet.getItems().get(position).getMusicInfo(), listener);
        Uri uri = Uri.parse(holder.image);
        Context context = holder.imageView.getContext();
        Picasso.with(context).load(uri).resize(150,150).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return dataSet != null ? dataSet.getItems().size() : 0;
    }
}
