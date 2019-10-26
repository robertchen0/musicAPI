package com.example.music;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MusicInfoPojo implements Parcelable {
    private List<ItemDetails> items;

    public List<ItemDetails> getItems() {
        return items;
    }

    public void setItems(List<ItemDetails> items) {
        this.items = items;
    }

    public  MusicInfoPojo(){

    }

    protected MusicInfoPojo(Parcel in) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            items = in.readParcelableList(items, ItemDetails.class.getClassLoader());
        }
        items = in.readArrayList(ItemDetails.class.getClassLoader());
    }

    public static final Creator<MusicInfoPojo> CREATOR = new Creator<MusicInfoPojo>() {
        @Override
        public MusicInfoPojo createFromParcel(Parcel in) {
            return new MusicInfoPojo(in);
        }

        @Override
        public MusicInfoPojo[] newArray(int size) {
            return new MusicInfoPojo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dest.writeParcelableList(items, flags);
        }
        dest.writeList(items);
    }
}
