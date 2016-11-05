package com.console.canreader.bean;

import java.io.UnsupportedEncodingException;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;
import com.console.canreader.utils.Contacts;

import android.util.Log;

public class SSMGGS extends AnalyzeUtils {
	// 数据类型
	public static final int comID = 3;
	// DataType
	// 车身信息
	public static final int CAR_INFO_DATA = 0x11;
	// 车身信息
	public static final int CAR_INFO_DATA_1 = 0x12;
	// 方向盘按键
	public static final int STEERING_BUTTON_DATA = 0x21;
	//面板旋钮
	public static final int KNOB_BUTTON=0x22;
		
	
	
	

	// 车身信息
	public static final int CAR_INFO_DATA_2 = 0x34;
	// 空调信息
	public static final int AIR_CONDITIONER_DATA = 0x31;
	// 车身基本信息
	public static final int CAR_BASIC_INFO_DATA = 0x11;
	// 雷达信息
	public static final int RADAR_DATA = 0x41;
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
			case CAR_INFO_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData(msg);
				break;
			case CAR_INFO_DATA_1:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_1(msg);
				break;
			case STEERING_BUTTON_DATA:
				mCanInfo.CHANGE_STATUS = 2;
				analyzeSteeringButtonData(msg);
				break;	
			case KNOB_BUTTON:
				mCanInfo.CHANGE_STATUS = 2;
				analyzeKnobButtonData(msg);
				break;
				
				
		/*	case AIR_CONDITIONER_DATA:
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
			case CAR_INFO_DATA_3:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_3(msg);
				break;*/
			default:
				break;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	static String KnobButtonDataSave="";
	private void analyzeKnobButtonData(byte[] msg) {
		
		if (KnobButtonDataSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			KnobButtonDataSave = BytesUtil.bytesToHexString(msg);
		}
		if ((msg[5] & 0xff) == 0) {
			return;
		}
		mCanInfo.CAR_VOLUME_KNOB=msg[5] & 0xff;
		switch ((msg[4] & 0x03)) {
		case 0x01:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.KNOBVOLUME;	
			break;
		case 0x02:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.KNOBSELECTOR;	
			break;
		default:
			break;
		}	
		mCanInfo.CHANGE_STATUS = 2;
	}
	
	static String carInfoSave_1 = "";

	void analyzeCarInfoData_1(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_1.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_1 = BytesUtil.bytesToHexString(msg);
		}
	
		mCanInfo.LEFT_FORONTDOOR_STATUS = ((int) (msg[6]>>7) & 0x01);
		mCanInfo.RIGHT_FORONTDOOR_STATUS = ((int) (msg[6]>>6) & 0x01);
		mCanInfo.LEFT_BACKDOOR_STATUS = ((int) (msg[6]>>5) & 0x01);
		mCanInfo.RIGHT_BACKDOOR_STATUS = ((int) (msg[6]>>4) & 0x01);
		mCanInfo.TRUNK_STATUS = ((int) (msg[6]>>3) & 0x01);
		mCanInfo.HOOD_STATUS = ((int) (msg[6]>>2) & 0x01);
		mCanInfo.SAFETY_BELT_STATUS = ((int) (msg[6]>>1) & 0x01)|((int) (msg[6]>>0) & 0x01);
	}

	
	
	static String carInfoSave = "";
	static int buttonTemp = 0;

	void analyzeCarInfoData(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave = BytesUtil.bytesToHexString(msg);
		}
		// 方向盘转角 CHANGE_STATUS=8
		int temp = ((int) msg[10] & 0xFF) * 256 + ((int) msg[11] & 0xFF);
		if(temp>32767){
			temp=0xFFFF-temp;
		}else{
			temp=-temp;
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
			case 0x04:
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
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
			case 0x0B:
			case 0x40:
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
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
	}
	
	static String SteeringButtonStatusDataSave = "";

	void analyzeSteeringButtonData(byte[] msg) {
		// TODO Auto-generated method stub
		if (SteeringButtonStatusDataSave
				.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			SteeringButtonStatusDataSave = BytesUtil.bytesToHexString(msg);
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
		case 0x06:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.BACK;
			break;
		case 0x09:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MUTE;
			break;
		case 0x20:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MAP;
			break;
		case 0x2B:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.HOME;
			break;
		case 0x2C:
		case 0x2F:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
			break;
		default:
			mCanInfo.STEERING_BUTTON_MODE = 0;
			break;
		}

		mCanInfo.STEERING_BUTTON_STATUS = (int) (msg[5] & 0xFF);
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


	
	static String carInfoSave_3 = "";

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



}
