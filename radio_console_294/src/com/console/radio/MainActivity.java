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
import com.console.radio.utils.DecoratorViewPager;
import com.console.radio.utils.DensityUtils;
import com.console.radio.utils.FMView;
import com.console.radio.utils.MyScrollView;
import com.console.radio.utils.MyScrollView.OnScrollChangedListener;
import com.console.radio.utils.PreferenceUtil;
import com.console.radio.utils.ScrollAnimation;
import com.console.radio.utils.Trace;
import com.console.radio.utils.VerticalSeekBar;
import com.console.radio.utils.VerticalSeekBar.OnProgressChangedListener;
import com.console.radio.utils.ViewPagerAdapter;
import com.console.radio.utils.VpAdapter;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
	FMView mFMView;

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
	private ImageView backButton;
	private ImageView changeLayoutButton;
	private TextView unitTv;
	private TextView bandTv;
	private TextView freqTv;
	float fmMaxValue = 109;
	float fmMinValue = 85;
	float amMaxValue = 1650;
	float amMinValue = 500;

	LayoutInflater inflater;
	private DecoratorViewPager freqVp;
	private ViewPagerAdapter freqVpAdapter;
	private List<View> freqViewList = new ArrayList<View>();
	private List<VpAdapter> vpAdapterlist = new ArrayList<VpAdapter>();

	private LinearLayout freq_layout;
	private LinearLayout channel_layout;
	private static boolean PAGECHANGELOCK = false;
	private static boolean SEARCHLOCK = false;

	private boolean isFM = true;
	private int band = 0;
	private int item = 0;
	private int DEFAULT_VALUME = 27;
	private int UNDEFAULT_VALUME = -100;
	private MyScrollView myScrollView;
	private List<Integer> allFqs = new ArrayList<Integer>();

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
					freqVp.setCurrentItem(msg.arg1);
				}
				break;
			case Contacts.MSG_PAGE_UNLOCK:
				PAGECHANGELOCK = false;
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
		changeLayoutButton = (ImageView) findViewById(R.id.change_layout_button);
		backButton = (ImageView) findViewById(R.id.back_button);
		unitTv = (TextView) findViewById(R.id.unit_tv);
		bandTv = (TextView) findViewById(R.id.band_tv);
		freqTv = (TextView) findViewById(R.id.freq_tv);
		mFMView = (FMView) findViewById(R.id.fMView);

		amfmButton.setOnClickListener(this);
		scanButton.setOnClickListener(this);
		playButton.setOnClickListener(this);
		preButton.setOnClickListener(this);
		nextButton.setOnClickListener(this);
		changeLayoutButton.setOnClickListener(this);
		backButton.setOnClickListener(this);

		preButton.setOnLongClickListener(this);
		nextButton.setOnLongClickListener(this);

		changePlayButton();
		getContentResolver().registerContentObserver(
				android.provider.Settings.System.getUriFor(Contacts.FMSTATUS),
				true, mFmStatusObserver);

		freq_layout = (LinearLayout) findViewById(R.id.freq_layout);
		channel_layout = (LinearLayout) findViewById(R.id.channel_layout);
		if (PreferenceUtil.getLayoutSetting(this) == 0) {
			freq_layout.scrollTo(0, 0);
			channel_layout.scrollTo(0, 0);
		} else {
			freq_layout.scrollTo(-550, 0);
			channel_layout.scrollTo(550, 0);
		}

		myScrollView = (MyScrollView) findViewById(R.id.channel_scrollView);
		myScrollView.setOnScrollChangedListener(new OnScrollChangedListener() {

			@Override
			public void OnItemSelected(int index) {
				// TODO Auto-generated method stub
				Message msg = new Message();
				msg.what = Contacts.MSG_CHANGE_PAGE;
				msg.arg1 = index;
				mHandler.removeMessages(Contacts.MSG_CHANGE_PAGE);
				mHandler.sendMessage(msg);
			}

			@Override
			public void OnChange(int position) {
				// TODO Auto-generated method stub

			}
		});
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

	private void changePlayButton() {
		int mode = Settings.System.getInt(getContentResolver(),
				Contacts.FMSTATUS, 0);
		if (mode == 1) {
			playButton.setImageResource(R.drawable.ic_stop);
		} else {
			playButton.setImageResource(R.drawable.ic_play);
		}
	}

	/*-------频率的viewpage设置   start--------------*/
	private void initFreqLayout() {
		// TODO Auto-generated method stub
		freqVp = (DecoratorViewPager) findViewById(R.id.freq_vp);
		for (int i = 0; i < 5; i++) {
			View view = inflater.inflate(R.layout.freq_listview, null);
			ListView listView = (ListView) view
					.findViewById(R.id.freq_listView);
			VpAdapter vpAdapter = new VpAdapter(this, allFqs, i);
			listView.setAdapter(vpAdapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					sendMsg(HEX_ITEM_STRINGS[position]);
				}
			});
			listView.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> parent,
						View view, int position, long id) {
					// TODO Auto-generated method stub
					sendMsg(HEX_ITEM_LONG_STRINGS[position]);
					return true;
				}
			});
			vpAdapterlist.add(vpAdapter);
			freqViewList.add(listView);
		}
		freqVp.setOffscreenPageLimit(2);
		freqVpAdapter = new ViewPagerAdapter(freqViewList);
		freqVp.setAdapter(freqVpAdapter);

		freqVp.setOnPageChangeListener(mFreqOnPageChangeListener);
	}

	OnPageChangeListener mFreqOnPageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
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
				case 3:
					sendMsg(Contacts.HEX_AM_BRAND_1);
					break;
				case 4:
					sendMsg(Contacts.HEX_AM_BRAND_2);
					break;
				default:
					break;
				}
			}
			SEARCHLOCK = false;
			updatePageView(allFqs, band, item);
			myScrollView.syncSeletedPosition(arg0);
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

	/*-------频率的viewpage设置   end--------------*/

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
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
		case R.id.back_button:
			finish();
			break;
		case R.id.change_layout_button:
			if (PreferenceUtil.getLayoutSetting(this) == 0) {
				PreferenceUtil.setLayoutSetting(this, 1);
				freq_layout.startAnimation(new ScrollAnimation(freq_layout,
						-550));
				channel_layout.startAnimation(new ScrollAnimation(
						channel_layout, 550));
			} else {
				PreferenceUtil.setLayoutSetting(this, 0);
				freq_layout.startAnimation(new ScrollAnimation(freq_layout, 0));
				channel_layout.startAnimation(new ScrollAnimation(
						channel_layout, 0));
			}

			break;
		default:
			break;
		}
	}
	
	public String getMsgString(final int freq, int type) {
		byte[] packet = new byte[5];
		packet[0] = Integer.valueOf("F1", 16).byteValue();
		packet[1] = Integer.valueOf("01", 16).byteValue();
		int data3 = freq / 256;
		int data4 = freq - data3 * 256;
		packet[2] = Integer.valueOf("00", 16).byteValue();
		packet[3] = Integer.valueOf(Integer.toHexString(data3), 16).byteValue();
		packet[4] = Integer.valueOf(Integer.toHexString(data4), 16).byteValue();
		return BytesUtil.bytesToHexString(packet);
	}


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
		if (packet[0] != Contacts.MODE_RADIO)
			return;
		switch (packet[2]) {
		case Contacts.FM1_FREQ:
		case Contacts.FM2_FREQ:
		case Contacts.FM3_FREQ:
		case Contacts.AM1_FREQ:
		case Contacts.AM2_FREQ:
			mRadioData.curFreq = ((int) packet[3] & 0xFF) << 8
					| ((int) packet[4] & 0xFF);
			if ((packet[3] == Contacts.BLINK) && (packet[4] == Contacts.BLINK)) {
				mRadioData.curFavDown = 100;
			}
			break;
		case Contacts.FM1_SELECT:
		case Contacts.FM2_SELECT:
		case Contacts.FM3_SELECT:
		case Contacts.AM1_SELECT:
		case Contacts.AM2_SELECT:
			mRadioData.curBand = ((int) packet[3] & 0xFF);
			mRadioData.curFavDown = ((int) packet[4] & 0xFF);
			break;
		case Contacts.FM1_1:
		case Contacts.FM2_1:
		case Contacts.FM3_1:
		case Contacts.AM1_1:
		case Contacts.AM2_1:
			mRadioData.FF[0] = ((int) packet[3] & 0xFF) << 8
					| ((int) packet[4] & 0xFF);
			break;
		case Contacts.FM1_2:
		case Contacts.FM2_2:
		case Contacts.FM3_2:
		case Contacts.AM1_2:
		case Contacts.AM2_2:
			mRadioData.FF[1] = ((int) packet[3] & 0xFF) << 8
					| ((int) packet[4] & 0xFF);
			break;
		case Contacts.FM1_3:
		case Contacts.FM2_3:
		case Contacts.FM3_3:
		case Contacts.AM1_3:
		case Contacts.AM2_3:
			mRadioData.FF[2] = ((int) packet[3] & 0xFF) << 8
					| ((int) packet[4] & 0xFF);
			break;
		case Contacts.FM1_4:
		case Contacts.FM2_4:
		case Contacts.FM3_4:
		case Contacts.AM1_4:
		case Contacts.AM2_4:
			mRadioData.FF[3] = ((int) packet[3] & 0xFF) << 8
					| ((int) packet[4] & 0xFF);
			break;
		case Contacts.FM1_5:
		case Contacts.FM2_5:
		case Contacts.FM3_5:
		case Contacts.AM1_5:
		case Contacts.AM2_5:
			mRadioData.FF[4] = ((int) packet[3] & 0xFF) << 8
					| ((int) packet[4] & 0xFF);
			break;
		case Contacts.FM1_6:
		case Contacts.FM2_6:
		case Contacts.FM3_6:
		case Contacts.AM1_6:
		case Contacts.AM2_6:
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
			showFrq(mRadioData.curFreq, mRadioData.curFreq>8000);
		updateAmFm(mRadioData.curFreq>8000);

		band = mRadioData.curBand;
		item = mRadioData.curFavDown - 1;
		if ((mRadioData.curBand < 3 && mRadioData.FF[0] > 5000)
				|| (mRadioData.curBand >= 3 && mRadioData.FF[0] < 5000)) {

			for (int i = 0; i < mRadioData.FF.length; i++) {
				if (mRadioData.FF[i] != -1)
					allFqs.set(mRadioData.curBand * 6 + i, mRadioData.FF[i]);
			}
		}
		updatePageItem(allFqs, band, item);
		if (mRadioData.curFreq != 65535) {
			if (mRadioData.curFreq >8000) {
				float fmvalue = checkFmValue(Float.parseFloat(df2
						.format((float) mRadioData.curFreq / 100.0f)));
				mFMView.setValue(fmvalue, mRadioData.curFreq >8000);
			} else {
				float fmvalue = checkAmValue(mRadioData.curFreq);
				mFMView.setValue(fmvalue, mRadioData.curFreq >8000);
			}
		}
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
		initFreqLayout();
		updatePageView(allFqs, band, item);
	}

	/**
	 * 更新所有band的页面
	 * 
	 * @param allFqs
	 * @param band
	 * @param item
	 */
	private void updatePageView(List<Integer> allFqs, int band, int item) {
		// TODO Auto-generated method stub

	/*	for (VpAdapter vpAdapter : vpAdapterlist) {
			vpAdapter.setSelectedItem(item, band);
			vpAdapter.notifyDataSetChanged();
		}

		((ListView) freqViewList.get(band)).setSelection(item);*/

		myScrollView.setSelectedItem(band);
		Message msg = new Message();
		msg.what = Contacts.MSG_CHANGE_PAGE;
		msg.arg1 = band;
		mHandler.removeMessages(Contacts.MSG_CHANGE_PAGE);
		mHandler.sendMessage(msg);

	}

	/**
	 * 更新当前band的页面
	 * 
	 * @param allFqs
	 * @param band
	 * @param item
	 */
	
	int saveBand=-1;
	int saveitem=-1;
	private void updatePageItem(List<Integer> allFqs, int band, int item) {
		// TODO Auto-generated method stub

		vpAdapterlist.get(band).setSelectedItem(item, band);
		vpAdapterlist.get(band).notifyDataSetChanged();

		if((saveBand!=band)||(saveitem!=item)){
			saveBand=band;
			saveitem=item;
			if (item >= 0 && item <= 5)
				((ListView) freqViewList.get(band)).smoothScrollToPosition(item);
		}
	
		myScrollView.setSelectedItem(band);

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
			bandTv.setText("FM");
			/*
			 * findViewById(R.id.am_channel_layout).setVisibility(View.GONE);
			 * findViewById(R.id.fm_channel_layout).setVisibility(View.VISIBLE);
			 */
		} else {
			amButton.setAlpha(1f);
			fmButton.setAlpha(0.5f);
			bandTv.setText("AM");
			/*
			 * findViewById(R.id.am_channel_layout).setVisibility(View.VISIBLE);
			 * findViewById(R.id.fm_channel_layout).setVisibility(View.GONE);
			 */
		}

	}

	public void saveFqDataWhenOnDestroy() {
		PreferenceUtil.setBand(this, band);
		PreferenceUtil.setFavoriteFq(this, item);
		PreferenceUtil.setAllFq(this, allFqs);
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

	private float checkFmValue(float value) {
		if (value > fmMaxValue)
			value = fmMaxValue;
		if (value < fmMinValue)
			value = fmMinValue;
		return value;
	}

	private float checkAmValue(float value) {
		if (value > amMaxValue)
			value = amMaxValue;
		if (value < amMinValue)
			value = amMinValue;
		return value;
	}

}
