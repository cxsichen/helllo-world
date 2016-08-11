package com.console.launcher_console.control;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.preference.Preference;
import android.util.Log;
import android.widget.Toast;
import cn.colink.serialport.service.ISerialPortCallback;
import cn.colink.serialport.service.ISerialPortService;

import com.console.launcher_console.MainActivity;
import com.console.launcher_console.R;
import com.console.launcher_console.service.SerialPortControlService;
import com.console.launcher_console.service.SerialPortControlService.MyBinder;
import com.console.launcher_console.util.BytesUtil;
import com.console.launcher_console.util.Constact;
import com.console.launcher_console.util.Contacts;
import com.console.launcher_console.util.PreferenceUtil;
import com.console.launcher_console.util.Trace;
import com.console.launcher_console.view.FMView.OnValueChangedListener;

public class SerialPortControl {

	Context context;
	private SerialPortControlService bindService;

	public SerialPortControl(Context context) {
		this.context = context;
		Intent intent = new Intent(context, SerialPortControlService.class);
		context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
	}

	private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			bindService = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			Log.i("cxs", "===SerialPortControl=====onServiceConnected====="
					+ service);
			MyBinder binder = (MyBinder) service;
			bindService = binder.getService();
			bindService
					.setDataCallback(new com.console.launcher_console.service.SerialPortControlService.DataCallback() {

						@Override
						public void OnChange(byte[] value) {
							// TODO Auto-generated method stub
							mDataCallback.OnChange(value);
						}
					});
		}
	};

	public void bindSpService() {
		if (bindService != null) {
			bindService.bindSpService();
			Log.i("cxs", "======SerialPortControl=======");
		}
	}

	public void unbindSpService() {
		if (bindService != null) {
			context.unbindService(conn);
			bindService = null;
		}
	}

	public void sendMsg(String msg) {
		try {
			if (bindService != null) {
				bindService.sendMsg(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public interface DataCallback {
		public void OnChange(byte[] value);
	}

	DataCallback mDataCallback;

	public void setDataCallback(DataCallback dataCallback) {
		this.mDataCallback = dataCallback;
	}

}
