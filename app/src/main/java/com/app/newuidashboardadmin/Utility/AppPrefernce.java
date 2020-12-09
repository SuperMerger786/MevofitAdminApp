package com.app.newuidashboardadmin.Utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ainul on 21/5/18.
 */

public class AppPrefernce {
    private static final String name = "prefernceAdmin";

    private Context context;
    private SharedPreferences sharedPreferences;

    public AppPrefernce(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);

    }

    public String getString(String key) {
        return sharedPreferences.getString(key, null);

    }

    public void setString(String key, String value) {
        sharedPreferences.edit().putString(key, value).commit();
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);

    }

    public void setBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    public void setUtilityPermission(boolean reminder) {
        sharedPreferences.edit().putBoolean("remindMe", reminder).commit();
    }
    public boolean getUtilityPermission() {
        return sharedPreferences.getBoolean("remindMe", true);
    }



    public void setBatteryPromptShow(int waterglass) {
        sharedPreferences.edit().putInt("showBattryPrompt", waterglass).commit();
    }


    public int getBatteryPromptShow() {
        return sharedPreferences.getInt("showBattryPrompt", 0);
    }






    public void remove(String key) {
        if(getString(key)!=null)
            sharedPreferences.edit().remove(key).commit();
    }


    public void clearPreference() {
        sharedPreferences.edit().clear().commit();

    }


    ///// Get Table

    public void setTableClickIsFirstTime(boolean isFirst){
        sharedPreferences.edit().putBoolean("isFirstt", isFirst).commit();
    }

    public boolean getTableClickIsFirstTime(){
        return sharedPreferences.getBoolean("isFirstt", false);
    }


    public void setGetTableId(String tableId){
        sharedPreferences.edit().putString("tableIDD", tableId).commit();
    }

    public String getGetTableId(){
        return  sharedPreferences.getString("tableIDD", "na");
    }

    public void setProfilePic(String profilepic){
        sharedPreferences.edit().putString("ProfilePic", profilepic).commit();
    }

    public String getProfilePic(){
        return  sharedPreferences.getString("ProfilePic", "http://alphaservices13.migital.net/webroot/profilepic/d3ec1951-abcf-4354-9c90-5d776e1d1126/TG5PJUQA81568883182_d3ec1951-abcf-4354-9c90-5d776e1d1126_ShOZpXKHR_bpSa25QWk_1594121744.jpg");
    }
    public void setInstanceBoxid(String profilepic){
        sharedPreferences.edit().putString("InstanceBoxid", profilepic).commit();
    }

    public String getInstanceBoxid(){
        return  sharedPreferences.getString("InstanceBoxid", "NA");
    }
}
