package com.wilbrom.movieudacity.utilities;


import android.content.Context;
import android.net.Uri;

import com.wilbrom.movieudacity.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    public static final String BASE_MOVIE_URL = "http://api.themoviedb.org/3/movie/";
    public static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";

    public static final String POPULAR = "popular";
    public static final String TOP_RATED = "top_rated";

    public static final String IMAGE_SIZE_w92 = "w92";
    public static final String IMAGE_SIZE_w154 = "w154";
    public static final String IMAGE_SIZE_w185 = "w185";
    public static final String IMAGE_SIZE_w342 = "w342";
    public static final String IMAGE_SIZE_w780 = "w780";

    public static URL getUrl(String sortBy, Context context) {
        String key = context.getString(R.string.movie_api_key);
        String fullPath = "";

        switch (sortBy) {
            case POPULAR:
                fullPath = BASE_MOVIE_URL + POPULAR;
                break;
            case TOP_RATED:
                fullPath = BASE_MOVIE_URL + TOP_RATED;
        }

        Uri uri = Uri.parse(fullPath).buildUpon()
                .appendQueryParameter("api_key", key)
                .build();

        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getImageUrl(String size) {
        switch (size) {
            case IMAGE_SIZE_w92:
                return BASE_IMAGE_URL + IMAGE_SIZE_w92;
            case IMAGE_SIZE_w154:
                return BASE_IMAGE_URL + IMAGE_SIZE_w154;
            case IMAGE_SIZE_w185:
                return BASE_IMAGE_URL + IMAGE_SIZE_w185;
            case IMAGE_SIZE_w342:
                return BASE_IMAGE_URL + IMAGE_SIZE_w342;
            case IMAGE_SIZE_w780:
                return BASE_IMAGE_URL + IMAGE_SIZE_w780;
            default:
                return BASE_IMAGE_URL + IMAGE_SIZE_w185;
        }
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
