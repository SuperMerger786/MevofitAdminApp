package com.app.newuidashboardadmin.plan.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SessionList {

    @SerializedName("plan_session_type")
    @Expose public String plan_session_type;

    @SerializedName("StandardPlanData")
    @Expose public ArrayList<StandardPlanData> StandardPlanData;


}
