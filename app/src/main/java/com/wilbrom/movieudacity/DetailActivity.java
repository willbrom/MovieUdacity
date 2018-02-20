package com.wilbrom.movieudacity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wilbrom.movieudacity.models.Results;
import com.wilbrom.movieudacity.utilities.NetworkUtils;

public class DetailActivity extends AppCompatActivity {

    private TextView title;
    private TextView overview;
    private TextView releaseDate;
    private TextView voteAverage;
    private ImageView backdrop;

    private Results results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        title = (TextView) findViewById(R.id.title);
        overview = (TextView) findViewById(R.id.overview);
        releaseDate = (TextView) findViewById(R.id.release_date);
        voteAverage = (TextView) findViewById(R.id.vote_average);
        backdrop = (ImageView) findViewById(R.id.backdrop);


        results = (Results) getIntent().getParcelableExtra("par");

        title.setText(results.getTitle());
        overview.setText(results.getOverView());
        releaseDate.setText(results.getReleaseDate());
        voteAverage.setText(String.valueOf(results.getVoteAverage()));

        Picasso.with(this)
                .load(NetworkUtils.getImageUrl(NetworkUtils.IMAGE_SIZE_w780) + results.getBackdropPath())
                .into(backdrop);
    }
}
