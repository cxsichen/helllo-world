package com.console.canreader.fragment.SSToyota;

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

public class RAV4CarSettingsFragment extends BaseFragment {

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
				settingsFragment.syncView(mCaninfo);
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

		private SwitchPreference mAirConPref;
		private SwitchPreference mCyclePref;
		private SwitchPreference mLampLockPref;
		private SwitchPreference mIntelligentPref;
		private ListPreference mAutoCapPref;
		RAV4CarSettingsFragment settingActivity;

		public SettingsFragment(RAV4CarSettingsFragment settingActivity) {
			this.settingActivity = settingActivity;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.setting_prefs);
			mAirConPref = (SwitchPreference) findPreference("AIRCON_WITH_AUTO");
			mCyclePref = (SwitchPreference) findPreference("CYCLE_WITH_AUTO");
			mLampLockPref = (SwitchPreference) findPreference("LAMP_WHEN_LOCK");
			mIntelligentPref = (SwitchPreference) findPreference("INTELLIGENT_LOCK");
			mAutoCapPref= (ListPreference) findPreference("AUTOMATIC_CAP_SENSEITIVITY");
			mAirConPref.setOnPreferenceChangeListener(this);
			mCyclePref.setOnPreferenceChangeListener(this);
			mLampLockPref.setOnPreferenceChangeListener(this);
			mIntelligentPref.setOnPreferenceChangeListener(this);
			mAutoCapPref.setOnPreferenceChangeListener(this);
		}

		public void syncView(CanInfo mCaninfo) {
			mAirConPref.setChecked(mCaninfo.AIRCON_WITH_AUTO==1);
			mCyclePref.setChecked(mCaninfo.CYCLE_WITH_AUTO==1);
			mLampLockPref.setChecked(mCaninfo.LAMP_WHEN_LOCK==1);
			mIntelligentPref.setChecked(mCaninfo.INTELLIGENT_LOCK==1);
			mAutoCapPref.setValue(String.valueOf(mCaninfo.AUTOMATIC_CAP_SENSEITIVITY));
			updatePreferenceDescription(mAutoCapPref,mCaninfo.AUTOMATIC_CAP_SENSEITIVITY);
		}
		
		private void updatePreferenceDescription(ListPreference preference,int currentTimeout) {
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
		                summary =  entries[best].toString();
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
			case "AIRCON_WITH_AUTO":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5036A0106"+((boolean) newValue ?"01":"00"));
				}
				break;
			case "CYCLE_WITH_AUTO":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5036A0107"+((boolean) newValue ?"01":"00"));
				}
				break;
			case "LAMP_WHEN_LOCK":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5036A0201"+((boolean) newValue ?"01":"00"));
				}

				break;
			case "INTELLIGENT_LOCK":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5036A0202"+((boolean) newValue ?"01":"00"));
				}
				break;
			case "AUTOMATIC_CAP_SENSEITIVITY":
				if (settingActivity != null) {
					try {
		                int value = Integer.parseInt((String) newValue);
		                settingActivity.sendMsg("5AA5036A03010"+String.valueOf(value));
		                updatePreferenceDescription(mAutoCapPref,value);
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
