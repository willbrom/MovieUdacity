package com.wilbrom.movieudacity.utilities;


import android.net.Uri;
import android.net.Uri.Builder;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    public static final String BASE_MOVIE_URL = "http://api.themoviedb.org/3/movie/";
    public static final String BASE_IMAGE_URL_w185 = "http://image.tmdb.org/t/p/w185";
    public static final String KEY = "d89d8544c746180e24453e6f99a145da";
    public static final String POPULAR = "popular";
    public static final String TOP_RATED = "top_rated";

    public static URL getUrl(String sortBy) {
        String fullPath = "";

        switch (sortBy) {
            case POPULAR:
                fullPath = BASE_MOVIE_URL + sortBy;
                break;
            case TOP_RATED:
                fullPath = BASE_MOVIE_URL + sortBy;
        }

        Uri uri = Uri.parse(fullPath).buildUpon()
                .appendQueryParameter("api_key", KEY)
                .build();

        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getHttpResponse(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            InputStream inputStream = connection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            if (scanner.hasNext()) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            connection.disconnect();
        }
    }

}
