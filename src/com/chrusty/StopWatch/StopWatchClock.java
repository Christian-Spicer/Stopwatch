package com.chrusty.StopWatch;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;

public class StopWatchClock extends Thread{
	
    private static int milliseconds;
    private static int seconds;
    private static int minutes;
	private static int hours;
    private Handler handler = new Handler();
    private static String timer1, timer2; 
	final static String STOPWATCH = "STOPWATCH";
    static Runnable thread = new Thread(StopWatchClock.access());
    private static StopWatchClock stopWatchClock;
    
    public static StopWatchClock access()
    {
    	if (stopWatchClock == null)
    		stopWatchClock = new StopWatchClock();
    	return stopWatchClock;
    }
	public static void Start()
	{
		AppContext.setStartTime(SystemClock.uptimeMillis());
		StopWatchClock.access().handler.postDelayed(thread, 0);
	}
	public static void Stop()
	{
		long timeSwapBuff = AppContext.getTimeSwapBuff();
		AppContext.setTimeSwapBuff(timeSwapBuff += AppContext.getTimeInMilliseconds());
		StopWatchClock.access().handler.removeCallbacks(thread);
    }
	public static void reset(long start, long mill, long buff, long time)
	{	
		AppContext.setStartTime(start);
		AppContext.setTimeInMilliseconds(mill);
		AppContext.setTimeSwapBuff(buff);
		AppContext.setUpdatedTime(time);
		AppContext.setStartTime(SystemClock.uptimeMillis());
		Message.message(AppContext.getContext(), "Reset Called.");
	}
	public void run() {
		final long start = AppContext.getStartTime();
    	AppContext.setTimeInMilliseconds(SystemClock.uptimeMillis()- start);
    	AppContext.setUpdatedTime(AppContext.getTimeSwapBuff() + AppContext.getTimeInMilliseconds());
        milliseconds = (int) (AppContext.getUpdatedTime() / 1);          
        seconds = milliseconds / 1000;
        milliseconds = milliseconds % 1000;
        minutes = seconds / 60;
        seconds = seconds % 60;
        hours = minutes /60;
        minutes = minutes % 60;
        timer1 = String.format("%02d:%02d:%02d:",  hours, minutes, seconds);
        timer2 = String.format("%03d", milliseconds);                             
        handler.postDelayed(thread, 0);
        Intent intent = new Intent();
		intent.setAction(STOPWATCH);
		intent.putExtra("DATAPASSED1", timer1);
		intent.putExtra("DATAPASSED2", timer2);
		AppContext.getContext().sendBroadcast(intent);
	}
}
