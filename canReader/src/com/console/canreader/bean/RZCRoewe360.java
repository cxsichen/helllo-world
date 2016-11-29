package com.console.canreader.bean;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.Trace;

import android.util.Log;

public class RZCRoewe360 extends AnalyzeUtils {
	// 数据类型
	public static final int comID = 1;
	// Head Code
	public static int HEAD_CODE = 0x2e;
	// DataType
	// 背光
	public static int BACK_LIGHT_DATA = 0x14;
	// 车速
	public static int CAR_SPEED_DATA = 0x16;
	// 方向盘按键
	public static int STEERING_BUTTON_DATA = 0x20;
	// 空调信息
	public static int AIR_CONDITIONER_DATA = 0x21;
	// 后雷达信息
	public static int BACK_RADER_DATA = 0x22;
	// 前雷达信息
	public static int FRONT_RADER_DATA = 0x23;
	// 基本信息
	public static int BASIC_INFO_DATA = 0x24;
	// 车身信息
	public static int CAR_INFO_DATA = 0x24;
	// 泊车辅助状态
	public static int PARK_ASSIT_DATA = 0x25;
	// 方向盘转角
	public static int STEERING_TURN_DATA = 0x29;
	// 功放状态
	public static int POWER_AMPLIFIER_DATA = 0x27;
	// 版本信息
	public static int VERSION_DATA = 0x30;
	// 车身警告信息
	public static int CAR_INFO_WARNING = 0x65;
	// 方向盘命令
	public static int STEERING_ORDER_DATA = 0x2F;
	// 车身时间信息
	public static int CAR_TIME_INFO = 0x50;
	// 环境温度信息
	public static int AMBIENT_TEMP_INFO = 0x51;

	public CanInfo getCanInfo() {
		return mCanInfo;
	}

