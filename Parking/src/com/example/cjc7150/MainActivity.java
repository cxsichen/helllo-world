package com.example.cjc7150;

import android.util.Log;

public class MainActivity {
	
	public static int setmode(byte value){
		return Setmode(value);
	}
	
	public static int getmode(){
		return Getmode();
	}

	public native static int Setmode(byte value); // ����0����1
	public native static int Getmode(); // ��ȡ�ײ�ͨ��
	static {
		System.loadLibrary("cjc7150");
	}
}