package com.m4.purdue.mhacks.findthataddress;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;


public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.m4.purdue.mhacks.findthataddress.MESSAGE";
	public final static String PATH = "com.m4.purdue.mhacks.findthataddress.PATH";

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
    	//Create an intent
    	Intent intent = new Intent(this, OCRActivity.class);
    	
    	Log.d("MainActivity", "Calling OCRActivity");
    	intent.putExtra(PATH, "/mnt/sdcard/DCIM/100LGDSC/CAM00023.jpg");
    	startActivity(intent);
    }
    
   /* public void testContext(){
    	Context c1 = new Context();
    }*/
    
}
