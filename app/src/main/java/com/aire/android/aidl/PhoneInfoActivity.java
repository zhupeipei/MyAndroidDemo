package com.aire.android.aidl;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.aire.android.reflect.FieldUtils;
import com.aire.android.reflect.MethodUtils;
import com.aire.android.test.R;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

public class PhoneInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_info);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                getImei();
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 10);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            if (permissions.length == 1 && permissions[0].equals(Manifest.permission.READ_PHONE_STATE)) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getImei();
                }
            }
        }
    }

    private void getImei() {
        Log.d("zimotag", "getImei hookTel start");
        hookTel();
        Log.d("zimotag", "getImei hookTel end");

        TelephonyManager telService = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String imei = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            imei = telService.getImei();
        }
        Log.d("zimo_test", "PhoneInfoActivity: getImei: " + imei);
    }

    private void hookTel() {
        try {
            Class<?> clazz = Class.forName("android.os.ServiceManager");
            Method getService = clazz.getDeclaredMethod("getService", String.class);
            final IBinder telBinder = (IBinder) getService.invoke(null, Context.TELEPHONY_SERVICE);
            Map<String, IBinder> cache = (Map<String, IBinder>) FieldUtils.readStaticField(clazz, "sCache");

            final Class telClazz = Class.forName("com.android.internal.telephony.ITelephony");
            TelephonyManager telService = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            final Object telephony = MethodUtils.invokeMethod(telService, "getITelephony");

            final IBinder proxy1 = (IBinder) Proxy.newProxyInstance(telBinder.getClass().getClassLoader(), new Class[]{IBinder.class}, new InvocationHandler() {

//                private IBinder telProxy = ITelephony.Stub.asInterface(ServiceManager.getService(Context.TELEPHONY_SERVICE));

                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    Log.d("zimotag", method.getName() + ", aaa");
                    if ("queryLocalInterface".equals(method.getName())) {
                        return Proxy.newProxyInstance(telephony.getClass().getClassLoader(), new Class[]{telClazz}, new InvocationHandler() {
                            @Override
                            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                Log.d("zimo_test", "PhoneInfoActivity: invoke: " + method.getName());
                                return null;
                            }
                        });
                    }
                    return method.invoke(telBinder, args);
                }
            });
            cache.put(Context.TELEPHONY_SERVICE, proxy1);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("zimotag", e.getMessage());
        }
    }
}