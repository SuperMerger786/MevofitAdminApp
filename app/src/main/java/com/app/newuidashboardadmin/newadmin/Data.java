
package com.app.newuidashboardadmin.newadmin;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("BookingPerformance")
    @Expose
    private BookingPerformance bookingPerformance;
    @SerializedName("UserNotification")
    @Expose
    public UserNotification userNotification;
    @SerializedName("TodayBookings")
    @Expose
    private List<TodayBooking> todayBookings = null;
    @SerializedName("UpcomingBookings")
    @Expose
    private UpcomingBookings upcomingBookings;
    public BookingPerformance getBookingPerformance() {
        return bookingPerformance;
    }
    public void setBookingPerformance(BookingPerformance bookingPerformance) {
        this.bookingPerformance = bookingPerformance;
    }

    public List<TodayBooking> getTodayBookings() {
        return todayBookings;
    }

    public void setTodayBookings(List<TodayBooking> todayBookings) {
        this.todayBookings = todayBookings;
    }

    public UpcomingBookings getUpcomingBookings() {
        return upcomingBookings;
    }

    public void setUpcomingBookings(UpcomingBookings upcomingBookings) {
        this.upcomingBookings = upcomingBookings;
    }


}