	@Override
	public void analyzeEach(byte[] msg) {
		// TODO Auto-generated method stub
		try {
			if (msg == null)
				return;
			if ((int) (msg[comID] & 0xFF) == BACK_LIGHT_DATA) {
				mCanInfo.CHANGE_STATUS = 0;
				analyzeBackLightData(msg);
			} else if ((int) (msg[comID] & 0xFF) == CAR_SPEED_DATA) {
				mCanInfo.CHANGE_STATUS = 1;
				analyzeCarSpeedData(msg);
			} else if ((int) (msg[comID] & 0xFF) == STEERING_BUTTON_DATA) {
				mCanInfo.CHANGE_STATUS = 2;
				analyzeSteeringButtonData(msg);
			} else if ((int) (msg[comID] & 0xFF) == AIR_CONDITIONER_DATA) {
				mCanInfo.CHANGE_STATUS = 3;
				analyzeAirConditionData(msg);
			} else if ((int) (msg[comID] & 0xFF) == BACK_RADER_DATA) {
				mCanInfo.CHANGE_STATUS = 4;
				analyzeBackRaderData(msg);
			} else if ((int) (msg[comID] & 0xFF) == FRONT_RADER_DATA) {
				mCanInfo.CHANGE_STATUS = 5;
				analyzeFrontRaderData(msg);
			}
			// else if ((int) (msg[i] & 0xFF) == BASIC_INFO_DATA) {
			// mCanInfo.CHANGE_STATUS = 6;
			// analyzeBasicInfoData(msg);
			// }
			else if ((int) (msg[comID] & 0xFF) == PARK_ASSIT_DATA) {
				mCanInfo.CHANGE_STATUS = 7;
				analyzeParkAssitData(msg);
			} else if ((int) (msg[comID] & 0xFF) == STEERING_TURN_DATA) {
				mCanInfo.CHANGE_STATUS = 8;
				analyzeSteeringTurnData(msg);
			} else if ((int) (msg[comID] & 0xFF) == POWER_AMPLIFIER_DATA) {
				mCanInfo.CHANGE_STATUS = 9;
				analyzePowerAmplifierData(msg);
			} else if ((int) (msg[comID] & 0xFF) == CAR_INFO_DATA) {
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData(msg);
			} else if ((int) (msg[comID] & 0xFF) == CAR_INFO_WARNING) {
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoWarning(msg);
			} else {
				mCanInfo.CHANGE_STATUS = 8888;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	private void analyzeCarInfoWarning(byte[] msg) {
		mCanInfo.SAFETY_BELT_STATUS = (int) (msg[3] & 0x01);
	}

	void analyzeCarInfoData(byte[] msg) {
		// TODO Auto-generated method stub
		mCanInfo.RIGHT_FORONTDOOR_STATUS = (int) ((msg[3] >> 7) & 0x01);
		mCanInfo.LEFT_FORONTDOOR_STATUS = (int) ((msg[3] >> 6) & 0x01);
		mCanInfo.RIGHT_BACKDOOR_STATUS = (int) ((msg[3] >> 5) & 0x01);
		mCanInfo.LEFT_BACKDOOR_STATUS = (int) ((msg[3] >> 4) & 0x01);
		mCanInfo.TRUNK_STATUS = (int) ((msg[3] >> 3) & 0x01);

		mCanInfo.HANDBRAKE_STATUS = (int) ((msg[4] >> 3) & 0x01);

	}

	void analyzePowerAmplifierData(byte[] msg) {
		// TODO Auto-generated method stub
		mCanInfo.POWER_AMPLIFIER_TYPE = (int) (msg[3] & 0xFF);
		mCanInfo.POWER_AMPLIFIER_VOlUME = (int) msg[4];
		mCanInfo.POWER_AMPLIFIER_BALANCE = (int) msg[5];
		mCanInfo.POWER_AMPLIFIER_FADER = (int) msg[6];
		mCanInfo.POWER_AMPLIFIER_BASS = (int) msg[7];
		mCanInfo.POWER_AMPLIFIER_MIDTONE = (int) msg[8];
		mCanInfo.POWER_AMPLIFIER_TREBLE = (int) msg[9];
		mCanInfo.POWER_AMPLIFIER_CHANGE = (int) (msg[10] & 0xFF);

	}

	void analyzeSteeringTurnData(byte[] msg) {
		// TODO Auto-generated method stub
		int turnTemp = ((int) msg[4] & 0xFF) << 8 | ((int) msg[3] & 0xFF);
		int turnTemp2 = (32768 - turnTemp) * 540 / 0x2000;
		Trace.i("turnTemp2=====" + turnTemp2);
		if (turnTemp2 >= 540) {
			mCanInfo.STERRING_WHELL_STATUS = 540;
		} else if (turnTemp2 <= -540) {
			mCanInfo.STERRING_WHELL_STATUS = -540;
		} else {
			mCanInfo.STERRING_WHELL_STATUS = turnTemp2;
		}
	}

	void analyzeParkAssitData(byte[] msg) {
		// TODO Auto-generated method stub
		if ((int) ((msg[3] >> 2) & 0x01) == 1
				&& (int) ((msg[3] >> 3) & 0x01) == 1) {
			mCanInfo.RADAR_ALARM_STATUS = 1;
		} else {
			mCanInfo.RADAR_ALARM_STATUS = 0;
		}
	}

	void analyzeBasicInfoData(byte[] msg) {
		// TODO Auto-generated method stub
		mCanInfo.LIGHT_MSG = (int) ((msg[3] >> 2) & 0x01);
		mCanInfo.STOPING_STATUS = (int) ((msg[3] >> 1) & 0x01);
		mCanInfo.REVERSE_GEAR_STATUS = (int) ((msg[3] >> 0) & 0x01);
	}

	void analyzeFrontRaderData(byte[] msg) {
		double a = (msg[3] & 0xff) / 3.0f;
		mCanInfo.FRONT_LEFT_DISTANCE = (int) Math.ceil(a);
		mCanInfo.FRONT_MIDDLE_LEFT_DISTANCE = (int) Math.ceil(a);
		mCanInfo.FRONT_MIDDLE_RIGHT_DISTANCE = (int) Math.ceil(a);
		mCanInfo.FRONT_RIGHT_DISTANCE = (int) Math.ceil(a);
	}

	void analyzeBackRaderData(byte[] msg) {
		double a = (msg[3] & 0xff) / 3.0f;
		mCanInfo.BACK_LEFT_DISTANCE = (int) Math.ceil(a);
		mCanInfo.BACK_MIDDLE_LEFT_DISTANCE = (int) Math.ceil(a);
		mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE = (int) Math.ceil(a);
		mCanInfo.BACK_RIGHT_DISTANCE = (int) Math.ceil(a);
	}

	void analyzeAirConditionData(byte[] msg) {
		// TODO Auto-generated method stub
		mCanInfo.AIR_CONDITIONER_STATUS = (int) ((msg[3] >> 7) & 0x01);
		mCanInfo.AC_INDICATOR_STATUS = (int) ((msg[3] >> 6) & 0x01);
		mCanInfo.CYCLE_INDICATOR = (int) ((msg[3] >> 5) & 0x01);
		mCanInfo.LARGE_LANTERN_INDICATOR = (int) ((msg[3] >> 4) & 0x01);
		mCanInfo.SMALL_LANTERN_INDICATOR = (int) ((msg[3] >> 3) & 0x01);
		mCanInfo.DAUL_LAMP_INDICATOR = (int) ((msg[3] >> 2) & 0x01);
		// mCanInfo.MAX_FRONT_LAMP_INDICATOR = (int) ((msg[3] >> 1) & 0x01);
		// mCanInfo.REAR_LAMP_INDICATOR = (int) ((msg[3] >> 0) & 0x01);

		// /////////
		if (((msg[4] >> 7) & 0x01) == 1 || ((msg[4] >> 5) & 0x01) == 1
				|| ((msg[4] >> 4) & 0x01) == 1) {
			mCanInfo.DOWNWARD_AIR_INDICATOR = 1;
		} else {
			mCanInfo.DOWNWARD_AIR_INDICATOR = 0;
		}
		if (((msg[4] >> 7) & 0x01) == 1 || ((msg[4] >> 6) & 0x01) == 1) {
			mCanInfo.PARALLEL_AIR_INDICATOR = 1;
		} else {
			mCanInfo.PARALLEL_AIR_INDICATOR = 0;
		}
		if (((msg[4] >> 4) & 0x01) == 1) {
			mCanInfo.UPWARD_AIR_INDICATOR = 1;
		} else {
			mCanInfo.UPWARD_AIR_INDICATOR = 0;
		}
		mCanInfo.AIR_RATE = (int) (msg[4] & 0x0f);

		int temp = (int) (msg[5] & 0xff);
		mCanInfo.DRIVING_POSITON_TEMP = temp == 0 ? 0 : temp == 31 ? 255
				: (18f + (temp - 1));

		mCanInfo.MAX_FRONT_LAMP_INDICATOR = (int) ((msg[7] >> 7) & 0x01);
		mCanInfo.REAR_LAMP_INDICATOR = (int) ((msg[7] >> 6) & 0x01);
		mCanInfo.AQS_CIRCLE = (int) ((msg[7] >> 5) & 0x01);

		if ((msg[8] >> 7 & 0x01) == 0) {// 室外温度 0是正数 1是负数
			mCanInfo.OUTSIDE_TEMPERATURE = (float) (msg[8] & 0xff);
			Trace.i("outside temp==" + mCanInfo.OUTSIDE_TEMPERATURE);
		} else {
			mCanInfo.OUTSIDE_TEMPERATURE = -1.0f * ((msg[8] & 0xff) - 128);
			Trace.i("outside temp====" + mCanInfo.OUTSIDE_TEMPERATURE);
		}
	}

	void analyzeSteeringButtonData(byte[] msg) {
		switch ((int) (msg[3] & 0xFF)) {
		case 0x01:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLUP;
			break;
		case 0x02:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLDOW;
			break;
		case 0x03:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUDOWN;
			break;
		case 0x04:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUUP;
			break;
		// case 0x05:
		// mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
		// break;
		case 0x06:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.ANSWER;
			break;
		case 0x07:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
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

	void analyzeBackLightData(byte[] msg) {
		// TODO Auto-generated method stub
		mCanInfo.BACK_LIGHT_DATA = (int) (msg[3] & 0xFF);
	}

}
