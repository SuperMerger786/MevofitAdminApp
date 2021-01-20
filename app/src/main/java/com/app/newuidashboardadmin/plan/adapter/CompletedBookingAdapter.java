package com.app.newuidashboardadmin.plan.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.app.newuidashboardadmin.R;
import com.app.newuidashboardadmin.plan.bean.PendingListData;
import com.app.newuidashboardadmin.plan.bean.SectionData;
import com.megogrid.megoauth.AuthorisedPreference;
import com.migital.digiproducthelper.MevoSellerDetailsActivity;
import com.migital.digiproducthelper.util.BookingSlotList;
import com.migital.digiproducthelper.util.DigiConfiguration;
import com.migital.digiproducthelper.util.DigiHelperPreference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class CompletedBookingAdapter extends RecyclerView.Adapter<CompletedBookingViewHolder> {
    ArrayList<PendingListData> arrayList;
    Context context;
    String type;

    public CompletedBookingAdapter(ArrayList<PendingListData> arrayList, Context context, String type) {
        this.arrayList = arrayList;
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public CompletedBookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = null;
        if (type.equalsIgnoreCase("completed")) {
            view = inflater.inflate(R.layout.completed_booking_history_item, parent, false);
        } else if (type.equalsIgnoreCase("cancelled")) {
            view = inflater.inflate(R.layout.cancelled_booking_history_item, parent, false);

        }

        return new CompletedBookingViewHolder(view, type);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedBookingViewHolder holder, int position) {
        PendingListData sectionData = arrayList.get(position);
       if(type.equalsIgnoreCase("cancelled")){
            holder.menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonShowCanceledUimenu(holder.menu,context,sectionData);
                }
            });
        }else if(type.equalsIgnoreCase("completed")){
            holder.menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonShowCompletedUimenu(holder.menu,context,sectionData);
                }
            });

        }
        holder.name.setText(sectionData.user_name);
        holder.age_gender.setText(sectionData.user_gender + " , " + sectionData.user_age + " yrs.");
        holder.city_county.setText(sectionData.user_city + " | " + sectionData.user_country);
        if (sectionData.last_session_slot_date.equalsIgnoreCase("NA")) {
            holder.last_session_type.setText("NEW CLIENT");
            holder.last_session_time.setText("");
        } else {
            holder.last_session_type.setText("LAST SESSION " + sectionData.last_plan_session_title.replace("Session", ""));
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format2 = new SimpleDateFormat("dd MMM");
            try {
                Date date = format1.parse(sectionData.last_session_slot_date);
                System.out.println(format2.format(date));

                SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
                SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
                SimpleDateFormat _12HourSDFWA = new SimpleDateFormat("hh:mm");
                Date starttime = _24HourSDF.parse(sectionData.last_session_start_time);
                Date endtime = _24HourSDF.parse(sectionData.last_session_end_time);
                System.out.println(_12HourSDF.format(sectionData.last_session_start_time));

                holder.last_session_time.setText(format2.format(date) + "  |  " + _12HourSDFWA.format(starttime) + " - " + _12HourSDF.format(endtime));


//            holder.last_session_time.setText(format2.format(date) + " | " + sectionData.last_session_start_time + " - " + sectionData.last_session_end_time);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        if (arrayList != null) {
            return arrayList.size();
        } else {
            return 0;
        }
    }
    public void onButtonShowCompletedUimenu(View view, Context context, PendingListData sectionData) {

        // inflate the layout of the popup window
        final LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.completed_list_basic_spinner,
                null);
        final PopupWindow pw = new PopupWindow(layout, ViewPager.LayoutParams.MATCH_PARENT,
                ViewPager.LayoutParams.WRAP_CONTENT, true);
        pw.setOutsideTouchable(true);
        pw.setFocusable(true);
        pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pw.showAsDropDown(view);
        TextView reshedule = layout.findViewById(com.migital.digiproducthelper.R.id.reshedule);
        TextView cancel = layout.findViewById(com.migital.digiproducthelper.R.id.cancel);
        reshedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pw.dismiss();
                DigiHelperPreference digiHelperPreference =  DigiHelperPreference.getInstance(context);
                digiHelperPreference.setBoolean(DigiHelperPreference.ISADMIN,true);
                digiHelperPreference.setString(DigiHelperPreference.TokenKey,sectionData.user_tk);
                digiHelperPreference.setString(DigiHelperPreference.MewardID,sectionData.user_meward_id);

                Intent intent = new Intent(context, MevoSellerDetailsActivity.class);
                intent.putExtra("instance_id",sectionData.instance_bid);
                intent.putExtra("sellerid",new AuthorisedPreference(context).getString(AuthorisedPreference.APP_SELLER_ID));
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("datas", sellerDetailData);
//                    intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pw.dismiss();
            }
        });


    }

    public void onButtonShowCanceledUimenu(View view, Context context, PendingListData sectionData) {

        // inflate the layout of the popup window
        final LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.cancel_list_popup,
                null);
        final PopupWindow pw = new PopupWindow(layout, ViewPager.LayoutParams.MATCH_PARENT,
                ViewPager.LayoutParams.WRAP_CONTENT, true);
        pw.setOutsideTouchable(true);
        pw.setFocusable(true);
        pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pw.showAsDropDown(view);
        TextView reshedule = layout.findViewById(com.migital.digiproducthelper.R.id.reshedule);


        reshedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pw.dismiss();
                DigiHelperPreference digiHelperPreference =  DigiHelperPreference.getInstance(context);
                digiHelperPreference.setBoolean(DigiHelperPreference.ISADMIN,true);
                digiHelperPreference.setString(DigiHelperPreference.TokenKey,sectionData.user_tk);
                digiHelperPreference.setString(DigiHelperPreference.MewardID,sectionData.user_meward_id);

                Intent intent = new Intent(context, MevoSellerDetailsActivity.class);
                intent.putExtra("instance_id",sectionData.instance_bid);
                intent.putExtra("sellerid", new AuthorisedPreference(context).getString(AuthorisedPreference.APP_SELLER_ID));
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("datas", sellerDetailData);
//                    intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });



    }

}

class CompletedBookingViewHolder extends RecyclerView.ViewHolder {
    TextView name, age_gender, sessiontype, city_county, last_session_type, last_session_time, price;
    ImageView menu;

    public CompletedBookingViewHolder(@NonNull View itemView, String type) {
        super(itemView);
        if (type.equalsIgnoreCase("completed")) {
            menu = itemView.findViewById(R.id.menu);
            name = itemView.findViewById(R.id.name);
            age_gender = itemView.findViewById(R.id.age_gender);
            sessiontype = itemView.findViewById(R.id.sessiontype);
            city_county = itemView.findViewById(R.id.city_county);
            last_session_type = itemView.findViewById(R.id.last_session_type);
            last_session_time = itemView.findViewById(R.id.last_session_time);
        } else if (type.equalsIgnoreCase("cancelled")) {
            name = itemView.findViewById(R.id.name);
            menu = itemView.findViewById(R.id.menu);
            age_gender = itemView.findViewById(R.id.age_gender);
            sessiontype = itemView.findViewById(R.id.sessiontype);
            city_county = itemView.findViewById(R.id.city_county);
            last_session_type = itemView.findViewById(R.id.last_session_type);
            last_session_time = itemView.findViewById(R.id.last_session_time);
        }
    }
}
