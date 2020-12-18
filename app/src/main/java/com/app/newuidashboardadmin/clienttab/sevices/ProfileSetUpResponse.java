package com.app.newuidashboardadmin.clienttab.sevices;

import com.google.gson.annotations.SerializedName;

public class ProfileSetUpResponse
{
    @SerializedName("msg")
    public String msg;
    @SerializedName("status")
    public int status;
    @SerializedName("mewardid")
    public String mewardid = "NA";
    @SerializedName("tokenkey")
    public String tokenkey = "NA";
}
