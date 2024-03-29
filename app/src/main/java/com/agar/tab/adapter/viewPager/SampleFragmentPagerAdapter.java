package com.agar.tab.adapter.viewPager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.agar.tab.view.fragment.PageFragment;
import com.agar.tab.utils.Util;

/**
 * Created by Gossan on 13/09/2016.
 */
public class SampleFragmentPagerAdapter extends FragmentStatePagerAdapter/*SmartFragmentStatePagerAdapter*/ {

    private Context ctx;
    private SparseArray<Fragment> fragments = new SparseArray<>();

    public SampleFragmentPagerAdapter(FragmentManager fm, Context ctx) {
        super(fm);
        this.ctx = ctx;
    }

    @Override
    public Fragment getItem(int position) {

        if(fragments.get(position/*, null*/) != null)
            return fragments.get(position);
        else {
            String pageName = (String)(Util.map.keySet().toArray()[position]);
            Fragment fm = PageFragment.newInstance(pageName);
            fragments.put(position, fm);
            return fm;
        }
    }

    /*@Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        fragments.remove(position);
        super.destroyItem(container, position, object);
    }*/

    @Override
    public int getCount() {
        return Util.map.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (String)(Util.map.keySet().toArray()[position]);
    }

    public SparseArray<Fragment> getFragments() {
        return fragments;
    }

    public void setFragments(SparseArray<Fragment> fragments) {
        this.fragments = fragments;
    }
}
