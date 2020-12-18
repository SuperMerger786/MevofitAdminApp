package com.app.newuidashboardadmin.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.view.WindowManager;


import androidx.appcompat.app.AppCompatActivity;

import com.app.newuidashboardadmin.AdminUI;
import com.app.newuidashboardadmin.MyLogger;
import com.app.newuidashboardadmin.R;
import com.app.newuidashboardadmin.Utility.AppPrefernce;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.megogrid.megoauth.AuthorisedPreference;
import com.megogrid.megoeventssdkhandler.ActionSdkIntializer;
import com.megogrid.megouser.MegoUserConfig;
import com.megogrid.megouser.MegoUserException;
import com.megogrid.megouser.sdkinterfaces.IUpdateAccount;


public class Admin extends AppCompatActivity {

//    Dialog dialog;
//    boolean is_true = true;
//    private static final int SYSTEM_ALERT_WINDOW_PERMISSION = 2084;

    AppPrefernce prefernce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.new_loading_screen);
        initHeader();
        initMegoEvent();
        prefernce = new AppPrefernce(this);

        FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                .setGcmSenderId("928900584668")
                .setApiKey("AIzaSyA0yafBpA9aGU_XwtADlipesU01-2R2xCE")
                .setApplicationId(getApplicationContext().getPackageName())
                .setStorageBucket("testproject-b2393.appspot.com")
                .setDatabaseUrl("https://testproject-b2393.firebaseio.com")
                .setStorageBucket("testproject-b2393.appspot.com")
                .build();

        MyLogger.println("check>>>>>makeSessionRequest>>Admin" + (FirebaseApp.getApps(Admin.this).isEmpty()));
        if (FirebaseApp.getApps(Admin.this).isEmpty())
            FirebaseApp.initializeApp(getApplicationContext(), firebaseOptions);


        new MegoUserConfig.ConfigBuilder(Admin.this).setGoogleKey("").enableResourceAppIcon().enableResourceAppBackground().build();
        com.megogrid.megouser.MegoUser.getInstance(Admin.this).initSDK();
        com.megogrid.megouser.MegoUser.getInstance(Admin.this).initializeAnonymous(new IUpdateAccount() {
            @Override
            public void onComplete(MegoUserException e) {

            }
        });

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                if (prefernce.getString("logIn") == null) {
                    Intent intent = new Intent(Admin.this, LogInpage.class);
                    intent.putExtra("sellerIDShort", getIntent().getStringExtra("sellerIDShort"));
                    intent.putExtra("password", getIntent().getStringExtra("password"));
                    intent.putExtra("email", getIntent().getStringExtra("email"));
                    startActivity(intent);
                    finish();
                } else {

                    Intent intent = new Intent(Admin.this, AdminUI.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);

    }


    public void initMegoEvent() {
        ActionSdkIntializer actionSdkIntializer = new ActionSdkIntializer(Admin.this);
        actionSdkIntializer.intializeSdk();
//        eventTrigger = new MevoEventTrigger(this, "MainWristActivity");
    }

    public void initHeader() {
        AuthorisedPreference authorisedPreference = new AuthorisedPreference(this);

//        Toolbar tool = (Toolbar) findViewById(R.id.tool);
//        setSupportActionBar(tool);
//        ActionBar actionbar = getSupportActionBar();
//        actionbar.setDisplayHomeAsUpEnabled(false);
//        actionbar.setTitle("");
//        TextView toolTitle = (TextView) findViewById(R.id.tooltitle);
//        tool.setBackgroundColor(Color.parseColor(authorisedPreference.getThemeColor()));
//        toolTitle.setTextColor(ContextCompat.getColor(this, R.color.white));
//        toolTitle.setGravity(Gravity.LEFT);


    }


}