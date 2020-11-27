package com.app.newuidashboardadmin.plan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.newuidashboardadmin.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

public class PlanConfigurationMainFragment extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.plan_configuration_main_fragment,container,false);
    initView(view);
    return view;
    }
    private void initView(View view) {
        viewPager =  view.findViewById(R.id.viewpager);
        tabLayout =  view.findViewById(R.id.tabs);
        setView();
    }
    private void setView() {
        List<Fragment> mFragmentList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        mFragmentList.add(new PlanFragment());
        titleList.add("Plan");
        mFragmentList.add(new ScheduleFragment());
        titleList.add("Schedule");

        PlanViewPagerAdapterAdmin adapter = new PlanViewPagerAdapterAdmin(getChildFragmentManager(), mFragmentList, titleList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }


}
