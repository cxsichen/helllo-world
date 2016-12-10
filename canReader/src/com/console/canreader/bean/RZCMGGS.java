package com.console.canreader.bean;

import java.io.UnsupportedEncodingException;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.Trace;

import android.util.Log;

public class RZCMGGS extends AnalyzeUtils {
	// 数据类型
	public static final int comID = 1;
	// Head Code
	public static int HEAD_CODE = 0x2e;
	// DataType
	// 方向盘按键
	public static final int STEERING_BUTTON_DATA = 0x20;
	// 空调信息
	public static final int AIR_CONDITIONER_DATA = 0x21;
	// 后雷达信息
	public static final int BACK_RADER_DATA = 0x22;
	// 车身信息
	public static final int CAR_INFO_DATA = 0x24;
	// 环境温度信息
	public static final int AMBIENT_TEMP_INFO = 0x27;
	// 方向盘转角
	public static final int STEERING_TURN_DATA = 0x29;
	// 版本信息
	public static final int  VERSION_DATA = 0x7F;
	// 车身信息
	public static final int CAR_INFO_DATA_1 = 0x40;

	// 背光
	public static int BACK_LIGHT_DATA = 0x14;
	// 车速
	public static int CAR_SPEED_DATA = 0x16;

	// 前雷达信息
	public static int FRONT_RADER_DATA = 0x23;
	
	
	// 泊车辅助状态
	public static int PARK_ASSIT_DATA = 0x25;
	
	// 功放状态
	public static int POWER_AMPLIFIER_DATA = 0x38;
	
