package com.wilbrom.movieudacity;


import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wilbrom.movieudacity.data.MovieContract;
import com.wilbrom.movieudacity.models.Results;
import com.wilbrom.movieudacity.utilities.NetworkUtils;


public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {

    private static final String BUNDLE_KEY = "bundle-key";
    private static final int LOADER_DB_ID = 33;
    private static final int LOADER_NETWORK_ID = 44;

    private TextView title;
    private TextView overview;
    private TextView releaseDate;
    private TextView voteAverage;
    private ImageView backdrop;
    private ImageView poster;
    private View parentView;
    private FloatingActionButton favoriteBtn;

    private Results results;
    private String callingClassName;
    private boolean isFavorite;
    private int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        parentView = (CoordinatorLayout) findViewById(R.id.coordinator);
        title = (TextView) findViewById(R.id.title);
        overview = (TextView) findViewById(R.id.overview);
        releaseDate = (TextView) findViewById(R.id.release_date);
        voteAverage = (TextView) findViewById(R.id.vote_average);
        backdrop = (ImageView) findViewById(R.id.backdrop);
        poster = (ImageView) findViewById(R.id.poster);
        favoriteBtn = (FloatingActionButton) findViewById(R.id.favorite_fab);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        results = (Results) intent.getParcelableExtra(Intent.EXTRA_TEXT);
        callingClassName = intent.getStringExtra(getString(R.string.class_name_extra));

        title.setText(results.getTitle());
        overview.setText(results.getOverView());
        releaseDate.setText(results.getReleaseDate());
        voteAverage.setText(String.valueOf(results.getVoteAverage()));

        Picasso.with(this)
                .load(NetworkUtils.getImageUrl(NetworkUtils.IMAGE_SIZE_w780) + results.getBackdropPath())
                .into(backdrop);

        Picasso.with(this)
                .load(NetworkUtils.getImageUrl(NetworkUtils.IMAGE_SIZE_w185) + results.getPosterPath())
                .placeholder(R.drawable.placeholder_100x150)
                .into(poster);

        movieId = results.getId();
        checkIfFavorite(movieId);

        Log.d("TAG", NetworkUtils.getReviewsUrl(String.valueOf(movieId), this) + "");
    }

    private void checkIfFavorite(int movieId) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY, String.valueOf(movieId));

        getLoaderManager().initLoader(LOADER_DB_ID, bundle, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

            if (callingClassName.equals(MainActivity.CLASS_NAME))
                NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
            else if (callingClassName.equals(FavoriteActivity.CLASS_NAME))
                NavUtils.navigateUpTo(this, new Intent(this, FavoriteActivity.class));

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickFav(View view) {
        if (!isFavorite) {
            Uri uri = getContentResolver().insert(MovieContract.ResultsEntry.CONTENT_URI, getContentValues());
            if (uri != null)
                Snackbar.make(parentView, getString(R.string.added_favorite), Snackbar.LENGTH_SHORT).show();
        } else {
            String movieIdStr = String.valueOf(movieId);

            Uri uri =  MovieContract.ResultsEntry.CONTENT_URI.buildUpon()
                    .appendPath(movieIdStr)
                    .build();

            String selection = MovieContract.ResultsEntry.COLUMN_MOVIE_ID + "=?";
            String[] selectionArgs = new String[]{movieIdStr};

            int i = getContentResolver().delete(uri, selection, selectionArgs);

            if (i > 0) {
                if (callingClassName.equals(MainActivity.CLASS_NAME))
                    Snackbar.make(parentView, R.string.removed_favorite, Snackbar.LENGTH_SHORT).show();
                else if (callingClassName.equals(FavoriteActivity.CLASS_NAME))
                    finish();
            }
        }
    }

    private ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MovieContract.ResultsEntry.COLUMN_VOTE_COUNT, results.getVoteCount());
        contentValues.put(MovieContract.ResultsEntry.COLUMN_MOVIE_ID, results.getId());
        contentValues.put(MovieContract.ResultsEntry.COLUMN_VIDEO, results.isVideo());
        contentValues.put(MovieContract.ResultsEntry.COLUMN_VOTE_AVERAGE, results.getVoteAverage());
        contentValues.put(MovieContract.ResultsEntry.COLUMN_TITLE, results.getTitle());
        contentValues.put(MovieContract.ResultsEntry.COLUMN_POPULARITY, results.getPopularity());
        contentValues.put(MovieContract.ResultsEntry.COLUMN_POSTER_PATH, results.getPosterPath());
        contentValues.put(MovieContract.ResultsEntry.COLUMN_ORIGINAL_LANGUAGE, results.getOriginalLanguage());
        contentValues.put(MovieContract.ResultsEntry.COLUMN_ORIGINAL_TITLE, results.getOriginalTitle());
        contentValues.put(MovieContract.ResultsEntry.COLUMN_GENRE_IDS, genreIdToString());
        contentValues.put(MovieContract.ResultsEntry.COLUMN_BACKDROP_PATH, results.getBackdropPath());
        contentValues.put(MovieContract.ResultsEntry.COLUMN_ADULT, results.isAdult());
        contentValues.put(MovieContract.ResultsEntry.COLUMN_OVERVIEW, results.getOverView());
        contentValues.put(MovieContract.ResultsEntry.COLUMN_RELEASE_DATE, results.getReleaseDate());

        return contentValues;
    }

    private String genreIdToString() {
        String genreIds = "";

        for (int i : results.getGenreIds()) {
            genreIds += String.valueOf(i) + " ";
        }

        return genreIds;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle bundle) {
        switch (id) {
            case LOADER_DB_ID:
                String movieId = bundle.getString(BUNDLE_KEY);

                Uri uri = MovieContract.ResultsEntry.CONTENT_URI.buildUpon()
                        .appendPath(movieId)
                        .build();

                String selection = MovieContract.ResultsEntry.COLUMN_MOVIE_ID + "=?";
                String[] selectionArgs = new String[]{movieId};

                return new CursorLoader(this,
                        uri,
                        null,
                        selection,
                        selectionArgs,
                        null);
            case LOADER_NETWORK_ID:
                return new AsyncTaskLoader(this) {

                    @Override
                    protected void onStartLoading() {
                        super.onStartLoading();
                    }

                    @Override
                    public Object loadInBackground() {
                        return null;
                    }

                    @Override
                    public void deliverResult(Object data) {
                        super.deliverResult(data);
                    }
                };
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object o) {
        switch (loader.getId()) {
            case LOADER_DB_ID:
                Cursor cursor = (Cursor) o;
                int count = cursor.getCount();

                if (count > 0) {
                    isFavorite = true;
                    favoriteBtn.setImageResource(R.drawable.ic_favorite_white_24dp);
                } else {
                    isFavorite = false;
                    favoriteBtn.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                }

                favoriteBtn.setVisibility(View.VISIBLE);
                break;
            case LOADER_NETWORK_ID:
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {}
}