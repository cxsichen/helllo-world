package com.console.canreader.bean;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;

import android.util.Log;

public class SSDFFG extends AnalyzeUtils {
	// 数据类型
	public static final int comID = 3;
	// DataType
	// 车身信息
	public static final int CAR_INFO_DATA = 0x12;
	// 车身信息
	public static final int CAR_INFO_DATA_1 = 0x32;
	// 车身信息
	public static final int CAR_INFO_DATA_2 = 0x34;
	// 空调信息
	public static final int AIR_CONDITIONER_DATA = 0x31;
	// 车身基本信息
	public static final int CAR_BASIC_INFO_DATA = 0x11;
	// 雷达信息
	public static final int RADAR_DATA = 0x41;



	public CanInfo getCanInfo() {
		return mCanInfo;
	}

	@Override
	public void analyzeEach(byte[] msg) {
		// TODO Auto-generated method stub
		super.analyzeEach(msg);
		try {
			if (msg == null)
				return;
			switch ((int) (msg[comID] & 0xFF)) {
			case AIR_CONDITIONER_DATA:
				mCanInfo.CHANGE_STATUS = 3;
				analyzeAirConditionData(msg);
				break;
			case CAR_INFO_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData(msg);
				break;
			case CAR_BASIC_INFO_DATA:
				analyzeCarBasicInfoData(msg);
				break;
			case RADAR_DATA:
				mCanInfo.CHANGE_STATUS = 4;
				analyzeRadarData(msg);
				break;
			default:
				break;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	static String radarSave = "";
	int temps[] = { 0, 0, 0, 0 };

	void analyzeRadarData(byte[] msg) {
		if (radarSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			radarSave = BytesUtil.bytesToHexString(msg);
		}

		for (int i = 0; i < 4; i++) {
			if ((int) (msg[4 + i] & 0xff) == 0) {
				temps[i] = 0;
			} else {
				temps[i] = ((((int) (msg[4 + i] & 0xff) - 1) / 2) + 1);
			}
		}
		if (mCanInfo.BACK_LEFT_DISTANCE != temps[0]
				|| mCanInfo.BACK_MIDDLE_LEFT_DISTANCE != temps[1]
				|| mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE != temps[2]
				|| mCanInfo.BACK_RIGHT_DISTANCE != temps[3]) {
			mCanInfo.BACK_LEFT_DISTANCE = temps[0];
			mCanInfo.BACK_MIDDLE_LEFT_DISTANCE = temps[1];
			mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE = temps[2];
			mCanInfo.BACK_RIGHT_DISTANCE = temps[3];
		}

	}

	static String carBasicInfo = "";

	/*
	 * 方向盘按键 STEERING_BUTTON_MODE 0：无按键或释放 1：vol+ 2：vol- 3：menuup 4：menu down 5：
	 * PHONE 6：mute 7：SRC 8：SPEECH/MIC 9:answer phone 10:hangup phone
	 */
	int keyCode[] = { 0, 1, 2, 13, 14, 5, 3, 12, 23, -1, -1 };

	void analyzeCarBasicInfoData(byte[] msg) {
		if (carBasicInfo.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carBasicInfo = BytesUtil.bytesToHexString(msg);
		}
		int temp = (int) ((msg[4] >> 3) & 0x01);
		if (temp != mCanInfo.HANDBRAKE_STATUS) {
			mCanInfo.HANDBRAKE_STATUS = temp;
			mCanInfo.CHANGE_STATUS = 10;
		}

		temp = (int) (msg[6] & 0xFF);
		for (int i = 0; i < keyCode.length; i++) {
			if (temp == keyCode[i]) {
				temp = i;
				break;
			}
		}
		if (mCanInfo.STEERING_BUTTON_MODE != temp) {
			mCanInfo.STEERING_BUTTON_MODE = temp;
			mCanInfo.CHANGE_STATUS = 2;
		}

		temp = (int) (msg[7] & 0xFF);
		if (mCanInfo.STEERING_BUTTON_STATUS != temp) {
			mCanInfo.STEERING_BUTTON_STATUS = temp;
			mCanInfo.CHANGE_STATUS = 2;
		}
	}

	static String carInfoSave = "";

	void analyzeCarInfoData(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave = BytesUtil.bytesToHexString(msg);
		}

		mCanInfo.RIGHT_BACKDOOR_STATUS = (int) ((msg[6] >> 4) & 0x01);
		mCanInfo.LEFT_BACKDOOR_STATUS = (int) ((msg[6] >> 5) & 0x01);
		mCanInfo.RIGHT_FORONTDOOR_STATUS = (int) ((msg[6] >> 6) & 0x01);
		mCanInfo.LEFT_FORONTDOOR_STATUS = (int) ((msg[6] >> 7) & 0x01);

		mCanInfo.SAFETY_BELT_STATUS = (int) ((msg[6] >> 2) & 0x01) == 0 ? 1 : 0;

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

		mCanInfo.AIR_CONDITIONER_STATUS = (int) ((msg[4] >> 6) & 0x01);
		mCanInfo.AC_INDICATOR_STATUS = (int) ((msg[5] >> 6) & 0x01);
		mCanInfo.CYCLE_INDICATOR = (int) ((msg[5] >> 4) & 0x01);

		mCanInfo.MAX_FRONT_LAMP_INDICATOR = (int) ((msg[6] >> 4) & 0x01);

		int temp = (int) (msg[8] & 0xff);
		if (temp == 0x0C) {
			mCanInfo.UPWARD_AIR_INDICATOR = 1;
		} else {
			mCanInfo.UPWARD_AIR_INDICATOR = 0;
		}

		if (temp == 0x05 || temp == 0x06) {
			mCanInfo.PARALLEL_AIR_INDICATOR = 1;
		} else {
			mCanInfo.PARALLEL_AIR_INDICATOR = 0;
		}

		if (temp == 0x03 || temp == 0x05 || temp == 0x0C) {
			mCanInfo.DOWNWARD_AIR_INDICATOR = 1;
		} else {
			mCanInfo.DOWNWARD_AIR_INDICATOR = 0;
		}

		mCanInfo.AIR_RATE = (int) (msg[9] & 0x0f);

		temp = (int) (msg[10] & 0xff);
		mCanInfo.DRIVING_POSITON_TEMP = temp == 0xFE ? 0 : temp == 0xFF ? 255
				: (temp * 0.5f);

	}

	void analyzeSteeringButtonData(byte[] msg) {
		// TODO Auto-generated method stub
		if (msg.length > 6) {
			return;
		}
		mCanInfo.STEERING_BUTTON_MODE = (int) (msg[3] & 0xFF);
		mCanInfo.STEERING_BUTTON_STATUS = (int) (msg[4] & 0xFF);
	}

}
