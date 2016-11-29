package com.console.canreader.bean;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;

import android.util.Log;

public class RZCFordEDGE extends AnalyzeUtils {
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
	public static int STEERING_BUTTON_DATA = 0x16;
	// 空调信息
	public static int AIR_CONDITIONER_DATA = 0x29;
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
	// 车身信息
	public static int CAR_INFO_DATA = 0x24;
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
				// analyzePowerAmplifierData(msg);
			} else if ((int) (msg[comID] & 0xFF) == CAR_INFO_DATA) {
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData(msg);
			} else {
				mCanInfo.CHANGE_STATUS = 8888;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	void analyzeCarInfoData(byte[] msg) {
		// TODO Auto-generated method stub
		switch ((msg[3] & 0xff)) {
		case 1:
			mCanInfo.RIGHT_FORONTDOOR_STATUS = (int) ((msg[3] >> 7) & 0x01);
			mCanInfo.LEFT_FORONTDOOR_STATUS = (int) ((msg[3] >> 6) & 0x01);
			mCanInfo.RIGHT_BACKDOOR_STATUS = (int) ((msg[3] >> 5) & 0x01);
			mCanInfo.LEFT_BACKDOOR_STATUS = (int) ((msg[3] >> 4) & 0x01);
			mCanInfo.TRUNK_STATUS = (int) ((msg[3] >> 3) & 0x01);
			mCanInfo.HOOD_STATUS = (int) ((msg[3] >> 2) & 0x01);

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
		if (temp > 22768) {
			mCanInfo.STERRING_WHELL_STATUS = -1 * (((temp ^ 0xff) + 1) - 32768)
					* 54 / 1000;
		} else {
			mCanInfo.STERRING_WHELL_STATUS = temp * 54 / 1000;
		}

	}

	void analyzeParkAssitData(byte[] msg) {
		// TODO Auto-generated method stub
		// mCanInfo.PARKING_ASSIT_STATUS = (int) ((msg[3] >> 1) & 0x01);
		if ((int) ((msg[3] >> 2) & 0x01) == 1
				&& (int) ((msg[3] >> 3) & 0x01) == 1) {
			mCanInfo.RADAR_ALARM_STATUS = 1;
		} else {
			mCanInfo.RADAR_ALARM_STATUS = 0;
		}
	}

	void analyzeBasicInfoData(byte[] msg) {
		// TODO Auto-generated method stub
		// mCanInfo.LIGHT_MSG = (int) ((msg[3] >> 2) & 0x01);
		// mCanInfo.STOPING_STATUS = (int) ((msg[3] >> 1) & 0x01);
		// mCanInfo.REVERSE_GEAR_STATUS = (int) ((msg[3] >> 0) & 0x01);
	}

	void analyzeFrontRaderData(byte[] msg) {
		// TODO Auto-generated method stub
		double a = (msg[3] & 0xff) / 8.0f;
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

	void analyzeBackRaderData(byte[] msg) {
		// TODO Auto-generated method stub
		double a = (msg[3] & 0xff) / 8.0f;
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

	void analyzeAirConditionData(byte[] msg) {
		// TODO Auto-generated method stub
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

		// int temp = (int) (msg[5] & 0xff);
		// mCanInfo.DRIVING_POSITON_TEMP = temp == 0 ? 0 : temp == 31 ? 255
		// : (18 + (temp - 1) * 0.5f);
		//
		// temp = (int) (msg[6] & 0xff);
		// mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = temp == 0 ? 0 : temp == 31 ?
		// 255
		// : (18 + (temp - 1) * 0.5f);

		// mCanInfo.AQS_CIRCLE = (int) ((msg[7] >> 7) & 0x01);
		// mCanInfo.LEFT_SEAT_TEMP = (int) ((msg[7] >> 4) & 0x03);
		// mCanInfo.RIGTHT_SEAT_TEMP = (int) ((msg[7] >> 0) & 0x03);
		if ((msg[7] & 0xff) >= 0xd8) {
			mCanInfo.OUTSIDE_TEMPERATURE = -1 * (((msg[8] & 0xff) ^ 0xff) + 1);
		} else {
			mCanInfo.OUTSIDE_TEMPERATURE = (float) (msg[8] & 0xff);
		}
		int temp = (msg[8] & 0xff);
		if (temp <= 0x3c) {// 摄氏度
			if ((temp >= 0x1f) && (temp <= 0x3b)) {
				mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = (temp - 0x1f) * 0.5f + 15.5f;
			} else if (temp < 0x1f) {
				mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = 0;
			} else {
				mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = 255;
			}
		} else {
			if ((temp >= 0x78) && (temp <= 0xAA)) {
				mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = (temp - 0x1f) + 60;
			} else if (temp < 0x78) {
				mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = 0;
			} else {
				mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = 255;
			}
		}
		switch (msg[9] & 0x03) {
		case 0:
			mCanInfo.LEFT_SEAT_TEMP = 0;
			break;
		case 1:
			mCanInfo.LEFT_SEAT_TEMP = 1;
			break;
		case 2:
			mCanInfo.LEFT_SEAT_TEMP = 2;
			break;

		default:
			break;
		}
		switch ((msg[9] >> 2) & 0x03) {
		case 0:
			mCanInfo.RIGTHT_SEAT_TEMP = 0;
			break;
		case 1:
			mCanInfo.RIGTHT_SEAT_TEMP = 1;
			break;
		case 2:
			mCanInfo.RIGTHT_SEAT_TEMP = 2;
			break;

		default:
			break;
		}
	}

	void analyzeSteeringButtonData(byte[] msg) {
		// TODO Auto-generated method stub
		mCanInfo.STEERING_BUTTON_MODE = (int) (msg[3] & 0xFF);
		mCanInfo.STEERING_BUTTON_STATUS = (int) (msg[4] & 0xFF);
	}

	void analyzeCarSpeedData(byte[] msg) {
		// TODO Auto-generated method stub
		// mCanInfo.CAR_SPEED_DATA = (((int) msg[3] & 0xFF) << 8 | ((int) msg[4]
		// & 0xFF)) / 16;
	}

	void analyzeBackLightData(byte[] msg) {
		// TODO Auto-generated method stub
		mCanInfo.BACK_LIGHT_DATA = (int) (msg[3] & 0xFF);
	}

}
