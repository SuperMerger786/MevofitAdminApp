package com.app.newuidashboardadmin.plan.bean.request;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.megogrid.megoauth.AuthorisedPreference;

public class GetSellerBookingSlotsRequest{

    @SerializedName("action")
    @Expose public String action = "GetSellerBookingSlots";

    @SerializedName("slot_date")
    @Expose public String slot_date;

    @SerializedName("booking_version")
    @Expose public String booking_version = "4";

    @SerializedName("isadmin")
    @Expose public String isadmin = "1";

    @SerializedName("mewardid")
    @Expose public String mewardid;

    @SerializedName("tokenkey")
    @Expose public String tokenkey;

    @SerializedName("paginationID")
    @Expose
    public String paginationID;

    @SerializedName("userSearchKeyword")
    @Expose
    public String name;

    public GetSellerBookingSlotsRequest(String action, Context context) {
        this.action = action;
        AuthorisedPreference authorisedPreference=new AuthorisedPreference(context);
        this.mewardid = authorisedPreference.getMewardId();
        this.tokenkey = authorisedPreference.getTokenKey();
    }

    @SerializedName("seller_uid")
    @Expose public String seller_uid;

    @SerializedName("has_seller")
    @Expose public boolean has_seller = true;

    public GetSellerBookingSlotsRequest(Context context,String slot_date) {
        AuthorisedPreference authorisedPreference = new AuthorisedPreference(context);
        this.slot_date = slot_date;
        this.mewardid = authorisedPreference.getMewardId();
        this.tokenkey = authorisedPreference.getTokenKey();
//        mewardid ="74PFT15YQ1602148478";
//        tokenkey ="Y5OVS2AC81602588317_d3ec1951-abcf-4354-9c90-5d776e1d1126_ShOZpXKHR_bpSa25QWk";
        this.seller_uid = authorisedPreference.getString(AuthorisedPreference.APP_SELLER_ID);
    }
    public void setPaginationID(String paginationID) {
        this.paginationID = paginationID;
    }

}
