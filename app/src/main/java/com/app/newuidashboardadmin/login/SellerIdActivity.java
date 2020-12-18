package com.app.newuidashboardadmin.login;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.newuidashboardadmin.AdminUI;
import com.app.newuidashboardadmin.R;
import com.app.newuidashboardadmin.Utility.AppPrefernce;
import com.app.newuidashboardadmin.services.WebServicesUrl;
import com.app.newuidashboardadmin.splash.LoadingScreen;
import com.megogrid.megoauth.AuthUtility;
import com.megogrid.megoauth.AuthorisedPreference;
import com.megogrid.megoauth.IAuthorized;
import com.megogrid.megoauth.MegoAuthorizer;
import com.megogrid.rest.AuthController;
import com.megogrid.runtimepermission.IPermission;
import com.megogrid.runtimepermission.RuntimePermission;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by test on 27/12/18.
 */

public class SellerIdActivity extends AppCompatActivity implements IAuthorized, View.OnClickListener {
    EditText ed_sellerId;
    TextView btnSubmitSellerId;
    private EditText emailId, passwordetx;

    ImageView back;
    String sellerIDShort;
    RuntimePermission runtimePermission;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_trans);
        initiate();

    }

    protected void initiate() {
        //Fabric.with(this, new Crashlytics());
        String[] perms = {Manifest.permission.READ_PHONE_STATE};

        runtimePermission = new RuntimePermission(SellerIdActivity.this, new IPermission() {
            @Override
            public void onPermissionGranted(String[] strings) {


            }

            @Override
            public void onPermissionDenied() {
                finish();
                System.out.println("SellerIdActivity.onPermissionDenied");
            }
        });
        runtimePermission.requestPermission(perms);
        AppPrefernce prefernce = new AppPrefernce(this);
        /*new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {*/
                if (prefernce.getString("logIn") == null) {
                    setContentView(R.layout.newui_signlogin);
                    AuthorisedPreference authorisedPreferencee = new AuthorisedPreference(SellerIdActivity.this);
                    authorisedPreferencee.setString(AuthorisedPreference.APP_SELLER_ID, "");
                    authorisedPreferencee.setTokenKey("NA");
                    authorisedPreferencee.setMewardId("NA");
                    initViews();
                } else if (prefernce.getString("logIn") != null) {
                    Intent in = new Intent(SellerIdActivity.this, Admin.class);
                    in.putExtra("from", "Loading");
                    startActivity(in);
                    finish();
                }
           /* }
        }, 100);*/
    }

    public void initViews() {

        ed_sellerId = (EditText) findViewById(R.id.ed_sellerId);
        btnSubmitSellerId = (TextView) findViewById(R.id.tv_next);
        emailId = (EditText) findViewById(R.id.email);
        passwordetx = (EditText) findViewById(R.id.password);
        btnSubmitSellerId.setOnClickListener(this);


        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(AuthUtility.getDarkColor(Color.parseColor("#00A6bc")));
        }


    }

    String password, email;

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tv_next:

                if (isNetworkOnline()) {
                    startDialog();
                    sellerIDShort = ed_sellerId.getText().toString(); //"b15b5c71-0583-49d9-b3ed-dff0b5f74d57";//
                    if (!isValid(sellerIDShort)) {
                        stopDialog();
                        ed_sellerId.startAnimation(AnimationUtils.loadAnimation(SellerIdActivity.this, R.anim.shake));
                        Toast.makeText(SellerIdActivity.this, "Please enter your correct Customer ID", Toast.LENGTH_LONG).show();
                    } else {
                        password = passwordetx.getText().toString();
                        email = emailId.getText().toString();
                        if (!isValid(email)) {
                            stopDialog();
                            emailId.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
                            Toast.makeText(this, "Please enter your email id", Toast.LENGTH_SHORT).show();
                        } else if (!emailValidator(email)) {
                            stopDialog();
                            Toast.makeText(this, "Enter valid email address", Toast.LENGTH_SHORT).show();
                        } else if (!isValid(password)) {
                            stopDialog();
                            passwordetx.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
                            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
                        } else {
//                         AppPrefernce prefernce = new AppPrefernce(SellerIdActivity.this);
//                         prefernce.setString("sellerID",sellerID);
                            InitiazeAuth();
                        }
                    }
                } else {
                    Toast.makeText(SellerIdActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void startDialog() {
        try {
            progressDialog = new ProgressDialog(SellerIdActivity.this, R.style.MyTheme);
            //   SpannableString ss2 = new SpannableString("Building your app data please wait");
            //    ss2.setSpan(new RelativeSizeSpan(getResources().getInteger(R.integer.splash_text_size_diloge)), 0, ss2.length(), 0);
            //    progressDialog.setMessage(ss2);
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
            progressDialog.getWindow().setGravity(Gravity.BOTTOM);
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean isValid(String imageView) {
        return (imageView != null && !imageView.trim().equalsIgnoreCase("NA") &&
                !imageView.trim().equalsIgnoreCase("") && imageView.length() > 4);
    }


    private void InitiazeAuth() {

        final MegoAuthorizer megoAuthorizer = new MegoAuthorizer(SellerIdActivity.this);
        AuthorisedPreference authorisedPreference = new AuthorisedPreference(SellerIdActivity.this);
        authorisedPreference.setString(AuthorisedPreference.DYNAMIC_LINK, WebServicesUrl.MEGO_HOST_LINK);


        authorisedPreference.setString(AuthorisedPreference.SELLER_SHORT_ID_KEY, sellerIDShort);
        authorisedPreference.setString(AuthorisedPreference.APP_ID_KEY, getResources().getString(R.string.appId));
        authorisedPreference.setString(AuthorisedPreference.SECRET_ID_KEY, getResources().getString(R.string.appSecret));
        authorisedPreference.setString(AuthorisedPreference.DEV_ID_KEY, getResources().getString(R.string.devId));
        authorisedPreference.setString(AuthorisedPreference.KEY_APPTYPE, "admin");
        // for message center
//        AppPreference appPreference = new AppPreference(SellerIdActivity.this);
//        appPreference.setString(AppPreference.APPID, getResources().getString(R.string.appId));
//        appPreference.setString(AppPreference.SECRETKEY, getResources().getString(R.string.appSecret));
//        appPreference.setString(AppPreference.DEVID, getResources().getString(R.string.devId));

        AuthController.setHostMode(true);
        megoAuthorizer.register(this);
        authorisedPreference.setIsSellerApp(false);
        //authorisedPreference.setSellerId(sellerIDShort);
        megoAuthorizer.initializeSdk();

    }


    @Override
    public void onSucessAuthorization(String s, String s1, String s2) {

        System.out.println("<<<<<<<<  OnSucesss s ====== ");


        AuthorisedPreference authorisedPreference = new AuthorisedPreference(SellerIdActivity.this);
        System.out.println("value is here " + authorisedPreference.getMewardId());

        if (authorisedPreference.getMewardId() != null && authorisedPreference.getMewardId().length() > 5) {
            stopDialog();

            AuthorisedPreference authorisedPreferencee = new AuthorisedPreference(SellerIdActivity.this);
            AppPrefernce prefernce = new AppPrefernce(SellerIdActivity.this);

            prefernce.setString("sellerID", authorisedPreferencee.getString(AuthorisedPreference.APP_SELLER_ID));
            System.out.println("<<<<<<<<<<APP_SELLER_ID =======   " + authorisedPreferencee.getString(AuthorisedPreference.APP_SELLER_ID));

            Intent intent = new Intent(SellerIdActivity.this, Admin.class);
            intent.putExtra("sellerIDShort", sellerIDShort);
            intent.putExtra("password", password);
            intent.putExtra("email", email);
            startActivity(intent);
            finish();

            System.out.println("<<<<<<<<  OnSucesss Success ====== ");

        } else {
            stopDialog();
            System.out.println("<<<<<<<<  OnSucesss Failure ====== ");
            Toast.makeText(SellerIdActivity.this, "Please Enter Valid Unique Customer ID", Toast.LENGTH_LONG).show();
        }

    }

/*    @Override
    public void onSucessGetHost(String str) {

       // System.out.println("<<<<<<<<  onSucessGetHost ====== ");


    }*/


    @Override
    public void onFailureAuthorization(String s) {
        stopDialog();
        Toast.makeText(SellerIdActivity.this, s, Toast.LENGTH_SHORT).show();

        System.out.println("<<<<<<<<  onFailureAuthorization ====== ");

    }


    private void stopDialog() {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isNetworkOnline() {
        boolean status = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connectivityManager.getNetworkInfo(0);
            if (info != null && info.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                info = connectivityManager.getNetworkInfo(1);
                if (info != null
                        && info.getState() == NetworkInfo.State.CONNECTED) {
                    status = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = false;
        }
        return status;
    }

}
