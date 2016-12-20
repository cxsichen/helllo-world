package com.console.canreader.bean;

import java.io.UnsupportedEncodingException;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;
import com.console.canreader.utils.Contacts;

import android.util.Log;

public class SSCHANGANYX extends AnalyzeUtils {
	// 数据类型
	public static final int comID = 3;
	// DataType
	// 车身基本信息
	public static final int CAR_BASIC_INFO_DATA = 0x11;
	// 车身基本信息
	public static final int CAR_INFO_DATA_1 = 0x12;
	// 空调信息
	public static final int AIR_CONDITIONER_DATA = 0x31;
	// 车身基本信息
	public static final int CAR_BASIC_INFO_DATA_1 = 0x21;
	// 车身基本信息
	public static final int CAR_BASIC_INFO_DATA_2 = 0x22;	
	// 车身基本信息
	public static final int CAR_INFO_DATA_3 = 0xF0;

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
			case CAR_BASIC_INFO_DATA:
				mCanInfo.CHANGE_STATUS = 2;
				analyzeCarBasicInfoData(msg);
				break;
			case CAR_INFO_DATA_1:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_1(msg);
				break;								
			case AIR_CONDITIONER_DATA:
				mCanInfo.CHANGE_STATUS = 3;
				analyzeAirConditionData(msg);
				break;
			case CAR_BASIC_INFO_DATA_1:
				mCanInfo.CHANGE_STATUS = 2;
				analyzeCarBasicInfoData_1(msg);
				break;
			case CAR_BASIC_INFO_DATA_2:
				mCanInfo.CHANGE_STATUS = 2;
				analyzeCarBasicInfoData_2(msg);
				break;
				
			case CAR_INFO_DATA_3:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_3(msg);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

    String radarSave = "";
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
	
   String carBasicInfo_2 = "";
	void analyzeCarBasicInfoData_2(byte[] msg) {
		if (carBasicInfo_2.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carBasicInfo_2 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.CAR_VOLUME_KNOB=msg[5] & 0xff;
		switch ((msg[4] & 0x03)) {
		case 0x01:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.KNOBVOLUME;	
			break;
		case 0x02:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.KNOBSELECTOR;	
			break;
		case 0x03:
		case 0x04:
		case 0x05:
		default:
			break;
		}	
	}
	
    String carBasicInfo_1 = "";
	void analyzeCarBasicInfoData_1(byte[] msg) {
		if (carBasicInfo_1.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carBasicInfo_1 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.STEERING_BUTTON_STATUS = (int) (msg[5] & 0x01);
		int temp = (int) (msg[4] & 0xff);
		switch (temp) {
		case 0x02:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUUP;
			break;
		case 0x03:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUDOWN;
			break;
		case 0x05:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.EQ;
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
		case 0x16:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.ENTER;
			break;
		case 0x20:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MAP;
			break;
		case 0x24:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MUSIC;
			break;
		case 0x37:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.HOME;
			break;
		case 0x39:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.POWER;
			break;
		case 0x43:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
			break;
		case 0x47:
		case 0x48:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.FM_AM;
			break;
		default:
			mCanInfo.STEERING_BUTTON_STATUS = 0;
			mCanInfo.STEERING_BUTTON_MODE = 0;
			break;
		}
	}

   String carBasicInfo = "";
   int steelWheel = 0;
	void analyzeCarBasicInfoData(byte[] msg) {
		if (carBasicInfo.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carBasicInfo = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.STEERING_BUTTON_STATUS = (int) (msg[7] & 0x01);
		int temp = (int) (msg[6] & 0xff);
		switch (temp) {
		case 0x01:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLUP;
			break;
		case 0x02:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLDOW;
			break;
		case 0x03:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MUTE;
			break;
		case 0x0C:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
			break;
		case 0x0D:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUUP;
			break;
		case 0x0E:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUDOWN;
			break;
		default:
			mCanInfo.STEERING_BUTTON_STATUS = 0;
			mCanInfo.STEERING_BUTTON_MODE = 0;
			break;
		}
		
		temp = ((int) msg[10] & 0xFF) << 8 | ((int) msg[11] & 0xFF);
		if (temp < 32767) {
			mCanInfo.STERRING_WHELL_STATUS = -temp;
		} else {
			mCanInfo.STERRING_WHELL_STATUS = 65536 - temp;
		}
		if (steelWheel != temp) {
			steelWheel = temp;
			mCanInfo.CHANGE_STATUS = 8;
		}

	}

    String carInfoSave_3 = "";

	void analyzeCarInfoData_3(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_3.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_3 = BytesUtil.bytesToHexString(msg);
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

    String carInfoSave_1 = "";

	void analyzeCarInfoData_1(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_1.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_1 = BytesUtil.bytesToHexString(msg);
		}

		mCanInfo.RIGHT_BACKDOOR_STATUS = (int) ((msg[6] >> 4) & 0x01);
		mCanInfo.LEFT_BACKDOOR_STATUS = (int) ((msg[6] >> 5) & 0x01);
		mCanInfo.RIGHT_FORONTDOOR_STATUS = (int) ((msg[6] >> 6) & 0x01);
		mCanInfo.LEFT_FORONTDOOR_STATUS = (int) ((msg[6] >> 7) & 0x01);
		mCanInfo.TRUNK_STATUS = (int) ((msg[6] >> 3) & 0x01);
		mCanInfo.SAFETY_BELT_STATUS = (int) ((msg[6] >> 1) & 0x01) == 0 ? 1 : 0;

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
        
		
		mCanInfo.AIR_CONDITIONER_STATUS = (int) ((msg[4] >> 6) & 0x01);
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

		if (temp == 0x03 ||temp == 0x05 || temp == 0x0C) {
			mCanInfo.DOWNWARD_AIR_INDICATOR = 1;
		} else {
			mCanInfo.DOWNWARD_AIR_INDICATOR = 0;
		}
		mCanInfo.AIR_RATE = (int) (msg[9] & 0x0f);

		
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
