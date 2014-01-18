package com.m4.purdue.mhacks.findthataddress;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;


public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.m4.purdue.mhacks.findthataddress.MESSAGE";
	public final static String IMAGE_PATH = "com.m4.purdue.mhacks.findthataddress.IMAGE_PATH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /**
     * Called when the user hits the send button
     * @param view
     */
    public void sendMessage(View view) {
    	//Set up an intent to pick an image
    	int currentVersion = android.os.Build.VERSION.SDK_INT;
    	//If it is in kit kat, the api changed
    	if (currentVersion >= android.os.Build.VERSION_CODES.KITKAT) {
    		Intent photoPicker = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    		startActivityForResult(photoPicker, 1);
    	} else {
	    	Intent photoPicker = new Intent(Intent.ACTION_GET_CONTENT);
	    	photoPicker.setType("image/*");
	    	startActivityForResult(photoPicker, 1);
    	}
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	if (resultCode == RESULT_OK) {
    		Uri chosenImageUri = data.getData();
        	
	    	//Create an intent for the OCR
	       	Intent intent = new Intent(this, OCRActivity.class);
	       	Log.d("MainActivity", "Calling OCRActivity");
	       	intent.putExtra(IMAGE_PATH, getRealPathFromUri(chosenImageUri));
	       	startActivity(intent);
        	
    	}
    }
    
    private String getRealPathFromUri(Uri imageUri) {
    	Cursor cursor = getContentResolver().query(imageUri, null, null, null, null);
    	if (cursor == null) {
    		return imageUri.getPath();
    	}
    	cursor.moveToFirst();
    	int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
    	return cursor.getString(index);
    }
    
}
