package com.console.canreader.activity;

import com.console.canreader.R;
import com.console.canreader.fragment.SSPeugeot.AirControlerSSPeugeot408;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.PreferenceUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MenuCdAcitivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		syncCanName();
		chooseAcActivity();
		finish();
	}
	
	private void syncCanName() {
		canName = PreferenceUtil.getCANName(this);
		canFirtName = PreferenceUtil.getFirstTwoString(this, canName);
	}
	private void startAcAcitivy(String str) {
		try {
			Intent intent = new Intent();
			intent.setClassName("com.console.canreader", str);
			startActivity(intent);
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("cxs", "====e=====" + e);
		}
	}

	private void chooseAcActivity() {
		// TODO Auto-generated method stub

		switch (canName) {
		case Contacts.CANNAMEGROUP.SSJeepFreedomG:
			startAcAcitivy("com.console.canreader.fragment.SSJeepFreedom.CdContorlActivity");
			break;

		default:
			break;
		}
	}
	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		super.show(mCaninfo);
	}

	@Override
	public void serviceConnected() {
		// TODO Auto-generated method stub
		super.serviceConnected();
	}

}
