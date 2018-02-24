package com.wilbrom.movieudacity.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wilbrom.movieudacity.R;
import com.wilbrom.movieudacity.models.Results;
import com.wilbrom.movieudacity.utilities.NetworkUtils;

import java.util.List;

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListAdapter.FavoriteItemViewHolder> {

    private List<Results> movieResults;

    public FavoriteListAdapter(Context context) {

    }

    @Override
    public FavoriteItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.favorite_item_layout, parent, false);
        return new FavoriteItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteItemViewHolder holder, int position) {
        Context context = holder.itemView.getContext();

        holder.favoriteTitle.setText(movieResults.get(position).getTitle());
        holder.favoriteDate.setText(movieResults.get(position).getReleaseDate());
        holder.favoriteVote.setText(String.valueOf(movieResults.get(position).getVoteAverage()));

        Picasso.with(context)
                .load(NetworkUtils.getImageUrl(NetworkUtils.IMAGE_SIZE_w92) + movieResults.get(position).getPosterPath())
                .placeholder(R.drawable.placeholder_62x90)
                .error(R.drawable.placeholder_62x90)
                .into(holder.favoritePoster);
    }

    @Override
    public int getItemCount() {
        if (movieResults == null) return 0;
        return movieResults.size();
    }

    public void setMovieResults(List<Results> movieResults) {
        this.movieResults = movieResults;
    }

    public class FavoriteItemViewHolder extends RecyclerView.ViewHolder{

        private TextView favoriteTitle;
        private TextView favoriteDate;
        private TextView favoriteVote;
        private ImageView favoritePoster;

        public FavoriteItemViewHolder(View itemView) {
            super(itemView);
            favoriteTitle = (TextView) itemView.findViewById(R.id.favorite_title);
            favoriteDate = (TextView) itemView.findViewById(R.id.favorite_date);
            favoriteVote = (TextView) itemView.findViewById(R.id.favorite_vote);
            favoritePoster = (ImageView) itemView.findViewById(R.id.favorite_poster);
        }
    }
}
