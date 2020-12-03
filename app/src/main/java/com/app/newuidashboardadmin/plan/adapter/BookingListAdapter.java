package com.app.newuidashboardadmin.plan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.newuidashboardadmin.R;
import com.app.newuidashboardadmin.plan.UserDetailsActivity;
import com.app.newuidashboardadmin.plan.bean.SlotData;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class BookingListAdapter extends RecyclerView.Adapter<BookingListViewHolder> {

    ArrayList<SlotData> slotDataArrayList;
    Context context;

    public BookingListAdapter(ArrayList<SlotData> slotDataArrayList, Context context) {
        this.slotDataArrayList = slotDataArrayList;
        this.context = context;
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
            holder.sessiontype.setText(slotDataArrayList.get(position).plan_session_type.toUpperCase());
            holder.bookbutton.setText("VIEW DETAILS");
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
            holder.sessiontype.setText(slotDataArrayList.get(position).plan_session_type.toUpperCase());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent = new Intent(context, UserDetailsActivity.class);
//                    intent.putExtra("data", slotDataArrayList.get(position));
//                    context.startActivity(intent);
                }
            });


        }


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

    public BookingListViewHolder(@NonNull View itemView) {
        super(itemView);
        bookbutton = itemView.findViewById(R.id.bookbutton);
        sessiontype = itemView.findViewById(R.id.sessiontype);
        name = itemView.findViewById(R.id.name);
        subtitel = itemView.findViewById(R.id.subtitel);
        time = itemView.findViewById(R.id.time);
        sellerimage = itemView.findViewById(R.id.sellerimage);
        cardView = itemView.findViewById(R.id.card);
    }
}