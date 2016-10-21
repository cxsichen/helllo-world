package com.console.canreader.service;

import java.util.Arrays;

import android.os.Parcel;
import android.os.Parcelable;

public class CanInfo implements Parcelable {

	/**
	 * mCanInfo.CHANGE_STATUS��ʾ���ĵ�״̬
	 * 
	 * STEERING_BUTTON_DATA--------------mCanInfo.CHANGE_STATUS = 2 ����״̬
	 * AIR_CONDITIONER_DATA--------------mCanInfo.CHANGE_STATUS = 3 �յ�״̬
	 * BACK_RADER_DATA-------------------mCanInfo.CHANGE_STATUS = 4 ���״�״̬
	 * FRONT_RADER_DATA------------------mCanInfo.CHANGE_STATUS = 5 ǰ�״�״̬
	 * RADAR_DATA------------------------mCanInfo.CHANGE_STATUS = 11 �״�״̬��ǰ���У�
	 * STEERING_TURN_DA+TA----------------mCanInfo.CHANGE_STATUS = 8 ������״̬
	 * CAR_INFO_DATA---------------------mCanInfo.CHANGE_STATUS = 10
	 * ����״̬�����ű�����������Ϣ������ ������Ϣ
	 */

	public int CHANGE_STATUS = 8888;

	public int HEAD_CODE = 0;
	/*
	 * ����
	 */
	public int BACK_LIGHT_DATA = -1;
	/*
	 * ����
	 */
	public int CAR_SPEED_DATA = -1;
	/*
	 * �����̰��� STEERING_BUTTON_MODE 0���ް������ͷ� 1��vol+ 2��vol- 3��menuup 4��menu down 5��
	 * PHONE 6��mute 7��SRC 8��SPEECH/MIC
	 */
	public int STEERING_BUTTON_MODE = 0;
	public int STEERING_BUTTON_STATUS = 0;
	/*
	 * �յ���Ϣ
	 */
	// �յ�״̬����on off
	public int AIR_CONDITIONER_STATUS = -1; // �յ�����
	public int AC_INDICATOR_STATUS = 0; // AC״̬
	public int CYCLE_INDICATOR = 0; // ����ѭ��ָʾ 2����ʾ 0��ѭ�� 1��ѭ��
	public int LARGE_LANTERN_INDICATOR = 0; // ��auto
	public int SMALL_LANTERN_INDICATOR = 0; // Сauto
	public int DAUL_LAMP_INDICATOR = 0;
	public int MAX_FRONT_LAMP_INDICATOR = 0; // ǰ������
	public int REAR_LAMP_INDICATOR = 0; // �󴰳���
	public int AC_MAX_STATUS = -1; 			   //AC MAX
	public int AUTO_STATUS = 0;			   //Auto����
	// ���ټ�������Ϣ
	public int UPWARD_AIR_INDICATOR = 0; // ��ǰ��
	public int PARALLEL_AIR_INDICATOR = 0; // ����
	public int DOWNWARD_AIR_INDICATOR = 0; // ����
	public int AIRCON_SHOW_REQUEST = 0;
	public int AIR_RATE = 0; // ���� -1���Զ� �����Ķ�����ֵ
	public float DRIVING_POSITON_TEMP = 0; // ��ʻλ�ô��¶� low��Ӧ0 high��Ӧ255 -1 ��ʾ����ʾ
	public float DEPUTY_DRIVING_POSITON_TEMP = 0; // ����ʻλ�ô��¶� low��Ӧ0 high��Ӧ255
													// -1 ��ʾ����ʾ
	// ���μ�����Ϣ

	public int AQS_CIRCLE = -1;

	public int LEFT_SEAT_TEMP = -1; // �������¶�
	public int RIGTHT_SEAT_TEMP = -1; // �������¶�

	/*
	 * ���״���Ϣ 0����ʾ 1����������� 4��Զ
	 */
	public int BACK_LEFT_DISTANCE = 0; // ����״�
	public int BACK_MIDDLE_LEFT_DISTANCE = 0; // ������״�
	public int BACK_MIDDLE_RIGHT_DISTANCE = 0; // �Һ����״�
	public int BACK_RIGHT_DISTANCE = 0; // �Һ��״�

	/*
	 * ǰ�״���Ϣ
	 */
	public int FRONT_LEFT_DISTANCE = 0; // ��ǰ�״�
	public int FRONT_MIDDLE_LEFT_DISTANCE = 0; // ��ǰ���״�
	public int FRONT_MIDDLE_RIGHT_DISTANCE = 0; // ��ǰ���״�
	public int FRONT_RIGHT_DISTANCE = 0; // ��ǰ�״�

	/*
	 * ������Ϣ
	 */
	public int LIGHT_MSG = 0;
	public int STOPING_STATUS = 0;
	public int REVERSE_GEAR_STATUS = 0;
	/*
	 * ��������״̬
	 */

