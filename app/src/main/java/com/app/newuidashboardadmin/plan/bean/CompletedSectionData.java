package com.app.newuidashboardadmin.plan.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CompletedSectionData {
    @SerializedName("slot_title")
    @Expose public String slot_title;
    @SerializedName("slot_data")
    @Expose public ArrayList <PendingListData> slotDataArrayList;
}
