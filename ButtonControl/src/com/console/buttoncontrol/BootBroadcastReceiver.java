package com.console.buttoncontrol;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent ;
import android.text.TextUtils;
import android.util.Log;


public class BootBroadcastReceiver extends BroadcastReceiver {

	public static final String ACTION_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";
	@Override
	public void onReceive(Context context, Intent intent) {
	    String action = intent.getAction();
		if (TextUtils.equals(action, ACTION_BOOT_COMPLETED)){
			context.startService(new Intent(context, SerialPortService.class));
		}
	}
}
