package com.levup.simpleplayer.views;

import com.levup.simpleplayer.models.Song;

import java.util.List;

/**
 * Created by java on 23.01.2017.
 */

public interface PlayListView {

    public void onPlayListLoaded(List<Song> songs);

}