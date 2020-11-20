package com.app.newuidashboardadmin.plan.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SectionData {
    @SerializedName("slot_title")
    @Expose public String slot_title;

    @SerializedName("slot_title_total_slot")
    @Expose public String slot_title_total_slot;

    @SerializedName("slot_title_booked_slot")
    @Expose public String slot_title_booked_slot;

    @SerializedName("slot_data")
    @Expose public ArrayList <SlotData> slotDataArrayList;
}
