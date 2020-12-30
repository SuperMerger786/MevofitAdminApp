package com.app.newuidashboardadmin.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spanned;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.app.newuidashboardadmin.MyLogger;
import com.app.newuidashboardadmin.R;
import com.google.android.material.internal.ForegroundLinearLayout;
import com.megogrid.activities.UserDetailEntity;
import com.megogrid.beans.ProfileCustomField;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Class containing some static utility methods.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
public class Utils {
    public static final int IO_BUFFER_SIZE = 8 * 1024;

    public Utils() {

    }

    public static void disableConnectionReuseIfNecessary() {
        if (hasHttpConnectionBug()) {
            System.setProperty("http.keepAlive", "false");
        }
    }

    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        }
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    public static boolean isExternalStorageRemovable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return Environment.isExternalStorageRemovable();
        }
        return true;
    }

    public static int dpToPx(Resources res, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                res.getDisplayMetrics());
    }

    public static File getExternalCacheDir(Context context) {
        if (hasExternalCacheDir()) {
            return context.getExternalCacheDir();
        }

        final String cacheDir = "/Android/data/" + context.getPackageName()
                + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath()
                + cacheDir);
    }

    public static long getUsableSpace(File path) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return path.getUsableSpace();
        }

        final StatFs stats = new StatFs(path.getPath());
        return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
    }

    public static int getMemoryClass(Context context) {
        return ((ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
    }

    public static boolean hasHttpConnectionBug() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO;
    }

    public static boolean hasExternalCacheDir() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    public static boolean hasActionBar() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static String formatString(float value) {
        String pattern = "##.##";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);

        String format = decimalFormat.format(value);
        return format;
    }

    public static String formatString2Digit(float value) {
        String pattern = "00";
        try {
            if (value < 10)
                pattern = "0";

        } catch (Exception e) {

        }
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        String format = decimalFormat.format(value);
        if (format.equalsIgnoreCase("NaN"))
            format = "00";
        return format;
    }

    public static float formatFloat2Digit(float value) {
        try {
            String pattern = "00";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);
            String format = decimalFormat.format(value);
            if (format.equalsIgnoreCase("NaN"))
                format = "00";
            return Float.parseFloat(format);
        } catch (Exception e) {
        }
        return value;
    }

    public static String formatString2Digit(String value) {
        String format = value;
        try {
            String pattern = "##.##";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);

            format = decimalFormat.format(Float.parseFloat(value));
            if (format.equalsIgnoreCase("NaN"))
                format = "0";
        } catch (Exception e) {
            MyLogger.println("Error here at jp.wasabeef.richeditor.Utils formatString2Digit=="
                    + e.getMessage());
        }
        return format;
    }

    public static String formatStringMeal(String value) {
        String format = value;
        try {
            String pattern = "##.##";
            DecimalFormat decimalFormat = new DecimalFormat(pattern);

            format = decimalFormat.format(Float.parseFloat(value));
            if (format.equalsIgnoreCase("NaN"))
                format = " - ";
        } catch (Exception e) {
            MyLogger.println("Error here at jp.wasabeef.richeditor.Utils formatString2Digit=="
                    + e.getMessage());
        }
        return format;
    }

    public static String getFormatedPhoneNumber(String storedNumber) {
        String mynumber = storedNumber;
        if (storedNumber.contains(":")) {
            String number[] = storedNumber.split(":");
            mynumber = number[0] + "" + number[1];
        }
        return mynumber;

    }

    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }

    public static void setHeightForWrapContent(View view, Activity a) {
        DisplayMetrics metrics = new DisplayMetrics();
        a.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int screenWidth = metrics.widthPixels;

        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0,
                MeasureSpec.UNSPECIFIED);
        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(screenWidth,
                MeasureSpec.EXACTLY);

        view.measure(widthMeasureSpec, heightMeasureSpec);
        int height = view.getMeasuredHeight();
        view.getLayoutParams().height = height;
    }

    static long TOAST_TIME_PREVIOUS = 0;

    public static void showToast(Context ctx, String message) {

        if (System.currentTimeMillis() - TOAST_TIME_PREVIOUS > 2000) {
            TOAST_TIME_PREVIOUS = System.currentTimeMillis();
            Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
        }
        // showSnackToast(ctx, message, false);

    }

    public static void setAlpha(View view, float alpha) {
        if (Build.VERSION.SDK_INT < 11) {
            final AlphaAnimation animation = new AlphaAnimation(alpha, alpha);
            animation.setDuration(0);
            animation.setFillAfter(true);
            view.startAnimation(animation);
        } else
            view.setAlpha(alpha);
    }

    public static String getMonthName(int monthType) {
        String month = "Jan";
        switch (monthType) {
            case Calendar.JANUARY:
                month = "January";
                break;
            case Calendar.FEBRUARY:
                month = "February";
                break;
            case Calendar.MARCH:
                month = "March";
                break;
            case Calendar.APRIL:
                month = "April";
                break;
            case Calendar.MAY:
                month = "May";
                break;
            case Calendar.JUNE:
                month = "Jun";
                break;
            case Calendar.JULY:
                month = "July";
                break;
            case Calendar.AUGUST:
                month = "August";
                break;
            case Calendar.SEPTEMBER:
                month = "September";
                break;
            case Calendar.OCTOBER:
                month = "October";
                break;
            case Calendar.NOVEMBER:
                month = "November";
                break;
            case Calendar.DECEMBER:
                month = "December";
                break;
        }
        return month;
    }

    public static String getWeekName(int week) {
        String weekName = "";
        switch (week) {
            case 1:
                weekName = "Sunday";

                break;
            case 2:
                weekName = "Monday";

                break;
            case 3:
                weekName = "Tuesday";

                break;
            case 4:
                weekName = "Wenesday";

                break;
            case 5:
                weekName = "Thursday";

                break;
            case 6:
                weekName = "Friday";

                break;
            case 7:
                weekName = "Saturday";

                break;

        }
        return weekName;
    }

    public static final String DATEFORMAT_DDMMYY = "dd/MM/yyyy";

    public static String getFormatedDate(long timeInSec, String dateFormat) {
        timeInSec = Math.abs(timeInSec);
        String dateTime = "";
        SimpleDateFormat formater = new SimpleDateFormat(dateFormat);

        Date date = new Date(timeInSec * 1000);
        dateTime = formater.format(date);
        return dateTime;
    }

    public static String getFormatedDateNew(long timeInSec, String dateFormat) {
        timeInSec = Math.abs(timeInSec);
        String dateTime = "";
        SimpleDateFormat formater = new SimpleDateFormat(dateFormat);

        Date date = new Date(timeInSec);
        dateTime = formater.format(date);
        return dateTime;
    }

    public static String getFormatedDateUTC(long timeInSec, String dateFormat) {
        String dateTime = "";
        SimpleDateFormat formater = new SimpleDateFormat(dateFormat);
        formater.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date(timeInSec * 1000);
        dateTime = formater.format(date);
        return dateTime;
    }


    public static final String TIME_FORMAT_AMPM = "hh:mm a";

    public static String GetGMTTimeAsString(long miliSec) {
        SimpleDateFormat formater = new SimpleDateFormat(TIME_FORMAT_AMPM);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(miliSec);
//        TimeZone timeZone = TimeZone.getTimeZone("GMT+00:00");
//        formater.setTimeZone(timeZone);
        Date date = new Date(cal.getTimeInMillis());
        String time = formater.format(date);
//        new SimpleDateFormat("hh:mm a").format(new Date())
//        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
//        Date date = new Date();
//        date.setTime(miliSec);
//        Calendar calendar= Calendar.getInstance();
//        calendar.setTimeInMillis(miliSec);
//    String time=calendar.get(Calendar.HOUR)+":"+calendar.get(Calendar.MINUTE)+" "+(calendar.get(Calendar.AM_PM)==Calendar.AM?"am":"pm");
//        final String utcTime = sdf.format(new Date(calendar.getTimeInMillis()));
        return time;


    }

    static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";

    private static String GetUTCdatetimeAsString(long miliSec) {
        final SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date();
        date.setTime(miliSec);
        final String utcTime = sdf.format(new Date());

        return utcTime;
    }

    public static long getUtcDate(long milisec) {
        Date dateToReturn = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATEFORMAT);

        try {
            dateToReturn = dateFormat.parse(GetUTCdatetimeAsString(milisec));
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(dateToReturn.getTime());
            return cal.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis();

    }

    public static long getCurrentTimeMiliGMT(long timeinMili) {
        String format = "EEE MMM dd HH:mm:ss z yyyy";
        SimpleDateFormat formater = new SimpleDateFormat(format);
        formater.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date(timeinMili);

        String dateTime = formater.format(date);
        Date mDate = null;
        try {
            mDate = formater.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long timeInMilliseconds = mDate.getTime();
        return timeInMilliseconds;
    }

    public static String getFormatedDateGMT(long timeInSec, String dateFormat) {
        String dateTime = "";
        SimpleDateFormat formater = new SimpleDateFormat(dateFormat);
        Date date = new Date(timeInSec * 1000);
        dateTime = formater.format(date);
        return dateTime;
    }

    public static String getFormatedDateGMTnew(long timeInSec, String dateFormat) {
        String dateTime = "";
//        Calendar cal = getCalendar();
//        cal.setTimeInMillis(timeInSec * 1000);

        SimpleDateFormat formater = new SimpleDateFormat(dateFormat);
//        TimeZone timeZone = TimeZone.getTimeZone("GMT+00:00");
//        formater.setTimeZone(timeZone);
        Date date = new Date(timeInSec);
        dateTime = formater.format(date);
        return dateTime;
    }


    public static long getTodayInitialDate(long timeInMillisecond) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeInMillisecond);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 1);
        long value = cal.getTimeInMillis() / 1000;
        return value;
    }

    public static long[] getDatePair(long timeInMillisecond) {
        long date[] = new long[2];
        Calendar cal = getCalendar();
        cal.setTimeInMillis(timeInMillisecond);
        long value1 = getTodayInitialDate(cal.getTimeInMillis());
        cal.add(Calendar.DAY_OF_MONTH, 1);
        long value2 = getTodayInitialDate(cal.getTimeInMillis());
        date[0] = value1;
        date[1] = value2;
        return date;
    }

    /*  public static String getBandRequestParam(Activity context) {
          String param = "";
          MyLogger.println("<<<< getbandrequest : " + context.getLocalClassName());
          try {
              JSONObject jsonObj = new JSONObject();
              jsonObj.put("deviceModel", Build.MANUFACTURER + " " + Build.MODEL);
              if (AppPreference.getInstance(context).getLastDevice() != null) {
                  jsonObj.put("bandId", AppPreference.getInstance(context).getLastDevice().getName());
              }

              param = jsonObj.toString();
          } catch (Exception ex) {

          }
          return param;
      }
  */
