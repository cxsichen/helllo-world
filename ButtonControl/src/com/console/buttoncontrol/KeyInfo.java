package com.console.buttoncontrol;

import android.content.Context;
import android.util.Log;

import com.console.buttoncontrol.utils.Contacts;

public class KeyInfo {

	final static byte[][] keyCode = { { (byte) 0x16, (byte) 0xB3 },
			{ (byte) 0x15, (byte) 0xB0 }, { (byte) 0x11, (byte) 0xB4 },
			{ (byte) 0x18, (byte) 0xBD }, { (byte) 0x19, (byte) 0xBC },
			{ (byte) 0x81, (byte) 0x24 }, { (byte) 0x91, (byte) 0x34 }};
	
	static int[] keyGroup;
	Context context;
	public KeyInfo(Context context) {
		// TODO Auto-generated constructor stub	
		this.context=context;
		if(keyGroup==null){
			keyGroup=new int[keyCode.length];
		}
		syncKeyCode(context);
	}
	
	public static void syncKeyCode(Context context) {
		// TODO Auto-generated method stub
		keyGroup[0] = android.provider.Settings.System.getInt(
				context.getContentResolver(), Contacts.DZ_KEYCODE0, 0);
		keyGroup[1] = android.provider.Settings.System.getInt(
				context.getContentResolver(), Contacts.DZ_KEYCODE1, 0);
		keyGroup[2] = android.provider.Settings.System.getInt(
				context.getContentResolver(), Contacts.DZ_KEYCODE2, 0);
		keyGroup[3] = android.provider.Settings.System.getInt(
				context.getContentResolver(), Contacts.DZ_KEYCODE3, 0);
		keyGroup[4] = android.provider.Settings.System.getInt(
				context.getContentResolver(), Contacts.DZ_KEYCODE4, 0);
		keyGroup[5] = android.provider.Settings.System.getInt(
				context.getContentResolver(), Contacts.DZ_KEYCODE5, 0);
		keyGroup[6] = android.provider.Settings.System.getInt(
				context.getContentResolver(), Contacts.DZ_KEYCODE6, 0);
	}

	public static int[]  getKeyGroup(){
		if(keyGroup==null){
			keyGroup=new int[7];
		}
		return keyGroup;
	}
	
	public static int getKeyCommand(int i){
		Log.i("cxs1","=====i====="+i);
		Log.i("cxs1","=====keyGroup[i]===="+keyGroup[i]);
		if(i>=keyGroup.length){
			return -1;
		}
		return keyGroup[i];
	}

}
