package com.console.canreader.fragment.SSToyota;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
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
import com.console.canreader.activity.Toyota.OilEleActivity.SettingsFragment;
import com.console.canreader.service.CanInfo;

public class RadarInfoFragment extends BaseFragment {

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

	public class SettingsFragment extends PreferenceFragment {

		Preference p1;
		Preference p2;
		Preference p3;
		Preference p4;
		RadarInfoFragment oilEleActivity;

		public SettingsFragment(RadarInfoFragment oilEleActivity) {
			this.oilEleActivity = oilEleActivity;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.radar_info_prefs);
			p1 = (Preference) findPreference("IS_RADAR_SHOW");
			p2 = (Preference) findPreference("RADAR_WARING_VOLUME");
			p3 = (Preference) findPreference("FRONT_RADAR_DISTANCE");
			p4 = (Preference) findPreference("BACK_RADAR_DISTANCE");
		}

		public void syncView(CanInfo mCaninfo) {
			p1.setSummary(((mCaninfo.IS_RADAR_SHOW == 1) ? "是" : "否"));
			p2.setSummary(String.valueOf(mCaninfo.RADAR_WARING_VOLUME));
			p3.setSummary(((mCaninfo.FRONT_RADAR_DISTANCE == 1) ? "1格" : (mCaninfo.FRONT_RADAR_DISTANCE == 2) ? "2格" : ""));
			p4.setSummary(((mCaninfo.BACK_RADAR_DISTANCE == 1) ? "1格" : (mCaninfo.BACK_RADAR_DISTANCE == 2) ? "2格" : ""));
		}

	}

}
