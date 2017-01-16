package com.levup.simpleplayer.views;

import android.content.Context;

import com.levup.simpleplayer.models.Song;

import java.util.List;

public interface SongsView {

    public Context getContext();

    public void onAllSongsLoaded(List<Song> songList);

}
