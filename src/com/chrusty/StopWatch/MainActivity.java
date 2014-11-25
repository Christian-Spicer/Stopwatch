package com.chrusty.StopWatch;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	super.onOptionsItemSelected(item);
    		switch(item.getItemId()){
    		case R.id.About:
    			AboutMenuItem();
    			break;
    		case R.id.Settings:
    			SettingsMenuItem();
    			break;
       		case R.id.Laps:
    			LapsMenuItem();
    			break;
    		default:
    			super.onOptionsItemSelected(item);
    	}
    	return true;
    }
	private void LapsMenuItem() {
		Intent intent = new Intent(this, ThirdMainActivity.class);
        startActivity(intent);
	}
	private void SettingsMenuItem() {
		Intent intent = new Intent(this, SecondMainActivity.class);
        startActivity(intent);
	}
	private void AboutMenuItem() {
		new AlertDialog.Builder(this)
		.setTitle("About")
		.setMessage("This is my first android app.\nVersion 1.0.\nWriten By Christian Spicer.")
		.setNeutralButton("Ok", new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
			
		}).show();
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
     
    @Override
    protected void onStop() {
     // TODO Auto-generated method stub
     super.onStop();
    }
    protected void onPause() {
        // TODO Auto-generated method stub
        
        super.onPause();
    }
   
}
