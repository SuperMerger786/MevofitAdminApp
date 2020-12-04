package com.app.newuidashboardadmin.plan.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SlotData implements Serializable {

    @SerializedName("store_id")
    @Expose public String store_id;

    @SerializedName("from_time")
    @Expose public String from_time;

    @SerializedName("to_time")
    @Expose public String to_time;

    @SerializedName("plan_session_type")
    @Expose public String plan_session_type;

    @SerializedName("user_booked_slot_status")
    @Expose public String user_booked_slot_status;

    @SerializedName("bookedBoxId")
    @Expose public String bookedBoxId;

    @SerializedName("is_slot_booked")
    @Expose public int is_slot_booked;

    @SerializedName("user_name")
    @Expose public String user_name;

    @SerializedName("user_tk")
    @Expose public String user_tk;

    @SerializedName("user_email")
    @Expose public String user_email;

    @SerializedName("user_profilepic")
    @Expose public String user_profilepic;

    @SerializedName("user_gender")
    @Expose public String user_gender;

    @SerializedName("user_city")
    @Expose public String user_city;

    @SerializedName("user_country")
    @Expose public String user_country;

    @SerializedName("user_age")
    @Expose public String user_age;

}
