package com.console.canreader.bean;

import java.io.UnsupportedEncodingException;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;
import com.console.canreader.utils.Contacts;

import android.util.Log;

public class SSGE extends AnalyzeUtils {
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
	// 面板按键
	public static final int MENU_BUTTON = 0x21;
	// 面板按键
	public static final int MENU_BUTTON_1 = 0x22;
	// 车身信息
	public static final int CAR_INFO_DATA_3 = 0x23;
	// 车身信息
	public static final int CAR_INFO_DATA_4 = 0x35;
	// 车身信息
	public static final int CAR_INFO_DATA_5 = 0x45;
	// 车身信息
	public static final int CAR_INFO_DATA_6 = 0x46;
	// 车身信息
	public static final int CAR_INFO_DATA_7 = 0x55;
	// 车身信息
	public static final int CAR_INFO_DATA_8 = 0x56;
	// 车身信息
	public static final int CAR_INFO_DATA_9 = 0x65;
	// 车身信息
	public static final int CAR_INFO_DATA_10 = 0x66;
	// 车身信息
	public static final int CAR_INFO_DATA_11 = 0x67;
	// 车身信息
	public static final int CAR_INFO_DATA_12 = 0x68;
	// 车身信息
	public static final int CAR_INFO_DATA_13 = 0x75;
	// 车身信息
	public static final int CAR_INFO_DATA_14 = 0x85;
	// 车身信息
	public static final int CAR_INFO_DATA_15 = 0xF0;
	// 车身信息
	public static final int CAR_INFO_DATA_16 = 0xC2;
	// 车身信息
	public static final int CAR_INFO_DATA_17 = 0xC3;
	// 车身信息
	public static final int CAR_INFO_DATA_18 = 0xB1;
	// 车身信息
	public static final int CAR_INFO_DATA_19 = 0xB2;
	// 车身信息
	public static final int CAR_INFO_DATA_20 = 0xB3;
	// 车身信息
	public static final int CAR_INFO_DATA_21 = 0xB4;

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
			case CAR_INFO_DATA_1:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_1(msg);
				break;
			case CAR_INFO_DATA_2:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_2(msg);
				break;
			case CAR_BASIC_INFO_DATA:
				analyzeCarBasicInfoData(msg);
				break;
			case RADAR_DATA:
				mCanInfo.CHANGE_STATUS = 11;
				analyzeRadarData(msg);
				break;
			case MENU_BUTTON:
				mCanInfo.CHANGE_STATUS = 2;
				analyzeMenuButton(msg);
				break;
			case MENU_BUTTON_1:
				mCanInfo.CHANGE_STATUS = 2;
				analyzeMenuButton_1(msg);
				break;
			case CAR_INFO_DATA_3:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_3(msg);
				break;
			case CAR_INFO_DATA_4:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_4(msg);
				break;
			case CAR_INFO_DATA_5:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_5(msg);
				break;
			case CAR_INFO_DATA_6:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_6(msg);
				break;
			case CAR_INFO_DATA_7:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_7(msg);
				break;
			case CAR_INFO_DATA_8:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_8(msg);
				break;
			case CAR_INFO_DATA_9:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_9(msg);
				break;
			case CAR_INFO_DATA_10:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_10(msg);
				break;
			case CAR_INFO_DATA_11:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_11(msg);
				break;
			case CAR_INFO_DATA_12:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_12(msg);
				break;
			case CAR_INFO_DATA_13:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_13(msg);
				break;
			case CAR_INFO_DATA_14:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_14(msg);
				break;
			case CAR_INFO_DATA_15:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_15(msg);
				break;
			case CAR_INFO_DATA_16:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_16(msg);
				break;
			case CAR_INFO_DATA_17:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_17(msg);
				break;
			case CAR_INFO_DATA_18:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_18(msg);
				break;
			case CAR_INFO_DATA_19:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_19(msg);
				break;
			case CAR_INFO_DATA_20:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_20(msg);
				break;
			case CAR_INFO_DATA_21:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_21(msg);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	static String menuButtonSave_1 = "";

	void analyzeMenuButton_1(byte[] msg) {
		if (menuButtonSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			menuButtonSave = BytesUtil.bytesToHexString(msg);
		}
       if((int) (msg[4] & 0xFF)==1){
    	   mCanInfo.CAR_VOLUME_KNOB=(int) msg[5];
    	   if(mCanInfo.CAR_VOLUME_KNOB>0)
    		   mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.KNOBVOLUMEUP;
    	   if(mCanInfo.CAR_VOLUME_KNOB<0)
    		   mCanInfo.CAR_VOLUME_KNOB=-mCanInfo.CAR_VOLUME_KNOB;
    		   mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.KNOBVOLUMEDOWN;
       }
       if(((int) (msg[4] & 0xFF)==2)||((int) (msg[4] & 0xFF)==3)){
    	   mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.KNOBSELECT;
    	   mCanInfo.CAR_VOLUME_KNOB=(int) msg[5];
       }
	
	}

	static String menuButtonSave = "";

