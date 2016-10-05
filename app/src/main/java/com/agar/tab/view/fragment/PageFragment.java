package com.agar.tab.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private OnItemSelectedListener listener;

    public interface OnItemSelectedListener{
        void onRssItemSelected(String link);
    }


    public static Fragment newInstance(String pageName){
        Bundle args = new Bundle();
        args.putString(ARG_PAGE, pageName);
        PageFragment mFragment = new PageFragment();
        mFragment.setArguments(args);
        return mFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnItemSelectedListener)
            listener = (OnItemSelectedListener)context;
        else
            throw new ClassCastException(context.toString()+" must implement PageFragment.OnItemSelectedListener");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PageFragmentPresenter();
        presenter.attachView(this);
        mPage = getArguments().getString(ARG_PAGE);
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
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RssFeedAdapter rssFeedAdapter = new RssFeedAdapter(getContext());
        rssFeedAdapter.getPublishSubject().subscribe(link -> listener.onRssItemSelected(link));
        recyclerView.setAdapter(rssFeedAdapter);
        if(!presenter.getItems().isEmpty())
            presenter.update();
    }

    @Override
    public void onDestroy() {
        presenter.detachView(this);
        super.onDestroy();
    }
}
