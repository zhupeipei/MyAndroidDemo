package com.aire.android.anr.spfix;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.aire.android.anr.spfix.proxy.ConcurrentLinkedDequeProxy;
import com.aire.android.anr.spfix.proxy.LinkedListFinisherProxy;
import com.aire.android.anr.spfix.proxy.LinkedListWorkProxy;
import com.aire.android.reflect.FieldUtils;
import com.aire.android.reflect.MethodUtils;
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

    private static boolean sWorkerHooked = false;

    public static void fix(Context context) {
        try {
            boolean isFixShareAnr = MMKVUtil.getInstance().getBoolean("fix_anr", true);
            if (!isFixShareAnr) {
                Log.i(TAG, "isFixShareAnr : false ");
                return;
            }
            Log.i(TAG, "isFixShareAnr : true ");
            @SuppressLint("PrivateApi") Class clazz = Class.forName("android.app.QueuedWork");

            try {
                Field pendingWorkFinisherField = clazz.getDeclaredField("sPendingWorkFinishers");
                pendingWorkFinisherField.setAccessible(true);
                ConcurrentLinkedDequeProxy<Runnable> concurrentLinkedDequeProxy = new ConcurrentLinkedDequeProxy<Runnable>();
                pendingWorkFinisherField.set(null, concurrentLinkedDequeProxy);
                Log.i(TAG, "set pending work finisher proxy success");
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

                    sWorkerHooked = true;
                    Log.i(TAG, "fix swork: " + sWorkerHooked);

                    hookQueuedWork(linkedListWorkProxy);
                }
                Log.i(TAG, "set work proxy success");
            } catch (Exception e) {
                e.printStackTrace();
                Log.i(TAG, "set work proxy error " + e);
            }

        } catch (Exception e) {
            Log.i(TAG, "set proxy error " + e);
            e.printStackTrace();
        }
    }

    private static void hookQueuedWork(LinkedListWorkProxy<Runnable> listWorkProxy) {
        try {
            @SuppressLint("PrivateApi") Class clazz = Class.forName("android.app.QueuedWork");
            MethodUtils.invokeStaticMethod(clazz, "getHandler");
            Handler handler = (Handler) FieldUtils.readStaticField(clazz, "sHandler");
            FieldUtils.writeStaticField(clazz, "sHandler", new HookedQueuedWorkHandler(handler.getLooper(), listWorkProxy));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class HookedQueuedWorkHandler extends Handler {
        static final int MSG_RUN = 1;

        static LinkedListWorkProxy<Runnable> sWork;

        HookedQueuedWorkHandler(Looper looper, LinkedListWorkProxy<Runnable> listWorkProxy) {
            super(looper);
            sWork = listWorkProxy;
        }

        public void handleMessage(Message msg) {
            if (msg.what == MSG_RUN) {
                LinkedList<Runnable> work = (LinkedList<Runnable>) sWork.clone();
                sWork.clear();

                removeMessages(HookedQueuedWorkHandler.MSG_RUN);

                if (work.size() > 0) {
                    for (Runnable w : work) {
                        w.run();
                    }
                }
            }
        }
    }
}
