
package com.app.newuidashboardadmin.newadmin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TodayBooking {


    @SerializedName("user_name")
    @Expose
    private String customername;
    @SerializedName("user_age")
    @Expose
    private String customerAge;
    @SerializedName("user_gender")
    @Expose
    private String customerGender;
    @SerializedName("user_profilepic")
    @Expose
    private String customerProfilepic;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;

/* "BookingId": "EKD7OIN1PL",
         "ItemBoxId": "61GYZDHUC",
         "seller_uid": "740bb9c3-17ff-4ef8-8922-9de82a9a2471"*/

    /*
             "callStatus": "pending",
             "customername": "NA",
             "BookingId": "JI103NVLXA",
             "ItemBoxId": "61GYZDHUC",
             "seller_uid": "740bb9c3-17ff-4ef8-8922-9de82a9a2471"*/
    @SerializedName("callStatus")
    @Expose
    private String callStatus;

    @SerializedName("BookingId")
    @Expose
    private String BookingId;

    @SerializedName("ItemBoxId")
    @Expose
    private String ItemBoxId;

    @SerializedName("seller_uid")
    @Expose
    private String seller_uid;

    public String getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(String callStatus) {
        this.callStatus = callStatus;
    }

    public String getBookingId() {
        return BookingId;
    }

    public void setBookingId(String bookingId) {
        BookingId = bookingId;
    }

    public String getItemBoxId() {
        return ItemBoxId;
    }

    public void setItemBoxId(String itemBoxId) {
        ItemBoxId = itemBoxId;
    }

    public String getSeller_uid() {
        return seller_uid;
    }

    public void setSeller_uid(String seller_uid) {
        this.seller_uid = seller_uid;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getCustomerAge() {
        return customerAge;
    }

    public void setCustomerAge(String customerAge) {
        this.customerAge = customerAge;
    }

    public String getCustomerGender() {
        return customerGender;
    }

    public void setCustomerGender(String customerGender) {
        this.customerGender = customerGender;
    }

    public String getCustomerProfilepic() {
        return customerProfilepic;
    }

    public void setCustomerProfilepic(String customerProfilepic) {
        this.customerProfilepic = customerProfilepic;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
