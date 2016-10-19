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

public class AirConSettingsFragment extends BaseFragment implements
		OnTouchListener {

	Button button1;
	Button button2;
	Button button3;
	Button button4;
	SettingsFragment settingsFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.sshonda_aircon_layout, container,
				false);
		initView(view);
		initFragment();
		return view;
	}

	private void initFragment() {
		// TODO Auto-generated method stub
		settingsFragment = new SettingsFragment(this);
		getActivity().getFragmentManager().beginTransaction()
				.replace(R.id.content_layout_air, settingsFragment).commit();
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
		button1 = (Button) view.findViewById(R.id.button1);
		button2 = (Button) view.findViewById(R.id.button2);
		button3 = (Button) view.findViewById(R.id.button3);
		button4 = (Button) view.findViewById(R.id.button4);

		button1.setOnTouchListener(this);
		button2.setOnTouchListener(this);
		button3.setOnTouchListener(this);
		button4.setOnTouchListener(this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				sendMsg("5AA5023D0901");
				break;
			case MotionEvent.ACTION_UP:
				sendMsg("5AA5023D0900");
				break;
			default:
				break;
			}
			break;
		case R.id.button2:
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				sendMsg("5AA5023D0A01");
				break;
			case MotionEvent.ACTION_UP:
				sendMsg("5AA5023D0A00");
				break;
			default:
				break;
			}
			break;
		case R.id.button3:
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				sendMsg("5AA5023D1701");
				break;
			case MotionEvent.ACTION_UP:
				sendMsg("5AA5023D1700");
				break;
			default:
				break;
			}
			break;
		case R.id.button4:
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				sendMsg("5AA5023D1801");
				break;
			case MotionEvent.ACTION_UP:
				sendMsg("5AA5023D1800");
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
		return true;
	}

	public class SettingsFragment extends PreferenceFragment implements
			OnPreferenceChangeListener {

		private SwitchPreference p1;
		private ListPreference p2;

		AirConSettingsFragment settingActivity;

		public SettingsFragment(AirConSettingsFragment settingActivity) {
			this.settingActivity = settingActivity;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.sshonda_aircon_setting_prefs);

			p1 = (SwitchPreference) findPreference("AC_INDICATOR_STATUS");
			p1.setOnPreferenceChangeListener(this);

			p2 = (ListPreference) findPreference("AIR_RATE");
			p2.setOnPreferenceChangeListener(this);
			
			if(settingActivity!=null){
				if(settingActivity.getCanInfo()!=null)
					syncView(settingActivity.getCanInfo());
			}
		}

		public void syncView(CanInfo mCaninfo) {

			p1.setChecked(mCaninfo.AC_INDICATOR_STATUS == 1);

			p2.setValue(String.valueOf(mCaninfo.AIR_RATE));
			updatePreferenceDescription(p2, mCaninfo.AIR_RATE);
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
			case "AC_INDICATOR_STATUS":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA5023D02"
							+ ((boolean) newValue ? "01" : "00"));
				}
				break;
			case "AIR_RATE":
				if (settingActivity != null) {
					try {
						int value = Integer.parseInt((String) newValue);
		                settingActivity.sendMsg("5AA5023D190"+String.valueOf(value));
		                updatePreferenceDescription(p2,value);
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
