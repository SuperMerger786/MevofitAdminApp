package com.app.newuidashboardadmin.plan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.newuidashboardadmin.R;
import com.app.newuidashboardadmin.plan.SlotTimeConfigureData;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SlotDateAdapter extends RecyclerView.Adapter<SlotDateViewholder> {

    ArrayList<SlotTimeConfigureData> slotTimeConfigureDataArrayList;
    Context context;
    public SlotDateAdapter(ArrayList<SlotTimeConfigureData> slotTimeConfigureDataArrayList, Context context) {
        this.slotTimeConfigureDataArrayList = slotTimeConfigureDataArrayList;
        this.context = context;
    }



    @NonNull
    @Override
    public SlotDateViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.slot_config_item, parent, false);
        return new SlotDateViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SlotDateViewholder holder, int position) {
        DateFormat startdateformat = new SimpleDateFormat("hh:mm");
        DateFormat enddateformat = new SimpleDateFormat("hh:mm a");
        String start_text = startdateformat.format(slotTimeConfigureDataArrayList.get(position).start_time);
        String endtext = enddateformat.format(slotTimeConfigureDataArrayList.get(position).end_time);
        holder.time.setText(start_text+" - "+endtext);
    }

    @Override
    public int getItemCount() {
        return slotTimeConfigureDataArrayList.size();
    }
}

class SlotDateViewholder extends RecyclerView.ViewHolder {
TextView time;
    public SlotDateViewholder(@NonNull View itemView) {
        super(itemView);
        time = itemView.findViewById(R.id.time);
    }
}

