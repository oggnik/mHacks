package com.m4.purdue.mhacks.findthataddress;
import java.io.*;
import android.app.Activity;
import android.net.Uri;
import android.content.Context;
import android.util.Log;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.app.*;
import android.util.Log;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.View;
import android.view.SurfaceView;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.graphics.Bitmap;
import android.widget.Button;
import android.widget.ImageView;
import java.sql.Date;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import android.view.*;
public class ActiveCamera extends Activity implements OnClickListener {
	public final static String EXTRA_MESSAGE = "com.m4.purdue.mhacks.findthataddress.MESSAGE";
	public final static String PATH = "com.m4.purdue.mhacks.findthataddress.PATH";
	 private SurfaceHolder SurfaceHolder1 = null;
	 private ImageView takepicture;
	 private Camera mCamera;
	 private  CameraPreview mCameraPreview;
	 Button button1;
	
	public boolean checkCameraHardware(Context context) {
    if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
        // this device has a camera
        return true;
    } else {
        // no camera on this device
        return false;
    }
   }
	 public void onClick(View view) {
	        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	        File file1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES,"MyCameraApp"));
			 if (!file1.exists()){
			        if (! file1.mkdirs()){
			            Log.d("MyCameraApp", "failed to create directory");
			        }
			 }
			   String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date(0));
		       File imageFile = new File(file1.getPath() + File.separator +
		           "IMG_"+ timeStamp + ".jpg");
		       Uri photoPath = Uri.fromFile(imageFile);
		       intent.putExtra(MediaStore.EXTRA_OUTPUT, photoPath);
		       startActivityForResult(intent, 1);
	    }
 

	   //Override
	   /* public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }
	    */
	    //Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);
	        button1 = (Button)findViewById(R.id.button1);
	        takepicture = (ImageView) findViewById(R.id.takepicture);
	        button1.setOnClickListener(this);
	        mCamera = getCameraInstance();
	        mCameraPreview = new Preview(this, mCamera);         
	        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
	        preview.addView(mCameraPreview);
	        
	    }
	    /**
	     * Called when the user hits the send button
	     * @param view
	     */
	    public void sendMessage(View view) {
	    	//Create an intent
	    	Intent intent = new Intent(this, OCRActivity.class);
	    	
	    	Log.d("MainActivity", "Calling OCRActivity");
	    	intent.putExtra(PATH, "/mnt/sdcard/DCIM/100LGDSC/CAM00024.jpg");
	    	startActivity(intent);
	    }

			  

}
