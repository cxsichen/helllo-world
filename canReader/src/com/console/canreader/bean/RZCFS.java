package com.console.canreader.bean;

import java.io.UnsupportedEncodingException;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;
import com.console.canreader.utils.Contacts;

import android.util.Log;

public class RZCFS extends AnalyzeUtils {

	// 数据类型
	public static final int comID = 1;
	// 方向盘按键
	public static final int STEERING_BUTTON_DATA = 0x21;
	// 方向盘按键
	public static final int STEERING_BUTTON_DATA_1 = 0x20;
	// 基本信息
	public static final int CAR_INFO_DATA_4 = 0x24;
	// 基本信息
	public static final int CAR_INFO_DATA_5 = 0x36;
	// 基本信息
	public static final int CAR_INFO_DATA_6 = 0x30;
	//车门信息
	public static final int CAR_INFO_DATA = 0x28;
	//版本信息
	public static final int CAR_INFO_DATA_1 = 0x7f;
	//空调信息
	public static final int CAR_INFO_DATA_2 = 0x23;
	


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
			case CAR_INFO_DATA_4:
				mCanInfo.CHANGE_STATUS = 4;
				analyzeCarInfoData_4(msg);
				break;
			case CAR_INFO_DATA_5:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_5(msg);
				break;
			case CAR_INFO_DATA_6:
				mCanInfo.CHANGE_STATUS = 8;
				analyzeCarInfoData_6(msg);
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
				mCanInfo.CHANGE_STATUS = 3;
				analyzeCarInfoData_2(msg);
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
	
	void analyzeCarInfoData_6(byte[] msg) {
		// TODO Auto-generated method stub	
		int temp = ((int) msg[4] & 0xFF) << 8 | ((int) msg[3] & 0xFF);
		if (temp < 32767) {
			mCanInfo.STERRING_WHELL_STATUS = temp/10;
		} else {
			mCanInfo.STERRING_WHELL_STATUS = (temp - 65536)/10;
		}
	}
	
	
	void analyzeCarInfoData_5(byte[] msg) {
		// TODO Auto-generated method stub	
		mCanInfo.OUTSIDE_TEMPERATURE =((int) ((msg[3] >> 7) & 0x01)==1)? (-(int) ((msg[3] >> 0) & 0x7F)):((int) ((msg[3] >> 0) & 0x7F));
	}
	
	void analyzeCarInfoData_4(byte[] msg) {
		// TODO Auto-generated method stub
		mCanInfo.BACK_LEFT_DISTANCE = (int) ((msg[3] >> 0) & 0xFF);
		mCanInfo.BACK_MIDDLE_LEFT_DISTANCE = (int) ((msg[4] >> 0) & 0xFF)>3?((int) ((msg[4] >> 0) & 0xFF)-1):(int) ((msg[4] >> 0) & 0xFF);
		mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE = (int) ((msg[4] >> 0) & 0xFF)>3?((int) ((msg[4] >> 0) & 0xFF)-1):(int) ((msg[4] >> 0) & 0xFF);
		mCanInfo.BACK_RIGHT_DISTANCE = (int) ((msg[5] >> 0) & 0xFF);
	}
	
	void analyzeSteeringButtonData_1(byte[] msg) {
		// TODO Auto-generated method stub
		switch ((int) (msg[3] & 0xFF)) {
		case 0x01:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLUP;
			break;
		case 0x02:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLDOW;
			break;
		case 0x06:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MUTE;
			break;
		case 0x07:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.HOME;
			break;
		case 0x08:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.BACK;
			break;
		case 0x0B:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.ANSWER_WITH_MENUUP;
			break;
		case 0x0C:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.HANGUP_WITH_MENUDOWN;
			break;
		case 0x0D:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
			break;
		default:
			mCanInfo.STEERING_BUTTON_MODE = 0;
			break;
		}

		mCanInfo.STEERING_BUTTON_STATUS = (int) (msg[4] & 0xFF);
	}

	void analyzeSteeringButtonData(byte[] msg) {
		// TODO Auto-generated method stub
		switch ((int) (msg[3] & 0xFF)) {
		case 0x01:
		case 0x2B:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLUP;
			break;
		case 0x02:
		case 0x2C:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLDOW;
			break;
		case 0x06:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MUTE;
			break;
		case 0x07:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.HOME;
			break;
		case 0x09:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.ANSWER;
			break;
		case 0x0A:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.HANGUP;
			break;
		case 0x20:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
			break;
		case 0x21:
		case 0x22:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.FM_AM;
			break;		
		case 0x23:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUUP;
			break;	
		case 0x24:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUDOWN;
			break;
		case 0x2D:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.POWER;
			break;
		case 0x2F:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.EQ;
			break;
		case 0x30:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.ENTER;
			break;
		case 0x32:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
			break;
		case 0x33:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VIDEO;
			break;
		case 0x34:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.BACK;
			break;
		case 0x35:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.HOME;
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
	}

	void analyzeCarInfoData_1(byte[] msg) {
		// TODO Auto-generated method stub
		int len = ((int) msg[2] & 0xFF);
		byte[] acscii = new byte[len];
		for (int i = 0; i < len; i++) {
			acscii[i] = msg[i + 3];
		}
		try {
			mCanInfo.VERSION = new String(acscii, "GBK");
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
		mCanInfo.AIR_CONDITIONER_STATUS = (int) ((msg[3] >> 7) & 0x01);
		mCanInfo.AC_INDICATOR_STATUS = (int) ((msg[3] >> 6) & 0x01);
		mCanInfo.CYCLE_INDICATOR = (int) ((msg[3] >> 5) & 0x01);

		mCanInfo.SMALL_LANTERN_INDICATOR = (int) ((msg[3] >> 3) & 0x01);

		mCanInfo.REAR_LAMP_INDICATOR = (int) ((msg[3] >> 1) & 0x01);
		
		int temp=(int) (msg[4] & 0xFF);
		if((temp==1)||(temp==2)){
			mCanInfo.PARALLEL_AIR_INDICATOR = 1;
		}else{
			mCanInfo.PARALLEL_AIR_INDICATOR = 0;
		}
		
		if((temp==4)||(temp==5)){
			mCanInfo.UPWARD_AIR_INDICATOR = 1;
		}else{
			mCanInfo.UPWARD_AIR_INDICATOR = 0;
		}
		
		if((temp==2)||(temp==3)||(temp==4)){
			mCanInfo.DOWNWARD_AIR_INDICATOR = 1;
		}else{
			mCanInfo.DOWNWARD_AIR_INDICATOR = 0;
		}

		mCanInfo.AIR_RATE = (int) (msg[5] & 0x0f);

		temp = (int) (msg[6] & 0xff);
		mCanInfo.DRIVING_POSITON_TEMP = temp == 0 ? 0 : temp == 0x10 ? 255
				: (16 + temp );
		mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = mCanInfo.DRIVING_POSITON_TEMP ;
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
