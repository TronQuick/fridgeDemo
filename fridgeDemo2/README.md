# README

## **项目说明**

这个demo为测试项目各需求功能的demo。



## **需求功能**

- 温度采集上传
  - 编写Android函数采集冰柜温度
- IO采集上传
  - 获取限位开关的输出值，上传到服务器后台，触发拍照
- 定位采集上传
  - 获取当前位置（GPS、4G），上传到服务器后台
- 拍照上传
  - 获取当前位置（GPS、4G），上传到服务器后台



## **进度**

### **温度采集上传**

基本完成函数编写

**测试结果：**主板可以接收到数据，但是无法发出数据，原因疑似为主板系统权限不足，等待主板供应方解决。



### **IO采集上传**

基本完成函数编写，通过获得的IO数值，触发开启自动拍照Activity

**测试结果：**功能正常运行



### 定位采集上传

GPS在室内定位准确度不高，因此此功能主要为获取4G定位。

**遇到的问题：**

一开始使用Android标准API中的网络定位服务进行开发，但测试时发现在无wifi情况下，或不在外部打开浏览器的情况下，app无法获取网络位置。推测原因是google服务被阉割或者在国内无法连接导致无法使用原生网络定位，网上也有类似推测。

**解决方案：**

1.使用基站定位，但是获得基站数据后，需要借用外部API来查询，才能通过基站数据来获取经纬度，外部API存在收费以及稳定性的问题；

2.使用高德地图开发的 Android定位sdk，需要收费。



### 拍照上传

完成基本功能编写，实现了无交互式自动对焦拍照，保存备份图片到本地，将JPG转换为base64，上传到后台。









