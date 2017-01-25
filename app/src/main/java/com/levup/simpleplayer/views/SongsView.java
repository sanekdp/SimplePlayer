package com.levup.simpleplayer.views;

import android.content.Context;

import com.levup.simpleplayer.models.Song;

import java.util.List;

/**
 * Created by java on 05.12.2016.
 */

public interface SongsView {

    Context getContext();

    void onAllSongsLoaded(List<Song> songList);

}