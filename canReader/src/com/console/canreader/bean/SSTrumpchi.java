package com.console.canreader.bean;

import java.io.UnsupportedEncodingException;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.Trace;

import android.util.Log;

public class SSTrumpchi extends AnalyzeUtils {

	// 数据类型
	public static final int comID = 3;
	// DataType
	// 车身信息
	public static final int CAR_INFO_DATA = 0x11;
	// 车身信息
	public static final int CAR_INFO_DATA_1 = 0x12;
	// 空调信息
	public static final int AIR_CONDITIONER_DATA = 0x31;
	// 空调控制
	public static final int AIR_CONDITIONER_DATA2 = 0x32;
	// 雷达信息
	public static final int RADAR_DATA = 0x41;
	// 模拟面板按键 0x22
	public static final int VIRTUAL_BUTTON_DATA = 0x21;
	// 面板旋钮
	public static final int KNOB_BUTTON = 0x22;
	// 软件版本信息 0xf0
	public static final int VERSION_DATA = 0xf0;
	// link/sos通讯信息 0x25
	public static final int LINK_SOS_DATA = 0x25;
	// 车辆设置信息 0x96
	public static final int CAR_SETTING_DATA = 0x96;
	// 车辆设置命令 0x9b DVD主机发给盒子
	// 车型设置0x24 DVD主机发给盒子
	// 功能使能标志 0x4f
	public static final int CAR_INFO_ENABLE = 0x4f;
	// 原车视频状态信息 0xe8
	public static final int CAR_CAMERA_STATUS = 0xe8;

	// 原车设置 导航主机发给盒子

	public CanInfo getCanInfo() {
		return mCanInfo;
	}