	public int PARKING_ASSIT_STATUS = 0;
	public int RADAR_ALARM_STATUS = 1; // 1Ϊ��ʾ 0Ϊ����ʾ
	/*
	 * ������ת��
	 */
	public int STERRING_WHELL_STATUS = 0; // ������ת�� -540 �� 540
											// ESP>0 ��ת ESP<0 ��ת
	/*
	 * ����״̬POWER_AMPLIFIER_DATA
	 */
	public int POWER_AMPLIFIER_TYPE = 0;
	public int POWER_AMPLIFIER_VOlUME = 0;
	public int POWER_AMPLIFIER_BALANCE = 0;
	public int POWER_AMPLIFIER_FADER = 0;
	public int POWER_AMPLIFIER_BASS = 0;
	public int POWER_AMPLIFIER_MIDTONE = 0;
	public int POWER_AMPLIFIER_TREBLE = 0;
	public int POWER_AMPLIFIER_CHANGE = 0;
	/*
	 * ������ϢCAR_INFO_DATA
	 */
	public int SAFETY_BELT_STATUS = -1; // ��ȫ��״̬ -1��ʾ�޴˹��� 0���� 1����
	public int DISINFECTON_STATUS = -1; // ���Һ״̬ -1��ʾ�޴˹���
	public int HANDBRAKE_STATUS = -1; // ��ɲ״̬ 0���� 1����

	public int HOOD_STATUS = 0; // ����� 0�� 1��
	public int TRUNK_STATUS = 0; // ��Ǳ���
	public int RIGHT_BACKDOOR_STATUS = 0; // �Һ���
	public int LEFT_BACKDOOR_STATUS = 0; // �����
	public int RIGHT_FORONTDOOR_STATUS = 0; // ��ǰ��
	public int LEFT_FORONTDOOR_STATUS = 0; // ��ǰ��

	public int ENGINE_SPEED = -1; // ������ת�� -1��ʾ�޴˹���
	public float DRIVING_SPEED = 0; // ˲ʱ����
	public float BATTERY_VOLTAGE = -1; // ��ص��� -1��ʾ�޴˹���
	public float OUTSIDE_TEMPERATURE = 0; // �����¶�
	public int DRIVING_DISTANCE = 0; // �г����
	public int REMAIN_FUEL = -1; // ʣ������ -1��ʾ�޴˹���

	public int FUEL_WARING_SIGN = 0; // û�ͱ���
	public int BATTERY_WARING_SIGN = 0; // û�籨��

	/*-----------------------------------------------------*/

	/*-----------------�ͺ�---------���---------------------------*/
	public float INSTANT_CONSUMPTION = 0; // ˲ʱ�ͺ�
	public int INSTANT_CONSUMPTION_UNIT = 0; // ˲ʱ�ͺĵ�λ
	public float CURRENT_AVERAGE_CONSUMPTION = 0; // ��ǰƽ���ͺ�
	public float HISTORY_AVERAGE_CONSUMPTION = 0; // ��ʷƽ���ͺ�
	public int CUR_HIS_AVERAGE_CONSUMPTION_UNIT = 0; // ��ǰ/��ʷ�ͺĵ�λ

	public float AVERAGE_CONSUMPTION = 0; // ƽ���ͺ�
	public int AVERAGE_CONSUMPTION_UNIT = 0; // ƽ���ͺĵ�λ

	public int CONSUMPTION_RANGE = 0; // �ͺ�����
	public float TRIP_A = 0; // trip A
	public int TRIP_A_UNIT = 0; // trip A ��λ

	public float TRIP_A_1 = 0;
	public float TRIP_A_1_AVERAGE_CONSUMPTION = 0;
	public float TRIP_A_2 = 0;
	public float TRIP_A_2_AVERAGE_CONSUMPTION = 0;
	public float TRIP_A_3 = 0;
	public float TRIP_A_3_AVERAGE_CONSUMPTION = 0;

	public int RANGE = 0; // �������
	public int RANGE_UNIT = 0; // ������̵�λ

	public int DISTANCE_UNIT = 0; // ��ʻ��̵�λ
	public int CONSUMPTION_UNIT = 0; // ƽ���ͺĵ�λ
	public int SPEED_UNIT = 0; // ƽ�����ٵ�λ

	public float DISTANCE_SINCE_START = 0; // ��������ʻ���
	public float CONSUMPTION_SINCE_START = 0; // ������ƽ���ͺ�
	public float SPEED_SINCE_START = 0; // ������ƽ������
	public int TRAVELLINGTIME_SINCE_START = 0; // ��������ʻʱ��

	public float DISTANCE_SINCE_REFUELING = 0; // �Լ�����ʻ���
	public float CONSUMPTION_SINCE_REFUELING = 0; // �Լ���ƽ���ͺ�
	public float SPEED_SINCE_REFUELINGT = 0; // �Լ���ƽ������
	public int TRAVELLINGTIME_SINCE_REFUELINGT = 0; // �Լ�����ʻʱ��

	public float DISTANCE_LONG_TERM = 0; // ��ʱ����ʻ���
	public float CONSUMPTION_LONG_TERM = 0; // ��ʱ��ƽ���ͺ�
	public float SPEED_LONG_TERM = 0; // ��ʱ��ƽ������
	public int TRAVELLINGTIME_LONG_TERM = 0; // ��ʱ����ʻʱ��

	public String VEHICLE_NO = ""; // �������

	public int INSPECTON_DAYS_STATUS = 0; // �������״̬
	public int INSPECTON_DAYS = 0; // �����������

	public int INSPECTON_DISTANCE_UNIT = 0; // ���������̵�λ
	public int INSPECTON_DISTANCE_STATUS = 0; // ����������״��
	public int INSPECTON_DISTANCE = 0; // ����������

