package com.m4.purdue.mhacks.findthataddress;
import android.content.Context;



import android.app.*;



public class AppContext extends Application{
	/** Check if this device has a camera */
	public static Context context;
	//protocols for creating context
	public void onCreate(){
		super.onCreate();
		AppContext.context = getApplicationContext();
	}
	
	public static Context getAppContext(){
		return AppContext.context;
	}
	/*private boolean checkCameraHardware(Context context) {
	    if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
	        // this device has a camera
	        return true;
	    } else {
	        // no camera on this device
	        return false;
	    }
	}*/

}