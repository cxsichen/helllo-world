package com.console.canreader.fragment.SSJeepFreedom;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;
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
import com.console.canreader.view.StepPreference;
import com.console.canreader.view.StepPreference.OnStepPreferenceClickListener;

public class LightEngineeFragment extends BaseFragment {

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
	OnStepPreferenceClickListener,OnPreferenceChangeListener {

		LightEngineeFragment settingActivity;
		StepPreference mStepPreference;
		PreferenceGroup mPreferenceGroup;
		List<SwitchPreference> mSwitchPreferenceGroup = new ArrayList<SwitchPreference>();
		List<Integer> mSwitchValueGroup = new ArrayList<Integer>();
		String[] swPreKey = { "CHANGE_ILL_STATUS","DAYTIME_LAMP_STATUS_ENABLE","CHANGE_ILL_STATUS_ENABLE",
				"ESP_ENABLE","HOLOGRAM_ENABLE","CAT_SETTTING_ENABLE"};
		String[] swPreMsg = { "5AA502630A","5AA5026309","5AA5026306",
				"5AA5026305","5AA5026304","5AA5026303"};

		List<ListPreference> mListPreferenceGroup = new ArrayList<ListPreference>();
		List<Integer> mListValueGroup = new ArrayList<Integer>();
		String[] listPreKey = {"ATMOSPHERE_ILL_STATUS","GO_HOME_LAMP_STATUS_ENABLE",
				"WELCOME_PERSION_ILL_STATUS_ENABLE","ATMOSPHERE_ILL_STATUS_ENABLE"};
		String[] listPreMsg = {"5AA502630B","5AA5026307",
				"5AA5026302","5AA5026301"};

		public SettingsFragment(LightEngineeFragment settingActivity) {
			this.settingActivity = settingActivity;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.ssfr_le_setting_prefs);

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
			mPreferenceGroup=(PreferenceGroup) findPreference("ATMOSPHERE_ILL_VALUE_P");
			mStepPreference = (StepPreference) findPreference("ATMOSPHERE_ILL_VALUE");
			mStepPreference.setMax(3);
			mStepPreference.setMin(1);
			mStepPreference.setOnStepPreferenceClickListener(this);

			if (settingActivity != null) {
				if (settingActivity.getCanInfo() != null)
					syncView(settingActivity.getCanInfo());
			}
		}
	

		public void syncView(CanInfo mCaninfo) {
			if(mCaninfo.ATMOSPHERE_ILL_VALUE==-1){
				getPreferenceScreen().removePreference(mPreferenceGroup);
				//((PreferenceGroup) findPreference("ATMOSPHERE_ILL_VALUE_P")).removePreference(mStepPreference);
			}else{
				getPreferenceScreen().addPreference(mPreferenceGroup);
				//((PreferenceGroup) findPreference("ATMOSPHERE_ILL_VALUE_P")).addPreference(mStepPreference);
				mStepPreference.setFreqTv(String.valueOf(mCaninfo.ATMOSPHERE_ILL_VALUE));
			}
			
			
			mSwitchValueGroup.clear();
			mSwitchValueGroup.add(mCaninfo.CHANGE_ILL_STATUS);
			mSwitchValueGroup.add(mCaninfo.DAYTIME_LAMP_STATUS_ENABLE);
			
			mSwitchValueGroup.add(mCaninfo.CHANGE_ILL_STATUS_ENABLE);
			mSwitchValueGroup.add(mCaninfo.ESP_ENABLE);
			mSwitchValueGroup.add(mCaninfo.HOLOGRAM_ENABLE);
			mSwitchValueGroup.add(mCaninfo.CAT_SETTTING_ENABLE);

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
			mListValueGroup.add(mCaninfo.ATMOSPHERE_ILL_STATUS);
			mListValueGroup.add(mCaninfo.GO_HOME_LAMP_STATUS_ENABLE);
			mListValueGroup.add(mCaninfo.WELCOME_PERSION_ILL_STATUS_ENABLE);
			mListValueGroup.add(mCaninfo.ATMOSPHERE_ILL_STATUS_ENABLE);
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
							settingActivity.sendMsg(listPreMsg[i]
									+ BytesUtil.changIntHex(value));
							updatePreferenceDescription(
									mListPreferenceGroup.get(i), value);
						} catch (NumberFormatException e) {
						}
					}
				}
			}
			return true;
		}

		@Override
		public void onPreButtonClick(Preference preference) {
			// TODO Auto-generated method stub
			if (settingActivity != null) {
				settingActivity.sendMsg("5AA5026308FF");
			}
			mStepPreference.setFreqTv(String.valueOf((Integer
					.parseInt(mStepPreference.getFreqTv()) - 1)));
		}

		@Override
		public void onNextButtonClick(Preference preference) {
			// TODO Auto-generated method stub
			if (settingActivity != null) {
				settingActivity.sendMsg("5AA502630801");
			}
			mStepPreference.setFreqTv(String.valueOf((Integer
					.parseInt(mStepPreference.getFreqTv())+ 1)));
			
		}
	}

}
