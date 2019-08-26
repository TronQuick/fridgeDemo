package com.giri.fridge;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.giri.fridge.entity.Data;
import com.giri.fridge.utils.PostStringUtil;
import com.google.gson.Gson;

import java.util.Date;

public class HorizonService extends Service {

    /** 设置定时任务时间间隔 */
    int intervalTime = 5000; // 这是5s

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.d("TAG", "打印时间: " + new Date().
                        toString());

                // 获取并上传实时Data，并提取柜门状态
                String doorStatus = catchAndUploadData();

                // 判断柜门状态，若为打开状态（1）,触发自动拍照上传
                if (doorStatus.equals("1")) {
                    // 触发自动拍照上传Activity
                    Intent dialogIntent = new Intent(getBaseContext(), AutoCameraActivity.class);
                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplication().startActivity(dialogIntent);
                }

            }

        }).start();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long triggerAtTime = SystemClock.elapsedRealtime() + intervalTime;
        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * 获取并上传数据
     * @return 柜门状态:String doorStatus="1" / doorStatus="0"
     * */
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

        // 获取柜门状态,判断拍照是否执行
        String doorStatus = "0"; // 默认柜门状态为关闭（0）


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
        Log.d("DATA", dataJSON);

        // 上传DataJSON,调用封装好的okHttp方法
        String uploadDataURL = getResources().getString(R.string.uploadDataURL);
//        PostStringUtil.postString(dataJSON, uploadDataURL);

        // 返回柜门状态
        return doorStatus;
    }

}