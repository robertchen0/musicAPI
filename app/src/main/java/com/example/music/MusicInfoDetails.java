package com.example.music;

import android.os.Parcel;
import android.os.Parcelable;

public class MusicInfoDetails implements Parcelable {
    private String artistName;
    private String collectionName;
    private Double trackPrice;
    private String artworkUrl100;
    private String previewUrl;

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public MusicInfoDetails(){

    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public Double getTrackPrice() {
        return trackPrice;
    }

    public void setTrackPrice(Double trackPrice) {
        this.trackPrice = trackPrice;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    }

    protected MusicInfoDetails(Parcel in) {
        artistName = in.readString();
        collectionName = in.readString();
        trackPrice = in.readDouble();
        artworkUrl100 = in.readString();
        previewUrl = in.readString();
    }

    public static final Creator<MusicInfoDetails> CREATOR = new Creator<MusicInfoDetails>() {
        @Override
        public MusicInfoDetails createFromParcel(Parcel in) {
            return new MusicInfoDetails(in);
        }

        @Override
        public MusicInfoDetails[] newArray(int size) {
            return new MusicInfoDetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(artistName);
        dest.writeString(collectionName);
        dest.writeDouble(trackPrice);
        dest.writeString(artworkUrl100);
        dest.writeString(previewUrl);
    }
}
