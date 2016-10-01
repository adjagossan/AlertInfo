package com.agar.tab.adapter.viewPager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.agar.tab.view.fragment.PageFragment;
import com.agar.tab.utils.Util;

/**
 * Created by Gossan on 13/09/2016.
 */
public class SampleFragmentPagerAdapter extends /*FragmentPagerAdapter*/SmartFragmentStatePagerAdapter {

    private Context ctx;
    private SparseArray<Fragment> registeredFragments = new SparseArray<>();

    public SampleFragmentPagerAdapter(FragmentManager fm, Context ctx) {
        super(fm);
        this.ctx = ctx;
        //Util.init();
    }

    @Override
    public Fragment getItem(int position) {
        Log.i("POSITION", Integer.toString(position));
        String pageName = (String)(Util.map.keySet().toArray()[position]);
        return PageFragment.newInstance(pageName);
    }

    @Override
    public int getCount() {
        return Util.map.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (String)(Util.map.keySet().toArray()[position])/*tabTitle[position]*/;
    }
}
