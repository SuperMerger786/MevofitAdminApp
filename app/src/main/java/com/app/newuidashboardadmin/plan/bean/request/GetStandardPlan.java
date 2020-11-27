package com.app.newuidashboardadmin.plan.bean.request;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.megogrid.megoauth.AuthorisedPreference;

public class GetStandardPlan {

    @SerializedName("action")
    @Expose public String action = "GetStandardPlan";

    @SerializedName("seller_uid")
    @Expose public String seller_uid;

    @SerializedName("mewardid")
    @Expose public String mewardid;

    @SerializedName("tokenkey")
    @Expose public String tokenkey;

    public GetStandardPlan(Context context) {
        AuthorisedPreference authorisedPreference = new AuthorisedPreference(context);
//        this.mewardid = authorisedPreference.getMewardId();
//        this.tokenkey = authorisedPreference.getTokenKey();
        mewardid ="74PFT15YQ1602148478";
        tokenkey ="Y5OVS2AC81602588317_d3ec1951-abcf-4354-9c90-5d776e1d1126_ShOZpXKHR_bpSa25QWk";
        this.seller_uid = authorisedPreference.getString(AuthorisedPreference.APP_SELLER_ID);
    }
}

//{
//        "action": "GetStandardPlan",
//        "seller_uid": "740bb9c3-17ff-4ef8-8922-9de82a9a2471",
//        "mewardid": "G8TFQ6MU01585570555",
//        "tokenkey": "VUHQ3ELWZ1585572379_d3ec1951-abcf-4354-9c90-5d776e1d1126_ShOZpXKHR_bpSa25QWk",
//        "timestamp": "1602072801266",
//        "encryption_status": "0"
//        }