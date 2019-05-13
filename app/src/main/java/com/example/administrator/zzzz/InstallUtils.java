package com.example.administrator.zzzz;

import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Created by Administrator on 2019/4/12.
 */

public class InstallUtils {


    //静默安装
    public static void installSlient(String path,String packageClassName,String className) {
        String cmd = "pm install -r " + path;
        Process process = null;
        DataOutputStream os = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = null;
        StringBuilder errorMsg = null;
        try {



            //静默安装需要root权限
            execLinuxCommand(packageClassName,className);
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
        String     s = format2.format(new Date());
        Log.e("TAG", s+"---成功消息：" + successMsg.toString() + "\n" + "错误消息: " + errorMsg.toString());


    }


    //定时启动
    public static void execLinuxCommand(String packageClassName,String className) {
        String cmd = "sleep 4200; am start -n "+packageClassName+"/"+className;
        //Runtime对象
        Runtime runtime = Runtime.getRuntime();
        try {
            Process localProcess = runtime.exec("su");
            OutputStream localOutputStream = localProcess.getOutputStream();
            DataOutputStream localDataOutputStream = new DataOutputStream(localOutputStream);
            localDataOutputStream.writeBytes(cmd);
            localDataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }








}
