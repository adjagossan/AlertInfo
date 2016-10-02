package com.agar.tab.adapter.viewPager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.agar.tab.view.fragment.PageFragment;
import com.agar.tab.utils.Util;

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
        Fragment f = this.getRegisteredFragment(position);
        if(f == null){
            String pageName = (String)(Util.map.keySet().toArray()[position]);
            f = PageFragment.newInstance(pageName);
        }
        return f/*PageFragment.newInstance(pageName)*/;
    }

    @Override
    public int getCount() {
        return Util.map.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (String)(Util.map.keySet().toArray()[position]);
    }
}
