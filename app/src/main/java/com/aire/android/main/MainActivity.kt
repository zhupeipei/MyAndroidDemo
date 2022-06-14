package com.aire.android.main

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.ArrayMap
import com.aire.android.aidl.PhoneInfoActivity
import com.aire.android.aidlconn.AidlConnActivity
import com.aire.android.annotation.AnnotationActivity
import com.aire.android.aspectJ.AspectJTestActivity
import com.aire.android.blockcanary.BlockCanaryActivity
import com.aire.android.coroutines.CoroutinesActivity
import com.aire.android.customview.CustomViewActivity
import com.aire.android.dialog.DialogActivity
import com.aire.android.eventbus.EventBusActivity
import com.aire.android.fragment.FragmentActivity
import com.aire.android.image.ImageViewActivity
import com.aire.android.layoutinflater.LayoutInflaterActivity
import com.aire.android.lifecycle.LifecycleActivity
import com.aire.android.okhttp.RequestActivity
import com.aire.android.recyclerview.RecyclerViewActivity
import com.aire.android.test.R
import com.aire.android.test.service.ServiceBindTestActivity
import com.aire.android.test.service.ServiceTestActivity
import com.aire.android.textureview.TextureViewActivity
import com.aire.android.webview.WebviewActivity

class MainActivity : AppCompatActivity {
    constructor() : super() {
        val aaa = 0
    }

    private val mHandler by lazy(LazyThreadSafetyMode.NONE) { Handler(Looper.getMainLooper()) }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i("abc", "onCreate: ")

        var cl = MainActivity::class.java.classLoader
        while (cl != null) {
            Log.i("abc", "onCreate: " + cl)
            cl = cl.parent
        }

        val arraymap = ArrayMap<String, String>()
        arraymap["haha"] = "ksks"
        arraymap.put("as", "zxc")

//        WebViewUtil.createWebView(this, mHandler)
    }

    override fun onStart() {
        Log.i("abc", "onStart1: " + this)
        super.onStart()
        Log.i("abc", "onStart2: " + this)
    }

    override fun onPause() {
        Log.i("abc", "onPause1: " + this)
        super.onPause()
        Log.i("abc", "onPause2: " + this + ", " + isChangingConfigurations)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.i("abc", "onRestoreInstanceState: " + this)
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.i("abc", "onSaveInstanceState: " + this)
        super.onSaveInstanceState(outState)
    }

    override fun onResume() {
        Log.i("abc", "onResume1: " + this)
        super.onResume()
        Log.i("abc", "onResume2: " + this)
        Toast.makeText(
            this,
            "abs${findViewById<View>(R.id.texttureview_main).width}",
            Toast.LENGTH_LONG
        ).show()

        val rootView = findViewById<LinearLayout>(R.id.main_root_view_ll)

        rootView.postDelayed(Runnable {
            val rootView1 = findViewById<LinearLayout>(R.id.main_root_view_ll)
            val tv = TextView(this)
            tv.text = "nihao zhongguo"
            rootView1.addView(tv)
        }, 5000)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        Log.i("abc", "onConfigurationChanged1: ")
        super.onConfigurationChanged(newConfig)
        Log.i("abc", "onConfigurationChanged2: ")
    }

    override fun onStop() {
        Log.i("abc", "onStop1: " + this)
        super.onStop()
        Log.i("abc", "onStop2: " + this + ", " + this.isChangingConfigurations)
    }

    override fun onDestroy() {
        Log.i("abd", "onDestroy1: " + this)
        super.onDestroy()
        Log.i("abd", "onDestroy2: " + this)
    }

    fun textureViewClick(view: View) {
        startActivity(Intent(this, TextureViewActivity::class.java))
    }

    fun telphoneImeiHook(view: View) {
        startActivity(Intent(this, PhoneInfoActivity::class.java))
    }

    fun createService(view: View) {
        startActivity(Intent(this, ServiceTestActivity::class.java))
    }

    fun bindService(view: View) {
        startActivity(Intent(this, ServiceBindTestActivity::class.java))
    }

    fun requestTest(view: View) {
        startActivity(Intent(this, RequestActivity::class.java))
    }

    fun webviewCrashFix(view: View) {
        startActivity(Intent(this, WebviewActivity::class.java))
    }

    fun lifecycleActivity(view: View) {
        startActivity(Intent(this, ActivityLifeActivity::class.java))
    }

    fun startFragment(view: View) {
        startActivity(Intent(this, FragmentActivity::class.java))
    }

    fun viewIntercept(view: View) {
        startActivity(Intent(this, CustomViewActivity::class.java))
    }

    fun createDialog(view: View) {
        startActivity(Intent(this, DialogActivity::class.java))
    }

    fun showIv(view: View) {
        startActivity(Intent(this, ImageViewActivity::class.java))
    }

    fun showRv(view: View) {
        startActivity(Intent(this, RecyclerViewActivity::class.java))
    }

    fun annotationProcess(view: View) {
        startActivity(Intent(this, AnnotationActivity::class.java))
    }

    fun aidlTest(view: View) {
        startActivity(Intent(this, AidlConnActivity::class.java))
    }

    fun blockcanaryClick(view: View) {
        startActivity(Intent(this, BlockCanaryActivity::class.java))
    }

    fun eventbusClick(view: View) {
        startActivity(Intent(this, EventBusActivity::class.java))
    }

    fun lifecycleClick(view: View) {
        startActivity(Intent(this, LifecycleActivity::class.java))
    }

    fun aspectJClick(view: View) {
        startActivity(Intent(this, AspectJTestActivity::class.java))
    }

    fun layoutInflaterClick(view: View) {
        startActivity(Intent(this, LayoutInflaterActivity::class.java))
    }

    fun coroutinesClick(view: View) {
        startActivity(Intent(this, CoroutinesActivity::class.java))
    }

}