package com.agar.tab.view.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;

import com.agar.tab.R;
import com.agar.tab.adapter.viewPager.SampleFragmentPagerAdapter;
import com.astuetz.PagerSlidingTabStrip;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    SampleFragmentPagerAdapter fragmentPagerAdapter;

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

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        viewPager.setPageMargin(pageMargin);

        fragmentPagerAdapter = new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                MainActivity.this);
        viewPager.setAdapter(fragmentPagerAdapter);

        PagerSlidingTabStrip pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pagerSlidingTabStrip.setViewPager(viewPager);
    }

    /*
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //loadList();
    }

    @TargetApi(Build.VERSION_CODES.N)
    private Observable<List<Item>> getFeed(){
        return Observable.create(subscriber -> {
            List<Rss> list =  DataHelper.getData();
            for(Rss rss : list){
                subscriber.onNext(rss.getChannel().getItem());
            }
            //list.forEach(rss -> subscriber.onNext(rss.getChannel().getItem()));
            subscriber.onCompleted();
        });
    }

    private int count;

    private void loadList(){
        count = 0;
        Subscriber<List<Item>> subscriber = new Subscriber<List<Item>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<Item> items) {
                View view = fragmentPagerAdapter.getRegisteredFragment(count).getView();
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
                RssFeedAdapter adapter = (RssFeedAdapter) recyclerView.getAdapter();
                adapter.setItems(items);
                count++;
            }
        };

        getFeed()
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }*/
}
