package com.console.canreader.bean;

import java.io.UnsupportedEncodingException;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;
import com.console.canreader.utils.Contacts;

import android.util.Log;

public class SSJeepFreedom extends AnalyzeUtils {
	// ��������
	public static final int comID = 3;
	// DataType
	// �����̰���
	public static final int STEERING_BUTTON_DATA = 0x11;
	// ������Ϣ
	public static final int CAR_INFO_DATA = 0x12;
	// ���������Ϣ
	public static final int CAR_INFO_DATA_2 = 0x21;
	// ���������Ϣ
	public static final int CAR_INFO_DATA_4 = 0x22;
	// �յ���Ϣ
	public static final int AIR_CONDITIONER_DATA = 0x31;
	// ���������Ϣ
	public static final int CAR_INFO_DATA_5 = 0x32;
	// ���������Ϣ
	public static final int CAR_INFO_DATA_6 = 0x41;
	// ���������Ϣ
	public static final int CAR_INFO_DATA_7 = 0x43;
	// ���������Ϣ
	public static final int CAR_INFO_DATA_8 = 0x62;
	// ���������Ϣ
	public static final int CAR_INFO_DATA_9 = 0x60;
	// ���������Ϣ
	public static final int CAR_INFO_DATA_10 = 0xAE;
	// ���������Ϣ
	public static final int CAR_INFO_DATA_11 = 0xC1;
	// ���������Ϣ
    public static final int CAR_INFO_DATA_12 = 0xA6;
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
				mCanInfo.CHANGE_STATUS = 2;
				analyzeCarInfoData_2(msg);
				break;
			case CAR_INFO_DATA_4:
				mCanInfo.CHANGE_STATUS = 2;
				analyzeCarInfoData_4(msg);
				break;
			case AIR_CONDITIONER_DATA:
				mCanInfo.CHANGE_STATUS = 3;
				analyzeAirConditionData(msg);
				break;
			case CAR_INFO_DATA_5:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_5(msg);
				break;
			case CAR_INFO_DATA_6:
				mCanInfo.CHANGE_STATUS = 11;
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
	
	String carInfoSave_12 = "";

