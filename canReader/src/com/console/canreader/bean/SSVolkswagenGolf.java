package com.console.canreader.bean;

import java.io.UnsupportedEncodingException;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;
import com.console.canreader.utils.Contacts;

import android.util.Log;

public class SSVolkswagenGolf extends AnalyzeUtils {
	// ��������
	public static final int comID = 3;
	// DataType
	// �����̰���
	public static final int STEERING_BUTTON_DATA = 0x11;
	// ������Ϣ
	public static final int CAR_INFO_DATA = 0x12;
	// ������Ϣ
	public static final int CAR_INFO_DATA_2 = 0x19;	
	// ������Ϣ
	public static final int CAR_INFO_DATA_1 = 0x13;
	// ������Ϣ
	public static final int CAR_INFO_DATA_4 = 0x14;
	// ������Ϣ
	public static final int CAR_INFO_DATA_5 = 0x15;
	// ������Ϣ
    public static final int CAR_INFO_DATA_6 = 0x16;
 // ������Ϣ
    public static final int CAR_INFO_DATA_7 = 0x1F;
	
	// �յ���Ϣ
	public static final int AIR_CONDITIONER_DATA = 0x31;
	
	// ���������Ϣ
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
			case STEERING_BUTTON_DATA:
				mCanInfo.CHANGE_STATUS = 2;
				analyzeSteeringButtonData(msg);
				break;
			case CAR_INFO_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData(msg);
				break;
			case CAR_INFO_DATA_2:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_2(msg);
				break;
			case CAR_INFO_DATA_1:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_1(msg);
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
				
				
			
			case AIR_CONDITIONER_DATA:
				mCanInfo.CHANGE_STATUS = 3;
				analyzeAirConditionData(msg);
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
	
	static String carInfoSave_7 = "";

	void analyzeCarInfoData_7(byte[] msg) {
		// TODO Auto-generated method stub
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
	
	
	static String carInfoSave_6 = "";

	void analyzeCarInfoData_6(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_6.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_6 = BytesUtil.bytesToHexString(msg);
		}		

		mCanInfo.CONVENIENCE_CONSUMERS = (int) (msg[5] & 0xff); 
		mCanInfo.CONVENIENCE_CONSUMERS_UNIT = (int) (msg[13] & 0xff); 

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
		mCanInfo.ENGINE_SPEED = (int) ((msg[12]) & 0x0FF)*256+(int) ((msg[13]<< 0) & 0x0FF);		
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

		mCanInfo.CONSUMPTION_SINCE_REFUELING = (int) (msg[4] & 0xff)*256+(int) (msg[5] & 0xff); // �Լ���ƽ���ͺ�
		mCanInfo.RANGE_SINCE_REFUELINGT = (int) (msg[6] & 0xff)*256+(int) (msg[7] & 0xff); // �Լ����������	
		mCanInfo.DISTANCE_SINCE_REFUELING = (int) (msg[8] & 0xff)*256+(int) (msg[9] & 0xff); //  �Լ�����ʻ���
		mCanInfo.TRAVELLINGTIME_SINCE_REFUELINGT = (int) (msg[10] & 0xff)*256+(int) (msg[11] & 0xff); //�Լ�����ʻʱ��
		mCanInfo.SPEED_SINCE_REFUELINGT = (int) (msg[12] & 0xff); // �Լ���ƽ������
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
		

		mCanInfo.CONSUMPTION_LONG_TERM = (int) (msg[4] & 0xff)*256+(int) (msg[5] & 0xff); // ��ʱ��ƽ���ͺ�
		mCanInfo.RANGE_LONG_TERM = (int) (msg[6] & 0xff)*256+(int) (msg[7] & 0xff); //��ʱ���������	
		mCanInfo.DISTANCE_LONG_TERM = (int) (msg[8] & 0xff)*256+(int) (msg[9] & 0xff); // ��ʱ����ʻ���
		mCanInfo.TRAVELLINGTIME_LONG_TERM = (int) (msg[10] & 0xff)*256+(int) (msg[11] & 0xff); //��ʱ����ʻʱ��
		mCanInfo.SPEED_LONG_TERM = (int) (msg[12] & 0xff); //��ʱ��ƽ������
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
		
		mCanInfo.CONSUMPTION_SINCE_START = (int) (msg[4] & 0xff)*256+(int) (msg[5] & 0xff); // ������ƽ���ͺ�
		mCanInfo.RANGE_SINCE_START = (int) (msg[6] & 0xff)*256+(int) (msg[7] & 0xff); // �������������	
		mCanInfo.DISTANCE_SINCE_START = (int) (msg[8] & 0xff)*256+(int) (msg[9] & 0xff); // ��������ʻ���
		mCanInfo.TRAVELLINGTIME_SINCE_START = (int) (msg[10] & 0xff)*256+(int) (msg[11] & 0xff); // ��������ʻʱ��
		mCanInfo.SPEED_SINCE_START = (int) (msg[12] & 0xff); // ������ƽ������
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
		
		mCanInfo.INSTANT_CONSUMPTION=(int) (msg[7] & 0xff)+(int) (msg[8] & 0xff)*0.01f;
		mCanInfo.BATTERY_WARING_SIGN = (int) ((msg[10] >> 7) & 0x01) ;
		mCanInfo.FUEL_WARING_SIGN = (int) ((msg[10] >> 6) & 0x01) ;
		mCanInfo.SAFETY_BELT_STATUS = (int) ((msg[10] >> 5) & 0x01) ;
		mCanInfo.DISINFECTON_STATUS = (int) ((msg[10] >> 4) & 0x01) ;
		
		mCanInfo.REMAIN_FUEL= (int) ((msg[11]) & 0xff) ;
		mCanInfo.BATTERY_VOLTAGE= (int) ((msg[12]) & 0xff)+(int) ((msg[13]) & 0xff)*0.01f ;
		
	}


