package com.console.canreader.bean;

import com.console.canreader.service.CanInfo;

import android.util.Log;

public abstract class AnalyzeUtils {

	CanInfo mCanInfo;

	public AnalyzeUtils() {

	}
	
	public void init(byte[] msg){
		mCanInfo = new CanInfo();
		analyze(msg);
	}

	public CanInfo getCanInfo() {
		return mCanInfo;
	}
   /**
    * mCanInfo.CHANGE_STATUS��ʾ���ĵ�״̬
    * 
    * STEERING_BUTTON_DATA--------------mCanInfo.CHANGE_STATUS = 2         ����״̬
    * AIR_CONDITIONER_DATA--------------mCanInfo.CHANGE_STATUS = 3         �յ�״̬
    * BACK_RADER_DATA-------------------mCanInfo.CHANGE_STATUS = 4         ���״�״̬
    * FRONT_RADER_DATA------------------mCanInfo.CHANGE_STATUS = 5         ǰ�״�״̬
    * RADAR_DATA------------------------mCanInfo.CHANGE_STATUS = 11        �״�״̬��ǰ���У�
    * STEERING_TURN_DATA----------------mCanInfo.CHANGE_STATUS = 8         ������״̬
    * CAR_INFO_DATA---------------------mCanInfo.CHANGE_STATUS = 10        ����״̬�����ű�����������Ϣ������
    *                                                                      ������Ϣ
    */
	public void analyze(byte[] msg) {
		// TODO Auto-generated method stub
		analyzeEach(msg);
		if (mAnalyzeUtilsCallback != null)
			mAnalyzeUtilsCallback.onChange(mCanInfo.CHANGE_STATUS);

	}

	public interface AnalyzeUtilsCallback {
		void onChange(int i);
	}

	AnalyzeUtilsCallback mAnalyzeUtilsCallback;

	public void setAnalyzeUtilsCallback(
			AnalyzeUtilsCallback mAnalyzeUtilsCallback) {
		this.mAnalyzeUtilsCallback = mAnalyzeUtilsCallback;
	}

	public void analyzeEach(byte[] msg) {
		// TODO Auto-generated method stub

	}

}
