package com.app.newuidashboardadmin.font;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class FiraSemiBoldTextView extends TextView {


    public FiraSemiBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public FiraSemiBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FiraSemiBoldTextView(Context context) {
        super(context);
        init();
    }

    public void init() {
        /*new Runnable() {
            @Override
            public void run() {*/
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/Poppins-SemiBold.ttf");
        setTypeface(tf, Typeface.NORMAL);
//            }
//        };


    }
}
