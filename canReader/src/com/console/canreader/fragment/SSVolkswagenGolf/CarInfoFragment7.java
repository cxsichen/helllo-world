package com.console.canreader.fragment.SSVolkswagenGolf;

import java.util.ArrayList;
import java.util.List;

import com.console.canreader.R;
import com.console.canreader.activity.BaseFragment;
import com.console.canreader.activity.VolkswagenGolf.GolfView1;
import com.console.canreader.activity.VolkswagenGolf.GolfView4;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.DensityUtils;
import com.console.canreader.utils.ViewPagerAdapter;
import com.console.canreader.view.ObdView;
import com.console.canreader.view.ViewPageFactory;

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
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

public class CarInfoFragment7 extends BaseFragment {

	private LayoutInflater inflater;
	TextView tv;
	int[] intTemp = new int[3];

	public CarInfoFragment7() {

	}

	String[] str = { "Air conditioning 空调", "Auxiliary heater 辅助加热器",
			"Rear window heating 后窗加热 ", "Left seat heating 左边座椅加热 ",
			"Right seat heating 右边座椅加热 ", "Left seat ventilation 左座椅通风",
			"Right head area heating 右前区域加热 ",
			"Left head area heating 左前区域加热 ",
			"Right head area heating 右前区域加热 ", "Front fog light 前雾灯",
			"Rear fog light 后雾灯", "Windscreen heating 前窗加热 ",
			"Steering wheel heating 方向盘加热 ", "Mirror heating 后视镜加热",
			"Rear seat heating 后座椅加热 ", "Rear seat ventilation 后座椅通风 ",
			"Socket 插座 ", "Drink holder 饮料架 ", };

	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		super.show(mCaninfo);
		if (mCaninfo != null) {
			if (mCaninfo.CHANGE_STATUS == 10) {
				String temp = "";
				intTemp[0] = mCaninfo.CONV_WARNING_MES_0;
				intTemp[1] = mCaninfo.CONV_WARNING_MES_1;
				intTemp[2] = mCaninfo.CONV_WARNING_MES_2;
				for (int i = 0; i < mCaninfo.CONV_WARNING_MES_NUM; i++) {
					if (intTemp[i] >= str.length) {
						break;
					}

					temp = temp + str[intTemp[i] - 1] + "\n";
					Log.i("cxs", "====temp=======" + temp);
				}
				tv.setText(temp);
			}
		}
	}

	@Override
	public void serviceConnected() {
		// TODO Auto-generated method stub
		super.serviceConnected();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		inflater = LayoutInflater.from(getActivity());
		View view = inflater.inflate(R.layout.rzc_golf_layout_2, null);
		tv = (TextView) view.findViewById(R.id.golf_tv_2);
		return view;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();

	}

}
