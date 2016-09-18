package com.console.canreader.bean;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;
import com.console.canreader.utils.Contacts;

import android.util.Log;

public class RZCBESTURNx80 extends AnalyzeUtils {

	// Head Code
	public static final int HEAD_CODE = 0x2e;
	// DataType
	// 方向盘按键
	public static final int STEERING_BUTTON_DATA = 0x12;
	// 空调信息
	public static final int AIR_CONDITIONER_DATA = 0x10;
	// 后雷达信息
	public static final int BACK_RADER_DATA = 0x32;
	// 方向盘转角
	public static final int STEERING_TURN_DATA = 0x30;
	// 车身信息
	public static final int CAR_INFO_DATA = 0x24;

	public RZCBESTURNx80(byte[] msg, int i) {
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
			case STEERING_TURN_DATA:
				mCanInfo.CHANGE_STATUS = 8;
				analyzeSteeringTurnData(msg);
				break;
			case STEERING_BUTTON_DATA:
				mCanInfo.CHANGE_STATUS = 2;
				analyzeSteeringButtonData(msg);
				break;
			case BACK_RADER_DATA:
				mCanInfo.CHANGE_STATUS = 4;
				analyzeBackRaderData(msg);
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

	static String steerSave = "";
	void analyzeSteeringTurnData(byte[] msg) {
		// TODO Auto-generated method stub
		if (steerSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			steerSave = BytesUtil.bytesToHexString(msg);
		}
		
		int temp = (int) ((msg[3] >> 7) & 0x01);
		if (temp == 0) { // 左转 正数
			mCanInfo.STERRING_WHELL_STATUS = (((int) msg[3] & 0x7F) << 8 | ((int) msg[4] & 0xFF)) / 10;
		} else { // 右转 负数
			mCanInfo.STERRING_WHELL_STATUS = -(((int) msg[3] & 0x7F) << 8 | ((int) msg[4] & 0xFF)) / 10;
		}
	}

	static String radarSave = "";
	void analyzeBackRaderData(byte[] msg) {
		// TODO Auto-generated method stub
		
		if (radarSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			radarSave = BytesUtil.bytesToHexString(msg);
		}
		
		if (((int) (msg[3] & 0xff)) == 0) {
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
		} else {

			int N = (int) 0xff;

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

	}

	static String airConSave = "";
	void analyzeAirConditionData(byte[] msg) {
		// TODO Auto-generated method stub
		if (airConSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			airConSave = BytesUtil.bytesToHexString(msg);
		}

		int temp = (int) (msg[3] & 0xff);
		int temp1 = (int) ((msg[6] >> 4) & 0x0f);
		mCanInfo.DRIVING_POSITON_TEMP = temp + temp1 / 10f;
		temp = (int) (msg[8] & 0xff);
		temp1 = (int) ((msg[6] >> 0) & 0x0f);
		mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = temp + temp1 / 10f;

		mCanInfo.CYCLE_INDICATOR = (int) ((msg[4] >> 7) & 0x01) == 0 ? 1 : 0;
		mCanInfo.MAX_FRONT_LAMP_INDICATOR = (int) ((msg[4] >> 6) & 0x01);
		mCanInfo.REAR_LAMP_INDICATOR = (int) ((msg[4] >> 5) & 0x01);
		mCanInfo.DOWNWARD_AIR_INDICATOR = (int) ((msg[4] >> 4) & 0x01);
		mCanInfo.PARALLEL_AIR_INDICATOR = (int) ((msg[4] >> 3) & 0x01);
		mCanInfo.LARGE_LANTERN_INDICATOR = (int) ((msg[4] >> 2) & 0x01);
		mCanInfo.AC_INDICATOR_STATUS = (int) ((msg[4] >> 0) & 0x01);

		mCanInfo.AIR_RATE = (int) (msg[5] & 0x0f);
	}

	/*
	 * 方向盘按键 STEERING_BUTTON_MODE 0：无按键或释放 1：vol+ 2：vol- 3：menuup 4：menu down 5：
	 * PHONE 6：mute 7：SRC 8：SPEECH/MIC 9:answer phone 10:hangup phone
	 */
	static String buttonSave = "";
	void analyzeSteeringButtonData(byte[] msg) {
		// TODO Auto-generated method stub
		if (buttonSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			buttonSave = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.STEERING_BUTTON_STATUS = 1;

		switch ((int) (msg[3] & 0xFF)) {
		case 0x14:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLUP;
			break;
		case 0x15:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLDOW;
			break;
		case 0x13:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUDOWN;
			break;
		case 0x12:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUUP;
			break;
		case 0x11:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
			break;
		case 0x30:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.ANSWER;
			break;
		case 0x31:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.HANGUP;
			break;
		case 0x16:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MUTE;
			break;
		default:
			mCanInfo.STEERING_BUTTON_MODE = 0;
			mCanInfo.STEERING_BUTTON_STATUS = 0;
			break;
		}

	}

}
