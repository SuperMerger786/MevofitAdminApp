package com.app.newuidashboardadmin;

/**
 * Created by pankaj on 28/10/16.
 */
public class MyLogger {
    private static final boolean isEnabled = true;

    public static void println(String mesz) {
        if (isEnabled) {
            System.out.println(mesz);
        }
    }
}
