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

public class TrumpchiCarSettingsFragment extends BaseFragment {

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

		private ListPreference car_info_LANGUAGE_CHANGE;
		private ListPreference car_info_AIR_COMFORTABLE_STATUS;
		private ListPreference car_info_WARNING_VOLUME;
		private ListPreference car_info_DRIVER_CHANGE_MODE;
		private ListPreference car_info_REMOTE_UNLOCK;
		private ListPreference car_info_GO_HOME_LAMP_STATUS;
		private ListPreference car_info_AUTO_LAMP_STATUS;
		
		
		
		private SwitchPreference car_info_AUTO_COMPRESSOR_STATUS;
		private SwitchPreference car_info_AUTO_CYCLE_STATUS;
		private SwitchPreference car_info_AIR_ANION_STATUS;
		private SwitchPreference car_info_DRIVING_POSITION_SETTING;
		private SwitchPreference car_info_DEPUTY_DRIVING_POSITION_SETTING;
		private SwitchPreference car_info_POSITION_WELCOME_SETTING;
		private SwitchPreference car_info_KEY_INTELLIGENCE;
		private SwitchPreference car_info_SPEED_LOCK;
		private SwitchPreference car_info_AUTO_UNLOCK;
		private SwitchPreference car_info_REMOTE_FRONT_LEFT;
		private SwitchPreference car_info_FRONT_WIPER_CARE;
		private SwitchPreference car_info_REAR_WIPER_STATUS;
		private SwitchPreference car_info_OUTSIDE_MIRROR_STATUS;
		private SwitchPreference car_info_FOG_LAMP_STATUS;
		private SwitchPreference car_info_DAYTIME_LAMP_STATUS;
		
		
		private EditTextPreference car_info_SPEED_OVER_SETTING;
		private EditTextPreference car_info_REMOTE_POWER_TIME;
		private EditTextPreference car_info_REMOTE_START_TIME;
		
		
		TrumpchiCarSettingsFragment settingActivity;

