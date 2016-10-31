package com.console.canreader.activity;

import com.console.canreader.R;
import com.console.canreader.activity.MenuPaAcitivity.CanNameObserver;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.PreferenceUtil;

import android.content.Intent;
import android.database.ContentObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MenuTpmsAcitivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// ��س��ͺ�Э��ѡ��
		syncCanName();
		getContentResolver()
				.registerContentObserver(
						android.provider.Settings.System
								.getUriFor(Contacts.CAN_CLASS_NAME),
						true, mCanNameObserver);
		chooseTpmsActivity();
		finish();
	}

	private void startTpmsAcitivy(String str) {
		try {
			Intent intent = new Intent();
			intent.setClassName("com.console.canreader", str);
			startActivity(intent);
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("cxs", "====e=====" + e);
		}
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
			if (!canName.equals(PreferenceUtil
					.getCANName(MenuTpmsAcitivity.this)))
				finish();

		}
	}

	private void syncCanName() {
		canName = PreferenceUtil.getCANName(this);
		canFirtName = PreferenceUtil.getFirstTwoString(this, canName);
	}

	private void chooseTpmsActivity() {
		// TODO Auto-generated method stub
		switch (canName) {
		case Contacts.CANNAMEGROUP.SSGE:
			startTpmsAcitivy("com.console.canreader.fragment.SSGE.TpmsActivity");
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
