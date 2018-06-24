package com.wilbrom.movieudacity.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Genre implements Parcelable {
    private String title;
    private int id;

    public Genre(String title, int id) {
        this.title = title;
        this.id = id;
    }

    protected Genre(Parcel in) {
        title = in.readString();
        id = in.readInt();
    }

    public static final Creator<Genre> CREATOR = new Creator<Genre>() {
        @Override
        public Genre createFromParcel(Parcel in) {
            return new Genre(in);
        }

        @Override
        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeInt(id);
    }
}
