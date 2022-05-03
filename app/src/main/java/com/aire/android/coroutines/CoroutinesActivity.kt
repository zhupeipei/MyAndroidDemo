package com.aire.android.coroutines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aire.android.test.R

class CoroutinesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines)
    }

    override fun onResume() {
        super.onResume()
        CoroutinesMain.main(arrayOf())
    }
}