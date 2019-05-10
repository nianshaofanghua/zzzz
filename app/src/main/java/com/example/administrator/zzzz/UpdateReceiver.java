package com.example.administrator.zzzz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2019/4/12.
 */

public class UpdateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        // TODO Auto-generated method stub
//        if (intent.getAction().equals("android.intent.action.PACKAGE_REPLACED")) {
//     //       Toast.makeText(context, "升级了一个安装包，重新启动此程序", Toast.LENGTH_SHORT).show();
//            Log.e("logzz","升级了一个安装包，重新启动此程序");
//            Intent intent2 = new Intent(context, MainActivity.class);
//            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent2);
//
//        }else {
//            Log.e("logzz",intent.getAction()+"---intentgetAction");
//        }
//        //接收安装广播
//        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
//            String packageName = intent.getDataString();
//            System.out.println("安装了:" + packageName + "包名的程序");
//            Log.e("logzz","安装了:" + packageName + "包名的程序");
//        }
//        //接收卸载广播
//        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
//            String packageName = intent.getDataString();
//            Log.e("logzz","卸载了:" + packageName + "包名的程序");
//
//        }
    }

}
