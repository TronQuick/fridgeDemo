package com.giri.fridgedemo2;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.giri.fridgedemo2.entity.RequestURL;
import com.giri.fridgedemo2.entity.ImageData;
import com.giri.fridgedemo2.utils.ImageUtils;
import com.giri.fridgedemo2.utils.PhotoActivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 自动拍摄功能的  主Activity
 */

public class AutoCameraActivity extends AppCompatActivity {

    private ImageView mIVData;

    // 生成时间戳
    public static String generateTimeStamp() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return timeStamp;
    }

    // ++
    // 使用时间戳命名图片
    String imgFileName = generateTimeStamp() + ".jpg";
    // ++
    // 定义储存路径
    String imgRootPath = Environment.getExternalStorageDirectory().getPath() + "/" + imgFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_camera);

        mIVData = (ImageView) findViewById(R.id.iv_data);

        Toast.makeText(this, "开始拍摄", Toast.LENGTH_SHORT).show();
        startActivityForResult(new Intent(this, PhotoActivity.class), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Toast.makeText(this, "拍摄已完成", Toast.LENGTH_SHORT).show();

        /** 自动拍摄，返回Bitmap Image */
        mIVData.setImageBitmap(Config.Image);

        // 提示开始上传
        Toast.makeText(this, "正在上传图片...", Toast.LENGTH_LONG).show();

        // 引用时间戳命名，进行备份储存，返回路径
        String imgBackupPath = ImageUtils.saveImage(imgFileName, Config.Image);
        System.out.println("备份图片路径:"+imgBackupPath);

        // 将Bitmap转换为base64，返回String
        String photoData = ImageUtils.bitmapToBase64(Config.Image);

        // 获取设备名称
//        BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
        String deviceName ="testDevice";

        // 封装对象,通过GSON工具类转换为JSON字符串
        ImageData imageData = new ImageData();
        imageData.setDeviceName(deviceName);
        imageData.setPhotoData(photoData);
        Gson gson = new Gson();
        String uploadImageJSON = gson.toJson(imageData);

        // 请求上传
//        postString(uploadImageJSON, RequestURL.uploadImageURL);

        Toast.makeText(AutoCameraActivity.this, "即将返回主界面", Toast.LENGTH_LONG).show();

        /* 延时器，13秒后关闭AutoCameraActivity */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 11000);
    }


    /**
     *  基于 OkHttp 的 postString 方法
     * */
    public void postString(String RequestContent,String url) {
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
                Toast.makeText(AutoCameraActivity.this, "POST request fail",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseStr = response.body().string();
                Toast.makeText(AutoCameraActivity.this, "responseCode：" + response.code(),Toast.LENGTH_SHORT).show();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AutoCameraActivity.this, "responseBody:" + responseStr,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