	static String SteeringButtonStatusDataSave = "";
	static int steelWheel = 0;
	static int handbrake = 0;
	static float speed = 0;
	void analyzeSteeringButtonData(byte[] msg) {
		// TODO Auto-generated method stub
		if (SteeringButtonStatusDataSave
				.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			SteeringButtonStatusDataSave = BytesUtil.bytesToHexString(msg);
		}
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
		case 0x04:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
			break;
		case 0x05:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.PHONE;
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
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
			break;
		case 0x0C:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SETTING;
			break;
		default:
			mCanInfo.STEERING_BUTTON_MODE = 0;
			break;
		}

		mCanInfo.STEERING_BUTTON_STATUS = (int) (msg[7] & 0xFF);

		int temp = ((int) msg[10] & 0xFF) << 8 | ((int) msg[11] & 0xFF);
		if (temp < 32767) {
			mCanInfo.STERRING_WHELL_STATUS = -temp;
		} else {
			mCanInfo.STERRING_WHELL_STATUS = 65536 - temp;
		}
		if (steelWheel != temp) {
			steelWheel = temp;
			mCanInfo.CHANGE_STATUS = 8;
		}

		mCanInfo.HANDBRAKE_STATUS = (int) ((msg[4] >> 3) & 0x01);
		if (handbrake != mCanInfo.HANDBRAKE_STATUS) {
			handbrake = mCanInfo.HANDBRAKE_STATUS;
			mCanInfo.CHANGE_STATUS = 10;
		}
		mCanInfo.DRIVING_SPEED= (int) (msg[5] & 0xff);
		if(speed!= mCanInfo.DRIVING_SPEED){
			speed = mCanInfo.DRIVING_SPEED;
			mCanInfo.CHANGE_STATUS = 10;
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
	 * �����̰��� STEERING_BUTTON_MODE 0���ް������ͷ� 1��vol+ 2��vol- 3��menuup 4��menu down 5��
	 * PHONE 6��mute 7��SRC 8��SPEECH/MIC 9:answer phone 10:hangup phone
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
			if (temp == 8) {
				temp = 21;
			}
			mCanInfo.STEERING_BUTTON_MODE = temp;
			mCanInfo.CHANGE_STATUS = 2;
		}

		temp = (int) (msg[7] & 0xFF);
		if (mCanInfo.STEERING_BUTTON_STATUS != temp) {
			mCanInfo.STEERING_BUTTON_STATUS = temp;
			mCanInfo.CHANGE_STATUS = 2;
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
		mCanInfo.REAR_LAMP_INDICATOR= (int) ((msg[6] >> 5) & 0x01);
		
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
		mCanInfo.DEPUTY_DRIVING_POSITON_TEMP=mCanInfo.DRIVING_POSITON_TEMP;
		mCanInfo.OUTSIDE_TEMPERATURE= ((int) (msg[15] & 0xff))*0.5f-40;
	}

}
