package com.example.java.simpleplayer.views;

import android.content.Context;

import com.example.java.simpleplayer.model.Song;

import java.util.List;

public interface SongsView {

    public Context getContext();

    public void onAllSongsLoaded(List<Song> songList);

}
