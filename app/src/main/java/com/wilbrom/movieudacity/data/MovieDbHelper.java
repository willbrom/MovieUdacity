package com.wilbrom.movieudacity.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.wilbrom.movieudacity.data.MovieContract.*;

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movieDatabase.db";
    private static final int DATABASE_VERSION = 1;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + ResultsEntry.TABLE_NAME + " (" +
                ResultsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ResultsEntry.COLUMN_VOTE_COUNT + " INTEGER NOT NULL, " +
                ResultsEntry.COLUMN_VIDEO + " INTEGER NOT NULL, " +
                ResultsEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, " +
                ResultsEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                ResultsEntry.COLUMN_POPULARITY + " REAL NOT NULL, " +
                ResultsEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                ResultsEntry.COLUMN_ORIGINAL_LANGUAGE + " TEXT NOT NULL, " +
                ResultsEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, " +
                ResultsEntry.COLUMN_GENRE_IDS + " TEXT NOT NULL, " +
                ResultsEntry.COLUMN_BACKDROP_PATH + " TEXT NOT NULL, " +
                ResultsEntry.COLUMN_ADULT + " INTEGER NOT NULL, " +
                ResultsEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                ResultsEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                "UNIQUE (" + ResultsEntry.COLUMN_MOVIE_ID + ") ON CONFLICT REPLACE); ";
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ResultsEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}