package com.wilbrom.movieudacity;


import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wilbrom.movieudacity.data.MovieContract;
import com.wilbrom.movieudacity.models.Results;
import com.wilbrom.movieudacity.utilities.NetworkUtils;

public class DetailActivity extends AppCompatActivity {

    private TextView title;
    private TextView overview;
    private TextView releaseDate;
    private TextView voteAverage;
    private ImageView backdrop;
    private ImageView poster;
    private View parentView;

    private Results results;

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
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        results = (Results) getIntent().getParcelableExtra(Intent.EXTRA_TEXT);

        title.setText(results.getTitle());
        overview.setText(results.getOverView());
        releaseDate.setText(results.getReleaseDate());
        voteAverage.setText(String.valueOf(results.getVoteAverage()));

        Picasso.with(this)
                .load(NetworkUtils.getImageUrl(NetworkUtils.IMAGE_SIZE_w780) + results.getBackdropPath())
                .into(backdrop);

        Picasso.with(this)
                .load(NetworkUtils.getImageUrl(NetworkUtils.IMAGE_SIZE_w154) + results.getPosterPath())
                .placeholder(R.drawable.placeholder_100x150)
                .into(poster);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickFav(View view) {
        Uri uri = getContentResolver().insert(MovieContract.ResultsEntry.CONTENT_URI, getContentValues());

        if (uri != null)
            Snackbar.make(parentView, "Data inserted", Snackbar.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Data inserted", Toast.LENGTH_SHORT).show();
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
}