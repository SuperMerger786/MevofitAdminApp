package com.app.newuidashboardadmin.firebase;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.speech.tts.TextToSpeech;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.app.newuidashboardadmin.R;
import com.app.newuidashboardadmin.Utility.AppPrefernce;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.megogrid.megoauth.AuthorisedPreference;
import com.megogrid.megowallet.slave.activity.OrderSummary;

import org.json.JSONException;
import org.json.JSONObject;

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
    int notifictionid = 0;

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

            if (jsonObj.has("total_unread_msgs")) {
                id = 3;
            } else {
                System.out.println("MyFirebaseMessagingService else esle else else ");

                if (type.equalsIgnoreCase("booking")) {

                    if (type_status.equalsIgnoreCase("inprogress")) {
                        id = 4;
                        messageBody = jsonObj.getString("message");
                        System.out.println("MyFirebaseMessagingService.onMessageReceived inprogress  " + messageBody);

                    } else if (type_status.equalsIgnoreCase("canceled")) {
                        id = 6;
                        messageBody = jsonObj.getString("message");
                        System.out.println("MyFirebaseMessagingService.onMessageReceived canceled  " + messageBody);

                    } else if (booking_for.equalsIgnoreCase("chat")) {
                        id = 8;
                        messageBody = jsonObj.getString("message");
                        header = jsonObj.getString("header");
                        String bookingid = jsonObj.getString("type_id");
                        prefernce.setString("start_chat", bookingid);
                        System.out.println("MyFirebaseMessagingService.onMessageReceived canceled  " + messageBody);

                    }
                    prefernce.setString("selectedTab", type);
//                    else if(type_status.equalsIgnoreCase("timeout")){
//                        id = 7;
//                        messageBody = jsonObj.getString("message");
//
//                    }

                } else if (type.equalsIgnoreCase("order")) {
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

                }

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


            }
            sendMyNotification(messageBody);
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

    private void sendMyNotification(String body) {

        Intent intent = null;
        /*if (id == 3) {
            intent = new Intent(this, UserListActivity.class);
            intent.putExtra("title", "Chat with user");
        } else if (id == 4) {
            intent = new Intent(this, MainActivity.class);
            StatusHandler statusHandler = StatusHandler.getInstance(this);
            statusHandler.setAlertIfNotResponded("Booking");
            //refreshBookingList();
        } else if (id == 6) {
            intent = new Intent(this, MainActivity.class);

        }

//        else if(id==7){
//            intent = new Intent(this, BookingActivity.class);
//        }

        else if (id == 1) {
            intent = new Intent(this, MainActivity.class);
            StatusHandler statusHandler = StatusHandler.getInstance(this);
            statusHandler.setAlertIfNotResponded("Order");
            //refreshOrderList();
        } else*/ if (id == 8) {
//            intent = new Intent(this, MainActivity.class);
        } else if (id == 9) {
            intent = new Intent(this, OrderSummary.class);
            intent.putExtra("typee", "schdule_app");
            intent.putExtra("orderId", orderid);
            intent.putExtra("decimalPlaces", 2);
            intent.putExtra("priceShow", 1);
        }


//        Notification.Builder builder = new Notification.Builder(this, intent, body, header);
//        Notification.Builder builder = new NotifyBuilder().getBuilder(this, intent, body, header);
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify((type_status+orderid).trim().hashCode(), builder.build());
        sendMyNotificationBelow(header);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);

    }
    @SuppressLint("WrongConstant")
    private void sendMyNotificationBelow(String body) {

        String CHANNEL_ID = "my_channel_02";// The id of the channel.
        int SUMMARY_ID = 0;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelcreate(CHANNEL_ID);
        }

//        Intent intent = null;
//        intent = new Intent(this, PeriodTrackNewActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Notification.BigTextStyle bigText = new Notification.BigTextStyle();
        bigText.bigText(messageBody);

        Notification.Builder notificationBuilder = new Notification.Builder(this)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentTitle(messageBody)
                .setAutoCancel(true)
                .setSound(soundUri)
//                .setContentIntent(pendingIntent)
                .setStyle(bigText)
                .setOngoing(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.app_icon))
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setLights(Color.RED, 3000, 3000);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder.setSound(alarmSound);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.drawable.app_icon);
            notificationBuilder.setColor(Color.parseColor(new AuthorisedPreference(this).getThemeColor()));

            notificationBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        } else {
            notificationBuilder.setSmallIcon(R.drawable.app_icon);

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder.setChannelId(CHANNEL_ID);
        }
        // notify by ring
//        try {
//            Uri ring_uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), ring_uri);
//            r.play();
//        } catch (Exception e) {
//             Error playing sound
//        }
        Random r = new Random();
        int iidd = r.nextInt(500 - 0) + 0;
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(iidd, notificationBuilder.build());
    }

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