	public int OILCHANGE_SERVICE_DAYS_STATUS = 0; // �������ͱ�������״��
	public int OILCHANGE_SERVICE_DAYS = 0; // �������ͱ�������

	public int OILCHANGE_SERVICE_DISTANCE_UNIT = 0; // �������ͱ�����̵�λ
	public int OILCHANGE_SERVICE_DISTANCE_STATUS = 0; // �������ͱ������״��
	public int OILCHANGE_SERVICE_DISTANCE = 0; // �������ͱ������

	public int TPMS_FL_TEMP = 0; // ǰ�����¶�
	public int TPMS_FR_TEMP = 0; // ǰ�ҳ����¶�
	public int TPMS_BL_TEMP = 0; // �������¶�
	public int TPMS_BR_TEMP = 0; // ���ҳ����¶�

	public float TPMS_FL_PRESSUE = 0; // ǰ����ѹ��
	public float TPMS_FR_PRESSUE = 0; // ǰ�ҳ���ѹ��
	public float TPMS_BL_PRESSUE = 0; // ������ѹ��
	public float TPMS_BR_PRESSUE = 0; // ���ҳ���ѹ��

	/*
	 * 0x00����ʾ�����޾��档 0x01����ɫ���棬̥ѹ�����쳣�� 0x02�������˸��̥ѹ�����쳣��©���ȣ���
	 * 0x03����ɫ���棬̥ѹƫ�߻���ƫ�͡� ����ֵ����
	 */

	public int TPMS_FL_WARING = 0; // ǰ���ֱ���
	public int TPMS_FR_WARING = 0; // ǰ�ҳ��ֱ���
	public int TPMS_BL_WARING = 0; // �����ֱ���
	public int TPMS_BR_WARING = 0; // ���ҳ��ֱ���

	public String VERSION = ""; // ����汾

	public int IS_POWER_MIXING = 0; // ������������ 1Ϊ�͵���
	public int BATTERY_LEVEL = 0; // ��ص��� �޵�λ
	public int MOTOR_DRIVE_BATTERY = 0; // ����������
	public int MOTOR_DRIVE_WHEEL = 0; // �����������
	public int ENGINE_DRIVE_MOTOR = 0; // �������������
	public int ENGINE_DRIVE_WHEEL = 0; // ��������������
	public int BATTERY_DRIVE_MOTOR = 0; // ����������
	public int WHEEL_DRIVE_MOTOR = 0; // �����������

	/*-----�г�  �ͺ�-----*/
	public float TRIP_OIL_CONSUMPTION_0 = 0;
	public float TRIP_OIL_CONSUMPTION_1 = 0;
	public float TRIP_OIL_CONSUMPTION_2 = 0;
	public float TRIP_OIL_CONSUMPTION_3 = 0;
	public float TRIP_OIL_CONSUMPTION_4 = 0;
	public float TRIP_OIL_CONSUMPTION_5 = 0;
	public int TRIP_OIL_CONSUMPTION_UNIT = 0;

	/*----��ʷ  �ͺ�-----*/
	public float HISTORY_OIL_CONSUMPTION_1 = 0;
	public float HISTORY_OIL_CONSUMPTION_2 = 0;
	public float HISTORY_OIL_CONSUMPTION_3 = 0;
	public float HISTORY_OIL_CONSUMPTION_4 = 0;
	public float HISTORY_OIL_CONSUMPTION_5 = 0;
	public float HISTORY_OIL_CONSUMPTION_6 = 0;
	public float HISTORY_OIL_CONSUMPTION_7 = 0;
	public float HISTORY_OIL_CONSUMPTION_8 = 0;
	public float HISTORY_OIL_CONSUMPTION_9 = 0;
	public float HISTORY_OIL_CONSUMPTION_10 = 0;
	public float HISTORY_OIL_CONSUMPTION_11 = 0;
	public float HISTORY_OIL_CONSUMPTION_12 = 0;
	public float HISTORY_OIL_CONSUMPTION_13 = 0;
	public float HISTORY_OIL_CONSUMPTION_14 = 0;
	public float HISTORY_OIL_CONSUMPTION_15 = 0;
	public int HISTORY_OIL_CONSUMPTION_UNIT = 0;

	/*----�״���Ϣ-----*/
	public int IS_RADAR_SHOW = 0; // �״���ʾ
	public int RADAR_WARING_VOLUME = 0; // ��������
	public int FRONT_RADAR_DISTANCE = 0; // ǰ�״����
	public int BACK_RADAR_DISTANCE = 0; // ���״����

	/*----����-----*/
	public int EQL_VOLUME = 0; // ����
	public int LR_BALANCE = 0; // ����ƽ��
	public int FB_BALANCE = 0; // ǰ��ƽ��
	public int BAS_VOLUME = 0; // ����ֵ
	public int MID_VOLUME = 0; // ����ֵ
	public int TRE_VOLUME = 0; // ����ֵ
	public int VOL_LINK_CARSPEED = 0; // �����복������
	public int DSP_SURROUND = 0; // DSP����
	public int EQL_MUTE = 0; // ����

