package com.wilbrom.movieudacity;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.wilbrom.movieudacity.adapters.FavoriteListAdapter;
import com.wilbrom.movieudacity.data.MovieContract;
import com.wilbrom.movieudacity.models.Results;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView movieRecyclerView;
    private FavoriteListAdapter adapter;
    private List<Results> movieResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        movieRecyclerView = (RecyclerView) findViewById(R.id.movie_recycler_view);

        movieRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new FavoriteListAdapter(this);
        movieRecyclerView.setAdapter(adapter);

        Cursor cursor = getContentResolver().query(MovieContract.ResultsEntry.CONTENT_URI, null, null, null, null);

        if (cursor != null)
            Log.d("TAG", "Got cursor");
        else
            Log.d("TAG", "Cursor empty");

        int i = 0;
        Log.d("TAG", "Count: " + cursor.getCount());
        while (cursor.getCount() > i) {
            cursor.moveToNext();
            movieResults.add(new Results(cursor.getString(cursor.getColumnIndex(MovieContract.ResultsEntry.COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndex(MovieContract.ResultsEntry.COLUMN_RELEASE_DATE)),
                    cursor.getDouble(cursor.getColumnIndex(MovieContract.ResultsEntry.COLUMN_VOTE_AVERAGE)),
                    cursor.getString(cursor.getColumnIndex(MovieContract.ResultsEntry.COLUMN_POSTER_PATH))));
            i++;
        }

        adapter.setMovieResults(movieResults);
    }
}
