package com.example.administrator.zzzz;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.kongqw.permissionslibrary.PermissionsManager;
import com.kongqw.wifilibrary.WiFiManager;
import com.kongqw.wifilibrary.listener.OnWifiConnectListener;
import com.kongqw.wifilibrary.listener.OnWifiEnabledListener;
import com.kongqw.wifilibrary.listener.OnWifiScanResultsListener;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity implements OnWifiEnabledListener, OnWifiScanResultsListener, OnWifiConnectListener {

    private String packageName, className;
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private final int GET_WIFI_LIST_REQUEST_CODE = 0;
    private WiFiManager mWiFiManager;
    private PermissionsManager mPermissionsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendHexSerialPort(AryChangeManager.stringToHex("0x550x040x84"));

        Log.e("logzz", "启动Main");


     String j =   Integer.toHexString(16);

        Log.e("uu", "启动Main"+j);

        String[] d = {"57" ,"4B" ,"4C", "59" ,"21" ,"00" ,"82" ,"01" ,"30","30", "30", "30", "30", "30" ,"30", "30" ,"30" ,"30" ,"30", "30" ,"30" ,"30" ,"30" ,"30" ,"30" ,"30", "30", "30", "30", "30", "30" ,"30"};

String ab = fun(d);

        Log.e("uu", "启动Main"+ab);

        RxTimerUtil.startTime(3000, new RxTimerUtil.IRxLastTime() {
            @Override
            public void doLast(String time) {

            }

            @Override
            public void doComplete() {

            }
        });


        int q = 1;
        double u = 0.5;

        Float b = Float.valueOf(q);

        Log.e("logzz", String.valueOf(u) + "---" + String.valueOf(b));


        View decorView = getWindow().getDecorView();
// Hide both the navigation bar and the status bar.  
// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as  
// a general rule, you should design your app to hide the status bar whenever you  
// hide the navigation bar.  
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);


        NavigationBarStatusBar(this, true);


        String a = getCPUSerial();


        findViewById(R.id.tv_intent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,FirstActivity.class);
//                startActivity(intent);
                mWiFiManager.startScan();
            }
        });


        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.RunningTaskInfo info = manager.getRunningTasks(1).get(0);
        final String shortClassName = info.topActivity.getShortClassName();    //类名
        className = info.topActivity.getClassName();              //完整类名
        packageName = info.topActivity.getPackageName();

        Log.e("logzz", shortClassName + "---" + className + "----" + packageName);


        Log.e("logzz", "" + strTo16("WLKY").toUpperCase());
//判断sd卡是否存在
        File sdDir;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);

        try {
            int versionCode = this.getPackageManager().
                    getPackageInfo(this.getPackageName(), 0).versionCode;
            Log.e("升级版本", "测试版本号---" + versionCode);
            if (versionCode == 1) {
                Log.e("升级版本", "测试版本号" + versionCode);
                File file = new File(Environment.getExternalStorageDirectory() + "/Download/cu.apk");
                if (file.exists()) {
                    Log.e("logzz", "外部存储可用..." + file.toString());
                    new Thread(new Runnable() {

                        public void run() {

                            try {
                                Thread.sleep(4000);

//                                InstallUtils.installSlient(Environment.getExternalStorageDirectory() + "/Download/cu.apk",packageName,shortClassName);

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }


                        }

                    }).start();
                }
            }
        } catch (Exception e) {

        }


        get();
