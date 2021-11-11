package com.aire.android.fragment

import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.aire.android.test.R

class FragmentActivity : AppCompatActivity() {
    companion object {
        const val TAG = "FragmentActivity"
    }

    private val mRootContainerId = R.id.main_fragment_container
    private val mRootContainer by lazy(LazyThreadSafetyMode.NONE) { findViewById<FrameLayout>(R.id.main_fragment_container) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_test)
        Log.i(TAG, "FragmentActivity onCreate: 1")
        addFragment()
        Log.i(TAG, "FragmentActivity onCreate: 2")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "FragmentActivity onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "FragmentActivity onResume: ")
    }

    private fun addFragment() {
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.replace(mRootContainerId, TestFragment())
        transaction.commitAllowingStateLoss()
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "FragmentActivity onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "FragmentActivity onDestroy: ")
    }
}