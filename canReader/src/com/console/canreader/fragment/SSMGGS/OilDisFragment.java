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

public class OilDisFragment extends BaseFragment {

	TextView tv1;
	TextView tv2;
	TextView tv3;
	TextView tv4;
	DecimalFormat fnum = new DecimalFormat("##0.00");
	
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

	public OilDisFragment() {

	}

    String[] strGroup={"mpg","km/L","L/100km"};

	@Override
	public void show(CanInfo caninfo) {
		// TODO Auto-generated method stub
		super.show(caninfo);
		if (caninfo != null) {
			if (caninfo.CHANGE_STATUS == 10) {
				tv1.setText(fnum.format(caninfo.DISTANCE_LONG_TERM)+(caninfo.RANGE_UNIT==1?"mile":"km"));
				tv2.setText(fnum.format(caninfo.TRIP_OIL_CONSUMPTION_0)+strGroup[caninfo.AVERAGE_CONSUMPTION_UNIT]);
				tv3.setText(fnum.format(caninfo.TRIP_OIL_CONSUMPTION_1)+strGroup[caninfo.AVERAGE_CONSUMPTION_UNIT]);
				tv4.setText(fnum.format(caninfo.TRIP_OIL_CONSUMPTION_2)+strGroup[caninfo.AVERAGE_CONSUMPTION_UNIT]);
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
		View view = inflater.inflate(R.layout.ssmggs_oil_dis, container,
				false);
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
		tv1=(TextView) pageView.findViewById(R.id.ssmggs_tv5);
		tv2=(TextView) pageView.findViewById(R.id.ssmggs_tv6);
		tv3=(TextView) pageView.findViewById(R.id.ssmggs_tv7);
		tv4=(TextView) pageView.findViewById(R.id.ssmggs_tv8);

	}

}
