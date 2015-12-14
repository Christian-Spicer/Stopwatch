package com.chrusty.StopWatch;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FirstMainActivity extends Activity {
	
	private boolean save = false;
	private boolean split = false;	
	private boolean reset = false;
	private DBControllerAdaptor controller;
	private Context context;
	private int lapcount;
    private String Laps = "Lap";
    private TextView timer1, timer2;
	Receiver Receiver;
	Button timerStartStopButton;
	Button timerResetButton;
	Button timerSplitButton;
	Button timerSavelapButton;
	String datapassed1;
	String datapassed2;
	TimeService mBoundService;
	boolean mServiceBound = false;
	AsyncService interFace;
    
	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_main);
	    timer1 = (TextView) findViewById(R.id.textTimer);
    	timer2 = (TextView) findViewById(R.id.textTimer1);
    	controller = new DBControllerAdaptor(this.getApplicationContext());
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String formattedDate = df.format(c.getTime());
        Receiver = new Receiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(StopWatchClock.STOPWATCH);
        this.registerReceiver(Receiver, intentFilter);
        timerStartStopButton = (Button) findViewById(R.id.btnStartStop);      
        if (isServiceRunning(TimeService.class))
        {
        	timerStartStopButton.setText("Stop");
        }
        else
        {
        	timerStartStopButton.setText("Start");
        }
        timerSavelapButton = (Button) findViewById(R.id.btnSaveLap);      
        timerSavelapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
            	if(save){
	            	++lapcount;
	            	String timer = (timer1.getText().toString() + timer2.getText().toString());
	                String splitlaps = (Laps + " " + lapcount);
	                long id = controller.insert(timer, splitlaps, formattedDate);
	                if (id<0){
	                	Message.message(context, "Unsucessful");
	                }else{
	                	Message.message(context, "Sucessfully Recorded");
	                }
            	}
            }
        });
        timerStartStopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
            	if(timerStartStopButton.getText().equals("Start")){
            		timerStartStopButton.setText("Stop");
            		save = true;
            		reset = false;
              	    onClickStartService(view);
            	} 
            	else
            	{
            		timerStartStopButton.setText("Start");
	              	save = true;
	              	reset = true;
	              	onClickStopService(view);
                }
            }
        });
        timerResetButton = (Button) findViewById(R.id.btnReset);      
        timerResetButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view){
        		if (reset)
        		{
	        		try {
	        			interFace.resetStartTime();
					} catch (RemoteException e) {
						Message.message(getApplicationContext(), "Reset didnt work.");
					}
	        		timer1.setText("00:00:00:");
	            	timer2.setText("000");
	            	save = false;
        		}
            }
        });
    	timerSplitButton = (Button) findViewById(R.id.btnSplit);      
        timerSplitButton.setOnClickListener(new View.OnClickListener() {
             public void onClick(View view){
             	 if(timerSplitButton.getText().equals("Split")){
                    timerSplitButton.setText("Resume");
                    split = true;
                    timer1.setText(datapassed1);
        	  	    timer2.setText(datapassed2);
              	 } else {
              	    timerSplitButton.setText("Split");
              	    split = false;
                 }	
             }
        });
	}
	public void onClickStartService(View V)
	{
		startService(new Intent(this, TimeService.class));
		if (Receiver == null)
    	{
			Receiver = new Receiver();
			IntentFilter intentFilter = new IntentFilter();
		    intentFilter.addAction(StopWatchClock.STOPWATCH);
		    this.registerReceiver(Receiver, intentFilter);
    	}
		getApplicationContext().bindService(new Intent(this, TimeService.class), mServiceConnection, 0);
	}
	public void onClickStopService(View V)
	{	
		stopService(new Intent(this, TimeService.class));
		if (mServiceBound) {
  			AppContext.getContext().unbindService(mServiceConnection);
			mServiceBound = false;
		}
	}
	private boolean isServiceRunning(Class<?> serviceClass) 
	{
	    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) 
	    {
	        if (serviceClass.getName().equals(service.service.getClassName())) 
	        {
	            return true;
	        }
	    }
	    return false;
	}

	@Override
    protected void onResume() {
        super.onResume();
        if (Receiver == null)
    	{
			Receiver = new Receiver();
			IntentFilter intentFilter = new IntentFilter();
		    intentFilter.addAction(StopWatchClock.STOPWATCH);
		    this.registerReceiver(Receiver, intentFilter);
    	}
        if (isServiceRunning(TimeService.class))
        {
    		AppContext.getContext().bindService(new Intent(this, TimeService.class), mServiceConnection, 0);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (Receiver != null)
		{
        	this.unregisterReceiver(Receiver);
        	Receiver = null;
 		}
    	//try {
			//interFace.unregisterCallBack(mCallback);
		//} catch (RemoteException e) {
			//e.printStackTrace();
		//}
    	if (mServiceBound) {
  			AppContext.getContext().unbindService(mServiceConnection);
			mServiceBound = false;
		}
    }
	@Override
    protected void onStart() {
    	super.onStart();
    	if (Receiver == null)
    	{
			Receiver = new Receiver();
			IntentFilter intentFilter = new IntentFilter();
		    intentFilter.addAction(StopWatchClock.STOPWATCH);
		    this.registerReceiver(Receiver, intentFilter);
    	}
    	if (isServiceRunning(TimeService.class))
        {
    		AppContext.getContext().bindService(new Intent(this, TimeService.class), mServiceConnection, 0);
        }
    }
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (Receiver != null)
		{
			this.unregisterReceiver(Receiver);
			Receiver = null;
 		}
		try {
			interFace.unregisterCallBack(mCallback);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if (mServiceBound) {
  			AppContext.getContext().unbindService(mServiceConnection);
			mServiceBound = false;
		}
	}
    @Override
    protected void onStop() {
      	super.onStop();
      	if (Receiver != null)
		{
            this.unregisterReceiver(Receiver);
            Receiver = null;
 		}
    	try {
			interFace.unregisterCallBack(mCallback);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	if (mServiceBound) {
  			AppContext.getContext().unbindService(mServiceConnection);
			mServiceBound = false;
		}
    }
    private class Receiver extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context arg0, Intent arg1) 
		{
			datapassed1 = arg1.getStringExtra("DATAPASSED1");
			datapassed2 = arg1.getStringExtra("DATAPASSED2");
			if (split)
           	{
           		timer1.setText(timer1.getText());
               	timer2.setText(timer2.getText());
           	}
			else{
				timer1.setText(datapassed1);
    	  	    timer2.setText(datapassed2);
			}
		}
	}
	private RemoteService mCallback = new RemoteService.Stub() {
		@Override
		public void fromActivity() throws RemoteException {
			// TODO Auto-generated method stub
			mCallback.fromActivity();
		}
	};
    private ServiceConnection mServiceConnection = new ServiceConnection() 
    {
		@Override
		public void onServiceDisconnected(ComponentName name) 
		{
			mServiceBound = false;
			Toast.makeText(getApplicationContext(), "OOPS Somethig went wrong Error 7", Toast.LENGTH_SHORT).show();
		}
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) 
		{
			interFace = AsyncService.Stub.asInterface((IBinder) service);
			//Toast.makeText(getApplicationContext(), "Bound", Toast.LENGTH_SHORT).show();
			mServiceBound = true;
			try {
				interFace.registerCallBack(mCallback);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	};
}