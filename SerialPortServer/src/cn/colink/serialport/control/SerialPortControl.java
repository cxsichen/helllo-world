package cn.colink.serialport.control;

import java.lang.reflect.Field;
import java.util.List;

import cn.colink.serialport.R;
import cn.colink.serialport.TailGateActivity;
import cn.colink.serialport.service.ISerialPortService;
import cn.colink.serialport.service.SerialPortService;
import cn.colink.serialport.utils.BytesUtil;
import cn.colink.serialport.utils.CoderUtils;
import cn.colink.serialport.utils.Constact;
import cn.colink.serialport.utils.Contacts;
import cn.colink.serialport.utils.PreferenceUtil;
import cn.colink.serialport.utils.Trace;
import cn.kuwo.autosdk.api.KWAPI;
import cn.kuwo.autosdk.api.OnPlayerStatusListener;
import cn.kuwo.autosdk.api.PlayState;
import cn.kuwo.autosdk.api.PlayerStatus;
import cn.kuwo.autosdk.bean.Music;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.music.IMediaPlaybackService;

public class SerialPortControl {
	SerialPortService mSerialPortService;

	private KWAPI mKwapi;
	private IMediaPlaybackService mIMediaPlaybackService;
	public static final String ACTION_STOP_MUSIC = "com.console.STOP_MUSIC";

	int volumeValue;
	/**
	 * ��Ч����Ĭ��ֵ
	 */
	private static int treValue = 7;
	private static int midValue = 7;
	private static int basValue = 7;
	private static int rowValue = 7;
	private static int colValue = 7;
	private static boolean isNaving = false;
	private static boolean isAcconOver = true;
	private final String[] modeApplist = { "com.console.radio",
			"cn.kuwo.kwmusiccar", "com.mxtech.videoplayer.pro",
			"com.mtk.bluetooth", "com.console.auxapp", "com.console.equalizer",
			"com.autonavi.amapauto", "com.srtc.pingwang", "com.console.dtv" }; // Acc
																				// on��򿪵�Ӧ�õ��б�

	/*
	 * 0 ������ 1 ���� 2 ��Ƶ 3 ���� 4 aux 5 ��Ч 6 ���� 7 �г���¼��8���ֵ���100ϲ������
	 */
	private final int[] Modes = { 0x06, 0x04, 0x05, 0x0B, 0X09, 0x11, 0x0A,
			0x0E, 0x08 };
	private static final String BOOT_COMPLETED_ACTION = "android.intent.action.BOOT_COMPLETED";
	private static final String RADIO_FREQ_ACTION = "action.colink.startFM";
	private static final String SEND_APP_CHANGE = "com.console.sendAppChange";
	private static final String SEND_BACK_CAR_OFF = "com.console.SEND_BACK_CAR_OFF";

	private PlayerStatus mPlayerStatus = PlayerStatus.STOP;
	private static long tailGateTimeSave = 0;
	private static String appNameSave = "";

	public static Dialog mDrivingWaringDialog = null;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Contacts.MSG_UPDATA_UI:
				byte[] packet = (byte[]) msg.obj;
				// �����ڷ��͹�����һ��������
				dealWithPacket(packet);
				break;
			case Contacts.MSG_SHOW_LOGO_WINDOW:  //��ʾlogo����
				String str=android.provider.Settings.System.getString(
						mSerialPortService.getContentResolver(), Constact.ACCLOGO);
				if(str!=null){
					Bitmap bm = BitmapFactory.decodeFile(str);
					if(bm!=null){
						showFloatWindow(mSerialPortService,bm);
					}
				}
				
				break;
			case Contacts.MSG_REMOVE_LOGO_WINDOW:  //����logo����
				removeFloatWindow(mSerialPortService);
				break;
			case Contacts.MSG_FACTORY_SOUND:
				int factorySoundValue = android.provider.Settings.System
						.getInt(mSerialPortService.getContentResolver(),
								Constact.FACTORY_SOUND, 30);
				int fmPowerValue = android.provider.Settings.System.getInt(
						mSerialPortService.getContentResolver(),
						Constact.FMPOWER, 0);
				sendMsg("F755" + BytesUtil.intToHexString(factorySoundValue)
						+ "00" + BytesUtil.intToHexString(fmPowerValue));
				break;
			case Contacts.MSG_RADIO_VALUME_CHANGE:
			/*
			 * if ((PreferenceUtil.getMode(SerialPortControlService.this) == 0)
			 * || (PreferenceUtil .getMode(SerialPortControlService.this) == 4))
			 * //aux������ģʽ��ʹ��mcu����������
			 */
			{
				int value = android.provider.Settings.System.getInt(
						mSerialPortService.getContentResolver(),
						Constact.KEY_VOLUME_VALUE, 27);
				sendMsg("FD020000" + BytesUtil.intToHexString(value));
			}
				break;
			case Contacts.MSG_RADIO_FREQ_MEG:
				if (PreferenceUtil.getMode(mSerialPortService) == 0
						&& (float) msg.obj != 0)
					sendMsg(getMsgString((int) ((float) msg.obj * 100), 1));
				break;
			case Contacts.MSG_SEND_FIRST_MSG:
				int launchMode = PreferenceUtil.getMode(mSerialPortService);
				sendMsg("F00000" + BytesUtil.intToHexString(launchMode) + "01");

				/*
				 * ����ý������
				 */
				Message msg2 = new Message();
				msg2.what = Contacts.MSG_FACTORY_SOUND;
				mHandler.removeMessages(Contacts.MSG_FACTORY_SOUND);
				mHandler.sendMessageDelayed(msg2, 12 * 1000);

				checkBackCar();
				break;
			case Contacts.MSG_CHECK_MODE:
				if (PreferenceUtil.getMode(mSerialPortService) == 100) { // ϲ������
					if (PreferenceUtil.getCheckMode(mSerialPortService) == 1) { // ϲ�����Ŷ�Ӧ����
						return;
					} else {
						sendMsg("F5020000" + BytesUtil.intToHexString(1));
						mHandler.sendEmptyMessageDelayed(
								Contacts.MSG_CHECK_MODE, 2 * 1000);
						return;
					}
				}
				if (PreferenceUtil.getMode(mSerialPortService) != PreferenceUtil
						.getCheckMode(mSerialPortService)) {
					/*
					 * Toast.makeText(SerialPortControlService.this,
					 * "     ģʽ����  �����Զ�������\n ���������Ӧ�������´�Ӧ��",
					 * Toast.LENGTH_LONG).show();
					 */

					sendMsg("F5020000"
							+ BytesUtil.intToHexString(PreferenceUtil
									.getMode(mSerialPortService)));
					mHandler.sendEmptyMessageDelayed(Contacts.MSG_CHECK_MODE,
							2 * 1000);
				}

