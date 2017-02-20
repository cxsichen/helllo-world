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
	 * STEERING_TURN_DATA----------------mCanInfo.CHANGE_STATUS = 8 ������״̬
	 * CAR_INFO_DATA---------------------mCanInfo.CHANGE_STATUS = 10
	 * ����״̬�����ű�����������Ϣ������ ������Ϣ
	 * WARING_DIALOG_DATA----------------mCanInfo.CHANGE_STATUS = 12 408������Ϣ
	 * WARING_DIALOG_DATA----------------mCanInfo.CHANGE_STATUS = 13 ������Ϣ
	 * PANORAMA_DATA---------------------mCanInfo.CHANGE_STATUS = 20 360ȫ��
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
	public int AC_MAX_STATUS = 0; 			   //AC MAX
	public int AUTO_STATUS = 0;			   //Auto����
	public int Mono_STATUS = 0;			   //��һ���� 1�� 0�� Ĭ�Ϲ�
	// ���ټ�������Ϣ
	public int UPWARD_AIR_INDICATOR = 0; // ��ǰ��
	public int PARALLEL_AIR_INDICATOR = 0; // ����
	public int DOWNWARD_AIR_INDICATOR = 0; // ����
	public int AIRCON_SHOW_REQUEST = 0;
	public int AIR_RATE = 0; // ���� -1���Զ� �����Ķ�����ֵ
	public int AIR_STRENGTH = 0; // ����ǿ�ȵȼ�  0�� 1�� 2��
	public float DRIVING_POSITON_TEMP = 0; // ��ʻλ�ô��¶� low��Ӧ0 high��Ӧ255 -1 ��ʾ����ʾ
	public float DEPUTY_DRIVING_POSITON_TEMP = 0; // ����ʻλ�ô��¶� low��Ӧ0 high��Ӧ255
													// -1 ��ʾ����ʾ
	// ���μ�����Ϣ

	public int AQS_CIRCLE = -1;

	public int LEFT_SEAT_TEMP = 0; // �������¶�
	public int RIGTHT_SEAT_TEMP = 0; // �������¶�

	/*
	 * ���״���Ϣ 0����ʾ 1����������� 4��Զ  �ǵò�Ҫ����0~4 �ᱨ����ʾ
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
	public int RADAR_ALARM_STATUS = 1; // �״ﱨ�� 1Ϊ��ʾ 0Ϊ����ʾ
	public int RADAR_ALARM_STATUS_ENABLE=0;			
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
	public int SAFETY_BELT_STATUS = -1; // ��ʻԱ��ȫ��״̬ -1��ʾ�޴˹��� 0���� 1����
	public int DEPUTY_SAFETY_BELT_STATUS = -1; //����ʻ��ȫ��״̬ 0���� 1����
	public int BACK_LEFT_SAFETY_BELT_STATUS = -1; //����ȫ��״̬ 0���� 1����
	public int BACK_MIDDLE_SAFETY_BELT_STATUS = -1; //���а�ȫ��״̬ 0���� 1����
	public int BACK_RIGHT_SAFETY_BELT_STATUS = -1; //���Ұ�ȫ��״̬ 0���� 1����
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
	public float OUTSIDE_TEMPERATURE = 0; // �����¶�        -10000f��ʾ��--��
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
	public int RANGE_ADD = 0; // �ۼ����

	public int DISTANCE_UNIT = 0; // ��ʻ��̵�λ
	public int CONSUMPTION_UNIT = 0; // ƽ���ͺĵ�λ
	public int AVERAGE_SPEED = 0; // ƽ������
	public int SPEED_UNIT = 0; // ƽ�����ٵ�λ

	public float DISTANCE_SINCE_START = 0; // ��������ʻ���
	public float CONSUMPTION_SINCE_START = 0; // ������ƽ���ͺ�
	public float SPEED_SINCE_START = 0; // ������ƽ������
	public int TRAVELLINGTIME_SINCE_START = 0; // ��������ʻʱ��
	public int RANGE_SINCE_START = 0; // �������������

	public float DISTANCE_SINCE_REFUELING = 0; // �Լ�����ʻ���
	public float CONSUMPTION_SINCE_REFUELING = 0; // �Լ���ƽ���ͺ�
	public float SPEED_SINCE_REFUELINGT = 0; // �Լ���ƽ������
	public int TRAVELLINGTIME_SINCE_REFUELINGT = 0; // �Լ�����ʻʱ��
	public int RANGE_SINCE_REFUELINGT = 0; // �Լ����������

	public float DISTANCE_LONG_TERM = 0; // ��ʱ����ʻ���
	public float CONSUMPTION_LONG_TERM = 0; // ��ʱ��ƽ���ͺ�
	public float SPEED_LONG_TERM = 0; // ��ʱ��ƽ������
	public int TRAVELLINGTIME_LONG_TERM = 0; // ��ʱ����ʻʱ��
	public int RANGE_LONG_TERM = 0; // ��ʱ���������

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
	
	public String WARING_MSG = ""; // �����ַ���
	public int WARING_MSG_STATUS = 0; // ��������
	
	public String VERSION = ""; // ����汾

	public int IS_POWER_MIXING = 0; // ������������ 1Ϊ�͵���
	public int BATTERY_LEVEL = 0; // ��ص��� �޵�λ
	public int MOTOR_DRIVE_BATTERY = 0; // ����������
	public int MOTOR_DRIVE_WHEEL = 0; // �����������
	public int ENGINE_DRIVE_MOTOR = 0; // �������������
	public int ENGINE_DRIVE_WHEEL = 0; // ��������������
	public int BATTERY_DRIVE_MOTOR = 0; // ����������
	public int WHEEL_DRIVE_MOTOR = 0; // �����������
	
	/*----���� ״̬-----*/
	public int FRONT_LEFT_DOORLOCK = 0; // ��ǰ����
	public int FRONT_RIGHT_DOORLOCK = 0; // ��ǰ����
	public int BACK_LEFT_DOORLOCK = 0; // �������
	public int BACK_RIGHT_DOORLOCK = 0; // �Һ�����
	
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
	public int SEAT_SOUND=0;  //��ʻ������״̬
	public int BOSE_CENTERPOINT=0; //BOSE CenterPoint״̬
	public int SURROND_VOLUME=0; //��������
	
	/*-----360ȫ������ͷ------*/
	public int PANORAMA_STATUS=0;  //360ȫ��״̬


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

	public int REMOTECONTROL_LOCK_FEEDBACK=0; //ң�����������趨
	public int REMOTECONTROL_UNLOCK_FEEDBACK=0; //ң�ؽ��������趨
	public int REMOTECONTROL_UNLOCK=0; //ң�ؽ����趨
	public int REMOTECONTROL_UNLOCK_AUTORELOCK=0; //ң�ؽ��������Զ������趨
	public int REMOTECONTROL_RELOCK_DOOR=0; //����ң�ش򿪵��ŵ��趨
	public int REMOTECONTROL_KEY_AUTORECOGNIZE=0; //��ʻԱԿ���Զ�ʶ���趨
	public int REMOTECONTROL_START=0; //Զ�������趨
	public int REMOTECONTROL_NEAR_AUTOUNLOCK=0; //���ܽ��������趨
	
	public int REMOTECONTROL_AWAY_AUTOLOCK=0; //�����복�����趨
	public int REMOTECONTROL_KEYLEF_ALARM=0; //Կ�����������趨
	public int REMOTECONTROL_MOVE_DOOR=0; //ң�ػ���������
	public int REMOTECONTROL_WINDOW_CONTROL=0; //ң�س�������
	
	/*----�����趨-----*/
	public int LIGHT_CARLAMP_SETTING=0; //Ѱ���ƹ����趨
	public int LIGHT_LOCKLAMP_SETTING=0; //���������ʱ�趨
	public int LIGHT_RIHGTTURN_LAMP_SETTING=0; //��ת���
	public int LIGHT_LEFTTURN_LAMP_SETTING=0; //��ת���
	
	/*----�����趨 �����趨״̬-----*/
	public int LOCK_PERSONAL_SETTING = 0;   // �뿪��ֹ���Ի��趨
	public int AUTO_LOCK_TIME = 0;   // �Զ�����ʱ��
	public int REMOTE_LOCK_SIGN = 0;   // ң��������ʾ 
	
	
	public int AUTO_OPEN_LOCK = 0; // ���ܽ����趨
	public int DRIVER_LINK_LOCK = 0; // ��ʻԱ�������������趨
	public int AUTO_OPEN_LOCK_P = 0; // �Զ������趨��P����
	public int AUTO_LOCK_P = 0; // �Զ������趨��P����
	public int AIRCON_WITH_AUTO = 0; // �յ���auto������
	public int CYCLE_WITH_AUTO = 0; // ����ѭ����auto������
	
	public int LOCK_OPENDOOR_WITHOUTLOCK=0; //��ֹ�����Զ��Զ������趨
	public int AUTO_LOCK_SETTING = 0; // �Զ������趨
	public int AUTO_OPEN_LOCK_Z = 0; // �Զ������趨���Զ�����
	public int LOCK_DELAY_LOCK=0;   //�ӳ������趨
	public int AUTO_OPEN_LOCK_S = 0; // �Զ������趨���ֶ�����
	
	/*--  ԭ������ʾ-----*/
	public int CAR_SHWO_LIGHT=0; //ԭ��������
	public int CAR_SHWO_CONTARST = 0; // ԭ�����Աȶ�
	public int CAR_SHWO_SATURATION = 0; // ԭ�������Ͷ�
	/*--  ����ģʽ-----*/
	public String BLUETOOTH_PASSWARD="";      //�����������
	public String BLUETOOTH_NAME="";      //�����绰����
	public int BLUETOOTH_MODE=0;      //�����绰����
	
	/*--  ����ͷģʽ-----*/
	public int BACK_CAMERA_MODE = 0; // ������ͷģʽ
	public int LEFT_CAMERA_SWITCH = 0; // ������ͷ����
	
	/*-- onstar ������-----*/
	public int ONSTAR_STATUS= 0; // ������״̬
	public int ONSTAR_PHONE_TYPE = 0; // ������ͨ������
	public int ONSTAR_PHONE_SIGN = 0; // ������ͨ����־
	
	public int ONSTAR_PHONE_HOUR = 0; // ͨ��ʱ��-Сʱ
	public int ONSTAR_PHONE_MINUTE = 0; // ͨ��ʱ��-����
	public int ONSTAR_PHONE_SECOND = 0; // ͨ��ʱ��-��
	public int ONSTAR_LEFTIME_1 = 0; // ʣ��ʱ���λ
	public int ONSTAR_LEFTIME_2 = 0; // ʣ��ʱ���λ
	public int ONSTAR_EFFECTTIME_YEAR_1 = 0; // ��Ч��-�� ��λ
	public int ONSTAR_EFFECTTIME_YEAR_2 = 0; // ��Ч��-�� ��λ
	public int ONSTAR_EFFECTTIME_MOUTH = 0; // ��Ч��-��
	public int ONSTAR_EFFECTTIME_DAY = 0; // ��Ч��-��
	
	public int ONSTAR_WARING_STATUS = 0; // ������Ϣ״̬
	public int ONSTAR_WARING_TYPE = 0; // ������Ϣ����
	
	public String ONSTAR_RECEIVE_PHONE = ""; // ���պ���
	/*--  �ƹ��趨-----*/
	public int WIPER_LINK_LAMP = 0; //��ˢ���Զ�����������Ի��趨
	public int AUTO_LIGHT_SENSEITIVITY = 0; //�Զ���������������
	public int AUTO_LIGHTING_SENSEITIVITY = 0; //�Զ����������
	public int FRONT_LAMP_OFF_TIME = 0; //ǰ����Զ�Ϩ��ʱ��
	public int LAMP_TURN_DARK_TIME = 0; //���ڵƹ����ʱ��
	
	public int LOW_BEAM=0;//�����
	public int HIGH_BEAM=0;//Զ���
	public int CLEARANCE_LAMP=0;//ʾ��� 
	public int FRONT_FOG_LAMP=0;//ǰ��� 
	public int REAR_FOG_LAMP=0;//����� 
	public int STOP_LAMP=0;//ɲ����
	public int PARKING_LAMP=0;//������
	public int DAYTIME_RUNNING_LAMP=0;//�ռ��г���
	public int RIGHT_TURNING_SIGNAL_LAMP=0;//��ת���
	public int LEFT_TURNING_SIGNAL_LAMP=0;//��ת���
	public int DOUBLE_FLASH_LAMP=0;//˫����
	/*--  ��ý����Ϣ-----*/
	public int MULTI_MEIDA_SOURCE=0;           //��ǰԴ
	public int MULTI_MEIDA_PLAYING_NUM=0;       //��ǰ������Ŀ
	public int MULTI_MEIDA_WHOLE_NUM=0;       //����Ŀ
	public int MULTI_MEIDA_PLAYING_WHOLE_TIME=0;       //��ǰ��Ŀ��ʱ��
	public int MULTI_MEIDA_PLAYING_TIME=0;       //��ǰCD��Ŀ����ʱ��
	public int MULTI_MEIDA_PLAYING_MINUTE=0;       //��ǰ����ʱ�� ����
	public int MULTI_MEIDA_PLAYING_SECOND=0;       //��ǰ����ʱ�� ��
	public int MULTI_MEIDA_PLAYING_PROGRESS=0;       //��ǰ���Ž���
	public int MULTI_MEIDA_PLAYING_STATUS=0;       //��ý��״̬
	public int MULTI_MEIDA_CD_STATUS=0;       //��״̬
	/*--  ����-----*/
	public int LIGHT_SWITCH_ON_TIME=0;       //��ͨʱ��
	public int LIGHT_AUTO_HEADLIGHT_RAIN=0;       //�Զ��г���(����)
	public int LIGHT_LANE_CHANGE_FLASH=0;       //���ת���
	public int LIGHT_DAYTIME_RUNNING_LIGHT=0;       //���е�
	public int LIGHT_SWITCH_LIGHTING=0;       //�Ǳ�/��������
	public int LIGHT_COMING_HOME=0;          //�ؼ�ģʽ����
	public int LIGHT_LEAVING_HOME=0;         //���ģʽ����
	
	public int LIGHT_TRAVELING_MODE=0;      //����ģʽ
	public int LIGHT_DOOR_AMBIENT=0;     //���Ż���������
	public int LIGHT_FOORWELL_LIGHT=0;   //�Ų��ռ������Ƶ���
	public int LIGHT_DYNAMIC_LIGHT_ASSIST=0; //��̬�ƹ⸨��
	public int LIGHT_MOTORWAY_LIGHT=0;   //��̬����涯	
	public int LIGHT_TOP_LIGHT=0;   //��������������
	public int LIGHT_FRONT_LIGHT=0;   //ǰ������������
	public int LIGHT_LIGHT_COLOR=0;   //������ɫ
	public int LIGHT_CAR_ENV_COLOR=0;   //���ڷ�Χ����
	public int LIGHT_RIGHT_FRONT_COLOR=0;   //��ǰ�ҵ�����
	public int LIGHT_ALL_AREA=0;   //��������
	
	public int LIGHT_COMING_HOME_BACKUP=0;         //���һؼҵ�����
	public int LIGHT_COMING_HOME_DIPPED=0;         //���һؼҽ����
	public int LIGHT_COMING_HOME_REARFOG=0;         //���һؼҺ����
	public int LIGHT_SEEK_CAR_BACKUP=0;         //Ѱ����ָʾ������
	public int LIGHT_SEEK_CAR_DIPPED=0;         //Ѱ����ָʾ�����
	public int LIGHT_SEEK_CAR_REARFOG=0;         //Ѱ����ָʾ�����
	
	public int LIGHT_COMING_HOME_TIME=0;         //���һؼҳ���ʱ��
	public int LIGHT_SEEK_CAR_TIME=0;         //Ѱ����ָʾ�Ƴ���ʱ��
	/*--  ���Ӿ��͹�ˮ��-----*/
	public int MIRROR_SYNC_ADJUST=0;    //���Ӿ�ͬ������ 
	public int MIRROR_LOWER_WHILE_REVERSING=0;   //������ʱ���Ӿ����� 
	public int WIPER_AUTO_IN_RAIN=0;   //�����Զ���ˮ 
	public int WIPER_REAR_WIPING_REVERSING=0;   //������ʱ�󴰲�����ˮ 
	public int MIRROR_FOLD_PARKING=0;   //פ��ʱ����  
	/*-----��ʻ����ϵͳ�趨--------*/
	public int RADAR_PARK = 0;      //�״ﲴ��
	public int LANE_DEPARTURE = 0; //����ƫ�븨��ϵͳ�趨
	public int PAUSE_LKAS_SIGN = 0; //��ͣLKAS��ʾ��
	public int DETECT_FRONT_CAR = 0; //ACCǰ��֪̽��ʾ��
	public int FRONT_DANGER_WAIRNG_DISTANCE = 0; //�趨ǰ��Σ�վ������
	public int DRIVER_ALERT_SYSTEM = 0; //ƣ�ͼ�ʻʶ��ϵͳ
	public int LAST_DISTANCE_SELECTED = 0; //�ϴ�ѡ��ĳ���
	public int FRONT_ASSIST_ACTIVE = 0; //ǰ������ϵͳ����
	public int FRONT_ASSIST_ADVANCE_WARNING = 0; //ǰ������ϵͳԤ��
	public int FRONT_ASSIST_DISPLAY_DISTANCHE_WARNING = 0; //��ʾ���뱨��
	public int ACC_DRIVER_PROGRAM = 0; //ACC-��ʻ����
	public int ACC_DISTANCE = 0; //ACC-����
	/*-----פ���͵���--------*/
	public int FRONT_VOLUME = 0; //ǰ������
	public int FRONT_FREQUNENCY = 0; //ǰ��Ƶ��
	public int BACK_VOLUME = 0; //������
	public int BACK_FREQUNENCY = 0; //��Ƶ��
	public int PARKING_MODE = 0; //����ģʽ
	/*-----�򿪺͹ر�-------*/
	public int CONV_OPENING = 0; //������ݿ��� 
	public int DOOR_UNLOCKING = 0; //�������������Ž����� 
	public int AUTOMATIC_LOCKING = 0; //�Զ���ֹ 
	public int SEAT_KEY_REMOTE_FIX = 0; //����ң��Կ�׼���ƥ��
	public int INDUCTON_REAR_DOOR_COVER = 0; //��Ӧʽ��β���
	
	/*-----MFD�๦����ʾ-------*/
	public int MFD_CURRENT_CONSUMPTION = 0; //��ǰ�ͺ�  
	public int MFD_AVERAGE_CONSUMPTION = 0; //ƽ���ͺ�
	public int MFD_CONVENIENCE_CONSUMERS = 0; //�������õ��� 
	public int MFD_ECO_TIPS = 0; //����������ʾ
	public int MFD_TRAVELLING_TIME = 0; //��ʻʱ�� 
	public int MFD_DISTANCE_TRAVELED = 0; //��ʻ���
	public int MFD_AVERAGE_SPEED= 0; //ƽ���ٶ�
	public int MFD_DIGITAL_SPEED_DISPLAY= 0; //����ʽ������ʾ 
	public int MFD_SPEED_WARINING= 0; //���ٱ���
	public int MFD_OIL_TEMP= 0; //���� 
	
	/*------�г�ģʽ----------*/
	public int DRIVING_MODE_COMFORT= 0; //����
	public int DRIVING_MODE_NORMAL= 0; //��׼
	public int DRIVING_MODE_SPORT= 0; //�˶�
	public int DRIVING_MODE_ECO= 0; //����
	public int DRIVING_MODE_INDIVDUAL= 0; //���Ի�
	public int DRIVING_MODE=0; //ģʽ
	public int DRIVING_MODE_INDIVDUAL_DCC= 0; //DCC
	public int DRIVING_MODE_INDIVDUAL_DBL= 0; //��̬����
	public int DRIVING_MODE_INDIVDUAL_Engine= 0; //������
	public int DRIVING_MODE_INDIVDUAL_ACC= 0; //ACC
	public int DRIVING_MODE_INDIVDUAL_AirCon= 0; //�յ�
	public int DRIVING_MODE_INDIVDUAL_Steering= 0; //������
	/*-----��λ-------*/
	public int UNIT_DISTANCE= 0; //��� 
	public int UNIT_SPEED= 0; //����
