package com.console.canreader.activity;

import java.util.ArrayList;
import java.util.List;

import com.console.canreader.R;
import com.console.canreader.activity.PeugeotCitroen.PeuAirConView;
import com.console.canreader.activity.VolkswagenGolf.GolfView1;
import com.console.canreader.activity.VolkswagenGolf.GolfView2;
import com.console.canreader.activity.VolkswagenGolf.GolfView3;
import com.console.canreader.activity.VolkswagenGolf.GolfView4;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.DensityUtils;
import com.console.canreader.utils.PreferenceUtil;
import com.console.canreader.utils.ViewPagerAdapter;
import com.console.canreader.view.ObdView;
import com.console.canreader.view.ViewPageFactory;

import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.FrameLayout.LayoutParams;

public class CarInfoWaringActivity extends baseActivity {
	public static final String TEST = "2e810101";

	private String canName = "";
	private String canFirtName = "";
	
	private ViewPager vp;
	private ViewPagerAdapter vpAdapter;
	private List<ViewPageFactory> viewsFactory;
	private LinearLayout indicatorLayout;
	/*
	 * TextView fuel_warn; TextView battery_warn;
	 */
	private static final String WARNSTART = "warn_start";
	private static final int KEYCODE_HOME = 271;
	CanInfo mCaninfo;
	int cout = 0;
	public static Boolean isResume = false;

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Contacts.MSG_GET_MSG:
				// 大众主动获取数据

				if (canName.equals(Contacts.CANNAMEGROUP.RZCVolkswagen)) {
					sendMsg(Contacts.HEX_GET_CAR_INFO);
					mHandler.sendEmptyMessageDelayed(Contacts.MSG_GET_MSG, 2000);
				}

				if (canName.equals(Contacts.CANNAMEGROUP.RZCVolkswagenGolf)) {
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
					mHandler.sendEmptyMessageDelayed(Contacts.MSG_GET_MSG,
							40000);
				}

				break;

