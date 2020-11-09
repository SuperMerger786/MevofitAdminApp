package com.app.newuidashboardadmin.font;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class LatoMediumTextView extends TextView {
    public LatoMediumTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public LatoMediumTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LatoMediumTextView(Context context) {
        super(context);
        init();
    }

    public void init() {
        /*new Runnable() {
            @Override
            public void run() {*/
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/Lato-medium.ttf");
        setTypeface(tf, Typeface.NORMAL);
//            }
//        };


    }
}
