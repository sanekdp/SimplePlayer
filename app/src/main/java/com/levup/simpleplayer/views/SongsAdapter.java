package com.levup.simpleplayer.views;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.levup.simpleplayer.R;
import com.levup.simpleplayer.models.Song;
import com.levup.simpleplayer.presenters.SongsRepository;

import java.util.List;

/**
 * Created by java on 07.12.2016.
 */

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongViewHolder> {

    private List<Song> mDataSource = null;

    private View.OnClickListener mOnItemClickListener;

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        mOnItemClickListener = onClickListener;
    }

    public void setDataSource(List<Song> dataSource) {
        Log.d("TAG", "ffffffffffffffffffff" + dataSource.size());
        mDataSource = dataSource;
        notifyDataSetChanged();
    }

    public List<Song> getDataSource() {
        return mDataSource;
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.item_main_song, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        final Song song = mDataSource.get(position);
        holder.bind(song);
        holder.itemView.setOnClickListener(mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mDataSource == null ? 0 : mDataSource.size();
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {

        private Song mSong;

        private ImageView mCoverImageView;
        private TextView mArtistTextView;
        private TextView mTitleTextView;

        public SongViewHolder(View itemView) {
            super(itemView);
            mCoverImageView = (ImageView) itemView.findViewById(R.id.coverImageView);
            mArtistTextView = (TextView) itemView.findViewById(R.id.artistTextView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
        }

        private void bind(@NonNull Song song) {
            mSong = song;
            mArtistTextView.setText(song.artistName);
            mTitleTextView.setText(song.title);
            String cover = SongsRepository.getAlbumCover(
                    itemView.getContext(),
                    song.albumId);
            Glide
                    .with(itemView.getContext())
                    .load(cover)
                    .centerCrop()
                    .placeholder(new ColorDrawable(Color.GRAY))
                    .crossFade()
                    .into(mCoverImageView);
        }

        public Song getSong() {
            return mSong;
        }
    }
}