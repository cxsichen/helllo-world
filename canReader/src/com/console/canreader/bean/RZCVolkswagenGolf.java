package com.console.canreader.bean;

import java.io.UnsupportedEncodingException;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;
import com.console.canreader.utils.Contacts;

import android.util.Log;

public class RZCVolkswagenGolf extends AnalyzeUtils {

	// 数据类型
	public static final int comID = 1;
	// DataType
	// 方向盘按键
	public final static int STEERING_BUTTON_DATA = 0x20;
	public final static int STEERING_BUTTON_DATA_1 = 0x2F;
	// 空调信息
	public final static int AIR_CONDITIONER_DATA = 0x21;
	// 后雷达信息
	public final static int BACK_RADER_DATA = 0x22;
	// 前雷达信息
	public final static int FRONT_RADER_DATA = 0x23;
	// 车身信息
	public final static int CAR_INFO_DATA = 0x24;
	public final static int CAR_INFO_DATA_1 = 0x16;
	public final static int CAR_INFO_DATA_2 = 0x27;
	public final static int CAR_INFO_DATA_3 = 0x50;
	public final static int CAR_INFO_DATA_4 = 0x63;
	// 方向盘转角
	public final static int STEERING_TURN_DATA = 0x29;
	// 软件版本
	public final static int CAR_INFO_DATA_5 = 0x30;
	public final static int CAR_INFO_DATA_6 = 0x40;
	public final static int CAR_INFO_DATA_7 = 0x60;
	public final static int CAR_INFO_DATA_8 = 0x62;
	public final static int CAR_INFO_DATA_9 = 0x65;

	public CanInfo getCanInfo() {
		return mCanInfo;
	}

