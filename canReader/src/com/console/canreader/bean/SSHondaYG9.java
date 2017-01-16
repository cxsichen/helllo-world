package com.console.canreader.bean;

import java.io.UnsupportedEncodingException;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;
import com.console.canreader.utils.Contacts;

import android.util.Log;

public class SSHondaYG9 extends AnalyzeUtils {
	// 数据类型
	public static final int comID = 3;
	// 车身信息
	public static final int CAR_INFO_DATA = 0x11;
	// 车身信息
    public static final int CAR_INFO_DATA_1 = 0x12;
    // 车身信息
 	public static final int CAR_INFO_DATA_2 = 0x16;
    // 车身信息
 	public static final int CAR_INFO_DATA_3 = 0x17;
     // 空调信息
 	public static final int AIR_CONDITIONER_DATA = 0x31;
	// 雷达信息
	public static final int RADAR_DATA = 0x41;
	// 车身信息
	public static final int CAR_INFO_DATA_7 = 0x65;
	// 车身信息
    public static final int CAR_INFO_DATA_5 = 0x67;
    // 车身信息
 	public static final int CAR_INFO_DATA_8 = 0x68;
	// 车身信息
	public static final int CAR_INFO_DATA_9 = 0x75;
	// 车身信息
	public static final int CAR_INFO_DATA_10 = 0xF0;
	// 车身信息
	public static final int CAR_INFO_DATA_4 = 0xE9;	
	// 车身信息
	public static final int CAR_INFO_DATA_6 = 0xE8;
	
	



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
			case AIR_CONDITIONER_DATA:
				mCanInfo.CHANGE_STATUS = 3;
				analyzeAirConditionData(msg);
				break;
			case CAR_INFO_DATA_7:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_7(msg);
				break;
			case CAR_INFO_DATA_5:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_5(msg);
				break;
			case CAR_INFO_DATA_9:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_9(msg);
				break;
			case CAR_INFO_DATA_10:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_10(msg);
				break;
			case CAR_INFO_DATA_4:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_4(msg);
				break;
				
			case CAR_INFO_DATA_6:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_6(msg);
				break;

			case CAR_INFO_DATA_8:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_8(msg);
				break;
			case RADAR_DATA:
				mCanInfo.CHANGE_STATUS = 11;
				analyzeRadarData(msg);
				break;
			default:
				break;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	 String radarSave = "";
	 int temps[] = { 0, 0, 0, 0, 0, 0, 0, 0 };

	void analyzeRadarData(byte[] msg) {
		if (radarSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			radarSave = BytesUtil.bytesToHexString(msg);
		}

		mCanInfo.BACK_LEFT_DISTANCE = (((int) (msg[4] & 0xFF)) == 0xff) ? 0
				: (((int) (msg[4] & 0xFF)) == 0) ? 0
						: (((int) (msg[4] & 0xFF)));
		mCanInfo.BACK_MIDDLE_LEFT_DISTANCE = (((int) (msg[5] & 0xFF)) == 0xff) ? 0
				: (((int) (msg[5] & 0xFF)) == 0) ? 0
						: ( ((int) (msg[5] & 0xFF)));
		mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE = (((int) (msg[6] & 0xFF)) == 0xff) ? 0
				: (((int) (msg[6] & 0xFF)) == 0) ? 0
						: (((int) (msg[6] & 0xFF)));
		mCanInfo.BACK_RIGHT_DISTANCE = (((int) (msg[7] & 0xFF)) == 0xff) ? 0
				: (((int) (msg[7] & 0xFF)) == 0) ? 0
						: (((int) (msg[7] & 0xFF)));
		mCanInfo.RADAR_ALARM_STATUS=((int) (msg[14] & 0xFF));

	}

	 String carInfoSave_1 = "";

