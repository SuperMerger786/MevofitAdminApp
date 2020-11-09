package com.app.newuidashboardadmin.splash;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;

import com.app.newuidashboardadmin.AdminUI;
import com.app.newuidashboardadmin.MyLogger;
import com.app.newuidashboardadmin.R;
import com.app.newuidashboardadmin.services.VolleyClient;
import com.app.newuidashboardadmin.services.WebServicesUrl;
import com.google.firebase.FirebaseApp;
import com.google.gson.Gson;
import com.megogrid.megoauth.AuthorisedPreference;
import com.megogrid.megoauth.IAuthorized;
import com.megogrid.megoauth.MegoAuthorizer;
import com.megogrid.rest.AuthController;

import java.util.Timer;
import java.util.TimerTask;


public class LoadingScreen
        extends FragmentActivity {

    //    private String packageNameEcho = "com.mevofit.fitness.fitnesstracker.walkingjogginghrbp.echotrackers";
//    public static boolean isSyncCompleted = false;
//    public static boolean isFirst = true;
    //    private static String MEVO_GOOGLE_ID = "465601469561-ovajj0ohgmv6n2nnpjpu799842svcq16.apps.googleusercontent.com";
//    private static String MEVO_GOOGLE_ID = "";
    Animation dotAnimation;
    Gson gson;
    //    IABManager iabManager;
    VolleyClient client;
    AVLoadingIndicatorView loader;
    AuthorisedPreference authorisedPreference1;
    //    FitSharedPrefreces fitSharedData;
//    SettingSharedData newSharedData;
    Timer timer;
    //    int counter = 0;
//    int maxCounter = 4;
//    int timeCounter = 0;
//    String value = ".";
    private int LOADING_DURATION = 8;// / /2= 1sec
        private boolean isLoadingCompleted = false, isAuthLoaded;
//    private AppSharedData sharedData;
//    private LoadingScreenSdkImpl loadingScreenInit;
//    private BandConnectionStatusImpl bandConnectionStatus;
    boolean authsucess = false;
//    MevoEventTrigger trigger;

    /*@TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        System.out.println("LoadingScreen.onRequestPermissionsResult check value >>>>>>1");
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                System.out.println("LoadingScreen.onRequestPermissionsResult check value >>>>>>2");
                OnAuthSuccess();
            } else {
                PermissionUtils.onCreateDialog(LoadingScreen.this, "READ PHONE STATE", "This permission is required to register your device on our server.", new PermissionUtils.Permissioncallback() {
                    @Override
                    public void onFailure() {
                        System.out.println("LoadingScreen.onRequestPermissionsResult check value >>>>>>3");
                        finish();
                    }

                    @Override
                    public void onRetry() {
                        System.out.println("LoadingScreen.onRequestPermissionsResult check value >>>>>>4");
//                            ActivityCompat.requestPermissions(LoadingScreen.this, new String[]{Manifest.permission.READ_PHONE_STATE}, PermissionUtils.Io_Permission);
                    }
                });
            }
        }


    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setOrientation(this);
        super.onCreate(savedInstanceState);
        System.out.println("oncreate of loading is called");
//        Util.onActivityCreateSetTheme(this, LoadingScreen.this);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.new_loading_screen);
//        boolean value = AppPreference.getInstance(LoadingScreen.this).getIsUpgradable();




        /*if (Util.getBandType(LoadingScreen.this) == AppConstants.SYNERGYBAND) {
            setGpsLocationPermission();
        } else {*/
        OnAuthSuccess();
//        }
    }

    @SuppressLint("SourceLockedOrientationActivity")
    public static void setOrientation(Activity context) {
        MyLogger.println("Only fullscreen opaque activities can request orientation" + android.os.Build.VERSION.SDK_INT);
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    //Get Gps Location permission for synergy band
    /*private void setGpsLocationPermission() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            MyLogger.println("gps>>>loactionserce>>1>>" + gps_enabled);
            if (gps_enabled) {
                OnAuthSuccess();
                MyLogger.println("gps>>>loactionserce>>2>>" + gps_enabled);
            } else {
                new LocationPremission(LoadingScreen.this);
                MyLogger.println("gps>>>loactionserce>>3>>" + gps_enabled);
            }
        } catch (Exception ex) {
            OnAuthSuccess();
        }

    }*/

    //After Get permision call this function initailize all sharedprefrences and create connection call
    // When connectionis break he
    private void OnAuthSuccess() {
        authsucess = true;
//        Fabric.with(this, new Crashlytics());
//        Utility.setstatusBarColor(getResources().getColor(R.color.header_color), this);
//        newSharedData = new SettingSharedData(this);
//        fitSharedData = new FitSharedPrefreces(this);
//        FacebookSdk.sdkInitialize(getApplicationContext());
        FirebaseApp.initializeApp(LoadingScreen.this);
        authorisedPreference1 = new AuthorisedPreference(LoadingScreen.this);
//        sharedData = new AppSharedData(this);
        initOncreate();
//        System.out.println("exception isa here " + BleApplication.getInstance().getPackageManager());

//        AppPreference appPreference = AppPreference.getInstance(this);
//        appPreference.setCalTime(Calendar.getInstance().getTimeInMillis());
        ImageView ivBackgroundImage = findViewById(R.id.iv_backround_image);
        ivBackgroundImage.setImageResource(R.drawable.icon_grey);
        /*Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!BandConnectionStatusImpl.isConnected()) {
                    System.out.println("oncreate of loading called for connectiin");
                    bandConnectionStatus.connect("LoadingScreen");
                }
            }
        }, 5000);*/
    }

    //after OnAuthSuccess call this function is call
    public void initOncreate() {
//        loadingScreenInit = new LoadingScreenSdkImpl(this, Util.getBandType(this));
//        bandConnectionStatus = BandConnectionStatusImpl.getinstance(BleApplication.getInstance(), null, 1, 1);//new BandConnectionStatusImpl(this, Util.getBandType(this));
//        initSDK();
        loader = (AVLoadingIndicatorView) findViewById(R.id.loader);
        Handler h = new Handler(Looper.getMainLooper());
//        h.postDelayed(new Runnable() {
//            @Override
//            public void run() {
        loader.setVisibility(View.VISIBLE);
        loader.smoothToShow();
//            }
//        }, 1500);

        h.postDelayed(new Runnable() {
            @Override
            public void run() {
//                isSyncCompleted = true;
//                sharedData = new AppSharedData(LoadingScreen.this);
//                sharedData.setFirstLaunch(true);// for launch event to get fired
                gson = new Gson();
                initGridZone();
//                fetchModuleVersion();
//                client = new VolleyClient(LoadingScreen.this, LoadingScreen.this);
//                trigger = new MevoEventTrigger(LoadingScreen.this, "loading");
//                trigger.initsdk(LoadingScreen.this, "loading");
//                iabManager = new IABManager(LoadingScreen.this);
//                iabManager.initializeSdk(true);
//                iabManager.setGoogleLicenceKey(getResources().getString(R.string.google_license_key));
//                fitSharedData = new FitSharedPrefreces(LoadingScreen.this);
                /*if (sharedData.isLoggedIn()) {
//                    loading.setText(getResources().getString(R.string.we_are_working));
                    String meUserData = sharedData.getMeUserDetail();

                    if (!meUserData.equalsIgnoreCase("NA")) {
                        ProfileDetailsResponse profileDetailsResponse = new Gson().fromJson(meUserData, ProfileDetailsResponse.class);
                        UserDetailEntity.getInstance(LoadingScreen.this).setUserDetailData(profileDetailsResponse, "LoadingScreen");
                    }
                } else {
//                    loading.setText(getResources().getString(R.string.loading));
                }*/
                dotAnimation = AnimationUtils.loadAnimation(LoadingScreen.this, R.anim.abc_fade_out);

                startTimer();
               /* if (UserDetailEntity.getInstance(LoadingScreen.this).getEmail() != null && !UserDetailEntity.getInstance(LoadingScreen.this).getEmail().equalsIgnoreCase(AppConstants.DEFAULT_EMAIL)) {
                    BleApplication.getInstance().getRequestQueue();
                    if (BandConnectionStatusImpl.isConnected()) {
                        boolean number_of_days = runOnceADay();
                        MyLogger.println("Difference in number of days (0)=====: " + (!number_of_days));
                        if (!number_of_days) {
                            isSyncCompleted = false;
                            client.makeRequest(WebServicesUrl.SYNC_COUTER_URL, Utils.getSingleKeyJson("email", UserDetailEntity.getInstance(LoadingScreen.this).getEmail()), "SyncCounter", false, VolleyClient.PromptTypes.None, "");
                        } else {
                            isSyncCompleted = true;
//                            setAllShopValuesInsert();
                        }
                    } else {
                        isSyncCompleted = true;
//                        setAllShopValuesInsert();
                    }
                } else {
                    isSyncCompleted = false;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            isSyncCompleted = true;
                        }
                    }, 3000);

                }*/
                MyLogger.println("Version based execution 1 " + Build.VERSION.SDK_INT);

            }

        }, 1000);
    }


    //es timer me Login condition aur bandConection check karte he
    // aur uske according (Login and signUp) and Main page open karte he
    private void startTimer() {
        try {
            if (timer == null)
                timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if(isAuthLoaded) {
                    Intent in = new Intent(LoadingScreen.this, AdminUI.class);
                    in.putExtra("from", "Loading");
                    startActivity(in);
                    timer.cancel();
                    finish();
                    }
                }
            }, 0, 5000);
        } catch (Exception e) {
            MyLogger.println("<<<checking LoadingScreen.run2===" + e.toString());
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        MainWristActivity.isAppRunning = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        MainWristActivity.isAppRunning = true;
    }


    //this function call from initOncreate this function hit GetHost service and Auth initialize
    //it is call only before Login or SignUp
    //condition based he Login or Signup ke bad ye every 4 hour pr call hoti he
    private void initGridZone() {
        //MeUserUtility.enableMessages = true;
//        AuthController.setHostMode(false);
//        if (ConfigReceivedMeVo.getInstance() != null)

        AuthController.setHostMode(true);
        final AuthorisedPreference authorisedPreference = new AuthorisedPreference(this);
        authorisedPreference.setString(AuthorisedPreference.DYNAMIC_LINK, WebServicesUrl.MEGO_HOST_LINK);
        MegoAuthorizer.pickFromString = true;
        final MegoAuthorizer megoAuthorizer = new MegoAuthorizer(this);
        megoAuthorizer.initializeSdk();
            /*authorisedPreference.setString(AuthorisedPreference.APP_ID_KEY, "T9L6IRQCO");
            authorisedPreference.setString(AuthorisedPreference.SECRET_ID_KEY, "413FHSB0W");
            authorisedPreference.setString(AuthorisedPreference.DEV_ID_KEY, "29681ed8-67a4-4751-8e42-1826f3e79dfd");*/
        authorisedPreference.setString(AuthorisedPreference.APP_ID_KEY, "ShOZpXKHR");
        authorisedPreference.setString(AuthorisedPreference.SECRET_ID_KEY, "bpSa25QWk");
        authorisedPreference.setString(AuthorisedPreference.DEV_ID_KEY, "d3ec1951-abcf-4354-9c90-5d776e1d1126");
        MyLogger.println("<<<checkinit>>>>> initialize==");
        megoAuthorizer.register(new IAuthorized() {
            @Override
            public void onSucessAuthorization(String s, String s1, String s2) {
                    /*if (getApplicationContext().getPackageName().equalsIgnoreCase(packageNameEcho)) {
                        megoAuthorizer.initializeSdkEcho();
                    } else {*/
                if (authorisedPreference.getString("mevomeward") == null || authorisedPreference.getString("mevomeward").equalsIgnoreCase("") || authorisedPreference.getMewardId().equalsIgnoreCase("NA"))
                    megoAuthorizer.initializeSdkMevo();
//                    }
                AuthorisedPreference authorisedPreference2 = new AuthorisedPreference(LoadingScreen.this);
                authorisedPreference.setSellerId("740bb9c3-17ff-4ef8-8922-9de82a9a2471");
                if (authorisedPreference2.getTokenKey() != null && authorisedPreference2.getTokenKey().length() > 4 && authorisedPreference2.getTokenKey().contains("V22ggPpsJ")) {
                    System.out.println("inside syncadaptor is callled here");
//                        SyncAdaptor syncAdaptor = new SyncAdaptor();
//                        syncAdaptor.startSynchingEcho(LoadingScreen.this, "", "");
                }
                MyLogger.println("<<<checkinit>>>>>  Loading Screen auth loading completed==");
                MyLogger.println("123456 WelcomeScreen.onCreate " + authorisedPreference1.getBoolean(AuthorisedPreference.F_STATUS));
                MyLogger.println("<<<<mewardid>>>" + s1 + ">>>>tokenkey==" + s2 + "=====" + s);

              authorisedPreference2.setSellerId("740bb9c3-17ff-4ef8-8922-9de82a9a2471");
                isAuthLoaded = true;
            }

            @Override
            public void onFailureAuthorization(String s) {
                MyLogger.println("<<<checking Loading Screen auth onFailureAuthorization " + s);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MyLogger.println("check>>>>>>gps>>location>>>>" + requestCode + "=====" + resultCode);
        if (requestCode == 199 && resultCode == Activity.RESULT_OK) {
            OnAuthSuccess();
        } else {
            finish();
        }
    }


    //Sync data ko per day ke basis pr call karte he es condion se
    // esme pr day condition check krte he
   /* public boolean runOnceADay() {
        SharedPreferences shp = getSharedPreferences(AppConstants.GENERAL_SHP, MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();
        long lastCheckedMillis = shp.getLong(AppConstants.ONCE_A_DAY, 0);
        long now = System.currentTimeMillis();

        System.out.println("LoadingScreen Difference " + lastCheckedMillis);
        System.out.println("LoadingScreen Difference in number of days Once a Day Test");

        long diffMillis = now - lastCheckedMillis;
        if (diffMillis >= (3600000 * 24)) {
            editor.putLong(AppConstants.ONCE_A_DAY, now);
            editor.commit();
            System.out.println("LoadingScreen Difference in number of days Once a Day Test");
            return false;
        } else {
            System.out.println("LoadingScreen Difference in number of days Too Early");
            return true;
        }
    }
*/

    //ye Auth ke initilaize ke liye he Login or SignUp ke bad every 4 hour pr Auth initialize hota he
    //esme 4 hour ki condition check hota he
    /*public boolean everyFourHour() {
        SharedPreferences shp = getSharedPreferences(AppConstants.FOURHOST_SHP_MAIN, MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();
        long lastCheckedMillis = shp.getLong(AppConstants.FOURHOST_A_DAY_MAIN, 0);
        long now = System.currentTimeMillis();
        long diffMillis = now - lastCheckedMillis;
        if (diffMillis >= (3600000 * 4)) {
            editor.putLong(AppConstants.FOURHOST_A_DAY_MAIN, now);
            editor.commit();
            System.out.println("Difference in number of days after 6 hour Test");
            return false;
        } else {
            System.out.println("Difference in number of days Too Early");
            return true;
        }
    }*/

   /* @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sharedData != null)
            sharedData.destroy();
        if (newSharedData != null)
            newSharedData.destroy();
        if (fitSharedData != null)
            fitSharedData.destroy();
        if (ConfigReceivedMeVo.getInstance() != null) {
            ConfigReceivedMeVo.getInstance().unregister();

        }
        loadingScreenInit.destroy();
    }*/
}
