package com.console.canreader.activity;

import com.console.canreader.R;
import com.console.canreader.service.CanInfo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MenuCdAcitivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		tv.setText("MenuCdAcitivity");
		setContentView(tv);
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
