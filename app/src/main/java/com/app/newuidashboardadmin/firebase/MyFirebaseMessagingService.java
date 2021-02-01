package com.app.newuidashboardadmin.firebase;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.app.newuidashboardadmin.AdminUI;
import com.app.newuidashboardadmin.R;
import com.app.newuidashboardadmin.Utility.AppPrefernce;
import com.contact.activity.PublisherVideoActivity;
import com.contact.util.ContactSdk;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.megogrid.megoauth.AuthorisedPreference;
import com.megogrid.megowallet.slave.activity.OrderSummary;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class MyFirebaseMessagingService extends FirebaseMessagingService //implements TextToSpeech.OnInitListener
{
    TextToSpeech textToSpeech;
    String messageBody;
    String header = "Hi Admin!";
    int id;
    String type;
    String type_status;
    String storeid;
    String pickup_by;
    String booking_for;
    String SellerProfileImage;
    int notifictionid = 0;
    int isNotificationCallType = 0;
    int isClientConnected, isClientDisConnected, isOnHold, isCallResumed;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        JSONObject jsonObj = null;
        try {
            System.out.println("MyFirebaseMessagingService.onMessageReceived remoteMessage.getData() ==== " + remoteMessage);
            messageBody = remoteMessage.getData().get("message");
            System.out.println("MyFirebaseMessagingService.onMessageReceived message message  message === " + messageBody);
            jsonObj = new JSONObject(messageBody);
            messageBody = jsonObj.getString("message");
            type = jsonObj.getString("type");
            type_status = jsonObj.getString("type_status");
            pickup_by = jsonObj.getString("pickup_by");
            booking_for = jsonObj.getString("booking_for");
            isNotificationCallType = jsonObj.getInt("isNotificationCallType");
            isClientConnected = jsonObj.getInt("isClientConnected");
            isClientDisConnected = jsonObj.getInt("isClientDisConnected");
            isOnHold = jsonObj.getInt("isOnHold");
            isCallResumed= jsonObj.getInt("isCallResumed");
            SellerProfileImage = jsonObj.getString("SellerProfileImage");
          /*  {
                "messenger_type_val": "one_to_one",
                    "TokApiKey": "47018654",
                    "isNotificationCallType": 1,
                    "is_booking_configure": "true",
                    "type_id": "BAF62PGEKU",
                    "SellerEmail": "saurabh077@migital.com",
                    "prod_media_type": "media",
                    "pickup_by": "",
                    "booking_for": "people",
                    "notification_id": 12960,
                    "type_status": "booked",
                    "message": "Hi Admin\r\nClient Connected to Admin",
                    "type": "booking",
                    "storeid": "554",
                    "prod_imageurl": "NA",
                    "timeStamp": 1609149453,
                    "TokBoxTokenID": "T1==cGFydG5lcl9pZD00NzAxODY1NCZzaWc9MGI3N2FhMmUzODE4NzllNTUzZjBlMWVjOTlmYmFiN2VjZTkxMzU0MDpzZXNzaW9uX2lkPTJfTVg0ME56QXhPRFkxTkg1LU1UWXdPVEUwT1RRd05qY3pOSDQwYTI5SVJIZFBUeXRtZFVkUVRFOUpNRkZFU25KSk1IZC1mZyZjcmVhdGVfdGltZT0xNjA5MTQ5NDA2JnJvbGU9cHVibGlzaGVyJm5vbmNlPTE2MDkxNDk0MDYuODczODcxMjU2OTE3MSZleHBpcmVfdGltZT0xNjA5NzU0MjA2JmNvbm5lY3Rpb25fZGF0YT1uYW1lJTNERWxlbyZpbml0aWFsX2xheW91dF9jbGFzc19saXN0PQ==",
                    "TokBoxSID": "2_MX40NzAxODY1NH5-MTYwOTE0OTQwNjczNH40a29IRHdPTytmdUdQTE9JMFFESnJJMHd-fg",
                    "SellerProfileImage": "http:\/\/shopping.migital.net\/webroot\/img\/developerimg\/choco_alphamerketplace_20190812123921_db\/mebase\/ProfileImage\/original_201222112435_5fe1d7732dbb6.jpg",
                    "header": "Client Connected to Admin",
                    "store_name": "Fitness",
                    "is_send_seller_pic": "1"
            }*/


            AppPrefernce prefernce = new AppPrefernce(this);
            try {
                storeid = jsonObj.getString("storeid");
                prefernce.setString("save_store", storeid);
                prefernce.setString("storeName", prefernce.getString(storeid + "name"));
                prefernce.setString("storeAddress", prefernce.getString(storeid + "add"));

            } catch (JSONException e) {
                System.out.println("MyFirebaseMessagingService.onMessageReceived " + e);
            }


            System.out.println("MyFirebaseMessagingService.onMessageReceived message message type === " + type);
            System.out.println("MyFirebaseMessagingService.onMessageReceived message message status_type === " + type_status);

           /* if (jsonObj.has("total_unread_msgs")) {
                id = 3;
            } else {*/
            System.out.println("MyFirebaseMessagingService else esle else else ");

//                if (type.equalsIgnoreCase("booking")) {

                   /* if (type_status.equalsIgnoreCase("inprogress")) {
                        id = 4;
                        messageBody = jsonObj.getString("message");
                        System.out.println("MyFirebaseMessagingService.onMessageReceived inprogress  " + messageBody);

                    } else if (type_status.equalsIgnoreCase("canceled")) {
                        id = 6;
                        messageBody = jsonObj.getString("message");
                        System.out.println("MyFirebaseMessagingService.onMessageReceived canceled  " + messageBody);

                    } else if (booking_for.equalsIgnoreCase("chat")) {*/
            id = 8;
            messageBody = jsonObj.getString("message");
            header = jsonObj.getString("header");
//            String bookingid = jsonObj.getString("type_id");
            if (isOnHold == 1) {
                ContactSdk.PutAdminCallOnHold(this);
            } else if (isClientDisConnected == 1) {
                ContactSdk.ClientDisconnectCall(this);
            } else if (isCallResumed == 1) {
                ContactSdk.ResumeAdminCallFromHold(this);
            }else if (isNotificationCallType == 1) {
               /* Intent intent = new Intent(this, PublisherVideoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("client_connected", true);
                startActivity(intent);*/
                System.out.println("MyFirebaseMessagingService.onMessageReceived message Calll>>>> === " + type);
                ContactSdk.SubscriberConnected(this);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                    sendMyNotification();
                } else {
                    sendMyNotificationBelow(messageBody);
                }
            }
                        /*prefernce.setString("start_chat", bookingid);
                        System.out.println("MyFirebaseMessagingService.onMessageReceived canceled  " + messageBody);

                    }*/
            prefernce.setString("selectedTab", type);
//                    else if(type_status.equalsIgnoreCase("timeout")){
//                        id = 7;
//                        messageBody = jsonObj.getString("message");
//
//                    }

               /* } else if (type.equalsIgnoreCase("order")){
                    String app_type = prefernce.getString("app_instance_behaviour");
                    if (app_type != null
                            && app_type.equalsIgnoreCase("schdule_app")
                            && !(type_status.equalsIgnoreCase("transaction failed"))) {
                        id = 9;
                        messageBody = jsonObj.getString("message");
                        header = jsonObj.getString("header");
                        orderid = jsonObj.getString("type_id");


                    } else if (type_status.equalsIgnoreCase("created")) {
                        id = 1;
                        messageBody = jsonObj.getString("message");
                        System.out.println("MyFirebaseMessagingService.onMessageReceived order  " + messageBody);
                        prefernce.setString("selectedTab", pickup_by);
                    }

                }*/

             /*   if (messageBody.equalsIgnoreCase("Booking In-progress")) {
                    id = 4;
                    messageBody = "New Booking Received";
                } else if(messageBody.equalsIgnoreCase("Booking canceled")){
                    id = 6;
                    messageBody = "Booking canceled by User";
                }
                else {
                    id = 1;
                    //messageBody = "Order Received";
                }*/


//            }

//            sendMyNotification(messageBody);
            //sendSpeechNotificationText(messageBody);
            System.out.println("MyFirebaseMessagingService.onMessageReceived message message === " + messageBody);
            System.out.println("MyFirebaseMessagingService.onMessageReceived messgaebody=== " + jsonObj.toString());
        } catch (Exception e) {
            System.out.println("MyFirebaseMessagingService.onMessageReceived " + e);
        }


    }


    /*   private void sendSpeechNotificationText(final String body) {
           textToSpeech = new TextToSpeech(this, this);
           speakWords(body);
       }

       private void speakWords(String body) {
           //speak straight away
           textToSpeech.setLanguage(Locale.US);

           System.out.println("TTSSTTSS   " + body);
           textToSpeech.speak(body, TextToSpeech.LANG_COUNTRY_AVAILABLE, null);
       }


       public void onInit(int status) {

           if (status == TextToSpeech.SUCCESS) {
               System.out.println("status====  " + status);
               int result = textToSpeech.setLanguage(Locale.ENGLISH);

               if (result == TextToSpeech.LANG_MISSING_DATA
                       || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                   Log.e("TTS", "This Language is not supported");
               } else {
                   System.out.println("MyFirebaseMessagingService.onInit messageeee==     " + messageBody);
                   speakWords(messageBody);
               }

           } else {
               Log.e("TTS", "Initilization Failed!");
           }

       }*/
    String orderid = "";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    private void sendMyNotification() {
        String CHANNEL_ID = "my_channel_02";// The id of the channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelcreate(CHANNEL_ID);
        }
        Intent intent = null;
        intent = new Intent(this, AdminUI.class);
        /*if (type.equalsIgnoreCase("order")) {
//            intent = new Intent(this, TrackOrderActivity.class);
//            intent.putExtra("title", "Track Order");
//            intent.putExtra("order_id", type_id);
        } else {
            if (isNotificationCallType == 1) {
//                intent = new Intent(this, ChatMainActivity.class);
//                intent.putExtra("bookingId", type_id);
//                intent.putExtra("isfromPush", true);
            } else {

               *//* intent = new Intent(this, BookingHistoryActivityNew.class);
                if (booking_for.equalsIgnoreCase("chat")) {
                    intent.putExtra("bookingId", type_id);
                    intent.putExtra("isfromPush", true);
                }*//*
            }
        }*/
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.expandable_layout);
//        contentView.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
        Notification.Builder notificationBuilder = new Notification.Builder(this)
