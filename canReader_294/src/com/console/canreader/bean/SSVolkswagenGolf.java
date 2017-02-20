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
	// ���������Ϣ
	public static final int CAR_INFO_DATA_3 = 0x1E;
	// ������Ϣ
	public static final int CAR_INFO_DATA_8 = 0xF0;
	// �յ���Ϣ
	public static final int AIR_CONDITIONER_DATA = 0x31;
	// �״���Ϣ
	public final static int BACK_RADER_DATA = 0x41;
	// ������Ϣ
	public static final int CAR_INFO_DATA_9 = 0x45;
	// ������Ϣ
	public static final int CAR_INFO_DATA_10 = 0x46;
	// ������Ϣ
	public static final int CAR_INFO_DATA_11 = 0x47;
	// ������Ϣ
	public static final int CAR_INFO_DATA_12 = 0x67;
	// ������Ϣ
	public static final int CAR_INFO_DATA_13 = 0x68;
	// ������Ϣ
	public static final int CAR_INFO_DATA_14 = 0x69;
	// ������Ϣ
	public static final int CAR_INFO_DATA_15 = 0x64;
	// ������Ϣ
	public static final int CAR_INFO_DATA_16 = 0x76;
	// ������Ϣ
	public static final int CAR_INFO_DATA_17 = 0x85;
	// ������Ϣ
	public static final int CAR_INFO_DATA_18 = 0x86;
	// ������Ϣ
    public static final int CAR_INFO_DATA_19 = 0xC1;
 // ������Ϣ
    public static final int CAR_INFO_DATA_20= 0x36;

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
			case CAR_INFO_DATA_3:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_3(msg);
				break;
			case CAR_INFO_DATA_8:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_8(msg);
				break;
			case AIR_CONDITIONER_DATA:
				mCanInfo.CHANGE_STATUS = 3;
				analyzeAirConditionData(msg);
				break;
			case BACK_RADER_DATA:
				mCanInfo.CHANGE_STATUS = 11;
				analyzeBackRaderData(msg);
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
			default:
				break;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	String carInfoSave_20 = "";

	void analyzeCarInfoData_20(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_20.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_20 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.LIGHT_RIHGTTURN_LAMP_SETTING=(int) ((msg[5] >> 0) & 0x01);//��ת���
		mCanInfo.LIGHT_LEFTTURN_LAMP_SETTING=(int) ((msg[5] >> 1) & 0x01); //��ת���	
	}
	
	String carInfoSave_19 = "";

	void analyzeCarInfoData_19(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_19.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_19 = BytesUtil.bytesToHexString(msg);
		}

		if (((int) ((msg[4] >> 7) & 0x01)) == 0) {
			mCanInfo.UNIT_DISTANCE= -1; //��� 
		}else{
			mCanInfo.UNIT_DISTANCE= (int) ((msg[5] >> 7) & 0x01); //��� 
		}
		
		if (((int) ((msg[4] >> 6) & 0x01)) == 0) {
			mCanInfo.UNIT_SPEED= -1; //����
		}else{
			mCanInfo.UNIT_SPEED= (int) ((msg[5] >> 6) & 0x01); //����
		}
		
		if (((int) ((msg[4] >> 5) & 0x01)) == 0) {
			mCanInfo.UNIT_TEMPERATURE= -1; //�¶�
		}else{
			mCanInfo.UNIT_TEMPERATURE= (int) ((msg[5] >> 5) & 0x01); //�¶�
		}
		
		if (((int) ((msg[4] >> 4) & 0x01)) == 0) {
			mCanInfo.UNIT_VOLUME= -1; //�ݻ�
		}else{
			mCanInfo.UNIT_VOLUME= (int) ((msg[5] >> 3) & 0x03); //�ݻ�
		}
		
		if (((int) ((msg[4] >> 3) & 0x01)) == 0) {
			mCanInfo.UNIT_CONSUMPTION= -1; //�ͺ�
		}else{
			mCanInfo.UNIT_CONSUMPTION= (int) ((msg[5] >> 1) & 0x03); //�ͺ�
		}
		
		if (((int) ((msg[4] >> 2) & 0x01)) == 0) {
			mCanInfo.UNIT_PRESSURE= -1; //��̥ѹ��
		}else{
			mCanInfo.UNIT_PRESSURE= (int) ((msg[6] >> 6) & 0x03); //��̥ѹ��
		}
	}


	String carInfoSave_18 = "";

	void analyzeCarInfoData_18(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_18.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_18 = BytesUtil.bytesToHexString(msg);
		}

		if (((int) ((msg[4] >> 7) & 0x01)) == 0) {
			mCanInfo.DRIVING_MODE_COMFORT = -1; // ����
		} else {
			mCanInfo.DRIVING_MODE_COMFORT = ((int) ((msg[5] >> 7) & 0x01));
		}

		if (((int) ((msg[4] >> 6) & 0x01)) == 0) {
			mCanInfo.DRIVING_MODE_NORMAL = -1; // ��׼
		} else {
			mCanInfo.DRIVING_MODE_NORMAL = ((int) ((msg[5] >> 6) & 0x01));
		}

		if (((int) ((msg[4] >> 5) & 0x01)) == 0) {
			mCanInfo.DRIVING_MODE_SPORT = -1; // �˶�
		} else {
			mCanInfo.DRIVING_MODE_SPORT = ((int) ((msg[5] >> 5) & 0x01));
		}

		if (((int) ((msg[4] >> 4) & 0x01)) == 0) {
			mCanInfo.DRIVING_MODE_ECO = -1; // ����
		} else {
			mCanInfo.DRIVING_MODE_ECO = ((int) ((msg[5] >> 4) & 0x01));
		}

		if (((int) ((msg[4] >> 3) & 0x01)) == 0) {
			mCanInfo.DRIVING_MODE_INDIVDUAL = -1; // ���Ի�
		} else {
			mCanInfo.DRIVING_MODE_INDIVDUAL = ((int) ((msg[5] >> 3) & 0x01));
		}

		mCanInfo.DRIVING_MODE = mCanInfo.DRIVING_MODE_COMFORT == 1 ? 1
				: (mCanInfo.DRIVING_MODE_NORMAL == 1)? 2
						: (mCanInfo.DRIVING_MODE_SPORT == 1) ? 3
								: (mCanInfo.DRIVING_MODE_ECO == 1 )? 4
										: (mCanInfo.DRIVING_MODE_INDIVDUAL == 1) ? 5
												: 0;


		if (mCanInfo.DRIVING_MODE_INDIVDUAL != 1) {
			mCanInfo.DRIVING_MODE_INDIVDUAL_DCC = -1; // DCC
			mCanInfo.DRIVING_MODE_INDIVDUAL_DBL = -1; // ��̬����
			mCanInfo.DRIVING_MODE_INDIVDUAL_Engine = -1; // ������
			mCanInfo.DRIVING_MODE_INDIVDUAL_ACC = -1; // ACC
			mCanInfo.DRIVING_MODE_INDIVDUAL_AirCon = -1; // �յ�
			mCanInfo.DRIVING_MODE_INDIVDUAL_Steering = -1; // ������
		} else {
			mCanInfo.DRIVING_MODE_INDIVDUAL_DCC = ((int) ((msg[6] >> 6) & 0x03)); // DCC
			mCanInfo.DRIVING_MODE_INDIVDUAL_DBL = ((int) ((msg[6] >> 4) & 0x03)); // ��̬����
			mCanInfo.DRIVING_MODE_INDIVDUAL_Engine = ((int) ((msg[6] >> 2) & 0x03)); // ������
			mCanInfo.DRIVING_MODE_INDIVDUAL_ACC = ((int) ((msg[6] >> 0) & 0x03)); // ACC
			mCanInfo.DRIVING_MODE_INDIVDUAL_AirCon = ((int) ((msg[7] >> 7) & 0x01)); // �յ�
			mCanInfo.DRIVING_MODE_INDIVDUAL_Steering = ((int) ((msg[7] >> 6) & 0x01)); // ������
		}

	}

	String carInfoSave_17 = "";

	void analyzeCarInfoData_17(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_17.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_17 = BytesUtil.bytesToHexString(msg);
		}

		if (((int) ((msg[4] >> 5) & 0x01)) == 0) {
			mCanInfo.ESC_SYSTEM = -1; // ESCϵͳ
		} else {
			mCanInfo.ESC_SYSTEM = ((int) ((msg[5] >> 5) & 0x03));
		}

	}

	String carInfoSave_16 = "";

	void analyzeCarInfoData_16(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_16.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_16 = BytesUtil.bytesToHexString(msg);
		}

		if (((int) ((msg[4] >> 7) & 0x01)) == 0) {
			mCanInfo.MFD_CURRENT_CONSUMPTION = -1; // ��ǰ�ͺ�
		} else {
			mCanInfo.MFD_CURRENT_CONSUMPTION = ((int) ((msg[6] >> 7) & 0x01));
		}

		if (((int) ((msg[4] >> 6) & 0x01)) == 0) {
			mCanInfo.MFD_AVERAGE_CONSUMPTION = -1; // ƽ���ͺ�
		} else {
			mCanInfo.MFD_AVERAGE_CONSUMPTION = ((int) ((msg[6] >> 6) & 0x01));
		}

		if (((int) ((msg[4] >> 5) & 0x01)) == 0) {
			mCanInfo.MFD_CONVENIENCE_CONSUMERS = -1; // �������õ���
		} else {
			mCanInfo.MFD_CONVENIENCE_CONSUMERS = ((int) ((msg[6] >> 5) & 0x01));
		}

		if (((int) ((msg[4] >> 4) & 0x01)) == 0) {
			mCanInfo.MFD_ECO_TIPS = -1; // ����������ʾ
		} else {
			mCanInfo.MFD_ECO_TIPS = ((int) ((msg[6] >> 4) & 0x01));
		}

		if (((int) ((msg[4] >> 3) & 0x01)) == 0) {
			mCanInfo.MFD_TRAVELLING_TIME = -1; // ��ʻʱ��
		} else {
			mCanInfo.MFD_TRAVELLING_TIME = ((int) ((msg[6] >> 3) & 0x01));
		}

		if (((int) ((msg[4] >> 2) & 0x01)) == 0) {
			mCanInfo.MFD_DISTANCE_TRAVELED = -1; // ��ʻ���
		} else {
			mCanInfo.MFD_DISTANCE_TRAVELED = ((int) ((msg[6] >> 2) & 0x01));
		}

		if (((int) ((msg[4] >> 1) & 0x01)) == 0) {
			mCanInfo.MFD_AVERAGE_SPEED = -1; // ƽ���ٶ�
		} else {
			mCanInfo.MFD_AVERAGE_SPEED = ((int) ((msg[6] >> 1) & 0x01));
		}

		if (((int) ((msg[4] >> 0) & 0x01)) == 0) {
			mCanInfo.MFD_DIGITAL_SPEED_DISPLAY = -1; // ����ʽ������ʾ
		} else {
			mCanInfo.MFD_DIGITAL_SPEED_DISPLAY = ((int) ((msg[6] >> 0) & 0x01));
		}

		if (((int) ((msg[5] >> 7) & 0x01)) == 0) {
			mCanInfo.MFD_SPEED_WARINING = -1; // ���ٱ���
		} else {
			mCanInfo.MFD_SPEED_WARINING = ((int) ((msg[7] >> 7) & 0x01));
		}

		if (((int) ((msg[5] >> 6) & 0x01)) == 0) {
			mCanInfo.MFD_OIL_TEMP = -1; // ����
		} else {
			mCanInfo.MFD_OIL_TEMP = ((int) ((msg[7] >> 6) & 0x01));
		}

	}

	String carInfoSave_15 = "";

	void analyzeCarInfoData_15(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_15.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_15 = BytesUtil.bytesToHexString(msg);
		}

		if (((int) ((msg[4] >> 7) & 0x01)) == 0) {
			mCanInfo.CONV_OPENING = -1; // ������ݿ���
		} else {
			mCanInfo.CONV_OPENING = ((int) ((msg[6] >> 6) & 0x03));
		}

		if (((int) ((msg[5] >> 7) & 0x01)) == 0) {
			mCanInfo.DOOR_UNLOCKING = -1; // �������������Ž�����
		} else {
			mCanInfo.DOOR_UNLOCKING = ((int) ((msg[7] >> 6) & 0x03));
		}

		if (((int) ((msg[5] >> 6) & 0x01)) == 0) {
			mCanInfo.AUTOMATIC_LOCKING = -1; // �Զ���ֹ
		} else {
			mCanInfo.AUTOMATIC_LOCKING = ((int) ((msg[7] >> 5) & 0x01));
		}

		if (((int) ((msg[5] >> 5) & 0x01)) == 0) {
			mCanInfo.SEAT_KEY_REMOTE_FIX = -1; // ����ң��Կ�׼���ƥ��
		} else {
			mCanInfo.SEAT_KEY_REMOTE_FIX = ((int) ((msg[7] >> 4) & 0x01));
		}

		if (((int) ((msg[5] >> 4) & 0x01)) == 0) {
			mCanInfo.INDUCTON_REAR_DOOR_COVER = -1; // ��Ӧʽ��β���
		} else {
			mCanInfo.INDUCTON_REAR_DOOR_COVER = ((int) ((msg[7] >> 3) & 0x01));
		}

	}

	String carInfoSave_14 = "";

	void analyzeCarInfoData_14(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_14.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_14 = BytesUtil.bytesToHexString(msg);
		}

		if (((int) ((msg[4] >> 7) & 0x01)) == 0) {
			mCanInfo.MIRROR_SYNC_ADJUST = -1; // ���Ӿ�ͬ������
		} else {
			mCanInfo.MIRROR_SYNC_ADJUST = ((int) ((msg[6] >> 7) & 0x01));
		}

		if (((int) ((msg[4] >> 6) & 0x01)) == 0) {
			mCanInfo.MIRROR_LOWER_WHILE_REVERSING = -1; // ������ʱ���Ӿ�����
		} else {
			mCanInfo.MIRROR_LOWER_WHILE_REVERSING = ((int) ((msg[6] >> 6) & 0x01));
		}

		if (((int) ((msg[4] >> 5) & 0x01)) == 0) {
			mCanInfo.MIRROR_FOLD_PARKING = -1; // פ��ʱ����
		} else {
			mCanInfo.MIRROR_FOLD_PARKING = ((int) ((msg[6] >> 5) & 0x01));
		}

		if (((int) ((msg[5] >> 7) & 0x01)) == 0) {
			mCanInfo.WIPER_AUTO_IN_RAIN = -1; // �����Զ���ˮ
		} else {
			mCanInfo.WIPER_AUTO_IN_RAIN = ((int) ((msg[7] >> 7) & 0x01));
		}

		if (((int) ((msg[5] >> 6) & 0x01)) == 0) {
			mCanInfo.WIPER_REAR_WIPING_REVERSING = -1; // ������ʱ�󴰲�����ˮ
		} else {
			mCanInfo.WIPER_REAR_WIPING_REVERSING = ((int) ((msg[7] >> 6) & 0x01));
		}
	}

    String carInfoSave_13 = "";

	void analyzeCarInfoData_13(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_13.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_13 = BytesUtil.bytesToHexString(msg);
		}

		if (((int) ((msg[4] >> 7) & 0x01)) == 0) {
			mCanInfo.LIGHT_DYNAMIC_LIGHT_ASSIST = -1; // ��̬�ƹ⸨��
		} else {
			mCanInfo.LIGHT_DYNAMIC_LIGHT_ASSIST = ((int) ((msg[7] >> 7) & 0x01));
		}

		if (((int) ((msg[4] >> 6) & 0x01)) == 0) {
			mCanInfo.LIGHT_MOTORWAY_LIGHT = -1; // ��̬����涯
		} else {
			mCanInfo.LIGHT_MOTORWAY_LIGHT = ((int) ((msg[7] >> 6) & 0x01));
		}

		if (((int) ((msg[4] >> 5) & 0x01)) == 0) {
			mCanInfo.LIGHT_SWITCH_ON_TIME = -1; // ��ͨʱ��
		} else {
			mCanInfo.LIGHT_SWITCH_ON_TIME = ((int) ((msg[7] >> 4) & 0x03));
		}

		if (((int) ((msg[4] >> 4) & 0x01)) == 0) {
			mCanInfo.LIGHT_AUTO_HEADLIGHT_RAIN = -1; // �Զ��г���(����)
		} else {
			mCanInfo.LIGHT_AUTO_HEADLIGHT_RAIN = ((int) ((msg[7] >> 3) & 0x01));
		}

		if (((int) ((msg[4] >> 3) & 0x01)) == 0) {
			mCanInfo.LIGHT_LANE_CHANGE_FLASH = -1; // ���ת���
		} else {
			mCanInfo.LIGHT_LANE_CHANGE_FLASH = ((int) ((msg[7] >> 2) & 0x01));
		}

		if (((int) ((msg[4] >> 2) & 0x01)) == 0) {
			mCanInfo.LIGHT_TRAVELING_MODE = -1; // ����ģʽ
		} else {
			mCanInfo.LIGHT_TRAVELING_MODE = ((int) ((msg[7] >> 1) & 0x01));
		}
		/*-------------------------*/
		if (((int) ((msg[5] >> 7) & 0x01)) == 0) {
			mCanInfo.LIGHT_SWITCH_LIGHTING = -1; // �Ǳ�/��������
		} else {
			mCanInfo.LIGHT_SWITCH_LIGHTING = ((int) ((msg[8] >> 0) & 0xFF));
		}

		if (((int) ((msg[5] >> 6) & 0x01)) == 0) {
			mCanInfo.LIGHT_DOOR_AMBIENT = -1; // ���Ż���������
		} else {
			mCanInfo.LIGHT_DOOR_AMBIENT = ((int) ((msg[9] >> 0) & 0xFF));
		}

		if (((int) ((msg[5] >> 5) & 0x01)) == 0) {
			mCanInfo.LIGHT_FOORWELL_LIGHT = -1; // �Ų��ռ������Ƶ���
		} else {
			mCanInfo.LIGHT_FOORWELL_LIGHT = ((int) ((msg[10] >> 0) & 0xFF));
		}

		if (((int) ((msg[6] >> 7) & 0x01)) == 0) {
			mCanInfo.LIGHT_COMING_HOME = -1; // �ؼ�ģʽ����
		} else {
			mCanInfo.LIGHT_COMING_HOME = ((int) ((msg[11] >> 0) & 0xFF));
		}

		if (((int) ((msg[6] >> 6) & 0x01)) == 0) {
			mCanInfo.LIGHT_LEAVING_HOME = -1; // ���ģʽ����
		} else {
			mCanInfo.LIGHT_LEAVING_HOME = ((int) ((msg[12] >> 0) & 0xFF));
		}

	}

	String carInfoSave_12 = "";

	void analyzeCarInfoData_12(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_12.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_12 = BytesUtil.bytesToHexString(msg);
		}
		if (((int) ((msg[4] >> 7) & 0x01)) == 0) {
			mCanInfo.LIGHT_LIGHT_COLOR = -1; // ����������ɫ
		} else {
			mCanInfo.LIGHT_LIGHT_COLOR = ((int) ((msg[6] >> 0) & 0xFF));
		}

		if (((int) ((msg[4] >> 6) & 0x01)) == 0) {
			mCanInfo.LIGHT_CAR_ENV_COLOR = -1; // ���ڷ�Χ����
		} else {
			mCanInfo.LIGHT_CAR_ENV_COLOR = ((int) ((msg[7] >> 0) & 0xFF));
		}

		if (((int) ((msg[4] >> 5) & 0x01)) == 0) {
			mCanInfo.LIGHT_RIGHT_FRONT_COLOR = -1; // ��ǰ�ҵ�����
		} else {
			mCanInfo.LIGHT_RIGHT_FRONT_COLOR = ((int) ((msg[8] >> 0) & 0xFF));
		}

	}

	String carInfoSave_11 = "";

	void analyzeCarInfoData_11(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_11.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_11 = BytesUtil.bytesToHexString(msg);
		}
		if (((int) ((msg[4] >> 7) & 0x01)) == 0) {
			mCanInfo.ACC_DRIVER_PROGRAM = -1; // ��ʻ����
		} else {
			mCanInfo.ACC_DRIVER_PROGRAM = ((int) ((msg[11] >> 6) & 0x03));
		}

		if (((int) ((msg[4] >> 6) & 0x01)) == 0) {
			mCanInfo.LAST_DISTANCE_SELECTED = -1; // �ϴ�ѡ��ĳ���
			mCanInfo.ACC_DISTANCE = -1; // �ϴ�ѡ��ĳ���
		} else {
			mCanInfo.LAST_DISTANCE_SELECTED = ((int) ((msg[11] >> 5) & 0x01)); // �ϴ�ѡ��ĳ���
			mCanInfo.ACC_DISTANCE = ((int) ((msg[11] >> 0) & 0x0f)); // �ϴ�ѡ��ĳ���
		}

		if (((int) ((msg[5] >> 7) & 0x01)) == 0) {
			mCanInfo.FRONT_ASSIST_ACTIVE = -1; // �ϴ�ѡ��ĳ���
		} else {
			mCanInfo.FRONT_ASSIST_ACTIVE = ((int) ((msg[12] >> 7) & 0x01)); // ǰ������ϵͳ����
		}

		if (((int) ((msg[5] >> 6) & 0x01)) == 0) {
			mCanInfo.FRONT_ASSIST_ADVANCE_WARNING = -1; // ǰ������ϵͳԤ��
		} else {
			mCanInfo.FRONT_ASSIST_ADVANCE_WARNING = ((int) ((msg[12] >> 6) & 0x01)); // ǰ������ϵͳ����
		}

		if (((int) ((msg[5] >> 5) & 0x01)) == 0) {
			mCanInfo.FRONT_ASSIST_DISPLAY_DISTANCHE_WARNING = -1; // ��ʾ���뱨��
		} else {
			mCanInfo.FRONT_ASSIST_DISPLAY_DISTANCHE_WARNING = ((int) ((msg[12] >> 5) & 0x01)); // ��ʾ���뱨��
		}

		if (((int) ((msg[6] >> 7) & 0x01)) == 0) {
			mCanInfo.LANE_DEPARTURE = -1; // ����Ӧ��������
		} else {
			mCanInfo.LANE_DEPARTURE = ((int) ((msg[13] >> 7) & 0x01)); // ����Ӧ��������
		}

		if (((int) ((msg[7] >> 7) & 0x01)) == 0) {
			mCanInfo.PAUSE_LKAS_SIGN = -1; // �źŵ�ʶ��
		} else {
			mCanInfo.PAUSE_LKAS_SIGN = ((int) ((msg[14] >> 7) & 0x01)); // �źŵ�ʶ��
		}

		if (((int) ((msg[8] >> 7) & 0x01)) == 0) {
			mCanInfo.DETECT_FRONT_CAR = -1; // ��ʻ����ϵͳ
		} else {
			mCanInfo.DETECT_FRONT_CAR = ((int) ((msg[15] >> 7) & 0x01)); // ��ʻ����ϵͳ
		}

		if (((int) ((msg[9] >> 7) & 0x01)) == 0) {
			mCanInfo.DRIVER_ALERT_SYSTEM = -1; // ǰ�ϰ���������
		} else {
			mCanInfo.DRIVER_ALERT_SYSTEM = ((int) ((msg[16] >> 7) & 0x01)); // ǰ�ϰ���������
		}

	}

	String carInfoSave_10 = "";

	void analyzeCarInfoData_10(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_10.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_10 = BytesUtil.bytesToHexString(msg);
		}

		if (((int) ((msg[4] >> 6) & 0x01)) == 0) {
			mCanInfo.TPMS_WINTER_SPEED_WARNING = -1;
			mCanInfo.TPMS_WINTER_SPEED_WARNING_VOL = -1;
		} else {
			mCanInfo.TPMS_WINTER_SPEED_WARNING = (int) (msg[5] >> 7) & 0x01; // ������̥���ٱ���
			mCanInfo.TPMS_WINTER_SPEED_WARNING_VOL = (int) (msg[6]) & 0xff; // ������̥���ٱ���ֵ
		}

	}

	String carInfoSave_9 = "";

	void analyzeCarInfoData_9(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_9.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_9 = BytesUtil.bytesToHexString(msg);
		}

		if (((int) ((msg[4] >> 7) & 0x01)) == 0) {
			mCanInfo.AUTO_PARK_CAR_STATUS = -1;
		} else {
			mCanInfo.AUTO_PARK_CAR_STATUS = (int) (msg[5] >> 7) & 0x01; // �Զ�����
		}

		if (((int) ((msg[4] >> 6) & 0x01)) == 0) {
			mCanInfo.FRONT_VOLUME = -1;
		} else {
			mCanInfo.FRONT_VOLUME = (int) (msg[6] >> 0) & 0xFF; // ǰ������
		}

		if (((int) ((msg[4] >> 5) & 0x01)) == 0) {
			mCanInfo.FRONT_FREQUNENCY = -1;
		} else {
			mCanInfo.FRONT_FREQUNENCY = (int) (msg[7] >> 0) & 0xFF; // ǰ��Ƶ��
		}

		if (((int) ((msg[4] >> 4) & 0x01)) == 0) {
			mCanInfo.BACK_VOLUME = -1;
		} else {
			mCanInfo.BACK_VOLUME = (int) (msg[8] >> 0) & 0xFF; // ������
		}

		if (((int) ((msg[4] >> 3) & 0x01)) == 0) {
			mCanInfo.BACK_FREQUNENCY = -1;
		} else {
			mCanInfo.BACK_FREQUNENCY = (int) (msg[9] >> 0) & 0xFF; // ��Ƶ��
		}

		if (((int) ((msg[4] >> 1) & 0x01)) == 0) {
			mCanInfo.PARKING_MODE = -1;
		} else {
			mCanInfo.PARKING_MODE = (int) (msg[10] >> 6) & 0x03; // ����ģʽ
		}

		if (((int) ((msg[4] >> 0) & 0x01)) == 0) {
			mCanInfo.RADAR_WARING_VOLUME = -1;
		} else {
			mCanInfo.RADAR_WARING_VOLUME = (int) (msg[10] >> 5) & 0x01; // �״�����
		}

	}

	String backRadarSave_9 = "";

	void analyzeBackRaderData(byte[] msg) {
		// TODO Auto-generated method stub
		if (backRadarSave_9.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			backRadarSave_9 = BytesUtil.bytesToHexString(msg);
		}

		mCanInfo.BACK_LEFT_DISTANCE = ((int) (msg[4] & 0xff)) >= 0xA6 ? 0
				: (((int) (msg[4] & 0xff)) / (0xA6 / 4)) + 1;
		mCanInfo.BACK_MIDDLE_LEFT_DISTANCE = ((int) (msg[5] & 0xff)) >= 0xA6 ? 0
				: (((int) (msg[5] & 0xff)) / (0xA6 / 4)) + 1;
		mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE = ((int) (msg[6] & 0xff)) >= 0xA6 ? 0
				: (((int) (msg[6] & 0xff)) / (0xA6 / 4)) + 1;
		mCanInfo.BACK_RIGHT_DISTANCE = ((int) (msg[7] & 0xff)) >= 0xA6 ? 0
				: (((int) (msg[7] & 0xff)) / (0xA6 / 4)) + 1;

		mCanInfo.FRONT_LEFT_DISTANCE = ((int) (msg[8] & 0xff)) >= 0xA6 ? 0
				: (((int) (msg[8] & 0xff)) / (0xA6 / 4)) + 1;
		mCanInfo.FRONT_MIDDLE_LEFT_DISTANCE = ((int) (msg[9] & 0xff)) >= 0xA6 ? 0
				: (((int) (msg[9] & 0xff)) / (0xA6 / 4)) + 1;
		mCanInfo.FRONT_MIDDLE_RIGHT_DISTANCE = ((int) (msg[10] & 0xff)) >= 0xA6 ? 0
				: (((int) (msg[10] & 0xff)) / (0xA6 / 4)) + 1;
		mCanInfo.FRONT_RIGHT_DISTANCE = ((int) (msg[11] & 0xff)) >= 0xA6 ? 0
				: (((int) (msg[11] & 0xff)) / (0xA6 / 4)) + 1;

	}

	String carInfoSave_8 = "";

	void analyzeCarInfoData_8(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_8.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_8 = BytesUtil.bytesToHexString(msg);
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

	String carInfoSave_7 = "";

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

	String carInfoSave_6 = "";

	void analyzeCarInfoData_6(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_6.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_6 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.CONVENIENCE_PERCENT = (int) (msg[4] & 0xff);
		mCanInfo.CONVENIENCE_CONSUMERS = (int) (msg[5] & 0xff);
		mCanInfo.CONVENIENCE_CONSUMERS_UNIT = (int) (msg[13] & 0xff);

	}

	String carInfoSave_2 = "";

	void analyzeCarInfoData_2(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_2.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_2 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.ENGINE_SPEED = (int) ((msg[12]) & 0x0FF) * 256
				+ (int) ((msg[13] << 0) & 0x0FF);
	}

	String carInfoSave_5 = "";

	void analyzeCarInfoData_5(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_5.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_5 = BytesUtil.bytesToHexString(msg);
		}

		mCanInfo.CONSUMPTION_SINCE_REFUELING = (int) (msg[4] & 0xff) * 256
				+ (int) (msg[5] & 0xff); // �Լ���ƽ���ͺ�
		mCanInfo.RANGE_SINCE_REFUELINGT = (int) (msg[6] & 0xff) * 256
				+ (int) (msg[7] & 0xff); // �Լ����������
		mCanInfo.DISTANCE_SINCE_REFUELING = (int) (msg[8] & 0xff) * 256
				+ (int) (msg[9] & 0xff); // �Լ�����ʻ���
		mCanInfo.TRAVELLINGTIME_SINCE_REFUELINGT = (int) (msg[10] & 0xff) * 256
				+ (int) (msg[11] & 0xff); // �Լ�����ʻʱ��
		mCanInfo.SPEED_SINCE_REFUELINGT = (int) (msg[12] & 0xff); // �Լ���ƽ������
	}

	String carInfoSave_4 = "";

	void analyzeCarInfoData_4(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_4.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_4 = BytesUtil.bytesToHexString(msg);
		}

		mCanInfo.CONSUMPTION_LONG_TERM = (int) (msg[4] & 0xff) * 256
				+ (int) (msg[5] & 0xff); // ��ʱ��ƽ���ͺ�
		mCanInfo.RANGE_LONG_TERM = (int) (msg[6] & 0xff) * 256
				+ (int) (msg[7] & 0xff); // ��ʱ���������
		mCanInfo.DISTANCE_LONG_TERM = (int) (msg[8] & 0xff) * 256
				+ (int) (msg[9] & 0xff); // ��ʱ����ʻ���
		mCanInfo.TRAVELLINGTIME_LONG_TERM = (int) (msg[10] & 0xff) * 256
				+ (int) (msg[11] & 0xff); // ��ʱ����ʻʱ��
		mCanInfo.SPEED_LONG_TERM = (int) (msg[12] & 0xff); // ��ʱ��ƽ������
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

		mCanInfo.CONSUMPTION_SINCE_START = (int) (msg[4] & 0xff) * 256
				+ (int) (msg[5] & 0xff); // ������ƽ���ͺ�
		mCanInfo.RANGE_SINCE_START = (int) (msg[6] & 0xff) * 256
				+ (int) (msg[7] & 0xff); // �������������
		mCanInfo.DISTANCE_SINCE_START = (int) (msg[8] & 0xff) * 256
				+ (int) (msg[9] & 0xff); // ��������ʻ���
		mCanInfo.TRAVELLINGTIME_SINCE_START = (int) (msg[10] & 0xff) * 256
				+ (int) (msg[11] & 0xff); // ��������ʻʱ��
		mCanInfo.SPEED_SINCE_START = (int) (msg[12] & 0xff); // ������ƽ������
	}

	String carInfoSave = "";

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

		mCanInfo.INSTANT_CONSUMPTION = (int) (msg[7] & 0xff)
				+ (int) (msg[8] & 0xff) * 0.01f;
		mCanInfo.BATTERY_WARING_SIGN = (int) ((msg[10] >> 7) & 0x01);
		mCanInfo.FUEL_WARING_SIGN = (int) ((msg[10] >> 6) & 0x01);
		mCanInfo.SAFETY_BELT_STATUS = (int) ((msg[10] >> 5) & 0x01);
		mCanInfo.DISINFECTON_STATUS = (int) ((msg[10] >> 4) & 0x01);

		mCanInfo.REMAIN_FUEL = (int) ((msg[11]) & 0xff);
		mCanInfo.BATTERY_VOLTAGE = (int) ((msg[12]) & 0xff)
				+ (int) ((msg[13]) & 0xff) * 0.01f;

	}

	String SteeringButtonStatusDataSave = "";
	int steelWheel = 0;
	int handbrake = 0;
	float speed = 0;

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
		mCanInfo.DRIVING_SPEED = (int) (msg[5] & 0xff);
		if (speed != mCanInfo.DRIVING_SPEED) {
			speed = mCanInfo.DRIVING_SPEED;
			mCanInfo.CHANGE_STATUS = 10;
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

	String carBasicInfo = "";

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

	String carInfoSave_3 = "";

	void analyzeCarInfoData_3(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_3.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_3 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.INSPECTON_DISTANCE_UNIT = (int) (msg[4] & 0xFF);

		mCanInfo.INSPECTON_DAYS_STATUS = (int) ((msg[5] >> 6) & 0x03);
		mCanInfo.INSPECTON_DISTANCE_STATUS = (int) ((msg[5] >> 4) & 0x03);
		mCanInfo.OILCHANGE_SERVICE_DAYS_STATUS = (int) ((msg[5] >> 2) & 0x03);
		mCanInfo.OILCHANGE_SERVICE_DISTANCE_STATUS = (int) ((msg[5] >> 0) & 0x03);

		mCanInfo.INSPECTON_DAYS = (int) (msg[6] & 0xFF) * 256
				+ (int) (msg[7] & 0xFF);
		mCanInfo.INSPECTON_DISTANCE = (int) (msg[8] & 0xFF) * 256
				+ (int) (msg[9] & 0xFF);
		mCanInfo.OILCHANGE_SERVICE_DAYS = (int) (msg[10] & 0xFF) * 256
				+ (int) (msg[11] & 0xFF);
		mCanInfo.OILCHANGE_SERVICE_DISTANCE = (int) (msg[12] & 0xFF) * 256
				+ (int) (msg[13] & 0xFF);

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
		// mCanInfo.AC_INDICATOR_STATUS = (int) ((msg[4] >> 5) & 0x01);
		mCanInfo.SMALL_LANTERN_INDICATOR = (int) ((msg[4] >> 3) & 0x01);
		mCanInfo.DAUL_LAMP_INDICATOR = (int) ((msg[4] >> 2) & 0x01);

		mCanInfo.AC_INDICATOR_STATUS = (int) ((msg[5] >> 6) & 0x01);
		mCanInfo.CYCLE_INDICATOR = (int) ((msg[5] >> 4) & 0x01);

		mCanInfo.RIGTHT_SEAT_TEMP = (int) ((msg[6] >> 2) & 0x03);
		mCanInfo.LEFT_SEAT_TEMP = (int) ((msg[6] >> 0) & 0x03);

		mCanInfo.AIR_STRENGTH = (int) ((msg[7] >> 6) & 0x03); // ����

		int temp = (int) (msg[8] & 0xff);
		mCanInfo.MAX_FRONT_LAMP_INDICATOR = (temp == 0x02) ? 1 : 0;

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
		temp = (int) (msg[11] & 0xff);
		mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = temp == 0xFE ? 0
				: temp == 0xFF ? 255 : (temp * 0.5f);
		if ((int) ((msg[5] >> 5) & 0x01) == 1) {
			mCanInfo.OUTSIDE_TEMPERATURE = ((int) (msg[15] & 0xff)) * 0.5f - 40;
		} else {
			mCanInfo.OUTSIDE_TEMPERATURE = (((int) (msg[15] & 0xff)) * 0.5f - 40) * 1.8f + 32;
		}
	}

}
