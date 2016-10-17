package com.console.canreader.bean;

import com.console.canreader.utils.Contacts;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

public class BeanFactory {

	private static AnalyzeUtils mAnalyzeUtils;
	
	public static AnalyzeUtils getCanInfo(Context context, byte[] mPacket,
			String canName) {
		if(mAnalyzeUtils==null){
			try {
				Class classManager = Class.forName("com.console.canreader.bean."+adjustcanName(canName));
				mAnalyzeUtils=(AnalyzeUtils) classManager.newInstance();
				mAnalyzeUtils.init(mPacket);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			mAnalyzeUtils.analyze(mPacket);
		}
		return mAnalyzeUtils;
		
	}
	
	private static String adjustcanName(String canName) {
		// TODO Auto-generated method stub
		switch (canName) {
		case Contacts.CANNAMEGROUP.SSToyotaBD:         //…–…„∑·ÃÔœµ¡–
		case Contacts.CANNAMEGROUP.SSToyotaRZ:
		case Contacts.CANNAMEGROUP.SSToyotaRAV4:
		case Contacts.CANNAMEGROUP.SSToyotaKMR:	
		case Contacts.CANNAMEGROUP.SSToyotaKLL:	
			canName=Contacts.CANNAMEGROUP.SSToyota;
			break;
		default:
			break;
		}
		return canName;
	}

	public static void setInfoEmpty(){
		mAnalyzeUtils=null;
	}
	
}
	

