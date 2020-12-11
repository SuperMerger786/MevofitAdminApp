package com.app.newuidashboardadmin;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.app.newuidashboardadmin.Utility.AppPrefernce;
import com.app.newuidashboardadmin.admingraph.GraphDatum;
import com.app.newuidashboardadmin.admingraph.GraphValues;
import com.app.newuidashboardadmin.newadmin.BookedList;
import com.app.newuidashboardadmin.newadmin.BookingPerformance;
import com.app.newuidashboardadmin.newadmin.NewDashboardEntity;
import com.app.newuidashboardadmin.newadmin.TodayBooking;
import com.app.newuidashboardadmin.services.Response;
import com.app.newuidashboardadmin.services.RestApiController;
import com.app.newuidashboardadmin.services.VolleyClient;
import com.app.newuidashboardadmin.services.WebServicesUrl;
import com.app.newuidashboardadmin.todaysbooking.BookingTokSIDRequest;
import com.app.newuidashboardadmin.todaysbooking.BookingTokSIDResponse;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
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
import com.megogrid.activities.MeUserSDKMevo;
import com.megogrid.megoauth.AuthorisedPreference;
import com.megogrid.megoeventbuilder.bean.Events;
import com.megogrid.megoeventpersistence.MewardDbHandler;
import com.megogrid.megoeventssdkhandler.ActionPerformer;
import com.megogrid.megouser.MegoUser;
import com.migital.digiproducthelper.extraui.NotificatinSetingactivity;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import android.view.View.MeasureSpec;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import dmax.dialog.SpotsDialog;
import newui_food.foodnewui.MyUpCommingClassAdminAdapter;

public class AdminDashBoardNewFragment extends Fragment implements IResponseUpdater {
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

