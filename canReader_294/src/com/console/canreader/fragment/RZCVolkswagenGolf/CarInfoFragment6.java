package com.console.canreader.fragment.RZCVolkswagenGolf;

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

public class CarInfoFragment6 extends BaseFragment {

	private LayoutInflater inflater;
	TextView tv;
	int[] intTemp = new int[6];

	public CarInfoFragment6() {

	}

	String[] str = {
			"Trailer mode �ϳ�ģʽ",
			"Windscreen could become fogged С�ĵ��粣������ ",
			"Operating temperature not maintained ����ά�ֹ����¶� ",
			" No driver detected δ���ּ�ʻԱ��driver ����������˼�����ʣ�δ ��⵽�������� ",
			"Gearbox requires engine to be running ����������",
			"Power consumption is high ����",
			"Starting start-stop system ������-ͣϵͳ ",
			"Engine must be running �����������",
			"Gradient too steep �¶�̫�� ",
			"Start-stop system is currently not available ��-ͣϵͳ��ǰ������",
			"Emergency brake function intervention �����ƶ����ܸ�Ԥ",
			"Brake requires engine to be running �ƶ�����Ҫ���������� ",
			"Power consumption is high ���ĸ�",
			"Air conditioning requires engine to be running �յ�������Ҫ���������� ",
			"Steering angle too great ת���̫��",
			"Manoeuvering mode ����ģʽ",
			"Defrost function is switched on ��˪���ܿ��� ",
			"Driver seatbelt not fastened ��ʻԱδϵ��ȫ�� ",
			"Driver door open ��ʻ���Ŵ� ",
			"Accelerator is depressed ����̤�屻���� ",
			"Windscreen heating is switched on ǰ�����ȿ���",
			"Maximum air conditioning  requires engine to be running �յ����������Ҫ���������� ",
			"Bonnet has been opened �����δ�غ� ",
			"Engine stop currently not possible ��������ǰ�޷�ֹͣ ",
			"Off-road mode is activated ԽҰģʽ������ ",
			"System protection intervention Button/lever activated too often ��ɲ��ť/��ʹ��̫Ƶ�� ",
			"Gear is engaged �������� ", "Start-stop button pressed ��-ͣ��ť�Ѱ��� ",
			"Air conditioning requires engine to be running �յ�������Ҫ�� �������� ",
			"ACC intervention ACC ״̬ ", "Park Assist is activated פ������������ ",
			"ESC is switched off ESC �ر� ", "Power consumption is high ���ĸ�",
			"Start-stop system requires engine to be running ��-ͣϵͳ��Ҫ���������� " };

	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		super.show(mCaninfo);
		if (mCaninfo != null) {
			if (mCaninfo.CHANGE_STATUS == 10) {
				if (mCaninfo.WARNING_MES_NUM == 0) {
					tv.setText(mCaninfo.START_STOP_MES == 1 ? "Start-stopϵͳ�Ѽ���"
							: "Start-stopϵͳδ����");
				} else {
					String temp = "";
					intTemp[0] = mCaninfo.WARNING_MES_0;
					intTemp[1] = mCaninfo.WARNING_MES_1;
					intTemp[2] = mCaninfo.WARNING_MES_2;
					intTemp[3] = mCaninfo.WARNING_MES_3;
					intTemp[4] = mCaninfo.WARNING_MES_4;
					intTemp[5] = mCaninfo.WARNING_MES_5;
					for (int i = 0; i < mCaninfo.WARNING_MES_NUM; i++) {
						if (intTemp[i] >= str.length) {
							break;
						}
						temp = temp + str[intTemp[i]] + "\n";
					}
					tv.setText(temp);
				}
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
		View view = inflater.inflate(R.layout.rzc_golf_layout_1, null);
		tv = (TextView) view.findViewById(R.id.golf_tv_1);
		return view;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();

	}

}
