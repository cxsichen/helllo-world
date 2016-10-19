package com.console.canreader.fragment.SSTrumpchi;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class GS4HIGHCarAirSettingsFragment extends BaseFragment {

	private TextView version;
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
		try {

			super.show(mCaninfo);
			if (mCaninfo != null) {
				if (settingsFragment != null) {
					settingsFragment.syncView(mCaninfo);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
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

		private ListPreference position_heat_left;
		private ListPreference position_heat_right;

		GS4HIGHCarAirSettingsFragment settingActivity;

		public SettingsFragment(GS4HIGHCarAirSettingsFragment settingActivity) {
			this.settingActivity = settingActivity;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.trumpchigs4_air_prefs);
			initView();
		}

		private void initView() {
			position_heat_left = (ListPreference) findPreference("position_heat_left");
			position_heat_right = (ListPreference) findPreference("position_heat_right");
			position_heat_left.setOnPreferenceChangeListener(this);
			position_heat_right.setOnPreferenceChangeListener(this);
		}

		public void syncView(CanInfo mCaninfo) {
			CharSequence[] a = position_heat_left.getEntries();
			position_heat_left
					.setSummary(a[mCaninfo.LEFT_SEAT_TEMP].toString());
			a = position_heat_right.getEntries();
			position_heat_right.setSummary(a[mCaninfo.RIGTHT_SEAT_TEMP]
					.toString());

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
			Log.i("cxs", "==onPreferenceChange-key===" + key);
			switch (key) {
			case "position_heat_left":
				if (settingActivity != null) {
					int value = Integer.parseInt((String) newValue);
					CharSequence[] a = position_heat_left.getEntries();
					position_heat_left.setSummary(a[value].toString());
					settingActivity.sendMsg("5AA5023B0F"
							+ BytesUtil.intToHexString(value));
				}
				break;
			case "position_heat_right":
				if (settingActivity != null) {
					int value = Integer.parseInt((String) newValue);
					CharSequence[] a = position_heat_right.getEntries();
					position_heat_right.setSummary(a[value].toString());
					settingActivity.sendMsg("5AA5023B10"
							+ BytesUtil.intToHexString(value));
				}
				break;

			default:
				break;
			}
			return true;
		}

	}

}
