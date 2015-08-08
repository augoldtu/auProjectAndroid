package com.app.appandroid;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;


import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;





import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class PageHotel_Detail extends Activity {


  TextView tvView;
  TextView tvView2;
  TextView tvView3;
  ImageView imageView;
  
  
  EditText txtUrl;
    
  ViewSwitcher Vs;
  
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.page_hotel_detail);
   
  tvView = ( TextView) findViewById(R.id.tvView);
  tvView2 = (TextView) findViewById(R.id.tvView2);
  tvView3 = (TextView) findViewById(R.id.tvView3);
  
  imageView = (ImageView)findViewById(R.id.ColImgPath2);
imageView.getLayoutParams().height = 200;
imageView.getLayoutParams().width = 200;
imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
   Intent intent = getIntent();
   
   String fName = intent.getStringExtra("imgID");
   String imgDesc = intent.getStringExtra("imgDesc");
   String imgPath = intent.getStringExtra("imgPath");
   String url = intent.getStringExtra("url");
  
   tvView.setText(fName);
   tvView2.setText(imgDesc);
        
         tvView3.setText(imgDesc);
         imageView.setImageBitmap(loadBitmap(imgPath));
  
    Vs = (ViewSwitcher) findViewById(R.id.viewSwitcher1);
	txtUrl = (EditText) findViewById(R.id.editText1);
	txtUrl.setText(url);
  
	ImageButton btn1 = (ImageButton) findViewById(R.id.imageButton2);
  btn1.setOnClickListener(new View.OnClickListener() {
      @SuppressLint("SetJavaScriptEnabled")
		public void onClick(View v) {
      	Vs.showNext();
         	
      	// webView1
  		WebView WebViw = (WebView) findViewById(R.id.webView1);
			WebViw.getSettings().setJavaScriptEnabled(true);
  		WebViw.loadUrl(txtUrl.getText().toString());
      }
  });
  
}
 
 private static final String TAG = "ERROR";
 private static final int IO_BUFFER_SIZE = 4 * 1024;
 public static Bitmap loadBitmap(String url) {
 Bitmap bitmap = null;
 InputStream in = null;
 BufferedOutputStream out = null;

 try {
     in = new BufferedInputStream(new URL(url).openStream(), IO_BUFFER_SIZE);

     final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
     out = new BufferedOutputStream(dataStream, IO_BUFFER_SIZE);
     copy(in, out);
     out.flush();

     final byte[] data = dataStream.toByteArray();
     BitmapFactory.Options options = new BitmapFactory.Options();

     bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,options);
 } catch (IOException e) {
     Log.e(TAG, "Could not load Bitmap from: " + url);
 } finally {
     closeStream(in);
     closeStream(out);
 }

 return bitmap;
 }

 private static void closeStream(Closeable stream) {
     if (stream != null) {
         try {
             stream.close();
         } catch (IOException e) {
             android.util.Log.e(TAG, "Could not close stream", e);
         }
     }
 }

 private static void copy(InputStream in, OutputStream out) throws IOException {
 byte[] b = new byte[IO_BUFFER_SIZE];
 int read;
 while ((read = in.read(b)) != -1) {
     out.write(b, 0, read);
 }
 }
 
}