	/*----�����趨-----*/
	public int AUTO_LOCK_SETTING = 0; // �Զ������趨
	public int AUTO_OPEN_LOCK = 0; // ���ܽ����趨
	public int DRIVER_LINK_LOCK = 0; // ��ʻԱ�������������趨
	public int AUTO_OPEN_LOCK_P = 0; // �Զ������趨��P����
	public int AUTO_LOCK_P = 0; // �Զ������趨��P����
	public int AIRCON_WITH_AUTO = 0; // �յ���auto������
	public int CYCLE_WITH_AUTO = 0; // ����ѭ����auto������
	/*----ң���趨-----*/
	public int LAMP_WHEN_LOCK = 0; // ��������ʱ��������Ӧ
	public int INTELLIGENT_LOCK = 0; // ���ܳ�����һ������
	public int TWICE_KEY_OPEN_LOCK = 0; // Կ�����ΰ��½����趨
	public int TWICE_BUTTON_OPEN_LOCK = 0; // ��ť���ΰ��½����趨

	public int AUTOMATIC_CAP_SENSEITIVITY = 0; // �Զ���ͷ������
	public int AUTOMATIC_LAMP_CLOSE = 0; // ���������ر�ʱ��
	
	public int REMOTELOCK_BEEP_SIGN = 0; // ң��������������ʾ
	public int REMOTELOCK_SIDELAMP_SIGN = 0; // ң���������ߵ���ʾ
	public int SPEECH_WARING_VOLUME = 0; // ��������ϵͳ������
	public int REMOTE_START_SYSTEM = 0; // ң������ϵͳ
	
	/*----�����趨״̬-----*/
	public int LOCK_PERSONAL_SETTING = 0;   // �뿪��ֹ���Ի��趨
	public int AUTO_LOCK_TIME = 0;   // �Զ�����ʱ��
	public int REMOTE_LOCK_SIGN = 0;   // ң��������ʾ  

	/*--  ����ͷģʽ-----*/
	public int BACK_CAMERA_MODE = 0; // ������ͷģʽ
	public int LEFT_CAMERA_SWITCH = 0; // ������ͷ����
	
	/*--  �ƹ��趨-----*/
	public int WIPER_LINK_LAMP = 0; //��ˢ���Զ�����������Ի��趨
	public int AUTO_LIGHT_SENSEITIVITY = 0; //�Զ���������������
	public int AUTO_LIGHTING_SENSEITIVITY = 0; //�Զ����������
	public int FRONT_LAMP_OFF_TIME = 0; //ǰ����Զ�Ϩ��ʱ��
	public int LAMP_TURN_DARK_TIME = 0; //���ڵƹ����ʱ��
	
	/*-----��ʻ����ϵͳ�趨--------*/
	public int LANE_DEPARTURE = 0; //����ƫ�븨��ϵͳ�趨
	public int PAUSE_LKAS_SIGN = 0; //��ͣLKAS��ʾ��
	public int DETECT_FRONT_CAR = 0; //ACCǰ��֪̽��ʾ��
	public int FRONT_DANGER_WAIRNG_DISTANCE = 0; //�趨ǰ��Σ�վ������

	/*-----��ʾ��״̬--------*/
	public int RATATIONAL_RATE = 0; //ת����ʾ
	public int MSG_NOTIFICATION = 0; //����Ϣ����
	public int ENGINEE_AUTO_CONTROL = 0; //�����������Զ���ͣ��ʾ
	public int ENERGY_BACKGROUND_LIGHT = 0; //����ģʽ�ı�������
	public int ADJUST_WARING_VOLUME = 0;   //������������  
	
	public int SWITCH_TRIPB_SETTING=0;     //���B�����������л�
	public int SWITCH_TRIPA_SETTING=0;     //���A�����������л�
	public int ADJUST_OUTSIDE_TEMP=0;     //�����ⲿ������ʾ
	            
	public int CAR_TYPE=0;   //��������
	
