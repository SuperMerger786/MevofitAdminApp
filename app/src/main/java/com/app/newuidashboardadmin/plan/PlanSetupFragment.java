package com.app.newuidashboardadmin.plan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.app.newuidashboardadmin.R;
import com.app.newuidashboardadmin.plan.PlanConfigurationMainFragment;
import com.app.newuidashboardadmin.plan.PlanFragment;
import com.app.newuidashboardadmin.plan.PlanViewPagerAdapterAdmin;
import com.app.newuidashboardadmin.plan.bean.SessionList;
import com.app.newuidashboardadmin.plan.bean.StandardPlanData;
import com.app.newuidashboardadmin.plan.bean.request.GetStandardPlan;
import com.app.newuidashboardadmin.services.Response;
import com.app.newuidashboardadmin.services.RestApiController;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

public class PlanSetupFragment extends Fragment {

    StandardPlanData standardPlanData;
    String plan_session_type;
    TextView session_titel;
    EditText time,session,price;
    Switch switchonoff;
    boolean isEditable = false;
    int time_min;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            Log.d("receiver", "Got message: " + message);
            time.setText(message+" MINS");
            time_min = Integer.parseInt(message);
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.plan_setup_fragment, container, false);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMessageReceiver,
                new IntentFilter("timechange"));
        initView(view);
        return view;
    }

    private void initView(View view) {
        session_titel = view.findViewById(R.id.session_titel);
        time = view.findViewById(R.id.time);
        session = view.findViewById(R.id.session);
        price = view.findViewById(R.id.price);
        switchonoff = view.findViewById(R.id.switchonoff);
        if(isEditable){
            time.setBackgroundResource(R.drawable.grey_border_container);
            session.setBackgroundResource(R.drawable.grey_border_container);
            price.setBackgroundResource(R.drawable.grey_border_container);
            time.setEnabled(true);
            session.setEnabled(true);
            price.setEnabled(true);
        }else {
            time.setEnabled(true);
            session.setEnabled(false);
            price.setEnabled(false);
            time.setBackgroundResource(R.drawable.grey_border_container);
            session.setBackgroundResource(R.drawable.grey_border_background_container);
            price.setBackgroundResource(R.drawable.grey_border_background_container);
        }

        setView();
    }

    private void setView(){
        session_titel.setText(standardPlanData.plan_title);
        session.setText(standardPlanData.no_of_session);
        price.setText(standardPlanData.discounted_price);
        switchonoff.setChecked(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mMessageReceiver);

    }
}
