package com.console.canreader.fragment.RZCVolkswagenGolf;

import java.util.ArrayList;
import java.util.List;

import com.console.canreader.ConsoleApplication;
import com.console.canreader.R;
import com.console.canreader.activity.BaseFragment;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.DensityUtils;
import com.console.canreader.utils.ViewPagerAdapter;
import com.console.canreader.view.ObdView;
import com.console.canreader.view.ObdView_294;
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
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

public class CarInfoFragment extends BaseFragment {
	public static final String TEST = "2e810101";

	private String canName = "";
	private String canFirtName = "";

	/*
	 * TextView fuel_warn; TextView battery_warn;
	 */
	private static final String WARNSTART = "warn_start";
	private static final int KEYCODE_HOME = 271;
	CanInfo mCaninfo;
	int cout = 0;

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Contacts.MSG_GET_MSG:
				// 大众主动获取数据
				sendMsg("2e90026300");
				sendMsg("2e90026310");
				sendMsg("2e90026311");
				sendMsg("2e90026320");
				sendMsg("2e90026321");
				sendMsg("2e90025010");
				sendMsg("2e90025020");
				sendMsg("2e90025021");
				sendMsg("2e90025022");
				sendMsg("2e90025030");
				sendMsg("2e90025031");
				sendMsg("2e90025032");
				sendMsg("2e90025040");
				sendMsg("2e90025041");
				sendMsg("2e90025042");
				sendMsg("2e90025050");
				sendMsg("2e90025051");
				sendMsg("2e90025052");
				mHandler.sendEmptyMessageDelayed(Contacts.MSG_GET_MSG, 40000);
				break;
			default:
				break;
			}
		}
	};

	private ViewPageFactory pageViewDefalut;

	public CarInfoFragment() {

	}

	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		super.show(mCaninfo);
		if (mCaninfo != null) {
			if (mCaninfo.CHANGE_STATUS == 10) {
				pageViewDefalut.showView(mCaninfo);
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
		if (ConsoleApplication.customId.equals("294"))
			pageViewDefalut = new ObdView_294(getActivity(),
					R.layout.dashboard_main_294);
			else
				pageViewDefalut = new ObdView(getActivity(),
						R.layout.dashboard_main);
		return pageViewDefalut.getView();
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		mHandler.removeMessages(Contacts.MSG_GET_MSG);
	}

}
