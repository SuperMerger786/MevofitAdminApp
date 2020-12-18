package com.app.newuidashboardadmin.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.newuidashboardadmin.AdminDashBoardNewFragment;
import com.app.newuidashboardadmin.CircularImageView;
import com.app.newuidashboardadmin.MyLogger;
import com.app.newuidashboardadmin.R;
import com.app.newuidashboardadmin.newadmin.TodayBooking;
import com.app.newuidashboardadmin.plan.UserDetailsActivity;
import com.app.newuidashboardadmin.plan.bean.SlotData;
import com.bumptech.glide.Glide;
import com.migital.digiproducthelper.MevoSellerDetailsActivity;
import com.migital.digiproducthelper.util.DigiHelperPreference;

import java.util.ArrayList;

public class BookingAdapterRecyclerView extends RecyclerView.Adapter<BookingAdapterRecyclerView.BookingLwHolder> {
    ArrayList<TodayBooking> todaylist;
    Context mContext;



    public BookingAdapterRecyclerView(ArrayList<TodayBooking> todaylist, Context context) {
        this.todaylist = todaylist;
        this.mContext = context;
    }

    @NonNull
    @Override
    public BookingLwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.admin_todaysbooking_status, parent, false);
        return new BookingLwHolder(view);
    }
    TodayBooking todaylistnew;
    @Override
    public void onBindViewHolder(@NonNull BookingLwHolder holder, int position) {

        String name1 = todaylist.get(position).getCustomername();
        String name2 = name1.replace("#NA", " ");
        holder.name.setText(name2);
        holder.tv_male.setText(todaylist.get(position).getCustomerAge() + "," + todaylist.get(position).getCustomerGender());
        MyLogger.println("image>>>>>" + todaylist.get(position).getCustomerProfilepic());
        Glide.with(mContext)
                .load(todaylist.get(position).getCustomerProfilepic())
                .error(Glide.with(holder.imgview).load(R.drawable.profile))
                .into(holder.imgview);
        holder.start_endtime.setText(todaylist.get(position).getStartTime() + "-" + todaylist.get(position).getEndTime());
        if (todaylist.get(position).getCallStatus().equalsIgnoreCase("Completed")) {
            holder.btn_starte.setText("View");
            holder.btn_starte.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            holder.btn_starte.setBackground(mContext.getResources().getDrawable(R.drawable.newui_btn_blue_outline));
        } else {
            holder.btn_starte.setText("start");
            holder.btn_starte.setTextColor(mContext.getResources().getColor(R.color.deep_white));
            holder.btn_starte.setBackground(mContext.getResources().getDrawable(R.drawable.background_start));
        }
        if (todaylist.get(position).getPlan_session_type().equalsIgnoreCase("group_session")) {
            holder.tv_session.setText("Group");
            holder.tv_session.setTextColor(mContext.getResources().getColor(R.color.group_color));
        } else {
            holder.tv_session.setText("Personal");
            holder.tv_session.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        }
        if (holder.btn_starte.getText().toString().equalsIgnoreCase("start")) {
            holder.btn_starte.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    todaylistnew = todaylist.get(position);
//                   makeSessionRequest(todaylist.get(position).getItemBoxId(), todaylist.get(position).getBookingId(), todaylist.get(position).getStartTime());
                }
            });
        } else {
            holder.btn_starte.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    todaylistnew = todaylist.get(position);
                    SlotData slotData = new SlotData();
                    slotData.user_name = name2;
                    slotData.user_profilepic = todaylistnew.getCustomerProfilepic();
                    slotData.user_gender = todaylistnew.getCustomerGender();
                    slotData.user_age = todaylistnew.getCustomerAge();
                    slotData.user_city = "NA";
                    slotData.user_country = "NA";

                    Intent intent = new Intent(mContext, UserDetailsActivity.class);
                    intent.putExtra("data", slotData);
                    mContext.startActivity(intent);
                    // makeSessionRequest(todaylist.get(position).getItemBoxId(), todaylist.get(position).getBookingId(), todaylist.get(position).getStartTime());
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return todaylist.size();
    }
    class BookingLwHolder extends RecyclerView.ViewHolder {
        TextView name, tv_male, tv_session, start_endtime, btn_starte;
        CircularImageView imgview;


        public BookingLwHolder(@NonNull View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tv_name);
            tv_male = (TextView) view.findViewById(R.id.tv_male);
            tv_session = (TextView) view.findViewById(R.id.tv_session);
            imgview = (CircularImageView) view.findViewById(R.id.iv_profile_dummy);
            start_endtime = (TextView) view.findViewById(R.id.start_endtime);
            btn_starte = (TextView) view.findViewById(R.id.btn_starte);
        }
    }
}

