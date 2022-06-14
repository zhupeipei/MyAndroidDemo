package com.aire.android.util;

import com.tencent.mmkv.MMKV;

/**
 * @author ZhuPeipei
 * @date 2022/6/14 13:48
 */
public class MMKVUtil {
    private static MMKVUtil sInstance;
    private MMKV mMmkv;

    private MMKVUtil() {
        mMmkv = MMKV.defaultMMKV();
    }

    public static MMKVUtil getInstance() {
        if (sInstance == null) {
            synchronized (MMKVUtil.class) {
                if (sInstance == null) {
                    sInstance = new MMKVUtil();
                }
            }
        }
        return sInstance;
    }

    public void saveString(String key, String val) {
        mMmkv.encode(key, val);
    }

    public String getStr(String key, String defaultVal) {
        return mMmkv.decodeString(key, defaultVal);
    }

    public void saveBoolean(String key, boolean val) {
        mMmkv.encode(key, val);
    }

    public boolean getBoolean(String key, boolean defaultVal) {
        return mMmkv.decodeBool(key, defaultVal);
    }
}
