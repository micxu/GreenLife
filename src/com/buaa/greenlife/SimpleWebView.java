package com.buaa.greenlife;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.webkit.WebView;

public class SimpleWebView extends Activity {
	
	private WebView myWebView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_web_view);
		
		myWebView = (WebView)findViewById(R.id.Webview);
		
		
		 Intent inte=getIntent();     
	     String myname=inte.getStringExtra("url"); 
	     
	     
		myWebView.loadUrl(myname.toString());  
		
		
	}
		
 
		 
		
		 
		

}
