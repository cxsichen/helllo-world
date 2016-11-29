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
	// 面板旋钮
	public static final int KNOB_BUTTON = 0x22;
	// 空调信息
	public static final int AIR_CONDITIONER_DATA = 0x31;
	// 雷达信息
	public static final int RADAR_DATA = 0x41;
	// 车身信息
	public static final int CAR_INFO_DATA_2 = 0x65;
	// 车身基本信息
	public static final int CAR_INFO_DATA_3 = 0xF0;
	// 车身基本信息
	public static final int CAR_INFO_DATA_4 = 0x66;
	// 车身基本信息
	public static final int CAR_INFO_DATA_5 = 0x67;
	// 车身基本信息
	public static final int CAR_INFO_DATA_6 = 0x70;
	// 车身基本信息
	public static final int CAR_INFO_DATA_7 = 0x32;
	// 车身基本信息
	public static final int CAR_INFO_DATA_8 = 0x33;
	// 车身基本信息
	public static final int CAR_INFO_DATA_9 = 0x34;
	// 车身基本信息
	public static final int CAR_INFO_DATA_10 = 0x36;
	// 车身基本信息
	public static final int CAR_INFO_DATA_11 = 0x39;
	// 车身基本信息
	public static final int CAR_INFO_DATA_12 = 0x77;

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
			case AIR_CONDITIONER_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeAirConditionData(msg);
				break;
			case RADAR_DATA:
				mCanInfo.CHANGE_STATUS = 4;
				analyzeRadarData(msg);
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
				mCanInfo.CHANGE_STATUS = 13;
				analyzeCarInfoData_11(msg);
				break;
			case CAR_INFO_DATA_12:
				mCanInfo.CHANGE_STATUS = 13;
			//	analyzeCarInfoData_12(msg);
				break;
			/*
			 * case AIR_CONDITIONER_DATA: mCanInfo.CHANGE_STATUS = 3;
			 * analyzeAirConditionData(msg); break; case CAR_INFO_DATA:
			 * mCanInfo.CHANGE_STATUS = 10; analyzeCarInfoData(msg); break; case
			 * CAR_BASIC_INFO_DATA: analyzeCarBasicInfoData(msg); break; case
			 * RADAR_DATA: mCanInfo.CHANGE_STATUS = 4; analyzeRadarData(msg);
			 * break; case CAR_INFO_DATA_3: mCanInfo.CHANGE_STATUS = 10;
			 * analyzeCarInfoData_3(msg); break;
			 */
			default:
				break;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	static String[] strGroup={"发动机故障","发动机排放故障"
		,"安全气囊警告指示灯","安全带未系警告灯亮","发动机冷却液温度灯  高温"
		,"发动机冷却液温度灯  高温闪","EPB系统故障警告灯亮"};
	static String carInfoSave_12 = "";

	void analyzeCarInfoData_12(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_12.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_12 = BytesUtil.bytesToHexString(msg);
		}

		try {
			mCanInfo.WARING_MSG=strGroup[((int) (msg[5]>>0) & 0xff)];
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static String carInfoSave_11 = "";

	void analyzeCarInfoData_11(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_11.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_11 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.WARING_MSG_STATUS=((int) (msg[4]>>0) & 0x03);
		int len = ((int) msg[2] & 0xFF)-1;
		byte[] unicode = new byte[len];
		
		for (int i = 0; i < len; i++) {
			unicode[i] = msg[i + 5];
		}
		try {
			mCanInfo.WARING_MSG = getStrFromUniCode(BytesUtil.bytesToHexString(unicode));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	 String getStrFromUniCode(String unicode){
	        String str = "";
	        for(int i=0;i<unicode.length();i+=4){
	            String s = "";
	            for(int j=i;j<i+4;j++){
	                s+=String.valueOf(unicode.charAt(j));
	            }
	            str+=String.valueOf((char)Integer.valueOf(s, 16).intValue());
	        }
	        return str;
	    }
	
	static String carInfoSave_10 = "";

	void analyzeCarInfoData_10(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_10.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_10 = BytesUtil.bytesToHexString(msg);
		}
		
		mCanInfo.LOW_BEAM=(int) ((msg[4] >> 7) & 0x01);//近光灯
		mCanInfo.HIGH_BEAM=(int) ((msg[4] >> 6) & 0x01);//远光灯
		mCanInfo.CLEARANCE_LAMP=(int) ((msg[4] >> 5) & 0x01);//示宽灯 
		mCanInfo.FRONT_FOG_LAMP=(int) ((msg[4] >> 4) & 0x01);//前雾灯 
		mCanInfo.REAR_FOG_LAMP=(int) ((msg[4] >> 3) & 0x01);//后雾灯 
		mCanInfo.STOP_LAMP=(int) ((msg[4] >> 2) & 0x01);//刹车灯
		mCanInfo.PARKING_LAMP=(int) ((msg[4] >> 1) & 0x01);//倒车灯
		mCanInfo.DAYTIME_RUNNING_LAMP=(int) ((msg[4] >> 0) & 0x01);//日间行车灯
		mCanInfo.RIGHT_TURNING_SIGNAL_LAMP=(int) ((msg[5] >> 7) & 0x01);//右转向灯
		mCanInfo.LEFT_TURNING_SIGNAL_LAMP=(int) ((msg[5] >> 6) & 0x01);//左转向灯
		mCanInfo.DOUBLE_FLASH_LAMP=(int) ((msg[5] >> 3) & 0x01);//双闪灯
		


	}

	static String carInfoSave_9 = "";

	void analyzeCarInfoData_9(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_9.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_9 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.DISTANCE_LONG_TERM = ((int) ((msg[8]) & 0xFF)*256*256
				+(int) ((msg[9]) & 0xFF)*256+(int) ((msg[10]) & 0xFF))*0.1f;
		mCanInfo.TRIP_OIL_CONSUMPTION_0 = ((int) ((msg[11]) & 0xFF)*256
				+(int) ((msg[12]) & 0xFF))*0.1f;
		mCanInfo.TRIP_OIL_CONSUMPTION_1 = ((int) ((msg[16]) & 0xFF)*256
				+(int) ((msg[17]) & 0xFF))*0.1f;
		mCanInfo.TRIP_OIL_CONSUMPTION_2 = ((int) ((msg[21]) & 0xFF)*256
				+(int) ((msg[22]) & 0xFF))*0.1f;
		
		mCanInfo.RANGE_UNIT= (int) ((msg[26] >> 2) & 0x01) ;
		mCanInfo.AVERAGE_CONSUMPTION_UNIT= (int) ((msg[26] >> 0) & 0x03) ;

	}
	
	static String carInfoSave_8 = "";

	void analyzeCarInfoData_8(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_8.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_8 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.FRONT_LEFT_DOORLOCK = (int) ((msg[7] >> 7) & 0x01) ;
		mCanInfo.FRONT_RIGHT_DOORLOCK = (int) ((msg[7] >> 6) & 0x01) ;
		mCanInfo.BACK_LEFT_DOORLOCK = (int) ((msg[7] >> 5) & 0x01) ;
		mCanInfo.BACK_RIGHT_DOORLOCK = (int) ((msg[7] >> 4) & 0x01) ;
	}
	
	static String carInfoSave_7 = "";

	void analyzeCarInfoData_7(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_7.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_7 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.ENGINE_SPEED = (int) (msg[6] & 0xFF) * 256
				+ (int) (msg[7] & 0xFF);
		mCanInfo.DRIVING_SPEED= (int) (msg[8] & 0xFF) * 256
				+ (int) (msg[9] & 0xFF);
		mCanInfo.BATTERY_VOLTAGE= (int) (msg[10] & 0xFF)*0.1f;
		mCanInfo.REMAIN_FUEL= (int) (msg[12] & 0xFF);
		mCanInfo.CAR_GEAR_STATUS= (int) (msg[5] & 0xFF);
		mCanInfo.THROTTLE_CONTROL= (int) (msg[11] & 0xFF);
	}
	
	static String carInfoSave_6 = "";

	void analyzeCarInfoData_6(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_6.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_6 = BytesUtil.bytesToHexString(msg);
		}

		if (((int) (msg[4] >> 7) & 0x01) == 0) {
			mCanInfo.LIGHT_COMING_HOME_TIME = -1;
		} else {
			mCanInfo.LIGHT_COMING_HOME_TIME = ((int) (msg[5] >> 4) & 0x0F);
		}
		
		if (((int) (msg[4] >> 6) & 0x01) == 0) {
			mCanInfo.LIGHT_SEEK_CAR_TIME = -1;
		} else {
			mCanInfo.LIGHT_SEEK_CAR_TIME = ((int) (msg[5] >> 0) & 0x0F);
		}

	}

	static String carInfoSave_5 = "";

	void analyzeCarInfoData_5(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_5.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_5 = BytesUtil.bytesToHexString(msg);
		}

		if (((int) (msg[4] >> 5) & 0x01) == 0) {
			mCanInfo.LIGHT_COMING_HOME_BACKUP = -1;
		} else {
			mCanInfo.LIGHT_COMING_HOME_BACKUP = ((int) (msg[5] >> 5) & 0x01);
		}

		if (((int) (msg[4] >> 4) & 0x01) == 0) {
			mCanInfo.LIGHT_COMING_HOME_DIPPED = -1;
		} else {
			mCanInfo.LIGHT_COMING_HOME_DIPPED = ((int) (msg[5] >> 4) & 0x01);
		}

		if (((int) (msg[4] >> 3) & 0x01) == 0) {
			mCanInfo.LIGHT_COMING_HOME_REARFOG = -1;
		} else {
			mCanInfo.LIGHT_COMING_HOME_REARFOG = ((int) (msg[5] >> 3) & 0x01);
		}

		if (((int) (msg[4] >> 2) & 0x01) == 0) {
			mCanInfo.LIGHT_SEEK_CAR_BACKUP = -1;
		} else {
			mCanInfo.LIGHT_SEEK_CAR_BACKUP = ((int) (msg[5] >> 2) & 0x01);
		}

		if (((int) (msg[4] >> 1) & 0x01) == 0) {
			mCanInfo.LIGHT_SEEK_CAR_DIPPED = -1;
		} else {
			mCanInfo.LIGHT_SEEK_CAR_DIPPED = ((int) (msg[5] >> 1) & 0x01);
		}

		if (((int) (msg[4] >> 0) & 0x01) == 0) {
			mCanInfo.LIGHT_SEEK_CAR_REARFOG = -1;
		} else {
			mCanInfo.LIGHT_SEEK_CAR_REARFOG = ((int) (msg[5] >> 0) & 0x01);
		}
	}

	static String carInfoSave_4 = "";

	void analyzeCarInfoData_4(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_4.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_4 = BytesUtil.bytesToHexString(msg);
		}

		if (((int) (msg[4] >> 0) & 0x01) == 0) {
			mCanInfo.AUTO_OPEN_LOCK_S = -1;
		} else {
			mCanInfo.AUTO_OPEN_LOCK_S = ((int) (msg[7] >> 7) & 0x01);
		}
	}

	static String carInfoSave_2 = "";

	void analyzeCarInfoData_2(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_2.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_2 = BytesUtil.bytesToHexString(msg);
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

		if (((int) (msg[4] >> 5) & 0x01) == 0) {
			mCanInfo.AUTO_OPEN_LOCK = -1;
		} else {
			mCanInfo.AUTO_OPEN_LOCK = ((int) (msg[5] >> 2) & 0x01);
		}

	}

	static String KnobButtonDataSave = "";

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

	static String carInfoSave_1 = "";

	void analyzeCarInfoData_1(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_1.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_1 = BytesUtil.bytesToHexString(msg);
		}

		mCanInfo.LEFT_FORONTDOOR_STATUS = ((int) (msg[6] >> 7) & 0x01);
		mCanInfo.RIGHT_FORONTDOOR_STATUS = ((int) (msg[6] >> 6) & 0x01);
		mCanInfo.LEFT_BACKDOOR_STATUS = ((int) (msg[6] >> 5) & 0x01);
		mCanInfo.RIGHT_BACKDOOR_STATUS = ((int) (msg[6] >> 4) & 0x01);
		mCanInfo.TRUNK_STATUS = ((int) (msg[6] >> 3) & 0x01);
		mCanInfo.HOOD_STATUS = ((int) (msg[6] >> 2) & 0x01);
		mCanInfo.SAFETY_BELT_STATUS = ((int) (msg[6] >> 1) & 0x01)
				| ((int) (msg[6] >> 0) & 0x01);
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
		if (temp > 32767) {
			temp = 0xFFFF - temp;
		} else {
			temp = -temp;
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
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.HOME;
				break;
			case 0x40:
				mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.PHONE_APP;
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
			if ((int) (msg[4 + i] & 0xff) == 0xff) {
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
		mCanInfo.AC_INDICATOR_STATUS = (int) ((msg[4] >> 0) & 0x01);
		mCanInfo.CYCLE_INDICATOR = (int) ((msg[5] >> 4) & 0x01)==1?0:1;
		mCanInfo.AUTO_STATUS = (int) ((msg[5] >> 3) & 0x01);
		mCanInfo.REAR_LAMP_INDICATOR = (int) ((msg[6] >> 5) & 0x01);
		mCanInfo.MAX_FRONT_LAMP_INDICATOR = (int) ((msg[6] >> 4) & 0x01);

		int temp = (int) (msg[8] & 0xff);
		if (temp == 0x02 || temp == 0x0B || temp == 0x0C || temp == 0x0D
				|| temp == 0x0E) {
			mCanInfo.UPWARD_AIR_INDICATOR = 1;
		} else {
			mCanInfo.UPWARD_AIR_INDICATOR = 0;
		}

		if (temp == 0x05 || temp == 0x06 || temp == 0x0D || temp == 0x0E) {
			mCanInfo.PARALLEL_AIR_INDICATOR = 1;
		} else {
			mCanInfo.PARALLEL_AIR_INDICATOR = 0;
		}

		if (temp == 0x03 || temp == 0x05 || temp == 0x0C || temp == 0x0E) {
			mCanInfo.DOWNWARD_AIR_INDICATOR = 1;
		} else {
			mCanInfo.DOWNWARD_AIR_INDICATOR = 0;
		}

		mCanInfo.AIR_RATE = (int) (msg[9] & 0x0f);

		temp = (int) (msg[10] & 0xff);
		mCanInfo.DRIVING_POSITON_TEMP = temp == 0xFE ? 0 : temp == 0xFF ? 255
				: (temp * 0.5f);
		mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = mCanInfo.DRIVING_POSITON_TEMP;
		mCanInfo.OUTSIDE_TEMPERATURE = ((msg[15]) & 0xff) * 0.5f - 40;

	}

}
