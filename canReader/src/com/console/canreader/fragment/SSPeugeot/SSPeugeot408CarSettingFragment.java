package com.console.canreader.fragment.SSPeugeot;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.Switch;

import com.console.canreader.R;
import com.console.canreader.activity.BaseFragment;
import com.console.canreader.fragment.SSTrumpchi.TrumpchiCarSettingsFragment.SettingsFragment;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;

public class SSPeugeot408CarSettingFragment extends BaseFragment implements
		OnClickListener, OnItemSelectedListener {

	private CanInfo mCanInfo;
	private boolean IsSync = true;
	private Switch AUTO_PARK_CAR_STATUS;
	private Switch RADAR_ALARM_STATUS;
	private Switch REAR_WIPER_STATUS;
	private Switch PARKING_STATUS;
	private Switch CAR_LOCK_AUTO_STATUS;
	private Switch CAR_LOCK_STATUS;
	private Switch DAYTIME_LAMP_STATUS;
	private Switch ATMOSPHERE_ILL_STATUS;
	private Switch BACK_CAR_VOICE_STATUS;

	private SeekBar ATMOSPHERE_ILL_VALUE;
	private Spinner WELCOME_PERSION_ILL_STATUS;
	private Spinner REMOTE_UNLOCK;
	private Spinner GO_HOME_LAMP_STATUS;
	private Switch TRUNK_UNLOCK_STATUS;
	private Switch CHANGE_ILL_STATUS;
	private Switch CHANGE_LINE_STATUS;
	private Switch WELCOME_FUNTION_STATUS;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.ss_peugeot408_car_setting,
				container, false);

		initView(view);
		try {
			syncView(getCanInfo());
		} catch (Exception e) {
		}
		return view;
	}

	private void syncView(CanInfo mCanInfo) {
		try {
			if (mCanInfo == null)
				return;
			AUTO_PARK_CAR_STATUS
					.setChecked(mCanInfo.AUTO_PARK_CAR_STATUS == 1 ? true
							: false);
			RADAR_ALARM_STATUS
					.setChecked(mCanInfo.RADAR_ALARM_STATUS == 1 ? true : false);
			REAR_WIPER_STATUS.setChecked(mCanInfo.REAR_WIPER_STATUS == 1 ? true
					: false);
			PARKING_STATUS.setChecked(mCanInfo.PARKING_STATUS == 1 ? true
					: false);
			CAR_LOCK_AUTO_STATUS
					.setChecked(mCanInfo.CAR_LOCK_AUTO_STATUS == 1 ? true
							: false);
			CAR_LOCK_STATUS.setChecked(mCanInfo.CAR_LOCK_STATUS == 1 ? true
					: false);
			DAYTIME_LAMP_STATUS
					.setChecked(mCanInfo.DAYTIME_LAMP_STATUS == 1 ? true
							: false);
			ATMOSPHERE_ILL_STATUS
					.setChecked(mCanInfo.ATMOSPHERE_ILL_STATUS == 1 ? true
							: false);
			// BACK_CAR_VOICE_STATUS.setChecked(mCanInfo.BACK_CAR_VOICE_STATUS==1?true:false);
			if (mCanInfo.ATMOSPHERE_ILL_STATUS == 0) {
				ATMOSPHERE_ILL_VALUE.setProgress(0);
				ATMOSPHERE_ILL_VALUE.setEnabled(false);
				;
			} else {
				ATMOSPHERE_ILL_VALUE.setEnabled(true);
				;
				ATMOSPHERE_ILL_VALUE.setProgress(mCanInfo.ATMOSPHERE_ILL_VALUE);
			}
			WELCOME_PERSION_ILL_STATUS
					.setSelection(mCanInfo.WELCOME_PERSION_ILL_STATUS);
			REMOTE_UNLOCK.setSelection(mCanInfo.REMOTE_UNLOCK);
			GO_HOME_LAMP_STATUS.setSelection(mCanInfo.GO_HOME_LAMP_STATUS);
			TRUNK_UNLOCK_STATUS
					.setChecked(mCanInfo.TRUNK_UNLOCK_STATUS == 1 ? true
							: false);
			CHANGE_ILL_STATUS.setChecked(mCanInfo.CHANGE_ILL_STATUS == 1 ? true
					: false);
			CHANGE_LINE_STATUS
					.setChecked(mCanInfo.CHANGE_LINE_STATUS == 1 ? true : false);
			WELCOME_FUNTION_STATUS
					.setChecked(mCanInfo.WELCOME_FUNTION_STATUS == 1 ? true
							: false);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private void initView(View view) {
		AUTO_PARK_CAR_STATUS = (Switch) view
				.findViewById(R.id.AUTO_PARK_CAR_STATUS);
		RADAR_ALARM_STATUS = (Switch) view
				.findViewById(R.id.RADAR_ALARM_STATUS);
		REAR_WIPER_STATUS = (Switch) view.findViewById(R.id.REAR_WIPER_STATUS);
		PARKING_STATUS = (Switch) view.findViewById(R.id.PARKING_STATUS);
		CAR_LOCK_AUTO_STATUS = (Switch) view
				.findViewById(R.id.CAR_LOCK_AUTO_STATUS);
		CAR_LOCK_STATUS = (Switch) view.findViewById(R.id.CAR_LOCK_STATUS);
		DAYTIME_LAMP_STATUS = (Switch) view
				.findViewById(R.id.DAYTIME_LAMP_STATUS);
		ATMOSPHERE_ILL_STATUS = (Switch) view
				.findViewById(R.id.ATMOSPHERE_ILL_STATUS);
		BACK_CAR_VOICE_STATUS = (Switch) view
				.findViewById(R.id.BACK_CAR_VOICE_STATUS);
		AUTO_PARK_CAR_STATUS.setOnClickListener(this);
		RADAR_ALARM_STATUS.setOnClickListener(this);
		REAR_WIPER_STATUS.setOnClickListener(this);
		PARKING_STATUS.setOnClickListener(this);
		CAR_LOCK_AUTO_STATUS.setOnClickListener(this);
		CAR_LOCK_STATUS.setOnClickListener(this);
		DAYTIME_LAMP_STATUS.setOnClickListener(this);
		ATMOSPHERE_ILL_STATUS.setOnClickListener(this);
		BACK_CAR_VOICE_STATUS.setOnClickListener(this);

		ATMOSPHERE_ILL_VALUE = (SeekBar) view
				.findViewById(R.id.ATMOSPHERE_ILL_VALUE);
		WELCOME_PERSION_ILL_STATUS = (Spinner) view
				.findViewById(R.id.WELCOME_PERSION_ILL_STATUS);
		REMOTE_UNLOCK = (Spinner) view.findViewById(R.id.REMOTE_UNLOCK);
		GO_HOME_LAMP_STATUS = (Spinner) view
				.findViewById(R.id.GO_HOME_LAMP_STATUS);
		TRUNK_UNLOCK_STATUS = (Switch) view
				.findViewById(R.id.TRUNK_UNLOCK_STATUS);
		CHANGE_ILL_STATUS = (Switch) view.findViewById(R.id.CHANGE_ILL_STATUS);
		CHANGE_LINE_STATUS = (Switch) view
				.findViewById(R.id.CHANGE_LINE_STATUS);
		WELCOME_FUNTION_STATUS = (Switch) view
				.findViewById(R.id.WELCOME_FUNTION_STATUS);
		ATMOSPHERE_ILL_VALUE
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub
						if (IsSync) {
							return;
						}

						if (ATMOSPHERE_ILL_STATUS.isChecked()) {
							sendMsg("5AA5027B0A8" + seekBar.getProgress());
						}
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {

					}
				});
		WELCOME_PERSION_ILL_STATUS.setOnItemSelectedListener(this);
		REMOTE_UNLOCK.setOnItemSelectedListener(this);
		GO_HOME_LAMP_STATUS.setOnItemSelectedListener(this);
		TRUNK_UNLOCK_STATUS.setOnClickListener(this);
		CHANGE_ILL_STATUS.setOnClickListener(this);
		CHANGE_LINE_STATUS.setOnClickListener(this);
		WELCOME_FUNTION_STATUS.setOnClickListener(this);

	}

	@Override
	public void serviceConnected() {
		super.serviceConnected();
	}

	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		try {
			mCanInfo = mCaninfo;
			IsSync = true;
			syncView(mCaninfo);
			IsSync = false;
		} catch (Exception e) {
		}
	}

	@Override
	public void onClick(View v) {
		try {
			switch (v.getId()) {
			case R.id.AUTO_PARK_CAR_STATUS:
				int v1 = AUTO_PARK_CAR_STATUS.isChecked() ? 1 : 0;
				sendMsg("5AA5027B08" + BytesUtil.intToHexString(v1));
				break;
			case R.id.RADAR_ALARM_STATUS:
				int v2 = RADAR_ALARM_STATUS.isChecked() ? 1 : 0;
				sendMsg("5AA5027B0B" + BytesUtil.intToHexString(v2));
				break;
			case R.id.REAR_WIPER_STATUS:
				int v3 = REAR_WIPER_STATUS.isChecked() ? 1 : 0;
				sendMsg("5AA5027B01" + BytesUtil.intToHexString(v3));
				break;
			case R.id.PARKING_STATUS:
				int v4 = PARKING_STATUS.isChecked() ? 1 : 0;
				sendMsg("5AA5027B02" + BytesUtil.intToHexString(v4));
				break;
			case R.id.CAR_LOCK_AUTO_STATUS:
				int v5 = CAR_LOCK_AUTO_STATUS.isChecked() ? 1 : 0;
				sendMsg("5AA5027B0C" + BytesUtil.intToHexString(v5));
				break;
			case R.id.CAR_LOCK_STATUS:
				int v6 = CAR_LOCK_STATUS.isChecked() ? 1 : 0;
				sendMsg("5AA5027B0D" + BytesUtil.intToHexString(v6));
				break;
			case R.id.DAYTIME_LAMP_STATUS:
				int v7 = DAYTIME_LAMP_STATUS.isChecked() ? 1 : 0;
				sendMsg("5AA5027B05" + BytesUtil.intToHexString(v7));
				break;

			case R.id.BACK_CAR_VOICE_STATUS:
				int v9 = BACK_CAR_VOICE_STATUS.isChecked() ? 1 : 0;
				sendMsg("5AA5027B03" + BytesUtil.intToHexString(v9));
				break;
			case R.id.TRUNK_UNLOCK_STATUS:
				int v10 = TRUNK_UNLOCK_STATUS.isChecked() ? 1 : 0;
				sendMsg("5AA5027d01" + BytesUtil.intToHexString(v10));
				break;
			case R.id.CHANGE_ILL_STATUS:
				int v11 = CHANGE_ILL_STATUS.isChecked() ? 1 : 0;
				sendMsg("5AA5027d02" + BytesUtil.intToHexString(v11));
				break;
			case R.id.CHANGE_LINE_STATUS:
				int v12 = CHANGE_LINE_STATUS.isChecked() ? 1 : 0;
				sendMsg("5AA5027d04" + BytesUtil.intToHexString(v12));
				break;
			case R.id.WELCOME_FUNTION_STATUS:
				int v13 = WELCOME_FUNTION_STATUS.isChecked() ? 1 : 0;
				sendMsg("5AA5027d05" + BytesUtil.intToHexString(v13));
				break;
			case R.id.ATMOSPHERE_ILL_STATUS:
				int v8 = ATMOSPHERE_ILL_STATUS.isChecked() ? 8 : 0;
				int v14 = ATMOSPHERE_ILL_VALUE.getProgress();
				sendMsg("5AA5027B0A" + v8 + v14);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		try {
			if (IsSync) {
				return;
			}
			switch (parent.getId()) {
			case R.id.WELCOME_PERSION_ILL_STATUS:
				sendMsg("5AA5027B09" + BytesUtil.intToHexString(position));
				break;
			case R.id.REMOTE_UNLOCK:
				sendMsg("5AA5027B04" + BytesUtil.intToHexString(position));
				break;
			case R.id.GO_HOME_LAMP_STATUS:
				sendMsg("5AA5027B06" + BytesUtil.intToHexString(position));
				break;
			default:
				break;
			}
		} catch (Exception e) {

		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

}
