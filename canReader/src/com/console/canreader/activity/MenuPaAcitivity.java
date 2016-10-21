package com.console.canreader.activity;

import com.console.canreader.R;
import com.console.canreader.activity.BaseActivity.CanNameObserver;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.PreferenceUtil;
import com.console.canreader.view.CarSelectedView;
import com.console.canreader.view.CarSelectedView.OnPositionChangedListener;
import com.console.canreader.view.VerticalSeekBar;
import com.console.canreader.view.VerticalSeekBar.OnProgressChangedListener;

import android.app.Activity;
import android.content.Intent;
import android.database.ContentObserver;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuPaAcitivity extends Activity {
	public static String canName = "";
	public static String canFirtName = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 监控车型和协议选择
		syncCanName();
		getContentResolver()
				.registerContentObserver(
						android.provider.Settings.System
								.getUriFor(Contacts.CAN_CLASS_NAME),
						true, mCanNameObserver);
		choosePaActivity();
		finish();
	}
		
	private void startPaAcitivy(String str){
		try {
			Intent intent=new Intent();
			intent.setClassName("com.console.canreader", str);
			startActivity(intent);
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("cxs","====e====="+e);
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
			if (!canName.equals(PreferenceUtil.getCANName(MenuPaAcitivity.this)))
				finish();

		}
	}

	private void syncCanName() {
		canName = PreferenceUtil.getCANName(this);
		canFirtName = PreferenceUtil.getFirstTwoString(this, canName);
	}
	
	private void choosePaActivity() {
		// TODO Auto-generated method stub
		switch (canName) {
		case Contacts.CANNAMEGROUP.SSToyotaBD: 
			startPaAcitivy("com.console.canreader.fragment.SSToyota.MenuPaAcitivity");
			break;
		case Contacts.CANNAMEGROUP.SSHyundai: 
		case Contacts.CANNAMEGROUP.SSHyundai16MT:
			startPaAcitivy("com.console.canreader.fragment.SSHyundai.MenuPaAcitivity");
			break;
		default:
			break;
		}
	}

}
