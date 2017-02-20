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

public class ScreenShowSettingsFragment extends BaseFragment {

	private TextView version;
	SettingsFragment settingsFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_activity_layout_2,
				container, false);
		initView(view);
		initFragment();
		return view;
	}

	private void initFragment() {
		// TODO Auto-generated method stub
		settingsFragment = new SettingsFragment(this);
		getActivity().getFragmentManager().beginTransaction()
				.replace(R.id.content_layout_2, settingsFragment).commit();
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


		private SwitchPreference p4;


		private ListPreference p6;
		private ListPreference p7;
		private ListPreference p8;

		ScreenShowSettingsFragment settingActivity;

		public SettingsFragment(ScreenShowSettingsFragment settingActivity) {
			this.settingActivity = settingActivity;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.sshondayg9_screen_show_setting_prefs);
			
			p8 = (ListPreference) findPreference("ADJUST_OUTSIDE_TEMP");
			p8.setOnPreferenceChangeListener(this);
			
			p4 = (SwitchPreference) findPreference("ENERGY_BACKGROUND_LIGHT");
			p4.setOnPreferenceChangeListener(this);
			
			
			p6 = (ListPreference) findPreference("SWITCH_TRIPB_SETTING");
			p6.setOnPreferenceChangeListener(this);

			p7 = (ListPreference) findPreference("SWITCH_TRIPA_SETTING");
			p7.setOnPreferenceChangeListener(this);
			
			





			if (settingActivity != null) {
				if (settingActivity.getCanInfo() != null)
					syncView(settingActivity.getCanInfo());
			}
		}

		public void syncView(CanInfo mCaninfo) {

			p4.setChecked(mCaninfo.ENERGY_BACKGROUND_LIGHT == 1);
	

			updateListPreference(p6, mCaninfo.SWITCH_TRIPB_SETTING);
			updateListPreference(p7, mCaninfo.SWITCH_TRIPA_SETTING);
			updateListPreference(p8, mCaninfo.ADJUST_OUTSIDE_TEMP);
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
			case "RATATIONAL_RATE":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5026F06"
							+ ((boolean) newValue ? "01" : "00"));
				}
				break;
			case "MSG_NOTIFICATION":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5026F07"
							+ ((boolean) newValue ? "01" : "00"));
				}
				break;
			case "ENGINEE_AUTO_CONTROL":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5026F08"
							+ ((boolean) newValue ? "01" : "00"));
				}
				break;
			case "ENERGY_BACKGROUND_LIGHT":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5027A03"
							+ ((boolean) newValue ? "01" : "00"));
				}
				break;
			case "SWITCH_TRIPB_SETTING":
				if (settingActivity != null) {
					try {
						int value = Integer.parseInt((String) newValue);
						settingActivity.sendMsg("5AA5027A020"
								+ (String) newValue);
						updatePreferenceDescription(p6, value);
					} catch (NumberFormatException e) {
					}
				}
				break;
			case "SWITCH_TRIPA_SETTING":
				if (settingActivity != null) {
					try {
						int value = Integer.parseInt((String) newValue);
						settingActivity.sendMsg("5AA5027A010"
								+ (String) newValue);
						updatePreferenceDescription(p7, value);
					} catch (NumberFormatException e) {
					}
				}
				break;
			case "ADJUST_OUTSIDE_TEMP":
				if (settingActivity != null) {
					try {
						int value = Integer.parseInt((String) newValue);
						settingActivity.sendMsg("5AA5026D010"
								+ (String) newValue);
						updatePreferenceDescription(p8, value);
					} catch (NumberFormatException e) {
					}
				}
				break;
			default:
				break;
			}
			return true;
		}
	}
}
