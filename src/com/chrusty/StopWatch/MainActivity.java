package com.chrusty.StopWatch;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity  implements OnTabChangeListener {
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
  	  	
        /* TabHost will have Tabs */
        final TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
    
    	/* TabSpec used to create a new tab. 
         * By using TabSpec only we can able to setContent to the tab.
         * By using TabSpec setIndicator() we can set name to tab. */
        
        /* tid1 is firstTabSpec Id. Its used to access outside. */
        TabSpec firstTabSpec = tabHost.newTabSpec("tid1");
        TabSpec secondTabSpec = tabHost.newTabSpec("tid2");
        TabSpec thirdTabSpec = tabHost.newTabSpec("tid3");

        Intent intent1 = new Intent(getApplicationContext(), FirstMainActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        firstTabSpec.setIndicator("WATCH");
        firstTabSpec.setContent(intent1);

        Intent intent2 = new Intent(getApplicationContext(), SecondMainActivity.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        secondTabSpec.setIndicator("SETTINGS");
        secondTabSpec.setContent(intent2);
        
        Intent intent3 = new Intent(getApplicationContext(), ThirdMainActivity.class);
        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        thirdTabSpec.setIndicator("LAPS");
        thirdTabSpec.setContent(intent3);
        
        /* Add tabSpec to the TabHost to display. */
        tabHost.addTab(firstTabSpec);
        tabHost.addTab(secondTabSpec);
        tabHost.addTab(thirdTabSpec);
        int index = 0;
		tabHost.setCurrentTab(index);
        
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String arg0) {
				setTabColor(tabHost);
				
			}
		});
		setTabColor(tabHost);		
	}
    public void setTabColor(TabHost tabhost) {
		
		for(int i=0;i<tabhost.getTabWidget().getChildCount();i++) {
			TextView tv = (TextView)  tabhost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //unselected
			tv.setTextColor(Color.parseColor("#ffffff"));
		}
	
	TextView tv = (TextView)  tabhost.getCurrentTabView().findViewById(android.R.id.title); //unselected
	tv.setTextColor(Color.parseColor("#00ffff"));
	}
	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub
		
	}
	@Override
    protected void onStart() {
     // TODO Auto-generated method stub
     super.onStart();
    }
}
