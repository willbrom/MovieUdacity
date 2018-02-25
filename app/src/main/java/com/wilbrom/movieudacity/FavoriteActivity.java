package com.wilbrom.movieudacity;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wilbrom.movieudacity.adapters.FavoriteListAdapter;
import com.wilbrom.movieudacity.data.MovieContract;


public class FavoriteActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_ID = 22;
    private RecyclerView movieRecyclerView;
    private FavoriteListAdapter adapter;

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
            adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        adapter.swapCursor(null);
    }
}
