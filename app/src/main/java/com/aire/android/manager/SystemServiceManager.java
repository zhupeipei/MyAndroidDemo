package com.aire.android.manager;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.MODIFY_PHONE_STATE;
import static android.Manifest.permission.VIBRATE;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.bluetooth.BluetoothManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraManager;
import android.hardware.display.DisplayManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.Vibrator;
import android.os.storage.StorageManager;
import android.telephony.PhoneStateListener;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.RequiresPermission;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ZhuPeipei
 * @date 2022/6/15 14:38
 */
public class SystemServiceManager {
    private static Map<String, WeakReference<Object>> sServiceManagerMap = new ConcurrentHashMap<>();

    public static <T> T getSystemService(Context context, String name) {
        if (context == null || TextUtils.isEmpty(name)) {
            return null;
        }
        //window 不做缓存
        if (TextUtils.equals(Context.WINDOW_SERVICE, name)) {
            return (T) context.getSystemService(name);
        }
        //LayoutInflater 不做缓存
        if (TextUtils.equals(Context.LAYOUT_INFLATER_SERVICE, name)) {
            return (T) context.getSystemService(name);
        }
        WeakReference<Object> weakService = sServiceManagerMap.get(name);
        if (weakService != null && weakService.get() != null) {
            return (T) weakService.get();
        }
        T service = (T) context.getSystemService(name);
        if (service != null) {
            sServiceManagerMap.put(name, new WeakReference<Object>(service));
        }
        return service;
    }

    public static ActivityManager getActivityManager(Context context) {
        ActivityManager service = getSystemService(context, Context.ACTIVITY_SERVICE);
        return service;
    }

    public static WindowManager getWindowManager(Context context) {
        WindowManager service = getSystemService(context, Context.WINDOW_SERVICE);
        return service;
    }

    public static AudioManager getAudioManager(Context context) {
        AudioManager service = getSystemService(context, Context.AUDIO_SERVICE);
        return service;
    }

    public static SensorManager getSensorManager(Context context) {
        SensorManager service = getSystemService(context, Context.SENSOR_SERVICE);
        return service;
    }

    public static NotificationManager getNotificationManager(Context context) {
        NotificationManager service = getSystemService(context, Context.NOTIFICATION_SERVICE);
        return service;
    }

    public static LayoutInflater getLayoutInflater(Context context) {
        LayoutInflater service = getSystemService(context, Context.LAYOUT_INFLATER_SERVICE);
        return service;
    }

    public static DisplayManager getDisplayManager(Context context) {
        DisplayManager service = getSystemService(context, Context.DISPLAY_SERVICE);
        return service;
    }

    public static CameraManager getCameraManager(Context context) {
        CameraManager service = getSystemService(context, Context.CAMERA_SERVICE);
        return service;
    }

    public static ConnectivityManager getConnectivityManager(Context context) {
        ConnectivityManager service = getSystemService(context, Context.CONNECTIVITY_SERVICE);
        return service;
    }

    public static BluetoothManager getBluetoothManager(Context context) {
        BluetoothManager service = getSystemService(context, Context.BLUETOOTH_SERVICE);
        return service;
    }

    public static AlarmManager getAlarmManager(Context context) {
        AlarmManager service = getSystemService(context, Context.ALARM_SERVICE);
        return service;
    }

    public static InputMethodManager getInputMethodManager(Context context) {
        InputMethodManager service = getSystemService(context, Context.INPUT_METHOD_SERVICE);
        return service;
    }

    public static TelephonyManager getTelephonyManager(Context context) {
        TelephonyManager service = getSystemService(context, Context.TELEPHONY_SERVICE);
        return service;
    }

    public static PowerManager getPowerManager(Context context) {
        PowerManager service = getSystemService(context, Context.POWER_SERVICE);
        return service;
    }

    public static WifiManager getWifiManager(Context context) {
        WifiManager service = getSystemService(context, Context.WIFI_SERVICE);
        return service;
    }

    public static ClipboardManager getClipboardManager(Context context) {
        ClipboardManager service = getSystemService(context, Context.CLIPBOARD_SERVICE);
        return service;
    }

    public static LocationManager getLocationManager(Context context) {
        LocationManager service = getSystemService(context, Context.LOCATION_SERVICE);
        return service;
    }

    public static BatteryManager getBatteryManager(Context context) {
        BatteryManager service = getSystemService(context, Context.BATTERY_SERVICE);
        return service;
    }

    public static AppOpsManager getAppOpsManager(Context context) {
        AppOpsManager service = getSystemService(context, Context.APP_OPS_SERVICE);
        return service;
    }