	@Override
	public void analyzeEach(byte[] msg) {
		// TODO Auto-generated method stub
		try {
			if (msg == null)
				return;
			switch ((int) (msg[comID] & 0xFF)) {
			case STEERING_BUTTON_DATA:
				mCanInfo.CHANGE_STATUS = 2;
				analyzeSteeringButtonData(msg);
				break;
			case STEERING_BUTTON_DATA_1:
				mCanInfo.CHANGE_STATUS = 2;
				analyzeSteeringButtonData_1(msg);
				break;
			case AIR_CONDITIONER_DATA:
				mCanInfo.CHANGE_STATUS = 3;
				analyzeAirConditionData(msg);
				break;
			case BACK_RADER_DATA:
				mCanInfo.CHANGE_STATUS = 4;
				analyzeBackRaderData(msg);
				break;
			case FRONT_RADER_DATA:
				mCanInfo.CHANGE_STATUS = 5;
				analyzeFrontRaderData(msg);
				break;
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
			case STEERING_TURN_DATA:
				mCanInfo.CHANGE_STATUS = 8;
				analyzeSteeringTurnData(msg);
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

	void analyzeCarInfoData_2(byte[] msg) {
		mCanInfo.OUTSIDE_TEMPERATURE = ((int) (msg[5] << 8 | ((int) msg[4] & 0xFF))) / 10;
	}

	void analyzeCarInfoData_3(byte[] msg) {

		switch (((int) msg[3] & 0xFF)) {
		case 0x10:
			mCanInfo.RANGE_UNIT = ((int) msg[4] & 0x01);
			mCanInfo.RANGE = ((int) msg[6] & 0xFF) << 8 | ((int) msg[5] & 0xFF);
			break;
		case 0x20:
			mCanInfo.DISTANCE_UNIT = ((int) msg[4] & 0x01);
			mCanInfo.DISTANCE_SINCE_START = (((int) (msg[5] & 0xFF))
					+ (((int) (msg[6] & 0xFF)) << 8)
					+ (((int) (msg[7] & 0xFF)) << 16) + (((int) (msg[8] & 0xFF)) << 24)) / 10;
			break;
		case 0x21:
			mCanInfo.DISTANCE_UNIT = ((int) msg[4] & 0x01);
			mCanInfo.DISTANCE_SINCE_REFUELING = (((int) (msg[5] & 0xFF))
					+ (((int) (msg[6] & 0xFF)) << 8)
					+ (((int) (msg[7] & 0xFF)) << 16) + (((int) (msg[8] & 0xFF)) << 24)) / 10;
			break;
		case 0x22:
			mCanInfo.DISTANCE_UNIT = ((int) msg[4] & 0x01);
			mCanInfo.DISTANCE_LONG_TERM = (((int) (msg[5] & 0xFF))
					+ (((int) (msg[6] & 0xFF)) << 8)
					+ (((int) (msg[7] & 0xFF)) << 16) + (((int) (msg[8] & 0xFF)) << 24)) / 10;
			break;
		case 0x30:
			mCanInfo.CONSUMPTION_UNIT = ((int) msg[4] & 0x03);
			mCanInfo.CONSUMPTION_SINCE_START = (((int) (msg[5] & 0xFF)) + (((int) (msg[6] & 0xFF)) << 8)) / 10;
			break;
		case 0x31:
			mCanInfo.CONSUMPTION_UNIT = ((int) msg[4] & 0x03);
			mCanInfo.CONSUMPTION_SINCE_REFUELING = (((int) (msg[5] & 0xFF)) + (((int) (msg[6] & 0xFF)) << 8)) / 10;
			break;
		case 0x32:
			mCanInfo.CONSUMPTION_UNIT = ((int) msg[4] & 0x03);
			mCanInfo.CONSUMPTION_LONG_TERM = (((int) (msg[5] & 0xFF)) + (((int) (msg[6] & 0xFF)) << 8)) / 10;
			break;
		case 0x40:
			mCanInfo.SPEED_UNIT = ((int) msg[4] & 0x01);
			mCanInfo.SPEED_SINCE_START = (((int) (msg[5] & 0xFF)) + (((int) (msg[6] & 0xFF)) << 8)) / 10;
			break;
		case 0x41:
			mCanInfo.SPEED_UNIT = ((int) msg[4] & 0x01);
			mCanInfo.SPEED_SINCE_REFUELINGT = (((int) (msg[5] & 0xFF)) + (((int) (msg[6] & 0xFF)) << 8)) / 10;
			break;
		case 0x42:
			mCanInfo.SPEED_UNIT = ((int) msg[4] & 0x01);
			mCanInfo.SPEED_LONG_TERM = (((int) (msg[5] & 0xFF)) + (((int) (msg[6] & 0xFF)) << 8)) / 10;
			break;
		case 0x50:
			mCanInfo.TRAVELLINGTIME_SINCE_START = (((int) (msg[4] & 0xFF))
					+ (((int) (msg[5] & 0xFF)) << 8) + (((int) (msg[6] & 0xFF)) << 16));
			break;
		case 0x51:
			mCanInfo.TRAVELLINGTIME_SINCE_REFUELINGT = (((int) (msg[4] & 0xFF))
					+ (((int) (msg[5] & 0xFF)) << 8) + (((int) (msg[6] & 0xFF)) << 16));
			break;
		case 0x52:
			mCanInfo.TRAVELLINGTIME_LONG_TERM = (((int) (msg[4] & 0xFF))
					+ (((int) (msg[5] & 0xFF)) << 8) + (((int) (msg[6] & 0xFF)) << 16));
			break;
		case 0x60:
			mCanInfo.CONVENIENCE_CONSUMERS = ((int) (msg[5] & 0xFF))
					+ (((int) (msg[6] & 0xFF)) * 256); // 舒适性用电器
			mCanInfo.CONVENIENCE_CONSUMERS_UNIT = ((int) msg[4] & 0x01);
			; // 舒适性用电器
			break;
		case 0x61:
			mCanInfo.INSTANT_CONSUMPTION_UNIT = ((int) msg[4] & 0x03); // 瞬时油耗单位
			mCanInfo.INSTANT_CONSUMPTION = (((int) (msg[5] & 0xFF)) + (((int) (msg[6] & 0xFF)) * 256)) / 10; // 瞬时油耗
			break;
		default:
			break;
		}
	}

	void analyzeCarInfoData_9(byte[] msg) {
		mCanInfo.TPMS_FL_WARING = (int) msg[3] & 0xFF; // 前左车轮报警
		mCanInfo.TPMS_FR_WARING = (int) msg[4] & 0xFF; // 前右车轮报警
		mCanInfo.TPMS_BL_WARING = (int) msg[5] & 0xFF; // 后左车轮报警
		mCanInfo.TPMS_BR_WARING = (int) msg[6] & 0xFF; // 后右车轮报警
	}

	void analyzeCarInfoData_8(byte[] msg) {
		mCanInfo.CONV_WARNING_MES_NUM = (int) msg[3] & 0xFF;
		mCanInfo.CONV_WARNING_MES_0 = (int) msg[4] & 0xFF;
		mCanInfo.CONV_WARNING_MES_1 = (int) msg[5] & 0xFF;
		mCanInfo.CONV_WARNING_MES_2 = (int) msg[6] & 0xFF;
	}

	void analyzeCarInfoData_7(byte[] msg) {
		int len = ((int) msg[2] & 0xFF);
		mCanInfo.WARNING_MES_NUM = (int) msg[3] & 0xFF;
		mCanInfo.WARNING_MES_0 = (int) msg[4] & 0xFF;
		mCanInfo.WARNING_MES_1 = (int) msg[5] & 0xFF;
		mCanInfo.WARNING_MES_2 = (int) msg[6] & 0xFF;
		mCanInfo.WARNING_MES_3 = (int) msg[7] & 0xFF;
		mCanInfo.WARNING_MES_4 = (int) msg[8] & 0xFF;
		mCanInfo.WARNING_MES_5 = (int) msg[9] & 0xFF;
		mCanInfo.START_STOP_MES = (int) msg[10] & 0xFF;
	}

	void analyzeCarInfoData_6(byte[] msg) {
		int len = ((int) msg[2] & 0xFF);
		switch ((int) msg[3] & 0xFF) {
		case 0x00:
			mCanInfo.LANGUAGE_CHANGE = (int) msg[4] & 0xFF;
			break;
		case 0x10:
			mCanInfo.ESC_SYSTEM = (int) msg[4] & 0xFF;
			break;
		case 0x20:
			mCanInfo.TYPES_SPEED_WARNING = (int) (msg[4] >> 0) & 0x01;
			mCanInfo.TYPES_SPEED_UNIT = (int) (msg[4] >> 1) & 0x01;
			mCanInfo.TYPES_SPEED = (int) (msg[5] >> 0) & 0xFF;
			break;
		case 0x30:
			mCanInfo.LANE_DEPARTURE = (int) (msg[4] >> 0) & 0x01;
			mCanInfo.DRIVER_ALERT_SYSTEM = (int) (msg[4] >> 1) & 0x01;
			break;
		case 0x31:
			mCanInfo.LAST_DISTANCE_SELECTED = (int) (msg[4] >> 0) & 0x01; // 上次选择的车距
			mCanInfo.FRONT_ASSIST_ACTIVE = (int) (msg[4] >> 1) & 0x01; // 前部辅助系统激活
			mCanInfo.FRONT_ASSIST_ADVANCE_WARNING = (int) (msg[4] >> 2) & 0x01;// 前部辅助系统预警
			mCanInfo.FRONT_ASSIST_DISPLAY_DISTANCHE_WARNING = (int) (msg[4] >> 3) & 0x01;// 显示距离报警
			mCanInfo.ACC_DRIVER_PROGRAM = (int) (msg[5] >> 0) & 0x0F; // ACC-行驶程序
			mCanInfo.ACC_DISTANCE = (int) (msg[5] >> 4) & 0x0F; // ACC-车距
			break;
		case 0x40:
			mCanInfo.AUTO_PARK_CAR_STATUS = (int) (msg[4] >> 0) & 0x01; // 自动激活
			mCanInfo.PARKING_ASSIT_STATUS = (int) (msg[4] >> 1) & 0x01; // 激活
			mCanInfo.FRONT_VOLUME = (int) (msg[5] >> 0) & 0x0F; // 前部音量
			mCanInfo.FRONT_FREQUNENCY = (int) (msg[5] >> 4) & 0x0F; // 前部频率
			mCanInfo.BACK_VOLUME = (int) (msg[6] >> 0) & 0x0F;// 后部音量
			mCanInfo.BACK_FREQUNENCY = (int) (msg[6] >> 4) & 0x0F; // 后部频率
			mCanInfo.PARKING_MODE = (int) (msg[7] >> 0) & 0x0F; // 倒车模式
			break;
		case 0x50:
			mCanInfo.LIGHT_SWITCH_ON_TIME = (int) (msg[4] >> 0) & 0x0F; // 接通时间
			mCanInfo.LIGHT_AUTO_HEADLIGHT_RAIN = (int) (msg[4] >> 4) & 0x01;
			; // 自动行车灯(雨天)
			mCanInfo.LIGHT_LANE_CHANGE_FLASH = (int) (msg[4] >> 5) & 0x01;
			; // 变道转向灯
			mCanInfo.LIGHT_DAYTIME_RUNNING_LIGHT = (int) (msg[4] >> 6) & 0x01;
			; // 日行灯
			mCanInfo.LIGHT_SWITCH_LIGHTING = (int) (msg[5] >> 0) & 0xFF; // 仪表/开关照明
			mCanInfo.LIGHT_COMING_HOME = (int) (msg[6] >> 0) & 0xFF; // 回家模式
			mCanInfo.LIGHT_LEAVING_HOME = (int) (msg[7] >> 0) & 0xFF; // 离家模式
			break;
		case 0x51:
			mCanInfo.LIGHT_TRAVELING_MODE = (int) (msg[4] >> 0) & 0x01; // 旅行模式
			mCanInfo.LIGHT_DOOR_AMBIENT = (int) (msg[5] >> 0) & 0xFF; // 车门环境照灯
			mCanInfo.LIGHT_FOORWELL_LIGHT = (int) (msg[6] >> 0) & 0xFF; // 脚步空间照明灯调节
			mCanInfo.LIGHT_DYNAMIC_LIGHT_ASSIST = (int) (msg[7] >> 1) & 0x01; // 动态灯光辅助
			mCanInfo.LIGHT_MOTORWAY_LIGHT = (int) (msg[7] >> 0) & 0x01; // 动态大灯随动
			break;
		case 0x52:
			mCanInfo.LIGHT_TOP_LIGHT = (int) (msg[4] >> 0) & 0xFF; // 车顶环境照明灯
			mCanInfo.LIGHT_FRONT_LIGHT = (int) (msg[5] >> 0) & 0xFF; // 前部环境照明灯
			mCanInfo.LIGHT_LIGHT_COLOR = (int) (msg[6] >> 0) & 0xFF; // 动态大灯随动
			mCanInfo.LIGHT_ALL_AREA = (int) (msg[7] >> 0) & 0xFF; // 动态大灯随动
			break;
		case 0x60:
			mCanInfo.MIRROR_SYNC_ADJUST = (int) (msg[4] >> 0) & 0x01; // 后视镜同步调节
			mCanInfo.MIRROR_LOWER_WHILE_REVERSING = (int) (msg[4] >> 1) & 0x01; // 倒车档时后视镜降低
			mCanInfo.WIPER_AUTO_IN_RAIN = (int) (msg[4] >> 2) & 0x01;
			; // 雨天自动刮水
			mCanInfo.WIPER_REAR_WIPING_REVERSING = (int) (msg[4] >> 3) & 0x01; // 倒车档时后窗玻璃刮水
			mCanInfo.MIRROR_FOLD_PARKING = (int) (msg[5] >> 0) & 0x01; // 驻车时内折
			break;
		case 0x70:
			mCanInfo.CONV_OPENING = (int) (msg[4] >> 0) & 0x0F; // 车窗便捷开启
			mCanInfo.DOOR_UNLOCKING = (int) (msg[4] >> 4) & 0x0F; // 中央门锁（车门解锁）
			mCanInfo.AUTOMATIC_LOCKING = (int) (msg[5] >> 0) & 0x01; // 自动锁止
			mCanInfo.INDUCTON_REAR_DOOR_COVER = (int) (msg[5] >> 1) & 0x01; // 感应式后尾箱盖
			break;
		case 0x80:
			mCanInfo.MFD_CURRENT_CONSUMPTION = (int) (msg[4] >> 0) & 0x01;// 当前油耗
			mCanInfo.MFD_AVERAGE_CONSUMPTION = (int) (msg[4] >> 1) & 0x01; // 平均油耗
			mCanInfo.MFD_CONVENIENCE_CONSUMERS = (int) (msg[4] >> 2) & 0x01; // 舒适性用电器
			mCanInfo.MFD_ECO_TIPS = (int) (msg[4] >> 3) & 0x01; // 舒适性用电器
			mCanInfo.MFD_TRAVELLING_TIME = (int) (msg[4] >> 4) & 0x01; // 行驶时间
			mCanInfo.MFD_DISTANCE_TRAVELED = (int) (msg[4] >> 5) & 0x01;// 行驶里程
			mCanInfo.MFD_AVERAGE_SPEED = (int) (msg[4] >> 6) & 0x01; // 平均速度
			mCanInfo.MFD_DIGITAL_SPEED_DISPLAY = (int) (msg[4] >> 7) & 0x01; // 数字式车速显示
			mCanInfo.MFD_SPEED_WARINING = (int) (msg[5] >> 0) & 0x01; // 车速报警
			mCanInfo.MFD_OIL_TEMP = (int) (msg[5] >> 1) & 0x01; // 油温
			break;
		case 0x90:
			mCanInfo.UNIT_DISTANCE = (int) (msg[4] >> 0) & 0x01; // 里程
			mCanInfo.UNIT_SPEED = (int) (msg[4] >> 1) & 0x01; // 车速
			mCanInfo.UNIT_TEMPERATURE = (int) (msg[4] >> 2) & 0x01; // 温度
			mCanInfo.UNIT_VOLUME = (int) (msg[4] >> 4) & 0x0F; // 容积
			mCanInfo.UNIT_CONSUMPTION = (int) (msg[5] >> 0) & 0x0F; // 油耗
			mCanInfo.UNIT_PRESSURE = (int) (msg[5] >> 4) & 0x0F; // 轮胎压力
			break;
		case 0xA0:
			mCanInfo.PROFILE_INFORMATION = (int) (msg[4] >> 0) & 0x0F; // 个人设置
			mCanInfo.INDIVIDUAL_ENGINE = (int) (msg[5] >> 4) & 0x0F; // 引擎
			mCanInfo.PROFILE_STEERING = (int) (msg[5] >> 0) & 0x0F; // 方向盘
			mCanInfo.PROFILE_FRONT_LIGHT = (int) (msg[6] >> 4) & 0x0F; // 前灯
			mCanInfo.PROFILE_CLIMATE = (int) (msg[6] >> 0) & 0x0F; // 气候
			break;
		case 0xB0:
			mCanInfo.REMOTE_KEY = (int) (msg[4] >> 1) & 0x01; // 遥控钥匙记忆匹配
			mCanInfo.KEY_ACTIVE = (int) (msg[4] >> 0) & 0x01; // 汽车钥匙已激活
			break;
		default:
			break;
		}
	}

	void analyzeCarInfoData_5(byte[] msg) {
		int len = ((int) msg[2] & 0xFF);
		byte[] acscii = new byte[len];
		for (int i = 0; i < len - 1; i++) {
			acscii[i] = msg[i + 4];
		}
		try {
			mCanInfo.VERSION = new String(acscii, "GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void analyzeCarInfoData_4(byte[] msg) {
		switch (((int) msg[3] & 0xFF)) {
		case 0x00:
			int len = ((int) msg[2] & 0xFF);
			byte[] acscii = new byte[len];
			for (int i = 0; i < len - 1; i++) {
				acscii[i] = msg[i + 4];
			}
			try {
				mCanInfo.VEHICLE_NO = new String(acscii, "GBK");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 0x10:

			mCanInfo.INSPECTON_DAYS_STATUS = ((int) (msg[4] >> 0) & 0x0F);
			mCanInfo.INSPECTON_DAYS = ((int) msg[5] & 0xFF)
					+ (((int) msg[6] & 0xFF) << 8);
			break;
		case 0x11:
			mCanInfo.INSPECTON_DISTANCE_UNIT = ((int) (msg[4] >> 4) & 0x0F);
			mCanInfo.INSPECTON_DISTANCE_STATUS = ((int) (msg[4] >> 0) & 0x0F);
			mCanInfo.INSPECTON_DISTANCE = (((int) msg[5] & 0xFF) + (((int) msg[6] & 0xFF) << 8));
			break;
		case 0x20:
			mCanInfo.OILCHANGE_SERVICE_DAYS_STATUS = ((int) (msg[4] >> 0) & 0x0F);
			mCanInfo.OILCHANGE_SERVICE_DAYS = ((int) msg[5] & 0xFF)
					+ (((int) msg[6] & 0xFF) << 8);
			break;
		case 0x21:
			mCanInfo.OILCHANGE_SERVICE_DISTANCE_UNIT = ((int) (msg[4] >> 4) & 0x0F);
			mCanInfo.OILCHANGE_SERVICE_DISTANCE_STATUS = ((int) (msg[4] >> 0) & 0x0F);
			mCanInfo.OILCHANGE_SERVICE_DISTANCE = (((int) msg[5] & 0xFF) + (((int) msg[6] & 0xFF) << 8));
			break;
		default:
			break;
		}
	}

	void analyzeCarInfoData_1(byte[] msg) {
		mCanInfo.DRIVING_SPEED = (((int) msg[4] & 0xFF) << 8 | ((int) msg[3] & 0xFF)) / 16;
	}

	void analyzeCarInfoData(byte[] msg) {
		// TODO Auto-generated method stub
		mCanInfo.RIGHT_FORONTDOOR_STATUS = (int) ((msg[3] >> 7) & 0x01);
		mCanInfo.LEFT_FORONTDOOR_STATUS = (int) ((msg[3] >> 6) & 0x01);
		mCanInfo.RIGHT_BACKDOOR_STATUS = (int) ((msg[3] >> 5) & 0x01);
		mCanInfo.LEFT_BACKDOOR_STATUS = (int) ((msg[3] >> 4) & 0x01);
		mCanInfo.TRUNK_STATUS = (int) ((msg[3] >> 3) & 0x01);
		mCanInfo.HOOD_STATUS = (int) ((msg[3] >> 2) & 0x01);
		mCanInfo.HANDBRAKE_STATUS = (int) ((msg[4] >> 1) & 0x01);
		mCanInfo.SAFETY_BELT_STATUS = -1; // 安全带状态 -1表示无此功能 0正常 1报警
		mCanInfo.DISINFECTON_STATUS = -1; // 清洁液状态 -1表示无此功能
		mCanInfo.REMAIN_FUEL = -1; // 剩余油量 -1表示无此功能
		mCanInfo.BATTERY_VOLTAGE = -1; // 电池电量 -1表示无此功能
		/*
		 * case 1:
		 * 
		 * break; case 2: mCanInfo.ENGINE_SPEED = (int) (msg[4] & 0xFF) * 256 +
		 * (int) (msg[5] & 0xFF); mCanInfo.DRIVING_SPEED = ((int) (msg[6] &
		 * 0xFF) * 256 + (int) (msg[7] & 0xFF)) * 0.01f;
		 * mCanInfo.BATTERY_VOLTAGE = ((int) (msg[8] & 0xFF) * 256 + (int)
		 * (msg[9] & 0xFF)) * 0.01f;
		 * 
		 * int temp = (int) (msg[10] & 0xFF) * 256 + (int) (msg[11] & 0xFF); if
		 * (temp < 32767) { mCanInfo.OUTSIDE_TEMPERATURE = temp * 0.1f; } else {
		 * mCanInfo.OUTSIDE_TEMPERATURE = (temp - 65536) * 0.1f; }
		 * mCanInfo.DRIVING_DISTANCE = (int) (msg[12] & 0xFF) * 65536 + (int)
		 * (msg[13] & 0xFF) * 256 + (int) (msg[14] & 0xFF); mCanInfo.REMAIN_FUEL
		 * = (int) (msg[15] & 0xFF); break; case 3: mCanInfo.FUEL_WARING_SIGN =
		 * (int) ((msg[4] >> 7) & 0x01); mCanInfo.BATTERY_WARING_SIGN = (int)
		 * ((msg[4] >> 6) & 0x01); default: break; }
		 */

	}

	void analyzeSteeringTurnData(byte[] msg) {
		// TODO Auto-generated method stub
		int temp = ((int) msg[4] & 0xFF) << 8 | ((int) msg[3] & 0xFF);
		if (temp < 32767) {
			mCanInfo.STERRING_WHELL_STATUS = -temp;
		} else {
			mCanInfo.STERRING_WHELL_STATUS = 65536 - temp;
		}
		Log.i("cxs","====mCanInfo.STERRING_WHELL_STATUS======"+mCanInfo.STERRING_WHELL_STATUS);

	}

	void analyzeFrontRaderData(byte[] msg) {
		// TODO Auto-generated method stub
		mCanInfo.FRONT_LEFT_DISTANCE = (((int) (msg[3] & 0xff)) == 0 ? 0
				: (((((float) (msg[3] & 0xff)) / (0x3C / 4f)) + 1) > 4 ? 4
						: (int) ((((float) (msg[3] & 0xff)) / (0x3C / 4f)) + 1)));
		mCanInfo.FRONT_MIDDLE_LEFT_DISTANCE = (((int) (msg[4] & 0xff)) == 0 ? 0
				: (((((float) (msg[4] & 0xff)) / (0x78 / 4f)) + 1) > 4 ? 4
						: (int) ((((float) (msg[4] & 0xff)) / (0x78 / 4f)) + 1)));
		mCanInfo.FRONT_MIDDLE_RIGHT_DISTANCE = (((int) (msg[5] & 0xff)) == 0 ? 0
				: (((((float) (msg[5] & 0xff)) / (0x78 / 4f)) + 1) > 4 ? 4
						: (int) ((((float) (msg[5] & 0xff)) / (0x78 / 4f)) + 1)));
		mCanInfo.FRONT_RIGHT_DISTANCE = (((int) (msg[6] & 0xff)) == 0 ? 0
				: (((((float) (msg[6] & 0xff)) / (0x3C / 4f)) + 1) > 4 ? 4
						: (int) ((((float) (msg[6] & 0xff)) / (0x3C / 4f)) + 1)));
	}

	void analyzeBackRaderData(byte[] msg) {
		// TODO Auto-generated method stub
		mCanInfo.BACK_LEFT_DISTANCE = (((int) (msg[3] & 0xff)) == 0 ? 0
				: (((((float) (msg[3] & 0xff)) / (0x3C / 4f)) + 1) > 4 ? 4
						: (int) ((((float) (msg[3] & 0xff)) / (0x3C / 4f)) + 1)));
		mCanInfo.BACK_MIDDLE_LEFT_DISTANCE = (((int) (msg[4] & 0xff)) == 0 ? 0
				: (((((float) (msg[4] & 0xff)) / (0xA5 / 4f)) + 1) > 4 ? 4
						: (int) ((((float) (msg[4] & 0xff)) / (0xA5 / 4f)) + 1)));
		mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE = (((int) (msg[5] & 0xff)) == 0 ? 0
				: (((((float) (msg[5] & 0xff)) / (0xA5 / 4f)) + 1) > 4 ? 4
						: (int) ((((float) (msg[5] & 0xff)) / (0xA5 / 4f)) + 1)));
		mCanInfo.BACK_RIGHT_DISTANCE = (((int) (msg[6] & 0xff)) == 0 ? 0
				: (((((float) (msg[6] & 0xff)) / (0x3C / 4f)) + 1) > 4 ? 4
						: (int) ((((float) (msg[6] & 0xff)) / (0x3C / 4f)) + 1)));

	}

	void analyzeAirConditionData(byte[] msg) {
		// TODO Auto-generated method stub
		mCanInfo.AIR_CONDITIONER_STATUS = (int) ((msg[3] >> 7) & 0x01);
		mCanInfo.AC_INDICATOR_STATUS = (int) ((msg[3] >> 6) & 0x01);
		mCanInfo.CYCLE_INDICATOR = (int) ((msg[3] >> 5) & 0x01);
		mCanInfo.LARGE_LANTERN_INDICATOR = (int) ((msg[3] >> 4) & 0x01);
		mCanInfo.SMALL_LANTERN_INDICATOR = (int) ((msg[3] >> 3) & 0x01);
		mCanInfo.DAUL_LAMP_INDICATOR = (int) ((msg[3] >> 2) & 0x01);
		mCanInfo.MAX_FRONT_LAMP_INDICATOR = (int) ((msg[7] >> 7) & 0x01);
		mCanInfo.REAR_LAMP_INDICATOR = (int) ((msg[7] >> 6) & 0x01);
		mCanInfo.UPWARD_AIR_INDICATOR = (int) ((msg[4] >> 7) & 0x01);
		mCanInfo.PARALLEL_AIR_INDICATOR = (int) ((msg[4] >> 6) & 0x01);
		mCanInfo.DOWNWARD_AIR_INDICATOR = (int) ((msg[4] >> 5) & 0x01);
		mCanInfo.AIRCON_SHOW_REQUEST = (int) ((msg[4] >> 4) & 0x01);
		mCanInfo.AIR_RATE = (int) (msg[4] & 0x0f);

		if (((int) (msg[7] & 0x01)) == 0) { // 摄氏度
			int temp = (int) (msg[5] & 0xff);
			mCanInfo.DRIVING_POSITON_TEMP = temp == 0 ? 0 : temp == 0x1f ? 255
					: (16 + (temp - 1) * 0.5f);

			temp = (int) (msg[6] & 0xff);
			mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = temp == 0 ? 0
					: temp == 0x1f ? 255 : (16 + (temp - 1) * 0.5f);
		} else { // 华氏
			int temp = (int) (msg[5] & 0xff);
			mCanInfo.DRIVING_POSITON_TEMP = temp == 0 ? 0 : temp == 0x1f ? 255
					: (60 + (temp - 1));

			temp = (int) (msg[6] & 0xff);
			mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = temp == 0 ? 0
					: temp == 0x1f ? 255 : (60 + (temp - 1));
		}

		mCanInfo.LEFT_SEAT_TEMP = (int) ((msg[8] >> 4) & 0x03);
		mCanInfo.RIGTHT_SEAT_TEMP = (int) ((msg[8] >> 0) & 0x03);

		mCanInfo.AIR_STRENGTH = (int) ((msg[9] >> 0) & 0x03);
	}

	/*
	 * 方向盘按键 STEERING_BUTTON_MODE 0：无按键或释放 1：vol+ 2：vol- 3：menuup 4：menu down 5：
	 * PHONE 6：mute 7：SRC 8：SPEECH/MIC 9:answer phone 10:hangup phone
	 */
	void analyzeSteeringButtonData(byte[] msg) {
		// TODO Auto-generated method stub
		switch ((int) (msg[3] & 0xFF)) {
		case 0x01:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLUP;
			break;
		case 0x02:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLDOW;
			break;
		case 0x03:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUDOWN;
			break;
		case 0x04:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUUP;
			break;
		case 0x07:
		case 0x08:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
			break;
		case 0x05:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.PHONE;
			break;
		case 0x06:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MUTE;
			break;
		default:
			mCanInfo.STEERING_BUTTON_MODE = 0;
			break;
		}

		mCanInfo.STEERING_BUTTON_STATUS = (int) (msg[4] & 0xFF);
	}
	
	void analyzeSteeringButtonData_1(byte[] msg) {
		// TODO Auto-generated method stub
		mCanInfo.STEERING_BUTTON_STATUS = 1;
		switch ((int) (msg[3] & 0xFF)) {
		case 0x01:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOICE_MENUUP;
			break;
		case 0x02:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOICE_MENUDOWN;
			break;
		case 0x11:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOICE_ANSWER;
			break;
		case 0x12:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOICE_HANGUP;
			break;
		case 0x16:
		case 0x17:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOICE_SRC;
			break;
		default:
			mCanInfo.STEERING_BUTTON_MODE = 0;
			mCanInfo.STEERING_BUTTON_STATUS = 0;
			break;
		}

		
	}

}
