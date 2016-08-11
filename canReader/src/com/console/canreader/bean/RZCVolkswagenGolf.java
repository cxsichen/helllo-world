package com.console.canreader.bean;

import android.util.Log;

public class RZCVolkswagenGolf extends AnalyzeUtils {

	// Head Code
	public static final int HEAD_CODE = 0x2e;
	// DataType
	// 背光
	public static final int BACK_LIGHT_DATA = 0x14;
	// 车速
	public static final int CAR_SPEED_DATA = 0x16;
	// 方向盘按键
	public static final int STEERING_BUTTON_DATA = 0x20;
	// 空调信息
	public static final int AIR_CONDITIONER_DATA = 0x21;
	// 后雷达信息
	public static final int BACK_RADER_DATA = 0x22;
	// 前雷达信息
	public static final int FRONT_RADER_DATA = 0x23;
	// 基本信息
	public static final int BASIC_INFO_DATA = 0x24;
	// 泊车辅助状态
	public static final int PARK_ASSIT_DATA = 0x25;
	// 方向盘转角
	public static final int STEERING_TURN_DATA = 0x26;
	// 功放状态
	public static final int POWER_AMPLIFIER_DATA = 0x27;
	// 版本信息
	public static final int VERSION_DATA = 0x30;
	// 车身信息
	public static final int CAR_INFO_DATA = 0x41;
	// 方向盘命令
	public static final int STEERING_ORDER_DATA = 0x2F;
	// 车身时间信息
	public static final int CAR_TIME_INFO = 0x26;
	//环境温度信息
	public static final int AMBIENT_TEMP_INFO = 0x27;

	public RZCVolkswagenGolf(byte[] msg,int i) {
		super(msg,i);
		// TODO Auto-generated constructor stub
		analyze(msg, i);
	}
	

	void analyzeTempInfo(byte[] msg) {
		// TODO Auto-generated method stub
//		int temp = ((int) msg[4] & 0xFF) << 8 | ((int) msg[3] & 0xFF);
//		if (temp < 32767) {
//			mCanInfo.STERRING_WHELL_STATUS = temp;
//		} else {
//			mCanInfo.STERRING_WHELL_STATUS = temp - 65536;
//		}
	}


	void analyzeCarTimeInfo(byte[] msg) {
		// TODO Auto-generated method stub
		if (msg.length > 11) {
			return;
		}
	}

	void analyzeSteeringOrderData(byte[] msg) {
		// TODO Auto-generated method stub
		if (msg.length > 6) {
			return;
		}

	}


	void analyzeCarInfoData(byte[] msg) {
		// TODO Auto-generated method stub
		if (msg.length > 17) {
			return;
		}
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
		if (msg.length > 12) {
			return;
		}
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
		if (msg.length > 6) {
			return;
		}
		int temp = ((int) msg[4] & 0xFF) << 8 | ((int) msg[3] & 0xFF);
		if (temp < 32767) {
			mCanInfo.STERRING_WHELL_STATUS = temp;
		} else {
			mCanInfo.STERRING_WHELL_STATUS = temp - 65536;
		}

	}


	void analyzeParkAssitData(byte[] msg) {
		// TODO Auto-generated method stub
		if (msg.length > 6) {
			return;
		}
		mCanInfo.PARKING_ASSIT_STATUS = (int) ((msg[3] >> 1) & 0x01);
		mCanInfo.RADAR_ALARM_STATUS = (int) ((msg[3] >> 0) & 0x01);
	}


	void analyzeBasicInfoData(byte[] msg) {
		// TODO Auto-generated method stub
		if (msg.length > 6) {
			return;
		}
		mCanInfo.RIGHT_FORONTDOOR_STATUS = (int) ((msg[3] >> 7) & 0x01);
		mCanInfo.LEFT_FORONTDOOR_STATUS = (int) ((msg[3] >> 6) & 0x01);
		mCanInfo.RIGHT_BACKDOOR_STATUS = (int) ((msg[3] >> 5) & 0x01);
		mCanInfo.LEFT_BACKDOOR_STATUS = (int) ((msg[3] >> 4) & 0x01);
		mCanInfo.TRUNK_STATUS = (int) ((msg[3] >> 3) & 0x01);

		mCanInfo.LIGHT_MSG = (int) ((msg[4] >> 2) & 0x01);
		mCanInfo.STOPING_STATUS = (int) ((msg[4] >> 1) & 0x01);
		mCanInfo.REVERSE_GEAR_STATUS = (int) ((msg[4] >> 0) & 0x01);
	}