	void analyzeMenuButton(byte[] msg) {
		if (menuButtonSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			menuButtonSave = BytesUtil.bytesToHexString(msg);
		}

		switch ((int) (msg[4] & 0xFF)) {
		case 0x01:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.POWER;
			break;
		case 0x02:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUUP;
			break;
		case 0x03:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUDOWN;
			break;
		case 0x04:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SETTING;
			break;
		case 0x05:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MUSIC;
			break;
		case 0x06:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.BACK;
			break;
		case 0x07:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.FM_AM;
			break;
		case 0x08:
		case 0x1F:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.AUX;
			break;
		case 0x09:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MUTE;
			break;
		case 0x0A:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.NUM1;
			break;
		case 0x0B:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.NUM2;
			break;
		case 0x0C:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.NUM3;
			break;
		case 0x0D:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.NUM4;
			break;
		case 0x0E:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.NUM5;
			break;
		case 0x0F:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.NUM6;
			break;		
		case 0x10:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MUSIC_PLAY_PAUSE;
			break;
		case 0x12:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.HOME;
			break;
		case 0x16:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.ENTER;
			break;
		case 0x17:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.DPAD_UP;
			break;
		case 0x18:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.DPAD_DOWN;
			break;
		case 0x19:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.DPAD_LEFT;
			break;
		case 0x1A:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.DPAD_RIHGT;
			break;
		case 0x20:
		case 0x25:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MAP;
			break;
		case 0x22:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.DEL;
			break;
		case 0x24:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VIDEO;
			break;
		case 0x29:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.PHONE_APP;
			break;
		case 0x2C:
		case 0x2D:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
			break;
		case 0x2A:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.ENTER;
			break;
		default:
			mCanInfo.STEERING_BUTTON_MODE = 0;
			break;
		}
		mCanInfo.STEERING_BUTTON_STATUS = (int) (msg[5] & 0xFF);
	}

	static String carInfoSave_20 = "";

