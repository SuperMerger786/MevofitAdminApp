package com.app.newuidashboardadmin.plan;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.newuidashboardadmin.R;
import com.app.newuidashboardadmin.plan.adapter.BookingListAdapter;
import com.app.newuidashboardadmin.plan.adapter.DateSelectorAdapter;
import com.app.newuidashboardadmin.plan.adapter.SectionListAdapter;
import com.app.newuidashboardadmin.plan.bean.BookingHistoryResponse;
import com.app.newuidashboardadmin.plan.bean.request.GetSellerBookingSlotsRequest;
import com.app.newuidashboardadmin.plan.callback.DateChooseCallback;
import com.app.newuidashboardadmin.services.Response;
import com.app.newuidashboardadmin.services.RestApiController;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TodayBookingFragment extends Fragment {
    ImageView left_arrow, right_arrow;
    Calendar currentdate;
    TextView monthname;
    RecyclerView bookrecyler;
    RecyclerView sectionlist;
    TextView datename, dateview;
    String type = "today";//today.cupcoming,previous

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.today_booking_slot_fragment, container, false);
        currentdate = Calendar.getInstance();
        initView(view);
        System.out.println("TodayBookingFragment.onCreateView internet check " + Check());
        return view;
    }

    private void initView(View view) {
        left_arrow = view.findViewById(R.id.left_arrow);
        right_arrow = view.findViewById(R.id.right_arrow);
        monthname = view.findViewById(R.id.month);
        bookrecyler = view.findViewById(R.id.bookrecyler);
        sectionlist = view.findViewById(R.id.sectionlist);
        datename = view.findViewById(R.id.datename);
        dateview = view.findViewById(R.id.dateview);
        left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentdate.add(Calendar.MONTH, -1);
                monthname.setText(new SimpleDateFormat("MMM,yyyy").format(currentdate.getTime()));
                getMonthDate(currentdate.getTime());
                sectionlist.setVisibility(View.GONE);
            }
        });
        right_arrow.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               currentdate.add(Calendar.MONTH, 1);
                                               monthname.setText(new SimpleDateFormat("MMM,yyyy").format(currentdate.getTime()));
                                               getMonthDate(currentdate.getTime());
                                               sectionlist.setVisibility(View.GONE);
                                           }
                                       }
        );
        getMonthDate(currentdate.getTime());
        monthname.setText(new SimpleDateFormat("MMM,yyyy").format(currentdate.getTime()));
        sectionlist.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    Calendar calendarown = Calendar.getInstance();
    private void getMonthDate(Date date) {


        ArrayList<Date> arrayList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int myMonth = cal.get(Calendar.MONTH);
        System.out.println("TodayBookingFragment.getMonthDate type "+type);
        while (myMonth == cal.get(Calendar.MONTH)) {
            if (type.equalsIgnoreCase("previous")) {
                System.out.println("TodayBookingFragment.getMonthDate date "+type+" - "+cal.getTime());
                if (cal.getTime().before(calendarown.getTime())) {
                    System.out.println("TodayBookingFragment.getMonthDate date "+type+" - "+cal.getTime());
                    System.out.print(cal.getTime());
                    arrayList.add(cal.getTime());
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                }else {
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                }
            } else if (type.equalsIgnoreCase("upcoming")) {
                System.out.println("TodayBookingFragment.getMonthDate date "+type+" - "+cal.getTime());
                if (cal.getTime().after(calendarown.getTime())) {
                    System.out.print(cal.getTime());
                    arrayList.add(cal.getTime());
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                }
                else {
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                }
            }else {
                System.out.print(cal.getTime());
                arrayList.add(cal.getTime());
                cal.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String todaydate = format.format(today);
        int todayposition = -1;
       if(type.equalsIgnoreCase("today")) {
           for (int i = 0; i < arrayList.size(); i++) {
               String curr = format.format(arrayList.get(i));
               if (curr.equalsIgnoreCase(todaydate)) {
                   todayposition = i;
               }
           }
       }else {
           todayposition = 0;
       }
        DateSelectorAdapter dateSelectorAdapter = new DateSelectorAdapter(getContext(), todayposition, arrayList, new DateChooseCallback() {
            @Override
            public void OnDateSelect(Date date) {
                System.out.println("TodayBookingFragment.OnDateSelect " + date);
                DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat format3 = new SimpleDateFormat("dd MMM,yyyy");
                DateFormat format4 = new SimpleDateFormat("EEEE");
                dateview.setText(format3.format(date));
                datename.setText(format4.format(date));
                String datestring = format2.format(date);
                fetchData(datestring);
            }
        });
        bookrecyler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        bookrecyler.setAdapter(dateSelectorAdapter);
        try {
            bookrecyler.smoothScrollToPosition(todayposition);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchData(String slotdate) {
        Gson gson = new Gson();
        RestApiController restApiController = new RestApiController(getContext(), new Response() {
            @Override
            public void onResponseObtained(Object response, int responseType, boolean isCachedData) {
                System.out.println("TodayBookingFragment.onResponseObtained response " + response.toString());
                BookingHistoryResponse bookingHistoryResponse = gson.fromJson(response.toString(), BookingHistoryResponse.class);
                if (bookingHistoryResponse != null && bookingHistoryResponse.sectionDataArrayList != null) {
                    SectionListAdapter sectionListAdapter = new SectionListAdapter(bookingHistoryResponse.sectionDataArrayList, getContext());
                    sectionlist.setVisibility(View.VISIBLE);
                    sectionlist.setAdapter(sectionListAdapter);
                } else {
                    sectionlist.setVisibility(View.GONE);
                }
            }

            @Override
            public void onErrorObtained(String errormsg, int responseType) {
                sectionlist.setVisibility(View.GONE);
                System.out.println("TodayBookingFragment.onErrorObtained error " + errormsg);

            }
        }, 65);
        GetSellerBookingSlotsRequest getSellerBookingSlotsRequest = new GetSellerBookingSlotsRequest(getContext(), slotdate);
        restApiController.fetchBookingHisory(getSellerBookingSlotsRequest);

    }

    public Boolean Check() {
        ConnectivityManager cn = (ConnectivityManager) getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf = cn.getActiveNetworkInfo();
        if (nf != null && nf.isConnected() == true) {
            return true;
        } else {
            Toast.makeText(getContext(), "No internet connection.!",
                    Toast.LENGTH_LONG).show();
            return false;
        }
    }

}
