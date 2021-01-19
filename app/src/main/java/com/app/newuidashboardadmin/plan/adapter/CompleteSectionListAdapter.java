package com.app.newuidashboardadmin.plan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.newuidashboardadmin.R;
import com.app.newuidashboardadmin.plan.bean.CompletedSectionData;
import com.app.newuidashboardadmin.plan.bean.SectionData;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CompleteSectionListAdapter extends RecyclerView.Adapter<CompletedSectionListViewHolder> {
    ArrayList<CompletedSectionData> arrayList;
    Context context;
    String type;

    public CompleteSectionListAdapter(ArrayList<CompletedSectionData> arrayList, Context context,String type) {
        this.arrayList = arrayList;
        this.context = context;
    this.type = type;
    }

    @NonNull
    @Override
    public CompletedSectionListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.section_list_data, parent, false);
        return new CompletedSectionListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedSectionListViewHolder holder, int position) {
        CompletedSectionData sectionData = arrayList.get(position);
        holder.sectiontitel.setText(sectionData.slot_title);
        holder.firstsection.setLayoutManager(new LinearLayoutManager(context));
        CompletedBookingAdapter adapter = new CompletedBookingAdapter(sectionData.slotDataArrayList, context,type);
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

class CompletedSectionListViewHolder extends RecyclerView.ViewHolder {
    TextView sectiontitel;
    RecyclerView firstsection;
    ImageView arrow;

    public CompletedSectionListViewHolder(@NonNull View itemView) {
        super(itemView);
        firstsection = itemView.findViewById(R.id.firstsection);
        sectiontitel = itemView.findViewById(R.id.sectiontitel);
        arrow = itemView.findViewById(R.id.arrow);
    }
}
