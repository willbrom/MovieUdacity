package com.wilbrom.movieudacity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.wilbrom.movieudacity.adapters.MovieListAdapter;
import com.wilbrom.movieudacity.models.Movies;
import com.wilbrom.movieudacity.utilities.JsonUtils;
import com.wilbrom.movieudacity.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private RecyclerView movieList;
    private MovieListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieList = (RecyclerView) findViewById(R.id.movie_list);
        movieList.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));

        adapter = new MovieListAdapter();
        movieList.setAdapter(adapter);
        URL url = NetworkUtils.getUrl(NetworkUtils.POPULAR);
        new MovieAsyncTask().execute(url);
    }

    public class MovieAsyncTask extends AsyncTask<URL, Void, Movies> {

        @Override
        protected Movies doInBackground(URL... urls) {
            Movies movies = null;

            try {
                movies = JsonUtils.parseMovieJson(NetworkUtils.getHttpResponse(urls[0]));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return movies;
        }

        @Override
        protected void onPostExecute(Movies movies) {
            if (movies != null) {
                Log.d("TAG", "Page: " + movies.getPage());
                movies.getResults();
            } else {
                Log.d("TAG", "Some thing went wrong");
            }
        }
    }
}