	void analyzeCarInfoData_1(byte[] msg) {
		if (carInfoSave_1.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_1 = BytesUtil.bytesToHexString(msg);
		}
		if(((int) ((msg[6] >> 1) & 0x01)==0)||((int) ((msg[6] >> 0) & 0x01)==0)){
			mCanInfo.SAFETY_BELT_STATUS=1;
		}else{
			mCanInfo.SAFETY_BELT_STATUS=0;
		}
		
		mCanInfo.HOOD_STATUS = (int) ((msg[6] >> 2) & 0x01);
		mCanInfo.TRUNK_STATUS = (int) ((msg[6] >> 3) & 0x01);
		mCanInfo.RIGHT_BACKDOOR_STATUS = (int) ((msg[6] >> 4) & 0x01);
		mCanInfo.LEFT_BACKDOOR_STATUS = (int) ((msg[6] >> 5) & 0x01);
		mCanInfo.RIGHT_FORONTDOOR_STATUS = (int) ((msg[6] >> 6) & 0x01);
		mCanInfo.LEFT_FORONTDOOR_STATUS = (int) ((msg[6] >> 7) & 0x01);

	}

	 String carInfoSave_2 = "";
	int[] rangs = { 60, 10, 12, 20, 30, 40, 50, 70, 80, 90, 100 };

