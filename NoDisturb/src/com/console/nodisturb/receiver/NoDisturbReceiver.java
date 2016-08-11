package com.console.nodisturb.receiver;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

public class NoDisturbReceiver extends BroadcastReceiver {

	
	public static final String ENTER_NODISTURB = "com.intent.action.ENTER_NODISTURB";
	public static final String LEAVE_NODISTURB = "com.intent.action.LEAVE_NODISTURB";
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		if (TextUtils.equals(action, ENTER_NODISTURB)){
			Intent mintent = new Intent();
			ComponentName cn = new ComponentName("com.console.nodisturb",
			        "com.console.nodisturb.MainActivity");
			mintent.setComponent(cn);
			mintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(mintent);
		}else if(TextUtils.equals(action, LEAVE_NODISTURB)){
			
		}
	}

}
