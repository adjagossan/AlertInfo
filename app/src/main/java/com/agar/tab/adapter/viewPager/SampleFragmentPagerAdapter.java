package com.agar.tab.adapter.viewPager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.agar.tab.view.fragment.PageFragment;
import com.agar.tab.utils.Util;

/**
 * Created by Gossan on 13/09/2016.
 */
public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context ctx;
    private String[] tabTitle = new String[]{"Tab1", "Tab2", "Tab3"};
    private SparseArray<Fragment> registeredFragments = new SparseArray<>();

    public SampleFragmentPagerAdapter(FragmentManager fm, Context ctx) {
        super(fm);
        this.ctx = ctx;
        Util.init();
    }

    @Override
    public Fragment getItem(int position) {
        String pageName = (String)(Util.map.keySet().toArray()[position]);
        return PageFragment.newInstance(pageName);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    public Fragment getRegisteredFragment(int position){
        return registeredFragments.get(position);
    }

    @Override
    public int getCount() {
        return this.tabTitle.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (String)(Util.map.keySet().toArray()[position])/*tabTitle[position]*/;
    }
}
