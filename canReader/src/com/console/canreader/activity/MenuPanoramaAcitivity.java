package com.console.canreader.activity;

import com.console.canreader.R;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.PreferenceUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MenuPanoramaAcitivity extends BaseActivity {

	public static String canName = "";
	public static String canFirtName = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		super.show(mCaninfo);
	}
    long lastTime=0;
	@Override
	public void serviceConnected() {
		// TODO Auto-generated method stub
		super.serviceConnected();
		syncCanName();
		switch (canName) {
		case Contacts.CANNAMEGROUP.SSNissan:
			if(System.currentTimeMillis()-lastTime>1000){
				lastTime=System.currentTimeMillis();
			    sendMsg("AA5502FD0100");
			}
			break;
		case Contacts.CANNAMEGROUP.SSTrumpchiGS5:
			if(System.currentTimeMillis()-lastTime>1000){
				lastTime=System.currentTimeMillis();
			    sendMsg("AA5502FD0100");
			}
			break;
		default:
			break;
		}
		finish();
	}
	private void syncCanName() {
		canName = PreferenceUtil.getCANName(this);
		canFirtName = PreferenceUtil.getFirstTwoString(this, canName);
	}
}
