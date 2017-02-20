package com.console.canreader.bean;

import java.io.UnsupportedEncodingException;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;
import com.console.canreader.utils.Contacts;

import android.util.Log;

public class RZCBAICSB extends AnalyzeUtils {

	// 数据类型
	public static final int comID = 1;
	// 方向盘按键
	public static final int STEERING_BUTTON_DATA = 0x20;
	//空调信息
	public static final int CAR_INFO_DATA_2 = 0x21;
	//车门信息
  	public static final int CAR_INFO_DATA = 0x24;
    // 基本信息
  	public static final int CAR_INFO_DATA_6 = 0x29;
     //版本信息
   	public static final int CAR_INFO_DATA_1 = 0x30;
    // 基本信息
    public static final int CAR_INFO_DATA_4 = 0x22;
    // 基本信息
 	public static final int CAR_INFO_DATA_5 = 0x23;

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
			case CAR_INFO_DATA_2:
				mCanInfo.CHANGE_STATUS = 3;
				analyzeCarInfoData_2(msg);
				break;
			case CAR_INFO_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData(msg);
				break;
			case CAR_INFO_DATA_6:
				mCanInfo.CHANGE_STATUS = 8;
				analyzeCarInfoData_6(msg);
				break;
			case CAR_INFO_DATA_1:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_1(msg);
				break;
			case CAR_INFO_DATA_4:
				mCanInfo.CHANGE_STATUS = 4;
				analyzeCarInfoData_4(msg);
				break;
			case CAR_INFO_DATA_5:
				mCanInfo.CHANGE_STATUS = 5;
				analyzeCarInfoData_5(msg);
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
	
	void analyzeCarInfoData_7(byte[] msg) {
		// TODO Auto-generated method stub	
		mCanInfo.LIGHT_TOP_LIGHT=(int) (msg[3]>>6)& 0x03;
		mCanInfo.AUTOMATIC_LAMP_CLOSE=(int) (msg[3]>>4)& 0x03;
		
		mCanInfo.DOOR_UNLOCKING=(int) (msg[4]>>7)& 0x01;		
		mCanInfo.AUTOMATIC_LOCKING=(int) (msg[4]>>6)& 0x01;
	}
	
	
	void analyzeCarInfoData_6(byte[] msg) {
		// TODO Auto-generated method stub	
		int temp = ((int) msg[4] & 0xFF) << 8 | ((int) msg[3] & 0xFF);
		mCanInfo.STERRING_WHELL_STATUS=(temp-0x1f00)/9;
	}
		
	void analyzeCarInfoData_5(byte[] msg) {
		// TODO Auto-generated method stub	
		mCanInfo.FRONT_LEFT_DISTANCE = ((int) ((msg[3] >> 0) & 0xFF)==0)?0:(5-(int) ((msg[3] >> 0) & 0xFF));
		mCanInfo.FRONT_MIDDLE_LEFT_DISTANCE =  ((int) ((msg[4] >> 0) & 0xFF)==0)?0:(5-(int) ((msg[4] >> 0) & 0xFF));
		mCanInfo.FRONT_MIDDLE_RIGHT_DISTANCE =  ((int) ((msg[5] >> 0) & 0xFF)==0)?0:(5-(int) ((msg[5] >> 0) & 0xFF));
		mCanInfo.FRONT_RIGHT_DISTANCE =  ((int) ((msg[6] >> 0) & 0xFF)==0)?0:(5-(int) ((msg[6] >> 0) & 0xFF));
	}
	
	void analyzeCarInfoData_4(byte[] msg) {
		// TODO Auto-generated method stub
		mCanInfo.BACK_LEFT_DISTANCE = ((int) ((msg[3] >> 0) & 0xFF)==0)?0:(5-(int) ((msg[3] >> 0) & 0xFF));
		mCanInfo.BACK_MIDDLE_LEFT_DISTANCE =  ((int) ((msg[4] >> 0) & 0xFF)==0)?0:(5-(int) ((msg[4] >> 0) & 0xFF));
		mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE =  ((int) ((msg[5] >> 0) & 0xFF)==0)?0:(5-(int) ((msg[5] >> 0) & 0xFF));
		mCanInfo.BACK_RIGHT_DISTANCE =  ((int) ((msg[6] >> 0) & 0xFF)==0)?0:(5-(int) ((msg[6] >> 0) & 0xFF));
	}
	
