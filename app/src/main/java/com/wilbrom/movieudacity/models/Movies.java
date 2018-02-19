package com.wilbrom.movieudacity.models;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Movies implements Parcelable {

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    private int page;
    private int totalResults;
    private int totalPages;
    private List<Results> results;

    public Movies() {
    }

    private Movies(Parcel in) {
        this.page = in.readInt();
        this.totalResults = in.readInt();
        this.totalPages = in.readInt();
        in.readTypedList(this.results, Results.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.page);
        parcel.writeInt(this.totalResults);
        parcel.writeInt(this.totalPages);
        parcel.writeTypedList(results);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }
}