//                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentTitle(header)
//                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent)
                .setGroup(GROUP_KEY_WORK_MONK)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.app_icon))
                .setLights(Color.RED, 3000, 3000);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mm aa");
            String formattedDate = dateFormat.format(new Date()).toString();
            contentView.setTextViewText(R.id.time,formattedDate);
            contentView.setTextViewText(R.id.header,header);
            contentView.setTextViewText(R.id.subtitel,messageBody);

            try {
                Bitmap image = BitmapFactory.decodeStream(new URL(SellerProfileImage).openConnection().getInputStream());
                setBitmap(contentView,R.id.seller_image,image);
            } catch(IOException e) {
                System.out.println(e);
            }

            notificationBuilder.setCustomBigContentView(contentView);
        }else {
        Notification.BigTextStyle bigText = new Notification.BigTextStyle();
        bigText.bigText(messageBody);
        notificationBuilder.setStyle(bigText);
        }


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder.setSound(alarmSound);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.drawable.notification);
            notificationBuilder.setColor(Color.parseColor(new AuthorisedPreference(this).getThemeColor()));
        } else {
            notificationBuilder.setSmallIcon(R.drawable.app_icon);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder.setChannelId(CHANNEL_ID);
        }
        Random r = new Random();
        int iidd;
        iidd = r.nextInt(500 - 0) + 0;
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(iidd, notificationBuilder.build());
    }
    private void setBitmap(RemoteViews views, int resId, Bitmap bitmap){
        Bitmap proxy = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(proxy);
        c.drawBitmap(bitmap, new Matrix(), null);
        views.setImageViewBitmap(resId, proxy);
    }

    @SuppressLint("WrongConstant")
    private void sendMyNotificationBelow(String body) {

        int SUMMARY_ID = 0;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelcreate(CHANNEL_ID);
        }

        Intent intent = null;
