package com.console.canreader.bean;

import java.io.UnsupportedEncodingException;

import com.console.canreader.bean.AnalyzeUtils.AnalyzeUtilsCallback;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;

import android.util.Log;

public class RZCJingKooM20 extends AnalyzeUtils {

	// 基本信息
	public static final int CAR_INFO_DATA = 0xFF;

	public RZCJingKooM20(byte[] msg, int i) {
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
			default:
				mCanInfo.CHANGE_STATUS = 8888;
				break;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	void analyzeCarInfoData(byte[] msg) {
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