    public static int getDefaultDataId(Context context) {
        int subscriberId = 0;
        if (Build.VERSION.SDK_INT > 24) {
            subscriberId = SubscriptionManager.getDefaultDataSubscriptionId();
        } else if (Build.VERSION.SDK_INT > 21) {
            try {
                SubscriptionManager service = getSystemService(context, Context.TELEPHONY_SUBSCRIPTION_SERVICE);
                Class cls = SubscriptionManager.class.getClass();
                Method method = cls.getDeclaredMethod("getDefaultDataSubId");
                subscriberId = (Integer) method.invoke(service);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return subscriberId;
    }

    public static String[] getStoragePath(Context context) {
        String[] paths = null;
        StorageManager service = getSystemService(context, Context.STORAGE_SERVICE);
        if (service == null) {
            return paths;
        }
        try {
            Method method = service.getClass().getMethod("getVolumePaths");
            method.setAccessible(true);
            paths = (String[]) method.invoke(service);// 调用该方法
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return paths;
    }

    public static boolean isLocationOpen(Context context) {
        LocationManager service = getSystemService(context, Context.LOCATION_SERVICE);
        if (service == null) {
            return false;
        }
        //gps定位
        boolean isGpsProvider = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
        //网络定位
        boolean isNetWorkProvider = service.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return isGpsProvider || isNetWorkProvider;
    }

    public static View inflaterLayout(Context context, int layoutId, ViewGroup container) {
        LayoutInflater service = getLayoutInflater(context);
        if (service == null) {
            return null;
        }
        return service.inflate(layoutId, container, false);
    }

    public static void switchAccessibility(Context context, boolean enable) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                AccessibilityManager service = getSystemService(context, Context.ACCESSIBILITY_SERVICE);
                Method set = service.getClass().getDeclaredMethod("setState", int.class);
                set.setAccessible(true);
                set.invoke(service, !enable ? 0 : 1);//参考AccessibilityManager.STATE_FLAG_ACCESSIBILITY_ENABLED
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isScreenOn(Context context) {
        PowerManager service = getPowerManager(context);
        if (service == null) {
            return false;
        }
        try {
            return service.isScreenOn();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean getMobileDataEnabled(Context context) {
        try {
            TelephonyManager service = getTelephonyManager(context);
            if (service == null) {
                return false;
            }
            @SuppressLint("PrivateApi")
            Method method = service.getClass().getDeclaredMethod("getDataEnabled");
            method.setAccessible(true);
            if (null != method) {
                return (boolean) method.invoke(service);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @RequiresPermission(MODIFY_PHONE_STATE)
    public static boolean setMobileDataEnabled(Context context, final boolean enabled) {
        try {
            TelephonyManager service = getTelephonyManager(context);
            if (service == null) {
                return false;
            }
            Method method = service.getClass().getDeclaredMethod("setDataEnabled", boolean.class);
            method.setAccessible(true);
            if (null != method) {
                method.invoke(service, enabled);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static TelephonyManager setTelephonyCallStateListener(Context context, PhoneStateListener listener) {
        return setTelephonyEventListener(context, Context.TELEPHONY_SERVICE, listener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    public static TelephonyManager setTelephonyCallStateListener(Context context, String name, PhoneStateListener listener) {
        return setTelephonyEventListener(context, name, listener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    public static TelephonyManager setTelephonyEventListener(Context context, String name, PhoneStateListener listener, int callState) {
        if (listener == null) {
            return null;
        }
        TelephonyManager service = getSystemService(context, name);
        if (service == null) {
            return null;
        }
        service.listen(listener, callState);
        return service;
    }

    public static boolean setClipBoardData(Context context, String data) {
        return setClipBoardData(context, null, data);
    }

    public static boolean setClipBoardData(Context context, String label, String data) {
        if (data == null) {
            return false;
        }
        ClipboardManager service = getClipboardManager(context);
        if (service == null) {
            return false;
        }
        try {
            service.setPrimaryClip(ClipData.newPlainText(label, data));
        } catch (Throwable e) {
            //华福反馈，某些oppo手机会抛异常
            e.printStackTrace();
        }
        return true;
    }

    public static boolean isWifiEnable(Context context) {
        WifiManager service = getWifiManager(context);
        return service != null && service.isWifiEnabled();
    }

    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager service = getConnectivityManager(context);
        if (service == null) {
            return false;
        }
        return service.getActiveNetworkInfo() != null
                && service.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }

    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager service = getConnectivityManager(context);
        if (service == null) {
            return false;
        }
        try {
            final NetworkInfo info = service.getActiveNetworkInfo();
            return info != null && info.isConnectedOrConnecting();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean adjustSoftInput(View view, boolean show) {
        if (view == null) {
            return false;
        }
        InputMethodManager service = getInputMethodManager(view.getContext());
        if (service == null) {
            return false;
        }
        if (show) {
            service.showSoftInput(view, 0);
        } else {
            if (service.isActive()) {
                service.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return true;
    }

    public static boolean hideSoftInput(Context context) {
        return hideSoftInput(context, 0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static boolean hideSoftInput(Context context, int showFlags, int hideFlags) {
        InputMethodManager service = getInputMethodManager(context);
        if (service == null) {
            return false;
        }
        service.toggleSoftInput(showFlags, hideFlags);
        return true;
    }

    public static boolean hideSoftInputFromWindow(Context context, IBinder windowToken, int flags) {
        if (windowToken == null) {
            return false;
        }
        InputMethodManager service = getInputMethodManager(context);
        if (service == null) {
            return false;
        }
        service.hideSoftInputFromWindow(windowToken, flags);
        return true;
    }

    public static boolean showSoftInput(View view) {
        return showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public static boolean showSoftInput(View view, int flags) {
        if (view == null) {
            return false;
        }
        InputMethodManager service = getInputMethodManager(view.getContext());
        if (service == null) {
            return false;
        }
        return service.showSoftInput(view, flags);
    }

    public static boolean requestAudioFocus(Context context, AudioManager.OnAudioFocusChangeListener listener) {
        AudioManager service = getAudioManager(context);
        if (service == null) {
            return false;
        }
        int result = service.requestAudioFocus(listener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    }

    @RequiresPermission(VIBRATE)
    public static boolean setVibrator(Context context, long duration) {
        if (duration <= 0) {
            return false;
        }
        Vibrator service = getSystemService(context, Context.VIBRATOR_SERVICE);
        if (service == null) {
            return false;
        }
        service.vibrate(duration);
        return true;
    }

    /**
     * 获取屏幕尺寸，但是不包括虚拟功能高度
     *
     * @return
     */
    public static int[] getNoVirtualNavBarScreenSize(Context context) {
        int[] result = new int[2];
        if (context == null) {
            return result;
        }
        WindowManager service = getWindowManager(context.getApplicationContext());
        if (service == null) {
            return result;
        }
        Display display = service.getDefaultDisplay();
        if (display == null) {
            return result;
        }
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        result[0] = dm.widthPixels;
        result[1] = dm.heightPixels;
        return result;
    }

    /**
     * 通过反射，获取包含虚拟键的整体屏幕高度
     *
     * @return 包含虚拟键的整体屏幕高度
     */
    public static int[] getHasVirtualNavBarScreenSize(Context context) {
        int[] result = new int[2];
        if (context == null) {
            return result;
        }
        WindowManager service = getWindowManager(context.getApplicationContext());
        if (service == null) {
            return result;
        }
        Display display = service.getDefaultDisplay();
        if (display == null) {
            return result;
        }
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            DisplayMetrics dm = new DisplayMetrics();
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            result[0] = dm.widthPixels;
            result[1] = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean isProcessInRecentTasks(Context context, String processName) {
        if (TextUtils.isEmpty(processName)) {
            return true;
        }
        ActivityManager service = getActivityManager(context);
        if (service == null) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            List<ActivityManager.AppTask> tasks = service.getAppTasks();
            if (tasks == null || tasks.isEmpty()) {
                return false;
            }
            for (ActivityManager.AppTask task : tasks) {
                ActivityManager.RecentTaskInfo info = null;
                if (task != null) {
                    info = task.getTaskInfo();
                }
                if (info != null && info.baseIntent != null
                        && info.baseIntent.getComponent() != null
                        && TextUtils.equals(processName, info.baseIntent.getComponent().getPackageName())) {
                    return true;
                }
            }
        } else {
            List<ActivityManager.RecentTaskInfo> list = service.getRecentTasks(100, ActivityManager.RECENT_IGNORE_UNAVAILABLE);
            if (list == null || list.isEmpty()) {
                return false;
            }
            for (int i = 0; i < list.size(); i++) {
                ActivityManager.RecentTaskInfo info = list.get(i);
                if (info != null && info.baseIntent != null
                        && info.baseIntent.getComponent() != null
                        && TextUtils.equals(processName, info.baseIntent.getComponent().getPackageName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
