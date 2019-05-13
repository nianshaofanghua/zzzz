package com.example.administrator.zzzz;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by syj on 2019/4/15.
 * rx定时器
 */

public class RxTimerUtil {
    private static Disposable mDisposable;

    /**
     * milliseconds毫秒后执行next操作
     *
     * @param milliseconds
     * @param iRxNext
     */
    public static void timer(long milliseconds, final IRxNext iRxNext) {

        mDisposable = Flowable.intervalRange(1, milliseconds, 0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (iRxNext != null) {
                            iRxNext.doIntent();
                        }
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (iRxNext != null) {
    iRxNext.doComplete();
                        }
                    }
                })
                .subscribe();


    }


    /**
     * 启动定时器
     */
    public static void startTime(final int milliseconds, final IRxLastTime iRxLastTime) {
//        int count_time = 10; //总时间
        Observable.interval(0, 1, TimeUnit.MILLISECONDS)
                .take(milliseconds + 1)//设置总共发送的次数
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        //aLong从0开始
                        return milliseconds - aLong;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Long value) {
                        //Log.d("Timer",""+value);
                        iRxLastTime.doLast("" + value);
                        BigDecimal b = new BigDecimal(CalcUtils.divide((double) value, (double) 1000));
                        double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        Log.e("logzz", "----" + f1);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        // TODO:2017/12/1
                        Log.e("logzz", "---onComplete-");
                        iRxLastTime.doComplete();
                        closeTimer();
                    }
                });
    }

    /**
     * 关闭定时器
     */
    public static void closeTimer() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    /**
     * 取消订阅
     */
    public static void cancel() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            Log.e("logzz", "====定时器取消======");
        }
    }


    public interface IRxNext {
        void doIntent();
        void doComplete();
    }

    public interface IRxLastTime {
        void doLast(String time);

        void doComplete();
    }
}
