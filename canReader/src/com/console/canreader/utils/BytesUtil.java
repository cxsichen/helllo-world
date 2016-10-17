package com.console.canreader.utils;

import android.util.Log;

public class BytesUtil {

	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	public static String intToHexString(int a) {
		StringBuilder stringBuilder = new StringBuilder("");
		int v = a & 0xFF;
		String hv = Integer.toHexString(v);
		if (hv.length() < 2) {
			stringBuilder.append(0);
		}
		stringBuilder.append(hv);
		return stringBuilder.toString();
	}

	/**
	 * Convert hex string to byte[] 鎶婁负瀛楃涓茶浆鍖栦负瀛楄妭鏁扮粍
	 * 
	 * @param hexString
	 *            the hex string
	 * @return byte[]
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * Convert char to byte
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	public static byte stringToByte(String str) {
		return Integer.valueOf(str, 16).byteValue();
	}

	public static byte intToByte(int i) {
		return Integer.valueOf(Integer.toHexString(i), 16).byteValue();
	}
    //睿志诚数据添加数据位
	public static String addRZCCheckBit(String str) {
		byte[] b = hexStringToBytes(str);
		byte sum = 0;

		for (int i = 1; i < b.length; i++) {
			sum += b[i];
		}
		sum = (byte) ((sum)^0xff);
		// String tmp = Integer.toHexString(255 - sum).toUpperCase();
		String tmp = Integer.toHexString(sum);
		if (tmp.length() > 2) {
			tmp = tmp.substring(tmp.length()-2,tmp.length());
		}else if(tmp.length()==1){
			tmp="0"+tmp;
		}
		return str + tmp;
	}
	
	//尚摄数据添加数据位
		public static String addSSCheckBit(String str) {
			byte[] b = hexStringToBytes(str);
			int sum = 0;

			for (int i = 2; i < b.length; i++) {
				sum = sum + b[i];
			}
			sum = (byte) (((byte)sum&(byte)0xFF)-1);
			// String tmp = Integer.toHexString(255 - sum).toUpperCase();
			String tmp = Integer.toHexString(sum).toUpperCase();
			if (tmp.length() > 2) {
				tmp = tmp.substring(tmp.length()-2,tmp.length());
			}else if(tmp.length()==1){
				tmp="0"+tmp;
			}
			return str + tmp;
		}

	public static String makeEfMsg(int tre, int bas, int ver, int hon) {
		StringBuilder stringBuilder = new StringBuilder("F8");
		String hv = Integer.toHexString(tre & 0xFF);
		if (hv.length() < 2) {
			stringBuilder.append(0);
		}
		stringBuilder.append(hv);

		hv = Integer.toHexString(bas & 0xFF);
		if (hv.length() < 2) {
			stringBuilder.append(0);
		}
		stringBuilder.append(hv);

		hv = Integer.toHexString(ver & 0xFF);
		if (hv.length() < 2) {
			stringBuilder.append(0);
		}
		stringBuilder.append(hv);

		hv = Integer.toHexString(hon & 0xFF);
		if (hv.length() < 2) {
			stringBuilder.append(0);
		}
		stringBuilder.append(hv);

		return stringBuilder.toString();
	}
	
}
