package com.console.canreader.fragment.SSVolkswagenGolf;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.console.canreader.R;
import com.console.canreader.activity.BaseFragment;
import com.console.canreader.activity.Toyota.OilEleActivity;
import com.console.canreader.activity.Toyota.SettingActivity;
import com.console.canreader.activity.Toyota.OilEleActivity.SettingsFragment;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;
import com.console.canreader.utils.PreferenceUtil;

public class CarInfoSettingsFragment extends BaseFragment {

	SettingsFragment settingsFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_activity_layout,
				container, false);
		initView(view);
		initFragment();
		return view;
	}

	private void initFragment() {
		// TODO Auto-generated method stub
		settingsFragment = new SettingsFragment(this);
		getActivity().getFragmentManager().beginTransaction()
				.replace(R.id.content_layout, settingsFragment).commit();
	}

	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		super.show(mCaninfo);
		if (mCaninfo != null) {
			if (settingsFragment != null) {
				try {
					settingsFragment.syncView(mCaninfo);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
	}

	@Override
	public void serviceConnected() {
		// TODO Auto-generated method stub
		super.serviceConnected();
	}

	private void initView(View view) {

	}

	public class SettingsFragment extends PreferenceFragment implements
			OnPreferenceChangeListener {

		CarInfoSettingsFragment settingActivity;

		List<SwitchPreference> mSwitchPreferenceGroup = new ArrayList<SwitchPreference>();
		List<Integer> mSwitchValueGroup = new ArrayList<Integer>();
		List<ListPreference> mListPreferenceGroup = new ArrayList<ListPreference>();
		List<Integer> mListValueGroup = new ArrayList<Integer>();
		Preference mPreference;
		/*-----------set data----------------*/
		String[] swPreKey = { "TYPES_SPEED_WARNING", "REMOTE_KEY",
				"KEY_ACTIVE", "PROFILE_STEERING" };
		String[] swPreMsg = { "2EC60220", "2EC602CB", "2EC602CA", "2EC602D1" };

		String[] listPreKey = { "LANGUAGE_CHANGE", "ESC_SYSTEM",
				"PROFILE_INFORMATION", "INDIVIDUAL_ENGINE",
				"PROFILE_FRONT_LIGHT", "PROFILE_CLIMATE" };
		String[] listPreMsg = { "2EC60200", "2EC60210", "2EC602D0", "2EC602D2",
				"2EC602D5", "2EC602D3" };

		private void addListData(List<Integer> mListValueGroup2,
				CanInfo mCaninfo) {
			// TODO Auto-generated method stub
			mListValueGroup2.add(mCaninfo.LANGUAGE_CHANGE);
			mListValueGroup2.add(mCaninfo.ESC_SYSTEM);

			mListValueGroup2.add(mCaninfo.PROFILE_INFORMATION);
			mListValueGroup2.add(mCaninfo.INDIVIDUAL_ENGINE);
			mListValueGroup2.add(mCaninfo.PROFILE_FRONT_LIGHT);
			mListValueGroup2.add(mCaninfo.PROFILE_CLIMATE);
		}

		private void addSwitchData(List<Integer> mSwitchValueGroup2,
				CanInfo mCaninfo) {
			// TODO Auto-generated method stub
			mSwitchValueGroup2.add(mCaninfo.TYPES_SPEED_WARNING);
			mSwitchValueGroup2.add(mCaninfo.REMOTE_KEY);
			mSwitchValueGroup2.add(mCaninfo.KEY_ACTIVE);
			mSwitchValueGroup2.add(mCaninfo.PROFILE_STEERING);

		}

		/*-----------set data----------------*/

		public SettingsFragment(CarInfoSettingsFragment settingActivity) {
			this.settingActivity = settingActivity;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.rzcgolf_setting_prefs);

			for (String str : swPreKey) {
				SwitchPreference p = (SwitchPreference) findPreference(str);
				p.setOnPreferenceChangeListener(this);
				mSwitchPreferenceGroup.add(p);
			}

			for (String str : listPreKey) {
				ListPreference p = (ListPreference) findPreference(str);
				p.setOnPreferenceChangeListener(this);
				mListPreferenceGroup.add(p);
			}

			mPreference = findPreference("TYPES_SPEED");

			if (settingActivity != null) {
				if (settingActivity.getCanInfo() != null)
					syncView(settingActivity.getCanInfo());
			}
		}

		public void syncView(CanInfo mCaninfo) {
			mPreference.setSummary(mCaninfo.TYPES_SPEED
					+ (mCaninfo.TYPES_SPEED_UNIT == 1 ? "mph" : "km/h"));

			mSwitchValueGroup.clear();
			addSwitchData(mSwitchValueGroup, mCaninfo);

			for (int i = 0; i < mSwitchPreferenceGroup.size(); i++) {
				mSwitchPreferenceGroup.get(i).setChecked(
						mSwitchValueGroup.get(i) == 1);
			}

			mListValueGroup.clear();
			addListData(mListValueGroup, mCaninfo);
			for (int i = 0; i < mListPreferenceGroup.size(); i++) {
				updatePreferenceDescription(mListPreferenceGroup.get(i),
						mListValueGroup.get(i));
			}
		}

		private void updatePreferenceDescription(ListPreference preference,
				int currentTimeout) {
			String summary;
			final CharSequence[] entries = preference.getEntries();
			final CharSequence[] values = preference.getEntryValues();
			if (entries == null || entries.length == 0) {
				summary = "";
			} else {
				int best = 0;
				for (int i = 0; i < values.length; i++) {
					int timeout = Integer.parseInt(values[i].toString());
					if (currentTimeout == timeout) {
						best = i;
						break;
					}
				}
				if (entries.length != 0) {
					summary = entries[best].toString();
				} else {
					summary = "";
				}

			}
			preference.setSummary(summary);
		}

		@Override
		public boolean onPreferenceChange(Preference preference, Object newValue) {
			// TODO Auto-generated method stub
			final String key = preference.getKey();
			for (int i = 0; i < swPreKey.length; i++) {
				if (key.equals(swPreKey[i])) {
					if (settingActivity != null)
						settingActivity.sendMsg(swPreMsg[i]
								+ ((boolean) newValue ? "01" : "00"));
				}
			}
			for (int i = 0; i < listPreKey.length; i++) {
				if (key.equals(listPreKey[i])) {
					if (settingActivity != null) {
						try {
							int value = Integer.parseInt((String) newValue);
							settingActivity.sendMsg(listPreMsg[i]
									+ BytesUtil.changIntHex(value));
							updatePreferenceDescription(
									mListPreferenceGroup.get(i), value);
						} catch (NumberFormatException e) {
						}
					}
				}
			}
			return true;
		}
	}

}
