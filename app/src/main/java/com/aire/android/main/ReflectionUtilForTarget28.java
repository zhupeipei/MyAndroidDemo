package com.aire.android.main;

import static android.os.Build.VERSION.SDK_INT;

import android.content.Context;
import android.os.Build;

import java.lang.reflect.Method;

/**
 * Author: Harry Shi
 * Email: harry.shi@ximalaya.com
 * Date: 2020/6/24
 *
 * target28 绕过AndroidP hidden api限制,在application的attachBaseContext中调用
 */
public class ReflectionUtilForTarget28 {
    //private static final String TAG = "Reflection";

    private static Object mVmRuntime;
    private static Method mSetHiddenApiExemptions;

    static {
        if (SDK_INT >= Build.VERSION_CODES.P) {
            try {
                Method forName = Class.class.getDeclaredMethod("forName", String.class);
                Method getDeclaredMethod = Class.class.getDeclaredMethod("getDeclaredMethod", String.class, Class[].class);

                Class<?> vmRuntimeClass = (Class<?>) forName.invoke(null, "dalvik.system.VMRuntime");
                Method getRuntime = (Method) getDeclaredMethod.invoke(vmRuntimeClass, "getRuntime", null);
                mSetHiddenApiExemptions = (Method) getDeclaredMethod.invoke(vmRuntimeClass, "setHiddenApiExemptions", new Class[]{String[].class});
                mVmRuntime = getRuntime.invoke(null);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        // System.loadLibrary("free-reflection");
    }

//    private static native int unsealNative(int targetSdkVersion);

//    private static int UNKNOWN = -9999;

//    private static final int ERROR_SET_APPLICATION_FAILED = -20;

    private static final int ERROR_EXEMPT_FAILED = -21;

//    private static int unsealed = UNKNOWN;

    public static int unseal(Context context) {
        if (SDK_INT < 28) {
            // Below Android P, ignore
            return 0;
        }

        // try exempt API first.
        if (exemptAll()) {
            return 0;
        } else {
            return ERROR_EXEMPT_FAILED;
        }
    }

    /**
     * make the method exempted from hidden API check.
     *
     * @param method the method signature prefix.
     * @return true if success.
     */
    public static boolean exempt(String method) {
        return exempt(new String[]{method});
    }

    /**
     * make specific methods exempted from hidden API check.
     *
     * @param methods the method signature prefix, such as "Ldalvik/system", "Landroid" or even "L"
     * @return true if success
     */
    public static boolean exempt(String... methods) {
        if (mVmRuntime == null || mSetHiddenApiExemptions == null) {
            return false;
        }

        try {
            mSetHiddenApiExemptions.invoke(mVmRuntime, new Object[]{methods});
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    /**
     * Make all hidden API exempted.
     *
     * @return true if success.
     */
    public static boolean exemptAll() {
        return exempt(new String[]{"L"});
    }
}
