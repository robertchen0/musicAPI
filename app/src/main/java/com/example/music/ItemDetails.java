package com.example.music;

import android.os.Parcel;
import android.os.Parcelable;

class ItemDetails implements Parcelable {
    private MusicInfoDetails musicInfo;

    public ItemDetails(){

    }

    protected ItemDetails(Parcel in) {
        musicInfo = in.readParcelable(MusicInfoDetails.class.getClassLoader());
    }

    public static final Creator<ItemDetails> CREATOR = new Creator<ItemDetails>() {
        @Override
        public ItemDetails createFromParcel(Parcel in) {
            return new ItemDetails(in);
        }

        @Override
        public ItemDetails[] newArray(int size) {
            return new ItemDetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public MusicInfoDetails getMusicInfo() {
        return musicInfo;
    }

    public void setMusicInfo(MusicInfoDetails musicInfo) {
        this.musicInfo = musicInfo;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(musicInfo, flags);
    }
}
