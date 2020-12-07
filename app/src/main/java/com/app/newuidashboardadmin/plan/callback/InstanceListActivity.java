package com.app.newuidashboardadmin.plan.callback;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.newuidashboardadmin.AdminUI;
import com.app.newuidashboardadmin.PlanConfigureActivity;
import com.app.newuidashboardadmin.R;
import com.app.newuidashboardadmin.Utility.AppPrefernce;
import com.app.newuidashboardadmin.plan.adapter.CategoryAddapter;
import com.app.newuidashboardadmin.plan.bean.CategoryData;
import com.app.newuidashboardadmin.plan.bean.GetSellerInstancesResponse;
import com.app.newuidashboardadmin.plan.bean.request.GetSellerInstancesRequest;
import com.app.newuidashboardadmin.services.Response;
import com.app.newuidashboardadmin.services.RestApiController;
import com.google.gson.Gson;

import java.util.ArrayList;

public class InstanceListActivity extends AppCompatActivity {
    RecyclerView instancelist;
    LinearLayout continue_btn;
    AppPrefernce prefernce;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instance_list);
        initView();
    }

    /* @Nullable
     @Override
     public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.activity_instance_list, container, false);
         initView(view);
         return view;
     }
 */
    private void initView() {
        instancelist = findViewById(R.id.instancelist);
        continue_btn = findViewById(R.id.continue_btn);
        prefernce = new AppPrefernce(InstanceListActivity.this);
        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstanceListActivity.this, AdminUI.class);
                startActivity(intent);
                finish();
            }
        });
        fetchData();
    }

    private void fetchData() {
        Gson gson = new Gson();
        RestApiController restApiController = new RestApiController(InstanceListActivity.this, new Response() {
            @Override
            public void onResponseObtained(Object response, int responseType, boolean isCachedData) {
                try {
                    GetSellerInstancesResponse getSellerInstancesResponse = gson.fromJson(response.toString(), GetSellerInstancesResponse.class);
                    setRecyclerData(getSellerInstancesResponse);
                } catch (Exception e) {
                    System.out.println("InstanceListFragment.onResponseObtained sdfs " + e);
                    e.printStackTrace();
                }

            }

            @Override
            public void onErrorObtained(String errormsg, int responseType) {

            }
        }, 45);

        GetSellerInstancesRequest getSellerInstancesRequest = new GetSellerInstancesRequest(InstanceListActivity.this, "1");
        restApiController.fetchInstnce(getSellerInstancesRequest);
    }

    CategoryData categoryData;

    public void setRecyclerData(GetSellerInstancesResponse getSellerInstancesResponse) {
        if (getSellerInstancesResponse != null && getSellerInstancesResponse.instanceData != null) {
            ArrayList<CategoryData> categoryDataArrayList = new ArrayList<>();
            for (int i = 0; i < getSellerInstancesResponse.instanceData.size(); i++) {
                for (int j = 0; j < getSellerInstancesResponse.instanceData.get(i).category_data.size(); j++) {
                    categoryData = getSellerInstancesResponse.instanceData.get(i).category_data.get(j);
                    categoryData.instance_boxid = getSellerInstancesResponse.instanceData.get(i).instance_boxid;
                    prefernce.setInstanceBoxid(categoryData.instance_boxid);
                    categoryData.instance_title = getSellerInstancesResponse.instanceData.get(i).instance_title;
                    categoryDataArrayList.add(categoryData);
                }
            }
            CategoryAddapter categoryAddapter = new CategoryAddapter(categoryDataArrayList, InstanceListActivity.this);
            instancelist.setLayoutManager(new LinearLayoutManager(InstanceListActivity.this));
            instancelist.setAdapter(categoryAddapter);
        }
    }
}
