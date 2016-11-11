package com.agar.tab.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.agar.tab.R;
import com.agar.tab.adapter.viewPager.SampleFragmentPagerAdapter;
import com.agar.tab.utils.Util;
import com.agar.tab.view.fragment.PageFragment;
import com.astuetz.PagerSlidingTabStrip;

import rx.Subscription;
import rx.subjects.PublishSubject;

public class MainActivity extends AppCompatActivity{

    ViewPager viewPager;
    SampleFragmentPagerAdapter fragmentPagerAdapter;
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
        viewPager.setAdapter(fragmentPagerAdapter);

        PagerSlidingTabStrip pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pagerSlidingTabStrip.setViewPager(viewPager);

        subscription = publishSubject.subscribe(link -> this.onRssItemSelected(link));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(subscription != null)
            subscription.unsubscribe();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_refresh:
                int currentItem = viewPager.getCurrentItem();
                PageFragment fm = (PageFragment)fragmentPagerAdapter.getFragments().get(currentItem);
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

}
