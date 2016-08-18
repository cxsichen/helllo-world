package com.console.canreader.bean;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;

import android.util.Log;

public class RZCVolkswagenGolf extends AnalyzeUtils {

	// DataType
	// 方向盘按键
	public final static int STEERING_BUTTON_DATA = 0x20;
	public final static int STEERING_BUTTON_DATA_1 = 0x2F;
	// 空调信息
	public final static int AIR_CONDITIONER_DATA = 0x21;
	// 后雷达信息
	public final static int BACK_RADER_DATA = 0x22;
	// 前雷达信息
	public final static int FRONT_RADER_DATA = 0x23;
	// 车身信息
	public final static int CAR_INFO_DATA = 0x24;
	public final static int CAR_INFO_DATA_1 = 0x16;
	public final static int CAR_INFO_DATA_2 = 0x27;
	public final static int CAR_INFO_DATA_3 = 0x50;
	// 方向盘转角
	public final static int STEERING_TURN_DATA = 0x29;

	public RZCVolkswagenGolf(byte[] msg, int i) {
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
			case CAR_INFO_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData(msg);
				break;
			case CAR_INFO_DATA_1:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_1(msg);
				break;
			case CAR_INFO_DATA_2:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_2(msg);
				break;
			case CAR_INFO_DATA_3:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_3(msg);
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

	void analyzeCarInfoData_2(byte[] msg) {
		mCanInfo.OUTSIDE_TEMPERATURE = ((int) (msg[5] << 8 | ((int) msg[4] & 0xFF))) / 10;
	}
	
	void analyzeCarInfoData_3(byte[] msg) {
		if(((int) msg[3] & 0xFF)==0x20){
			mCanInfo.DRIVING_DISTANCE = (((int) (msg[5] & 0xFF))+(((int) (msg[6] & 0xFF))<<8)+(((int) (msg[7] & 0xFF))<<16)+
					(((int) (msg[8] & 0xFF))<<24))/10;
		}		
	}

	void analyzeCarInfoData_1(byte[] msg) {
		mCanInfo.DRIVING_SPEED = (((int) msg[4] & 0xFF) << 8 | ((int) msg[3] & 0xFF)) / 16;
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
		mCanInfo.SAFETY_BELT_STATUS = -1; // 安全带状态 -1表示无此功能 0正常 1报警
		mCanInfo.DISINFECTON_STATUS = -1; // 清洁液状态 -1表示无此功能
		mCanInfo.REMAIN_FUEL = -1; // 剩余油量 -1表示无此功能
		mCanInfo.BATTERY_VOLTAGE = -1; // 电池电量 -1表示无此功能
		/*
		 * case 1:
		 * 
		 * break; case 2: mCanInfo.ENGINE_SPEED = (int) (msg[4] & 0xFF) * 256 +
		 * (int) (msg[5] & 0xFF); mCanInfo.DRIVING_SPEED = ((int) (msg[6] &
		 * 0xFF) * 256 + (int) (msg[7] & 0xFF)) * 0.01f;
		 * mCanInfo.BATTERY_VOLTAGE = ((int) (msg[8] & 0xFF) * 256 + (int)
		 * (msg[9] & 0xFF)) * 0.01f;
		 * 
		 * int temp = (int) (msg[10] & 0xFF) * 256 + (int) (msg[11] & 0xFF); if
		 * (temp < 32767) { mCanInfo.OUTSIDE_TEMPERATURE = temp * 0.1f; } else {
		 * mCanInfo.OUTSIDE_TEMPERATURE = (temp - 65536) * 0.1f; }
		 * mCanInfo.DRIVING_DISTANCE = (int) (msg[12] & 0xFF) * 65536 + (int)
		 * (msg[13] & 0xFF) * 256 + (int) (msg[14] & 0xFF); mCanInfo.REMAIN_FUEL
		 * = (int) (msg[15] & 0xFF); break; case 3: mCanInfo.FUEL_WARING_SIGN =
		 * (int) ((msg[4] >> 7) & 0x01); mCanInfo.BATTERY_WARING_SIGN = (int)
		 * ((msg[4] >> 6) & 0x01); default: break; }
		 */

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
		mCanInfo.FRONT_LEFT_DISTANCE = (((int) (msg[3] & 0xff)) == 0 ? 0
				: (((((float) (msg[3] & 0xff)) / (0x3C / 4f)) + 1) > 4 ? 4
						: (int) ((((float) (msg[3] & 0xff)) / (0x3C / 4f)) + 1)));
		mCanInfo.FRONT_MIDDLE_LEFT_DISTANCE = (((int) (msg[4] & 0xff)) == 0 ? 0
				: (((((float) (msg[4] & 0xff)) / (0x78 / 4f)) + 1) > 4 ? 4
						: (int) ((((float) (msg[4] & 0xff)) / (0x78 / 4f)) + 1)));
		mCanInfo.FRONT_MIDDLE_RIGHT_DISTANCE = (((int) (msg[5] & 0xff)) == 0 ? 0
				: (((((float) (msg[5] & 0xff)) / (0x78 / 4f)) + 1) > 4 ? 4
						: (int) ((((float) (msg[5] & 0xff)) / (0x78 / 4f)) + 1)));
		mCanInfo.FRONT_RIGHT_DISTANCE = (((int) (msg[6] & 0xff)) == 0 ? 0
				: (((((float) (msg[6] & 0xff)) / (0x3C / 4f)) + 1) > 4 ? 4
						: (int) ((((float) (msg[6] & 0xff)) / (0x3C / 4f)) + 1)));
	}

	void analyzeBackRaderData(byte[] msg) {
		// TODO Auto-generated method stub
		mCanInfo.BACK_LEFT_DISTANCE = (((int) (msg[3] & 0xff)) == 0 ? 0
				: (((((float) (msg[3] & 0xff)) / (0x3C / 4f)) + 1) > 4 ? 4
						: (int) ((((float) (msg[3] & 0xff)) / (0x3C / 4f)) + 1)));
		mCanInfo.BACK_MIDDLE_LEFT_DISTANCE = (((int) (msg[4] & 0xff)) == 0 ? 0
				: (((((float) (msg[4] & 0xff)) / (0xA5 / 4f)) + 1) > 4 ? 4
						: (int) ((((float) (msg[4] & 0xff)) / (0xA5 / 4f)) + 1)));
		mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE = (((int) (msg[5] & 0xff)) == 0 ? 0
				: (((((float) (msg[5] & 0xff)) / (0xA5 / 4f)) + 1) > 4 ? 4
						: (int) ((((float) (msg[5] & 0xff)) / (0xA5 / 4f)) + 1)));
		mCanInfo.BACK_RIGHT_DISTANCE = (((int) (msg[6] & 0xff)) == 0 ? 0
				: (((((float) (msg[6] & 0xff)) / (0x3C / 4f)) + 1) > 4 ? 4
						: (int) ((((float) (msg[6] & 0xff)) / (0x3C / 4f)) + 1)));

	}

	void analyzeAirConditionData(byte[] msg) {
		// TODO Auto-generated method stub
		mCanInfo.AIR_CONDITIONER_STATUS = (int) ((msg[3] >> 7) & 0x01);
		mCanInfo.AC_INDICATOR_STATUS = (int) ((msg[3] >> 6) & 0x01);
		mCanInfo.CYCLE_INDICATOR = (int) ((msg[3] >> 5) & 0x01);
		mCanInfo.LARGE_LANTERN_INDICATOR = (int) ((msg[3] >> 4) & 0x01);
		mCanInfo.SMALL_LANTERN_INDICATOR = (int) ((msg[3] >> 3) & 0x01);
		mCanInfo.DAUL_LAMP_INDICATOR = (int) ((msg[3] >> 2) & 0x01);
		mCanInfo.MAX_FRONT_LAMP_INDICATOR = (int) ((msg[7] >> 7) & 0x01);
		mCanInfo.REAR_LAMP_INDICATOR = (int) ((msg[7] >> 6) & 0x01);

		mCanInfo.UPWARD_AIR_INDICATOR = (int) ((msg[4] >> 7) & 0x01);
		mCanInfo.PARALLEL_AIR_INDICATOR = (int) ((msg[4] >> 6) & 0x01);
		mCanInfo.DOWNWARD_AIR_INDICATOR = (int) ((msg[4] >> 5) & 0x01);
		mCanInfo.AIRCON_SHOW_REQUEST = (int) ((msg[4] >> 4) & 0x01);
		mCanInfo.AIR_RATE = (int) (msg[4] & 0x0f);

		if (((int) (msg[7] & 0x01)) == 0) { // 摄氏度
			int temp = (int) (msg[5] & 0xff);
			mCanInfo.DRIVING_POSITON_TEMP = temp == 0 ? 0 : temp == 0x1f ? 255
					: (16 + (temp - 1) * 0.5f);

			temp = (int) (msg[6] & 0xff);
			mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = temp == 0 ? 0
					: temp == 0x1f ? 255 : (16 + (temp - 1) * 0.5f);
		} else { // 华氏
			int temp = (int) (msg[5] & 0xff);
			mCanInfo.DRIVING_POSITON_TEMP = temp == 0 ? 0 : temp == 0x1f ? 255
					: (60 + (temp - 1));

			temp = (int) (msg[6] & 0xff);
			mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = temp == 0 ? 0
					: temp == 0x1f ? 255 : (60 + (temp - 1));
		}

		mCanInfo.LEFT_SEAT_TEMP = (int) ((msg[8] >> 4) & 0x03);
		mCanInfo.RIGTHT_SEAT_TEMP = (int) ((msg[8] >> 0) & 0x03);

	}

	/*
	 * 方向盘按键 STEERING_BUTTON_MODE 0：无按键或释放 1：vol+ 2：vol- 3：menuup 4：menu down 5：
	 * PHONE 6：mute 7：SRC 8：SPEECH/MIC 9:answer phone 10:hangup phone
	 */
	void analyzeSteeringButtonData(byte[] msg) {
		// TODO Auto-generated method stub
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
		case 0x07:
		case 0x08:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
			break;
		case 0x05:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.PHONE;
			break;
		case 0x06:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MUTE;
			break;
		default:
			mCanInfo.STEERING_BUTTON_MODE = 0;
			break;
		}

		mCanInfo.STEERING_BUTTON_STATUS = (int) (msg[4] & 0xFF);
	}

}
