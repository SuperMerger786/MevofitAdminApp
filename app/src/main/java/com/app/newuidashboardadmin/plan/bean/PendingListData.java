package com.app.newuidashboardadmin.plan.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PendingListData {
    @SerializedName("BookingId")
    @Expose public String BookingId;

    @SerializedName("currency_symbol")
    @Expose public String currency_symbol;

    @SerializedName("variant_discounted_price")
    @Expose public String variant_discounted_price;

    @SerializedName("last_plan_session_type")
    @Expose public String last_plan_session_type;

    @SerializedName("last_session_slot_date")
    @Expose public String last_session_slot_date;

    @SerializedName("last_plan_session_title")
    @Expose public String last_plan_session_title;

    @SerializedName("last_session_start_time")
    @Expose public String last_session_start_time;

    @SerializedName("last_session_end_time")
    @Expose public String last_session_end_time;

    @SerializedName("last_session_user_booked_slot_id")
    @Expose public String last_session_user_booked_slot_id;

    @SerializedName("user_tk")
    @Expose public String user_tk;

    @SerializedName("user_profilepic")
    @Expose public String user_profilepic;

    @SerializedName("user_gender")
    @Expose public String user_gender;

    @SerializedName("user_city")
    @Expose public String user_city;

    @SerializedName("user_country")
    @Expose public String user_country;

    @SerializedName("user_name")
    @Expose public String user_name;

    @SerializedName("user_age")
    @Expose public String user_age;

    @SerializedName("instance_bid")
    @Expose public String instance_bid;

    @SerializedName("category_bid")
    @Expose public String category_bid;

    @SerializedName("user_meward_id")
    @Expose public String user_meward_id;

    @SerializedName("shop_plan_session_title")
    @Expose public String shop_plan_session_title = "";







}
