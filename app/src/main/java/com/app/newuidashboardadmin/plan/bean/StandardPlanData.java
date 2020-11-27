package com.app.newuidashboardadmin.plan.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StandardPlanData {

    @SerializedName("plan_title")
    @Expose public String plan_title;

    @SerializedName("no_of_session")
    @Expose public String no_of_session;

    @SerializedName("discounted_price")
    @Expose public String discounted_price;

    @SerializedName("instance_currency")
    @Expose public String instance_currency;

    @SerializedName("instance_currency_symbol")
    @Expose public String instance_currency_symbol;

}