	public int AIR_CONDITIONER_CONTROL = 0; // �յ��Ƿ��н�����ƣ����Ϊ1���У�����Ҫ�Զ�����
	
	
	/*-------------------add by xyw start----------------------------------*/
	public int CAR_GEAR_STATUS = -1;							//����λ��-1��ʾ��Ч 1P 2N 3R 4D 5S
	public int CAR_BACK_STATUS = -1;							//����״̬          0�ǵ���    1����
	public int CAR_ILL_STATUS = -1;								//ILL�ƹ�          0��ƹر�    1��ƴ�
	public int CAR_VOLUME_KNOB=0;									//������ť��ֵ
	public int CAR_VOLUME_KNOB_UP=0;								//������ť���ӵ�ֵ
	public int CAR_VOLUME_KNOB_DOWN=0;								//���������ٵ�ֵ
	public int CAR_SELECTOR_KNOB=0;									//ѡ����ťֵ
	public int CAR_SELECTOR_KNOB_UP=0;								//ѡ����ť ���ӵ�ֵ
	public int CAR_SELECTOR_KNOB_DOWN=0;							//������ť���ٵ�ֵ
	public int LINK_SOS_STATUS=0;								//Link SoSͨѶ    0�˳�  1LINK  2SOS
	public int LANGUAGE_CHANGE=1;								//��������1���� 2 Ӣ��
	public int AUTO_COMPRESSOR_STATUS=0;						//AUTOʱѹ����״̬    1�� 0��
	public int AUTO_CYCLE_STATUS=0;								//AUTOʱ����ѭ�����Ʒ�ʽ   1�Զ�  0�ֶ�
	public int AIR_COMFORTABLE_STATUS=0;						//�յ�������������  00���� 01���� 02����
	public int AIR_ANION_STATUS=0;									//������ģʽ  1����  0�ر�
	public int DRIVING_POSITION_SETTING=0;						//��ʻ�����Զ��������� 1��  0��
	public int DEPUTY_DRIVING_POSITION_SETTING=0;				//����ʻ�����Զ��������� 1��  0��
	public int POSITION_WELCOME_SETTING=0;						//����ӭ����������  1����  0�ر�
	public int KEY_INTELLIGENCE=0;								//����Կ���Զ�ʶ������  1���� 0�ر�
	public int SPEED_OVER_SETTING=0;							//��ʻ�������ٱ���  �ٶ�=Data3*10km/h
	public int WARNING_VOLUME=0;								//��ʻ��������Ǳ�������    0��   1��  2��
	public int REMOTE_POWER_TIME=0;								//��ʻ����Զ���ϵ�ʱ��    0-30����
	public int REMOTE_START_TIME=0;								//��ʻ����Զ������ʱ�� 0-30����
	public int DRIVER_CHANGE_MODE=0;							//��ʻ����ת��ģʽ  0�˶�  1��׼  2����
	public int REMOTE_UNLOCK=0;									//ң�ؽ���   1����ǰ��  0���г���
	public int SPEED_LOCK=0;									//��������  1�� 0�� 
	public int AUTO_UNLOCK;										//�Զ�����  1�� 0�� 
	public int REMOTE_FRONT_LEFT=0;								//ң����ǰ�����촰   1�� 0��
	public int FRONT_WIPER_CARE=0;								//ǰ���ά������  1��  0��
	public int REAR_WIPER_STATUS=0;								//����ε����Զ���ˢ����  1�� 0��
	public int OUTSIDE_MIRROR_STATUS=0;							//����Ӿ��Զ��۵�  1�� 0��
	public int GO_HOME_LAMP_STATUS=0;							//���һؼ�����  0�ر� 1�������  2�������
	public int FOG_LAMP_STATUS=0;								//���ת����  1�� 0��
	public int DAYTIME_LAMP_STATUS=0;							//�ռ��г���  1�� 0��
	public int AUTO_LAMP_STATUS;								//�Զ��ƹ�������  0��  1�� 2��
	
	public int ESP_ENABLE=1;                                    //������ת��  1��Ч 0��Ч
	public int HOLOGRAM_ENABLE=1;								//ȫϢӰ������ 1��Ч 0��Ч
	public int CAT_SETTTING_ENABLE=1;							//����������Ϣ 1���� 0��Ч
	public int PANEL_SSETTING_ENABLE=1;							//��尴����Ϣ 1��Ч 0��Ч
	public int AIR_INFO_ENABLE=1;								//�յ���Ϣ  1��Ч 0��Ч
	
	public int CAMERA_DIAPLAY_ENABLE=0;							//����ͷ��ʾ����  0�� 1��
	public int CAMERA_MODE=1;									//ȫ������ͷģʽ 1ǰ+ȫ    2ǰ+�Ҳ�     3��+ȫ     4 ˮƽͣ��+ȫ     5��ֱͣ��+ȫ
	/*-------------------add by xyw end----------------------------------*/

	
   
