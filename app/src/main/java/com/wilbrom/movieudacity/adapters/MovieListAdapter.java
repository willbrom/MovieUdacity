package com.wilbrom.movieudacity.adapters;


import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wilbrom.movieudacity.R;
import com.wilbrom.movieudacity.models.Results;
import com.wilbrom.movieudacity.utilities.NetworkUtils;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieItemViewHolder> {

    private List<Results> resultsList;
    private MovieItemInteractionListener mListener;

    public interface MovieItemInteractionListener {
        void onClickMovieItem(Results results);
    }

    public MovieListAdapter(Context context) {
        mListener = (MovieItemInteractionListener) context;
    }

    @Override
    public MovieItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.movie_item_layout, parent, false);
        return new MovieItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieItemViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        String imageSize;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            imageSize = NetworkUtils.IMAGE_SIZE_w185;
        else
            imageSize = NetworkUtils.IMAGE_SIZE_w342;

        Picasso.with(context)
                .load(NetworkUtils.getImageUrl(imageSize) + resultsList.get(position).getPosterPath())
                .into(holder.moviePoster);
    }

    @Override
    public int getItemCount() {
        if (resultsList == null) return 0;
        return resultsList.size();
    }

    public void setResultsList(List<Results> resultsList) {
        this.resultsList = resultsList;
        notifyDataSetChanged();
    }

    public class MovieItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView moviePoster;

        public MovieItemViewHolder(View itemView) {
            super(itemView);
            moviePoster = (ImageView) itemView.findViewById(R.id.movie_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClickMovieItem(resultsList.get(getAdapterPosition()));
        }
    }
}
