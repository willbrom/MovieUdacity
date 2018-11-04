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
import com.wilbrom.movieudacity.models.Videos;
import com.wilbrom.movieudacity.utilities.NetworkUtils;

import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoItemViewHolder> {

    public interface OnClickTrailerListener {
        void onClickTrailer(String trailerKey);
    }

    private OnClickTrailerListener mListener;
    private List<Videos.VideoResults> videoResults;
    private Context context;

    public VideoListAdapter(Context context) {
        this.context = context;
        mListener = (OnClickTrailerListener) context;
    }

    @Override
    public VideoItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.video_item_layout, parent, false);
        return new VideoItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoItemViewHolder holder, int position) {
        holder.videoItem.setText(videoResults.get(position).getName());
        Picasso.with(context)
                .load(NetworkUtils.getVideoThubnailUrl(videoResults.get(position).getKey(), "mqdefault"))
                .placeholder(context.getResources().getDrawable(R.drawable.placeholder_thumbnail_mq))
                .into(holder.videoTrailer);
    }

    @Override
    public int getItemCount() {
        if (videoResults == null) return 0;
        return videoResults.size();
    }

    public void setVideoResults(List<Videos.VideoResults> videoResults) {
        this.videoResults = videoResults;
        notifyDataSetChanged();
    }


    public class VideoItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView videoItem;
        private ImageView videoTrailer;

        public VideoItemViewHolder(View itemView) {
            super(itemView);

            videoItem = (TextView) itemView.findViewById(R.id.video_text);
            videoTrailer = (ImageView) itemView.findViewById(R.id.video_trailer);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClickTrailer(videoResults.get(getAdapterPosition()).getKey());
        }
    }
}
