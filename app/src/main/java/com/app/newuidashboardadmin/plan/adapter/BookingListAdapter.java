package com.app.newuidashboardadmin.plan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.newuidashboardadmin.R;
import com.app.newuidashboardadmin.plan.bean.SlotData;

import java.util.ArrayList;

import androidx.annotation.NonNull;
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
        View view = inflater.inflate(R.layout.booking_history_item,parent,false);
        return new BookingListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }
}

class BookingListViewHolder extends RecyclerView.ViewHolder{

    public BookingListViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}