package com.giri.fridgedemo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

/**
 *      获取限位开关的输出值，上传到服务器后台，触发拍照
 * */

public class IoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_io);

        // 获取限位开关的输出值


        // 上传限位开关的输出值


        // 进行判断，触发拍照
        if (1 != 0) {
            Intent intent = new Intent(this, AutoCameraActivity.class);
            startActivity(intent);
        }

        // 返回主界面
        finish();
    }

}
