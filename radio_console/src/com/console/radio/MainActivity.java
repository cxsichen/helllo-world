package com.console.radio;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.colink.serialport.service.ISerialPortCallback;
import cn.colink.serialport.service.ISerialPortService;



import com.console.radio.bean.RadioData;
import com.console.radio.bean.RadioDataEvent;
import com.console.radio.utils.BytesUtil;
import com.console.radio.utils.Contacts;
import com.console.radio.utils.DensityUtils;
import com.console.radio.utils.PreferenceUtil;
import com.console.radio.utils.Trace;
import com.console.radio.utils.VerticalSeekBar;
import com.console.radio.utils.VerticalSeekBar.OnProgressChangedListener;
import com.console.radio.utils.ViewPagerAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.ContentObserver;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings;
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
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

public class MainActivity extends Activity implements OnClickListener,
		OnLongClickListener {

	private final int FM_START_FREQ = 8750;
	private final int FM_END_FREQ = 10800;
	private final int AM_START_FREQ = 522;
	private final int AM_END_FREQ = 1620;
	private RadioData mRadioData = new RadioData();
	SeekBar mSeekbar;

	private final String[] HEX_ITEM_STRINGS = new String[] {
			Contacts.HEX_ITEM_FIRST, Contacts.HEX_ITEM_SECOND,
			Contacts.HEX_ITEM_THIRTH, Contacts.HEX_ITEM_FOUR,
			Contacts.HEX_ITEM_FIVE, Contacts.HEX_ITEM_SIXTH };
	private final String[] HEX_ITEM_LONG_STRINGS = new String[] {
			Contacts.HEX_ITEM_LONG_FIRST, Contacts.HEX_ITEM_LONG_SECOND,
			Contacts.HEX_ITEM_LONG_THIRTH, Contacts.HEX_ITEM_LONG_FOUR,
			Contacts.HEX_ITEM_LONG_FIVE, Contacts.HEX_ITEM_LONG_SIXTH };
	private final int[] PAGE_ITEM_ID = new int[] { R.id.first_channel,
			R.id.second_channel, R.id.third_channel, R.id.forth_channel,
			R.id.fifth_channel, R.id.sixth_channel };

	public static final String ACTION_APP_CANCEL = "action_fm_app_cancel";

	private DecimalFormat df2 = new DecimalFormat("###.00");
	private ISerialPortService mISpService;

	private TextView amButton;
	private TextView fmButton;
	private LinearLayout amfmButton;
	private ImageView scanButton;
	private ImageView playButton;
	private ImageView preButton;
	private ImageView nextButton;
	private TextView unitTv;
	private TextView freqTv;

	LayoutInflater inflater;
	private ViewPager amVp;
	private ViewPagerAdapter amVpAdapter;
	private List<View> amViews = new ArrayList<View>();;
	private LinearLayout amIndicatorLayout;

	private ViewPager fmVp;
	private ViewPagerAdapter fmVpAdapter;
	private List<View> fmViews = new ArrayList<View>();;
	private LinearLayout fmIndicatorLayout;
	private static boolean PAGECHANGELOCK = false;
	private static boolean SEARCHLOCK = false;

	private boolean isFM = true;
	private int band = 0;
	private int item = 0;
	private int DEFAULT_VALUME = 27;
	private int UNDEFAULT_VALUME = -100;
	private List<Integer> allFqs = new ArrayList<Integer>();
//	private int volumeValue = 0;
	Dialog valumeDialog; // 舍弃

	

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Contacts.MSG_UPDATA_UI:
				byte[] packet = (byte[]) msg.obj;
				radioDataAndUpdateUI(new RadioDataEvent(packet));
				break;
			case Contacts.MSG_CHANGE_PAGE:
				if (!PAGECHANGELOCK) {
					if (msg.arg1 < 3) {
						fmVp.setCurrentItem(msg.arg1);
					} else {
						amVp.setCurrentItem(msg.arg1 - 3);
					}
				}
				break;
			case Contacts.MSG_PAGE_UNLOCK:
				PAGECHANGELOCK = false;
				break;
			case Contacts.MSG_HIDE_VALUMEDIALOG:
				if (valumeDialog != null) {
					if (valumeDialog.isShowing())
						valumeDialog.dismiss();
				}
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
			msg.what = Contacts.MSG_UPDATA_UI;
			msg.obj = bytes;
			mHandler.sendMessage(msg);
		}
	};

	private ServiceConnection mServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Trace.i("onServiceConnected");
			mISpService = ISerialPortService.Stub.asInterface(service);
			try {
				mISpService.addClient(mICallback);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			// sendMsg(Contacts.HEX_HOME_TO_FM);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mISpService = null;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
		getAudioManager();
		// requestAudioFocus();
		setFqDataWhenOnCreate();

	}

	private void initView() {
		// TODO Auto-generated method stub

		inflater = LayoutInflater.from(this);

		amButton = (TextView) findViewById(R.id.am_button);
		fmButton = (TextView) findViewById(R.id.fm_button);
		amfmButton = (LinearLayout) findViewById(R.id.am_fm_changeButton);
		scanButton = (ImageView) findViewById(R.id.scan_button);
		playButton = (ImageView) findViewById(R.id.play_button);
		preButton = (ImageView) findViewById(R.id.pre_button);
		nextButton = (ImageView) findViewById(R.id.next_button);
		unitTv = (TextView) findViewById(R.id.unit_tv);
		freqTv = (TextView) findViewById(R.id.freq_tv);

		// amButton.setOnClickListener(this);
		// fmButton.setOnClickListener(this);
		amfmButton.setOnClickListener(this);
		scanButton.setOnClickListener(this);
	    playButton.setOnClickListener(this);
		preButton.setOnClickListener(this);
		nextButton.setOnClickListener(this);

		preButton.setOnLongClickListener(this);
		nextButton.setOnLongClickListener(this);

		initAmLayout();
		initFmLayout();
		Settings.System.putInt(getContentResolver(),
				Contacts.FMSTATUS, 0);
		changePlayButton();
		getContentResolver().registerContentObserver(
				android.provider.Settings.System.getUriFor(Contacts.FMSTATUS),
				true, mFmStatusObserver);
	}
	
	private FmStatusObserver mFmStatusObserver = new FmStatusObserver();

	public class FmStatusObserver extends ContentObserver {
		public FmStatusObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			changePlayButton();
		}
	}
	
	private void  changePlayButton(){
		Log.i("cxs","---------changePlayButton---------------");
		int mode = Settings.System.getInt(getContentResolver(),
				Contacts.FMSTATUS,0);
		if(mode==1){
			playButton.setImageResource(R.drawable.ic_play);
		}else{
			playButton.setImageResource(R.drawable.ic_stop);
		}
	}


	/*-------AM的viewpage设置   start--------------*/
	private void initAmLayout() {
		// TODO Auto-generated method stub
		amVp = (ViewPager) findViewById(R.id.am_vp);
		for (int i = 0; i < 2; i++) {
			View pageView = inflater.inflate(R.layout.page_layout, null);
			for (int j = 0; j < 6; j++) {
				pageView.findViewById(PAGE_ITEM_ID[j]).setTag(j);
				pageView.findViewById(PAGE_ITEM_ID[j]).setOnClickListener(
						new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								sendMsg(HEX_ITEM_STRINGS[(Integer) v.getTag()]);
							}
						});
				pageView.findViewById(PAGE_ITEM_ID[j]).setOnLongClickListener(
						new OnLongClickListener() {

							@Override
							public boolean onLongClick(View v) {
								// TODO Auto-generated method stub
								sendMsg(HEX_ITEM_LONG_STRINGS[(Integer) v
										.getTag()]);
								return true;
							}
						});
			}
			amViews.add(pageView);
		}
		amVp.setOffscreenPageLimit(2);
		amVpAdapter = new ViewPagerAdapter(amViews);
		amVp.setAdapter(amVpAdapter);

		amIndicatorLayout = (LinearLayout) findViewById(R.id.am_indicator);
		for (int i = 0; i < amViews.size(); i++) {
			ImageView imageView = new ImageView(this);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			lp.setMargins(DensityUtils.dp2px(this, 4), 0,
					DensityUtils.dp2px(this, 4), 0);
			imageView.setLayoutParams(lp);
			if (i == 0) {
				imageView.setImageResource(R.drawable.white_oval);
			} else {
				imageView.setImageResource(R.drawable.gray_oval);
			}
			amIndicatorLayout.addView(imageView);
		}

		amVp.setOnPageChangeListener(mAmOnPageChangeListener);
	}

	OnPageChangeListener mAmOnPageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			for (int i = 0; i < amViews.size(); i++) {
				((ImageView) amIndicatorLayout.getChildAt(i))
						.setImageResource(R.drawable.gray_oval);
			}
			((ImageView) amIndicatorLayout.getChildAt(arg0))
					.setImageResource(R.drawable.white_oval);
			if (!SEARCHLOCK) {
				switch (arg0) {
				case 0:
					sendMsg(Contacts.HEX_AM_BRAND_1);
					break;
				case 1:
					sendMsg(Contacts.HEX_AM_BRAND_2);
					break;
				default:
					break;
				}
			}
			SEARCHLOCK = false;
			updatePageView(allFqs, band, item);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			PAGECHANGELOCK = true;
			mHandler.removeMessages(Contacts.MSG_CHANGE_PAGE);
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

			if (arg0 == 0) {
				mHandler.removeMessages(Contacts.MSG_PAGE_UNLOCK);
				mHandler.sendEmptyMessageDelayed(Contacts.MSG_PAGE_UNLOCK, 1000);
			} else {
				mHandler.removeMessages(Contacts.MSG_PAGE_UNLOCK);
			}
		}
	};

	/*-------AM的viewpage设置   end--------------*/

	/*-------FM的viewpage设置   start--------------*/
	private void initFmLayout() {
		// TODO Auto-generated method stub
		fmVp = (ViewPager) findViewById(R.id.fm_vp);
		for (int i = 0; i < 3; i++) {
			View pageView = inflater.inflate(R.layout.page_layout, null);
			for (int j = 0; j < 6; j++) {
				pageView.findViewById(PAGE_ITEM_ID[j]).setTag(j);
				pageView.findViewById(PAGE_ITEM_ID[j]).setOnClickListener(
						new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								sendMsg(HEX_ITEM_STRINGS[(Integer) v.getTag()]);
							}
						});
				pageView.findViewById(PAGE_ITEM_ID[j]).setOnLongClickListener(
						new OnLongClickListener() {

							@Override
							public boolean onLongClick(View v) {
								// TODO Auto-generated method stub
								sendMsg(HEX_ITEM_LONG_STRINGS[(Integer) v
										.getTag()]);
								return true;
							}
						});
			}
			fmViews.add(pageView);
		}
		fmVp.setOffscreenPageLimit(2);
		fmVpAdapter = new ViewPagerAdapter(fmViews);
		fmVp.setAdapter(fmVpAdapter);

		fmIndicatorLayout = (LinearLayout) findViewById(R.id.fm_indicator);
		for (int i = 0; i < fmViews.size(); i++) {
			ImageView imageView = new ImageView(this);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			lp.setMargins(DensityUtils.dp2px(this, 4), 0,
					DensityUtils.dp2px(this, 4), 0);
			imageView.setLayoutParams(lp);
			if (i == 0) {
				imageView.setImageResource(R.drawable.white_oval);
			} else {
				imageView.setImageResource(R.drawable.gray_oval);
			}
			fmIndicatorLayout.addView(imageView);
		}

		fmVp.setOnPageChangeListener(mFmOnPageChangeListener);
	}

	private static int fmPage = 0;
	OnPageChangeListener mFmOnPageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			for (int i = 0; i < fmViews.size(); i++) {
				((ImageView) fmIndicatorLayout.getChildAt(i))
						.setImageResource(R.drawable.gray_oval);
			}
			((ImageView) fmIndicatorLayout.getChildAt(arg0))
					.setImageResource(R.drawable.white_oval);
			fmPage = arg0;
			if (!SEARCHLOCK) {
				switch (arg0) {
				case 0:
					sendMsg(Contacts.HEX_FM_BRAND_1);
					break;
				case 1:
					sendMsg(Contacts.HEX_FM_BRAND_2);
					break;
				case 2:
					sendMsg(Contacts.HEX_FM_BRAND_3);
					break;
				default:
					break;
				}
			}
			SEARCHLOCK = false;
			updatePageView(allFqs, band, item);

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			PAGECHANGELOCK = true;
			mHandler.removeMessages(Contacts.MSG_CHANGE_PAGE);
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

			if (arg0 == 0) {
				mHandler.removeMessages(Contacts.MSG_PAGE_UNLOCK);
				mHandler.sendEmptyMessageDelayed(Contacts.MSG_PAGE_UNLOCK, 1000);
			} else {
				mHandler.removeMessages(Contacts.MSG_PAGE_UNLOCK);
			}
			Log.i("cxs", "========onPageScrollStateChanged=======" + arg0);

		}
	};

	/*-------FM的viewpage设置   end--------------*/

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		/*
		 * case R.id.am_button: sendMsg(Contacts.HEX_AM_BRAND_1); break; case
		 * R.id.fm_button: sendMsg(Contacts.HEX_FM_BRAND_1); break;
		 */
		case R.id.am_fm_changeButton:
			if (isFM) {
				sendMsg(Contacts.HEX_AM_BRAND_1);
			} else {
				sendMsg(Contacts.HEX_FM_BRAND_1);
			}
			break;
		case R.id.scan_button:
			sendMsg(Contacts.HEX_AUTO_SCAN);
			SEARCHLOCK = true;
			break;
		case R.id.pre_button:
			sendMsg(Contacts.HEX_PRE_STEP_MOVE);
			break;
		case R.id.next_button:
			sendMsg(Contacts.HEX_NEXT_STEP_MOVE);
			break;
		case R.id.play_button:
			sendMsg(Contacts.FM_PLAY);
			break;
		default:
			break;
		}
	}

	// 音量调节进度条对话框 暂时放弃
