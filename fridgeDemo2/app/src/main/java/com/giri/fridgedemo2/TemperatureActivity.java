package com.giri.fridgedemo2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import droi.sdk.api.SerialPort;

/***
 *      API介绍
 *     RS485: J905 "/dev/ttyMT2"
 *
 *  函数：public SerialPort(new File(string path), int baudrate, int flag)
 *  描述：获取串口 path---串口节点 baudrate---波特率 flag---校验位
 *  范例： SerialPort mSerialPort = new SerialPort(new File("/dev/ttyMT2" ), 921600, 0);
 *
 *
 *  函数：public InputStrem getInputStream()
 *  描述：串口读数据
 *  范例： InputStream mInputStream = mSerialPort. getInputStream();
 *  mInputStream.read(byte);
 *
 *  函数：public InputStrem getOutputStream()
 *  描述：串口写数据
 *  范例： OutputStrem mOutputStrem = mSerialPort. getOutputStream(); OutputStream.write(byte)；
 *
 **/


/**
 * 编写Android函数采集冰柜温度，采集后上传
 */
public class TemperatureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        // 获取温度数据
        Double temperature = getTemperature();

        // 上传
        if (temperature != null) {
            Toast.makeText(this, "采集到的温度为：" + temperature, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "正在上传温度数据..." , Toast.LENGTH_LONG).show();

            // 上传方法...


        } else {
            Toast.makeText(this, "温度采集失败", Toast.LENGTH_SHORT).show();

        }


        finish();
    }

    /**
     * 获取串口、读取串口数据、获取温度
     * @return Double 温度
     *         0x61
     *          0x3A
     */
    public Double getTemperature() {

        double temperature = 999;

        try {
            // cmd
            byte[] cmd = new byte[]{0x01, 0x04, 0x04, 0x00, 0x00, 0x01, 0x61, 0x3A};

            // 创建接收缓冲区
            byte[] buffer = new byte[128];

            // 获取串口 path---串口节点 baudrate---波特率 flags---校验位
            SerialPort mSerialPort = new SerialPort(new File("/dev/ttyMT2"), 9600, 0);

            // 串口写数据
            OutputStream mOutputStream = mSerialPort.getOutputStream();
            mOutputStream.write(cmd);

            // 串口读数据
            InputStream mInputStream = mSerialPort.getInputStream();

            // 接收至缓冲区,获取读取的字节数
            int dataLength = mInputStream.read(buffer);

            // 提取将缓冲区中的数据
            byte[] receiveByte = new byte[dataLength];
            for (int i = 0; i < dataLength; i++) {
                receiveByte[i] = buffer[i];
            }

            // 校验接收数据
            if (receiveByte[1] != cmd[1]) {
                System.out.println("采集异常");
                Toast.makeText(this, "采集异常", Toast.LENGTH_SHORT).show();
            }

            int receiveDataLength = receiveByte[2];
            int realLength = receiveDataLength / 2;


            for (int i = 0; i < realLength; i++) {

                // C#:double ecL = (double.Parse(receiveByte[3].ToString())) * 256 + double.Parse(receiveByte[4].ToString("F2"));
                double ecL = (Double.parseDouble(receiveByte[3]+"")) * 256 + Double.parseDouble(receiveByte[4]+"");
                temperature = ecL * 0.1;
                String result = "第1触头：" + "returns_Value：" + ecL + "\n" + "第1触头：" + "temperature：" + temperature;
                System.out.println(result);
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return temperature;
    }


}

//    C#:
//    public void Get_temperature()
//    {
//        int datalength=new int();
//        s.Send(cmd);
//
//        //Console.WriteLine("send_Temperature CMD：" + BitConverter.ToString(cmd));
//        datalength= s.Receive(receivedata);
//
//        byte[] receivebyte = new byte[datalength];
//        for (int i = 0; i < datalength; i++)
//        {
//            receivebyte[i] = receivedata[i];
//        }
//        //Console.WriteLine("receive_Temperature CMD：" + BitConverter.ToString(receivebyte));
//        if (receivebyte[1] != cmd[1])
//        {
//            Console.WriteLine("采集异常");
//        }
//        int receiveDataLength = receivebyte[2];
//        int realLength= receiveDataLength / 2;
//        for (int i = 0; i < realLength; i++)
//        {
//            double ecL = (double.Parse(receivebyte[3].ToString())) * 256 + double.Parse(receivebyte[4].ToString("F2"));
//            double tp = ecL * 0.1;
//            Console.WriteLine("第" + (addr + 1) + "触头" + "returns_Value：" + "  " + ecL);
//            Console.WriteLine("第" + (addr + 1) + "触头" + "temperature：" + "  " + tp);
//        }
//        receivebyte = null;
//    }

