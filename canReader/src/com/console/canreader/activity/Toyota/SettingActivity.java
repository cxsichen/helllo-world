package com.console.canreader.activity.Toyota;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

import com.console.canreader.R;
import com.console.canreader.activity.baseActivity;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.DensityUtils;
import com.console.canreader.utils.PreferenceUtil;
import com.console.canreader.utils.ViewPagerAdapter;
import com.console.canreader.view.ObdView;
import com.console.canreader.view.ViewPageFactory;

public class SettingActivity extends baseActivity {

	private TextView version;
	SettingsFragment settingsFragment;
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Contacts.MSG_GET_MSG:
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_activity_layout);
		initFragment();
	}

	private void initFragment() {
		// TODO Auto-generated method stub
		settingsFragment = new SettingsFragment(this);
		getFragmentManager().beginTransaction()
				.replace(R.id.content_layout, settingsFragment).commit();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// mHandler.sendEmptyMessageDelayed(Contacts.MSG_GET_MSG, 2000);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		super.show(mCaninfo);
		if (mCaninfo != null) {
           if(settingsFragment!=null){
        	   settingsFragment.syncView(mCaninfo);
           }
		}

	}

	@Override
	public void serviceConnected() {
		// TODO Auto-generated method stub
		super.serviceConnected();
	}

	public class SettingsFragment extends PreferenceFragment implements
			OnPreferenceChangeListener {
		private SwitchPreference mAirConPref;
		private SwitchPreference mCyclePref;
		private SwitchPreference mLampLockPref;
		private SwitchPreference mIntelligentPref;
		private ListPreference mAutoCapPref;
		SettingActivity settingActivity;
		
		public SettingsFragment(SettingActivity settingActivity){
			this.settingActivity=settingActivity;
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
	
		
		public void syncView(CanInfo mCaninfo){
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
