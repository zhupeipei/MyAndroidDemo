package com.aire.android.lifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.aire.android.test.R;

public class LifecycleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);
        getLifecycle().addObserver(new LifecyclePresenter());
    }
}