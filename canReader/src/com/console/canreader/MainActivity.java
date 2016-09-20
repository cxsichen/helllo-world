package com.console.canreader;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.console.canreader.service.CanInfo;
import com.console.canreader.service.CanService;
import com.console.canreader.service.CanService.CanServiceBinder;
import com.console.canreader.service.CanService.ReaderCallback;
import com.console.canreader.service.ICanCallback;
import com.console.canreader.service.ICanService;
import com.console.canreader.utils.BytesUtil;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.DensityUtils;
import com.console.canreader.utils.PreferenceUtil;
import com.console.canreader.utils.ViewPagerAdapter;
import com.console.canreader.view.ObdView;
import com.console.canreader.view.ViewPageFactory;
import com.console.canreader.view.VolkswagenGolfView.GolfView1;
import com.console.canreader.view.VolkswagenGolfView.GolfView2;
import com.console.canreader.view.VolkswagenGolfView.GolfView3;
import com.console.canreader.view.VolkswagenGolfView.GolfView4;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings;
import android.provider.SyncStateContract.Constants;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

public class MainActivity extends Activity {
	public static final String TEST = "2e810101";

	private int canType = -1; // 盒子厂家 0：睿志诚 1：尚摄
	private int carType = -1; // 车型 0:大众

	private ViewPager vp;
	private ViewPagerAdapter vpAdapter;
	private List<ViewPageFactory> viewsFactory;
	private LinearLayout indicatorLayout;
	/*
	 * TextView fuel_warn; TextView battery_warn;
	 */
	private static final String WARNSTART = "warn_start";
	private static final int KEYCODE_HOME = 271;
	CanInfo mCaninfo;
	int cout = 0;
	public static Boolean isResume = false;

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Contacts.MSG_UPDATA_UI:
				mCaninfo = (CanInfo) msg.obj;
				if (mCaninfo != null) {
					checkStartMode(mCaninfo);
					show(mCaninfo);
				}
				break;
			case Contacts.MSG_GET_MSG:
				// 大众主动获取数据

				if (canType == 0 && carType == 0) {
					sendMsg(Contacts.HEX_GET_CAR_INFO);
					mHandler.sendEmptyMessageDelayed(Contacts.MSG_GET_MSG, 2000);
				}

				if (canType == 0 && carType == 1) {
					sendMsg("2e90026300");
					sendMsg("2e90026310");
					sendMsg("2e90026311");
					sendMsg("2e90026320");
					sendMsg("2e90026321");
					sendMsg("2e90025010");
					sendMsg("2e90025020");
					sendMsg("2e90025021");
					sendMsg("2e90025022");
					sendMsg("2e90025030");
					sendMsg("2e90025031");
					sendMsg("2e90025032");
					sendMsg("2e90025040");
					sendMsg("2e90025041");
					sendMsg("2e90025042");
					sendMsg("2e90025050");
					sendMsg("2e90025051");
					sendMsg("2e90025052");
					mHandler.sendEmptyMessageDelayed(Contacts.MSG_GET_MSG,
							40000);
				}

				break;

