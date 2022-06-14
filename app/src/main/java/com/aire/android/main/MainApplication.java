package com.aire.android.main;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.aire.android.anr.spfix.SpAnrFix;
import com.tencent.mmkv.MMKV;

import java.security.KeyPairGenerator;

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

        String rootDir = MMKV.initialize(this);
        Log.i("MainApplication", "mmkv root " + rootDir);

        boolean fixed = SpAnrFix.fix(this);
        Log.i("MainApplication", "onCreate spFixed: " + fixed);
        Toast.makeText(this, "onCreate spFixed: " + fixed, Toast.LENGTH_LONG).show();

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
