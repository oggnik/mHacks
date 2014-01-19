package com.m4.purdue.mhacks.findthataddress;
/*import System;
import Google.GData.Client;
import Google.GData.Documents;
*/
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;

public class OCRActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ocr);
		// Show the Up button in the action bar.
		setupActionBar();
		
		/*
		 * Inspired greatly by
		 * http://gaut.am/making-an-ocr-android-app-using-tesseract/
		 */
		
		Log.d("OCRActivity", "OCRActivity onCreate");
		
		//Get the image
		Intent intent = getIntent();
		Log.d("OCRActivity", "Got the intent");
		String filePath = intent.getStringExtra(MainActivity.IMAGE_PATH);
		
		
		
		try {
			
			Bitmap bitmap = BitmapFactory.decodeFile(filePath);
			Log.d("OCRActivity", "decoded bitmap");
			ExifInterface exif = new ExifInterface(filePath);
			int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			
			//If the image is rotated, correct the rotation
			int rotate = 0;
			switch (exifOrientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					rotate = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					rotate = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					rotate = 270;
					break;
			}
			
			if (rotate != 0) {
				int width = bitmap.getWidth();
				int height = bitmap.getHeight();
				
				Matrix mtx = new Matrix();
				mtx.preRotate(rotate);
				
				//Rotating Bitmap & convert to ARGB_8888, required by tess
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, mtx, false);
			}
			bitmap = getResizedBitmap(bitmap, 350);
			Log.d("OCRActivity", "Bitmap width: " + bitmap.getWidth());
			bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
			
			Log.d("OCRActivity", "Bitmap rotated");
			
			final String DATA_PATH = Environment.getExternalStorageDirectory().toString() + "/FTA/";
			
			File dir = new File(DATA_PATH + "tessdata");
		    dir.mkdirs();

		    //If the traineddata doesn't exist yet, copy it from the assets
		    if (!(new File(DATA_PATH + "tessdata/eng.traineddata")).exists()) {
		        try {
		        	Log.d("OCRActivity ", "Copying Trained data");
		            AssetManager assetManager = getAssets();
		            InputStream in = assetManager.open("tessdata/eng.traineddata");
		            OutputStream out = new FileOutputStream(DATA_PATH
		                    + "tessdata/eng.traineddata");

		            byte[] buf = new byte[1024];
		            int len;
		            while ((len = in.read(buf)) > 0) {
		                out.write(buf, 0, len);
		            }
		            in.close();
		            out.close();
		        } catch (IOException e) {
		        	//Do nothing
		        }
		    }
			Log.d("OCRActivity ", "API calls");
		    
			//Do the OCR
			TessBaseAPI baseApi = new TessBaseAPI();
			// DATA_PATH = Path to the storage
			Log.d("OCRActivity ", "init");
			baseApi.init(DATA_PATH, "eng");
			Log.d("OCRActivity ", "setImage");
			baseApi.setImage(bitmap);
			//The text
			Log.d("OCRActivity ", "get text");
			String recognizedText = baseApi.getUTF8Text();
			Log.d("OCRActivity ", recognizedText);
			baseApi.end();
			
			Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?q=" + recognizedText));
			startActivity(mapIntent);
			Log.d("OCRActivity ", "Map Call");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.d("OCRActivity", "OCRActivity Catch Exception: " + e);
		}
		
	}
	
	private Bitmap getResizedBitmap(Bitmap image, int maxDimension) {
		int width = image.getWidth();
		int height = image.getHeight();
		//Ratio is > 0 if width is bigger than height
		double ratio = width / height;
		if (ratio > 0) {
			width = maxDimension;
			//Keep the aspect ratio
			height = (int) (width / ratio);
		} else {
			height = maxDimension;
			//Keep the aspect ratio
			width = (int) (height / ratio);
		}
		return Bitmap.createScaledBitmap(image, width, height, true);
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
