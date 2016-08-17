package com.console.canreader.bean;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;

import android.util.Log;

public class RZCTrumpche extends AnalyzeUtils {

	// Head Code
	public static final int HEAD_CODE = 0x2e;
	// DataType
	// 方向盘按键
	public static final int STEERING_BUTTON_DATA = 0x12;
	// 空调信息
	public static final int AIR_CONDITIONER_DATA = 0x10;
	public static final int AIR_CONDITIONER_DATA_1 = 0x52;
	// 后雷达信息
	public static final int BACK_RADER_DATA = 0x32;
	// 前雷达信息
	public static final int FRONT_RADER_DATA = 0x33;
	// 方向盘转角
	public static final int STEERING_TURN_DATA = 0x31;
	// 车身信息
	public static final int CAR_INFO_DATA = 0x24;

	public RZCTrumpche(byte[] msg, int i) {
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
			case AIR_CONDITIONER_DATA_1:
				mCanInfo.CHANGE_STATUS = 3;
				analyzeAirConditionData_1(msg);
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
			case STEERING_TURN_DATA:
				mCanInfo.CHANGE_STATUS = 8;
				analyzeSteeringTurnData(msg);
				break;
			default:
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
		mCanInfo.STERRING_WHELL_STATUS = (0x1e00 - (((int) msg[3] & 0xFF) << 8 | ((int) msg[4] & 0xFF))) / 9;
	}

	void analyzeFrontRaderData(byte[] msg) {
		// TODO Auto-generated method stub
		int N = (int) (msg[4] & 0xff);

		mCanInfo.FRONT_LEFT_DISTANCE = (((int) (msg[5] & 0xff)) == N ? 0
				: (((((float) (msg[5] & 0xff)) / (N / 4f)) + 1) > 4 ? 4
						: (int) ((((float) (msg[5] & 0xff)) / (N / 4f)) + 1)));
		mCanInfo.FRONT_MIDDLE_LEFT_DISTANCE = (((int) (msg[6] & 0xff)) == N ? 0
				: (((((float) (msg[6] & 0xff)) / (N / 4f)) + 1) > 4 ? 4
						: (int) ((((float) (msg[6] & 0xff)) / (N / 4f)) + 1)));
		mCanInfo.FRONT_MIDDLE_RIGHT_DISTANCE = (((int) (msg[7] & 0xff)) == N ? 0
				: (((((float) (msg[7] & 0xff)) / (N / 4f)) + 1) > 4 ? 4
						: (int) ((((float) (msg[7] & 0xff)) / (N / 4f)) + 1)));
		mCanInfo.FRONT_RIGHT_DISTANCE = (((int) (msg[8] & 0xff)) == N ? 0
				: (((((float) (msg[8] & 0xff)) / (N / 4f)) + 1) > 4 ? 4
						: (int) ((((float) (msg[8] & 0xff)) / (N / 4f)) + 1)));
	}

