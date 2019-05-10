package com.example.administrator.zzzz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;

/**
 * Created by Administrator on 2019/4/15.
 */

public class CountTimer extends CountDownTimer {

    private Context context;

    /**
     * 参数 millisInFuture       倒计时总时间（如60S，120s等）
     * 参数 countDownInterval    渐变时间（每次倒计1s）
     */
    public CountTimer(long millisInFuture, long countDownInterval,Context context) {
        super(millisInFuture, countDownInterval);
        this.context=context;
    }
    // 计时完毕时触发
    @Override
    public void onFinish() {
        Intent intent = new Intent(context,MainActivity.class);
        context.startActivity(intent);

    }
    // 计时过程显示
    @Override
    public void onTick(long millisUntilFinished) {
    }


}
