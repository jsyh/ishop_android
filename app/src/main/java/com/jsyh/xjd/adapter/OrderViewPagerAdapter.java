package com.jsyh.xjd.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jsyh.xjd.fragment.OrderFragment;

import java.util.List;

/**
 * Created by Su on 2016/5/3.
 */
public class OrderViewPagerAdapter extends FragmentPagerAdapter {
    private List<String> mStringList;

    private FragmentManager mFragmentManager;

    public OrderViewPagerAdapter(FragmentManager fm, List<String> stringList) {
        super(fm);
        mFragmentManager = fm;
        mStringList = stringList;
    }

    @Override
    public Fragment getItem(int position) {
        return OrderFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return mStringList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mStringList.get(position);
    }

}
