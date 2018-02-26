package com.wilbrom.movieudacity.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wilbrom.movieudacity.R;
import com.wilbrom.movieudacity.models.Reviews;

import java.util.List;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ReviewItemViewHolder> {

    private List<Reviews.ReviewResults> reviewResults;

    @Override
    public ReviewItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.review_item_layout, parent, false);
        return new ReviewItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewItemViewHolder holder, int position) {
        holder.author.setText(reviewResults.get(position).getAuthor());
        holder.content.setText(reviewResults.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        if (reviewResults == null) return 0;
        return reviewResults.size();
    }

    public void setReviewResults(List<Reviews.ReviewResults> reviewResults) {
        this.reviewResults = reviewResults;
    }

    public class ReviewItemViewHolder extends RecyclerView.ViewHolder {

        private TextView author;
        private TextView content;

        public ReviewItemViewHolder(View itemView) {
            super(itemView);

            author = (TextView) itemView.findViewById(R.id.review_author);
            content = (TextView) itemView.findViewById(R.id.review_content);
        }
    }
}
