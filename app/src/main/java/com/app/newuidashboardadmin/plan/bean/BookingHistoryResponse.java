package com.app.newuidashboardadmin.plan.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BookingHistoryResponse {
    @SerializedName("data")
    @Expose public ArrayList<SectionData> sectionDataArrayList;
}
