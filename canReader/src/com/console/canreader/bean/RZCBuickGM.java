package com.console.canreader.bean;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.Trace;

import android.util.Log;

public class RZCBuickGM extends AnalyzeUtils {
	// 数据类型
	public static final int comID = 1;
	// Head Code
	public static final int HEAD_CODE = 0x2e;
	// DataType
	// 方向盘按键
	public static final int STEERING_BUTTON_DATA = 0x01;
	// 面板盘按键 0x02

	// 空调信息【0x03】
	public static final int AIR_CONDITIONER_DATA = 0x03;
	public static final int AIR_CONDITIONER_DATA_1 = 0x05;

	// 小灯信息【0x04】

	// 车身空调控制信息【0x05】

	// 车身中控设定信息【Ox06】
	// 倒车雷达开关信息【0x07】 OnStar 电话信息【0x08】 OnStar 状态信息【0x09】 车身中控设定信息 2【Ox0A】
	// 后雷达信息
	public static final int RADER_DATA = 0x22;
	// 前雷达信息
	public static final int FRONT_RADER_DATA = 0x23;
	// 方向盘转角
	public static final int STEERING_TURN_DATA = 0x26;
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
			case AIR_CONDITIONER_DATA_1:
				mCanInfo.CHANGE_STATUS = 3;
				analyzeAirConditionData_1(msg);
				break;
			case STEERING_BUTTON_DATA:
				mCanInfo.CHANGE_STATUS = 2;
				analyzeSteeringButtonData(msg);
				break;
			case RADER_DATA:
				mCanInfo.CHANGE_STATUS = 4;
				analyzeRaderData(msg);
				break;
			case FRONT_RADER_DATA:
				mCanInfo.CHANGE_STATUS = 5;
				analyzeFrontRaderData(msg);
				break;
			case CAR_INFO_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData(msg);
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

	}

	void analyzeSteeringTurnData(byte[] msg) {
		// TODO Auto-generated method stub

		int temp = ((int) msg[4] & 0xFF) << 8 | ((int) msg[3] & 0xFF);
		if (temp < (0xfff / 2)) {
			mCanInfo.STERRING_WHELL_STATUS = (int) (temp * 1.4);
		} else {
			mCanInfo.STERRING_WHELL_STATUS = (int) ((temp - 0xfff) * 1.4);
		}

	}

	void analyzeRaderData(byte[] msg) {
		// TODO Auto-generated method stub
		double a = (msg[3] & 0xff) / 2.0f;
		mCanInfo.BACK_LEFT_DISTANCE = (int) Math.ceil(a);
		a = (msg[4] & 0xff) / 2.0f;
		mCanInfo.BACK_LEFT_DISTANCE = (int) Math.ceil(a);
		;
		a = (msg[5] & 0xff) / 2.0f;
		mCanInfo.BACK_LEFT_DISTANCE = (int) Math.ceil(a);
		;
		a = (msg[6] & 0xff) / 2.0f;
		mCanInfo.BACK_LEFT_DISTANCE = (int) Math.ceil(a);
		;

	}

	void analyzeFrontRaderData(byte[] msg) {
		// TODO Auto-generated method stub
		double a = (msg[3] & 0xff) / 2.0f;
		mCanInfo.FRONT_LEFT_DISTANCE = (int) Math.ceil(a);
		a = (msg[4] & 0xff) / 2.0f;
		mCanInfo.FRONT_MIDDLE_LEFT_DISTANCE = (int) Math.ceil(a);
		;
		a = (msg[5] & 0xff) / 2.0f;
		mCanInfo.FRONT_MIDDLE_RIGHT_DISTANCE = (int) Math.ceil(a);
		;
		a = (msg[6] & 0xff) / 2.0f;
		mCanInfo.FRONT_RIGHT_DISTANCE = (int) Math.ceil(a);
		;
	}

	void analyzeAirConditionData_1(byte[] msg) {
		// TODO Auto-generated method stub

		// ////////////////////////
		mCanInfo.MAX_FRONT_LAMP_INDICATOR = (int) (msg[3] & 0x01);
		mCanInfo.REAR_LAMP_INDICATOR = (int) ((msg[3] >> 1) & 0x01);

		// ///////////////////
	}

	void analyzeAirConditionData(byte[] msg) {

		mCanInfo.AIR_CONDITIONER_STATUS = (int) ((msg[3] >> 7) & 0x01);
		mCanInfo.AC_INDICATOR_STATUS = (int) ((msg[3] >> 6) & 0x01);
		mCanInfo.CYCLE_INDICATOR = (int) ((msg[3] >> 5) & 0x01);
		// mCanInfo.AQS_CIRCLE=(int) ((msg[3] >> 3) & 0x01);
		// mCanInfo.LARGE_LANTERN_INDICATOR = (int) ((msg[3] >> 4) & 0x01);
		// mCanInfo.SMALL_LANTERN_INDICATOR = (int) ((msg[3] >> 3) & 0x01);
		// ////////////////////////
		if ((msg[4] & 0x0f) == 0) {
			mCanInfo.UPWARD_AIR_INDICATOR = 0;
			mCanInfo.PARALLEL_AIR_INDICATOR = 0;
			mCanInfo.DOWNWARD_AIR_INDICATOR = 0;
		}
		if ((msg[4] & 0x0f) == 1 || (msg[4] & 0x0f) == 2
				|| (msg[4] & 0x0f) == 6 || (msg[4] & 0x0f) == 7
				|| (msg[4] & 0x0f) == 8 || (msg[4] & 0x0f) == 9) {
			mCanInfo.UPWARD_AIR_INDICATOR = 1;
		} else {
			mCanInfo.UPWARD_AIR_INDICATOR = 0;
		}
		if ((msg[4] & 0x0f) == 1 || (msg[4] & 0x0f) == 4
				|| (msg[4] & 0x0f) == 5 || (msg[4] & 0x0f) == 6
				|| (msg[4] & 0x0f) == 9) {
			mCanInfo.PARALLEL_AIR_INDICATOR = 1;
		} else {
			mCanInfo.PARALLEL_AIR_INDICATOR = 0;
		}
		if ((msg[4] & 0x0f) == 1 || (msg[4] & 0x0f) == 3
				|| (msg[4] & 0x0f) == 4 || (msg[4] & 0x0f) == 8
				| (msg[4] & 0x0f) == 9) {
			mCanInfo.DOWNWARD_AIR_INDICATOR = 1;
		} else {
			mCanInfo.DOWNWARD_AIR_INDICATOR = 0;
		}
		mCanInfo.LEFT_SEAT_TEMP = ((int) ((msg[7] >> 4) & 0x0f)) > 4 ? 4
				: ((int) ((msg[7] >> 4) & 0x0f));
		mCanInfo.RIGTHT_SEAT_TEMP = ((int) ((msg[7] >> 0) & 0x0f)) > 4 ? 4
				: ((int) ((msg[7] >> 0) & 0x0f));

		if ((int) ((msg[4] >> 4) & 0x01) == 1) {
			if ((int) (msg[3] & 0x01) == 1) {
				mCanInfo.AIR_RATE = -1;
			} else {
				mCanInfo.AIR_RATE = 8;
			}
		} else {
			mCanInfo.AIR_RATE = (int) (msg[3] & 0x07);
		}
		// /////////////////////////
		int temp = 0;

		temp = (int) (msg[5] & 0xff);
		if (temp <= 0x1C && temp > 0x00) {
			mCanInfo.DRIVING_POSITON_TEMP = 17 + (temp - 0x01) * 0.5f;
		} else {
			switch (temp) {
			case 0x00:
				mCanInfo.DRIVING_POSITON_TEMP = 0;
				break;
			case 0x1E:
				mCanInfo.DRIVING_POSITON_TEMP = 255;
				break;
			case 0x1D:
				mCanInfo.DRIVING_POSITON_TEMP = 16f;
				break;
			case 0x1F:
				mCanInfo.DRIVING_POSITON_TEMP = 16.5f;
				break;
			case 0x20:
				mCanInfo.DRIVING_POSITON_TEMP = 15f;
				break;
			case 0x21:
				mCanInfo.DRIVING_POSITON_TEMP = 15.5f;
				break;
			case 0x22:
				mCanInfo.DRIVING_POSITON_TEMP = 31f;
				break;

			default:
				break;
			}
		}

		temp = (int) (msg[6] & 0xff);

		if (temp <= 0x1C && temp > 0x00) {
			mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = 17 + (temp - 0x01) * 0.5f;
		} else {
			switch (temp) {
			case 0x00:
				mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = 0;
				break;
			case 0x1E:
				mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = 255;
				break;
			case 0x1D:
				mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = 16f;
				break;
			case 0x1F:
				mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = 16.5f;
				break;
			case 0x20:
				mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = 15f;
				break;
			case 0x21:
				mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = 15.5f;
				break;
			case 0x22:
				mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = 31f;
				break;

			default:
				break;
			}
		}

		if ((msg[8] & 0xff) >= 0xf5) {
			mCanInfo.OUTSIDE_TEMPERATURE = -1 * (((msg[8] & 0xff) ^ 0xff) + 1);
		} else {
			mCanInfo.OUTSIDE_TEMPERATURE = (float) (msg[8] & 0xff);
		}
		// ///////////////////////////

		// ////////////////////////////

	}

	/*
	 * 方向盘按键 STEERING_BUTTON_MODE 0：无按键或释放 1：vol+ 2：vol- 3：menuup 4：menu down
	 * 5：SRC 6SPEECH/answer phone 7:hangup phone
	 */

	void analyzeSteeringButtonData(byte[] msg) {
		Trace.i("msg.===" + (msg[3] & 0xFF));
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
		case 0x05:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
			break;
		case 0x06:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.ANSWER;
			break;
		case 0x07:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.HANGUP;
			break;
		default:
			mCanInfo.STEERING_BUTTON_MODE = 0;
			break;
		}

		mCanInfo.STEERING_BUTTON_STATUS = (int) (msg[4] & 0xFF);

	}

}
