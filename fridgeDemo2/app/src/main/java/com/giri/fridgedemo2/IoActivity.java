package com.giri.fridgedemo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.giri.fridgedemo2.utils.GPIOUtil;


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

        // 获取限位开关的输出值
        GPIOUtil gpioUtil = new GPIOUtil();
//
//        // 设置GPIO口为输入模式
//        gpioUtil.gpio_input();
//
//        // 设置GPIO口为输出模式
//        gpioUtil.gpio_output();
//
//        // 设置GPIO口为高电平
//        gpioUtil.set_gpio_high();
//
//        // 设置GPIO口为低电平
//        gpioUtil.set_gpio_low();

        // 读取GPIO
        String gpioPath = "/sys/devices/virtual/misc/mtgpio/pin";
        String gpioString = gpioUtil.getGpioString(gpioPath);

        // 要求获取65的第三位：1 / 0
        System.out.println(gpioString);
        Toast.makeText(this,gpioString,Toast.LENGTH_LONG).show();

        // doorStatus柜门状态



        // 进行判断，触发拍照
        if (1 != 0) {
            Intent intent = new Intent(this, AutoCameraActivity.class);
            startActivity(intent);
        }

        // 返回主界面
        finish();
    }

}
