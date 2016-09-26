package com.agar.tab.presenter;

import android.util.Log;

import com.agar.tab.data.DataHelper;
import com.agar.tab.model.Item;
import com.agar.tab.model.Rss;
import com.agar.tab.view.fragment.PageFragment;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Gossan on 22/09/2016.
 */
public class PageFragmentPresenter implements Presenter<PageFragment> {

    private PageFragment fragment;
    private Subscription subscription;
    private List<Item> items = new ArrayList();

    @Override
    public void attachView(PageFragment view) {
        this.fragment = view;
    }

    @Override
    public void detachView(PageFragment view) {
        this.fragment = null;
        if(subscription != null)
            subscription.unsubscribe();
    }

    public static Observable<Rss> getFeed(String url){
        return Observable.create(subscriber -> {
            subscriber.onNext(DataHelper.getRssFeed(url));
            subscriber.onCompleted();
        });
    }

    public void loadData(String url){
        if(subscription != null)
            subscription.unsubscribe();

        /*Observable<Rss> observable = Observable.create(subscriber -> {
            subscriber.onNext(DataHelper.getRssFeed(url));
            subscriber.onCompleted();
        });*/

        subscription = /*observable*/getFeed(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(error -> Observable.empty())
                .subscribe(
                        rss -> items.addAll(rss.getChannel().getItem()),
                        error -> error.getMessage(),
                        () -> {Log.i("rxjava", Integer.toString(items.size()));fragment.setItem(items);}
                        );
    }
}
