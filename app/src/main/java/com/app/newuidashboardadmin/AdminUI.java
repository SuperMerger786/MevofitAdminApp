package com.app.newuidashboardadmin;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.app.newuidashboardadmin.planner.PlanWorkout;


public class AdminUI extends AppCompatActivity  {
    ImageView icon_tab1, icon_tab2, icon_tab3, icon_tab4, icon_tab6;
    RelativeLayout llStats, llDevice, tabLayout4, tabLayout2, tabLayout6;
    public ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newui_test);
        pager = (ViewPager) findViewById(R.id.pager);
        llStats = (RelativeLayout) findViewById(R.id.llStats);
        llDevice = (RelativeLayout) findViewById(R.id.tabLayout3);
        tabLayout4 = (RelativeLayout) findViewById(R.id.tabLayout4);
        tabLayout2 = (RelativeLayout) findViewById(R.id.tabLayout2);
        tabLayout6 = (RelativeLayout) findViewById(R.id.tabLayout6);
        icon_tab1 = (ImageView) findViewById(R.id.icon_tab1);
        icon_tab2 = (ImageView) findViewById(R.id.icon_tab2);
        icon_tab3 = (ImageView) findViewById(R.id.icon_tab3);
        icon_tab4 = (ImageView) findViewById(R.id.icon_tab4);
        icon_tab6 = (ImageView) findViewById(R.id.icon_tab6);
        llStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(0);
            }
        });

        llDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                stopTimerSync();
                pager.setCurrentItem(2);
            }
        });
        tabLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                stopTimerSync();
                pager.setCurrentItem(1);
            }
        });
        tabLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                stopTimerSync();
                pager.setCurrentItem(4);
            }
        });

        tabLayout6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                stopTimerSync();
                pager.setCurrentItem(3);
            }
        });
        setInItPager("oncreate");
    }



    private void setInItPager(String from) {
        BootstrapPagerAdapter pagerAdapter = new BootstrapPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(5);
        pager.setPageTransformer(true, new ZoomOutPageTransformer());
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int i) {
                MyLogger.println("<<<< open quiz 2222 : " + i);
                icon_tab1.setSelected(false);
                icon_tab2.setSelected(false);
                icon_tab3.setSelected(false);
                icon_tab4.setSelected(false);
                icon_tab6.setSelected(false);

                if (i == 0) {
                    icon_tab1.setSelected(true);
                } else if (i == 1) {
                    icon_tab2.setSelected(true);
                } else if (i == 2) {
                    icon_tab3.setSelected(true);
                } else if (i == 3) {
                    icon_tab6.setSelected(true);
                } else if (i == 4) {
                    icon_tab4.setSelected(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                final float normalizedposition = Math.abs(Math.abs(position) - 1);
                page.setAlpha(normalizedposition);
            }
        });
        pager.setCurrentItem(0);
        MyLogger.println("MainWristActivity>>>>>>" + from + "  " + pager);
    }

    AdminDashBoardNewFragment tabListFragment;

    public class BootstrapPagerAdapter extends FragmentStatePagerAdapter {

        public BootstrapPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return 5;//6;
        }

        @Override
        public int getItemPosition(Object object) {
            return FragmentStatePagerAdapter.POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            tabListFragment = new AdminDashBoardNewFragment();
            if (position == 0) {
                return tabListFragment;
            } else if (position == 2) {
                return new AdminDashBoardNewFragment();
            } else if (position == 1) {
                return new AdminDashBoardNewFragment();
            } else if (position == 4) {
                return new PlanWorkout();

            } else {
                return new AdminDashBoardNewFragment();
            }
        }
    }

    public void clearReference() {
        if (tabListFragment != null) {
            tabListFragment = null;
        }
    }

}
