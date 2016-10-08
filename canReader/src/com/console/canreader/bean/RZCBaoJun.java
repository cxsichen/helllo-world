package com.console.canreader.bean;

import java.io.UnsupportedEncodingException;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;

import android.util.Log;

public class RZCBaoJun extends AnalyzeUtils {

	// 基本信息
	public static final int CAR_INFO_DATA = 0x28;
	public static final int CAR_INFO_DATA_1 = 0x7f;
	// 方向盘按键
    public static final int STEERING_BUTTON_DATA = 0x21;
	// 后雷达信息
	public static final int BACK_RADER_DATA = 0x26;
	// 前雷达信息
	public static final int FRONT_RADER_DATA = 0x27;
	// 方向盘转角
    public static final int STEERING_TURN_DATA = 0x30;
    
	public RZCBaoJun(byte[] msg, int i) {
		// TODO Auto-generated constructor stub
		super(msg, i);
	}

	public CanInfo getCanInfo() {
		return mCanInfo;
	}

	@Override
	public void analyzeEach(byte[] msg, int i) {
		// TODO Auto-generated method stub
		try {
			if (msg == null)
				return;
			switch ((int) (msg[i] & 0xFF)) {
			case CAR_INFO_DATA:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData(msg);
				break;
			case CAR_INFO_DATA_1:
				mCanInfo.CHANGE_STATUS = 10;
				analyzeCarInfoData_1(msg);
				break;
			case STEERING_BUTTON_DATA:
				mCanInfo.CHANGE_STATUS = 2;
				analyzeSteeringButtonData(msg);
				break;
			case BACK_RADER_DATA:
				mCanInfo.CHANGE_STATUS = 4;
				analyzeBackRaderData(msg);
				break;
			case FRONT_RADER_DATA:
				mCanInfo.CHANGE_STATUS = 5;
				analyzeFrontRaderData(msg);
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
	
	

	void analyzeSteeringTurnData(byte[] msg) {
		// TODO Auto-generated method stub
		int temp = ((int) msg[4] & 0xFF) << 8 | ((int) msg[3] & 0xFF);
		if (temp < 32767) {
			mCanInfo.STERRING_WHELL_STATUS = -(temp/(9232/540));
		} else {
			mCanInfo.STERRING_WHELL_STATUS = (65536 - temp)/(9232/540);
		}

	}
	
	void analyzeFrontRaderData(byte[] msg) {
		// TODO Auto-generated method stub
		mCanInfo.FRONT_LEFT_DISTANCE = (int) (msg[3] & 0xff);
		mCanInfo.FRONT_MIDDLE_LEFT_DISTANCE = (int) (msg[4] & 0xff);
		mCanInfo.FRONT_MIDDLE_RIGHT_DISTANCE = (int) (msg[5] & 0xff);
		mCanInfo.FRONT_RIGHT_DISTANCE = (int) (msg[6] & 0xff);
	}

	void analyzeBackRaderData(byte[] msg) {
		// TODO Auto-generated method stub
		mCanInfo.BACK_LEFT_DISTANCE = (int) (msg[3] & 0xff);
		mCanInfo.BACK_MIDDLE_LEFT_DISTANCE = (int) (msg[4] & 0xff);
		mCanInfo.BACK_MIDDLE_RIGHT_DISTANCE = (int) (msg[5] & 0xff);
		mCanInfo.BACK_RIGHT_DISTANCE = (int) (msg[6] & 0xff);
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
		
		if((mCanInfo.RIGHT_FORONTDOOR_STATUS==0)&&(mCanInfo.RIGHT_BACKDOOR_STATUS==0)
				&&(mCanInfo.LEFT_BACKDOOR_STATUS==0)){
			if(((int) (msg[3] >> 1) & 0x01)==0){
				mCanInfo.RIGHT_FORONTDOOR_STATUS=0;
				mCanInfo.RIGHT_BACKDOOR_STATUS=0;
				mCanInfo.LEFT_BACKDOOR_STATUS=0;
			}else{
				mCanInfo.RIGHT_FORONTDOOR_STATUS=1;
				mCanInfo.RIGHT_BACKDOOR_STATUS=1;
				mCanInfo.LEFT_BACKDOOR_STATUS=1;
			}
		}
		
		if(mCanInfo.LEFT_FORONTDOOR_STATUS==0){
			if(((int) (msg[3] >> 0) & 0x01)==0){
				mCanInfo.LEFT_FORONTDOOR_STATUS=0;
			}else{
				mCanInfo.LEFT_FORONTDOOR_STATUS=1;
			}
		}

		mCanInfo.SAFETY_BELT_STATUS=((int) ((msg[4] >> 3) & 0x01))==0?1:0;
		mCanInfo.HANDBRAKE_STATUS=((int) ((msg[4] >> 4) & 0x01));
	}
	
	void analyzeCarInfoData_1(byte[] msg) {
		// TODO Auto-generated method stub
		int len=((int) msg[2] & 0xFF);
		byte[] acscii=new byte[len];
		for(int i=0;i<len;i++){
			acscii[i]=msg[i+3];
		}
		try {
			mCanInfo.VEHICLE_NO =new String(acscii,"GBK");			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
