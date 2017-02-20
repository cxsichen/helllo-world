package com.console.canreader.activity;

import com.console.canreader.R;
import com.console.canreader.activity.MenuTpmsAcitivity.CanNameObserver;
import com.console.canreader.fragment.SSPeugeot.AirControlerSSPeugeot408;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.PreferenceUtil;

import android.content.Intent;
import android.database.ContentObserver;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class MenuAcAcitivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		syncCanName();
		getContentResolver()
				.registerContentObserver(
						android.provider.Settings.System
								.getUriFor(Contacts.CAN_CLASS_NAME),
						true, mCanNameObserver);
		chooseAcActivity();
		finish();
	}

	private void startAcAcitivy(String str) {
		try {
			Intent intent = new Intent();
			intent.setClassName("com.console.canreader", str);
			startActivity(intent);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getContentResolver().unregisterContentObserver(mCanNameObserver);
	}

	private CanNameObserver mCanNameObserver = new CanNameObserver();

	public class CanNameObserver extends ContentObserver {
		public CanNameObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			if (!canName
					.equals(PreferenceUtil.getCANName(MenuAcAcitivity.this)))
				finish();

		}
	}

	private void syncCanName() {
		canName = PreferenceUtil.getCANName(this);
		canFirtName = PreferenceUtil.getFirstTwoString(this, canName);
	}

	private void chooseAcActivity() {
		// TODO Auto-generated method stub
		if (canName.contains(Contacts.CANNAMEGROUP.SSPeugeot)) {
			startActivity(new Intent(this, AirControlerSSPeugeot408.class));
			finish();
		}
		switch (canName) {
		case Contacts.CANNAMEGROUP.SSJeepZNZ17:
			startAcAcitivy("com.console.canreader.fragment.SSJeepZNZ17.AirContorlActivity");
			break;
		case Contacts.CANNAMEGROUP.SSJeepFreedomG:
		case Contacts.CANNAMEGROUP.SSJeepFreedomGH:
		case Contacts.CANNAMEGROUP.SSJeepFreedomGL:
		case Contacts.CANNAMEGROUP.SSJeepFreedomGM:
			startAcAcitivy("com.console.canreader.fragment.SSJeepFreedom.AirContorlActivity");
			break;
		case Contacts.CANNAMEGROUP.SSVolkswagenGolf:
			startAcAcitivy("com.console.canreader.fragment.SSVolkswagenGolf.AirContorlActivity");
			break;
		case Contacts.CANNAMEGROUP.SSCheryR7:
			startAcAcitivy("com.console.canreader.fragment.SSChery.AirContorlActivity");
			break;
		case Contacts.CANNAMEGROUP.RZCMGGS:
			startAcAcitivy("com.console.canreader.fragment.RZCMGGS.AirContorlActivity");
			break;
		case Contacts.CANNAMEGROUP.SSMGGS:
			startAcAcitivy("com.console.canreader.fragment.SSMGGS.AirContorlActivity");
			break;
		case Contacts.CANNAMEGROUP.SSGE:
			startAcAcitivy("com.console.canreader.fragment.SSGE.AirContorlActivity");
			break;
		case Contacts.CANNAMEGROUP.RZCVolkswagenGolf:
			startAcAcitivy("com.console.canreader.fragment.RZCVolkswagenGolf.AirContorlActivity");
			break;
		default:
			startAcAcitivy("com.console.canreader.fragment."+canName+".AirContorlActivity");
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
