package com.console.canreader.fragment.SSToyota;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
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
import com.console.canreader.activity.Toyota.OilEleActivity.SettingsFragment;
import com.console.canreader.service.CanInfo;

public class OilEleFragment extends BaseFragment {

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

	public class SettingsFragment extends PreferenceFragment {

		Preference p1;
		Preference p2;
		Preference p3;
		Preference p4;
		Preference p5;
		Preference p6;
		Preference p7;
		Preference p8;

		OilEleFragment oilEleActivity;

		public SettingsFragment(OilEleFragment oilEleActivity) {
			this.oilEleActivity = oilEleActivity;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.oil_elc_info_prefs);
			p1 = (Preference) findPreference("IS_POWER_MIXING");
			p2 = (Preference) findPreference("BATTERY_LEVEL");
			p3 = (Preference) findPreference("MOTOR_DRIVE_BATTERY");
			p4 = (Preference) findPreference("MOTOR_DRIVE_WHEEL");
			p5 = (Preference) findPreference("ENGINE_DRIVE_MOTOR");
			p6 = (Preference) findPreference("ENGINE_DRIVE_WHEEL");
			p7 = (Preference) findPreference("BATTERY_DRIVE_MOTOR");
			p8 = (Preference) findPreference("WHEEL_DRIVE_MOTOR");
			
			if(oilEleActivity!=null){
				if(oilEleActivity.getCanInfo()!=null)
					syncView(oilEleActivity.getCanInfo());
			}

		}

		public void syncView(CanInfo mCaninfo) {
			p1.setSummary(((mCaninfo.IS_POWER_MIXING == 1) ? "是" : "否"));
			p2.setSummary(String.valueOf(mCaninfo.BATTERY_LEVEL));
			p3.setSummary(((mCaninfo.MOTOR_DRIVE_BATTERY == 1) ? "是" : "否"));
			p4.setSummary(((mCaninfo.MOTOR_DRIVE_WHEEL == 1) ? "是" : "否"));
			p5.setSummary(((mCaninfo.ENGINE_DRIVE_MOTOR == 1) ? "是" : "否"));
			p6.setSummary(((mCaninfo.ENGINE_DRIVE_WHEEL == 1) ? "是" : "否"));
			p7.setSummary(((mCaninfo.BATTERY_DRIVE_MOTOR == 1) ? "是" : "否"));
			p8.setSummary(((mCaninfo.WHEEL_DRIVE_MOTOR == 1) ? "是" : "否"));
		}

	}

}
