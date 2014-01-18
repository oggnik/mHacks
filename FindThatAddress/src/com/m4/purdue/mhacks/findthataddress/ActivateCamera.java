import android.content.Context;
import android.content.pm.PackageManager;
package com.m4.purdue.mhacks.findthataddress;
import android.hardware.Camera;

import android.hardware.Camera.Parameters;


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
}