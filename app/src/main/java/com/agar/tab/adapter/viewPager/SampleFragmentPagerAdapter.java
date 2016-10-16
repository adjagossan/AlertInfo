package com.agar.tab.adapter.viewPager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.agar.tab.view.fragment.PageFragment;
import com.agar.tab.utils.Util;

import java.util.Map;

/**
 * Created by Gossan on 13/09/2016.
 */
public class SampleFragmentPagerAdapter extends SmartFragmentStatePagerAdapter {

    private Context ctx;

    public SampleFragmentPagerAdapter(FragmentManager fm, Context ctx) {
        super(fm);
        this.ctx = ctx;
    }

    @Override
    public Fragment getItem(int position) {
        for(Map.Entry<String, String> entry : Util.map.entrySet()){
            Log.i("PagerAdapter1", entry.getKey());
        }

        String pageName = (String)(Util.map.keySet().toArray()[position]);
        Log.i("PagerAdapter2", position+" "+pageName);
        return PageFragment.newInstance(pageName);
    }

    @Override
    public int getCount() {
        return Util.map.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Log.i("PagerAdapter3", position+" "+(Util.map.keySet().toArray()[position]));
        return (String)(Util.map.keySet().toArray()[position]);
    }
}
