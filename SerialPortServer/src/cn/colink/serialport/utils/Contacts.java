package cn.colink.serialport.utils;

public class Contacts {

	public static final int K_POWER = 0x5D;
	public static final int K_MAINMENU = 0x6E;

	public static final int K_AVMUTE = 0x58;
	public static final int K_NEXT = 0x1C;
	public static final int K_PREV = 0x1E;

	public static final int K_VOLUP = 0x56;
	public static final int K_VOLDN = 0x57;
	

	public static final int K_PHONE_UP = 0xAE;
	public static final int K_PHONE_DN = 0xAF;

	public static final int K_PALYPAUSE = 0X1B;

	public static final int VOL_UP = 1;
	public static final int VOL_DOWN = 2;
	public static final int MENU_UP = 3;
	public static final int MENU_DOWN = 4;
	public static final int TEL = 5;
	public static final int MUTE = 6;
	public static final int SRC = 0x07;
	public static final int MIC = 8;
	public static final int TEL_ANSWER = 9;
	public static final int TEL_HANDUP = 10;

	public static final int MSG_RADIO_DATA = 0x01;
	public static final int MSG_UPDATA_UI = 0x02;
	public static final int MSG_DATA = 0x03;

	public static final String HEX_AUTO_SCAN = "F506000019EB";
	public static final String HEX_START = "F0000000010E";
	public static final String HEX_ST = "F50600000DF7";
	public static final String HEX_LOC = "F50600000FF5";
	public static final String HEX_BAND = "F50600000AFA";
	public static final String HEX_PS = "F506000009FB";
	public static final String HEX_HOME = "F50600000EF6";
	public static final String HEX_ITEM_FIRST = "F50600000103";
	public static final String HEX_ITEM_SECOND = "F50600000202";
	public static final String HEX_ITEM_THIRTH = "F50600000301";
	public static final String HEX_ITEM_FOUR = "F50600000400";
	public static final String HEX_ITEM_FIVE = "F506000005FF";
	public static final String HEX_ITEM_SIXTH = "F506000006FE";
	public static final String HEX_RIGHT_STEP_MOVE = "F50600000CF8";
	public static final String HEX_LEFT_STEP_MOVE = "F50600000BF9";
	public static final String HEX_RIGHT_FAST_MOVE = "F50600001CE8";
	public static final String HEX_LEFT_FAST_MOVE = "F50600001BE9";
	public static final String HEX_RESET_BACK_TIME = "F000000014FB";
	public static final String HEX_FM_TO_HOME = "F50600000EF6";
	public static final String HEX_BT_TO_HOME = "F50B000000FF";

	public static final String HEX_HAND_UP = "F00000000F00";
	public static final String HEX_HAND_DOWN = "F00000000E01";

	public static final int KEY_HEAD = 0x71;

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
	// public static final int MODE_GPS = 0x7A;//GPS界面的信�??(备用)//This is
	// changed for CDC tianlai
	// public static final int MODE_CDC = 0x7A;//CDC界面的信�??
	// public static final int MODE_BLUETOOTH = 0x0B;//蓝牙界面的信�??
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
	public static final int MODE_MSG= 0x77;
	public static final int VERSION_0= 0x60;
	public static final int VERSION_1= 0x61;
	public static final int VERSION_2= 0x62;
	public static final int VERSION_3= 0x63;
	public static final int VERSION_4= 0x64;
	public static final int BACKLIGHT= 0x78;

}
