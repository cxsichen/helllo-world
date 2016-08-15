package com.console.canreader.bean;

import com.console.canreader.utils.Contacts;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

public class BeanFactory {

	private static AnalyzeUtils mAnalyzeUtils;

	public static AnalyzeUtils getCanInfo(Context context, byte[] mPacket,
			int canType, int carType) {
		Log.i("cxs","========canType=========="+canType);
	    Log.i("cxs","========carType=========="+carType);
	    
		switch (canType) {
		case 0: // 睿志诚
			switch (carType) {
			case 0:                           //大众
				getRZCVolkswagen(mPacket);
				break;
			case 1:                           //大众高尔夫
				getRZCVolkswagenGolf(mPacket);
				break;
			case 2:                           //本田
				getRZCHonda(mPacket);
				break;
			case 8:                           //日产
				getRZCNISSAN(mPacket);
				break;
			default:
				break;
			}
			break;
		case 1: // 尚摄
			switch (carType) {
			case 2:                           //本田
				getSSHonda(mPacket);
				break;
			case 3:                            //丰田
				getSSToyota(mPacket);
				break;
			case 4:                            //丰田锐志
				getSSToyotaRZ(mPacket);
				break;
			case 5:                           //通用
				getSSGE(mPacket);
				break;
			case 6:                           //风光580
				getSSDFFG(mPacket);
				break;
			default:
				break;
			}			
			break;
		default:
			break;
		}
		return mAnalyzeUtils;
	}
	
	public static void setInfoEmpty(){
		mAnalyzeUtils=null;
	}
	
	private static void getSSToyotaRZ(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new SSToyotaRZ(mPacket,3);
		} else {
			mAnalyzeUtils.analyze(mPacket,3);//第四位是信息type位
		}
	}
	
	private static void getSSHonda(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new SSHonda(mPacket,3);
		} else {
			mAnalyzeUtils.analyze(mPacket,3);//第四位是信息type位
		}
	}
	
	private static void getSSToyota(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new SSToyota(mPacket,3);
		} else {
			mAnalyzeUtils.analyze(mPacket,3);//第四位是信息type位
		}
	}
	
	private static void getSSGE(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new SSGE(mPacket,3);
		} else {
			mAnalyzeUtils.analyze(mPacket,3);//第四位是信息type位
		}
	}
	
	
	private static void getSSDFFG(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new SSDFFG(mPacket,3);
		} else {
			mAnalyzeUtils.analyze(mPacket,3);//第四位是信息type位
		}
	}
	
	private static void getRZCHonda(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new RZCHonda(mPacket,1);
		} else {
			mAnalyzeUtils.analyze(mPacket,1);//第二位是信息type位
		}
	}
	
	private static void getRZCNISSAN(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new RZCNISSAN(mPacket,1);
		} else {
			mAnalyzeUtils.analyze(mPacket,1);//第二位是信息type位
		}
	}

	private static void getRZCVolkswagen(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new RZCVolkswagen(mPacket,1);
		} else {
			mAnalyzeUtils.analyze(mPacket,1);//第二位是信息type位
		}
	}

	private static void getRZCVolkswagenGolf(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new RZCVolkswagenGolf(mPacket,1);
		} else {
			mAnalyzeUtils.analyze(mPacket,1);//第二位是信息type位
		}
	}
}
