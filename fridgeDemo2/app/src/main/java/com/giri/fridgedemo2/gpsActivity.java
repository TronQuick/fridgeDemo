package com.giri.fridgedemo2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import android.widget.Toast;

import com.giri.fridgedemo2.utils.LocationUtils;

/**
 * 获取当前位置（GPS、4G），上传到服务器后台
 */

public class gpsActivity extends AppCompatActivity {

    private boolean flag;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        // 获取 GPS 定位
        String gpsLocation[] = getGPSLocation();
        System.out.println(gpsLocation);

        // 获取 NetWork 定位
        String[] networkLocation = getNetworkLocation();
        System.out.println(networkLocation);

        // 上传定位信息
//        Toast.makeText(this, "准备上传定位信息...", Toast.LENGTH_SHORT).show();

        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initPermission();// 针对6.0以上版本做权限适配
    }

    /**
     * 权限获取
     */
    private void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查权限
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // 请求权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                flag = true;
            }
        } else {
            flag = true;
        }
    }

    /**
     * 权限的结果回调函数
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            flag = grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED;
        }
    }


    /**
     * 通过GPS获取定位信息
     *
     * @return String[] GPS定位信息
     *         String[0] = GPS定位描述字符串
     *         String[1] = GPS lat定位
     *         String[2] = GPS lng定位
     */
    public String[] getGPSLocation() {
        Location gps = LocationUtils.getGPSLocation(this);

        final String[] gpsLocation = new String[3];
        gpsLocation[0] = "未获取到GPS定位信息";
        gpsLocation[1] = "未获取到GPS.lat";
        gpsLocation[2] = "未获取到GPS.lng";

        if (gps == null) {
            // 设置定位监听，因为GPS定位，第一次进来可能获取不到，通过设置监听，可以在有效的时间范围内获取定位信息
            LocationUtils.addLocationListener(context, LocationManager.GPS_PROVIDER, new LocationUtils.ILocationListener() {

                @Override
                public void onSuccessLocation(Location location) {
                    if (location != null) {
                        gpsLocation[0] = "GPS onSuccessLocation 定位信息:  lat==" + location.getLatitude() + "     lng==" + location.getLongitude();
                        gpsLocation[1] = location.getLatitude()+"";
                        gpsLocation[2] = location.getLongitude()+"";
                        Toast.makeText(gpsActivity.this, gpsLocation[0], Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(gpsActivity.this, gpsLocation[0], Toast.LENGTH_LONG).show();
                    }
                }
            });

        } else {
            gpsLocation[0] = "GPS定位信息: lat==" + gps.getLatitude() + "  lng==" + gps.getLongitude();
            gpsLocation[1] = gps.getLatitude()+"";
            gpsLocation[2] = gps.getLongitude()+"";
            Toast.makeText(this, gpsLocation[0], Toast.LENGTH_SHORT).show();
        }
        return gpsLocation;
    }

    /**
     * 通过网络获取定位信息
     * @return String[] 网络定位信息
     *         String[0] = 网络定位描述字符串
     *         String[1] = 网络定位 lat定位
     *         String[2] = 网络定位 lng定位
     */
    private String[] getNetworkLocation() {
        // 获取网络定位
        Location net = LocationUtils.getNetWorkLocation(this);
        String[] networkLocation = new String[3];

        networkLocation[0] = "未获取到网络定位信息";
        networkLocation[1] = "未获取到 networkLocation.lat";
        networkLocation[2] = "未获取到 networkLocation.lng";

        // 判断获取结果
        if (net == null) {
            // 获取失败，弹出提示
            Toast.makeText(this, networkLocation[0], Toast.LENGTH_LONG).show();
        } else {
            // 获取成功，弹出结果
            networkLocation[0] = "网络定位信息: lat==" + net.getLatitude() + "  lng==" + net.getLongitude();
            networkLocation[1] = net.getLatitude()+"";
            networkLocation[2] = net.getLongitude()+"";
            Toast.makeText(this, networkLocation[0], Toast.LENGTH_LONG).show();
        }
        return networkLocation;
    }

    /**
     * 定位上传方法
     */
    public boolean uploadLocation(String gpsLocation, String networkLocation) {
        // 上传
//        Toast.makeText(this, "正在上传定位信息...", Toast.LENGTH_SHORT).show();

        // 判断上传是否完成
        if (1 != 0) {
//            Toast.makeText(this, "定位信息上传成功", Toast.LENGTH_SHORT).show();
            return true;
        } else return false;
    }
}