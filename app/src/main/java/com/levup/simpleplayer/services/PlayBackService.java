package com.levup.simpleplayer.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


import com.levup.simpleplayer.BuildConfig;
import com.levup.simpleplayer.views.MusicActivity;

public class PlayBackService extends Service implements MediaPlayer.OnPreparedListener, MusicActivity.PlayBackInteraction {

    public static final String ACTION_PLAY = BuildConfig.APPLICATION_ID + ".action.PLAY";

    private boolean isPaused = false;

    public static final String TAG = PlayBackService.class.getSimpleName();
    private static final int NOTIFICATION_ID = 101;

    private final IBinder mBinder = new PlayBackBinder();

    private MediaPlayer mMediaPlayer = null;

    public static Intent newInstance(Context context) {
        return new Intent(context, PlayBackService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()");
        Toast.makeText(this, "onCreate()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand(" + intent.getAction() + ")");
        if (intent.getAction() == null) return Service.START_STICKY;
        if (intent.getAction().equals(ACTION_PLAY)) {
            try {
                mMediaPlayer = new MediaPlayer();
                mMediaPlayer.setDataSource(this, getSongs());
                mMediaPlayer.setOnPreparedListener(this);
                // mMediaPlayer.prepareAsync();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return Service.START_STICKY;
    }

    private Uri getSongs() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor == null) {
            // query failed, handle error.
        } else if (!cursor.moveToFirst()) {
            // no media on the device
        } else {
            int titleColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
            do {
                long thisId = cursor.getLong(idColumn);
                String thisTitle = cursor.getString(titleColumn);
                Uri contentUri = ContentUris.withAppendedId(
                        android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        thisId);

                return contentUri;
                // ...process entry...
            } while (cursor.moveToNext());
        }
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Toast.makeText(this, "onDestroy()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onDestroy()");
    }

    /*@Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this, "onUnbind()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onUnbind()");
        return super.onUnbind(intent);
    }*/

    public PlayBackService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mMediaPlayer.start();

        PendingIntent pi = PendingIntent.getActivity(
                getApplicationContext(),
                0,
                new Intent(getApplicationContext(), MusicActivity.class),
                PendingIntent.FLAG_NO_CREATE);

//        NotificationCompat.Builder builder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setContentTitle("My notification")
//                        .setContentText("Hello World!")
//                        .setContentIntent(pi);

        //startForeground(NOTIFICATION_ID, builder.build());
    }

    public void playSongId(long songId) {
        // Song song = SongsRepository.getSongForID(this, songId);
        Uri contentUri = ContentUris.withAppendedId(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                songId);
        try {
            if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                mMediaPlayer.start();
                mMediaPlayer.release();
                mMediaPlayer = null;
            }
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(this, contentUri);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean play() {
        try {
            if (mMediaPlayer != null) {
                mMediaPlayer.start();
                isPaused = false;
                return true;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void play(long songId) {
        playSongId(songId);
    }

    @Override
    public void pause() {
        try {
            if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
                isPaused = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void stop() {
        try {
            if (mMediaPlayer != null && mMediaPlayer.isPlaying())
                mMediaPlayer.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isPaused() {
        return isPaused;
    }


    public class PlayBackBinder extends Binder {
        public PlayBackService getService() {
            return PlayBackService.this;
        }
    }
}