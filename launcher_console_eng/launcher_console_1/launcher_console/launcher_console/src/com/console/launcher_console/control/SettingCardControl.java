package com.console.launcher_console.control;

import com.console.launcher_console.R;
import com.console.launcher_console.util.BytesUtil;
import com.console.launcher_console.util.PreferenceUtil;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SettingCardControl implements OnClickListener {

	
	private LinearLayout settingLayout;
	private Context context;
	private SerialPortControl mSerialPortControl;
	
	public SettingCardControl(Context context, LinearLayout layout,SerialPortControl mSerialPortControl) {
		settingLayout = layout;
		this.mSerialPortControl=mSerialPortControl;
		this.context = context;
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		settingLayout.findViewById(R.id.wifi_button).setOnClickListener(this);
		settingLayout.findViewById(R.id.wifi_hostpost_button).setOnClickListener(this);
		settingLayout.findViewById(R.id.effects_button).setOnClickListener(this);
		settingLayout.findViewById(R.id.device_bind_button).setOnClickListener(this);
		settingLayout.findViewById(R.id.setting_button).setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.wifi_button:
			context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
			break;
		case R.id.wifi_hostpost_button:
			Intent whostpostsintent = new Intent();
			ComponentName comp = new ComponentName("com.android.settings",  
					"com.android.settings.TetherSettings");  
			whostpostsintent.setComponent(comp);  
			whostpostsintent.setAction("android.intent.action.VIEW");
			context.startActivity(whostpostsintent);
			break;
		case R.id.effects_button:			
			Intent effectsIntent=new Intent();
			effectsIntent.setClassName("com.console.equalizer", "com.console.equalizer.MainActivity");
			context.startActivity(effectsIntent);
			break;
		case R.id.device_bind_button:
			Intent deviceIntent=new Intent();
			deviceIntent.setAction("com.android.settings.DEVICEBIND_SETTING");
			context.startActivity(deviceIntent);
			break;
		case R.id.setting_button:
			context.startActivity(new Intent(Settings.ACTION_SETTINGS));
			break;
		default:
			break;
		}
	}
}
