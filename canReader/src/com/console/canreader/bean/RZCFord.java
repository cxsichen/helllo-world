package com.console.canreader.bean;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;


import android.util.Log;

public class RZCFord extends AnalyzeUtils {
	// 数据类型
	public static final int comID = 1;
	// Head Code
	public static final int HEAD_CODE = 0x2e;
	// DataType
	// 方向盘按键
	public static final int STEERING_BUTTON_DATA = 0x20;
	// 空调信息
	public static final int AIR_CONDITIONER_DATA = 0x21;
	// 后雷达信息
	public static final int BACK_RADER_DATA = 0x22;
	// 前雷达信息
	public static final int FRONT_RADER_DATA = 0x23;
	// 方向盘转角
	public static final int STEERING_TURN_DATA = 0x31;
	// 车身信息
	public static final int CAR_INFO_DATA = 0x24;

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
			case STEERING_BUTTON_DATA:
				mCanInfo.CHANGE_STATUS = 2;
				analyzeSteeringButtonData(msg);
				break;
			case BACK_RADER_DATA:
				mCanInfo.CHANGE_STATUS = 4;
				analyzeBackRaderData(msg);
				break;
			case FRONT_RADER_DATA:
				mCanInfo.CHANGE_STATUS = 5;
				analyzeFrontRaderData(msg);
				break;
			case CAR_INFO_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData(msg);
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

	void analyzeCarInfoData(byte[] msg) {
		// TODO Auto-generated method stub

		mCanInfo.RIGHT_FORONTDOOR_STATUS = (int) ((msg[3] >> 7) & 0x01);
		mCanInfo.LEFT_FORONTDOOR_STATUS = (int) ((msg[3] >> 6) & 0x01);
		mCanInfo.RIGHT_BACKDOOR_STATUS = (int) ((msg[3] >> 5) & 0x01);
		mCanInfo.LEFT_BACKDOOR_STATUS = (int) ((msg[3] >> 4) & 0x01);
		mCanInfo.TRUNK_STATUS = (int) ((msg[3] >> 3) & 0x01);

		mCanInfo.HANDBRAKE_STATUS = (int) ((msg[4] >> 3) & 0x01);
		mCanInfo.SAFETY_BELT_STATUS = (int) ((msg[4] >> 7) & 0x01);
		mCanInfo.BATTERY_VOLTAGE = -1;
		mCanInfo.REMAIN_FUEL = -1;
	}

	void analyzeSteeringTurnData(byte[] msg) {
		// TODO Auto-generated method stub
		mCanInfo.STERRING_WHELL_STATUS = (0x1e00 - (((int) msg[3] & 0xFF) << 8 | ((int) msg[4] & 0xFF))) / 9;
	}

	void analyzeFrontRaderData(byte[] msg) {
		// TODO Auto-generated method stub

		mCanInfo.FRONT_LEFT_DISTANCE = (((int) (msg[3] & 0xff)) == 0 ? 0
				: (((((float) (msg[3] & 0xff)) / (0x1f / 4f)) + 1) > 4 ? 4
						: (int) ((((float) (msg[3] & 0xff)) / (0x1f / 4f)) + 1)));
		mCanInfo.FRONT_MIDDLE_LEFT_DISTANCE = (((int) (msg[4] & 0xff)) == 0 ? 0
				: (((((float) (msg[4] & 0xff)) / (0x1f / 4f)) + 1) > 4 ? 4
						: (int) ((((float) (msg[4] & 0xff)) / (0x1f / 4f)) + 1)));
		mCanInfo.FRONT_MIDDLE_RIGHT_DISTANCE = (((int) (msg[5] & 0xff)) == 0 ? 0
				: (((((float) (msg[5] & 0xff)) / (0x1f / 4f)) + 1) > 4 ? 4
						: (int) ((((float) (msg[5] & 0xff)) / (0x1f / 4f)) + 1)));
		mCanInfo.FRONT_RIGHT_DISTANCE = (((int) (msg[6] & 0xff)) == 0 ? 0
				: (((((float) (msg[6] & 0xff)) / (0x1f / 4f)) + 1) > 4 ? 4
						: (int) ((((float) (msg[6] & 0xff)) / (0x1f / 4f)) + 1)));

	}

