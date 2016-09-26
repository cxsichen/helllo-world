package com.console.canreader.utils;

public class Contacts {

	/**
	 * 
	 * @author okg 方向盘按键 STEERING_BUTTON_MODE 0：无按键或释放 1：vol+ 2：vol- 3：menuup
	 *         4：menu down 5： PHONE 6：mute 7：SRC 8：SPEECH/MIC 9:answer phone
	 *         10:hangup phone
	 */
	public final static class KEYEVENT {
		public static final int VOLUP = 1;
		public static final int VOLDOW = 2;
		public static final int MENUUP = 3;
		public static final int MENUDOWN = 4;
		public static final int PHONE = 5;
		public static final int MUTE = 6;
		public static final int SRC = 7;
		public static final int SPEECH = 8;
		public static final int ANSWER = 9;
		public static final int HANGUP = 10;
		public static final int MENU_LONG_UP = 11;
		public static final int MENU_LONG_DOWN = 12;
		public static final int CANINFOPAGE = 13;
	}

	/**
	 * 睿志诚 0 
	 * 尚摄 1
	 */

	public final static class CANTYPEGROUP {
		public static final int RAISE = 0;
		public static final int HIWORLD = 1;
	}

	/**
	 * Volkswagen 大众车系    0 
	 * VolkswagenGolf 大众高尔夫   1
	 * Honda 本田凌派 杰德 歌诗图    2
	 * Toyota 丰田CAMRY RAV4    3 
	 * ToyotaReiz 丰田锐志    4
	 * GE 通用    5
	 * FG580 风光580 6
	 * PeugeotCitroen 标致系列  7
	 * Nissan 日产系列   8
	 * Trumpche 广汽传祺  9
	 * Focus 福特通用  10
	 * HondaCRV 本田CRV    11
	 * HondaDA 本田DA      12
	 * BuickGM 别克通用   13
	 * EDGE 福特锐界    14
	 * Roewe360 荣威360  15
	 * MGGS 名爵锐腾   16
	 * BESTURNX80 奔腾X80 17
	 * FHCM3 海马M3   18
	 */
	public final static class CARTYPEGROUP {
		public static final int Volkswagen = 0;
		public static final int VolkswagenGolf = 1;
		public static final int Honda = 2;
		public static final int Toyota = 3;
		public static final int ToyotaReiz = 4;
		public static final int GE = 5;
		public static final int FG580 = 6;
		public static final int PeugeotCitroen = 7;
		public static final int Nissan = 8;
		public static final int Trumpche = 9;
		public static final int Focus = 10;
		public static final int HondaCRV = 11;
		public static final int HondaDA = 12;
		public static final int BuickGM = 13;
		public static final int EDGE = 14;
		public static final int Roewe360 = 15;
		public static final int MGGS = 16;
		public static final int BESTURNX80 = 17;
		public static final int FHCM3 = 18;
		
	}

	public final static String CARTYPE = "carType";
	public final static String CANTYPE = "canType";
	public static final String MODE = "Console_mode"; // 声音模式
	public static final String KEY_VOLUME_VALUE = "volume_value";   //收音机音量
	public static final String BACK_CAR = "back_car_state";
	public static final String ACC_STATE = "acc_state";

	public static final int VOL_UP = 1;
	public static final int VOL_DOWN = 2;
	public static final int MENU_UP = 3;
	public static final int MENU_DOWN = 4;
	public static final int TEL = 5;
	public static final int MUTE = 6;
	public static final int SRC = 7;
	public static final int MIC = 8;
	public static final int TEL_ANSWER = 9;
	public static final int TEL_HANDUP = 10;
	public static final int MENU_LONG_UP = 11;
	public static final int MENU_LONG_DOWN = 12;
	public static final int CANINFOPAGE = 13;

	public static final int MSG_RADIO_DATA = 0x01;
	public static final int MSG_UPDATA_UI = 0x02;
	public static final int MSG_DATA = 0x03;
	public static final int MSG_UPDATE_TIME_LABEL = 0x04;
	public static final int MSG_UPDATE_DATE_AND_TIME = 0x05;
	public static final int MSG_HIDE_POPUP_VIEW = 0x06;
	public static final int MSG_CHANGE_PAGE = 0x07;
	public static final int MSG_PAGE_UNLOCK = 0x08;
	public static final int MSG_SEND_MSG = 0x09;
	public static final int MSG_GET_MSG = 0x0a;
	public static final int MSG_ONCE_GET_MSG = 0x0b;

