package com.app.newuidashboardadmin.planner;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.newuidashboardadmin.R;
import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class PlanWorkout extends Fragment {
    Gson gson;
    private WeakReference<Context> mContext;
    ImageView indicator_id,indicator_id2,indicator_id3,indicator_id4,indicator_id5;
    RecyclerView excercise_list,excercise_list2,excercise_list3,excercise_list4,excercise_list5;
    ArrayList<String> strlist = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        this.mContext = context;
        this.mContext = new WeakReference<>(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = new Gson();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.excerplan_view, container, false);
//        id_shop_now = (ImageView) view.findViewById(R.id.id_menu_device);
        excercise_list = (RecyclerView) view.findViewById(R.id.excercise_list);
        indicator_id = (ImageView) view.findViewById(R.id.indicator_id);
        excercise_list2 = (RecyclerView) view.findViewById(R.id.excercise_list2);
        indicator_id2 = (ImageView) view.findViewById(R.id.indicator_id2);
        excercise_list3 = (RecyclerView) view.findViewById(R.id.excercise_list3);
        indicator_id3 = (ImageView) view.findViewById(R.id.indicator_id3);
        excercise_list4 = (RecyclerView) view.findViewById(R.id.excercise_list4);
        indicator_id4 = (ImageView) view.findViewById(R.id.indicator_id4);
        excercise_list5 = (RecyclerView) view.findViewById(R.id.excercise_list5);
        indicator_id5 = (ImageView) view.findViewById(R.id.indicator_id5);

        getExpandableList();
        return view;
    }

//    DigitalData digital;

    // getting expandable list view
    public void getExpandableList() {
//        final String response = cacheSharedData.getDigitalData();
       /* if (response != null && !response.equalsIgnoreCase("NA")) {
            MyLogger.println("GetHomePublishData>>>check>>>>>1>>>>========2=");
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject digistr = jsonObject.getJSONObject("DigitalData");
                JSONArray digiArray = digistr.getJSONArray("data");
                if (digiArray != null) {
//                    digital = gson.fromJson(digistr.toString(), DigitalData.class);

                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            MyLogger.println("GetHomePublishData>>>check>>>>>1>>>>========2=");
        }*/
        indicator_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (excercise_list.getVisibility() == View.VISIBLE) {
                    excercise_list.setVisibility(View.GONE);
                    indicator_id.setImageResource(R.drawable.newui_mevolife_uparrow);
                    indicator_id.setRotation(180);
                } else {
                    excercise_list.setVisibility(View.VISIBLE);
                    indicator_id.setImageResource(R.drawable.newui_mevolife_uparrow_select);
                    indicator_id.setRotation(0);
                }
//                showDialog();
            }
        });
        indicator_id2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (excercise_list2.getVisibility() == View.VISIBLE) {
                    excercise_list2.setVisibility(View.GONE);
                    indicator_id2.setImageResource(R.drawable.newui_mevolife_uparrow);
                    indicator_id2.setRotation(180);
                } else {
                    excercise_list2.setVisibility(View.VISIBLE);
                    indicator_id2.setImageResource(R.drawable.newui_mevolife_uparrow_select);
                    indicator_id2.setRotation(0);
                }
//                showDialog();
            }
        });
        indicator_id3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (excercise_list3.getVisibility() == View.VISIBLE) {
                    excercise_list3.setVisibility(View.GONE);
                    indicator_id3.setImageResource(R.drawable.newui_mevolife_uparrow);
                    indicator_id3.setRotation(180);
                } else {
                    excercise_list3.setVisibility(View.VISIBLE);
                    indicator_id3.setImageResource(R.drawable.newui_mevolife_uparrow_select);
                    indicator_id3.setRotation(0);
                }
//                showDialog();
            }
        });
        indicator_id4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (excercise_list4.getVisibility() == View.VISIBLE) {
                    excercise_list4.setVisibility(View.GONE);
                    indicator_id4.setImageResource(R.drawable.newui_mevolife_uparrow);
                    indicator_id4.setRotation(180);
                } else {
                    excercise_list4.setVisibility(View.VISIBLE);
                    indicator_id4.setImageResource(R.drawable.newui_mevolife_uparrow_select);
                    indicator_id4.setRotation(0);
                }
//                showDialog();
            }
        });
        indicator_id5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (excercise_list5.getVisibility() == View.VISIBLE) {
                    excercise_list5.setVisibility(View.GONE);
                    indicator_id5.setImageResource(R.drawable.newui_mevolife_uparrow);
                    indicator_id5.setRotation(180);
                } else {
                    excercise_list5.setVisibility(View.VISIBLE);
                    indicator_id5.setImageResource(R.drawable.newui_mevolife_uparrow_select);
                    indicator_id5.setRotation(0);
                }
//                showDialog();
            }
        });
        if (strlist.size() > 0) {
            strlist.clear();
        }
        strlist.add("a");
        GridLayoutManager glm = new GridLayoutManager(getActivity(), 5);
        excercise_list.setLayoutManager(glm);
        ExcerciseListAdapter myAdapterNew = new ExcerciseListAdapter(getActivity(), strlist);
        excercise_list.setAdapter(myAdapterNew);
    }

    /*private ArrayList<Category> getCategaries(int position) {
        ArrayList<com.migital.digiproducthelper.bean.incomming.Category> listcate = new ArrayList<>();
        for (int i = 0; i < digital.getData().get(position).getCategoryData().size(); i++) {
            com.migital.digiproducthelper.bean.incomming.Category category = new Category();
            category = digiConfiguration.GetCategoryData(digital.getData().get(position).getCategoryData().get(i).getCatImageUrl(), digital.getData().get(position).getCategoryData().get(i).getCategoryTitle(), digital.getData().get(position).getCategoryData().get(i).getCategoryBoxid());
            listcate.add(category);
        }
        return listcate;
    }*/

   /* public void showDialog() {
        final Dialog dialog = new Dialog(mContext.get());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.newui_dialog_dashboard);
        dialog.setTitle("");

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.LEFT | Gravity.TOP;
        dialog.getWindow().setAttributes(lp);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        LinearLayout order_user = (LinearLayout) dialog.findViewById(R.id.order_user);
        LinearLayout expertuser = (LinearLayout) dialog.findViewById(R.id.expert_user);
        order_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentq = new Intent(getActivity(), OrderMarket.class);
                startActivity(intentq);
            }
        });
        expertuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext.get(), BookingHistoryDigital.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }*/
}
