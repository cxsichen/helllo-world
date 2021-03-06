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

public class TrumpchiGS5CarInfoFragment extends BaseFragment {

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

		private Preference Park_setting;
		private Preference REV_setting;
		private Preference ILL_setting;
		private Preference safety_setting;

		TrumpchiGS5CarInfoFragment settingActivity;

		public SettingsFragment(TrumpchiGS5CarInfoFragment settingActivity) {
			this.settingActivity = settingActivity;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.trumpchigs5_info_prefs);
			initView();
			if (settingActivity != null) {
				if (settingActivity.getCanInfo() != null)
					syncView(settingActivity.getCanInfo());
			}
		}

		private void initView() {
			Park_setting = findPreference("Park_setting");
			REV_setting = findPreference("REV_setting");
			ILL_setting = findPreference("ILL_setting");
			safety_setting = findPreference("safety_setting");
		}

		public void syncView(CanInfo mCanInfo) {
			Park_setting
					.setTitle(mCanInfo.HANDBRAKE_STATUS == 0 ? R.string.car_info_brake_down
							: R.string.car_info_brake_pull);
			REV_setting
					.setTitle(mCanInfo.CAR_BACK_STATUS == 0 ? R.string.car_info_REV_no
							: R.string.car_info_REV_yes);
			ILL_setting
					.setTitle(mCanInfo.CAR_ILL_STATUS == 0 ? R.string.car_info_ILL_close
							: R.string.car_info_ILL_open);
			safety_setting
					.setTitle(mCanInfo.SAFETY_BELT_STATUS == 0 ? R.string.car_info_safe_no
							: R.string.car_info_safe_yes);
		}

		@Override
		public boolean onPreferenceChange(Preference preference, Object newValue) {

			return false;
		}

	}

}
