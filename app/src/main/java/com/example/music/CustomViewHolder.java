package com.example.music;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;


public class CustomViewHolder extends RecyclerView.ViewHolder {

    TextView tvArtistName, tvCollectionName, tvTrackPrice;
    ImageView imageView;
    Bitmap bitmap;
    String TAG;



    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);
        tvArtistName = itemView.findViewById(R.id.tv_artist);
        tvCollectionName = itemView.findViewById(R.id.tv_music_title);
        tvTrackPrice = itemView.findViewById(R.id.tv_price);
        imageView = itemView.findViewById(R.id.iv_image);
    }
        public void bindItem(final MusicInfoDetails item) {
            tvArtistName.setText(item.getArtistName());
            tvCollectionName.setText(item.getCollectionName());
            tvTrackPrice.setText(Double.toString(item.getTrackPrice()));

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//
                    Log.d(TAG, "onClick: Clicked");
                }

                private void startActivity(Intent intent) {
                }
            });
        }

    }
