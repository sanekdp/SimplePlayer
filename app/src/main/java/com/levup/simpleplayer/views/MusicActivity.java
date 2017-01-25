package com.levup.simpleplayer.views;

import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import com.levup.simpleplayer.R;
import com.levup.simpleplayer.models.Song;
import com.levup.simpleplayer.presenters.SongsPresenter;
import com.levup.simpleplayer.services.PlayBackService;
import com.levup.simpleplayer.views.base.BaseActivity;

import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

public class MusicActivity extends BaseActivity {

    public interface PlayBackInteraction {

        boolean play();

        void pause();

        void play(long songId);

        boolean isPaused();

        Observable<Integer> gerDurationObservable();

        void onUserSeek(int progress);



    }

    private PlayBackService mService;

    private boolean mBound = false;

    @Nullable
    public PlayBackInteraction getPlayBackInteraction() {
        return mService;
    }

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent playBackIntent = PlayBackService.newInstance(this);
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
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }
}