package com.app.newuidashboardadmin.plan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.newuidashboardadmin.R;
import com.app.newuidashboardadmin.plan.bean.SlotData;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.megogrid.megoauth.AuthorisedPreference;

import java.util.ArrayList;

public class UserDetailsActivity extends AppCompatActivity {
    TabLayout tabs;
    ViewPager viewPager;

    ImageView sellerimage;
    TextView name, subtitel, description;
    private ArrayList<String> tabTitle = new ArrayList<>();
    private ArrayList<Fragment> tabFrag = new ArrayList<>();
    SlotData slotData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        slotData = (SlotData) getIntent().getSerializableExtra("data");
        initViews();
    }

    private void initViews() {
        viewPager = findViewById(R.id.view_pager);
        tabs = findViewById(R.id.tabs);
        name = findViewById(R.id.name);
        subtitel = findViewById(R.id.subtitel);
        description = findViewById(R.id.description);
        sellerimage = findViewById(R.id.sellerimage);
        Glide.with(this).load(slotData.user_profilepic).into(sellerimage);
        name.setText(slotData.user_name);
        subtitel.setText(slotData.user_gender + " " + slotData.user_age + " years");
        description.setText("Marketplce | " + slotData.user_city + "," + slotData.user_country);
        setTabview();
    }

    private void setTabview() {
        tabTitle.clear();
        tabFrag.clear();

        tabTitle.add("VITALS");
        VitalFragment viaVitalFragment = new VitalFragment();
        tabFrag.add(viaVitalFragment);

        tabTitle.add("TRACKER");
        VitalFragment vitalFragmenta = new VitalFragment();
        tabFrag.add(vitalFragmenta);

        tabTitle.add("SUPPORT");
        VitalFragment vitalFragmentb = new VitalFragment();
        tabFrag.add(vitalFragmentb);

        tabTitle.add("BILLING");
        VitalFragment vitalFragmentc = new VitalFragment();
        tabFrag.add(vitalFragmentc);

        PlanViewPagerAdapterAdmin adapter = new PlanViewPagerAdapterAdmin(this.getSupportFragmentManager(), tabFrag, tabTitle);
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

        tabs.setSelectedTabIndicatorColor(Color.parseColor(new AuthorisedPreference(this).getThemeColor()));
        tabs.setTabTextColors(Color.parseColor("#838383"), Color.parseColor(new AuthorisedPreference(this).getThemeColor()));

        View root = tabs.getChildAt(0);
        if (root instanceof LinearLayout) {
            ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(Color.parseColor("#f5f5f5"));
            drawable.setSize(4, 1);
            ((LinearLayout) root).setDividerPadding(10);
            ((LinearLayout) root).setDividerDrawable(drawable);
        }

    }

}
