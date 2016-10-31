package com.console.canreader.bean;

import java.io.UnsupportedEncodingException;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;
import com.console.canreader.utils.Contacts;

import android.util.Log;

public class SSHonda15CRV extends AnalyzeUtils {
	// ��������
	public static final int comID = 3;
	// ������Ϣ
    public static final int CAR_INFO_DATA = 0x11;
    // ������Ϣ
 	public static final int CAR_INFO_DATA_1 = 0x12;	
    // �״���Ϣ
 	public static final int RADAR_DATA = 0x41;
 // ������Ϣ
    public static final int CAR_INFO_DATA_4 = 0xE8;
    // ������Ϣ
    public static final int CAR_INFO_DATA_10 = 0xF0;
 // ������Ϣ
 	public static final int CAR_INFO_DATA_3 = 0x17;
 // ������Ϣ
 	public static final int CAR_INFO_DATA_2 = 0x16;
 	
	public CanInfo getCanInfo() {
		return mCanInfo;
	}

	@Override
	public void analyzeEach(byte[] msg) {
		// TODO Auto-generated method stub
		super.analyzeEach(msg);
		try {
			if (msg == null)
				return;
			switch ((int) (msg[comID] & 0xFF)) {
			case CAR_INFO_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData(msg);
				break;
			case CAR_INFO_DATA_1:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_1(msg);
				break;				
			case RADAR_DATA:
				mCanInfo.CHANGE_STATUS = 11;
				analyzeRadarData(msg);
				break;
			case CAR_INFO_DATA_4:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_4(msg);
				break;
			case CAR_INFO_DATA_3:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_3(msg);
				break;
			case CAR_INFO_DATA_2:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_2(msg);
				break;
			case CAR_INFO_DATA_10:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_10(msg);
				break;	
			default:
				break;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	static String radarSave = "";
	static int temps[] = { 0, 0, 0, 0, 0, 0, 0, 0 };

	void analyzeRadarData(byte[] msg) {
		if (radarSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			radarSave = BytesUtil.bytesToHexString(msg);
		}

		mCanInfo.BACK_LEFT_DISTANCE = (((int) (msg[4] & 0xFF)) == 0xff) ? 0
				: (((int) (msg[4] & 0xFF)) == 0) ? 0
						: (5 - ((int) (msg[4] & 0xFF)));
		mCanInfo.BACK_MIDDLE_LEFT_DISTANCE = (((int) (msg[5] & 0xFF)) == 0xff) ? 0
				: (((int) (msg[5] & 0xFF)) == 0) ? 0
						: (5 - ((int) (msg[5] & 0xFF)));
		mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE = (((int) (msg[6] & 0xFF)) == 0xff) ? 0
				: (((int) (msg[6] & 0xFF)) == 0) ? 0
						: (5 - ((int) (msg[6] & 0xFF)));
		mCanInfo.BACK_RIGHT_DISTANCE = (((int) (msg[7] & 0xFF)) == 0xff) ? 0
				: (((int) (msg[7] & 0xFF)) == 0) ? 0
						: (5 - ((int) (msg[7] & 0xFF)));

		mCanInfo.FRONT_LEFT_DISTANCE = (((int) (msg[8] & 0xFF)) == 0xff) ? 0
				: (((int) (msg[8] & 0xFF)) == 0) ? 0
						: (5 - ((int) (msg[8] & 0xFF)));
		mCanInfo.FRONT_MIDDLE_LEFT_DISTANCE = (((int) (msg[9] & 0xFF)) == 0xff) ? 0
				: (((int) (msg[9] & 0xFF)) == 0) ? 0
						: (5 - ((int) (msg[9] & 0xFF)));
		mCanInfo.FRONT_MIDDLE_RIGHT_DISTANCE = (((int) (msg[10] & 0xFF)) == 0xff) ? 0
				: (((int) (msg[10] & 0xFF)) == 0) ? 0
						: (5 - ((int) (msg[10] & 0xFF)));
		mCanInfo.FRONT_RIGHT_DISTANCE = (((int) (msg[11] & 0xFF)) == 0xff) ? 0
				: (((int) (msg[11] & 0xFF)) == 0) ? 0
						: (5 - ((int) (msg[11] & 0xFF)));
		mCanInfo.RADAR_ALARM_STATUS=(int) (msg[14] & 0xFF);

	}

	static String carInfoSave_1 = "";

	void analyzeCarInfoData_1(byte[] msg) {
		if (carInfoSave_1.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_1 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.HOOD_STATUS = (int) ((msg[6] >> 2) & 0x01);
		mCanInfo.TRUNK_STATUS = (int) ((msg[6] >> 3) & 0x01);
		mCanInfo.RIGHT_BACKDOOR_STATUS = (int) ((msg[6] >> 4) & 0x01);
		mCanInfo.LEFT_BACKDOOR_STATUS = (int) ((msg[6] >> 5) & 0x01);
		mCanInfo.RIGHT_FORONTDOOR_STATUS = (int) ((msg[6] >> 6) & 0x01);
		mCanInfo.LEFT_FORONTDOOR_STATUS = (int) ((msg[6] >> 7) & 0x01);

	}

	static String carInfoSave_2 = "";
	int[] rangs = { 60, 10, 12, 20, 30, 40, 50, 70, 80, 90, 100 };

	void analyzeCarInfoData_2(byte[] msg) {
		if (carInfoSave_2.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_2 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.INSTANT_CONSUMPTION = (int) ((msg[4] >> 0) & 0xFF);
		mCanInfo.CURRENT_AVERAGE_CONSUMPTION = ((int) (msg[5] & 0xFF) * 256 + (int) (msg[6] & 0xFF)) * 0.1f;
		mCanInfo.HISTORY_AVERAGE_CONSUMPTION = ((int) (msg[7] & 0xFF) * 256 + (int) (msg[8] & 0xFF)) * 0.1f;
		mCanInfo.AVERAGE_CONSUMPTION = ((int) (msg[9] & 0xFF) * 256 + (int) (msg[10] & 0xFF)) * 0.1f;
		mCanInfo.TRIP_A = ((int) (msg[11] & 0xFF) * 256 * 256
				+ (int) (msg[12] & 0xFF) * 256 + (int) (msg[13] & 0xFF)) * 0.1f;
		mCanInfo.RANGE = ((int) (msg[14] & 0xFF) * 256 + (int) (msg[15] & 0xFF));

		mCanInfo.RANGE_UNIT = (int) ((msg[16] >> 7) & 0x01);
		mCanInfo.TRIP_A_UNIT = (int) ((msg[16] >> 6) & 0x01);
		mCanInfo.AVERAGE_CONSUMPTION_UNIT = (int) ((msg[16] >> 4) & 0x03);
		mCanInfo.CUR_HIS_AVERAGE_CONSUMPTION_UNIT = (int) ((msg[16] >> 2) & 0x03);
		mCanInfo.INSTANT_CONSUMPTION_UNIT = (int) ((msg[16] >> 2) & 0x03);
		int temp = (int) ((msg[14] >> 0) & 0xFF);
		mCanInfo.CONSUMPTION_RANGE = rangs[temp];
	}

	static String carInfoSave_3 = "";

	void analyzeCarInfoData_3(byte[] msg) {
		if (carInfoSave_3.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_3 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.TRIP_A_1 = ((int) (msg[4] & 0xFF) * 256 * 256
				+ (int) (msg[5] & 0xFF) * 256 + (int) (msg[6] & 0xFF)) * 0.1f;
		mCanInfo.TRIP_A_1_AVERAGE_CONSUMPTION = ((int) (msg[7] & 0xFF) * 256 + (int) (msg[8] & 0xFF))* 0.1f;
		mCanInfo.TRIP_A_2 = ((int) (msg[9] & 0xFF) * 256 * 256
				+ (int) (msg[10] & 0xFF) * 256 + (int) (msg[11] & 0xFF)) * 0.1f;
		mCanInfo.TRIP_A_2_AVERAGE_CONSUMPTION = ((int) (msg[12] & 0xFF) * 256 + (int) (msg[13] & 0xFF))* 0.1f;
		mCanInfo.TRIP_A_3 = ((int) (msg[14] & 0xFF) * 256 * 256
				+ (int) (msg[15] & 0xFF) * 256 + (int) (msg[16] & 0xFF)) * 0.1f;
		mCanInfo.TRIP_A_3_AVERAGE_CONSUMPTION = ((int) (msg[17] & 0xFF) * 256 + (int) (msg[18] & 0xFF))* 0.1f;
	}
	
	static String carInfoSave_4 = "";

	void analyzeCarInfoData_4(byte[] msg) {
		if (carInfoSave_4.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_4 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.BACK_CAMERA_MODE = (int) (msg[5] & 0xFF);
		mCanInfo.LEFT_CAMERA_SWITCH = (int) (msg[6] & 0xFF);
	}
	
	static String carInfoSave_5 = "";
	
	void analyzeCarInfoData_5(byte[] msg) {
		if (carInfoSave_5.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_5 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.WIPER_LINK_LAMP = (int) ((msg[4] >> 3) & 0x01);
		mCanInfo.AUTO_LIGHT_SENSEITIVITY = (int) ((msg[4] >> 0) & 0x07);
		
		mCanInfo.AUTO_LIGHTING_SENSEITIVITY = (int) ((msg[5] >> 4) & 0x07);
		mCanInfo.FRONT_LAMP_OFF_TIME = (int) ((msg[5] >> 2) & 0x03);
		mCanInfo.LAMP_TURN_DARK_TIME = (int) ((msg[5] >> 0) & 0x03);
	}


	
	static String carInfoSave_6 = "";
	
	void analyzeCarInfoData_6(byte[] msg) {
		if (carInfoSave_6.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_6 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.REMOTELOCK_BEEP_SIGN = (int) ((msg[5] >> 3) & 0x01);
		mCanInfo.REMOTELOCK_SIDELAMP_SIGN = (int) ((msg[5] >> 2) & 0x01);		
		mCanInfo.SPEECH_WARING_VOLUME = (int) ((msg[5] >> 1) & 0x01);
		mCanInfo.REMOTE_START_SYSTEM = (int) ((msg[5] >> 0) & 0x01);
	}
	
	static String carInfoSave_7 = "";	
	void analyzeCarInfoData_7(byte[] msg) {
		if (carInfoSave_7.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_7 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.LOCK_PERSONAL_SETTING = (int) ((msg[5] >> 3) & 0x01);
		mCanInfo.AUTO_LOCK_TIME = (int) ((msg[5] >> 1) & 0x03);
		mCanInfo.REMOTE_LOCK_SIGN = (int) ((msg[5] >> 0) & 0x01);
	}
	
	static String carInfoSave_8 = "";	
	void analyzeCarInfoData_8(byte[] msg) {
		if (carInfoSave_8.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_8 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.LANE_DEPARTURE = (int) ((msg[5] >> 4) & 0x03);
		mCanInfo.PAUSE_LKAS_SIGN = (int) ((msg[5] >> 3) & 0x01);
		mCanInfo.DETECT_FRONT_CAR = (int) ((msg[5] >> 2) & 0x01);
		mCanInfo.FRONT_DANGER_WAIRNG_DISTANCE = (int) ((msg[5] >> 0) & 0x03);
	}
	static String carInfoSave_9 = "";	
	
	void analyzeCarInfoData_9(byte[] msg) {
		if (carInfoSave_9.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_9 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.RATATIONAL_RATE = (int) ((msg[4] >> 5) & 0x01);
		mCanInfo.MSG_NOTIFICATION = (int) ((msg[4] >> 4) & 0x01);
		mCanInfo.ENGINEE_AUTO_CONTROL = (int) ((msg[4] >> 3) & 0x01);
		mCanInfo.ENERGY_BACKGROUND_LIGHT = (int) ((msg[4] >> 2) & 0x01);
		mCanInfo.ADJUST_WARING_VOLUME = (int) ((msg[4] >> 0) & 0x03);
		
		mCanInfo.SWITCH_TRIPB_SETTING = (int) ((msg[5] >> 5) & 0x03);
		mCanInfo.SWITCH_TRIPA_SETTING = (int) ((msg[5] >> 3) & 0x03);
		mCanInfo.ADJUST_OUTSIDE_TEMP = (int) ((msg[5] >> 0) & 0x07);
	}
	
	static String carInfoSave_10 = "";	
	
	void analyzeCarInfoData_10(byte[] msg) {
		if (carInfoSave_10.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_10 = BytesUtil.bytesToHexString(msg);
		}
		int len = ((int) msg[2] & 0xFF);
		byte[] acscii = new byte[len];
		for (int i = 0; i < len; i++) {
			acscii[i] = msg[i + 4];

		}
		try {
			mCanInfo.VERSION = new String(acscii, "GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	static String carInfoSave = "";
	static int buttonTemp = 0;
	/*
	 * �����̰��� STEERING_BUTTON_MODE 0���ް������ͷ� 1��vol+ 2��vol- 3��menuup 4��menu down 5��
	 * PHONE 6��mute 7��SRC 8��SPEECH/MIC 9:answer phone 10:hangup phone
	 */
	static int keyCode[] = { 0, 1, 2, 8, 9, -1, 3, 4, 4, 5, 6 };

	void analyzeCarInfoData(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave = BytesUtil.bytesToHexString(msg);
		}
		// ������ת�� CHANGE_STATUS=8
		int temp = 0;
		if ((int) ((msg[10] >> 7) & 0x01) == 0) {
			temp = -(((int) msg[10] & 0x7F) * 256 + ((int) msg[11] & 0xFF)) / 10;
		} else {
			temp = (((int) msg[10] & 0x7F) * 256 + ((int) msg[11] & 0xFF)) / 10;
		}

		if (mCanInfo.STERRING_WHELL_STATUS != temp) {
			mCanInfo.STERRING_WHELL_STATUS = temp;
			mCanInfo.CHANGE_STATUS = 8;
		}

		// ���� CHANGE_STATUS=2

		if (buttonTemp != (int) (msg[6] & 0xFF)) {
			buttonTemp = (int) (msg[6] & 0xFF);
			switch (buttonTemp) {
			case 0x01:
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLUP;
				break;
			case 0x02:
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLDOW;
				break;
			case 0x04:
			case 0x0B:
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
				break;
			case 0x05:
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.ANSWER;
				break;
			case 0x06:
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.HANGUP;
				break;
			case 0x08:
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUUP;
				break;
			case 0x09:
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUDOWN;
				break;
			default:
				mCanInfo.STEERING_BUTTON_MODE = 0;
				break;
			}
			mCanInfo.CHANGE_STATUS = 2;
		}

		temp = (int) (msg[7] & 0xFF);
		if (mCanInfo.STEERING_BUTTON_STATUS != temp) {
			mCanInfo.STEERING_BUTTON_STATUS = temp;
			mCanInfo.CHANGE_STATUS = 2;
		}

		// ���� CHANGE_STATUS=10

		// mCanInfo.ENGINE_SPEED = ((int) msg[12] & 0xFF) * 256
		// + ((int) msg[13] & 0xFF);
		mCanInfo.HANDBRAKE_STATUS = (int) ((msg[4] >> 3) & 0x01);
		mCanInfo.DRIVING_SPEED = ((int) msg[5] & 0xFF);
		mCanInfo.DISINFECTON_STATUS = -1;
		mCanInfo.SAFETY_BELT_STATUS = -1;
		mCanInfo.REMAIN_FUEL = -1;
		mCanInfo.BATTERY_VOLTAGE = -1;
	}

	static String airConSave = "";

	void analyzeAirConditionData(byte[] msg) {
		// TODO Auto-generated method stub
		if (airConSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			airConSave = BytesUtil.bytesToHexString(msg);
		}

		mCanInfo.AIR_CONDITIONER_STATUS = ((int) (msg[4] >> 6) & 0x01);
		mCanInfo.CYCLE_INDICATOR = ((int) (msg[5] >> 4) & 0x01);
		mCanInfo.LARGE_LANTERN_INDICATOR = 0;
		mCanInfo.SMALL_LANTERN_INDICATOR = (int) ((msg[4] >> 3) & 0x01);
		mCanInfo.AC_INDICATOR_STATUS = ((int) (msg[4] >> 0) & 0x01);
		mCanInfo.MAX_FRONT_LAMP_INDICATOR = (int) ((msg[6] >> 4) & 0x01);
		mCanInfo.REAR_LAMP_INDICATOR = (int) ((msg[6] >> 5) & 0x01);
		mCanInfo.AIR_RATE = (int) (msg[9] & 0x0f);

		int temp = (int) (msg[10] & 0xff);
		mCanInfo.DRIVING_POSITON_TEMP = temp == 0xFE ? 0 : temp == 0xFF ? 255
				: (temp * 0.5f);

		temp = (int) (msg[11] & 0xff);
		mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = temp == 0xFE ? 0
				: temp == 0xFF ? 255 : (temp * 0.5f);

		temp = (int) (msg[8] & 0xff);
		if (temp == 0x04 || temp == 0x07 || temp == 0x0A) {
			mCanInfo.UPWARD_AIR_INDICATOR = 1;
		} else {
			mCanInfo.UPWARD_AIR_INDICATOR = 0;
		}

		if (temp == 0x05 || temp == 0x06 || temp == 0x07 || temp == 0x0A) {
			mCanInfo.PARALLEL_AIR_INDICATOR = 1;
		} else {
			mCanInfo.PARALLEL_AIR_INDICATOR = 0;
		}

		if (temp == 0x03 || temp == 0x04 || temp == 0x05 || temp == 0x0A) {
			mCanInfo.DOWNWARD_AIR_INDICATOR = 1;
		} else {
			mCanInfo.DOWNWARD_AIR_INDICATOR = 0;
		}
	}

}
