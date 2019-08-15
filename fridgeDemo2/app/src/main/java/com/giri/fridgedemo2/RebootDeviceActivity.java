package com.giri.fridgedemo2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import droi.sdk.api.Droitech;

public class RebootDeviceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reboot);

        reboot();
    }


    /**
     *      重启设备
     * */
    public void reboot() {
        Droitech droitech = new Droitech(this);
        droitech.reboot();
    }

}
