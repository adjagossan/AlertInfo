package com.agar.tab.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.agar.tab.R;
import com.agar.tab.adapter.recyclerView.RssFeedAdapter;
import com.agar.tab.presenter.PageFragmentPresenter;
import com.agar.tab.utils.Util;

/**
 * Created by Gossan on 13/09/2016.
 */
public class PageFragment extends Fragment {
    private static final String ARG_PAGE = "ARG_PAGE";
    private String mPage;
    private PageFragmentPresenter presenter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static Fragment newInstance(String pageName){
        Bundle args = new Bundle();
        args.putString(ARG_PAGE, pageName);
        PageFragment mFragment = new PageFragment();
        mFragment.setArguments(args);
        return mFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(presenter == null)
            presenter = new PageFragmentPresenter();
        presenter.attachView(this);

        mPage = getArguments().getString(ARG_PAGE);
        if(/*Util.isConnected() &&*/ presenter.getItems().isEmpty())
            presenter.loadData(Util.map.get(mPage));
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        if(Util.isConnected() && presenter.getItems().isEmpty())
            progressBar.setVisibility(View.VISIBLE);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RssFeedAdapter rssFeedAdapter = new RssFeedAdapter(getContext());
        recyclerView.setAdapter(rssFeedAdapter);
        if(!presenter.getItems().isEmpty())
            presenter.update();

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mSwipeRefreshLayout.setOnRefreshListener(()-> onRefresh());
    }

    private void onRefresh(){
        if(Util.isConnected())
            presenter.refresh(Util.map.get(mPage));
        else
            mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        presenter.detachView(this);
        super.onDestroy();
    }

    public PageFragmentPresenter getPresenter() {
        return presenter;
    }
}
