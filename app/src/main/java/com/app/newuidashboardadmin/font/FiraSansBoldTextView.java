package com.app.newuidashboardadmin.font;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by yoganshu on 7/7/16.
 */
@SuppressLint("AppCompatCustomView")
public class FiraSansBoldTextView extends TextView {


    public FiraSansBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public FiraSansBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FiraSansBoldTextView(Context context) {
        super(context);
        init();
    }

    public void init() {
    /*  new Runnable() {
            @Override
            public void run() {*/
                Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/Poppins-Bold.ttf");
                setTypeface(tf, Typeface.NORMAL);
//            }
//        };


    }
}