	void analyzeBackRaderData(byte[] msg) {
		// TODO Auto-generated method stub

		int N = (int) (msg[4] & 0xff);
		
		mCanInfo.BACK_LEFT_DISTANCE = (((int) (msg[5] & 0xff)) == N ? 0
				: (((((float) (msg[5] & 0xff)) / (N / 4f)) + 1) > 4 ? 4
						: (int) ((((float) (msg[5] & 0xff)) / (N / 4f)) + 1)));
		mCanInfo.BACK_MIDDLE_LEFT_DISTANCE = (((int) (msg[6] & 0xff)) == N ? 0
				: (((((float) (msg[6] & 0xff)) / (N / 4f)) + 1) > 4 ? 4
						: (int) ((((float) (msg[6] & 0xff)) / (N / 4f)) + 1)));
		mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE = (((int) (msg[7] & 0xff)) == N ? 0
				: (((((float) (msg[7] & 0xff)) / (N / 4f)) + 1) > 4 ? 4
						: (int) ((((float) (msg[7] & 0xff)) / (N / 4f)) + 1)));
		mCanInfo.BACK_RIGHT_DISTANCE = (((int) (msg[8] & 0xff)) == N ? 0
				: (((((float) (msg[8] & 0xff)) / (N / 4f)) + 1) > 4 ? 4
						: (int) ((((float) (msg[8] & 0xff)) / (N / 4f)) + 1)));

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
		mCanInfo.CYCLE_INDICATOR = (int) ((msg[4] >> 7) & 0x01);
		mCanInfo.MAX_FRONT_LAMP_INDICATOR = (int) ((msg[4] >> 6) & 0x01);
		mCanInfo.REAR_LAMP_INDICATOR = (int) ((msg[4] >> 5) & 0x01);
		mCanInfo.DOWNWARD_AIR_INDICATOR = (int) ((msg[4] >> 4) & 0x01);
		mCanInfo.PARALLEL_AIR_INDICATOR = (int) ((msg[4] >> 3) & 0x01);
		mCanInfo.SMALL_LANTERN_INDICATOR = (int) ((msg[4] >> 2) & 0x01);
		mCanInfo.AC_INDICATOR_STATUS = (int) ((msg[4] >> 0) & 0x01);
		mCanInfo.AIR_RATE = (int) (msg[5] & 0x0f);
		if (mCanInfo.AIR_RATE == 0) {
			mCanInfo.AIR_CONDITIONER_STATUS = 0;
		} else {
			mCanInfo.AIR_CONDITIONER_STATUS = 1;
		}

		int temp = (int) (msg[6] & 0xff);
		mCanInfo.DRIVING_POSITON_TEMP = temp == 1 ? 0 : temp == 0x39 ? 255
				: (18.5f + ((temp - 3) / 2f) * 0.5f);

		temp = (int) (msg[7] & 0xff);
		mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = temp == 1 ? 0
				: temp == 0x39 ? 255 : (18.5f + ((temp - 3) / 2f) * 0.5f);

	}

	/*
	 * 方向盘按键 STEERING_BUTTON_MODE 0：无按键或释放 1：vol+ 2：vol- 3：menuup 4：menu down 5：
	 * PHONE 6：mute 7：SRC 8：SPEECH/MIC 9:answer phone 10:hangup phone
	 */

	void analyzeSteeringButtonData(byte[] msg) {
		// TODO Auto-generated method stub

		if ((int) (msg[2] & 0xFF) == 1) {
			mCanInfo.STEERING_BUTTON_STATUS = 1;
		} else if ((int) (msg[2] & 0xFF) == 2) {
			mCanInfo.STEERING_BUTTON_STATUS = (int) (msg[4] & 0xFF);
		}

		switch ((int) (msg[3] & 0xFF)) {
		case 0x00:
			mCanInfo.STEERING_BUTTON_STATUS = 0; // 按鍵釋放
			break;
		case 0x11:
			mCanInfo.STEERING_BUTTON_MODE = 8;
			break;
		case 0x12:
			mCanInfo.STEERING_BUTTON_MODE = 3;
			break;
		case 0x13:
			mCanInfo.STEERING_BUTTON_MODE = 4;
			break;
		case 0x14:
			mCanInfo.STEERING_BUTTON_MODE = 1;
			break;
		case 0x15:
			mCanInfo.STEERING_BUTTON_MODE = 2;
			break;
		case 0x16:
			mCanInfo.STEERING_BUTTON_MODE = 6;
			break;
		case 0x30:
			mCanInfo.STEERING_BUTTON_MODE = 9;
			break;
		case 0x31:
			mCanInfo.STEERING_BUTTON_MODE = 10;
			break;
		default:
			mCanInfo.STEERING_BUTTON_MODE = 0;
			break;
		}

	}

	void analyzeCarSpeedData(byte[] msg) {
		// TODO Auto-generated method stub
		mCanInfo.CAR_SPEED_DATA = (((int) msg[3] & 0xFF) << 8 | ((int) msg[4] & 0xFF)) / 16;
	}

}
