package com.app.newuidashboardadmin.plan.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetSellerInstancesResponse {
@SerializedName("data")
    @Expose public ArrayList<InstanceData> instanceData;

}
