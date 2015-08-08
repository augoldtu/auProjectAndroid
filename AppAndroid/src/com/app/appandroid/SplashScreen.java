package com.app.appandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {


		 private boolean backbtnPress;

		 private static final int SPLASH_DURATION = 3000; //1000 = 1นาที

		 private Handler myHandler;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);

		 myHandler = new Handler(); 
		 myHandler.postDelayed(new Runnable() {

		  

	  @Override
		  public void run() {
		   finish();
		   if(!backbtnPress)
		   {
               Intent intent = new Intent(SplashScreen.this, TabSample.class);
               SplashScreen.this.startActivity(intent);
           }


   }

}, SPLASH_DURATION);

}

		 @Override

		 public void onBackPressed() {
		  backbtnPress = true;
		  super.onBackPressed();
	
	}
}