	public static final int MSG_DIGLOG_HIDE = 0x0F;

	public static final String ACK = "ff";
	public static final String NACK = "f0";
	// DataType
	// 背光
	public static final int BACK_LIGHT_DATA = 0;
	// 车速
	public static final int CAR_SPEED_DATA = 1;
	// 方向盘按键
	public static final int STEERING_BUTTON_DATA = 2;
	// 空调信息
	public static final int AIR_CONDITIONER_DATA = 3;
	// 后雷达信息
	public static final int BACK_RADER_DATA = 4;
	// 前雷达信息
	public static final int FRONT_RADER_DATA = 5;
	// 基本信息
	public static final int BASIC_INFO_DATA = 6;
	// 泊车辅助状态
	public static final int PARK_ASSIT_DATA = 7;
	// 方向盘转角
	public static final int STEERING_TURN_DATA = 8;
	// 功放状态
	public static final int POWER_AMPLIFIER_DATA = 9;
	// 版本信息
	public static final int VERSION_DATA = 10;
	// 车身信息
	public static final int CAR_INFO_DATA = 11;

	public static final String HEX_AUTO_SCAN = "F506000019";
	public static final String HEX_START = "F000000001";
	public static final String HEX_ST = "F50600000D";
	public static final String HEX_LOC = "F50600000F";
	public static final String HEX_BAND = "F50600000A";
	public static final String HEX_PS = "F506000009";
	public static final String HEX_HOME = "F50600000E";
	public static final String HEX_ITEM_FIRST = "F506000001";
	public static final String HEX_ITEM_SECOND = "F506000002";
	public static final String HEX_ITEM_THIRTH = "F506000003";
	public static final String HEX_ITEM_FOUR = "F506000004";
	public static final String HEX_ITEM_FIVE = "F506000005";
	public static final String HEX_ITEM_SIXTH = "F506000006";
	public static final String HEX_ITEM_LONG_FIRST = "F506000011";
	public static final String HEX_ITEM_LONG_SECOND = "F506000012";
	public static final String HEX_ITEM_LONG_THIRTH = "F506000013";
	public static final String HEX_ITEM_LONG_FOUR = "F506000014";
	public static final String HEX_ITEM_LONG_FIVE = "F506000015";
	public static final String HEX_ITEM_LONG_SIXTH = "F506000016";
	public static final String HEX_NEXT_STEP_MOVE = "F50600000C";
	public static final String HEX_PRE_STEP_MOVE = "F50600000B";
	public static final String HEX_NEXT_FAST_MOVE = "F50600001C";
	public static final String HEX_PRE_FAST_MOVE = "F50600001B";
	public static final String HEX_RESET_BACK_TIME = "F000000014";
	public static final String HEX_VOLUMN = "F00000001F";
	public static final String HEX_VOLUMN_SUB = "F508000003";
	public static final String HEX_VOLUMN_ADD = "F508000002";
	public static final String HEX_FM_BRAND_1 = "F507000000";
	public static final String HEX_FM_BRAND_2 = "F507000001";
	public static final String HEX_FM_BRAND_3 = "F507000002";
	public static final String HEX_AM_BRAND_1 = "F507000003";
	public static final String HEX_AM_BRAND_2 = "F507000004";
	public static final String HEX_SOUND_MOREN = "F50500000E";
	public static final String HEX_SOUND_YAOGUN = "F505000005";
	public static final String HEX_SOUND_LIUXING = "F505000006";
	public static final String HEX_SOUND_JIESHI = "F505000007";
	public static final String HEX_SOUND_JINDIAN = "F505000008";
	public static final String HEX_SOUND_CAR_UP = "F50500000D";
	public static final String HEX_SOUND_CAR_DOWN = "F50500000C";
	public static final String HEX_SOUND_CAR_LEFT = "F50500000A";
	public static final String HEX_SOUND_CAR_RIGHT = "F50500000B";
	public static final String HEX_SOUND_CAR_RESET = "F50500000E";
	public static final String HEX_GET_CAR_INFO = "2e90024102";
	public static final String HEX_GET_CAR_INFO_0_1 = "2e90022700";
	public static final String HEX_GET_CAR_INFO_1 = "2e90024101";
	public static final String HEX_GET_CAR_INFO_3 = "2e90024103";
	public static final String CONNECTMSG = "2e810101";
	public static final String DISCONNECTMSG = "2e810100";
	/** volumn popup */
	public static final String HEX_VOLUMN_POPUP = "F00000001F";
	/** volumn silent, */
	public static final String HEX_VOLUMN_SILENT = "F506000007";
	public static final int FM1_FREQ = 0x30;
	public static final int FM2_FREQ = 0x31;
	public static final int FM3_FREQ = 0x32;
	public static final int AM1_FREQ = 0x33;
	public static final int AM2_FREQ = 0x34;
	public static final int FM1_SELECT = 0x40;
	public static final int FM2_SELECT = 0x41;
	public static final int FM3_SELECT = 0x42;
	public static final int AM1_SELECT = 0x43;
	public static final int AM2_SELECT = 0x44;
	public static final int FM1_1 = 0x00;
	public static final int FM1_2 = 0x01;
	public static final int FM1_3 = 0x02;
	public static final int FM1_4 = 0x03;
	public static final int FM1_5 = 0x04;
	public static final int FM1_6 = 0x05;
	public static final int FM2_1 = 0x06;
	public static final int FM2_2 = 0x07;
	public static final int FM2_3 = 0x08;
	public static final int FM2_4 = 0x09;
	public static final int FM2_5 = 0x0A;
	public static final int FM2_6 = 0x0B;
	public static final int FM3_1 = 0x0C;
	public static final int FM3_2 = 0x0D;
	public static final int FM3_3 = 0x0E;
	public static final int FM3_4 = 0x0F;
	public static final int FM3_5 = 0x10;
	public static final int FM3_6 = 0x11;
	public static final int AM1_1 = 0x12;
	public static final int AM1_2 = 0x13;
	public static final int AM1_3 = 0x14;
	public static final int AM1_4 = 0x15;
	public static final int AM1_5 = 0x16;
	public static final int AM1_6 = 0x17;
	public static final int AM2_1 = 0x18;
	public static final int AM2_2 = 0x19;
	public static final int AM2_3 = 0x1A;
	public static final int AM2_4 = 0x1B;
	public static final int AM2_5 = 0x1C;
	public static final int AM2_6 = 0x1D;

