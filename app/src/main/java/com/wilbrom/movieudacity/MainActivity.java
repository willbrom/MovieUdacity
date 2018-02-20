package com.wilbrom.movieudacity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.wilbrom.movieudacity.adapters.MovieListAdapter;
import com.wilbrom.movieudacity.models.Movies;
import com.wilbrom.movieudacity.models.Results;
import com.wilbrom.movieudacity.utilities.JsonUtils;
import com.wilbrom.movieudacity.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.MovieItemInteractionListener {

    private View container;
    private RecyclerView movieList;
    private MovieListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = (FrameLayout) findViewById(R.id.container);
        movieList = (RecyclerView) findViewById(R.id.movie_list);

        movieList.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));

        adapter = new MovieListAdapter(this);
        movieList.setAdapter(adapter);

        URL url = NetworkUtils.getUrl(NetworkUtils.POPULAR);
        new MovieAsyncTask().execute(url);
    }

    @Override
    public void onClickMovieItem(Results results) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, results);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_setting:
                startActivity(new Intent(this, SettingActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class MovieAsyncTask extends AsyncTask<URL, Void, Movies> {

        @Override
        protected Movies doInBackground(URL... urls) {
            Movies movies = null;

            try {
                String res = NetworkUtils.getHttpResponse(urls[0]);
                movies = JsonUtils.parseMovieJson(res);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return movies;
        }

        @Override
        protected void onPostExecute(Movies movies) {
            if (movies != null) {
                adapter.setResultsList(movies.getResults());
            } else {
                Snackbar.make(container, getString(R.string.error_occurred), Snackbar.LENGTH_INDEFINITE).show();
            }
        }
    }
}
