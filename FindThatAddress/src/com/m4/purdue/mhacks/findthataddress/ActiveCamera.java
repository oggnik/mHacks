/*package com.m4.purdue.mhacks.findthataddress;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.content.pm.PackageManager;
import android.app.*;
import java.io.*;
<uses-permission android:name="android.permission.CAMERA" />
<uses-feature android:name="android.hardware.camera" />
<uses-feature android:name="android.hardware.camera.autofocus" />
public class ActiveCamera {
	private boolean checkCameraHardware(Context context) {
    if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
        // this device has a camera
        return true;
    } else {
        // no camera on this device
        return false;
    }
   }
public void saveToFile(String message){
	File file = new File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES,"MyCameraApp");
	 if (!file.exists()){
	        if (! file.mkdirs()){
	            Log.d("MyCameraApp", "failed to create directory");
	            return null;
	        }
	 }
}
	public static void main(String[] args){
		if(checkCameraHardware(Context context)){
		  Camera camera = null;
		  try{
			camera = open();
		  }catch(Exception e){
		  }
		  Parameters parameter = camera.getParameters();
		  
		}
		
	}

}*/
