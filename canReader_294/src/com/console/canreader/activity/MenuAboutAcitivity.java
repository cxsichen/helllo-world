package com.console.canreader.activity;

import com.console.canreader.R;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class MenuAboutAcitivity extends BaseActivity {
	private TextView version;

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Contacts.MSG_GET_MSG:
				// 主动获取数据
				// sendMsg("2ef10101");
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.only_version_layout);
		initView();
	}

	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		super.show(mCaninfo);
		if (mCaninfo != null) {
			if (!mCaninfo.VERSION.equals(version.getText())) {
				version.setText(mCaninfo.VERSION);
			}
		}

	}

	@Override
	public void serviceConnected() {
		// TODO Auto-generated method stub
		super.serviceConnected();
		mHandler.sendEmptyMessageDelayed(Contacts.MSG_GET_MSG, 1000);
	}

	private void initView() {
		version = (TextView) findViewById(R.id.Jingkoo_version);
	}

}
