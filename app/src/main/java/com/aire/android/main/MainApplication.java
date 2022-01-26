package com.aire.android.main;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * @Author: Zhupeipei
 * @CreateDate: 2021/10/16 7:46 下午
 */
public class MainApplication extends Application {
    public static Application INSTANCE;

    public MainApplication() {
        int i = 0;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;

        Log.i("zimo22222", "onCreate: start");
//        Looper.getMainLooper().setMessageLogging(new Printer() {
//            @Override
//            public void println(String x) {
//                Log.i("zimo22222", "println: " + x);
//            }
//        });

//        BlockCanary.install(this, new MyBlockCanaryContext()).start();//在主进程初始化调用
    }

    @Override
    protected void attachBaseContext(Context base) {
        Log.d("zimotag", "attachBaseContext: " + base.getApplicationContext());
        super.attachBaseContext(base);
        Log.d("zimotag", "attachBaseContext2: " + base.getApplicationContext());
    }
}
