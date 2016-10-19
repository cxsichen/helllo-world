package com.console.canreader.fragment.SSHonda;

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
					Log.i("cxs","========e===="+e);
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

		private SwitchPreference p1;
		private ListPreference p2;
		private ListPreference p3;
		private ListPreference p4;
		private ListPreference p5;
		
		private SwitchPreference p6;
		private SwitchPreference p7;
		private SwitchPreference p8;
		private SwitchPreference p9;
		
		private SwitchPreference p10;
		private ListPreference p11;
		private SwitchPreference p12;
		
		private ListPreference p13;
		private SwitchPreference p14;
		private SwitchPreference p15;
		private ListPreference p16;

		SeniorSettingsFragment settingActivity;

		public SettingsFragment(SeniorSettingsFragment settingActivity) {
			this.settingActivity = settingActivity;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.sshonda_senior_setting_prefs);
            /*---------lamp ------*/
			p1 = (SwitchPreference) findPreference("WIPER_LINK_LAMP");
			p1.setOnPreferenceChangeListener(this);

			p2 = (ListPreference) findPreference("AUTO_LIGHT_SENSEITIVITY");
			p2.setOnPreferenceChangeListener(this);
			
			p3 = (ListPreference) findPreference("AUTO_LIGHTING_SENSEITIVITY");
			p3.setOnPreferenceChangeListener(this);
			
			p4 = (ListPreference) findPreference("FRONT_LAMP_OFF_TIME");
			p4.setOnPreferenceChangeListener(this);
			
			p5 = (ListPreference) findPreference("LAMP_TURN_DARK_TIME");
			p5.setOnPreferenceChangeListener(this);
			 /*---------lamp ------*/
			
			/*----------remote cotrol------*/
			p6 = (SwitchPreference) findPreference("REMOTELOCK_BEEP_SIGN");
			p6.setOnPreferenceChangeListener(this);
			
			p7 = (SwitchPreference) findPreference("REMOTELOCK_SIDELAMP_SIGN");
			p7.setOnPreferenceChangeListener(this);
			
			p8 = (SwitchPreference) findPreference("SPEECH_WARING_VOLUME");
			p8.setOnPreferenceChangeListener(this);
			
			p9 = (SwitchPreference) findPreference("REMOTE_START_SYSTEM");
			p9.setOnPreferenceChangeListener(this);
			/*----------remote cotrol------*/
			
			/*----------lock------*/
			p10 = (SwitchPreference) findPreference("LOCK_PERSONAL_SETTING");
			p10.setOnPreferenceChangeListener(this);
			
			p11 = (ListPreference) findPreference("AUTO_LOCK_TIME");
			p11.setOnPreferenceChangeListener(this);
					
			p12 = (SwitchPreference) findPreference("REMOTE_LOCK_SIGN");
			p12.setOnPreferenceChangeListener(this);			
			/*----------lock------*/
			
			/*----------adas------*/
			p13 = (ListPreference) findPreference("LANE_DEPARTURE");
			p13.setOnPreferenceChangeListener(this);
			
			p14 = (SwitchPreference) findPreference("PAUSE_LKAS_SIGN");
			p14.setOnPreferenceChangeListener(this);
			
			p15 = (SwitchPreference) findPreference("DETECT_FRONT_CAR");
			p15.setOnPreferenceChangeListener(this);
			
			p16 = (ListPreference) findPreference("FRONT_DANGER_WAIRNG_DISTANCE");
			p16.setOnPreferenceChangeListener(this);			
			/*----------adas------*/
			
			if(settingActivity!=null){
				if(settingActivity.getCanInfo()!=null)
					syncView(settingActivity.getCanInfo());
			}
		}

		public void syncView(CanInfo mCaninfo) {
			 /*---------lamp ------*/
			p1.setChecked(mCaninfo.WIPER_LINK_LAMP == 1);

			updateListPreference(p2,mCaninfo.AUTO_LIGHT_SENSEITIVITY);
			updateListPreference(p3,mCaninfo.AUTO_LIGHTING_SENSEITIVITY);
			updateListPreference(p4,mCaninfo.FRONT_LAMP_OFF_TIME);
			updateListPreference(p5,mCaninfo.LAMP_TURN_DARK_TIME);
			 /*---------lamp ------*/
			
			/*----------remote cotrol------*/
			p6.setChecked(mCaninfo.REMOTELOCK_BEEP_SIGN == 1);
			p7.setChecked(mCaninfo.REMOTELOCK_SIDELAMP_SIGN == 1);
			p8.setChecked(mCaninfo.SPEECH_WARING_VOLUME == 1);
			p9.setChecked(mCaninfo.REMOTE_START_SYSTEM == 1);			
			/*----------remote cotrol------*/
			
			/*----------lock------*/
			p10.setChecked(mCaninfo.LOCK_PERSONAL_SETTING == 1);
			updateListPreference(p11,mCaninfo.AUTO_LOCK_TIME);
			p12.setChecked(mCaninfo.REMOTE_LOCK_SIGN == 1);		
			/*----------lock------*/
			
			/*----------adas------*/
			updateListPreference(p13,mCaninfo.LANE_DEPARTURE);
			p14.setChecked(mCaninfo.PAUSE_LKAS_SIGN == 1);
			p15.setChecked(mCaninfo.DETECT_FRONT_CAR == 1);
			updateListPreference(p16,mCaninfo.FRONT_DANGER_WAIRNG_DISTANCE);
			/*----------adas------*/
		}
		
		public  void updateListPreference(ListPreference p,int index){
			p.setValue(String.valueOf(index));
			updatePreferenceDescription(p,index);
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
			case "WIPER_LINK_LAMP":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5026C05"
							+ ((boolean) newValue ? "01" : "00"));
				}
				break;
			case "AUTO_LIGHT_SENSEITIVITY":
				if (settingActivity != null) {
					try {
						int value = Integer.parseInt((String) newValue);						
						settingActivity.sendMsg("5AA5026C040"+(String) newValue);					
						updatePreferenceDescription(p2, value);
					} catch (NumberFormatException e) {
					}
				}
				break;
			case "AUTO_LIGHTING_SENSEITIVITY":
				if (settingActivity != null) {
					try {
						int value = Integer.parseInt((String) newValue);						
						settingActivity.sendMsg("5AA5026C030"+(String) newValue);					
						updatePreferenceDescription(p3, value);
					} catch (NumberFormatException e) {
					}
				}
				break;
			case "FRONT_LAMP_OFF_TIME":
				if (settingActivity != null) {
					try {
						int value = Integer.parseInt((String) newValue);						
						settingActivity.sendMsg("5AA5026C020"+(String) newValue);					
						updatePreferenceDescription(p4, value);
					} catch (NumberFormatException e) {
					}
				}
				break;
			case "LAMP_TURN_DARK_TIME":
				if (settingActivity != null) {
					try {
						int value = Integer.parseInt((String) newValue);						
						settingActivity.sendMsg("5AA5026C010"+(String) newValue);					
						updatePreferenceDescription(p5, value);
					} catch (NumberFormatException e) {
					}
				}
				break;
			/*---------lamp ------*/
		    /*----------remote cotrol------*/
			case "REMOTELOCK_BEEP_SIGN":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5026B04"
							+ ((boolean) newValue ? "01" : "00"));
				}
				break;
			case "REMOTELOCK_SIDELAMP_SIGN":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5026B03"
							+ ((boolean) newValue ? "01" : "00"));
				}
				break;
			case "SPEECH_WARING_VOLUME":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5026B02"
							+ ((boolean) newValue ? "01" : "00"));
				}
				break;
			case "REMOTE_START_SYSTEM":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5026B01"
							+ ((boolean) newValue ? "01" : "00"));
				}
				break;
		    /*----------remote cotrol------*/
			/*----------lock------*/
			case "LOCK_PERSONAL_SETTING":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5026D03"
							+ ((boolean) newValue ? "01" : "00"));
				}
				break;
			case "AUTO_LOCK_TIME":
				if (settingActivity != null) {
					try {
						int value = Integer.parseInt((String) newValue);						
						settingActivity.sendMsg("5AA5026D020"+(String) newValue);					
						updatePreferenceDescription(p11, value);
					} catch (NumberFormatException e) {
					}
				}
				break;	
			case "REMOTE_LOCK_SIGN":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5026D01"
							+ ((boolean) newValue ? "01" : "00"));
				}
				break;
		    /*----------adas------*/
			case "LANE_DEPARTURE":
				if (settingActivity != null) {
					try {
						int value = Integer.parseInt((String) newValue);						
						settingActivity.sendMsg("5AA5026E040"+(String) newValue);					
						updatePreferenceDescription(p13, value);
					} catch (NumberFormatException e) {
					}
				}
				break;
			case "PAUSE_LKAS_SIGN":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5026E03"
							+ ((boolean) newValue ? "01" : "00"));
				}
				break;
			case "DETECT_FRONT_CAR":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5026E02"
							+ ((boolean) newValue ? "01" : "00"));
				}
				break;
			case "FRONT_DANGER_WAIRNG_DISTANCE":
				if (settingActivity != null) {
					try {
						int value = Integer.parseInt((String) newValue);						
						settingActivity.sendMsg("5AA5026E010"+(String) newValue);					
						updatePreferenceDescription(p16, value);
					} catch (NumberFormatException e) {
					}
				}
				break;			
		    /*----------adas------*/
			default:
				break;
			}
			return true;
		}
	}
}
