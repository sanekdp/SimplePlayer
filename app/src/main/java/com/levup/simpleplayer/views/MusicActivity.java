package com.levup.simpleplayer.views;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Bundle;
import com.levup.simpleplayer.services.PlayBackService;
import com.levup.simpleplayer.views.base.BaseActivity;


public class MusicActivity extends BaseActivity {

    private PlayBackService mService;
    private boolean mBound = false;

    public PlayBackInteraction getPlayBackInteraction() {
        return mService;
    }

    private PlayBackInteraction mPlayBackInteraction = null;



    public interface PlayBackInteraction {
        boolean play();

        void play(long songId);

        void pause();

        void stop();

        boolean isPaused();
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