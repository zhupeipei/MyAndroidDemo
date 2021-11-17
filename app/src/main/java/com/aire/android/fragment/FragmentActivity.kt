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
        Log.i(TAG, "FragmentActivity onCreate: 1")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_test)
        Log.i(TAG, "FragmentActivity onCreate: 2")
        addFragment()

        mRootContainer.postDelayed(Runnable {
//            addFragment()
//            replaceFragment()
        }, 1000)
        Log.i(TAG, "FragmentActivity onCreate: 3")
    }

    override fun onStart() {
        Log.i(TAG, "FragmentActivity onStart: ")
        super.onStart()
//        Log.i(TAG, "onStart " + Log.getStackTraceString(Throwable()))
    }

    override fun onResume() {
        Log.i(TAG, "FragmentActivity onResume: ")
        super.onResume()
//        Log.i(TAG, "onResume " + Log.getStackTraceString(Throwable()))
    }

    private fun addFragment() {
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.add(mRootContainerId, TestFragment())
        transaction.commitAllowingStateLoss()
        // commitNow 不支持回退栈
    }

    private fun replaceFragment() {
        val fm = supportFragmentManager
        val tranaction = fm.beginTransaction()
        tranaction.replace(mRootContainerId, ChildFragment("child"))
        tranaction.commitAllowingStateLoss()
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