package com.wilbrom.movieudacity.adapters;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wilbrom.movieudacity.R;
import com.wilbrom.movieudacity.models.Results;
import com.wilbrom.movieudacity.models.Reviews;
import com.wilbrom.movieudacity.models.Videos;
import com.wilbrom.movieudacity.utilities.NetworkUtils;

import java.util.List;

public class DetailListAdapter extends RecyclerView.Adapter<DetailListAdapter.DetailItemViewHolder> {

    private static final int VIEW_TYPE_DETAIL = 11;
    private static final int VIEW_TYPE_REVIEW = 22;
    private static final int VIEW_TYPE_VIDEO = 33;

    private List<Object> detailResults;
    private Context context;

    public DetailListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public DetailItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_DETAIL) {
            View view = layoutInflater.inflate(R.layout.movie_detail_layout, parent, false);
            return new DetailItemViewHolder(view);
        } else if (viewType == VIEW_TYPE_REVIEW) {
            View view = layoutInflater.inflate(R.layout.review_item_layout, parent, false);
            return new DetailItemViewHolder(view);
        } else {
            View view = layoutInflater.inflate(R.layout.video_layout, parent, false);
            return new DetailItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(DetailItemViewHolder holder, int position) {
        Context context = holder.itemView.getContext();

        if (position == 0) {
            Results results = (Results) detailResults.get(0);
            holder.title.setText(results.getTitle());
            holder.overview.setText(results.getOverView());
            holder.releaseDate.setText(results.getReleaseDate());
            holder.voteAverage.setText(String.valueOf(results.getVoteAverage()));

            Picasso.with(context)
                    .load(NetworkUtils.getImageUrl(NetworkUtils.IMAGE_SIZE_w185) + results.getPosterPath())
                    .placeholder(R.drawable.placeholder_100x150)
                    .into(holder.poster);

        } else if (detailResults.get(position) instanceof Reviews.ReviewResults ) {
            Reviews.ReviewResults reviewResults = (Reviews.ReviewResults) detailResults.get(position);
            holder.author.setText(reviewResults.getAuthor());
            holder.content.setText(reviewResults.getContent());
        } else if (detailResults.get(position) instanceof Videos ) {
            Videos videos = (Videos) detailResults.get(position);
            holder.videoRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            holder.videoRecyclerView.setAdapter(holder.adapter);
            holder.adapter.setVideoResults(videos.getVideoResultsList());
        }
    }

    @Override
    public int getItemCount() {
        if (detailResults == null) return 0;
        return detailResults.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return VIEW_TYPE_DETAIL;
        else if (detailResults.get(position) instanceof Videos)
            return VIEW_TYPE_VIDEO;
        else
            return VIEW_TYPE_REVIEW;
    }

    public void setDetailResults(List<Object> detailResults) {
        this.detailResults = detailResults;
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

        private RecyclerView videoRecyclerView;

        private VideoListAdapter adapter;

        public DetailItemViewHolder(View itemView) {
            super(itemView);
            int id = itemView.getId();
            if (id == R.id.detail_parent) {
                title = (TextView) itemView.findViewById(R.id.title);
                overview = (TextView) itemView.findViewById(R.id.overview);
                releaseDate = (TextView) itemView.findViewById(R.id.release_date);
                voteAverage = (TextView) itemView.findViewById(R.id.vote_average);
                poster = (ImageView) itemView.findViewById(R.id.poster);
            } else if (id == R.id.review_parent) {
                author = (TextView) itemView.findViewById(R.id.review_author);
                content = (TextView) itemView.findViewById(R.id.review_content);
            } else {
                videoRecyclerView = (RecyclerView) itemView.findViewById(R.id.video_recycler_view);
                adapter = new VideoListAdapter(context);
            }
        }
    }
}
