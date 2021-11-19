package com.aire.android.main;

import android.app.Application;
import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.util.Printer;

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

        Looper.getMainLooper().setMessageLogging(new Printer() {
            @Override
            public void println(String x) {
                Log.i("zimo22222", "println: " + x);
            }
        });

    }

    @Override
    protected void attachBaseContext(Context base) {
        Log.d("zimotag", "attachBaseContext: " + base.getApplicationContext());
        super.attachBaseContext(base);
        Log.d("zimotag", "attachBaseContext2: " + base.getApplicationContext());
    }
}
