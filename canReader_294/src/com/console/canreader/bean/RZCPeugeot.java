package com.console.canreader.bean;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;
import com.console.canreader.utils.Contacts;

import android.util.Log;

public class RZCPeugeot extends AnalyzeUtils {
	// 数据类型
	public static final int comID = 2;
	// Head Code
	public static final int HEAD_CODE = 0x2e;
	// DataType
	// 方向盘按键
	public static final int STEERING_BUTTON_DATA = 0x02;
	// 空调信息
	public static final int AIR_CONDITIONER_DATA = 0x21;
	// 雷达信息
	public static final int RADAR_DATA = 0x32;
	// 方向盘转角
	public static final int STEERING_TURN_DATA = 0x29;
	// 车身信息
	public static final int CAR_INFO_DATA = 0x38;
	public static final int CAR_INFO_DATA_1 = 0x36;

	public CanInfo getCanInfo() {
		return mCanInfo;
	}

	@Override
	public void analyzeEach(byte[] msg) {
		// TODO Auto-generated method stub
		try {
			if (msg == null)
				return;
			switch ((int) (msg[comID] & 0xFF)) {
			case AIR_CONDITIONER_DATA:
				mCanInfo.CHANGE_STATUS = 3;
				analyzeAirConditionData(msg);
				break;
			case STEERING_TURN_DATA:
				mCanInfo.CHANGE_STATUS = 8;
				analyzeSteeringTurnData(msg);
				break;
			case STEERING_BUTTON_DATA:
				mCanInfo.CHANGE_STATUS = 2;
				analyzeSteeringButtonData(msg);
				break;
			case RADAR_DATA:
				mCanInfo.CHANGE_STATUS = 4;
				analyzeRaderData(msg);
				break;
			case CAR_INFO_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData(msg);
				break;
			case CAR_INFO_DATA_1:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_1(msg);
				break;
			default:
				mCanInfo.CHANGE_STATUS = 8888;
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	static String canInfoSave_1 = "";

	void analyzeCarInfoData_1(byte[] msg) {
		// TODO Auto-generated method stub
		if (canInfoSave_1.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			canInfoSave_1 = BytesUtil.bytesToHexString(msg);
		}
		if (((int) ((msg[3] >> 7) & 0x01) == 0)) {
			mCanInfo.OUTSIDE_TEMPERATURE = (int) (msg[3] & 0x7f);
		} else {
			mCanInfo.OUTSIDE_TEMPERATURE = -(int) (msg[3] & 0x7f);
		}

	}

	static String canInfoSave = "";

	void analyzeCarInfoData(byte[] msg) {
		// TODO Auto-generated method stub
		if (canInfoSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			canInfoSave = BytesUtil.bytesToHexString(msg);
		}

		mCanInfo.RIGHT_FORONTDOOR_STATUS = (int) ((msg[3] >> 6) & 0x01);
		mCanInfo.LEFT_FORONTDOOR_STATUS = (int) ((msg[3] >> 7) & 0x01);
		mCanInfo.RIGHT_BACKDOOR_STATUS = (int) ((msg[3] >> 4) & 0x01);
		mCanInfo.LEFT_BACKDOOR_STATUS = (int) ((msg[3] >> 5) & 0x01);
		mCanInfo.TRUNK_STATUS = (int) ((msg[3] >> 3) & 0x01);
		mCanInfo.HOOD_STATUS = (int) ((msg[3] >> 2) & 0x01);
		mCanInfo.SAFETY_BELT_STATUS = (int) ((msg[3] >> 1) & 0x01);

		mCanInfo.HANDBRAKE_STATUS = (int) ((msg[6] >> 1) & 0x01);

		/*
		 * mCanInfo.SAFETY_BELT_STATUS = (int) ((msg[4] >> 7) & 0x01);
		 * mCanInfo.BATTERY_VOLTAGE = -1; mCanInfo.REMAIN_FUEL = -1;
		 */
	}

	static String steerSave = "";

	void analyzeSteeringTurnData(byte[] msg) {
		// TODO Auto-generated method stub
		if (steerSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			steerSave = BytesUtil.bytesToHexString(msg);
		}

		int temp = ((int) msg[4] & 0xFF) << 8 | ((int) msg[3] & 0xFF);
		if (temp < 32767) {
			mCanInfo.STERRING_WHELL_STATUS = temp / 10;
		} else {
			mCanInfo.STERRING_WHELL_STATUS = (temp - 65536) / 10;
		}
	}

	static String radarSave = "";

	void analyzeRaderData(byte[] msg) {
		// TODO Auto-generated method stub

		if (radarSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			radarSave = BytesUtil.bytesToHexString(msg);
		}

		if (((int) (msg[3] & 0xff)) == 0x02) {
			mCanInfo.BACK_LEFT_DISTANCE = ((int) (msg[4] & 0xff)) == 0 ? 1
					: ((int) (msg[4] & 0xff)) == 5 ? 0
							: ((int) (msg[4] & 0xff));
			mCanInfo.BACK_MIDDLE_LEFT_DISTANCE = ((int) (msg[5] & 0xff)) == 0 ? 1
					: ((int) (msg[5] & 0xff)) == 5 ? 0
							: ((int) (msg[5] & 0xff));
			mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE = ((int) (msg[5] & 0xff)) == 0 ? 1
					: ((int) (msg[5] & 0xff)) == 5 ? 0
							: ((int) (msg[5] & 0xff));
			mCanInfo.BACK_RIGHT_DISTANCE = ((int) (msg[6] & 0xff)) == 0 ? 1
					: ((int) (msg[6] & 0xff)) == 5 ? 0
							: ((int) (msg[6] & 0xff));

			mCanInfo.FRONT_LEFT_DISTANCE = ((int) (msg[7] & 0xff)) == 0 ? 1
					: ((int) (msg[7] & 0xff)) == 5 ? 0
							: ((int) (msg[7] & 0xff));
			mCanInfo.FRONT_MIDDLE_LEFT_DISTANCE = ((int) (msg[8] & 0xff)) == 0 ? 1
					: ((int) (msg[8] & 0xff)) == 5 ? 0
							: ((int) (msg[8] & 0xff));
			mCanInfo.FRONT_MIDDLE_RIGHT_DISTANCE = ((int) (msg[8] & 0xff)) == 0 ? 1
					: ((int) (msg[8] & 0xff)) == 5 ? 0
							: ((int) (msg[8] & 0xff));
			mCanInfo.FRONT_RIGHT_DISTANCE = ((int) (msg[9] & 0xff)) == 0 ? 1
					: ((int) (msg[9] & 0xff)) == 5 ? 0
							: ((int) (msg[9] & 0xff));
		} else {
			mCanInfo.BACK_LEFT_DISTANCE = 0;
			mCanInfo.BACK_MIDDLE_LEFT_DISTANCE = 0;
			mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE = 0;
			mCanInfo.BACK_RIGHT_DISTANCE = 0;

			mCanInfo.FRONT_LEFT_DISTANCE = 0;
			mCanInfo.FRONT_MIDDLE_LEFT_DISTANCE = 0;
			mCanInfo.FRONT_MIDDLE_RIGHT_DISTANCE = 0;
			mCanInfo.FRONT_RIGHT_DISTANCE = 0;
		}

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
		mCanInfo.AIR_CONDITIONER_STATUS = (int) ((msg[3] >> 7) & 0x01);
		mCanInfo.AC_INDICATOR_STATUS = (int) ((msg[3] >> 6) & 0x01);
		mCanInfo.CYCLE_INDICATOR = (int) ((msg[3] >> 5) & 0x01);
		mCanInfo.SMALL_LANTERN_INDICATOR = (int) ((msg[3] >> 3) & 0x01);
		mCanInfo.REAR_LAMP_INDICATOR = (int) ((msg[3] >> 0) & 0x01);

		mCanInfo.UPWARD_AIR_INDICATOR = (int) ((msg[4] >> 7) & 0x01);
		mCanInfo.PARALLEL_AIR_INDICATOR = (int) ((msg[4] >> 6) & 0x01);
		mCanInfo.DOWNWARD_AIR_INDICATOR = (int) ((msg[4] >> 5) & 0x01);

		mCanInfo.AIR_RATE = (int) ((msg[4] >> 0) & 0x0f);
		mCanInfo.MAX_FRONT_LAMP_INDICATOR = (int) ((msg[7] >> 7) & 0x01);

		int temp = (int) ((msg[7] >> 0) & 0x01);
		int temp1 = (int) (msg[5] & 0xff);
		int temp2 = (int) (msg[6] & 0xff);
		if (temp == 0) { // 摄氏
			mCanInfo.DRIVING_POSITON_TEMP = temp1 == 0 ? 0
					: temp1 == 0xFF ? 255 : temp1 * 0.5f;
			mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = temp2 == 0 ? 0
					: temp2 == 0xFF ? 255 : temp2 * 0.5f;
		} else { // 华氏
			mCanInfo.DRIVING_POSITON_TEMP = temp1 == 0 ? 0
					: temp1 == 0xFF ? 255 : temp1;
			mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = temp2 == 0 ? 0
					: temp2 == 0xFF ? 255 : temp2;
		}
	}

	/*
	 * 方向盘按键 STEERING_BUTTON_MODE 0：无按键或释放 1：vol+ 2：vol- 3：menuup 4：menu down 5：
	 * PHONE 6：mute 7：SRC 8：SPEECH/MIC 9:answer phone 10:hangup phone
	 */
	static String buttonSave = "";

	void analyzeSteeringButtonData(byte[] msg) {
		// TODO Auto-generated method stub
		if (buttonSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			buttonSave = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.STEERING_BUTTON_STATUS = 1;

		switch ((int) (msg[3] & 0xFF)) {
		case 0x14:
		case 0x03:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLUP;
			break;
		case 0x15:
		case 0x04:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLDOW;
			break;
		case 0x13:
		case 0x18:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUDOWN;
			break;
		case 0x12:
		case 0x17:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUUP;
			break;
		case 0x11:
		case 0x21:
		case 0x02:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
			break;
		case 0x30:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.ANSWER;
			break;
		case 0x31:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.HANGUP;
			break;
		case 0x16:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MUTE;
			break;
		case 0x20:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.CANINFOPAGE;
			break;
		default:
			mCanInfo.STEERING_BUTTON_MODE = 0;
			mCanInfo.STEERING_BUTTON_STATUS = 0;
			break;
		}

	}

}
