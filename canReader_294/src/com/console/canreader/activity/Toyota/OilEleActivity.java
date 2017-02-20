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
import com.console.canreader.activity.BaseActivity;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.DensityUtils;
import com.console.canreader.utils.PreferenceUtil;
import com.console.canreader.utils.ViewPagerAdapter;
import com.console.canreader.view.ObdView;
import com.console.canreader.view.ViewPageFactory;

public class OilEleActivity extends BaseActivity {

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

	public class SettingsFragment extends PreferenceFragment {

		Preference p1;
		Preference p2;
		Preference p3;
		Preference p4;
		Preference p5;
		Preference p6;
		Preference p7;
		Preference p8;

		OilEleActivity oilEleActivity;

		public SettingsFragment(OilEleActivity oilEleActivity) {
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
