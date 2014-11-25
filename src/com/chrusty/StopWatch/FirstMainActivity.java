package com.chrusty.StopWatch;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import com.chrusty.StopWatch.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FirstMainActivity extends Activity {
	
	private boolean split = false;
	private boolean start = false;
	private boolean reset = false;
	private boolean stop = false;
	private boolean save = false;
	private DBControllerAdaptor controller;
	private Context context;
	private int lapcount;
    private String Laps = "Lap";
    private String timer1, timer2; 
    private TextView timer3, timer4;
    private int milliseconds;
    private int seconds;
    private int minutes;
    private int hours;
    private long StartTime = 0L;
    private long timeInMilliseconds = 0L;
    private long timeSwapBuff = 0L;
    private long updatedTime = 0L;
    private Handler handler = new Handler();;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_main);
	    timer3 = (TextView) findViewById(R.id.textTimer);
    	timer4 = (TextView) findViewById(R.id.textTimer1);
    	controller = new DBControllerAdaptor(this.getApplicationContext());
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String formattedDate = df.format(c.getTime());
    	
        final Button timerSavelapButton = (Button) findViewById(R.id.btnSaveLap);      
        timerSavelapButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
            	if(save){
	            	++lapcount;
	            	String timer = (timer3.getText().toString()+timer4.getText().toString());
	                String splitlaps = (Laps +" " + lapcount);
	                long id = controller.insert(timer, splitlaps, formattedDate);
	                if (id<0){
	                	Message.message(context, "Unsucessful");
	                }else{
	                	Message.message(context, "Sucessfully Recorded");
	                }
            	}
            }
        });
        final Button timerStartStopButton = (Button) findViewById(R.id.btnStartStop);      
        timerStartStopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
            	if(timerStartStopButton.getText().equals("Start")){
            		timerStartStopButton.setText("Stop");
            		split = true;
            		StartTime = SystemClock.uptimeMillis();
            		handler.removeCallbacks(UpdateTimeTask);
        			handler.postDelayed(UpdateTimeTask, 0);
            		start = true;
            		stop = true;
            		reset = false;
            		save = true;
            		SplitText();
            	} else if(stop){
            		timerStartStopButton.setText("Start");
	              	split = true;
	              	timeSwapBuff += timeInMilliseconds;
	            	handler.removeCallbacks(UpdateTimeTask);
	                start = false;
	              	reset = true;
	              	save = true;
	              	SplitText();
                }
            }
        });
        Button timerResetButton = (Button) findViewById(R.id.btnReset);      
        timerResetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
            	if(reset){
            		StartTime = 0L;
            		timeInMilliseconds = 0L;
            		timeSwapBuff = 0L;
            		updatedTime = 0L;
	            	timer3.setText("00:00:00:");
	            	timer4.setText("000");
	            	SplitText();
	            	StartText();
	            	save = false;
            	}
            }
        });
    	final Button timerSplitButton = (Button) findViewById(R.id.btnSplit);      
        timerSplitButton.setOnClickListener(new View.OnClickListener() {
             public void onClick(View view){
	             if(start){
	             	 if(timerSplitButton.getText().equals("Split")){
	                    timerSplitButton.setText("Resume");
	                    split = false;
	              	    reset = false;
	              	    stop = false;
	              	    timer3.setText(timer1);
	              	    timer4.setText(timer2);
	              	 } else {
	              	    timerSplitButton.setText("Split");
	              	    split = true;
	              	    stop = true;
	              	    reset = false;
	                 }
            	 }
             }
        });
	}
	private void SplitText() {
    	Button button = (Button)findViewById(R.id.btnSplit);
    	button.setText("Split");
 	}
	private void StartText() {
    	Button button = (Button)findViewById(R.id.btnStartStop);
    	button.setText("Start");
 	}
	@Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
    protected void onStart() {
     // TODO Auto-generated method stub
     super.onStart();
    }
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
    @Override
    protected void onStop() {
     // TODO Auto-generated method stub
  	  	super.onStop();
    }
	private Runnable UpdateTimeTask = new Runnable(){
        public void run() {
        	final long start = StartTime;
            timeInMilliseconds = SystemClock.uptimeMillis()- start;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            milliseconds = (int) (updatedTime / 1);          
            seconds = milliseconds / 1000;
            milliseconds = milliseconds % 1000;
            minutes = seconds / 60;
            seconds = seconds % 60;
            hours = minutes /60;
            minutes = minutes % 60;
            timer1 = String.format("%02d:%02d:%02d:",  hours, minutes, seconds);
            timer2 = String.format("%03d", milliseconds);                             
            handler.postDelayed(this, 0);
            if(split){
            	timer3.setText(timer1);
            	timer4.setText(timer2);
        	}
     	}
	};
}
