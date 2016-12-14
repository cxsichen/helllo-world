package com.console.canreader.fragment.SSHondaYG9;

import android.os.Bundle;
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

public class SeniorSettingsFragment extends BaseFragment {

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
		super.show(mCaninfo);
		if (mCaninfo != null) {
			if (settingsFragment != null) {
				try {
					settingsFragment.syncView(mCaninfo);
				} catch (Exception e) {
					// TODO: handle exception
					Log.i("cxs", "========e====" + e);
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

		private ListPreference p4;
		private ListPreference p5;


		private ListPreference p11;
		private SwitchPreference p12;

		SeniorSettingsFragment settingActivity;

		public SettingsFragment(SeniorSettingsFragment settingActivity) {
			this.settingActivity = settingActivity;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.sshondayg9_senior_setting_prefs);
			/*---------lamp ------*/

			p4 = (ListPreference) findPreference("FRONT_LAMP_OFF_TIME");
			p4.setOnPreferenceChangeListener(this);

			p5 = (ListPreference) findPreference("LAMP_TURN_DARK_TIME");
			p5.setOnPreferenceChangeListener(this);
			/*---------lamp ------*/


			/*----------lock------*/
			p11 = (ListPreference) findPreference("AUTO_LOCK_TIME");
			p11.setOnPreferenceChangeListener(this);

			p12 = (SwitchPreference) findPreference("REMOTE_LOCK_SIGN");
			p12.setOnPreferenceChangeListener(this);
			/*----------lock------*/


			if (settingActivity != null) {
				if (settingActivity.getCanInfo() != null)
					syncView(settingActivity.getCanInfo());
			}
		}

		public void syncView(CanInfo mCaninfo) {
			/*---------lamp ------*/
			updateListPreference(p4, mCaninfo.FRONT_LAMP_OFF_TIME);
			updateListPreference(p5, mCaninfo.LAMP_TURN_DARK_TIME);
			/*---------lamp ------*/

			/*----------lock------*/
			updateListPreference(p11, mCaninfo.AUTO_LOCK_TIME);
			p12.setChecked(mCaninfo.REMOTE_LOCK_SIGN == 1);
			/*----------lock------*/


		}

		public void updateListPreference(ListPreference p, int index) {
			p.setValue(String.valueOf(index));
			updatePreferenceDescription(p, index);
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
			switch (key) {
			/*---------lamp ------*/
			case "FRONT_LAMP_OFF_TIME":
				if (settingActivity != null) {
					try {
						int value = Integer.parseInt((String) newValue);
						settingActivity.sendMsg("5AA5026C020"
								+ (String) newValue);
						updatePreferenceDescription(p4, value);
					} catch (NumberFormatException e) {
					}
				}
				break;
			case "LAMP_TURN_DARK_TIME":
				if (settingActivity != null) {
					try {
						int value = Integer.parseInt((String) newValue);
						settingActivity.sendMsg("5AA5026C010"
								+ (String) newValue);
						updatePreferenceDescription(p5, value);
					} catch (NumberFormatException e) {
					}
				}
				break;
			/*---------lamp ------*/
			/*----------lock------*/
			case "AUTO_LOCK_TIME":
				if (settingActivity != null) {
					try {
						int value = Integer.parseInt((String) newValue);
						settingActivity.sendMsg("5AA5026A030"
								+ (String) newValue);
						updatePreferenceDescription(p11, value);
					} catch (NumberFormatException e) {
					}
				}
				break;
			case "REMOTE_LOCK_SIGN":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5026A04"
							+ ((boolean) newValue ? "01" : "00"));
				}
				break;
			default:
				break;
			}
			return true;
		}
	}
}
