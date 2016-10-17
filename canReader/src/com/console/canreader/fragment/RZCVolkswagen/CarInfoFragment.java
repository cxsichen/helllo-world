package com.console.canreader.fragment.RZCVolkswagen;

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

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Contacts.MSG_GET_MSG:
				// 大众主动获取数据
				sendMsg(Contacts.CONNECTMSG);
				sendMsg(Contacts.HEX_GET_CAR_INFO);
				mHandler.sendEmptyMessageDelayed(Contacts.MSG_GET_MSG, 5000);
				break;
			default:
				break;
			}
		}
	};

	public CarInfoFragment() {

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.main, container, false);
		initView(view);
		return view;
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		mHandler.removeMessages(Contacts.MSG_GET_MSG);
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		if (viewsFactory == null)
			viewsFactory = new ArrayList<ViewPageFactory>();
		if (vp == null)
			vp = (ViewPager) view.findViewById(R.id.vp);
		vp.setOffscreenPageLimit(2);
		if (vpAdapter == null)
			vpAdapter = new ViewPagerAdapter(viewsFactory);
		ViewPageFactory pageViewDefalut = new ObdView(getActivity(),
				R.layout.dashboard_main);
		viewsFactory.add(pageViewDefalut);
		vp.setAdapter(vpAdapter);
		initIndicator(view);
	}

	/**
	 * init Indicator
	 */
	protected void initIndicator(View view) {
		if (indicatorLayout == null)
			indicatorLayout = (LinearLayout) view.findViewById(
					R.id.indicator);

		indicatorLayout.removeAllViews();

		if (viewsFactory.size() < 2) {
			return;
		}

		for (int i = 0; i < viewsFactory.size(); i++) {
			ImageView imageView = new ImageView(getActivity());
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			lp.setMargins(DensityUtils.dip2px(getActivity(), 4), 0,
					DensityUtils.dip2px(getActivity(), 4), 0);
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
