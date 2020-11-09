package com.app.newuidashboardadmin.font;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * Created by yoganshu on 22/11/16.
 */
@SuppressLint("AppCompatCustomView")
public class FiraSansLightTextView extends TextView {
    public FiraSansLightTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public FiraSansLightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FiraSansLightTextView(Context context) {
        super(context);
        init();
    }


    public void init() {
      /*  new Runnable() {
            @Override
            public void run() {*/
                Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "font/Poppins-Light.ttf");
                setTypeface(tf, Typeface.NORMAL);

//            }
//        };

    }
}
