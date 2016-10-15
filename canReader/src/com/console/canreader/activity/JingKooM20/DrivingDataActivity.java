package com.console.canreader.activity.JingKooM20;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

import com.console.canreader.R;
import com.console.canreader.activity.BaseActivity;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.DensityUtils;
import com.console.canreader.utils.PreferenceUtil;
import com.console.canreader.utils.ViewPagerAdapter;
import com.console.canreader.view.ObdView;
import com.console.canreader.view.ViewPageFactory;

public class DrivingDataActivity extends BaseActivity {
		
	private TextView version;
	
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Contacts.MSG_GET_MSG:
				// 主动获取数据
	            sendMsg("2ef10101");
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
		setContentView(R.layout.only_version_layout);
		initView();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mHandler.sendEmptyMessageDelayed(Contacts.MSG_GET_MSG, 2000);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		super.show(mCaninfo);
		if(mCaninfo!=null){
			if(!mCaninfo.VEHICLE_NO.equals(version.getText())){
				version.setText(mCaninfo.VEHICLE_NO);
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