/*	private void showVolumeSeekbar() {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(this);
		View layout = inflater.inflate(R.layout.seekbar, null);
		if (valumeDialog == null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setView(layout);
			builder.setCancelable(true);
			// builder.create().show();
			valumeDialog = builder.create();
		}
		if (!valumeDialog.isShowing())
			valumeDialog.show();
		mHandler.removeMessages(Contacts.MSG_HIDE_VALUMEDIALOG);
		mHandler.sendEmptyMessageDelayed(Contacts.MSG_HIDE_VALUMEDIALOG, 5000);
		// View view=null;
		// view.setOnClickListener(this);
		volumeValue = PreferenceUtil.getVolume(this);
		if (volumeValue == UNDEFAULT_VALUME) {
			volumeValue = DEFAULT_VALUME;
			PreferenceUtil.setVolume(MainActivity.this, volumeValue);
			sendMsg("FD020000" + BytesUtil.intToHexString(volumeValue));
		}
		mSeekbar = (SeekBar) layout.findViewById(R.id.seek);
	//	mSeekbar.setProgress(volumeValue);
		final TextView tv = (TextView) layout.findViewById(R.id.text);
	//	tv.setText("" + volumeValue);
		mSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				Log.i("cxs", "=========onStopTrackingTouch=============");
				PreferenceUtil.setVolume(MainActivity.this, volumeValue);
				sendMsg("FD020000" + BytesUtil.intToHexString(volumeValue));
				mHandler.removeMessages(Contacts.MSG_HIDE_VALUMEDIALOG);
				mHandler.sendEmptyMessageDelayed(
						Contacts.MSG_HIDE_VALUMEDIALOG, 5000);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				Log.i("cxs", "=========onStartTrackingTouch=============");

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				Log.i("cxs", "=========onProgressChanged============="
						+ progress);
				volumeValue = progress;
				tv.setText("" + volumeValue);
				mHandler.removeMessages(Contacts.MSG_HIDE_VALUMEDIALOG);
				mHandler.sendEmptyMessageDelayed(
						Contacts.MSG_HIDE_VALUMEDIALOG, 5000);
			}
		});
	}*/

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.pre_button:
			sendMsg(Contacts.HEX_PRE_FAST_MOVE);
			break;
		case R.id.next_button:
			sendMsg(Contacts.HEX_NEXT_FAST_MOVE);
			break;
		default:
			break;
		}
		return true;
	}

	public void radioDataAndUpdateUI(RadioDataEvent event) {
		Log.i("cxs", "packet : " + BytesUtil.bytesToHexString(event.mPacket));
		byte[] packet = event.mPacket;
	/*	if (packet[0] == Contacts.SYSTEM_INFO && packet[1] == Contacts.ZERO
				&& packet[2] == Contacts.ZERO && packet[3] == Contacts.ZERO) {
			volumeValue = ((int) packet[4] & 0xFF);
			PreferenceUtil.setVolume(MainActivity.this, volumeValue);
			if (mSeekbar != null)
				mSeekbar.setProgress(volumeValue);
		}*/
		if (packet[0] != Contacts.MODE_RADIO)
			return;
		switch (packet[2]) {
		case Contacts.FM1_FREQ:
		case Contacts.FM2_FREQ:
		case Contacts.FM3_FREQ:
		case Contacts.AM1_FREQ:
		case Contacts.AM2_FREQ:
			// 棰戠巼
			mRadioData.curFreq = ((int) packet[3] & 0xFF) << 8
					| ((int) packet[4] & 0xFF);
			// 褰撶涓変綅鍜岀鍥涗綅涓�0xff鏃跺�欒〃绀烘悳绱�
			if ((packet[3] == Contacts.BLINK) && (packet[4] == Contacts.BLINK)) {
				mRadioData.curFavDown = 100;
			}
			break;
		case Contacts.FM1_SELECT:
		case Contacts.FM2_SELECT:
		case Contacts.FM3_SELECT:
		case Contacts.AM1_SELECT:
		case Contacts.AM2_SELECT:
			// 棰戦亾鍜屽綋鍓嶉閬撶殑鎸変笅鐨勯敭
			mRadioData.curBand = ((int) packet[3] & 0xFF);
			mRadioData.curFavDown = ((int) packet[4] & 0xFF);
			break;
		case Contacts.FM1_1:
		case Contacts.FM2_1:
		case Contacts.FM3_1:
		case Contacts.AM1_1:
		case Contacts.AM2_1:
			// F1妗嗙殑鏁板��
			mRadioData.FF[0] = ((int) packet[3] & 0xFF) << 8
					| ((int) packet[4] & 0xFF);
			break;
		case Contacts.FM1_2:
		case Contacts.FM2_2:
		case Contacts.FM3_2:
		case Contacts.AM1_2:
		case Contacts.AM2_2:
			// F2妗嗙殑鏁板��
			mRadioData.FF[1] = ((int) packet[3] & 0xFF) << 8
					| ((int) packet[4] & 0xFF);
			break;
		case Contacts.FM1_3:
		case Contacts.FM2_3:
		case Contacts.FM3_3:
		case Contacts.AM1_3:
		case Contacts.AM2_3:
			// F3妗嗙殑鏁板��
			mRadioData.FF[2] = ((int) packet[3] & 0xFF) << 8
					| ((int) packet[4] & 0xFF);
			break;
		case Contacts.FM1_4:
		case Contacts.FM2_4:
		case Contacts.FM3_4:
		case Contacts.AM1_4:
		case Contacts.AM2_4:
			// F4妗嗙殑鏁板��
			mRadioData.FF[3] = ((int) packet[3] & 0xFF) << 8
					| ((int) packet[4] & 0xFF);
			break;
		case Contacts.FM1_5:
		case Contacts.FM2_5:
		case Contacts.FM3_5:
		case Contacts.AM1_5:
		case Contacts.AM2_5:
			// F5妗嗙殑鏁板��
			mRadioData.FF[4] = ((int) packet[3] & 0xFF) << 8
					| ((int) packet[4] & 0xFF);
			break;
		case Contacts.FM1_6:
		case Contacts.FM2_6:
		case Contacts.FM3_6:
		case Contacts.AM1_6:
		case Contacts.AM2_6:
			// F6妗嗙殑鏁板��
			mRadioData.FF[5] = ((int) packet[3] & 0xFF) << 8
					| ((int) packet[4] & 0xFF);
			break;
		}

		if (mRadioData.curBand < 3) {
			isFM = true;
		} else {
			isFM = false;
		}
		if (mRadioData.curFreq != 65535)
			showFrq(mRadioData.curFreq, isFM);
		updateAmFm(isFM);

		band = mRadioData.curBand;
		item = mRadioData.curFavDown - 1;

		if ((mRadioData.curBand < 3 && mRadioData.FF[0] > 5000)
				|| (mRadioData.curBand >= 3 && mRadioData.FF[0] < 5000)) {

			for (int i = 0; i < mRadioData.FF.length; i++) {
				allFqs.set(mRadioData.curBand * 6 + i, mRadioData.FF[i]);
			}
		}
		
		updatePageItem(allFqs, band, item);
	}

	@Override
	protected void onResume() {
		Trace.i("FM MainActivity onResume");
		super.onResume();
		bindSerialPortService();
	}

	@Override
	protected void onPause() {
		Trace.i("FM MainActivity onPause");
		super.onPause();
		unBindSerialPortService();
	}

	@Override
	protected void onDestroy() {
		Trace.i("FM MainActivity onDestroy");
		sendBroadcast(new Intent(ACTION_APP_CANCEL));
		saveFqDataWhenOnDestroy();
		abandonAudioFocus();
		super.onDestroy();
	}

	private void bindSerialPortService() {
		Intent intent = new Intent();
		intent.setClassName("cn.colink.serialport",
				"cn.colink.serialport.service.SerialPortService");
		bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
	}

	private void unBindSerialPortService() {
		try {
			if (mISpService != null)
				mISpService.removeCliend(mICallback);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		unbindService(mServiceConnection);
	}

	public void sendMsg(String msg) {
		try {
			if (mISpService != null) {
				mISpService.sendDataToSp(BytesUtil.addCheckBit(msg));
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Trace.i("keyCode :" + keyCode);
		switch (keyCode) {
		case KeyEvent.KEYCODE_HOME:
			Trace.i("home press");
			finish();
			break;

		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void setFqDataWhenOnCreate() {

		band = PreferenceUtil.getBand(this);
		if (band < 3) {
			isFM = true;
		} else {
			isFM = false;
		}
		item = PreferenceUtil.getFavoriteFq(this);
		PreferenceUtil.getAllFq(this, allFqs);

		int positon = band * 6 + item;
		int valueFreq = 0;
		if (isFM) {
			valueFreq = (positon >= 0 && positon < allFqs.size()) ? allFqs
					.get(positon) : PreferenceUtil.DEFAULT_FM;
		} else {
			valueFreq = (positon >= 0 && positon < allFqs.size()) ? allFqs
					.get(positon) : PreferenceUtil.DEFAULT_AM;
		}
		showFrq(valueFreq, isFM);
		updateAmFm(isFM);
		updatePageView(allFqs, band, item);
	}
	
	/**
     * 更新所有band的页面
     * @param allFqs
     * @param band
     * @param item
     */
	private void updatePageView(List<Integer> allFqs, int band, int item) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 6; j++) {
				TextView tv = ((TextView) (fmViews.get(i)
						.findViewById(PAGE_ITEM_ID[j])));
				tv.setText(String.valueOf(df2.format((float) allFqs.get(i * 6
						+ j) / 100.0f)));
				tv.setTextColor(getResources().getColor(R.color.text_normal));
				if (band == i && item == j) {
					tv.setTextColor(getResources().getColor(
							R.color.text_selected));
				}

			}
		}
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 6; j++) {
				TextView tv = ((TextView) (amViews.get(i)
						.findViewById(PAGE_ITEM_ID[j])));
				tv.setText(String.valueOf(allFqs.get(18 + i * 6 + j)));
				tv.setTextColor(getResources().getColor(R.color.text_normal));
				if ((band - 3) == i && item == j) {
					tv.setTextColor(getResources().getColor(
							R.color.text_selected));
				}
			}
		}

		Message msg = new Message();
		msg.what = Contacts.MSG_CHANGE_PAGE;
		msg.arg1 = band;
		mHandler.removeMessages(Contacts.MSG_CHANGE_PAGE);
		mHandler.sendMessage(msg);

	}
     /**
      * 更新当前band的页面
      * @param allFqs
      * @param band
      * @param item
      */
	private void updatePageItem(List<Integer> allFqs, int band, int item) {
		// TODO Auto-generated method stub
		if (band < 3) {
			for (int j = 0; j < 6; j++) {
				TextView tv = ((TextView) (fmViews.get(band)
						.findViewById(PAGE_ITEM_ID[j])));

				tv.setText(String.valueOf(df2.format((float) allFqs.get(band
						* 6 + j) / 100.0f)));
				tv.setTextColor(getResources().getColor(R.color.text_normal));
				if (item == j) {
					tv.setTextColor(getResources().getColor(
							R.color.text_selected));
				}
			}

		} else {
			for (int j = 0; j < 6; j++) {
				TextView tv = ((TextView) (amViews.get((band - 3))
						.findViewById(PAGE_ITEM_ID[j])));
				tv.setText(String.valueOf(allFqs.get(18 + (band - 3) * 6 + j)));
				tv.setTextColor(getResources().getColor(R.color.text_normal));
				if (item == j) {
					tv.setTextColor(getResources().getColor(
							R.color.text_selected));
				}
			}

		}

		Message msg = new Message();
		msg.what = Contacts.MSG_CHANGE_PAGE;
		msg.arg1 = band;
		mHandler.sendMessage(msg);

	}

	private void showFrq(int valueFreq, boolean isFM2) {
		// TODO Auto-generated method stub
		if (isFM2) {
			freqTv.setText(String.valueOf(df2
					.format((float) valueFreq / 100.0f)));
		} else {
			freqTv.setText(String.valueOf(valueFreq));
		}
	}

	private void updateAmFm(boolean isFM) {
		// TODO Auto-generated method stub
		if (isFM) {
			amButton.setAlpha(0.5f);
			fmButton.setAlpha(1f);
			unitTv.setText("MHZ");
			findViewById(R.id.am_channel_layout).setVisibility(View.GONE);
			findViewById(R.id.fm_channel_layout).setVisibility(View.VISIBLE);
			// amVp.setCurrentItem(0);
		} else {
			amButton.setAlpha(1f);
			fmButton.setAlpha(0.5f);
			unitTv.setText("KHZ");
			findViewById(R.id.am_channel_layout).setVisibility(View.VISIBLE);
			findViewById(R.id.fm_channel_layout).setVisibility(View.GONE);
			// fmVp.setCurrentItem(0);
		}

	}

	public void saveFqDataWhenOnDestroy() {
		PreferenceUtil.setBand(this, band);
		PreferenceUtil.setFavoriteFq(this, item);
		PreferenceUtil.setAllFq(this, allFqs);
	//	PreferenceUtil.setVolume(this, volumeValue);
	}

	private AudioManager mAudioManager;

	private int requestAudioFocus() {
		return getAudioManager().requestAudioFocus(
				new OnAudioFocusChangeListener() {

					@Override
					public void onAudioFocusChange(int focusChange) {
						switch (focusChange) {
						case AudioManager.AUDIOFOCUS_GAIN:

							break;
						case AudioManager.AUDIOFOCUS_LOSS:
						case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
							// sendMsg(Contacts.HEX_OTHER_MODEL);
							// finish();
							break;
						default:
							break;
						}
					}
				}, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

	}

	private void abandonAudioFocus() {
		getAudioManager().abandonAudioFocus(null);
	}

	private AudioManager getAudioManager() {
		if (mAudioManager == null) {
			mAudioManager = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
		}
		return mAudioManager;
	}

}
