package com.example.java.simpleplayer.views;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.java.simpleplayer.R;
import com.example.java.simpleplayer.model.Song;
import com.example.java.simpleplayer.presenters.SongsPresenter;
import com.example.java.simpleplayer.services.PlayBackService;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SongsView {

    public static final String TAG = MainActivity.class.getSimpleName();
    private static final int SPAN_COUNT = 2;

    private SongsPresenter mPresenter = new SongsPresenter();

    private PlayBackService mService;
    private boolean mBound = false;

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder iBinder) {
            PlayBackService.PlayBackBinder binder
                    = (PlayBackService.PlayBackBinder) iBinder;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mPresenter.onAttachToView(this);
        mPresenter.loadAllSongs();

        Intent playBackIntent = PlayBackService.newInstance(this);
        playBackIntent.setAction(PlayBackService.ACTION_PLAY);
        startService(playBackIntent);



    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent playBackIntent = PlayBackService.newInstance(this);
        bindService(playBackIntent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onDetach();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onAllSongsLoaded(List<Song> songList) {
        final RecyclerView.LayoutManager manager = new GridLayoutManager(
                this,
                SPAN_COUNT);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        final SongsAdapter adapter = new SongsAdapter();
        adapter.setDataSource(songList);
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(item -> {
            final SongsAdapter.SongViewHolder holder =
                    (SongsAdapter.SongViewHolder) mRecyclerView.findContainingViewHolder(item);
            if(holder == null) return;
            final Song song = holder.getSong();
            final long songId = song.id;
            if(mBound) {
                mService.playSongId(songId);
            }
        });
    }
}