//    public static Intent getConnectionServiceIntent(Context context, boolean ConnectionStatus, String from) {
//        MyLogger.println("<<<< getConnectionServiceIntent : " + ConnectionStatus + "  from : " + from);
//        if (PrefUtil.getString(context, BaseActionUtils.ACTION_DEVICE_NAME) != null && PrefUtil.getString(context, BaseActionUtils.ACTION_DEVICE_NAME).length() > 3) {
//
//
//            Intent service = new Intent(context, ConnectionService.class);
//            service.putExtra(AppConstants.DEVICE_CONNECTION_STATUS, ConnectionStatus);
//            return service;
//        }
//        return null;
//    }

    public synchronized static String getFormatedTime(long timeSec,
                                                      boolean isHour, boolean isMinute, boolean isSec) {
        String hr = "00";
        String min = "00";
        String sec = "00";
        String finalString = "";

        if (isHour) {
            int hour = (int) timeSec / (60 * 60);
            if (hour < 10)
                hr = "0" + hour;
            else
                hr = "" + hour;

            finalString = hr;
        }

        if (isMinute) {
            int minute = (int) ((timeSec % (60 * 60)) / 60);
            if (minute < 10)
                min = "0" + minute;
            else
                min = "" + minute;

            finalString = finalString + ":" + min;
        }

        if (isSec) {
            int second = (int) ((timeSec % (60 * 60)) % 60);
            if (second < 10)
                sec = "0" + second;
            else
                sec = "" + second;

            finalString = finalString + ":" + sec;
        }
        return finalString; // To-Do only minute is visible... bt
        // uses the seconds also
    }

    public static String getImageInString(File imagefile, Context context) {
        String img = "";

        if (imagefile.exists()) {
            FileInputStream fis;
            try {
                // fis = new FileInputStream(imagefile);
                Bitmap bi = setOrentation(imagefile.getAbsolutePath(), 2);
                // BitmapFactory.decodeStream(fis);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bi.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();

                if (data != null)
                    img = Base64.encodeToString(data, Base64.DEFAULT);

                // Bitmap encodedImage = Base64.encodeBytes(img);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    public static Bitmap getBitmapFromFilePath(File imagefile) {
        Bitmap bi = null;
        if (imagefile.exists()) {
            try {
                bi = setOrentation(imagefile.getAbsolutePath(), 2);
                MyLogger.println("Bitmap returned from path is = " + bi);
            } catch (Exception e) {
                MyLogger.println("Error here 123===" + e.getMessage());
                e.printStackTrace();
            }
        }
        return bi;
    }

    public static Bitmap getBitmapFromResource(Activity activity, int resourceId) {
        Bitmap bm = BitmapFactory.decodeResource(activity.getResources(), resourceId);
        bm = scaleBitmap(bm);
        return bm;
    }

    public static String getEncodedImage(Bitmap bitmap) {
        String img = "";
        try {
            // fis = new FileInputStream(imagefile);
            // BitmapFactory.decodeStream(fis);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);
            byte[] data = baos.toByteArray();

            if (data != null)
                img = Base64.encodeToString(data, Base64.DEFAULT);

            // Bitmap encodedImage = Base64.encodeBytes(img);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return img;
    }

    public static String getCommunityImage(Bitmap bitmap) {
        String img = "";
        try {
            // fis = new FileInputStream(imagefile);
            // BitmapFactory.decodeStream(fis);
//            Bitmap bmp = getResizedBitmapLessThan2MB(bitmap,600);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] data = baos.toByteArray();

            if (data != null)
                img = Base64.encodeToString(data, Base64.DEFAULT);

            // Bitmap encodedImage = Base64.encodeBytes(img);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return img;
    }

    public static String compressImage(String imageUri, Context context) {

        String filePath = getRealPathFromURI(imageUri, context);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inDither = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

        //      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;
//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);


//      this options allow android to claim the bitmap memory if it runs low on memory
//        options.inPurgeable = true;
//        options.inInputShareable = true;

        options.inTempStorage = new byte[16 * 1024];
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inDither = true;

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        options.inBitmap = bmp;
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.RGB_565);
        } catch (OutOfMemoryError exception) {
            //   scaledBitmap = reduceResolution(filePath, actualWidth, actualHeight);
//            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
        } catch (Exception e) {
            e.printStackTrace();
            return filePath;
        }

        if (scaledBitmap.getByteCount() > (2000 * 1024)) {
            return compressImage(filename, context);

        } else
            return filename;


    }

    private static Bitmap reduceResolution(String filePath, int viewWidth, int viewHeight) {
        int reqHeight = viewHeight;
        int reqWidth = viewWidth;

        BitmapFactory.Options options = new BitmapFactory.Options();

        // First decode with inJustDecodeBounds=true to check dimensions
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        double viewAspectRatio = 1.0 * viewWidth / viewHeight;
        double bitmapAspectRatio = 1.0 * options.outWidth / options.outHeight;

        if (bitmapAspectRatio > viewAspectRatio)
            reqHeight = (int) (viewWidth / bitmapAspectRatio);
        else
            reqWidth = (int) (viewHeight * bitmapAspectRatio);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
//        options.inPreferredConfig = Bitmap.Config.RGB_565;
//        options.inDither = true;
        options.inMutable = true;
        System.gc();                                        // TODO - remove explicit gc calls
        return BitmapFactory.decodeFile(filePath, options);
    }


    public static String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }

    private static String getRealPathFromURI(String contentURI, Context context) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = context.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    public static Bitmap getResizedBitmapLessThan2MB(Bitmap image, int maxSize) {
        if (image.getByteCount() > (2000 * 1024)) {
            int width = image.getWidth();
            int height = image.getHeight();


            float bitmapRatio = (float) width / (float) height;
            if (bitmapRatio > 0) {
                width = maxSize;
                height = (int) (width / bitmapRatio);
            } else {
                height = maxSize;
                width = (int) (height * bitmapRatio);
            }
            Bitmap reduced_bitmap = Bitmap.createScaledBitmap(image, width, height, true);

            if (reduced_bitmap.getByteCount() > (2000 * 1024)) {
                return getResizedBitmapLessThan2MB(reduced_bitmap, maxSize);
            } else {
                return reduced_bitmap;
            }
        } else return image;
    }
//    public static Bitmap getCompressedImage(Bitmap image)
//    {
//        Bitmap scaledBitmap = null;
//        BitmapFactory.Options options = new BitmapFactory.Options();
//
////      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
////      you try the use the bitmap here, you will get null.
//        options.inJustDecodeBounds = true;
//        int actualHeight = image.getHeight();
//        int actualWidth =image.getWidth();
//
//        float maxHeight = 816.0f;
//        float maxWidth = 612.0f;
//        float imgRatio = actualWidth / actualHeight;
//        float maxRatio = maxWidth / maxHeight;
//
//        if (actualHeight > maxHeight || actualWidth > maxWidth) {
//            if (imgRatio < maxRatio) {
//                imgRatio = maxHeight / actualHeight;
//                actualWidth = (int) (imgRatio * actualWidth);
//                actualHeight = (int) maxHeight;
//            } else if (imgRatio > maxRatio) {
//                imgRatio = maxWidth / actualWidth;
//                actualHeight = (int) (imgRatio * actualHeight);
//                actualWidth = (int) maxWidth;
//            } else {
//                actualHeight = (int) maxHeight;
//                actualWidth = (int) maxWidth;
//
//            }
//        }
//
//        try {
//            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
//        } catch (OutOfMemoryError exception) {
//            exception.printStackTrace();
//        }
//        Bitmap bmp = BitmapFactory.decod
//    }

    public static String getCameraImage(Bitmap bitmap) {
        String img = "";
        try {
            // fis = new FileInputStream(imagefile);
            // BitmapFactory.decodeStream(fis);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2; //4, 8, etc. the more value, the worst quality of image
            bitmap.compress(Bitmap.CompressFormat.PNG, 70, baos);
            byte[] data = baos.toByteArray();

            if (data != null)
                img = Base64.encodeToString(data, Base64.DEFAULT);

            // Bitmap encodedImage = Base64.encodeBytes(img);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return img;
    }

    public static Bitmap getBitmapFromFilePath(File imagefile, int compressValue) {
        Bitmap bi = null;
        if (imagefile.exists()) {
            try {
                bi = setOrentation(imagefile.getAbsolutePath(), compressValue);
                MyLogger.println("Bitmap returned from path is = " + bi);
            } catch (Exception e) {
                MyLogger.println("Error here 123===" + e.getMessage());
                e.printStackTrace();
            }
        }
        return bi;
    }

    public static String getEncodedImage(byte[] data) {
        String img = "";
        try {

            if (data != null)
                img = Base64.encodeToString(data, Base64.DEFAULT);

            // Bitmap encodedImage = Base64.encodeBytes(img);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return img;
    }


    public static Bitmap decodeBase64(String encodeImage) {
        byte[] decodedByte = Base64.decode(encodeImage, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static Bitmap scaleBitmap(Bitmap bmp) {
        Bitmap myBitmap = bmp;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            MyLogger.println("Bitmap value in setorie==" + myBitmap.getWidth());
            if ((myBitmap.getWidth() >= 800) && (myBitmap.getWidth() <= 1280)) {
                options.inSampleSize = 3;
            } else if ((myBitmap.getWidth() >= 1280) && myBitmap.getWidth() <= 1600) {
                options.inSampleSize = 6;
            } else if (myBitmap.getWidth() < 800) {
                options.inSampleSize = 1;
            } else {
                options.inSampleSize = 8;
            }

            // myBitmap = BitmapFactory.decodeStream(new FileInputStream(new
            // File(
            // file)), null, options);

            Matrix matrix = new Matrix();
            myBitmap = Bitmap.createBitmap(myBitmap, 0, 0, options.outWidth,
                    options.outHeight, matrix, true);
            MyLogger.println("My Bitmap after is =" + myBitmap);
            return myBitmap;
        } catch (Exception e) {
            MyLogger.println("out of memory or file not found"
                    + e.getMessage());
            e.printStackTrace();
        }
        return myBitmap;
    }

    public static Bitmap setOrentation(String file, int size) {
        Bitmap myBitmap = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = size;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            myBitmap = BitmapFactory.decodeFile(file, options);
            MyLogger.println("Bitmap value in setorie==" + myBitmap);
            if ((myBitmap.getWidth() >= 800) && (myBitmap.getHeight() >= 1280))
                options.inSampleSize = 5;

            // myBitmap = BitmapFactory.decodeStream(new FileInputStream(new
            // File(
            // file)), null, options);
            ExifInterface exif = new ExifInterface(file);
            String orientString = exif
                    .getAttribute(ExifInterface.TAG_ORIENTATION);
            int orientation = orientString != null ? Integer
                    .parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
            int rotationAngle = 0;

            if (orientation == ExifInterface.ORIENTATION_ROTATE_90)
                rotationAngle = 90;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_180)
                rotationAngle = 180;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_270)
                rotationAngle = 270;

            Matrix matrix = new Matrix();
            matrix.setRotate(rotationAngle, (float) myBitmap.getWidth() / 2,
                    (float) myBitmap.getHeight() / 2);
            myBitmap = Bitmap.createBitmap(myBitmap, 0, 0, options.outWidth,
                    options.outHeight, matrix, true);
            MyLogger.println("My Bitmap after is =" + myBitmap);
            return myBitmap;
        } catch (Exception e) {
            MyLogger.println("out of memory or file not found"
                    + e.getMessage());
            e.printStackTrace();
        }
        return myBitmap;
    }

    public static final int PROMPT_BUTTON_TWO = 110;
    public static final int PROMPT_BUTTON_ONE = 111;

    /*public static void showPrompt(Activity ctx, String title, String message,
                                  int promptType, int requestCode) {
        Intent in = new Intent(ctx, TwoButtonDialog.class);
        in.putExtra("promptType", requestCode);
        in.putExtra("title", title);
        in.putExtra("message", message);
        ctx.startActivityForResult(in, requestCode);

    }

    public static void showPrompt(Activity ctx, String title, String message,
                                  int promptType, int requestCode, String leftButton, String rightButton) {
        Intent in = new Intent(ctx, TwoButtonDialog.class);
        in.putExtra("promptType", requestCode);
        in.putExtra("title", title);
        in.putExtra("message", message);
        in.putExtra("buttonOne", rightButton);
        in.putExtra("buttonTwo", leftButton);
        ctx.startActivityForResult(in, requestCode);

    }*/

    public static void share(Activity activity, String message) {

        String linkToShare = "https://play.google.com/store/apps/details?id=com.mevolife.mevo";// playstorelink

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = message;
//        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
//                "Subject Here");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody + " " + linkToShare);
        activity.startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }

//    public static void  share(Activity activity) {
//        String linkToShare = "https://play.google.com/store/apps/details?id=com.fit.bites";// playstorelink
//
//        Intent email = new Intent(Intent.ACTION_SEND);
//        email.setType("text/plain");
//        email.putExtra(Intent.EXTRA_TEXT, linkToShare);
//        activity.startActivity(email);
//    }


    public static long getHourlyTimeSeconds(long currentTime) {
        Calendar cal = getCalendar();
        cal.setTimeInMillis(currentTime);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 1);
        return cal.getTimeInMillis();// / 1000;

    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static String getSingleKeyJson(String key, String value) {
        JSONObject obj = null;
        try {
            obj = new JSONObject();
            obj.put(key, value);

        } catch (Exception e) {

        }
        if (obj != null)
            return obj.toString();
        else
            return "";

    }

    public static String getJsonFromHashMap(HashMap<String, String> hashMap) {
        JSONObject jsonObj = new JSONObject(hashMap);
        if (jsonObj != null)
            return jsonObj.toString();
        else
            return "";
    }

   /*public static void showSnackToast(Activity mContext,
                                      SnackBar.OnMessageClickListener snackListner, int meszId) {
        SnackBar mSnackBar1 = new SnackBar.Builder(mContext)
                .withStyle(SnackBar.Style.INFO).withSnackBarHeight(65)
                .withOnClickListener(snackListner).withMessageId(meszId)
                .withBackgroundColorId(R.color.challenges_header)
                .withDuration(SnackBar.MED_SNACK).show();
    }*/

    public static void showSnackToast(Activity mContext, String mesz,
                                      boolean finishactivity, int colorId) {
        MyLogger.println("snack bar show 111");
        /*SnackBar mSnackBar2 = new SnackBar.Builder(mContext)
                .withStyle(SnackBar.Style.INFO).withSnackBarHeight(65)
                .withOnClickListener(null).withMessage(mesz)
                .withBackgroundColorId(colorId)
                .withDuration(SnackBar.MED_SNACK).show();*/
        if (finishactivity)
            mContext.finish();
        MyLogger.println("snack bar show 222");
    }

    public static long[] getTimeArray(long time) {
        long[] dateTime = new long[2];
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTimeInMillis(time);
        currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
        currentCalendar.set(Calendar.MINUTE, 0);
        dateTime[0] = currentCalendar.getTimeInMillis();
        currentCalendar.add(Calendar.DAY_OF_MONTH, 1);
        dateTime[1] = currentCalendar.getTimeInMillis();
        dateTime[0] = dateTime[0] / 1000;
        dateTime[1] = dateTime[1] / 1000;
        return dateTime;
    }

    static Calendar currentCalendar;

    /*public static long[] getInitialTime(long time, int calendarType,
                                        int weekCategory, String from) {
        long[] dateTime = new long[2];
        if (currentCalendar == null)
            currentCalendar = getCalendar();

        currentCalendar.setTimeInMillis(time);
        if (calendarType == AppConstants.CALENDAR_DAILY) {
            currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
            currentCalendar.set(Calendar.MINUTE, 0);
            dateTime[0] = currentCalendar.getTimeInMillis();
            currentCalendar.add(Calendar.DAY_OF_MONTH, 1);
            dateTime[1] = currentCalendar.getTimeInMillis();
        } else if (calendarType == AppConstants.CALENDAR_YESTERDAY) {
            currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
            currentCalendar.set(Calendar.MINUTE, 0);
            dateTime[1] = currentCalendar.getTimeInMillis();
            currentCalendar.add(Calendar.DAY_OF_MONTH, -1);
            dateTime[0] = currentCalendar.getTimeInMillis();
        } else if (calendarType == AppConstants.CALENDAR_MONTHLY) {
            currentCalendar.set(Calendar.DAY_OF_MONTH, 1);
            currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
            currentCalendar.set(Calendar.MINUTE, 0);
            dateTime[0] = currentCalendar.getTimeInMillis();
            currentCalendar.add(Calendar.DAY_OF_MONTH,
                    currentCalendar.getMaximum(Calendar.DAY_OF_MONTH) - 1);
            dateTime[1] = currentCalendar.getTimeInMillis();
        } else if (calendarType == AppConstants.CALENDAR_WEEKLY) {
            if (weekCategory == 1) {
                currentCalendar.set(Calendar.DAY_OF_MONTH, 1);
                currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
                currentCalendar.set(Calendar.MINUTE, 0);
            } else if (weekCategory == 2) {
                currentCalendar.set(Calendar.DAY_OF_MONTH, 8);
                currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
                currentCalendar.set(Calendar.MINUTE, 0);
            } else if (weekCategory == 3) {
                currentCalendar.set(Calendar.DAY_OF_MONTH, 15);
                currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
                currentCalendar.set(Calendar.MINUTE, 0);
            } else if (weekCategory == 4) {
                currentCalendar.set(Calendar.DAY_OF_MONTH, 22);
                currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
                currentCalendar.set(Calendar.MINUTE, 0);
            }
            dateTime[0] = currentCalendar.getTimeInMillis();
            if (weekCategory == 4) {
                int remianDays = currentCalendar
                        .getMaximum(Calendar.DAY_OF_MONTH) - 22;
                MyLogger.println("Maximu days in current month = "
                        + remianDays);
                currentCalendar.add(Calendar.DAY_OF_MONTH, remianDays - 1);
            } else {
                currentCalendar.add(Calendar.DAY_OF_MONTH, -6);
            }
            MyLogger.println("Maximu days in current month after = "
                    + currentCalendar.get(Calendar.DAY_OF_MONTH));
            dateTime[1] = currentCalendar.getTimeInMillis();

        } else if (calendarType == AppConstants.CALENDAR_HOURLY) {
            if (from.equalsIgnoreCase("Graph"))
                currentCalendar.setTimeInMillis(time);
            else {
                currentCalendar = getCalendar();
                currentCalendar.set(Calendar.HOUR_OF_DAY, weekCategory - 1);
            }
            MyLogger.println("Time here is ==before= "
                    + currentCalendar.getTime().toGMTString() + "==="
                    + weekCategory);
            // currentCalendar.set(Calendar.HOUR_OF_DAY, weekCategory - 1);
            MyLogger.println("Time here is ==after1= "
                    + currentCalendar.getTime().toGMTString());
            currentCalendar.set(Calendar.MINUTE, 1);
            dateTime[0] = currentCalendar.getTimeInMillis();
            if (from.equalsIgnoreCase("Graph"))
                currentCalendar.add(Calendar.HOUR_OF_DAY, 3);
            else
                currentCalendar.add(Calendar.HOUR_OF_DAY, 1);
            MyLogger.println("Time here is ==after2= "
                    + currentCalendar.getTime().toGMTString());
            MyLogger.println("Time here is ==after= "
                    + currentCalendar.getTime().toGMTString());
            dateTime[1] = currentCalendar.getTimeInMillis();
        } else if (calendarType == AppConstants.CALENDAR_TOMORROW) {
            currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
            currentCalendar.set(Calendar.MINUTE, 0);
            currentCalendar.add(Calendar.DAY_OF_MONTH, 1);
            dateTime[0] = currentCalendar.getTimeInMillis();
            currentCalendar.add(Calendar.DAY_OF_MONTH, 1);
            dateTime[1] = currentCalendar.getTimeInMillis();
        }

        MyLogger.println("Date time here is = " + dateTime[0] + "==="
                + dateTime[1]);
        dateTime[0] = dateTime[0] / 1000;
        dateTime[1] = dateTime[1] / 1000;
        return dateTime;
    }*/

    public static long getInitialTimeWeek(long time) {
        if (currentCalendar == null)
            currentCalendar = getCalendar();


        currentCalendar.setTimeInMillis(time);

        System.out.println("<<<< getInitialtimeWeek 0000 : " +
                new SimpleDateFormat("dd/MM/yyyy").format(time)
                + currentCalendar.getFirstDayOfWeek());

        currentCalendar.set(Calendar.DAY_OF_WEEK, currentCalendar.getFirstDayOfWeek());
        currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
        currentCalendar.set(Calendar.MINUTE, 0);
        currentCalendar.set(Calendar.SECOND, 0);
        currentCalendar.set(Calendar.MILLISECOND, 1);
        System.out.println("<<<< getInitialtimeWeek 1111 : " +
                new SimpleDateFormat("dd/MM/yyyy").format(currentCalendar.getTimeInMillis())
                + currentCalendar.getFirstDayOfWeek());

        return currentCalendar.getTimeInMillis();
    }


    public static long getInitialTimeMonth(long time) {
        if (currentCalendar == null)
            currentCalendar = getCalendar();


        currentCalendar.setTimeInMillis(time);

        System.out.println("<<<< getInitialtimeWeek 0000 : " + new SimpleDateFormat("dd/MM/yyyy").format(time));

        currentCalendar.set(Calendar.DAY_OF_MONTH, 1);
        currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
        currentCalendar.set(Calendar.MINUTE, 0);
        currentCalendar.set(Calendar.SECOND, 0);
        currentCalendar.set(Calendar.MILLISECOND, 1);
        System.out.println("<<<< getInitialtimeWeek 1111 : " +
                new SimpleDateFormat("dd/MM/yyyy").format(currentCalendar.getTimeInMillis())
                + currentCalendar.getFirstDayOfWeek());

        return currentCalendar.getTimeInMillis();
    }

    public static long[] getInitialTime(long todayDate, int fromdate, int todate) {
        Calendar currentCalendar = null;
        long[] dateTime = new long[2];
        if (currentCalendar == null)
            currentCalendar = getCalendar();

        currentCalendar.setTimeInMillis(todayDate);
        MyLogger.println("<<<< testing calender 0000 : " + currentCalendar.getTimeInMillis());
        currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
        currentCalendar.set(Calendar.MINUTE, 0);
        currentCalendar.add(Calendar.DAY_OF_MONTH, fromdate);

        MyLogger.println("<<<< testing calender 1111 : " + currentCalendar.getTimeInMillis());

        dateTime[1] = currentCalendar.getTimeInMillis();
        if (todate == 0)
            currentCalendar.add(Calendar.DAY_OF_MONTH, -999);
        else
            currentCalendar.add(Calendar.DAY_OF_MONTH, todate);

        MyLogger.println("<<<< testing calender 2222 : " + currentCalendar.getTimeInMillis());

        dateTime[0] = currentCalendar.getTimeInMillis();
        dateTime[0] = dateTime[0] / 1000;
        dateTime[1] = dateTime[1] / 1000;

        MyLogger.println("date time in getinitail time for evo grid==="
                + dateTime[0] + "====date==" + dateTime[1]);

        return dateTime;

    }

    public static int getCurrentDay() {
        Calendar cal = getCalendar();
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public long getMonthDate(long time) {

        Calendar cal = getCalendar();
        cal.setTimeInMillis(time * 1000);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis() / 1000;
    }

    public static long getDateDay(long time) {

        Calendar cal = getCalendar();
        cal.setTimeInMillis(time * 1000);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis() / 1000;
    }

    /*public static int statusBarColorWithShadow(int color, Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true, activity);
        }

        SystemBarTintManager tintManager;
        tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(color);
        return color;
    }*/

    @TargetApi(19)
    public static void setTranslucentStatus(boolean on, Activity act) {
        Window win = act.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public static String GetCountryZipCode(Context context) {

        String CountryID = "";
        String CountryZipCode = "";

        TelephonyManager manager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        // getNetworkCountryIso
        CountryID = manager.getSimCountryIso().toUpperCase();
        String[] rl = context.getResources().getStringArray(
                R.array.CountryCodes);
        for (int i = 0; i < rl.length; i++) {
            String[] g = rl[i].split(";");
            if (g[0].trim().equals(CountryID.trim())) {
                CountryZipCode = g[2];
                break;
            }
        }
        return CountryZipCode;
    }

    public static boolean isOnline(Context ctx) {
        try {
            ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

            return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable()
                    && cm.getActiveNetworkInfo().isConnected();
        } catch (Exception e) {

        }
        return false;
    }

    public static int GetCountryZipCodePos(Context context) {

        String CountryID = "";
        String CountryZipCode = "";

        TelephonyManager manager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        // getNetworkCountryIso
        CountryID = manager.getSimCountryIso().toUpperCase();
        String[] rl = context.getResources().getStringArray(
                R.array.CountryCodes);
        for (int i = 0; i < rl.length; i++) {
            String[] g = rl[i].split(";");
            if (g[0].trim().equals(CountryID.trim())) {
                return i;
            }
        }
        return 0;
    }

    public static LinkedHashMap<String, CountryInfo> getCounteryInfoMAP(
            Context context) {
        LinkedHashMap<String, CountryInfo> countery_info_map = new LinkedHashMap<String, CountryInfo>();
        String[] rl = context.getResources().getStringArray(
                R.array.CountryCodes);
        for (int i = 0; i < rl.length; i++) {
            String[] g = rl[i].split(";");
            CountryInfo countryInfo = new CountryInfo();
            countryInfo.name = g[1];
            countryInfo.zipcode = g[2];
            countryInfo.countery_id = g[0];
            countery_info_map.put(g[0], countryInfo);
        }
        return countery_info_map;
    }

    public static ArrayList<CountryInfo> getCounteryInfoList(Context context) {
        ArrayList<CountryInfo> countery_info_map = new ArrayList<CountryInfo>();
        String[] rl = context.getResources().getStringArray(
                R.array.CountryCodes);
        for (int i = 0; i < rl.length; i++) {
            String[] g = rl[i].split(";");
            CountryInfo countryInfo = new CountryInfo();
            countryInfo.name = g[1];
            countryInfo.zipcode = g[2];
            countryInfo.countery_id = g[0];
            countery_info_map.add(countryInfo);
        }
        return countery_info_map;
    }

    public static class CountryInfo {
        public String name;
        public String zipcode;
        public String countery_id;
    }

    public static void showDialogAnimation(Activity activity) {
        activity.overridePendingTransition(R.anim.abc_fade_in,
                R.anim.slide_out_right);
    }

    @SuppressLint("NewApi")
    public static int setstatusBarColor(int color, Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (activity.getClass().getName().equalsIgnoreCase("com.mig.appdashboard.DashBoardDemo")) {
                window.setStatusBarColor(activity.getResources().getColor(R.color.mealplan_header_status));
            } else {
                window.setStatusBarColor(activity.getResources().getColor(color));
            }
        }
        return color;
    }

    public static int getScaledValue(float value, Context mContext) {
        float density = mContext.getResources().getDisplayMetrics().density;
        return (int) (value * density);
    }

    public static void showKeyboard(Activity activity) {
        if (activity != null) {
            activity.getWindow()
                    .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public static void hideKeyboard(Activity act) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) act.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(act.getCurrentFocus()
                    .getWindowToken(), 0);
        } catch (Exception e) {

        }
    }

    public static void showKeyboard(View view, Context ctx) {
        try {
            view.requestFocus();
            InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        } catch (Exception e) {

        }

    }

    public static void hideKeyboardActivityStarts(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static void openBrowser(Activity mActivity, String url) {
        try {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            mActivity.startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(mActivity, "No application can handle this request."
                    + " Please install a webbrowser", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public static final Bitmap createDrawableFromView(final Context context,
                                                      final View v) {
        //

        v.setDrawingCacheEnabled(true);

        // getActivity() is the important code :)
        // Without it the view will have a dimension of 0,0 and the bitmap will
        // be null
        v.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());

        v.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache(), 0, 0, 200, 200);
        //      v.setDrawingCacheEnabled(false); // clear drawing cache
        return b;
    }

    public static Calendar getCalendar() {
        Calendar cal = Calendar.getInstance();
//        TimeZone timeZone = TimeZone.getTimeZone("GMT+00:00");
//        cal.setTimeZone(timeZone);
        MyLogger.println("Time zone time is ==" + cal.toString());
        return cal;
    }

   /* static LatLng latlng = null;

    public static LatLng getLocation(Context ctx) {
        LocationManager locationManager = null;
        final AppSharedData appData = new AppSharedData(ctx);
        try {
            if (locationManager == null)
                locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);

            List<String> providers = locationManager.getAllProviders();
            Location location;

            for (String provider : providers) {

                if (locationManager.isProviderEnabled(provider)) {
                    // dont use listener, it sometimes does not work
                    location = locationManager.getLastKnownLocation(provider);
                    if (location != null) {
                        // getDeviceInfo(location, context);
                        latlng = new LatLng(location.getLatitude(), location.getLongitude());
                        appData.setLastLocation(latlng.latitude + "," + latlng.longitude);
                        break;
                    }
                }

            }

            if (latlng == null) {
                MyLocation loc = new MyLocation();
                loc.getLocation(ctx, new MyLocation.LocationResult() {

                    @Override
                    public void gotLocation(Location location) {
                        if (location != null) {
                            latlng = new LatLng(location.getLatitude(), location.getLongitude());
                            appData.setLastLocation(latlng.latitude + "," + latlng.longitude);
                        }
                    }
                });
            }
        } catch (Exception e) {
        }

        return latlng;
    }*/

    public static float toLbs(float kgs) {
        return kgs * 2.2046f;
    }

    public static void handleFaceBook(Context context) {
        try {
//            com.facebook.appevents.AppEventsLogger.activateApp(context, context.getResources().getString(R.string.fb_id));
        } catch (Exception e) {
            MyLogger.println("<<<< handleFaceBook error : " + e);
        }

    }

    public static float toKgs(float lbs) {
        return lbs / 2.2046f;
    }

    public static int toInt(float lbs) {
        return (int) lbs;
    }

    public static String inchtoString(float inch) {
        int myfeet = (int) (inch / 12);
        int myinch = (int) (inch % 12);
        return myfeet + " ft" + " " + myinch + " in";

    }

    public static String getContactDisplayNameByNumber(Context ctx, String number) {
        MyLogger.println("getContactDisplayNameByNumber == " + number);
        String temp = "";

//
//        PhoneNumberUtil p = PhoneNumberUtil.getInstance();
//        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
//        String region = tm.getSimCountryIso();
//        Phonenumber.PhoneNumber pn = null;
//        try {
//            pn = p.parse(number, region);
//        } catch (NumberParseException e) {
//            e.printStackTrace();
//        }
//
//        boolean isValid = p
//                .isValidNumber(pn);
//        if (isValid) {
//            String internationalFormat = p.format(
//                    pn,
//                    PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
//            MyLogger.println("complete phone number is === " + internationalFormat);
//        }
//        String nationalnumber = String.valueOf(pn.getNationalNumber()); //2012345678
//        String countrycallingcode = String.valueOf(pn.getCountryCode()); //44

        return "";

//        if (number != null && number.length() != 0) {
//
//            String name = number;// lookupNumber(ctx, number);
//
//            if (!name.contains(number))
//                return name;
//            else {
//
//                TelephonyManager manager = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
//
//                String usersCountryISOCode = manager.getNetworkCountryIso();
//
//                PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
//                try {
//                    Phonenumber.PhoneNumber phonenUmber = phoneUtil.parse(name,
//                            usersCountryISOCode);
//                    if (phoneUtil.isValidNumber(phonenUmber)) {
//                        temp = phoneUtil
//                                .getNationalSignificantNumber(phonenUmber);
//
//
//                        //  name = lookupNumber(ctx, temp);
//                        MyLogger.println("contact number and name are == " + name + "===" + number);
//                        return name;
//                    }
//
//                } catch (Exception e) {
//                    MyLogger.println("Exception here at 222==" + e.getMessage());
//                    e.printStackTrace();
//
//                }
//                MyLogger.println("contact number and name are ==usersCountryISOCode " + usersCountryISOCode);
//                return number;
//            }
//        } else {
//            return "Invalid Number";
//        }
    }

    private static String lookupNumber(Context ctx, String number) {

        Uri uri = Uri.withAppendedPath(
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(number));
        String name = number;

        ContentResolver contentResolver = ctx.getContentResolver();
        Cursor contactLookup = contentResolver.query(uri, new String[]{
                BaseColumns._ID, ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);

        try {
            if (contactLookup != null && contactLookup.getCount() > 0) {
                contactLookup.moveToNext();
                name = contactLookup.getString(contactLookup
                        .getColumnIndex(ContactsContract.Data.DISPLAY_NAME));

            }
        } catch (Exception e) {
            MyLogger.println("Exception here at 111==" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (contactLookup != null) {
                contactLookup.close();
            }
        }

        return name;
    }

    public static Bitmap getBitmapFromView(Activity ctx, View view) {
        DisplayMetrics metrics = new DisplayMetrics();
        ctx.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        int totalHeight = view.getHeight();
        int totalWidth = view.getWidth();
        float percent = totalHeight / (float) totalHeight;

        Bitmap canvasBitmap = Bitmap.createBitmap(totalWidth,
                (int) (totalHeight * percent), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(canvasBitmap);

        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);

        canvas.save();
        canvas.scale(percent, percent);
        view.draw(canvas);
        canvas.restore();

        return canvasBitmap;
    }

   /* public static void showPremiumFeaturePrompt(String moduleName, Activity mActivity) {
        boolean mesz = true;
        if (mesz)
            return;
        AppSharedData sharedData = new AppSharedData(mActivity.getApplicationContext());

        PurchaseHandler purchase = null;
        PurchaseHandler.PremiumFeature moduleType = null;
        if (moduleName == AppConstants.MODULE_RECIPE)
            moduleType = PurchaseHandler.PremiumFeature.Ads;
//        else if (moduleName == AppConstants.MODULE_TIPS)
//            moduleType = PurchaseHandler.PremiumFeature.Tips;
//        else if (moduleName == AppConstants.MODULE_EXERCISE)
//            moduleType = PurchaseHandler.PremiumFeature.Exercise;
//        else if (moduleName == AppConstants.MODULE_YOGA)
//            moduleType = PurchaseHandler.PremiumFeature.Yoga;
        else if (moduleName == AppConstants.MODULE_WORKOUT)
            moduleType = PurchaseHandler.PremiumFeature.Ads;

        purchase = new PurchaseHandler(mActivity, moduleType);
        if (!purchase.isFeatureUnlocked())
            return;

        if (moduleName == AppConstants.MODULE_RECIPE && sharedData.isPremiumRecipe())
            return;
        else if (moduleName == AppConstants.MODULE_TIPS && sharedData.isPremiumTips())
            return;
        else if (moduleName == AppConstants.MODULE_EXERCISE && sharedData.isPremiumExercise())
            return;
        else if (moduleName == AppConstants.MODULE_YOGA && sharedData.isPremiumYoga())
            return;
        else if (moduleName == AppConstants.MODULE_WORKOUT && sharedData.isPremiumWorkout())
            return;

        *//*Intent in = new Intent(mActivity, PremiumUnlockedPrompt.class);
        in.putExtra("boxId", purchase.getPurchasedBoxId());
        in.putExtra("moduleType", moduleName);
        mActivity.startActivity(in);*//*

    }*/


   /* public static View setMaterialCardView(final Context context, View childview, Card.OnCardClickListener onCardClickListener) {
        if (context == null)
            return childview;
        MaterialLargeImageCard card = new MaterialLargeImageCard(context);
        card.setOnClickListener(onCardClickListener);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//getLayoutInflater();
        View mainview = inflater.inflate(R.layout.native_material_nativecardview, null, false);
        try {

            CardViewNative cardView = (CardViewNative) mainview.findViewById(R.id.cardview_main);
            cardView.setCard_layout_resourceID(R.layout.native_material_largeimage_text_card_cusmaterial);
            View view_main = cardView.getInternalOuterView();
            cardView.setClipChildren(false);

            ForegroundLinearLayout foregroundLinearLayout = (ForegroundLinearLayout) view_main.findViewById(R.id.card_main_layout);
            foregroundLinearLayout.removeAllViews();
            foregroundLinearLayout.setClipChildren(false);
            Utils.setForegroundSelectionToView(context, foregroundLinearLayout);
            foregroundLinearLayout.addView(childview);
            cardView.setCard(card);
        } catch (Exception e) {
            System.out.println("<<<< exception while making card view riple : " + e);
        }

        return mainview;

    }*/

   /* public static View setMaterialCardView(final Context context, View childview, String moduleType, Card.OnCardClickListener onCardClickListener) {
        MaterialLargeImageCard card = new MaterialLargeImageCard(context);
        card.setTag(moduleType);
        card.setOnClickListener(onCardClickListener);
        //Set card in the CardViewNative
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//getLayoutInflater();
        View mainview = inflater.inflate(R.layout.native_material_nativecardview, null, false);
//      childview=inflater.inflate(R.layout.native_material_largeimage_text_cardcustom, null, false);
        CardViewNative cardView = (CardViewNative) mainview.findViewById(R.id.cardview_main);
        cardView.setCard_layout_resourceID(R.layout.native_material_largeimage_text_card_cusmaterial);
        View view_main = cardView.getInternalOuterView();

        ForegroundLinearLayout foregroundLinearLayout = (ForegroundLinearLayout) view_main.findViewById(R.id.card_main_layout);
        Utils.setForegroundSelectionToView(context, foregroundLinearLayout);
        foregroundLinearLayout.removeAllViews();
        foregroundLinearLayout.addView(childview);
        cardView.setCard(card);

        mainview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                System.out.println("Item clicked long == ");
                return false;
            }
        });
        return mainview;
    }*/

    /*public static void setBackgroudSelectionToView(Context mContext, View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setBackgroundResource(R.drawable.ripple_effect);
        } else {
            view.setBackgroundResource(R.drawable.prelollipop_selection);
        }
    }*/

    public static void setForegroundSelectionToView(Context mContext, ForegroundLinearLayout view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setClipChildren(false);
//            view.setForeground(mContext.getResources().getDrawable(R.drawable.ripple_effect));
        } else {
            view.setClipChildren(false);
//            view.setForeground(mContext.getResources().getDrawable(R.drawable.prelollipop_selection));
        }
    }

    /*public static void setBackgroudSelectionToView(Context mContext, View view, int selectioncolor, int unselectcolor) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setBackgroundResource(R.drawable.ripple_effect);
        } else {
            if (selectioncolor == 0 || unselectcolor == 0) {
                view.setBackgroundResource(R.drawable.prelollipop_selection);
                return;
            }
            StateListDrawable mIcon = new StateListDrawable();
            mIcon.addState(
                    new int[]{android.R.attr.state_pressed},
                    new ColorDrawable(selectioncolor));

            mIcon.addState(
                    new int[]{-android.R.attr.state_pressed},
                    new ColorDrawable(unselectcolor));

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackgroundDrawable(mIcon);
            } else
                view.setBackground(mIcon);
        }

    }*/

   /* public static void setRippleBackgroudSelection(Context mContext, final View view, final ViewGroup parent1, Object tag, final View.OnClickListener onClickListener) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setBackgroundResource(R.drawable.ripple_effect);
            view.setOnClickListener(onClickListener);
        } else {
            int optionId = R.layout.rippleparent_layout;
            ViewGroup parent = (ViewGroup) view.getParent();
            int index = parent.indexOfChild(view);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
            parent.removeView(view);
            ViewGroup view1 = (ViewGroup) ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(optionId, parent, false);
            view1.addView(view);
            view.setTag(tag);
            view.setOnClickListener(null);

            view1.setLayoutParams(layoutParams);
            MyLogger.println(" 1989 layout params " + view.getLayoutParams());
            parent.addView(view1, index);
        }


    }

    public static void setRippleBackgroudSelectionReminder(Context mContext, final View view, final ViewGroup parent1, Object tag, final View.OnClickListener onClickListener) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setBackgroundResource(R.drawable.ripple_effect);
            view.setOnClickListener(onClickListener);
        } else {
            int optionId = R.layout.rippleparent_layout;
            ViewGroup parent = (ViewGroup) view.getParent();
            int index = parent.indexOfChild(view);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
            parent.removeView(view);
            ViewGroup view1 = (ViewGroup) ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(optionId, parent, false);
            view1.addView(view);
            view.setTag(tag);
            view.setOnClickListener(null);

            view1.setLayoutParams(layoutParams);
            parent.addView(view1, index);
        }


    }

    public static void setRippleBackgroudSelectionReminder1(Context mContext, final View view, final ViewGroup parent1, Object tag, final View.OnClickListener onClickListener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setBackgroundResource(R.drawable.ripple_effect);
            view.setOnClickListener(onClickListener);
        } else {
            int optionId = R.layout.rippleparent_layout;
            ViewGroup parent = (ViewGroup) view.getParent();
            int index = parent.indexOfChild(view);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            parent.removeView(view);
            ViewGroup view1 = (ViewGroup) ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(optionId, parent, false);
            view1.addView(view);
            view.setTag(tag);
            view.setOnClickListener(null);

            view1.setLayoutParams(layoutParams);
            parent.addView(view1, index);
        }


    }*/

    public static boolean isMyServiceRunning(Context context, String servicename, String from) {
        MyLogger.println("Latlng value are = starting service from : " + from);
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager
                .getRunningServices(Integer.MAX_VALUE)) {
            if (servicename.equals(
                    service.service.getClassName())) {
                MyLogger.println("<<<< set total starting service true from : " + from);
                return true;
            }
        }

        MyLogger.println("<<<< set total starting service false from : " + from);
        return false;
    }

    public static int scaledValue(Context context, float value) {
        float scale = context.getResources().getDisplayMetrics().density;
        int dip = (int) (value * scale + 0.5f);
        return dip;
    }

    private final static float METRIC_RUNNING_FACTOR = 1.02784823f;

   /* public static float getCalories(float mBodyWeight, float distanceTravelled, Context mContext) {
        if (UserDetailEntity.getInstance(mContext).getWeightunit().equalsIgnoreCase(AppConstants.poundUnit))
            mBodyWeight = mBodyWeight * 0.453592f;
        float caloriesBurned = mBodyWeight * METRIC_RUNNING_FACTOR
                * distanceTravelled; // kilometer
        System.out.println("Calories calculate value is == " + mBodyWeight + "==" + distanceTravelled + "==" + caloriesBurned);
        return caloriesBurned;
    }

    public static String getFormatedDistance(float distanceMeter, AppSharedData sharedData) {
        if (sharedData.getDistanceUnit().equalsIgnoreCase(AppConstants.UNIT_DISTANCE_KG)) {
            if (distanceMeter < 1000) {
                return formatString2Digit("" + distanceMeter) + "" + AppConstants.M;
            } else {
                return Utils.formatString2Digit("" + (distanceMeter / 1000)) + "" + AppConstants.KM;
            }
        }
        return distanceMeter + "";
    }

    public static String getFormatedAvgSpeed(float distanceMeter, long duration, AppSharedData sharedData) {
        float avgspeed = (float) distanceMeter / duration; //m/s
        float avgKM = avgspeed * 3.6f; //km/hr
        return formatString2Digit(avgKM) + " km/hr";
    }

    public static String getFormatedPace(float distanceMeter, long duration, AppSharedData sharedData) {
        float lastDis = distanceMeter / 1000;//km
        float timeDiff = (float) duration / 60;
        float pace = timeDiff / lastDis;  //m/km
        int second = (int) ((pace * 60) % 60);
        int minute = (int) (pace);
        String paceValue = Utils.formatString2Digit("" + minute) + "' " + Utils.formatString2Digit(second + "") + "\" ";
        return paceValue;
    }*/


   /* public static int getSteps(float totalDistanceInMeter, Context ctx) {
        Float bodyHeight = UserDetailEntity.getInstance(ctx).getHeight();
        if (bodyHeight <= 1)
            bodyHeight = 68f;
        float stepLenght = bodyHeight * 0.414f;
        float stepLenghtMeter = stepLenght * 0.0254f;

        int totalSteps = (int) (totalDistanceInMeter / stepLenghtMeter);
        MyLogger.println("Steps total value is = " + totalDistanceInMeter + "==" + stepLenght + "==" + stepLenghtMeter + "===" + UserDetailEntity.getInstance(ctx).getHeight());
        return totalSteps;

    }

    public static void setMapWithInScreen(List<LocationEntity> points, GoogleMap mapView) {
        try {
            LatLngBounds.Builder bc = new LatLngBounds.Builder();

            for (LocationEntity item : points) {
                bc.include(new LatLng(item.getLatitude(), item.getLongitude()));
            }

            mapView.moveCamera(CameraUpdateFactory.newLatLngBounds(bc.build(), 80));
            mapView.animateCamera(CameraUpdateFactory.newLatLngBounds(bc.build(), 80));
        } catch (Exception ex) {
            MyLogger.println("Exception at bound==" + ex.getMessage());
        }
    }*/

    public static boolean isValidEmail(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /*public static ArrayList<ProfileCustomField> makePRofileCustom(UserDetailEntity user) {

        profileCustomFields = new ArrayList<>();
        MyLogger.println("set<<<<<<<<<<agefro>>1>" + user.getAge());
        addCustomField(UserCustomField.EVENT_PROFILE_AGE, "Age", String.valueOf(user.getAge()), "Years");
        addCustomField(UserCustomField.EVENT_PROFILE_WEIGHT, "Weight", String.valueOf(UserDetailEntity.getWeight()), String.valueOf(user.getWeightunit()));
        addCustomField(UserCustomField.EVENT_PROFILE_HEIGHT, "Height", String.valueOf(user.getHeight()), String.valueOf(user.getHeightunit()));
        addCustomField(UserCustomField.EVENT_WEIGHT_PER_WEEK, "WeightPerWeek", String.valueOf(user.getTargetWeightPerWeek()), String.valueOf(user.getTargetWeightPerWeekUnit()));
        addCustomField(UserCustomField.EVENT_LIFESTYLE, "Lifestyle", String.valueOf(user.getActivity_level()), "NA");
        addCustomField(UserCustomField.EVENT_GOAL, "GoalType", user.getGoalType(), "NA");
        addCustomField(UserCustomField.EVENT_MYCALORIES, "MyCalories", String.valueOf(user.getMycalories()), "0");
        addCustomField(UserCustomField.EVENT_MYPROTEIN, "MyProtein", String.valueOf(user.getMyprotein()), "0");
        addCustomField(UserCustomField.EVENT_MYCARBS, "MyCarbs", String.valueOf(user.getMycarbs()), "0");
        addCustomField(UserCustomField.EVENT_MYFATS, "MyFats", String.valueOf(user.getMyfat()), "0");
        addCustomField(UserCustomField.EVENT_DOB, "DateOfBirth", String.valueOf(user.getDateofbirth()), "0");
        addCustomField(UserCustomField.EVENT_IDEAL_WEIGHT, "Ideal Weight", String.valueOf(user.getTargetweight()), "0");


        System.out.println("Basic values are === 0000 : " + profileCustomFields.size() + "========" + profileCustomFields.toString());

        return profileCustomFields;
    }*/

    private static ArrayList<ProfileCustomField> profileCustomFields;

    private static void addCustomField(String customFieldID, String fieldName, String value, String unit) {
        ProfileCustomField customField = new ProfileCustomField();
        customField.id = customFieldID;
        customField.name = fieldName;
        customField.value = value;

        customField.unit = unit;
        profileCustomFields.add(customField);

    }

    /**
     * // Males: IBW = 50 kg + 2.3 kg for each inch over 5 feet.
     * // Females: IBW = 45.5 kg + 2.3 kg for each inch over 5 feet.
     **/

    public static float getIdealWeight(Context mContext) {
        return UserDetailEntity.getInstance(mContext).getTargetweight();

    }




   /* public static long getStartDateFromMinuteSy(com.mevodevice.HealthSleep sleepData, int type) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, sleepData.getYear());
        cal.set(Calendar.MONTH, sleepData.getMonth() - 1);
        if (sleepData.getSleepEndedTimeH() < sleepData.getSleepStartedTimeH())
            cal.set(Calendar.DAY_OF_MONTH, sleepData.getDay() + 1);
        else
            cal.set(Calendar.DAY_OF_MONTH, sleepData.getDay());
        long initialDate = getTodayInitialDate(cal.getTimeInMillis()) * 1000;
        initialDate = initialDate + ((sleepData.getSleepEndedTimeH() * 60 * 60 * 1000) + (sleepData.getSleepEndedTimeM() * 60 * 1000));
        initialDate = initialDate - (sleepData.getTotalSleepMinutes() * 60 * 1000);

        System.out.println("Utils.wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwgetStartDateFromMinute >>check 2  " + sleepData.getTotalSleepMinutes());
        System.out.println("Utils.wwwwwwwwwwwwwwwwwwwcalutetime" + initialDate / 1000 + "---------- " + initialDate);
        return initialDate / 1000;
    }

    public static long getEndStartDateFromMinuteSy(com.mevodevice.HealthSleep sleepData) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, sleepData.getYear());
        cal.set(Calendar.MONTH, sleepData.getMonth() - 1);
        if (sleepData.getSleepEndedTimeH() < sleepData.getSleepStartedTimeH())
            cal.set(Calendar.DAY_OF_MONTH, sleepData.getDay() + 1);
        else
            cal.set(Calendar.DAY_OF_MONTH, sleepData.getDay());
        long initialDate = getTodayInitialDate(cal.getTimeInMillis()) * 1000;
        initialDate = initialDate + ((sleepData.getSleepEndedTimeH() * 60 * 60 * 1000) + (sleepData.getSleepEndedTimeM() * 60 * 1000));
        System.out.println("Utils.wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwgetEndStartDateFromMinute check " + cal.getTimeInMillis() / 1000);
        return initialDate / 1000;
    }*/

   /* public static long getStartDateFromMinute(com.mevodevice.HealthSleep sleepData, int type) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, sleepData.getYear());
        cal.set(Calendar.MONTH, sleepData.getMonth() - 1);
        cal.set(Calendar.DAY_OF_MONTH, sleepData.getDay());
        System.out.println("Utils.wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwgetStartDateFromMinute >>check 1  " + cal.getTimeInMillis());
        cal.set(Calendar.HOUR, sleepData.getSleepEndedTimeH());
        cal.set(Calendar.MINUTE, sleepData.getSleepEndedTimeM());
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        if (sleepData.getSleepEndedTimeH() > 12)
            cal.set(Calendar.AM_PM, Calendar.PM);
        else
            cal.set(Calendar.AM_PM, Calendar.AM);

        long time = cal.getTimeInMillis();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(time);
        System.out.println("Utils.wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwgetStartDateFromMinute >>check 2  " + time);
//        cal1.add(Calendar.HOUR, -sleepData.getTotalSleepMinutes() / 60);
        cal1.add(Calendar.MINUTE, -sleepData.getTotalSleepMinutes());
        System.out.println("Utils.wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwgetStartDateFromMinute >>check 3  " + cal1.getTimeInMillis());


        System.out.println("Utils.wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwgetStartDateFromMinute " + cal1.getTimeInMillis() / 1000 + "\t\t" + cal1.toString());
        return cal1.getTimeInMillis() / 1000;
    }

    public static long getEndStartDateFromMinute(com.mevodevice.HealthSleep sleepData) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, sleepData.getYear());
        cal.set(Calendar.MONTH, sleepData.getMonth() - 1);
        cal.set(Calendar.DAY_OF_MONTH, sleepData.getDay());
        cal.set(Calendar.HOUR, sleepData.getSleepEndedTimeH());
        cal.set(Calendar.MINUTE, sleepData.getSleepEndedTimeM());
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        if (sleepData.getSleepEndedTimeH() / 60 > 12)
            cal.set(Calendar.AM_PM, Calendar.PM);
        else
            cal.set(Calendar.AM_PM, Calendar.AM);
        System.out.println("Utils.wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwgetEndStartDateFromMinute check " + cal.getTimeInMillis() / 1000);
        return cal.getTimeInMillis() / 1000;
    }

    public static long getStartDateFromMinute(SleepData sleepData) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, sleepData.getYear());
        cal.set(Calendar.MONTH, sleepData.getMonth() - 1);
        cal.set(Calendar.DAY_OF_MONTH, sleepData.getDay());


        long initialDate = getTodayInitialDate(cal.getTimeInMillis()) * 1000;
        initialDate = initialDate + (sleepData.getStart_time() * 60 * 1000);
        System.out.println("<<< dummy data sleep changing time 1111 : " + initialDate);
        return initialDate;
    }

    //for iwon sleep
    public static long getStartDateFromMinuteIwown(SleepDataIw sleepData) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, sleepData.getYear());
        cal.set(Calendar.MONTH, sleepData.getMonth() - 1);
        cal.set(Calendar.DAY_OF_MONTH, sleepData.getDay());


        long initialDate = getTodayInitialDate(cal.getTimeInMillis()) * 1000;
        initialDate = initialDate + (sleepData.getStart_time() * 60 * 1000);
        System.out.println("<<< dummy data sleep changing time 1111 : " + initialDate);
        return initialDate;
    }

    public static long getEndStartDateFromMinuteIwown(SleepDataIw sleepData) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, sleepData.getYear());
        cal.set(Calendar.MONTH, sleepData.getMonth() - 1);
        if (sleepData.getEnd_time() < sleepData.getStart_time())
            cal.set(Calendar.DAY_OF_MONTH, sleepData.getDay() + 1);
        else
            cal.set(Calendar.DAY_OF_MONTH, sleepData.getDay());

        long initialDate = getTodayInitialDate(cal.getTimeInMillis()) * 1000;
        initialDate = initialDate + (sleepData.getEnd_time() * 60 * 1000);
        System.out.println("<<< dummy data sleep changing time 1111 : " + initialDate);
        return initialDate;
    }
    //end iwon sleep

    public static long getEndStartDateFromMinute(SleepData sleepData) {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, sleepData.getYear());
        cal.set(Calendar.MONTH, sleepData.getMonth() - 1);
        if (sleepData.getEnd_time() < sleepData.getStart_time())
            cal.set(Calendar.DAY_OF_MONTH, sleepData.getDay() + 1);
        else
            cal.set(Calendar.DAY_OF_MONTH, sleepData.getDay());


        long initialDate = getTodayInitialDate(cal.getTimeInMillis()) * 1000;
        initialDate = initialDate + (sleepData.getEnd_time() * 60 * 1000);
        System.out.println("<<< dummy data sleep changing time 1111 : " + initialDate);
        return initialDate;
    }*/


    public static String readFromAssert(Context context, String fileName) {
        String json = null;
        InputStream is;
        try {
            is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (Exception e) {
        }
//			MyLogger.println("1989 loaded json = "+json);
        return json;
    }

    public void decodeFileForCamera(File f, int cameraType, String imagePath) {
        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream stream1 = new FileInputStream(f);
            BitmapFactory.decodeStream(stream1, null, o);
            stream1.close();

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;

            System.out.println("<<<< capture image picture taken inside orientation 0000 : ");
            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            FileInputStream stream2 = new FileInputStream(f);
            Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, o2);
            stream2.close();

            if (cameraType == 0) {
                if ((bitmap.getWidth() >= 800) && (bitmap.getHeight() >= 1280)) {
                    o2.inSampleSize = 5;
                    System.out.println("<<<< capture image picture taken inside blog for back : "
                            + bitmap.getWidth() + " height : " + bitmap.getHeight());
                }
            }

            ExifInterface exif = new ExifInterface(f.getPath());
            String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);

            System.out.println("<<<< capture image picture taken inside orientation 1111 : " + orientString);

            int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;

            System.out.println("<<<< capture image picture taken inside orientation 2222 : " + orientation);

            int rotationAngle = 0;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90)
                rotationAngle = 90;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_180)
                rotationAngle = 180;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_270)
                rotationAngle = 270;

            if (orientation == ExifInterface.ORIENTATION_UNDEFINED)
                if (cameraType == 1)
                    rotationAngle = 180;

            String deviceMan = Build.MANUFACTURER;
            System.out.println("<<<< capture image picture taken inside orientation 3333 a : " + rotationAngle);

            if (deviceMan.equalsIgnoreCase("samsung") && orientation == ExifInterface.ORIENTATION_ROTATE_90)
                rotationAngle = 270;

            System.out.println("<<<< capture image picture taken inside orientation 3333 b : " + rotationAngle);

            Matrix matrix = new Matrix();
            matrix.setRotate(rotationAngle, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, o2.outWidth, o2.outHeight, matrix, true);

            ByteArrayOutputStream blob = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, blob);
            byte[] bitmapdata = blob.toByteArray();
            saveImage(bitmapdata, imagePath);

        } catch (Throwable ex) {
            System.out.println("<<<< capture image picture taken inside orientation 3333 error : " + ex);
        }
    }

    public static void saveImage(byte[] data, String imagePath) {
        File file = new File(imagePath);

        OutputStream os = null;
        System.out.println("<<<< capture image picture taken save 1111 : " + imagePath);
        try {
            os = new FileOutputStream(file);
            os.write(data);
            os.close();
        } catch (IOException e) {
            System.out.println("<<<< capture image picture taken save  1111 e: " + e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    // Ignore
                    System.out.println("<<<< capture image picture taken save 2222 e: " + e);
                }
            }
        }
    }

    public static boolean isNetworkAvailable(Context context) {
       /* try {
            MyLogger.println("isNetworkAvailable>>>>>1>" + BleApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE)
            );
            MyLogger.println("isNetworkAvailable>>>>>2>" + context.getSystemService(Context.CONNECTIVITY_SERVICE));
        } catch (Exception e) {
            MyLogger.println("isNetworkAvailable>>>>>>" + e.toString());
        }*/

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;

    }

    public static void showDialog(Context context) {
//        Intent circleMesz = new Intent(context, CustomDialogMeassageWait.class);
//        context.startActivity(circleMesz);
    }

    public static void hideDialog() {

//        if (CustomDialogMeassageWait.getIntance() != null)
//            CustomDialogMeassageWait.getIntance().finish();

    }

    public static String getPublish_Type(String id) {
        System.out.println("MeBaseThemeController.getDrawable_Base id--" + id);
        if (id.equalsIgnoreCase("1"))
            return "food";
        else if (id.equalsIgnoreCase("2"))
            return "Realtors";
        else if (id.equalsIgnoreCase("3"))
            return "Realtors";
        else if (id.equalsIgnoreCase("4"))
            return "Events";
        else if (id.equalsIgnoreCase("5"))
            return "Occasions";
        else if (id.equalsIgnoreCase("6"))
            return "SpaSaloon";
        else if (id.equalsIgnoreCase("7"))
            return "Fashion";
        else if (id.equalsIgnoreCase("8"))
            return "Brands";
        else if (id.equalsIgnoreCase("9"))
            return "Profession";
        else
            return "other";
    }

    public static long getMiliSeconds(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        long initialDate = cal.getTimeInMillis();
        System.out.println("<<< dummy data sleep changing time 1111 : " + initialDate);
        return initialDate;
    }

    public static String echronicsstr = "com.mevofit.fitness.fitnesstracker.walkingjogginghrbp.echotrackers";

   /* public static void showEchoprompt(Activity ctx) {
        String packagename = ctx.getPackageName();
        MyLogger.println("Slim-Hr>>>>setDeviceTypeOnUpdate==daohandlerpackage===" + packagename);
        if (packagename.equalsIgnoreCase(echronicsstr)) {
            Intent in = new Intent(ctx, TwoButtonDialog.class);
            in.putExtra("promptTypes", TwoButtonDialog.promptTypes.EchronicsPrompt);
            ctx.startActivity(in);
        }
    }

    public static int getdevelopermode(Context ctx) {
        AppPreference appPreference = AppPreference.getInstance(ctx);
        int adb = 0;
        if (appPreference.getDevelperMode() >= 20) {
            adb = 1;//Settings.Secure.getInt(ctx.getContentResolver(), Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0);
        }
        MyLogger.println("MainWristActivity>>>>developermoed0>>" + adb);
        return adb;
    }*/
    public static long getMilliFromDate(String dateFormat) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = formatter.parse(dateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Today is " + date);
        return date.getTime();
    }
    public static String getFormattedDate(String dateFormat) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = formatter.parse(dateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Today is " + date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DATE);

        if (!((day > 10) && (day < 19)))
            switch (day % 10) {
                case 1:
                    return new SimpleDateFormat("d'st' MMMM").format(date);
                case 2:
                    return new SimpleDateFormat("d'nd' MMMM").format(date);
                case 3:
                    return new SimpleDateFormat("d'rd' MMMM").format(date);
                default:
                    return new SimpleDateFormat("d'th' MMMM").format(date);
            }
        return new SimpleDateFormat("d'th' MMMM ").format(date);
    }
    public static String getFormattedDateHeader(String dateFormat) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = formatter.parse(dateFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat("dd MMM").format(date);
    }

}