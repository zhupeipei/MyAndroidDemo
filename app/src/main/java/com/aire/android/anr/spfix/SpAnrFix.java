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
import java.lang.reflect.Method;
import java.util.LinkedList;

/**
 * Created by jack.qin on 2021/8/26.
 *
 * @author jack.qin
 * @email jack.qin@ximalaya.com
 */
public class SpAnrFix {
    public static final String TAG = "SpAnrFix";

    private static Object sProcessingWork = new Object();

    private static boolean sWorkerHooked = false;
    private static HookedQueuedWorkHandler sHandler;

    public static void mockAnr() {
        Log.i(TAG, "mockAnr: sendMessage");
        sHandler.sendEmptyMessage(2);
    }

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
            } catch (Throwable e) {
                e.printStackTrace();
                Log.i(TAG, "set pending work finisher proxy error " + e);
            }

            try {
                Field finishersField = clazz.getDeclaredField("sFinishers");
                finishersField.setAccessible(true);
                LinkedListFinisherProxy<Runnable> linkedListFinisherProxy = new LinkedListFinisherProxy<Runnable>();
                finishersField.set(null, linkedListFinisherProxy);
                Log.i(TAG, "set finisher proxy success");
            } catch (Throwable e) {
                e.printStackTrace();
                Log.i(TAG, "set finisher proxy error " + e);
            }

            try {
                Field workField = clazz.getDeclaredField("sWork");
                workField.setAccessible(true);

                Field field = clazz.getDeclaredField("sProcessingWork");
                field.setAccessible(true);
                sProcessingWork = field.get(null);

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

                    hookQueuedWork(linkedListWorkProxy);

                    sWorkerHooked = true;
                    Log.i(TAG, "fix swork: " + sWorkerHooked);
                }
                Log.i(TAG, "set work proxy success");
            } catch (Throwable e) {
                e.printStackTrace();
                Log.i(TAG, "set work proxy error " + e);
            }

        } catch (Throwable e) {
            Log.i(TAG, "set proxy error " + e);
            e.printStackTrace();
        }
    }

    private static void hookQueuedWork(LinkedListWorkProxy<Runnable> listWorkProxy) throws Throwable {
        @SuppressLint("PrivateApi") Class clazz = Class.forName("android.app.QueuedWork");
        // getHandler 方法调用
        final Method method = clazz.getDeclaredMethod("getHandler", null);
        method.setAccessible(true);
        method.invoke(null, new Object[0]);
        // sHandler
        @SuppressLint("SoonBlockedPrivateApi") Field handlerField = clazz.getDeclaredField("sHandler");
        handlerField.setAccessible(true);
        Handler handler = (Handler) handlerField.get(null);
        if (handler == null) {
            throw new RuntimeException("handler null");
        }
        sHandler = new HookedQueuedWorkHandler(handler.getLooper(), listWorkProxy);
        FieldUtils.writeStaticField(clazz, "sHandler", sHandler);
    }

    private static class HookedQueuedWorkHandler extends Handler {
        static final int MSG_RUN = 1;
        static final int MSG_ANR_MOCK = 2;

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
            } else if (msg.what == MSG_ANR_MOCK) {
                Log.i(TAG, "mockAnr: sleep");
                synchronized (sProcessingWork) {
                    try {
                        Thread.sleep(60000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.i(TAG, "mockAnr: sleep end >>>");
            }
        }
    }
}
