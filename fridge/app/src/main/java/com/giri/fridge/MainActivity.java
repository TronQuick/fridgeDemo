package com.giri.fridge;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 开始服务
        Intent intent = new Intent(this, HorizonService.class);
        startService(intent);

        // 测试拍摄功能
//        Intent intent = new Intent(this,AutoCameraActivity.class);
//        startActivity(intent);
    }
}
