package com.app.newuidashboardadmin.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
;
import androidx.appcompat.app.AppCompatActivity;

import com.app.newuidashboardadmin.AdminUI;
import com.app.newuidashboardadmin.MyLogger;
import com.app.newuidashboardadmin.R;
import com.app.newuidashboardadmin.Utility.AppPrefernce;
import com.app.newuidashboardadmin.plan.callback.InstanceListActivity;
import com.megogrid.activities.ModuleHandler;
import com.megogrid.activities.ProfileDetailsResponse;
import com.megogrid.megoauth.AuthUtility;
import com.megogrid.megoauth.AuthorisedPreference;
import com.megogrid.megouser.MegoUserException;
import com.megogrid.megouser.MegoUserSDK;
import com.megogrid.megouser.sdkinterfaces.IAdvanceHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogInpage extends AppCompatActivity implements View.OnClickListener,IAdvanceHandler {
    private EditText emailId, passwordetx;
    private TextView showpass,forgotPwd,login;
    LinearLayout layout_toolbar;
    private AuthorisedPreference authorisedPreference;
    EditText ed_sellerId;

    String password,email,sellerIDShort;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newui_signlogin);
        authorisedPreference = new AuthorisedPreference(this);
        //AuthUtility.setThemeColorInStatusBar(this);
        if (Build.VERSION.SDK_INT >= 21)
        {
            getWindow().setStatusBarColor(AuthUtility.getDarkColor(Color.parseColor("#00A6bc")));
        }
        setUpViews();
        startLogin();

    }

    private void setUpViews()
    {
        //layout_toolbar = (LinearLayout) findViewById(R.id.layout_toolbar);
        //layout_toolbar.setBackgroundColor(Color.parseColor(authorisedPreference.getThemeColor()));
        ed_sellerId = (EditText) findViewById(R.id.ed_sellerId);
        emailId = (EditText) findViewById(R.id.email);
        passwordetx = (EditText) findViewById(R.id.password);
//        forgotPwd = (TextView) findViewById(R.id.forgotpassword);
//        showpass = (TextView) findViewById(R.id.show);
        login = (TextView) findViewById(R.id.tv_next);
        //login.setBackgroundColor(Color.parseColor(authorisedPreference.getThemeColor()));


        sellerIDShort=getIntent().getStringExtra("sellerIDShort");
        password=getIntent().getStringExtra("password");
        email=getIntent().getStringExtra("email");

        ed_sellerId.setText(sellerIDShort);
        ed_sellerId.setEnabled(false);
        ed_sellerId.setFocusable(false);
        passwordetx.setText(password);
        emailId.setText(email);

        login.setOnClickListener(this);
//        showpass.setOnClickListener(this);
//        forgotPwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
//            case R.id.show:
//                if (showpass.getText().toString().equalsIgnoreCase("show"))
//                {
//                    passwordetx.setInputType(InputType.TYPE_CLASS_TEXT);
//                    showpass.setText("HIDE");
//                }
//                else
//                {
//                    showpass.setText("SHOW");
//                    passwordetx.setInputType(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                }
//                break;

            case R.id.tv_next:
                System.out.println("LogInpage.onClick");
                password = passwordetx.getText().toString();
                email = emailId.getText().toString();
                if (!isValid(email)) {
                    emailId.startAnimation(AnimationUtils.loadAnimation(LogInpage.this, R.anim.shake));
                    Toast.makeText(LogInpage.this,"Please enter your email id", Toast.LENGTH_SHORT).show();
                }
                else if(!emailValidator(email)){
                    Toast.makeText(this, "Enter valid email address", Toast.LENGTH_SHORT).show();
                }
                else if (!isValid(password)) {
                    passwordetx.startAnimation(AnimationUtils.loadAnimation(LogInpage.this, R.anim.shake));
                    Toast.makeText(LogInpage.this,"Please enter your password", Toast.LENGTH_SHORT).show();
                }
                else {
                        startLogin();
                }
                break;

//            case R.id.forgotpassword:
//                onForgotPassword();
//                break;


        }
    }

    private void startLogin() {

        MegoUserSDK sdk = MegoUserSDK.getInstance(this, this);
        try {
            sdk.initialize(MegoUserSDK.MegoUserType.EMAIL_LOGIN,email, password);

        } catch (MegoUserException e) {
            e.printStackTrace();
        }
    }

    public boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
//    public void onForgotPassword()
//    {
//        final Dialog dialog = new Dialog(LogInpage.this, R.style.MaterialDialogSheet);
//        dialog.setContentView(R.layout.dialog_forgot_password);
//        dialog.setTitle(" ");
//
//        final LinearLayout title = (LinearLayout) dialog.findViewById(R.id.TopLL);
//        GradientDrawable drawable1 = (GradientDrawable) title.getBackground();
//        drawable1.setColor(Color.parseColor(authorisedPreference.getThemeColor()));
//
//
//        final TextView txtSubHeader = (TextView) dialog.findViewById(R.id.txtSubHeader);
//        final EditText forgot_emailId = (EditText) dialog.findViewById(R.id.forgot_emailId);
//        final TextView sendBtn = (TextView) dialog.findViewById(R.id.sendBtn);
//        GradientDrawable drawable = (GradientDrawable) sendBtn.getBackground();
//        drawable.setColor(Color.parseColor(authorisedPreference.getThemeColor()));
//
//        txtSubHeader.setTextColor(Color.parseColor(authorisedPreference.getThemeColor()));
//
//        sendBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String edEmailreg=forgot_emailId.getText().toString();
//                if(edEmailreg.equalsIgnoreCase(""))
//                    Toast.makeText(LogInpage.this,"Please enter registered id", Toast.LENGTH_SHORT).show();
//                else if(!emailValidator(edEmailreg))
//                    Toast.makeText(LogInpage.this, "Enter valid email address", Toast.LENGTH_SHORT).show();
//                else
//                {
//                    MegoUserSDK megoUserSDK = MegoUserSDK.getInstance(LogInpage.this, LogInpage.this);
//
//                    try {
//                        megoUserSDK.initialize(MegoUserSDK.MegoUserType.FORGOT_PASW, edEmailreg);
//                    }
//                    catch (MegoUserException e) {
//                        e.printStackTrace();
//                    }
//                    dialog.dismiss();
//                }
//            }
//        });
//
//        dialog.show();
//
//    }

    public static boolean isValid(String imageView) {
        return (imageView != null && !imageView.trim().equalsIgnoreCase("NA") &&
                !imageView.trim().equalsIgnoreCase(""));
    }


    @Override
    public void onResponse(MegoUserSDK.MegoUserType megoUserType, MegoUserException e, ProfileDetailsResponse profileDetailsResponse) {

        System.out.println("LogInpage.onResponse exception =======  "+e + "   "+ megoUserType);
        if(e==null) {
            AppPrefernce prefernce=new AppPrefernce(this);
            switch (megoUserType)
            {
                case EMAIL_LOGIN:

                    MyLogger.println("LogInpage.onRespon>>>>>makeSessionRequest>>0>>>>>> "+profileDetailsResponse.profilepic);
                            prefernce.setProfilePic(profileDetailsResponse.profilepic);
                    prefernce.setString("logIn","LoggedIn");
                    prefernce.setString("userName",email);
                    prefernce.setString("password",password);


                    Intent intent = new Intent(this, InstanceListActivity.class);
                    startActivity(intent);
                    finish();


                    break;

                case FORGOT_PASW:
                    ModuleHandler.logEvent(this,ModuleHandler.EVENT_FORGET_PASSWORD);
                    break;
            }
        }
    }


}