	public CanInfo() {

	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(CHANGE_STATUS);
		dest.writeInt(HEAD_CODE);
		dest.writeInt(BACK_LIGHT_DATA);
		dest.writeInt(CAR_SPEED_DATA);
		dest.writeInt(STEERING_BUTTON_MODE);
		dest.writeInt(STEERING_BUTTON_STATUS);
		dest.writeInt(AIR_CONDITIONER_STATUS);
		dest.writeInt(AC_INDICATOR_STATUS);
		dest.writeInt(CYCLE_INDICATOR);
		dest.writeInt(LARGE_LANTERN_INDICATOR);
		dest.writeInt(SMALL_LANTERN_INDICATOR);
		dest.writeInt(DAUL_LAMP_INDICATOR);
		dest.writeInt(MAX_FRONT_LAMP_INDICATOR);
		dest.writeInt(REAR_LAMP_INDICATOR);

		dest.writeInt(UPWARD_AIR_INDICATOR);
		dest.writeInt(PARALLEL_AIR_INDICATOR);
		dest.writeInt(DOWNWARD_AIR_INDICATOR);
		dest.writeInt(AIRCON_SHOW_REQUEST);
		dest.writeInt(AIR_RATE);

		dest.writeFloat(DRIVING_POSITON_TEMP);
		dest.writeFloat(DEPUTY_DRIVING_POSITON_TEMP);

		dest.writeInt(AQS_CIRCLE);
		dest.writeInt(LEFT_SEAT_TEMP);
		dest.writeInt(RIGTHT_SEAT_TEMP);

		dest.writeInt(BACK_LEFT_DISTANCE);
		dest.writeInt(BACK_MIDDLE_LEFT_DISTANCE);
		dest.writeInt(BACK_MIDDLE_RIGHT_DISTANCE);
		dest.writeInt(BACK_RIGHT_DISTANCE);

		dest.writeInt(FRONT_LEFT_DISTANCE);
		dest.writeInt(FRONT_MIDDLE_LEFT_DISTANCE);
		dest.writeInt(FRONT_MIDDLE_RIGHT_DISTANCE);
		dest.writeInt(FRONT_RIGHT_DISTANCE);

		dest.writeInt(LIGHT_MSG);
		dest.writeInt(STOPING_STATUS);
		dest.writeInt(REVERSE_GEAR_STATUS);

		dest.writeInt(PARKING_ASSIT_STATUS);
		dest.writeInt(RADAR_ALARM_STATUS);

		dest.writeInt(STERRING_WHELL_STATUS);

		dest.writeInt(POWER_AMPLIFIER_TYPE);
		dest.writeInt(POWER_AMPLIFIER_VOlUME);
		dest.writeInt(POWER_AMPLIFIER_BALANCE);
		dest.writeInt(POWER_AMPLIFIER_FADER);
		dest.writeInt(POWER_AMPLIFIER_BASS);
		dest.writeInt(POWER_AMPLIFIER_MIDTONE);
		dest.writeInt(POWER_AMPLIFIER_TREBLE);
		dest.writeInt(POWER_AMPLIFIER_CHANGE);

		dest.writeInt(SAFETY_BELT_STATUS);
		dest.writeInt(DISINFECTON_STATUS);
		dest.writeInt(HANDBRAKE_STATUS);
		dest.writeInt(TRUNK_STATUS);
		dest.writeInt(RIGHT_BACKDOOR_STATUS);
		dest.writeInt(LEFT_BACKDOOR_STATUS);
		dest.writeInt(RIGHT_FORONTDOOR_STATUS);
		dest.writeInt(LEFT_FORONTDOOR_STATUS);

		dest.writeInt(ENGINE_SPEED);
		dest.writeFloat(DRIVING_SPEED);
		dest.writeFloat(BATTERY_VOLTAGE);
		dest.writeFloat(OUTSIDE_TEMPERATURE);
		dest.writeInt(DRIVING_DISTANCE);
		dest.writeInt(REMAIN_FUEL);

		dest.writeInt(FUEL_WARING_SIGN);
		dest.writeInt(BATTERY_WARING_SIGN);
	}

	public static final Parcelable.Creator<CanInfo> CREATOR = new Parcelable.Creator<CanInfo>() {
		public CanInfo createFromParcel(Parcel in) {
			return new CanInfo(in);
		}

		public CanInfo[] newArray(int size) {
			return new CanInfo[size];
		}
	};

	private CanInfo(Parcel in) {
		CHANGE_STATUS = in.readInt();

		HEAD_CODE = in.readInt();

		BACK_LIGHT_DATA = in.readInt();

		CAR_SPEED_DATA = in.readInt();

		STEERING_BUTTON_MODE = in.readInt();
		STEERING_BUTTON_STATUS = in.readInt();

		AIR_CONDITIONER_STATUS = in.readInt();
		AC_INDICATOR_STATUS = in.readInt();
		CYCLE_INDICATOR = in.readInt();
		LARGE_LANTERN_INDICATOR = in.readInt();
		SMALL_LANTERN_INDICATOR = in.readInt();
		DAUL_LAMP_INDICATOR = in.readInt();
		MAX_FRONT_LAMP_INDICATOR = in.readInt();
		REAR_LAMP_INDICATOR = in.readInt();
		// byte2���ټ�������Ϣ
		UPWARD_AIR_INDICATOR = in.readInt();
		PARALLEL_AIR_INDICATOR = in.readInt();
		DOWNWARD_AIR_INDICATOR = in.readInt();
		AIRCON_SHOW_REQUEST = in.readInt();
		AIR_RATE = in.readInt();
		// byte3��ʻλ�ô��¶�
		DRIVING_POSITON_TEMP = in.readFloat();
		// byte4����ʻλ�ô��¶�
		DEPUTY_DRIVING_POSITON_TEMP = in.readFloat();
		// byte5���μ�����Ϣ
		AQS_CIRCLE = in.readInt();
		LEFT_SEAT_TEMP = in.readInt();
		RIGTHT_SEAT_TEMP = in.readInt();
		/*
		 * ���״���Ϣ
		 */
		BACK_LEFT_DISTANCE = in.readInt();
		BACK_MIDDLE_LEFT_DISTANCE = in.readInt();
		BACK_MIDDLE_RIGHT_DISTANCE = in.readInt();
		BACK_RIGHT_DISTANCE = in.readInt();
		/*
		 * ǰ�״���Ϣ
		 */
		FRONT_LEFT_DISTANCE = in.readInt();
		FRONT_MIDDLE_LEFT_DISTANCE = in.readInt();
		FRONT_MIDDLE_RIGHT_DISTANCE = in.readInt();
		FRONT_RIGHT_DISTANCE = in.readInt();
		/*
		 * ������Ϣ
		 */
		LIGHT_MSG = in.readInt();
		STOPING_STATUS = in.readInt();
		REVERSE_GEAR_STATUS = in.readInt();
		/*
		 * ��������״̬
		 */
		PARKING_ASSIT_STATUS = in.readInt();
		RADAR_ALARM_STATUS = in.readInt();
		/*
		 * ������ת��
		 */
		STERRING_WHELL_STATUS = in.readInt();
		/*
		 * ����״̬POWER_AMPLIFIER_DATA
		 */
		POWER_AMPLIFIER_TYPE = in.readInt();
		POWER_AMPLIFIER_VOlUME = in.readInt();
		POWER_AMPLIFIER_BALANCE = in.readInt();
		POWER_AMPLIFIER_FADER = in.readInt();
		POWER_AMPLIFIER_BASS = in.readInt();
		POWER_AMPLIFIER_MIDTONE = in.readInt();
		POWER_AMPLIFIER_TREBLE = in.readInt();
		POWER_AMPLIFIER_CHANGE = in.readInt();
		/*
		 * ������ϢCAR_INFO_DATA
		 */
		SAFETY_BELT_STATUS = in.readInt();
		DISINFECTON_STATUS = in.readInt();
		HANDBRAKE_STATUS = in.readInt();
		TRUNK_STATUS = in.readInt();
		RIGHT_BACKDOOR_STATUS = in.readInt();
		LEFT_BACKDOOR_STATUS = in.readInt();
		RIGHT_FORONTDOOR_STATUS = in.readInt();
		LEFT_FORONTDOOR_STATUS = in.readInt();

		ENGINE_SPEED = in.readInt();
		DRIVING_SPEED = in.readFloat();
		BATTERY_VOLTAGE = in.readFloat();
		OUTSIDE_TEMPERATURE = in.readFloat();
		DRIVING_DISTANCE = in.readInt();
		REMAIN_FUEL = in.readInt();

		FUEL_WARING_SIGN = in.readInt();
		BATTERY_WARING_SIGN = in.readInt();
	}

