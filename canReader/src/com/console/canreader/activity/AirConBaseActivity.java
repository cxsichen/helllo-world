package com.console.canreader.activity;

import com.console.canreader.GuideActivity;
import com.console.canreader.service.CanInfo;
import com.console.canreader.service.CanService;
import com.console.canreader.service.ICanCallback;
import com.console.canreader.service.ICanService;
import com.console.canreader.utils.BytesUtil;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.PreferenceUtil;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;

public class AirConBaseActivity extends BaseActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Settings.System.putInt(getContentResolver(), "no_aircon", 1);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Settings.System.putInt(getContentResolver(), "no_aircon", 1);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Settings.System.putInt(getContentResolver(), "no_aircon", 0);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Settings.System.putInt(getContentResolver(), "no_aircon", 0);
	}
}
