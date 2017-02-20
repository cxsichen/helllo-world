package com.console.canreader.bean;

import java.io.UnsupportedEncodingException;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;
import com.console.canreader.utils.Contacts;

import android.util.Log;

public class SSJeepFreedom extends AnalyzeUtils {
	// 数据类型
	public static final int comID = 3;
	// DataType
	// 方向盘按键
	public static final int STEERING_BUTTON_DATA = 0x11;
	// 车身信息
	public static final int CAR_INFO_DATA = 0x12;
	// 车身基本信息
	public static final int CAR_INFO_DATA_2 = 0x21;
	// 车身基本信息
	public static final int CAR_INFO_DATA_4 = 0x22;
	// 空调信息
	public static final int AIR_CONDITIONER_DATA = 0x31;
	// 车身基本信息
	public static final int CAR_INFO_DATA_5 = 0x32;
	// 车身基本信息
	public static final int CAR_INFO_DATA_6 = 0x41;
	// 车身基本信息
	public static final int CAR_INFO_DATA_7 = 0x43;
	// 车身基本信息
	public static final int CAR_INFO_DATA_8 = 0x62;
	// 车身基本信息
	public static final int CAR_INFO_DATA_9 = 0x60;
	// 车身基本信息
	public static final int CAR_INFO_DATA_10 = 0xAE;
	// 车身基本信息
	public static final int CAR_INFO_DATA_11 = 0xC1;
	// 车身基本信息
    public static final int CAR_INFO_DATA_12 = 0xA6;
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

		mCanInfo.EQL_VOLUME =  (int) (msg[4] >> 0) & 0xFF; // 音量
		mCanInfo.LR_BALANCE =  (int) (msg[5] >> 0) ; // 左右平衡
		mCanInfo.FB_BALANCE =  (int) (msg[6] >> 0) ; // 前后平衡
		mCanInfo.BAS_VOLUME =  (int) (msg[7] >> 0); // 低音值
		mCanInfo.MID_VOLUME = (int) (msg[8] >> 0) ;// 中音值
		mCanInfo.TRE_VOLUME =  (int) (msg[9] >> 0); // 高音值
		mCanInfo.VOL_LINK_CARSPEED =  (int) (msg[10] >> 1) & 0x03; // 音量与车速联动
		mCanInfo.DSP_SURROUND = (int) (msg[10] >> 0) & 0x01;// DSP环绕

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
		//胎压单位
		mCanInfo.UNIT_PRESSURE= (int) (msg[4] >> 0) & 0xFF;
		//温度单位
		mCanInfo.UNIT_TEMPERATURE= (int) (msg[5] >> 0) & 0xFF;
		//油耗单位
		mCanInfo.UNIT_CONSUMPTION= (int) (msg[6] >> 0) & 0xFF;
		//单位制
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

		// 碟状态
		mCanInfo.MULTI_MEIDA_CD_STATUS = ((int) ((msg[4] >> 0) & 0x01) == 0) ? -1
				: (int) ((msg[7] >> 0) & 0xFF);
		// 播放状态
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

		// 锁车时发出提示音
		mCanInfo.REMOTELOCK_BEEP_SIGN = ((int) ((msg[4] >> 5) & 0x01) == 0) ? -1
				: (int) ((msg[5] >> 5) & 0x03);
		// 车门报警
		mCanInfo.REMOTELOCK_SIDELAMP_SIGN = ((int) ((msg[4] >> 4) & 0x01) == 0) ? -1
				: (int) ((msg[5] >> 4) & 0x01);
		// 无钥匙进入
		mCanInfo.SPEECH_WARING_VOLUME = ((int) ((msg[4] >> 3) & 0x01) == 0) ? -1
				: (int) ((msg[5] >> 3) & 0x01);
		// 原车解锁
		mCanInfo.REMOTE_START_SYSTEM = ((int) ((msg[4] >> 2) & 0x01) == 0) ? -1
				: (int) ((msg[5] >> 2) & 0x01);
		// 下车时自动解锁
		mCanInfo.REMOTECONTROL_LOCK_FEEDBACK = ((int) ((msg[4] >> 1) & 0x01) == 0) ? -1
				: (int) ((msg[5] >> 1) & 0x01);
		// 自动车门锁定
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

