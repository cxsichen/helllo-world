package com.console.canreader;






import com.console.canreader.utils.CrashHandler;

import android.app.Application;
import android.util.Log;
public class ConsoleApplication extends Application {
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		CrashHandler.getInstance().init(this);
	}	
}
