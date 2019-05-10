package com.example.administrator.zzzz;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Administrator on 2019/4/29.
 */

public class Application extends android.app.Application{
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
                      return;
                  }
              LeakCanary.install(this);
    }
}