	void analyzeBackRaderData(byte[] msg) {
		// TODO Auto-generated method stub
		mCanInfo.BACK_LEFT_DISTANCE = (((int) (msg[3] & 0xff)) == 0 ? 0
				: (((((float) (msg[3] & 0xff)) / (0x1f / 4f)) + 1) > 4 ? 4
						: (int) ((((float) (msg[3] & 0xff)) / (0x1f / 4f)) + 1)));
		mCanInfo.BACK_MIDDLE_LEFT_DISTANCE = (((int) (msg[4] & 0xff)) == 0 ? 0
				: (((((float) (msg[4] & 0xff)) / (0x1f / 4f)) + 1) > 4 ? 4
						: (int) ((((float) (msg[4] & 0xff)) / (0x1f / 4f)) + 1)));
		mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE = (((int) (msg[5] & 0xff)) == 0 ? 0
				: (((((float) (msg[5] & 0xff)) / (0x1f / 4f)) + 1) > 4 ? 4
						: (int) ((((float) (msg[5] & 0xff)) / (0x1f / 4f)) + 1)));
		mCanInfo.BACK_RIGHT_DISTANCE = (((int) (msg[6] & 0xff)) == 0 ? 0
				: (((((float) (msg[6] & 0xff)) / (0x1f / 4f)) + 1) > 4 ? 4
						: (int) ((((float) (msg[6] & 0xff)) / (0x1f / 4f)) + 1)));
	}

	void analyzeAirConditionData_1(byte[] msg) {
		// TODO Auto-generated method stub
		if ((int) (msg[3] & 0xff) == 0x16) {
			mCanInfo.LEFT_SEAT_TEMP = (int) (msg[4] & 0xff);
		} else if ((int) (msg[3] & 0xff) == 0x17) {
			mCanInfo.RIGTHT_SEAT_TEMP = (int) (msg[4] & 0xff);
		}
	}

	void analyzeAirConditionData(byte[] msg) {
		// TODO Auto-generated method stub

		mCanInfo.AIR_CONDITIONER_STATUS = (int) ((msg[3] >> 7) & 0x01);
		mCanInfo.AC_INDICATOR_STATUS = (int) ((msg[3] >> 6) & 0x01);
		mCanInfo.CYCLE_INDICATOR = (int) ((msg[3] >> 5) & 0x01);
		mCanInfo.SMALL_LANTERN_INDICATOR = (int) ((msg[3] >> 3) & 0x01);
		mCanInfo.MAX_FRONT_LAMP_INDICATOR = (int) ((msg[3] >> 1) & 0x01);

		mCanInfo.UPWARD_AIR_INDICATOR = (int) ((msg[4] >> 7) & 0x01);
		mCanInfo.PARALLEL_AIR_INDICATOR = (int) ((msg[4] >> 6) & 0x01);
		mCanInfo.DOWNWARD_AIR_INDICATOR = (int) ((msg[4] >> 5) & 0x01);

		mCanInfo.AIR_RATE = (int) (msg[4] & 0x0f);

		int temp = (int) (msg[5] & 0xff);
		mCanInfo.DRIVING_POSITON_TEMP = temp == 0 ? 0 : temp == 0x7F ? 255
				: (15.5f + (temp - 0x1F) * 0.5f);

		temp = (int) (msg[6] & 0xff);
		mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = temp == 0 ? 0
				: temp == 0x7F ? 255 : (15.5f + (temp - 0x1F) * 0.5f);

		mCanInfo.OUTSIDE_TEMPERATURE = (int) msg[8];

	}

	/*
	 * 方向盘按键 STEERING_BUTTON_MODE 0：无按键或释放 1：vol+ 2：vol- 3：menuup 4：menu down 5：
	 * PHONE 6：mute 7：SRC 8：SPEECH/MIC 9:answer phone 10:hangup phone
	 */

	void analyzeSteeringButtonData(byte[] msg) {
		// TODO Auto-generated method stub

		switch ((int) (msg[3] & 0xFF)) {
		case 0x01:
		case 0xf0:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLUP;
			break;
		case 0x02:
		case 0xf1:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLDOW;
			break;
		case 0x03:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUDOWN;
			break;
		case 0x04:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUUP;
			break;
		case 0x07:
		case 0x37:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
			break;
		case 0x39:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.PHONE;
			break;
		case 0x52:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.ANSWER;
			break;
		case 0x53:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.HANGUP;
			break;
		case 0x5A:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MUTE;
			break;
		default:
			mCanInfo.STEERING_BUTTON_MODE = 0;
			break;
		}

		mCanInfo.STEERING_BUTTON_STATUS = (int) (msg[4] & 0xFF);
	}

	void analyzeCarSpeedData(byte[] msg) {
		// TODO Auto-generated method stub
		mCanInfo.CAR_SPEED_DATA = (((int) msg[3] & 0xFF) << 8 | ((int) msg[4] & 0xFF)) / 16;
	}

}