	void analyzeCarInfoData_2(byte[] msg) {
		if (carInfoSave_2.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_2 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.INSTANT_CONSUMPTION = (int) ((msg[4] >> 0) & 0xFF);
		mCanInfo.CURRENT_AVERAGE_CONSUMPTION = ((int) (msg[5] & 0xFF) * 256 + (int) (msg[6] & 0xFF)) * 0.1f;
		mCanInfo.HISTORY_AVERAGE_CONSUMPTION = ((int) (msg[7] & 0xFF) * 256 + (int) (msg[8] & 0xFF)) * 0.1f;
		mCanInfo.AVERAGE_CONSUMPTION = ((int) (msg[9] & 0xFF) * 256 + (int) (msg[10] & 0xFF)) * 0.1f;
		mCanInfo.TRIP_A = ((int) (msg[11] & 0xFF) * 256 * 256
				+ (int) (msg[12] & 0xFF) * 256 + (int) (msg[13] & 0xFF)) * 0.1f;
		mCanInfo.RANGE = ((int) (msg[14] & 0xFF) * 256 + (int) (msg[15] & 0xFF));

		mCanInfo.RANGE_UNIT = (int) ((msg[16] >> 7) & 0x01);
		mCanInfo.TRIP_A_UNIT = (int) ((msg[16] >> 6) & 0x01);
		mCanInfo.AVERAGE_CONSUMPTION_UNIT = (int) ((msg[16] >> 4) & 0x03);
		mCanInfo.CUR_HIS_AVERAGE_CONSUMPTION_UNIT = (int) ((msg[16] >> 2) & 0x03);
		mCanInfo.INSTANT_CONSUMPTION_UNIT = (int) ((msg[16] >> 0) & 0x03);
		int temp = (int) ((msg[17] >> 0) & 0xFF);
		mCanInfo.CONSUMPTION_RANGE = rangs[temp];
	}

	 String carInfoSave_3 = "";

	void analyzeCarInfoData_3(byte[] msg) {
		if (carInfoSave_3.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_3 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.TRIP_A_1 = ((int) (msg[4] & 0xFF) * 256 * 256
				+ (int) (msg[5] & 0xFF) * 256 + (int) (msg[6] & 0xFF)) * 0.1f;
		mCanInfo.TRIP_A_1_AVERAGE_CONSUMPTION = ((int) (msg[7] & 0xFF) * 256 + (int) (msg[8] & 0xFF)) * 0.1f;
		mCanInfo.TRIP_A_2 = ((int) (msg[9] & 0xFF) * 256 * 256
				+ (int) (msg[10] & 0xFF) * 256 + (int) (msg[11] & 0xFF)) * 0.1f;
		mCanInfo.TRIP_A_2_AVERAGE_CONSUMPTION = ((int) (msg[12] & 0xFF) * 256 + (int) (msg[13] & 0xFF)) * 0.1f;
		mCanInfo.TRIP_A_3 = ((int) (msg[14] & 0xFF) * 256 * 256
				+ (int) (msg[15] & 0xFF) * 256 + (int) (msg[16] & 0xFF)) * 0.1f;
		mCanInfo.TRIP_A_3_AVERAGE_CONSUMPTION = ((int) (msg[17] & 0xFF) * 256 + (int) (msg[18] & 0xFF)) * 0.1f;
	}

	 String carInfoSave_4 = "";
	 int rightCamera_switch = -1;
	
	void analyzeCarInfoData_4(byte[] msg) {
		if (carInfoSave_4.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_4 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.BACK_CAMERA_MODE = (int) (msg[5] & 0xFF);
		mCanInfo.LEFT_CAMERA_SWITCH = (int) (msg[6] & 0xFF);
		
		if (rightCamera_switch != mCanInfo.LEFT_CAMERA_SWITCH) {
			Log.i("cxs", "========rightCamera_switch========"
					+ rightCamera_switch);
			if (rightCamera_switch != -1) {
				if (mCanInfo.LEFT_CAMERA_SWITCH == 1) {
					mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.OPENRIGHTSIGHT;
				} else {
					mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.CLOSERIGHTSIGHT;
				}
				mCanInfo.STEERING_BUTTON_STATUS = 1;
				mCanInfo.CHANGE_STATUS = 2;
			}
			rightCamera_switch = mCanInfo.LEFT_CAMERA_SWITCH;
		}
		
	}

	 String carInfoSave_5 = "";

	void analyzeCarInfoData_5(byte[] msg) {
		if (carInfoSave_5.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_5 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.FRONT_LAMP_OFF_TIME = (int) ((msg[5] >> 2) & 0x03);
		mCanInfo.LAMP_TURN_DARK_TIME = (int) ((msg[5] >> 0) & 0x03);
	}

	 String carInfoSave_6 = "";

	void analyzeCarInfoData_6(byte[] msg) {
		if (carInfoSave_6.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_6 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.CAR_SHWO_LIGHT=(int) (msg[4] & 0xFF); //原车屏亮度
		mCanInfo.CAR_SHWO_CONTARST = (int) (msg[5]); // 原车屏对比度
		mCanInfo.CAR_SHWO_SATURATION = (int) (msg[6]); // 原车屏饱和度
		
	}

	 String carInfoSave_7 = "";

	void analyzeCarInfoData_7(byte[] msg) {
		if (carInfoSave_7.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_7 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.AUTO_LOCK_TIME = (int) ((msg[5] >> 4) & 0x03);
		mCanInfo.REMOTE_LOCK_SIGN = (int) ((msg[5] >> 6) & 0x01);
	}

	 String carInfoSave_8 = "";

	void analyzeCarInfoData_8(byte[] msg) {
		if (carInfoSave_8.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_8 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.ADJUST_OUTSIDE_TEMP = (int) ((msg[5] >> 0) & 0x0F);
	}

	 String carInfoSave_9 = "";

	void analyzeCarInfoData_9(byte[] msg) {
		if (carInfoSave_9.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_9 = BytesUtil.bytesToHexString(msg);
		}

		mCanInfo.ENERGY_BACKGROUND_LIGHT = (int) ((msg[5] >> 4) & 0x01);
		mCanInfo.SWITCH_TRIPB_SETTING= (int) ((msg[5] >> 2) & 0x03);
		mCanInfo.SWITCH_TRIPA_SETTING= (int) ((msg[5] >> 0) & 0x03);
	}

	 String carInfoSave_10 = "";

	void analyzeCarInfoData_10(byte[] msg) {
		if (carInfoSave_10.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_10 = BytesUtil.bytesToHexString(msg);
		}
		int len = ((int) msg[2] & 0xFF);
		byte[] acscii = new byte[len];
		for (int i = 0; i < len; i++) {
			acscii[i] = msg[i + 4];

		}
		try {
			mCanInfo.VERSION = new String(acscii, "GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	 String carInfoSave = "";
	 int buttonTemp = 0;

	/*
	 * 方向盘按键 STEERING_BUTTON_MODE 0：无按键或释放 1：vol+ 2：vol- 3：menuup 4：menu down 5：
	 * PHONE 6：mute 7：SRC 8：SPEECH/MIC 9:answer phone 10:hangup phone
	 */

	void analyzeCarInfoData(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave = BytesUtil.bytesToHexString(msg);
		}
		// 方向盘转角 CHANGE_STATUS=8
		int temp = 0;
		if ((int) ((msg[10] >> 7) & 0x01) == 0) {
			temp = (((int) msg[10] & 0x7F) * 256 + ((int) msg[11] & 0xFF)) / 10;
		} else {
			temp = -(((int) msg[10] & 0x7F) * 256 + ((int) msg[11] & 0xFF)) / 10;
		}

		if (mCanInfo.STERRING_WHELL_STATUS != temp) {
			mCanInfo.STERRING_WHELL_STATUS = temp;
			mCanInfo.CHANGE_STATUS = 8;
		}
		// 按键 CHANGE_STATUS=2

		if (buttonTemp != (int) (msg[6] & 0xFF)) {
			buttonTemp = (int) (msg[6] & 0xFF);
			switch (buttonTemp) {
			case 0x01:
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLUP;
				break;
			case 0x02:
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLDOW;
				break;
			case 0x05:
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.ANSWER;
				break;
			case 0x06:
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.HANGUP;
				break;
			case 0x08:
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUUP;
				break;
			case 0x09:
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUDOWN;
				break;
			case 0x0A:
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
				break;
			case 0x0C:
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VIDEO;
				break;		
			default:
				mCanInfo.STEERING_BUTTON_MODE = 0;
				break;
			}
			mCanInfo.CHANGE_STATUS = 2;
		}

		temp = (int) (msg[7] & 0xFF);
		if (mCanInfo.STEERING_BUTTON_STATUS != temp) {
			mCanInfo.STEERING_BUTTON_STATUS = temp;
			mCanInfo.CHANGE_STATUS = 2;
		}

		// 报警 CHANGE_STATUS=10

		mCanInfo.DRIVING_SPEED = ((int) msg[5] & 0xFF);
		/*
		 * mCanInfo.HANDBRAKE_STATUS = (int) ((msg[4] >> 3) & 0x01);
		 * mCanInfo.DISINFECTON_STATUS = -1; mCanInfo.SAFETY_BELT_STATUS = -1;
		 * mCanInfo.REMAIN_FUEL = -1; mCanInfo.BATTERY_VOLTAGE = -1;
		 */

	}

	 String airConSave = "";

	void analyzeAirConditionData(byte[] msg) {
		// TODO Auto-generated method stub
		if (airConSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			airConSave = BytesUtil.bytesToHexString(msg);
		}

		mCanInfo.AIR_CONDITIONER_STATUS = ((int) (msg[4] >> 6) & 0x01);
		mCanInfo.CYCLE_INDICATOR = ((int) (msg[5] >> 4) & 0x01);
		mCanInfo.LARGE_LANTERN_INDICATOR = 0;
		mCanInfo.SMALL_LANTERN_INDICATOR = (int) ((msg[4] >> 3) & 0x01);
		mCanInfo.AC_INDICATOR_STATUS = ((int) (msg[4] >> 0) & 0x01);
		mCanInfo.AIR_RATE = (int) (msg[9] & 0x0f);

		int temp = (int) (msg[10] & 0xff);
		mCanInfo.DRIVING_POSITON_TEMP = temp == 0xFE ? 0 : temp == 0xFF ? 255
				: temp ;

		temp = (int) (msg[11] & 0xff);
		mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = temp == 0xFE ? 0
				: temp == 0xFF ? 255 : temp ;

		temp = (int) (msg[8] & 0xff);
		if (temp == 0x04 || temp == 0x02|| temp == 0x07  ) {
			mCanInfo.UPWARD_AIR_INDICATOR = 1;
		} else {
			mCanInfo.UPWARD_AIR_INDICATOR = 0;
		}

		if (temp == 0x05 || temp == 0x06 || temp == 0x07 ) {
			mCanInfo.PARALLEL_AIR_INDICATOR = 1;
		} else {
			mCanInfo.PARALLEL_AIR_INDICATOR = 0;
		}

		if (temp == 0x03 || temp == 0x04 || temp == 0x05) {
			mCanInfo.DOWNWARD_AIR_INDICATOR = 1;
		} else {
			mCanInfo.DOWNWARD_AIR_INDICATOR = 0;
		}
		if(temp == 0x02){
			mCanInfo.MAX_FRONT_LAMP_INDICATOR=1;
		}else{
			mCanInfo.MAX_FRONT_LAMP_INDICATOR=0;
		}
		if(temp == 0x08){
			mCanInfo.REAR_LAMP_INDICATOR=1;
		}else{
			mCanInfo.REAR_LAMP_INDICATOR=0;
		}

		
	}

}
