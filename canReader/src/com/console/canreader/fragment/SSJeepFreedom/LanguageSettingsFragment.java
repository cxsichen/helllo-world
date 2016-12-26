package com.console.canreader.fragment.SSJeepFreedom;

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
import android.view.View.OnClickListener;
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

public class LanguageSettingsFragment extends BaseFragment implements
		OnClickListener {

	Button button1;
	Button button2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.sscherry_language, container,
				false);
		initView(view);
		return view;
	}

	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		super.show(mCaninfo);
	}

	@Override
	public void serviceConnected() {
		// TODO Auto-generated method stub
		super.serviceConnected();
	}

	private void initView(View view) {
		button1 = (Button) view.findViewById(R.id.sscherry_btn1);
		button2 = (Button) view.findViewById(R.id.sscherry_btn2);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.sscherry_btn1:
			sendMsg("5AA5029A0102");
			break;
		case R.id.sscherry_btn2:
			sendMsg("5AA5029A0101");
			break;
		default:
			break;
		}
	}

}