		public SettingsFragment(TrumpchiCarSettingsFragment settingActivity) {
			this.settingActivity = settingActivity;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.trumpchiga6_setting_prefs);
			initView();
		}

		private void initView() {
			car_info_LANGUAGE_CHANGE=(ListPreference) findPreference("car_info_LANGUAGE_CHANGE");
			car_info_AIR_COMFORTABLE_STATUS=(ListPreference) findPreference("car_info_AIR_COMFORTABLE_STATUS");
			car_info_WARNING_VOLUME=(ListPreference) findPreference("car_info_WARNING_VOLUME");
			car_info_DRIVER_CHANGE_MODE=(ListPreference) findPreference("car_info_DRIVER_CHANGE_MODE");
			car_info_REMOTE_UNLOCK=(ListPreference) findPreference("car_info_REMOTE_UNLOCK");
			car_info_GO_HOME_LAMP_STATUS=(ListPreference) findPreference("car_info_GO_HOME_LAMP_STATUS");
			car_info_AUTO_LAMP_STATUS=(ListPreference) findPreference("car_info_AUTO_LAMP_STATUS");
			
			
			car_info_AUTO_COMPRESSOR_STATUS=(SwitchPreference) findPreference("car_info_AUTO_COMPRESSOR_STATUS");
			car_info_AUTO_CYCLE_STATUS=(SwitchPreference) findPreference("car_info_AUTO_CYCLE_STATUS");
			car_info_AIR_ANION_STATUS=(SwitchPreference) findPreference("car_info_AIR_ANION_STATUS");
			car_info_DRIVING_POSITION_SETTING=(SwitchPreference) findPreference("car_info_DRIVING_POSITION_SETTING");
			car_info_DEPUTY_DRIVING_POSITION_SETTING=(SwitchPreference) findPreference("car_info_DEPUTY_DRIVING_POSITION_SETTING");
			car_info_POSITION_WELCOME_SETTING=(SwitchPreference) findPreference("car_info_POSITION_WELCOME_SETTING");
			car_info_KEY_INTELLIGENCE=(SwitchPreference) findPreference("car_info_KEY_INTELLIGENCE");
			car_info_SPEED_LOCK=(SwitchPreference) findPreference("car_info_SPEED_LOCK");
			car_info_AUTO_UNLOCK=(SwitchPreference) findPreference("car_info_AUTO_UNLOCK");
			car_info_REMOTE_FRONT_LEFT=(SwitchPreference) findPreference("car_info_REMOTE_FRONT_LEFT");
			car_info_FRONT_WIPER_CARE=(SwitchPreference) findPreference("car_info_FRONT_WIPER_CARE");
			car_info_REAR_WIPER_STATUS=(SwitchPreference) findPreference("car_info_REAR_WIPER_STATUS");
			car_info_OUTSIDE_MIRROR_STATUS=(SwitchPreference) findPreference("car_info_OUTSIDE_MIRROR_STATUS");
			car_info_FOG_LAMP_STATUS=(SwitchPreference) findPreference("car_info_FOG_LAMP_STATUS");
			car_info_DAYTIME_LAMP_STATUS=(SwitchPreference) findPreference("car_info_DAYTIME_LAMP_STATUS");
			
			car_info_SPEED_OVER_SETTING=(EditTextPreference) findPreference("car_info_SPEED_OVER_SETTING");
			car_info_REMOTE_POWER_TIME=(EditTextPreference) findPreference("car_info_REMOTE_POWER_TIME");
			car_info_REMOTE_START_TIME=(EditTextPreference) findPreference("car_info_REMOTE_START_TIME");
			
			car_info_LANGUAGE_CHANGE.setOnPreferenceChangeListener(this);
			car_info_AIR_COMFORTABLE_STATUS.setOnPreferenceChangeListener(this);
			car_info_WARNING_VOLUME.setOnPreferenceChangeListener(this);
			car_info_DRIVER_CHANGE_MODE.setOnPreferenceChangeListener(this);
			car_info_REMOTE_UNLOCK.setOnPreferenceChangeListener(this);
			car_info_GO_HOME_LAMP_STATUS.setOnPreferenceChangeListener(this);
			car_info_AUTO_LAMP_STATUS.setOnPreferenceChangeListener(this);
			
			car_info_AUTO_COMPRESSOR_STATUS.setOnPreferenceChangeListener(this);
			car_info_AUTO_CYCLE_STATUS.setOnPreferenceChangeListener(this);
			car_info_AIR_ANION_STATUS.setOnPreferenceChangeListener(this);
			car_info_DRIVING_POSITION_SETTING.setOnPreferenceChangeListener(this);
			car_info_DEPUTY_DRIVING_POSITION_SETTING.setOnPreferenceChangeListener(this);
			car_info_POSITION_WELCOME_SETTING.setOnPreferenceChangeListener(this);
			car_info_KEY_INTELLIGENCE.setOnPreferenceChangeListener(this);
			car_info_SPEED_LOCK.setOnPreferenceChangeListener(this);
			car_info_AUTO_UNLOCK.setOnPreferenceChangeListener(this);
			car_info_REMOTE_FRONT_LEFT.setOnPreferenceChangeListener(this);
			car_info_FRONT_WIPER_CARE.setOnPreferenceChangeListener(this);
			car_info_REAR_WIPER_STATUS.setOnPreferenceChangeListener(this);
			car_info_OUTSIDE_MIRROR_STATUS.setOnPreferenceChangeListener(this);
			car_info_FOG_LAMP_STATUS.setOnPreferenceChangeListener(this);
			car_info_DAYTIME_LAMP_STATUS.setOnPreferenceChangeListener(this);
			
			car_info_SPEED_OVER_SETTING.setOnPreferenceChangeListener(this);
			car_info_REMOTE_POWER_TIME.setOnPreferenceChangeListener(this);
			car_info_REMOTE_START_TIME.setOnPreferenceChangeListener(this);
			
		}

		public void syncView(CanInfo mCaninfo) {
			CharSequence[] a=car_info_LANGUAGE_CHANGE.getEntries();
			car_info_LANGUAGE_CHANGE.setSummary(mCaninfo.LANGUAGE_CHANGE==1?a[0].toString():a[1].toString());
			 a=car_info_AIR_COMFORTABLE_STATUS.getEntries();
			car_info_AIR_COMFORTABLE_STATUS.setSummary(a[mCaninfo.AIR_COMFORTABLE_STATUS].toString());
			 a=car_info_WARNING_VOLUME.getEntries();
			car_info_WARNING_VOLUME.setSummary(a[mCaninfo.WARNING_VOLUME].toString());
			a=car_info_DRIVER_CHANGE_MODE.getEntries();
			car_info_DRIVER_CHANGE_MODE.setSummary(a[mCaninfo.DRIVER_CHANGE_MODE].toString());
			a=car_info_REMOTE_UNLOCK.getEntries();
			car_info_REMOTE_UNLOCK.setSummary(a[mCaninfo.REMOTE_UNLOCK].toString());
			a=car_info_GO_HOME_LAMP_STATUS.getEntries();
			car_info_GO_HOME_LAMP_STATUS.setSummary(a[mCaninfo.GO_HOME_LAMP_STATUS].toString());
			a=car_info_AUTO_LAMP_STATUS.getEntries();
			car_info_AUTO_LAMP_STATUS.setSummary(a[mCaninfo.AUTO_LAMP_STATUS].toString());
			
			car_info_AUTO_COMPRESSOR_STATUS.setChecked(mCaninfo.AUTO_COMPRESSOR_STATUS==1?true:false);
			car_info_AUTO_CYCLE_STATUS.setChecked(mCaninfo.AUTO_CYCLE_STATUS==1?true:false);
			car_info_AIR_ANION_STATUS.setChecked(mCaninfo.AIR_ANION_STATUS==1?true:false);
			car_info_DRIVING_POSITION_SETTING.setChecked(mCaninfo.DRIVING_POSITION_SETTING==1?true:false);
			car_info_DEPUTY_DRIVING_POSITION_SETTING.setChecked(mCaninfo.DEPUTY_DRIVING_POSITION_SETTING==1?true:false);
			car_info_POSITION_WELCOME_SETTING.setChecked(mCaninfo.POSITION_WELCOME_SETTING==1?true:false);
			car_info_KEY_INTELLIGENCE.setChecked(mCaninfo.KEY_INTELLIGENCE==1?true:false);
			car_info_SPEED_LOCK.setChecked(mCaninfo.SPEED_LOCK==1?true:false);
			car_info_AUTO_UNLOCK.setChecked(mCaninfo.AUTO_UNLOCK==1?true:false);
			car_info_REMOTE_FRONT_LEFT.setChecked(mCaninfo.REMOTE_FRONT_LEFT==1?true:false);
			car_info_FRONT_WIPER_CARE.setChecked(mCaninfo.FRONT_WIPER_CARE==1?true:false);
			car_info_REAR_WIPER_STATUS.setChecked(mCaninfo.REAR_WIPER_STATUS==1?true:false);
			car_info_OUTSIDE_MIRROR_STATUS.setChecked(mCaninfo.OUTSIDE_MIRROR_STATUS==1?true:false);
			car_info_FOG_LAMP_STATUS.setChecked(mCaninfo.FOG_LAMP_STATUS==1?true:false);
			car_info_DAYTIME_LAMP_STATUS.setChecked(mCaninfo.DAYTIME_LAMP_STATUS==1?true:false);
			
			String b="";
			b=String.valueOf(mCaninfo.SPEED_OVER_SETTING)+getString(R.string.str_kmh);
			car_info_SPEED_OVER_SETTING.setSummary(b);
			b=String.valueOf(mCaninfo.REMOTE_POWER_TIME)+getString(R.string.str_minute);
			car_info_REMOTE_POWER_TIME.setSummary(b);
			b=String.valueOf(mCaninfo.REMOTE_START_TIME)+getString(R.string.str_minute);
			car_info_REMOTE_START_TIME.setSummary(b);
			
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
			Log.i("cxs","==onPreferenceChange-key==="+key);
			switch (key) {
			case "car_info_LANGUAGE_CHANGE":
				if (settingActivity != null) {
					int value = Integer.parseInt((String) newValue);
					settingActivity.sendMsg("5AA5029B01"+(value==1?"00":"01"));
				}
				break;
			case "car_info_AUTO_COMPRESSOR_STATUS":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5029B02"+((boolean) newValue ?"01":"00"));
				}
				break;
			case "car_info_AUTO_CYCLE_STATUS":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5029B03"+((boolean) newValue ?"01":"00"));
				}
				break;
			case "car_info_AIR_COMFORTABLE_STATUS":
				if (settingActivity != null) {
					int value = Integer.parseInt((String) newValue);
					settingActivity.sendMsg("5AA5029B04"+BytesUtil.intToHexString(value));
				}
				break;
			case "car_info_AIR_ANION_STATUS":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5029B16"+((boolean) newValue ?"01":"00"));
				}
				break;
			case "car_info_DRIVING_POSITION_SETTING":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5029B05"+((boolean) newValue ?"01":"00"));
				}
				break;
			case "car_info_DEPUTY_DRIVING_POSITION_SETTING":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5029B06"+((boolean) newValue ?"01":"00"));
				}
				break;
			case "car_info_POSITION_WELCOME_SETTING":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5029B17"+((boolean) newValue ?"01":"00"));
				}
				break;
			case "car_info_KEY_INTELLIGENCE":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5029B18"+((boolean) newValue ?"01":"00"));
				}
				break;
			case "car_info_SPEED_OVER_SETTING":
				if (settingActivity != null) {
					int value = Integer.parseInt((String) newValue);
					settingActivity.sendMsg("5AA5029B07"+BytesUtil.intToHexString(value/10));
				}
				break;
			case "car_info_WARNING_VOLUME":
				if (settingActivity != null) {
					int value = Integer.parseInt((String) newValue);
					settingActivity.sendMsg("5AA5029B08"+BytesUtil.intToHexString(value));
				}
				break;
			case "car_info_REMOTE_POWER_TIME":
				if (settingActivity != null) {
					int value = Integer.parseInt((String) newValue);
					settingActivity.sendMsg("5AA5029B09"+BytesUtil.intToHexString(value));
				}
				break;
			case "car_info_REMOTE_START_TIME":
				if (settingActivity != null) {
					int value = Integer.parseInt((String) newValue);
					settingActivity.sendMsg("5AA5029B0A"+BytesUtil.intToHexString(value));
				}

				break;
			case "car_info_DRIVER_CHANGE_MODE":
				if (settingActivity != null) {
					int value = Integer.parseInt((String) newValue);
					settingActivity.sendMsg("5AA5029B0B"+BytesUtil.intToHexString(value));
				}
				break;
			case "car_info_REMOTE_UNLOCK":
				if (settingActivity != null) {
					int value = Integer.parseInt((String) newValue);
					settingActivity.sendMsg("5AA5029B0C"+BytesUtil.intToHexString(value));
				}
				break;
			case "car_info_SPEED_LOCK":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5029B0D"+((boolean) newValue ?"01":"00"));
				}
				break;
			case "car_info_AUTO_UNLOCK":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5029B0E"+((boolean) newValue ?"01":"00"));
				}
				break;
			case "car_info_REMOTE_FRONT_LEFT":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5029B0F"+((boolean) newValue ?"01":"00"));
				}
				break;	
				
			case "car_info_FRONT_WIPER_CARE":///
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5029B10"+((boolean) newValue ?"01":"00"));
				}
				break;	
			case "car_info_REAR_WIPER_STATUS":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5029B11"+((boolean) newValue ?"01":"00"));
				}
				break;	
			case "car_info_OUTSIDE_MIRROR_STATUS":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5029B19"+((boolean) newValue ?"01":"00"));
				}
				break;
			case "car_info_GO_HOME_LAMP_STATUS":
				if (settingActivity != null) {
					int value = Integer.parseInt((String) newValue);
					settingActivity.sendMsg("5AA5029B12"+BytesUtil.intToHexString(value));
				}
				break;
			case "car_info_FOG_LAMP_STATUS":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5029B13"+((boolean) newValue ?"01":"00"));
				}
				break;
			case "car_info_DAYTIME_LAMP_STATUS":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5029B14"+((boolean) newValue ?"01":"00"));
				}
				break;
			case "car_info_AUTO_LAMP_STATUS":///
				if (settingActivity != null) {
					int value = Integer.parseInt((String) newValue);
					settingActivity.sendMsg("5AA5029B15"+BytesUtil.intToHexString(value));
				}
				break;
				
			default:
				break;
			}
			return true;
		}

	}

}