//        if (type.equalsIgnoreCase("order")) {
        intent = new Intent(this, AdminUI.class);
//            intent.putExtra("title", "Track Order");
//            intent.putExtra("order_id", type_id);
//        } else {
//            intent = new Intent(this, BookingHistoryActivityNew.class);
//            if (booking_for.equalsIgnoreCase("chat")) {
//                intent.putExtra("bookingId", type_id);
//                intent.putExtra("isfromPush", true);
//            }
//        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Notification.BigTextStyle bigText = new Notification.BigTextStyle();
        bigText.bigText(messageBody);

        Notification.Builder notificationBuilder = new Notification.Builder(this)
                .setContentTitle(header)
//                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent)
                .setStyle(bigText)
                .setOngoing(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.app_icon))
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setLights(Color.RED, 3000, 3000);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder.setSound(alarmSound);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.drawable.notification);
            notificationBuilder.setColor(Color.parseColor(new AuthorisedPreference(this).getThemeColor()));

            notificationBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        } else {
            notificationBuilder.setSmallIcon(R.drawable.app_icon);

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder.setChannelId(CHANNEL_ID);
        }
        Random r = new Random();
        int iidd = r.nextInt(500 - 0) + 0;
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(iidd, notificationBuilder.build());
    }

    String CHANNEL_ID = "my_channel_02";
    String GROUP_KEY_WORK_MONK = "other";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void channelcreate(String CHANNEL_ID) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, "sssss", importance);
        mChannel.setDescription("wdqwedfqw");
        mChannel.enableLights(true);
        mNotificationManager.createNotificationChannel(mChannel);
    }


}
