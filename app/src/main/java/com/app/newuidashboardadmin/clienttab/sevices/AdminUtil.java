package com.app.newuidashboardadmin.clienttab.sevices;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class AdminUtil {
    public static boolean isValid(String imageView) {
        return (imageView != null && !imageView.trim().equalsIgnoreCase("NA") &&
                !imageView.trim().equalsIgnoreCase("") && imageView.length() > 4);
    }


    public static boolean isValidEmail(String value) {
        return isValid(value) && value.contains("@");
    }
    public static void makeImageRequest(final LinearLayout defaultimg, final ImageView imageView, final String imageUrl, final Context context) {

        if (imageView != null) {
            defaultimg.setVisibility(View.VISIBLE);


            if (isValid(imageUrl)) {

                Glide.with(context).load(imageUrl).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                        Glide.with(context).load(imageUrl).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                defaultimg.setVisibility(View.VISIBLE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                defaultimg.setVisibility(View.INVISIBLE);
                                return false;
                            }
                        }).into(imageView);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        defaultimg.setVisibility(View.INVISIBLE);
                        return false;
                    }
                }).into(imageView);
            }


        }
    }
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
