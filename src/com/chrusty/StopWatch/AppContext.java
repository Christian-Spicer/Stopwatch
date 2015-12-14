package com.chrusty.StopWatch;

import android.app.Application;
import android.content.Context;
 
public class AppContext extends Application {
    /**
     * Keeps a reference of the application context
     */
    private static Context sContext;
    private static long StartTime = 0L;
	private static long timeInMilliseconds = 0L;
    private static long timeSwapBuff = 0L;
    private static long updatedTime = 0L;
    @Override
    public void onCreate() {
        super.onCreate();
        AppContext.sContext = getApplicationContext();
    }
    public static Context getContext() {
        return sContext;
    }
    public static long getStartTime() {
		return StartTime;
	}
	public static void setStartTime(long startTime) {
		AppContext.StartTime = startTime;
	}
	public static long getTimeInMilliseconds() {
		return timeInMilliseconds;
	}
	public static void setTimeInMilliseconds(long timeInMilliseconds) {
		AppContext.timeInMilliseconds = timeInMilliseconds;
	}
	public static long getTimeSwapBuff() {
		return timeSwapBuff;
	}
	public static void setTimeSwapBuff(long timeSwapBuff) {
		AppContext.timeSwapBuff = timeSwapBuff;
	}
	public static long getUpdatedTime() {
		return updatedTime;
	}
	public static void setUpdatedTime(long updatedTime) {
		AppContext.updatedTime = updatedTime;
	}
}