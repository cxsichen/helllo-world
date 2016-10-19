package com.console.canreader.fragment.SSTrumpchi;

import android.os.Bundle;
import android.preference.EditTextPreference;
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
import com.console.canreader.utils.BytesUtil;

public class GS4HIGHLinkSOSFragment extends BaseFragment {

	private TextView Link_SOS;
	SettingsFragment settingsFragment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.trumpchegs4_linksos_prefs,
				container, false);
		initView(view);
		init();
		return view;
	}

	private void init() {

	}

	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		try {
		super.show(mCaninfo);

			if (mCaninfo != null) {
				switch (mCaninfo.LINK_SOS_STATUS) {
				case 0:
					Link_SOS.setText(R.string.communication_Link_SOS_0);
					break;

				case 1:
					Link_SOS.setText(R.string.communication_Link_SOS_1);
					break;
				case 2:
					Link_SOS.setText(R.string.communication_Link_SOS_2);
					break;

				default:
					Link_SOS.setText(R.string.communication_Link_SOS_0);
					break;
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
		Link_SOS = (TextView) view.findViewById(R.id.Link_SOS_status);
	}

}
