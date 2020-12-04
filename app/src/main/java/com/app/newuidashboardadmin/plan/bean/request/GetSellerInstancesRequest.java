package com.app.newuidashboardadmin.plan.bean.request;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.megogrid.megoauth.AuthorisedPreference;

public class GetSellerInstancesRequest {
    @SerializedName("action")
    @Expose public String action = "GetSellerInstances";


    @SerializedName("os")
    @Expose public String os = "Android";

    @SerializedName("seller_uid")
    @Expose public String seller_uid;

    @SerializedName("mewardid")
    @Expose public String mewardid;

    @SerializedName("storeid")
    @Expose public String storeid;

    @SerializedName("tokenkey")
    @Expose public String tokenkey;

    public GetSellerInstancesRequest(Context context, String storeid) {
        this.storeid = storeid;
        AuthorisedPreference authorisedPreference = new AuthorisedPreference(context);
//        this.mewardid = authorisedPreference.getMewardId();
//        this.tokenkey = authorisedPreference.getTokenKey();
        mewardid ="74PFT15YQ1602148478";
        tokenkey ="Y5OVS2AC81602588317_d3ec1951-abcf-4354-9c90-5d776e1d1126_ShOZpXKHR_bpSa25QWk";
        this.seller_uid = authorisedPreference.getString(AuthorisedPreference.APP_SELLER_ID);
    }
}
