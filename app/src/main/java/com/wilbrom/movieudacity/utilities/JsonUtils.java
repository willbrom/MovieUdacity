package com.wilbrom.movieudacity.utilities;


import com.wilbrom.movieudacity.models.Genre;
import com.wilbrom.movieudacity.models.Movies;
import com.wilbrom.movieudacity.models.Results;
import com.wilbrom.movieudacity.models.Reviews;
import com.wilbrom.movieudacity.models.Videos;

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

    public static Reviews parseReviewJson(String rawJson) {
        Reviews reviews = null;

        try {
            reviews = new Reviews();
            JSONObject rootObject = new JSONObject(rawJson);
            reviews.setId(rootObject.getInt("id"));
            reviews.setPage(rootObject.getInt("page"));
            reviews.setTotalPages(rootObject.getInt("total_pages"));
            reviews.setTotalResults(rootObject.getInt("total_results"));

            JSONArray resultsArray = rootObject.getJSONArray("results");

            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject resultObj = resultsArray.getJSONObject(i);
                reviews.setReviewsResults(resultObj.getString("id"),
                        resultObj.getString("author"),
                        resultObj.getString("content"),
                        resultObj.getString("url"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reviews;
    }

    public static Videos parseVideoJson(String rawJson) {
        Videos videos = null;

        try {
            videos = new Videos();
            JSONObject rootObject = new JSONObject(rawJson);
            videos.setId(rootObject.getInt("id"));

            JSONArray resultsArray = rootObject.getJSONArray("results");

            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject resultObj = resultsArray.getJSONObject(i);
                videos.addVideoResultsListItem(resultObj.getString("id"),
                        resultObj.getString("iso_639_1"),
                        resultObj.getString("iso_3166_1"),
                        resultObj.getString("key"),
                        resultObj.getString("name"),
                        resultObj.getString("site"),
                        resultObj.getInt("size"),
                        resultObj.getString("type"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return videos;
    }

    public static ArrayList<Genre> parseGenreJson(String rawJson) {
        ArrayList<Genre> genreList = new ArrayList<>();

        try {
            JSONObject rootObject = new JSONObject(rawJson);
            JSONArray genreArray = rootObject.getJSONArray("genres");

            for (int i = 0; i < genreArray.length(); i++) {
                int id = genreArray.getJSONObject(i).getInt("id");
                String name = genreArray.getJSONObject(i).getString("name");
                genreList.add(new Genre(name, id));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return genreList;
    }
}
