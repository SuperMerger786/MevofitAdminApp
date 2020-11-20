package com.app.newuidashboardadmin.plan;


import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class PlanViewPagerAdapterAdmin extends FragmentPagerAdapter
{
    private final List<Fragment> mFragmentList;
    private final List<String> mFragmentTitleList;

    public PlanViewPagerAdapterAdmin(FragmentManager manager, List<Fragment> fragment, List<String> title) {
        super(manager);
        this.mFragmentList = fragment;
        this.mFragmentTitleList = title;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount()
    {
        return mFragmentList.size();
    }



    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}