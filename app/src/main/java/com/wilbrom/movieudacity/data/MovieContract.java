package com.wilbrom.movieudacity.data;


import android.provider.BaseColumns;

public class MovieContract {

    private MovieContract() {}

    public static final class ResultsEntry implements BaseColumns {
        public static final String TABLE_NAME = "results";
        public static final String COLUMN_VOTE_COUNT = "voteCount";
        public static final String COLUMN_MOVIE_ID = "movieId";
        public static final String COLUMN_VIDEO = "video";
        public static final String COLUMN_VOTE_AVERAGE = "voteAverage";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_POSTER_PATH = "posterPath";
        public static final String COLUMN_ORIGINAL_LANGUAGE = "originalLanguage";
        public static final String COLUMN_ORIGINAL_TITLE = "originalTitle";
        public static final String COLUMN_GENRE_IDS = "genreIds";
        public static final String COLUMN_BACKDROP_PATH = "backdropPath";
        public static final String COLUMN_ADULT = "adult";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";
    }
}