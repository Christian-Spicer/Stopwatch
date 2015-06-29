package com.chrusty.StopWatch;

import android.app.Application;
import android.content.Context;
 
public class AppContext extends Application {
    /**
     * Keeps a reference of the application context
     */
    private static Context sContext;
 
    @Override
    public void onCreate() {
        super.onCreate();
        AppContext.sContext = getApplicationContext();
    }
    /**
     * Returns the application context
     *
     * @return application context
     */
    public static Context getContext() {
        return sContext;
    }
}