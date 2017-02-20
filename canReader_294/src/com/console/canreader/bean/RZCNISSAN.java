package com.console.canreader.bean;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;

import android.util.Log;

public class RZCNISSAN extends AnalyzeUtils {

	// 数据类型
	public static final int comID = 1;
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
	public static final int STEERING_TURN_DATA = 0x29;
	// 功放状态
	public static final int POWER_AMPLIFIER_DATA = 0x27;
	// 版本信息
	public static final int VERSION_DATA = 0x30;
	// 车身信息
	public static final int CAR_INFO_DATA = 0x28;
	// 车身时间信息
	public static final int CAR_TIME_INFO = 0x28;
	// 环境温度信息
	public static final int AMBIENT_TEMP_INFO = 0x51;

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
		if ((int) ((msg[3] >> 7) & 0x01) == 0) {
			mCanInfo.STERRING_WHELL_STATUS = (((int) msg[3] & 0x7F) * 256 + ((int) msg[4] & 0xFF)) / 10;
		} else {
			mCanInfo.STERRING_WHELL_STATUS = -(((int) msg[3] & 0x7F) * 256 + ((int) msg[4] & 0xFF)) / 10;
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

		mCanInfo.AQS_CIRCLE = (int) ((msg[7] >> 7) & 0x01);
		mCanInfo.LEFT_SEAT_TEMP = (int) ((msg[7] >> 4) & 0x03);
		mCanInfo.RIGTHT_SEAT_TEMP = (int) ((msg[7] >> 0) & 0x03);

	}

	/*
	 * 方向盘按键 STEERING_BUTTON_MODE 0：无按键或释放 1：vol+ 2：vol- 3：menuup 4：menu down 5：
	 * PHONE 6：mute 7：SRC 8：SPEECH/MIC 9:answer phone 10:hangup phone
	 */

	void analyzeSteeringButtonData(byte[] msg) {
		// TODO Auto-generated method stub
		switch ((int) (msg[3] & 0xFF)) {
		case 0x01:
			mCanInfo.STEERING_BUTTON_MODE = 1;
			break;
		case 0x02:
			mCanInfo.STEERING_BUTTON_MODE = 2;
			break;
		case 0x03:
			mCanInfo.STEERING_BUTTON_MODE = 3;
			break;
		case 0x04:
			mCanInfo.STEERING_BUTTON_MODE = 4;
			break;
		case 0x07:
			mCanInfo.STEERING_BUTTON_MODE = 7;
			break;
		case 0x09:
			mCanInfo.STEERING_BUTTON_MODE = 9;
			break;
		case 0x0A:
			mCanInfo.STEERING_BUTTON_MODE = 10;
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
