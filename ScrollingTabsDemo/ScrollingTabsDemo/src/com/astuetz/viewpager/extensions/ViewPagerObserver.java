/*
 * Copyright (C) 2011 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.astuetz.viewpager.extensions;

import java.util.ArrayList;

import android.support.v4.view.ViewPager.OnPageChangeListener;

public class ViewPagerObserver implements OnPageChangeListener {

	@SuppressWarnings("unused")
	private static final String TAG = "com.astuetz.viewpager.extensions";

	private ArrayList<OnPageChangeListener> mListeners = new ArrayList<OnPageChangeListener>();

	public void addListener(OnPageChangeListener listener) {
		mListeners.add(listener);
	}

	@Override
	public void onPageScrollStateChanged(int state) {
		for (OnPageChangeListener l : mListeners) {
			l.onPageScrollStateChanged(state);
		}
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		for (OnPageChangeListener l : mListeners) {
			l.onPageScrolled(position, positionOffset, positionOffsetPixels);
		}
	}

	@Override
	public void onPageSelected(int position) {
		for (OnPageChangeListener l : mListeners) {
			l.onPageSelected(position);
		}
	}

}