    SpotsDialog progressdialog;
    ListView booking_list;
    LineChart chart;
    LinearLayout add_entry;
    AppPrefernce appPrefernce;
    TextView id_date, id_time, id_timetwo, id_minone, id_mintwo;
    LinearLayout booking_id;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_home_new, container, false);
        recycerView_upcomming = (RecyclerView) view.findViewById(R.id.recycerView_upcomming);
        ln_upcomming_class = (FrameLayout) view.findViewById(R.id.ln_upcomming_class);
        booking_list = (ListView) view.findViewById(R.id.booking_list);
        iv_pre = (ImageView) view.findViewById(R.id.iv_pre);
        booking_status = (CardView) view.findViewById(R.id.booking_status);
        book_count = (TextView) view.findViewById(R.id.book_count);
        total_booking = (TextView) view.findViewById(R.id.total_booking);
        total_earn = (TextView) view.findViewById(R.id.total_earn);
        total_avrrate = (TextView) view.findViewById(R.id.total_avrrate);
        booking_tab = (TextView) view.findViewById(R.id.booking_tab);
        payment_tab = (TextView) view.findViewById(R.id.payment_tab);

        //booking counter

        id_time = (TextView) view.findViewById(R.id.id_time);
        id_timetwo = (TextView) view.findViewById(R.id.id_timetwo);
        id_minone = (TextView) view.findViewById(R.id.id_minone);
        id_mintwo = (TextView) view.findViewById(R.id.id_minone);
        booking_id = (LinearLayout) view.findViewById(R.id.booking_id);

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
//                showDialog();
            }
        });
        iv_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NotificatinSetingactivity.class);
                startActivity(intent);
            }
        });
        hithome();

        return view;
    }

    private void hithome() {
        progressdialog = startProgressDialog(mContext, "Loading.....");
        progressdialog.show();
        volleyClient.makeRequest(WebServicesUrl.Categaries, getHome().toString(), "Adminhome");
    }

    private void hitGraphValues() {
        progressdialog = startProgressDialog(mContext, "Loading.....");
        progressdialog.show();
        volleyClient.makeRequest(WebServicesUrl.CategariesBooking, getGraphValues().toString(), "GraphValues");
    }

    private void listenerSet(String response) {

//        String response = loadJSONFromAsset();
        NewDashboardEntity adminDashboard = gson.fromJson(response, NewDashboardEntity.class);

        setBookingPerFormance(adminDashboard.getData().getBookingPerformance());
        MyLogger.println("todays>>>>>booking>>>" + adminDashboard.getData().getTodayBookings().size());
        if (adminDashboard.getData().getTodayBookings() != null && adminDashboard.getData().getTodayBookings().size() > 0) {
            booking_status.setVisibility(View.VISIBLE);
            booking_list.setAdapter(new BookingTodaysItemAdapter((ArrayList<TodayBooking>) adminDashboard.getData().getTodayBookings()));
            setListHeight(booking_list);
            book_count.setText("" + adminDashboard.getData().getTodayBookings().size());
            SetCounter(adminDashboard.getData().getTodayBookings());
        } else {
            booking_status.setVisibility(View.GONE);
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
        total_booking.setText(bookingPerFormance.getTotalBookings());
//        Currency currency = Currency.getInstance(bookingPerFormance.getCurrencySymbol());
//        String symbol = currency.getSymbol();
        total_earn.setText(getCurrencySymbol(bookingPerFormance.getCurrencySymbol()) + " " + bookingPerFormance.getTotalEarnings());
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
        if (response != null && reqTag.equalsIgnoreCase("Adminhome")) {
//            imagelayout.stopShimmer();
//            imagelayout.setVisibility(View.VISIBLE);
            if (progressdialog != null && progressdialog.isShowing())
                progressdialog.dismiss();
            MyLogger.println("GetHomePublishData>>>check>>>>>1>>>>>>>" + reqTag + "===========" + response);
            hitGraphValues();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    listenerSet(response);
                }
            }, 2000);
            MyLogger.println("GetHomePublishData>>>check>>>>>1>>>>>>22222>" + reqTag);
        } else {
            if (progressdialog != null && progressdialog.isShowing())
                progressdialog.dismiss();
            adminDashboard = gson.fromJson(response, GraphValues.class);
            initializeChart(chart, "booking");
        }

    }

    GraphValues adminDashboard;

    @Override
    public void onServerResponseError(String reqTag, String errorMessage) {

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

    class BookingTodaysItemAdapter extends BaseAdapter {

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
            CircularImageView imgview = (CircularImageView) view.findViewById(R.id.iv_profile_dummy);
            TextView start_endtime = (TextView) view.findViewById(R.id.start_endtime);
            TextView btn_starte = (TextView) view.findViewById(R.id.btn_starte);
            name.setText(todaylist.get(position).getCustomername());
            tv_male.setText(todaylist.get(position).getCustomerAge() + "," + todaylist.get(position).getCustomerGender());
            MyLogger.println("image>>>>>" + todaylist.get(position).getCustomerProfilepic());
            Glide.with(mContext)
                    .load(todaylist.get(position).getCustomerProfilepic())
                    .error(Glide.with(imgview).load(R.drawable.profile))
                    .into(imgview);
            start_endtime.setText(todaylist.get(position).getStartTime() + "-" + todaylist.get(position).getEndTime());
            btn_starte.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    todaylistnew = todaylist.get(position);
                    makeSessionRequest(todaylist.get(position).getItemBoxId(), todaylist.get(position).getBookingId(), todaylist.get(position).getStartTime());
                }
            });
            return view;
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

    private JSONObject getHome() {
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

    private void makeSessionRequest(String itemBoxId, String bookedboxId, String start_time) {
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
                MyLogger.println("check>>>>>makeSessionRequest>>0 " + response.toString() + " " + CallUtility.currentbookingid + " " + todaylistnew.getBookingId());
                ContactSdk.PublisherVideoCall(mContext, "30",
                        todaylistnew.getCustomername(), appPrefernce.getProfilePic(), todaylistnew.getCustomerProfilepic(),
                        sidResponse.TokBoxTokenID, sidResponse.TokBoxSID, sidResponse.TokApiKey, "11");
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
        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        l.setMaxSizePercent(4);
        mChart.invalidate();
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
        MyLogger.println("<<<< graphValues setting graph 1111 : " + xVals.size() + " <<<y.size>>> " + yVals.size() + " <<<totalEntries>>> " + totalEntries);
        if (totalEntries > 0) {
            chart.setVisibility(View.VISIBLE);
            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
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
        LineDataSet set2 = new LineDataSet(val, "");

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
                if ((getMilliFromDate(bookingList.get(i).getStartTime()) > System.currentTimeMillis())) {
                    int hour = (int) (((getMilliFromDate(bookingList.get(i).getStartTime()) - System.currentTimeMillis()) / 1000) / 3600);
                    String minute = "" + ((((getMilliFromDate(bookingList.get(i).getStartTime()) - System.currentTimeMillis()) / 1000) % 3600) / 60);
                    String seconds = "" + ((((getMilliFromDate(bookingList.get(i).getStartTime()) - System.currentTimeMillis()) / 1000) % 3600) % 60);
                    int[] minutearr = setIntArray(minute);
                    int[] secondarr = setIntArray(seconds);
//                    System.out.println("MainWristActivity.callBoking <<<<<list2>" + bookingList.size() + "====" + minutearr.length + "====" + minute + "====" + secondarr.length + "====" + seconds);
                    if (hour == 0) {
                        if (Integer.parseInt(minute) >= 0) {
                            if (minutearr.length > 1) {
                                id_time.setText("" + minutearr[0]);
                                id_timetwo.setText("" + minutearr[1]);
                            } else {
                                id_time.setText("0");
                                id_timetwo.setText("" + minutearr[0]);
                            }
                        } else {
                            id_time.setText("0");
                            id_timetwo.setText("0");
                        }
                        if (secondarr.length > 1) {
                            id_minone.setText("" + secondarr[0]);
                            id_mintwo.setText("" + secondarr[1]);

                        } else {
                            id_minone.setText("0");
                            id_mintwo.setText("" + secondarr[0]);
                        }
                    } else {
                        id_time.setText("0");
                        id_timetwo.setText("0");
                        id_minone.setText("0");
                        id_mintwo.setText("0");
                    }
                    break;
                }
            }
        } else {
            booking_id.setVisibility(View.GONE);
        }

    }
}
