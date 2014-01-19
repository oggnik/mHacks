package com.m4.purdue.mhacks.findthataddress;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.m4.purdue.mhacks.findthataddress.MESSAGE";
	public final static String IMAGE_PATH = "com.m4.purdue.mhacks.findthataddress.IMAGE_PATH";
	public final static String PHOTO_TAKEN	= "com.m4.purdue.mhacks.findthataddress.PHOTO_TAKEN";
	public final static int LOAD_IMAGE = 1;
	public final static int CAPTURE_IMAGE = 2;
	
	//For the camera
	private Button take_button;
	private Button load_button;
	private ImageView _image;
	private TextView _field;
	private String _path;
	private boolean _taken;
	
	
	public boolean checkCameraHardware(Context context) {
	    if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
	        // this device has a camera
	        return true;
	    } else {
	        // no camera on this device
	        return false;
	    }
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity", "On create main activity");
        
        if (!checkCameraHardware(this.getBaseContext())) {
        	//If there is no camera, quit it
        	finish();
        }
        
        _image = (ImageView) findViewById(R.id.image);
        _field = (TextView) findViewById(R.id.field);
        load_button = (Button) findViewById(R.id.load_button);
        //load_button.setOnClickListener(new LoadButtonClickHandler());
        take_button = (Button) findViewById(R.id.take_button);
        //take_button.setOnClickListener(new TakeButtonClickHandler());
        
        _path = Environment.getExternalStorageDirectory() + "/images/findThatAddress.jpg";
    }

/*    public class TakeButtonClickHandler implements View.OnClickListener {
    	public void onClick( View view ){
    		Log.d("MainActivity", "TakeButtonClickHandler.onClick()" );
    		startCameraActivity();
    	}
    }
    public class LoadButtonClickHandler implements View.OnClickListener {
    	public void onClick( View view ){
    		Log.d("MainActivity", "LoadButtonClickHandler.onClick()" );
    		loadImage();
    	}
    }*/
    
    public void startCameraActivity(View view) {
    	Log.d("MainActivity", "startCameraActivity()" );
    	File file = new File( _path );
    	Uri outputFileUri = Uri.fromFile( file );
    	
    	Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE );
    	intent.putExtra( MediaStore.EXTRA_OUTPUT, outputFileUri );
    	
    	startActivityForResult( intent, CAPTURE_IMAGE );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    

    public void loadImage(View view) {
    	Log.d("MainActivity", "loadImage");
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
    
    private void onPhotoTaken() {
    	Log.i( "MainActivity", "onPhotoTaken" );
    	
    	_taken = true;
    	
    	BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
    	
    	Bitmap bitmap = BitmapFactory.decodeFile( _path, options );
    	
    	_image.setImageBitmap(bitmap);
    	
    	_field.setVisibility( View.GONE );
    	
    	//Create an intent for the OCR
       	Intent intent = new Intent(this, OCRActivity.class);
       	Log.d("MainActivity", "Calling OCRActivity");
       	intent.putExtra(IMAGE_PATH, _path);
       	startActivity(intent);
    }
    
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	if (requestCode == CAPTURE_IMAGE) {
    		if (resultCode == -1) {
    			onPhotoTaken();
    		}
    		return;
    	}
    	
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
