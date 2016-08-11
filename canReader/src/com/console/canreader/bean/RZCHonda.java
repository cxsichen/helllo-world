package com.console.canreader.bean;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;

import android.util.Log;

public class RZCHonda extends AnalyzeUtils {

	// 空调信息
	public static final int AIR_CONDITIONER_DATA = 0x21;
	// 后雷达信息
	public static final int BACK_RADER_DATA = 0x22;
	// 前雷达信息
	public static final int FRONT_RADER_DATA = 0x23;
	// 方向盘转角
	public static final int STEERING_TURN_DATA = 0x29;

	// Head Code
	public static int HEAD_CODE = 0x2e;
	// DataType
	// 背光
	public static int BACK_LIGHT_DATA = 0x14;
	// 车速
	public static int CAR_SPEED_DATA = 0x16;
	// 方向盘按键
	public static int STEERING_BUTTON_DATA = 0x20;
	

	// 基本信息
	public static int BASIC_INFO_DATA = 0x24;
	// 泊车辅助状态
	public static int PARK_ASSIT_DATA = 0x25;
	
	// 功放状态
	public static int POWER_AMPLIFIER_DATA = 0x27;
	// 版本信息
	public static int VERSION_DATA = 0x30;
	// 车身信息
	public static int CAR_INFO_DATA = 0x41;
	// 方向盘命令
	public static int STEERING_ORDER_DATA = 0x2F;
	// 车身时间信息
	public static int CAR_TIME_INFO = 0x50;
	// 环境温度信息
	public static int AMBIENT_TEMP_INFO = 0x51;

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
				break;
			}
			/*
			 * if ((int) (msg[i] & 0xFF) == BACK_LIGHT_DATA) {
			 * mCanInfo.CHANGE_STATUS = 0; analyzeBackLightData(msg); } else if
			 * ((int) (msg[i] & 0xFF) == CAR_SPEED_DATA) {
			 * mCanInfo.CHANGE_STATUS = 1; analyzeCarSpeedData(msg); } else if
			 * ((int) (msg[i] & 0xFF) == STEERING_BUTTON_DATA) {
			 * mCanInfo.CHANGE_STATUS = 2; analyzeSteeringButtonData(msg); }
			 * else if ((int) (msg[i] & 0xFF) == AIR_CONDITIONER_DATA) {
			 * mCanInfo.CHANGE_STATUS = 3; analyzeAirConditionData(msg); } else
			 * if ((int) (msg[i] & 0xFF) == BACK_RADER_DATA) {
			 * mCanInfo.CHANGE_STATUS = 4; analyzeBackRaderData(msg); } else if
			 * ((int) (msg[i] & 0xFF) == FRONT_RADER_DATA) {
			 * mCanInfo.CHANGE_STATUS = 5; analyzeFrontRaderData(msg); } else if
			 * ((int) (msg[i] & 0xFF) == BASIC_INFO_DATA) {
			 * mCanInfo.CHANGE_STATUS = 6; analyzeBasicInfoData(msg); } else if
			 * ((int) (msg[i] & 0xFF) == PARK_ASSIT_DATA) {
			 * mCanInfo.CHANGE_STATUS = 7; analyzeParkAssitData(msg); } else if
			 * ((int) (msg[i] & 0xFF) == STEERING_TURN_DATA) {
			 * mCanInfo.CHANGE_STATUS = 8; analyzeSteeringTurnData(msg); } else
			 * if ((int) (msg[i] & 0xFF) == POWER_AMPLIFIER_DATA) {
			 * mCanInfo.CHANGE_STATUS = 9; analyzePowerAmplifierData(msg); }
			 * else if ((int) (msg[i] & 0xFF) == CAR_INFO_DATA) {
			 * mCanInfo.CHANGE_STATUS = 10; analyzeCarInfoData(msg); }
			 */
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	void analyzeCarInfoData(byte[] msg) {
		// TODO Auto-generated method stub
		switch ((msg[3] & 0xff)) {
		case 1:
			mCanInfo.SAFETY_BELT_STATUS = (int) ((msg[4] >> 7) & 0x01);
			mCanInfo.DISINFECTON_STATUS = (int) ((msg[4] >> 6) & 0x01);
			mCanInfo.HANDBRAKE_STATUS = (int) ((msg[4] >> 5) & 0x01);
			mCanInfo.TRUNK_STATUS = (int) ((msg[4] >> 4) & 0x01);
			mCanInfo.RIGHT_BACKDOOR_STATUS = (int) ((msg[4] >> 3) & 0x01);
			mCanInfo.LEFT_BACKDOOR_STATUS = (int) ((msg[4] >> 2) & 0x01);
			mCanInfo.RIGHT_FORONTDOOR_STATUS = (int) ((msg[4] >> 1) & 0x01);
			mCanInfo.LEFT_FORONTDOOR_STATUS = (int) ((msg[4] >> 0) & 0x01);
			break;
		case 2:
			mCanInfo.ENGINE_SPEED = (int) (msg[4] & 0xFF) * 256
					+ (int) (msg[5] & 0xFF);
			mCanInfo.DRIVING_SPEED = ((int) (msg[6] & 0xFF) * 256 + (int) (msg[7] & 0xFF)) * 0.01f;
			mCanInfo.BATTERY_VOLTAGE = ((int) (msg[8] & 0xFF) * 256 + (int) (msg[9] & 0xFF)) * 0.01f;

			int temp = (int) (msg[10] & 0xFF) * 256 + (int) (msg[11] & 0xFF);
			if (temp < 32767) {
				mCanInfo.OUTSIDE_TEMPERATURE = temp * 0.1f;
			} else {
				mCanInfo.OUTSIDE_TEMPERATURE = (temp - 65536) * 0.1f;
			}
			mCanInfo.DRIVING_DISTANCE = (int) (msg[12] & 0xFF) * 65536
					+ (int) (msg[13] & 0xFF) * 256 + (int) (msg[14] & 0xFF);
			mCanInfo.REMAIN_FUEL = (int) (msg[15] & 0xFF);
			break;
		case 3:
			mCanInfo.FUEL_WARING_SIGN = (int) ((msg[4] >> 7) & 0x01);
			mCanInfo.BATTERY_WARING_SIGN = (int) ((msg[4] >> 6) & 0x01);
		default:
			break;
		}

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
		int temp = ((int) msg[4] & 0xFF) << 8 | ((int) msg[3] & 0xFF);
		if (temp < 32767) {
			mCanInfo.STERRING_WHELL_STATUS = -temp;
		} else {
			mCanInfo.STERRING_WHELL_STATUS =  65536 -temp ;
		}

	}

	void analyzeParkAssitData(byte[] msg) {
		// TODO Auto-generated method stub
		mCanInfo.PARKING_ASSIT_STATUS = (int) ((msg[3] >> 1) & 0x01);
		mCanInfo.RADAR_ALARM_STATUS = (int) ((msg[3] >> 0) & 0x01);
	}

	void analyzeBasicInfoData(byte[] msg) {
		// TODO Auto-generated method stub
		mCanInfo.LIGHT_MSG = (int) ((msg[3] >> 2) & 0x01);
		mCanInfo.STOPING_STATUS = (int) ((msg[3] >> 1) & 0x01);
		mCanInfo.REVERSE_GEAR_STATUS = (int) ((msg[3] >> 0) & 0x01);
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
		mCanInfo.LARGE_LANTERN_INDICATOR = (int) ((msg[3] >> 3) & 0x01);
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
		mCanInfo.STEERING_BUTTON_MODE = (int) (msg[3] & 0xFF);
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
