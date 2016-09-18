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
		case 0: // �־��
			switch (carType) {
			case 0:                           //����
				getRZCVolkswagen(mPacket);
				break;
			case 1:                           //���ڸ߶���          δ���
				getRZCVolkswagenGolf(mPacket);
				break;
			case 2:                           //����                         δ���
				getRZCHonda(mPacket);
				break;
			case 3:                          //���� ������־
			case 4:  
				getRZCToyota(mPacket);		 //���� ����CAMRY
				break;
			case 8:                           //�ղ�
				getRZCNISSAN(mPacket);
				break;
			case 9:                           //��������
				getRZCTrumpche(mPacket);
				break;
			case 10:                           //����˹
				getRZCFOCUS(mPacket);
				break;
			case 11:                           //����CRV
				getRZCHondaCRV(mPacket);
				break;
			case 12:                           //����DA
				getRZCHondaDA(mPacket);
				break;
			case 13:							//��� GMͨ��
				getRZCBuickGM(mPacket);
				break;
			case 14:							//�������
				getRZCEDGE(mPacket);
				break;
			case 15:							//����360
				getRZCRoewe360(mPacket);
				break;
			case 16:							//MG GS MG����
				getRZCMGGS(mPacket);
				break;
			case 17:							//����X80
				getRZCBESTURNx80(mPacket);
				break;
			case 18:							//����M3
				getRZCFHCm3(mPacket);
				break;
			default:						
				break;
			}
			break;
		case 1: // ����
			switch (carType) {
			case 2:                           //����
				getSSHonda(mPacket);
				break;
			case 3:                            //����
				getSSToyota(mPacket);
				break;
			case 4:                            //������־
				getSSToyotaRZ(mPacket);
				break;
			case 5:                           //ͨ��
				getSSGE(mPacket);
				break;
			case 6:                           //���580
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
			mAnalyzeUtils.analyze(mPacket,2);//����λ����Ϣtypeλ
		}
	}
	
	private static void getRZCFHCm3(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new RZCFHCm3(mPacket,1);
		} else {
			mAnalyzeUtils.analyze(mPacket,2);//����λ����Ϣtypeλ
		}
	}
	
	private static void getRZCMGGS(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new RZCMGGS(mPacket,1);
		} else {
			mAnalyzeUtils.analyze(mPacket,1);//�ڶ�λ����Ϣtypeλ
		}
	}
	private static void getRZCRoewe360(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new RZCRoewe360(mPacket,1);
		} else {
			mAnalyzeUtils.analyze(mPacket,1);//�ڶ�λ����Ϣtypeλ
		}
	}
	private static void getRZCEDGE(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new RZCEDGE(mPacket,1);
		} else {
			mAnalyzeUtils.analyze(mPacket,1);//�ڶ�λ����Ϣtypeλ
		}
	}
	
	private static void getRZCBuickGM(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new RZCBuickGM(mPacket,1);
		} else {
			mAnalyzeUtils.analyze(mPacket,1);//�ڶ�λ����Ϣtypeλ
		}
	}

	public static void setInfoEmpty(){
		mAnalyzeUtils=null;
	}
	
	private static void getSSToyotaRZ(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new SSToyotaRZ(mPacket,3);
		} else {
			mAnalyzeUtils.analyze(mPacket,3);//����λ����Ϣtypeλ
		}
	}
	
	private static void getSSHonda(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new SSHonda(mPacket,3);
		} else {
			mAnalyzeUtils.analyze(mPacket,3);//����λ����Ϣtypeλ
		}
	}
	
	private static void getSSToyota(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new SSToyota(mPacket,3);
		} else {
			mAnalyzeUtils.analyze(mPacket,3);//����λ����Ϣtypeλ
		}
	}
	
	private static void getSSGE(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new SSGE(mPacket,3);
		} else {
			mAnalyzeUtils.analyze(mPacket,3);//����λ����Ϣtypeλ
		}
	}
	
	
	private static void getSSDFFG(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new SSDFFG(mPacket,3);
		} else {
			mAnalyzeUtils.analyze(mPacket,3);//����λ����Ϣtypeλ
		}
	}
	
	private static void getRZCToyota(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new RZCToyota(mPacket,1);
		} else {
			mAnalyzeUtils.analyze(mPacket,1);//�ڶ�λ����Ϣtypeλ
		}
	}
	
	private static void getRZCHonda(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new RZCHonda(mPacket,1);
		} else {
			mAnalyzeUtils.analyze(mPacket,1);//�ڶ�λ����Ϣtypeλ
		}
	}
	
	private static void getRZCFOCUS(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new RZCFOCUS(mPacket,1);
		} else {
			mAnalyzeUtils.analyze(mPacket,1);//�ڶ�λ����Ϣtypeλ
		}
	}
	
	private static void getRZCHondaCRV(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new RZCHondaCRV(mPacket,1);
		} else {
			mAnalyzeUtils.analyze(mPacket,1);//�ڶ�λ����Ϣtypeλ
		}
	}
	
	
	private static void getRZCHondaDA(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new RZCHondaDA(mPacket,1);
		} else {
			mAnalyzeUtils.analyze(mPacket,1);//�ڶ�λ����Ϣtypeλ
		}
	}
	
	private static void getRZCTrumpche(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new RZCTrumpche(mPacket,1);
		} else {
			mAnalyzeUtils.analyze(mPacket,1);//�ڶ�λ����Ϣtypeλ
		}
	}
	
	private static void getRZCNISSAN(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new RZCNISSAN(mPacket,1);
		} else {
			mAnalyzeUtils.analyze(mPacket,1);//�ڶ�λ����Ϣtypeλ
		}
	}

	private static void getRZCVolkswagen(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new RZCVolkswagen(mPacket,1);
		} else {
			mAnalyzeUtils.analyze(mPacket,1);//�ڶ�λ����Ϣtypeλ
		}
	}

	private static void getRZCVolkswagenGolf(byte[] mPacket) {
		if (mAnalyzeUtils == null) {
			mAnalyzeUtils = new RZCVolkswagenGolf(mPacket,1);
		} else {
			mAnalyzeUtils.analyze(mPacket,1);//�ڶ�λ����Ϣtypeλ
		}
	}
}