				break;
			case Contacts.MSG_BACK_CAR: // �������¼�
				// if (!getTopActivity(SerialPortControlService.this).equals(
				// "com.console.parking")) {
				Trace.m("--------MSG_BACK_CAR-----parkingState-------"
						+ isAcconOver);
				if (isAcconOver) {
					Intent intent = new Intent();
					intent.setClassName("com.console.parking",
							"com.console.parking.MainActivity");
					intent.addCategory(Intent.CATEGORY_DEFAULT);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					mSerialPortService.startActivity(intent);
				}
				break;
			/**
			 * ����app�л�ģʽ���� 0 ������ 1 ���� 2 ��Ƶ 3 ���� 4 aux 5 ��Ч 6 ���� 7
			 * �г���¼��8���ֵ���100ϲ������
			 */
			case Contacts.MSG_APP_CHANGE:
				mHandler.removeMessages(Contacts.MSG_CHECK_MODE);
				switch ((String) msg.obj) {
				case "com.console.parking": // ������ ������obd����������ģʽ�л�
				case "com.console.canreader":
				case "com.console.launcher_console":
				case "cn.coogo.hardware.service":
				case "com.android.stk":
				case "com.android.settings":
				case "com.console.nodisturb":
					break;
				case "com.console.radio":
					if (PreferenceUtil.getMode(mSerialPortService) != 0) {
						PreferenceUtil.setMode(mSerialPortService, 0);
						sendMsg("F5020000" + BytesUtil.intToHexString(0));
						// ��ͣ����
						stopMusic();

						// �е�����ģʽ��ʱ�� ����������ֵ
						int value = android.provider.Settings.System.getInt(
								mSerialPortService.getContentResolver(),
								Constact.KEY_VOLUME_VALUE, 27);
						sendMsg("FD020000" + BytesUtil.intToHexString(value));
						// ��Fmģʽ Ĭ�ϴ�fm
						Settings.System.putInt(
								mSerialPortService.getContentResolver(),
								Constact.FMSTATUS, 0);

					}
					break;
				case "cn.kuwo.kwmusiccar":
				case "com.google.android.music":
				case "com.android.music":
					if (PreferenceUtil.getMode(mSerialPortService) != 1) {
						PreferenceUtil.setMode(mSerialPortService, 1);
						sendMsg("F5020000" + BytesUtil.intToHexString(1));
					}
					break;
				case "com.mxtech.videoplayer.pro":
				case "com.google.android.videos":
				case "com.android.video":
					if (PreferenceUtil.getMode(mSerialPortService) != 2) {
						PreferenceUtil.setMode(mSerialPortService, 2);
						sendMsg("F5020000" + BytesUtil.intToHexString(2));
						// ��ͣ����
						stopMusic();
					}
					break;
				case "com.mtk.bluetooth":
					if (PreferenceUtil.getMode(mSerialPortService) != 3) {
						// ��ͣ����
						// stopKWMusic();
						PreferenceUtil.setMode(mSerialPortService, 3);
						sendMsg("F5020000" + BytesUtil.intToHexString(3));
					}
					break;
				case "com.console.auxapp":
					if (PreferenceUtil.getMode(mSerialPortService) != 4) {
						PreferenceUtil.setMode(mSerialPortService, 4);
						sendMsg("F5020000" + BytesUtil.intToHexString(4));
						// ��ͣ����
						stopMusic();
						// �е�AUXģʽ��ʱ�� ����������ֵ ��AUX������һ������ģʽ��
						int value = android.provider.Settings.System.getInt(
								mSerialPortService.getContentResolver(),
								Constact.KEY_VOLUME_VALUE, 27);
						sendMsg("FD020000" + BytesUtil.intToHexString(value));

					}
					break;
				case "com.console.equalizer":
					if (PreferenceUtil.getMode(mSerialPortService) != 5) {
						PreferenceUtil.setMode(mSerialPortService, 5);
						sendMsg("F5020000" + BytesUtil.intToHexString(5));
					}

					break;
				case "com.baidu.navi":
				case "com.autonavi.amapauto":
				case "com.here.app.maps":
				case "com.google.android.apps.maps":
				case "com.waze":
					if (PreferenceUtil.getMode(mSerialPortService) != 6) {
						PreferenceUtil.setMode(mSerialPortService, 6);
						sendMsg("F5020000" + BytesUtil.intToHexString(6));
					}

					break;
				case "com.srtc.pingwang":
				case "com.xair.h264demo":
				case "com.cam.dod":
					if (PreferenceUtil.getMode(mSerialPortService) != 7) {
						PreferenceUtil.setMode(mSerialPortService, 7);
						sendMsg("F5020000" + BytesUtil.intToHexString(7));
					}
					break;
				case "com.console.dtv":
					if (PreferenceUtil.getMode(mSerialPortService) != 8) {
						PreferenceUtil.setMode(mSerialPortService, 8);
						sendMsg("F5020000" + BytesUtil.intToHexString(8));
					}
					break;
				case "com.ximalaya.ting.android.car": // ϲ�����Ŷ�Ӧ����
					if (PreferenceUtil.getMode(mSerialPortService) != 100) {
						PreferenceUtil.setMode(mSerialPortService, 100);
						sendMsg("F5020000" + BytesUtil.intToHexString(1));
					}
					break;
				default:
					if (PreferenceUtil.getMode(mSerialPortService) != 1) { // ������Ӧ��Ĭ������ģʽ
						PreferenceUtil.setMode(mSerialPortService, 1);
						sendMsg("F5020000" + BytesUtil.intToHexString(1));
					}
					break;
				}
				// У��ģʽ״̬
				mHandler.sendEmptyMessageDelayed(Contacts.MSG_CHECK_MODE,
						2 * 1000);

				// ����ģʽ
				if (PreferenceUtil.getMode(mSerialPortService) != 0) {
					Settings.System.putInt(
							mSerialPortService.getContentResolver(),
							Constact.FMSTATUS, 1);
				}

