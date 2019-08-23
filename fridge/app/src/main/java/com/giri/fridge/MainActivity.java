package com.giri.fridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.giri.fridge.entity.Data;
import com.giri.fridge.entity.RequestURL;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 获取并上传实时Data，并提取柜门状态
                String doorStatus = catchAndUploadData();

                // 判断柜门状态，若为打开状态（1）,触发自动拍照上传
                if (doorStatus.equals(1)) {
                    // 触发自动拍照上传Activity
//                        Intent intent = new Intent(this, AutoCameraActivity.class);
//                        startActivity(intent);
                }
            }
        }, 2000);

    }


    public String catchAndUploadData() {

        // 获取设备名称
        String deviceName = "testDevice";

        // 获取设备状态
        String status = "testStatus";

        // 获取电池电量
        String electric = "testElectric";

        // 获取位置（经纬度）
        String latitude = "testLac";
        String longitude = "testLng";

        // 获取柜门状态,默认柜门状态为关闭（0）
        String doorStatus = "0";

        //获取温度
        String wd = "testWd";

        // 输出Data的JSON字符串
        Data data = new Data();
        data.setDeviceName(deviceName);
        data.setStatus(status);
        data.setElectric(electric);
        data.setLatitude(latitude);
        data.setLongitude(longitude);
        data.setDoorStatus(doorStatus);
        data.setWd(wd);
        Gson gson = new Gson();
        String dataJSON = gson.toJson(data);
        Toast.makeText(this, dataJSON, Toast.LENGTH_LONG).show();

        // 上传
//        postString(dataJSON, RequestURL.uploadDataURL);

        return doorStatus;
    }


    /**
     * postString
     *
     * @param RequestContent 请求内容（字符串）
     * @param url            请求地址
     */
    public void postString(String RequestContent, String url) {
        // 获取okHttp实体
        OkHttpClient client = new OkHttpClient();

        // RequestBody中的MediaType指定为纯文本，编码方式是utf-8
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;charset=utf-8"),
                RequestContent);

        // 发送请求
        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        // 处理响应
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(MainActivity.this, "POST request fail", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseStr = response.body().string();
                Toast.makeText(MainActivity.this, "responseCode：" + response.code(), Toast.LENGTH_SHORT).show();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "responseBody:" + responseStr, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
