package com.levup.simpleplayer.presenters;


import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.levup.simpleplayer.model.Song;
import com.levup.simpleplayer.views.SongsView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SongsPresenter {

    private SongsView mView = null;

    public void onAttachToView(@NonNull SongsView songsView) {
        mView = songsView;
    }

    private Subscription subscription = null;

    public void loadAllSongs() {
        subscription = Observable.just(SongsRepository.getAllSongs(mView.getContext()))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(songs -> { mView.onAllSongsLoaded(songs);},
                        Throwable::printStackTrace);

    }

    public void onDetach() {
        if(subscription != null)
            subscription.unsubscribe();
    }

}