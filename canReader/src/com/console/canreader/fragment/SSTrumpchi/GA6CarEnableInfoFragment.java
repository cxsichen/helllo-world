package com.console.canreader.fragment.SSTrumpchi;

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

public class GA6CarEnableInfoFragment extends BaseFragment {

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

		private Preference ESP_enable;
		private Preference panel_enable;
		private Preference AIR_enable;

		GA6CarEnableInfoFragment settingActivity;

		public SettingsFragment(GA6CarEnableInfoFragment settingActivity) {
			this.settingActivity = settingActivity;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.trumpchiga6_enable_prefs);
			initView();
			
			if(settingActivity!=null){
				if(settingActivity.getCanInfo()!=null)
					syncView(settingActivity.getCanInfo());
			}
		}

		private void initView() {
			ESP_enable = findPreference("ESP_enable");
			panel_enable = findPreference("panel_enable");
			AIR_enable = findPreference("AIR_enable");
		}

		public void syncView(CanInfo mCanInfo) {
			ESP_enable
					.setSummary(mCanInfo.ESP_ENABLE == 0 ? R.string.car_info_unable
							: R.string.car_info_enable);
			panel_enable
					.setSummary(mCanInfo.PANEL_SSETTING_ENABLE == 0 ? R.string.car_info_unable
							: R.string.car_info_enable);
			AIR_enable
					.setSummary(mCanInfo.AIR_INFO_ENABLE == 0 ? R.string.car_info_unable
							: R.string.car_info_enable);
		}

		@Override
		public boolean onPreferenceChange(Preference preference, Object newValue) {

			return false;
		}

	}

}
