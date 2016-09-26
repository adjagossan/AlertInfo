package com.agar.tab.presenter;

/**
 * Created by Gossan on 22/09/2016.
 */
public interface Presenter<V> {
    void attachView(V view);
    void detachView(V view);
}
