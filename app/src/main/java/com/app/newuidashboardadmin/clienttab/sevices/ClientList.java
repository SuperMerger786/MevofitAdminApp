package com.app.newuidashboardadmin.clienttab.sevices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClientList implements Serializable {

        @SerializedName("store_id")
        @Expose
        public String store_id;

        @SerializedName("from_time")
        @Expose
        public String from_time;

        @SerializedName("to_time")
        @Expose
        public String to_time;

        @SerializedName("last_plan_session_type")
        @Expose
        public String last_plan_session_title;

        @SerializedName("is_last_session")
        @Expose
        public boolean is_last_session;

        @SerializedName("last_session_slot_date")
        @Expose
        public String last_session_slot_date;

        @SerializedName("last_session_start_time")
        @Expose
        public String last_session_start_time;

        @SerializedName("last_session_end_time")
        @Expose
        public String last_session_end_time;

        @SerializedName("bookedBoxId")
        @Expose
        public String bookedBoxId;

        @SerializedName("user_name")
        @Expose
        public String user_name;

        @SerializedName("user_tk")
        @Expose
        public String user_tk;

        @SerializedName("user_meward_id")
        @Expose
        public String user_meward_id;

        @SerializedName("user_email")
        @Expose
        public String user_email;

        @SerializedName("user_profilepic")
        @Expose
        public String user_profilepic;

        @SerializedName("user_gender")
        @Expose
        public String user_gender;

        @SerializedName("user_city")
        @Expose
        public String user_city;

        @SerializedName("user_country")
        @Expose
        public String user_country;

        @SerializedName("user_age")
        @Expose
        public String user_age;

        @SerializedName("reg_user_type")
        @Expose
        public String reg_user_type;

        public String currency_symbol, variant_discounted_price;

}
