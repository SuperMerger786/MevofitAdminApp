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
import android.widget.TextView;

import com.app.newuidashboardadmin.R;
import com.app.newuidashboardadmin.plan.bean.SessionList;
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
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

public class StandardPlanFragment extends Fragment {

    TextView firstsection, secondsection, thirdsection;
    ViewPager firstViewpager, secondviewpager, thirdviewpager;
    TabLayout tab_layout, tab_layout_second, tab_layout_third;
    CardView third_card, second_card, first_card;
    EditText timeedit;
    long delay = 1000; // 1 seconds after user stops typing
    long last_text_edit = 0;
    Handler handler = new Handler();
    String time = "15";
    String instanceid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.standard_plan_layout_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

//        instanceid = getArguments().getString("instanceId");

        timeedit = view.findViewById(R.id.timeedit);
        first_card = view.findViewById(R.id.first_card);
        second_card = view.findViewById(R.id.second_card);
        third_card = view.findViewById(R.id.third_card);

        first_card.setVisibility(View.GONE);
        second_card.setVisibility(View.GONE);
        third_card.setVisibility(View.GONE);

        firstsection = view.findViewById(R.id.firstsection);
        firstViewpager = view.findViewById(R.id.firstViewpager);
        tab_layout = view.findViewById(R.id.tab_layout);

        secondsection = view.findViewById(R.id.secondsection);
        secondviewpager = view.findViewById(R.id.secondviewpager);
        tab_layout_second = view.findViewById(R.id.tab_layout_second);

        thirdsection = view.findViewById(R.id.thirdsection);
        thirdviewpager = view.findViewById(R.id.thirdviewpager);
        tab_layout_third = view.findViewById(R.id.tab_layout_third);
        fetchStandardData();

        Runnable input_finish_checker = new Runnable() {
            public void run() {
                if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
                    sendMessage(timeedit.getText().toString());
                    time = timeedit.getText().toString();
                }
            }
        };
        timeedit.setText(String.valueOf(time));
        timeedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // is only executed if the EditText was directly changed by the user
                handler.removeCallbacks(input_finish_checker);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    last_text_edit = System.currentTimeMillis();
                    handler.postDelayed(input_finish_checker, delay);
                } else {
                }
            }
        });

    }

    private void fetchStandardData() {
        Gson gson = new Gson();
        RestApiController restApiController = new RestApiController(getContext(), new Response() {
            @Override
            public void onResponseObtained(Object response, int responseType, boolean isCachedData) {
                System.out.println("PlanSetupFragment.onResponseObtained data response is " + response.toString());
                StandardPlanResponse standardPlanResponse = gson.fromJson(response.toString(), StandardPlanResponse.class);
                if (standardPlanResponse != null && standardPlanResponse.data != null && standardPlanResponse.data.size() > 0) {
                    setFirstsection(standardPlanResponse.data.get(0));
                }
                if (standardPlanResponse != null && standardPlanResponse.data != null && standardPlanResponse.data.size() > 1) {
                    setSecondsection(standardPlanResponse.data.get(1));
                }
                if (standardPlanResponse != null && standardPlanResponse.data != null && standardPlanResponse.data.size() > 2) {
                    setThirdsection(standardPlanResponse.data.get(2));
                }
            }

            @Override
            public void onErrorObtained(String errormsg, int responseType) {
                System.out.println("PlanSetupFragment.onErrorObtained asdf error " + errormsg);
            }
        }, 45);
        GetStandardPlan getStandardPlan = new GetStandardPlan(getContext(), instanceid);
        restApiController.fetchStandardPlan(getStandardPlan);
        System.out.println("StandardPlanFragment.fetchStandardData instance "+instanceid);
    }

    private void setFirstsection(SessionList sessionList) {
        first_card.setVisibility(View.VISIBLE);
        ArrayList<Fragment> tabFrag = new ArrayList<>();
        ArrayList<String> tabTitle = new ArrayList<>();
        firstsection.setText(sessionList.plan_session_type.replace("_", " ").toUpperCase());
        for (int i = 0; i < sessionList.StandardPlanData.size(); i++) {
            PlanSetupFragment planSetupFragment = new PlanSetupFragment();
            planSetupFragment.plan_session_type = sessionList.plan_session_type;
            planSetupFragment.standardPlanData = sessionList.StandardPlanData.get(i);
            planSetupFragment.time_min = Integer.parseInt(time);
            tabFrag.add(planSetupFragment);
            tabTitle.add("");
        }

        PlanViewPagerAdapterAdmin adapter = new PlanViewPagerAdapterAdmin(this.getChildFragmentManager(), tabFrag, tabTitle);
        firstViewpager.setAdapter(adapter);
        firstViewpager.setOffscreenPageLimit(2);
        tab_layout.setupWithViewPager(firstViewpager, true);
    }

    private void setSecondsection(SessionList sessionList) {
        second_card.setVisibility(View.VISIBLE);
        ArrayList<Fragment> tabFrag = new ArrayList<>();
        ArrayList<String> tabTitle = new ArrayList<>();
        secondsection.setText(sessionList.plan_session_type.replace("_", " ").toUpperCase());
        for (int i = 0; i < sessionList.StandardPlanData.size(); i++) {
            PlanSetupFragment planSetupFragment = new PlanSetupFragment();
            planSetupFragment.plan_session_type = sessionList.plan_session_type;
            planSetupFragment.time_min = Integer.parseInt(time);
            planSetupFragment.standardPlanData = sessionList.StandardPlanData.get(i);
            tabFrag.add(planSetupFragment);
            tabTitle.add("");
        }

        PlanViewPagerAdapterAdmin adapter = new PlanViewPagerAdapterAdmin(this.getChildFragmentManager(), tabFrag, tabTitle);
        secondviewpager.setAdapter(adapter);
        secondviewpager.setOffscreenPageLimit(2);
        tab_layout_second.setupWithViewPager(secondviewpager, true);
    }

    private void setThirdsection(SessionList sessionList) {
        third_card.setVisibility(View.VISIBLE);
        ArrayList<Fragment> tabFrag = new ArrayList<>();
        ArrayList<String> tabTitle = new ArrayList<>();
        thirdsection.setText(sessionList.plan_session_type.replace("_", " ").toUpperCase());
        for (int i = 0; i < sessionList.StandardPlanData.size(); i++) {
            PlanSetupFragment planSetupFragment = new PlanSetupFragment();
            planSetupFragment.plan_session_type = sessionList.plan_session_type;
            planSetupFragment.time_min = Integer.parseInt(time);
            planSetupFragment.standardPlanData = sessionList.StandardPlanData.get(i);
            tabFrag.add(planSetupFragment);
            tabTitle.add("");
        }
        PlanViewPagerAdapterAdmin adapter = new PlanViewPagerAdapterAdmin(this.getChildFragmentManager(), tabFrag, tabTitle);
        thirdviewpager.setAdapter(adapter);
        thirdviewpager.setOffscreenPageLimit(2);
        tab_layout_third.setupWithViewPager(thirdviewpager, true);
    }

    private void sendMessage(String time) {
        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("timechange");
        // You can also include some extra data.
        intent.putExtra("message", time);
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }

    private void openTimePrompt() {

    }


}
