package com.app.newuidashboardadmin.plan.bean.request;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.megogrid.megoauth.AuthorisedPreference;

public class ChangeBookingStatusRequest {

    @SerializedName("action")
    @Expose
    String action = "ChangeBookingStatus";

    @SerializedName("booking_status")
    @Expose
    String booking_status;

    @SerializedName("status_changed_by")
    @Expose
    String status_changed_by = "AdminAPP";

    @SerializedName("BookingId")
    @Expose
    String BookingId;

    @SerializedName("booking_version")
    @Expose
    String booking_version = "4";

    @SerializedName("mewardid")
    @Expose
    String mewardid;

    @SerializedName("seller_uid")
    @Expose
    String seller_uid;

    @SerializedName("tokenkey")
    @Expose
    String tokenkey;

    public ChangeBookingStatusRequest(Context context, String booking_status, String bookingId) {
        AuthorisedPreference authorisedPreference = new AuthorisedPreference(context);
        this.booking_status = booking_status;
        this.status_changed_by = status_changed_by;
        BookingId = bookingId;
        this.mewardid = authorisedPreference.getMewardId();
        this.seller_uid = authorisedPreference.getString(AuthorisedPreference.APP_SELLER_ID);
        this.tokenkey = authorisedPreference.getTokenKey();
    }
}
