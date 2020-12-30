package com.app.newuidashboardadmin;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.newuidashboardadmin.Utility.AppPrefernce;
import com.app.newuidashboardadmin.Utility.VersionShowPropmt;
import com.app.newuidashboardadmin.admingraph.GraphDatum;
import com.app.newuidashboardadmin.admingraph.GraphValues;
import com.app.newuidashboardadmin.newadmin.BookedList;
import com.app.newuidashboardadmin.newadmin.BookingPerformance;
import com.app.newuidashboardadmin.newadmin.NewDashboardEntity;
import com.app.newuidashboardadmin.newadmin.TodayBooking;
import com.app.newuidashboardadmin.plan.UserDetailsActivity;
import com.app.newuidashboardadmin.plan.bean.SlotData;
import com.app.newuidashboardadmin.services.Response;
import com.app.newuidashboardadmin.services.RestApiController;
import com.app.newuidashboardadmin.services.VolleyClient;
import com.app.newuidashboardadmin.services.WebServicesUrl;
import com.app.newuidashboardadmin.todaysbooking.BookingTokSIDRequest;
import com.app.newuidashboardadmin.todaysbooking.BookingTokSIDResponse;
import com.app.newuidashboardadmin.view.BookingAdapterRecyclerView;
import com.app.newuidashboardadmin.view.SetDeviceDetail;
import com.app.newuidashboardadmin.view.Utils;
import com.bumptech.glide.Glide;
import com.contact.util.CallUtility;
import com.contact.util.ContactSdk;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.Gson;
import com.megogrid.megoauth.AuthorisedPreference;
import com.megogrid.megoeventbuilder.bean.Events;
import com.megogrid.megoeventpersistence.MewardDbHandler;
import com.megogrid.megoeventssdkhandler.ActionPerformer;
import com.migital.digiproducthelper.BookingHistoryDigital;
import com.migital.digiproducthelper.FavoriteActivity;
import com.migital.digiproducthelper.extraui.NotificatinSetingactivity;
import com.migital.digiproducthelper.processui.TrackerAppListActivity;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import android.view.View.MeasureSpec;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import dmax.dialog.SpotsDialog;
import newui_food.foodnewui.MyUpCommingClassAdminAdapter;

public class AdminDashBoardNewFragment extends Fragment implements IResponseUpdater, DatePickerDialog.OnDateSetListener {
    //declaring objects
    RecyclerView recycerView_upcomming;
    Gson gson;
    private Context mContext;
    VolleyClient volleyClient;

    //booking
    FrameLayout ln_upcomming_class;
    CardView booking_status;
    TextView book_count, booking_tab, payment_tab;

    //    bookingperformance
    TextView total_booking, total_earn, total_avrrate;
    ImageView set_calender;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyLogger.println("MainFragment>>>>>>>onCreate");
    }

    Calendar dateCalendar;
    ImageView iv_pre;
    TextView count;
    SpotsDialog progressdialog;
    RecyclerView booking_list;
    LineChart chart;
    LinearLayout add_entry;
    AppPrefernce appPrefernce;
    TextView id_date, id_time, id_timetwo, id_minone, id_mintwo, id_hourone, id_hourtwo;
    LinearLayout booking_id, rl_challengeHeaderLayout, nodata_booking;
    EditText ed_referencecode;
    TextView txt_share;//,txtcoundown;
    Date date;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_home_new, container, false);
        recycerView_upcomming = (RecyclerView) view.findViewById(R.id.recycerView_upcomming);
        ln_upcomming_class = (FrameLayout) view.findViewById(R.id.ln_upcomming_class);
        booking_list = (RecyclerView) view.findViewById(R.id.booking_list);
        iv_pre = (ImageView) view.findViewById(R.id.iv_pre);
        count = (TextView) view.findViewById(R.id.count);
        booking_status = (CardView) view.findViewById(R.id.booking_status);
        book_count = (TextView) view.findViewById(R.id.book_count);
        total_booking = (TextView) view.findViewById(R.id.total_booking);
        total_earn = (TextView) view.findViewById(R.id.total_earn);
        total_avrrate = (TextView) view.findViewById(R.id.total_avrrate);
        booking_tab = (TextView) view.findViewById(R.id.booking_tab);
        payment_tab = (TextView) view.findViewById(R.id.payment_tab);
        ed_referencecode = (EditText) view.findViewById(R.id.ed_referencecode);
        txt_share = (TextView) view.findViewById(R.id.txt_share);
        set_calender = (ImageView) view.findViewById(R.id.set_calender);
//        txtcoundown  = (TextView) view.findViewById(R.id.txtcoundown);

        //booking counter
        id_hourone = (TextView) view.findViewById(R.id.id_hourone);
        id_hourtwo = (TextView) view.findViewById(R.id.id_hourtwo);
        id_time = (TextView) view.findViewById(R.id.id_time);
        id_timetwo = (TextView) view.findViewById(R.id.id_timetwo);
        id_minone = (TextView) view.findViewById(R.id.id_minone);
        id_mintwo = (TextView) view.findViewById(R.id.id_mintwo);
        nodata_booking = (LinearLayout) view.findViewById(R.id.nodata_booking);
        booking_id = (LinearLayout) view.findViewById(R.id.booking_id);

        rl_challengeHeaderLayout = (LinearLayout) view.findViewById(R.id.rl_challengeHeaderLayout);

        chart = (LineChart) view.findViewById(R.id.chart);
        add_entry = (LinearLayout) view.findViewById(R.id.add_entry);
        volleyClient = new VolleyClient(mContext, this);
        gson = new Gson();
        MyLogger.println("volleyclient Inside  on oncreate==");
        dateCalendar = Calendar.getInstance();
        frag_menu = (ImageView) view.findViewById(R.id.frag_menu_id);
        appPrefernce = new AppPrefernce(mContext);
        booking_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initializeChart(chart, "booking");
            }
        });
        payment_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initializeChart(chart, "payment");
            }
        });
        frag_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
