package com.aire.android.textureview

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.SurfaceTexture
import android.hardware.Camera
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.TextureView
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import com.aire.android.test.R
import java.io.IOException

class TextureViewActivity : AppCompatActivity(), TextureView.SurfaceTextureListener {
    private val TAG = "TextureViewActivity"
    private val REQUEST_CODE_CAMERA = 2

    private var mCamera: Camera? = null
    private var mTextureView: TextureView? = null
    private val mRootView by lazy(LazyThreadSafetyMode.NONE) { findViewById<FrameLayout>(R.id.main_act_texture_view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate 1")

        setContentView(R.layout.activity_texture_view)

        requestCameraPermission()

        Log.d(TAG, "onCreate 2")
    }

    private fun requestCameraPermission() {
        val checkPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (checkPermission == PermissionChecker.PERMISSION_GRANTED) {
            addCameraView()
        } else if (checkPermission == PermissionChecker.PERMISSION_DENIED || checkPermission == PermissionChecker.PERMISSION_DENIED_APP_OP) {
            requestPermissions(
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CODE_CAMERA
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_CAMERA) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                addCameraView()
            }
        }
    }

    private fun addCameraView() {
        Log.d(TAG, "addCameraView 1")

        mTextureView = TextureView(this)
        mTextureView?.setSurfaceTextureListener(this)

        mRootView.addView(mTextureView)

        Log.d(TAG, "addCameraView 2")
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
        Log.d(TAG, "onSurfaceTextureAvailable $width $height")

        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg params: Void?): Void? {
                Log.d(TAG, "onSurfaceTextureAvailable 1 $width $height")

                mCamera = Camera.open()

                try {
                    mCamera?.setPreviewTexture(surface)
                    mCamera?.startPreview()
                } catch (ioe: IOException) {
                    // Something bad happened
                }

                return null
            }
        }.execute()
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
        Log.d(TAG, "onSurfaceTextureSizeChanged $width $height")
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
        Log.d(TAG, "onSurfaceTextureDestroyed")
        mCamera?.stopPreview()
        mCamera?.release()
        return true
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
        Log.d(TAG, "onSurfaceTextureUpdated")
    }
}