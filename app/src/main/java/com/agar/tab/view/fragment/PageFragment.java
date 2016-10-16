package com.agar.tab.view.fragment;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private boolean isConnected = false;

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
        presenter = new PageFragmentPresenter();
        presenter.attachView(this);
        mPage = getArguments().getString(ARG_PAGE);
        if(Util.isConnected())
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
        if(Util.isConnected())
            progressBar.setVisibility(View.VISIBLE);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RssFeedAdapter rssFeedAdapter = new RssFeedAdapter(getContext());
        recyclerView.setAdapter(rssFeedAdapter);
        if(!presenter.getItems().isEmpty())
            presenter.update();
        if(view != null)
            showSnackBar(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //showSnackBar(getView());
    }

    private void showSnackBar(View view){
        if(!Util.isConnected()){

            Snackbar.make(view, this.getArguments().getString(ARG_PAGE)/*mPageR.string.snackBarText*/, Snackbar.LENGTH_LONG)
                    .setAction(R.string.snackBarAction,
                            v -> {
                                if(Util.isOnline()) {
                                    view.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                                    presenter.loadData(Util.map.get(mPage));
                                }else
                                    view.findViewById(R.id.progressBar).setVisibility(View.GONE);
                                    showSnackBar(view);
                            })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_dark))
                    .show();
        }
    }

    @Override
    public void onDestroy() {
        presenter.detachView(this);
        super.onDestroy();
    }
}
