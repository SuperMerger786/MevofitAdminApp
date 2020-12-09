package com.app.newuidashboardadmin.plan.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.newuidashboardadmin.R;
import com.app.newuidashboardadmin.plan.UserDetailsActivity;
import com.app.newuidashboardadmin.plan.bean.SlotData;
import com.bumptech.glide.Glide;
import com.migital.digiproducthelper.MevoSellerDetailsActivity;
import com.migital.digiproducthelper.SellerDetailsSlotActivity;
import com.migital.digiproducthelper.bean.incomming.SellerDetailData;
import com.migital.digiproducthelper.util.DigiHelperPreference;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class BookingListAdapter extends RecyclerView.Adapter<BookingListViewHolder> {

    ArrayList<SlotData> slotDataArrayList;
    Context context;
    DigiHelperPreference digiHelperPreference;

    private final String[] backgroundColors = {
            "#FFA7C9",
            "#FFC59F",
            "#FFFCB9",
            "#A2FC9A"};

    public BookingListAdapter(ArrayList<SlotData> slotDataArrayList, Context context) {
        this.slotDataArrayList = slotDataArrayList;
        this.context = context;
        this.digiHelperPreference = DigiHelperPreference.getInstance(context);
    }

    @NonNull
    @Override
    public BookingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.booking_history_item, parent, false);
        return new BookingListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingListViewHolder holder, int position) {
        if (slotDataArrayList.get(position).is_slot_booked == 1) {

            holder.name.setText(slotDataArrayList.get(position).user_name);
            holder.subtitel.setText(slotDataArrayList.get(position).user_age + ", " + slotDataArrayList.get(position).user_gender + " | " + slotDataArrayList.get(position).user_city + "," + slotDataArrayList.get(position).user_country);
            Glide.with(context).load(slotDataArrayList.get(position).user_profilepic).into(holder.sellerimage);
            holder.time.setText(slotDataArrayList.get(position).from_time + " - " + slotDataArrayList.get(position).to_time);
            holder.sessiontype.setText(slotDataArrayList.get(position).plan_session_title.toUpperCase());
            holder.bookbutton.setText("VIEW DETAILS");
            holder.bookbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    digiHelperPreference.setBoolean(DigiHelperPreference.ISADMIN,true);
                    digiHelperPreference.setString(DigiHelperPreference.TokenKey,"VUHQ3ELWZ1585572379_d3ec1951-abcf-4354-9c90-5d776e1d1126_ShOZpXKHR_bpSa25QWk");
                    digiHelperPreference.setString(DigiHelperPreference.MewardID,"G8TFQ6MU01585570555");

                    Intent intent = new Intent(context, MevoSellerDetailsActivity.class);
                    intent.putExtra("instance_id","0RSTJNUQS");
                    intent.putExtra("sellerid","740bb9c3-17ff-4ef8-8922-9de82a9a2471");
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("datas", sellerDetailData);
//                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
            GradientDrawable gradientDrawable = (GradientDrawable) holder.bookbutton.getBackground();
            gradientDrawable.setColor(Color.parseColor("#898989"));
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, UserDetailsActivity.class);
                    intent.putExtra("data", slotDataArrayList.get(position));
                    context.startActivity(intent);

                }
            });
        } else {
            holder.name.setText("AVAILABLE");
            holder.subtitel.setVisibility(View.GONE);
            holder.time.setText(slotDataArrayList.get(position).from_time + " - " + slotDataArrayList.get(position).to_time);
            holder.bookbutton.setText("BOOK NOW");
            holder.sessiontype.setText(slotDataArrayList.get(position).plan_session_title.toUpperCase());
            GradientDrawable gradientDrawable = (GradientDrawable) holder.bookbutton.getBackground();
            gradientDrawable.setColor(Color.parseColor("#306dd0"));
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent = new Intent(context, UserDetailsActivity.class);
//                    intent.putExtra("data", slotDataArrayList.get(position));
//                    context.startActivity(intent);
                }
            });
        }

        if(slotDataArrayList.get(position).plan_session_type.equalsIgnoreCase("group_session")){
            holder.sessiontype.setTextColor(Color.parseColor("#B45F05"));
        }else if(slotDataArrayList.get(position).plan_session_type.equalsIgnoreCase("single_session")){
            holder.sessiontype.setTextColor(Color.parseColor("#0B5292"));
        }else {
            holder.sessiontype.setTextColor(Color.parseColor("#0097A8"));

        }

        int index = position % backgroundColors.length;
        GradientDrawable drawable = (GradientDrawable) holder.side_bar.getBackground();
        drawable.setColor(Color.parseColor(backgroundColors[index]));

    }


    @Override
    public int getItemCount() {
        return slotDataArrayList.size();
    }
}

class BookingListViewHolder extends RecyclerView.ViewHolder {
    TextView sessiontype, name, subtitel, time;
    ImageView sellerimage;
    CardView cardView;
    Button bookbutton;
    RelativeLayout side_bar;

    public BookingListViewHolder(@NonNull View itemView) {
        super(itemView);
        bookbutton = itemView.findViewById(R.id.bookbutton);
        side_bar = itemView.findViewById(R.id.side_bar);
        sessiontype = itemView.findViewById(R.id.sessiontype);
        name = itemView.findViewById(R.id.name);
        subtitel = itemView.findViewById(R.id.subtitel);
        time = itemView.findViewById(R.id.time);
        sellerimage = itemView.findViewById(R.id.sellerimage);
        cardView = itemView.findViewById(R.id.card);
    }
}