package com.wilbrom.movieudacity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.wilbrom.movieudacity.adapters.MovieListAdapter;
import com.wilbrom.movieudacity.models.Movies;
import com.wilbrom.movieudacity.models.Results;
import com.wilbrom.movieudacity.utilities.JsonUtils;
import com.wilbrom.movieudacity.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.MovieItemInteractionListener {

    private RecyclerView movieList;
    private MovieListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        intent.putExtra("par", results);
        startActivity(intent);
    }

    public class MovieAsyncTask extends AsyncTask<URL, Void, Movies> {

        @Override
        protected Movies doInBackground(URL... urls) {
            Movies movies = null;

            try {
                String res = NetworkUtils.getHttpResponse(urls[0]);
                Log.d("TAG", res);
                movies = JsonUtils.parseMovieJson(res);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return movies;
        }

        @Override
        protected void onPostExecute(Movies movies) {
            if (movies != null) {
                Log.d("TAG", "Page: " + movies.getPage());
                adapter.setResultsList(movies.getResults());
            } else {
                Log.d("TAG", "Some thing went wrong");
            }
        }
    }
}
