

package com.app.newuidashboardadmin;

import android.app.Application;
import android.text.TextUtils;

import androidx.multidex.MultiDex;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

//@ReportsCrashes(mailTo = "anilvns09@gmail.com", customReportContent = {
//        ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME,
//        ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL,
//        ReportField.CUSTOM_DATA, ReportField.STACK_TRACE, ReportField.LOGCAT}, mode = ReportingInteractionMode.TOAST, resToastText = R.string.crash_toast_text)
public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static AppController mInstance;

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        MultiDex.install(this);
//        FirebaseApp.initializeApp(this);


    }
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    public <T> void addToRequestQueue(JsonObjectRequest req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }
}