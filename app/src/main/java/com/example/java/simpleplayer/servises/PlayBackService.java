package com.example.java.simpleplayer.servises;

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
import android.provider.MediaStore;
import android.support.compat.BuildConfig;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;


public class PlayBackService extends Service implements MediaPlayer.OnPreparedListener {

    public static final String ACTION_PLAY = BuildConfig.APPLICATION_ID + ".action.PLAY";

    public static final String TAG = PlayBackService.class.getSimpleName();

    private final IBinder mBinder = new PlayBackBinder();

    private MediaPlayer mMideaPlayer = null;

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

        if (intent.getAction().equals(ACTION_PLAY)){
            mMideaPlayer = new MediaPlayer();
            try {
                mMideaPlayer.setDataSource(this, getSong());
            } catch (IOException e) {
                e.printStackTrace();
            }
            mMideaPlayer.setOnPreparedListener(this);
            mMideaPlayer.prepareAsync();
        }

        return Service.START_STICKY;
    }

    private Uri getSong(){
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
                Uri contentUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, thisId);

                return contentUri;
            } while (cursor.moveToNext());
        }
        return null;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

        mediaPlayer.start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onDestroy()");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this, "onUnbind()", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onUnbind()");
        return super.onUnbind(intent);
    }

    public PlayBackService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class PlayBackBinder extends Binder {
        public PlayBackService getService() {
            return PlayBackService.this;
        }
    }
}
