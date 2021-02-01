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
        if (getString(key) != null)
            sharedPreferences.edit().remove(key).commit();
    }


    public void clearPreference() {
        sharedPreferences.edit().clear().commit();

    }


    ///// Get Table

    public void setTableClickIsFirstTime(boolean isFirst) {
        sharedPreferences.edit().putBoolean("isFirstt", isFirst).commit();
    }

    public boolean getTableClickIsFirstTime() {
        return sharedPreferences.getBoolean("isFirstt", false);
    }


    public void setGetTableId(String tableId) {
        sharedPreferences.edit().putString("tableIDD", tableId).commit();
    }

    public String getGetTableId() {
        return sharedPreferences.getString("tableIDD", "na");
    }

    public void setProfilePic(String profilepic) {
        sharedPreferences.edit().putString("ProfilePic", profilepic).commit();
    }

    public String getProfilePic() {
        return sharedPreferences.getString("ProfilePic", "NA");
    }

    public void setInstanceBoxid(String profilepic) {
        sharedPreferences.edit().putString("InstanceBoxid", profilepic).commit();
    }

    public String getInstanceBoxid() {
        return sharedPreferences.getString("InstanceBoxid", "NA");
    }


    //emailpass
    public void setEmailId(String email) {
        sharedPreferences.edit().putString("EmailId", email).commit();
    }

    public String getEmailId() {
        return sharedPreferences.getString("EmailId", "NA");
    }
    public void setEPassword(String email) {
        sharedPreferences.edit().putString("Password", email).commit();
    }

    public String getEPassword() {
        return sharedPreferences.getString("Password", "NA");
    }

    public String getDeviceID() {
        return sharedPreferences.getString("DeviceID", "NA");
    }

    public void setDeviceID(String developermode) {
        sharedPreferences.edit().putString("DeviceID", developermode).commit();
    }

    public String getDeviceIDSuccessDetail() {
        return sharedPreferences.getString("DeviceIDSuccessDetail", "NA");
    }

    public void setDeviceIDSuccessDetail(String developermode) {
        sharedPreferences.edit().putString("DeviceIDSuccessDetail", developermode).commit();
    }
}
