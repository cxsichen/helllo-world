package com.console.canreader.bean;

import com.console.canreader.utils.Contacts;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

public class BeanFactory {

	private static AnalyzeUtils mAnalyzeUtils;

	public static AnalyzeUtils getCanInfo(Context context, byte[] mPacket,
			int canType, int carType) {
		switch (canType) {
		case 0: // 睿志诚
			switch (carType) {
			case 0:                           //大众
				getRZCVolkswagen(mPacket);
				break;
			case 1:                           //大众高尔夫          未完成
				getRZCVolkswagenGolf(mPacket);
				break;
			case 2:                           //本田                         未完成
				getRZCHonda(mPacket);
				break;
			case 3:                          //丰田 丰田锐志
			case 4:  
				getRZCToyota(mPacket);		 //丰田 丰田CAMRY
				break;
			case 8:                           //日产
				getRZCNISSAN(mPacket);
				break;
			case 9:                           //广汽传祺
				getRZCTrumpche(mPacket);
				break;
			case 10:                           //福克斯
				getRZCFOCUS(mPacket);
				break;
			case 11:                           //本田CRV
				getRZCHondaCRV(mPacket);
				break;
			case 12:                           //本田DA
				getRZCHondaDA(mPacket);
				break;
			case 13:							//别克 GM通用
				getRZCBuickGM(mPacket);
				break;
			case 14:							//福特锐界
				getRZCEDGE(mPacket);
				break;
			case 15:							//荣威360
				getRZCRoewe360(mPacket);
				break;
			case 16:							//MG GS MG锐腾
				getRZCMGGS(mPacket);
				break;
			case 17:							//奔腾X80
				getRZCBESTURNx80(mPacket);
				break;
			case 18:							//海马M3
				getRZCFHCm3(mPacket);
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
	
	private static void getRZCBESTURNx80(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new RZCBESTURNx80(mPacket,1);
		} else {
			mAnalyzeUtils.analyze(mPacket,2);//第三位是信息type位
		}
	}
	
	private static void getRZCFHCm3(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new RZCFHCm3(mPacket,1);
		} else {
			mAnalyzeUtils.analyze(mPacket,2);//第三位是信息type位
		}
	}
	
	private static void getRZCMGGS(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new RZCMGGS(mPacket,1);
		} else {
			mAnalyzeUtils.analyze(mPacket,1);//第二位是信息type位
		}
	}
	private static void getRZCRoewe360(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new RZCRoewe360(mPacket,1);
		} else {
			mAnalyzeUtils.analyze(mPacket,1);//第二位是信息type位
		}
	}
	private static void getRZCEDGE(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new RZCEDGE(mPacket,1);
		} else {
			mAnalyzeUtils.analyze(mPacket,1);//第二位是信息type位
		}
	}
	
	private static void getRZCBuickGM(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new RZCBuickGM(mPacket,1);
		} else {
			mAnalyzeUtils.analyze(mPacket,1);//第二位是信息type位
		}
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
	
	private static void getRZCToyota(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new RZCToyota(mPacket,1);
		} else {
			mAnalyzeUtils.analyze(mPacket,1);//第二位是信息type位
		}
	}
	
	private static void getRZCHonda(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new RZCHonda(mPacket,1);
		} else {
			mAnalyzeUtils.analyze(mPacket,1);//第二位是信息type位
		}
	}
	
	private static void getRZCFOCUS(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new RZCFOCUS(mPacket,1);
		} else {
			mAnalyzeUtils.analyze(mPacket,1);//第二位是信息type位
		}
	}
	
	private static void getRZCHondaCRV(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new RZCHondaCRV(mPacket,1);
		} else {
			mAnalyzeUtils.analyze(mPacket,1);//第二位是信息type位
		}
	}
	
	
	private static void getRZCHondaDA(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new RZCHondaDA(mPacket,1);
		} else {
			mAnalyzeUtils.analyze(mPacket,1);//第二位是信息type位
		}
	}
	
	private static void getRZCTrumpche(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new RZCTrumpche(mPacket,1);
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
