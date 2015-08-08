package com.app.appandroid;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;


public class TabSample extends TabActivity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	
		setTabs() ;
	}
	private void setTabs()
	{
		
		addTab("หน้าหลัก", R.drawable.tab_home, PageHome.class);
        addTab("ท่องเที่ยว", R.drawable.tab_home3, PageTravel.class);
        addTab("โรงแรม", R.drawable.tab_home4, PageHotel.class);
		addTab("ร้านอาหาร", R.drawable.tab_home2, PageRestaurant.class);
		//addTab("พิกัดที่เที่ยว", R.drawable.tab_home5, PageMap.class);

	}
	
	private void addTab(String labelId, int drawableId, Class<?> c)
	{
		TabHost tabHost = getTabHost();
		Intent intent = new Intent(this, c);
		TabHost.TabSpec spec = tabHost.newTabSpec("tab" + labelId);	
		
		View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
		TextView title = (TextView) tabIndicator.findViewById(R.id.title);
		title.setText(labelId);
		ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
		icon.setImageResource(drawableId);
		
		spec.setIndicator(tabIndicator);
		spec.setContent(intent);
		tabHost.addTab(spec);
	}
}
