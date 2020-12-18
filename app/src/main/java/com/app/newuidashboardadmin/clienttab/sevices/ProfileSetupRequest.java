package com.app.newuidashboardadmin.clienttab.sevices;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.megogrid.megoauth.AuthorisedPreference;
import com.megogrid.megouserutil.SharedEncryption;

import java.security.GeneralSecurityException;

public final class ProfileSetupRequest
{
    @SerializedName("action")
    @Expose
    public String action = "ProfileRegistration";
    @SerializedName("firstname")
    @Expose
    public String firstname = "NA";

    @SerializedName("encript_email")
    @Expose
    public String encript_email;
    @SerializedName("channel")
    @Expose
    public String channel = "Email";

    @SerializedName("lastname")
    @Expose
    public String lastname = "";
    @SerializedName("phoneno")
    @Expose
    public String phoneno = "NA";
    @SerializedName("profilepic")
    @Expose
    public String profilepic = "NA";
    @SerializedName("mewardid")
    @Expose
    public String mewardid = "NA";
    @SerializedName("tokenkey")
    @Expose
    public String tokenkey = "NA";
    @SerializedName("type")
    @Expose
    public String type = "Registration";

    @SerializedName("password")
    @Expose
    public String password = "1234";
    @SerializedName("registrationmode")
    @Expose
    public String registrationmode = "email";
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("touch_login")

    @Expose
    public String touch_login = "0";
    @SerializedName("user_type")
    @Expose
    public String user_type = "user";


    public ProfileSetupRequest(Context context, String email, String firstname, String phoneno) {

        this.mewardid = new AuthorisedPreference(context).getMewardId();
        this.tokenkey = new AuthorisedPreference(context).getTokenKey();
        this.email = email;
        this.firstname = firstname;
        this.phoneno = phoneno;
        try {
            this.encript_email= SharedEncryption.encryptStack("MigRegistrat@234",email);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
}

