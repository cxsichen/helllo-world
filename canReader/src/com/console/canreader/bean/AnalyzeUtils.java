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
    * mCanInfo.CHANGE_STATUS表示更改的状态
    * 
    * STEERING_BUTTON_DATA--------------mCanInfo.CHANGE_STATUS = 2         按键状态
    * AIR_CONDITIONER_DATA--------------mCanInfo.CHANGE_STATUS = 3         空调状态
    * BACK_RADER_DATA-------------------mCanInfo.CHANGE_STATUS = 4         后雷达状态
    * FRONT_RADER_DATA------------------mCanInfo.CHANGE_STATUS = 5         前雷达状态
    * RADAR_DATA------------------------mCanInfo.CHANGE_STATUS = 11        雷达状态（前后都有）
    * STEERING_TURN_DATA----------------mCanInfo.CHANGE_STATUS = 8         方向盘状态
    * CAR_INFO_DATA---------------------mCanInfo.CHANGE_STATUS = 10        报警状态（车门报警，车身信息报警）
    *                                                                      车身信息
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
