package com.m4.purdue.mhacks.findthataddress;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.m4.purdue.mhacks.findthataddress.MESSAGE";

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
    	//Toast.makeText(this, "You clicked the button", Toast.LENGTH_SHORT).show();
    	//Create an intent
    	Intent intent = new Intent(this, DisplayMessageActivity.class);
    	
    	//Get the text of the field and add it to the intent 
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	String message = editText.getText().toString();
    	intent.putExtra(EXTRA_MESSAGE, message);
    	startActivity(intent);
    }
    
}
