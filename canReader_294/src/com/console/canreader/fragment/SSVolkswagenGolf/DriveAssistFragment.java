package com.console.canreader.fragment.SSVolkswagenGolf;

import java.util.ArrayList;
import java.util.List;

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
import com.console.canreader.utils.BytesUtil;
import com.console.canreader.utils.PreferenceUtil;

public class DriveAssistFragment extends BaseFragment {

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

		DriveAssistFragment settingActivity;

		List<SwitchPreference> mSwitchPreferenceGroup = new ArrayList<SwitchPreference>();
		List<Integer> mSwitchValueGroup = new ArrayList<Integer>();
		List<ListPreference> mListPreferenceGroup = new ArrayList<ListPreference>();
		List<Integer> mListValueGroup = new ArrayList<Integer>();

		/*-----------set data----------------*/
		String[] swPreKey = { "LAST_DISTANCE_SELECTED","FRONT_ASSIST_ACTIVE","FRONT_ASSIST_ADVANCE_WARNING",
				 "FRONT_ASSIST_DISPLAY_DISTANCHE_WARNING","LANE_DEPARTURE","PAUSE_LKAS_SIGN",
				 "DETECT_FRONT_CAR","DRIVER_ALERT_SYSTEM"};
		String[] swPreMsg = { "5AA5024C0B","5AA5024C01","5AA5024C02",
				 "5AA5024C03","5AA5024C04","5AA5024C05",
				 "5AA5024C06","5AA5024C07",};

		String[] listPreKey = {"ACC_DRIVER_PROGRAM","ACC_DISTANCE"};
		String[] listPreMsg = {"5AA5024C09","5AA5024C08"};

		private void addListData(List<Integer> mListValueGroup2,
				CanInfo mCaninfo) {
			// TODO Auto-generated method stub
			
			  mListValueGroup2.add(mCaninfo.ACC_DRIVER_PROGRAM);
			  mListValueGroup2.add(mCaninfo.ACC_DISTANCE);
			 
		}

		private void addSwitchData(List<Integer> mSwitchValueGroup2,
				CanInfo mCaninfo) {
			// TODO Auto-generated method stub
			mSwitchValueGroup2.add(mCaninfo.LAST_DISTANCE_SELECTED);
			mSwitchValueGroup2.add(mCaninfo.FRONT_ASSIST_ACTIVE);
			mSwitchValueGroup2.add(mCaninfo.FRONT_ASSIST_ADVANCE_WARNING);
			mSwitchValueGroup2.add(mCaninfo.FRONT_ASSIST_DISPLAY_DISTANCHE_WARNING);
			mSwitchValueGroup2.add(mCaninfo.LANE_DEPARTURE);
			mSwitchValueGroup2.add(mCaninfo.PAUSE_LKAS_SIGN);
			mSwitchValueGroup2.add(mCaninfo.DETECT_FRONT_CAR);
			mSwitchValueGroup2.add(mCaninfo.DRIVER_ALERT_SYSTEM);

		}

		/*-----------set data----------------*/

		public SettingsFragment(DriveAssistFragment settingActivity) {
			this.settingActivity = settingActivity;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.ssgolf_setting_prefs_1);

			for (String str : swPreKey) {
				SwitchPreference p = (SwitchPreference) findPreference(str);
				p.setOnPreferenceChangeListener(this);
				mSwitchPreferenceGroup.add(p);
			}

			for (String str : listPreKey) {
				ListPreference p = (ListPreference) findPreference(str);
				p.setOnPreferenceChangeListener(this);
				mListPreferenceGroup.add(p);
			}

			if (settingActivity != null) {
				if (settingActivity.getCanInfo() != null)
					syncView(settingActivity.getCanInfo());
			}
		}

		public void syncView(CanInfo mCaninfo) {

			mSwitchValueGroup.clear();
			addSwitchData(mSwitchValueGroup, mCaninfo);

			for (int i = 0; i < mSwitchPreferenceGroup.size(); i++) {
				if (mSwitchValueGroup.get(i) == -1) {
					getPreferenceScreen().removePreference(
							mSwitchPreferenceGroup.get(i));
				} else {
					getPreferenceScreen().addPreference(
							mSwitchPreferenceGroup.get(i));
					mSwitchPreferenceGroup.get(i).setChecked(
							mSwitchValueGroup.get(i) == 1);
				}

			}

			mListValueGroup.clear();
			addListData(mListValueGroup, mCaninfo);
			for (int i = 0; i < mListPreferenceGroup.size(); i++) {
				if (mListValueGroup.get(i) == -1) {
					getPreferenceScreen().removePreference(
							mListPreferenceGroup.get(i));
				} else {
					getPreferenceScreen().addPreference(
							mListPreferenceGroup.get(i));
					updatePreferenceDescription(mListPreferenceGroup.get(i),
							mListValueGroup.get(i));
				}

			}
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
			for (int i = 0; i < swPreKey.length; i++) {
				if (key.equals(swPreKey[i])) {
					if (settingActivity != null)
						settingActivity.sendMsg(swPreMsg[i]
								+ ((boolean) newValue ? "01" : "00"));
				}
			}
			for (int i = 0; i < listPreKey.length; i++) {
				if (key.equals(listPreKey[i])) {
					if (settingActivity != null) {
						try {							
							int value = Integer.parseInt((String) newValue);
                            if(key.equals("ACC_DRIVER_PROGRAM")){
                            	settingActivity.sendMsg(listPreMsg[i]
										+ BytesUtil.changIntHex(value+1));
							}else if(key.equals("ACC_DISTANCE")){
                            	settingActivity.sendMsg(listPreMsg[i]
										+ BytesUtil.changIntHex(value+1));
							}else{
								settingActivity.sendMsg(listPreMsg[i]
										+ BytesUtil.changIntHex(value));
							}
							
							updatePreferenceDescription(
									mListPreferenceGroup.get(i), value);
						} catch (NumberFormatException e) {
						}
					}
				}
			}
			return true;
		}
	}

}
