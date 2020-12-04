package com.app.newuidashboardadmin.plan.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryData {

    @SerializedName("isSellerSlotConfigured")
    @Expose public String isSellerSlotConfigured;

    @SerializedName("category_title")
    @Expose public String category_title;

    @SerializedName("category_boxid")
    @Expose public String category_boxid;

    @SerializedName("instance_boxid")
    @Expose public String instance_boxid;

    @SerializedName("instance_title")
    @Expose public String instance_title;

    @SerializedName("catImageUrl")
    @Expose public String catImageUrl;



}
