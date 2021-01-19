package com.app.newuidashboardadmin.push;

import android.content.Context;

import com.megogrid.megoeventbuilder.bean.Events;
import com.megogrid.megoeventpersistence.MewardDbHandler;
import com.megogrid.megoeventssdkhandler.ActionPerformer;

import java.util.ArrayList;

/**
 * Created by ashish on 31/8/18.
 */

public class EventHandlerAdmin {
    public static final String BOOKING_DECLINE = "X2TJSGBCO";
    public static final String BOOKING_DECLINE_TITEL = "Booking Declined";

    public static final String BOOKING_CONFIRMED = "QIFI4FCG9";
    public static final String BOOKING_CONFIRMED_TITEL = "Booking Confirmed";






//    public static void logEvent(Context context, String eventId, String titel) {
////        ActionPerformer actionPerformer=new ActionPerformer(context);
////        MegoRewardHandler megoRewardHandler=new MegoRewardHandler.MewardBuilders().setEventID(eventId).setEventStatus(EventAction.ACTION_COMPLETED).build();
////        actionPerformer.onActionperformed(megoRewardHandler);
//        BookingServerController serverController = new BookingServerController(context, new BookingResponse() {
//            @Override
//            public void onResponseObtained(Object response, int responseType, boolean isCachedData) {
//
//            }
//
//            @Override
//            public void onErrorObtained(String errormsg, int responseType) {
//
//            }
//        }, 34, false);
//        MewardDbHandler mewardDbHandler = new MewardDbHandler(context);
//        ArrayList<Events> eventses = mewardDbHandler.getEvent(eventId, "Complete");
//        ArrayList<String> rules = new ArrayList<>();
//        if (eventses != null && eventses.size() > 0) {
//            rules.add(eventses.get(0).ruleId);
//            if (rules.size() > 0) {
//                System.out.println("OrderSummary.onClick rull id " + rules.get(0));
//                ProcessEventBook processEvent = new ProcessEventBook(context, eventId, "Complete", titel, rules, "");
//                serverController.sentEmailInvoice(processEvent, serverController.EmaiL);
//            }
//        }
//    }

//    public static String getRuleId(Context context, String eventId) {
//        MewardDbHandler mewardDbHandler = new MewardDbHandler(context);
//        ArrayList<Events> eventses = mewardDbHandler.getEvent(eventId, "Complete");
//        ArrayList<String> rules = new ArrayList<>();
//        if (eventses != null && eventses.size() > 0) {
//            rules.add(eventses.get(0).ruleId);
//            if (rules.size() > 0) {
//                return rules.get(0);
//            } else {
//                return "NA";
//            }
//        } else {
//            return "NA";
//        }
//    }

    public static void sendPush(String event_Id, String booking_id, String eventName, Context context) {
        MewardDbHandler mewardDbHandler = new MewardDbHandler(context);
        ArrayList<Events> eventses = mewardDbHandler.getEvent(event_Id, "Complete");
        System.out.println("EventHandler.sendPush notification event_id "+event_Id+" booking_id "+booking_id+" eventName "+eventName);
        if (eventses != null) {
            try {
                ActionPerformer actionPerformer = new ActionPerformer();
                ArrayList key=new ArrayList();
                key.add(booking_id);
                actionPerformer.sendPush(context, event_Id, eventses.get(0).ruleId, key);
            }
            catch (Exception e)
            {
                System.out.println("EventHandler.sendPush error "+e);
            }



        }


    }

}
