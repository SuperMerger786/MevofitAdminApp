package com.app.newuidashboardadmin.font;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class LatoHeavyTextView extends TextView {
    public LatoHeavyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public LatoHeavyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LatoHeavyTextView(Context context) {
        super(context);
        init();
    }

    public void init() {
        /*new Runnable() {
            @Override
            public void run() {*/
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/Lato-heavy.ttf");
        setTypeface(tf, Typeface.NORMAL);
//            }
//        };


    }
}
