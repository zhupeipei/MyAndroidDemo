package com.aire.android.image;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.aire.android.main.MainApplication;
import com.aire.android.test.R;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class ImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        ImageView iv1 = findViewById(R.id.main_iv_1);
        Picasso.get()
//                .pauseTag("ahha")
                .load("http://ww3.sinaimg.cn/large/610dc034jw1fasakfvqe1j20u00mhgn2.jpg")
                .tag("ahhaha")
                .fit()
                .into(iv1);

        ImageView iv2 = findViewById(R.id.main_iv_2);
        Glide.with(MainApplication.INSTANCE)
                .load("http://ww3.sinaimg.cn/large/610dc034jw1fasakfvqe1j20u00mhgn2.jpg")
                .centerCrop()
                .into(iv2);

//        Glide.with(MainApplication.INSTANCE)
//                .load("http://ww3.sinaimg.cn/large/610dc034jw1fasakfvqe1j20u00mhgn2.jpg")
//                .into(iv1);
    }
}