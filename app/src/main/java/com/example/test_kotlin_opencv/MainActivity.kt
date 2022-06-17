package com.example.test_kotlin_opencv

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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
import org.opencv.android.LoaderCallbackInterface.SUCCESS
import org.opencv.android.OpenCVLoader
import org.opencv.android.OpenCVLoader.OPENCV_VERSION_3_0_0
import org.opencv.core.*
import org.opencv.core.CvType.CV_8U
import org.opencv.imgproc.Imgproc.*
import androidx.activity.compose.setContent
import java.util.ArrayList

class MainActivity : ComponentActivity(), CvCameraViewListener2 {
    private var openCvCameraView: CameraBridgeViewBase? = null
    private lateinit var intermediateMat: Mat

    private val loaderCallback: BaseLoaderCallback = object : BaseLoaderCallback(this) {
        override fun onManagerConnected(status: Int) {
            if (status == SUCCESS) {
                Log.i(TAG, "OpenCV loaded successfully")
                openCvCameraView?.enableView()
            } else {
                super.onManagerConnected(status)
            }
        }
    }

    val cameraViewList: List<CameraBridgeViewBase?>
        get() {
            return listOf(openCvCameraView)
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

        setContentView(R.layout.activity_main)

        openCvCameraView = findViewById(R.id.image_manipulations_activity_surface_view)

        openCvCameraView?.let {
            it.visibility = CameraBridgeViewBase.VISIBLE
            it.setCvCameraViewListener(this)
        }
//        setContent {
//            AndroidView(
//                factory = { context: Context ->
//                    val view = LayoutInflater.from(context)
//                        .inflate(R.layout.activity_main, null, false)
//                    openCvCameraView =
//                        view.findViewById(R.id.image_manipulations_activity_surface_view)
//                    openCvCameraView?.let {
//                        it.visibility = CameraBridgeViewBase.VISIBLE
//                        it.setCvCameraViewListener(this)
//                    }
//                    // do whatever you want...
//                    view // return the view
//                },
//                update = { view ->
//                    // Update the view
//                }
//            )
//        }
    }


    public override fun onPause() {
        super.onPause()
        openCvCameraView?.disableView()
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
        openCvCameraView?.disableView()
    }

    override fun onCameraViewStarted(width: Int, height: Int) {
    }

    override fun onCameraViewStopped() {
        // Explicitly deallocate Mats
        intermediateMat.release()
    }

    override fun onCameraFrame(inputFrame: CvCameraViewFrame): Mat {
        val rgba = inputFrame.gray()
        println("kemo")
        return rgba
    }
//    protected open val cameraViewList: List<CameraBridgeViewBase?>
//        get() {
//            return ArrayList<CameraBridgeViewBase?>()
//        }

    private fun onCameraPermissionGranted() {
        val cameraViews = cameraViewList
        if (cameraViews != null) {
            for (cameraBridgeViewBase in cameraViews) {
                cameraBridgeViewBase?.setCameraPermissionGranted()
            }
        }
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


}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TestkotlinopencvTheme {
        Greeting("Android")
    }
}