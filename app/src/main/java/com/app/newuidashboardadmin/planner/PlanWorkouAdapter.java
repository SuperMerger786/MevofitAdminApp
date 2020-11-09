package com.app.newuidashboardadmin.planner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.app.newuidashboardadmin.MyLogger;
import com.app.newuidashboardadmin.R;
import com.bumptech.glide.Glide;

public class PlanWorkouAdapter
//        extends BaseExpandableListAdapter
{
   /* private Context context;
    DigitalData digital;

    public PlanWorkouAdapter(DigitalData digital, Context context) {
        this.digital = digital;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        if (digital.getData() != null) {
            MyLogger.println("GetHomePublishData>>>getGroupCount=" + digital.getData().size());
            return digital.getData().size();
        } else {
            return 0;
        }
    }

    @Override
    public int getChildrenCount(int i) {
        MyLogger.println("GetHomePublishData>>>getChildrenCount=" + i);
        if (digital.getData() != null) {
            MyLogger.println("GetHomePublishData>>>getChildrenCount=" + digital.getData().get(i).getCategoryData().size());
            return digital.getData().get(i).getCategoryData().size();
        }else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int i) {
        MyLogger.println("GetHomePublishData>>>getGroup=" + digital.getData().get(i));
        return digital.getData().get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        MyLogger.println("GetHomePublishData>>>getChild=" + digital.getData().get(i).getCategoryData().get(i1));
        return digital.getData().get(i).getCategoryData().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.mevoexpand_parent, null);
        }
        String parent_title = digital.getData().get(i).getInstanceTitle();
        String imageUri = digital.getData().get(i).getInstImageUrl();
        MyLogger.println("GetHomePublishData>>>getGroupView=" + digital.getData().get(i).getInstanceTitle() + "====" + b + "==" + imageUri);
        TextView title = (TextView) view.findViewById(R.id.parent_txt);
        ImageView indicator_id = (ImageView) view.findViewById(R.id.indicator_id);
        ImageView parent_image = (ImageView) view.findViewById(R.id.parent_image);
        Glide.with(context)
                .load(imageUri)
                .error(Glide.with(parent_image).load(R.drawable.mevopro_recipes))
                .into(parent_image);
        if (b) {
            indicator_id.setImageResource(R.drawable.newui_mevolife_uparrow_select);
            indicator_id.setRotation(0);
            title.setTextColor(context.getResources().getColor(R.color.colorPrimary));
//            indicator_id.setImageResource(R.drawable.arrow_right_white);
        } else {
            title.setTextColor(context.getResources().getColor(R.color.black));
            indicator_id.setImageResource(R.drawable.newui_mevolife_uparrow);
            indicator_id.setRotation(180);
        }
        title.setText(parent_title);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.mevolifeexpand_child, null);
        }
        String child_title = digital.getData().get(i).getCategoryData().get(i1).getCategoryTitle();
        MyLogger.println("GetHomePublishData>>>getChildView=" + digital.getData().get(i).getCategoryData().get(i1).getCategoryTitle());
        TextView title = (TextView) view.findViewById(R.id.child_txt);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_child);
        Glide.with(context)
                .load(digital.getData().get(i).getCategoryData().get(i1).getCatImageUrl())
                .into(imageView);
        title.setText(child_title);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }*/
}


