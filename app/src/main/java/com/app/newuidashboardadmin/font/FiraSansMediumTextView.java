package com.app.newuidashboardadmin.font;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by yoganshu on 20/6/16.
 */
@SuppressLint("AppCompatCustomView")
public class FiraSansMediumTextView extends TextView {


    public FiraSansMediumTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public FiraSansMediumTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FiraSansMediumTextView(Context context) {
        super(context);
        init();
    }

    public void init() {
        /*new Runnable() {
            @Override
            public void run() {*/
                Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/Poppins-Medium.ttf");
                setTypeface(tf, Typeface.NORMAL);
//            }
//        };


    }
}
