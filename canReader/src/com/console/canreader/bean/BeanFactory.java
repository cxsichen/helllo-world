package com.console.canreader.bean;

import com.console.canreader.utils.Contacts;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

public class BeanFactory {

	private static AnalyzeUtils mAnalyzeUtils;
	private static String utilCanName = null;

	public static AnalyzeUtils getCanInfo(Context context, byte[] mPacket,
			String canName) {
		if (mAnalyzeUtils == null) {
			try {
				if (utilCanName == null) {
					utilCanName = adjustcanName(canName);
				}
				Class classManager = Class
						.forName("com.console.canreader.bean."
								+ adjustcanName(utilCanName));
				mAnalyzeUtils = (AnalyzeUtils) classManager.newInstance();
				mAnalyzeUtils.init(mPacket);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			mAnalyzeUtils.analyze(mPacket);
		}
		return mAnalyzeUtils;
	}

	private static String adjustcanName(String canName) {
		// TODO Auto-generated method stub
		switch (canName) {
		case Contacts.CANNAMEGROUP.SSToyotaBD: // 尚摄丰田系列
		case Contacts.CANNAMEGROUP.SSToyotaRZ:
		case Contacts.CANNAMEGROUP.SSToyotaRAV4:
		case Contacts.CANNAMEGROUP.SSToyotaKMR:
		case Contacts.CANNAMEGROUP.SSToyotaKLL:
			canName = Contacts.CANNAMEGROUP.SSToyota;
			break;
		case Contacts.CANNAMEGROUP.SSHondaSY: // 尚摄本田系列
		case Contacts.CANNAMEGROUP.SSHondaLP:
			canName = Contacts.CANNAMEGROUP.SSHonda;
			break;
		case Contacts.CANNAMEGROUP.SSTrumpchiGA6: // 广汽传祺
		case Contacts.CANNAMEGROUP.SSTrumpchiGS4:
		case Contacts.CANNAMEGROUP.SSTrumpchiGS4HIGH:
		case Contacts.CANNAMEGROUP.SSTrumpchiGS5:
			canName = Contacts.CANNAMEGROUP.SSTrumpchi;
			break;
		case Contacts.CANNAMEGROUP.SSHyundai16MT:
			canName = Contacts.CANNAMEGROUP.SSHyundai;
			break;
		case Contacts.CANNAMEGROUP.SSNissanWithout360:
			canName = Contacts.CANNAMEGROUP.SSNissan;
			break;
		case Contacts.CANNAMEGROUP.SSFordEDGE:
		case Contacts.CANNAMEGROUP.SSFordFocus:
			canName = Contacts.CANNAMEGROUP.SSFord;
			break;
		case Contacts.CANNAMEGROUP.SSPeugeot: // 标致
		case Contacts.CANNAMEGROUP.SSPeugeotSEGA:
		case Contacts.CANNAMEGROUP.SSPeugeotC4:
		case Contacts.CANNAMEGROUP.SSPeugeotC5:
		case Contacts.CANNAMEGROUP.SSPeugeotC55:
		case Contacts.CANNAMEGROUP.SSPeugeot307:
		case Contacts.CANNAMEGROUP.SSPeugeot308:
		case Contacts.CANNAMEGROUP.SSPeugeot3008ALL:
		case Contacts.CANNAMEGROUP.SSPeugeot2008:
		case Contacts.CANNAMEGROUP.SSPeugeot3008LOW:
		case Contacts.CANNAMEGROUP.SSPeugeot301:
		case Contacts.CANNAMEGROUP.SSPeugeotC3XR:
			canName = Contacts.CANNAMEGROUP.SSPeugeot;
			break;
		case Contacts.CANNAMEGROUP.SSPeugeot308S:
			canName = Contacts.CANNAMEGROUP.SSPeugeot308S;
			break;
		case Contacts.CANNAMEGROUP.SSPeugeot408:
			canName = Contacts.CANNAMEGROUP.SSPeugeot408;
			break;
		case Contacts.CANNAMEGROUP.SSPeugeotC4L:
			canName = Contacts.CANNAMEGROUP.SSPeugeotC4L;
			break;
		case Contacts.CANNAMEGROUP.SSPeugeot508LOW:
			canName = Contacts.CANNAMEGROUP.SSPeugeot508LOW;
			break;
		case Contacts.CANNAMEGROUP.SSPeugeot508HIGH:
			canName = Contacts.CANNAMEGROUP.SSPeugeot508HIGH;
			break;
		case Contacts.CANNAMEGROUP.SSPeugeotDS4HIGH:
			canName = Contacts.CANNAMEGROUP.SSPeugeotDS4HIGH;
			break;
		case Contacts.CANNAMEGROUP.SSPeugeotDS4LOW:
			canName = Contacts.CANNAMEGROUP.SSPeugeotDS4LOW;
			break;
		case Contacts.CANNAMEGROUP.SSPeugeotDS5LSHIGH:
			canName = Contacts.CANNAMEGROUP.SSPeugeotDS5LSHIGH;
			break;
		case Contacts.CANNAMEGROUP.SSPeugeotDS5LSLOW:
			canName = Contacts.CANNAMEGROUP.SSPeugeotDS5LSLOW;
			break;
		case Contacts.CANNAMEGROUP.SSPeugeotDS5HIGH:
			canName = Contacts.CANNAMEGROUP.SSPeugeotDS5HIGH;
			break;
		case Contacts.CANNAMEGROUP.SSPeugeotDS5LOW:
			canName = Contacts.CANNAMEGROUP.SSPeugeotDS5LOW;
			break;
		default:
			break;
		}
		return canName;
	}

	public static void setInfoEmpty() {
		mAnalyzeUtils = null;
		utilCanName = null;
	}

}
