package com.app.appandroid;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class PageRestaurant extends Activity {

	
	Button mBtnHome;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_restaurant);

		mBtnHome = (Button) findViewById(R.id.mBtnHome);

		
		mBtnHome.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				
				Intent i = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(i);

			}

		});

		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		
		final ListView lstView1 = (ListView) findViewById(R.id.listView1);

		

		
		String url = "http://victorymonumentmap.com/App_Travel_Korat/json_restaurant.php";

		try {
			JSONArray data = new JSONArray(getJSONUrl(url));

			final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> map;

			for (int i = 0; i < data.length(); i++) {
				JSONObject c = data.getJSONObject(i);
				map = new HashMap<String, String>();
				map.put("ImageID", c.getString("shop_title"));
				map.put("ImageDesc", c.getString("shop_detail"));
				map.put("ImagePath", c.getString("shop_img_url"));// image
				map.put("url", c.getString("shop_email"));
				MyArrList.add(map);
			}

			lstView1.setAdapter(new ImageAdapter(this, MyArrList));

			final AlertDialog.Builder imageDialog = new AlertDialog.Builder(
					this);
			final LayoutInflater inflater = (LayoutInflater) this
					.getSystemService(LAYOUT_INFLATER_SERVICE);

			
			lstView1.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {

					View layout = inflater.inflate(
							R.layout.page_restaurant_fullimage_dialog,
							(ViewGroup) findViewById(R.id.layout_root));
					ImageView image = (ImageView) layout
							.findViewById(R.id.fullimage);

					try {
						image.setImageBitmap(loadBitmap(MyArrList.get(position)
								.get("ImagePath")));
					} catch (Exception e) {
						
						image.setImageResource(android.R.drawable.ic_menu_report_image);
					}

					imageDialog.setIcon(android.R.drawable.btn_star_big_on);
					imageDialog.setTitle("View : "
							+ MyArrList.get(position).get("ImageDesc"));
					imageDialog.setView(layout);

					imageDialog.setPositiveButton(android.R.string.ok,
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();

								}

							});

					imageDialog.create();
					imageDialog.show();

				}
			});

		} catch (JSONException e) {
			
			e.printStackTrace();
		}

	}

	public class ImageAdapter extends BaseAdapter {
		private Context context;
		private ArrayList<HashMap<String, String>> MyArr = new ArrayList<HashMap<String, String>>();
		protected String fileName;

		public ImageAdapter(Context c, ArrayList<HashMap<String, String>> list) {
			
			context = c;
			MyArr = list;
		}

		public int getCount() {
			
			return MyArr.size();
		}

		public Object getItem(int position) {
			
			return position;
		}

		public long getItemId(int position) {
			
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			

			final LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final String imgID;
			final String imgDesc;
			final String imgPath;
			final String url;

			if (convertView == null) {
				convertView = inflater.inflate(R.layout.page_restaurant_column, null);
			}

			
			ImageView imageView = (ImageView) convertView
					.findViewById(R.id.ColImgPath);
			imageView.getLayoutParams().height = 100;
			imageView.getLayoutParams().width = 100;
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imgPath = MyArr.get(position).get("ImagePath");
			try {
				imageView.setImageBitmap(loadBitmap(imgPath));
			} catch (Exception e) {
				
				imageView
						.setImageResource(android.R.drawable.ic_menu_report_image);
			}

			
			TextView txtPosition = (TextView) convertView
					.findViewById(R.id.ColImgID);
			txtPosition.setPadding(10, 0, 0, 0);
			imgID = MyArr.get(position).get("ImageID");
			txtPosition.setText(": " + imgID);
			
			TextView txtPicName = (TextView) convertView
					.findViewById(R.id.ColImgDesc);
			txtPicName.setPadding(50, 0, 0, 0);
			imgDesc = MyArr.get(position).get("ImageDesc");
			txtPicName.setText(": " + imgDesc);
			url = MyArr.get(position).get("url");
			final Button submitBtn = (Button) convertView
					.findViewById(R.id.btnSubmit);
			submitBtn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {

					
					Intent intent = new Intent(inflater.getContext(),
							PageRestaurant_Detail.class);
					intent.putExtra("imgID", imgID);
					intent.putExtra("imgDesc", imgDesc);
					intent.putExtra("imgPath", imgPath);
					intent.putExtra("url", url);
					
					startActivity(intent);
				}
			});
			return convertView;

		}

	}

	
	public String getJSONUrl(String url) {
		StringBuilder str = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) { 
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					str.append(line);
				}
			} else {
				Log.e("Log", "Failed to download file..");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str.toString();
	}

	
	private static final String TAG = "ERROR";
	private static final int IO_BUFFER_SIZE = 4 * 1024;

	public static Bitmap loadBitmap(String url) {
		Bitmap bitmap = null;
		InputStream in = null;
		BufferedOutputStream out = null;

		try {
			in = new BufferedInputStream(new URL(url).openStream(),
					IO_BUFFER_SIZE);

			final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
			out = new BufferedOutputStream(dataStream, IO_BUFFER_SIZE);
			copy(in, out);
			out.flush();

			final byte[] data = dataStream.toByteArray();
			BitmapFactory.Options options = new BitmapFactory.Options();
			

			bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,
					options);
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

	private static void copy(InputStream in, OutputStream out)
			throws IOException {
		byte[] b = new byte[IO_BUFFER_SIZE];
		int read;
		while ((read = in.read(b)) != -1) {
			out.write(b, 0, read);
		}
	}
	

}
