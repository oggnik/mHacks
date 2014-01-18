package com.m4.purdue.mhacks.findthataddress;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;


public class map extends Activity{
	
	public void mapIt(String address){
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + address));
		startActivity(intent);
	}
}