		// 车内氛围灯
		mCanInfo.ATMOSPHERE_ILL_STATUS = ((int) ((msg[4] >> 6) & 0x01) == 0) ? -1
				: (int) ((msg[7] >> 5) & 0x07);
		// 前照灯敏感度
		mCanInfo.ATMOSPHERE_ILL_VALUE = ((int) ((msg[4] >> 5) & 0x01) == 0) ? -1
				: (int) ((msg[6] >> 6) & 0x03);
		// 转角辅助灯
		mCanInfo.CHANGE_ILL_STATUS = ((int) ((msg[4] >> 4) & 0x01) == 0) ? -1
				: (int) ((msg[5] >> 4) & 0x01);
		// 解锁开启车灯
		mCanInfo.DAYTIME_LAMP_STATUS_ENABLE = ((int) ((msg[4] >> 3) & 0x01) == 0) ? -1
				: (int) ((msg[7] >> 2) & 0x01);
		// 发动机关闭电源延迟
		mCanInfo.GO_HOME_LAMP_STATUS_ENABLE = ((int) ((msg[4] >> 2) & 0x01) == 0) ? -1
				: (int) ((msg[6] >> 4) & 0x03);
		// 靠近时大灯亮起
		mCanInfo.WELCOME_PERSION_ILL_STATUS_ENABLE = ((int) ((msg[4] >> 1) & 0x01) == 0) ? -1
				: (int) ((msg[6] >> 2) & 0x03);
		// 大灯关闭延迟
		mCanInfo.ATMOSPHERE_ILL_STATUS_ENABLE = ((int) ((msg[4] >> 0) & 0x01) == 0) ? -1
				: (int) ((msg[6] >> 0) & 0x03);

