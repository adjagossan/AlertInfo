package com.agar.tab.presenter;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.agar.tab.R;
import com.agar.tab.adapter.recyclerView.RssFeedAdapter;
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
    private List<Item> newItems = new ArrayList();

    public List<Item> getItems(){
        return this.items;
    }

    public void setItems(List<Item> items){
        this.items.clear();
        this.items.addAll(items);
    }

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

        subscription = getFeed(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(error -> Observable.empty())
                .subscribe
                        (
                            rss -> this.setItems(rss.getChannel().getItem()),
                            error -> error.getMessage(),
                            () -> update()
                        );
    }

    public void update(){
        if(fragment != null){
            View view = fragment.getView();
            ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            RssFeedAdapter adapter = (RssFeedAdapter) recyclerView.getAdapter();
            adapter.setItems(this.items);
            progressBar.setVisibility(View.GONE);
        }
    }

    public void refresh(String url){

        if(subscription != null)
            subscription.unsubscribe();

        subscription = getFeed(url)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(error -> Observable.empty())
                .subscribe
                        (
                                rss -> newItems = rss.getChannel().getItem(),
                                error -> error.getMessage(),
                                () -> filter()
                        );
    }

    private void filter(){
        List<Item> newValues = new ArrayList<>();

        if(!items.isEmpty()) {
            for (Item item : newItems) {
                if (item.equals(items.get(0))) {
                    //newValues.add(item);
                    break;
                } else
                    newValues.add(item);
            }
        }

        if(fragment != null){
            View view = fragment.getView();
            SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            RssFeedAdapter adapter = (RssFeedAdapter) recyclerView.getAdapter();
            adapter.addItems(newValues);
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}
