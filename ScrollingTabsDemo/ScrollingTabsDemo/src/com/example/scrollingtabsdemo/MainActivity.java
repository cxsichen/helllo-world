package com.example.scrollingtabsdemo;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.astuetz.viewpager.extensions.ScrollingTabsView;
import com.astuetz.viewpager.extensions.TabsAdapter;

public class MainActivity extends FragmentActivity {

	private ViewPager viewPager;
	private ScrollingTabsAdapter scrollingTabsAdapter;
	private ScrollingTabsView scrollingTabs;
	private FragsAdapter pagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		viewPager = (ViewPager) findViewById(R.id.pager);

		// 设置缓存fragment的数量
		viewPager.setOffscreenPageLimit(2);
		viewPager.setCurrentItem(0);
		viewPager.setPageMargin(4);

		// 设置适配器
		pagerAdapter = new FragsAdapter(getSupportFragmentManager());
		viewPager.setAdapter(pagerAdapter);
		
		
		// 设置滑动标签的适配器和宿主ViewPager
		scrollingTabs = (ScrollingTabsView) findViewById(R.id.scrolling_tabs);
		scrollingTabsAdapter = new ScrollingTabsAdapter(this);
		scrollingTabs.setAdapter(scrollingTabsAdapter);
		scrollingTabs.setViewPager(viewPager);

	}

	/**
	 * ViewPager适配器
	 * 
	 * @author zhaokaiqiang
	 * 
	 */
	private class FragsAdapter extends FragmentStatePagerAdapter {

		private ArrayList<Fragment> fragments;

		public FragsAdapter(FragmentManager fm) {
			super(fm);

			fragments = new ArrayList<Fragment>();
			for (int i = 0; i < ScrollingTabsAdapter.mTitles.length; i++) {
				fragments.add(new MyFragment(i));
			}

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
	 * 滑动标签适配器
	 * 
	 * @author zhaokaiqiang
	 * 
	 */
	private static class ScrollingTabsAdapter implements TabsAdapter {

		private Activity mContext;
		public static String[] mTitles = { "首页首页首页首页首页首页首页", "推荐", "最新", "娱乐", "设置" ,"首页首页首页首页首页首页首页"
			,"首页首页首页首页首页首页首页","首页首页首页首页首页首页首页"};

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

}
