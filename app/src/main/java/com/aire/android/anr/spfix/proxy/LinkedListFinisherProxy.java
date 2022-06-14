package com.aire.android.anr.spfix.proxy;

import android.util.Log;

import androidx.annotation.Nullable;

import com.aire.android.anr.spfix.SpAnrFix;

import java.util.LinkedList;

/**
 * Created by jack.qin on 2021/9/12.
 *
 * @author jack.qin
 * @email jack.qin@ximalaya.com
 */
public class LinkedListFinisherProxy<E> extends LinkedList<E> {
    @Override
    public boolean add(E e) {
        Log.i(SpAnrFix.TAG, "finisher add");
        return super.add(e);
    }

    @Nullable
    @Override
    public E poll() {
        Log.i(SpAnrFix.TAG, "finisher poll return null");
        return null;
    }

    @Override
    public boolean remove(@Nullable Object o) {
        Log.i(SpAnrFix.TAG, "finisher remove");
        return super.remove(o);
    }

    @Override
    public boolean isEmpty() {
        return true;
    }
}
