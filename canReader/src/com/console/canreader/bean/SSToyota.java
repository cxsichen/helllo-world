package com.console.canreader.bean;

import java.io.UnsupportedEncodingException;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;

import android.util.Log;

public class SSToyota extends AnalyzeUtils {
	// 数据类型
	public static final int comID = 3;
	// 空调信息
	public static final int AIR_CONDITIONER_DATA = 0x82;
	// 雷达信息
	public static final int RADAR_DATA = 0x41;
	// 车身信息
	public static final int CAR_INFO_DATA = 0x11;
	// 车身信息
	public static final int CAR_INFO_DATA_1 = 0x13;
	// 车身信息
	public static final int CAR_INFO_DATA_2 = 0x32;
	// 车身信息
	public static final int CAR_INFO_DATA_3 = 0x62;
	// 车身信息
	public static final int CAR_INFO_DATA_4 = 0xF0;
	// 车身信息
	public static final int CAR_INFO_DATA_5 = 0x1F;
	// 车身信息
	public static final int CAR_INFO_DATA_6 = 0x16;
	// 车身信息
	public static final int CAR_INFO_DATA_7 = 0x17;
	// 车身功放信息
	public static final int CAR_INFO_DATA_8 = 0xA6;
	// 车身功放信息
	public static final int CAR_INFO_DATA_9 = 0xE8;

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
			case AIR_CONDITIONER_DATA:
				mCanInfo.CHANGE_STATUS = 3;
				analyzeAirConditionData(msg);
				break;
			case CAR_INFO_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData(msg);
				break;
			case CAR_INFO_DATA_1:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_1(msg);
				break;
			case CAR_INFO_DATA_2:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_2(msg);
				break;
			case CAR_INFO_DATA_3:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_3(msg);
				break;
			case CAR_INFO_DATA_4:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_4(msg);
				break;
			case CAR_INFO_DATA_5:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_5(msg);
				break;
			case CAR_INFO_DATA_6:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_6(msg);
				break;
			case CAR_INFO_DATA_7:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_7(msg);
				break;
			case CAR_INFO_DATA_8:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_8(msg);
				break;
			case CAR_INFO_DATA_9:
				mCanInfo.CHANGE_STATUS = 20;
				analyzeCarInfoData_9(msg);
				break;
			case RADAR_DATA:
				mCanInfo.CHANGE_STATUS = 11;
				analyzeRadarData(msg);
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
				: ((int) (msg[4] & 0xFF));
		mCanInfo.BACK_MIDDLE_LEFT_DISTANCE = (((int) (msg[5] & 0xFF)) == 0xff) ? 0
				: ((int) (msg[5] & 0xFF));
		mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE = (((int) (msg[5] & 0xFF)) == 0xff) ? 0
				: ((int) (msg[5] & 0xFF));
		mCanInfo.BACK_RIGHT_DISTANCE = (((int) (msg[7] & 0xFF)) == 0xff) ? 0
				: ((int) (msg[7] & 0xFF));

		mCanInfo.FRONT_LEFT_DISTANCE = (((int) (msg[8] & 0xFF)) == 0xff) ? 0
				: ((int) (msg[8] & 0xFF));
		mCanInfo.FRONT_MIDDLE_LEFT_DISTANCE = (((int) (msg[9] & 0xFF)) == 0xff) ? 0
				: ((int) (msg[9] & 0xFF));
		mCanInfo.FRONT_MIDDLE_RIGHT_DISTANCE = (((int) (msg[9] & 0xFF)) == 0xff) ? 0
				: ((int) (msg[9] & 0xFF));
		mCanInfo.FRONT_RIGHT_DISTANCE = (((int) (msg[11] & 0xFF)) == 0xff) ? 0
				: ((int) (msg[11] & 0xFF));

