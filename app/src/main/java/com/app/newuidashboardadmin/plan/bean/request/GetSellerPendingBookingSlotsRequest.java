package com.app.newuidashboardadmin.plan.bean.request;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.megogrid.megoauth.AuthorisedPreference;

public class GetSellerPendingBookingSlotsRequest {

    @SerializedName("action")
    @Expose
    public String action = "GetPendingBookings";

    @SerializedName("filterDate")
    @Expose
    public String filterDate;

    @SerializedName("booking_version")
    @Expose
    public String booking_version = "4";

    @SerializedName("isadmin")
    @Expose
    public String isadmin = "1";

    @SerializedName("mewardid")
    @Expose
    public String mewardid;

    @SerializedName("tokenkey")
    @Expose
    public String tokenkey;

//    @SerializedName("paginationID")
//    @Expose
//    public String paginationID;

//    @SerializedName("userSearchKeyword")
//    @Expose
//    public String name;

    @SerializedName("seller_uid")
    @Expose
    public String seller_uid;

    @SerializedName("has_seller")
    @Expose
    public boolean has_seller = true;

    public GetSellerPendingBookingSlotsRequest(String type,Context context, String filterDate) {
        AuthorisedPreference authorisedPreference = new AuthorisedPreference(context);
        this.filterDate = filterDate;

        this.mewardid = authorisedPreference.getMewardId();
        this.tokenkey = authorisedPreference.getTokenKey();
//        mewardid ="74PFT15YQ1602148478";
//        tokenkey ="Y5OVS2AC81602588317_d3ec1951-abcf-4354-9c90-5d776e1d1126_ShOZpXKHR_bpSa25QWk";
        this.seller_uid = authorisedPreference.getString(AuthorisedPreference.APP_SELLER_ID);
    }

}
