package com.aire.android.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aire.android.main.BaseActivity;
import com.aire.android.test.R;

public class DialogActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
    }

    public void createDialog(View view) {
        Dialog dialog = new MyDialog(DialogActivity.this);
        dialog.setContentView(R.layout.dialog_dialog);

        TextView tv = new TextView(DialogActivity.this);
//        tv.setLayoutParams();
        int padding = (int) (20 * 2.75);
        tv.setPadding(padding, padding, padding, padding);
        tv.setText("奥斯卡开始看大师课打卡拉上来达拉斯");
        tv.setTextSize(3000);
        tv.setTextColor(Color.RED);

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Log.i(MyDialog.TAG, "onDismiss: ");
            }
        });

        dialog.show();

        dialog.addContentView(tv, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }
}