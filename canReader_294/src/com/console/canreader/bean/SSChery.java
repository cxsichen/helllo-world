package com.console.canreader.bean;

import java.io.UnsupportedEncodingException;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;
import com.console.canreader.utils.Contacts;

import android.util.Log;

public class SSChery extends AnalyzeUtils {
	// 数据类型
	public static final int comID = 3;
	// 车身基本信息
	public static final int CAR_INFO_DATA = 0x11;
	// 车身详细信息
	public static final int CAR_INFO_DATA_1 = 0x12;
	// 空调信息
	public static final int AIR_CONDITIONER_DATA = 0x31;
	// 前后雷达信息
	public static final int RADAR_DATA = 0x41;
	// 面板按键
	public static final int PANEL_BUTTON = 0x21;
	// 面板旋钮
	public static final int PANEL_KNOB = 0x22;
	// 其他中控信息反馈
	public static final int CONTROLL_INFO_DATA = 0x87;
	// 车型信息
	public static final int CAR_MODEL_DATA = 0x26;
	// 原车屏状态信息
	public static final int CAR_STATUS_DATA = 0xe8;
	// 软件版本信息
	public static final int CAR_VERSION_DATA = 0xf0;

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
				analyzeCarInfoData1(msg);
				break;
			case AIR_CONDITIONER_DATA:
				mCanInfo.CHANGE_STATUS = 3;
				analyzeAirConditionData(msg);
				break;
			case RADAR_DATA:
				mCanInfo.CHANGE_STATUS = 11;
				analyzeRadarData(msg);
				break;
			case PANEL_BUTTON:
				mCanInfo.CHANGE_STATUS = 2;
				analyzePanelButton(msg);
				break;
			case PANEL_KNOB:
				mCanInfo.CHANGE_STATUS = 2;
				analyzePanelKnob(msg);
				break;
			case CONTROLL_INFO_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeControllData(msg);
				break;
			case CAR_MODEL_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarModeData(msg);
				break;
			case CAR_VERSION_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarVersionData(msg);
				break;
			case CAR_STATUS_DATA:
				mCanInfo.CHANGE_STATUS = 2;
				analyzeCarStatusData(msg);
				break;
			default:
				break;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	 String CarStatusDatasave = "";

	private void analyzeCarStatusData(byte[] msg) {
		if (CarStatusDatasave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			CarStatusDatasave = BytesUtil.bytesToHexString(msg);
		}
		if(((int) (msg[6] & 0xff)==1)||((int) (msg[7] & 0xff)==1)||((int) (msg[8] & 0xff)==1)){
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.OPENRIGHTSIGHT;
		}else{
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.CLOSERIGHTSIGHT;
		}

	}

	 String CarVersionDatasave = "";

