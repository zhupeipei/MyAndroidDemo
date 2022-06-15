package com.aire.android.util.size;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import com.aire.android.manager.SystemServiceManager;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * ClassName:BaseUtil Function: TODO ADD FUNCTION Reason: TODO ADD REASON
 *
 * @author jack.qin
 * @Date 2015-4-20 下午3:54:37
 * @see
 * @since Ver 1.1
 */
public class BaseUtil {

    public static int dp2px(Context context, float dipValue) {
        return (int) (dp2pxReturnFloat(context, dipValue));
    }

    public static float dp2pxReturnFloat(Context context, float dipValue) {
        if (context == null) return dipValue * 1.5F;
        final float scale = context.getResources().getDisplayMetrics().density;
        return dipValue * scale + 0.5F;
    }

    public static int sp2px(Context context, float dipValue) {
        return (int) (sp2pxReturnFloat(context, dipValue));
    }

    public static float sp2pxReturnFloat(Context context, float dipValue) {
        if (context == null) return dipValue * 1.5F;
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return dipValue * scale + 0.5F;
    }

    public static int px2dip(Context context, float pxValue) {
        if (context == null)
            return (int) (pxValue * 1.5);
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int pt2px(Context context, float ptValue) {
        int dpi = context.getResources().getDisplayMetrics().densityDpi;
        return (int) (ptValue * dpi / 72);
    }

    /**
     * 优先从Activity获取WindowManager
     *
     * @param context
     * @return
     */
    private static WindowManager getWindowManagerForActivity(Context context) {
        WindowManager windowManager = null;
        if (!(context instanceof Activity)) {
            Activity activity = null; //BaseApplication.getTopActivity();
            if (activity != null) {
                windowManager = activity.getWindowManager();
            }
        }
        if (windowManager == null) {
            windowManager = SystemServiceManager.getWindowManager(context);
        }
        return windowManager;
    }

    private static int mScreenHeight;
    private static int mScreenWidth;

    public static void clearScreenSizeCache() {
        mScreenHeight = mScreenWidth = 0;
    }

    /**
     * get the width of the device screen
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        if (mScreenWidth > 0) {
            return mScreenWidth;
        }
        if (context == null) {
            return 1;
        }

        // 优先从Activity获取WindowManager，因为在分屏时Activity的WindowManager可以拿到分屏后的大小。
        WindowManager wm = getWindowManagerForActivity(context);
        if (wm == null) {
            return 1;
        }
        Point size = new Point();
        Display display = wm.getDefaultDisplay();
        if (display == null) {
            return 1;
        }
        display.getSize(size);
        if (size.x < size.y) {
            mScreenWidth = size.x;
            return mScreenWidth;
        } else {
            //当屏幕宽度大于高度时,部分手机可能是错误的值，所以不缓存。
            mScreenWidth = 0;
            return size.x;
        }
    }

    /**
     * get the height of the device screen
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        if (mScreenHeight > 0) {
            return mScreenHeight;
        }
        if (context == null) {
            return 1;
        }


        WindowManager wm = getWindowManagerForActivity(context);
        if (wm == null) {
            return 1;
        }
        Point size = new Point();
        Display display = wm.getDefaultDisplay();
        if (display == null) {
            return 1;
        }
        display.getSize(size);
        mScreenHeight = size.y;
        return mScreenHeight;
    }

    public static boolean isFoldScreen(Context context) {
        return isHWFoldScreen() || isMiFoldScreen() || isOppoFoldScreen(context)
                || isHonorFoldScreen(context) || isViVoFoldScreen();
    }

    public static boolean isHWFoldScreen() {
        return "ANL-AN00".equals(Build.MODEL) || "TAH-AN00m".equals(Build.MODEL)
                || "TET-AN10".equals(Build.MODEL);
    }

    public static boolean isHWFoldLargeScreen(Context context) {
        return isHWFoldScreen() && getScreenWidth(context) >= 2200 && getScreenHeight(context) >= 2200;
    }

    public static boolean isMiFoldScreen() {
        return getIntSystemProperties("persist.sys.muiltdisplay_type") == 2;
    }

    public static boolean isOppoFoldScreen(Context context) {
        if (context == null) {
            return false;
        }
        return context.getPackageManager().hasSystemFeature("oplus.feature.largescreen");
    }

    public static boolean isHonorFoldScreen(Context context) {
        if (context == null) {
            return false;
        }
        return "HONOR".equalsIgnoreCase(Build.MANUFACTURER)
                && context.getPackageManager().hasSystemFeature("com.hihonor.hardware.sensor.posture");
    }

    public static boolean isViVoFoldScreen() {
        try {
            Class<?> clz = Class.forName("android.util.FtDeviceInfo");
            Object instance = clz.newInstance();
            Method method = clz.getMethod("getDeviceType");
            String status = (String) method.invoke(instance);
            return "foldable".equals(status);
        } catch (Exception e) {
            return false;
        }
    }

    public static String getStrSystemProperties(String name) {
        String res = "";
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method getMethod = clz.getMethod("get", String.class);
            res = (String) getMethod.invoke(clz, name);
        } catch (Exception e) {
            Log.e("getStrSystemProperties", "get() ERROR!!! Exception!", e);
        }
        return res;
    }

    public static int getIntSystemProperties(String name) {
        int res = 0;
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method getIntMethod = clz.getMethod("getInt", String.class, int.class);
            Object tmp = getIntMethod.invoke(clz, name, 0);
            if (tmp instanceof Integer) {
                res = (int) tmp;
            }
        } catch (Exception e) {
            Log.e("getIntSystemProperties", "get() ERROR!!! Exception!", e);
        }
        return res;
    }

    /**
     * 获取状态栏的高度
     */
    public static int mStatusBarHeight = 0;
    private static boolean mIsStatusBarHeightCached = false;

    public static int getStatusBarHeight(Context context) {
        if (context == null)
            return 0;
        if (mStatusBarHeight != 0) {
            return mStatusBarHeight;
        }
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object obj = clazz.newInstance();
            Field field = clazz.getField("status_bar_height");
            int temp = Integer.parseInt(field.get(obj).toString());
            mStatusBarHeight = context.getResources()
                    .getDimensionPixelSize(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //缓存statusBar高度，防止以后获取不到高度

        return mStatusBarHeight;
    }

    public static int getStatusBarHeightUseCache(Context context) {
        if (mStatusBarHeight != 0) {
            return mStatusBarHeight;
        }

        int statusBarHeight = 0;

        if (statusBarHeight != 0) {
            return statusBarHeight;
        }

        return getStatusBarHeight(context);
    }

    /**
     * 是否是平板
     *
     * @param context
     * @return
     */
    public static boolean isTabletDevice(Context context) {
        if (context == null) {
            return false;
        }
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >=
                Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    //字节转换为KB或MB
    public static String byteToMb(double bytes) {
        String result;
        double kb = bytes / 1024;
        if (kb > 1024) {
            double mb = kb / 1024;
            DecimalFormat df = new DecimalFormat("0.0");
            result = df.format(mb) + "MB/s";
            Log.e("byteToMb", "1");
        } else {
            DecimalFormat df = new DecimalFormat("0.0");
            result = df.format(bytes / 1024) + "KB/s";
            Log.e("byteToMb", "2");
        }
        Log.e("byteToMb", result);
        return result;
    }

    public static int[] getNotchSize(Context context) {

        int[] ret = new int[]{0, 0};

        try {

            ClassLoader cl = context.getClassLoader();

            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");

            Method get = HwNotchSizeUtil.getMethod("getNotchSize");

            ret = (int[]) get.invoke(HwNotchSizeUtil);

        } catch (ClassNotFoundException e) {

            Log.e("test", "getNotchSize ClassNotFoundException");

        } catch (NoSuchMethodException e) {

            Log.e("test", "getNotchSize NoSuchMethodException");

        } catch (Exception e) {

            Log.e("test", "getNotchSize Exception");

        } finally {

            return ret;

        }

    }


    /**
     * 获取虚拟按键栏高度
     *
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        if (isMeizu()) {
            return getSmartBarHeight(context);
        }
        int result = 0;
        if (hasNavBar(context)) {
            Resources res = context.getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    /**
     * 检查是否存在虚拟按键栏
     *
     * @param context
     * @return
     */
    public static boolean hasNavBar(Context context) {
        if (context == null) {
            return false;
        }

        if (isSmartisanR1()) {
            return true;
        }

        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    /**
     * 判断虚拟按键栏是否重写
     *
     * @return
     */
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        return sNavBarOverride;
    }

    /**
     * 判断是否meizu手机
     *
     * @return
     */
    public static boolean isMeizu() {
        return Build.BRAND.equals("Meizu");
    }

    /**
     * 获取魅族手机底部虚拟键盘高度
     *
     * @param context
     * @return
     */
    public static int getSmartBarHeight(Context context) {
        if (context == null)
            return 0;

        try {
            Class c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("mz_action_button_min_height");
            int height = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static PackageInfo getPackageArchiveInfo(PackageManager packageManager, String archiveFilePath, int flags) {
        // Workaround for https://code.google.com/p/android/issues/detail?id=9151#c8

        return packageManager.getPackageArchiveInfo(archiveFilePath, flags);


//		try {
//			Class packageParserClass = Class.forName(
//					"android.content.pm.PackageParser");
//			Class[] innerClasses = packageParserClass.getDeclaredClasses();
//			Class packageParserPackageClass = null;
//			for (Class innerClass : innerClasses) {
//				if (0 == innerClass.getName().compareTo("android.content.pm.PackageParser$Package")) {
//					packageParserPackageClass = innerClass;
//					break;
//				}
//			}
//			Constructor packageParserConstructor = packageParserClass.getConstructor(
//					String.class);
//			Method parsePackageMethod = packageParserClass.getDeclaredMethod(
//					"parsePackage", File.class, String.class, DisplayMetrics.class, int.class);
//			Method collectCertificatesMethod = packageParserClass.getDeclaredMethod(
//					"collectCertificates", packageParserPackageClass, int.class);
//			Method generatePackageInfoMethod = packageParserClass.getDeclaredMethod(
//					"generatePackageInfo", packageParserPackageClass, int[].class, int.class, long.class, long.class);
//			packageParserConstructor.setAccessible(true);
//			parsePackageMethod.setAccessible(true);
//			collectCertificatesMethod.setAccessible(true);
//			generatePackageInfoMethod.setAccessible(true);
//
//			Object packageParser = packageParserConstructor.newInstance(archiveFilePath);
//
//			DisplayMetrics metrics = new DisplayMetrics();
//			metrics.setToDefaults();
//
//			final File sourceFile = new File(archiveFilePath);
//
//			Object pkg = parsePackageMethod.invoke(
//					packageParser,
//					sourceFile,
//					archiveFilePath,
//					metrics,
//					0);
//			if (pkg == null) {
//				return null;
//			}
//
//			if ((flags & android.content.pm.PackageManager.GET_SIGNATURES) != 0) {
//				collectCertificatesMethod.invoke(packageParser, pkg, 0);
//			}
//
//			return (PackageInfo) generatePackageInfoMethod.invoke(null, pkg, null, flags, 0, 0);
//		} catch (Exception e) {
//			Log.e("Signature Monitor",
//					"android.content.pm.PackageParser reflection failed: " + e.toString());
//		}
//
//		return null;
    }

    public static boolean verifyPluginFileSignature(Context context, String pluginFilepath) {
        File pluginFile = new File(pluginFilepath);
        if (!pluginFile.exists()) {
            return false;
        }

        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo newInfo = getPackageArchiveInfo(packageManager, pluginFilepath, PackageManager.GET_ACTIVITIES | PackageManager.GET_SIGNATURES);
            PackageInfo mainPkgInfo = packageManager.getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            return checkSignatures(newInfo, mainPkgInfo) == PackageManager.SIGNATURE_MATCH;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }

    private static int checkSignatures(PackageInfo pluginPackageInfo,
                                       PackageInfo mainPackageInfo) {

        Signature[] pluginSignatures = pluginPackageInfo.signatures;
        Signature[] mainSignatures = mainPackageInfo.signatures;
        boolean pluginSigned = pluginSignatures != null
                && pluginSignatures.length > 0;
        boolean mainSigned = mainSignatures != null
                && mainSignatures.length > 0;

        if (pluginSignatures != null && mainSignatures != null)
            Log.d("checkSignatures ", pluginSignatures.length + "  " + mainSignatures.length);

        if (!pluginSigned && !mainSigned) {
            return PackageManager.SIGNATURE_NEITHER_SIGNED;
        } else if (!pluginSigned && mainSigned) {
            return PackageManager.SIGNATURE_FIRST_NOT_SIGNED;
        } else if (pluginSigned && !mainSigned) {
            return PackageManager.SIGNATURE_SECOND_NOT_SIGNED;
        } else {
            if (pluginSignatures.length == mainSignatures.length) {
                for (int i = 0; i < pluginSignatures.length; i++) {
                    Signature s1 = pluginSignatures[i];
                    Signature s2 = mainSignatures[i];
                    if (!Arrays.equals(s1.toByteArray(), s2.toByteArray())) {
                        return PackageManager.SIGNATURE_NO_MATCH;
                    }
                }
                return PackageManager.SIGNATURE_MATCH;
            } else {
                return PackageManager.SIGNATURE_NO_MATCH;
            }
        }
    }

    public static boolean activityIsLive(Activity activity) {
        if (activity == null) {
            return false;
        }
        if (activity.isFinishing()) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (activity.isDestroyed()) {
                return false;
            }
        }
        return true;
    }

    private static int startCount = 0;
    public static boolean hasReadStart = false;

    public static int largeMemoryClass;
    public static int memoryClass;

    public static int getLargeMemoryClass(Context context) {
        if (largeMemoryClass <= 0) {
            ActivityManager am = SystemServiceManager.getActivityManager(context);
            if (am != null) {
                largeMemoryClass = am.getLargeMemoryClass();
            }
        }
        return largeMemoryClass;
    }

    public static int getMemoryClass(Context context) {
        if (memoryClass <= 0) {
            ActivityManager am = SystemServiceManager.getActivityManager(context);
            if (am != null) {
                memoryClass = am.getLargeMemoryClass();
            }
        }
        return memoryClass;
    }

    public static int calculateMemoryCacheSize(Context context) {
        ActivityManager am = SystemServiceManager.getActivityManager(context);
        if (am == null)
            return 1024 * 1024 * 10;
        boolean largeHeap = (context.getApplicationInfo().flags & 1048576) != 0;
        largeMemoryClass = am.getLargeMemoryClass();
        memoryClass = am.getMemoryClass();
        int memory = largeHeap ? largeMemoryClass : memoryClass;
        int memoryCacheSize = (int) (1048576L * (long) memory / 28L);
        Log.e("ImageMemoryCacheSize:", "_____图片缓存大小___" + memoryCacheSize / 1024 / 1024);
        return memoryCacheSize;
    }

    private static int mScreenWithInDp;

    public static int getScreenWidthForDp(Context context) {
        if (mScreenWithInDp != 0) {
            return mScreenWithInDp;
        }
        if (context == null)
            return 1;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int width = dm.widthPixels;// 屏幕宽度（像素）
        float density = dm.density;//屏幕密度（0.75 / 1.0 / 1.5）
        //屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width / density);//屏幕宽度(dp)
        return screenWidth;
    }

    /**
     * 获取屏幕尺寸，但是不包括虚拟功能高度
     *
     * @return
     */
    public static int getNoVirtualNavBarScreenHeight(Context context) {
        int[] result = SystemServiceManager.getNoVirtualNavBarScreenSize(context);
        return result[1];
    }

    /**
     * 通过反射，获取包含虚拟键的整体屏幕高度
     *
     * @return 包含虚拟键的整体屏幕高度
     */
    public static int getHasVirtualNavBarScreenHeight(Context context) {
        int[] result = SystemServiceManager.getHasVirtualNavBarScreenSize(context);
        return result[1];
    }

    /**
     * 检查虚拟导航键是否显示（通用方法）
     *
     * @param context 上下文
     * @return true 显示 false 隐藏
     */
    public static boolean isNavigationBarShow(Context context) {
        return getHasVirtualNavBarScreenHeight(context) - getNoVirtualNavBarScreenHeight(context) > 0;
    }

    /**
     * 获取 OPPO R15 手机导航模式
     * <p>
     * 使用虚拟导航键时为 0 或者 1:(0 表示使用虚拟导航按键且关闭手动隐藏功能，1表示使用虚拟导航按键且打开手动隐藏功能)
     * 使用导航手势时为 2
     */
    private static final String HIDE_NAVIGATIONBAR_ENABLE = "hide_navigationbar_enable";

    public static int oppoHideNavigationBarEnabled(Context context) {
        int val = 0;
        try {
            val = Settings.Secure.getInt(context.getContentResolver(), HIDE_NAVIGATIONBAR_ENABLE, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return val;
    }

    /**
     * 检测手机是否坚果R1手机
     *
     * @return true 是坚果R1 false 不是坚果R1
     */
    public static boolean isSmartisanR1() {
        return "trident".equalsIgnoreCase(Build.DEVICE);
    }

    public static boolean isHuaWei() {
        return "huawei".equalsIgnoreCase(Build.MANUFACTURER);
    }


    /**
     * 通过反射判断是否是透明页面
     */
    public static boolean isTranslucentOrFloating(Activity activity) {
        boolean isTranslucentOrFloating = false;
        try {
            int[] styleableRes = (int[]) Class.forName("com.android.internal.R$styleable").getField("Window").get(null);
            final TypedArray ta = activity.obtainStyledAttributes(styleableRes);
            Method m = ActivityInfo.class.getMethod("isTranslucentOrFloating", TypedArray.class);
            m.setAccessible(true);
            isTranslucentOrFloating = (boolean) m.invoke(null, ta);
            m.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTranslucentOrFloating;
    }

    /**
     * 通过反射将方向设置为 SCREEN_ORIENTATION_UNSPECIFIED，绕开系统的检查
     */
    public static boolean fixOrientation(Activity activity) {
        try {
            Field field = Activity.class.getDeclaredField("mActivityInfo");
            field.setAccessible(true);
            ActivityInfo o = (ActivityInfo) field.get(activity);
            if (o != null && (o.screenOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE ||
                    o.screenOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT ||
                    o.screenOrientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE ||
                    o.screenOrientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT ||
                    o.screenOrientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE ||
                    o.screenOrientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT ||
                    o.screenOrientation == ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE ||
                    o.screenOrientation == ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT ||
                    o.screenOrientation == ActivityInfo.SCREEN_ORIENTATION_LOCKED)) {
                o.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
            }
            field.setAccessible(false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 通过反射将activity设置成非透明，并设置新背景色 用于解决android8.0 透明主题的activity设置方向时崩溃的系统bug；
     */
    public static void convertFromTranslucent(Activity activity, int color) {
        try {
            Method method = Activity.class.getDeclaredMethod("convertFromTranslucent");
            method.setAccessible(true);
            method.invoke(activity);
            //设置背景为白色，否则背景会显示黑色背景(设置透明色无效)
            activity.getWindow().getDecorView().setBackgroundColor(color);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isNavigationBarShowing(Context context) {
        //判断手机底部是否支持导航栏显示
        boolean haveNavigationBar = checkDeviceHasNavigationBar(context);
        if (haveNavigationBar) {
            if (Build.VERSION.SDK_INT >= 17) {
                String brand = Build.BRAND;

                String mDeviceInfo;
                if (brand.equalsIgnoreCase("HUAWEI") || "HONOR".equals(brand)) {
                    mDeviceInfo = "navigationbar_is_min";
                } else if (brand.equalsIgnoreCase("XIAOMI")) {
                    mDeviceInfo = "force_fsg_nav_bar";
                } else if (brand.equalsIgnoreCase("VIVO")) {
                    mDeviceInfo = "navigation_gesture_on";
                    if (Settings.Secure.getInt(context.getContentResolver(), mDeviceInfo, 0) == 0) {
                        return true;
                    }
                } else if (brand.equalsIgnoreCase("OPPO")) {
                    mDeviceInfo = "hide_navigationbar_enable";
                    if (Settings.Secure.getInt(context.getContentResolver(), mDeviceInfo, 0) == 0) {
                        return true;
                    }
                } else if (brand.equalsIgnoreCase("samsung")) {
                    mDeviceInfo = "navigationbar_hide_bar_enabled";
                } else {
                    mDeviceInfo = "navigationbar_is_min";
                }
                if (Settings.Global.getInt(context.getContentResolver(), mDeviceInfo, 0) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;
    }

    public static int getNavigationBarHeight1(Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            Resources resources = activity.getResources();
            int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            int height = resources.getDimensionPixelSize(resourceId);
            //超出系统默认的导航栏高度以上，则认为存在虚拟导航
            if ((realSize.y - size.y) > (height - 10)) {
                return height;
            }

            return 0;
        } else {
            boolean menu = ViewConfiguration.get(activity).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if (menu || back) {
                return 0;
            } else {
                Resources resources = activity.getResources();
                int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
                int height = resources.getDimensionPixelSize(resourceId);
                return height;
            }
        }

    }
}
