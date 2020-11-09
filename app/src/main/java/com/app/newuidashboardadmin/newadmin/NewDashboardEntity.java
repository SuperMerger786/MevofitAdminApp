
package com.app.newuidashboardadmin.newadmin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewDashboardEntity {

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("fn_msg")
    @Expose
    private String fnMsg;
    @SerializedName("u_name")
    @Expose
    private String uName;
    @SerializedName("u_email")
    @Expose
    private String uEmail;
    @SerializedName("server_time")
    @Expose
    private String serverTime;
    @SerializedName("user_time")
    @Expose
    private String userTime;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFnMsg() {
        return fnMsg;
    }

    public void setFnMsg(String fnMsg) {
        this.fnMsg = fnMsg;
    }

    public String getUName() {
        return uName;
    }

    public void setUName(String uName) {
        this.uName = uName;
    }

    public String getUEmail() {
        return uEmail;
    }

    public void setUEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public String getUserTime() {
        return userTime;
    }

    public void setUserTime(String userTime) {
        this.userTime = userTime;
    }

}
