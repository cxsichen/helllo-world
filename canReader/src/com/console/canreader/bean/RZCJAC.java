package com.console.canreader.bean;

import java.io.UnsupportedEncodingException;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;
import com.console.canreader.utils.Contacts;

import android.util.Log;

public class RZCJAC extends AnalyzeUtils {

	// 数据类型
	public static final int comID = 1;
	// 方向盘按键
	public static final int STEERING_BUTTON_DATA = 0x21;
	// 基本信息
	public static final int CAR_INFO_DATA = 0x28;
	public static final int CAR_INFO_DATA_1 = 0x7f;
	public static final int CAR_INFO_DATA_2 = 0x38;
	public static final int CAR_INFO_DATA_3 = 0x39;

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
			default:
				mCanInfo.CHANGE_STATUS = 8888;
				break;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	void analyzeSteeringButtonData(byte[] msg) {
		// TODO Auto-generated method stub
		switch ((int) (msg[3] & 0xFF)) {
		case 0x01:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLUP;
			break;
		case 0x02:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLDOW;
			break;
		case 0x0C:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUDOWN;
			break;
		case 0x0B:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUUP;
			break;
		case 0x07:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
			break;
		case 0x09:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.ANSWER;
			break;
		case 0x0A:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.HANGUP;
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

	void analyzeCarInfoData(byte[] msg) {
		// TODO Auto-generated method stub
		mCanInfo.RIGHT_FORONTDOOR_STATUS = (int) ((msg[3] >> 7) & 0x01);
		mCanInfo.LEFT_FORONTDOOR_STATUS = (int) ((msg[3] >> 6) & 0x01);
		mCanInfo.RIGHT_BACKDOOR_STATUS = (int) ((msg[3] >> 5) & 0x01);
		mCanInfo.LEFT_BACKDOOR_STATUS = (int) ((msg[3] >> 4) & 0x01);
		mCanInfo.TRUNK_STATUS = (int) ((msg[3] >> 3) & 0x01);
		mCanInfo.HOOD_STATUS = (int) ((msg[3] >> 2) & 0x01);

		mCanInfo.SAFETY_BELT_STATUS = ((int) ((msg[4] >> 3) & 0x01)) == 0 ? 1
				: 0;
		mCanInfo.HANDBRAKE_STATUS = ((int) ((msg[4] >> 4) & 0x01));
	}

	void analyzeCarInfoData_1(byte[] msg) {
		// TODO Auto-generated method stub
		int len = ((int) msg[2] & 0xFF);
		byte[] acscii = new byte[len];
		for (int i = 0; i < len; i++) {
			acscii[i] = msg[i + 3];
		}
		try {
			mCanInfo.VEHICLE_NO = new String(acscii, "GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static String dataSave = "";

	void analyzeCarInfoData_2(byte[] msg) {
		// TODO Auto-generated method stub
		if (dataSave.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			dataSave = BytesUtil.bytesToHexString(msg);
		}
		mCanInfo.TPMS_FL_TEMP = (int) (msg[3] & 0xff) - 40;
		mCanInfo.TPMS_FR_TEMP = (int) (msg[4] & 0xff) - 40;
		mCanInfo.TPMS_BL_TEMP = (int) (msg[5] & 0xff) - 40;
		mCanInfo.TPMS_BR_TEMP = (int) (msg[6] & 0xff) - 40;

		mCanInfo.TPMS_FL_PRESSUE = getFloatRound((msg[7] & 0xff) * 0.0274, 10);
		mCanInfo.TPMS_FR_PRESSUE = getFloatRound((msg[8] & 0xff) * 0.0274, 10);
		mCanInfo.TPMS_BL_PRESSUE = getFloatRound((msg[9] & 0xff) * 0.0274, 10);
		mCanInfo.TPMS_BR_PRESSUE = getFloatRound((msg[10] & 0xff) * 0.0274, 10);

	}

	/**
	 * 给参数返回指定小数点后 a 位的四舍五入
	 * 
	 * @param sourceData
	 *            要取舍的原数据
	 * @param a
	 *            小数点 后的 位数（如：10：小数点后1位；100：小数据后2位以此类推）
	 * @return 舍取后的 数据
	 */
	public static float getFloatRound(double sourceData, int a) {
		int i = (int) Math.round(sourceData * a); // 小数点后 a 位前移，并四舍五入
		float f2 = (float) (i / (float) a); // 还原小数点后 a 位
		return f2;
	}

	static String dataSave1 = "";

	void analyzeCarInfoData_3(byte[] msg) {
		// TODO Auto-generated method stub
		if (dataSave1.equals(BytesUtil.bytesToHexString(msg))) {
			mCanInfo.CHANGE_STATUS = 8888;
			return;
		} else {
			dataSave1 = BytesUtil.bytesToHexString(msg);
		}

		mCanInfo.TPMS_FL_WARING = (int) (msg[3] & 0xff);
		mCanInfo.TPMS_FR_WARING = (int) (msg[4] & 0xff);
		mCanInfo.TPMS_BL_WARING = (int) (msg[5] & 0xff);
		mCanInfo.TPMS_BR_WARING = (int) (msg[6] & 0xff);

	}

}
