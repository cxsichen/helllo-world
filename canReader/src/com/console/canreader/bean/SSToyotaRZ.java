package com.console.canreader.bean;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;

import android.util.Log;

public class SSToyotaRZ extends AnalyzeUtils {
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
			// 无空调信息
			/*
			 * case AIR_CONDITIONER_DATA: mCanInfo.CHANGE_STATUS = 3;
			 * analyzeAirConditionData(msg); break;
			 */
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

		// mCanInfo.ENGINE_SPEED = ((int) msg[12] & 0xFF) * 256
		// + ((int) msg[13] & 0xFF);
		mCanInfo.HANDBRAKE_STATUS = (int) ((msg[4] >> 3) & 0x01);
		mCanInfo.DRIVING_SPEED = ((int) msg[5] & 0xFF);
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
		mCanInfo.CYCLE_INDICATOR = 0;
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

	}

}
