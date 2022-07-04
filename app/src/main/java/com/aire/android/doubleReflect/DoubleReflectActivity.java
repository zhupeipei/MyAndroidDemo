package com.aire.android.doubleReflect;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.aire.android.test.R;

import java.lang.reflect.Method;

public class DoubleReflectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_reflect);

        // 反射
//        hook();

        // 双重反射
        doubleHook();

        // stackTest
        stackTestParent();
    }

    private void hook() {
        // 反射
        Class clazz = TestClazz.class;
        try {
            Method method = clazz.getDeclaredMethod("say");
            method.setAccessible(true);
            method.invoke(new TestClazz());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doubleHook() {
        try {
            Class classClazz = Class.class;
            Method declaredMethod = classClazz.getDeclaredMethod("getDeclaredMethod", String.class, Class[].class);
            declaredMethod.setAccessible(true);

            Class clazz = TestClazz.class;
            Method getInstanceMethod = (Method) declaredMethod.invoke(clazz, "getInstance", null);
            getInstanceMethod.setAccessible(true);

            Object instance = getInstanceMethod.invoke(clazz, null);

            Method method = (Method) declaredMethod.invoke(clazz, "say", null);
            method.setAccessible(true);
            method.invoke(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stackTestParent() {
        try {
            stackTest();
        } catch (Exception e) {
            try {
                Method mainMethod = TestClazz.class.getDeclaredMethod("startService");
                mainMethod.setAccessible(true);
                mainMethod.invoke(TestClazz.getInstance());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    // 通过throw 看下堆栈
    private void stackTest() throws Exception {
        try {
            int res = 1 / 0;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}