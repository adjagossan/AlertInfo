package com.agar.tab.view.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agar.tab.R;
import com.agar.tab.adapter.recyclerView.RssFeedAdapter;
import com.agar.tab.model.Item;
import com.agar.tab.presenter.PageFragmentPresenter;
import com.agar.tab.utils.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gossan on 13/09/2016.
 */
public class PageFragment extends Fragment {
    static final String ARG_PAGE = "ARG_PAGE";
    static final String ARG_ITEM = "ARG_ITEM";
    private String mPage;
    private static Fragment mFragment;
    private PageFragmentPresenter presenter;

    private Fragment getInstance(){
        return mFragment;
    }

    private String getPage(){
        return getArguments().getString(ARG_PAGE);
    }

    private List<Item> getItem(){
        return getArguments().getParcelableArrayList(ARG_ITEM);
    }

    public void setItem(List<Item> items){
        getArguments().putParcelableArrayList(ARG_ITEM, new ArrayList<Parcelable>(items));
    }

    public static Fragment newInstance(String pageName){
        Bundle args = new Bundle();
        args.putString(ARG_PAGE, pageName);
        mFragment = new PageFragment();
        mFragment.setArguments(args);
        return mFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PageFragmentPresenter();
        presenter.attachView(this);
        mPage = getArguments().getString(ARG_PAGE);
        Log.i(this.getClass().getCanonicalName(), Util.map.get(mPage));
        presenter.loadData(Util.map.get(mPage));

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        //Log.i("PageFragment", Integer.toString(getItem().size()));
        RssFeedAdapter rssFeedAdapter = new RssFeedAdapter(getItem(), getContext());
        recyclerView.setAdapter(rssFeedAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //TextView textView = (TextView)view.findViewById(R.id.tvTitle);
        //textView.setText("Fragment #"+mPage);
        return view;
    }

    @Override
    public void onDestroy() {
        presenter.detachView(this);
        super.onDestroy();
    }
}
