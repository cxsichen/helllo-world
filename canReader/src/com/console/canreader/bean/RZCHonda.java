package com.console.canreader.bean;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;

import android.util.Log;

public class RZCHonda extends AnalyzeUtils {

	// 方向盘按键
	public static final int STEERING_BUTTON_DATA = 0x20;
	// 空调信息
	public static final int AIR_CONDITIONER_DATA = 0x21;
	// 基本信息
	public static final int CAR_INFO_DATA = 0x24;
	// 方向盘转角
	public static final int STEERING_TURN_DATA = 0x29;
	// 后雷达信息
	public static final int BACK_RADER_DATA = 0x22;
	// 前雷达信息
	public static final int FRONT_RADER_DATA = 0x23;


	public RZCHonda(byte[] msg, int i) {
		// TODO Auto-generated constructor stub
		super(msg, i);
	}

	public CanInfo getCanInfo() {
		return mCanInfo;
	}

	@Override
	public void analyzeEach(byte[] msg, int i) {
		// TODO Auto-generated method stub
		try {
			if (msg == null)
				return;
			switch ((int) (msg[i] & 0xFF)) {
			case CAR_INFO_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData(msg);
				break;
			case STEERING_BUTTON_DATA:
				mCanInfo.CHANGE_STATUS = 2;
				analyzeSteeringButtonData(msg);
				break;
			case AIR_CONDITIONER_DATA:
				mCanInfo.CHANGE_STATUS = 3;
				analyzeAirConditionData(msg);
				break;
			case BACK_RADER_DATA:
				mCanInfo.CHANGE_STATUS = 4;
				analyzeBackRaderData(msg);
				break;
			case FRONT_RADER_DATA:
				mCanInfo.CHANGE_STATUS = 5;
				analyzeFrontRaderData(msg);
				break;
			case STEERING_TURN_DATA:
				mCanInfo.CHANGE_STATUS = 8;
				analyzeSteeringTurnData(msg);
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
		mCanInfo.HOOD_STATUS = (int) ((msg[3] >> 2) & 0x01);

		mCanInfo.HANDBRAKE_STATUS = (int) ((msg[4] >> 1) & 0x01);

	}

	void analyzeSteeringTurnData(byte[] msg) {
		// TODO Auto-generated method stub
		int temp = ((int) msg[4] & 0xFF) << 8 | ((int) msg[3] & 0xFF);
		if (temp < 32767) {
			mCanInfo.STERRING_WHELL_STATUS = -temp;
		} else {
			mCanInfo.STERRING_WHELL_STATUS = 65536 - temp;
		}
	}

	void analyzeFrontRaderData(byte[] msg) {
		// TODO Auto-generated method stub
		mCanInfo.FRONT_LEFT_DISTANCE = (int) (msg[3] & 0xff);
		mCanInfo.FRONT_MIDDLE_LEFT_DISTANCE = (int) (msg[4] & 0xff);
		mCanInfo.FRONT_MIDDLE_RIGHT_DISTANCE = (int) (msg[5] & 0xff);
		mCanInfo.FRONT_RIGHT_DISTANCE = (int) (msg[6] & 0xff);
	}

	void analyzeBackRaderData(byte[] msg) {
		// TODO Auto-generated method stub
		mCanInfo.BACK_LEFT_DISTANCE = (int) (msg[3] & 0xff);
		mCanInfo.BACK_MIDDLE_LEFT_DISTANCE = (int) (msg[4] & 0xff);
		mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE = (int) (msg[5] & 0xff);
		mCanInfo.BACK_RIGHT_DISTANCE = (int) (msg[6] & 0xff);
	}

	void analyzeAirConditionData(byte[] msg) {
		// TODO Auto-generated method stub
		mCanInfo.AIR_CONDITIONER_STATUS = (int) ((msg[3] >> 7) & 0x01);
		mCanInfo.AC_INDICATOR_STATUS = (int) ((msg[3] >> 6) & 0x01);
		mCanInfo.CYCLE_INDICATOR = (int) ((msg[3] >> 5) & 0x01);
		mCanInfo.SMALL_LANTERN_INDICATOR = (int) ((msg[3] >> 3) & 0x01);
		mCanInfo.MAX_FRONT_LAMP_INDICATOR = (int) ((msg[7] >> 7) & 0x01);
		mCanInfo.REAR_LAMP_INDICATOR = (int) ((msg[3] >> 0) & 0x01);

		mCanInfo.UPWARD_AIR_INDICATOR = (int) ((msg[4] >> 7) & 0x01);
		mCanInfo.PARALLEL_AIR_INDICATOR = (int) ((msg[4] >> 6) & 0x01);
		mCanInfo.DOWNWARD_AIR_INDICATOR = (int) ((msg[4] >> 5) & 0x01);

		mCanInfo.AIR_RATE = (int) (msg[4] & 0x0f);

		int tempMode = (int) ((msg[8] >> 7) & 0x01);

		int temp = (int) (msg[5] & 0xff);
		if (tempMode == 0) {
			mCanInfo.DRIVING_POSITON_TEMP = temp == 0 ? 0 : temp == 31 ? 255
					: temp;
		} else {
			mCanInfo.DRIVING_POSITON_TEMP = temp == 0 ? 0 : temp == 31 ? 255
					: temp * 0.5f;
		}

		temp = (int) (msg[6] & 0xff);

		if (tempMode == 0) {
			mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = temp == 0 ? 0
					: temp == 31 ? 255 : temp;
		} else {
			mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = temp == 0 ? 0
					: temp == 31 ? 255 : temp * 0.5f;
		}

	}

	void analyzeSteeringButtonData(byte[] msg) {
		// TODO Auto-generated method stub
		switch ((int) (msg[3] & 0xFF)) {
		case 0x01:
		case 0x81:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLUP;
			break;
		case 0x02:
		case 0x82:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLDOW;
			break;
		case 0x03:
		case 0x14:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUDOWN;
			break;
		case 0x04:
		case 0x13:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUUP;
			break;
		case 0x07:
		case 0x08:
		case 0x17:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
			break;
		case 0x09:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.ANSWER;
			break;
		case 0x0A:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.HANGUP;
			break;
		case 0x30:
		case 0x86:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MUTE;
			break;
		default:
			mCanInfo.STEERING_BUTTON_MODE = 0;
			break;
		}

		mCanInfo.STEERING_BUTTON_STATUS = (int) (msg[4] & 0xFF);
	}
}
