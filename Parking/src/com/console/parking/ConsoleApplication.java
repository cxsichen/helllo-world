package com.console.parking;





import com.console.parking.util.CrashHandler;
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
