package com.console.canreader.fragment.SSGE;

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
import com.console.canreader.utils.PreferenceUtil;

public class RemoteControlSettingsFragment extends BaseFragment {

	SettingsFragment settingsFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_activity_layout_5,
				container, false);
		initView(view);
		initFragment();
		return view;
	}

	private void initFragment() {
		// TODO Auto-generated method stub
		settingsFragment = new SettingsFragment(this);
		getActivity().getFragmentManager().beginTransaction()
				.replace(R.id.content_layout_5, settingsFragment).commit();
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

		RemoteControlSettingsFragment settingActivity;

		List<SwitchPreference> mSwitchPreferenceGroup = new ArrayList<SwitchPreference>();
		List<Integer> mSwitchValueGroup = new ArrayList<Integer>();
		String[] swPreKey = { "REMOTECONTROL_UNLOCK_FEEDBACK",
				"REMOTECONTROL_UNLOCK", "REMOTECONTROL_UNLOCK_AUTORELOCK",
				"REMOTECONTROL_RELOCK_DOOR", "REMOTECONTROL_KEY_AUTORECOGNIZE",
				"REMOTECONTROL_START", "REMOTECONTROL_NEAR_AUTOUNLOCK",
				"REMOTECONTROL_KEYLEF_ALARM", "REMOTECONTROL_MOVE_DOOR",
				"REMOTECONTROL_WINDOW_CONTROL" };
		String[] swPreMsg = { "5AA5026B02", "5AA5026B03", "5AA5026B04",
				"5AA5026B05", "5AA5026B06", "5AA5026B07", "5AA5026B08",
				"5AA5026B09", "5AA5026B0B", "5AA5026B0C" };

		List<ListPreference> mListPreferenceGroup = new ArrayList<ListPreference>();
		List<Integer> mListValueGroup = new ArrayList<Integer>();
		String[] listPreKey = { "REMOTECONTROL_LOCK_FEEDBACK", "REMOTECONTROL_AWAY_AUTOLOCK" };
		String[] listPreMsg = { "5AA5026B010", "5AA5026B0A0" };

		public SettingsFragment(RemoteControlSettingsFragment settingActivity) {
			this.settingActivity = settingActivity;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.ssge_remotecontrol_setting_prefs);

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
			mSwitchValueGroup.add(mCaninfo.REMOTECONTROL_UNLOCK_FEEDBACK);
			mSwitchValueGroup.add(mCaninfo.REMOTECONTROL_UNLOCK);
			mSwitchValueGroup.add(mCaninfo.REMOTECONTROL_UNLOCK_AUTORELOCK);
			mSwitchValueGroup.add(mCaninfo.REMOTECONTROL_RELOCK_DOOR);
			mSwitchValueGroup.add(mCaninfo.REMOTECONTROL_KEY_AUTORECOGNIZE);
			mSwitchValueGroup.add(mCaninfo.REMOTECONTROL_START);
			mSwitchValueGroup.add(mCaninfo.REMOTECONTROL_NEAR_AUTOUNLOCK);
			mSwitchValueGroup.add(mCaninfo.REMOTECONTROL_KEYLEF_ALARM);
			mSwitchValueGroup.add(mCaninfo.REMOTECONTROL_MOVE_DOOR);
			mSwitchValueGroup.add(mCaninfo.REMOTECONTROL_WINDOW_CONTROL);
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
			mListValueGroup.add(mCaninfo.REMOTECONTROL_LOCK_FEEDBACK);
			mListValueGroup.add(mCaninfo.REMOTECONTROL_AWAY_AUTOLOCK);
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
									+ String.valueOf(value));
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
