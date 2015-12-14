package com.chrusty.StopWatch;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.widget.Toast;

public class TimeService extends Service{

	PowerManager mgr = (PowerManager)AppContext.getContext().getSystemService(Context.POWER_SERVICE);
	WakeLock wakeLock = mgr.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakeLock");
	final static RemoteCallbackList<RemoteService> mCallbacks = new RemoteCallbackList<RemoteService>();
	
	private final static AsyncService.Stub mBinder = new AsyncService.Stub() 
	{
		@Override
	    public void registerCallBack(RemoteService cb) throws RemoteException 
	    {
            mCallbacks.register(cb);
		}
		@Override
		public void unregisterCallBack(RemoteService cb) throws RemoteException 
		{
			mCallbacks.unregister(cb);
		}
		@Override
		public void resetStartTime() throws RemoteException {
			StopWatchClock.reset(0, 0, 0, 0);
		}
	};
	@Override
	public IBinder onBind(Intent intent) {
		Toast.makeText(getApplicationContext(), "Service Is Binded", Toast.LENGTH_SHORT).show();
		return mBinder;
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		StopWatchClock.Start();
		Toast.makeText(getApplicationContext(), "Service Started", Toast.LENGTH_LONG).show();
		Notification("Stop Watch", "Now Fast Are You");
		wakeLock.acquire();
		return Service.START_STICKY;
	}
	@Override
	public void onRebind(Intent intent){
		super.onRebind(intent);
		Toast.makeText(getApplicationContext(), "Service is ReBinded", Toast.LENGTH_SHORT).show();
	}
	@Override
	public boolean onUnbind(Intent intent){
		Toast.makeText(getApplicationContext(), "Service is unBinded", Toast.LENGTH_SHORT).show();
		return true;
	}

	private void Notification(String notificationTitle, String notificationMessage){ 
		Bitmap bitMap = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_stat_name);
		Intent notificationIntent = new Intent(this, MainActivity.class);
		notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		android.app.Notification.Builder builder = new android.app.Notification.Builder(this);
		builder.setSmallIcon(R.drawable.ic_stat_name);
		builder.setContentTitle(notificationTitle);
		builder.setContentText(notificationMessage);
		builder.setContentIntent(pendingIntent);
		builder.setWhen(System.currentTimeMillis());
		builder.setLargeIcon(bitMap);
		builder.setOngoing(true);
		builder.setAutoCancel(false);
		builder.setTicker("Stop Watch.");
		startForeground(1234, builder.build());
	}
	@Override
	public void onDestroy(){
		super.onDestroy();
		StopWatchClock.Stop();
		wakeLock.release();
		Toast.makeText(getApplicationContext(), "Service Stopped", Toast.LENGTH_LONG).show();
	}	
}
