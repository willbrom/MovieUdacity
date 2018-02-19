package com.wilbrom.movieudacity.utilities;


import com.wilbrom.movieudacity.models.Movies;
import com.wilbrom.movieudacity.models.Results;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Movies parseMovieJson(String rawJson) {
        Movies movies = null;
        Results results = null;
        List<Results> resultsList = new ArrayList<>();

        try {
            movies = new Movies();
            JSONObject rootObject = new JSONObject(rawJson);
            JSONArray resultsArray = rootObject.getJSONArray("results");

            movies.setPage(rootObject.getInt("page"));
            movies.setTotalResults(rootObject.getInt("total_results"));
            movies.setTotalPages(rootObject.getInt("total_pages"));

            int[] genreId = null;
            for (int i = 0; i < resultsArray.length(); i++) {
                results = new Results();
                JSONArray genreIds = resultsArray.getJSONObject(i).getJSONArray("genre_ids");
                genreId = new int[genreIds.length()];

                results.setVoteCount(resultsArray.getJSONObject(i).getInt("vote_count"));
                results.setId(resultsArray.getJSONObject(i).getInt("id"));
                results.setVideo(resultsArray.getJSONObject(i).getBoolean("video"));
                results.setVoteAverage(resultsArray.getJSONObject(i).getDouble("vote_average"));
                results.setTitle(resultsArray.getJSONObject(i).getString("title"));
                results.setPopularity(resultsArray.getJSONObject(i).getDouble("popularity"));
                results.setPosterPath(resultsArray.getJSONObject(i).getString("poster_path"));
                results.setOriginalLanguage(resultsArray.getJSONObject(i).getString("original_language"));
                results.setOriginalTitle(resultsArray.getJSONObject(i).getString("original_title"));
                results.setBackdropPath(resultsArray.getJSONObject(i).getString("backdrop_path"));
                results.setAdult(resultsArray.getJSONObject(i).getBoolean("adult"));
                results.setOverView(resultsArray.getJSONObject(i).getString("overview"));
                results.setReleaseDate(resultsArray.getJSONObject(i).getString("release_date"));

                for (int j = 0; j < genreIds.length(); j++) {
                    genreId[j] = genreIds.getInt(j);
                }
                results.setGenreIds(genreId);

                resultsList.add(results);
            }

            movies.setResults(resultsList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movies;
    }
}
