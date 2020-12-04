package com.app.newuidashboardadmin.plan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.newuidashboardadmin.PlanConfigureActivity;
import com.app.newuidashboardadmin.R;
import com.app.newuidashboardadmin.plan.adapter.CategoryAddapter;
import com.app.newuidashboardadmin.plan.bean.CategoryData;
import com.app.newuidashboardadmin.plan.bean.GetSellerInstancesResponse;
import com.app.newuidashboardadmin.plan.bean.request.GetSellerInstancesRequest;
import com.app.newuidashboardadmin.services.Response;
import com.app.newuidashboardadmin.services.RestApiController;
import com.google.gson.Gson;

import java.util.ArrayList;

public class InstanceListFragment extends Fragment {
    RecyclerView instancelist;
LinearLayout continue_btn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_instance_list,container,false);
initView(view);
        return view;
    }

    private void initView(View view) {
        instancelist = view.findViewById(R.id.instancelist);
        continue_btn = view.findViewById(R.id.continue_btn);
        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PlanConfigureActivity.class);
                getActivity().startActivity(intent);
            }
        });
        fetchData();
    }

    private void fetchData() {
        Gson gson = new Gson();
        RestApiController restApiController = new RestApiController(getContext(), new Response() {
            @Override
            public void onResponseObtained(Object response, int responseType, boolean isCachedData) {
                try {
                    GetSellerInstancesResponse getSellerInstancesResponse = gson.fromJson(response.toString(), GetSellerInstancesResponse.class);
                    setRecyclerData(getSellerInstancesResponse);
                } catch (Exception e) {
                    System.out.println("InstanceListFragment.onResponseObtained sdfs "+ e);
                    e.printStackTrace();
                }

            }

            @Override
            public void onErrorObtained(String errormsg, int responseType) {

            }
        }, 45);

        GetSellerInstancesRequest getSellerInstancesRequest = new GetSellerInstancesRequest(getContext(), "1");
        restApiController.fetchInstnce(getSellerInstancesRequest);
    }

    public void setRecyclerData(GetSellerInstancesResponse getSellerInstancesResponse) {
        if (getSellerInstancesResponse != null && getSellerInstancesResponse.instanceData != null) {
            ArrayList<CategoryData> categoryDataArrayList = new ArrayList<>();
            for (int i = 0; i < getSellerInstancesResponse.instanceData.size(); i++) {
                for (int j = 0; j < getSellerInstancesResponse.instanceData.get(i).category_data.size(); j++) {
                    CategoryData categoryData = getSellerInstancesResponse.instanceData.get(i).category_data.get(j);
                    categoryData.instance_boxid = getSellerInstancesResponse.instanceData.get(i).instance_boxid;
                    categoryData.instance_title = getSellerInstancesResponse.instanceData.get(i).instance_title;
                    categoryDataArrayList.add(categoryData);
                }
            }
            CategoryAddapter categoryAddapter = new CategoryAddapter(categoryDataArrayList, getContext());
            instancelist.setLayoutManager(new LinearLayoutManager(getContext()));
            instancelist.setAdapter(categoryAddapter);
        }
    }
}
