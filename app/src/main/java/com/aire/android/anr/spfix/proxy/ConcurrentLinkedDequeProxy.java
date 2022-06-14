package com.aire.android.anr.spfix.proxy;

import android.util.Log;

import com.aire.android.anr.spfix.SpAnrFix;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by jack.qin on 2021/9/12.
 *
 * @author jack.qin
 * @email jack.qin@ximalaya.com
 */
public class ConcurrentLinkedDequeProxy<E> extends ConcurrentLinkedQueue<E> {
    @Override
    public boolean add(E e) {
        return super.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return super.remove(o);
    }

    @Override
    public E poll() {
        Log.i(SpAnrFix.TAG, "sPendingWorkFinishers poll return  null");
        return null;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}
