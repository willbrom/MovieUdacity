package com.wilbrom.movieudacity.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wilbrom.movieudacity.R;
import com.wilbrom.movieudacity.models.Videos;

import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoItemViewHolder> {

    private List<Videos.VideoResults> videoResults;

    @Override
    public VideoItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.video_item_layout, parent, false);
        return new VideoItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoItemViewHolder holder, int position) {
        holder.videoItem.setText(videoResults.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (videoResults == null) return 0;
        return videoResults.size();
    }

    public void setVideoResults(List<Videos.VideoResults> videoResults) {
        this.videoResults = videoResults;
    }


    public class VideoItemViewHolder extends RecyclerView.ViewHolder {

        private TextView videoItem;

        public VideoItemViewHolder(View itemView) {
            super(itemView);

            videoItem = (TextView) itemView.findViewById(R.id.video_item);
        }
    }
}
