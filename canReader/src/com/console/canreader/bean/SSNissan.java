package com.console.canreader.bean;

import java.io.UnsupportedEncodingException;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.Trace;

import android.util.Log;

public class SSNissan extends AnalyzeUtils {

	// 数据类型
	public static final int comID = 3;
	// 方向盘按键
	public static final int STEERING_BUTTON_DATA = 0x72;
	// 车身信息
	public static final int CAR_INFO_DATA = 0xF0;
	// 车身信息
	public static final int CAR_INFO_DATA_2 = 0xA6;
	// 雷达信息
	public static final int RADAR_DATA = 0x41;
	// 车身信息
	public static final int CAR_INFO_DATA_3 = 0xF2;

	public CanInfo getCanInfo() {
		return mCanInfo;
	}

	@Override
	public void analyzeEach(byte[] msg) {
		try {
			if (msg == null)
				return;
			switch ((int) (msg[comID] & 0xFF)) {
			case STEERING_BUTTON_DATA:
				mCanInfo.CHANGE_STATUS = 2;
				analyzeSteeringButtonData(msg);
				break;
			case CAR_INFO_DATA_2:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_2(msg);
				break;
			case CAR_INFO_DATA_3:
				mCanInfo.CHANGE_STATUS = 20;
				analyzeCarInfoData_3(msg);
				break;
			case CAR_INFO_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData(msg);
				break;
			case RADAR_DATA:
				mCanInfo.CHANGE_STATUS = 11;
				analyzeRadarData(msg);
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

	static String radarSave = "";

	void analyzeRadarData(byte[] msg) {
		if (radarSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			radarSave = BytesUtil.bytesToHexString(msg);
		}

		mCanInfo.BACK_LEFT_DISTANCE = (((int) (msg[4] & 0xFF)) == 0xff) ? 4
				: 4 - ((int) (msg[4] & 0xFF));
		mCanInfo.BACK_MIDDLE_LEFT_DISTANCE = (((int) (msg[5] & 0xFF)) == 0xff) ? 4
				: 4 - ((int) (msg[5] & 0xFF));
		mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE = (((int) (msg[6] & 0xFF)) == 0xff) ? 4
				: 4 - ((int) (msg[6] & 0xFF));
		mCanInfo.BACK_RIGHT_DISTANCE = (((int) (msg[7] & 0xFF)) == 0xff) ? 4
				: 4 - ((int) (msg[7] & 0xFF));

		mCanInfo.FRONT_LEFT_DISTANCE = (((int) (msg[8] & 0xFF)) == 0xff) ? 4
				: 4 - ((int) (msg[8] & 0xFF));
		mCanInfo.FRONT_MIDDLE_LEFT_DISTANCE = (((int) (msg[9] & 0xFF)) == 0xff) ? 4
				: 4 - ((int) (msg[9] & 0xFF));
		mCanInfo.FRONT_MIDDLE_RIGHT_DISTANCE = (((int) (msg[10] & 0xFF)) == 0xff) ? 4
				: 4 - ((int) (msg[10] & 0xFF));
		mCanInfo.FRONT_RIGHT_DISTANCE = (((int) (msg[11] & 0xFF)) == 0xff) ? 4
				: 4 - ((int) (msg[11] & 0xFF));

	}

	static String SteeringButtonStatusDataSave = "";
	static int steelWheel = 0;

	void analyzeSteeringButtonData(byte[] msg) {
		// TODO Auto-generated method stub
		if (SteeringButtonStatusDataSave
				.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			SteeringButtonStatusDataSave = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.STEERING_BUTTON_STATUS = 1;
		switch ((int) (msg[6] & 0xFF)) {
		case 0x01:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLUP;
			break;
		case 0x02:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLDOW;
			break;
		case 0x05:
		case 0x06:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.PHONE;
			break;
		case 0x0E:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUDOWN;
			break;
		case 0x0D:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUUP;
			break;
		case 0x04:
		case 0x0A:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
			break;
		default:
			mCanInfo.STEERING_BUTTON_MODE = 0;
			mCanInfo.STEERING_BUTTON_STATUS = 0;
			break;
		}

		int temp = ((int) msg[8] & 0xFF) << 8 | ((int) msg[9] & 0xFF);
		if (temp < 32767) {
			mCanInfo.STERRING_WHELL_STATUS = -temp;
		} else {
			mCanInfo.STERRING_WHELL_STATUS = 65536 - temp;
		}
		if (steelWheel != temp) {
			steelWheel = temp;
			mCanInfo.CHANGE_STATUS = 8;
		}
	}

	static String carInfoSave_2 = "";

	void analyzeCarInfoData_2(byte[] msg) {
		if (carInfoSave_2.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_2 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.EQL_VOLUME = (int) (msg[4] & 0xff);
		mCanInfo.LR_BALANCE = (int) (msg[5] & 0xff);
		mCanInfo.FB_BALANCE = (int) (msg[6] & 0xff);
		mCanInfo.BAS_VOLUME = (int) (msg[7] & 0xff);
		mCanInfo.MID_VOLUME = (int) (msg[8] & 0xff);
		mCanInfo.TRE_VOLUME = (int) (msg[9] & 0xff);

		mCanInfo.SEAT_SOUND = (int) ((msg[10] >> 4) & 0xff);
		mCanInfo.BOSE_CENTERPOINT = (int) ((msg[10] >> 3) & 0xff);
		mCanInfo.VOL_LINK_CARSPEED = (int) ((msg[10] >> 0) & 0x07);
		mCanInfo.SURROND_VOLUME = (int) ((msg[11] >> 0) & 0xff);
	}

	static String carInfoSave_3 = "";

	void analyzeCarInfoData_3(byte[] msg) {
		if (carInfoSave_3.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_3 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.PANORAMA_STATUS = (int) (msg[4] & 0xff);
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
		mCanInfo.SMALL_LANTERN_INDICATOR = (int) ((msg[4] >> 3) & 0x01);
		mCanInfo.AC_INDICATOR_STATUS = (int) ((msg[4] >> 0) & 0x03);
		mCanInfo.MAX_FRONT_LAMP_INDICATOR = (int) ((msg[6] >> 4) & 0x01);
		int temp = (int) (msg[8] & 0x0f);
		if (temp == 0x00) {
			mCanInfo.UPWARD_AIR_INDICATOR = 0;
			mCanInfo.PARALLEL_AIR_INDICATOR = 0;
			mCanInfo.DOWNWARD_AIR_INDICATOR = 0;
		}
		if (temp == 0x01 || temp == 0x02 || temp == 0x0b || temp == 0x0c
				|| temp == 0x0d || temp == 0x0e) {
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

		temp = (int) (msg[15] & 0xff);
		mCanInfo.OUTSIDE_TEMPERATURE = temp * 0.5f - 40f;
	}

	static String carInfoSave = "";

	void analyzeCarInfoData(byte[] msg) {
		if (carInfoSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave = BytesUtil.bytesToHexString(msg);
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

	static String carInfoSave_1 = "";

	void analyzeCarInfoData_1(byte[] msg) {
		if (carInfoSave_1.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_1 = BytesUtil.bytesToHexString(msg);
		}
		if ((int) (msg[4] & 0xff) == 0x09) {
			mCanInfo.CAR_TYPE = (int) (msg[5] & 0xff);
		}
	}

	static String VirtualDataSave = "";

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
		case 0x4B:// AM-FM
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.FM_AM;
			break;
		case 0x24:// MEDIA
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MUSIC;
			break;
		case 0x28:// PHONE
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.PHONE_APP;
			break;
		case 0x2B:// HOME
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.HOME;
			break;
		case 0x39:// MAP
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MAP;
			break;
		default:
			mCanInfo.STEERING_BUTTON_MODE = 0;
			break;
		}
		temp = (int) (msg[5] & 0xff);
		mCanInfo.STEERING_BUTTON_STATUS = temp;
	}

	static String KnobButtonDataSave = "";

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

}
