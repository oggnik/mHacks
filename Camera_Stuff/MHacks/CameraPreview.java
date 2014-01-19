package com.m4.purdue.mhacks.findthataddress;
import android.app.Activity;
import android.content.Context;
import java.io.*;

import android.os.Bundle;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.SurfaceHolder.Callback;
import android.view.
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.hardware.*;
import android.hardware.Camera.PictureCallback;

import android.widget.TextView;
public class CameraPreview extends ViewGroup implements OnClickListener,SurfaceHolder.Callback{
    SurfaceView surface;
    SurfaceHolder holder;
    PictureCallback photo;
    Camera camera2;
    CameraPreview (Context context) {
    	super(context);    	
    	SurfaceView surface = new SurfaceView(context);
    	
    }

	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		
		
	}
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		camera2.startPreview();
		
	}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		camera2 = Camera.open();
		try {
			camera2.setPreviewDisplay(holder);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        camera2.startPreview();
		
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		camera2.stopPreview();
		camera2.release();	
		camera2 = null;
	}

	@Override
	public void onClick(View v) {
		camera2.takePicture(null, null, photo);
		
	}
    
	//override
	
}
