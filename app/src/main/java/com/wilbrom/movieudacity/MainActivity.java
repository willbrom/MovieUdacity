package com.wilbrom.movieudacity;


import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.wilbrom.movieudacity.adapters.MovieListAdapter;
import com.wilbrom.movieudacity.interfaces.MovieItemInteractionListener;
import com.wilbrom.movieudacity.models.Genre;
import com.wilbrom.movieudacity.models.Movies;
import com.wilbrom.movieudacity.models.Results;
import com.wilbrom.movieudacity.utilities.JsonUtils;
import com.wilbrom.movieudacity.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieItemInteractionListener,
        SharedPreferences.OnSharedPreferenceChangeListener,
        LoaderManager.LoaderCallbacks<Movies> {

    public static final String CLASS_NAME = MainActivity.class.getSimpleName();
    private static final int LOADER_ID = 11;

    private View container;
    private RecyclerView movieList;
    private ProgressBar progress;
    private MovieListAdapter adapter;
    private String sortBy;

    public static ArrayList<Genre> genreList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = (FrameLayout) findViewById(R.id.container);
        movieList = (RecyclerView) findViewById(R.id.movie_list);
        progress = (ProgressBar) findViewById(R.id.progress);

        movieList.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));

        adapter = new MovieListAdapter(this);
        movieList.setAdapter(adapter);

        setUpSharedPreferences();
        startAsync(sortBy, false);

        if (savedInstanceState == null)
            new GetGenreAsyncTask().execute();
        else {
            genreList = savedInstanceState.getParcelableArrayList("genre-list");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("genre-list", genreList);
        super.onSaveInstanceState(outState);
    }

    private void setUpSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sortBy = sharedPreferences.getString(getString(R.string.preference_sort_list_key), getString(R.string.preference_sort_list_default));
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    private void startAsync(String sortBy, boolean prefChange) {
        URL url = null;

        switch (sortBy) {
            case NetworkUtils.POPULAR:
                url = NetworkUtils.getUrl(NetworkUtils.POPULAR, this);
                break;
            case NetworkUtils.TOP_RATED:
                url = NetworkUtils.getUrl(NetworkUtils.TOP_RATED, this);
                break;
        }

        Bundle bundle = new Bundle();
        bundle.putString(Intent.EXTRA_TEXT, url.toString());

        LoaderManager manager = getLoaderManager();
        Loader<Movies> loader = manager.getLoader(LOADER_ID);

        if (loader != null && !prefChange)
            manager.initLoader(LOADER_ID, bundle, this);
        else
            manager.restartLoader(LOADER_ID, bundle, this);
    }

    @Override
    public void onClickMovieItem(Results results) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, results);
        intent.putExtra(getString(R.string.class_name_extra), CLASS_NAME);
        startActivity(intent);
    }

    @Override
    public void onClickFavoriteItem(int position) {}

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
            case R.id.action_favorite:
                startActivity(new Intent(this, FavoriteActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.preference_sort_list_key))) {
            sortBy = sharedPreferences.getString(key, getString(R.string.preference_sort_list_default));
            startAsync(sortBy, true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public Loader<Movies> onCreateLoader(int i, final Bundle bundle) {
        return new AsyncTaskLoader<Movies>(this) {
            private Movies data;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();

                if (data != null)
                    deliverResult(data);
                else {
                    progress.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }

            @Override
            public Movies loadInBackground() {
                String url = bundle.getString(Intent.EXTRA_TEXT);
                Movies movies = null;

                try {
                    if (url != null) {
                        String response = NetworkUtils.getHttpResponse(new URL(url));
                        movies = JsonUtils.parseMovieJson(response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return movies;
            }

            @Override
            public void deliverResult(Movies data) {
                this.data = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Movies> loader, Movies movies) {
        progress.setVisibility(View.INVISIBLE);
        if (movies != null) {
            adapter.setResultsList(movies.getResults());
        } else {
            Snackbar.make(container, getString(R.string.error_occurred), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<Movies> loader) {}

    public class GetGenreAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            URL url = NetworkUtils.getUrl("genre", MainActivity.this);
            String data = "";

            try {
                data = NetworkUtils.getHttpResponse(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            if (!s.equals("")) {
                genreList = JsonUtils.parseGenreJson(s);
            }
        }
    }
}
