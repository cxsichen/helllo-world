package com.example.cjc7150;

import android.util.Log;

public class MainActivity {
	
	public static int setmode(byte value){
		Log.i("cxs","=======com.example.cjc7150==MainActivity======="+Setmode(value));
		return Setmode(value);
	}

	public native static int Setmode(byte value); // …Ë÷√0ªÚ’ﬂ1

	static {
		System.loadLibrary("cjc7150");
	}
}