	void analyzeFrontRaderData(byte[] msg) {
		// TODO Auto-generated method stub
		if (msg.length > 10) {
			return;
		}
		mCanInfo.FRONT_LEFT_DISTANCE = ((int) (msg[3] & 0xff));
		mCanInfo.FRONT_MIDDLE_LEFT_DISTANCE = ((int) (msg[4] & 0xff));
		mCanInfo.FRONT_MIDDLE_RIGHT_DISTANCE = ((int) (msg[5] & 0xff));
		mCanInfo.FRONT_RIGHT_DISTANCE = ((int) (msg[6] & 0xff));

	}


	void analyzeBackRaderData(byte[] msg) {
		// TODO Auto-generated method stub
		if (msg.length > 10) {
			return;
		}
		mCanInfo.BACK_LEFT_DISTANCE = ((int) (msg[3] & 0xff));
		mCanInfo.BACK_MIDDLE_LEFT_DISTANCE = ((int) (msg[4] & 0xff));
		mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE = ((int) (msg[5] & 0xff));
		mCanInfo.BACK_RIGHT_DISTANCE = ((int) (msg[6] & 0xff));

	}


	void analyzeAirConditionData(byte[] msg) {
		// TODO Auto-generated method stub
		if (msg.length > 11) {
			return;
		}
		mCanInfo.AIR_CONDITIONER_STATUS = (int) ((msg[3] >> 7) & 0x01);
		mCanInfo.AC_INDICATOR_STATUS = (int) ((msg[3] >> 6) & 0x01);
		mCanInfo.CYCLE_INDICATOR = (int) ((msg[3] >> 5) & 0x01);
		mCanInfo.LARGE_LANTERN_INDICATOR = (int) ((msg[3] >> 4) & 0x01);
		mCanInfo.SMALL_LANTERN_INDICATOR = (int) ((msg[3] >> 3) & 0x01);
		mCanInfo.DAUL_LAMP_INDICATOR = (int) ((msg[3] >> 2) & 0x01);
		mCanInfo.MAX_FRONT_LAMP_INDICATOR = (int) ((msg[3] >> 1) & 0x01);
		mCanInfo.REAR_LAMP_INDICATOR = (int) ((msg[3] >> 0) & 0x01);

		mCanInfo.UPWARD_AIR_INDICATOR = (int) ((msg[4] >> 7) & 0x01);
		mCanInfo.PARALLEL_AIR_INDICATOR = (int) ((msg[4] >> 6) & 0x01);
		mCanInfo.DOWNWARD_AIR_INDICATOR = (int) ((msg[4] >> 5) & 0x01);
		mCanInfo.AIRCON_SHOW_REQUEST = (int) ((msg[4] >> 4) & 0x01);
		mCanInfo.AIR_RATE = (int) (msg[4] & 0x0f);

		int temp = (int) (msg[5] & 0xff);
		mCanInfo.DRIVING_POSITON_TEMP = temp == 0 ? 0 : temp == 31 ? 255
				: (18 + (temp - 1) * 0.5f);

		temp = (int) (msg[6] & 0xff);
		mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = temp == 0 ? 0 : temp == 31 ? 255
				: (18 + (temp - 1) * 0.5f);

		mCanInfo.LEFT_SEAT_TEMP = (int) ((msg[8] >> 4) & 0x03);
		mCanInfo.RIGTHT_SEAT_TEMP = (int) ((msg[8] >> 0) & 0x03);

	}


	void analyzeSteeringButtonData(byte[] msg) {
		// TODO Auto-generated method stub
		if (msg.length > 6) {
			return;
		}

		mCanInfo.STEERING_BUTTON_MODE = (int) (msg[3] & 0xFF);
		if (mCanInfo.STEERING_BUTTON_MODE == 3) {
			mCanInfo.STEERING_BUTTON_MODE = 4;
		} else if (mCanInfo.STEERING_BUTTON_MODE == 4) {
			mCanInfo.STEERING_BUTTON_MODE = 3;
		}
		mCanInfo.STEERING_BUTTON_STATUS = (int) (msg[4] & 0xFF);
	}


	void analyzeCarSpeedData(byte[] msg) {
		// TODO Auto-generated method stub
		if (msg.length > 6) {
			return;
		}
		mCanInfo.CAR_SPEED_DATA = (((int) msg[3] & 0xFF) << 8 | ((int) msg[4] & 0xFF)) / 16;
	}

	// 背光
	void analyzeBackLightData(byte[] msg) {
		// TODO Auto-generated method stub
		if (msg.length > 6) {
			return;
		}
		mCanInfo.BACK_LIGHT_DATA = (int) (msg[3] & 0xFF);
	}



}
