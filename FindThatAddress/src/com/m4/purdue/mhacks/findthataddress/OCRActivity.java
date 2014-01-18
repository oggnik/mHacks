package com.m4.purdue.mhacks.findthataddress;
/*import System;
import Google.GData.Client;
import Google.GData.Documents;
*/
import java.io.BufferedReader;
import java.io.IOException;

import org.apache.http.client.HttpClient;

import com.googlecode.tesseract.android.TessBaseAPI;

import android.media.ExifInterface;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;

public class OCRActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ocr);
		// Show the Up button in the action bar.
		setupActionBar();
		
		//Get the image
		Intent intent = getIntent();
		String filePath = intent.getStringExtra(MainActivity.PATH);
		//Just display the text for now
		Toast.makeText(this, filePath, Toast.LENGTH_SHORT).show();
		
		try {
			
			Bitmap bitmap = BitmapFactory.decodeFile(filePath);
			
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
			bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
			
			//Do the OCR
			
			TessBaseAPI baseApi = new TessBaseAPI();
			// DATA_PATH = Path to the storage
			// lang = for which the language data exists, usually "eng"
			baseApi.init("/mnt/sdcard/tesseract/tessdata/eng.traineddata", "eng");
			// Eg. baseApi.init("/mnt/sdcard/tesseract/tessdata/eng.traineddata", "eng");
			baseApi.setImage(bitmap);
			//The text
			String recognizedText = baseApi.getUTF8Text();
			baseApi.end();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		//Set up HTTP Client
		/*String imageTextUrl = file.getExportLinks().get(filepath);	
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(imageTextUrl);
		get.setHeader("Authorization"< "Bearer" + token);
		HttpResponse response = client.execute(get);
		
		//Read into buffer
		StringBuffer sb = new StringBUffer();
		
		BufferedReader in=null;
		try{
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String str;
			while((str = in.readLine()) !=null){
				sb.append(str);
			}
			
		}
		finally{
			if(in != null) {
				in.close();
			}
		}
	}

	//Send data to new Intent to display
	Intent intent = new Intent(UploadImageService.this, VerifyTextActivity.class);
	intent.putExtra("ocrText", sb.toString());
	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	startActivity(intent);
	*/
	
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
