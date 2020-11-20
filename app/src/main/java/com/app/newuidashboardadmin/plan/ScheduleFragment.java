package com.app.newuidashboardadmin.plan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.newuidashboardadmin.R;
import com.app.newuidashboardadmin.plan.adapter.BookingListAdapter;
import com.app.newuidashboardadmin.plan.adapter.DateSelectorAdapter;
import com.app.newuidashboardadmin.plan.adapter.SlotDateAdapter;
import com.app.newuidashboardadmin.plan.callback.DateChooseCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ScheduleFragment extends Fragment {
    ImageView left_arrow, right_arrow;
    Calendar currentdate;
    TextView monthname;
    RecyclerView bookrecyler;
    RecyclerView firstsection, secondsection, thirdsection;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.schedule_fragment, container, false);
        currentdate = Calendar.getInstance();
        initView(view);
        return view;
    }

    private void initView(View view) {
        left_arrow = view.findViewById(R.id.left_arrow);
        right_arrow = view.findViewById(R.id.right_arrow);
        monthname = view.findViewById(R.id.month);
        bookrecyler = view.findViewById(R.id.bookrecyler);
        firstsection = view.findViewById(R.id.firstsection);
        secondsection = view.findViewById(R.id.secondsection);
        thirdsection = view.findViewById(R.id.thirdsection);
        left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentdate.add(Calendar.MONTH, -1);
                monthname.setText(new SimpleDateFormat("MMM,yyyy").format(currentdate.getTime()));
                getMonthDate(currentdate.getTime());
            }
        });
        right_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentdate.add(Calendar.MONTH, 1);
                monthname.setText(new SimpleDateFormat("MMM,yyyy").format(currentdate.getTime()));
                getMonthDate(currentdate.getTime());
            }
        });
        getMonthDate(currentdate.getTime());
        monthname.setText(new SimpleDateFormat("MMM,yyyy").format(currentdate.getTime()));
        firstsection.setLayoutManager(new GridLayoutManager(getContext(), 3));
        secondsection.setLayoutManager(new GridLayoutManager(getContext(), 3));
        thirdsection.setLayoutManager(new GridLayoutManager(getContext(), 3));
//        BookingListAdapter adapter = new BookingListAdapter();
//        firstsection.setAdapter(adapter);
//        secondsection.setAdapter(adapter);
//        thirdsection.setAdapter(adapter);
        ArrayList<SlotTimeConfigureData> morning = getSlotarray("09:30", "13:30", 15, 30);
        SlotDateAdapter morningadapte = new SlotDateAdapter(morning, getContext());
        firstsection.setAdapter(morningadapte);
        ArrayList<SlotTimeConfigureData> noon = getSlotarray("15:00", "17:00", 15, 30);
        SlotDateAdapter noonadapte = new SlotDateAdapter(noon, getContext());
        secondsection.setAdapter(noonadapte);
        ArrayList<SlotTimeConfigureData> evening = getSlotarray("18:00", "20:30", 15, 30);
        SlotDateAdapter eveningadapter = new SlotDateAdapter(evening, getContext());
        thirdsection.setAdapter(eveningadapter);
    }

    private void getMonthDate(Date date) {
        ArrayList<Date> arrayList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int myMonth = cal.get(Calendar.MONTH);

        while (myMonth == cal.get(Calendar.MONTH)) {
            System.out.print(cal.getTime());
            arrayList.add(cal.getTime());
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String todaydate = format.format(today);
        int todayposition = -1;
        for (int i = 0; i < arrayList.size(); i++) {
            String curr = format.format(arrayList.get(i));
            if(curr.equalsIgnoreCase(todaydate)){
                todayposition = i;
            }
        }
        DateSelectorAdapter dateSelectorAdapter = new DateSelectorAdapter(getContext(),todayposition, arrayList, new DateChooseCallback() {
            @Override
            public void OnDateSelect(Date date) {
                System.out.println("TodayBookingFragment.OnDateSelect " + date);
            }
        });
        bookrecyler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        bookrecyler.setAdapter(dateSelectorAdapter);
    }

    private ArrayList<SlotTimeConfigureData> getSlotarray(String start_tm, String end_tm, int break_interval, int slot_time) {
        ArrayList<SlotTimeConfigureData> arrayList = new ArrayList<>();
        long break_mili = 60000 * break_interval;
        long slotmili = 60000 * slot_time;
        System.out.println("ScheduleFragment.getSlotarray time interval minute " + break_interval + " in mili " + break_mili);
        String date1 = "26/02/2011";
        String date2 = "26/02/2011";
        String format = "dd/MM/yyyy HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date dateObj1 = sdf.parse(date1 + " " + start_tm);
            Date dateObj2 = sdf.parse(date2 + " " + end_tm);
            System.out.println("Date Start: " + dateObj1);
            System.out.println("Date End: " + dateObj2);

//Date d = new Date(dateObj1.getTime() + 3600000);


            long dif = dateObj1.getTime();
            while (dif < dateObj2.getTime()) {
                SlotTimeConfigureData slotTimeConfigureData = new SlotTimeConfigureData();
                Date start_slot = new Date(dif);
                slotTimeConfigureData.start_time = start_slot;
                dif += slotmili;
                Date end_slot = new Date(dif);
                slotTimeConfigureData.end_time = end_slot;
                System.out.println("ScheduleFragment.getSlotarray date slot " + start_slot + " end :-" + end_slot);
                dif += break_mili;
                arrayList.add(slotTimeConfigureData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

}
