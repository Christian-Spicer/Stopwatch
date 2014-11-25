package com.chrusty.StopWatch;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SecondMainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.second_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_watch) {
			super.onBackPressed();
	        return true;
		}
		if (id == R.id.Laps) {
			Intent intent = new Intent(this, ThirdMainActivity.class);
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
}
