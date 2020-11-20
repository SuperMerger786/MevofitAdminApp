package com.app.newuidashboardadmin.plan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.newuidashboardadmin.R;
import com.app.newuidashboardadmin.plan.callback.DateChooseCallback;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.recyclerview.widget.RecyclerView;

public class DateSelectorAdapter extends RecyclerView.Adapter<DateSelectorAdapter.DateSelectorViewHolder> {
    DateChooseCallback dateChooseCallback;
//    boolean isClicked;
    Context context;
    int lastselecctedposition = -1;
    ArrayList<Date> slotDateDataArrayList;
    String todaydate;

    public DateSelectorAdapter(Context context, int todayposition,ArrayList<Date> slotDateDataArrayList, DateChooseCallback dateChooseCallback) {
        this.slotDateDataArrayList = slotDateDataArrayList;
        this.dateChooseCallback = dateChooseCallback;
        this.context =  new WeakReference<>(context).get();
//        this.isClicked = isClicked;
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        todaydate = format.format(today);
        lastselecctedposition = todayposition;
        System.out.println("DateSelectorAdapter.DateSelectorAdapter position "+lastselecctedposition);
    }

    @Override
    public DateSelectorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_day_date, parent, false);
        return new DateSelectorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DateSelectorViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        try {
//            String string = slotDateDataArrayList.get(position);
            final Date date = slotDateDataArrayList.get(position);
            System.out.println(date); // Sat Jan 02 00:00:00 GMT 2010
            DateFormat format2 = new SimpleDateFormat("EEEE");
            DateFormat dateformat = new SimpleDateFormat("dd");
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String positiondate = format.format(date);
            String finalDay = format2.format(date);
            System.out.println("BookingDateAdapter.onBindViewHolder day name " + finalDay);
            holder.weekday.setText(finalDay);
            String finaldate = dateformat.format(date);
            holder.date.setText(finaldate);

//            if(positiondate.equalsIgnoreCase(todaydate)){
//                lastselecctedposition = position;
//            }

            if(lastselecctedposition == position){
                holder.parent.setSelected(true);
                holder.date.setTextColor(Color.parseColor("#2E2D2D"));
                holder.weekday.setTextColor(Color.parseColor("#FFFFFF"));
                holder.slotcount.setTextColor(Color.parseColor("#FFFFFF"));
                dateChooseCallback.OnDateSelect(date);
            }else {
                holder.parent.setSelected(false);
                holder.date.setTextColor(Color.parseColor("#2E2D2D"));
                holder.weekday.setTextColor(Color.parseColor("#3E3D3D"));
                holder.slotcount.setTextColor(Color.parseColor("#818181"));
            }

            holder.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (lastselecctedposition != position) {
                       lastselecctedposition = position;
                       notifyDataSetChanged();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return slotDateDataArrayList.size();
    }

    class DateSelectorViewHolder extends RecyclerView.ViewHolder {
        LinearLayout parent;
        TextView weekday, date, slotcount;

        public DateSelectorViewHolder(View view) {
            super(view);
            weekday = view.findViewById(R.id.day);
            date = view.findViewById(R.id.date);
            parent = view.findViewById(R.id.parent);
            slotcount = view.findViewById(R.id.slotcount);
        }
    }
}