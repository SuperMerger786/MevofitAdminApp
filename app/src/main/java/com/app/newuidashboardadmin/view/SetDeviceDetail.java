package com.app.newuidashboardadmin.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.newuidashboardadmin.R;
import com.app.newuidashboardadmin.Utility.AppPrefernce;


public class SetDeviceDetail extends AppCompatActivity {
    AppPrefernce appPreference;
    TextView device_id, device_id_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setdevice_detail);
        appPreference = new AppPrefernce(this);
        device_id = (TextView) findViewById(R.id.device_id);
        device_id_detail = (TextView) findViewById(R.id.device_id_detail);
        device_id.setText(appPreference.getDeviceID());
        device_id_detail.setText(appPreference.getDeviceIDSuccessDetail());
    }
}