		mCanInfo.RADAR_ALARM_STATUS = (int) (msg[14] & 0xFF);

	}

	static String carInfoSave_1 = "";

	void analyzeCarInfoData_1(byte[] msg) {
		if (carInfoSave_1.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_1 = BytesUtil.bytesToHexString(msg);
		}

		int time = ((int) msg[10] & 0xFF) * 256 + ((int) msg[11] & 0xFF);
		int speed = ((int) msg[12] & 0xFF) * 256 + ((int) msg[13] & 0xFF);
		mCanInfo.DRIVING_DISTANCE = time * speed / 60;
	}

	static String carInfoSave_2 = "";

	void analyzeCarInfoData_2(byte[] msg) {
		if (carInfoSave_2.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_2 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.ENGINE_SPEED = ((int) msg[4] & 0xFF) * 256
				+ ((int) msg[5] & 0xFF);
		mCanInfo.DRIVING_SPEED = ((int) msg[6] & 0xFF) * 256
				+ ((int) msg[7] & 0xFF);
	}

	static String carInfoSave_3 = "";

	void analyzeCarInfoData_3(byte[] msg) {
		if (carInfoSave_3.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_3 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.AUTO_LOCK_SETTING = (int) ((msg[5] >> 6) & 0x01);
		mCanInfo.AUTO_OPEN_LOCK = (int) ((msg[5] >> 5) & 0x01);
		mCanInfo.DRIVER_LINK_LOCK = (int) ((msg[5] >> 4) & 0x01);
		mCanInfo.AUTO_OPEN_LOCK_P = (int) ((msg[5] >> 3) & 0x01);
		mCanInfo.AUTO_LOCK_P = (int) ((msg[5] >> 2) & 0x01);
		mCanInfo.AIRCON_WITH_AUTO = (int) ((msg[5] >> 1) & 0x01);
		mCanInfo.CYCLE_WITH_AUTO = (int) ((msg[5] >> 0) & 0x01);

		mCanInfo.LAMP_WHEN_LOCK = (int) ((msg[6] >> 7) & 0x01);
		mCanInfo.TWICE_BUTTON_OPEN_LOCK = (int) ((msg[6] >> 6) & 0x01);
		mCanInfo.INTELLIGENT_LOCK = (int) ((msg[6] >> 5) & 0x01);
		mCanInfo.TWICE_KEY_OPEN_LOCK = (int) ((msg[6] >> 4) & 0x01);

		mCanInfo.AUTOMATIC_CAP_SENSEITIVITY = (int) ((msg[7] >> 0) & 0x07);
		mCanInfo.AUTOMATIC_LAMP_CLOSE = (int) ((msg[7] >> 3) & 0x07);
		mCanInfo.IS_RADAR_SHOW = (int) ((msg[4] >> 7) & 0x01);
		mCanInfo.RADAR_WARING_VOLUME = (int) ((msg[4] >> 4) & 0x07);
		mCanInfo.FRONT_RADAR_DISTANCE = (int) ((msg[4] >> 2) & 0x03);
		mCanInfo.BACK_RADAR_DISTANCE = (int) ((msg[4] >> 0) & 0x03);

	}

	static String carInfoSave_4 = "";

	void analyzeCarInfoData_4(byte[] msg) {
		if (carInfoSave_4.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_4 = BytesUtil.bytesToHexString(msg);
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

	static String carInfoSave_5 = "";

	void analyzeCarInfoData_5(byte[] msg) {
		if (carInfoSave_5.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_5 = BytesUtil.bytesToHexString(msg);
		}

		mCanInfo.IS_POWER_MIXING = (int) ((msg[4] >> 7) & 0x01);
		mCanInfo.BATTERY_LEVEL = (int) ((msg[4] >> 0) & 0x07);

		mCanInfo.MOTOR_DRIVE_BATTERY = (int) ((msg[5] >> 0) & 0x07);
		mCanInfo.MOTOR_DRIVE_WHEEL = (int) ((msg[5] >> 1) & 0x07);
		mCanInfo.ENGINE_DRIVE_MOTOR = (int) ((msg[5] >> 2) & 0x07);
		mCanInfo.ENGINE_DRIVE_WHEEL = (int) ((msg[5] >> 3) & 0x07);
		mCanInfo.BATTERY_DRIVE_MOTOR = (int) ((msg[5] >> 4) & 0x07);
		mCanInfo.WHEEL_DRIVE_MOTOR = (int) ((msg[5] >> 5) & 0x07);

	}

	static String carInfoSave_6 = "";

	void analyzeCarInfoData_6(byte[] msg) {
		if (carInfoSave_6.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_6 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.TRIP_OIL_CONSUMPTION_0 = (((int) msg[4] & 0xFF) * 256 + ((int) msg[5] & 0xFF)) / 10f;
		mCanInfo.TRIP_OIL_CONSUMPTION_1 = (((int) msg[6] & 0xFF) * 256 + ((int) msg[7] & 0xFF)) / 10f;
		mCanInfo.TRIP_OIL_CONSUMPTION_2 = (((int) msg[8] & 0xFF) * 256 + ((int) msg[9] & 0xFF)) / 10f;

		mCanInfo.TRIP_OIL_CONSUMPTION_3 = (((int) msg[10] & 0xFF) * 256 + ((int) msg[11] & 0xFF)) / 10f;
		mCanInfo.TRIP_OIL_CONSUMPTION_4 = (((int) msg[12] & 0xFF) * 256 + ((int) msg[13] & 0xFF)) / 10f;
		mCanInfo.TRIP_OIL_CONSUMPTION_5 = (((int) msg[14] & 0xFF) * 256 + ((int) msg[15] & 0xFF)) / 10f;

		mCanInfo.TRIP_OIL_CONSUMPTION_UNIT = (int) ((msg[16] >> 0) & 0xFF);
	}

	static String carInfoSave_7 = "";

	void analyzeCarInfoData_7(byte[] msg) {
		if (carInfoSave_7.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_7 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.HISTORY_OIL_CONSUMPTION_1 = (((int) msg[4] & 0xFF) * 256 + ((int) msg[5] & 0xFF)) / 10f;
		mCanInfo.HISTORY_OIL_CONSUMPTION_2 = (((int) msg[6] & 0xFF) * 256 + ((int) msg[7] & 0xFF)) / 10f;
		mCanInfo.HISTORY_OIL_CONSUMPTION_3 = (((int) msg[8] & 0xFF) * 256 + ((int) msg[9] & 0xFF)) / 10f;
		mCanInfo.HISTORY_OIL_CONSUMPTION_4 = (((int) msg[10] & 0xFF) * 256 + ((int) msg[11] & 0xFF)) / 10f;
		mCanInfo.HISTORY_OIL_CONSUMPTION_5 = (((int) msg[12] & 0xFF) * 256 + ((int) msg[13] & 0xFF)) / 10f;

		mCanInfo.HISTORY_OIL_CONSUMPTION_6 = (((int) msg[14] & 0xFF) * 256 + ((int) msg[15] & 0xFF)) / 10f;
		mCanInfo.HISTORY_OIL_CONSUMPTION_7 = (((int) msg[16] & 0xFF) * 256 + ((int) msg[17] & 0xFF)) / 10f;
		mCanInfo.HISTORY_OIL_CONSUMPTION_8 = (((int) msg[18] & 0xFF) * 256 + ((int) msg[19] & 0xFF)) / 10f;
		mCanInfo.HISTORY_OIL_CONSUMPTION_9 = (((int) msg[20] & 0xFF) * 256 + ((int) msg[21] & 0xFF)) / 10f;
		mCanInfo.HISTORY_OIL_CONSUMPTION_10 = (((int) msg[22] & 0xFF) * 256 + ((int) msg[23] & 0xFF)) / 10f;

		mCanInfo.HISTORY_OIL_CONSUMPTION_11 = (((int) msg[24] & 0xFF) * 256 + ((int) msg[25] & 0xFF)) / 10f;
		mCanInfo.HISTORY_OIL_CONSUMPTION_12 = (((int) msg[26] & 0xFF) * 256 + ((int) msg[27] & 0xFF)) / 10f;
		mCanInfo.HISTORY_OIL_CONSUMPTION_13 = (((int) msg[28] & 0xFF) * 256 + ((int) msg[29] & 0xFF)) / 10f;
		mCanInfo.HISTORY_OIL_CONSUMPTION_14 = (((int) msg[30] & 0xFF) * 256 + ((int) msg[31] & 0xFF)) / 10f;
		mCanInfo.HISTORY_OIL_CONSUMPTION_15 = (((int) msg[32] & 0xFF) * 256 + ((int) msg[33] & 0xFF)) / 10f;

		mCanInfo.HISTORY_OIL_CONSUMPTION_UNIT = (int) ((msg[64] >> 0) & 0xFF);
	}

	static String carInfoSave_9 = "";

	void analyzeCarInfoData_9(byte[] msg) {
		if (carInfoSave_9.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_9 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.PANORAMA_STATUS = (int) ((msg[7] >> 0) & 0xFF);

	}

	static String carInfoSave_8 = "";

	void analyzeCarInfoData_8(byte[] msg) {
		if (carInfoSave_8.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_8 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.EQL_VOLUME = (int) ((msg[4] >> 0) & 0xFF);
		mCanInfo.LR_BALANCE = (int) ((msg[5] >> 0) & 0xFF);
		mCanInfo.FB_BALANCE = (int) ((msg[6] >> 0) & 0xFF);
		mCanInfo.BAS_VOLUME = (int) ((msg[7] >> 0) & 0xFF);
		mCanInfo.MID_VOLUME = (int) ((msg[8] >> 0) & 0xFF);
		mCanInfo.TRE_VOLUME = (int) ((msg[9] >> 0) & 0xFF);
		mCanInfo.VOL_LINK_CARSPEED = (int) ((msg[10] >> 1) & 0x01);
		mCanInfo.DSP_SURROUND = (int) ((msg[10] >> 0) & 0x01);
	}

	static String carInfoSave = "";
	static int buttonTemp = 0;
	/*
	 * 方向盘按键 STEERING_BUTTON_MODE 0：无按键或释放 1：vol+ 2：vol- 3：menuup 4：menu down 5：
	 * PHONE 6：mute 7：SRC 8：SPEECH/MIC 9:answer phone 10:hangup phone
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
		// 方向盘转角 CHANGE_STATUS=8
		int temp = ((int) msg[10] & 0xFF) << 8 | ((int) msg[11] & 0xFF);
		if (temp < 32767) {
			if (mCanInfo.STERRING_WHELL_STATUS != -temp) {
				mCanInfo.STERRING_WHELL_STATUS = -temp;
				mCanInfo.CHANGE_STATUS = 8;
			}

		} else {
			if (mCanInfo.STERRING_WHELL_STATUS != 65536 - temp) {
				mCanInfo.STERRING_WHELL_STATUS = 65536 - temp;
				mCanInfo.CHANGE_STATUS = 8;
			}
		}

		// 按键 CHANGE_STATUS=2

		if (buttonTemp != (int) (msg[6] & 0xFF)) {
			buttonTemp = (int) (msg[6] & 0xFF);
			switch (buttonTemp) {
			case 0x01:
				mCanInfo.STEERING_BUTTON_MODE = 1;
				break;
			case 0x02:
				mCanInfo.STEERING_BUTTON_MODE = 2;
				break;
			case 0x04:
				mCanInfo.STEERING_BUTTON_MODE = 8;
				break;
			case 0x05:
				mCanInfo.STEERING_BUTTON_MODE = 9;
				break;
			case 0x06:
				mCanInfo.STEERING_BUTTON_MODE = 10;
				break;
			case 0x08:
				mCanInfo.STEERING_BUTTON_MODE = 3;
				break;
			case 0x09:
				mCanInfo.STEERING_BUTTON_MODE = 4;
				break;
			case 0x0C:
				mCanInfo.STEERING_BUTTON_MODE = 8;
				break;
			case 0x0D:
				mCanInfo.STEERING_BUTTON_MODE = 3;
				break;
			case 0x0E:
				mCanInfo.STEERING_BUTTON_MODE = 4;
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

		// 报警 CHANGE_STATUS=10

		mCanInfo.TRUNK_STATUS = (int) ((msg[8] >> 3) & 0x01);
		mCanInfo.RIGHT_BACKDOOR_STATUS = (int) ((msg[8] >> 5) & 0x01);
		mCanInfo.LEFT_BACKDOOR_STATUS = (int) ((msg[8] >> 4) & 0x01);
		mCanInfo.RIGHT_FORONTDOOR_STATUS = (int) ((msg[8] >> 7) & 0x01);
		mCanInfo.LEFT_FORONTDOOR_STATUS = (int) ((msg[8] >> 6) & 0x01);

		/*
		 * mCanInfo.ENGINE_SPEED = ((int) msg[12] & 0xFF) * 256 + ((int) msg[13]
		 * & 0xFF);
		 */
		mCanInfo.HANDBRAKE_STATUS = (int) ((msg[4] >> 3) & 0x01);
		// mCanInfo.DRIVING_SPEED = ((int) msg[5] & 0xFF);
		mCanInfo.DISINFECTON_STATUS = -1;
		mCanInfo.SAFETY_BELT_STATUS = -1;
		mCanInfo.REMAIN_FUEL = -1;
		mCanInfo.BATTERY_VOLTAGE = -1;
		/*
		 * mCanInfo.FUEL_WARING_SIGN = (int) ((msg[10] >> 7) & 0x01);
		 * mCanInfo.BATTERY_WARING_SIGN = (int) ((msg[10] >> 6) & 0x01);
		 * mCanInfo.HANDBRAKE_STATUS = (int) (msg[12] & 0xFF);
		 * mCanInfo.BATTERY_VOLTAGE = ((int) (msg[11] & 0xFF)) * 0.1f;
		 */
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

		mCanInfo.AIR_CONDITIONER_STATUS = 1;
		mCanInfo.CYCLE_INDICATOR = 2;
		mCanInfo.AC_INDICATOR_STATUS = ((int) (msg[5] >> 6) & 0x01);
		mCanInfo.LARGE_LANTERN_INDICATOR = 0;
		mCanInfo.SMALL_LANTERN_INDICATOR = (int) ((msg[4] >> 3) & 0x01);

		mCanInfo.MAX_FRONT_LAMP_INDICATOR = (int) ((msg[5] >> 4) & 0x01);

		mCanInfo.REAR_LAMP_INDICATOR = (int) ((msg[5] >> 5) & 0x01);

		int temp = (int) (msg[6] & 0xff);
		mCanInfo.DRIVING_POSITON_TEMP = temp == 0x01 ? 0 : temp == 0xFF ? 255
				: (temp * 0.5f - 40);

		temp = (int) (msg[7] & 0xff);
		mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = temp == 0x01 ? 0
				: temp == 0xFF ? 255 : (temp * 0.5f - 40);

		mCanInfo.AIR_RATE = (int) (msg[8] & 0x0f);

		mCanInfo.DOWNWARD_AIR_INDICATOR = (int) ((msg[8] >> 6) & 0x01);
		mCanInfo.PARALLEL_AIR_INDICATOR = (int) ((msg[8] >> 5) & 0x01);
		mCanInfo.UPWARD_AIR_INDICATOR = (int) ((msg[8] >> 4) & 0x01);
		
		mCanInfo.LEFT_SEAT_TEMP =0; // 左座椅温度
		mCanInfo.RIGTHT_SEAT_TEMP = 0; // 右座椅温度

	}

}
