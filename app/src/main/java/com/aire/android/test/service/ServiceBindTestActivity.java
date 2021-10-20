package com.aire.android.test.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.aire.android.test.R;

public class ServiceBindTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_bind_test);

        bindService(new Intent(this, ServiceTest.class), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d("serviceTest", "onServiceConnected packageName: " + name.getPackageName()
                        + ", className: " + name.getClassName() + ", service: " + service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d("serviceTest", "onServiceDisconnected packageName: " + name.getPackageName()
                        + ", className: " + name.getClassName());
            }
        }, Context.BIND_AUTO_CREATE);
    }
}