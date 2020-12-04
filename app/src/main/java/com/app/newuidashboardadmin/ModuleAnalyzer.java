package com.app.newuidashboardadmin;

import android.content.Context;

import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

//import a.b.a.G;


/**
 * Created by pankaj on 19/8/16.
 */
public class ModuleAnalyzer {

    public enum ModuleAnalyze {
        Calories
    }

    public class AnalyzeEntity {
        private String key;
        private float value;

        private int totalDaysLogged;

        public int getTotalDaysLogged() {
            return totalDaysLogged;
        }

        public void setTotalDaysLogged(int totalDaysLogged) {
            this.totalDaysLogged = totalDaysLogged;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public float getValue() {
            return value;
        }

        public String toString() {
            return new Gson().toJson(this);
        }

        public void setValue(float value) {
            this.value = value;
        }
    }

    public class ExpandableAnalyzeEntity {

        String dates;
        ArrayList<AnalyzeEntity> data;

        public String getDates() {
            return dates;
        }

        public void setDates(String dates) {
            this.dates = dates;
        }

        public ArrayList<AnalyzeEntity> getData() {
            return data;
        }

        public void setData(ArrayList<AnalyzeEntity> data) {
            this.data = data;
        }

        public String toString() {
            return new Gson().toJson(this);
        }

    }

    WeakReference<Context> mContext;


    public ModuleAnalyzer(Context mContext) {
        this.mContext = new WeakReference<>(mContext);

    }

    public ArrayList<AnalyzeEntity> getAllWristStepsDailyDaily12(long startingTime) {

        ArrayList<AnalyzeEntity> caloriesList = new ArrayList<>();
        long[] dateTime = null;
        MyLogger.println("<<<< getting data of band by date initial : " + startingTime);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(startingTime);
        AnalyzeEntity analyzeEntity = new AnalyzeEntity();
        analyzeEntity.setKey("may");
        analyzeEntity.setValue(50f);
        caloriesList.add(analyzeEntity);
        AnalyzeEntity analyzeEntity1 = new AnalyzeEntity();
        analyzeEntity1.setKey("june");
        analyzeEntity1.setValue(150f);
        caloriesList.add(analyzeEntity1);
        AnalyzeEntity analyzeEntity2 = new AnalyzeEntity();
        analyzeEntity2.setKey("july");
        analyzeEntity2.setValue(20f);
        caloriesList.add(analyzeEntity2);
        AnalyzeEntity analyzeEntity3 = new AnalyzeEntity();
        analyzeEntity3.setKey("august");
        analyzeEntity3.setValue(250f);
        caloriesList.add(analyzeEntity3);
        return caloriesList;

    }


    public void destroy() {

        if (mContext != null) {
            mContext.clear();
        }

    }
}