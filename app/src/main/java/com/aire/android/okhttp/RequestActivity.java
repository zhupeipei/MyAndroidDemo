package com.aire.android.okhttp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.aire.android.test.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final TextView tv = findViewById(R.id.main_request_tv);
        tv.setText("虎丘");

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient.Builder().build();

                client.newCall(new Request.Builder().url("https://www.baidu.com").build()).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        tv.post(new Runnable() {
                            @Override
                            public void run() {
                                tv.setText("request faile");
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        final String result = response.body().string();
                        tv.post(new Runnable() {
                            @Override
                            public void run() {
                                tv.setText(result);
                            }
                        });
                    }
                });
            }
        });
    }
}