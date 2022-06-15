package com.aire.android.io;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.aire.android.test.R;
import com.aire.android.util.TraceUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;

public class IOActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ioactivity);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final Thread mockIoThread = new Thread(this::mockIo);
        mockIoThread.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("zimotag", mockIoThread.getState() + "\n" + TraceUtil.getTrace(mockIoThread));
            }
        }, 2000);
    }

    static byte[] bytes;
    static Object sLock = new Object();

    static {
        bytes = new byte[10240];
        Arrays.fill(bytes, 0, bytes.length, (byte) 1);
    }

    private void mockIo() {
        String filePath = IOActivity.this.getExternalCacheDir().getAbsolutePath();
        String fileName = "1.txt";
        while (true) {
            synchronized (sLock) {
                try {
                    writeToFile(new File(filePath, fileName));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void writeToFile(File file) throws Exception {
        if (file == null) {
            return;
        }
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        FileOutputStream os = new FileOutputStream(file);
        os.write(bytes, 0, bytes.length);
    }
}