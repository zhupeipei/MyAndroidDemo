package com.aire.android.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.aire.android.anr.spfix.SpAnrFix
import com.aire.android.test.R
import java.util.concurrent.CountDownLatch

class ActivityLifeActivity : AppCompatActivity() {
    companion object {
        const val TAG = "lifecycle_activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life_cycle)
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: ")

        SpAnrFix.mockAnr()

//        val c = CountDownLatch(1)
//        val sharedPreferences = this.getSharedPreferences("ting_data", Context.MODE_PRIVATE)
//        Thread {
//            kotlin.run {
//                c.countDown()
//
//                val value = "hello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_worldhello_world";
//                for (index in 0 until 1000) {
//                    val data = value + System.currentTimeMillis();
//
//                    sharedPreferences.edit()
//                        .putString("key${System.currentTimeMillis()}", data)
//                        .apply()
//                }
//
//            }
//        }.start()
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop: ")
    }
}