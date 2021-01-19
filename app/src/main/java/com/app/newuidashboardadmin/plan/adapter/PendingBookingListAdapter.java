package com.app.newuidashboardadmin.plan.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.newuidashboardadmin.R;
import com.app.newuidashboardadmin.plan.UserDetailsActivity;
import com.app.newuidashboardadmin.plan.bean.PendingListData;
import com.app.newuidashboardadmin.plan.bean.SlotData;
import com.app.newuidashboardadmin.plan.callback.ButtonClickCallback;
import com.bumptech.glide.Glide;
import com.migital.digiproducthelper.MevoSellerDetailsActivity;
import com.migital.digiproducthelper.util.DigiHelperPreference;
import com.migital.digiproducthelper.util.Utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class PendingBookingListAdapter extends RecyclerView.Adapter<PendingBookingListViewHolder> {

    ArrayList<PendingListData> slotDataArrayList;
    Context context;
    DigiHelperPreference digiHelperPreference;
    ButtonClickCallback buttonClickCallback;
    String type = "pending";
    private final String[] backgroundColors = {
            "#FFA7C9",
            "#FFC59F",
            "#FFFCB9",
            "#A2FC9A"};

    public PendingBookingListAdapter(ArrayList<PendingListData> pendingListData, Context context, ButtonClickCallback buttonClickCallback, String type) {
        this.slotDataArrayList = pendingListData;
        this.context = context;
        this.buttonClickCallback = buttonClickCallback;
        this.digiHelperPreference = DigiHelperPreference.getInstance(context);
        this.type = type;
    }

    @NonNull
    @Override
    public PendingBookingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        if (type.equalsIgnoreCase("open")) {
            view = inflater.inflate(R.layout.open_booking_history_item, parent, false);

        } else {
            view = inflater.inflate(R.layout.pending_booking_history_item, parent, false);

        }
        return new PendingBookingListViewHolder(view, type);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingBookingListViewHolder holder, int position) {
        if (type.equalsIgnoreCase("open")) {
            holder.name.setText(slotDataArrayList.get(position).user_name);
            holder.age_gender.setText(slotDataArrayList.get(position).user_gender + " , " + slotDataArrayList.get(position).user_age + " yrs.");
            holder.city_county.setText(slotDataArrayList.get(position).user_city + " | " + slotDataArrayList.get(position).user_country);
            String currency = new Utility().getCurrencySymbol(slotDataArrayList.get(position).currency_symbol);
            holder.sessiontype.setText(slotDataArrayList.get(position).shop_plan_session_title.toUpperCase());
            holder.price.setText(currency + "" + slotDataArrayList.get(position).variant_discounted_price);
            if (slotDataArrayList.get(position).last_session_slot_date.equalsIgnoreCase("NA")) {
                holder.lastsession_type.setText("NEW CLIENT");
                holder.last_session_time.setText("");
            } else {
                holder.lastsession_type.setText("LAST SESSION " + slotDataArrayList.get(position).last_plan_session_title.replace("Session", ""));
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat format2 = new SimpleDateFormat("dd MMM");
                try {
                    Date date = format1.parse(slotDataArrayList.get(position).last_session_slot_date);
                    System.out.println(format2.format(date));
                    SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
                    SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
                    SimpleDateFormat _12HourSDFWA = new SimpleDateFormat("hh:mm");
                    Date starttime = _24HourSDF.parse(slotDataArrayList.get(position).last_session_start_time);
                    Date endtime = _24HourSDF.parse(slotDataArrayList.get(position).last_session_end_time);
                    System.out.println(_12HourSDF.format(slotDataArrayList.get(position).last_session_start_time));

                    holder.last_session_time.setText(format2.format(date) + " | " + _12HourSDFWA.format(starttime) + " - " + _12HourSDF.format(endtime));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            holder.menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonShowOpenUimenu(holder.menu, context);
                }
            });
        } else {
            GradientDrawable gradientDrawable = (GradientDrawable) holder.acept_button.getBackground();
            gradientDrawable.setColor(Color.parseColor("#306dd0"));
            holder.sessiontype.setText(slotDataArrayList.get(position).shop_plan_session_title.toUpperCase());
            holder.name.setText(slotDataArrayList.get(position).user_name);
            if (slotDataArrayList.get(position).last_session_slot_date.equalsIgnoreCase("NA")) {
                holder.lastsession_type.setText("NEW CLIENT");
                holder.last_session_time.setText("");
            } else {
                holder.lastsession_type.setText("LAST SESSION " + slotDataArrayList.get(position).last_plan_session_title.replace("Session", ""));
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat format2 = new SimpleDateFormat("dd MMM");
                try {
                    Date date = format1.parse(slotDataArrayList.get(position).last_session_slot_date);
                    System.out.println(format2.format(date));

                    SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
                    SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
                    SimpleDateFormat _12HourSDFWA = new SimpleDateFormat("hh:mm");
                    Date starttime = _24HourSDF.parse(slotDataArrayList.get(position).last_session_start_time);
                    Date endtime = _24HourSDF.parse(slotDataArrayList.get(position).last_session_end_time);
                    System.out.println(_12HourSDF.format(slotDataArrayList.get(position).last_session_start_time));

                    holder.last_session_time.setText(format2.format(date) + " | " + _12HourSDFWA.format(starttime) + " - " + _12HourSDF.format(endtime));


//                holder.last_session_time.setText(format2.format(date) + " | " + slotDataArrayList.get(position).last_session_start_time + " - " + slotDataArrayList.get(position).last_session_end_time);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            String currency = new Utility().getCurrencySymbol(slotDataArrayList.get(position).currency_symbol);
            holder.price.setText(currency + "" + slotDataArrayList.get(position).variant_discounted_price);
            holder.place_city.setText(slotDataArrayList.get(position).user_city + " | " + slotDataArrayList.get(position).user_country);
            holder.gender_age.setText(slotDataArrayList.get(position).user_gender + ", " + slotDataArrayList.get(position).user_age + " yrs.");


            holder.acept_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (buttonClickCallback != null)
                        buttonClickCallback.OnDateSelect(slotDataArrayList.get(position), false);
                }
            });
            holder.cancel_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (buttonClickCallback != null)
                        buttonClickCallback.OnDateSelect(slotDataArrayList.get(position), true);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return slotDataArrayList.size();
    }

    public void onButtonShowOpenUimenu(View view, Context context) {

        // inflate the layout of the popup window
        final LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.open_list_basic_spinner,
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

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pw.dismiss();
            }
        });


    }

}

