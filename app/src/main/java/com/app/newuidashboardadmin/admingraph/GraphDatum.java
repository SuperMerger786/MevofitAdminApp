
package com.app.newuidashboardadmin.admingraph;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GraphDatum {

    @SerializedName("totalEarnings")
    @Expose
    private String totalEarnings;
    @SerializedName("totalBookings")
    @Expose
    private String totalBookings;
    @SerializedName("bookingMonth")
    @Expose
    private String bookingMonth;

    public String getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(String totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public String getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(String totalBookings) {
        this.totalBookings = totalBookings;
    }

    public String getBookingMonth() {
        return bookingMonth;
    }

    public void setBookingMonth(String bookingMonth) {
        this.bookingMonth = bookingMonth;
    }

}
