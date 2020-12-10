package com.app.newuidashboardadmin.clienttab.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.app.newuidashboardadmin.R;
import com.app.newuidashboardadmin.clienttab.adapter.BootstrapPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.megogrid.megoauth.AuthorisedPreference;

import java.util.ArrayList;

public class ClientPagerFragment extends Fragment
{
    TabLayout tabLayout;
    ViewPager viewpager;
    BootstrapPagerAdapter pagerAdapter;
    LinearLayout parent,toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("ClentPagerFragment.onCreateView");
        View view = inflater.inflate(R.layout.client_pager_fragment,container,false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setView();
    }

    private void setView()
    {
        AuthorisedPreference authorisedPreference= new AuthorisedPreference(getContext());
        toolbar.setBackgroundColor(Color.parseColor("#3e"+authorisedPreference.getThemeColor().substring(1)));

        ClientFragment previous=new ClientFragment();
        Bundle bundle =new Bundle();
        bundle.putString("type","GetPreviousBookings");
        previous.setArguments(bundle);


        ClientFragment today=new ClientFragment();
        Bundle bundle2 =new Bundle();
        bundle2.putString("type","GetTodayBookings");
        today.setArguments(bundle2);


        ClientFragment upcoming=new ClientFragment();
        Bundle bundle3 =new Bundle();
        bundle3.putString("type","GetUpcomingBookings");
        upcoming.setArguments(bundle3);


        tabLayout.addTab(tabLayout.newTab().setText("PREVIOUS"));
        tabLayout.addTab(tabLayout.newTab().setText("TODAY"));
        tabLayout.addTab(tabLayout.newTab().setText("UPCOMING"));


        tabLayout.setTabTextColors(Color.parseColor("#000000"), Color.parseColor(authorisedPreference.getThemeColor()));
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor(authorisedPreference.getThemeColor()));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        ArrayList<Fragment> fragments=new ArrayList<>();
        fragments.add(previous);
        fragments.add(today);
        fragments.add(upcoming);


        pagerAdapter = new BootstrapPagerAdapter(getChildFragmentManager(),fragments);
        viewpager.setAdapter(pagerAdapter);
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewpager.setOffscreenPageLimit(fragments.size());
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initView(View view)
    {
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);
        parent= (LinearLayout) view.findViewById(R.id.parent);
        toolbar= (LinearLayout) view.findViewById(R.id.toolbar);

    }

    @Override
    public void onResume()
    {
        super.onResume();

    }

    public ViewPager getViewpager()
    {
        return viewpager;
    }



}
