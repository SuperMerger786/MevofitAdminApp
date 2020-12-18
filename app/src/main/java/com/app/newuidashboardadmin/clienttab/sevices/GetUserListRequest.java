package com.app.newuidashboardadmin.clienttab.sevices;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.megogrid.megoauth.AuthorisedPreference;

/**
 * Created by test on 2/11/18.
 */

public class GetUserListRequest {

    @SerializedName("action")
    @Expose
    public String action;

    @SerializedName("mewardid")
    @Expose
    public String mewardid;

    @SerializedName("tokenkey")
    @Expose
    public String tokenkey;


    @SerializedName("isadmin")
    @Expose
    public String isadmin;


    @SerializedName("booking_version")
    @Expose
    public String booking_version="4";

    public void setPaginationID(String paginationID)
    {
        this.paginationID = paginationID;
    }

    @SerializedName("paginationID")
    @Expose
    public String paginationID;

    @SerializedName("name")
    @Expose
    public String keyword;



    AuthorisedPreference authorisedPreference;


    public GetUserListRequest(String action,Context context)
    {
        authorisedPreference = new AuthorisedPreference(context);
        this.action= action;
        isadmin="1";
        this.mewardid = authorisedPreference.getMewardId();
        this.tokenkey = authorisedPreference.getTokenKey();

    }




}