//                ((AdminUI)mContext).getmDrawerLayout().openDrawer(GravityCompat.START);
            }
        });
        iv_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NotificatinSetingactivity.class);
                startActivity(intent);
            }
        });
        iv_pre.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                VersionShowPropmt versionShowPropmt = new VersionShowPropmt();
                versionShowPropmt.showDialog(getContext());
                return true;
            }
        });
        rl_challengeHeaderLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(getActivity(), SetDeviceDetail.class);
                startActivity(intent);
                return true;
            }
        });
        txt_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkReferencetext(ed_referencecode.getText().toString(), ed_referencecode)) {
                    shareReferencePost(ed_referencecode.getText().toString());
                }
            }
        });
        set_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDateHere();
            }
        });
        date = dateCalendar.getTime();

        return view;
    }

    private boolean checkReferencetext(String Name, EditText NameEditText) {
        boolean istrue = false;
        if (Name.length() == 0) {
            NameEditText.requestFocus();
            NameEditText.setError("FIELD CANNOT BE EMPTY");
            return false;
        } else {
            return true;
        }
    }

    private void hithome(String timedate) {
        if (Utils.isNetworkAvailable(mContext)) {
            progressdialog = startProgressDialog(mContext, "Loading.....");
            progressdialog.show();
            volleyClient.makeRequest(WebServicesUrl.Categaries, getHome(timedate).toString(), "Adminhome");
        } else {
            Toast.makeText(mContext, "No Internet connection!", Toast.LENGTH_LONG).show();
        }
    }

    private void hitGraphValues() {
        if (Utils.isNetworkAvailable(mContext)) {
            progressdialog = startProgressDialog(mContext, "Loading.....");
            progressdialog.show();
            volleyClient.makeRequest(WebServicesUrl.CategariesBooking, getGraphValues().toString(), "GraphValues");
        } else {
            Toast.makeText(mContext, "No Internet connection!", Toast.LENGTH_LONG).show();
        }
    }

    private void listenerSet(String response) {

//        String response = loadJSONFromAsset();
        NewDashboardEntity adminDashboard = gson.fromJson(response, NewDashboardEntity.class);

        setBookingPerFormance(adminDashboard.getData().getBookingPerformance());
        MyLogger.println("todays>>>>>booking>>>" + adminDashboard.getData().getTodayBookings().size());
        if (adminDashboard != null && adminDashboard.getData() != null && adminDashboard.getData().userNotification != null && adminDashboard.getData().userNotification.totalUnreadNotification != null) {
            System.out.println("AdminDashBoardNewFragment.listenerSet no data is null data " + adminDashboard.getData().userNotification.totalUnreadNotification);
            if (adminDashboard.getData().userNotification.totalUnreadNotification.equalsIgnoreCase("0")) {
                count.setVisibility(View.GONE);
                System.out.println("AdminDashBoardNewFragment.listenerSet data not set");
            } else {
                System.out.println("AdminDashBoardNewFragment.listenerSet data set " + adminDashboard.getData().userNotification.totalUnreadNotification);
                count.setVisibility(View.VISIBLE);
                count.setText(adminDashboard.getData().userNotification.totalUnreadNotification);
            }
        } else {
            System.out.println("AdminDashBoardNewFragment.listenerSet some data is null");
        }

        if (adminDashboard.getData().getTodayBookings() != null && adminDashboard.getData().getTodayBookings().size() > 0) {
            booking_status.setVisibility(View.VISIBLE);
            nodata_booking.setVisibility(View.GONE);
            ArrayList<TodayBooking> todaylist = (ArrayList<TodayBooking>) adminDashboard.getData().getTodayBookings();
            Collections.sort(todaylist, new Comparator<TodayBooking>() {
                @Override
                public int compare(TodayBooking lhs, TodayBooking rhs) {
                    try {
                        return lhs.getStartTime().compareTo(rhs.getStartTime());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    return lhs.getKey().compareTo(rhs.getKey());
                    return 0;
                }
            });
            /*booking_list.setAdapter(new BookingTodaysItemAdapter(todaylist));
            setListHeight(booking_list);*/
            BookingAdapterRecyclerView bookingAdapterRecyclerView = new BookingAdapterRecyclerView(todaylist, mContext);
            final LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            booking_list.setLayoutManager(manager);
            booking_list.setAdapter(bookingAdapterRecyclerView);
//            setListHeight(booking_list);
            book_count.setText("" + adminDashboard.getData().getTodayBookings().size());
            SetCounter(adminDashboard.getData().getTodayBookings());
        } else {
            ArrayList<TodayBooking> todaylist = (ArrayList<TodayBooking>) adminDashboard.getData().getTodayBookings();
            BookingAdapterRecyclerView bookingAdapterRecyclerView = new BookingAdapterRecyclerView(todaylist, mContext);
            final LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            booking_list.setLayoutManager(manager);
            booking_list.setAdapter(bookingAdapterRecyclerView);
            book_count.setText("0");
            booking_status.setVisibility(View.VISIBLE);
            nodata_booking.setVisibility(View.VISIBLE);
        }


        MyLogger.println("todays>>>>>booking>>2>" + adminDashboard.getData().getUpcomingBookings().getBookedList());
        if (adminDashboard.getData().getUpcomingBookings().getBookedList() != null) {
            ln_upcomming_class.setVisibility(View.VISIBLE);
            bookingSetUp((ArrayList<BookedList>) adminDashboard.getData().getUpcomingBookings().getBookedList());
        } else {
            ln_upcomming_class.setVisibility(View.GONE);
        }
    }

    private void setBookingPerFormance(BookingPerformance bookingPerFormance) {
        if (!bookingPerFormance.getTotalBookings().equalsIgnoreCase("NA")) {
            total_booking.setText(bookingPerFormance.getTotalBookings());
        } else {
            total_booking.setText("0");
        }
//        Currency currency = Currency.getInstance(bookingPerFormance.getCurrencySymbol());
//        String symbol = currency.getSymbol();
        if (!bookingPerFormance.getTotalEarnings().equalsIgnoreCase("NA")) {
            total_earn.setText(getCurrencySymbol(bookingPerFormance.getCurrencySymbol()) + " " + bookingPerFormance.getTotalEarnings());
        } else {
            total_earn.setText("0");
        }
        total_avrrate.setText("" + bookingPerFormance.getAverageRating());

    }

    public static String getCurrencySymbol(String currencySymbel) {

        try {
            if (currencySymbel.contains("\\u"))
                return String.valueOf(((char) Integer.parseInt(currencySymbel.replace("\\u", "").trim(), 16)));
            else
                return currencySymbel;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return " ";

    }

    @Override
    public void onStart() {
        super.onStart();

    }


    public String loadJSONFromAsset() {
        String json = null;
        InputStream is;
        try {
            is = mContext.getAssets().open("newdashboard.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (Exception e) {
        }
        System.out.println("1989 loaded json = " + json);
        return json;
    }

    @Override
    public void onServerResponseSuccess(String reqTag, final String response) throws Exception {
        MyLogger.println("GetHomePublishData>>>check>>>>>1>>>>>>22222>" + reqTag + "====" + response);
        if (progressdialog != null && progressdialog.isShowing())
            progressdialog.dismiss();
        if (response != null && reqTag.equalsIgnoreCase("Adminhome")) {
//            imagelayout.stopShimmer();
//            imagelayout.setVisibility(View.VISIBLE);
//            if (progressdialog != null && progressdialog.isShowing())
//                progressdialog.dismiss();
            MyLogger.println("GetHomePublishData>>>check>>>>>1>>>>>>>" + reqTag + "===========" + response);
            hitGraphValues();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    listenerSet(response);
                }
            }, 2000);

        } else if (response != null && reqTag.equalsIgnoreCase("VideoStatus")) {
//            if (progressdialog != null && progressdialog.isShowing())
//                progressdialog.dismiss();
            hithome(strdate);
        } else {
//            if (progressdialog != null && progressdialog.isShowing())
//                progressdialog.dismiss();
            adminDashboard = gson.fromJson(response, GraphValues.class);
            initializeChart(chart, "booking");
        }

    }

    GraphValues adminDashboard;

    @Override
    public void onServerResponseError(String reqTag, String errorMessage) {
        if (progressdialog != null && progressdialog.isShowing())
            progressdialog.dismiss();
    }


    private void bookingSetUp(final ArrayList<BookedList> bookinglist) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("MainWristActivity.callBoking call");
                if (bookinglist.size() > 0) {
                    ln_upcomming_class.setVisibility(View.VISIBLE);
                    displayListUpCommingClasses(bookinglist);

                } else {
                    ln_upcomming_class.setVisibility(View.GONE);
                }

            }
        }, 500);

    }

    private int[] setIntArray(String guess) {
        String temp = guess;
        int[] newGuess = new int[temp.length()];
        for (int i = 0; i < temp.length(); i++) {
            newGuess[i] = temp.charAt(i) - '0';
        }
        return newGuess;
    }

    private void displayListUpCommingClasses(ArrayList<BookedList> bookingList) {
        @SuppressLint("WrongConstant") LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayout.HORIZONTAL, false);
        recycerView_upcomming.setLayoutManager(linearLayoutManager);
        recycerView_upcomming.setAdapter(new MyUpCommingClassAdminAdapter(mContext, bookingList));
    }

    //Navigation drawer
    private ImageView frag_menu, close;


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public SpotsDialog startProgressDialog(Context context, String tittle) {
        SpotsDialog dialog = new SpotsDialog(context, tittle);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }


  /*  public void showDialog() {
        final Dialog dialog = new Dialog(mContext);
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
        LinearLayout tracker_user = (LinearLayout) dialog.findViewById(R.id.tracker_user);
        LinearLayout txt_timeline = (LinearLayout) dialog.findViewById(R.id.txt_timeline);
        txt_timeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent currentIntent = new Intent(mContext, NewUIFoodSeessionCompleteActivity.class);
                startActivity(currentIntent);
            }
        });
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
                Intent intent = new Intent(mContext, BookingHistoryDigital.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        tracker_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent currentIntent = new Intent(mContext, NewUIWorkoutPlanExercisesNew.class);
                startActivity(currentIntent);
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


    public String getDateCurrentTimeZone(long timestamp) {
        try {
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp * 1000);
//            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM hh:mm a");
            Date currenTimeZone = (Date) calendar.getTime();
            return sdf.format(currenTimeZone);
        } catch (Exception e) {
        }
        return "";
    }

    /*public long getMilliFromDateCounter(String dateFormat) {
        String[] straarray = dateFormat.split(":");
//        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(straarray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(straarray[1]));
//        date.setTime(calendar.getTimeInMillis());
//        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
       *//* try {
            date = formatter.parse(dateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }*//*
        System.out.println("Today is " + calendar.getTimeInMillis());
        return calendar.getTimeInMillis();
    }*/
    public long getMilliFromDate(String dateFormat) {
        String[] straarray = dateFormat.split(":");
//        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(straarray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(straarray[1]));
//        date.setTime(calendar.getTimeInMillis());
//        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
       /* try {
            date = formatter.parse(dateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        System.out.println("Today is " + calendar.getTimeInMillis());
        return calendar.getTimeInMillis();
    }

    TodayBooking todaylistnew;

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        dateCalendar.set(Calendar.YEAR, year);
        dateCalendar.set(Calendar.MONTH, month);
        dateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        date = dateCalendar.getTime();
        String strdate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        hithome(strdate);
    }

    /*  class BookingTodaysItemAdapter extends BaseAdapter {

          ArrayList<TodayBooking> todaylist;
          LayoutInflater inflater;

          public BookingTodaysItemAdapter(ArrayList<TodayBooking> todaylist) {
              this.todaylist = todaylist;
              inflater = getActivity().getLayoutInflater();
          }

          @Override
          public int getCount() {
              if (todaylist != null) {
                  MyLogger.println("list size = " + todaylist.size());
                  return todaylist.size();
              } else
                  return 0;
          }

          @Override
          public Object getItem(int position) {
              return null;
          }

          @Override
          public long getItemId(int position) {
              return 0;
          }

          @Override
          public View getView(final int position, View convertView, ViewGroup parent) {
              View view = inflater.inflate(R.layout.admin_todaysbooking_status, parent, false);
              TextView name = (TextView) view.findViewById(R.id.tv_name);
              TextView tv_male = (TextView) view.findViewById(R.id.tv_male);
              TextView tv_session = (TextView) view.findViewById(R.id.tv_session);
              CircularImageView imgview = (CircularImageView) view.findViewById(R.id.iv_profile_dummy);
              TextView start_endtime = (TextView) view.findViewById(R.id.start_endtime);
              TextView btn_starte = (TextView) view.findViewById(R.id.btn_starte);
              String name1 = todaylist.get(position).getCustomername();
              String name2 = name1.replace("#NA", " ");
              name.setText(name2);
              tv_male.setText(todaylist.get(position).getCustomerAge() + "," + todaylist.get(position).getCustomerGender());
              MyLogger.println("image>>>>>" + todaylist.get(position).getCustomerProfilepic());
              Glide.with(mContext)
                      .load(todaylist.get(position).getCustomerProfilepic())
                      .error(Glide.with(imgview).load(R.drawable.profile))
                      .into(imgview);
              start_endtime.setText(todaylist.get(position).getStartTime() + "-" + todaylist.get(position).getEndTime());
              if (todaylist.get(position).getCallStatus().equalsIgnoreCase("Completed")) {
                  btn_starte.setText("View");
                  btn_starte.setTextColor(getResources().getColor(R.color.colorPrimary));
                  btn_starte.setBackground(getResources().getDrawable(R.drawable.newui_btn_blue_outline));
              } else {
                  btn_starte.setText("start");
                  btn_starte.setTextColor(getResources().getColor(R.color.deep_white));
                  btn_starte.setBackground(getResources().getDrawable(R.drawable.background_start));
              }
              if (todaylist.get(position).getPlan_session_type().equalsIgnoreCase("group_session")) {
                  tv_session.setText("Group");
                  tv_session.setTextColor(getResources().getColor(R.color.group_color));
              } else {
                  tv_session.setText("Personal");
                  tv_session.setTextColor(getResources().getColor(R.color.colorPrimary));
              }
              if (btn_starte.getText().toString().equalsIgnoreCase("start")) {
                  btn_starte.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                          todaylistnew = todaylist.get(position);
                          makeSessionRequest(todaylist.get(position).getItemBoxId(), todaylist.get(position).getBookingId(), todaylist.get(position).getStartTime());
                      }
                  });
              } else {
                  btn_starte.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                          todaylistnew = todaylist.get(position);
                          SlotData slotData  = new SlotData();
                          slotData.user_name = name2;
                          slotData.user_profilepic = todaylistnew.getCustomerProfilepic();
                          slotData.user_gender = todaylistnew.getCustomerGender();
                          slotData.user_age = todaylistnew.getCustomerAge();
                          slotData.user_city = "NA";
                          slotData.user_country = "NA";

                          Intent intent = new Intent(mContext, UserDetailsActivity.class);
                          intent.putExtra("data", slotData);
                          startActivity(intent);
                          // makeSessionRequest(todaylist.get(position).getItemBoxId(), todaylist.get(position).getBookingId(), todaylist.get(position).getStartTime());
                      }
                  });
              }
              return view;
          }
      }*/
    public class BookingAdapterRecyclerView extends RecyclerView.Adapter<BookingAdapterRecyclerView.BookingLwHolder> {
        ArrayList<TodayBooking> todaylist;
        Context mContext;


        public BookingAdapterRecyclerView(ArrayList<TodayBooking> todaylist, Context context) {
            this.todaylist = todaylist;
            this.mContext = context;
        }

        @NonNull
        @Override
        public BookingAdapterRecyclerView.BookingLwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.admin_todaysbooking_status, parent, false);
            return new BookingAdapterRecyclerView.BookingLwHolder(view);
        }

        //      TodayBooking todaylistnew;
        @Override
        public void onBindViewHolder(@NonNull BookingAdapterRecyclerView.BookingLwHolder holder, int position) {

            String name1 = todaylist.get(position).getCustomername();
            String name2 = name1.replace("#NA", " ");
            holder.name.setText(name2);
            holder.tv_male.setText(todaylist.get(position).getCustomerAge() + "," + todaylist.get(position).getCustomerGender());
            MyLogger.println("image>>>>>" + todaylist.get(position).getCustomerProfilepic());
            Glide.with(mContext)
                    .load(todaylist.get(position).getCustomerProfilepic())
                    .error(Glide.with(holder.imgview).load(R.drawable.profile))
                    .into(holder.imgview);
            holder.start_endtime.setText(getStartTime(todaylist.get(position).getStartTime()) + "-" + getEndTime(todaylist.get(position).getEndTime()));
            if (todaylist.get(position).getCallStatus().equalsIgnoreCase("Completed")) {
                holder.btn_starte.setText("View");
                holder.btn_starte.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                holder.btn_starte.setBackground(mContext.getResources().getDrawable(R.drawable.newui_btn_blue_outline));
            } else {
                holder.btn_starte.setText("start");
                holder.btn_starte.setTextColor(mContext.getResources().getColor(R.color.deep_white));
                holder.btn_starte.setBackground(mContext.getResources().getDrawable(R.drawable.background_start));
            }
            if (todaylist.get(position).getPlan_session_type().equalsIgnoreCase("group_session")) {
                holder.tv_session.setText("Group");
                holder.tv_session.setTextColor(mContext.getResources().getColor(R.color.group_color));
            } else {
                holder.tv_session.setText("Personal");
                holder.tv_session.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            }
            if (holder.btn_starte.getText().toString().equalsIgnoreCase("start")) {
                holder.btn_starte.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        todaylistnew = todaylist.get(position);
                        makeSessionRequest(todaylist.get(position).getItemBoxId(), todaylist.get(position).getBookingId(), todaylist.get(position).getStartTime());
                    }
                });
            } else {
                holder.btn_starte.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        todaylistnew = todaylist.get(position);
                        SlotData slotData = new SlotData();
                        slotData.user_name = name2;
                        slotData.user_profilepic = todaylistnew.getCustomerProfilepic();
                        slotData.user_gender = todaylistnew.getCustomerGender();
                        slotData.user_age = todaylistnew.getCustomerAge();
                        slotData.user_city = "NA";
                        slotData.user_country = "NA";
                        Intent intent = new Intent(mContext, UserDetailsActivity.class);
                        intent.putExtra("data", slotData);
                        mContext.startActivity(intent);
//                        makeSessionRequest(todaylist.get(position).getItemBoxId(), todaylist.get(position).getBookingId(), todaylist.get(position).getStartTime());
                    }
                });
            }

        }


        @Override
        public int getItemCount() {
            return todaylist.size();
        }

        class BookingLwHolder extends RecyclerView.ViewHolder {
            TextView name, tv_male, tv_session, start_endtime, btn_starte;
            CircularImageView imgview;


            public BookingLwHolder(@NonNull View view) {
                super(view);
                name = (TextView) view.findViewById(R.id.tv_name);
                tv_male = (TextView) view.findViewById(R.id.tv_male);
                tv_session = (TextView) view.findViewById(R.id.tv_session);
                imgview = (CircularImageView) view.findViewById(R.id.iv_profile_dummy);
                start_endtime = (TextView) view.findViewById(R.id.start_endtime);
                btn_starte = (TextView) view.findViewById(R.id.btn_starte);
            }
        }

        private String getStartTime(String start) {
            String strtime = "NA";
//            String time = "3:30 PM";
            SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm");
            SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");
            try {
                strtime = date12Format.format(date24Format.parse(start));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return strtime;
        }

        private String getEndTime(String end) {
            String endtime = "NA";
//            String time = "3:30 PM";
            SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");
            SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");
            try {
                endtime = date12Format.format(date24Format.parse(end));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return endtime;
        }
    }

    private void setListHeight(ListView listView) {
        ListAdapter mAdapter = listView.getAdapter();
        if (mAdapter == null)
            return;

        int totalHeight = 0;
        int listWidth = listView.getMeasuredWidth();

        for (int i = 0; i < mAdapter.getCount(); i++) {
            View mView = mAdapter.getView(i, null, listView);

            mView.measure(
                    MeasureSpec.makeMeasureSpec(listWidth, MeasureSpec.EXACTLY),

                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

            totalHeight += mView.getMeasuredHeight();
            Log.w("HEIGHT" + i, String.valueOf(totalHeight));

        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    AuthorisedPreference authorisedPreference;

    private JSONObject getHome(String datetime) {
        authorisedPreference = new AuthorisedPreference(mContext);
        MyLogger.println("seller_id>>>>>>>>>0> " + authorisedPreference.getString("app_sellerrid"));
//        authorisedPreference.setSellerId("740bb9c3-17ff-4ef8-8922-9de82a9a2471");
//        authorisedPreference.setString("app_sellerrid", "740bb9c3-17ff-4ef8-8922-9de82a9a2471");
//        MyLogger.println("seller_id>>>>>>>>>1> " + authorisedPreference.getString("app_sellerrid"));
        JSONObject jsonObj = null;
        jsonObj = new JSONObject();
        try {
            jsonObj.put("action", "GetHomeDashBoard");
            jsonObj.put("mewardid", authorisedPreference.getMewardId());
            jsonObj.put("tokenkey", authorisedPreference.getTokenKey());
            jsonObj.put("user_type", "developer");
            jsonObj.put("requestTime", System.currentTimeMillis());
            jsonObj.put("seller_uid", authorisedPreference.getString("app_sellerrid"));
            jsonObj.put("isadmin", "1");
            jsonObj.put("bookedSlotDate", datetime);//2020-12-17"

            jsonObj.put("instance_boxid", appPrefernce.getInstanceBoxid());
            jsonObj.put("store_id", "");
            jsonObj.put("has_seller", "true");
            jsonObj.put("encryption_status", "0");

            return jsonObj;
        } catch (Exception e) {
            return jsonObj;
        }
    }

    /*{
        "action": "GetHomeDashBoard",
            "encryption_status": "0",
            "mewardid": "74PFT15YQ1602148478",
            "tokenkey": "Y5OVS2AC81602588317_d3ec1951-abcf-4354-9c90-5d776e1d1126_ShOZpXKHR_bpSa25QWk",
            "user_type": "developer",
            "requestTime": "1601976588925",
            "seller_uid": "740bb9c3-17ff-4ef8-8922-9de82a9a2471",
            "isadmin": "1"
    }
*/
    String starttime = "NA";
    String bookingId = "NA";

    public void makeSessionRequest(String itemBoxId, String bookedboxId, String start_time) {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String strdate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        starttime = start_time;
        bookingId = bookedboxId;
        BookingTokSIDRequest request = new BookingTokSIDRequest(mContext, itemBoxId, bookedboxId, start_time);
//        VolleyClient controller = new RestApiController(this, this, 0);
        progressdialog = startProgressDialog(mContext, "Loading.....");
        progressdialog.show();
        RestApiController controller = new RestApiController(((AdminUI) mContext), new Response() {
            @Override
            public void onResponseObtained(Object response, int responseType, boolean isCachedData) {
                if (progressdialog != null && progressdialog.isShowing())
                    progressdialog.dismiss();

                BookingTokSIDResponse sidResponse = new Gson().fromJson(response.toString(), BookingTokSIDResponse.class);
                CallUtility.currentbookingid = todaylistnew.getBookingId();
                CallUtility.start_time = starttime;
                CallUtility.start_date = strdate;
                MyLogger.println("check>>>>>makeSessionRequest>>0 " + response.toString() + " " + CallUtility.currentbookingid + " " + todaylistnew.getBookingId());
                ContactSdk.PublisherVideoCall(mContext, "30",
                        todaylistnew.getCustomername(), appPrefernce.getProfilePic(), todaylistnew.getCustomerProfilepic(),
                        sidResponse.TokBoxTokenID, sidResponse.TokBoxSID, sidResponse.TokApiKey, "11");
                contacted = true;
                /*ContactSdk.PublisherVideoCall(this, bookSummary.BookingDuration.split("")[0],
                        bookSummary.customername, bookSummary.ImageUrl,bookSummary.profilepic,
                        sidResponse.TokBoxTokenID, sidResponse.TokBoxSID,sidResponse.TokApiKey,bookSummary.messenger_type_val);*/
                sendPush(EVENT_CALL_TO_CUSTOMER, todaylistnew.getBookingId());
            }

            @Override
            public void onErrorObtained(String errormsg, int responseType) {
                if (progressdialog != null && progressdialog.isShowing())
                    progressdialog.dismiss();

            }
        }, 0);
        controller.makemebasedRequest(request);
    }

    public static String EVENT_CALL_TO_CUSTOMER = "DBIXLKLBA";

    public void sendPush(String id, String bookingId) {
        MewardDbHandler mewardDbHandler = new MewardDbHandler(mContext);
        ArrayList<Events> eventses = mewardDbHandler.getEvent(id, "Complete");
        ArrayList<String> rules = new ArrayList<>();
        MyLogger.println("check>>>>>makeSessionRequest>>1 " + eventses);
        if (eventses != null) {

            try {
                ActionPerformer actionPerformer = new ActionPerformer();
                ArrayList<String> key = new ArrayList<>();
                key.add(bookingId);
                actionPerformer.sendPush(mContext, id, eventses.get(0).ruleId, key);
            } catch (Exception e) {
                System.out.println("CallSummary.updateCallStatus " + e);
            }


        }


    }

    public void initializeChart(LineChart mChart, String str) {
        mChart.setDrawGridBackground(false);
        mChart.setDrawBorders(false);
        mChart.setBorderWidth(0);
        mChart.setDescription("");
        mChart.setTouchEnabled(true);
        mChart.setDragEnabled(false);
        mChart.setScaleEnabled(false);
        mChart.setPinchZoom(false);
        mChart.setMinOffset(1);
        mChart.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        mChart.setMaxVisibleValueCount(60);
        mChart.setDrawGridBackground(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setSpaceBetweenLabels(0);
        xAxis.setDrawGridLines(false);

//        xAxis.setAxisLineWidth(5);
        xAxis.setAxisLineColor(getActivity().getResources().getColor(R.color.setting_subheading));
        xAxis.setTextColor(getActivity().getResources().getColor(R.color.setting_subheading));
//        xAxis.setEnabled(false);
        mChart.animateY(12500);
        mChart.getLegend().setEnabled(false);
        mChart.getAxisLeft().setDrawGridLines(true);
//        mChart.getAxisRight().setDrawGridLines(true);

        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);
        mChart.setMarkerView(mv);
        XAxis xl = mChart.getXAxis();
        xl.setAvoidFirstLastClipping(true);
        xl.setAxisLineColor(getActivity().getResources().getColor(R.color.setting_subheading));
        xl.setDrawGridLines(true);
        xl.setTextColor(getActivity().getResources().getColor(R.color.setting_subheading));
        xl.setLabelsToSkip(0);
//        xl.setEnabled(false);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setInverted(false);
        leftAxis.setAxisMinValue(0f);
//        leftAxis.setAxisLineColor(getActivity().getResources().getColor(R.color.setting_subheading));
        leftAxis.setAxisLineColor(getActivity().getResources().getColor(R.color.setting_subheading));
        leftAxis.setTextColor(getActivity().getResources().getColor(R.color.setting_subheading));
//        leftAxis.setEnabled(false);
//        xl.setDrawAxisLine(false);

        YAxis rightAxis = mChart.getAxisRight();
//        rightAxis.setAxisLineColor(Color.WHITE);
        rightAxis.setDrawGridLines(false);
        rightAxis.setEnabled(false);
//        rightAxis.setAxisLineColor(Color.RED);
        rightAxis.setTextColor(getActivity().getResources().getColor(R.color.setting_subheading));
        rightAxis.setEnabled(false);
//        xl.setDrawAxisLine(false);
        /* Set data on the chart */
        setFatData(mChart, str);
        /*Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        l.setMaxSizePercent(4);
        mChart.invalidate();*/
    }

    ArrayList<String> xVals;
    ArrayList<Entry> yVals;
    ArrayList<GraphDatum> graphValues;
    ModuleAnalyzer moduleAnalyzer;

    //graph chart pr data aur color set hota he
    public void setFatData(LineChart chart, String fragmentTypeNew) {
        moduleAnalyzer = new ModuleAnalyzer(getActivity());
        xVals = new ArrayList<String>();
        yVals = new ArrayList<Entry>();
        graphValues = new ArrayList<>();
        int totalEntries = 0;
        MyLogger.println("fragment type value is ==  fragment type " + fragmentTypeNew);
        if (fragmentTypeNew.equalsIgnoreCase("booking")) {
            booking_tab.setBackground(getResources().getDrawable(R.drawable.btn_green_solid30));
            booking_tab.setTextColor(getResources().getColor(R.color.deep_white));
            payment_tab.setBackground(null);
            payment_tab.setTextColor(getResources().getColor(R.color.colorPrimary));
            graphValues.addAll(adminDashboard.getData().getGraphData());
            for (int i = 0; i < graphValues.size(); i++) {
                xVals.add(getMonth(Integer.parseInt(graphValues.get(i).getBookingMonth())));
                float val = Float.parseFloat(graphValues.get(i).getTotalBookings());
                if (val > 0) {
                    totalEntries++;
                }
                yVals.add(new Entry(val, i));
            }
        } else {
            payment_tab.setBackground(getResources().getDrawable(R.drawable.btn_green_solid30));
            payment_tab.setTextColor(getResources().getColor(R.color.deep_white));
            booking_tab.setBackground(null);
            booking_tab.setTextColor(getResources().getColor(R.color.colorPrimary));
            graphValues.addAll(adminDashboard.getData().getGraphData());
            for (int i = 0; i < graphValues.size(); i++) {
                xVals.add(getMonth(Integer.parseInt(graphValues.get(i).getBookingMonth())));
                float val = Float.parseFloat(graphValues.get(i).getTotalEarnings());
                if (val > 0) {
                    totalEntries++;
                }
                yVals.add(new Entry(val, i));
            }
        }



        /*for (int i = 0; i < graphValues1.size(); i++) {
            xVals.add(graphValues1.get(i).getKey());
            float val = graphValues1.get(i).getValue();
            if (val > 0) {
                totalEntries++;
            }
            yVals1.add(new Entry(val, i));
        }
        for (int i = 0; i < graphValues2.size(); i++) {
            xVals.add(graphValues2.get(i).getKey());
            float val = graphValues2.get(i).getValue();
            if (val > 0) {
                totalEntries++;
            }
            yVals2.add(new Entry(val, i));
        }*/

        if (totalEntries == 0) {
            chart.setVisibility(View.INVISIBLE);
            add_entry.setVisibility(View.VISIBLE);
//            goal.setVisibility(View.VISIBLE);
        } else {
            chart.setVisibility(View.VISIBLE);
//            goal.setVisibility(View.VISIBLE);
            add_entry.setVisibility(View.INVISIBLE);
        }


        MyLogger.println("<<<<< graphValues setting graph : " + graphValues.size() + " <<<details>>> " + graphValues.toString());
        MyLogger.println("<<<< graphValues setting graph 1111 : " + xVals.get(0) + " <<<y.size>>> " + yVals.get(0) + " <<<totalEntries>>> " + totalEntries);
        if (totalEntries > 0) {
            chart.setVisibility(View.VISIBLE);
            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            YAxis yAxis = chart.getAxisLeft();
            if (fragmentTypeNew.equalsIgnoreCase("booking")) {
                if (totalEntries == 1) {
                    yAxis.setLabelCount(1);
                } else if (totalEntries == 2) {
                    yAxis.setLabelCount(2);
                } else {
                    yAxis.setLabelCount(5);
                }
            } else {
                yAxis.setLabelCount(5);
            }

            LineDataSet set1 = addlineToChart(getResources().getColor(R.color.Graph_tab_color), yVals);
            dataSets.add(set1);
            /*LineDataSet set2 = addlineToChart(getResources().getColor(R.color.graph_circle_color_orange), yVals1);
            dataSets.add(set2);
            LineDataSet set3 = addlineToChart(getResources().getColor(R.color.graph_circle_color_red), yVals2);
            dataSets.add(set3);*/
            ArrayList<String> filterxVAls = removeDuplicates(xVals);
            LineData data = new LineData(filterxVAls, dataSets);
            chart.setData(data);
            chart.animateY(5000);
        } else {
            chart.setVisibility(View.GONE);
        }
    }

    public LineDataSet addlineToChart(int color, ArrayList<Entry> val) {
        LineDataSet set2 = new LineDataSet(val, "hw");

        set2.setCircleColorHole(getResources().getColor(android.R.color.white));
        set2.setFillColor(getResources().getColor(R.color.Graph_tab_color));
        set2.setCircleColor(color);
        set2.setDrawValues(false);
        set2.setColor(color);
        set2.setCircleRadius(3.0f);
        set2.setLineWidth(1.0f);
        set2.setDrawFilled(true);
        return set2;
    }

    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {
        ArrayList<T> newList = new ArrayList<T>();
        for (T element : list) {
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
        // return the new list
        return newList;
    }

    private JSONObject getGraphValues() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        authorisedPreference = new AuthorisedPreference(mContext);
        JSONObject jsonObj = null;
        jsonObj = new JSONObject();
        try {
            jsonObj.put("action", "GetBookingGraphData");
            jsonObj.put("mewardid", authorisedPreference.getMewardId());
            jsonObj.put("tokenkey", authorisedPreference.getTokenKey());
            jsonObj.put("user_type", "selleradmin");
            jsonObj.put("year", "" + year);
            jsonObj.put("instance_boxid", appPrefernce.getInstanceBoxid());
            jsonObj.put("seller_uid", authorisedPreference.getString("app_sellerrid"));
            jsonObj.put("isadmin", "1");
            jsonObj.put("store_id", "");
            jsonObj.put("has_seller", "true");
            jsonObj.put("encryption_status", "0");

            return jsonObj;
        } catch (Exception e) {
            return jsonObj;
        }

    }

    private String getMonth(int month) {
        String monthname = "NA";
        if (month == 1) {
            monthname = "Jan";
        } else if (month == 2) {
            monthname = "Feb";
        } else if (month == 3) {
            monthname = "March";
        } else if (month == 4) {
            monthname = "April";
        } else if (month == 5) {
            monthname = "May";
        } else if (month == 6) {
            monthname = "June";
        } else if (month == 7) {
            monthname = "July";
        } else if (month == 8) {
            monthname = "August";
        } else if (month == 9) {
            monthname = "Sept";
        } else if (month == 10) {
            monthname = "Oct";
        } else if (month == 11) {
            monthname = "Nov";
        } else if (month == 12) {
            monthname = "Dec";
        }
        return monthname;
    }

    public void SetCounter(List<TodayBooking> bookingList) {

        if (bookingList.size() > 0) {
            booking_id.setVisibility(View.VISIBLE);
//                            id_desc.setText(bookingList.get(0).company_name);
//            id_date.setText("Your booking with " + bookingList.get(0).tablename + " is confirmed for " + bookingList.get(0).starttime);
                          /*  Glide.with(mContext.get())
                                    .load(bookingList.get(0).ImageUrl)
                                    .error(Glide.with(book_img).load(R.drawable.prfile_background))
                                    .into(book_img);*/

            for (int i = 0; i < bookingList.size(); i++) {
                System.out.println("MainWristActivity.callBoking <<<<<list1>" + bookingList.size() + "====" + getMilliFromDate(bookingList.get(i).getStartTime()) + "===" + (getMilliFromDate(bookingList.get(i).getStartTime()) - System.currentTimeMillis()) + "=minute=" + ((((getMilliFromDate(bookingList.get(i).getStartTime()) - System.currentTimeMillis()) / 1000) % 3600) / 60) + "==seconds==" + ((((getMilliFromDate(bookingList.get(i).getStartTime()) - System.currentTimeMillis()) / 1000) % 3600) % 60) + "===hour=" + (((getMilliFromDate(bookingList.get(i).getStartTime()) - System.currentTimeMillis()) / 1000) / 3600));
                /*if((getMilliFromDate(bookingList.get(i).getStartTime()) - System.currentTimeMillis())>0) {
                    startTimer(Integer.parseInt((getMilliFromDate(bookingList.get(i).getStartTime()) - System.currentTimeMillis()) + ""));
                }*/
                if ((getMilliFromDate(bookingList.get(i).getStartTime()) > System.currentTimeMillis()) //&& (((getMilliFromDate(bookingList.get(i).getStartTime()) - System.currentTimeMillis()) / 1000) / 3600) < 1
                ) {
                    int hour = (int) (((getMilliFromDate(bookingList.get(i).getStartTime()) - System.currentTimeMillis()) / 1000) / 3600);
                    long milisecondtime = (getMilliFromDate(bookingList.get(i).getStartTime()) - System.currentTimeMillis());
//                    String minute = "" + ((((getMilliFromDate(bookingList.get(i).getStartTime()) - System.currentTimeMillis()) / 1000) % 3600) / 60);
//                    String seconds = "" + ((((getMilliFromDate(bookingList.get(i).getStartTime()) - System.currentTimeMillis()) / 1000) % 3600) % 60);
//                    int[] minutearr = setIntArray(minute);
//                    int[] secondarr = setIntArray(seconds);
                    CountDownTimer countDownTimer = new CountDownTimer(milisecondtime, 1000) {
                        public void onTick(long millisUntilFinished) {
                            long millis = millisUntilFinished;
                            //Convert milliseconds into hour,minute and seconds
                            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                            String[] hmsvalue = hms.split(":");
                            int[] hourarr = setIntArray(hmsvalue[0]);
                            int[] minutearr = setIntArray(hmsvalue[1]);
                            int[] secondarr = setIntArray(hmsvalue[2]);
//                            txtcoundown.setText(hmsvalue[1]+":"+hmsvalue[2]);//set text
//                            txtcoundown.setText(hour+" "+secondarr[0]+":"+secondarr[1]+"====="+hms);
//                            if (hour == 0) {

                            if (hourarr.length > 1) {
                                id_hourone.setText("" + hourarr[0]);
                                id_hourtwo.setText("" + hourarr[1]);

                            } else {
                                id_hourone.setText("" + hourarr[0]);
                                id_hourtwo.setText("" + hourarr[1]);
//                                    id_minone.setText("0");
//                                    id_mintwo.setText("" + secondarr[0]);
                            }
                            if (Integer.parseInt(hmsvalue[1]) >= 0) {
                                if (minutearr.length > 1) {
                                    id_time.setText("" + minutearr[0]);
                                    id_timetwo.setText("" + minutearr[1]);
                                } else {
//                                        id_time.setText("0");
//                                        id_timetwo.setText("" + minutearr[0]);
                                    id_time.setText("" + minutearr[0]);
                                    id_timetwo.setText("" + minutearr[1]);
                                }
                            } else {
                                id_time.setText("0");
                                id_timetwo.setText("0");
                            }
                            if (secondarr.length > 1) {
                                id_minone.setText("" + secondarr[0]);
                                id_mintwo.setText("" + secondarr[1]);

                            } else {
                                id_minone.setText("" + secondarr[0]);
                                id_mintwo.setText("" + secondarr[1]);
//                                    id_minone.setText("0");
//                                    id_mintwo.setText("" + secondarr[0]);
                            }

                            /*} else {
                                id_time.setText("0");
                                id_timetwo.setText("0");
                                id_minone.setText("0");
                                id_mintwo.setText("0");
                            }*/
                        }

                        public void onFinish() {
                            id_time.setText("0");
                            id_timetwo.setText("0");
                            id_minone.setText("0");
                            id_mintwo.setText("0");
                            //txtcoundown.setText("TIME'S UP!!"); //On finish change timer text
                        }
                    }.start();
//                    System.out.println("MainWristActivity.callBoking <<<<<list2>" + bookingList.size() + "====" + minutearr.length + "====" + minute + "====" + secondarr.length + "====" + seconds);

                    break;
                }
            }
        } else {
            booking_id.setVisibility(View.GONE);
        }

    }
    /*private void startTimer(int noOfMinutes) {
        CountDownTimer countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                 String[] hmsvalue =hms.split(":");
                txtcoundown.setText(hmsvalue[1]+":"+hmsvalue[2]);//set text
            }
            public void onFinish() {
                txtcoundown.setText("TIME'S UP!!"); //On finish change timer text
            }
        }.start();

    }*/


    public void shareReferencePost(String refrenceCode) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Recipes");
        intent.putExtra(Intent.EXTRA_TEXT, refrenceCode);

        intent.putExtra("Module", "ReferenceCode");
        intent.setType("text/plain");
        mContext.startActivity(Intent.createChooser(intent, "ReferenceCode"));

    }

    String strdate;

    @Override
    public void onResume() {
        super.onResume();
        if (contacted) {
            confirmCallCompletedOrConfirmtoCall("Conversation Status");
        }
        strdate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        hithome(strdate);
    }

    boolean contacted;

    public void confirmCallCompletedOrConfirmtoCall(String title) {

        contacted = false;
        final Dialog dialog = new Dialog(mContext, R.style.MaterialDialogSheet);
        dialog.setContentView(R.layout.dialog_open_restaurant);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setTitle(" ");
        dialog.show();

        LinearLayout layout_cancle = (LinearLayout) dialog.findViewById(R.id.layout_cancle);
        TextView titleOpenNCloserestunt = (TextView) dialog.findViewById(R.id.titleOpenNCloserestunt);
        TextView subtitleOpenNCloserestunt = (TextView) dialog.findViewById(R.id.subtitleOpenNCloserestunt);
        final TextView cancleBtn = (TextView) dialog.findViewById(R.id.cancleBtn);
        TextView openRestaurantBtn = (TextView) dialog.findViewById(R.id.openRestaurantBtn);
        dialog.findViewById(R.id.store_detail).setVisibility(View.GONE);
        titleOpenNCloserestunt.setText(title);


        subtitleOpenNCloserestunt.setText("Your conversation is intrupted or You completed your conversation.");
        cancleBtn.setText("Failed");
        openRestaurantBtn.setText("Completed");
        openRestaurantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                updateCallStatus("Completed", null);

            }
        });
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                onCallFailed();
            }
        });

        layout_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Please confirm your status for conversation", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onCallFailed() {

        final Dialog dialog = new Dialog(mContext, R.style.MaterialDialogSheet);
        dialog.setContentView(R.layout.dialog_order_reason_tablemonk_admin);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setTitle(" ");
        dialog.show();

        LinearLayout layout_cancle = (LinearLayout) dialog.findViewById(R.id.layout_cancle);
        final TextView submitBtn = (TextView) dialog.findViewById(R.id.submitBtn);
        final RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radioGroup);
        final EditText edSugesstionText = (EditText) dialog.findViewById(R.id.edSugesstionText);
        final TextView title = (TextView) dialog.findViewById(R.id.txtDeclineOrder);

        title.setText("Call Failed Reason");
        final ArrayList<String> declineReasons = new ArrayList<>();
        declineReasons.add("Phone not reachable");
        declineReasons.add("Due to some issue, I am unable to call today.");
        declineReasons.add("I will call you after 10 to 15 min");
        declineReasons.add("Others(Please specify reason)");


        int i = 1;

        final String[] reasonString = {""};
        for (String r : declineReasons) {
            final RadioButton rdbtn = new RadioButton(mContext);
            rdbtn.setText("   " + r);
            rdbtn.setButtonDrawable(R.drawable.radio_selector);
            rdbtn.setTextColor(Color.parseColor("#706e6f"));

            rdbtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.sp12));
            //  rdbtn.setTextSize(16);

            rdbtn.setId(i);
            i++;
            rdbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                 @Override
                                                 public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                     if (b) {
                                                         reasonString[0] = compoundButton.getText().toString().substring(3);
                                                     }
                                                 }

                                             }

            );
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 7, 0, 7);
            radioGroup.addView(rdbtn, layoutParams);
        }

        edSugesstionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RadioButton) radioGroup.getChildAt(declineReasons.size() - 1)).setChecked(true);

            }
        });


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String other = edSugesstionText.getText().toString();
                if (reasonString[0].equalsIgnoreCase("Others(Please specify reason)"))
                    reasonString[0] = other;

                if (reasonString[0].equalsIgnoreCase(""))
                    Toast.makeText(mContext, "Please enter reason", Toast.LENGTH_LONG).show();
                else {
                    updateCallStatus("Failed", reasonString[0]);
//                    updateCallStatus("Contacted", reasonString[0]);
                    dialog.dismiss();
                }

            }
        });


        layout_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                confirmCallCompletedOrConfirmtoCall("Conversation Status");
            }
        });


    }

    private void updateCallStatus(String call_status, String reason) {
//        UpdateBookingStatusRequest request = new UpdateBookingStatusRequest(reason,this, call_status, bookSummary.BookingId);
//        RestApiController restApiController = new RestApiController(this, this, RestApiController.UPDATECALLSTATUS);
//        restApiController.makemebasedRequest(request, show);
        if (Utils.isNetworkAvailable(mContext)) {
            progressdialog = startProgressDialog(mContext, "Loading.....");
            progressdialog.show();
            volleyClient.makeRequest(WebServicesUrl.CategariesBooking, getVideoStatus(call_status, reason).toString(), "VideoStatus");
        } else {
            Toast.makeText(mContext, "No Internet connection!", Toast.LENGTH_LONG).show();
        }
    }

    private JSONObject getVideoStatus(String callStatus, String call_fail_reason) {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String strdate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        authorisedPreference = new AuthorisedPreference(mContext);
        JSONObject jsonObj = null;
        jsonObj = new JSONObject();
        try {
            jsonObj.put("action", "ChangeCallStatus");
            jsonObj.put("mewardid", authorisedPreference.getMewardId());
            jsonObj.put("tokenkey", authorisedPreference.getTokenKey());
            jsonObj.put("user_type", "selleradmin");
            jsonObj.put("has_seller", "true");
            jsonObj.put("encryption_status", "0");
            jsonObj.put("seller_uid", authorisedPreference.getString("app_sellerrid"));

            jsonObj.put("start_date", strdate);
            jsonObj.put("start_time", starttime);
            jsonObj.put("booking_version", "4");
            jsonObj.put("type", "booking");
            jsonObj.put("BookingId", bookingId);
            jsonObj.put("callStatus", callStatus);
            jsonObj.put("call_failed_reason", call_fail_reason);

            return jsonObj;
        } catch (Exception e) {
            return jsonObj;
        }
       /* {
            "action": "ChangeCallStatus",
             "mewardid": "74PFT15YQ1602148478",
                "tokenkey": "Y5OVS2AC81602588317_d3ec1951-abcf-4354-9c90-5d776e1d1126_ShOZpXKHR_bpSa25QWk",
                 "user_type": "selleradmin",
                "seller_uid": "740bb9c3-17ff-4ef8-8922-9de82a9a2471",
                "encryption_status": "0"

                "start_date":"2020-10-13",
                "start_time":"18:40",
                "booking_version":"4",
                "type": "booking",
                 "BookingId": "U5BY7IT9QW",

                "callStatus": "Failed",
                "call_failed_reason": "Network Connection",

        }
        {
            "customername": "Yash",
                "customerAge": "35",
                "customerGender": "Male",
                "customerProfilepic": "",
                "start_time": "14:00",
                "end_time": "14:30",
                "BookingId": "NRX7HEMIQG",
                "ItemBoxId": "61GYZDHUC",
                "seller_uid": "740bb9c3-17ff-4ef8-8922-9de82a9a2471"
        },*/
    }

    public void setDateHere() {
        int year = dateCalendar.get(Calendar.YEAR);
        int month = dateCalendar.get(Calendar.MONTH);
        int day = dateCalendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, R.style.DialogTheme, this, year, month, day);
        try {
            datePickerDialog.show();
        } catch (Exception e) {
        }
    }

    public void showDialog() {
        final Dialog dialog = new Dialog(mContext);
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
        LinearLayout tracker_user = (LinearLayout) dialog.findViewById(R.id.tracker_user);
//        LinearLayout txt_timeline = (LinearLayout) dialog.findViewById(R.id.txt_timeline);
//        LinearLayout id_follow_ups = (LinearLayout) dialog.findViewById(R.id.id_follow_ups);
       /* txt_timeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        order_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intentq = new Intent(getActivity(), OrderMarket.class);
//                startActivity(intentq);
            }
        });
        expertuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BookingHistoryDigital.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        tracker_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent currentIntent = new Intent(mContext.get(), FoodWorkoutUpcommingSesion.class);
                startActivity(currentIntent);*/
                Intent currentIntent = new Intent(mContext, TrackerAppListActivity.class);
                startActivity(currentIntent);
            }
        });
        /*id_follow_ups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FavoriteActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });*/

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