	@Override
	public void analyzeEach(byte[] msg) {
		try {
			if (msg == null)
				return;
			switch ((int) (msg[comID] & 0xFF)) {
			case CAR_INFO_DATA:
				// mCanInfo.CHANGE_STATUS = 2;
				// analyzeCarBasicInfoData(msg);
				analyzeCarInfoData(msg);
				break;
			case CAR_INFO_DATA_1:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData2(msg);
				break;
			case AIR_CONDITIONER_DATA:
				mCanInfo.CHANGE_STATUS = 3;
				analyzeAirConditionData(msg);
				break;
			case RADAR_DATA:
				mCanInfo.CHANGE_STATUS = 11;
				analyzeRadarData(msg);
				break;
			case VIRTUAL_BUTTON_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeVirtualData(msg);
				break;
			case KNOB_BUTTON:
				mCanInfo.CHANGE_STATUS = 2;
				analyzeKnobButtonData(msg);
				break;
			case VERSION_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeVersionData(msg);
				break;
			case LINK_SOS_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeLINKSOSData(msg);
				break;
			case CAR_SETTING_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarSettingData(msg);
				break;
			case CAR_INFO_ENABLE:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoEnableData(msg);
				break;
			case CAR_CAMERA_STATUS:
				mCanInfo.CHANGE_STATUS = 20;
				analyzeCarCameraStatusData(msg);
				break;
			// case CAR_BASIC_INFO_DATA:
			// analyzeCarBasicInfoData(msg);
			// break;
			default:
				break;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	 String CarCameraStatusDataSave = "";

	private void analyzeCarCameraStatusData(byte[] msg) {
		if (CarCameraStatusDataSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			CarCameraStatusDataSave = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.PANORAMA_STATUS = msg[4] & 0x0f;
		mCanInfo.CAMERA_MODE = msg[5] & 0x0f;
	}

	 String CarInfoEnableDataSave = "";

	private void analyzeCarInfoEnableData(byte[] msg) {
		if (CarInfoEnableDataSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			CarInfoEnableDataSave = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.ESP_ENABLE = (msg[4] >> 7) & 0x01;
		mCanInfo.HOLOGRAM_ENABLE = (msg[4] >> 6) & 0x01;
		mCanInfo.CAT_SETTTING_ENABLE = (msg[4] >> 5) & 0x01;
		mCanInfo.PANEL_SSETTING_ENABLE = (msg[4] >> 4) & 0x01;
		mCanInfo.AIR_INFO_ENABLE = (msg[4] >> 3) & 0x01;
	}

	 String CarSettingDataSave = "";

	private void analyzeCarSettingData(byte[] msg) {

		if (CarSettingDataSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			CarSettingDataSave = BytesUtil.bytesToHexString(msg);
		}

		mCanInfo.LANGUAGE_CHANGE = msg[4] & 0x0f;
		mCanInfo.AUTO_COMPRESSOR_STATUS = (msg[5] >> 7) & 0x01;
		mCanInfo.AUTO_CYCLE_STATUS = (msg[5] >> 6) & 0x01;
		mCanInfo.AIR_COMFORTABLE_STATUS = (msg[5] >> 4) & 0x03;
		mCanInfo.AIR_ANION_STATUS = (msg[5] >> 3) & 0x01;

		mCanInfo.DRIVING_POSITION_SETTING = (msg[6] >> 7) & 0x01;
		mCanInfo.DEPUTY_DRIVING_POSITION_SETTING = (msg[6] >> 6) & 0x01;
		mCanInfo.POSITION_WELCOME_SETTING = (msg[6] >> 5) & 0x01;
		mCanInfo.KEY_INTELLIGENCE = (msg[6] >> 4) & 0x01;

		mCanInfo.SPEED_OVER_SETTING = (msg[7] & 0xff) * 10;
		mCanInfo.WARNING_VOLUME = msg[8] & 0xff;
		mCanInfo.REMOTE_POWER_TIME = msg[9] & 0xff;
		mCanInfo.REMOTE_START_TIME = msg[10] & 0xff;
		mCanInfo.DRIVER_CHANGE_MODE = msg[11] & 0xff;
		mCanInfo.REMOTE_UNLOCK = (msg[12] >> 7) & 0x01;
		mCanInfo.SPEED_LOCK = (msg[12] >> 6) & 0x01;
		mCanInfo.AUTO_UNLOCK = (msg[12] >> 5) & 0x01;
		mCanInfo.REMOTE_FRONT_LEFT = (msg[12] >> 4) & 0x01;
		mCanInfo.FRONT_WIPER_CARE = (msg[12] >> 3) & 0x01;
		mCanInfo.REAR_WIPER_STATUS = (msg[12] >> 2) & 0x01;
		mCanInfo.OUTSIDE_MIRROR_STATUS = (msg[12] >> 1) & 0x01;

		mCanInfo.GO_HOME_LAMP_STATUS = (msg[13] >> 6) & 0x03;
		mCanInfo.FOG_LAMP_STATUS = (msg[13] >> 5) & 0x01;
		mCanInfo.DAYTIME_LAMP_STATUS = (msg[13] >> 4) & 0x01;
		mCanInfo.AUTO_LAMP_STATUS = (msg[13] >> 2) & 0x03;
	}

	 String LINKSOSDataSave = "";

	private void analyzeLINKSOSData(byte[] msg) {
		if (LINKSOSDataSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			LINKSOSDataSave = BytesUtil.bytesToHexString(msg);
		}
		switch (msg[4] & 0x0f) {
		case 0x00:
			mCanInfo.LINK_SOS_STATUS = 0;
			break;
		case 0x01:
			mCanInfo.LINK_SOS_STATUS = 1;
			break;
		case 0x02:
			mCanInfo.LINK_SOS_STATUS = 2;
			break;

		default:
			break;
		}
	}

	 String VersionDataSave = "";

	private void analyzeVersionData(byte[] msg) {
		if (VersionDataSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			VersionDataSave = BytesUtil.bytesToHexString(msg);
		}
		int len = ((int) msg[2] & 0xFF);
		byte[] acscii = new byte[len];
		for (int i = 0; i < len; i++) {
			acscii[i] = msg[i + 4];

		}
		try {
			mCanInfo.VERSION = new String(acscii, "GBK");
			// Trace.i("-=====" + mCanInfo.VERSION_SS);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	 String KnobButtonDataSave = "";

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

	 String VirtualDataSave = "";

	private void analyzeVirtualData(byte[] msg) {
		if (VirtualDataSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			VirtualDataSave = BytesUtil.bytesToHexString(msg);
		}
		int temp = (int) (msg[4] & 0xff);
		switch (temp) {
		case 0x00:

			break;
		case 0x01:// power
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.POWER;
			break;
		case 0x02:// 上一曲
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUUP;
			break;
		case 0x03:// 下一曲
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUDOWN;
			break;
		case 0x04:// AM-FM
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.FM_AM;
			break;
		case 0x09:// MUTE
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MUTE;
			break;
		case 0x0a:// NUM1
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.NUM1;
			break;
		case 0x0b:// NUM2
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.NUM2;
			break;
		case 0x0c:// NUM3
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.NUM3;
			break;
		case 0x0d:// NUM4
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.NUM4;
			break;
		case 0x0e:// NUM5
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.NUM5;
			break;
		case 0x0f:// NUM6
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.NUM6;
			break;
		case 0x11:// Eject
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.DEL;
			break;
		case 0x15:// APS
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.POWER;
			break;
		case 0x16:// TUNE　SEL
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.EQ;
			break;
		case 0x25:// NAVI
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MAP;
			break;
		case 0x2d:// MENU
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.HOME;
			break;
		case 0x36:// SET
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.ENTER;
			break;
		case 0x38:// MODE
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
			break;
		case 0x39:// SCAN
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.FM_AM;
			break;
		default:
			mCanInfo.STEERING_BUTTON_MODE = 0;
			break;
		}
		temp = (int) (msg[5] & 0xff);
		if (mCanInfo.STEERING_BUTTON_STATUS != temp) {
			mCanInfo.STEERING_BUTTON_STATUS = temp;
			mCanInfo.CHANGE_STATUS = 2;
		}
		mCanInfo.CHANGE_STATUS = 2;
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
				temps[i] = ((((int) (msg[4 + i] & 0xff) - 1) * 4 / 255) + 1);
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
	 * 方向盘按键 STEERING_BUTTON_MODE 0：无按键或释放 1：vol+ 2：vol- 3：menuup 4：menu down 5：
	 * PHONE 6：mute 7：SRC 8：SPEECH/MIC 9:answer phone 10:hangup phone
	 */

	void analyzeCarBasicInfoData(byte[] msg) {
		if (carBasicInfo.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carBasicInfo = BytesUtil.bytesToHexString(msg);
		}

		int temp = (int) (msg[6] & 0xFF);
		for (int i = 0; i < keyCode.length; i++) {
			if (temp == keyCode[i]) {
				temp = i;
				break;
			}
		}
		if (mCanInfo.STEERING_BUTTON_MODE != temp) {
			mCanInfo.STEERING_BUTTON_MODE = temp;
			mCanInfo.CHANGE_STATUS = 2;
		}

		temp = (int) (msg[7] & 0xFF);
		if (mCanInfo.STEERING_BUTTON_STATUS != temp) {
			mCanInfo.STEERING_BUTTON_STATUS = temp;
			mCanInfo.CHANGE_STATUS = 2;
		}
	}

	static String carInfoSave = "";

	private void analyzeCarInfoData2(byte[] msg) {
		if (carInfoSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave = BytesUtil.bytesToHexString(msg);
		}
		switch (msg[5] & 0x0f) {
		case 0x00:
			mCanInfo.CAR_GEAR_STATUS = -1;
			break;
		case 0x01:
			mCanInfo.CAR_GEAR_STATUS = 1;
			break;
		case 0x02:
			mCanInfo.CAR_GEAR_STATUS = 2;
			break;
		case 0x03:
			mCanInfo.CAR_GEAR_STATUS = 3;
			break;
		case 0x04:
			mCanInfo.CAR_GEAR_STATUS = 4;
			break;
		case 0x05:
			mCanInfo.CAR_GEAR_STATUS = 5;
			break;
		default:
			break;
		}

		mCanInfo.SAFETY_BELT_STATUS = (int) ((msg[6] >> 1) & 0x01) == 0 ? 0 : 1;
		mCanInfo.HOOD_STATUS = (int) ((msg[6] >> 2) & 0x01);
		mCanInfo.TRUNK_STATUS = (int) ((msg[6] >> 3) & 0x01);
		mCanInfo.RIGHT_BACKDOOR_STATUS = (int) ((msg[6] >> 4) & 0x01);
		mCanInfo.LEFT_BACKDOOR_STATUS = (int) ((msg[6] >> 5) & 0x01);
		mCanInfo.RIGHT_FORONTDOOR_STATUS = (int) ((msg[6] >> 6) & 0x01);
		mCanInfo.LEFT_FORONTDOOR_STATUS = (int) ((msg[6] >> 7) & 0x01);

	}

	/*
	 * 方向盘按键 STEERING_BUTTON_MODE 0：无按键或释放 1：vol+ 2：vol- 3：menuup 4：menu down 5：
	 * PHONE 6：mute 7：SRC 8：SPEECH/MIC 9:answer phone 10:hangup phone
	 */
	int keyCode[] = { 0, 1, 2, 6, -1, 9, 10, -1, 3, 4, 7 };
	 String CarInfoDatasave = "";

	void analyzeCarInfoData(byte[] msg) {
		if (CarInfoDatasave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			CarInfoDatasave = BytesUtil.bytesToHexString(msg);
		}
		// TODO Auto-generated method stub
		mCanInfo.HANDBRAKE_STATUS = (int) ((msg[4] >> 3) & 0x01);
		mCanInfo.CAR_BACK_STATUS = (int) ((msg[4] >> 2) & 0x01);
		mCanInfo.CAR_ILL_STATUS = (int) ((msg[4] >> 1) & 0x01);

		int ESP3 = (int) ((msg[10] >> 4) & 0x0f);
		int ESP2 = (int) ((msg[10] >> 0) & 0x0f);
		int ESP1 = (int) ((msg[11] >> 4) & 0x0f);
		int ESP0 = (int) ((msg[11] >> 0) & 0x0f);
		int ESP = ESP3 * 16 * 16 * 16 + ESP2 * 16 * 16 + ESP1 * 16 + ESP0;
		if (ESP >= 0xEA84) {
			mCanInfo.STERRING_WHELL_STATUS = (int) (-0.1 * (0xffff - ESP + 1));
		} else {
			mCanInfo.STERRING_WHELL_STATUS = (int) (0.1 * ESP);
		}
		mCanInfo.CHANGE_STATUS = 10;
		int temp = (int) (msg[6] & 0xFF);
		/*for (int i = 0; i < keyCode.length; i++) {
			if (temp == keyCode[i]) {
				temp = i;
				break;
			}
		}*/
		switch (temp) {
		case 0x01:
			temp = Contacts.KEYEVENT.VOLUP;
			break;
		case 0x02:
			temp = Contacts.KEYEVENT.VOLDOW;
			break;
		case 0x03:
			temp = Contacts.KEYEVENT.MUTE;
			break;
		case 0x05:
			temp = Contacts.KEYEVENT.ANSWER;
			break;
		case 0x06:
			temp = Contacts.KEYEVENT.HANGUP;
			break;
		case 0x08:
			temp = Contacts.KEYEVENT.MENUUP;
			break;
		case 0x09:
			temp = Contacts.KEYEVENT.MENUDOWN;
			break;
		case 0x0A:
			temp = Contacts.KEYEVENT.SRC;
			break;		
		default:
			temp = 0;
			break;
		}
		if (mCanInfo.STEERING_BUTTON_MODE != temp) {
			mCanInfo.STEERING_BUTTON_MODE = temp;
			mCanInfo.CHANGE_STATUS = 2;
		}

		temp = (int) (msg[7] & 0xFF);
		if (mCanInfo.STEERING_BUTTON_STATUS != temp) {
			mCanInfo.STEERING_BUTTON_STATUS = temp;
			mCanInfo.CHANGE_STATUS = 2;
		}
		// //
		// switch ((int) (msg[6] & 0x0f)) {
		// case 0x00:
		//
		// break;
		// case 0x01:
		// mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLUP;
		// break;
		// case 0x02:
		// mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLDOW;
		// break;
		// case 0x03:
		// mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MUTE;
		// break;
		// case 0x05:
		// mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.ANSWER;
		// break;
		// case 0x06:
		// mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.HANGUP;
		// break;
		// case 0x08:
		// mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUUP;
		// break;
		// case 0x09:
		// mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUDOWN;
		// break;
		// case 0x0a:
		// mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
		// break;
		// default:
		// break;
		// }
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
		mCanInfo.AC_MAX_STATUS = (int) ((msg[4] >> 5) & 0x01);
		mCanInfo.DAUL_LAMP_INDICATOR = (int) ((msg[4] >> 2) & 0x01);
		mCanInfo.AC_INDICATOR_STATUS = (int) (msg[4] & 0x01);

		mCanInfo.CYCLE_INDICATOR = (int) ((msg[5] >> 4) & 0x01);
		mCanInfo.AUTO_STATUS = (int) ((msg[5] >> 3) & 0x01);

		mCanInfo.REAR_LAMP_INDICATOR = (int) ((msg[6] >> 5) & 0x01);
		mCanInfo.MAX_FRONT_LAMP_INDICATOR = (int) ((msg[6] >> 4) & 0x01);
		mCanInfo.RIGTHT_SEAT_TEMP = (int) ((msg[6] >> 2) & 0x03);
		mCanInfo.LEFT_SEAT_TEMP = (int) (msg[6] & 0x03);

		int temp = (int) (msg[8] & 0x0f);
		if (temp == 0x00) {
			mCanInfo.UPWARD_AIR_INDICATOR = 0;
			mCanInfo.PARALLEL_AIR_INDICATOR = 0;
			mCanInfo.DOWNWARD_AIR_INDICATOR = 0;
		}
		if (temp == 0x01 || temp == 0x0b || temp == 0x0c || temp == 0x0d
				|| temp == 0x0e) {
			mCanInfo.UPWARD_AIR_INDICATOR = 1;
		} else {
			mCanInfo.UPWARD_AIR_INDICATOR = 0;
		}
		if (temp == 0x01 || temp == 0x05 || temp == 0x06 || temp == 0x0d
				|| temp == 0x0e) {
			mCanInfo.PARALLEL_AIR_INDICATOR = 1;
		} else {
			mCanInfo.PARALLEL_AIR_INDICATOR = 0;
		}
		if (temp == 0x01 || temp == 0x03 || temp == 0x05 || temp == 0x0c
				|| temp == 0x0e) {
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

	}

	void analyzeSteeringButtonData(byte[] msg) {
		// TODO Auto-generated method stub
		if (msg.length > 6) {
			return;
		}
		mCanInfo.STEERING_BUTTON_MODE = (int) (msg[3] & 0xFF);
		mCanInfo.STEERING_BUTTON_STATUS = (int) (msg[4] & 0xFF);
	}

}
