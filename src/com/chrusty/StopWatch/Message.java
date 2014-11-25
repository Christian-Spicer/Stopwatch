package com.chrusty.StopWatch;

import android.content.Context;
import android.widget.Toast;

public class Message {
	static void message(Context context, String message){
		Toast.makeText(ApplicationContextProvider.getContext(), message, Toast.LENGTH_SHORT).show();
		
	}
}
