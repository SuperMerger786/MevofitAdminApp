package com.app.newuidashboardadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.app.newuidashboardadmin.plan.PlanConfigurationMainFragment;
import com.app.newuidashboardadmin.plan.PlanFragment;

public class PlanConfigureActivity extends AppCompatActivity {
    String instanceId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_configure);
        instanceId = getIntent().getStringExtra("instanceId");
        System.out.println("PlanConfigureActivity.onCreate instance id "+instanceId);
        PlanConfigurationMainFragment planConfigurationMainFragment = new PlanConfigurationMainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("instanceid",instanceId);
        planConfigurationMainFragment.setArguments(bundle);
        setFragment(planConfigurationMainFragment);
    }

    protected void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.placeholder, fragment);
        fragmentTransaction.commit();
    }

}
