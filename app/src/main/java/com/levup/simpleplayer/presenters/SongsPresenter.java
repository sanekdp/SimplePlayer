package com.levup.simpleplayer.presenters;

import android.support.annotation.NonNull;

import com.levup.simpleplayer.repositories.SongsRepository;
import com.levup.simpleplayer.views.SongsView;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by java on 05.12.2016.
 */

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