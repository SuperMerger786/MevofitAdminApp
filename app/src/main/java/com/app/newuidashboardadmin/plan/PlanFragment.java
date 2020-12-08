package com.app.newuidashboardadmin.plan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.app.newuidashboardadmin.R;
import com.app.newuidashboardadmin.plan.bean.SessionList;
import com.app.newuidashboardadmin.plan.bean.StandardPlanData;
import com.app.newuidashboardadmin.plan.bean.StandardPlanResponse;
import com.app.newuidashboardadmin.plan.bean.request.GetStandardPlan;
import com.app.newuidashboardadmin.services.Response;
import com.app.newuidashboardadmin.services.RestApiController;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

public class PlanFragment extends Fragment {
String instanceid;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.plan_layout_fragment, container, false);
//        instanceid = getArguments().getString("instanceid");
        initView(view);
        return view;
    }

    private void initView(View view) {
        RadioGroup rg = (RadioGroup) view.findViewById(R.id.radiogroup);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.standardradio:
                        // do operations specific to this selection
                        StandardPlanFragment standardPlanFragment = new StandardPlanFragment();
                        standardPlanFragment.instanceid = instanceid;
//                        Bundle bundle = new Bundle();
//                        bundle.putString("instanceid",instanceid);
//                        standardPlanFragment.setArguments(bundle);
                        setFragment(standardPlanFragment);
                        break;
                    case R.id.customeradio:
                        // do operations specific to this selection
                        CustomePlanFragment customePlanFragment = new CustomePlanFragment();
                        customePlanFragment.instanceid = instanceid;
//                        Bundle bundletwo = new Bundle();
//                        bundletwo.putString("instanceid",instanceid);
//                        customePlanFragment.setArguments(bundletwo);
                        setFragment(customePlanFragment);
                        break;
                }
            }
        });
    }

    protected void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.placeholderchild, fragment);
        fragmentTransaction.commit();
    }

}