			case Contacts.MSG_ONCE_GET_MSG:
				if (canFirtName.equals(Contacts.CANFISRTNAMEGROUP.RAISE)) {
					sendMsg(Contacts.CONNECTMSG);
				}
				if (canName.equals(Contacts.CANNAMEGROUP.RZCVolkswagen)) {
					sendMsg(Contacts.HEX_GET_CAR_INFO_1);
					sendMsg(Contacts.HEX_GET_CAR_INFO_3);
				}
				break;
			default:
				break;
			}
		}
	};

	private void checkStartMode(CanInfo mCanInfo) {
		// TODO Auto-generated method stub
		int mode = Settings.System.getInt(getContentResolver(), WARNSTART, 0);
		if (mode == 1) {
			if (vp != null)
				vp.setCurrentItem(0);
		}
		if (mode == 1 && mCanInfo.FUEL_WARING_SIGN != 1
				&& mCanInfo.BATTERY_WARING_SIGN != 1
				&& mCanInfo.SAFETY_BELT_STATUS != 1
				&& mCanInfo.DISINFECTON_STATUS != 1
				&& mCanInfo.HANDBRAKE_STATUS != 1) {
			moveTaskToBack(true);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initView();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Settings.System.putInt(getContentResolver(), WARNSTART, 0);
		super.onPause();
		mHandler.removeMessages(Contacts.MSG_GET_MSG);
		isResume = false;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		mHandler.sendEmptyMessageDelayed(Contacts.MSG_GET_MSG, 2000);
		mHandler.sendEmptyMessageDelayed(Contacts.MSG_ONCE_GET_MSG, 5000);
		isResume = true;

	}

	private void initView() {
		// TODO Auto-generated method stub
		if (viewsFactory == null)
			viewsFactory = new ArrayList<ViewPageFactory>();
		if (vp == null)
			vp = (ViewPager) findViewById(R.id.vp);
		vp.setOffscreenPageLimit(2);
		if (vpAdapter == null)
			vpAdapter = new ViewPagerAdapter(viewsFactory);
		ViewPageFactory pageViewDefalut = new ObdView(this,
				R.layout.dashboard_main);
		viewsFactory.add(pageViewDefalut);

		/*
		 * if (PreferenceUtil.getCARTYPE(this) != carType) { switch
		 * (PreferenceUtil.getCARTYPE(this)) { case
		 * Contacts.CARTYPEGROUP.PeugeotCitroen: carType =
		 * PreferenceUtil.getCARTYPE(this); vp.removeAllViews();
		 * viewsFactory.clear();
		 * 
		 * ViewPageFactory pageView1 = new ObdView(this,
		 * R.layout.dashboard_main); viewsFactory.add(pageView1);
		 * 
		 * ViewPageFactory peuAirConView = new PeuAirConView(this,
		 * R.layout.ac_controler_peugeot_408); viewsFactory.add(peuAirConView);
		 * 
		 * vpAdapter.notifyDataSetChanged(); break; case
		 * Contacts.CARTYPEGROUP.VolkswagenGolf: carType =
		 * PreferenceUtil.getCARTYPE(this);
		 * 
		 * vp.removeAllViews(); viewsFactory.clear();
		 * 
		 * ViewPageFactory pageView = new ObdView(this,
		 * R.layout.dashboard_main); viewsFactory.add(pageView);
		 * 
		 * ViewPageFactory GolfView1 = new GolfView1(this,
		 * R.layout.carinfo_layout_1); viewsFactory.add(GolfView1);
		 * 
		 * ViewPageFactory GolfView2 = new GolfView2(this,
		 * R.layout.carinfo_layout_2); viewsFactory.add(GolfView2);
		 * 
		 * ViewPageFactory GolfView3 = new GolfView3(this,
		 * R.layout.carinfo_layout_3); viewsFactory.add(GolfView3);
		 * 
		 * ViewPageFactory GolfView4 = new GolfView4(this,
		 * R.layout.carinfo_layout_4); viewsFactory.add(GolfView4);
		 * 
		 * vpAdapter.notifyDataSetChanged(); break;
		 * 
		 * default: vp.removeAllViews(); viewsFactory.clear();
		 * 
		 * ViewPageFactory pageViewDefalut = new ObdView(this,
		 * R.layout.dashboard_main); viewsFactory.add(pageViewDefalut);
		 * vpAdapter.notifyDataSetChanged(); break; }
		 * 
		 * }
		 */
		vp.setAdapter(vpAdapter);
		initIndicator();
		syncCanName();
	}
	
	private void syncCanName(){
		canName = PreferenceUtil.getCANName(this);
		canFirtName=PreferenceUtil.getFirstTwoString(this, canName);
	}


	/**
	 * init Indicator
	 */
	protected void initIndicator() {
		if (indicatorLayout == null)
			indicatorLayout = (LinearLayout) findViewById(R.id.indicator);

		indicatorLayout.removeAllViews();

		if (viewsFactory.size() < 2) {
			return;
		}

		for (int i = 0; i < viewsFactory.size(); i++) {
			ImageView imageView = new ImageView(this);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			lp.setMargins(DensityUtils.dip2px(this, 4), 0,
					DensityUtils.dip2px(this, 4), 0);
			imageView.setLayoutParams(lp);
			if (i == 0) {
				imageView.setImageResource(R.drawable.white_oval);
			} else {
				imageView.setImageResource(R.drawable.gray_oval);
			}
			indicatorLayout.addView(imageView);
		}
		vp.setOnPageChangeListener(mOnPageChangeListener);
	}

	OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			for (int i = 0; i < viewsFactory.size(); i++) {
				((ImageView) indicatorLayout.getChildAt(i))
						.setImageResource(R.drawable.gray_oval);
			}
			((ImageView) indicatorLayout.getChildAt(arg0))
					.setImageResource(R.drawable.white_oval);

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			indicatorLayout.setVisibility(View.VISIBLE);
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			// indicatorLayout.setVisibility(View.INVISIBLE);
		}
	};

	/**
	 * 数据变化 需要改变界面的时候调用
	 */
	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		super.show(mCaninfo);
		if (mCaninfo != null) {
			checkStartMode(mCaninfo);
			if (mCaninfo.CHANGE_STATUS == 10) {
				for (ViewPageFactory mViewPageFactory : viewsFactory) {
					mViewPageFactory.showView(mCaninfo);
				}
			}
		}
	}

	/**
	 * 服务链接时调用
	 */

	@Override
	public void serviceConnected() {
		// TODO Auto-generated method stub
		super.serviceConnected();
		mHandler.sendEmptyMessageDelayed(Contacts.MSG_GET_MSG, 1000);
		mHandler.sendEmptyMessageDelayed(Contacts.MSG_ONCE_GET_MSG, 5000);
	}

	/**
	 * 服务断开链接时调用
	 */
	@Override
	public void serviceDisconnected() {
		// TODO Auto-generated method stub
		super.serviceDisconnected();
	}

}