				break;
			/**
			 * ACC ONһ�������׶�״̬ 1 ��ʼ��ֵ����������� 2 ����ģʽ���� 3 ��⵹��
			 */
			case Contacts.MSG_ACCON_MSG:
				isAcconOver = false;
				/*
				 * ����������
				 */
				Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);

				mHomeIntent.addCategory(Intent.CATEGORY_HOME);
				mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
				mSerialPortService.startActivity(mHomeIntent);
				/**
				 * ��ʾlogo����
				 */
				mHandler.sendEmptyMessage(Contacts.MSG_SHOW_LOGO_WINDOW);
				/*
				 * ����canЭ�����
				 */
				try {
					Intent canIntent = new Intent(
							"com.console.canreader.service.CanService");
					canIntent.setPackage("com.console.canreader");
					mSerialPortService.startService(canIntent);
				} catch (Exception e) {
					// TODO: handle exception
				}
				/*
				 * ������Ч�ĸߵ���ֵ
				 */
				sendEquValue();

				/*
				 * ����ý������
				 */
				Message msg1 = new Message();
				msg1.what = Contacts.MSG_FACTORY_SOUND;
				mHandler.removeMessages(Contacts.MSG_FACTORY_SOUND);
				mHandler.sendMessageDelayed(msg1, 12 * 1000);

				/*
				 * acc on ���Զ�����֮ǰ������ģʽ��Ӧ��app
				 */
				mHandler.sendEmptyMessageDelayed(Contacts.MSG_ACCON_MSG_1, 2000);

				break;
			case Contacts.MSG_ACCON_MSG_1:
				int mode = PreferenceUtil.getMode(mSerialPortService);
				Trace.m("--------MSG_ACCON_MSG_1------mode-------" + mode);
				sendMsg("F5020000" + BytesUtil.intToHexString(mode));
				startModeActivty(mode);
				mHandler.sendEmptyMessageDelayed(Contacts.MSG_ACCON_MSG_2, 6000);
				break;
			case Contacts.MSG_ACCON_MSG_2:
				int parkingState = android.provider.Settings.System.getInt(
						mSerialPortService.getContentResolver(),
						Constact.BACK_CAR, 0);
				Trace.m("--------MSG_ACCON_MSG_2------parkingState-------"
						+ parkingState);
				if (parkingState == 1) {
					Intent intent = new Intent();
					intent.setClassName("com.console.parking",
							"com.console.parking.MainActivity");
					intent.addCategory(Intent.CATEGORY_DEFAULT);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					mSerialPortService.startActivity(intent);
				}
				isAcconOver = true;
				break;
			default:
				break;
			}
		}
	};

	/**
	 * ������Ч�ĸߵ���ֵ
	 */
	private void sendEquValue() {
		int[] values = PreferenceUtil.getEquValue(mSerialPortService, basValue,
				midValue, treValue, rowValue, colValue);
		basValue = values[0];
		midValue = values[1];
		treValue = values[2];
		rowValue = values[3];
		colValue = values[4];
		sendMsg(BytesUtil.makeEfMsg(basValue, midValue, treValue, rowValue,
				colValue));
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

	private void startModeActivty(int mode) {
		// TODO Auto-generated method stub
		// if when acc off ,the naving is process
		if (isNaving) {
			mode = 6;
		}
		switch (mode) {
		case 1: // ����
			try {
				if (appNameSave.equals("cn.kuwo.kwmusiccar")) {
					startMusic();
				}
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			break;
		case 2: // ��Ƶ
			try {
				startVideo();
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			break;
		case 6:
			try {
				startNavi();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case 7: // ������ͷ��app
			try {
				// if (MainActivity.isAppInstalled(this, MainActivity.RECAPP_3))
				// {
				// Intent recIntent3 = getPackageManager()
				// .getLaunchIntentForPackage(MainActivity.RECAPP_3);
				// recIntent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				// startActivity(recIntent3);
				// } else if (MainActivity.isAppInstalled(this,
				// MainActivity.RECAPP_2)) {
				// Intent recIntent2 = getPackageManager()
				// .getLaunchIntentForPackage(MainActivity.RECAPP_2);
				// recIntent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				// startActivity(recIntent2);
				// } else if (MainActivity.isAppInstalled(this,
				// MainActivity.RECAPP_1)) {
				// Intent recIntent = getPackageManager()
				// .getLaunchIntentForPackage(MainActivity.RECAPP_1);
				// recIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				// startActivity(recIntent);
				// }
				Trace.m("--------MSG_ACCON_MSG---startModeActivty(7)-----");
				startMusic();
			} catch (Exception e) {
				// TODO: handle exception
			}
			break;
		case 100:// ϲ������
			try {
				Intent xmlyIntent = mSerialPortService.getPackageManager()
						.getLaunchIntentForPackage(
								"com.ximalaya.ting.android.car");
				mSerialPortService.startActivity(xmlyIntent);
			} catch (Exception e) {
				// TODO: handle exception
			}
			break;
		default:
			Trace.m("--------MSG_ACCON_MSG------default-------"
					+ modeApplist[mode]);
			openApplication(mSerialPortService, modeApplist[mode]);
			break;
		}

	}

	private void startVideo() throws Exception {
		if (checkLocale("CN")) {
			Trace.m("--------SerialPortServer------startVideo-------");
			openApplication(mSerialPortService, "com.mxtech.videoplayer.pro");
		} else {
			openApplication(mSerialPortService, "com.android.video");
		}
	}

	private void startMusic() throws Exception {
		if (checkLocale("CN")) {
			Trace.m("--------SerialPortServer------startMusic-------");
			mKwapi.startAPP(mSerialPortService, true);
		} else {
			openApplication(mSerialPortService, "com.android.music");
		}
	}

	private void startNavi() throws Exception {
		// TODO Auto-generated method stub
		int mapType = Settings.System.getInt(
				mSerialPortService.getContentResolver(), Constact.MAP_INDEX, 0);
		if (checkLocale("CN")) {
			if (mapType == 0) {
				Intent naviIntent = mSerialPortService.getPackageManager()
						.getLaunchIntentForPackage("com.baidu.navi");
				naviIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mSerialPortService.startActivity(naviIntent);
			} else {
				Intent naviIntent = mSerialPortService.getPackageManager()
						.getLaunchIntentForPackage("com.autonavi.amapauto");
				naviIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mSerialPortService.startActivity(naviIntent);
			}
		} else {
			if (mapType == 0) {
				Intent naviIntent = mSerialPortService.getPackageManager()
						.getLaunchIntentForPackage("com.here.app.maps");
				mSerialPortService.startActivity(naviIntent);
			} else if (mapType == 1) {
				Intent naviIntent = mSerialPortService.getPackageManager()
						.getLaunchIntentForPackage(
								"com.google.android.apps.maps");
				mSerialPortService.startActivity(naviIntent);
			} else if (mapType == 2) {
				Intent naviIntent = mSerialPortService.getPackageManager()
						.getLaunchIntentForPackage("com.waze");
				mSerialPortService.startActivity(naviIntent);
			}
		}

	}

	public static boolean openApplication(Context context, String pkgName) {
		if (TextUtils.isEmpty(pkgName)) {
			return false;
		}

		Intent intent = context.getPackageManager().getLaunchIntentForPackage(
				pkgName);
		if (intent == null) {
			return false;
		}
		Trace.m("====SerialPortService===openApplication==========" + pkgName);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
		return true;
	}

	int[] version = new int[20];

	private void dealWithPacket(byte[] packet) {
		// TODO Auto-generated method stub
		switch (packet[0]) {
		case Contacts.STATUS: // ��������
			if (Settings.System.getInt(mSerialPortService.getContentResolver(),
					Constact.DETECTCHANGE, 0) == 0) { // β�ź���ɲ״̬��ѡ�� 0����ɲ 1��β��
				if (android.provider.Settings.System.getString(
						mSerialPortService.getContentResolver(),
						Constact.APPLIST).equals("com.mxtech.videoplayer.pro")) {
					int hand_brake_status = (int) ((packet[1] >> 1) & 0x01); // ��ɲ״̬
					if (hand_brake_status == 1) {
						if (mDrivingWaringDialog == null)
							mDrivingWaringDialog = initDialog("��ʻ��������ر���Ƶ");
						if (!mDrivingWaringDialog.isShowing()) {
							mDrivingWaringDialog
									.getWindow()
									.setType(
											WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
							mDrivingWaringDialog.show();
						}
					}
				} else {
					if (mDrivingWaringDialog != null) {
						if (mDrivingWaringDialog.isShowing()) {
							mDrivingWaringDialog.dismiss();
							mDrivingWaringDialog = null;
						}
					}
				}
			} else {
				int tail_door_status = (int) ((packet[1] >> 1) & 0x01); // β��״̬
				if (tail_door_status != Settings.System.getInt(
						mSerialPortService.getContentResolver(),
						Constact.TAILDOORSTATUS, 0)) {
					Settings.System.putInt(
							mSerialPortService.getContentResolver(),
							Constact.TAILDOORSTATUS, tail_door_status);
					/*
					 * if(tail_door_status==1){
					 * openApplication(mSerialPortService,
					 * "com.console.TailGate"); }
					 */
				}
			}

			break;
		case Contacts.RADIO_MSG: // Fmģʽ����״̬
			if (packet[2] == (int) 0x01) {
				Settings.System.putInt(mSerialPortService.getContentResolver(),
						Constact.FMSTATUS, 1);
			} else {
				Settings.System.putInt(mSerialPortService.getContentResolver(),
						Constact.FMSTATUS, 0);
			}
			break;
		case Contacts.SWITCH_MODE:
			if (packet[1] == Contacts.ZERO && packet[2] == Contacts.ZERO
					&& packet[3] == Contacts.ZERO) {
				if (packet[4] == Contacts.BACK_CAR) {
					// MCU�������ĵ����ź�
					Trace.m("==========Contacts.BACK_CAR==========="
							+ android.provider.Settings.System.getInt(
									mSerialPortService.getContentResolver(),
									Constact.BACK_CAR, 1));
					if (android.provider.Settings.System.getInt(
							mSerialPortService.getContentResolver(),
							Constact.BACK_CAR, 1) != 1) {
						android.provider.Settings.System.putInt(
								mSerialPortService.getContentResolver(),
								Constact.BACK_CAR, 1);
					}

				} else if (packet[4] == Contacts.BACK_CAR_OFF) {
					// MCU�������ĵ��������ź�
					Trace.m("==========Contacts.BACK_CAR_OFF==========="
							+ android.provider.Settings.System.getInt(
									mSerialPortService.getContentResolver(),
									Constact.BACK_CAR, 1));
					if (android.provider.Settings.System.getInt(
							mSerialPortService.getContentResolver(),
							Constact.BACK_CAR, 0) != 0) {
						android.provider.Settings.System.putInt(
								mSerialPortService.getContentResolver(),
								Constact.BACK_CAR, 0);

						Intent intent = new Intent();
						intent.setAction(SEND_BACK_CAR_OFF);
						mSerialPortService.sendBroadcast(intent);
					}
				} else {
					// MCU��������ģʽУ��
					for (int i = 0; i < Modes.length; i++) {
						if (packet[4] == Modes[i]) {
							// PreferenceUtil.setMode(context, i);
							PreferenceUtil.setCheckMode(mSerialPortService, i);
						}
					}
				}
			}
			break;
		case Contacts.BACKLIGHT:// ������Ϣ
			if (packet[1] == Contacts.ONE && packet[2] == Contacts.ZERO // ��ƿ������ͱ���
					&& packet[3] == Contacts.ZERO && packet[4] == Contacts.ZERO) {
				changeBackLight(true);

			} else if (packet[1] == Contacts.ZERO && packet[2] == Contacts.ZERO // ��ƹرջָ�����
					&& packet[3] == Contacts.ZERO && packet[4] == Contacts.ZERO) {
				changeBackLight(false);

			}
			break;
		case Contacts.VERSION_0: // ��ȡmcu�汾��
			version[0] = packet[1] & 0xff;
			version[1] = packet[2] & 0xff;
			version[2] = packet[3] & 0xff;
			version[3] = packet[4] & 0xff;
			Settings.System.putString(mSerialPortService.getContentResolver(),
					Constact.MCU_VERSION, CoderUtils.ascii2String(version));
			break;
		case Contacts.VERSION_1:
			version[4] = packet[1] & 0xff;
			version[5] = packet[2] & 0xff;
			version[6] = packet[3] & 0xff;
			version[7] = packet[4] & 0xff;
			Settings.System.putString(mSerialPortService.getContentResolver(),
					Constact.MCU_VERSION, CoderUtils.ascii2String(version));
			break;
		case Contacts.VERSION_2:
			version[8] = packet[1] & 0xff;
			version[9] = packet[2] & 0xff;
			version[10] = packet[3] & 0xff;
			version[11] = packet[4] & 0xff;
			Settings.System.putString(mSerialPortService.getContentResolver(),
					Constact.MCU_VERSION, CoderUtils.ascii2String(version));
			break;
		case Contacts.VERSION_3:
			version[12] = packet[1] & 0xff;
			version[13] = packet[2] & 0xff;
			version[14] = packet[3] & 0xff;
			version[15] = packet[4] & 0xff;
			Settings.System.putString(mSerialPortService.getContentResolver(),
					Constact.MCU_VERSION, CoderUtils.ascii2String(version));
			break;
		case Contacts.VERSION_4:
			version[16] = packet[1] & 0xff;
			version[17] = packet[2] & 0xff;
			version[18] = packet[3] & 0xff;
			version[19] = packet[4] & 0xff;
			Settings.System.putString(mSerialPortService.getContentResolver(),
					Constact.MCU_VERSION, CoderUtils.ascii2String(version));
			break;
		default:
			break;
		}

	}

	/**
	 * @param down
	 *            down Ϊtrue ��ƿ������ͱ��� down Ϊfalse ��ƹرջָ�����
	 */
	private void changeBackLight(Boolean down) {
		// TODO Auto-generated method stub
		if (down) {
			try {
				int value = Settings.System.getInt(
						mSerialPortService.getContentResolver(),
						Settings.System.SCREEN_BRIGHTNESS, 0);
				Trace.m("=========SCREEN_BRIGHTNESS=====value=========="
						+ value);
				if (value > Constact.DEFAULT_BRIGHTNESS) {
					Settings.System.putInt(
							mSerialPortService.getContentResolver(),
							Constact.USER_SAVE_BRIGHTNESS, value);
					Settings.System.putInt(
							mSerialPortService.getContentResolver(),
							Settings.System.SCREEN_BRIGHTNESS,
							Constact.DEFAULT_BRIGHTNESS);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			try {
				int value = Settings.System.getInt(
						mSerialPortService.getContentResolver(),
						Constact.USER_SAVE_BRIGHTNESS, 0);
				Trace.m("=========USER_SAVE_BRIGHTNESS=====value=========="
						+ value);
				if (value > Constact.DEFAULT_BRIGHTNESS) {
					Settings.System.putInt(
							mSerialPortService.getContentResolver(),
							Settings.System.SCREEN_BRIGHTNESS, value);
					Settings.System.putInt(
							mSerialPortService.getContentResolver(),
							Constact.USER_SAVE_BRIGHTNESS, 0);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public SerialPortControl(SerialPortService mSerialPortService) {
		// TODO Auto-generated constructor stub
		this.mSerialPortService = mSerialPortService;
		doRegisterReceiver();

		doRegisterContentObserver();
		// checkBackCar();
		initKw();
		initMusicService();
	}

	private void checkBackCar() {
		// TODO Auto-generated method stub
		/*
		 * �������
		 */
		int backstate = android.provider.Settings.System.getInt(
				mSerialPortService.getContentResolver(), Constact.BACK_CAR, 0);
		Trace.m("====MSG_SEND_FIRST_MSG=backstate====" + backstate);
		handleBackCar(backstate);
	}

	private BroadcastReceiver myReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			switch (intent.getAction()) {
			case BOOT_COMPLETED_ACTION:
				Message msg = new Message();
				msg.what = Contacts.MSG_SEND_FIRST_MSG;
				mHandler.sendMessageDelayed(msg, 1000 * 5);
				break;
			case RADIO_FREQ_ACTION:
				float freq = intent.getFloatExtra("fm_fq", 0);
				Trace.m("--------RADIO_FREQ_ACTION----freq----" + freq);
				Message freqmsg = new Message();
				freqmsg.what = Contacts.MSG_RADIO_FREQ_MEG;
				freqmsg.obj = freq;
				mHandler.sendMessageDelayed(freqmsg, 1000);
				break;
			default:
				break;
			}
		}
	};

	private void doRegisterContentObserver() {
		// TODO Auto-generated method stub
		// �����������fm״̬
		mSerialPortService.getContentResolver().registerContentObserver(
				android.provider.Settings.System.getUriFor(Constact.FM_SWITCH),
				true, mVoiceFmControlObserver);

		// ���acc״̬
		mSerialPortService.getContentResolver().registerContentObserver(
				android.provider.Settings.System.getUriFor(Constact.ACC_STATE),
				true, mAccStateObserver);
		// ���������״̬
		mSerialPortService.getContentResolver().registerContentObserver(
				android.provider.Settings.System.getUriFor(Constact.TTS_SHOW),
				true, mTTSShowObserver);
		mSerialPortService.getContentResolver().registerContentObserver(
				android.provider.Settings.System.getUriFor(Constact.BACK_CAR),
				true, mBackCarObserver);
		mSerialPortService.getContentResolver().registerContentObserver(
				android.provider.Settings.System.getUriFor(Constact.APPLIST),
				true, mAPPObserver);

		volumeValue = android.provider.Settings.System.getInt(
				mSerialPortService.getContentResolver(),
				Constact.KEY_VOLUME_VALUE, 27);
		mSerialPortService
				.getContentResolver()
				.registerContentObserver(
						android.provider.Settings.System
								.getUriFor(Constact.KEY_VOLUME_VALUE),
						true, mValumeObserver);
		mSerialPortService
				.getContentResolver()
				.registerContentObserver(
						android.provider.Settings.System
								.getUriFor(Constact.FACTORY_SOUND),
						true, mFactorySoundObserver);

		mSerialPortService.getContentResolver().registerContentObserver(
				android.provider.Settings.System.getUriFor(Constact.FMPOWER),
				true, mFmPowerObserver);
	}

	/**
	 * �������ߵ�Դ
	 */
	private FmPowerObserver mFmPowerObserver = new FmPowerObserver();

	public class FmPowerObserver extends ContentObserver {
		public FmPowerObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			Message msg = new Message();
			msg.what = Contacts.MSG_FACTORY_SOUND;
			mHandler.removeMessages(Contacts.MSG_FACTORY_SOUND);
			mHandler.sendMessageDelayed(msg, 100);
		}
	}

	/**
	 * ý������
	 */
	private FactorySoundObserver mFactorySoundObserver = new FactorySoundObserver();

	public class FactorySoundObserver extends ContentObserver {
		public FactorySoundObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			Message msg = new Message();
			msg.what = Contacts.MSG_FACTORY_SOUND;
			mHandler.removeMessages(Contacts.MSG_FACTORY_SOUND);
			mHandler.sendMessageDelayed(msg, 100);

		}
	}

	private ValumeObserver mValumeObserver = new ValumeObserver();

	public class ValumeObserver extends ContentObserver {
		public ValumeObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			int value = android.provider.Settings.System.getInt(
					mSerialPortService.getContentResolver(),
					Constact.KEY_VOLUME_VALUE, 27);
			if (value != volumeValue) {
				volumeValue = value;
				Message msg = new Message();
				msg.what = Contacts.MSG_RADIO_VALUME_CHANGE; // ��������������
				msg.arg1 = volumeValue;
				mHandler.removeMessages(Contacts.MSG_RADIO_VALUME_CHANGE);
				mHandler.sendMessageDelayed(msg, 100);
			}
		}
	}

	private APPObserver mAPPObserver = new APPObserver();

	public class APPObserver extends ContentObserver {
		public APPObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);

			String appName = android.provider.Settings.System.getString(
					mSerialPortService.getContentResolver(), Constact.APPLIST);
			handleAPPChange(appName);

			if (android.provider.Settings.System.getInt(
					mSerialPortService.getContentResolver(),
					Constact.ACC_STATE, 0) == 1) {
				if (appName != "cn.coogo.hardware.service") {
					appNameSave = appName;
				}
			}
			Trace.m("==========mAPPObserver================" + appName);
		}
	}

	private void handleAPPChange(String appName) {
		// TODO Auto-generated method stub
		RADIOWAKE = false;
		AUXWAKE = false;
		EQUWAKE = false;
		// Ϊ����Ӧ���л�����ͼ�����ǲ���Ҫ�л�ģʽ�������Ԥ�����ñ�־λ����������һ��ģʽ�л���
		if (Settings.System.getInt(mSerialPortService.getContentResolver(),
				Constact.AVOIDCONSOLEMODE, 0) == 1) {
			Settings.System.putInt(mSerialPortService.getContentResolver(),
					Constact.AVOIDCONSOLEMODE, 0);
		} else {
			Message msg = new Message();
			msg.what = Contacts.MSG_APP_CHANGE;
			msg.obj = appName;
			mHandler.sendMessage(msg);
		}
	}

	private BackCarObserver mBackCarObserver = new BackCarObserver();

	public class BackCarObserver extends ContentObserver {
		public BackCarObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			int state = android.provider.Settings.System.getInt(
					mSerialPortService.getContentResolver(), Constact.BACK_CAR,
					0);
			Trace.m("========handleBackCar======" + state);
			handleBackCar(state);
		}
	}

	private void handleBackCar(int state) {
		// TODO Auto-generated method stub
		if (state == 1) {
			mHandler.sendEmptyMessage(Contacts.MSG_BACK_CAR);
		} else {
			mHandler.removeMessages(Contacts.MSG_BACK_CAR);
		}
	}

	public static String getTopActivity(Context context) {
		if (context == null) {
			return null;
		}
		ActivityManager am = (ActivityManager) context
				.getSystemService(Activity.ACTIVITY_SERVICE);
		if (am == null) {
			return null;
		}
		if (Build.VERSION.SDK_INT <= 20) {
			List<RunningTaskInfo> tasks = am.getRunningTasks(1);
			if (tasks != null && !tasks.isEmpty()) {
				ComponentName componentName = tasks.get(0).topActivity;
				if (componentName != null) {
					return componentName.getClassName();
				}
			}
		} else {
			RunningAppProcessInfo currentInfo = null;
			Field field = null;
			int START_TASK_TO_FRONT = 2;
			int PROCESS_STATE_PERSISTENT_UI = 1;
			String pkgName = null;
			try {
				field = RunningAppProcessInfo.class
						.getDeclaredField("processState");
			} catch (Exception e) {
				return null;
			}
			List<RunningAppProcessInfo> appList = am.getRunningAppProcesses();
			if (appList == null || appList.isEmpty()) {
				return null;
			}
			for (RunningAppProcessInfo app : appList) {
				if (app != null
						&& app.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
					Integer state = null;
					try {
						state = field.getInt(app);
					} catch (Exception e) {
						return null;
					}
					if (state != null
							&& (state == START_TASK_TO_FRONT || state == PROCESS_STATE_PERSISTENT_UI)) {
						currentInfo = app;
						break;
					}
				}
			}
			if (currentInfo != null) {
				pkgName = currentInfo.processName;
			}
			return pkgName;
		}
		return null;
	}

	private TTSShowObserver mTTSShowObserver = new TTSShowObserver();

	public class TTSShowObserver extends ContentObserver {
		public TTSShowObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			int state = android.provider.Settings.System.getInt(
					mSerialPortService.getContentResolver(), Constact.TTS_SHOW,
					0);
			handleTTS(state);
		}
	}

	Boolean RADIOWAKE = false;
	Boolean AUXWAKE = false;
	Boolean EQUWAKE = false;

	private void handleTTS(int state) {
		// TODO Auto-generated method stub
		int mode = android.provider.Settings.System.getInt(
				mSerialPortService.getContentResolver(), Constact.MODE, 0);
		if (state == 1 && mode == 0) { // state=1 ���Ա����� mode=0 ��������
			RADIOWAKE = true;
			PreferenceUtil.setMode(mSerialPortService, 1);
			sendMsg(Contacts.MUSIC_MODE);
		}
		if (state == 0 && RADIOWAKE) {
			RADIOWAKE = false;
			PreferenceUtil.setMode(mSerialPortService, 0);
			sendMsg(Contacts.RADIO_MODE);
		}

		if (state == 1 && mode == 4) { // state=1 ���Ա����� mode=4 AUX����
			AUXWAKE = true;
			PreferenceUtil.setMode(mSerialPortService, 1);
			sendMsg(Contacts.MUSIC_MODE);
		}
		if (state == 0 && AUXWAKE) {
			AUXWAKE = false;
			PreferenceUtil.setMode(mSerialPortService, 4);
			sendMsg(Contacts.AUX_MODE);
		}

		if (state == 1 && mode == 5) { // state=1 ���Ա����� mode=5 ��Ч����
			EQUWAKE = true;
			PreferenceUtil.setMode(mSerialPortService, 1);
			sendMsg(Contacts.MUSIC_MODE);
		}
		if (state == 0 && EQUWAKE) {
			EQUWAKE = false;
			PreferenceUtil.setMode(mSerialPortService, 5);
			sendMsg(Contacts.EQUALIZER_MODE);
		}
	}

	// �����������fm״̬
	private VoiceFmControlObserver mVoiceFmControlObserver = new VoiceFmControlObserver();

	public class VoiceFmControlObserver extends ContentObserver {
		public VoiceFmControlObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			int state = android.provider.Settings.System.getInt(
					mSerialPortService.getContentResolver(),
					Constact.FM_SWITCH, 0);
			handleFmSwitchState(state);
		}
	}

	private void handleFmSwitchState(int state) {
		// TODO Auto-generated method stub
		int mode = android.provider.Settings.System.getInt(
				mSerialPortService.getContentResolver(), Constact.MODE, 0);
		if (mode != 0) {
			if (state == 0)
				openApplication(mSerialPortService, "com.console.radio");
		} else {
			if (state == Settings.System.getInt(
					mSerialPortService.getContentResolver(), Constact.FMSTATUS,
					0)) {
				sendMsg(Contacts.FM_PLAY);
			}
		}
	}

	// ���acc״̬
	private AccStateObserver mAccStateObserver = new AccStateObserver();

	public class AccStateObserver extends ContentObserver {
		public AccStateObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			int state = android.provider.Settings.System.getInt(
					mSerialPortService.getContentResolver(),
					Constact.ACC_STATE, 0);
			handleAccState(state);
		}
	}

	private void handleAccState(int state) {
		// TODO Auto-generated method stub
		if (state == 1) {
			mHandler.removeMessages(Contacts.MSG_ACCON_MSG);
			mHandler.removeMessages(Contacts.MSG_ACCON_MSG_1);
			mHandler.removeMessages(Contacts.MSG_ACCON_MSG_2);
			mHandler.sendEmptyMessageDelayed(Contacts.MSG_ACCON_MSG, 500);
		} else {
			mHandler.sendEmptyMessage(Contacts.MSG_REMOVE_LOGO_WINDOW);
			mHandler.removeMessages(Contacts.MSG_ACCON_MSG);
			mHandler.removeMessages(Contacts.MSG_ACCON_MSG_1);
			mHandler.removeMessages(Contacts.MSG_ACCON_MSG_2);
			mHandler.removeMessages(Contacts.MSG_FACTORY_SOUND);
			/*
			 * �Ƿ��ڵ���״̬
			 */
			isNaving = (android.provider.Settings.System.getInt(
					mSerialPortService.getContentResolver(),
					Constact.NAVING_STATUS, 0) == 1);
		}
	}

	private void doRegisterReceiver() {
		// TODO Auto-generated method stub
		IntentFilter filter = new IntentFilter();
		filter.addAction(BOOT_COMPLETED_ACTION);
		filter.addAction(RADIO_FREQ_ACTION);
		mSerialPortService.registerReceiver(myReceiver, filter);
	}

	public void sendMsg(String msg) {
		try {
			if (mSerialPortService != null) {
				mSerialPortService.sendMsg(BytesUtil.addCheckBit(msg));
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void onDestroy() {
		mSerialPortService.getContentResolver().unregisterContentObserver(
				mAccStateObserver);
		mSerialPortService.getContentResolver().unregisterContentObserver(
				mTTSShowObserver);
		mSerialPortService.getContentResolver().unregisterContentObserver(
				mBackCarObserver);
		mSerialPortService.getContentResolver().unregisterContentObserver(
				mAPPObserver);
		mSerialPortService.getContentResolver().unregisterContentObserver(
				mValumeObserver);
		mSerialPortService.getContentResolver().unregisterContentObserver(
				mFactorySoundObserver);
		mSerialPortService.getContentResolver().unregisterContentObserver(
				mFmPowerObserver);
		mSerialPortService.getContentResolver().unregisterContentObserver(
				mVoiceFmControlObserver);
		if (mKwapi != null)
			mKwapi.unRegisterPlayerStatusListener(mSerialPortService);
		if (mIMediaPlaybackService != null) {
			mSerialPortService.unbindService(mServiceConnection);
			mSerialPortService = null;
		}

	}

	// service�������Ĵ�������
	public void deal(byte[] mPacket) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		msg.what = Contacts.MSG_UPDATA_UI;
		msg.obj = mPacket;
		mHandler.sendMessage(msg);
	}

	public void dealCommand(String command) {
		// TODO Auto-generated method stub
		int mode = PreferenceUtil.getMode(mSerialPortService);
		switch (command) {
		// �򿪺͹ر�β�ŵ�����
		case Constact.ACTION_TAILGATE_CHANGE:
			if (System.currentTimeMillis() - tailGateTimeSave > 2 * 1000) {
				tailGateTimeSave = System.currentTimeMillis();
				sendMsg(Contacts.HEX_TAILGATE_CHANGE);
			}
			break;
		case Constact.ACTION_RADIO_MENU_UP:
			if (mode == 0) { // FMģʽ
				sendMsg(Contacts.HEX_PRE_SHORT_MOVE);
			}
			break;
		case Constact.ACTION_RADIO_MENU_DOWN:
			if (mode == 0) { // FMģʽ
				sendMsg(Contacts.HEX_NEXT_SHORT_MOVE);
			}
			break;
		case Constact.ACTION_RADIO_PLAY_PAUSE:
			if (mode == 0) { // FMģʽ
				sendMsg(Contacts.FM_PLAY);
			}
			break;
		case Constact.ACTION_MENU_LONG_UP:
			if (mode == 0) { // FMģʽ
				sendMsg(Contacts.FM_PRE);
			}
			break;
		case Constact.ACTION_MENU_LONG_DOWN:
			if (mode == 0) { // FMģʽ
				sendMsg(Contacts.FM_NEXT);
			}
			break;
		case Constact.ACTION_STOP_MUSIC:
			stopMusic();
			break;
		case Constact.ACTION_MUSIC_START:
			try {
				startMusic();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	/*--------------local music contorl--------------------*/
	private void initMusicService() {
		// TODO Auto-generated method stub
		try {
			Intent intent = new Intent();
			intent.setClassName("com.android.music",
					"com.android.music.MediaPlaybackService");
			mSerialPortService.bindService(intent, mServiceConnection,
					mSerialPortService.BIND_AUTO_CREATE);
		} catch (Exception e) {
			Log.i("cxs", "==========e=======" + e);
			e.printStackTrace();
		}
	}

	private ServiceConnection mServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mIMediaPlaybackService = IMediaPlaybackService.Stub
					.asInterface(service);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mIMediaPlaybackService = null;
		}
	};

	/*---------------����control-------------------------*/
	private void initKw() {
		// TODO Auto-generated method stub
		mKwapi = KWAPI.createKWAPI(mSerialPortService, "auto");

		mKwapi.registerPlayerStatusListener(mSerialPortService,
				new OnPlayerStatusListener() {

					@Override
					public void onPlayerStatus(PlayerStatus playerStatus,
							Music music) {
						Log.i("cxs", "====playerStatus=====" + playerStatus);
						mPlayerStatus = playerStatus;
						if (playerStatus.equals(PlayerStatus.PLAYING)) {
							mSerialPortService
									.sendBroadcast(new Intent(
											"console.hardwareService.action.MEDIA_CONSOLE_COMMAND"));
						}
					}
				});
	}

	/*
	 * private void controlMusicNext() { if (checkLocale("CN")) {
	 * mKwapi.setPlayState(mSerialPortService, PlayState.STATE_NEXT); } else {
	 * try { mIMediaPlaybackService.next(); } catch (Exception e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } } }
	 * 
	 * private void controlMusicPrevious() { if (checkLocale("CN")) {
	 * mKwapi.setPlayState(mSerialPortService, PlayState.STATE_PRE); } else {
	 * try { mIMediaPlaybackService.prev(); } catch (Exception e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } } }
	 * 
	 * private void controlMusicPlay() { Log.i("cxs", "--------controlPlay----"
	 * + checkLocale("CN")); if (checkLocale("CN")) { if
	 * (mPlayerStatus.equals(PlayerStatus.PLAYING)) {
	 * mKwapi.setPlayState(mSerialPortService, PlayState.STATE_PAUSE); } else {
	 * mKwapi.setPlayState(mSerialPortService, PlayState.STATE_PLAY); } } else {
	 * try { Log.i("cxs", "--------controlPlay---isPlaying-" +
	 * mIMediaPlaybackService.isPlaying()); if
	 * (mIMediaPlaybackService.isPlaying()) { mIMediaPlaybackService.pause(); }
	 * else { mIMediaPlaybackService.play(); } } catch (Exception e) { // TODO
	 * Auto-generated catch block Log.i("cxs", "--------controlPlay--e--" + e);
	 * e.printStackTrace(); } } }
	 */

	private void stopMusic() {
		mKwapi.setPlayState(mSerialPortService, PlayState.STATE_PAUSE);
		mKwapi.exitAPP(mSerialPortService);
		try {
			mIMediaPlaybackService.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*---------------����control-------------------------*/

	Boolean checkLocale(String str) {
		return mSerialPortService.getResources().getConfiguration().locale
				.getCountry().equals(str);
	}

	/**
	 * ����canReaderǿ��ֹͣӦ��
	 */
	protected void forceStopPackage(String str) {
		Intent intent = new Intent();
		intent.setClassName("com.console.canreader",
				"com.console.canreader.service.CanService");
		intent.putExtra("keyEvent", Constact.ACTION_CONCOSOLE_FORCESTOP_PACKAGE);
		intent.putExtra("keyEventArg", str);
		mSerialPortService.startService(intent);
	}

	/**
	 * �򿪶Ի���
	 */

	private Dialog initDialog(String str) {
		// TODO Auto-generated method stub
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				mSerialPortService);
		alertDialog.setMessage(str);
		alertDialog.setTitle("���棺");
		return alertDialog.create();
	}

	/*-------------------��ʾ��������  start--------------------*/
	// ���帡�����ڲ���
	private static RelativeLayout mFloatLayout;
	private static WindowManager.LayoutParams wmParams;
	// ���������������ò��ֲ����Ķ���
	private static WindowManager mWindowManager;
	private static String floatWaringStr = "";
    private static ImageView mIv;
	@SuppressWarnings("static-access")
	public  void showFloatWindow(Context mContext,Bitmap bm) {
		mHandler.removeMessages(Contacts.MSG_REMOVE_LOGO_WINDOW);
		if (mFloatLayout == null || mWindowManager == null||mIv==null) {
			wmParams = new WindowManager.LayoutParams();
			// ͨ��getApplication��ȡ����WindowManagerImpl.CompatModeWrapper
			if (mWindowManager == null)
				mWindowManager = (WindowManager) mContext
						.getSystemService(mContext.WINDOW_SERVICE);
			// ����window type
			wmParams.type = LayoutParams.TYPE_PHONE;
			// ����ͼƬ��ʽ��Ч��Ϊ����͸��
			wmParams.format = PixelFormat.RGBA_8888;
			// ���ø������ڲ��ɾ۽���ʵ�ֲ���������������������ɼ����ڵĲ�����
			wmParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE;
			// ������������ʾ��ͣ��λ��Ϊ����ö�
			wmParams.gravity = Gravity.CENTER;

			// �����������ڳ�������
			wmParams.width = 1050;
			wmParams.height = 550;

			LayoutInflater inflater = LayoutInflater.from(mContext);
			mFloatLayout = (RelativeLayout) inflater.inflate(
					R.layout.alert_window_menu, null);

			mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
					View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
					.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
			// ����Ļ���Ͻ�Ϊԭ�㣬����x��y��ʼֵ�������gravity
			wmParams.x = 0;
			wmParams.y = 0;
			
			mIv=(ImageView) mFloatLayout.findViewById(R.id.acc_logo);
			// ���mFloatLayout
			mWindowManager.addView(mFloatLayout, wmParams);
		}
		mIv.setImageBitmap(bm);
		mHandler.sendEmptyMessageDelayed(Contacts.MSG_REMOVE_LOGO_WINDOW, 3*1000);
	}

	public  void removeFloatWindow(Context mContext) {
		if (mWindowManager != null) {
			if (mFloatLayout != null) {
				mWindowManager.removeView(mFloatLayout);
			}
			mFloatLayout = null;
		}
	}

	/*-------------------��ʾ��������  end--------------------*/

}
