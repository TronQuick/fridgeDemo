package com.giri.fridgedemo2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 * 获取限位开关的输出值，上传到服务器后台，触发拍照
 */

/**
 * API文档：
 * EM50-G 预留 4 个 GPIO，软件对应关系如下
 * J303 /sys/devices/virtual/misc/mtgpio/pin
 *
 * 调用 GPIO
 * 描述：设置 GPIO 口为输入模式
 * 范例：
 *  echo "-wmode 60 0" > /sys/devices/virtual/misc/mtgpio/pin
 *  echo "-wdir 60 0" > /sys/devices/virtual/misc/mtgpio/pin
 *
 * 描述：设置 GPIO 口为输出模式
 * 范例：
 *  echo "-wmode 60 0" > /sys/devices/virtual/misc/mtgpio/pin
 *  echo "-wdir 60 1" > /sys/devices/virtual/misc/mtgpio/pin
 *
 * 描述：设置 GPIO 口为高电平
 * 范例：
 *  echo "-wmode 60 0" > /sys/devices/virtual/misc/mtgpio/pin
 *  echo "-wdir 60 1" > /sys/devices/virtual/misc/mtgpio/pin
 *  echo "-wdout 60 1" > /sys/devices/virtual/misc/mtgpio/pin
 *
 * 描述：设置 GPIO 口为低电平
 * 范例：
 *  echo "-wmode 60 0" > /sys/devices/virtual/misc/mtgpio/pin
 *  echo "-wdir 60 1" > /sys/devices/virtual/misc/mtgpio/pin
 *  echo "-wdout 60 0" > /sys/devices/virtual/misc/mtgpio/pin
 *
 * 描述：获取 GPIO 口状态
 * 范例：
 *  cat /sys/devices/virtual/misc/mtgpio/pin | grep " 60" (3 位)
 *  60: 0 0 0 0 1 1 1 0
 */

public class IoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_io);

        // 获取限位开关的输出值,获取柜门状态（1/0）
        String gpioPath = "/sys/devices/virtual/misc/mtgpio/pin";
        String doorStatus = getGpioString(gpioPath);

        // 输出提示柜门状态
        Toast.makeText(this,"柜门状态: "+doorStatus,Toast.LENGTH_SHORT).show();

        // doorStatus柜门状态


        // 进行判断，触发拍照
        if (1 != 0) {
//            Intent intent = new Intent(this, AutoCameraActivity.class);
//            startActivity(intent);
        }

        // 返回主界面
        finish();
    }

    /**
     *  已改写方法，调用方法会 直接返回柜门状态 String "1"或 "0"
     * */
    public String getGpioString(String path) {
        String defString = "";// 默认值
        // 创建接收缓冲区
        char[] buffer = new char[2048];
        int charNum ;
        try {
            @SuppressWarnings("resource")
            BufferedReader reader = new BufferedReader(new FileReader(path));
            charNum = reader.read(buffer);

            /**
             *      经测试，buffer[990] 为 "65:00*01010" 中的 "6"，
             *      要求获取65的第三位，柜门状态设定为65的第三位，
             *      因此获取并返回 buffer[995]
             * */
            // 65~68为buffer[990]~buffer[1040]
//            for (int i = 990; i < 1040 ; i++) {
//                defString = defString + buffer[i];
//            }
            defString = buffer[995]+"";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defString;
    }

}
