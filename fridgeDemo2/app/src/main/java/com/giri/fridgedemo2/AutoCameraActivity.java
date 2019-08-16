package com.giri.fridgedemo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import com.giri.fridgedemo2.Utils.PhotoActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

        Toast.makeText(this,"开始拍摄",Toast.LENGTH_SHORT).show();
        startActivityForResult(new Intent(this, PhotoActivity.class), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Toast.makeText(this,"拍摄已完成",Toast.LENGTH_SHORT).show();

        /** 自动拍摄返回的Bitmap Image */
        mIVData.setImageBitmap(Config.Image);

        // 提示开始上传
        Toast.makeText(this, "正在上传图片...", Toast.LENGTH_LONG).show();

        // 引用时间戳命名，进行备份储存，返回路径
        String imgBackupPath = saveImage(imgFileName, Config.Image);

        // 传入图片路径，进行上传
        File imgFile = new File(imgBackupPath);
//                String imgUploadResult = UploadFileUtil.uploadFile(imgFile, "RequestURL");

        // 通过imgUploadResult判断上传结果，200为上传成功,弹出提示
        if (1 != 0) {
            Toast.makeText(this, "图片上传成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "图片上传失败", Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(AutoCameraActivity.this, "即将返回主界面", Toast.LENGTH_LONG).show();

        /* 延时器，13秒后关闭activity */
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                finish();
            }
        }, 11000);
    }


    /**
     * 本地储存方法
     * 路径：/储存根目录/fridge/
     *
     * @param name 自定义储存文件名
     * @param bmp  bitmap
     * @return 文件完整路径
     */
    public String saveImage(String name, Bitmap bmp) {

        File appDir = new File(Environment.getExternalStorageDirectory(), "CameraBackUp");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = name;
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
