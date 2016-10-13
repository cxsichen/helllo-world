package com.console.canreader.bean;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;

import android.util.Log;

public class SSGE extends AnalyzeUtils {
	// 数据类型
	public static final int comID = 3;
	// DataType
	// 车身信息
	public static final int CAR_INFO_DATA = 0x12;
	// 车身信息
	public static final int CAR_INFO_DATA_1 = 0x32;
	// 车身信息
	public static final int CAR_INFO_DATA_2 = 0x34;
	// 空调信息
	public static final int AIR_CONDITIONER_DATA = 0x31;
	// 车身基本信息
	public static final int CAR_BASIC_INFO_DATA = 0x11;
	// 雷达信息
	public static final int RADAR_DATA = 0x41;

	// 背光
	public static int BACK_LIGHT_DATA = 0x14;
	// 车速
	public static int CAR_SPEED_DATA = 0x16;
	// 方向盘按键
	public static int STEERING_BUTTON_DATA = 0x20;
	// 后雷达信息
	public static int BACK_RADER_DATA = 0x22;
	// 前雷达信息
	public static int FRONT_RADER_DATA = 0x23;
	// 基本信息
	public static int BASIC_INFO_DATA = 0x24;
	// 泊车辅助状态
	public static int PARK_ASSIT_DATA = 0x25;
	// 方向盘转角
	public static int STEERING_TURN_DATA = 0x26;
	// 功放状态
	public static int POWER_AMPLIFIER_DATA = 0x27;
	// 版本信息
	public static int VERSION_DATA = 0x30;
	// 方向盘命令
	public static int STEERING_ORDER_DATA = 0x2F;
	// 车身时间信息
	public static int CAR_TIME_INFO = 0x12;
	// 环境温度信息
	public static int AMBIENT_TEMP_INFO = 0x51;


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
			case CAR_BASIC_INFO_DATA:
				analyzeCarBasicInfoData(msg);
				break;
			case RADAR_DATA:
				mCanInfo.CHANGE_STATUS = 11;
				analyzeRadarData(msg);
				break;
			default:
				break;
			}

			/*
			 * if ((int) (msg[i] & 0xFF) == CAR_SPEED_DATA) {
			 * mCanInfo.CHANGE_STATUS = 1; analyzeCarSpeedData(msg); } else if
			 * ((int) (msg[i] & 0xFF) == STEERING_BUTTON_DATA) {
			 * mCanInfo.CHANGE_STATUS = 2; analyzeSteeringButtonData(msg); }
			 * else if ((int) (msg[i] & 0xFF) == BACK_RADER_DATA) {
			 * mCanInfo.CHANGE_STATUS = 4; analyzeBackRaderData(msg); } else if
			 * ((int) (msg[i] & 0xFF) == FRONT_RADER_DATA) {
			 * mCanInfo.CHANGE_STATUS = 5; analyzeFrontRaderData(msg); } else if
			 * ((int) (msg[i] & 0xFF) == STEERING_TURN_DATA) {
			 * mCanInfo.CHANGE_STATUS = 8; analyzeSteeringTurnData(msg); }
			 */
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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

		for (int i = 0; i < 8; i++) {
			temps[i] = (((int) (msg[4 + i] & 0xff) * 4 / 255) + 1) > 4 ? 4
					: (((int) (msg[4 + i] & 0xff) * 4 / 255) + 1);
		}
		if (mCanInfo.BACK_LEFT_DISTANCE != temps[0]
				|| mCanInfo.BACK_MIDDLE_LEFT_DISTANCE != temps[1]
				|| mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE != temps[2]
				|| mCanInfo.BACK_RIGHT_DISTANCE != temps[3]
				|| mCanInfo.FRONT_LEFT_DISTANCE != temps[4]
				|| mCanInfo.FRONT_MIDDLE_LEFT_DISTANCE != temps[5]
				|| mCanInfo.FRONT_MIDDLE_RIGHT_DISTANCE != temps[6]
				|| mCanInfo.FRONT_RIGHT_DISTANCE != temps[7]) {
			mCanInfo.BACK_LEFT_DISTANCE = temps[0];
			mCanInfo.BACK_MIDDLE_LEFT_DISTANCE = temps[1];
			mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE = temps[2];
			mCanInfo.BACK_RIGHT_DISTANCE = temps[3];

			mCanInfo.FRONT_LEFT_DISTANCE = temps[4];
			mCanInfo.FRONT_MIDDLE_LEFT_DISTANCE = temps[5];
			mCanInfo.FRONT_MIDDLE_RIGHT_DISTANCE = temps[6];
			mCanInfo.FRONT_RIGHT_DISTANCE = temps[7];
		}

	}

	static String carBasicInfo = "";

	/*
	 * 方向盘按键 STEERING_BUTTON_MODE 0：无按键或释放 1：vol+ 2：vol- 3：menuup 4：menu down 5：
	 * PHONE 6：mute 7：SRC 8：SPEECH/MIC 9:answer phone 10:hangup phone
	 */
	static int keyCode[] = { 0, 1, 2, 8, 9, -1, 3, 4, 4, 5, 6 };

	void analyzeCarBasicInfoData(byte[] msg) {
		if (carBasicInfo.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carBasicInfo = BytesUtil.bytesToHexString(msg);
		}

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

		temp = (int) (msg[6] & 0xFF);
		for (int i = 0; i < keyCode.length; i++) {
			if (temp == keyCode[i]) {
				temp = i;
				break;
			}
		}
		if (mCanInfo.STEERING_BUTTON_MODE != temp) {
			mCanInfo.STEERING_BUTTON_MODE = temp;
			mCanInfo.CHANGE_STATUS = 2;
		}

		temp = (int) (msg[7] & 0xFF);
		if (mCanInfo.STEERING_BUTTON_STATUS != temp) {
			mCanInfo.STEERING_BUTTON_STATUS = temp;
			mCanInfo.CHANGE_STATUS = 2;
		}
	}

	static String carInfoSave_2 = "";

	void analyzeCarInfoData_2(byte[] msg) {
		if (carInfoSave_2.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_2 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.DRIVING_DISTANCE = ((int) msg[8] & 0xFF) << 16
				| ((int) msg[9] & 0xFF) << 8 | ((int) msg[10] & 0xFF);

	}

	static String carInfoSave_1 = "";

	void analyzeCarInfoData_1(byte[] msg) {

		if (carInfoSave_1.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_1 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.ENGINE_SPEED = ((int) msg[6] & 0xFF) << 8
				| ((int) msg[7] & 0xFF);
		mCanInfo.DRIVING_SPEED = ((int) msg[8] & 0xFF) << 8
				| ((int) msg[9] & 0xFF);
		mCanInfo.REMAIN_FUEL = (int) (msg[12] & 0xFF);
	}

	static String carInfoSave = "";

	void analyzeCarInfoData(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.HOOD_STATUS = (int) ((msg[6] >> 2) & 0x01);
		mCanInfo.TRUNK_STATUS = (int) ((msg[6] >> 3) & 0x01);
		mCanInfo.RIGHT_BACKDOOR_STATUS = (int) ((msg[6] >> 4) & 0x01);
		mCanInfo.LEFT_BACKDOOR_STATUS = (int) ((msg[6] >> 5) & 0x01);
		mCanInfo.RIGHT_FORONTDOOR_STATUS = (int) ((msg[6] >> 6) & 0x01);
		mCanInfo.LEFT_FORONTDOOR_STATUS = (int) ((msg[6] >> 7) & 0x01);

		mCanInfo.FUEL_WARING_SIGN = (int) ((msg[10] >> 7) & 0x01);
		mCanInfo.BATTERY_WARING_SIGN = (int) ((msg[10] >> 6) & 0x01);
		mCanInfo.HANDBRAKE_STATUS = (int) (msg[12] & 0xFF);
		mCanInfo.BATTERY_VOLTAGE = ((int) (msg[11] & 0xFF)) * 0.1f;
		mCanInfo.SAFETY_BELT_STATUS = -1;
		mCanInfo.DISINFECTON_STATUS = -1;
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
		mCanInfo.AIRCON_SHOW_REQUEST = (int) ((msg[4] >> 7) & 0x01);
		mCanInfo.AIR_CONDITIONER_STATUS = (int) ((msg[4] >> 6) & 0x01);
		mCanInfo.AC_INDICATOR_STATUS = (((int) ((msg[4] >> 0) & 0x03)) == 0 ? 0
				: 1);

		int temp = (int) (msg[10] & 0xff);
		mCanInfo.DRIVING_POSITON_TEMP = temp == 0xFE ? 0 : temp == 0xFF ? 255
				: (temp * 0.5f);

		temp = (int) (msg[11] & 0xff);
		mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = temp == 0xFE ? 0
				: temp == 0xFF ? 255 : (temp * 0.5f);

		mCanInfo.AIR_RATE = ((int) (msg[9] & 0x0f) == 0x13) ? -1
				: ((int) (msg[9] & 0x0f));

		mCanInfo.MAX_FRONT_LAMP_INDICATOR = (int) ((msg[6] >> 4) & 0x01);

		mCanInfo.REAR_LAMP_INDICATOR = (int) ((msg[6] >> 5) & 0x01);

		temp = (int) ((msg[5] >> 3) & 0x01);
		if (temp == 1) {
			mCanInfo.CYCLE_INDICATOR = 2;
		} else {
			mCanInfo.CYCLE_INDICATOR = (int) ((msg[5] >> 4) & 0x01);
		}

		mCanInfo.LEFT_SEAT_TEMP = (int) ((msg[6] >> 2) & 0x03);
		mCanInfo.RIGTHT_SEAT_TEMP = (int) ((msg[6] >> 0) & 0x03);

		temp = (int) (msg[8] & 0xff);
		if (temp == 0x0B || temp == 0x0C || temp == 0x0D || temp == 0x0E) {
			mCanInfo.UPWARD_AIR_INDICATOR = 1;
		} else {
			mCanInfo.UPWARD_AIR_INDICATOR = 0;
		}

		if (temp == 0x05 || temp == 0x06 || temp == 0x0D || temp == 0x0E) {
			mCanInfo.PARALLEL_AIR_INDICATOR = 1;
		} else {
			mCanInfo.PARALLEL_AIR_INDICATOR = 0;
		}

		if (temp == 0x03 || temp == 0x05 || temp == 0x0C || temp == 0x0E) {
			mCanInfo.DOWNWARD_AIR_INDICATOR = 1;
		} else {
			mCanInfo.DOWNWARD_AIR_INDICATOR = 0;
		}

		mCanInfo.OUTSIDE_TEMPERATURE = ((int) (msg[15] & 0xff)) * 0.5f - 40f;

	}

}
