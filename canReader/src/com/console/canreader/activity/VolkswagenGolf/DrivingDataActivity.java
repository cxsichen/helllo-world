package com.console.canreader.activity.VolkswagenGolf;

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

	private ViewPager vp;
	private ViewPagerAdapter vpAdapter;
	private List<ViewPageFactory> viewsFactory;
	private LinearLayout indicatorLayout;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
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
		mHandler.removeMessages(Contacts.MSG_GET_MSG);
	}

	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		super.show(mCaninfo);
		if (mCaninfo != null) {
			if (mCaninfo.CHANGE_STATUS == 10) {
				for (ViewPageFactory mViewPageFactory : viewsFactory) {
					mViewPageFactory.showView(mCaninfo);
				}
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
		// TODO Auto-generated method stub
		if (viewsFactory == null)
			viewsFactory = new ArrayList<ViewPageFactory>();
		if (vp == null)
			vp = (ViewPager) findViewById(R.id.vp);
		vp.setOffscreenPageLimit(2);
		if (vpAdapter == null)
			vpAdapter = new ViewPagerAdapter(viewsFactory);
		ViewPageFactory GolfView1 = new GolfView1(this,
				R.layout.carinfo_layout_1);
		viewsFactory.add(GolfView1);

		ViewPageFactory GolfView2 = new GolfView2(this,
				R.layout.carinfo_layout_2);
		viewsFactory.add(GolfView2);

		ViewPageFactory GolfView3 = new GolfView3(this,
				R.layout.carinfo_layout_3);
		viewsFactory.add(GolfView3);

		ViewPageFactory GolfView4 = new GolfView4(this,
				R.layout.carinfo_layout_4);
		viewsFactory.add(GolfView4);
		vp.setAdapter(vpAdapter);
		initIndicator();
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

}
