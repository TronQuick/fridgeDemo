package com.giri.fridge;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.giri.fridge.entity.ImageData;
import com.giri.fridge.utils.Config;
import com.giri.fridge.utils.ImageUtils;
import com.giri.fridge.utils.PhotoActivity;
import com.giri.fridge.utils.PostStringUtil;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自动拍摄并上传的Activity
 */

public class AutoCameraActivity extends AppCompatActivity {
    private ImageView mIVData;

    // 生成时间戳
    public static String generateTimeStamp() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return timeStamp;
    }

    // 使用时间戳命名图片
    String imgFileName = generateTimeStamp() + ".jpg";

    // 定义储存路径
    String imgRootPath = Environment.getExternalStorageDirectory().getPath() + "/" + imgFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_camera);
        mIVData = (ImageView) findViewById(R.id.iv_data);
        startActivityForResult(new Intent(this, PhotoActivity.class), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /** 自动拍摄，返回Bitmap Image */
        mIVData.setImageBitmap(Config.Image);

        // 引用时间戳命名，进行备份储存，返回路径
        String imgBackupPath = ImageUtils.saveImage(imgFileName, Config.Image);
        System.out.println("备份图片路径:"+imgBackupPath);

        // 将Bitmap转换为base64，返回String
        String photoData = ImageUtils.bitmapToBase64(Config.Image);

        // 获取设备名称
//        BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
        String deviceName = "testDevice";

        // 封装对象,通过GSON工具类转换为JSON字符串
        ImageData imageData = new ImageData();
        imageData.setDeviceName(deviceName);
        imageData.setPhotoData(photoData);
        Gson gson = new Gson();
        String uploadImageJSON = gson.toJson(imageData);

        // 上传uploadImageJSON,调用封装好的okHttp方法
        String uploadImageURL = getResources().getString(R.string.uploadDataURL);
        PostStringUtil.postString(uploadImageJSON, uploadImageURL);

        /** 延时器，0.5秒后关闭AutoCameraActivity */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 500);
//        finish();
    }
}