//	public int UNIT_TEMPERATURE= 0; //�¶�
	public int UNIT_VOLUME= 0; //�ݻ�
//	public int UNIT_CONSUMPTION= 0; //�ͺ�
	public int UNIT_PRESSURE= 0; //��̥ѹ��
	public int UNIT_TYPE= 0;     //��λ��
	/*-----̥ѹ�趨-------*/
	public int TPMS_SHOW= 0; //��̥ѹ�������ʾ 
	public int TPMS_WINTER_SPEED_WARNING= 0; //������̥���ٱ���
	public int TPMS_WINTER_SPEED_WARNING_VOL= 0; //������̥���ٱ���ֵ
	/*-----���Ի�����------*/
	public int PROFILE_INFORMATION= 0; //��������
	public int INDIVIDUAL_ENGINE= 0; //����
	public int PROFILE_STEERING= 0; //������
	public int PROFILE_FRONT_LIGHT= 0; //ǰ��
	public int PROFILE_CLIMATE= 0; //����
	
	public int REMOTE_KEY= 0; //ң��Կ�׼���ƥ�� 
	public int KEY_ACTIVE= 0; //����Կ���Ѽ��� 
	/*-----��ʾ��״̬--------*/
	public int RATATIONAL_RATE = 0; //ת����ʾ
	public int MSG_NOTIFICATION = 0; //����Ϣ����
	public int ENGINEE_AUTO_CONTROL = 0; //�����������Զ���ͣ��ʾ
	public int ENERGY_BACKGROUND_LIGHT = 0; //����ģʽ�ı�������
	public int ADJUST_WARING_VOLUME = 0;   //������������  
	
	public int SWITCH_TRIPB_SETTING=0;     //���B�����������л�
	public int SWITCH_TRIPA_SETTING=0;     //���A�����������л�
	public int ADJUST_OUTSIDE_TEMP=0;     //�����ⲿ������ʾ
	
	/*------�Ǳ���ʾ�趨��Ϣ-----*/
	public int DISPLAY_ECO_MIXPOWER=0;     //��϶���ECOָʾ�趨
	public int DISPLAY_NAVI_MSG=0;     //�Ǳ�����Ϣ�趨
	public int DISPLAY_SPEED_RANG=0;     //�ٶȷ�Χ��ʾģʽ�趨
	/*------�˶�ģʽ-----*/
	public int SPORT_ENGINE_STATUS=0;     //�˶�ģʽ������״̬�趨
	public int SPORT_BACKLIGHT_MODE=0;     //�˶�ģʽ����ģʽ�趨
	
	public int CAR_TYPE=0;   //��������
	
	public int AIR_CONDITIONER_CONTROL = 0; // �յ��Ƿ��н�����ƣ����Ϊ1���У�����Ҫ�Զ�����
	
	/*----�յ� ����-----*/
	public int AIRCON_AUTO_WIND=0;     //�Զ�����ģʽ�趨
	public int AIRCON_MODE=0;          //�յ�ģʽ�趨
	public int AIRCON_AIR_QUALITY=0;   //���������������趨
	public int AIRCON_AUTOZONE_TEMP=0; //�Զ������¶��趨
	public int AIRCON_SEAT_AUTOVENT=0;          //�����Զ�ͨ���趨
	public int AIRCON_SEAT_AUTOHEAT=0;          //�����Զ������趨
	public int AIRCON_CONTORL_AUTOVENT=0;          //ң�����������Զ�ͨ��
	public int AIRCON_CONTORL_AUTOHEAT=0;          //ң�����������Զ�����
	
	public int AIRCON_BACKAREA_TEMP=0;          //���������¶�
	public int AIRCON_FRONT_DEMIST=0;          //ǰ���Զ�ȥ��
	public int AIRCON_BACK_DEMIST=0;          //���Զ�ȥ��
	public int AIRCON_REMOTE_START=0;          //ң�������յ�
	public int AIRCON_AIR_QUALITY_1=0;          //��������������1�趨
	public int AIRCON_CONTORL_AUTOHEAT_1=0;          //ң�����������Զ�����1
	
	/*----��ײ/���ϵͳ----*/
	public int CRASHPROOF_SIDE_BLIND_AREA=0;          //��ä������ϵͳ
	public int CRASHPROOF_WARING=0;          //��ײ���������趨
	public int CRASHPROOF_PART_ASSIT=0;          //��������ϵͳ�趨�����ϳ�������
	public int CRASHPROOF_24GHZ_RADAR=0;          //24GHZ�״��趨
	public int CRASHPROOF_AUTO_READY=0;          //�Զ���ײ׼��
	public int CRASHPROOF_CARSTATUS_NOTIFY=0;          //����״̬֪ͨ
	public int CRASHPROOF_RAMPWAY_ASSIT=0;          //�µ�����ϵͳ
	/*----������ʾ��Ϣ----*/
	public int WARNING_MES_NUM=0;        //��ʾ��Ϣ����Ŀ   
	public int WARNING_MES_0=0;
	public int WARNING_MES_1=0; 
	public int WARNING_MES_2=0; 
	public int WARNING_MES_3=0; 
	public int WARNING_MES_4=0; 
	public int WARNING_MES_5=0; 
	public int START_STOP_MES=0;
	
	public int CONV_WARNING_MES_NUM=0;     
	public int CONV_WARNING_MES_0=0;
	public int CONV_WARNING_MES_1=0; 
	public int CONV_WARNING_MES_2=0;
	/*----������/�������趨----*/
	public int CONVENIENCE_SEAT_PARK_MOVE=0;     //��ʻԱ����ͣ����λ�趨
	public int CONVENIENCE_RIPE_PARK_MOVE=0;     //ת������복��λ�趨
	public int CONVENIENCE_OUTERMIRROR_PARK_LEAN=0;     //����Ӿ������Զ���б�趨
	public int CONVENIENCE_OUTERMIRROR_PARK_FOLD=0;     //����Ӿ��Զ��۵��趨
	public int CONVENIENCE_DRIVER_PRIVATE_SETTING=0;     //��ʻԱ���������趨
	public int CONVENIENCE_REVERSE_BACKWIPE_AUTO=0;     //�����Զ�����ˢ
	public int CONVENIENCE_RIPE_PARK_LEAN=0;     //ת������복��б�趨
	public int CONVENIENCE_AUTO_WIPE=0;     //�Զ����
	public int CONVENIENCE_CONSUMERS = 0; //�������õ��� 
	public int CONVENIENCE_CONSUMERS_UNIT = 0; //�������õ�����λ
	public int CONVENIENCE_PERCENT = 0; //�����ͺĿ̶����ֵ
	public float FREQUENCY_VALUE=0;              //Ƶ��ֵ
	public float TEMP_UNIT=0;              //�¶ȵ�λ
	
	public int ESC_SYSTEM=0;             //ESCϵͳ
	public int TYPES_SPEED_WARNING=0;             //TYPES�ٶ�Ԥ��
	public int TYPES_SPEED_UNIT=0;             //TYPES�ٶȵ�λ
	public int TYPES_SPEED=0;             //TYPES�ٶ�
	
	public int THROTTLE_CONTROL=0;        //������λ�� 
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
	
	public int SOS_STATUS=0;									//SOS״̬ 0�ر� 1���� 2�Ѿ�����
	public int LINK_SOS_STATUS=0;								//Link SoSͨѶ    0�˳�  1LINK  2SOS
	public int LANGUAGE_CHANGE=1;								//��������1���� 2 Ӣ��
	public int WARNING_ID=0;									//�澯��Ϣ
	
	public int AUTO_COMPRESSOR_STATUS=0;						//AUTOʱѹ����״̬    1�� 0��
	public int AUTO_CYCLE_STATUS=0;								//AUTOʱ����ѭ�����Ʒ�ʽ   1�Զ�  0�ֶ�
	public int AIR_COMFORTABLE_STATUS=0;						//�յ�������������  00���� 01���� 02����
	public int AIR_ANION_STATUS=0;								//������ģʽ  1����  0�ر�
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
	public int CAR_LOCK_AUTO_STATUS=0;							//�����Զ����� 1���� 0ͣ��	
	public int CAR_LOCK_STATUS=0;								//�������� 1���� 0ͣ��
	public int CAR_LOCK_AUTO_STATUS_ENABLE;						//�����Զ����� 1��Ч 0��Ч
	public int CAR_LOCK_STATUS_ENABLE;							//�������� 1��Ч 0��Ч
	public int CAR_UNLOCK_STATUS_ENABLE;						//���Ž��� 1��Ч 0��Ч
	
	public int REMOTE_FRONT_LEFT=0;								//ң����ǰ�����촰   1�� 0��
	public int FRONT_WIPER_CARE=0;								//ǰ���ά������  1��  0��
	public int REAR_WIPER_STATUS=0;								//����ε����Զ���ˢ����  1�� 0��
	public int OUTSIDE_MIRROR_STATUS=0;							//����Ӿ��Զ��۵�  1�� 0��
	public int REAR_WIPER_STATUS_ENABLE;						//�����Զ������ 1��Ч 0��Ч
	
	public int FOG_LAMP_STATUS=0;								//���ת����  1�� 0��
	public int DAYTIME_LAMP_STATUS=0;							//�ռ��г���  1�� 0��
	public int AUTO_LAMP_STATUS;								//�Զ��ƹ�������  0��  1�� 2��
	public int GO_HOME_LAMP_STATUS=0;							//���һؼ�����  0�ر� 1�������  2�������
	public int WELCOME_PERSION_ILL_STATUS=0;					//ӭ������ 00ȡ�� 01b 15s 10b 30s 11b 60s
	public int ATMOSPHERE_ILL_STATUS=0;							//�������� 1���� 0ͣ��
	public int ATMOSPHERE_ILL_VALUE=0;							//��������ֵ
	public int CHANGE_ILL_STATUS=0;								//�涯ת������������ 1���� 0ͣ��
	public int DAYTIME_LAMP_STATUS_ENABLE;						//�ռ��г��� 1��Ч 0��Ч
	public int GO_HOME_LAMP_STATUS_ENABLE;						//���һؼ����� 1��Ч 0��Ч
	public int WELCOME_PERSION_ILL_STATUS_ENABLE;				//ӭ������ 1��Ч 0��Ч
	public int ATMOSPHERE_ILL_STATUS_ENABLE;					//��Χ���� 1��Ч 0��Ч
	public int CHANGE_ILL_STATUS_ENABLE;						//�涯ת������������ 1��Ч0��Ч
	
	
	public int ESP_ENABLE=1;                                    //������ת��  1��Ч 0��Ч
	public int HOLOGRAM_ENABLE=1;								//ȫϢӰ������ 1��Ч 0��Ч
	public int CAT_SETTTING_ENABLE=1;							//����������Ϣ 1���� 0��Ч
	public int PANEL_SSETTING_ENABLE=1;							//��尴����Ϣ 1��Ч 0��Ч
	public int AIR_INFO_ENABLE=1;								//�յ���Ϣ  1��Ч 0��Ч
	
	public int CAMERA_DIAPLAY_ENABLE=0;							//����ͷ��ʾ����  0�� 1��
	public int CAMERA_MODE=1;									//ȫ������ͷģʽ 1ǰ+ȫ    2ǰ+�Ҳ�     3��+ȫ     4 ˮƽͣ��+ȫ     5��ֱͣ��+ȫ
	
	public int KEY_IN=0;										//Կ�ײ���״̬ 1���� 0�γ�
	public int CAR_ACC_STATUS=0;								//ACC���� 1�ϵ� 0û�ϵ�
	
	public int AUTO_PARK_CAR_STATUS=0;							//�Զ�פ�� 1���� 0ͣ��
	public int AUTO_PARK_CAR_STATUS_ENABLE=0;					//�Զ�פ�� 1��Ч 0��Ч
	public int ENGINE_START_STATUS_ENABLE=0;					//��������ͣͣ�ù���ʹ�� 1��Ч 0��Ч
	public int ENGINE_START_STATUS=0;							//��������ͣͣ���趨
	
	public int PARKING_STATUS_ENABLE;							//פ������ 1��Ч 0��Ч
	public int TRUNK_UNLOCK_STATUS_ENABLE;						//������������������� 1��Ч0��Ч
	public int CHANGE_LINE_STATUS_ENABLE;						//������� 1��Ч0��Ч
	public int WELCOME_FUNTION_STATUS_ENABLE;					//ӭ������ 1��Ч 0��Ч
	
	public int PARKING_STATUS=0;								//פ������ 1���� 0ͣ��
	public int TRUNK_UNLOCK_STATUS=0;							//������������������� 1���� 0ͣ��
	public int CHANGE_LINE_STATUS;								//������� 1���� 0ͣ��
	public int WELCOME_FUNTION_STATUS;							//ӭ������ 1���� 0ͣ��
	
	public int REMEMBER_SPEED_STATUS=0;							//�Ѽ�����ٶ�ֵ���� 1�� 0��
	public int REMEMBER_SPEED_1=0;								//�����ٶ�1  1ѡ�� 0δѡ��
	public int REMEMBER_SPEED_2=0;								//�����ٶ�2  1ѡ�� 0δѡ��
	public int REMEMBER_SPEED_3=0;								//�����ٶ�3  1ѡ�� 0δѡ��
	public int REMEMBER_SPEED_4=0;								//�����ٶ�4  1ѡ�� 0δѡ��
	public int REMEMBER_SPEED_5=0;								//�����ٶ�5  1ѡ�� 0δѡ��
	public int REMEMBER_SPEED_6=0;								//�����ٶ�6  1ѡ�� 0δѡ��
	public int REMEMBER_SPEED_1_VALUE=0;								//�����ٶ�ֵ1  
	public int REMEMBER_SPEED_2_VALUE=0;								//�����ٶ�ֵ2  
	public int REMEMBER_SPEED_3_VALUE=0;								//�����ٶ�ֵ3  
	public int REMEMBER_SPEED_4_VALUE=0;								//�����ٶ�ֵ4  
	public int REMEMBER_SPEED_5_VALUE=0;								//�����ٶ�ֵ5  
	public int REMEMBER_SPEED_6_VALUE=0;								//�����ٶ�ֵ6  
	public int REMEMBER_SPEED_STATUS_ENABLE=0;					//�Ѽ�����ٶ�ֵʹ�� 1��Ч 0��Ч
	public int REMEMBER_SPEED_1_ENABLE=0;						//ʹ���ٶ�1  1��Ч 0��Ч
	public int REMEMBER_SPEED_2_ENABLE=0;						//ʹ���ٶ�2  1��Ч 0��Ч
	public int REMEMBER_SPEED_3_ENABLE=0;						//ʹ���ٶ�3  1��Ч 0��Ч
	public int REMEMBER_SPEED_4_ENABLE=0;						//ʹ���ٶ�4  1��Ч 0��Ч
	public int REMEMBER_SPEED_5_ENABLE=0;						//ʹ���ٶ�5  1��Ч 0��Ч
	public int REMEMBER_SPEED_6_ENABLE=0;						//ʹ���ٶ�6  1��Ч 0��Ч
	
	public int CRUISE_SPEED_STATUS=0;							//Ѳ���ٶ�ֵ���� 1�� 0��
	public int CRUISE_SPEED_1=0;								//�����ٶ�1  1ѡ�� 0δѡ��
	public int CRUISE_SPEED_2=0;								//�����ٶ�2  1ѡ�� 0δѡ��
	public int CRUISE_SPEED_3=0;								//�����ٶ�3  1ѡ�� 0δѡ��
	public int CRUISE_SPEED_4=0;								//�����ٶ�4  1ѡ�� 0δѡ��
	public int CRUISE_SPEED_5=0;								//�����ٶ�5  1ѡ�� 0δѡ��
	public int CRUISE_SPEED_6=0;								//�����ٶ�6  1ѡ�� 0δѡ��
	public int CRUISE_SPEED_1_VALUE=0;								//�����ٶ�ֵ1  
	public int CRUISE_SPEED_2_VALUE=0;								//�����ٶ�ֵ2  
	public int CRUISE_SPEED_3_VALUE=0;								//�����ٶ�ֵ3  
	public int CRUISE_SPEED_4_VALUE=0;								//�����ٶ�ֵ4  
	public int CRUISE_SPEED_5_VALUE=0;								//�����ٶ�ֵ5 
	public int CRUISE_SPEED_6_VALUE=0;								//�����ٶ�ֵ6 
	public int CRUISE_SPEED_STATUS_ENABLE=0;					//Ѳ���ٶ�ֵʹ�� 1��Ч 0��Ч
	public int CRUISE_SPEED_1_ENABLE=0;							//ʹ���ٶ�1  1��Ч 0��Ч
	public int CRUISE_SPEED_2_ENABLE=0;							//ʹ���ٶ�2  1��Ч 0��Ч
	public int CRUISE_SPEED_3_ENABLE=0;							//ʹ���ٶ�3  1��Ч 0��Ч
	public int CRUISE_SPEED_4_ENABLE=0;							//ʹ���ٶ�4  1��Ч 0��Ч
	public int CRUISE_SPEED_5_ENABLE=0;							//ʹ���ٶ�5  1��Ч 0��Ч
	public int CRUISE_SPEED_6_ENABLE=0;							//ʹ���ٶ�6  1��Ч 0��Ч
	
	public int UNIT_TEMPERATURE_ENABLE=1;						//Unit Step�趨��Ϣ Temperature 1��Ч 0��Ч
	public int UNIT_CONSUMPTION_ENABLE=1;						//Unit Step�趨��Ϣ Consumption 1��Ч 0��Ч
	public int UNIT_TEMPERATURE=1;								//Unit Stepѡ����Ϣ Temperature 1:DegC  0:F
	public int UNIT_CONSUMPTION=1;								//Unit Stepѡ����Ϣ Consumption  00:l/100km   01bkm/l  10b:mpg(us)
	
	public int TIME_YEAR=0;									//ʱ�� ��20xx
	public int TIME_MONTH=0;								//ʱ�� ��		
	public int TIME_DAY=0;									//ʱ�� ��
	public int TIME_HOUR=0;									//ʱ�� ʱ
	public int TIME_MINUTE;									//ʱ�� ��
	public int TIME_FORMAT;									//ʱ���ʽ
	public int CRITICAL_PARK_ENABLE;						//�����ƶ���ʹ��
	public int AUTO_LOCK_SETTING_ENABLE;					//�Զ�����ʹ��
	public int FRONT_LAMP_DELAY_ENABLE;						//ǰ�յ��ӳ�ʹ��
	public int TURN_START_AVM_ENABLE;						//ת������AVMʹ��
	public int TURN_START_ANIMATION_ENABLE;						//ת����������ʹ��
	public int SELECTOR_CAR_ASSIST_ENABLE; 						//ѡ����������ʹ��
	public int SPEED_OVER_SETTING_ENABLE; 						//���ٱ���ʹ��
	
	public int CRITICAL_PARK_STATUS;						//�����ƶ���������
	public int CRITICAL_PARK_MODE;							//�����ƶ�������ʽ
	public int FRONT_LAMP_DELAY_STATUS;						//ǰ�յ��ӳ�
	public int TURN_START_AVM_STATUS;						//ת������AVM
	public int TURN_START_ANIMATION_STATUS;						//ת����������
	public int SELECTOR_CAR_ASSIST_STATUS; 						//ѡ����������
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
		dest.writeInt(CAMERA_MODE);	
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
		CAMERA_MODE= in.readInt();
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
