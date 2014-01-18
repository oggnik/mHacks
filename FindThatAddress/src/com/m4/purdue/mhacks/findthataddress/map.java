package com.m4.purdue.mhacks.findthataddress;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class map extends Activity{
	
	public void mapIt(String address){
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + address));
		startActivity(intent);
	}
}