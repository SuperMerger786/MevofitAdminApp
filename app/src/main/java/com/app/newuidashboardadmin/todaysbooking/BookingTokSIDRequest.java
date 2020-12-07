package com.app.newuidashboardadmin.todaysbooking;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.megogrid.megoauth.AuthorisedPreference;

public class BookingTokSIDRequest {

    @SerializedName("action")
    @Expose
    public String action="BookingTokBoxSID";

    @SerializedName("mewardid")
    @Expose
    public String mewardid;

    @SerializedName("tokenkey")
    @Expose
    public String tokenkey;


    @SerializedName("itemBoxId")
    @Expose
    public String itemBoxId;

    @SerializedName("bookedboxId")
    @Expose
    public String bookedboxId;

    @SerializedName("start_time")
    @Expose
    public String start_time;

    @SerializedName("os")
    @Expose
    public String os = "Android";


    public BookingTokSIDRequest(Context context, String itemBoxId, String bookedboxId, String start_time) {
        AuthorisedPreference authorisedPreference=new AuthorisedPreference(context);
//        jsonObj.put("mewardid", "74PFT15YQ1602148478");
//        jsonObj.put("tokenkey", "Y5OVS2AC81602588317_d3ec1951-abcf-4354-9c90-5d776e1d1126_ShOZpXKHR_bpSa25QWk");
//        mewardid ="74PFT15YQ1602148478";
//        tokenkey ="Y5OVS2AC81602588317_d3ec1951-abcf-4354-9c90-5d776e1d1126_ShOZpXKHR_bpSa25QWk";
        mewardid =authorisedPreference.getMewardId();
        tokenkey=authorisedPreference.getTokenKey();
        this.bookedboxId = bookedboxId;
        this.itemBoxId = itemBoxId;
        this.start_time = start_time;
    }
}
