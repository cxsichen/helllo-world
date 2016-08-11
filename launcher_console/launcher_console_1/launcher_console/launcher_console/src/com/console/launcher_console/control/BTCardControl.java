package com.console.launcher_console.control;

import java.util.List;
import java.util.Set;

import com.console.launcher_console.R;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BTCardControl extends BroadcastReceiver {

	private LinearLayout btCardLayout;
	private Context context;
	BluetoothHeadset mBluetoothHeadset;
	private TextView btStatusTv;

	public BTCardControl(Context context, LinearLayout layout) {
		btCardLayout = layout;
		this.context = context;
		initView();
		init();
	}

	private void initView() {
		// TODO Auto-generated method stub
		btStatusTv = (TextView) btCardLayout
				.findViewById(R.id.bt_device_status);
	}

	private void init() {
		// TODO Auto-generated method stub
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("android.bluetooth.device.action.ACL_CONNECTED");
		intentFilter
				.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
		// 注册广播接收器，接收并处理搜索结果
		context.registerReceiver(this, intentFilter);

	}

	private BluetoothProfile.ServiceListener mProfileListener = new BluetoothProfile.ServiceListener() {
		public void onServiceConnected(int profile, BluetoothProfile proxy) {
			mBluetoothHeadset = (BluetoothHeadset) proxy;
			mBluetoothHeadset.getConnectedDevices();
		}

		public void onServiceDisconnected(int profile) {
			if (profile == BluetoothProfile.HEADSET) {
				mBluetoothHeadset = null;
			}
		}
	};
	private BluetoothAdapter mBluetoothAdapter;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
	
		if (action.equals("android.bluetooth.device.action.ACL_CONNECTED")) {
			BluetoothDevice device = intent
					.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			btStatusTv.setText(device.getName());
		} else if (action
				.equals("android.bluetooth.device.action.ACL_DISCONNECTED")) {
			btStatusTv.setText(R.string.device_name);
		}
	}
	
	public void unregisterReceiver(){
		context.unregisterReceiver(this);
	}

}
