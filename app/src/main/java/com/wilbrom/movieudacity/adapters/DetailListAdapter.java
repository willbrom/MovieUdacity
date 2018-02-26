package com.wilbrom.movieudacity.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wilbrom.movieudacity.R;
import com.wilbrom.movieudacity.models.Results;
import com.wilbrom.movieudacity.models.Reviews;
import com.wilbrom.movieudacity.utilities.NetworkUtils;

import java.util.List;

public class DetailListAdapter extends RecyclerView.Adapter<DetailListAdapter.DetailItemViewHolder> {

    private static final int VIEW_TYPE_DETAIL = 11;
    private static final int VIEW_TYPE_REVIEW = 22;

    private Results results;
    private List<Reviews.ReviewResults> reviewResults;

    @Override
    public DetailItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_DETAIL) {
            View view = layoutInflater.inflate(R.layout.movie_detail_layout, parent, false);
            return new DetailItemViewHolder(view);
        } else {
            View view = layoutInflater.inflate(R.layout.review_item_layout, parent, false);
            return new DetailItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(DetailItemViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        if (position == 0) {

            holder.title.setText(results.getTitle());
            holder.overview.setText(results.getOverView());
            holder.releaseDate.setText(results.getReleaseDate());
            holder.voteAverage.setText(String.valueOf(results.getVoteAverage()));

            Picasso.with(context)
                    .load(NetworkUtils.getImageUrl(NetworkUtils.IMAGE_SIZE_w185) + results.getPosterPath())
                    .placeholder(R.drawable.placeholder_100x150)
                    .into(holder.poster);

        } else {
            holder.author.setText(reviewResults.get(position).getAuthor());
            holder.content.setText(reviewResults.get(position).getContent());
        }

    }

    @Override
    public int getItemCount() {
//        if (reviewResults != null)
//            return ;
        return reviewResults.size() + 1;
    }

    public void setResults(Results results , List<Reviews.ReviewResults> reviewResults) {
        this.results = results;
        this.reviewResults = reviewResults;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_DETAIL;
        } else
            return VIEW_TYPE_REVIEW;
    }

    public void setReviewResults() {
        notifyDataSetChanged();
    }

    public class DetailItemViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView overview;
        private TextView releaseDate;
        private TextView voteAverage;
        private ImageView poster;

        private TextView author;
        private TextView content;

        public DetailItemViewHolder(View itemView) {
            super(itemView);
            int id = itemView.getId();
            if (id == R.id.detail_parent) {
                title = (TextView) itemView.findViewById(R.id.title);
                overview = (TextView) itemView.findViewById(R.id.overview);
                releaseDate = (TextView) itemView.findViewById(R.id.release_date);
                voteAverage = (TextView) itemView.findViewById(R.id.vote_average);
                poster = (ImageView) itemView.findViewById(R.id.poster);
            } else {
                author = (TextView) itemView.findViewById(R.id.review_author);
                content = (TextView) itemView.findViewById(R.id.review_content);
            }
        }
    }
}
