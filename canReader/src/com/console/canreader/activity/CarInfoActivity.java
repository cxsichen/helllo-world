package com.console.canreader.activity;

import java.util.ArrayList;

import com.astuetz.viewpager.extensions.ScrollingTabsView;
import com.astuetz.viewpager.extensions.TabsAdapter;
import com.console.canreader.R;
import com.console.canreader.bean.AnalyzeUtils;
import com.console.canreader.service.CanInfo;
import com.example.scrollingtabsdemo.MyFragment;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CarInfoActivity extends BaseActivity {

	private ViewPager viewPager;
	private ScrollingTabsAdapter scrollingTabsAdapter;
	private ScrollingTabsView scrollingTabs;
	private FragsAdapter pagerAdapter;
	public static String[] mTitles;
	public static String[] mFragmentTitles;
	private ArrayList<BaseFragment> fragments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_activity_main);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		initData();
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		switch (fragments.size()) {
		case 0:
			return;
		case 1:
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.fragment_layout, fragments.get(0)).commit();
			break;
		default:
			viewPager = (ViewPager) findViewById(R.id.pager);
			viewPager.setVisibility(View.VISIBLE);
			// …Ë÷√ª∫¥Êfragmentµƒ ˝¡ø
			viewPager.setOffscreenPageLimit(6);
			viewPager.setCurrentItem(0);
			viewPager.setPageMargin(4);

			// …Ë÷√  ≈‰∆˜
			pagerAdapter = new FragsAdapter(getSupportFragmentManager());
			viewPager.setAdapter(pagerAdapter);

			// …Ë÷√ª¨∂Ø±Í«©µƒ  ≈‰∆˜∫ÕÀﬁ÷˜ViewPager
			scrollingTabs = (ScrollingTabsView) findViewById(R.id.scrolling_tabs);
			scrollingTabs.setVisibility(View.VISIBLE);
			scrollingTabsAdapter = new ScrollingTabsAdapter(this);
			scrollingTabs.setAdapter(scrollingTabsAdapter);
			scrollingTabs.setViewPager(viewPager);
			break;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void initData() {
		// TODO Auto-generated method stub
		fragments = new ArrayList<BaseFragment>();
		try {
			Resources res = getResources();
			int itemsId = getResources().getIdentifier(canName + "_items",
					"array", getPackageName());
			mTitles = res.getStringArray(itemsId);

			int fragmentId = getResources().getIdentifier(
					canName + "_fragment", "array", getPackageName());
			mFragmentTitles = res.getStringArray(fragmentId);

			for (int i = 0; i < mFragmentTitles.length && i < mTitles.length; i++) {
				try {
					Class classManager = Class.forName(mFragmentTitles[i]);
					BaseFragment mFragment = (BaseFragment) classManager
							.newInstance();
					fragments.add(mFragment);
					mFragment.setIndex(i, CarInfoActivity.this);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.i("cxs", "=====e==========" + e);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("cxs", "=====e==========" + e);
		}
	}

	/**
	 * ViewPager  ≈‰∆˜
	 * 
	 * @author zhaokaiqiang
	 * 
	 */
	private class FragsAdapter extends FragmentStatePagerAdapter {

		public FragsAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}

		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}
	}

	/**
	 * ª¨∂Ø±Í«©  ≈‰∆˜
	 * 
	 * @author zhaokaiqiang
	 * 
	 */
	private static class ScrollingTabsAdapter implements TabsAdapter {

		private Activity mContext;

		public ScrollingTabsAdapter(Activity ctx) {
			this.mContext = ctx;
		}

		@Override
		public View getView(int position) {
			Button tab = (Button) mContext.getLayoutInflater().inflate(
					R.layout.tab_scrolling, null);
			tab.setText(mTitles[position]);
			return tab;
		}

	}

	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		super.show(mCaninfo);
		if (fragments != null) {
			for (BaseFragment fragment : fragments) {
				if (fragment.getResumeStatus())
					fragment.show(mCaninfo);
			}
		}
	}

	@Override
	public void serviceConnected() {
		// TODO Auto-generated method stub
		super.serviceConnected();
		if (fragments != null) {
			for (BaseFragment fragment : fragments) {
				fragment.serviceConnected();
			}

		}
	}

}
