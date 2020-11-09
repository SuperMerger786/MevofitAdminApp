
package com.app.newuidashboardadmin.newadmin;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpcomingBookings {

    @SerializedName("bookedList")
    @Expose
    private List<BookedList> bookedList = null;

    public List<BookedList> getBookedList() {
        return bookedList;
    }

    public void setBookedList(List<BookedList> bookedList) {
        this.bookedList = bookedList;
    }

}
