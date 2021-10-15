package com.aire.android.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.aire.android.aidl.PhoneInfoActivity
import com.aire.android.test.R
import com.aire.android.textureview.TextureViewActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun textureViewClick(view: View) {
        startActivity(Intent(this, TextureViewActivity::class.java))
    }

    fun telphoneImeiHook(view: View) {
        startActivity(Intent(this, PhoneInfoActivity::class.java))
    }
}