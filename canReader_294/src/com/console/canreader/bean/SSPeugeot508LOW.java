package com.console.canreader.bean;

import java.io.UnsupportedEncodingException;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;
import com.console.canreader.utils.Contacts;

import android.provider.Settings;
import android.util.Log;

public class SSPeugeot508LOW extends AnalyzeUtils {
	// 数据类型
	public static final int comID = 3;
	// 车身基本信息
	public static final int CAR_INFO_DATA = 0x11;
	// 车身详细信息
	public static final int CAR_INFO_DATA_1 = 0x12;
	// 行车电脑信息page0
	public static final int CAR_INFO_DATA_2 = 0x13;
	// 行车电脑信息page1
	public static final int CAR_INFO_DATA_3 = 0x14;
	// 行车电脑信息page2
	public static final int CAR_INFO_DATA_4 = 0x15;
	// 面板按键
	public static final int VIRTUAL_BUTTON_DATA = 0x21;
	// 面板旋钮
	public static final int KNOB_BUTTON = 0x22;
	// 空调信息
	public static final int AIR_CONDITIONER_DATA = 0x31;
	// 雷达信息
	public static final int RADAR_DATA = 0x41;
	// 告警信息
	public static final int WARNING_INFO_DATA = 0x42;
	// 车辆设置信息使能1
	public static final int CAR_INFO_ENABLE_DATA_1 = 0x71;
	// 车辆设置信息使能2
	public static final int CAR_INFO_ENABLE_DATA_2 = 0x72;
	// 车辆设置信息1
	public static final int CAR_SETTING_DATA_1 = 0x76;
	// 车辆设置信息2
	public static final int CAR_SETTING_DATA_2 = 0x79;
	// 软件版本号
	public static final int VERSION_INFO_DATA = 0xf0;
	// 已记忆的速度值设置信息
	public static final int REMEMBER_SPEED_DATA = 0x81;
	// 巡航速度设置信息
	public static final int CRUISE_SPEED_DATA = 0x82;
	// SOS信息
	public static final int SOS_INFO_DATA = 0x83;
	// 运动模式设置信息
	public static final int SPORT_MODE_DATA = 0x85;
	// 语言设置信息
	public static final int LANGUAGE_SETTING_DATA = 0x94;
	// Unit设定信息
	public static final int UNIT_SETTING_DATA = 0xc1;
	// TIME DATA（时间和日期）设定信息
	public static final int TIME_SETTING_DATA = 0xc2;