	void analyzeSteeringButtonData_1(byte[] msg) {
		// TODO Auto-generated method stub
		switch ((int) (msg[3] & 0xFF)) {
		case 0x01:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.POWER;
			break;
		case 0x02:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
			break;
		case 0x03:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MUTE;
			break;
		case 0x04:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.PHONE;
			break;
		case 0x05:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.PHONE_APP;
			break;
		case 0x06:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.HOME;
			break;
		case 0x07:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.BACK;
			break;
		case 0x08:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.FM_AM;
			break;
		case 0x09:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MUSIC;
			break;
		case 0x0A:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.ENTER;
			break;
		case 0x0B:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.KNOBVOLUMEUP;
			mCanInfo.CAR_VOLUME_KNOB = (int) (msg[4] & 0xFF);
			break;
		case 0x0C:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.KNOBVOLUMEDOWN;
			mCanInfo.CAR_VOLUME_KNOB = (int) (msg[4] & 0xFF);
			break;
		case 0x0D:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.EQ;
			break;
		case 0x0E:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLDOW;
			break;
		case 0x0F:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLUP;
			break;
		case 0x10:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SETTING;
			break;
		case 0x11:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUUP;
			break;
		case 0x12:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUDOWN;
			break;
		case 0x13:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MAP;
			break;
		case 0x14:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.PHONE;
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
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLUP;
			break;
		case 0x02:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.VOLDOW;
			break;
		case 0x03:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUUP;
			break;	
		case 0x04:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.MENUDOWN;
			break;
		case 0x05:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.PHONE;
			break;
		case 0x06:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.PHONE_WHIT_MUTE;
			break;
		case 0x07:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.SRC;
			break;
		case 0x08:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.KNOBVOLUMEUP;
			mCanInfo.CAR_VOLUME_KNOB=(int) (msg[4] & 0xFF);
			break;
		case 0x09:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.KNOBVOLUMEDOWN;
			mCanInfo.CAR_VOLUME_KNOB=(int) (msg[4] & 0xFF);
			break;
		case 0x0A:
			mCanInfo.STEERING_BUTTON_MODE = Contacts.KEYEVENT.POWER;
			break;
		case 0x16:
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
		mCanInfo.HOOD_STATUS= (int) ((msg[3] >> 2) & 0x01);
		mCanInfo.HANDBRAKE_STATUS= (int) ((msg[4] >> 1) & 0x01);
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

	void analyzeCarInfoData_2(byte[] msg) {
		// TODO Auto-generated method stub
		mCanInfo.AIR_CONDITIONER_STATUS = (int) ((msg[3] >> 7) & 0x01);
		mCanInfo.AC_INDICATOR_STATUS = (int) ((msg[3] >> 6) & 0x01);
		mCanInfo.CYCLE_INDICATOR = (int) ((msg[3] >> 5) & 0x01);
		
		
		mCanInfo.UPWARD_AIR_INDICATOR= (int) ((msg[4] >> 7) & 0x01);
		mCanInfo.PARALLEL_AIR_INDICATOR= (int) ((msg[4] >> 6) & 0x01);
		mCanInfo.DOWNWARD_AIR_INDICATOR= (int) ((msg[4] >> 5) & 0x01);
		
		mCanInfo.AIR_RATE = (int) ((msg[4] >> 0) & 0x0F);

		mCanInfo.DRIVING_POSITON_TEMP = (int) ((msg[5] >> 0) & 0xFF);
		mCanInfo.DEPUTY_DRIVING_POSITON_TEMP = mCanInfo.DRIVING_POSITON_TEMP ;
		
		mCanInfo.MAX_FRONT_LAMP_INDICATOR = (int) ((msg[7] >> 7) & 0x01);
		mCanInfo.REAR_LAMP_INDICATOR = (int) ((msg[7] >> 6) & 0x01);
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
