package com.console.canreader.fragment.SSMGGS;

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

public class LightSettingsFragment extends BaseFragment {

	SettingsFragment settingsFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_activity_layout_1,
				container, false);
		initView(view);
		initFragment();
		return view;
	}

	private void initFragment() {
		// TODO Auto-generated method stub
		settingsFragment = new SettingsFragment(this);
		getActivity().getFragmentManager().beginTransaction()
				.replace(R.id.content_layout_1, settingsFragment).commit();
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

		LightSettingsFragment settingActivity;

		List<SwitchPreference> mSwitchPreferenceGroup = new ArrayList<SwitchPreference>();
		List<Integer> mSwitchValueGroup = new ArrayList<Integer>();
		String[] swPreKey = { "LIGHT_COMING_HOME_BACKUP",
				"LIGHT_COMING_HOME_DIPPED", "LIGHT_COMING_HOME_REARFOG",
				"LIGHT_SEEK_CAR_BACKUP", "LIGHT_SEEK_CAR_DIPPED",
				"LIGHT_SEEK_CAR_REARFOG" };
		String[] swPreMsg = { "5AA5026C03", "5AA5026C04", "5AA5026C05",
				"5AA5026C06", "5AA5026C07", "5AA5026C08" };

		List<ListPreference> mListPreferenceGroup = new ArrayList<ListPreference>();
		List<Integer> mListValueGroup = new ArrayList<Integer>();
		String[] listPreKey = {"LIGHT_COMING_HOME_TIME", "LIGHT_SEEK_CAR_TIME",};
		String[] listPreMsg = {"5AA5026C09", "5AA5026C0A" };

		public SettingsFragment(LightSettingsFragment settingActivity) {
			this.settingActivity = settingActivity;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.ssmggs_light_setting_prefs);

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

			if (settingActivity != null) {
				if (settingActivity.getCanInfo() != null)
					syncView(settingActivity.getCanInfo());
			}
		}

		public void syncView(CanInfo mCaninfo) {
			mSwitchValueGroup.clear();
			mSwitchValueGroup.add(mCaninfo.LIGHT_COMING_HOME_BACKUP);
			mSwitchValueGroup.add(mCaninfo.LIGHT_COMING_HOME_DIPPED);
			mSwitchValueGroup.add(mCaninfo.LIGHT_COMING_HOME_REARFOG);
			mSwitchValueGroup.add(mCaninfo.LIGHT_SEEK_CAR_BACKUP);
			mSwitchValueGroup.add(mCaninfo.LIGHT_SEEK_CAR_DIPPED);
			mSwitchValueGroup.add(mCaninfo.LIGHT_SEEK_CAR_REARFOG);
			for (int i = 0; i < mSwitchPreferenceGroup.size(); i++) {
				if (mSwitchValueGroup.get(i) == -1) {
					getPreferenceScreen().removePreference(
							mSwitchPreferenceGroup.get(i));
				} else {
					getPreferenceScreen().addPreference(
							mSwitchPreferenceGroup.get(i));
					mSwitchPreferenceGroup.get(i).setChecked(
							mSwitchValueGroup.get(i) == 1);
				}

			}

			mListValueGroup.clear();
			mListValueGroup.add(mCaninfo.LIGHT_COMING_HOME_TIME);
			mListValueGroup.add(mCaninfo.LIGHT_SEEK_CAR_TIME);
			for (int i = 0; i < mListPreferenceGroup.size(); i++) {
				if (mListValueGroup.get(i) == -1) {
					getPreferenceScreen().removePreference(
							mListPreferenceGroup.get(i));
				} else {
					getPreferenceScreen().addPreference(
							mListPreferenceGroup.get(i));
					updatePreferenceDescription(mListPreferenceGroup.get(i),
							mListValueGroup.get(i));
				}

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
									+ BytesUtil.intToHexString(value));
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
