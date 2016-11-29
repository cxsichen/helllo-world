package com.console.canreader.fragment.SSHonda12CRV;

import java.text.DecimalFormat;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.provider.Settings;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.console.canreader.R;
import com.console.canreader.activity.BaseFragment;
import com.console.canreader.activity.Toyota.OilEleActivity;
import com.console.canreader.activity.Toyota.SettingActivity;
import com.console.canreader.activity.Toyota.OilEleActivity.SettingsFragment;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.PreferenceUtil;

public class MediaFragment extends BaseFragment implements OnClickListener {

	TextView tv0;
	TextView tv1;
	TextView tv2;
	TextView tv3;
	TextView tv4;

	SeekBar sb1;

	ImageView iv1;
	ImageView iv2;
	ImageView iv3;
	ImageView iv4;
	String[] strGroup = { "", "暂停", "播放", "上一曲", "停止", "下一曲", "弹出", "加载", "无效" };
	public static final String APPLIST = "Console_applist";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.sshonda12crv_layout_1, container,
				false);
		initView(view);

		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Settings.System.putString(getActivity().getContentResolver(), APPLIST,
				"com.console.auxapp");
	}

	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		super.show(mCaninfo);
		if (mCaninfo != null) {
			int temp = 0;
			switch (mCaninfo.MULTI_MEIDA_PLAYING_STATUS) {
			case 0x06:
				temp = 4;
				break;
			case 0x09:
				temp = 5;
				break;
			case 0x0C:
				temp = 6;
				break;
			case 0x0D:
				temp = 7;
				break;
			default:
				temp = mCaninfo.MULTI_MEIDA_PLAYING_STATUS;
				break;
			}
			if (temp > 8)
				temp = 8;
			tv0.setText(strGroup[temp]);
			tv1.setText(mCaninfo.MULTI_MEIDA_SOURCE == 0x0D ? "USB"
					: mCaninfo.MULTI_MEIDA_SOURCE == 0x0E ? "IPOD" : "");
			tv2.setText(mCaninfo.MULTI_MEIDA_PLAYING_NUM + "");
			tv3.setText(mCaninfo.MULTI_MEIDA_WHOLE_NUM + "");
			tv4.setText(mCaninfo.MULTI_MEIDA_PLAYING_MINUTE + ":"
					+ mCaninfo.MULTI_MEIDA_PLAYING_SECOND);
			sb1.setProgress(mCaninfo.MULTI_MEIDA_PLAYING_PROGRESS);
		}
	}

	@Override
	public void serviceConnected() {
		// TODO Auto-generated method stub
		super.serviceConnected();
	}

	private void initView(View view) {
		tv0 = (TextView) view.findViewById(R.id.tv0);
		tv1 = (TextView) view.findViewById(R.id.tv1);
		tv2 = (TextView) view.findViewById(R.id.tv2);
		tv3 = (TextView) view.findViewById(R.id.tv3);
		tv4 = (TextView) view.findViewById(R.id.tv4);

		sb1 = (SeekBar) view.findViewById(R.id.sb1);

		iv1 = (ImageView) view.findViewById(R.id.iv1);
		iv2 = (ImageView) view.findViewById(R.id.iv2);
		iv3 = (ImageView) view.findViewById(R.id.iv3);
		iv4 = (ImageView) view.findViewById(R.id.iv4);

		iv1.setOnClickListener(this);
		iv2.setOnClickListener(this);
		iv3.setOnClickListener(this);
		iv4.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv1:
			sendMsg("AA5502AC0701");
			break;
		case R.id.iv2:
			sendMsg("AA5502AC0100");
			break;
		case R.id.iv3:
			sendMsg("AA5502AC0200");
			break;
		case R.id.iv4:
			sendMsg("AA5502AC0700");
			break;

		default:
			break;
		}
	}
}
