package com.app.newuidashboardadmin.services;

import android.content.Context;

import com.app.newuidashboardadmin.clienttab.sevices.GetUserListRequest;
import com.app.newuidashboardadmin.firebase.SetDeviceRequest;
import com.app.newuidashboardadmin.plan.bean.request.GetSellerBookingSlotsRequest;
import com.app.newuidashboardadmin.plan.bean.request.GetSellerInstancesRequest;
import com.app.newuidashboardadmin.plan.bean.request.GetStandardPlan;
import com.app.newuidashboardadmin.todaysbooking.BookingTokSIDRequest;
import com.megogrid.megoauth.AuthorisedPreference;

import java.lang.ref.WeakReference;

import dmax.dialog.SpotsDialog;
import com.app.newuidashboardadmin.clienttab.sevices.ProfileSetupRequest;

public class RestApiController implements Response {
    private WeakReference<Context> contxt;
    private Response response;
    public Context mContext;
    private int responseType;
    private String url = "http://secureservice.megogrid.com/DemoApps/demoApps";
    private String urlAplha = "http://secureservice.megogrid.com/me_users/MeUser/meuser/";
    public static String DEMO_EXTENTION = "DemoApps/demoApps";
    private String push_url = "http://alphaservices13.migital.net/MasterService/megogrid";//"http://secureservice.megogrid.com/MasterService/megogrid";
    public static String USER_EXTENTION = "me_users/MeUser/meuser/";
    public static String BOOKING_EXTENTION = "booking/BookingV3/booking";
    public static String BASE_EXTENTION = "me_base/MeBase/mebase/";
    public static String Push_url_exte = "MasterService/megogrid";
    private String bookingUrl = "http://alphaservices13.migital.net/booking/BookingV3/booking";
    private String baseUrl = "http://alphaservices13.migital.net/me_base/MeBase/mebase/";
    public RestClient client;
    public static final int REGISTRATIONREQUEST = 27;
    public static final int GETUSERS = 16;


    public RestApiController(Context context, Response response, int responseType) {
        this.contxt = new WeakReference<>(context);
        this.response = response;
        mContext = context;
        this.responseType = responseType;
        client = new RestClient(contxt.get(), this);
        AuthorisedPreference authorisedPreference = new AuthorisedPreference(context);
        if (authorisedPreference.getString(AuthorisedPreference.MegoBase_KEY).length() > 2) {
            url = authorisedPreference.getString(AuthorisedPreference.MegoBase_KEY) + DEMO_EXTENTION;
            urlAplha = authorisedPreference.getString(AuthorisedPreference.MegoBase_KEY) + USER_EXTENTION;
            bookingUrl = authorisedPreference.getString(AuthorisedPreference.MegoBase_KEY) + BOOKING_EXTENTION;
            baseUrl = authorisedPreference.getString(AuthorisedPreference.MegoBase_KEY) + BASE_EXTENTION;
//            push_url = authorisedPreference.getString(AuthorisedPreference.MegoBase_KEY) + Push_url_exte;
        }

    }


   /* public void makeUpdateRequest(UpdateAppRequest request) {
        client.Communicate(url, request, responseType);

    }

    public void setDeviceIdRequest(SetDeviceRequest request) {
        client.Communicate(push_url, request, responseType);

    }*/

    @Override
    public void onResponseObtained(Object response, int responseType,
                                   boolean iscached) {
        stopDialog();
        this.response.onResponseObtained(response, responseType, iscached);
    }

    @Override
    public void onErrorObtained(String errormsg, int responseType) {

        this.response.onErrorObtained(errormsg, responseType);
    }

   /* public void getLederboardData(LeaderboardRequest request) {
        client.Communicate(urlAplha, request, responseType);

    }*/

    public void makemebasedRequest(BookingTokSIDRequest request) {
        client.Communicate(bookingUrl, request, responseType);
    }

    public void fetchBookingHisory(GetSellerBookingSlotsRequest request) {
        client.Communicate(bookingUrl, request, responseType);
    }

    public void fetchStandardPlan(GetStandardPlan request) {
        client.Communicate(baseUrl, request, responseType);
    }

    public void fetchInstnce(GetSellerInstancesRequest request) {
        client.Communicate(baseUrl, request, responseType);
    }

    public void makemebasedRequest(GetSellerBookingSlotsRequest request, boolean b) {
        if(b)
            startDialog();
        client.Communicate(bookingUrl, request, responseType);

    }

    public void makemebasedRequest(ProfileSetupRequest request, boolean show) {
        if(show)
            startDialog();
        client.Communicate(urlAplha, request, responseType);

    }
    SpotsDialog progressdialog;
    public void startDialog() {
        if(progressdialog!=null){
            progressdialog.dismiss();
            progressdialog.cancel();
            progressdialog=null;

        }
        progressdialog = new SpotsDialog(mContext, "loading");
        progressdialog.setCancelable(true);
        progressdialog.setCanceledOnTouchOutside(true);
        progressdialog.show();
    }
    public void stopDialog() {
        if(progressdialog!=null){
            progressdialog.dismiss();
            progressdialog.cancel();
            progressdialog=null;

        }

    }
    /*public void setDeviceIdRequest(SetDeviceRequest request) {
        if(BaseUtility.isValid(request.tokenkey) && BaseUtility.isValid(request.mac_id) && BaseUtility.isValid(request.device_id) )
            client.Communicate(push_url, request, responseType);

    }*/
    public void setDeviceIdRequest(SetDeviceRequest request) {
        client.Communicate(push_url, request, responseType);

    }
}


