package com.console.launcher_console.util;

import java.util.List;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class ViewPagerAdapter extends PagerAdapter {

	private List<View> list;

	public ViewPagerAdapter(List<View> list) {
		super();
		this.list = list;
	}

	public void setList(List<View> pList) {
		list = pList;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}

	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		// ((ViewPager) arg0).removeView(list.get(arg1 % list.size()));
	}

	@Override
	public Object instantiateItem(View arg0, int arg1) {
		try {
			// ((ViewPager) arg0).addView(list.get(arg1),0);
			((ViewPager) arg0).addView((View) list.get(arg1 % list.size()), 0);
		} catch (Exception e) {
		}
		return list.get(arg1 % list.size());

	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {

	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {

	}

	@Override
	public void finishUpdate(View arg0) {

	}

}
