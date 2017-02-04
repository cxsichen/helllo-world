package cn.lzl.partycontrol;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

import cn.colink.serialport.service.ISerialPortCallback;
import cn.colink.serialport.service.ISerialPortService;
import cn.lzl.partycontrol.presenter.PwdCheckPresenter;
import cn.lzl.partycontrol.presenter.PwdCheckPresenterImpl;
import cn.lzl.partycontrol.presenter.SettingModelPresenter;
import cn.lzl.partycontrol.presenter.SettingModelPresenterImpl;
import cn.lzl.partycontrol.utils.BytesUtil;
import cn.lzl.partycontrol.utils.Contacts;
import cn.lzl.partycontrol.utils.SpUtil;
import cn.lzl.partycontrol.utils.Trace;
import cn.lzl.partycontrol.view.MainTitleView;
import cn.lzl.partycontrol.view.PwdCheckView;
import cn.lzl.partycontrol.view.SettingModelView;

public class MainActivity extends BaseActivity implements OnClickListener,
		PwdCheckView, SettingModelView {

	private ISerialPortService mISpService;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Contacts.MSG_UPDATA_UI:
				byte[] packet = (byte[]) msg.obj;
				receiveMsg(packet);
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
			// sendMsg(Contacts.HEX_HOME_TO_PARTYCONTROLL);
			sendMsg(BytesUtil.addCheckBit(Contacts.HEX_MODEL_CONTROL));
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

	private Button mMainNum0Btn;
	private Button mMainNum1Btn;
	private Button mMainNum2Btn;
	private Button mMainNum3Btn;
	private Button mMainNum4Btn;
	private Button mMainNum5Btn;
	private Button mMainNum6Btn;
	private Button mMainNum7Btn;
	private Button mMainNum8Btn;
	private Button mMainNum9Btn;
	private Button mMainOkBtn;
	private TextView mMainPwdTv;
	private StringBuffer mStringBuffer;
	private String mPwd;
	private Button mMainSettingModelBtn;
	private Button mMainResetBtn;
	private Button mMainSaveBtn;
	private Button mMainPorwerBtn;
	private Button mMainModeBtn;
	private Button mMainVolumeBtn;
	private Button mMainNextBtn;
	private Button mMainPreBtn;
	private Button mMainVolumnAddBtn;
	private Button mMainVolumnSubBtn;
	private Button mMainPhoneAnswerBtn;
	private Button mMainPhoneDropedBtn;
	private Button mMainPauseBtn;

	private Button mMainBackBtn;
	private Button mMainHandUpBtn;
	private Button mMainMenuDownHandupBtn;
	private Button mMainMenuUpAnswerBtn;
	private Button mMainFmBtn;
	private Button mMainMusicBtn;
	private Button mMainNavBtn;

	private ImageView mMainLeftBtn;
	private ImageView mMainRightBtn;

	private PwdCheckPresenter mPwdCheckPresenter;
	private SettingModelPresenter mSettingModelPresenter;
	private DecimalFormat mNumberFormat = new DecimalFormat("000");
	private boolean isHavedReponse = false;
	public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED,
				FLAG_HOMEKEY_DISPATCHED);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		bindSpService();
	}

	@Override
	protected void onPause() {
		super.onPause();
		sendMsg(Contacts.HEX_MODEL_SAVE);
		finish();
		unbindSpService();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void initializeView() {
		mMainNum0Btn = (Button) findViewById(R.id.mMainNum0Btn);
		mMainNum1Btn = (Button) findViewById(R.id.mMainNum1Btn);
		mMainNum2Btn = (Button) findViewById(R.id.mMainNum2Btn);
		mMainNum3Btn = (Button) findViewById(R.id.mMainNum3Btn);
		mMainNum4Btn = (Button) findViewById(R.id.mMainNum4Btn);
		mMainNum5Btn = (Button) findViewById(R.id.mMainNum5Btn);
		mMainNum6Btn = (Button) findViewById(R.id.mMainNum6Btn);
		mMainNum7Btn = (Button) findViewById(R.id.mMainNum7Btn);
		mMainNum8Btn = (Button) findViewById(R.id.mMainNum8Btn);
		mMainNum9Btn = (Button) findViewById(R.id.mMainNum9Btn);
		mMainOkBtn = (Button) findViewById(R.id.mMainOkBtn);
		mMainPwdTv = (TextView) findViewById(R.id.mMainPwdTv);

		mMainNum0Btn.setOnClickListener(this);
		mMainNum1Btn.setOnClickListener(this);
		mMainNum2Btn.setOnClickListener(this);
		mMainNum3Btn.setOnClickListener(this);
		mMainNum4Btn.setOnClickListener(this);
		mMainNum5Btn.setOnClickListener(this);
		mMainNum6Btn.setOnClickListener(this);
		mMainNum7Btn.setOnClickListener(this);
		mMainNum8Btn.setOnClickListener(this);
		mMainNum9Btn.setOnClickListener(this);
		mMainOkBtn.setOnClickListener(this);

		mMainSettingModelBtn = (Button) findViewById(R.id.mMainSettingModelBtn);
		mMainResetBtn = (Button) findViewById(R.id.mMainResetBtn);
		mMainSaveBtn = (Button) findViewById(R.id.mMainSaveBtn);
		mMainPorwerBtn = (Button) findViewById(R.id.mMainPowerBtn);
		mMainModeBtn = (Button) findViewById(R.id.mMainModeBtn);
		mMainVolumeBtn = (Button) findViewById(R.id.mMainVolumeBtn);
		mMainNextBtn = (Button) findViewById(R.id.mMainNextBtn);
		mMainPreBtn = (Button) findViewById(R.id.mMainPreBtn);
		mMainVolumnAddBtn = (Button) findViewById(R.id.mMainVolumeAddBtn);
		mMainVolumnSubBtn = (Button) findViewById(R.id.mMainVolumeSubBtn);
		mMainPhoneAnswerBtn = (Button) findViewById(R.id.mMainPhoneAnswerBtn);
		mMainPhoneDropedBtn = (Button) findViewById(R.id.mMainPhoneDropedBtn);
		mMainPauseBtn = (Button) findViewById(R.id.mMainPauseBtn);
		mMainLeftBtn = (ImageView) findViewById(R.id.mMainLeftBtn);
		mMainRightBtn = (ImageView) findViewById(R.id.mMainRightBtn);

		mMainBackBtn = (Button) findViewById(R.id.mMainBackBtn);
		mMainHandUpBtn = (Button) findViewById(R.id.mMainPhoneHandupBtn);
		mMainMenuDownHandupBtn = (Button) findViewById(R.id.mMainMenuDownHandupBtn);
		mMainMenuUpAnswerBtn = (Button) findViewById(R.id.mMainMenuUpAnswerBtn);
		mMainFmBtn = (Button) findViewById(R.id.mMainFmBtn);
		mMainMusicBtn = (Button) findViewById(R.id.mMainMusicBtn);
		mMainNavBtn = (Button) findViewById(R.id.mMainNavBtn);

		mMainResetBtn.setOnClickListener(this);
		mMainSaveBtn.setOnClickListener(this);
		mMainPorwerBtn.setOnClickListener(this);
		mMainModeBtn.setOnClickListener(this);
		mMainVolumeBtn.setOnClickListener(this);
		mMainNextBtn.setOnClickListener(this);
		mMainPreBtn.setOnClickListener(this);
		mMainVolumnAddBtn.setOnClickListener(this);
		mMainVolumnSubBtn.setOnClickListener(this);
		mMainPhoneAnswerBtn.setOnClickListener(this);
		mMainPhoneDropedBtn.setOnClickListener(this);
		mMainPauseBtn.setOnClickListener(this);
		mMainLeftBtn.setOnClickListener(this);
		mMainRightBtn.setOnClickListener(this);

		mMainBackBtn.setOnClickListener(this);
		mMainHandUpBtn.setOnClickListener(this);
		mMainMenuDownHandupBtn.setOnClickListener(this);
		mMainMenuUpAnswerBtn.setOnClickListener(this);
		mMainFmBtn.setOnClickListener(this);
		mMainMusicBtn.setOnClickListener(this);
		mMainNavBtn.setOnClickListener(this);
	}

	@Override
	protected void initializeData() {
		TranslateAnimation tAnimation = new TranslateAnimation(0, 0, -100, 0);
		tAnimation.setDuration(800);
		tAnimation.startNow();
		mPwd = SpUtil.getPwd(this);
		mStringBuffer = new StringBuffer();
		mPwdCheckPresenter = new PwdCheckPresenterImpl(this);
		mSettingModelPresenter = new SettingModelPresenterImpl(this);
	}

	protected void receiveMsg(byte[] packet) {
		/*
		 * if(!isHavedReponse && packet != null){ if(packet[0] ==
		 * Contacts.SWITCH_MODE) isHavedReponse = true; }
		 * if(!isHavedReponse)return;
		 */
		if (packet[0] != Contacts.FACTORY_SETUP)
			return;
		switch (packet[1] & 0xFF) {
		case 0x10:
			// mMainSettingModelBtn.setText(getResources().
			// getStringArray(R.array.settingModel)[((int) packet[4] & 0xFF)]);
			break;
		case 0x11:
			mMainPorwerBtn.setText(onChangeValue(packet));
			break;
		case 0x12:
			mMainModeBtn.setText(onChangeValue(packet));
			break;
		case 0x13:
			mMainVolumeBtn.setText(onChangeValue(packet));
			break;
		case 0x14:
			mMainNextBtn.setText(onChangeValue(packet));
			break;
		case 0x15:
			mMainPreBtn.setText(onChangeValue(packet));
			break;
		case 0x16:
			mMainVolumnAddBtn.setText(onChangeValue(packet));
			break;
		case 0x17:
			mMainVolumnSubBtn.setText(onChangeValue(packet));
			break;
		case 0x18:
			mMainPhoneAnswerBtn.setText(onChangeValue(packet));
			break;
		case 0x19:
			mMainPhoneDropedBtn.setText(onChangeValue(packet));
			break;
		case 0x1A:
			mMainPauseBtn.setText(onChangeValue(packet));
			break;
		case 0x1B:
			mMainHandUpBtn.setText(onChangeValue(packet));
			break;
		case 0x1C:
			mMainMenuDownHandupBtn.setText(onChangeValue(packet));
			break;
		case 0x1D:
			mMainMenuUpAnswerBtn.setText(onChangeValue(packet));
			break;
		case 0x1E:
			mMainBackBtn.setText(onChangeValue(packet));
			break;
		case 0x1F:
			mMainFmBtn.setText(onChangeValue(packet));
			break;
		case 0x20:
			mMainMusicBtn.setText(onChangeValue(packet));
			break;
		case 0x21:
			mMainNavBtn.setText(onChangeValue(packet));
			break;
		default:
			break;
		}
	}

	public void sendMsg(String msg) {
		try {
			if (mISpService != null) {
				Trace.i("Sound MainActivity sendMsg");
				mISpService.sendDataToSp(msg);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mMainNum0Btn:
			mStringBuffer.append(getResources().getString(R.string.num0));
			mMainPwdTv.setText(mStringBuffer.toString());
			break;
		case R.id.mMainNum1Btn:
			mStringBuffer.append(getResources().getString(R.string.num1));
			mMainPwdTv.setText(mStringBuffer.toString());
			break;
		case R.id.mMainNum2Btn:
			mStringBuffer.append(getResources().getString(R.string.num2));
			mMainPwdTv.setText(mStringBuffer.toString());
			break;
		case R.id.mMainNum3Btn:
			mStringBuffer.append(getResources().getString(R.string.num3));
			mMainPwdTv.setText(mStringBuffer.toString());
			break;
		case R.id.mMainNum4Btn:
			mStringBuffer.append(getResources().getString(R.string.num4));
			mMainPwdTv.setText(mStringBuffer.toString());
			break;
		case R.id.mMainNum5Btn:
			mStringBuffer.append(getResources().getString(R.string.num5));
			mMainPwdTv.setText(mStringBuffer.toString());
			break;
		case R.id.mMainNum6Btn:
			mStringBuffer.append(getResources().getString(R.string.num6));
			mMainPwdTv.setText(mStringBuffer.toString());
			break;
		case R.id.mMainNum7Btn:
			mStringBuffer.append(getResources().getString(R.string.num7));
			mMainPwdTv.setText(mStringBuffer.toString());
			break;
		case R.id.mMainNum8Btn:
			mStringBuffer.append(getResources().getString(R.string.num8));
			mMainPwdTv.setText(mStringBuffer.toString());
			break;
		case R.id.mMainNum9Btn:
			mStringBuffer.append(getResources().getString(R.string.num9));
			mMainPwdTv.setText(mStringBuffer.toString());
			break;
		case R.id.mMainOkBtn:
			checkPwd(mStringBuffer.toString());
			mMainPwdTv.setText("");
			mStringBuffer.delete(0, mStringBuffer.length());
			break;
		case R.id.mMainResetBtn:
			sendMsg(BytesUtil.addCheckBit(Contacts.HEX_RESET));
			break;
		case R.id.mMainSaveBtn:
			sendMsg(Contacts.HEX_MODEL_SAVE);
			finish();
			break;
		case R.id.mMainPowerBtn:
			sendMsg(Contacts.HEX_MODEL_PORWER);
			break;
		case R.id.mMainModeBtn:
			sendMsg(Contacts.HEX_MODEL_MODE);
			break;
		case R.id.mMainVolumeBtn:
			sendMsg(Contacts.HEX_MODEL_VOLUMN);
			break;
		case R.id.mMainNextBtn:
			sendMsg(Contacts.HEX_MODEL_NEXT);
			break;
		case R.id.mMainPreBtn:
			sendMsg(Contacts.HEX_MODEL_PRE);
			break;
		case R.id.mMainVolumeAddBtn:
			sendMsg(Contacts.HEX_MODEL_VOLUMN_ADD);
			break;
		case R.id.mMainVolumeSubBtn:
			sendMsg(Contacts.HEX_MODEL_VOLUMN_SUB);
			break;
		case R.id.mMainPhoneAnswerBtn:
			sendMsg(Contacts.HEX_MODEL_PHONE_ANWSER);
			break;
		case R.id.mMainPhoneDropedBtn:
			sendMsg(Contacts.HEX_MODEL_PHONE_DROPED);
			break;
		case R.id.mMainPauseBtn:
			sendMsg(Contacts.HEX_MODEL_PAUSE);
			break;
		case R.id.mMainLeftBtn:
			mSettingModelPresenter.onChangeSettingModel(Contacts.Action.LEFT
					.value());
			break;
		case R.id.mMainRightBtn:
			mSettingModelPresenter.onChangeSettingModel(Contacts.Action.RIGHT
					.value());
			break;
		case R.id.mMainBackBtn:
			sendMsg(BytesUtil.addCheckBit(Contacts.HEX_BACK));
			break;
		case R.id.mMainPhoneHandupBtn:
			sendMsg(BytesUtil.addCheckBit(Contacts.HEX_HAND_UP));
			break;
		case R.id.mMainMenuDownHandupBtn:
			sendMsg(BytesUtil.addCheckBit(Contacts.HEX_MEUNDOWN_HANDUP));
			break;
		case R.id.mMainMenuUpAnswerBtn:
			sendMsg(BytesUtil.addCheckBit(Contacts.HEX_MEUNUP_ANSWER));
			break;
		case R.id.mMainFmBtn:
			sendMsg(BytesUtil.addCheckBit(Contacts.HEX_FM));
			break;
		case R.id.mMainMusicBtn:
			sendMsg(BytesUtil.addCheckBit(Contacts.HEX_MUSIC));
			break;
		case R.id.mMainNavBtn:
			sendMsg(BytesUtil.addCheckBit(Contacts.HEX_NAV));
			break;
		default:
			break;
		}
	}

	private void checkPwd(String pwd) {
		mPwdCheckPresenter.onCheckPwd(pwd);
	}

	@Override
	public void onResult(boolean isSuccess) {
		if (isSuccess) {
			sendMsg(Contacts.HEX_HOME_TO_PARTYCONTROLL2);
			sendMsg(Contacts.HEX_HOME_TO_PARTYCONTROLL3);
			sendMsg(Contacts.HEX_HOME_TO_PARTYCONTROLL4);
		}
	}

	@Override
	public String initPwd() {
		return mPwd;
	}

	@Override
	public void onChangeSettingModel(String msg) {
		Trace.i("Setting model msg : " + msg);
		sendMsg(msg);
	}

	public String onChangeValue(byte[] packet) {
		return mNumberFormat.format(((int) packet[2] & 0xFF)) + " "
				+ mNumberFormat.format(((int) packet[3] & 0xFF)) + " "
				+ mNumberFormat.format(((int) packet[4] & 0xFF));
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Trace.i("keyCode :" + keyCode);
		switch (keyCode) {
		case KeyEvent.KEYCODE_HOME:
			Trace.i("home press");
			sendMsg(Contacts.HEX_MODEL_SAVE);
			finish();
			break;
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		sendMsg(Contacts.HEX_MODEL_SAVE);
		finish();
	}

}
