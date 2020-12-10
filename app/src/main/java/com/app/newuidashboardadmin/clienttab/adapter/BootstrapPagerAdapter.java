package com.app.newuidashboardadmin.clienttab.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;



import java.util.ArrayList;

public class BootstrapPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<Fragment> fragments;
    public BootstrapPagerAdapter(FragmentManager fragmentManager, ArrayList<Fragment> fragments) {
        super(fragmentManager);
        this.fragments=fragments;
    }

    @Override
    public int getCount() {
        return fragments.size();//6;
    }

    @Override
    public int getItemPosition(Object object) {
        return FragmentStatePagerAdapter.POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
       return fragments.get(position);
    }
}
