//package com.example.test_kotlin_opencv
//
//import android.Manifest
//import android.annotation.TargetApi
//import android.content.pm.PackageManager
//import android.os.Build
//import android.os.Bundle
//import android.util.Log
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.annotation.RequiresApi
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Surface
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import com.example.test_kotlin_opencv.ui.theme.TestkotlinopencvTheme
//import org.opencv.android.BaseLoaderCallback
//import org.opencv.android.CameraBridgeViewBase
//import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame
//import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2
//import org.opencv.android.LoaderCallbackInterface
//import org.opencv.android.OpenCVLoader
//import org.opencv.core.*
//
//
//class MainActivity2 : ComponentActivity(), CvCameraViewListener2 {
//    private var openCvCameraView: CameraBridgeViewBase? = null
//    private lateinit var intermediateMat: Mat
//
//    private val loaderCallback: BaseLoaderCallback = object : BaseLoaderCallback(this) {
//        override fun onManagerConnected(status: Int) {
//            if (status == SUCCESS) {
//                Log.i(TAG, "OpenCV loaded successfully")
//                openCvCameraView?.enableView()
//            } else {
//                super.onManagerConnected(status)
//            }
//        }
//    }
//
//    override val cameraViewList: List<CameraBridgeViewBase?>
//        get() {
//            return listOf(openCvCameraView)
//        }
//
//    companion object {
//        private const val TAG = "OCVSample::Activity"
//    }
//
//    init {
//        Log.i(TAG, "Instantiated new " + this.javaClass)
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        initOpenCV()
//        onRequestPermissions()
//        setContentView(R.layout.activity_main)
//
//        openCvCameraView = findViewById(R.id.image_manipulations_activity_surface_view)
//
//        openCvCameraView?.let {
//            it.visibility = CameraBridgeViewBase.VISIBLE
//            it.setCvCameraViewListener(this)
//        }
//        setContent {
//            TestkotlinopencvTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//                    Greeting("Android")
//                }
//            }
//        }
//    }
//
//    public override fun onResume() {
//        super.onResume()
//        if (!OpenCVLoader.initDebug()) {
//            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization")
//            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, loaderCallback)
//        } else {
//            Log.d(TAG, "OpenCV library found inside package. Using it!")
//            loaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS)
//        }
//    }
//
//    public override fun onPause() {
//        super.onPause()
//        openCvCameraView?.disableView()
//    }
//
//    public override fun onDestroy() {
//        super.onDestroy()
//        openCvCameraView?.disableView()
//    }
//
//
//    override fun onCameraViewStarted(width: Int, height: Int) {
//        TODO("Not yet implemented")
//    }
//
//    override fun onCameraViewStopped() {
//        // Explicitly deallocate Mats
////        intermediateMat.release()
//    }
//
//    override fun onCameraFrame(inputFrame: CvCameraViewFrame?): Mat {
//        val img: Mat = inputFrame!!.gray();
//        return img;
//    }
//
//    private fun initOpenCV() {
//        if (!OpenCVLoader.initDebug())
//            Log.e("OpenCV", "Unable to load OpenCV!");
//        else
//            Log.d("OpenCV", "OpenCV loaded Successfully!");
//    }
//
//     fun onRequestPermissions() {
//        super.onStart()
//        var havePermission = true
//        if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission("android.permission.CAMERA") != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(arrayOf("android.permission.CAMERA"), 200)
//            havePermission = false
//        }
//        if (havePermission) {
////            onCameraPermissionGranted()
//        }
//    }
//
//    @TargetApi(23)
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        if (requestCode == 200 && grantResults.isNotEmpty() && grantResults[0] == 0) {
////            onCameraPermissionGranted()
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }
//
//}
//
////@Composable
////fun Greeting(name: String) {
////    Text(text = "Hello $name!")
////}
////
////@Preview(showBackground = true)
////@Composable
////fun DefaultPreview() {
////    TestkotlinopencvTheme {
////        Greeting("Android")
////    }
////}