package com.app.newuidashboardadmin.plan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.newuidashboardadmin.CircularImageView;
import com.app.newuidashboardadmin.PlanConfigureActivity;
import com.app.newuidashboardadmin.R;
import com.app.newuidashboardadmin.plan.PlanConfigurationMainFragment;
import com.app.newuidashboardadmin.plan.bean.CategoryData;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAddapter extends RecyclerView.Adapter<CategoryViewHolder> {
    ArrayList<CategoryData> categoryDataArrayList;
    Context context;

    public CategoryAddapter(ArrayList<CategoryData> categoryDataArrayList, Context context) {
        this.categoryDataArrayList = categoryDataArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.instance_list, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Glide.with(context).load(categoryDataArrayList.get(position).catImageUrl).into(holder.image);
        holder.categoryname.setText(categoryDataArrayList.get(position).category_title);
        holder.instancename.setText(categoryDataArrayList.get(position).instance_title);
        if (categoryDataArrayList.get(position).isSellerSlotConfigured.equalsIgnoreCase("1")) {
            holder.complete.setVisibility(View.VISIBLE);
            holder.setup.setVisibility(View.GONE);
        } else {
            holder.complete.setVisibility(View.GONE);
            holder.setup.setVisibility(View.VISIBLE);
        }
        holder.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlanConfigureActivity.class);
                intent.putExtra("instanceId",categoryDataArrayList.get(position).instance_boxid);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return categoryDataArrayList.size();
    }
}

class CategoryViewHolder extends RecyclerView.ViewHolder {
    CircularImageView image;
    TextView instancename, categoryname;
    LinearLayout complete;
    TextView setup;
    CardView background;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        background = itemView.findViewById(R.id.background);
        image = itemView.findViewById(R.id.image);
        instancename = itemView.findViewById(R.id.instancename);
        categoryname = itemView.findViewById(R.id.categoryname);
        complete = itemView.findViewById(R.id.complete);
        setup = itemView.findViewById(R.id.setup);

    }
}