	void analyzeCarInfoData_20(byte[] msg) {
		if (carInfoSave_20.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_20 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.ONSTAR_WARING_STATUS = ((int) (msg[4] >> 7) & 0x01);
		mCanInfo.ONSTAR_WARING_TYPE = ((int) (msg[4]) & 0x7F);
	}

	static String carInfoSave_19 = "";

	void analyzeCarInfoData_19(byte[] msg) {
		if (carInfoSave_19.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_19 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.ONSTAR_PHONE_HOUR = ((int) (msg[4]) & 0xFF);
		mCanInfo.ONSTAR_PHONE_MINUTE = ((int) (msg[5]) & 0xFF);
		mCanInfo.ONSTAR_PHONE_SECOND = ((int) (msg[6]) & 0xFF);

		mCanInfo.ONSTAR_LEFTIME_1 = ((int) (msg[7]) & 0xFF);
		mCanInfo.ONSTAR_LEFTIME_2 = ((int) (msg[8]) & 0xFF);

		mCanInfo.ONSTAR_EFFECTTIME_YEAR_1 = ((int) (msg[9]) & 0xFF);
		mCanInfo.ONSTAR_EFFECTTIME_YEAR_2 = ((int) (msg[10]) & 0xFF);
		mCanInfo.ONSTAR_EFFECTTIME_MOUTH = ((int) (msg[11]) & 0xFF);
		mCanInfo.ONSTAR_EFFECTTIME_DAY = ((int) (msg[12]) & 0xFF);
	}

	static String carInfoSave_18 = "";

	void analyzeCarInfoData_18(byte[] msg) {
		if (carInfoSave_18.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_18 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.ONSTAR_STATUS = ((int) (msg[4]) & 0xFF);
		mCanInfo.ONSTAR_PHONE_TYPE = ((int) (msg[5]) & 0xFF);
		mCanInfo.ONSTAR_PHONE_SIGN = ((int) (msg[6]) & 0x01);
	}

	static String carInfoSave_21 = "";

	void analyzeCarInfoData_21(byte[] msg) {
		if (carInfoSave_21.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_21 = BytesUtil.bytesToHexString(msg);
		}
		int len = ((int) msg[2] & 0xFF);
		byte[] acscii = new byte[len];
		for (int i = 0; i < len; i++) {
			acscii[i] = msg[i + 4];

		}
		try {
			mCanInfo.ONSTAR_RECEIVE_PHONE = new String(acscii, "GB2312");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static String carInfoSave_17 = "";

	void analyzeCarInfoData_17(byte[] msg) {
		if (carInfoSave_17.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_17 = BytesUtil.bytesToHexString(msg);
		}
		int len = ((int) msg[2] & 0xFF);
		byte[] acscii = new byte[len];
		for (int i = 0; i < len; i++) {
			acscii[i] = msg[i + 4];

		}
		try {
			mCanInfo.BLUETOOTH_NAME = new String(acscii, "GB2312");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static String carInfoSave_16 = "";

	void analyzeCarInfoData_16(byte[] msg) {
		if (carInfoSave_16.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_16 = BytesUtil.bytesToHexString(msg);
		}
		int len = ((int) msg[2] & 0xFF);
		byte[] acscii = new byte[len];
		for (int i = 0; i < len; i++) {
			acscii[i] = msg[i + 4];

		}
		try {
			mCanInfo.BLUETOOTH_PASSWARD = new String(acscii, "GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static String carInfoSave_15 = "";

	void analyzeCarInfoData_15(byte[] msg) {
		if (carInfoSave_15.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_15 = BytesUtil.bytesToHexString(msg);
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

	static String carInfoSave_14 = "";

	void analyzeCarInfoData_14(byte[] msg) {
		if (carInfoSave_14.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_14 = BytesUtil.bytesToHexString(msg);
		}
		if (((int) (msg[4] >> 7) & 0x01) == 0) {
			mCanInfo.SPORT_ENGINE_STATUS = -1;
		} else {
			mCanInfo.SPORT_ENGINE_STATUS = ((int) (msg[5] >> 7) & 0x01);
		}

		if (((int) (msg[4] >> 6) & 0x01) == 0) {
			mCanInfo.SPORT_BACKLIGHT_MODE = -1;
		} else {
			mCanInfo.SPORT_BACKLIGHT_MODE = ((int) (msg[5] >> 6) & 0x01);
		}

	}

	static String carInfoSave_13 = "";

	void analyzeCarInfoData_13(byte[] msg) {
		if (carInfoSave_13.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_13 = BytesUtil.bytesToHexString(msg);
		}
		if (((int) (msg[4] >> 7) & 0x01) == 0) {
			mCanInfo.DISPLAY_ECO_MIXPOWER = -1;
		} else {
			mCanInfo.DISPLAY_ECO_MIXPOWER = ((int) (msg[5] >> 7) & 0x01);
		}

		if (((int) (msg[4] >> 6) & 0x01) == 0) {
			mCanInfo.DISPLAY_NAVI_MSG = -1;
		} else {
			mCanInfo.DISPLAY_NAVI_MSG = ((int) (msg[5] >> 6) & 0x01);
		}

		if (((int) (msg[4] >> 5) & 0x01) == 0) {
			mCanInfo.DISPLAY_SPEED_RANG = -1;
		} else {
			mCanInfo.DISPLAY_SPEED_RANG = ((int) (msg[5] >> 5) & 0x01);
		}
	}

	static String carInfoSave_12 = "";

	void analyzeCarInfoData_12(byte[] msg) {
		if (carInfoSave_12.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_12 = BytesUtil.bytesToHexString(msg);
		}
		if (((int) (msg[4] >> 7) & 0x01) == 0) {
			mCanInfo.TPMS_FL_PRESSUE = -1;
			mCanInfo.TPMS_FR_PRESSUE = -1;
			mCanInfo.TPMS_BL_PRESSUE = -1;
			mCanInfo.TPMS_BR_PRESSUE = -1;
		} else {
			mCanInfo.TPMS_FL_PRESSUE = ((int) msg[5] & 0xFF) * 256
					+ ((int) msg[6] & 0xFF);
			mCanInfo.TPMS_FR_PRESSUE = ((int) msg[7] & 0xFF) * 256
					+ ((int) msg[8] & 0xFF);
			mCanInfo.TPMS_BL_PRESSUE = ((int) msg[9] & 0xFF) * 256
					+ ((int) msg[10] & 0xFF);
			mCanInfo.TPMS_BR_PRESSUE = ((int) msg[11] & 0xFF) * 256
					+ ((int) msg[12] & 0xFF);
		}
		mCanInfo.TPMS_FL_WARING = 0;
		mCanInfo.TPMS_FR_WARING = 0;
		mCanInfo.TPMS_BL_WARING = 0;
		mCanInfo.TPMS_BR_WARING = 0;
		if (((int) (msg[15] >> 2) & 0x01) == 1) {
			if ((((int) (msg[15] >> 1) & 0x01) == 1)
					|| (((int) (msg[15] >> 0) & 0x01) == 1)) {
				mCanInfo.TPMS_FL_WARING = 1;
			}
		}
		if (((int) (msg[16] >> 2) & 0x01) == 1) {
			if ((((int) (msg[16] >> 1) & 0x01) == 1)
					|| (((int) (msg[16] >> 0) & 0x01) == 1)) {
				mCanInfo.TPMS_FR_WARING = 1;
			}
		}
		if (((int) (msg[17] >> 2) & 0x01) == 1) {
			if ((((int) (msg[17] >> 1) & 0x01) == 1)
					|| (((int) (msg[17] >> 0) & 0x01) == 1)) {
				mCanInfo.TPMS_BL_WARING = 1;
			}
		}
		if (((int) (msg[18] >> 2) & 0x01) == 1) {
			if ((((int) (msg[18] >> 1) & 0x01) == 1)
					|| (((int) (msg[18] >> 0) & 0x01) == 1)) {
				mCanInfo.TPMS_BR_WARING = 1;
			}
		}
	}

	static String carInfoSave_11 = "";

	void analyzeCarInfoData_11(byte[] msg) {
		if (carInfoSave_11.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_11 = BytesUtil.bytesToHexString(msg);
		}
		if (((int) (msg[4] >> 7) & 0x01) == 0) {
			mCanInfo.LIGHT_CARLAMP_SETTING = -1;
		} else {
			mCanInfo.LIGHT_CARLAMP_SETTING = ((int) (msg[5] >> 7) & 0x01);
		}

		if (((int) (msg[4] >> 6) & 0x01) == 0) {
			mCanInfo.LIGHT_LOCKLAMP_SETTING = -1;
		} else {
			mCanInfo.LIGHT_LOCKLAMP_SETTING = ((int) (msg[5] >> 5) & 0x03);
		}

		if (((int) (msg[4] >> 5) & 0x01) == 0) {
			mCanInfo.LIGHT_RIHGTTURN_LAMP_SETTING = -1;
		} else {
			mCanInfo.LIGHT_RIHGTTURN_LAMP_SETTING = ((int) (msg[5] >> 4) & 0x01);
		}

		if (((int) (msg[4] >> 5) & 0x01) == 0) {
			mCanInfo.LIGHT_LEFTTURN_LAMP_SETTING = -1;
		} else {
			mCanInfo.LIGHT_LEFTTURN_LAMP_SETTING = ((int) (msg[5] >> 3) & 0x01);
		}

	}

	static String carInfoSave_10 = "";

	void analyzeCarInfoData_10(byte[] msg) {
		if (carInfoSave_10.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_10 = BytesUtil.bytesToHexString(msg);
		}
		if (((int) (msg[4] >> 7) & 0x01) == 0) {
			mCanInfo.REMOTECONTROL_LOCK_FEEDBACK = -1;
		} else {
			mCanInfo.REMOTECONTROL_LOCK_FEEDBACK = ((int) (msg[6] >> 6) & 0x03);
		}
		if (((int) (msg[4] >> 6) & 0x01) == 0) {
			mCanInfo.REMOTECONTROL_UNLOCK_FEEDBACK = -1;
		} else {
			mCanInfo.REMOTECONTROL_UNLOCK_FEEDBACK = ((int) (msg[6] >> 5) & 0x01);
		}
		if (((int) (msg[4] >> 5) & 0x01) == 0) {
			mCanInfo.REMOTECONTROL_UNLOCK = -1;
		} else {
			mCanInfo.REMOTECONTROL_UNLOCK = ((int) (msg[6] >> 4) & 0x01);
		}
		if (((int) (msg[4] >> 4) & 0x01) == 0) {
			mCanInfo.REMOTECONTROL_UNLOCK_AUTORELOCK = -1;
		} else {
			mCanInfo.REMOTECONTROL_UNLOCK_AUTORELOCK = ((int) (msg[6] >> 3) & 0x01);
		}
		if (((int) (msg[4] >> 3) & 0x01) == 0) {
			mCanInfo.REMOTECONTROL_RELOCK_DOOR = -1;
		} else {
			mCanInfo.REMOTECONTROL_RELOCK_DOOR = ((int) (msg[6] >> 2) & 0x01);
		}
		if (((int) (msg[4] >> 2) & 0x01) == 0) {
			mCanInfo.REMOTECONTROL_KEY_AUTORECOGNIZE = -1;
		} else {
			mCanInfo.REMOTECONTROL_KEY_AUTORECOGNIZE = ((int) (msg[6] >> 1) & 0x01);
		}
		if (((int) (msg[4] >> 1) & 0x01) == 0) {
			mCanInfo.REMOTECONTROL_START = -1;
		} else {
			mCanInfo.REMOTECONTROL_START = ((int) (msg[6] >> 0) & 0x01);
		}
		if (((int) (msg[4] >> 0) & 0x01) == 0) {
			mCanInfo.REMOTECONTROL_NEAR_AUTOUNLOCK = -1;
		} else {
			mCanInfo.REMOTECONTROL_NEAR_AUTOUNLOCK = ((int) (msg[7] >> 7) & 0x01);
		}
		if (((int) (msg[5] >> 7) & 0x01) == 0) {
			mCanInfo.REMOTECONTROL_AWAY_AUTOLOCK = -1;
		} else {
			mCanInfo.REMOTECONTROL_AWAY_AUTOLOCK = ((int) (msg[7] >> 4) & 0x03);
		}
		if (((int) (msg[5] >> 6) & 0x01) == 0) {
			mCanInfo.REMOTECONTROL_KEYLEF_ALARM = -1;
		} else {
			mCanInfo.REMOTECONTROL_KEYLEF_ALARM = ((int) (msg[7] >> 6) & 0x01);
		}

		if (((int) (msg[5] >> 5) & 0x01) == 0) {
			mCanInfo.REMOTECONTROL_MOVE_DOOR = -1;
		} else {
			mCanInfo.REMOTECONTROL_MOVE_DOOR = ((int) (msg[7] >> 3) & 0x01);
		}

		if (((int) (msg[5] >> 4) & 0x01) == 0) {
			mCanInfo.REMOTECONTROL_WINDOW_CONTROL = -1;
		} else {
			mCanInfo.REMOTECONTROL_WINDOW_CONTROL = ((int) (msg[7] >> 2) & 0x01);
		}
	}

	static String carInfoSave_9 = "";

	void analyzeCarInfoData_9(byte[] msg) {
		if (carInfoSave_9.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_9 = BytesUtil.bytesToHexString(msg);
		}
		if (((int) (msg[4] >> 7) & 0x01) == 0) {
			mCanInfo.LOCK_OPENDOOR_WITHOUTLOCK = -1;
		} else {
			mCanInfo.LOCK_OPENDOOR_WITHOUTLOCK = ((int) (msg[5] >> 7) & 0x01);
		}

		if (((int) (msg[4] >> 6) & 0x01) == 0) {
			mCanInfo.AUTO_LOCK_SETTING = -1;
		} else {
			mCanInfo.AUTO_LOCK_SETTING = ((int) (msg[5] >> 6) & 0x01);
		}

		if (((int) (msg[4] >> 5) & 0x01) == 0) {
			mCanInfo.AUTO_OPEN_LOCK_Z = -1;
		} else {
			mCanInfo.AUTO_OPEN_LOCK_Z = ((int) (msg[5] >> 4) & 0x03);
		}

		if (((int) (msg[4] >> 4) & 0x01) == 0) {
			mCanInfo.LOCK_DELAY_LOCK = -1;
		} else {
			mCanInfo.LOCK_DELAY_LOCK = ((int) (msg[5] >> 3) & 0x01);
		}

		if (((int) (msg[4] >> 3) & 0x01) == 0) {
			mCanInfo.AUTO_OPEN_LOCK_S = -1;
		} else {
			mCanInfo.AUTO_OPEN_LOCK_S = ((int) (msg[5] >> 0) & 0x03);
		}
	}

	static String carInfoSave_8 = "";

	void analyzeCarInfoData_8(byte[] msg) {
		if (carInfoSave_8.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_8 = BytesUtil.bytesToHexString(msg);
		}
		if (((int) (msg[4] >> 7) & 0x01) == 0) {
			mCanInfo.CONVENIENCE_AUTO_WIPE = -1;
		} else {
			mCanInfo.CONVENIENCE_AUTO_WIPE = ((int) (msg[5] >> 7) & 0x01);
		}
	}

	static String carInfoSave_7 = "";

	void analyzeCarInfoData_7(byte[] msg) {
		if (carInfoSave_7.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_7 = BytesUtil.bytesToHexString(msg);
		}
		if (((int) (msg[4] >> 7) & 0x01) == 0) {
			mCanInfo.CONVENIENCE_SEAT_PARK_MOVE = -1;
		} else {
			mCanInfo.CONVENIENCE_SEAT_PARK_MOVE = ((int) (msg[5] >> 7) & 0x01);
		}

		if (((int) (msg[4] >> 6) & 0x01) == 0) {
			mCanInfo.CONVENIENCE_RIPE_PARK_MOVE = -1;
		} else {
			mCanInfo.CONVENIENCE_RIPE_PARK_MOVE = ((int) (msg[5] >> 5) & 0x03);
		}

		if (((int) (msg[4] >> 5) & 0x01) == 0) {
			mCanInfo.CONVENIENCE_OUTERMIRROR_PARK_LEAN = -1;
		} else {
			mCanInfo.CONVENIENCE_OUTERMIRROR_PARK_LEAN = ((int) (msg[5] >> 4) & 0x01);
		}

		if (((int) (msg[4] >> 4) & 0x01) == 0) {
			mCanInfo.CONVENIENCE_OUTERMIRROR_PARK_FOLD = -1;
		} else {
			mCanInfo.CONVENIENCE_OUTERMIRROR_PARK_FOLD = ((int) (msg[5] >> 3) & 0x01);
		}

		if (((int) (msg[4] >> 3) & 0x01) == 0) {
			mCanInfo.CONVENIENCE_DRIVER_PRIVATE_SETTING = -1;
		} else {
			mCanInfo.CONVENIENCE_DRIVER_PRIVATE_SETTING = ((int) (msg[5] >> 2) & 0x01);
		}

		if (((int) (msg[4] >> 2) & 0x01) == 0) {
			mCanInfo.CONVENIENCE_REVERSE_BACKWIPE_AUTO = -1;
		} else {
			mCanInfo.CONVENIENCE_REVERSE_BACKWIPE_AUTO = ((int) (msg[5] >> 1) & 0x01);
		}

		if (((int) (msg[4] >> 1) & 0x01) == 0) {
			mCanInfo.CONVENIENCE_RIPE_PARK_LEAN = -1;
		} else {
			mCanInfo.CONVENIENCE_RIPE_PARK_LEAN = ((int) (msg[5] >> 0) & 0x01);
		}

	}

	static String carInfoSave_6 = "";

	void analyzeCarInfoData_6(byte[] msg) {
		if (carInfoSave_6.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_6 = BytesUtil.bytesToHexString(msg);
		}
		if (((int) (msg[4] >> 7) & 0x01) == 0) {
			mCanInfo.CRASHPROOF_CARSTATUS_NOTIFY = -1;
		} else {
			mCanInfo.CRASHPROOF_CARSTATUS_NOTIFY = ((int) (msg[5] >> 7) & 0x01);
		}

		if (((int) (msg[4] >> 6) & 0x01) == 0) {
			mCanInfo.CRASHPROOF_RAMPWAY_ASSIT = -1;
		} else {
			mCanInfo.CRASHPROOF_RAMPWAY_ASSIT = ((int) (msg[5] >> 6) & 0x01);
		}
	}

	static String carInfoSave_5 = "";

	void analyzeCarInfoData_5(byte[] msg) {
		if (carInfoSave_5.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_5 = BytesUtil.bytesToHexString(msg);
		}
		if (((int) (msg[4] >> 7) & 0x01) == 0) {
			mCanInfo.PARKING_ASSIT_STATUS = -1;
		} else {
			mCanInfo.PARKING_ASSIT_STATUS = ((int) (msg[5] >> 7) & 0x01);
		}

		if (((int) (msg[4] >> 6) & 0x01) == 0) {
			mCanInfo.CRASHPROOF_SIDE_BLIND_AREA = -1;
		} else {
			mCanInfo.CRASHPROOF_SIDE_BLIND_AREA = ((int) (msg[5] >> 6) & 0x01);
		}

		if (((int) (msg[4] >> 5) & 0x01) == 0) {
			mCanInfo.CRASHPROOF_WARING = -1;
		} else {
			mCanInfo.CRASHPROOF_WARING = ((int) (msg[5] >> 5) & 0x01);
		}

		if (((int) (msg[4] >> 4) & 0x01) == 0) {
			mCanInfo.CRASHPROOF_PART_ASSIT = -1;
		} else {
			mCanInfo.CRASHPROOF_PART_ASSIT = ((int) (msg[5] >> 3) & 0x03);
		}

		if (((int) (msg[4] >> 3) & 0x01) == 0) {
			mCanInfo.CRASHPROOF_24GHZ_RADAR = -1;
		} else {
			mCanInfo.CRASHPROOF_24GHZ_RADAR = ((int) (msg[5] >> 2) & 0x01);
		}

		if (((int) (msg[4] >> 2) & 0x01) == 0) {
			mCanInfo.CRASHPROOF_AUTO_READY = -1;
		} else {
			mCanInfo.CRASHPROOF_AUTO_READY = ((int) (msg[5] >> 0) & 0x03);
		}

	}

	static String carInfoSave_4 = "";

	void analyzeCarInfoData_4(byte[] msg) {
		if (carInfoSave_4.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_4 = BytesUtil.bytesToHexString(msg);
		}
		if (((int) (msg[4] >> 7) & 0x01) == 0) {
			mCanInfo.AIRCON_AUTO_WIND = -1;
		} else {
			mCanInfo.AIRCON_AUTO_WIND = ((int) (msg[6] >> 6) & 0x03);
		}

		if (((int) (msg[4] >> 6) & 0x01) == 0) {
			mCanInfo.AIRCON_MODE = -1;
		} else {
			mCanInfo.AIRCON_MODE = ((int) (msg[6] >> 4) & 0x03);
		}

		if (((int) (msg[4] >> 5) & 0x01) == 0) {
			mCanInfo.AIRCON_AIR_QUALITY = -1;
		} else {
			mCanInfo.AIRCON_AIR_QUALITY = ((int) (msg[6] >> 2) & 0x03);
		}

		if (((int) (msg[4] >> 4) & 0x01) == 0) {
			mCanInfo.AIRCON_AUTOZONE_TEMP = -1;
		} else {
			mCanInfo.AIRCON_AUTOZONE_TEMP = ((int) (msg[6] >> 0) & 0x03);
		}

		if (((int) (msg[4] >> 3) & 0x01) == 0) {
			mCanInfo.AIRCON_SEAT_AUTOVENT = -1;
		} else {
			mCanInfo.AIRCON_SEAT_AUTOVENT = ((int) (msg[7] >> 7) & 0x01);
		}

		if (((int) (msg[4] >> 2) & 0x01) == 0) {
			mCanInfo.AIRCON_SEAT_AUTOHEAT = -1;
		} else {
			mCanInfo.AIRCON_SEAT_AUTOHEAT = ((int) (msg[7] >> 6) & 0x01);
		}

		if (((int) (msg[4] >> 1) & 0x01) == 0) {
			mCanInfo.AIRCON_CONTORL_AUTOVENT = -1;
		} else {
			mCanInfo.AIRCON_CONTORL_AUTOVENT = ((int) (msg[7] >> 5) & 0x01);
		}

		if (((int) (msg[4] >> 0) & 0x01) == 0) {
			mCanInfo.AIRCON_CONTORL_AUTOHEAT = -1;
		} else {
			mCanInfo.AIRCON_CONTORL_AUTOHEAT = ((int) (msg[7] >> 4) & 0x01);
		}

		if (((int) (msg[5] >> 7) & 0x01) == 0) {
			mCanInfo.AIRCON_BACKAREA_TEMP = -1;
		} else {
			mCanInfo.AIRCON_BACKAREA_TEMP = ((int) (msg[7] >> 2) & 0x03);
		}

		if (((int) (msg[5] >> 6) & 0x01) == 0) {
			mCanInfo.AIRCON_FRONT_DEMIST = -1;
		} else {
			mCanInfo.AIRCON_FRONT_DEMIST = ((int) (msg[7] >> 1) & 0x01);
		}

		if (((int) (msg[5] >> 5) & 0x01) == 0) {
			mCanInfo.AIRCON_BACK_DEMIST = -1;
		} else {
			mCanInfo.AIRCON_BACK_DEMIST = ((int) (msg[7] >> 0) & 0x01);
		}

		if (((int) (msg[5] >> 4) & 0x01) == 0) {
			mCanInfo.AIRCON_REMOTE_START = -1;
		} else {
			mCanInfo.AIRCON_REMOTE_START = ((int) (msg[8] >> 7) & 0x01);
		}

		if (((int) (msg[5] >> 3) & 0x01) == 0) {
			mCanInfo.AIRCON_AIR_QUALITY_1 = -1;
		} else {
			mCanInfo.AIRCON_AIR_QUALITY_1 = ((int) (msg[8] >> 5) & 0x03);
		}

		if (((int) (msg[5] >> 2) & 0x01) == 0) {
			mCanInfo.AIRCON_CONTORL_AUTOHEAT_1 = -1;
		} else {
			mCanInfo.AIRCON_CONTORL_AUTOHEAT_1 = ((int) (msg[8] >> 3) & 0x03);
		}
	}

	static String carInfoSave_3 = "";

	void analyzeCarInfoData_3(byte[] msg) {
		if (carInfoSave_3.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_3 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.CAR_TYPE = ((int) msg[4] & 0xFF);
	}

	static String radarSave = "";
	static int temps[] = { 0, 0, 0, 0, 0, 0, 0, 0 };

	void analyzeRadarData(byte[] msg) {
		if (radarSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			radarSave = BytesUtil.bytesToHexString(msg);
		}

		for (int i = 0; i < 8; i++) {
			temps[i] = (((int) (msg[4 + i] & 0xff) * 4 / 255) + 1) > 4 ? 4
					: (((int) (msg[4 + i] & 0xff) * 4 / 255) + 1);
		}
		if (mCanInfo.BACK_LEFT_DISTANCE != temps[0]
				|| mCanInfo.BACK_MIDDLE_LEFT_DISTANCE != temps[1]
				|| mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE != temps[2]
				|| mCanInfo.BACK_RIGHT_DISTANCE != temps[3]
				|| mCanInfo.FRONT_LEFT_DISTANCE != temps[4]
				|| mCanInfo.FRONT_MIDDLE_LEFT_DISTANCE != temps[5]
				|| mCanInfo.FRONT_MIDDLE_RIGHT_DISTANCE != temps[6]
				|| mCanInfo.FRONT_RIGHT_DISTANCE != temps[7]) {
			mCanInfo.BACK_LEFT_DISTANCE = temps[0];
			mCanInfo.BACK_MIDDLE_LEFT_DISTANCE = temps[1];
			mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE = temps[2];
			mCanInfo.BACK_RIGHT_DISTANCE = temps[3];

			mCanInfo.FRONT_LEFT_DISTANCE = temps[4];
			mCanInfo.FRONT_MIDDLE_LEFT_DISTANCE = temps[5];
			mCanInfo.FRONT_MIDDLE_RIGHT_DISTANCE = temps[6];
			mCanInfo.FRONT_RIGHT_DISTANCE = temps[7];
		}

	}

	static String carBasicInfo = "";

	/*
	 * 方向盘按键 STEERING_BUTTON_MODE 0：无按键或释放 1：vol+ 2：vol- 3：menuup 4：menu down 5：
	 * PHONE 6：mute 7：SRC 8：SPEECH/MIC 9:answer phone 10:hangup phone
	 */
	static int keyCode[] = { 0, 1, 2, 8, 9, -1, 3, 4, 4, 5, 6 };

	void analyzeCarBasicInfoData(byte[] msg) {
		if (carBasicInfo.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carBasicInfo = BytesUtil.bytesToHexString(msg);
		}

		int temp = ((int) msg[10] & 0xFF) << 8 | ((int) msg[11] & 0xFF);
		if (temp < 32767) {
			if (mCanInfo.STERRING_WHELL_STATUS != -temp) {
				mCanInfo.STERRING_WHELL_STATUS = -temp;
				mCanInfo.CHANGE_STATUS = 8;
			}

		} else {
			if (mCanInfo.STERRING_WHELL_STATUS != 65536 - temp) {
				mCanInfo.STERRING_WHELL_STATUS = 65536 - temp;
				mCanInfo.CHANGE_STATUS = 8;
			}
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

	static String carInfoSave_2 = "";

	void analyzeCarInfoData_2(byte[] msg) {
		if (carInfoSave_2.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_2 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.INSTANT_CONSUMPTION = (((int) msg[4] & 0xFF) * 256 + ((int) msg[5] & 0xFF)) * 0.1f;
		mCanInfo.RANGE = (((int) msg[6] & 0xFF) << 8 + ((int) msg[7] & 0xFF));
		mCanInfo.DRIVING_DISTANCE = ((((int) msg[8] & 0xFF) << 16)
				| (((int) msg[9] & 0xFF) << 8) | (((int) msg[10] & 0xFF))) / 10;

		mCanInfo.TRIP_A_1_AVERAGE_CONSUMPTION = (((int) msg[11] & 0xFF) * 256 + ((int) msg[12] & 0xFF)) * 0.1f;
		mCanInfo.TRIP_A_1 = ((((int) msg[13] & 0xFF) << 16)
				| (((int) msg[14] & 0xFF) << 8) | (((int) msg[15] & 0xFF))) * 0.1f;
		mCanInfo.TRIP_A_2_AVERAGE_CONSUMPTION = (((int) msg[16] & 0xFF) * 256 + ((int) msg[17] & 0xFF)) * 0.1f;
		mCanInfo.TRIP_A_2 = ((((int) msg[18] & 0xFF) << 16)
				| (((int) msg[19] & 0xFF) << 8) | (((int) msg[20] & 0xFF))) * 0.1f;
		mCanInfo.TRIP_A_3_AVERAGE_CONSUMPTION = (((int) msg[21] & 0xFF) * 256 + ((int) msg[22] & 0xFF)) * 0.1f;
		mCanInfo.TRIP_A_3 = ((((int) msg[23] & 0xFF) << 16)
				| (((int) msg[24] & 0xFF) << 8) | (((int) msg[25] & 0xFF))) * 0.1f;

		mCanInfo.INSTANT_CONSUMPTION_UNIT = (int) ((msg[26] >> 2) & 0x01);
		mCanInfo.TRIP_A_UNIT = (int) ((msg[26] >> 0) & 0x03);
	}

	static String carInfoSave_1 = "";

	void analyzeCarInfoData_1(byte[] msg) {

		if (carInfoSave_1.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_1 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.ENGINE_SPEED = ((int) msg[6] & 0xFF) << 8
				| ((int) msg[7] & 0xFF);
		mCanInfo.DRIVING_SPEED = ((int) msg[8] & 0xFF) << 8
				| ((int) msg[9] & 0xFF);
		mCanInfo.REMAIN_FUEL = (int) (msg[12] & 0xFF);
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
		mCanInfo.HOOD_STATUS = (int) ((msg[6] >> 2) & 0x01);
		mCanInfo.TRUNK_STATUS = (int) ((msg[6] >> 3) & 0x01);
		mCanInfo.RIGHT_BACKDOOR_STATUS = (int) ((msg[6] >> 4) & 0x01);
		mCanInfo.LEFT_BACKDOOR_STATUS = (int) ((msg[6] >> 5) & 0x01);
		mCanInfo.RIGHT_FORONTDOOR_STATUS = (int) ((msg[6] >> 6) & 0x01);
		mCanInfo.LEFT_FORONTDOOR_STATUS = (int) ((msg[6] >> 7) & 0x01);

		mCanInfo.FUEL_WARING_SIGN = (int) ((msg[10] >> 7) & 0x01);
		mCanInfo.BATTERY_WARING_SIGN = (int) ((msg[10] >> 6) & 0x01);
		mCanInfo.HANDBRAKE_STATUS = (int) (msg[12] & 0xFF);
		mCanInfo.BATTERY_VOLTAGE = ((int) (msg[11] & 0xFF)) * 0.1f;
		mCanInfo.SAFETY_BELT_STATUS = -1;
		mCanInfo.DISINFECTON_STATUS = -1;
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
		mCanInfo.AIRCON_SHOW_REQUEST = (int) ((msg[4] >> 7) & 0x01);
		mCanInfo.AIR_CONDITIONER_STATUS = (int) ((msg[4] >> 6) & 0x01);
		mCanInfo.MIRROR_SYNC_ADJUST = (int) ((msg[4] >> 2) & 0x03);  //sync
		mCanInfo.AC_INDICATOR_STATUS = (((int) ((msg[4] >> 0) & 0x03)) == 0 ? 0
				: 1);
		
		int temp = (int) ((msg[5] >> 4) & 0x01);
		mCanInfo.CYCLE_INDICATOR = temp;
		mCanInfo.SMALL_LANTERN_INDICATOR = (int) ((msg[5] >> 3) & 0x01);

		mCanInfo.MAX_FRONT_LAMP_INDICATOR = (int) ((msg[6] >> 4) & 0x01);
		mCanInfo.REAR_LAMP_INDICATOR = (int) ((msg[6] >> 5) & 0x01);
		mCanInfo.LEFT_SEAT_TEMP = (int) ((msg[6] >> 0) & 0x03);
		mCanInfo.RIGTHT_SEAT_TEMP = (int) ((msg[6] >> 2) & 0x03);
		

		 temp = (int) (msg[10] & 0xff);
		mCanInfo.DRIVING_POSITON_TEMP = temp == 0xFE ? 0 : temp == 0xFF ? 255
				: (temp * 0.5f);

		temp = (int) (msg[11] & 0xff);
		mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = temp == 0xFE ? 0
				: temp == 0xFF ? 255 : (temp * 0.5f);

		mCanInfo.AIR_RATE = ((int) (msg[9] & 0x0f) == 0x13) ? -1
				: ((int) (msg[9] & 0x0f));

		temp = (int) (msg[8] & 0xff);
		if (temp == 0x01||temp == 0x02||temp == 0x0B || temp == 0x0C || temp == 0x0D || temp == 0x0E) {
			mCanInfo.UPWARD_AIR_INDICATOR = 1;
		} else {
			mCanInfo.UPWARD_AIR_INDICATOR = 0;
		}

		if (temp == 0x01||temp == 0x05 || temp == 0x06 || temp == 0x0D || temp == 0x0E) {
			mCanInfo.PARALLEL_AIR_INDICATOR = 1;
		} else {
			mCanInfo.PARALLEL_AIR_INDICATOR = 0;
		}

		if (temp == 0x01||temp == 0x03 || temp == 0x05 || temp == 0x0C || temp == 0x0E) {
			mCanInfo.DOWNWARD_AIR_INDICATOR = 1;
		} else {
			mCanInfo.DOWNWARD_AIR_INDICATOR = 0;
		}

		mCanInfo.OUTSIDE_TEMPERATURE = ((int) (msg[15] & 0xff)) * 0.5f - 40f;

		

		

	}

}
