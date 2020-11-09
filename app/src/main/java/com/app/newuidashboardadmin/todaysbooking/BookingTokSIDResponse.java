package com.app.newuidashboardadmin.todaysbooking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingTokSIDResponse {
    @SerializedName("TokBoxSID")
    @Expose
    public String TokBoxSID;

    @SerializedName("TokBoxTokenID")
    @Expose
    public String TokBoxTokenID;

}