		// 锁车时转向灯闪烁
		mCanInfo.CHANGE_ILL_STATUS_ENABLE = ((int) ((msg[5] >> 3) & 0x01) == 0) ? -1
				: (int) ((msg[7] >> 4) & 0x01);
		// 日间行驶灯
		mCanInfo.ESP_ENABLE = ((int) ((msg[5] >> 2) & 0x01) == 0) ? -1
				: (int) ((msg[7] >> 3) & 0x01);
		// 自动防眩光灯
		mCanInfo.HOLOGRAM_ENABLE = ((int) ((msg[5] >> 1) & 0x01) == 0) ? -1
				: (int) ((msg[7] >> 1) & 0x01);
		// 启动雨刷时自动启动大灯
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
			mCanInfo.RADAR_PARK = -1; // 雷达泊车
		} else {
			mCanInfo.RADAR_PARK = (int) ((msg[7] >> 6) & 0x03);
		}

		if ((int) ((msg[4] >> 3) & 0x01) == 0) {
			mCanInfo.LANE_DEPARTURE = -1; // 车道偏离校正力度
		} else {
			mCanInfo.LANE_DEPARTURE = (int) ((msg[7] >> 4) & 0x03);
		}

		if ((int) ((msg[4] >> 2) & 0x01) == 0) {
			mCanInfo.PAUSE_LKAS_SIGN = -1; // 车道偏离警告
		} else {
			mCanInfo.PAUSE_LKAS_SIGN = (int) ((msg[7] >> 2) & 0x03);
		}

		if ((int) ((msg[4] >> 1) & 0x01) == 0) {
			mCanInfo.DETECT_FRONT_CAR = -1; // 前方碰撞报警自动制动
		} else {
			mCanInfo.DETECT_FRONT_CAR = (int) ((msg[7] >> 1) & 0x01);
		}

		if ((int) ((msg[4] >> 0) & 0x01) == 0) {
			mCanInfo.FRONT_DANGER_WAIRNG_DISTANCE = -1; // 前方碰撞报警警告
		} else {
			mCanInfo.FRONT_DANGER_WAIRNG_DISTANCE = (int) ((msg[7] >> 0) & 0x01);
		}

		// 自动驻车制动
		mCanInfo.AUTO_PARK_CAR_STATUS = ((int) ((msg[5] >> 6) & 0x01) == 0) ? -1
				: (int) ((msg[8] >> 7) & 0x01);
		// 坡道起步辅助
		mCanInfo.PARKING_STATUS = ((int) ((msg[5] >> 5) & 0x01) == 0) ? -1
				: (int) ((msg[8] >> 6) & 0x01);
		// 雨量感应式雨刷
		mCanInfo.CONVENIENCE_REVERSE_BACKWIPE_AUTO = ((int) ((msg[5] >> 4) & 0x01) == 0) ? -1
				: (int) ((msg[8] >> 5) & 0x01);

		// 影像泊车动态引导线
		mCanInfo.CONVENIENCE_SEAT_PARK_MOVE = ((int) ((msg[5] >> 2) & 0x01) == 0) ? -1
				: (int) ((msg[8] >> 3) & 0x01);
		// 影像泊车固定引导线
		mCanInfo.CONVENIENCE_RIPE_PARK_MOVE = ((int) ((msg[5] >> 1) & 0x01) == 0) ? -1
				: (int) ((msg[8] >> 2) & 0x01);
		// 盲点报警
		mCanInfo.CRASHPROOF_SIDE_BLIND_AREA = ((int) ((msg[5] >> 0) & 0x01) == 0) ? -1
				: (int) ((msg[8] >> 0) & 0x03);

		// 自动开启舒适系统
		mCanInfo.FOG_LAMP_STATUS = ((int) ((msg[6] >> 4) & 0x01) == 0) ? -1
				: (int) ((msg[9] >> 6) & 0x03);
		// 缩回手刹允许制动系统服务
		mCanInfo.DAYTIME_LAMP_STATUS = ((int) ((msg[6] >> 3) & 0x01) == 0) ? -1
				: (int) ((msg[9] >> 5) & 0x01);
		// 后ParkSense雷达泊车制动辅助
		mCanInfo.AUTO_LAMP_STATUS = ((int) ((msg[6] >> 2) & 0x01) == 0) ? -1
				: (int) ((msg[9] >> 4) & 0x01);
		// 后ParkSense音量
		mCanInfo.GO_HOME_LAMP_STATUS = ((int) ((msg[6] >> 1) & 0x01) == 0) ? -1
				: (int) ((msg[9] >> 2) & 0x03);
		// 前ParkSense音量
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
		mCanInfo.EQL_VOLUME = (int) (msg[4] & 0xFF); // 音量
		mCanInfo.LR_BALANCE = (int) (msg[5] & 0xFF); // 左右平衡
		mCanInfo.FB_BALANCE = (int) (msg[6] & 0xFF); // 前后平衡
		mCanInfo.BAS_VOLUME = (int) (msg[7] & 0xFF); // 低音值
		mCanInfo.MID_VOLUME = (int) (msg[8] & 0xFF); // 中音值
		mCanInfo.TRE_VOLUME = (int) (msg[9] & 0xFF); // 高音值
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
	 * 方向盘按键 STEERING_BUTTON_MODE 0：无按键或释放 1：vol+ 2：vol- 3：menuup 4：menu down 5：
	 * PHONE 6：mute 7：SRC 8：SPEECH/MIC 9:answer phone 10:hangup phone
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

		mCanInfo.LEFT_SEAT_TEMP = (int) ((msg[6] >> 0) & 0x03); // 左座椅温度
		mCanInfo.RIGTHT_SEAT_TEMP = (int) ((msg[6] >> 2) & 0x03); // 右座椅温度

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
