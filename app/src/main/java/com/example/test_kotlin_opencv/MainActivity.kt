package com.example.test_kotlin_opencv

import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.example.test_kotlin_opencv.ui.theme.TestkotlinopencvTheme
import org.opencv.android.BaseLoaderCallback
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2
import org.opencv.android.JavaCameraView
import org.opencv.android.LoaderCallbackInterface.SUCCESS
import org.opencv.android.OpenCVLoader
import org.opencv.android.OpenCVLoader.OPENCV_VERSION_3_0_0
import org.opencv.core.Mat

class MainActivity : ComponentActivity(), CvCameraViewListener2 {
    var javaCameraView: JavaCameraView? = null
    var activeCamera = CameraBridgeViewBase.CAMERA_ID_BACK

    private val loaderCallback: BaseLoaderCallback = object : BaseLoaderCallback(this) {
        override fun onManagerConnected(status: Int) {
            if (status == SUCCESS) {
                Log.i(TAG, "OpenCV loaded successfully")
                javaCameraView?.enableView()
            } else {
                super.onManagerConnected(status)
            }
        }
    }

    companion object {
        private const val TAG = "OCVSample::Activity"
    }

    init {
        Log.i(TAG, "Instantiated new " + this.javaClass)
    }

    /** Called when the activity is first created.  */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContentView(R.layout.activity_main)
//        javaCameraView =
//            findViewById<View>(R.id.image_manipulations_activity_surface_view) as JavaCameraView
//        initializeCamera(javaCameraView!!, activeCamera)
        setContent {
            Greeting("kareem")
        }

    }


    public override fun onPause() {
        super.onPause()
        javaCameraView?.disableView()
    }

    public override fun onResume() {
        super.onResume()
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization")
            OpenCVLoader.initAsync(OPENCV_VERSION_3_0_0, this, loaderCallback)
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!")
            loaderCallback.onManagerConnected(SUCCESS)
        }
    }

    public override fun onDestroy() {
        super.onDestroy()
        javaCameraView?.disableView()

    }

    override fun onCameraViewStarted(width: Int, height: Int) {
    }

    override fun onCameraViewStopped() {
    }

    override fun onCameraFrame(inputFrame: CvCameraViewFrame): Mat {
        val rgba = inputFrame.gray()
        println("kemo")
        return rgba
    }


    private fun onCameraPermissionGranted() {
        javaCameraView?.setCameraPermissionGranted()
    }

    override fun onStart() {
        super.onStart()
        var havePermission = true
        if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission("android.permission.CAMERA") != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf("android.permission.CAMERA"), 200)
            havePermission = false
        }
        if (havePermission) {
            onCameraPermissionGranted()
        }

    }

    @TargetApi(23)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == 200 && grantResults.isNotEmpty() && grantResults[0] == 0) {
            onCameraPermissionGranted()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @Composable
    fun Greeting(name: String) {
        Column() {
            Text(text = "Hello $name!")
            AndroidView(
                factory = { context: Context ->
                    var view = LayoutInflater.from(context)
                        .inflate(R.layout.activity_main, null, false)
                    javaCameraView =
                        view.findViewById(R.id.image_manipulations_activity_surface_view)
                    initializeCamera(javaCameraView!!, activeCamera)

                    // do whatever you want...
                    view // return the view
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        TestkotlinopencvTheme {
            Greeting("Android")
        }
    }

    private fun initializeCamera(javaCameraView: JavaCameraView, activeCamera: Int) {
        javaCameraView.setCameraPermissionGranted()
        javaCameraView.setCameraIndex(activeCamera)
        javaCameraView.visibility = CameraBridgeViewBase.VISIBLE
        javaCameraView.setCvCameraViewListener(this)
    }


}

