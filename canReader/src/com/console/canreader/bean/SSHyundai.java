package com.console.canreader.bean;

import java.io.UnsupportedEncodingException;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.Trace;

import android.util.Log;

public class SSHyundai extends AnalyzeUtils {

	// 数据类型
	public static final int comID = 3;
	// 方向盘按键
	public static final int STEERING_BUTTON_DATA = 0x11;
	// 空调信息
	public static final int AIR_CONDITIONER_DATA = 0x31;
	// 车身信息
	public static final int CAR_INFO_DATA = 0xF0;
	// 模拟面板按键 0x22
	public static final int VIRTUAL_BUTTON_DATA = 0x21;
	// 面板旋钮
	public static final int KNOB_BUTTON = 0x22;
	// 车身信息
	public static final int CAR_INFO_DATA_1 = 0x26;
	// 车身信息
	public static final int CAR_INFO_DATA_2 = 0xA6;

	// DataType

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
			case AIR_CONDITIONER_DATA:
				mCanInfo.CHANGE_STATUS = 3;
				analyzeAirConditionData(msg);
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
			case VIRTUAL_BUTTON_DATA:
				mCanInfo.CHANGE_STATUS = 2;
				analyzeVirtualData(msg);
				break;
			case KNOB_BUTTON:
				mCanInfo.CHANGE_STATUS = 2;
				analyzeKnobButtonData(msg);
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

	 String SteeringButtonStatusDataSave = "";

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
		case 0x05:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.ANSWER;
			break;
		case 0x06:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.HANGUP;
			break;
		case 0x09:
		case 0x21:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUDOWN;
			break;
		case 0x08:
		case 0x20:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUUP;
			break;
		case 0x0A:
		case 0x0B:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
			break;
		default:
			mCanInfo.STEERING_BUTTON_MODE = 0;
			break;
		}

		mCanInfo.STEERING_BUTTON_STATUS = (int) (msg[7] & 0xFF);
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

	 String carInfoSave = "";

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

	 String carInfoSave_1 = "";

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

	 String carInfoSave_2 = "";

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
		mCanInfo.EQL_MUTE = (int) (msg[10] & 0xff);
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

}