	void analyzeCarInfoData_12(byte[] msg) {
		// TODO Auto-generated method stub
		if (carInfoSave_12.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_12 = BytesUtil.bytesToHexString(msg);
		}

		mCanInfo.EQL_VOLUME =  (int) (msg[4] >> 0) & 0xFF; // ����
		mCanInfo.LR_BALANCE =  (int) (msg[5] >> 0) ; // ����ƽ��
		mCanInfo.FB_BALANCE =  (int) (msg[6] >> 0) ; // ǰ��ƽ��
		mCanInfo.BAS_VOLUME =  (int) (msg[7] >> 0); // ����ֵ
		mCanInfo.MID_VOLUME = (int) (msg[8] >> 0) ;// ����ֵ
		mCanInfo.TRE_VOLUME =  (int) (msg[9] >> 0); // ����ֵ
		mCanInfo.VOL_LINK_CARSPEED =  (int) (msg[10] >> 1) & 0x03; // �����복������
		mCanInfo.DSP_SURROUND = (int) (msg[10] >> 0) & 0x01;// DSP����

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
		//̥ѹ��λ
		mCanInfo.UNIT_PRESSURE= (int) (msg[4] >> 0) & 0xFF;
		//�¶ȵ�λ
		mCanInfo.UNIT_TEMPERATURE= (int) (msg[5] >> 0) & 0xFF;
		//�ͺĵ�λ
		mCanInfo.UNIT_CONSUMPTION= (int) (msg[6] >> 0) & 0xFF;
		//��λ��
		mCanInfo.UNIT_TYPE= (int) (msg[7] >> 0) & 0xFF;
		

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

		// ��״̬
		mCanInfo.MULTI_MEIDA_CD_STATUS = ((int) ((msg[4] >> 0) & 0x01) == 0) ? -1
				: (int) ((msg[7] >> 0) & 0xFF);
		// ����״̬
		mCanInfo.MULTI_MEIDA_PLAYING_STATUS = ((int) ((msg[5] >> 0) & 0x01) == 0) ? -1
				: ((int) ((msg[8] >> 1) & 0x01) == 1) ? 2
						: ((int) ((msg[8] >> 0) & 0x01) == 1) ? 1 : 0;
		mCanInfo.MULTI_MEIDA_WHOLE_NUM = ((int) ((msg[6] >> 0) & 0x01) == 0) ? -1
				: (int) ((msg[9] >> 0) & 0xFF)*256+(int) ((msg[10] >> 0) & 0xFF);
		mCanInfo.MULTI_MEIDA_PLAYING_NUM = ((int) ((msg[6] >> 0) & 0x01) == 0) ? -1
				: (int) ((msg[11] >> 0) & 0xFF)*256+(int) ((msg[12] >> 0) & 0xFF);
		mCanInfo.MULTI_MEIDA_PLAYING_WHOLE_TIME = ((int) ((msg[6] >> 0) & 0x01) == 0) ? -1
				: (int) ((msg[13] >> 0) & 0xFF)*256+(int) ((msg[14] >> 0) & 0xFF);
		mCanInfo.MULTI_MEIDA_PLAYING_TIME = ((int) ((msg[6] >> 0) & 0x01) == 0) ? -1
				: (int) ((msg[15] >> 0) & 0xFF)*256+(int) ((msg[16] >> 0) & 0xFF);

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

		// ����ʱ������ʾ��
		mCanInfo.REMOTELOCK_BEEP_SIGN = ((int) ((msg[4] >> 5) & 0x01) == 0) ? -1
				: (int) ((msg[5] >> 5) & 0x03);
		// ���ű���
		mCanInfo.REMOTELOCK_SIDELAMP_SIGN = ((int) ((msg[4] >> 4) & 0x01) == 0) ? -1
				: (int) ((msg[5] >> 4) & 0x01);
		// ��Կ�׽���
		mCanInfo.SPEECH_WARING_VOLUME = ((int) ((msg[4] >> 3) & 0x01) == 0) ? -1
				: (int) ((msg[5] >> 3) & 0x01);
		// ԭ������
		mCanInfo.REMOTE_START_SYSTEM = ((int) ((msg[4] >> 2) & 0x01) == 0) ? -1
				: (int) ((msg[5] >> 2) & 0x01);
		// �³�ʱ�Զ�����
		mCanInfo.REMOTECONTROL_LOCK_FEEDBACK = ((int) ((msg[4] >> 1) & 0x01) == 0) ? -1
				: (int) ((msg[5] >> 1) & 0x01);
		// �Զ���������
		mCanInfo.REMOTECONTROL_UNLOCK_FEEDBACK = ((int) ((msg[4] >> 0) & 0x01) == 0) ? -1
				: (int) ((msg[5] >> 0) & 0x01);

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

		// ���ڷ�Χ��
		mCanInfo.ATMOSPHERE_ILL_STATUS = ((int) ((msg[4] >> 6) & 0x01) == 0) ? -1
				: (int) ((msg[7] >> 5) & 0x07);
		// ǰ�յ����ж�
		mCanInfo.ATMOSPHERE_ILL_VALUE = ((int) ((msg[4] >> 5) & 0x01) == 0) ? -1
				: (int) ((msg[6] >> 6) & 0x03);
		// ת�Ǹ�����
		mCanInfo.CHANGE_ILL_STATUS = ((int) ((msg[4] >> 4) & 0x01) == 0) ? -1
				: (int) ((msg[5] >> 4) & 0x01);
		// ������������
		mCanInfo.DAYTIME_LAMP_STATUS_ENABLE = ((int) ((msg[4] >> 3) & 0x01) == 0) ? -1
				: (int) ((msg[7] >> 2) & 0x01);
		// �������رյ�Դ�ӳ�
		mCanInfo.GO_HOME_LAMP_STATUS_ENABLE = ((int) ((msg[4] >> 2) & 0x01) == 0) ? -1
				: (int) ((msg[6] >> 4) & 0x03);
		// ����ʱ�������
		mCanInfo.WELCOME_PERSION_ILL_STATUS_ENABLE = ((int) ((msg[4] >> 1) & 0x01) == 0) ? -1
				: (int) ((msg[6] >> 2) & 0x03);
		// ��ƹر��ӳ�
		mCanInfo.ATMOSPHERE_ILL_STATUS_ENABLE = ((int) ((msg[4] >> 0) & 0x01) == 0) ? -1
				: (int) ((msg[6] >> 0) & 0x03);

		// ����ʱת�����˸
		mCanInfo.CHANGE_ILL_STATUS_ENABLE = ((int) ((msg[5] >> 3) & 0x01) == 0) ? -1
				: (int) ((msg[7] >> 4) & 0x01);
		// �ռ���ʻ��
		mCanInfo.ESP_ENABLE = ((int) ((msg[5] >> 2) & 0x01) == 0) ? -1
				: (int) ((msg[7] >> 3) & 0x01);
		// �Զ���ѣ���
		mCanInfo.HOLOGRAM_ENABLE = ((int) ((msg[5] >> 1) & 0x01) == 0) ? -1
				: (int) ((msg[7] >> 1) & 0x01);
		// ������ˢʱ�Զ��������
		mCanInfo.CAT_SETTTING_ENABLE = ((int) ((msg[5] >> 0) & 0x01) == 0) ? -1
				: (int) ((msg[7] >> 0) & 0x01);

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
		if ((int) ((msg[4] >> 4) & 0x01) == 0) {
			mCanInfo.RADAR_PARK = -1; // �״ﲴ��
		} else {
			mCanInfo.RADAR_PARK = (int) ((msg[7] >> 6) & 0x03);
		}

		if ((int) ((msg[4] >> 3) & 0x01) == 0) {
			mCanInfo.LANE_DEPARTURE = -1; // ����ƫ��У������
		} else {
			mCanInfo.LANE_DEPARTURE = (int) ((msg[7] >> 4) & 0x03);
		}

		if ((int) ((msg[4] >> 2) & 0x01) == 0) {
			mCanInfo.PAUSE_LKAS_SIGN = -1; // ����ƫ�뾯��
		} else {
			mCanInfo.PAUSE_LKAS_SIGN = (int) ((msg[7] >> 2) & 0x03);
		}

		if ((int) ((msg[4] >> 1) & 0x01) == 0) {
			mCanInfo.DETECT_FRONT_CAR = -1; // ǰ����ײ�����Զ��ƶ�
		} else {
			mCanInfo.DETECT_FRONT_CAR = (int) ((msg[7] >> 1) & 0x01);
		}

		if ((int) ((msg[4] >> 0) & 0x01) == 0) {
			mCanInfo.FRONT_DANGER_WAIRNG_DISTANCE = -1; // ǰ����ײ��������
		} else {
			mCanInfo.FRONT_DANGER_WAIRNG_DISTANCE = (int) ((msg[7] >> 0) & 0x01);
		}

		// �Զ�פ���ƶ�
		mCanInfo.AUTO_PARK_CAR_STATUS = ((int) ((msg[5] >> 6) & 0x01) == 0) ? -1
				: (int) ((msg[8] >> 7) & 0x01);
		// �µ��𲽸���
		mCanInfo.PARKING_STATUS = ((int) ((msg[5] >> 5) & 0x01) == 0) ? -1
				: (int) ((msg[8] >> 6) & 0x01);
		// ������Ӧʽ��ˢ
		mCanInfo.CONVENIENCE_REVERSE_BACKWIPE_AUTO = ((int) ((msg[5] >> 4) & 0x01) == 0) ? -1
				: (int) ((msg[8] >> 5) & 0x01);

		// Ӱ�񲴳���̬������
		mCanInfo.CONVENIENCE_SEAT_PARK_MOVE = ((int) ((msg[5] >> 2) & 0x01) == 0) ? -1
				: (int) ((msg[8] >> 3) & 0x01);
		// Ӱ�񲴳��̶�������
		mCanInfo.CONVENIENCE_RIPE_PARK_MOVE = ((int) ((msg[5] >> 1) & 0x01) == 0) ? -1
				: (int) ((msg[8] >> 2) & 0x01);
		// ä�㱨��
		mCanInfo.CRASHPROOF_SIDE_BLIND_AREA = ((int) ((msg[5] >> 0) & 0x01) == 0) ? -1
				: (int) ((msg[8] >> 0) & 0x03);

		// �Զ���������ϵͳ
		mCanInfo.FOG_LAMP_STATUS = ((int) ((msg[6] >> 4) & 0x01) == 0) ? -1
				: (int) ((msg[9] >> 6) & 0x03);
		// ������ɲ�����ƶ�ϵͳ����
		mCanInfo.DAYTIME_LAMP_STATUS = ((int) ((msg[6] >> 3) & 0x01) == 0) ? -1
				: (int) ((msg[9] >> 5) & 0x01);
		// ��ParkSense�״ﲴ���ƶ�����
		mCanInfo.AUTO_LAMP_STATUS = ((int) ((msg[6] >> 2) & 0x01) == 0) ? -1
				: (int) ((msg[9] >> 4) & 0x01);
		// ��ParkSense����
		mCanInfo.GO_HOME_LAMP_STATUS = ((int) ((msg[6] >> 1) & 0x01) == 0) ? -1
				: (int) ((msg[9] >> 2) & 0x03);
		// ǰParkSense����
		mCanInfo.WELCOME_PERSION_ILL_STATUS = ((int) ((msg[6] >> 0) & 0x01) == 0) ? -1
				: (int) ((msg[9] >> 0) & 0x03);
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
		mCanInfo.BACK_LEFT_DISTANCE = (int) (msg[4] & 0xFF) == 0xff ? 0
				: (int) (msg[4] & 0xFF);
		mCanInfo.BACK_MIDDLE_LEFT_DISTANCE = (int) (msg[5] & 0xFF) == 0xff ? 0
				: (int) (((msg[5] & 0xFF) + 0.5f) / 1.5f);
		mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE = (int) (msg[6] & 0xFF) == 0xff ? 0
				: (int) (((msg[6] & 0xFF) + 0.5f) / (1.5f));
		mCanInfo.BACK_RIGHT_DISTANCE = (int) (msg[7] & 0xFF) == 0xff ? 0
				: (int) (msg[7] & 0xFF);

		mCanInfo.FRONT_LEFT_DISTANCE = (int) (msg[8] & 0xFF) == 0xff ? 0
				: (int) (msg[8] & 0xFF);
		mCanInfo.FRONT_MIDDLE_LEFT_DISTANCE = (int) (msg[9] & 0xFF) == 0xff ? 0
				: (int) (msg[9] & 0xFF);
		mCanInfo.FRONT_MIDDLE_RIGHT_DISTANCE = (int) (msg[10] & 0xFF) == 0xff ? 0
				: (int) (msg[10] & 0xFF);
		mCanInfo.FRONT_RIGHT_DISTANCE = (int) (msg[11] & 0xFF) == 0xff ? 0
				: (int) (msg[11] & 0xFF);

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

		mCanInfo.ENGINE_SPEED = (int) (msg[6] & 0xFF) * 256
				+ (int) (msg[7] & 0xFF);
		mCanInfo.DRIVING_SPEED = (int) (msg[8] & 0xFF) * 256
				+ (int) (msg[9] & 0xFF);

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
		if ((int) (msg[4] & 0xFF) == 1) {
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.KNOBVOLUME;
		} else {
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.KNOBSELECTOR;
		}
		mCanInfo.CAR_VOLUME_KNOB = (int) (msg[5] & 0xFF);
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

		switch ((int) (msg[4] & 0xFF)) {
		case 0x06:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.BACK;
			break;
		case 0x09:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MUTE;
			break;
		case 0x31:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.POWER;
			break;
		case 0x32:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.BROSWE;
			break;
		default:
			mCanInfo.STEERING_BUTTON_MODE = 0;
			break;
		}
		mCanInfo.STEERING_BUTTON_STATUS = (int) (msg[5] & 0xFF);
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
		mCanInfo.EQL_VOLUME = (int) (msg[4] & 0xFF); // ����
		mCanInfo.LR_BALANCE = (int) (msg[5] & 0xFF); // ����ƽ��
		mCanInfo.FB_BALANCE = (int) (msg[6] & 0xFF); // ǰ��ƽ��
		mCanInfo.BAS_VOLUME = (int) (msg[7] & 0xFF); // ����ֵ
		mCanInfo.MID_VOLUME = (int) (msg[8] & 0xFF); // ����ֵ
		mCanInfo.TRE_VOLUME = (int) (msg[9] & 0xFF); // ����ֵ
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
		mCanInfo.TRUNK_STATUS = (int) ((msg[6] >> 3) & 0x01);
		mCanInfo.RIGHT_BACKDOOR_STATUS = (int) ((msg[6] >> 4) & 0x01);
		mCanInfo.LEFT_BACKDOOR_STATUS = (int) ((msg[6] >> 5) & 0x01);
		mCanInfo.RIGHT_FORONTDOOR_STATUS = (int) ((msg[6] >> 6) & 0x01);
		mCanInfo.LEFT_FORONTDOOR_STATUS = (int) ((msg[6] >> 7) & 0x01);
		if (((int) ((msg[6] >> 1) & 0x01) == 0)
				|| ((int) ((msg[6] >> 0) & 0x01) == 0)) {
			mCanInfo.SAFETY_BELT_STATUS = 1;
		} else {
			mCanInfo.SAFETY_BELT_STATUS = 0;
		}

	}

