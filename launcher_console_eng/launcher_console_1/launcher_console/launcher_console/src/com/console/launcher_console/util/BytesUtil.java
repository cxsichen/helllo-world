package com.console.launcher_console.util;

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
	 * Convert hex string to byte[] 把为字符串转化为字节数组
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

	public static String addCheckBit(String str) {
		byte[] b = hexStringToBytes(str);
		int sum = 0;
		for (int i = 0; i < 5; i++) {
			sum = sum + b[i];
		}
		String tmp = Integer.toHexString(255 - sum).toUpperCase();
		if (tmp.length() > 2) {
			tmp = tmp.substring(1);
		}
		return str + tmp;
	}

	public static String makeEfMsg(int bas, int mid,int tre, int ver, int hon) {
		StringBuilder stringBuilder = new StringBuilder("F8");
		String hv = Integer.toHexString(bas & 0xFF);
		if (hv.length() < 2) {
			stringBuilder.append(0);
		}
		stringBuilder.append(hv);
       

		hv = Integer.toHexString(mid & 0xFF)+Integer.toHexString(tre & 0xFF);	
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