			case Contacts.MSG_ONCE_GET_MSG:
				if (canType == 0) {
					sendMsg(Contacts.CONNECTMSG);
				}
				if (canType == 0 && carType == 0) {
					sendMsg(Contacts.HEX_GET_CAR_INFO_1);
					sendMsg(Contacts.HEX_GET_CAR_INFO_3);
				}
				break;
			default:
				break;
			}
		}
	};

	private void checkStartMode(CanInfo mCanInfo) {
		// TODO Auto-generated method stub
		int mode = Settings.System.getInt(getContentResolver(), WARNSTART, 0);
		if (mode == 1) {
			if (vp != null)
				vp.setCurrentItem(0);
		}
		if (mode == 1 && mCanInfo.FUEL_WARING_SIGN != 1
				&& mCanInfo.BATTERY_WARING_SIGN != 1
				&& mCanInfo.SAFETY_BELT_STATUS != 1
				&& mCanInfo.DISINFECTON_STATUS != 1
				&& mCanInfo.HANDBRAKE_STATUS != 1) {
			moveTaskToBack(true);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		bindService();
		initView();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Settings.System.putInt(getContentResolver(), WARNSTART, 0);
		super.onPause();
		mHandler.removeMessages(Contacts.MSG_GET_MSG);
		isResume = false;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (mISpService != null) {
			mHandler.sendEmptyMessageDelayed(Contacts.MSG_GET_MSG, 2000);
			mHandler.sendEmptyMessageDelayed(Contacts.MSG_ONCE_GET_MSG, 5000);
		}
		initView();
		firstShow();
		isResume = true;

	}

	private void firstShow() {
		// TODO Auto-generated method stub
		try {
			if (mISpService != null) {
				Message msg = new Message();
				msg.what = Contacts.MSG_UPDATA_UI;
				CanInfo tempInfo = mISpService.getCanInfo();
				// this page only deal with the type 10
				tempInfo.CHANGE_STATUS = 10;
				msg.obj = tempInfo;
				mHandler.sendMessage(msg);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	protected void show(CanInfo caninfo) {
		// TODO Auto-generated method stub
		if (mCaninfo.CHANGE_STATUS == 10) {
			for (ViewPageFactory mViewPageFactory : viewsFactory) {
				mViewPageFactory.showView(caninfo);
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			moveTaskToBack(true);
			return true;
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void initView() {
		// TODO Auto-generated method stub
		if (viewsFactory == null)
			viewsFactory = new ArrayList<ViewPageFactory>();
		if (vp == null)
			vp = (ViewPager) findViewById(R.id.vp);
		vp.setOffscreenPageLimit(2);
		if (vpAdapter == null)
			vpAdapter = new ViewPagerAdapter(viewsFactory);
		if (PreferenceUtil.getCARTYPE(this) == 1 && carType != 1) { // 大众高尔夫

			carType = PreferenceUtil.getCARTYPE(this);

			vp.removeAllViews();
			viewsFactory.clear();

			ViewPageFactory pageView = new ObdView(this,
					R.layout.dashboard_main);
			viewsFactory.add(pageView);

			ViewPageFactory GolfView1 = new GolfView1(this,
					R.layout.carinfo_layout_1);
			viewsFactory.add(GolfView1);

			ViewPageFactory GolfView2 = new GolfView2(this,
					R.layout.carinfo_layout_2);
			viewsFactory.add(GolfView2);

			ViewPageFactory GolfView3 = new GolfView3(this,
					R.layout.carinfo_layout_3);
			viewsFactory.add(GolfView3);

			ViewPageFactory GolfView4 = new GolfView4(this,
					R.layout.carinfo_layout_4);
			viewsFactory.add(GolfView4);

			vpAdapter.notifyDataSetChanged();
		} else if (PreferenceUtil.getCARTYPE(this) != 1) {
			vp.removeAllViews();
			viewsFactory.clear();
			ViewPageFactory pageView = new ObdView(this,
					R.layout.dashboard_main);
			viewsFactory.add(pageView);
			vpAdapter.notifyDataSetChanged();
		}
		vp.setAdapter(vpAdapter);

		initIndicator();

		canType = PreferenceUtil.getCANTYPE(this);
		carType = PreferenceUtil.getCARTYPE(this);
	}

	/**
	 * init Indicator
	 */
	protected void initIndicator() {
		if (indicatorLayout == null)
			indicatorLayout = (LinearLayout) findViewById(R.id.indicator);

		indicatorLayout.removeAllViews();

		if (viewsFactory.size() < 2) {
			return;
		}

		for (int i = 0; i < viewsFactory.size(); i++) {
			ImageView imageView = new ImageView(this);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			lp.setMargins(DensityUtils.dip2px(this, 4), 0,
					DensityUtils.dip2px(this, 4), 0);
			imageView.setLayoutParams(lp);
			if (i == 0) {
				imageView.setImageResource(R.drawable.white_oval);
			} else {
				imageView.setImageResource(R.drawable.gray_oval);
			}
			indicatorLayout.addView(imageView);
		}
		vp.setOnPageChangeListener(mOnPageChangeListener);
	}

	OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			for (int i = 0; i < viewsFactory.size(); i++) {
				((ImageView) indicatorLayout.getChildAt(i))
						.setImageResource(R.drawable.gray_oval);
			}
			((ImageView) indicatorLayout.getChildAt(arg0))
					.setImageResource(R.drawable.white_oval);

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			indicatorLayout.setVisibility(View.VISIBLE);
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			// indicatorLayout.setVisibility(View.INVISIBLE);
		}
	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unBindService();
		super.onDestroy();
	}

	private void bindService() {
		Intent intent = new Intent();
		intent.setClassName("com.console.canreader",
				"com.console.canreader.service.CanService");
		bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
	}

	private void unBindService() {
		try {
			if (mISpService != null)
				mISpService.removeCliend(mICallback);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		unbindService(mServiceConnection);
	}

	/**
	 * bind service
	 */
	private ICanService mISpService;
	private ICanCallback mICallback = new ICanCallback.Stub() {

		@Override
		public void readDataFromServer(CanInfo canInfo) throws RemoteException {
			Message msg = new Message();
			msg.what = Contacts.MSG_UPDATA_UI;
			msg.obj = canInfo;
			mHandler.sendMessage(msg);
		}
	};

	private ServiceConnection mServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mISpService = ICanService.Stub.asInterface(service);
			try {
				mISpService.addClient(mICallback);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			Message msg = new Message();
			mHandler.sendEmptyMessageDelayed(Contacts.MSG_GET_MSG, 1000);
			mHandler.sendEmptyMessageDelayed(Contacts.MSG_ONCE_GET_MSG, 5000);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mISpService = null;
		}
	};

	/**
	 * bind service
	 */

	public void sendMsg(String msg) {
		try {
			if (mISpService != null) {
				mISpService.sendDataToSp(BytesUtil.addRZCCheckBit(msg));
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
