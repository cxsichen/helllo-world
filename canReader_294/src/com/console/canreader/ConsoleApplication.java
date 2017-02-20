package com.console.canreader;

import com.console.canreader.utils.CrashHandler;
import com.console.canreader.utils.SystemPropertiesProxy;

import android.app.Application;
import android.util.Log;

public class ConsoleApplication extends Application {

	public static String customId = "999";

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		// 获取客户的信息
		try {
			 customId=SystemPropertiesProxy.get(this, "ro.inet.consumer.code","999");
		} catch (Exception e) {
			customId = "999";
		}
		CrashHandler.getInstance().init(this);
	}
}