	public static final int SWITCH_MODE = 0x70;// 切换系统状�??
	public static final int KEY_CODE = 0x71;// 键盘按键
	// public static final int MODE_MAIN = 0x02;//主界面的信息(备用)
	public static final int MODE_RADIO = 0x76;// RADIO界面的信�??
	// public static final int MODE_TV = 0x77;//TV界面的信�??(备用)
	// public static final int MODE_DVD = 0x78;//DVD界面的信�??(备用)
	// public static final int MODE_AUX = 0x79;//AUX界面的信�??(备用)
	// public static final int MODE_GPS = 0x7A;//GPS界面的信�??(备用)//This is changed
	// for CDC tianlai
	// public static final int MODE_CDC = 0x7A;//CDC界面的信�??
	public static final int MODE_BLUETOOTH = 0x0B;// 蓝牙界面的信�??
	// public static final int MODE_CDC = 0x0C;//CDC界面的信�??
	// public static final int MODE_CONFIG_AUDIO = 0x11;//声音设置信息
	// public static final int MODE_CONFIG_DISP = 0x12;//显示设置信息
	public static final int SYSTEM_TIME = 0x7C;// 系统时间
	public static final int SYSTEM_INFO = 0x7D;// 系统信息
	public static final int FACTORY_SETUP = 0x6F;
	public static final int MODE_AUDIO_5_1 = 0x8C;// GPS音量
	public static final int TOUCH_SEND = 0x72;// touch
	public static final int FUNC_KEY = 0x88;
	public static final int SYSTEM_HW = 0x80;
	public static final int VOLBAR = 0x7E;
	public static final int BLINK = -1;
	public static final int VOLK_HEAD_CODE = 0x2e;
	public static final int VOLK_HEAD_CODE_1 = 0xfd;
	public static final int VOLK_HEAD_CODE_2 = 0xfc;
	public static final int SS_HEAD_CODE_1 = 0x5A;
	public static final int SS_HEAD_CODE_2 = 0xA5;

}
