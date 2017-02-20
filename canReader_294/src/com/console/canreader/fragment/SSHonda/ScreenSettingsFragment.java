package com.console.canreader.fragment.SSHonda;

import android.os.Bundle;
import android.preference.PreferenceGroup;
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

public class ScreenSettingsFragment extends BaseFragment {

	private TextView version;
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

	/*	private SwitchPreference p1;*/
		private ListPreference p2;

		ScreenSettingsFragment settingActivity;
		
		private Boolean ISRESUME=false;

		public SettingsFragment(ScreenSettingsFragment settingActivity) {
			this.settingActivity = settingActivity;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.sshonda_screen_setting_prefs);

/*			p1 = (SwitchPreference) findPreference("LEFT_CAMERA_SWITCH");
			p1.setOnPreferenceChangeListener(this);*/


			p2 = (ListPreference) findPreference("BACK_CAMERA_MODE");
			p2.setOnPreferenceChangeListener(this);
			
			
			if(settingActivity!=null){
				if(settingActivity.getCanInfo()!=null)
					syncView(settingActivity.getCanInfo());
			}
		}
		
		@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			if(settingActivity!=null){
				if(settingActivity.getCanInfo()!=null)
					syncView(settingActivity.getCanInfo());
			}
		}
		

		public void syncView(CanInfo mCaninfo) {
	/*		p1.setChecked(mCaninfo.LEFT_CAMERA_SWITCH == 1);*/
			p2.setValue(String.valueOf(mCaninfo.BACK_CAMERA_MODE - 1));
			updatePreferenceDescription(p2, mCaninfo.BACK_CAMERA_MODE - 1);
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
			case "LEFT_CAMERA_SWITCH":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA502F207"
							+ ((boolean) newValue ? "01" : "00"));
				}
				break;
			case "BACK_CAMERA_MODE":
				if (settingActivity != null) {
					try {
						int value = Integer.parseInt((String) newValue);
						switch (value) {
						case 0:
							settingActivity.sendMsg("5AA502F201FF");
							break;
						case 1:
							settingActivity.sendMsg("5AA502F202FF");
							break;
						case 2:
							settingActivity.sendMsg("5AA502F203FF");
							break;
						default:
							break;
						}
						updatePreferenceDescription(p2, value);
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