//        int a = 0x84;
//        byte b = (byte)a;
//
//        byte[] bytes = {0x55,0x04,b,0x11};
//        byte c =     getXor(bytes);
//        byte[]      z = {0x55,0x04,b,0x11,c};


        // WIFI管理器
        mWiFiManager = WiFiManager.getInstance(getApplicationContext());
        // 动态权限管理器
        mPermissionsManager = new PermissionsManager(this) {
            @Override
            public void authorized(int requestCode) {
                // 6.0 以上系统授权通过
                if (GET_WIFI_LIST_REQUEST_CODE == requestCode) {
                    // 获取WIFI列表
                    List<ScanResult> scanResults = mWiFiManager.getScanResults();
                    Log.e("logxx", "" + scanResults.size());
                }
            }

            @Override
            public void noAuthorization(int requestCode, String[] lacksPermissions) {
                // 6.0 以上系统授权失败
            }

            @Override
            public void ignore() {
                // 6.0 以下系统 获取WIFI列表
                List<ScanResult> scanResults = mWiFiManager.getScanResults();

            }
        };
        // 请求WIFI列表
        mPermissionsManager.checkPermissions(GET_WIFI_LIST_REQUEST_CODE, PERMISSIONS);

        getListOfConnectedDevice();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 添加监听
        mWiFiManager.setOnWifiEnabledListener(this);
        mWiFiManager.setOnWifiScanResultsListener(this);
        mWiFiManager.setOnWifiConnectListener(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("logzz", "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("logzz", "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("logzz", "onRestart");
    }


    public static byte getXor(byte[] datas) {

        byte temp = datas[0];

        for (int i = 1; i < datas.length; i++) {
            temp ^= datas[i];
        }

        return temp;
    }

    public static String getCPUSerial() {
        String cpuAddress = "";
        try {
            //读取CPU信息
            Process pp = Runtime.getRuntime().exec("cat /proc/cpuinfo");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            //查找CPU序列号
            for (int i = 1; i < 100; i++) {
                String str = input.readLine();
                if (str != null) {
                    //查找到序列号所在行
                    if (str.indexOf("Serial") > -1) {
                        //提取序列号
                        String strCPU = str.substring(str.indexOf(":") + 1,
                                str.length());
                        //去空格
                        cpuAddress = strCPU.trim();
                        break;
                    }
                } else {
                    //文件结尾
                    break;
                }
            }
        } catch (Exception ex) {
            //赋予默认值
            ex.printStackTrace();
        }
        return cpuAddress;
    }


    public void get() {
        String order = "";
        //String orderId = "RE5ca46d17b4bc0859447240";
        String orderId = "000000000000000000000000"; //订单号
        String sevno = "00";//主板号
        String a = "WLKY"; // 命令前缀
        String b = strTo16(a).toUpperCase(); //字符串转16进制
        order = order + b;
        String h = Integer.toHexString(33);// 约定串转16进制
        order = order + h;
        order = order + sevno;
        order = order + "82"; // 开锁指令
        order = order + "01";   //开锁位置
        order = order + strTo16(orderId).toUpperCase();// 订单号转16进制大写
        order = order.trim();
        String[] zz = toByte(order);// 字符串转为两个一组的数组
        String[] yy = new String[zz.length + 1];
        for (int i = 0; i < zz.length; i++) {
            yy[i] = zz[i];
        }
        yy[yy.length - 1] = fun(zz);//最后结尾把字符串转为10进制数字进行异或 异或后结果由十进制数字转为16进制大写加入数组

        String id = "";
        for (int i = 0; i < yy.length; i++) {
            if (TextUtils.isEmpty(id)) {
                id = yy[i];
            } else {
                id = id + yy[i];
            }

        }
        byte[] bytes = sendHexSerialPort(AryChangeManager.stringToHex(id));

        byte[] mm = id.getBytes();

        Log.e("logzz", bytes.length + "---" + mm.length);
    }

    public static String strTo16(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }


    public String[] toByte(String order) {
        String[] u = order.split("");
        String[] ww = new String[u.length - 1];
        for (int i = 0; i < u.length; i++) {
            if (!TextUtils.isEmpty(u[i])) {
                ww[i - 1] = u[i];
            }
        }
        String[] zz = new String[ww.length / 2];
        int count = 0;
        for (int i = 0; i < ww.length; i++) {
            if (i % 2 == 0 || i == 0) {
                if (i + 1 < ww.length) {
                    zz[count] = ww[i] + ww[i + 1];
                    count++;
                }


            }
        }
        return zz;
    }

    public String fun(String[] a) {

        int temp = 0;
        for (int i = 0; i < a.length; i++) {
            temp ^= Integer.parseInt(a[i], 16);
        }
        Log.e("logzz", "异或" + temp);
        return Integer.toHexString(temp).toUpperCase() + "";
    }


    public static byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }

    /**
     * 将用户输入的字符串转成16进制数
     *
     * @param data 要进行转化的字符串
     * @return 转换完成后的16进制数
     */
    public static int[] stringToHex(String data) {
        data = data.toLowerCase();
        data = data.replace(" ", "");
        Log.i("TAG", "data=" + data);
        Log.i("TAG", "data.length=" + data.length());
        int[] buf;
        if (data.length() % 2 == 0) {
            buf = new int[data.length() / 2];
        } else {
            buf = new int[data.length() / 2 + 1];
        }
        for (int i = 0, j = 0; i < data.length(); i += 2, j++) {
            char c1 = data.charAt(i);
            char c2;
            if (i + 1 == data.length()) {
                c2 = 0;
            } else {
                c2 = data.charAt(i + 1);
            }
            int n1 = charToHex(c1);
            int n2 = charToHex(c2);

            buf[j] = 16 * n1 + n2;
            Log.i("TAG", "c1=" + c1 + " c2=" + c2);
        }
        for (int k : buf) {
            Log.i("TAG", "k=" + k);
        }
        return buf;
    }

    /**
     * 将char类型的数转换成16进制的数
     *
     * @param c char数
     * @return 对应的16进制数
     */
    public static int charToHex(char c) {
        int n = 0;
        switch (c) {
            case '0':
                n = 0X0;
                break;
            case '1':
                n = 0X1;
                break;
            case '2':
                n = 0X2;
                break;
            case '3':
                n = 0X3;
                break;
            case '4':
                n = 0X4;
                break;
            case '5':
                n = 0X5;
                break;
            case '6':
                n = 0X6;
                break;
            case '7':
                n = 0X7;
                break;
            case '8':
                n = 0X8;
                break;
            case '9':
                n = 0X9;
                break;
            case 'a':
                n = 0XA;
                break;
            case 'b':
                n = 0XB;
                break;
            case 'c':
                n = 0XC;
                break;
            case 'd':
                n = 0XD;
                break;
            case 'e':
                n = 0XE;
                break;
            case 'f':
                n = 0XF;
                break;
        }
        return n;
    }

    /**
     * 发送十六进制串口数据的方法
     *
     * @param data 要发送的数据
     */
    public byte[] sendHexSerialPort(int[] data) {
        Log.i("test", "发送串口数据");
        byte[] buf = null;
        try {
            //将16进制的int类型的数组转换为byte数组
            buf = AryChangeManager.hexToByte(data);


            Log.i("test", "串口数据发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("test", "串口数据发送失败");
        }

        return buf;
    }

    //静默安装
    private void installSlient(String path) {
        String cmd = "pm install -r " + path;
        Process process = null;
        DataOutputStream os = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = null;
        StringBuilder errorMsg = null;
        try {
            //静默安装需要root权限
            execLinuxCommand();
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.write(cmd.getBytes());
            os.writeBytes("\n");

            os.writeBytes("exit\n");
            os.flush();
            //执行命令
            int value = process.waitFor();
            //获取返回结果
            successMsg = new StringBuilder();
            errorMsg = new StringBuilder();
            successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
            errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String s;
            while ((s = successResult.readLine()) != null) {
                successMsg.append(s);
            }
            while ((s = errorResult.readLine()) != null) {
                errorMsg.append(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (process != null) {
                    process.destroy();
                }
                if (successResult != null) {
                    successResult.close();
                }
                if (errorResult != null) {
                    errorResult.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        java.text.DateFormat format2 = new java.text.SimpleDateFormat("yyyy:MM:dd:hh:mm:ss");
        String s = format2.format(new Date());
        Log.e("TAG", s + "---成功消息：" + successMsg.toString() + "\n" + "错误消息: " + errorMsg.toString());


    }


    //定时启动
    private void execLinuxCommand() {
        String cmd = "sleep 4200; am start -n com.example.administrator.zzzz/com.example.administrator.zzzz.MainActivity";
        //Runtime对象
        Runtime runtime = Runtime.getRuntime();
        try {
            Process localProcess = runtime.exec("su");
            OutputStream localOutputStream = localProcess.getOutputStream();
            DataOutputStream localDataOutputStream = new DataOutputStream(localOutputStream);
            localDataOutputStream.writeBytes(cmd);
            localDataOutputStream.flush();
            Log.e("logzz", "项目准备重");
        } catch (IOException e) {
            Log.e("logzz", "项目准备重" + e.toString());
            e.printStackTrace();
        }
    }


    protected void excutesucmd(String currenttempfilepath) {
        Process process = null;
        OutputStream out = null;
        InputStream in = null;
        try {
            // 请求root
            process = Runtime.getRuntime().exec("su");
            out = process.getOutputStream();
            // 调用安装
            out.write(("pm install -r " + currenttempfilepath + "\n").getBytes());
            in = process.getInputStream();
            int len = 0;
            byte[] bs = new byte[256];
            while (-1 != (len = in.read(bs))) {
                String state = new String(bs, 0, len);
                if (state.equals("success\n")) {
                    //安装成功后的操作

                    //静态注册自启动广播
                    Intent intent = new Intent();
                    //与清单文件的receiver的anction对应
                    intent.setAction("android.intent.action.PACKAGE_REPLACED");
                    //发送广播
                    sendBroadcast(intent);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onForwardToAccessibility(View view) {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivity(intent);
    }

    public void onSmartInstall(String apkPath) {
        if (TextUtils.isEmpty(apkPath)) {
            Toast.makeText(this, "请选择安装包！", Toast.LENGTH_SHORT).show();
            return;
        }
        Uri uri = Uri.fromFile(new File(apkPath));
        Intent localIntent = new Intent(Intent.ACTION_VIEW);
        localIntent.setDataAndType(uri, "application/vnd.android.package-archive");
        startActivity(localIntent);
    }

    /**
     * 导航栏，状态栏隐藏
     *
     * @param activity
     */
    public static void NavigationBarStatusBar(Activity activity, boolean hasFocus) {
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    public void onWifiEnabled(boolean enabled) {

    }

    @Override
    public void onScanResults(List<ScanResult> scanResults) {
        Log.e("logxx", "回调" + scanResults.size());
    }

    @Override
    public void onWiFiConnectLog(String log) {

    }

    @Override
    public void onWiFiConnectSuccess(String SSID) {

    }

    @Override
    public void onWiFiConnectFailure(String SSID) {

    }

    public void getListOfConnectedDevice() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                BufferedReader br = null;
                boolean isFirstLine = true;

                try {
                    br = new BufferedReader(new FileReader("/proc/net/arp"));
                    String line;

                    while ((line = br.readLine()) != null) {
                        if (isFirstLine) {
                            isFirstLine = false;
                            continue;
                        }

                        String[] splitted = line.split(" +");

                        if (splitted != null && splitted.length >= 4) {

                            String ipAddress = splitted[0];
                            String macAddress = splitted[3];

                            boolean isReachable = InetAddress.getByName(
                                    splitted[0]).isReachable(500);  // this is network call so we cant do that on UI thread, so i take background thread.
                            if (isReachable) {
                                Log.d("Device Information", ipAddress + " : "
                                        + macAddress);

                                finish();
                            } else {
                                Log.d("Device Information", ipAddress + " : "
                                        + macAddress);

                            }

                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }


}
