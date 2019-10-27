package com.example.music;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class CustomViewHolder extends RecyclerView.ViewHolder {

    TextView tvArtistName, tvCollectionName, tvTrackPrice;
    ImageView imageView;
    String previewUrl;
    String image;
    String TAG;
    CardView cardView;


    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);
        tvArtistName = itemView.findViewById(R.id.tv_artist);
        tvCollectionName = itemView.findViewById(R.id.tv_music_title);
        tvTrackPrice = itemView.findViewById(R.id.tv_price);
        imageView = itemView.findViewById(R.id.iv_image);
        cardView = itemView.findViewById(R.id.cardview);
    }
        public void bindItem(final MusicInfoDetails item, final ActivityListener listener) {
            previewUrl = item.getPreviewUrl();
            image = item.getArtworkUrl100();

            tvArtistName.setText(item.getArtistName());
            tvCollectionName.setText(item.getCollectionName());
            tvTrackPrice.setText("$ "+ (Double.toString(item.getTrackPrice())));

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.previewMusic(item);
                    Log.d(TAG, "onClick: clicked");
                }
            });
        }

    }
