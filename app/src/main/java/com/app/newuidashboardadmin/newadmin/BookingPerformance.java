
package com.app.newuidashboardadmin.newadmin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingPerformance {

    @SerializedName("totalSlotBookings")
    @Expose
    private String totalBookings;
    @SerializedName("totalEarnings")
    @Expose
    private String totalEarnings;
    @SerializedName("currency_symbol")
    @Expose
    private String currencySymbol;
    @SerializedName("average_rating")
    @Expose
    private Integer averageRating;

    public String getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(String totalBookings) {
        this.totalBookings = totalBookings;
    }

    public String getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(String totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public Integer getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Integer averageRating) {
        this.averageRating = averageRating;
    }

}
