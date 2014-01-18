<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
package com.m4.purdue.mhacks.findthataddress;

import android.hardware.Camera;

import android.hardware.Camera.Parameters;
import android.content.Context;
import android.os.Bundle;
import android.app.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
/* 1. Open the app, the camera should be turned on. In this case, 
 * there are 2 choices: 1) take a photo via camera. 2) pick up a 
 * photo from the library
 * Caution: Remember to release the Camera object by calling the Camera.release() 
 * ***when your application is done using it! If your application does not properly 
 * release the camera, all subsequent attempts to access the camera, 
 * including those by your own application, will fail and may cause your 
 * or other applications to be shut down.
 * 
 * If the device doesn't have camera(/** Check if this device has a camera 
private boolean checkCameraHardware(Context context) {
    if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
        // this device has a camera
        return true;
    } else {
        // no camera on this device
        return false;
    }
})(interface	Camera.ErrorCallback (Callback interface for 
camera error notification.) , choose a photo from the files
 * 
 * 2.If choose 1), the following methods can be used: 
 class	Camera.Size	Image size (width and height dimensions). 
 **abstract void	 onPictureTaken(byte[] data, Camera camera)
Called when image data is available after a picture is taken.

abstract void	 onPictureTaken(byte[] data, Camera camera)
Called when image data is available after a picture is taken.

static Camera	 open()
Creates a new Camera object to access the first back-facing camera on the device.

final void	 reconnect()
Reconnects to the camera service after another process used it.

final void	 release()
Disconnects and releases the Camera object resources.

final void	 stopSmoothZoom()
Stops the smooth zoom.

final void	 takePicture(Camera.ShutterCallback shutter, Camera.PictureCallback raw, Camera.PictureCallback jpeg)
Equivalent to takePicture(shutter, raw, null, jpeg).

*/
public class ActivateCamera {
	/** Check if this device has a camera */
	private boolean checkCameraHardware(Context context) {
	    if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
	        // this device has a camera
	        return true;
	    } else {
	        // no camera on this device
	        return false;
	    }
	}
	
	public static void main(String[] args){
		Context context1 = new Context();
		if(context1.checkCameraHardware()){
			Camera camera1 = null;
			try{
				camera1 = Camera.open();
			}
				catch(Exception e){
				}
		}
	}
  
}
