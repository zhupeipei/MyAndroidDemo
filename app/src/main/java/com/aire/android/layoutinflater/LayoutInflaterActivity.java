package com.aire.android.layoutinflater;

import static org.greenrobot.eventbus.util.ErrorDialogManager.factory;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aire.android.main.MainApplication;
import com.aire.android.test.R;

import java.lang.reflect.Field;

public class LayoutInflaterActivity extends AppCompatActivity {
    private static final String TAG = "LayoutInflaterActivity";

    private static void setLayoutInflaterFac(Context context) {
        final LayoutInflater.Factory f1 = LayoutInflater.from(context).getFactory();
        final LayoutInflater.Factory2 f2 = LayoutInflater.from(context).getFactory2();

        LayoutInflater.Factory2 fNew = new LayoutInflater.Factory2() {
            @Nullable
            @Override
            public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
                View view = f2.onCreateView(parent, name, context, attrs);
                handleView(view);
                return view;
            }

            @Nullable
            @Override
            public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
                View view = f1.onCreateView(name, context, attrs);
                handleView(view);
                return view;
            }

            private void handleView(View view) {
                Log.i(TAG, "handleView: " + view);
            }
        };

        try {
            Field sLayoutInflaterFactory2Field = LayoutInflater.class.getDeclaredField("mFactory2");
            sLayoutInflaterFactory2Field.setAccessible(true);

            sLayoutInflaterFactory2Field.set(LayoutInflater.from(context), fNew);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLayoutInflaterFac(this);

        setContentView(R.layout.activity_layout_inflater);

        inflateViewStub();
    }

    private void inflateViewStub() {
        ViewStub viewStub = findViewById(R.id.layout_inflater_view_stub);
        viewStub.inflate();
    }
}