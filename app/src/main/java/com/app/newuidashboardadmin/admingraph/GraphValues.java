
package com.app.newuidashboardadmin.admingraph;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GraphValues {

    @SerializedName("data")
    @Expose
    private GraphData data;
    @SerializedName("status_code")
    @Expose
    private Integer statusCode;
    @SerializedName("server_time")
    @Expose
    private String serverTime;
    @SerializedName("user_time")
    @Expose
    private String userTime;

    public GraphData getData() {
        return data;
    }

    public void setData(GraphData data) {
        this.data = data;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
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
