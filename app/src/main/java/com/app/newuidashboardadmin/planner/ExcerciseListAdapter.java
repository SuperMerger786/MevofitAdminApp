package com.app.newuidashboardadmin.planner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.newuidashboardadmin.R;

import java.util.ArrayList;

public class ExcerciseListAdapter extends RecyclerView.Adapter<ExcerciseListAdapter.MyViewHolder> {
    ArrayList<String> excercisename = new ArrayList<>();
    Context context;

    ExcerciseListAdapter(Context context, ArrayList<String> excercisename) {
        this.excercisename = excercisename;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.excerlist_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        MyViewHolder(View view) {
            super(view);

        }
    }
}
