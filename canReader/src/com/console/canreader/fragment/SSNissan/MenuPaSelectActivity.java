package com.console.canreader.fragment.SSNissan;

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
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.console.canreader.R;
import com.console.canreader.activity.BaseActivity;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.DensityUtils;
import com.console.canreader.utils.PreferenceUtil;
import com.console.canreader.utils.ViewPagerAdapter;
import com.console.canreader.view.ObdView;
import com.console.canreader.view.SeekBarPreference;
import com.console.canreader.view.SeekBarPreference.OnSeekBarPrefsChangeListener;
import com.console.canreader.view.ViewPageFactory;
import com.console.canreader.view.VolumePreference;

public class MenuPaSelectActivity extends BaseActivity {

	SettingsFragment settingsFragment;
	private ActionBar actionBar;

	/*
	 * SeekBar seekbar; int car = 0; int progress=0;
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.ssnissan_equ_fragment_activity_layout);
		initFragment();
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		/*
		 * seekbar = (SeekBar) findViewById(R.id.seek); seekbar.setMax(10);
		 * seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
		 * 
		 * @Override public void onStopTrackingTouch(SeekBar seekBar) { // TODO
		 * Auto-generated method stub
		 * Log.i("cxs","======onStopTrackingTouch=="+progress);
		 * sendMsg("AA5502AD08"+adjustNum(progress-5)); }
		 * 
		 * @Override public void onStartTrackingTouch(SeekBar seekBar) { // TODO
		 * Auto-generated method stub
		 * 
		 * }
		 * 
		 * @Override public void onProgressChanged(SeekBar seekBar, int
		 * progress, boolean fromUser) { // TODO Auto-generated method stub
		 * MenuPaSelectActivity.this.progress=progress; } });
		 */
	}

	private String adjustNum(int num) {
		String tmp = Integer.toHexString(num).toUpperCase();
		if (tmp.length() > 2) {
			tmp = tmp.substring(tmp.length() - 2, tmp.length());
		} else if (tmp.length() == 1) {
			tmp = "0" + tmp;
		}
		return tmp;
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
				// seekbar.setProgress(((byte)mCaninfo.SURROND_VOLUME)+5);
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

	public class SettingsFragment extends PreferenceFragment implements
			OnPreferenceChangeListener {
		// private SwitchPreference mAirConPref;
		MenuPaSelectActivity settingActivity;
		private SeekBarPreference mSeekBarPreference1;
		int eqlValue = 0;

		public SettingsFragment(MenuPaSelectActivity settingActivity) {
			this.settingActivity = settingActivity;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.ssnissan_equal_prefs);
			mSeekBarPreference1 = (SeekBarPreference) findPreference("EQL_VOLUME");
			mSeekBarPreference1.setMax(40);
			mSeekBarPreference1
					.setOnSeekBarPrefsChangeListener(new OnSeekBarPrefsChangeListener() {

						@Override
						public void onStopTrackingTouch(String key,
								SeekBar seekBar) {
							// TODO Auto-generated method stub
							Log.i("cxs",
									"=====onStopTrackingTouch=====eqlValue=="
											+ eqlValue);
							settingActivity.sendMsg("AA5502AD01"
									+ adjustNum(0x01));
						}

						@Override
						public void onStartTrackingTouch(String key,
								SeekBar seekBar) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onProgressChanged(String key,
								SeekBar seekBar, int progress, boolean fromUser) {
							// TODO Auto-generated method stub
							eqlValue = progress;

						}
					});
			/*
			 * mAirConPref = (SwitchPreference) findPreference("EQL_MUTE");
			 * 
			 * mAirConPref.setOnPreferenceChangeListener(this);
			 */
		}

		public void syncView(CanInfo mCaninfo) {
			// mAirConPref.setChecked(mCaninfo.EQL_MUTE == 1);
			mSeekBarPreference1.setProgress(mCaninfo.EQL_VOLUME);
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
			case "EQL_MUTE":
				if (settingActivity != null) {
					settingActivity.sendMsg("5AA502AD07"
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
