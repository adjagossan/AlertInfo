package com.agar.tab.presenter;

import com.agar.tab.model.Rss;

import org.junit.Test;

import rx.observers.TestSubscriber;

/**
 * Created by Gossan on 24/09/2016.
 */
public class PageFragmentPresenterTest {

    @Test
    public void loadFeed(){
        String url = "http://www.france24.com/fr/actualites/rss";
        TestSubscriber<Rss> testSubscriber = new TestSubscriber<>();
        PageFragmentPresenter.getFeed(url)
                .subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);
    }
}