	@Override
	public String toString() {
		return "CanInfo [CHANGE_STATUS=" + CHANGE_STATUS + ", HEAD_CODE="
				+ HEAD_CODE + ", BACK_LIGHT_DATA=" + BACK_LIGHT_DATA
				+ ", CAR_SPEED_DATA=" + CAR_SPEED_DATA
				+ ", STEERING_BUTTON_MODE=" + STEERING_BUTTON_MODE
				+ ", STEERING_BUTTON_STATUS=" + STEERING_BUTTON_STATUS
				+ ", AIR_CONDITIONER_STATUS=" + AIR_CONDITIONER_STATUS
				+ ", AC_INDICATOR_STATUS=" + AC_INDICATOR_STATUS
				+ ", CYCLE_INDICATOR=" + CYCLE_INDICATOR
				+ ", LARGE_LANTERN_INDICATOR=" + LARGE_LANTERN_INDICATOR
				+ ", SMALL_LANTERN_INDICATOR=" + SMALL_LANTERN_INDICATOR
				+ ", DAUL_LAMP_INDICATOR=" + DAUL_LAMP_INDICATOR
				+ ", MAX_FRONT_LAMP_INDICATOR=" + MAX_FRONT_LAMP_INDICATOR
				+ ", REAR_LAMP_INDICATOR=" + REAR_LAMP_INDICATOR
				+ ", UPWARD_AIR_INDICATOR=" + UPWARD_AIR_INDICATOR
				+ ", PARALLEL_AIR_INDICATOR=" + PARALLEL_AIR_INDICATOR
				+ ", DOWNWARD_AIR_INDICATOR=" + DOWNWARD_AIR_INDICATOR
				+ ", AIRCON_SHOW_REQUEST=" + AIRCON_SHOW_REQUEST
				+ ", AIR_RATE=" + AIR_RATE + ", DRIVING_POSITON_TEMP="
				+ DRIVING_POSITON_TEMP + ", DEPUTY_DRIVING_POSITON_TEMP="
				+ DEPUTY_DRIVING_POSITON_TEMP + ", AQS_CIRCLE=" + AQS_CIRCLE
				+ ", LEFT_SEAT_TEMP=" + LEFT_SEAT_TEMP + ", RIGTHT_SEAT_TEMP="
				+ RIGTHT_SEAT_TEMP + ", BACK_LEFT_DISTANCE="
				+ BACK_LEFT_DISTANCE + ", BACK_MIDDLE_LEFT_DISTANCE="
				+ BACK_MIDDLE_LEFT_DISTANCE + ", BACK_MIDDLE_RIGHT_DISTANCE="
				+ BACK_MIDDLE_RIGHT_DISTANCE + ", BACK_RIGHT_DISTANCE="
				+ BACK_RIGHT_DISTANCE + ", FRONT_LEFT_DISTANCE="
				+ FRONT_LEFT_DISTANCE + ", FRONT_MIDDLE_LEFT_DISTANCE="
				+ FRONT_MIDDLE_LEFT_DISTANCE + ", FRONT_MIDDLE_RIGHT_DISTANCE="
				+ FRONT_MIDDLE_RIGHT_DISTANCE + ", FRONT_RIGHT_DISTANCE="
				+ FRONT_RIGHT_DISTANCE + ", LIGHT_MSG=" + LIGHT_MSG
				+ ", STOPING_STATUS=" + STOPING_STATUS
				+ ", REVERSE_GEAR_STATUS=" + REVERSE_GEAR_STATUS
				+ ", PARKING_ASSIT_STATUS=" + PARKING_ASSIT_STATUS
				+ ", RADAR_ALARM_STATUS=" + RADAR_ALARM_STATUS
				+ ", STERRING_WHELL_STATUS=" + STERRING_WHELL_STATUS
				+ ", POWER_AMPLIFIER_TYPE=" + POWER_AMPLIFIER_TYPE
				+ ", POWER_AMPLIFIER_VOlUME=" + POWER_AMPLIFIER_VOlUME
				+ ", POWER_AMPLIFIER_BALANCE=" + POWER_AMPLIFIER_BALANCE
				+ ", POWER_AMPLIFIER_FADER=" + POWER_AMPLIFIER_FADER
				+ ", POWER_AMPLIFIER_BASS=" + POWER_AMPLIFIER_BASS
				+ ", POWER_AMPLIFIER_MIDTONE=" + POWER_AMPLIFIER_MIDTONE
				+ ", POWER_AMPLIFIER_TREBLE=" + POWER_AMPLIFIER_TREBLE
				+ ", POWER_AMPLIFIER_CHANGE=" + POWER_AMPLIFIER_CHANGE
				+ ", SAFETY_BELT_STATUS=" + SAFETY_BELT_STATUS
				+ ", DISINFECTON_STATUS=" + DISINFECTON_STATUS
				+ ", HANDBRAKE_STATUS=" + HANDBRAKE_STATUS + ", HOOD_STATUS="
				+ HOOD_STATUS + ", TRUNK_STATUS=" + TRUNK_STATUS
				+ ", RIGHT_BACKDOOR_STATUS=" + RIGHT_BACKDOOR_STATUS
				+ ", LEFT_BACKDOOR_STATUS=" + LEFT_BACKDOOR_STATUS
				+ ", RIGHT_FORONTDOOR_STATUS=" + RIGHT_FORONTDOOR_STATUS
				+ ", LEFT_FORONTDOOR_STATUS=" + LEFT_FORONTDOOR_STATUS
				+ ", ENGINE_SPEED=" + ENGINE_SPEED + ", DRIVING_SPEED="
				+ DRIVING_SPEED + ", BATTERY_VOLTAGE=" + BATTERY_VOLTAGE
				+ ", OUTSIDE_TEMPERATURE=" + OUTSIDE_TEMPERATURE
				+ ", DRIVING_DISTANCE=" + DRIVING_DISTANCE + ", REMAIN_FUEL="
				+ REMAIN_FUEL + ", FUEL_WARING_SIGN=" + FUEL_WARING_SIGN
				+ ", BATTERY_WARING_SIGN=" + BATTERY_WARING_SIGN + ", RANGE="
				+ RANGE + ", RANGE_UNIT=" + RANGE_UNIT + ", DISTANCE_UNIT="
				+ DISTANCE_UNIT + ", CONSUMPTION_UNIT=" + CONSUMPTION_UNIT
				+ ", SPEED_UNIT=" + SPEED_UNIT + ", DISTANCE_SINCE_START="
				+ DISTANCE_SINCE_START + ", CONSUMPTION_SINCE_START="
				+ CONSUMPTION_SINCE_START + ", SPEED_SINCE_START="
				+ SPEED_SINCE_START + ", TRAVELLINGTIME_SINCE_START="
				+ TRAVELLINGTIME_SINCE_START + ", DISTANCE_SINCE_REFUELING="
				+ DISTANCE_SINCE_REFUELING + ", CONSUMPTION_SINCE_REFUELING="
				+ CONSUMPTION_SINCE_REFUELING + ", SPEED_SINCE_REFUELINGT="
				+ SPEED_SINCE_REFUELINGT + ", TRAVELLINGTIME_SINCE_REFUELINGT="
				+ TRAVELLINGTIME_SINCE_REFUELINGT + ", DISTANCE_LONG_TERM="
				+ DISTANCE_LONG_TERM + ", CONSUMPTION_LONG_TERM="
				+ CONSUMPTION_LONG_TERM + ", SPEED_LONG_TERM="
				+ SPEED_LONG_TERM + ", TRAVELLINGTIME_LONG_TERM="
				+ TRAVELLINGTIME_LONG_TERM + ", VEHICLE_NO=" + VEHICLE_NO
				+ ", INSPECTON_DAYS_STATUS=" + INSPECTON_DAYS_STATUS
				+ ", INSPECTON_DAYS=" + INSPECTON_DAYS
				+ ", INSPECTON_DISTANCE_UNIT=" + INSPECTON_DISTANCE_UNIT
				+ ", INSPECTON_DISTANCE_STATUS=" + INSPECTON_DISTANCE_STATUS
				+ ", INSPECTON_DISTANCE=" + INSPECTON_DISTANCE
				+ ", OILCHANGE_SERVICE_DAYS_STATUS="
				+ OILCHANGE_SERVICE_DAYS_STATUS + ", OILCHANGE_SERVICE_DAYS="
				+ OILCHANGE_SERVICE_DAYS + ", OILCHANGE_SERVICE_DISTANCE_UNIT="
				+ OILCHANGE_SERVICE_DISTANCE_UNIT
				+ ", OILCHANGE_SERVICE_DISTANCE_STATUS="
				+ OILCHANGE_SERVICE_DISTANCE_STATUS
				+ ", OILCHANGE_SERVICE_DISTANCE=" + OILCHANGE_SERVICE_DISTANCE
				+ "]";
	}

}
