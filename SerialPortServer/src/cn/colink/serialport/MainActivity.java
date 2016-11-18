package cn.colink.serialport;

import cn.colink.serialport.service.ISerialPortCallback;
import cn.colink.serialport.service.ISerialPortService;
import cn.colink.serialport.utils.BytesUtil;
import android.R.drawable;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private ISerialPortService mISpService;
	TextView tv;
	String str = "";
	Button button;
	ScrollView scroll;
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				str = str + BytesUtil.bytesToHexString((byte[]) msg.obj)+"\n";
				tv.setText(str);
				scroll.fullScroll(ScrollView.FOCUS_DOWN);
				break;
			default:
				break;
			}
		}
	};

	private ISerialPortCallback mICallback = new ISerialPortCallback.Stub() {

		@Override
		public void readDataFromServer(byte[] bytes) throws RemoteException {

			Message msg = new Message();
			msg.what = 1;
			msg.obj = bytes;
			mHandler.sendMessage(msg);
		}
	};

	private ServiceConnection mServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {

			mISpService = ISerialPortService.Stub.asInterface(service);
			try {
				mISpService.addClient(mICallback);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mISpService = null;
		}
	};

	private void bindSpService() {
		try {
			Intent intent = new Intent();
			intent.setClassName("cn.colink.serialport",
					"cn.colink.serialport.service.SerialPortService");
			bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void unbindSpService() {
		try {
			if (mISpService != null)
				mISpService.removeCliend(mICallback);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		try {
			unbindService(mServiceConnection);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		bindSpService();
		scroll= (ScrollView) findViewById(R.id.scro);
		tv = (TextView) findViewById(R.id.tv);
		button = (Button) findViewById(R.id.btn);
		button.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i("cxs", "========onResume()===========");
	}

	@Override
	protected void onDestroy() {
		unbindSpService();
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn:
			str = "";
			tv.setText(str);
			break;

		default:
			break;
		}
	}

}
