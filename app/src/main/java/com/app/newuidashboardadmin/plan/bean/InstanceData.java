package com.app.newuidashboardadmin.plan.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class InstanceData {
    @SerializedName("instance_boxid")
    @Expose public String instance_boxid;

    @SerializedName("instance_title")
    @Expose public String instance_title;


    @SerializedName("category_data")
    @Expose public ArrayList<CategoryData> category_data;

}
