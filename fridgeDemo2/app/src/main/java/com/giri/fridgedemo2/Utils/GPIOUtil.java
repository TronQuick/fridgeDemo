package com.giri.fridgedemo2.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class GPIOUtil {

    //读GPIO path="/sys/devices/virtual/misc/mtgpio/pin"
    public String getGpioString(String path) {
        String defString = "0";// 默认值
        try {
            @SuppressWarnings("resource")
            BufferedReader reader = new BufferedReader(new FileReader(path));
            defString = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defString;
    }

    //将GPIO口设置为输出的时候,默认是输出，调用下面的方法即可变成默认输入
    public void gpio_input() {
        RootCommand("echo \"-wmode 60 0\" > /sys/devices/virtual/misc/mtgpio/pin");
        RootCommand("echo \"-wdir 60 0\" > /sys/devices/virtual/misc/mtgpio/pin");
    }

    // 设置GPIO口为输出模式
    public void gpio_output() {
        RootCommand("echo \"-wmode 60 0\" > /sys/devices/virtual/misc/mtgpio/pin");
        RootCommand("echo \"-wdir 60 1\" > /sys/devices/virtual/misc/mtgpio/pin");
    }

    // 设置GPIO口为高电平
    public void set_gpio_high() {
        RootCommand("echo \"-wmode 60 0\" > /sys/devices/virtual/misc/mtgpio/pin");
        RootCommand("echo \"-wdir 60 1\" > /sys/devices/virtual/misc/mtgpio/pin");
        RootCommand("echo \"-wdout 60 1\" > /sys/devices/virtual/misc/mtgpio/pin");
    }

    // 设置GPIO口为低电平
    public void set_gpio_low() {
        RootCommand("echo \"-wmode 60 0\" > /sys/devices/virtual/misc/mtgpio/pin");
        RootCommand("echo \"-wdir 60 1\" > /sys/devices/virtual/misc/mtgpio/pin");
        RootCommand("echo \"-wdout 60 0\" > /sys/devices/virtual/misc/mtgpio/pin");
    }

    //当GPIO口为输出的时候，通过以下的办法来控制高低电平
//    public boolean set_gpio0_high(View v) {   //拉高
//        boolean FLAG = RootCommand("echo  1 > /sys/class/backlight/rk28_bl/gpio1");
//        Log.e("123high", String.valueOf(FLAG));
//        return FLAG;
//    }

//    public boolean set_gpio0_low(View v) {    //拉低
//        boolean FLAG = RootCommand("echo 0 > /sys/class/backlight/rk28_bl/gpio1");
//        Log.e("123low", String.valueOf(FLAG));
////        read_gpio0();
//        return FLAG;
//    }

    //  其他的GPIO口都是一样的方法（gpio0、gpio1、gpio2、gpio3）
    //下面的是执行的方法
    private boolean RootCommand(String command) {
        Process process = null;
        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(command + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
            }
        }
        return true;
    }
}