	private void analyzeCarVersionData(byte[] msg) {
		if (CarVersionDatasave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			CarVersionDatasave = BytesUtil.bytesToHexString(msg);
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

	 String CarModeDatasave = "";

	private void analyzeCarModeData(byte[] msg) {
		if (CarModeDatasave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			CarModeDatasave = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.CAR_TYPE = (int) (msg[5] & 0xff);

	}

	 String ControllDatasave = "";

	private void analyzeControllData(byte[] msg) {
		if (ControllDatasave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			ControllDatasave = BytesUtil.bytesToHexString(msg);
		}

		mCanInfo.CRITICAL_PARK_ENABLE = (int) ((msg[4] >> 7) & 0x01);
		mCanInfo.AUTO_LOCK_SETTING_ENABLE = (int) ((msg[4] >> 6) & 0x01);
		mCanInfo.FRONT_LAMP_DELAY_ENABLE = (int) ((msg[4] >> 5) & 0x01);
		mCanInfo.DAYTIME_LAMP_STATUS_ENABLE = (int) ((msg[4] >> 4) & 0x01);
		mCanInfo.TURN_START_AVM_ENABLE = (int) ((msg[4] >> 3) & 0x01);
		mCanInfo.TURN_START_ANIMATION_ENABLE = (int) ((msg[4] >> 2) & 0x01);
		mCanInfo.SELECTOR_CAR_ASSIST_ENABLE = (int) ((msg[4] >> 1) & 0x01);
		mCanInfo.SPEED_OVER_SETTING_ENABLE = (int) ((msg[4] >> 0) & 0x01);

		if(mCanInfo.CRITICAL_PARK_ENABLE==1){
			mCanInfo.CRITICAL_PARK_STATUS = (int) ((msg[6] >> 7) & 0x01);
			mCanInfo.CRITICAL_PARK_MODE = (int) ((msg[6] >> 5) & 0x03);
		}else{
			mCanInfo.CRITICAL_PARK_STATUS = -1;
			mCanInfo.CRITICAL_PARK_MODE = -1;
		}
		
		if(mCanInfo.AUTO_LOCK_SETTING_ENABLE==1){
			mCanInfo.AUTO_LOCK_SETTING = (int) ((msg[6] >> 4) & 0x01);
		}else{
			mCanInfo.AUTO_LOCK_SETTING = -1;
		}
		
		
		
		mCanInfo.FRONT_LAMP_DELAY_STATUS = mCanInfo.FRONT_LAMP_DELAY_ENABLE==0?-1:(int) ((msg[6] >> 3) & 0x01);
		mCanInfo.DAYTIME_LAMP_STATUS = mCanInfo.DAYTIME_LAMP_STATUS_ENABLE==0?-1:(int) ((msg[6] >> 2) & 0x01);
		mCanInfo.TURN_START_AVM_STATUS = mCanInfo.TURN_START_AVM_ENABLE==0?-1:(int) ((msg[6] >> 1) & 0x01);
		mCanInfo.TURN_START_ANIMATION_STATUS =mCanInfo.TURN_START_ANIMATION_ENABLE==0?-1:(int) ((msg[6] >> 0) & 0x01);

		mCanInfo.SELECTOR_CAR_ASSIST_STATUS = mCanInfo.SELECTOR_CAR_ASSIST_ENABLE==0?-1:(int) ((msg[7] >> 6) & 0x03);
		mCanInfo.SPEED_OVER_SETTING = mCanInfo.SPEED_OVER_SETTING_ENABLE==0?-1:(int) (msg[7] & 0x1f);
		mCanInfo.BACK_LIGHT_DATA=((int) ((msg[5] >> 7) & 0x01)==0)?-1:(int) ((msg[8] >> 4) & 0x0f);
	}

	 String PanelKnobsave = "";

	private void analyzePanelKnob(byte[] msg) {
		if (PanelKnobsave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			PanelKnobsave = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.KNOBVOLUME;
		mCanInfo.CAR_VOLUME_KNOB = (int) (msg[5] & 0x0f);
		mCanInfo.CHANGE_STATUS = 2;
	}

	 String PanelButtonsave = "";

	private void analyzePanelButton(byte[] msg) {
		if (PanelButtonsave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			PanelButtonsave = BytesUtil.bytesToHexString(msg);
		}
		int temp = (int) (msg[4] & 0xff);
		switch (temp) {
		case 0x01:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.POWER;
			break;
		case 0x25:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MAP;
			break;
		case 0x2b:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.HOME;
			break;
		case 0x37:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.CARINFO;
			break;
		case 0x43:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MUSIC;
			break;
		default:
			break;
		}
		temp = (int) (msg[5] & 0x01);
		if (mCanInfo.STEERING_BUTTON_STATUS != temp) {
			mCanInfo.STEERING_BUTTON_STATUS = temp;
			mCanInfo.CHANGE_STATUS = 2;
		}

	}

	 String carData1Save = "";

	private void analyzeCarInfoData1(byte[] msg) {
		if (carData1Save.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carData1Save = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.LEFT_FORONTDOOR_STATUS = (int) ((msg[6] >> 7) & 0x01);
		mCanInfo.RIGHT_FORONTDOOR_STATUS = (int) ((msg[6] >> 6) & 0x01);
		mCanInfo.LEFT_BACKDOOR_STATUS = (int) ((msg[6] >> 5) & 0x01);
		mCanInfo.RIGHT_BACKDOOR_STATUS = (int) ((msg[6] >> 4) & 0x01);
		mCanInfo.TRUNK_STATUS = (int) ((msg[6] >> 3) & 0x01);
		if((((msg[6] >> 2) & 0x01) == 0 )||(((msg[6] >> 1) & 0x01) == 0 ) ){
			mCanInfo.SAFETY_BELT_STATUS =1;
		}else{
			mCanInfo.SAFETY_BELT_STATUS =0;
		}
		mCanInfo.HOOD_STATUS = (int) ((msg[6] >> 0) & 0x01);

	}

	 String carDataSave = "";

	private void analyzeCarInfoData(byte[] msg) {
		if (carDataSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carDataSave = BytesUtil.bytesToHexString(msg);
		}

		mCanInfo.HANDBRAKE_STATUS = (int) ((msg[4] >> 3) & 0x01);
		mCanInfo.CAR_BACK_STATUS = (int) ((msg[4] >> 2 & 0x01));
		mCanInfo.CAR_ILL_STATUS = (int) ((msg[4] >> 1 & 0x01));
		mCanInfo.CAR_ACC_STATUS = (int) ((msg[4] >> 0 & 0x01));

		int temp = (int) (msg[6] & 0x0f);
		switch (temp) {
		case 0x00:
			break;
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
		case 0x0c:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.HOME;
			break;
		default:
			break;
		}

		temp = (int) (msg[7] & 0x01);
		if (mCanInfo.STEERING_BUTTON_STATUS != temp) {
			mCanInfo.STEERING_BUTTON_STATUS = temp;
			mCanInfo.CHANGE_STATUS = 2;
		}

		// 方向盘转角 CHANGE_STATUS=8
		temp = (int) (msg[10] & 0xFF) * 256 + (int) (msg[11] & 0xFF);
		if (temp > 0xffff / 2) {
			if (mCanInfo.STERRING_WHELL_STATUS != (65535 - temp)) {
				mCanInfo.STERRING_WHELL_STATUS = 65535 - temp;
				mCanInfo.CHANGE_STATUS = 8;
			}
		} else {
			if (mCanInfo.STERRING_WHELL_STATUS != -temp) {
				mCanInfo.STERRING_WHELL_STATUS = -temp;
				mCanInfo.CHANGE_STATUS = 8;
			}
		}

		// Log.i("xxx",
		// "mCanInfo.STERRING_WHELL_STATUS=="+mCanInfo.STERRING_WHELL_STATUS);

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

		mCanInfo.BACK_LEFT_DISTANCE = (int) (msg[4] & 0x0f);
		if(mCanInfo.BACK_LEFT_DISTANCE==15){
			mCanInfo.BACK_LEFT_DISTANCE=4;
		}
		mCanInfo.BACK_MIDDLE_LEFT_DISTANCE = (int) (msg[5] & 0x0f);
		if(mCanInfo.BACK_MIDDLE_LEFT_DISTANCE==15){
			mCanInfo.BACK_MIDDLE_LEFT_DISTANCE=4;
		}
		mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE = (int) (msg[6] & 0x0f);
		if(mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE==15){
			mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE=4;
		}
		mCanInfo.BACK_RIGHT_DISTANCE = (int) (msg[7] & 0x0f);
		if(mCanInfo.BACK_RIGHT_DISTANCE==15){
			mCanInfo.BACK_RIGHT_DISTANCE=4;
		}
		mCanInfo.FRONT_LEFT_DISTANCE = (int) (msg[8] & 0x0f);
		if(mCanInfo.FRONT_LEFT_DISTANCE==15){
			mCanInfo.FRONT_LEFT_DISTANCE=4;
		}
		mCanInfo.FRONT_MIDDLE_LEFT_DISTANCE = (int) (msg[9] & 0x0f);
		if(mCanInfo.FRONT_MIDDLE_LEFT_DISTANCE==15){
			mCanInfo.FRONT_MIDDLE_LEFT_DISTANCE=4;
		}
		mCanInfo.FRONT_MIDDLE_RIGHT_DISTANCE = (int) (msg[10] & 0x0f);
		if(mCanInfo.FRONT_MIDDLE_RIGHT_DISTANCE==15){
			mCanInfo.FRONT_MIDDLE_RIGHT_DISTANCE=4;
		}
		mCanInfo.FRONT_RIGHT_DISTANCE = (int) (msg[11] & 0x0f);
		if(mCanInfo.FRONT_RIGHT_DISTANCE==15){
			mCanInfo.FRONT_RIGHT_DISTANCE=4;
		}

		mCanInfo.RADAR_ALARM_STATUS = (int) (msg[14] & 0x0f);
		
		Log.i("cxs","=========mCanInfo.BACK_LEFT_DISTANCE========"+mCanInfo.BACK_LEFT_DISTANCE);
		// //////////雷达显示方式没做/////////////////////
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
		mCanInfo.AC_MAX_STATUS = (int) ((msg[4] >> 5) & 0x01);
		mCanInfo.SMALL_LANTERN_INDICATOR = (int) ((msg[4] >> 3) & 0x01);

		mCanInfo.AC_INDICATOR_STATUS = (int) ((msg[5] >> 6) & 0x01);
		mCanInfo.CYCLE_INDICATOR = ((int) (msg[5] >> 4) & 0x01);
		mCanInfo.DAUL_LAMP_INDICATOR = ((int) (msg[5] >> 2) & 0x01);

		mCanInfo.REAR_LAMP_INDICATOR = ((int) (msg[6] >> 5) & 0x01);
		mCanInfo.MAX_FRONT_LAMP_INDICATOR = ((int) (msg[6] >> 4) & 0x01);

		int temp = msg[8] & 0x0f;
		if (temp == 0) {
			mCanInfo.DOWNWARD_AIR_INDICATOR = 0;
			mCanInfo.PARALLEL_AIR_INDICATOR = 0;
			mCanInfo.UPWARD_AIR_INDICATOR = 0;
		} else {
			if (temp == 0x01 || temp == 0x03 || temp == 0x05 || temp == 0x0c
					|| temp == 0x0e) {
				mCanInfo.DOWNWARD_AIR_INDICATOR = 1;
			} else {
				mCanInfo.DOWNWARD_AIR_INDICATOR = 0;
			}
			if (temp == 0x01 || temp == 0x05 || temp == 0x06 || temp == 0x0d
					|| temp == 0x0e) {
				mCanInfo.PARALLEL_AIR_INDICATOR = 1;
			} else {
				mCanInfo.PARALLEL_AIR_INDICATOR = 0;
			}
			if (temp == 0x01 || temp == 0x0c || temp == 0x0d || temp == 0x0e) {
				mCanInfo.UPWARD_AIR_INDICATOR = 1;
			} else {
				mCanInfo.UPWARD_AIR_INDICATOR = 0;
			}
		}
		mCanInfo.AIR_RATE = (int) (msg[9] & 0x0f);
		temp = (int) (msg[10] & 0xff);
		mCanInfo.DRIVING_POSITON_TEMP = temp == 0xFE ? 0 : temp == 0xFF ? 255
				: (temp * 0.5f);
		temp = (int) (msg[11] & 0xff);
		mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = temp == 0xFE ? 0
				: temp == 0xFF ? 255 : (temp * 0.5f);
		temp = (int) (msg[15] & 0xff);
		mCanInfo.OUTSIDE_TEMPERATURE = temp * 0.5f - 40;

	}

}
