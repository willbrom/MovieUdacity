package com.wilbrom.movieudacity;


import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.wilbrom.movieudacity.adapters.FavoriteListAdapter;
import com.wilbrom.movieudacity.data.MovieContract;
import com.wilbrom.movieudacity.interfaces.MovieItemInteractionListener;
import com.wilbrom.movieudacity.models.Results;

public class FavoriteActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, MovieItemInteractionListener {

    public static final String CLASS_NAME = FavoriteActivity.class.getSimpleName();
    private static final int LOADER_ID = 22;

    private RecyclerView movieRecyclerView;
    private FavoriteListAdapter adapter;
    private Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        movieRecyclerView = (RecyclerView) findViewById(R.id.movie_recycler_view);

        movieRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new FavoriteListAdapter(this);
        movieRecyclerView.setAdapter(adapter);

        LoaderManager manager = getLoaderManager();
        Loader<Cursor> loader = manager.getLoader(LOADER_ID);

        if (loader != null)
            manager.initLoader(LOADER_ID, null, this);
        else
            manager.restartLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                MovieContract.ResultsEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCursor = cursor;
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        adapter.swapCursor(null);
    }

    @Override
    public void onClickMovieItem(Results results) {}

    @Override
    public void onClickFavoriteItem(int position) {
        if (mCursor != null) {
            Log.d("TAG", "position is: " + position);
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, getResults(position));
            intent.putExtra(getString(R.string.class_name_extra), CLASS_NAME);
            startActivity(intent);
        }
    }

    private Results getResults(int position) {
        mCursor.moveToPosition(position);

        int movieId = mCursor.getInt(mCursor.getColumnIndex(MovieContract.ResultsEntry.COLUMN_MOVIE_ID));
        String title = mCursor.getString(mCursor.getColumnIndex(MovieContract.ResultsEntry.COLUMN_TITLE));
        String overView = mCursor.getString(mCursor.getColumnIndex(MovieContract.ResultsEntry.COLUMN_OVERVIEW));
        String releaseDate = mCursor.getString(mCursor.getColumnIndex(MovieContract.ResultsEntry.COLUMN_RELEASE_DATE));
        double voteAverage = mCursor.getDouble(mCursor.getColumnIndex(MovieContract.ResultsEntry.COLUMN_VOTE_AVERAGE));
        String posterPath = mCursor.getString(mCursor.getColumnIndex(MovieContract.ResultsEntry.COLUMN_POSTER_PATH));
        String backdropPath = mCursor.getString(mCursor.getColumnIndex(MovieContract.ResultsEntry.COLUMN_BACKDROP_PATH));

        return new Results(movieId, title, overView, releaseDate, voteAverage, posterPath, backdropPath);
    }
}