	String SteeringButtonStatusDataSave = "";
	int steelWheel = 0;
	float handSave = 0;

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
		case 0x0C:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.HOME;
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

		mCanInfo.HANDBRAKE_STATUS = (int) ((msg[4] >> 3) & 0xFF);
		if (handSave != mCanInfo.HANDBRAKE_STATUS) {
			handSave = mCanInfo.HANDBRAKE_STATUS;
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
		mCanInfo.SMALL_LANTERN_INDICATOR = (int) ((msg[4] >> 3) & 0x01);
		mCanInfo.DAUL_LAMP_INDICATOR = (int) ((msg[4] >> 2) & 0x01);

		mCanInfo.AC_INDICATOR_STATUS = (int) ((msg[5] >> 6) & 0x01);
		mCanInfo.CYCLE_INDICATOR = (int) ((msg[5] >> 4) & 0x01);

		mCanInfo.MAX_FRONT_LAMP_INDICATOR = (int) ((msg[6] >> 4) & 0x01);
		mCanInfo.REAR_LAMP_INDICATOR = (int) ((msg[6] >> 5) & 0x01);

		mCanInfo.LEFT_SEAT_TEMP = (int) ((msg[6] >> 0) & 0x03); // �������¶�
		mCanInfo.RIGTHT_SEAT_TEMP = (int) ((msg[6] >> 2) & 0x03); // �������¶�

		int temp = (int) (msg[8] & 0xff);
		if (temp == 0x0C || temp == 0x01) {
			mCanInfo.UPWARD_AIR_INDICATOR = 1;
		} else {
			mCanInfo.UPWARD_AIR_INDICATOR = 0;
		}

		if (temp == 0x05 || temp == 0x06 || temp == 0x01) {
			mCanInfo.PARALLEL_AIR_INDICATOR = 1;
		} else {
			mCanInfo.PARALLEL_AIR_INDICATOR = 0;
		}

		if (temp == 0x01 || temp == 0x03 || temp == 0x05 || temp == 0x0C) {
			mCanInfo.DOWNWARD_AIR_INDICATOR = 1;
		} else {
			mCanInfo.DOWNWARD_AIR_INDICATOR = 0;
		}

		mCanInfo.AIR_RATE = (int) (msg[9] & 0xFF) == 0x13 ? -1
				: (int) (msg[9] & 0xFF);

		temp = (int) (msg[10] & 0xff);
		mCanInfo.DRIVING_POSITON_TEMP = temp == 0xFE ? 0 : temp == 0xFF ? 255
				: (temp * 0.5f);
		temp = (int) (msg[11] & 0xff);
		mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = temp == 0xFE ? 0
				: temp == 0xFF ? 255 : (temp * 0.5f);
		mCanInfo.OUTSIDE_TEMPERATURE = ((int) (msg[15] & 0xff)) * 0.5f - 40;
	}

}
