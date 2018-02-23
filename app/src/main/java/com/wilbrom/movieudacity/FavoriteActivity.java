package com.wilbrom.movieudacity;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.wilbrom.movieudacity.data.MovieContract;

public class FavoriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        String[] projection = new String[]{MovieContract.ResultsEntry.COLUMN_TITLE};
        Cursor cursor = getContentResolver().query(MovieContract.ResultsEntry.CONTENT_URI, projection, null, null, null);
        if (cursor != null)
            Log.d("TAG", "Got cursor");
        else
            Log.d("TAG", "Cursor empty");

        cursor.moveToNext();
        Log.d("TAG", cursor.getString(0));

    }
}