class PendingBookingListViewHolder extends RecyclerView.ViewHolder {
    TextView sessiontype, lastsession_type, name, gender_age, place_city, price, last_session_time, age_gender, city_county;
    Button acept_button, cancel_button;
    ImageView menu;

    public PendingBookingListViewHolder(@NonNull View itemView, String type) {
        super(itemView);
        if (type.equalsIgnoreCase("open")) {
            menu = itemView.findViewById(R.id.menu);
            price = itemView.findViewById(R.id.price);
            name = itemView.findViewById(R.id.name);
            age_gender = itemView.findViewById(R.id.age_gender);
            sessiontype = itemView.findViewById(R.id.sessiontype);
            city_county = itemView.findViewById(R.id.city_county);
            lastsession_type = itemView.findViewById(R.id.last_session_type);
            last_session_time = itemView.findViewById(R.id.last_session_time);
        } else {

            sessiontype = itemView.findViewById(R.id.session_type);
            name = itemView.findViewById(R.id.name);
            gender_age = itemView.findViewById(R.id.gender_age);
            place_city = itemView.findViewById(R.id.place_city);
            cancel_button = itemView.findViewById(R.id.cancel_button);
            price = itemView.findViewById(R.id.price);
            acept_button = itemView.findViewById(R.id.acept_button);
            lastsession_type = itemView.findViewById(R.id.lastsession_type);
            last_session_time = itemView.findViewById(R.id.last_session_time);
        }
    }
}