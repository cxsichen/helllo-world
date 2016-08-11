package com.console.canreader.bean;

import com.console.canreader.service.CanInfo;

import android.util.Log;

public abstract class AnalyzeUtils {

	CanInfo mCanInfo;

	public AnalyzeUtils(byte[] msg, int i) {
		mCanInfo = new CanInfo();
		analyze(msg, i);
	}

	public CanInfo getCanInfo() {
		return mCanInfo;
	}

	public void analyze(byte[] msg, int i) {
		// TODO Auto-generated method stub
		analyzeEach(msg, i);
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

	public void analyzeEach(byte[] msg, int i) {
		// TODO Auto-generated method stub

	}

}
