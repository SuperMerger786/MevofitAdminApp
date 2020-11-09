package com.app.newuidashboardadmin;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.contact.util.ContactSdk;
import com.google.gson.Gson;
import com.megogrid.megoauth.AuthorisedPreference;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
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
    TextView book_count;

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
        volleyClient = new VolleyClient(mContext, this);
        gson = new Gson();
        MyLogger.println("volleyclient Inside  on oncreate==");
        dateCalendar = Calendar.getInstance();
        frag_menu = (ImageView) view.findViewById(R.id.frag_menu_id);
        frag_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showDialog();
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

        } else {
            booking_status.setVisibility(View.GONE);
        }
        MyLogger.println("todays>>>>>booking>>2>" + adminDashboard.getData().getUpcomingBookings().getBookedList());
        if (adminDashboard.getData().getUpcomingBookings().getBookedList() != null) {
            bookingSetUp((ArrayList<BookedList>) adminDashboard.getData().getUpcomingBookings().getBookedList());
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
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    listenerSet(response);
                }
            }, 2000);
            MyLogger.println("GetHomePublishData>>>check>>>>>1>>>>>>22222>" + reqTag);
        } else {

        }
    }

    @Override
    public void onServerResponseError(String reqTag, String errorMessage) {

    }


    private void bookingSetUp(final ArrayList<BookedList> bookinglist) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("MainWristActivity.callBoking call");
                displayListUpCommingClasses(bookinglist);

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
        authorisedPreference=new AuthorisedPreference(mContext);
        MyLogger.println("seller_id>>>>>>>>>0> "+authorisedPreference.getString("app_sellerrid"));
        authorisedPreference.setSellerId("740bb9c3-17ff-4ef8-8922-9de82a9a2471");
        authorisedPreference.setString("app_sellerrid","740bb9c3-17ff-4ef8-8922-9de82a9a2471");
        MyLogger.println("seller_id>>>>>>>>>1> "+authorisedPreference.getString("app_sellerrid"));
        JSONObject jsonObj = null;
        jsonObj = new JSONObject();
        try {
            jsonObj.put("action", "GetHomeDashBoard");
            jsonObj.put("mewardid", "74PFT15YQ1602148478");
            jsonObj.put("tokenkey", "Y5OVS2AC81602588317_d3ec1951-abcf-4354-9c90-5d776e1d1126_ShOZpXKHR_bpSa25QWk");
            jsonObj.put("user_type", "developer");
            jsonObj.put("requestTime", "1601976588925");
            jsonObj.put("seller_uid", authorisedPreference.getString("app_sellerrid"));
            jsonObj.put("isadmin", "1");
            jsonObj.put("store_id", "");
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
                ContactSdk.PublisherVideoCall(mContext, "30",
                        todaylistnew.getCustomername(), todaylistnew.getCustomerProfilepic(), todaylistnew.getCustomerProfilepic(),
                        sidResponse.TokBoxTokenID, sidResponse.TokBoxSID, "");
//            sendPush(PushLogger.EVENT_CALL_TO_CUSTOMER);
            }

            @Override
            public void onErrorObtained(String errormsg, int responseType) {
                if (progressdialog != null && progressdialog.isShowing())
                    progressdialog.dismiss();

            }
        }, 0);
        controller.makemebasedRequest(request);
    }
}
