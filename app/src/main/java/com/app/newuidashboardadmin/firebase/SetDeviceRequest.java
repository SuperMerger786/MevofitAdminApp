package com.app.newuidashboardadmin.firebase;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.megogrid.megoauth.AuthorisedPreference;

/**
 * Created by test on 1/11/18.
 */

public class SetDeviceRequest {

    @SerializedName("action")
    @Expose
    public String action="SetDevice";


    @SerializedName("device_id")
    @Expose
    public String device_id;


    @SerializedName("mac_id")
    @Expose
    public String mac_id;

    @SerializedName("mewardid")
    @Expose
    public String mewardid;

    @SerializedName("os")
    @Expose
    public String os;

    @SerializedName("tokenkey")
    @Expose
    public String tokenkey;

    @SerializedName("user_appversion")
    @Expose
    public String user_appversion;

    public SetDeviceRequest(Context context, String device_id)
    {
        AuthorisedPreference preference=new AuthorisedPreference(context);
        mewardid=preference.getMewardId();
        mac_id=preference.getString(preference.MAC_ID);
        tokenkey=preference.getTokenKey();
        this.device_id=device_id;
        System.out.println("SetDeviceRequest.SetDeviceRequest device_id ==== "+device_id);
        user_appversion="1.0";
        os="Android";

    }

}
