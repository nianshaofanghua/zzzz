package com.example.administrator.zzzz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class RecurrenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recurrence);

        findViewById(R.id.tv_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }

    int count = 0;

    public void initData() {
        // 打开串口操作  4个打开串口操作
        // 执行倒计时操作 实现递归调用间隔一秒
        RxTimerUtil.timer(3, new RxTimerUtil.IRxNext() {
            @Override
            public void doIntent() {
                Log.e("log", "调用打开串口方法" + count);
                count++;
            }

            @Override
            public void doComplete() {
                count= 0;
                Log.e("log", "方法结束count置0");
            }
        });

//       RxTimerUtil.startTime(3000, new RxTimerUtil.IRxLastTime() {
//           @Override
//           public void doLast(String time) {
//               Log.e("log","调用多次串口方法"+count);
//               count++;
//           }
//
//           @Override
//           public void doComplete() {
//
//           }
//       });
    }
}
