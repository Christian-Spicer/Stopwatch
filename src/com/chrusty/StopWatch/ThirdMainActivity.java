package com.chrusty.StopWatch;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ThirdMainActivity extends ListActivity {

	Intent intent;
	TextView Id;
	DBControllerAdaptor controller = new DBControllerAdaptor(this);
	ArrayList<HashMap<String, String>> List;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_third_main);
	    //String data = controller.getAllData();
	    //Message.message(this, data);
	    callList();
	}
	public void callList(){
		List = controller.getAll();
		ListAdapter adapter = new SimpleAdapter(this, List, R.layout.view_entry, new String[] { "id","laps","time","date"}, new int[] {R.id.id, R.id.laps, R.id.time, R.id.date}); 
		setListAdapter(adapter);
		if(List.size()!=-1) {
			ListView lv = getListView();
			lv.setOnItemClickListener(new OnItemClickListener() {
				  @Override 
				  public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
					  Id = (TextView) view.findViewById(R.id.id);
					  final String valId = Id.getText().toString();
						new AlertDialog.Builder(ThirdMainActivity.this)  
						.setMessage("Do You Want To \n\t Delete Lap?")
						.setCancelable(false)  
						.setPositiveButton("No", new DialogInterface.OnClickListener() {  
						    public void onClick(DialogInterface dialog, int which)   
						    {  
						          dialog.cancel();  
						    }  
						})    
						.setNegativeButton("Yes", new DialogInterface.OnClickListener() {  
						      public void onClick(DialogInterface dialog, int which)   
						      {  
						    	  remove(valId);
						      }  
						}).show();
					   
				  }
			}); 
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.third_main, menu);
		return true;
	}
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	int id = item.getItemId();
		if (id == R.id.action_watch) {
			super.onBackPressed();
		}
		if (id == R.id.Settings) {
			Intent intent = new Intent(this, SecondMainActivity.class);
	        startActivity(intent);
	        return true;
		}
		if (id == R.id.About) {
			new AlertDialog.Builder(this)
			.setTitle("About")
			.setMessage("This is my first android app.\nVersion 1.0.\nWriten By Christian Spicer.")
			.setNeutralButton("Ok", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
				
			}).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
    }
	public void remove(String valId) {
		
		controller.delete(valId);
		List.clear();
		callList();
	}
	@Override
	public void onResume(){
		super.onResume();
	}
	@Override
	public void onStart(){
		super.onStart();
	}
	@Override
	public void onDestroy(){
		super.onDestroy();
	}
}

