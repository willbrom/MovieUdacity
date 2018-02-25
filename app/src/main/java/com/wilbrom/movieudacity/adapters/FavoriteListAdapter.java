package com.wilbrom.movieudacity.adapters;


import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wilbrom.movieudacity.R;
import com.wilbrom.movieudacity.data.MovieContract;
import com.wilbrom.movieudacity.utilities.NetworkUtils;


public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListAdapter.FavoriteItemViewHolder> {

    private Cursor mCursor;

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
        mCursor.moveToPosition(position);

        String title = mCursor.getString(mCursor.getColumnIndex(MovieContract.ResultsEntry.COLUMN_TITLE));
        String releaseDate = mCursor.getString(mCursor.getColumnIndex(MovieContract.ResultsEntry.COLUMN_RELEASE_DATE));
        double vote = mCursor.getDouble(mCursor.getColumnIndex(MovieContract.ResultsEntry.COLUMN_VOTE_AVERAGE));
        String posterPath = mCursor.getString(mCursor.getColumnIndex(MovieContract.ResultsEntry.COLUMN_POSTER_PATH));

        holder.favoriteTitle.setText(title);
        holder.favoriteDate.setText(releaseDate);
        holder.favoriteVote.setText(String.valueOf(vote));

        Picasso.with(context)
                .load(NetworkUtils.getImageUrl(NetworkUtils.IMAGE_SIZE_w154) + posterPath)
                .placeholder(R.drawable.placeholder_62x90)
                .error(R.drawable.placeholder_62x90)
                .into(holder.favoritePoster);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) return 0;
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
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
