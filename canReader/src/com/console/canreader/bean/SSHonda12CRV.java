package com.console.canreader.bean;

import java.io.UnsupportedEncodingException;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;
import com.console.canreader.utils.Contacts;

import android.util.Log;

public class SSHonda12CRV extends AnalyzeUtils {
	// 数据类型
	public static final int comID = 3;
	// 方向盘按键
	public static final int STEERING_BUTTON_DATA = 0x72;
	// 车身信息
	public static final int CAR_INFO_DATA_10 = 0xF0;
	// 车身信息
	public static final int CAR_INFO_DATA = 0xB1;
	// 车身信息
	public static final int CAR_INFO_DATA_1 = 0xA4;

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
			case CAR_INFO_DATA_10:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_10(msg);
				break;
			case CAR_INFO_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData(msg);
				break;
			case CAR_INFO_DATA_1:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_1(msg);
				break;

			default:
				break;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	static String SteeringButtonStatusDataSave = "";

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
		case 0x0A:
		case 0x0B:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
			break;
		default:
			mCanInfo.STEERING_BUTTON_MODE = 0;
			mCanInfo.STEERING_BUTTON_STATUS = 0;
			break;
		}
	}

	static String carInfoSave_10 = "";

	void analyzeCarInfoData_10(byte[] msg) {
		if (carInfoSave_10.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_10 = BytesUtil.bytesToHexString(msg);
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

	static String carInfoSave = "";

	void analyzeCarInfoData(byte[] msg) {
		if (carInfoSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.BLUETOOTH_MODE = msg[4] & 0xff;
	}

	static String carInfoSave_1 = "";

	void analyzeCarInfoData_1(byte[] msg) {
		if (carInfoSave_1.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			carInfoSave_1 = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.MULTI_MEIDA_SOURCE = (int) (msg[4] & 0x0f);
		mCanInfo.MULTI_MEIDA_PLAYING_NUM = (int) (msg[7] & 0xff)
				+ (int) (msg[8] & 0xff) * 256;
		mCanInfo.MULTI_MEIDA_WHOLE_NUM = (int) (msg[9] & 0xff)
				+ (int) (msg[10] & 0xff) * 256;
		mCanInfo.MULTI_MEIDA_PLAYING_MINUTE = (int) (msg[11] & 0xff);
		mCanInfo.MULTI_MEIDA_PLAYING_SECOND = (int) (msg[12] & 0xff);
		mCanInfo.MULTI_MEIDA_PLAYING_PROGRESS = (int) (msg[13] & 0xff);
		mCanInfo.MULTI_MEIDA_PLAYING_STATUS = (int) (msg[14] & 0xff);
	}

}
