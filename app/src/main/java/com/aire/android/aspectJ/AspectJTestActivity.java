package com.aire.android.aspectJ;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aire.android.test.R;

/**
 * @author ZhuPeipei
 * @date 2022/1/27 18:00
 */
public class AspectJTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aspect_jtest);
    }

    public void startWebViewForAspectJ(View view) {
        startActivity(new Intent(this, WebViewTestForAspectJInKotlinActivity.class));
    }

    public void startWebViewForAspectJJava(View view) {
        startActivity(new Intent(this, WebViewTestForAspectJInJavaActivity.class));
    }

}