	// 行车信息设定命令 0x1b DVD到主机
	// 车型设置 0x24 DVD到主机
	// 控制空调 0x3b DVD到主机
	// 车辆设置命令 0x7b DVD到主机
	// 车辆设置命令2 0x7d DVD到主机
	// 已记忆的速度值设置命令
	// 巡航速度值设置命令
	// 运动模式设置命令

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
			case CAR_INFO_DATA_4:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_4(msg);
				break;
			case VIRTUAL_BUTTON_DATA:
				mCanInfo.CHANGE_STATUS = 2;
				analyzeVirtualButtonData(msg);
				break;
			case KNOB_BUTTON:
				mCanInfo.CHANGE_STATUS = 2;
				analyzeKnobButtonData(msg);
				break;
			case AIR_CONDITIONER_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeAirConditionData(msg);
				break;
			case RADAR_DATA:
				mCanInfo.CHANGE_STATUS = 11;
				analyzeRadarData(msg);
				break;
			case CAR_INFO_ENABLE_DATA_1:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarEnableData1(msg);
				break;
			case CAR_INFO_ENABLE_DATA_2:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarEnableData2(msg);
				break;
			case CAR_SETTING_DATA_1:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarSettingData1(msg);
				break;
			case CAR_SETTING_DATA_2:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarSettingData2(msg);
				break;
			case REMEMBER_SPEED_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeRememberSpeedData(msg);
				break;
			case CRUISE_SPEED_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCruiseSpeedData(msg);
				break;
			case SOS_INFO_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeSOSInfoData(msg);
				break;
			case SPORT_MODE_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeSportModeData(msg);
				break;
			case LANGUAGE_SETTING_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeLanguageSettingData(msg);
				break;
			case WARNING_INFO_DATA:
				mCanInfo.CHANGE_STATUS = 12;
				analyzeWarningInfoData(msg);
				break;
			case UNIT_SETTING_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeUnitSettingData(msg);
				break;
			case TIME_SETTING_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeTimeSettingData(msg);
				break;
			default:
				break;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	 String KnobButtonData = "";
	private void analyzeKnobButtonData(byte[] msg) {
		if (KnobButtonData.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			KnobButtonData = BytesUtil.bytesToHexString(msg);
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
		mCanInfo.CHANGE_STATUS = 2;
	}
	
	 String VirtualButtonData = "";
	private void analyzeVirtualButtonData(byte[] msg) {
		if (VirtualButtonData.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			VirtualButtonData = BytesUtil.bytesToHexString(msg);
		}
		int temp=msg[4]&0xff;
		switch (temp) {
		case 0x01://power
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.POWER;
			break;
		case 0x09:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MUSIC;
		case 0x16:
		case 0x27:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.HOME;
			break;

		default:
			break;
		}
		temp = (int) (msg[5] & 0xFF);
		if (mCanInfo.STEERING_BUTTON_STATUS != temp) {
			mCanInfo.STEERING_BUTTON_STATUS = temp;
			mCanInfo.CHANGE_STATUS = 2;
		}
	}

	 String TimeSettingData = "";
	private void analyzeTimeSettingData(byte[] msg) {
		if (TimeSettingData.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			TimeSettingData = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.TIME_YEAR=(int)(msg[4]&0xff);
		mCanInfo.TIME_MONTH=(int)(msg[5]&0xff);
		mCanInfo.TIME_DAY=(int)(msg[6]&0xff);
		mCanInfo.TIME_HOUR=(int)(msg[7]&0xff);
		mCanInfo.TIME_MINUTE=(int)(msg[8]&0xff);
		mCanInfo.TIME_FORMAT=(int)(msg[9]&0xff);
	}

	 String UnitSettingData = "";
	private void analyzeUnitSettingData(byte[] msg) {
		if (UnitSettingData.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			UnitSettingData = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.UNIT_TEMPERATURE_ENABLE=(int)((msg[4]>>5)&0x01);
		mCanInfo.UNIT_CONSUMPTION_ENABLE=(int)((msg[4]>>3)&0x01);
		
		mCanInfo.UNIT_TEMPERATURE=(int)((msg[5]>>5)&0x01);
		mCanInfo.UNIT_CONSUMPTION=(int)((msg[5]>>1)&0x03);
	}

	 String WarningInfoData = "";
	private void analyzeWarningInfoData(byte[] msg) {
		if (WarningInfoData.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			WarningInfoData = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.WARNING_ID=(int)((msg[4]&0xff)<<8)+(int)(msg[5]&0xff);
	}

	 String LanguageSettingData = "";
	private void analyzeLanguageSettingData(byte[] msg) {
		if (LanguageSettingData.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			LanguageSettingData = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.LANGUAGE_CHANGE=(int)(msg[4]&0xff);
	}

	 String SportModeData = "";
	private void analyzeSportModeData(byte[] msg) {
		if (SportModeData.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			SportModeData = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.ENGINE_START_STATUS_ENABLE=(int)((msg[4]>>7)&0x01);
		mCanInfo.ENGINE_START_STATUS=(int)((msg[5]>>7)&0x01);
	}
	
	 String SOSInfoData = "";
	private void analyzeSOSInfoData(byte[] msg) {
		if (SOSInfoData.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			SOSInfoData = BytesUtil.bytesToHexString(msg);
		}
		
		mCanInfo.SOS_STATUS=(int)(msg[4]&0x0f);
	}

	 String CruiseSpeedData = "";
	private void analyzeCruiseSpeedData(byte[] msg) {
		if (CruiseSpeedData.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			CruiseSpeedData = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.CRUISE_SPEED_STATUS=(int)((msg[4]>>7)&0x01);
		mCanInfo.CRUISE_SPEED_1=(int)((msg[4]>>6)&0x01);
		mCanInfo.CRUISE_SPEED_2=(int)((msg[4]>>5)&0x01);
		mCanInfo.CRUISE_SPEED_3=(int)((msg[4]>>4)&0x01);
		mCanInfo.CRUISE_SPEED_4=(int)((msg[4]>>3)&0x01);
		mCanInfo.CRUISE_SPEED_5=(int)((msg[4]>>2)&0x01);
		mCanInfo.CRUISE_SPEED_6=(int)((msg[4]>>1)&0x01);
		
		mCanInfo.CRUISE_SPEED_1_VALUE=(int)(msg[5]&0xff);
		mCanInfo.CRUISE_SPEED_2_VALUE=(int)(msg[6]&0xff);
		mCanInfo.CRUISE_SPEED_3_VALUE=(int)(msg[7]&0xff);
		mCanInfo.CRUISE_SPEED_4_VALUE=(int)(msg[8]&0xff);
		mCanInfo.CRUISE_SPEED_5_VALUE=(int)(msg[9]&0xff);
		mCanInfo.CRUISE_SPEED_6_VALUE=(int)(msg[10]&0xff);
		
		mCanInfo.CRUISE_SPEED_STATUS_ENABLE=(int)((msg[13]>>7)&0x01);
		mCanInfo.CRUISE_SPEED_1_ENABLE=(int)((msg[13]>>6)&0x01);
		mCanInfo.CRUISE_SPEED_2_ENABLE=(int)((msg[13]>>5)&0x01);
		mCanInfo.CRUISE_SPEED_3_ENABLE=(int)((msg[13]>>4)&0x01);
		mCanInfo.CRUISE_SPEED_4_ENABLE=(int)((msg[13]>>3)&0x01);
		mCanInfo.CRUISE_SPEED_5_ENABLE=(int)((msg[13]>>2)&0x01);
		mCanInfo.CRUISE_SPEED_6_ENABLE=(int)((msg[13]>>1)&0x01);
	}
	 String RememberSpeedData = "";
	private void analyzeRememberSpeedData(byte[] msg) {
		if (RememberSpeedData.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			RememberSpeedData = BytesUtil.bytesToHexString(msg);
		}
		
		mCanInfo.REMEMBER_SPEED_STATUS=(int)((msg[4]>>7)&0x01);
		mCanInfo.REMEMBER_SPEED_1=(int)((msg[4]>>6)&0x01);
		mCanInfo.REMEMBER_SPEED_2=(int)((msg[4]>>5)&0x01);
		mCanInfo.REMEMBER_SPEED_3=(int)((msg[4]>>4)&0x01);
		mCanInfo.REMEMBER_SPEED_4=(int)((msg[4]>>3)&0x01);
		mCanInfo.REMEMBER_SPEED_5=(int)((msg[4]>>2)&0x01);
		mCanInfo.REMEMBER_SPEED_6=(int)((msg[4]>>1)&0x01);
		
		mCanInfo.REMEMBER_SPEED_1_VALUE=(int)(msg[5]&0xff);
		mCanInfo.REMEMBER_SPEED_2_VALUE=(int)(msg[6]&0xff);
		mCanInfo.REMEMBER_SPEED_3_VALUE=(int)(msg[7]&0xff);
		mCanInfo.REMEMBER_SPEED_4_VALUE=(int)(msg[8]&0xff);
		mCanInfo.REMEMBER_SPEED_5_VALUE=(int)(msg[9]&0xff);
		mCanInfo.REMEMBER_SPEED_6_VALUE=(int)(msg[10]&0xff);
		
		mCanInfo.REMEMBER_SPEED_STATUS_ENABLE=(int)((msg[13]>>7)&0x01);
		mCanInfo.REMEMBER_SPEED_1_ENABLE=(int)((msg[13]>>6)&0x01);
		mCanInfo.REMEMBER_SPEED_2_ENABLE=(int)((msg[13]>>5)&0x01);
		mCanInfo.REMEMBER_SPEED_3_ENABLE=(int)((msg[13]>>4)&0x01);
		mCanInfo.REMEMBER_SPEED_4_ENABLE=(int)((msg[13]>>3)&0x01);
		mCanInfo.REMEMBER_SPEED_5_ENABLE=(int)((msg[13]>>2)&0x01);
		mCanInfo.REMEMBER_SPEED_6_ENABLE=(int)((msg[13]>>1)&0x01);
		
	}

	 String CarSettingData2 = "";
	private void analyzeCarSettingData2(byte[] msg) {
		if (CarSettingData2.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			CarSettingData2 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.TRUNK_UNLOCK_STATUS=(int)((msg[4]>>7)&0x01);
		mCanInfo.CHANGE_ILL_STATUS=(int)((msg[4]>>6)&0x01);
		mCanInfo.CHANGE_LINE_STATUS=(int)((msg[4]>>5)&0x01);
		mCanInfo.WELCOME_FUNTION_STATUS=(int)((msg[4]>>4)&0x01);
		
	}
	 String CarSettingData1 = "";
	private void analyzeCarSettingData1(byte[] msg) {
		if (CarSettingData1.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			CarSettingData1 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.AUTO_PARK_CAR_STATUS=(int)((msg[4]>>7)&0x01);
		mCanInfo.RADAR_ALARM_STATUS=(int)((msg[4]>>6)&0x01);
		mCanInfo.WELCOME_PERSION_ILL_STATUS=(int)((msg[4]>>4)&0x03);
		mCanInfo.ATMOSPHERE_ILL_STATUS=(int)((msg[4]>>3)&0x01);
		mCanInfo.ATMOSPHERE_ILL_VALUE=(int)((msg[4]>>0)&0x07);
		
		mCanInfo.REAR_WIPER_STATUS=(int)((msg[5]>>7)&0x01);
		mCanInfo.PARKING_STATUS=(int)((msg[5]>>6)&0x01);
		mCanInfo.CAR_LOCK_AUTO_STATUS=(int)((msg[5]>>5)&0x01);
		mCanInfo.CAR_LOCK_STATUS=(int)((msg[5]>>4)&0x01);
		mCanInfo.REMOTE_UNLOCK=(int)((msg[5]>>3)&0x01);
		mCanInfo.DAYTIME_LAMP_STATUS=(int)((msg[5]>>2)&0x01);
		mCanInfo.GO_HOME_LAMP_STATUS=(int)((msg[5]>>0)&0x03);
	}
	 String CarEnableData2 = "";
	private void analyzeCarEnableData2(byte[] msg) {
		if (CarEnableData2.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			CarEnableData2 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.TRUNK_UNLOCK_STATUS_ENABLE=(int)((msg[4]>>7)&0x01);
		mCanInfo.CHANGE_ILL_STATUS_ENABLE=(int)((msg[4]>>6)&0x01);
		mCanInfo.CHANGE_LINE_STATUS_ENABLE=(int)((msg[4]>>5)&0x01);
		mCanInfo.WELCOME_FUNTION_STATUS_ENABLE=(int)((msg[4]>>4)&0x01);
		
	}
	 String CarEnableData1 = "";
	private void analyzeCarEnableData1(byte[] msg) {
		if (CarEnableData1.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			CarEnableData1 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.AUTO_PARK_CAR_STATUS_ENABLE=(int)((msg[4]>>7)&0x01);
		mCanInfo.RADAR_ALARM_STATUS_ENABLE=(int)((msg[4]>>6)&0x01);
		mCanInfo.WELCOME_PERSION_ILL_STATUS_ENABLE=(int)((msg[4]>>5)&0x01);
		mCanInfo.ATMOSPHERE_ILL_STATUS_ENABLE=(int)((msg[4]>>3)&0x01);
		
		mCanInfo.REAR_WIPER_STATUS_ENABLE=(int)((msg[5]>>7)&0x01);
		mCanInfo.PARKING_STATUS_ENABLE=(int)((msg[5]>>6)&0x01);
		mCanInfo.CAR_LOCK_AUTO_STATUS_ENABLE=(int)((msg[5]>>5)&0x01);
		mCanInfo.CAR_LOCK_STATUS_ENABLE=(int)((msg[5]>>4)&0x01);
		mCanInfo.CAR_UNLOCK_STATUS_ENABLE=(int)((msg[5]>>3)&0x01);
		mCanInfo.DAYTIME_LAMP_STATUS_ENABLE=(int)((msg[5]>>2)&0x01);
		mCanInfo.GO_HOME_LAMP_STATUS_ENABLE=(int)((msg[5]>>1)&0x01);
		
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
				:(((int) (msg[4] & 0xFF)) == 0x00)?1:(int)(Math.ceil((double)(msg[4] & 0xFF)/2));
		
		mCanInfo.BACK_MIDDLE_LEFT_DISTANCE = (((int) (msg[5] & 0xFF)) == 0xff) ? 0
				:(((int) (msg[5] & 0xFF)) == 0x00)?1:(int)(Math.ceil((double)(msg[5] & 0xFF)/2));
		
		mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE = (((int) (msg[6] & 0xFF)) == 0xff) ? 0
				:(((int) (msg[6] & 0xFF)) == 0x00)?1:(int)(Math.ceil((double)(msg[6] & 0xFF)/2));
		
		mCanInfo.BACK_RIGHT_DISTANCE = (((int) (msg[7] & 0xFF)) == 0xff) ? 0
				:(((int) (msg[7] & 0xFF)) == 0x00)?1:(int)(Math.ceil((double)(msg[7] & 0xFF)/2));
		
		
		mCanInfo.FRONT_LEFT_DISTANCE = (((int) (msg[8] & 0xFF)) == 0xff) ? 0
				:(((int) (msg[8] & 0xFF)) == 0x00)?1:(int)(Math.ceil((double)(msg[8] & 0xFF)/2));
		mCanInfo.FRONT_MIDDLE_LEFT_DISTANCE = (((int) (msg[9] & 0xFF)) == 0xff) ? 0
				:(((int) (msg[9] & 0xFF)) == 0x00)?1:(int)(Math.ceil((double)(msg[9] & 0xFF)/2));
		mCanInfo.FRONT_MIDDLE_RIGHT_DISTANCE = (((int) (msg[10] & 0xFF)) == 0xff) ? 0
				:(((int) (msg[10] & 0xFF)) == 0x00)?1:(int)(Math.ceil((double)(msg[10] & 0xFF)/2));
		mCanInfo.FRONT_RIGHT_DISTANCE = (((int) (msg[11] & 0xFF)) == 0xff) ? 0
				:(((int) (msg[11] & 0xFF)) == 0x00)?1:(int)(Math.ceil((double)(msg[11] & 0xFF)/2));
//		Log.i("xxx", "mCanInfo.BACK_LEFT_DISTANCE=="+mCanInfo.BACK_LEFT_DISTANCE);
//		Log.i("xxx", "mCanInfo.BACK_MIDDLE_LEFT_DISTANCE=="+mCanInfo.BACK_MIDDLE_LEFT_DISTANCE);
//		Log.i("xxx", "mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE=="+mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE);
//		Log.i("xxx", "mCanInfo.BACK_RIGHT_DISTANCE=="+mCanInfo.BACK_RIGHT_DISTANCE);
//		Log.i("xxx", "mCanInfo.DISTANCE=="+mCanInfo.FRONT_LEFT_DISTANCE);
//		Log.i("xxx", "mCanInfo.DISTANCE=="+mCanInfo.FRONT_MIDDLE_LEFT_DISTANCE);
//		Log.i("xxx", "mCanInfo.DISTANCE=="+mCanInfo.FRONT_MIDDLE_RIGHT_DISTANCE);
//		Log.i("xxx", "mCanInfo.DISTANCE=="+mCanInfo.FRONT_RIGHT_DISTANCE);

		//mCanInfo.RADAR_ALARM_STATUS = (int) (msg[14] & 0xFF);

	}

	 String carInfoSave_1 = "";

	void analyzeCarInfoData_1(byte[] msg) {
		if (carInfoSave_1.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_1 = BytesUtil.bytesToHexString(msg);
		}

		// 报警 CHANGE_STATUS=10

		mCanInfo.TRUNK_STATUS = (int) ((msg[6] >> 3) & 0x01);
		mCanInfo.RIGHT_BACKDOOR_STATUS = (int) ((msg[6] >> 4) & 0x01);
		mCanInfo.LEFT_BACKDOOR_STATUS = (int) ((msg[6] >> 5) & 0x01);
		mCanInfo.RIGHT_FORONTDOOR_STATUS = (int) ((msg[6] >> 6) & 0x01);
		mCanInfo.LEFT_FORONTDOOR_STATUS = (int) ((msg[6] >> 7) & 0x01);

		mCanInfo.SAFETY_BELT_STATUS = (int) ((msg[6] >> 2) & 0x01)==1?0:1;
		mCanInfo.DEPUTY_SAFETY_BELT_STATUS = (int) ((msg[11] >> 7) & 0x01);
		mCanInfo.BACK_LEFT_SAFETY_BELT_STATUS = (int) ((msg[11] >> 6) & 0x01);
		mCanInfo.BACK_MIDDLE_SAFETY_BELT_STATUS = (int) ((msg[11] >> 5) & 0x01);
		mCanInfo.BACK_RIGHT_SAFETY_BELT_STATUS = (int) ((msg[11] >> 4) & 0x01);
	}

	 String carInfoSave_2 = "";

	void analyzeCarInfoData_2(byte[] msg) {
		if (carInfoSave_2.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_2 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.INSTANT_CONSUMPTION =  (float) (0.1*(((int) msg[4] & 0xFF) * 256
				+ ((int) msg[5] & 0xFF)));
		mCanInfo.RANGE = ((int) msg[6] & 0xFF) * 256
				+ ((int) msg[7] & 0xFF);
	}

	 String carInfoSave_3 = "";

	void analyzeCarInfoData_3(byte[] msg) {
		if (carInfoSave_3.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_3 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.AVERAGE_CONSUMPTION =  (float) (0.1*(((int) msg[4] & 0xFF) * 256
				+ ((int) msg[5] & 0xFF)));
		mCanInfo.AVERAGE_SPEED=(int) msg[7] & 0xFF;
		mCanInfo.RANGE_ADD = ((int) msg[8] & 0xFF) * 256
				+ ((int) msg[9] & 0xFF);

	}

	 String carInfoSave_4 = "";

	void analyzeCarInfoData_4(byte[] msg) {
		if (carInfoSave_4.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_4 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.AVERAGE_CONSUMPTION =  (float) (0.1*(((int) msg[4] & 0xFF) * 256
				+ ((int) msg[5] & 0xFF)));
		mCanInfo.AVERAGE_SPEED=(int) msg[7] & 0xFF;
		mCanInfo.RANGE_ADD = ((int) msg[8] & 0xFF) * 256
				+ ((int) msg[9] & 0xFF);
//		int len = ((int) msg[2] & 0xFF);
//		byte[] acscii = new byte[len];
//		for (int i = 0; i < len; i++) {
//			acscii[i] = msg[i + 4];
//
//		}
//		try {
//			mCanInfo.VERSION = new String(acscii, "GBK");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	 String carInfoSave = "";
	 int buttonTemp = 0;
	/*
	 * 方向盘按键 STEERING_BUTTON_MODE 0：无按键或释放 1：vol+ 2：vol- 3：menuup 4：menu down 5：
	 * PHONE 6：mute 7：SRC 8：SPEECH/MIC 9:answer phone 10:hangup phone
	 */
	 int keyCode[] = { 0, 1, 2, 8, 9, -1, 3, 4, 4, 5, 6 };

	void analyzeCarInfoData(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.RADAR_ALARM_STATUS =(int)((msg[4]>>5)&0x01);
		mCanInfo.KEY_IN= (int)((msg[4]>>4)&0x01);
		mCanInfo.HANDBRAKE_STATUS= (int)((msg[4]>>3)&0x01);
		mCanInfo.CAR_BACK_STATUS=(int)((msg[4]>>2)&0x01);
		mCanInfo.CAR_ILL_STATUS=(int)((msg[4]>>1)&0x01);
		mCanInfo.CAR_ACC_STATUS=(int)((msg[4]>>0)&0x01);
		mCanInfo.BACK_LIGHT_DATA=(int)((msg[9]>>0)&0xff);
		
		// 方向盘转角 CHANGE_STATUS=8
		int temp = (int) (msg[10] & 0xFF)*256+(int) (msg[11] & 0xFF);
		if (temp > 0xffff/2) {
				mCanInfo.STERRING_WHELL_STATUS = 65535-temp;
				mCanInfo.CHANGE_STATUS = 8;
		} else{
				mCanInfo.STERRING_WHELL_STATUS =  -temp;
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
			case 0x03:
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MUTE;
				break;
			case 0x05:
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.ANSWER;
				break;
			case 0x08:
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUDOWN;
				break;
			case 0x09:
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUUP;
				break;
			case 0x0A:
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.HOME;
				break;
			case 0x0B:
				break;
			case 0x0D:
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUUP;
				break;
			case 0x0E:
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUDOWN;
				break;
			case 0x0F://ok
				break;
			case 0x10://ESC
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.BACK;
				break;
			case 0x11://Memo up
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUUP;
				break;
			case 0x12://Memo down
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUDOWN;
				break;
			case 0x13://List
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MUSIC;
				break;
			case 0x14://Wiper Button
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.Wiper;
				break;
			case 0x15://Check 自检键
				break;
			case 0x16://行车电脑页面切换键
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.HOME;
				break;
			case 0x40://BT wipper Button 一键蓝牙
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.PHONE_APP;
				break;
			default:
				mCanInfo.STEERING_BUTTON_MODE = 0;
				break;
			}
			mCanInfo.CHANGE_STATUS = 2;
		}
//		if ((int) ((msg[12] >> 7) & 0x01) == 0) {
//			mCanInfo.STEERING_BUTTON_MODE = 16;
//		}
		temp = (int) (msg[7] & 0xFF);
		if (mCanInfo.STEERING_BUTTON_STATUS != temp) {
			mCanInfo.STEERING_BUTTON_STATUS = temp;
			mCanInfo.CHANGE_STATUS = 2;
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

		mCanInfo.AIR_CONDITIONER_STATUS = ((int) (msg[4] >> 6) & 0x01);
		mCanInfo.AC_MAX_STATUS = ((int) (msg[4] >> 5) & 0x01);
		mCanInfo.AUTO_STATUS = (int) ((msg[4] >> 3) & 0x01);
		mCanInfo.AC_INDICATOR_STATUS = (int) ((msg[4] >> 0) & 0x01);
		mCanInfo.Mono_STATUS=(int) ((msg[4] >> 2) & 0x01);
		mCanInfo.CYCLE_INDICATOR = (int) ((msg[5] >> 4) & 0x01);;
		mCanInfo.REAR_LAMP_INDICATOR = (int) ((msg[6] >> 5) & 0x01);
		mCanInfo.MAX_FRONT_LAMP_INDICATOR = (int) ((msg[6] >> 4) & 0x01);

		mCanInfo.AIR_STRENGTH=(int) ((msg[7] >> 0) & 0x0f);
		int temp = (int) (msg[8] & 0x0f);
		if(temp==0x0b||temp==0x0c||temp==0x0d||temp==0x0e){
			mCanInfo.UPWARD_AIR_INDICATOR=1;
		}else{
			mCanInfo.UPWARD_AIR_INDICATOR=0;
		}
		if(temp==0x05||temp==0x06||temp==0x0d||temp==0x0e){
			mCanInfo.PARALLEL_AIR_INDICATOR=1;
		}else{
			mCanInfo.PARALLEL_AIR_INDICATOR=0;
		}
		if(temp==0x03||temp==0x05||temp==0x0c||temp==0x0e){
			mCanInfo.DOWNWARD_AIR_INDICATOR=1;
		}else{
			mCanInfo.DOWNWARD_AIR_INDICATOR=0;
		}
		mCanInfo.AIR_RATE=(int) (msg[9] & 0x0f);
		
		temp=(int) (msg[10] & 0xff);
		float temp2= temp == 0xFE ? 0 : temp == 0xFF ? 255
				: (temp * 0.5f );
		if(temp2>20&&temp2<26){
			mCanInfo.DRIVING_POSITON_TEMP=temp2;
		}else{
			mCanInfo.DRIVING_POSITON_TEMP=(float) Math.floor(temp2);
		}
		temp=(int) (msg[11] & 0xff);
		 temp2= temp == 0xFE ? 0 : temp == 0xFF ? 255
				: (temp * 0.5f );
		if(temp2>20&&temp2<26){
			mCanInfo.DEPUTY_DRIVING_POSITON_TEMP=temp2;
		}else{
			mCanInfo.DEPUTY_DRIVING_POSITON_TEMP=(float) Math.floor(temp2);
		}
		temp=(int) (msg[15] & 0xff);
		mCanInfo.OUTSIDE_TEMPERATURE=(float) (temp*0.5-40);

	}

}
