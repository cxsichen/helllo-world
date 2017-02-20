package com.console.equalizer;



import com.console.equalizer.util.CrashHandler;
import com.console.equalizer.util.SystemPropertiesProxy;

import android.app.Application;

public class MApplication extends Application {
	
	public String clientCode;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		clientCode = SystemPropertiesProxy.get(MApplication.this, "ro.inet.consumer.code", "000");
		CrashHandler.getInstance().init(this);
	}
}
