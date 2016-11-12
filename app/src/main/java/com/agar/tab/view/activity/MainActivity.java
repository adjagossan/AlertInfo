package com.agar.tab.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.agar.tab.R;
import com.agar.tab.adapter.viewPager.SampleFragmentPagerAdapter;
import com.agar.tab.utils.Util;
import com.agar.tab.view.fragment.PageFragment;
import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Subscription;
import rx.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity{

    private PagerSlidingTabStrip pagerSlidingTabStrip;
    private ViewPager viewPager;
    private SampleFragmentPagerAdapter fragmentPagerAdapter;
    static PublishSubject<String> publishSubject = PublishSubject.create();
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

       /* if(BuildConfig.DEBUG){
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolBar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolBar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources()
                .getDisplayMetrics());
        viewPager.setPageMargin(pageMargin);

        fragmentPagerAdapter = new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                MainActivity.this);

        if(savedInstanceState != null){
            SparseArray<Fragment> fragments = new SparseArray<>();
            List<String> titles = savedInstanceState.getStringArrayList("TAB_TITLE");
            for(int index = 0; index < titles.size(); index++){
                Fragment fm = getSupportFragmentManager().getFragment(savedInstanceState,titles.get(index));
                fragments.put(index, fm);
            }
            fragmentPagerAdapter.setFragments(fragments);
        }

        viewPager.setAdapter(fragmentPagerAdapter);

        pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pagerSlidingTabStrip.setViewPager(viewPager);

        pagerSlidingTabStrip.setOnPageChangeListener(mPageChangeListener);

        subscription = publishSubject.subscribe(link -> this.onRssItemSelected(link));
    }

    ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            int currentItem = viewPager.getCurrentItem();
            PageFragment fm = (PageFragment)fragmentPagerAdapter.getFragments().get(currentItem);
            showSnackBar(fm);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(subscription != null)
            subscription.unsubscribe();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        SparseArray<Fragment> fragments = fragmentPagerAdapter.getFragments();
        List<String> titles = new ArrayList<>();

        for(int index = 0; index < fragments.size(); index++){
            PageFragment pf = (PageFragment)fragments.get(index);
            String mPage = pf.getArguments().getString("ARG_PAGE");
            titles.add(mPage);
            outState.remove(mPage);
            if(pf.isAdded())
                getSupportFragmentManager().putFragment(outState, mPage, pf);
        }
        outState.remove("TAB_TITLE");
        outState.putStringArrayList("TAB_TITLE", new ArrayList<>(titles));

        Toast.makeText(this, Integer.toString(outState.size())+" bundle", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        Toast.makeText(this, Integer.toString(fragmentPagerAdapter.getFragments().size()), Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_refresh:
                int currentItem = viewPager.getCurrentItem();
                PageFragment fm = (PageFragment)fragmentPagerAdapter.getFragments().get(currentItem);
                Toast.makeText(this, fm.toString(), Toast.LENGTH_SHORT).show();
                ProgressBar pb = (ProgressBar) fm.getView().findViewById(R.id.progressBar);
                pb.setVisibility(View.VISIBLE);
                String mPage = fm.getArguments().getString("ARG_PAGE");
                fm.getPresenter().refresh(Util.map.get(mPage));
                pb.setVisibility(View.GONE);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onRssItemSelected(String link) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(link));
        startActivity(intent);
    }

    public static PublishSubject<String> getSubject(){
        return publishSubject;
    }

    private void showSnackBar(PageFragment pf){
        if(!Util.isConnected()){

            Snackbar.make(pf.getView(), R.string.snackBarText, Snackbar.LENGTH_LONG)
                    .setAction(R.string.snackBarAction,
                            v -> {
                                if(Util.isOnline()) {
                                    pf.getView().findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                                    String mPage = pf.getArguments().getString("ARG_PAGE");
                                    pf.getPresenter().loadData(Util.map.get(mPage));
                                }else
                                    pf.getView().findViewById(R.id.progressBar).setVisibility(View.GONE);
                                showSnackBar(pf);
                            })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_dark))
                    .show();
        }
    }
}
