package com.app.newuidashboardadmin.clienttab.sevices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetUserListResponse {
    @SerializedName("bookedList")
    @Expose
    public ArrayList<ClientList> data;

}
