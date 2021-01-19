package com.app.newuidashboardadmin.Utility;

public class UtilityAdmin {


    public static String getCurrencySymbol(String currencySymbel) {

        try {
            if (currencySymbel.contains("\\u"))
                return String.valueOf(((char) Integer.parseInt(currencySymbel.replace("\\u", "").trim(), 16)));
            else
                return currencySymbel;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return " ";

    }


}
