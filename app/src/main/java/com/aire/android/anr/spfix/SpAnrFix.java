package com.aire.android.anr.spfix;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.aire.android.anr.spfix.proxy.ConcurrentLinkedDequeProxy;
import com.aire.android.anr.spfix.proxy.LinkedListFinisherProxy;
import com.aire.android.anr.spfix.proxy.LinkedListWorkProxy;
import com.aire.android.util.MMKVUtil;

import java.lang.reflect.Field;
import java.util.LinkedList;

/**
 * Created by jack.qin on 2021/8/26.
 *
 * @author jack.qin
 * @email jack.qin@ximalaya.com
 */
public class SpAnrFix {
    public static final String TAG = "SpAnrFix";

    public static boolean fix(Context context) {
        boolean fixed = false;
        try {
            boolean isFixShareAnr = MMKVUtil.getInstance().getBoolean("fix_anr", true);
            if (!isFixShareAnr) {
                Log.i(TAG, "isFixShareAnr : false ");
                return fixed;
            }
            Log.i(TAG, "isFixShareAnr : true ");
            @SuppressLint("PrivateApi") Class clazz = Class.forName("android.app.QueuedWork");

            try {
                Field pendingWorkFinisherField = clazz.getDeclaredField("sPendingWorkFinishers");
                pendingWorkFinisherField.setAccessible(true);
                ConcurrentLinkedDequeProxy<Runnable> concurrentLinkedDequeProxy = new ConcurrentLinkedDequeProxy<Runnable>();
                pendingWorkFinisherField.set(null, concurrentLinkedDequeProxy);
                Log.i(TAG, "set pending work finisher proxy success");
                fixed = true;
            } catch (Exception e) {
                e.printStackTrace();
                Log.i(TAG, "set pending work finisher proxy error " + e);
            }

            try {
                Field finishersField = clazz.getDeclaredField("sFinishers");
                finishersField.setAccessible(true);
                LinkedListFinisherProxy<Runnable> linkedListFinisherProxy = new LinkedListFinisherProxy<Runnable>();
                finishersField.set(null, linkedListFinisherProxy);
                Log.i(TAG, "set finisher proxy success");
                fixed = true;
            } catch (Exception e) {
                e.printStackTrace();
                Log.i(TAG, "set finisher proxy error " + e);
            }

            try {
                Field workField = clazz.getDeclaredField("sWork");
                workField.setAccessible(true);

                Field lockField = clazz.getDeclaredField("sLock");
                lockField.setAccessible(true);
                Object lockObject = lockField.get(null);
                Log.i(TAG, "lockObject " + lockObject.toString());
                synchronized (lockObject) {
                    LinkedList<Runnable> originWorkList = (LinkedList<Runnable>) workField.get(null);
                    LinkedListWorkProxy<Runnable> linkedListWorkProxy = new LinkedListWorkProxy<Runnable>();
                    Log.i(TAG, "originWorkList size " + originWorkList.size());
                    linkedListWorkProxy.addAll(originWorkList);
                    workField.set(null, linkedListWorkProxy);
                }
                Log.i(TAG, "set work proxy success");
                fixed = true;
            } catch (Exception e) {
                e.printStackTrace();
                Log.i(TAG, "set work proxy error " + e);
            }

        } catch (Exception e) {
            Log.i(TAG, "set proxy error " + e);
            e.printStackTrace();
        }
        return fixed;
    }
}
