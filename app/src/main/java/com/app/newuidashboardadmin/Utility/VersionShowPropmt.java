package com.app.newuidashboardadmin.Utility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.app.newuidashboardadmin.R;

public class VersionShowPropmt {

    public void showDialog(Context context){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.version_dialouge);
        TextView text = (TextView) dialog.findViewById(R.id.vesrion);

//        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String version = pInfo.versionName;
            System.out.println("VersionShowPropmt.showDialog version "+ version);
            text.setText("Current version is "+ version);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }

        Button dialogButton = (Button) dialog.findViewById(R.id.close);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

}
