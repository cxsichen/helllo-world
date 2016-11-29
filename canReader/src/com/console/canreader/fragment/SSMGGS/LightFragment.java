package com.console.canreader.fragment.SSMGGS;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.console.canreader.R;
import com.console.canreader.activity.BaseFragment;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.DensityUtils;
import com.console.canreader.utils.ViewPagerAdapter;
import com.console.canreader.view.ObdView;
import com.console.canreader.view.ViewPageFactory;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.View.OnCreateContextMenuListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

public class LightFragment extends BaseFragment {
	
	TextView tv0;
	TextView tv1;
	TextView tv2;
	TextView tv3;
	TextView tv4;
	TextView tv5;
	TextView tv6;
	TextView tv7;
	TextView tv8;
	TextView tv9;
	TextView tv10;

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Contacts.MSG_GET_MSG:

				break;
			default:
				break;
			}
		}
	};

	public LightFragment() {

	}

	@Override
	public void show(CanInfo caninfo) {
		// TODO Auto-generated method stub
		super.show(caninfo);
		if (caninfo != null) {
			if (caninfo.CHANGE_STATUS == 10) {
				tv0.setText(caninfo.LOW_BEAM==1?"开":"关");
				tv1.setText(caninfo.HIGH_BEAM==1?"开":"关");
				tv2.setText(caninfo.CLEARANCE_LAMP==1?"开":"关");
				tv3.setText(caninfo.FRONT_FOG_LAMP==1?"开":"关");
				tv4.setText(caninfo.REAR_FOG_LAMP==1?"开":"关");
				tv5.setText(caninfo.STOP_LAMP==1?"开":"关");
				tv6.setText(caninfo.PARKING_LAMP==1?"开":"关");
				tv7.setText(caninfo.DAYTIME_RUNNING_LAMP==1?"开":"关");
				tv8.setText(caninfo.RIGHT_TURNING_SIGNAL_LAMP==1?"开":"关");
				tv9.setText(caninfo.LEFT_TURNING_SIGNAL_LAMP==1?"开":"关");
				tv10.setText(caninfo.DOUBLE_FLASH_LAMP==1?"开":"关");
			}
		}
	}

	@Override
	public void serviceConnected() {
		// TODO Auto-generated method stub
		super.serviceConnected();
		mHandler.sendEmptyMessageDelayed(Contacts.MSG_GET_MSG, 1000);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.ssmggs_light, container, false);
		initView(view);
		return view;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	private void initView(View pageView) {
		// TODO Auto-generated method stub
		tv0 = (TextView) pageView.findViewById(R.id.ssmggs_tv10);
		tv1 = (TextView) pageView.findViewById(R.id.ssmggs_tv11);
		tv2 = (TextView) pageView.findViewById(R.id.ssmggs_tv12);
		tv3 = (TextView) pageView.findViewById(R.id.ssmggs_tv13_0);
		tv4 = (TextView) pageView.findViewById(R.id.ssmggs_tv13);
		tv5 = (TextView) pageView.findViewById(R.id.ssmggs_tv14);
		tv6 = (TextView) pageView.findViewById(R.id.ssmggs_tv15);
		tv7 = (TextView) pageView.findViewById(R.id.ssmggs_tv16);
		tv8 = (TextView) pageView.findViewById(R.id.ssmggs_tv17);
		tv9 = (TextView) pageView.findViewById(R.id.ssmggs_tv18);
		tv10 = (TextView) pageView.findViewById(R.id.ssmggs_tv19);
	}

}
