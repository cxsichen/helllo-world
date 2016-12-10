package com.console.canreader.fragment.SSFS;

import java.text.DecimalFormat;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.console.canreader.R;
import com.console.canreader.activity.BaseFragment;
import com.console.canreader.activity.Toyota.OilEleActivity;
import com.console.canreader.activity.Toyota.SettingActivity;
import com.console.canreader.activity.Toyota.OilEleActivity.SettingsFragment;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.PreferenceUtil;

public class CarTypeSettingsFragment extends BaseFragment {

	TextView tv;
	TextView tv1;
	SettingsFragment settingsFragment;
	private DecimalFormat df;
	ImageView speedHand;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.dashboard_main_3, container, false);
		initView(view);
		df = new DecimalFormat("###.00");
		return view;
	}

	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		super.show(mCaninfo);
		if (mCaninfo != null) {
			if (mCaninfo.ENGINE_SPEED != -1)
				tv.setText((int) mCaninfo.ENGINE_SPEED + " RPM");
			tv1.setText((int) mCaninfo.DRIVING_SPEED + "KM/H");
			speedHand.setRotation(1.125f * mCaninfo.DRIVING_SPEED);
		}
	}

	@Override
	public void serviceConnected() {
		// TODO Auto-generated method stub
		super.serviceConnected();
	}

	private void initView(View view) {
		tv = (TextView) view.findViewById(R.id.enginee);
		tv1 = (TextView) view.findViewById(R.id.driving_speed);
		TextView tv3=(TextView) view.findViewById(R.id.distance);
		tv3.setText("---");
		speedHand = (ImageView) view.findViewById(R.id.speed_hand);
	}

}
