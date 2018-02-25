package com.wilbrom.movieudacity.interfaces;


import com.wilbrom.movieudacity.models.Results;

public interface MovieItemInteractionListener {
    void onClickMovieItem(Results results);
    void onClickFavoriteItem(int position);
}
