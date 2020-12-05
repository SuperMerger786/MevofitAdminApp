package com.app.newuidashboardadmin.plan.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.newuidashboardadmin.R;
import com.app.newuidashboardadmin.plan.bean.SectionData;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SectionListAdapter extends RecyclerView.Adapter<SectionListViewHolder> {
    ArrayList<SectionData> arrayList;
    Context context;

    public SectionListAdapter(ArrayList<SectionData> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public SectionListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.section_list_data, parent, false);
        return new SectionListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionListViewHolder holder, int position) {
        SectionData sectionData = arrayList.get(position);
        holder.sectiontitel.setText(sectionData.slot_title + "  (" + sectionData.slot_title_booked_slot + ")");
        holder.firstsection.setLayoutManager(new LinearLayoutManager(context));
        BookingListAdapter adapter = new BookingListAdapter(sectionData.slotDataArrayList, context);
        holder.firstsection.setAdapter(adapter);
        holder.arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.firstsection.getVisibility() == View.VISIBLE){
                    holder.firstsection.setVisibility(View.GONE);
                    holder.arrow.setRotation(180);

                }else{
                    holder.firstsection.setVisibility(View.VISIBLE);
                    holder.arrow.setRotation(0);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        if (arrayList != null) {
            return arrayList.size();
        } else {
            return 0;
        }
    }
}

class SectionListViewHolder extends RecyclerView.ViewHolder {
    TextView sectiontitel;
    RecyclerView firstsection;
    ImageView arrow;

    public SectionListViewHolder(@NonNull View itemView) {
        super(itemView);
        firstsection = itemView.findViewById(R.id.firstsection);
        sectiontitel = itemView.findViewById(R.id.sectiontitel);
        arrow = itemView.findViewById(R.id.arrow);
    }
}
