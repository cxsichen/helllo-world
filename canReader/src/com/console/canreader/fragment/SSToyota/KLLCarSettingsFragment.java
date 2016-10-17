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

public class KLLCarSettingsFragment extends BaseFragment {

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

		private SwitchPreference p6;
		private SwitchPreference p7;
		
		
		private SwitchPreference p8;
		private SwitchPreference p9;

		
		private ListPreference mAutoCapPref;
		private ListPreference p12;
		
		KLLCarSettingsFragment settingActivity;

		public SettingsFragment(KLLCarSettingsFragment settingActivity) {
			this.settingActivity = settingActivity;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.sskll_setting_prefs);

			p6 = (SwitchPreference) findPreference("AIRCON_WITH_AUTO");
			p7 = (SwitchPreference) findPreference("CYCLE_WITH_AUTO");
			

			p6.setOnPreferenceChangeListener(this);
			p7.setOnPreferenceChangeListener(this);
			
			p8 = (SwitchPreference) findPreference("LAMP_WHEN_LOCK");
			p9 = (SwitchPreference) findPreference("INTELLIGENT_LOCK");


			p8.setOnPreferenceChangeListener(this);
			p9.setOnPreferenceChangeListener(this);
	
			
			mAutoCapPref= (ListPreference) findPreference("AUTOMATIC_CAP_SENSEITIVITY");
			mAutoCapPref.setOnPreferenceChangeListener(this);
			p12= (ListPreference) findPreference("AUTOMATIC_LAMP_CLOSE");
			p12.setOnPreferenceChangeListener(this);
		}

		public void syncView(CanInfo mCaninfo) {

			p6.setChecked(mCaninfo.AIRCON_WITH_AUTO==1);
			p7.setChecked(mCaninfo.CYCLE_WITH_AUTO==1);
						
			p8.setChecked(mCaninfo.LAMP_WHEN_LOCK==1);
			p9.setChecked(mCaninfo.INTELLIGENT_LOCK==1);

			
			mAutoCapPref.setValue(String.valueOf(mCaninfo.AUTOMATIC_CAP_SENSEITIVITY));
			updatePreferenceDescription(mAutoCapPref,mCaninfo.AUTOMATIC_CAP_SENSEITIVITY);
			
			p12.setValue(String.valueOf(mCaninfo.AUTOMATIC_LAMP_CLOSE));
			updatePreferenceDescription(p12,mCaninfo.AUTOMATIC_LAMP_CLOSE);
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
			case "AUTO_LOCK_SETTING":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5036A0101"+((boolean) newValue ?"01":"00"));
				}
				break;
			case "AUTO_OPEN_LOCK":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5036A0102"+((boolean) newValue ?"00":"01"));
				}
				break;
			case "DRIVER_LINK_LOCK":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5036A0103"+((boolean) newValue ?"01":"00"));
				}
				break;
			case "AUTO_OPEN_LOCK_P":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5036A0104"+((boolean) newValue ?"01":"00"));
				}
				break;
			case "AUTO_LOCK_P":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5036A0105"+((boolean) newValue ?"01":"00"));
				}
				break;
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
			case "TWICE_KEY_OPEN_LOCK":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5036A0203"+((boolean) newValue ?"01":"00"));
				}

				break;
			case "TWICE_BUTTON_OPEN_LOCK":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5036A0204"+((boolean) newValue ?"01":"00"));
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
			case "AUTOMATIC_LAMP_CLOSE":
				if (settingActivity != null) {
					try {
		                int value = Integer.parseInt((String) newValue);
		                settingActivity.sendMsg("5AA5036A03020"+String.valueOf(value));
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
