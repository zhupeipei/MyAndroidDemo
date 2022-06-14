package com.aire.android.anr.spfix.proxy;

import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.aire.android.anr.spfix.SpAnrFix;

import java.util.LinkedList;

/**
 * Created by jack.qin on 2021/9/13.
 *
 * @author jack.qin
 * @email jack.qin@ximalaya.com
 */
public class LinkedListWorkProxy<E> extends LinkedList<E> {
    @NonNull
    @Override
    public Object clone() {
        if(Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId()) {
            Log.i(SpAnrFix.TAG, "work clone return empty list");
            return new LinkedListWorkProxy<E>();
        }
        return super.clone();
    }

    @Override
    public void clear() {
        if(Thread.currentThread().getId() == Looper.getMainLooper().getThread().getId()) {
            Log.i(SpAnrFix.TAG, "work not clear ");
            return;
        }
        super.clear();
    }
}
