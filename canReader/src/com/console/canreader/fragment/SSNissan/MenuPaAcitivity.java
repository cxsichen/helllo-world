package com.console.canreader.fragment.SSNissan;

import com.console.canreader.R;
import com.console.canreader.activity.BaseActivity;
import com.console.canreader.fragment.SSNissan.MenuPaSelectActivity.SettingsFragment;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.PreferenceUtil;
import com.console.canreader.view.CarSelectedView;
import com.console.canreader.view.SeekBarPreference;
import com.console.canreader.view.CarSelectedView.OnPositionChangedListener;
import com.console.canreader.view.StepPreference;
import com.console.canreader.view.StepPreference.OnStepPreferenceClickListener;
import com.console.canreader.view.VerticalSeekBar;
import com.console.canreader.view.SeekBarPreference.OnSeekBarPrefsChangeListener;
import com.console.canreader.view.VerticalSeekBar.OnProgressChangedListener;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.SwitchPreference;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MenuPaAcitivity extends BaseActivity {

	SettingsFragment settingsFragment;
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {

			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ssnissan_equ_fragment_activity_layout);
		initFragment();
		initView();
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
	}

	private void initView() {
		// TODO Auto-generated method stub

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

	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		super.show(mCaninfo);
		try {
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

	public class SettingsFragment extends PreferenceFragment implements
			OnStepPreferenceClickListener, OnPreferenceChangeListener {
		// private SwitchPreference mAirConPref;
		MenuPaAcitivity settingActivity;
		StepPreference mStepPreference;
		StepPreference mStepPreference1;
		StepPreference mStepPreference2;

		StepPreference mStepPreference3;
		StepPreference mStepPreference4;
		StepPreference mStepPreference5;
		StepPreference mStepPreference6;
		StepPreference mStepPreference7;

		SwitchPreference mSwitchPreference1;
		SwitchPreference mSwitchPreference2;

		public SettingsFragment(MenuPaAcitivity settingActivity) {
			this.settingActivity = settingActivity;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.ssnissan_equal_prefs);
			mStepPreference = (StepPreference) findPreference("EQL_VOLUME");
			mStepPreference.setMax(40);
			mStepPreference.setMin(0);
			mStepPreference.setOnStepPreferenceClickListener(this);

			mStepPreference1 = (StepPreference) findPreference("LR_BALANCE");
			mStepPreference1.setMax(5);
			mStepPreference1.setMin(-5);
			mStepPreference1.setOnStepPreferenceClickListener(this);

			mStepPreference2 = (StepPreference) findPreference("FB_BALANCE");
			mStepPreference2.setMax(5);
			mStepPreference2.setMin(-5);
			mStepPreference2.setOnStepPreferenceClickListener(this);

			mStepPreference3 = (StepPreference) findPreference("BAS_VOLUME");
			mStepPreference3.setMax(5);
			mStepPreference3.setMin(-5);
			mStepPreference3.setOnStepPreferenceClickListener(this);

			mStepPreference4 = (StepPreference) findPreference("MID_VOLUME");
			mStepPreference4.setMax(5);
			mStepPreference4.setMin(-5);
			mStepPreference4.setOnStepPreferenceClickListener(this);

			mStepPreference5 = (StepPreference) findPreference("TRE_VOLUME");
			mStepPreference5.setMax(5);
			mStepPreference5.setMin(-5);
			mStepPreference5.setOnStepPreferenceClickListener(this);

			mStepPreference6 = (StepPreference) findPreference("SURROND_VOLUME");
			mStepPreference6.setMax(5);
			mStepPreference6.setMin(-5);
			mStepPreference6.setOnStepPreferenceClickListener(this);

			mStepPreference7 = (StepPreference) findPreference("VOL_LINK_CARSPEED");
			mStepPreference7.setMax(5);
			mStepPreference7.setMin(0);
			mStepPreference7.setOnStepPreferenceClickListener(this);

			mSwitchPreference1 = (SwitchPreference) findPreference("BOSE_CENTERPOINT");
			mSwitchPreference1.setOnPreferenceChangeListener(this);
			mSwitchPreference2 = (SwitchPreference) findPreference("SEAT_SOUND");
			mSwitchPreference2.setOnPreferenceChangeListener(this);

		}

		public void syncView(CanInfo mCaninfo) {
			mStepPreference.setFreqTv(String.valueOf(mCaninfo.EQL_VOLUME));
			mStepPreference1.setFreqTv(String
					.valueOf((byte) mCaninfo.LR_BALANCE));
			mStepPreference2.setFreqTv(String
					.valueOf((byte) mCaninfo.FB_BALANCE));
			mStepPreference3.setFreqTv(String
					.valueOf((byte) mCaninfo.BAS_VOLUME));
			mStepPreference4.setFreqTv(String
					.valueOf((byte) mCaninfo.MID_VOLUME));
			mStepPreference5.setFreqTv(String
					.valueOf((byte) mCaninfo.TRE_VOLUME));
			mStepPreference6.setFreqTv(String
					.valueOf((byte) mCaninfo.SURROND_VOLUME));
			mStepPreference7.setFreqTv(String
					.valueOf((byte) mCaninfo.VOL_LINK_CARSPEED));

			mSwitchPreference1.setChecked(mCaninfo.BOSE_CENTERPOINT == 1);
			mSwitchPreference2.setChecked(mCaninfo.SEAT_SOUND == 1);
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
			case "BOSE_CENTERPOINT":
				if (settingActivity != null) {
					settingActivity.sendMsg("AA5502AD09"
							+ ((boolean) newValue ? "01" : "00"));
				}
				break;
			case "SEAT_SOUND":
				if (settingActivity != null) {
					settingActivity.sendMsg("AA5502AD0A"
							+ ((boolean) newValue ? "01" : "00"));
				}
				break;
			default:
				break;
			}
			return true;
		}

		@Override
		public void onPreButtonClick(Preference preference) {
			// TODO Auto-generated method stub
			final String key = preference.getKey();
			switch (key) {
			case "EQL_VOLUME":
				if (settingActivity != null) {
					settingActivity.sendMsg("AA5502AD01ff");
				}
				mStepPreference.setFreqTv(String.valueOf((Integer
						.parseInt(mStepPreference.getFreqTv()) - 1)));
				break;
			case "LR_BALANCE":
				if (settingActivity != null) {
					settingActivity.sendMsg("AA5502AD02ff");
				}
				mStepPreference1.setFreqTv(String.valueOf((Integer
						.parseInt(mStepPreference1.getFreqTv()) - 1)));
				break;
			case "FB_BALANCE":
				if (settingActivity != null) {
					settingActivity.sendMsg("AA5502AD03ff");
				}
				mStepPreference2.setFreqTv(String.valueOf((Integer
						.parseInt(mStepPreference2.getFreqTv()) - 1)));
				break;
			case "BAS_VOLUME":
				if (settingActivity != null) {
					settingActivity.sendMsg("AA5502AD04ff");
				}
				mStepPreference3.setFreqTv(String.valueOf((Integer
						.parseInt(mStepPreference3.getFreqTv()) - 1)));
				break;
			case "MID_VOLUME":
				if (settingActivity != null) {
					settingActivity.sendMsg("AA5502AD05ff");
				}
				mStepPreference4.setFreqTv(String.valueOf((Integer
						.parseInt(mStepPreference4.getFreqTv()) - 1)));
				break;
			case "TRE_VOLUME":
				if (settingActivity != null) {
					settingActivity.sendMsg("AA5502AD06ff");
				}
				mStepPreference5.setFreqTv(String.valueOf((Integer
						.parseInt(mStepPreference5.getFreqTv()) - 1)));
				break;
			case "SURROND_VOLUME":
				if (settingActivity != null) {
					settingActivity.sendMsg("AA5502AD08ff");
				}
				mStepPreference6.setFreqTv(String.valueOf((Integer
						.parseInt(mStepPreference6.getFreqTv()) - 1)));
				break;
			case "VOL_LINK_CARSPEED":
				if (settingActivity != null) {
					settingActivity.sendMsg("AA5502AD07ff");
				}
				mStepPreference7.setFreqTv(String.valueOf((Integer
						.parseInt(mStepPreference7.getFreqTv()) - 1)));
				break;

			default:
				break;
			}
		}

		@Override
		public void onNextButtonClick(Preference preference) {
			// TODO Auto-generated method stub
			final String key = preference.getKey();
			switch (key) {
			case "EQL_VOLUME":
				if (settingActivity != null) {
					settingActivity.sendMsg("AA5502AD0101");
				}
				mStepPreference.setFreqTv(String.valueOf((Integer
						.parseInt(mStepPreference.getFreqTv()) + 1)));
				break;
			case "LR_BALANCE":
				if (settingActivity != null) {
					settingActivity.sendMsg("AA5502AD0201");
				}
				mStepPreference1.setFreqTv(String.valueOf((Integer
						.parseInt(mStepPreference1.getFreqTv()) + 1)));
				break;
			case "FB_BALANCE":
				if (settingActivity != null) {
					settingActivity.sendMsg("AA5502AD0301");
				}
				mStepPreference2.setFreqTv(String.valueOf((Integer
						.parseInt(mStepPreference2.getFreqTv()) + 1)));
				break;
			case "BAS_VOLUME":
				if (settingActivity != null) {
					settingActivity.sendMsg("AA5502AD0401");
				}
				mStepPreference3.setFreqTv(String.valueOf((Integer
						.parseInt(mStepPreference3.getFreqTv()) + 1)));
				break;
			case "MID_VOLUME":
				if (settingActivity != null) {
					settingActivity.sendMsg("AA5502AD0501");
				}
				mStepPreference4.setFreqTv(String.valueOf((Integer
						.parseInt(mStepPreference4.getFreqTv()) + 1)));
				break;
			case "TRE_VOLUME":
				if (settingActivity != null) {
					settingActivity.sendMsg("AA5502AD0601");
				}
				mStepPreference5.setFreqTv(String.valueOf((Integer
						.parseInt(mStepPreference5.getFreqTv()) + 1)));
				break;
			case "SURROND_VOLUME":
				if (settingActivity != null) {
					settingActivity.sendMsg("AA5502AD0801");
				}
				mStepPreference6.setFreqTv(String.valueOf((Integer
						.parseInt(mStepPreference6.getFreqTv()) + 1)));
				break;
			case "VOL_LINK_CARSPEED":
				if (settingActivity != null) {
					settingActivity.sendMsg("AA5502AD0701");
				}
				mStepPreference7.setFreqTv(String.valueOf((Integer
						.parseInt(mStepPreference7.getFreqTv()) + 1)));
				break;
			default:
				break;
			}
		}

	}

}
