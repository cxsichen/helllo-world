package com.console.canreader.fragment.SSJeepZNZ17;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.content.Intent;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

import com.console.canreader.R;
import com.console.canreader.activity.BaseActivity;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.DensityUtils;
import com.console.canreader.utils.PreferenceUtil;
import com.console.canreader.utils.ViewPagerAdapter;
import com.console.canreader.view.ObdView;
import com.console.canreader.view.ViewPageFactory;
import com.console.canreader.view.VolumePreference;

public class MenuPaSelectActivity extends BaseActivity implements
		View.OnClickListener {

	private TextView version;
	SettingsFragment settingsFragment;
	private ActionBar actionBar;
	private ImageView pre_button;
	private TextView freq_tv;
	private ImageView next_button;

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
		actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.equ_fragment_activity_layout);
		initFragment();
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		pre_button = (ImageView) findViewById(R.id.pre_button);
		freq_tv = (TextView) findViewById(R.id.freq_tv);
		next_button = (ImageView) findViewById(R.id.next_button);

		pre_button.setOnClickListener(this);
		next_button.setOnClickListener(this);
	}

	private void initFragment() {
		// TODO Auto-generated method stub
		settingsFragment = new SettingsFragment(this);
		getFragmentManager().beginTransaction()
				.replace(R.id.content_layout, settingsFragment).commit();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(MenuPaSelectActivity.this, MenuPaAcitivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.fade_in, R.anim.left_out);
		finish();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
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
		try {
			if (mCaninfo != null) {
				freq_tv.setText(mCaninfo.EQL_VOLUME + "");
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(mCaninfo==null){
			return;
		}
		switch (v.getId()) {
		case R.id.pre_button:
			int i=mCaninfo.EQL_VOLUME-1;
			if(i<0){
				i=0;
			}else if(i>38){
				i=38;
			}
			sendMsg("5AA502AD01"+BytesUtil.intToHexString(i));
			break;
		case R.id.next_button:
			int i1=mCaninfo.EQL_VOLUME+1;
			if(i1<0){
				i=0;
			}else if(i1>38){
				i=38;
			}
			sendMsg("5AA502AD01"+BytesUtil.intToHexString(i1));
			break;

		default:
			break;
		}
	}

	public class SettingsFragment extends PreferenceFragment implements
			OnPreferenceChangeListener {
		private ListPreference mAirConPref;
		private SwitchPreference mCyclePref;

		MenuPaSelectActivity settingActivity;

		public SettingsFragment(MenuPaSelectActivity settingActivity) {
			this.settingActivity = settingActivity;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.ssfr_equal_prefs);
			mAirConPref = (ListPreference) findPreference("VOL_LINK_CARSPEED");
			mCyclePref = (SwitchPreference) findPreference("DSP_SURROUND");

			mAirConPref.setOnPreferenceChangeListener(this);
			mCyclePref.setOnPreferenceChangeListener(this);

		}

		public void syncView(CanInfo mCaninfo) {
			updatePreferenceDescription(mAirConPref,
					mCaninfo.VOL_LINK_CARSPEED);
			mCyclePref.setChecked(mCaninfo.DSP_SURROUND == 1);

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
			switch (key) {
			case "VOL_LINK_CARSPEED":
				if (settingActivity != null) {
					try {
						int value = Integer.parseInt((String) newValue);
						settingActivity.sendMsg("5AA502AD07"
								+ BytesUtil.changIntHex(value));
						updatePreferenceDescription(mAirConPref,
								value);
					} catch (NumberFormatException e) {
					}
				}
				break;
			case "DSP_SURROUND":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA502AD08"
							+ ((boolean) newValue ? "01" : "00"));
				}
				break;
			default:
				break;
			}
			return true;
		}

	}

}