	// 车身警告信息
	public static int CAR_INFO_WARNING = 0x65;
	// 方向盘命令
	public static int STEERING_ORDER_DATA = 0x2F;
	// 车身时间信息
	public static int CAR_TIME_INFO = 0x50;
	

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
			case AIR_CONDITIONER_DATA:
				mCanInfo.CHANGE_STATUS = 3;
				analyzeAirConditionData(msg);
				break;
			case BACK_RADER_DATA:
				mCanInfo.CHANGE_STATUS = 4;
				analyzeBackRaderData(msg);
				break;
			case CAR_INFO_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData(msg);
				break;
			case CAR_INFO_DATA_1:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_1(msg);
				break;
			case AMBIENT_TEMP_INFO:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeOutSideTemputerature(msg);
				break;
			case STEERING_TURN_DATA:
				mCanInfo.CHANGE_STATUS = 8;
				analyzeSteeringTurnData(msg);
				break;
			case VERSION_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeVersionData(msg);
				break;
			default:
				break;
			}

			if (false) {
				if ((int) (msg[comID] & 0xFF) == BACK_LIGHT_DATA) {
					mCanInfo.CHANGE_STATUS = 0;
					analyzeBackLightData(msg);
				} else if ((int) (msg[comID] & 0xFF) == CAR_SPEED_DATA) {
					mCanInfo.CHANGE_STATUS = 1;
					analyzeCarSpeedData(msg);
				} else if ((int) (msg[comID] & 0xFF) == STEERING_BUTTON_DATA) {

				} else if ((int) (msg[comID] & 0xFF) == AIR_CONDITIONER_DATA) {

				} else if ((int) (msg[comID] & 0xFF) == BACK_RADER_DATA) {

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
					
				}
				// else if ((int) (msg[i] & 0xFF) == POWER_AMPLIFIER_DATA) {
				// mCanInfo.CHANGE_STATUS = 9;
				// analyzePowerAmplifierData(msg);
				// }
				else if ((int) (msg[comID] & 0xFF) == CAR_INFO_DATA) {
					
				} else if ((int) (msg[comID] & 0xFF) == AMBIENT_TEMP_INFO) {
					
				} else {
					mCanInfo.CHANGE_STATUS = 8888;
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	private void analyzeOutSideTemputerature(byte[] msg) {
		if ((msg[3] >> 7) == 0) {
			mCanInfo.OUTSIDE_TEMPERATURE = (int) (msg[3] & 0xff);
		} else {
			mCanInfo.OUTSIDE_TEMPERATURE = (int) (msg[3] & 0x7f) * (-1);
		}
	}

	void analyzeCarInfoData_1(byte[] msg) {
		// TODO Auto-generated method stub
		mCanInfo.AUTO_LOCK_SETTING = (int) ((msg[3] >> 7) & 0x01);
		mCanInfo.AUTO_OPEN_LOCK_Z = (int) ((msg[3] >> 6) & 0x01);
		mCanInfo.AUTO_OPEN_LOCK = (int) ((msg[3] >> 5) & 0x01);
		mCanInfo.AUTO_OPEN_LOCK_S = (int) ((msg[3] >> 4) & 0x01);
		
		mCanInfo.LIGHT_COMING_HOME_BACKUP = (int) ((msg[4] >> 7) & 0x01);
		mCanInfo.LIGHT_COMING_HOME_DIPPED = (int) ((msg[4] >> 6) & 0x01);
		mCanInfo.LIGHT_COMING_HOME_REARFOG = (int) ((msg[4] >> 5) & 0x01);
		mCanInfo.LIGHT_COMING_HOME_TIME = (int) ((msg[4] >> 0) & 0x0F);
		
		mCanInfo.LIGHT_SEEK_CAR_BACKUP = (int) ((msg[5] >> 7) & 0x01);
		mCanInfo.LIGHT_SEEK_CAR_DIPPED = (int) ((msg[5] >> 6) & 0x01);
		mCanInfo.LIGHT_SEEK_CAR_REARFOG = (int) ((msg[5] >> 5) & 0x01);
		mCanInfo.LIGHT_SEEK_CAR_TIME = (int) ((msg[5] >> 0) & 0x0F);
	}

	void analyzeCarInfoData(byte[] msg) {
		// TODO Auto-generated method stub
		mCanInfo.RIGHT_FORONTDOOR_STATUS = (int) ((msg[3] >> 7) & 0x01);
		mCanInfo.LEFT_FORONTDOOR_STATUS = (int) ((msg[3] >> 6) & 0x01);
		mCanInfo.RIGHT_BACKDOOR_STATUS = (int) ((msg[3] >> 5) & 0x01);
		mCanInfo.LEFT_BACKDOOR_STATUS = (int) ((msg[3] >> 4) & 0x01);
		mCanInfo.TRUNK_STATUS = (int) ((msg[3] >> 3) & 0x01);

		mCanInfo.HANDBRAKE_STATUS = (int) ((msg[4] >> 1) & 0x01);
		mCanInfo.SAFETY_BELT_STATUS = (int) ((msg[4] >> 4) & 0x01);
	}
	
	void analyzeVersionData(byte[] msg) {
		// TODO Auto-generated method stub
		int len = ((int) msg[2] & 0xFF);
		byte[] acscii = new byte[len];
		for (int i = 0; i < len; i++) {
			acscii[i] = msg[i + 3];

		}
		try {
			mCanInfo.VERSION = new String(acscii, "GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		int turnTemp = ((int) msg[3] & 0xFF) << 8 | ((int) msg[4] & 0xFF);
		int turnTemp2 = -((32701 - turnTemp) * 540 / 0x1e97);
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
		double a = (msg[3] & 0xff) / 2.0f;
		mCanInfo.BACK_LEFT_DISTANCE = (int) Math.ceil(a);
		a = (msg[4] & 0xff) / 2.0f;
		mCanInfo.BACK_MIDDLE_LEFT_DISTANCE = (int) Math.ceil(a);
		a = (msg[5] & 0xff) / 2.0f;
		mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE = (int) Math.ceil(a);
		a = (msg[6] & 0xff) / 2.0f;
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
		int AirTemp = (msg[4] >> 4) & 0x0f;
		if (AirTemp == 1 || AirTemp == 2 || AirTemp == 3) {
			mCanInfo.DOWNWARD_AIR_INDICATOR = 1;
		} else {
			mCanInfo.DOWNWARD_AIR_INDICATOR = 0;
		}
		if (AirTemp == 0 || AirTemp == 1) {
			mCanInfo.PARALLEL_AIR_INDICATOR = 1;
		} else {
			mCanInfo.PARALLEL_AIR_INDICATOR = 0;
		}
		if (AirTemp == 3 || AirTemp == 4) {
			mCanInfo.UPWARD_AIR_INDICATOR = 1;
		} else {
			mCanInfo.UPWARD_AIR_INDICATOR = 0;
		}
		if (AirTemp == 0x0f) {
			mCanInfo.UPWARD_AIR_INDICATOR = 1;
			mCanInfo.PARALLEL_AIR_INDICATOR = 1;
			mCanInfo.DOWNWARD_AIR_INDICATOR = 1;
		}
		if ((msg[4] & 0x0f) == 0x0f) {
			mCanInfo.AIR_RATE = -1;
		} else {
			mCanInfo.AIR_RATE = (int) (msg[4] & 0x0f);
		}

		int temp = (int) (msg[5] & 0xff);
		mCanInfo.DRIVING_POSITON_TEMP = temp == 0 ? 0 : temp == 15 ? 255
				: (18f + (temp - 2));
		mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = mCanInfo.DRIVING_POSITON_TEMP;

		mCanInfo.MAX_FRONT_LAMP_INDICATOR = (int) ((msg[6] >> 7) & 0x01);
		mCanInfo.REAR_LAMP_INDICATOR = (int) ((msg[6] >> 6) & 0x01);
		// mCanInfo.AQS_CIRCLE = (int) ((msg[7] >> 5) & 0x01);

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
		case 0x06:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.ANSWER;
			break;
		case 0x07:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
			break;
		case 0x08:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.PHONE_APP;
			break;
		case 0x09:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.PHONE;
			break;
		case 0x10:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
			break;
		case 0x11:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
			break;
		case 0x12:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.BACK;
			break;
		case 0x13:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.HOME;
			break;
		case 0x32:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MAP;
			break;
		case 0x80:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.POWER;
			break;
		case 0x16:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MUTE;
			break;
		case 0x81:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.KNOBVOLUMEUP;
			mCanInfo.CAR_VOLUME_KNOB = (int) (msg[4] & 0xFF);
			break;
		case 0x82:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.KNOBVOLUMEDOWN;
			mCanInfo.CAR_VOLUME_KNOB = (int) (msg[4] & 0xFF);
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
