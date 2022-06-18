//package com.example.test_kotlin_opencv;
//
//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.WindowManager;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import org.opencv.android.BaseLoaderCallback;
//import org.opencv.android.CameraBridgeViewBase;
//import org.opencv.android.JavaCameraView;
//import org.opencv.android.OpenCVLoader;
//import org.opencv.core.Core;
//import org.opencv.core.Mat;
//
//public class MainActivity extends AppCompatActivity implements
//        CameraBridgeViewBase.CvCameraViewListener2
//{
//    private static String TAG = "MainActivity";
//    JavaCameraView javaCameraView;
//    Mat mRGBA;
//    private static final int MY_CAMERA_REQUEST_CODE = 100;
//    int activeCamera = CameraBridgeViewBase.CAMERA_ID_BACK;
//
//
//    BaseLoaderCallback baseLoaderCallback = new BaseLoaderCallback(MainActivity.this) {
//        @Override
//        public void onManagerConnected(int status)
//        {
//            if (status == BaseLoaderCallback.SUCCESS) {
//                javaCameraView.enableView();
//            } else {
//                super.onManagerConnected(status);
//            }
//        }
//    };
//
//    static
//    {
//        if (OpenCVLoader.initDebug())
//        {
//            Log.d(TAG, "OpenCV is Configured or Connected successfully.");
//        }
//        else
//        {
//            Log.d(TAG, "OpenCV not Working or Loaded.");
//        }
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        javaCameraView = (JavaCameraView) findViewById(R.id.my_camera_view);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//                == PackageManager.PERMISSION_GRANTED) {
//            Log.d(TAG, "Permissions granted");
//            initializeCamera(javaCameraView, activeCamera);
//        } else {
//            Log.d(TAG, "Troubles");
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == MY_CAMERA_REQUEST_CODE) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Camera Permission granted", Toast.LENGTH_LONG).show();
//                initializeCamera(javaCameraView, activeCamera);
//            } else {
//                Toast.makeText(this, "Camera Permission denied", Toast.LENGTH_LONG).show();
//            }
//        }
//    }
//
//    private void initializeCamera(JavaCameraView javaCameraView, int activeCamera){
//        javaCameraView.setCameraPermissionGranted();
//        javaCameraView.setCameraIndex(activeCamera);
//
//        javaCameraView.setVisibility(CameraBridgeViewBase.VISIBLE);
//        javaCameraView.setCvCameraViewListener(this);
//    }
//
//    @Override
//    public void onCameraViewStarted(int width, int height)
//    {
//
//    }
//
//    @Override
//    public void onCameraViewStopped()
//    {
//        mRGBA.release();
//    }
//
//    @Override
//    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame)
//    {
//        // code for the back camera
//        mRGBA = inputFrame.rgba();
//        return mRGBA;
//    }
//
//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture) {
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//        if (javaCameraView != null)
//        {
//            javaCameraView.disableView();
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        if (javaCameraView != null)
//        {
//            javaCameraView.disableView();
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        if (OpenCVLoader.initDebug())
//        {
//            Log.d(TAG, "OpenCV is Configured or Connected successfully.");
//            baseLoaderCallback.onManagerConnected(BaseLoaderCallback.SUCCESS);
//        }
//        else
//        {
//            Log.d(TAG, "OpenCV not Working or Loaded.");
//            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, baseLoaderCallback);
//        }
//    }
//}