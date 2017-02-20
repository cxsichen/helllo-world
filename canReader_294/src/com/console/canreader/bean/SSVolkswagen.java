package com.console.canreader.bean;

import java.io.UnsupportedEncodingException;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.Trace;

import android.util.Log;

public class SSVolkswagen extends AnalyzeUtils {

	// 数据类型
	public static final int comID = 3;
	// 方向盘按键
	public static final int STEERING_BUTTON_DATA = 0x72;
	// 空调信息
	public static final int AIR_CONDITIONER_DATA = 0x73;
	// 车身信息
	public static final int CAR_INFO_DATA_10 = 0xF0;
	// 车门信息
	public static final int CAR_INFO_DATA = 0x47;
	// 车身信息
	public static final int CAR_INFO_DATA_2 = 0x12;
	// 车身信息
	public static final int CAR_INFO_DATA_3 = 0x13;
	
	


	// DataType

	public CanInfo getCanInfo() {
		return mCanInfo;
	}

	@Override
	public void analyzeEach(byte[] msg) {
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
			case CAR_INFO_DATA_10:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_10(msg);
				break;
			case CAR_INFO_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData(msg);
				break;
			case CAR_INFO_DATA_2:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_2(msg);
				break;		
			case CAR_INFO_DATA_3:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_3(msg);
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

	String SteeringButtonStatusDataSave_2 = "";

	void analyzeSteeringButtonData_2(byte[] msg) {
		// TODO Auto-generated method stub
		if (SteeringButtonStatusDataSave_2.equals(BytesUtil
				.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			SteeringButtonStatusDataSave_2 = BytesUtil.bytesToHexString(msg);
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
		case 0x05:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
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
		case 0x1F:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.AUX;
			break;
		case 0x28:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.PHONE_APP;
			break;
		case 0x2A:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.ENTER;
			break;
		case 0x2C:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
			break;
		case 0x2D:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.FM_AM;
			break;
		case 0x30:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.NUM7;
			break;
		case 0x31:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.NUM8;
			break;
		case 0x32:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.NUM9;
			break;
		case 0x33:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.NUM0;
			break;
		case 0x34:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.NUMSTAR;
			break;
		case 0x35:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.POUND;
			break;
		case 0x3A:
		case 0x3B:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MUSIC;
			break;
		default:
			mCanInfo.STEERING_BUTTON_MODE = 0;
			break;
		}

		mCanInfo.STEERING_BUTTON_STATUS = (int) (msg[5] & 0xFF);
	}

	String SteeringButtonStatusDataSave_3 = "";

	void analyzeSteeringButtonData_3(byte[] msg) {
		// TODO Auto-generated method stub
		if (SteeringButtonStatusDataSave_3.equals(BytesUtil
				.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			SteeringButtonStatusDataSave_3 = BytesUtil.bytesToHexString(msg);
		}

		if ((msg[4] & 0xff) == 1) {
			mCanInfo.CAR_VOLUME_KNOB = (int) msg[5];
			if (mCanInfo.CAR_VOLUME_KNOB > 0) {
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.KNOBVOLUMEUP;
			} else if (mCanInfo.CAR_VOLUME_KNOB < 0) {
				mCanInfo.CAR_VOLUME_KNOB = -mCanInfo.CAR_VOLUME_KNOB;
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.KNOBVOLUMEDOWN;
			}
		} else {
			mCanInfo.STEERING_BUTTON_MODE = 0;
		}
	}

	/*
	 * 方向盘转角最大值135 前后雷达最大值 160 左右雷达最大值 60
	 */
	String SteeringButtonStatusDataSave = "";

	void analyzeSteeringButtonData(byte[] msg) {
		// TODO Auto-generated method stub
		if (SteeringButtonStatusDataSave
				.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			SteeringButtonStatusDataSave = BytesUtil.bytesToHexString(msg);
		}

		mCanInfo.STEERING_BUTTON_STATUS = 1;

		switch ((int) (msg[6] & 0xFF)) {
		case 0x01:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLUP;
			break;
		case 0x02:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLDOW;
			break;
		case 0x03:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MUTE;
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
		default:
			mCanInfo.STEERING_BUTTON_MODE = 0;
			mCanInfo.STEERING_BUTTON_STATUS = 0;
			break;
		}
		int temp = (int) ((msg[4] >> 3) & 0x01);
		if (temp != mCanInfo.HANDBRAKE_STATUS) {
			mCanInfo.HANDBRAKE_STATUS = temp;
			mCanInfo.CHANGE_STATUS = 10;
		}

		temp = (int) ((msg[5] >> 0) & 0xFF);
		if (temp != mCanInfo.DRIVING_SPEED) {
			mCanInfo.DRIVING_SPEED = temp;
			mCanInfo.CHANGE_STATUS = 10;
		}
		int temp1 = (int) ((msg[8] >> 0) & 0xFF);
		int temp2 = (int) ((msg[9] >> 0) & 0xFF);
		if (temp1 != 0xFF && temp2 != 0xFF) {
			if (temp2 == 0 && temp1 == 0) {
				temp = 0;
			} else {
				temp = ((temp1 == 0) ? (-temp2) : temp1) * 540 / 135;
			}
		} else {
			temp = 0;
		}

		if (mCanInfo.STERRING_WHELL_STATUS != temp) {
			mCanInfo.STERRING_WHELL_STATUS = temp;
			mCanInfo.CHANGE_STATUS = 8;
		}

		temp1 = (int) ((msg[10] >> 0) & 0xFF);
		if (temp1 != 0xFF) {
			temp = (temp1 / 40 + 1) > 4 ? 4 : (temp1 / 40 + 1);
		} else {
			temp = 0;
		}
		if (temp != mCanInfo.BACK_LEFT_DISTANCE) {
			mCanInfo.BACK_LEFT_DISTANCE = temp;
			mCanInfo.CHANGE_STATUS = 11;
		}

		temp1 = (int) ((msg[11] >> 0) & 0xFF);
		if (temp1 != 0xFF) {
			temp = (temp1 / 40 + 1) > 4 ? 4 : (temp1 / 40 + 1);
		} else {
			temp = 0;
		}
		if (temp != mCanInfo.BACK_MIDDLE_LEFT_DISTANCE) {
			mCanInfo.BACK_MIDDLE_LEFT_DISTANCE = temp;
			mCanInfo.CHANGE_STATUS = 11;
		}

		temp1 = (int) ((msg[12] >> 0) & 0xFF);
		if (temp1 != 0xFF) {
			temp = (temp1 / 40 + 1) > 4 ? 4 : (temp1 / 40 + 1);
		} else {
			temp = 0;
		}
		if (temp != mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE) {
			mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE = temp;
			mCanInfo.CHANGE_STATUS = 11;
		}

		temp1 = (int) ((msg[13] >> 0) & 0xFF);
		if (temp1 != 0xFF) {
			temp = (temp1 / 40 + 1) > 4 ? 4 : (temp1 / 40 + 1);
		} else {
			temp = 0;
		}
		if (temp != mCanInfo.BACK_RIGHT_DISTANCE) {
			mCanInfo.BACK_RIGHT_DISTANCE = temp;
			mCanInfo.CHANGE_STATUS = 11;
		}

		temp1 = (int) ((msg[14] >> 0) & 0xFF);
		if (temp1 != 0xFF) {
			temp = (temp1 / 40 + 1) > 4 ? 4 : (temp1 / 40 + 1);
		} else {
			temp = 0;
		}
		if (temp != mCanInfo.FRONT_LEFT_DISTANCE) {
			mCanInfo.FRONT_LEFT_DISTANCE = temp;
			mCanInfo.CHANGE_STATUS = 11;
		}

		temp1 = (int) ((msg[15] >> 0) & 0xFF);
		if (temp1 != 0xFF) {
			temp = (temp1 / 40 + 1) > 4 ? 4 : (temp1 / 40 + 1);
		} else {
			temp = 0;
		}
		if (temp != mCanInfo.FRONT_MIDDLE_LEFT_DISTANCE) {
			mCanInfo.FRONT_MIDDLE_LEFT_DISTANCE = temp;
			mCanInfo.CHANGE_STATUS = 11;
		}

		temp1 = (int) ((msg[16] >> 0) & 0xFF);
		if (temp1 != 0xFF) {
			temp = (temp1 / 40 + 1) > 4 ? 4 : (temp1 / 40 + 1);
		} else {
			temp = 0;
		}
		if (temp != mCanInfo.FRONT_MIDDLE_RIGHT_DISTANCE) {
			mCanInfo.FRONT_MIDDLE_RIGHT_DISTANCE = temp;
			mCanInfo.CHANGE_STATUS = 11;
		}

		temp1 = (int) ((msg[17] >> 0) & 0xFF);
		if (temp1 != 0xFF) {
			temp = (temp1 / 40 + 1) > 4 ? 4 : (temp1 / 40 + 1);
		} else {
			temp = 0;
		}
		if (temp != mCanInfo.FRONT_RIGHT_DISTANCE) {
			mCanInfo.FRONT_RIGHT_DISTANCE = temp;
			mCanInfo.CHANGE_STATUS = 11;
		}

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
		mCanInfo.CYCLE_INDICATOR = ((msg[4] >> 4) & 0x01) == 0 ? 0 : 1;

		mCanInfo.SMALL_LANTERN_INDICATOR = (int) ((msg[4] >> 3) & 0x01);
		mCanInfo.AC_MAX_STATUS = (int) ((msg[4] >> 1) & 0x01);
		mCanInfo.LARGE_LANTERN_INDICATOR = (int) ((msg[4] >> 0) & 0x01);

		mCanInfo.AC_INDICATOR_STATUS = (int) ((msg[5] >> 6) & 0x01);
		mCanInfo.REAR_LAMP_INDICATOR = (int) ((msg[5] >> 5) & 0x01);
		mCanInfo.MAX_FRONT_LAMP_INDICATOR = (int) ((msg[5] >> 4) & 0x01);
		mCanInfo.RIGTHT_SEAT_TEMP = (int) ((msg[5] >> 2) & 0x03);
		mCanInfo.LEFT_SEAT_TEMP = (int) ((msg[5] >> 0) & 0x03);

		int temp = (int) (msg[6] & 0xff);
		mCanInfo.DRIVING_POSITON_TEMP = temp == 0x00 ? -1 : temp == 0x01 ? 0
				: temp == 0xFF ? 255 : temp;
		temp = (int) (msg[7] & 0xff);
		mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = temp == 0x00 ? -1
				: temp == 0x01 ? 0 : temp == 0xFF ? 255 : temp;

		mCanInfo.UPWARD_AIR_INDICATOR = (int) ((msg[8] >> 4) & 0x01);
		mCanInfo.PARALLEL_AIR_INDICATOR = (int) ((msg[8] >> 5) & 0x01);
		mCanInfo.DOWNWARD_AIR_INDICATOR =(int) ((msg[8] >> 6) & 0x01);

		mCanInfo.AIR_RATE = (int) ((msg[8] >> 0) & 0x0f);
		
		mCanInfo.OUTSIDE_TEMPERATURE = (int) ((msg[10] >> 0) & 0xff)*0.5f-40;
		
		temp = (int) ((msg[11]>>6) & 0x01);
		if(temp!=mCanInfo.LEFT_FORONTDOOR_STATUS){
			mCanInfo.LEFT_FORONTDOOR_STATUS=temp;
			mCanInfo.CHANGE_STATUS = 10;
		}
			
		temp = (int) ((msg[11]>>7) & 0x01);
		if(temp!=mCanInfo.RIGHT_FORONTDOOR_STATUS){
			mCanInfo.RIGHT_FORONTDOOR_STATUS=temp;
			mCanInfo.CHANGE_STATUS = 10;
		}
		
		temp = (int) ((msg[11]>>5) & 0x01);
		if(temp!=mCanInfo.LEFT_BACKDOOR_STATUS){
			mCanInfo.LEFT_BACKDOOR_STATUS=temp;
			mCanInfo.CHANGE_STATUS = 10;
		}
		
		temp = (int) ((msg[11]>>4) & 0x01);
		if(temp!=mCanInfo.RIGHT_BACKDOOR_STATUS){
			mCanInfo.RIGHT_BACKDOOR_STATUS=temp;
			mCanInfo.CHANGE_STATUS = 10;
		}
		
		temp = (int) ((msg[11]>>3) & 0x01);
		if(temp!=mCanInfo.TRUNK_STATUS){
			mCanInfo.TRUNK_STATUS=temp;
			mCanInfo.CHANGE_STATUS = 10;
		}
		
		temp = (int) ((msg[11]>>2) & 0x01);
		if(temp!=mCanInfo.HOOD_STATUS){
			mCanInfo.HOOD_STATUS=temp;
			mCanInfo.CHANGE_STATUS = 10;
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

		mCanInfo.BACK_LEFT_DISTANCE = (((int) (msg[4] & 0xFF)) == 0xff) ? 4
				: ((msg[4] & 0xFF) + 2) / 2;
		mCanInfo.BACK_MIDDLE_LEFT_DISTANCE = (((int) (msg[5] & 0xFF)) == 0xff) ? 4
				: ((msg[5] & 0xFF) + 2) / 2;
		mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE = (((int) (msg[6] & 0xFF)) == 0xff) ? 4
				: ((msg[6] & 0xFF) + 2) / 2;
		mCanInfo.BACK_RIGHT_DISTANCE = (((int) (msg[7] & 0xFF)) == 0xff) ? 4
				: ((msg[7] & 0xFF) + 2) / 2;

		mCanInfo.FRONT_LEFT_DISTANCE = (((int) (msg[8] & 0xFF)) == 0xff) ? 4
				: ((msg[8] & 0xFF) + 2) / 2;
		mCanInfo.FRONT_MIDDLE_LEFT_DISTANCE = (((int) (msg[9] & 0xFF)) == 0xff) ? 4
				: ((msg[9] & 0xFF) + 2) / 2;
		mCanInfo.FRONT_MIDDLE_RIGHT_DISTANCE = (((int) (msg[10] & 0xFF)) == 0xff) ? 4
				: ((msg[10] & 0xFF) + 2) / 2;
		mCanInfo.FRONT_RIGHT_DISTANCE = (((int) (msg[11] & 0xFF)) == 0xff) ? 4
				: ((msg[11] & 0xFF) + 2) / 2;

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

	String SteeringButtonStatusDataSave_1 = "";

	void analyzeSteeringButtonData_1(byte[] msg) {
		// TODO Auto-generated method stub
		if (SteeringButtonStatusDataSave_1.equals(BytesUtil
				.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			SteeringButtonStatusDataSave_1 = BytesUtil.bytesToHexString(msg);
		}

		switch ((int) (msg[4] & 0xFF)) {
		case 0x01:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOICE_FM;
			break;
		case 0x07:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOICE_MUSIC_PLAY_PAUSE;
			break;
		case 0x05:
			if ((int) (msg[5] & 0xFF) == 1) {
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOICE_MENUUP;
			} else if ((int) (msg[5] & 0xFF) == 2) {
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOICE_MENUDOWN;
			}
			break;
		case 0x03:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.FM_CHANGE_FREQUENCY;
			mCanInfo.FREQUENCY_VALUE = (int) (msg[5] & 0xFF)
					+ ((int) (msg[6] & 0xFF)) * 0.01f;
			break;
		default:
			break;
		}
	}

	String carInfoSave = "";

	void analyzeCarInfoData(byte[] msg) {
		if (carInfoSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave = BytesUtil.bytesToHexString(msg);
		}
		//路边驻车
		mCanInfo.STOPING_STATUS=(int) ((msg[17]>>7) & 0x01);
		//入库驻车
		mCanInfo.REVERSE_GEAR_STATUS=(int) ((msg[17]>>6) & 0x01);
		//雷达静音
		mCanInfo.RADAR_ALARM_STATUS_ENABLE=(int) ((msg[17]>>5) & 0x01);
	}

	String carInfoSave_2 = "";

	void analyzeCarInfoData_2(byte[] msg) {
		if (carInfoSave_2.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_2 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.FUEL_WARING_SIGN = (int) ((msg[10]>>7) & 0x01);
		mCanInfo.BATTERY_WARING_SIGN = (int) ((msg[10]>>6) & 0x01);
		mCanInfo.SAFETY_BELT_STATUS = (int) ((msg[10]>>5) & 0x01);
		mCanInfo.DISINFECTON_STATUS = (int) ((msg[10]>>4) & 0x01);
		
		mCanInfo.REMAIN_FUEL = (int) ((msg[11]>>0) & 0xff);
		mCanInfo.BATTERY_VOLTAGE = (int) ((msg[12]>>0) & 0xff)+changeM((int) ((msg[13]>>0) & 0xff));
	}
	float changeM(int x){
		float temp=x;
		int i=0;
		for(;x/10>=1;i++){
			x=x/10;
		}
		
		for(int j=0;j<=i;j++){
			temp=temp/10f;
		}
		return temp;
	}

	String carInfoSave_3 = "";

	void analyzeCarInfoData_3(byte[] msg) {
		if (carInfoSave_3.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_3 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.DRIVING_DISTANCE = (int) (msg[4] & 0xff)*256*256+(int) (msg[5] & 0xff)*256+(int) (msg[6] & 0xff);
		mCanInfo.ENGINE_SPEED = (int) (msg[12] & 0xff)*256+(int) (msg[13] & 0xff);
	}

	String carInfoSave_4 = "";

	void analyzeCarInfoData_4(byte[] msg) {
		if (carInfoSave_4.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_4 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.TEMP_UNIT = (int) ((msg[5] >> 4) & 0x01);
	}

	String carInfoSave_5 = "";

	void analyzeCarInfoData_5(byte[] msg) {
		if (carInfoSave_5.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_5 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.CAMERA_MODE = (int) ((msg[6]) & 0xff);
	}

	String carInfoSave_6 = "";

	void analyzeCarInfoData_6(byte[] msg) {
		if (carInfoSave_6.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_6 = BytesUtil.bytesToHexString(msg);
		}
		if (((int) ((msg[8]) & 0xff) == 0xff)
				&& ((int) ((msg[9]) & 0xff) == 0xff)
				&& ((int) ((msg[10]) & 0xff) == 0xff)) {
			mCanInfo.DRIVING_DISTANCE = 0;
		} else {
			mCanInfo.DRIVING_DISTANCE = (int) ((msg[8]) & 0xff) * 256 * 256
					+ (int) ((msg[9]) & 0xff) * 256 + (int) ((msg[10]) & 0xff);
		}
	}

	String carInfoSave_7 = "";

	void analyzeCarInfoData_7(byte[] msg) {
		if (carInfoSave_7.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_7 = BytesUtil.bytesToHexString(msg);
		}
		int len = ((int) msg[2] & 0xFF);
		byte[] acscii = new byte[len];
		for (int i = 0; i < len; i++) {
			acscii[i] = msg[i + 4];

		}
		try {
			mCanInfo.VEHICLE_NO = new String(acscii, "GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	String carInfoSave_1 = "";

	void analyzeCarInfoData_1(byte[] msg) {
		if (carInfoSave_1.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_1 = BytesUtil.bytesToHexString(msg);
		}
		if ((int) (msg[4] & 0xff) == 0x09) {
			mCanInfo.CAR_TYPE = (int) (msg[5] & 0xff);
		}
	}

	String VirtualDataSave = "";

	private void analyzeVirtualData(byte[] msg) {
		if (VirtualDataSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			VirtualDataSave = BytesUtil.bytesToHexString(msg);
		}
		int temp = (int) (msg[4] & 0xff);
		switch (temp) {
		case 0x00:
			break;
		case 0x01:// power
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.POWER;
			break;
		case 0x02:// 上一曲
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUUP;
			break;
		case 0x03:// 下一曲
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUDOWN;
			break;
		case 0x4B:// AM-FM
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.FM_AM;
			break;
		case 0x24:// MEDIA
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MUSIC;
			break;
		case 0x28:// PHONE
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.PHONE_APP;
			break;
		case 0x2B:// HOME
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.HOME;
			break;
		case 0x39:// MAP
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MAP;
			break;
		default:
			mCanInfo.STEERING_BUTTON_MODE = 0;
			break;
		}
		temp = (int) (msg[5] & 0xff);
		mCanInfo.STEERING_BUTTON_STATUS = temp;
	}

	String KnobButtonDataSave = "";

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

		mCanInfo.CAR_VOLUME_KNOB = msg[5] & 0xff;
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

}
