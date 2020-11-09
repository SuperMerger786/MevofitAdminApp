package com.app.newuidashboardadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    //    BootstrapPagerAdapter pagerAdapter;
    public ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = (ViewPager) findViewById(R.id.pager);
       /* llStats = (RelativeLayout) findViewById(R.id.llStats);
        llDevice = (RelativeLayout) findViewById(R.id.tabLayout3);
        tabLayout4 = (RelativeLayout) findViewById(R.id.tabLayout4);
        tabLayout2 = (RelativeLayout) findViewById(R.id.tabLayout2);
        tabLayout5 = (RelativeLayout) findViewById(R.id.tabLayout5);
        tabLayout6 = (RelativeLayout) findViewById(R.id.tabLayout6);
        icon_tab1 = (ImageView) findViewById(R.id.icon_tab1);
        icon_tab2 = (ImageView) findViewById(R.id.icon_tab2);
        icon_tab3 = (ImageView) findViewById(R.id.icon_tab3);
        icon_tab4 = (ImageView) findViewById(R.id.icon_tab4);
        icon_tab5 = (ImageView) findViewById(R.id.icon_tab5);
        icon_tab6 = (ImageView) findViewById(R.id.icon_tab6);*/
    }
   /* private void setInItPager(String from) {
        pagerAdapter = new BootstrapPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(4);
        pager.setPageTransformer(true, new ZoomOutPageTransformer());
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int i) {
                System.out.println("<<<< open quiz 2222 : " + i);
                icon_tab1.setSelected(false);
                icon_tab2.setSelected(false);
                icon_tab3.setSelected(false);
                icon_tab4.setSelected(false);
                icon_tab5.setSelected(false);
                icon_tab6.setSelected(false);

                if (i == 0) {
//                    if (TabListFragment.viewPager != null) {
//                        TabListFragment.viewPager.setCurrentItem(0);
//                    }
                    icon_tab1.setSelected(true);
                } else if (i == 1) {
                    icon_tab2.setSelected(true);
                } else if (i == 2) {
                    icon_tab3.setSelected(true);
                } else if (i == 3) {
                    icon_tab6.setSelected(true);
                } else if (i == 4) {
                    icon_tab4.setSelected(true);
                } else if (i == 5) {
                    icon_tab5.setSelected(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        pager.setCurrentItem(0);
        MyLogger.println("MainWristActivity>>>>>>" + from + "  " + pager);
    }
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
            tabListFragment = new TabListFragment();
            if (position == 0) {
                return tabListFragment;
            } else if (position == 1) {
                return new ForumDetailRecycler();
            } else if (position == 2) {
                return new PagerFragment();
            } else if (position == 3) {
                return new ChallengesViewPagerFragment();
            } else {
                return new ChallengesViewPagerFragment();
            }
        }
    }*/
}
