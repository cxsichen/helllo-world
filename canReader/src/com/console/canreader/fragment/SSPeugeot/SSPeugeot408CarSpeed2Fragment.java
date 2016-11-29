package com.console.canreader.fragment.SSPeugeot;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.console.canreader.R;
import com.console.canreader.activity.BaseFragment;
import com.console.canreader.fragment.SSTrumpchi.TrumpchiCarSettingsFragment;
import com.console.canreader.fragment.SSTrumpchi.TrumpchiCarSettingsFragment.SettingsFragment;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;

public class SSPeugeot408CarSpeed2Fragment extends BaseFragment {

	private CanInfo mCanInfo;
	private static boolean IsSync2 = true;

	private CheckBox CRUISE_SPEED_STATUS_ENABLE;
	private CheckBox CRUISE_SPEED_1_ENABLE;
	private CheckBox CRUISE_SPEED_2_ENABLE;
	private CheckBox CRUISE_SPEED_3_ENABLE;
	private CheckBox CRUISE_SPEED_4_ENABLE;
	private CheckBox CRUISE_SPEED_5_ENABLE;
	private CheckBox CRUISE_SPEED_6_ENABLE;

	private CheckBox CRUISE_SPEED_STATUS;
	private CheckBox CRUISE_SPEED_1;
	private CheckBox CRUISE_SPEED_2;
	private CheckBox CRUISE_SPEED_3;
	private CheckBox CRUISE_SPEED_4;
	private CheckBox CRUISE_SPEED_5;
	private CheckBox CRUISE_SPEED_6;

	private TextView CRUISE_SPEED_1_VALUE;
	private TextView CRUISE_SPEED_2_VALUE;
	private TextView CRUISE_SPEED_3_VALUE;
	private TextView CRUISE_SPEED_4_VALUE;
	private TextView CRUISE_SPEED_5_VALUE;
	private TextView CRUISE_SPEED_6_VALUE;

	private SeekBar CRUISE_SPEED_1_VALUE_S;
	private SeekBar CRUISE_SPEED_2_VALUE_S;
	private SeekBar CRUISE_SPEED_3_VALUE_S;
	private SeekBar CRUISE_SPEED_4_VALUE_S;
	private SeekBar CRUISE_SPEED_5_VALUE_S;
	private SeekBar CRUISE_SPEED_6_VALUE_S;

	private OnSeekBarChangeListener seekBarListenenr = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			if (IsSync2) {
				return;
			}
			// TODO Auto-generated method stub
			String s1 = BytesUtil.intToHexString(CRUISE_SPEED_1_VALUE_S
					.getProgress());
			String s2 = BytesUtil.intToHexString(CRUISE_SPEED_2_VALUE_S
					.getProgress());
			String s3 = BytesUtil.intToHexString(CRUISE_SPEED_3_VALUE_S
					.getProgress());
			String s4 = BytesUtil.intToHexString(CRUISE_SPEED_4_VALUE_S
					.getProgress());
			String s5 = BytesUtil.intToHexString(CRUISE_SPEED_5_VALUE_S
					.getProgress());
			String s6 = BytesUtil.intToHexString(CRUISE_SPEED_6_VALUE_S
					.getProgress());
			Log.i("xxx", "s1=" + s1);
			Log.i("xxx", "s2=" + s2);
			Log.i("xxx", "s3=" + s3);
			Log.i("xxx", "s4=" + s4);
			Log.i("xxx", "s5=" + s5);
			Log.i("xxx", "s6=" + s6);
			int v0 = CRUISE_SPEED_STATUS.isChecked() ? 1 : 0;
			int v1 = CRUISE_SPEED_1.isChecked() ? 1 : 0;
			int v2 = CRUISE_SPEED_2.isChecked() ? 1 : 0;
			int v3 = CRUISE_SPEED_3.isChecked() ? 1 : 0;
			int v4 = CRUISE_SPEED_4.isChecked() ? 1 : 0;
			int v5 = CRUISE_SPEED_5.isChecked() ? 1 : 0;
			int v6 = CRUISE_SPEED_6.isChecked() ? 1 : 0;
			int vC = (v0 << 7) + (v1 << 6) + (v2 << 5) + (v3 << 4) + (v4 << 3)
					+ (v5 << 2) + (v6 << 1);
			sendMsg("5AA50A8B" + BytesUtil.intToHexString(vC) + s1 + s2 + s3
					+ s4 + s5 + s6 + "000000");
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			try {
				CRUISE_SPEED_1_VALUE.setText(""
						+ CRUISE_SPEED_1_VALUE_S.getProgress());
				CRUISE_SPEED_2_VALUE.setText(""
						+ CRUISE_SPEED_2_VALUE_S.getProgress());
				CRUISE_SPEED_3_VALUE.setText(""
						+ CRUISE_SPEED_3_VALUE_S.getProgress());
				CRUISE_SPEED_4_VALUE.setText(""
						+ CRUISE_SPEED_4_VALUE_S.getProgress());
				CRUISE_SPEED_5_VALUE.setText(""
						+ CRUISE_SPEED_5_VALUE_S.getProgress());
				CRUISE_SPEED_6_VALUE.setText(""
						+ CRUISE_SPEED_6_VALUE_S.getProgress());
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	};

	private OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (IsSync2) {
				return;
			}

			Log.i("xxx", "buttonView=" + buttonView.getId());
			Log.i("xxx", "isChecked=" + isChecked);
			int v0 = CRUISE_SPEED_STATUS.isChecked() ? 1 : 0;
			int v1 = CRUISE_SPEED_1.isChecked() ? 1 : 0;
			int v2 = CRUISE_SPEED_2.isChecked() ? 1 : 0;
			int v3 = CRUISE_SPEED_3.isChecked() ? 1 : 0;
			int v4 = CRUISE_SPEED_4.isChecked() ? 1 : 0;
			int v5 = CRUISE_SPEED_5.isChecked() ? 1 : 0;
			int v6 = CRUISE_SPEED_6.isChecked() ? 1 : 0;
			int vC = (v0 << 7) + (v1 << 6) + (v2 << 5) + (v3 << 4) + (v4 << 3)
					+ (v5 << 2) + (v6 << 1);
			String s1 = BytesUtil.intToHexString(mCanInfo.CRUISE_SPEED_1_VALUE);
			String s2 = BytesUtil.intToHexString(mCanInfo.CRUISE_SPEED_2_VALUE);
			String s3 = BytesUtil.intToHexString(mCanInfo.CRUISE_SPEED_3_VALUE);
			String s4 = BytesUtil.intToHexString(mCanInfo.CRUISE_SPEED_4_VALUE);
			String s5 = BytesUtil.intToHexString(mCanInfo.CRUISE_SPEED_5_VALUE);
			String s6 = BytesUtil.intToHexString(mCanInfo.CRUISE_SPEED_6_VALUE);
			Log.i("xxx", "string v==" + BytesUtil.intToHexString(vC));
			Log.i("xxx", "s3=" + s3);
			Log.i("xxx", "s6=" + s6);
			sendMsg("5AA50A8B" + BytesUtil.intToHexString(vC) + s1 + s2 + s3
					+ s4 + s5 + s6 + "000000");

		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.ss_peugeot408_cruise_speed,
				container, false);

		initView(view);
		syncView(getCanInfo());
		return view;
	}

	private void syncView(CanInfo canInfo) {
		try {

			CRUISE_SPEED_STATUS_ENABLE
					.setChecked(canInfo.CRUISE_SPEED_STATUS_ENABLE == 1 ? true
							: false);
			CRUISE_SPEED_1_ENABLE
					.setChecked(canInfo.CRUISE_SPEED_1_ENABLE == 1 ? true
							: false);
			CRUISE_SPEED_2_ENABLE
					.setChecked(canInfo.CRUISE_SPEED_2_ENABLE == 1 ? true
							: false);
			CRUISE_SPEED_3_ENABLE
					.setChecked(canInfo.CRUISE_SPEED_3_ENABLE == 1 ? true
							: false);
			CRUISE_SPEED_4_ENABLE
					.setChecked(canInfo.CRUISE_SPEED_4_ENABLE == 1 ? true
							: false);
			CRUISE_SPEED_5_ENABLE
					.setChecked(canInfo.CRUISE_SPEED_5_ENABLE == 1 ? true
							: false);
			CRUISE_SPEED_6_ENABLE
					.setChecked(canInfo.CRUISE_SPEED_6_ENABLE == 1 ? true
							: false);

			CRUISE_SPEED_STATUS
					.setChecked(canInfo.CRUISE_SPEED_STATUS == 1 ? true : false);
			CRUISE_SPEED_1.setChecked(canInfo.CRUISE_SPEED_1 == 1 ? true
					: false);
			CRUISE_SPEED_2.setChecked(canInfo.CRUISE_SPEED_2 == 1 ? true
					: false);
			CRUISE_SPEED_3.setChecked(canInfo.CRUISE_SPEED_3 == 1 ? true
					: false);
			CRUISE_SPEED_4.setChecked(canInfo.CRUISE_SPEED_4 == 1 ? true
					: false);
			CRUISE_SPEED_5.setChecked(canInfo.CRUISE_SPEED_5 == 1 ? true
					: false);
			CRUISE_SPEED_6.setChecked(canInfo.CRUISE_SPEED_6 == 1 ? true
					: false);

			CRUISE_SPEED_1_VALUE.setText("" + canInfo.CRUISE_SPEED_1_VALUE);
			CRUISE_SPEED_2_VALUE.setText("" + canInfo.CRUISE_SPEED_2_VALUE);
			CRUISE_SPEED_3_VALUE.setText("" + canInfo.CRUISE_SPEED_3_VALUE);
			CRUISE_SPEED_4_VALUE.setText("" + canInfo.CRUISE_SPEED_4_VALUE);
			CRUISE_SPEED_5_VALUE.setText("" + canInfo.CRUISE_SPEED_5_VALUE);
			CRUISE_SPEED_6_VALUE.setText("" + canInfo.CRUISE_SPEED_6_VALUE);

			CRUISE_SPEED_1_VALUE_S.setProgress(canInfo.CRUISE_SPEED_1_VALUE);
			CRUISE_SPEED_2_VALUE_S.setProgress(canInfo.CRUISE_SPEED_2_VALUE);
			CRUISE_SPEED_3_VALUE_S.setProgress(canInfo.CRUISE_SPEED_3_VALUE);
			CRUISE_SPEED_4_VALUE_S.setProgress(canInfo.CRUISE_SPEED_4_VALUE);
			CRUISE_SPEED_5_VALUE_S.setProgress(canInfo.CRUISE_SPEED_5_VALUE);
			CRUISE_SPEED_6_VALUE_S.setProgress(canInfo.CRUISE_SPEED_6_VALUE);

			if (CRUISE_SPEED_STATUS.isChecked()) {
				CRUISE_SPEED_1.setEnabled(true);
				CRUISE_SPEED_2.setEnabled(true);
				CRUISE_SPEED_3.setEnabled(true);
				CRUISE_SPEED_4.setEnabled(true);
				CRUISE_SPEED_5.setEnabled(true);
				CRUISE_SPEED_6.setEnabled(true);
				CRUISE_SPEED_1_VALUE_S.setEnabled(CRUISE_SPEED_1.isChecked());
				CRUISE_SPEED_2_VALUE_S.setEnabled(CRUISE_SPEED_2.isChecked());
				CRUISE_SPEED_3_VALUE_S.setEnabled(CRUISE_SPEED_3.isChecked());
				CRUISE_SPEED_4_VALUE_S.setEnabled(CRUISE_SPEED_4.isChecked());
				CRUISE_SPEED_5_VALUE_S.setEnabled(CRUISE_SPEED_5.isChecked());
				CRUISE_SPEED_6_VALUE_S.setEnabled(CRUISE_SPEED_6.isChecked());
			} else {
				CRUISE_SPEED_1.setEnabled(false);
				CRUISE_SPEED_2.setEnabled(false);
				CRUISE_SPEED_3.setEnabled(false);
				CRUISE_SPEED_4.setEnabled(false);
				CRUISE_SPEED_5.setEnabled(false);
				CRUISE_SPEED_6.setEnabled(false);
				CRUISE_SPEED_1_VALUE_S.setEnabled(false);
				CRUISE_SPEED_2_VALUE_S.setEnabled(false);
				CRUISE_SPEED_3_VALUE_S.setEnabled(false);
				CRUISE_SPEED_4_VALUE_S.setEnabled(false);
				CRUISE_SPEED_5_VALUE_S.setEnabled(false);
				CRUISE_SPEED_6_VALUE_S.setEnabled(false);

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void initView(View view) {
		CRUISE_SPEED_STATUS_ENABLE = (CheckBox) view
				.findViewById(R.id.CRUISE_SPEED_STATUS_ENABLE);
		CRUISE_SPEED_1_ENABLE = (CheckBox) view
				.findViewById(R.id.CRUISE_SPEED_1_ENABLE);
		CRUISE_SPEED_2_ENABLE = (CheckBox) view
				.findViewById(R.id.CRUISE_SPEED_2_ENABLE);
		CRUISE_SPEED_3_ENABLE = (CheckBox) view
				.findViewById(R.id.CRUISE_SPEED_3_ENABLE);
		CRUISE_SPEED_4_ENABLE = (CheckBox) view
				.findViewById(R.id.CRUISE_SPEED_4_ENABLE);
		CRUISE_SPEED_5_ENABLE = (CheckBox) view
				.findViewById(R.id.CRUISE_SPEED_5_ENABLE);
		CRUISE_SPEED_6_ENABLE = (CheckBox) view
				.findViewById(R.id.CRUISE_SPEED_6_ENABLE);

		CRUISE_SPEED_STATUS = (CheckBox) view
				.findViewById(R.id.CRUISE_SPEED_STATUS);
		CRUISE_SPEED_1 = (CheckBox) view.findViewById(R.id.CRUISE_SPEED_1);
		CRUISE_SPEED_2 = (CheckBox) view.findViewById(R.id.CRUISE_SPEED_2);
		CRUISE_SPEED_3 = (CheckBox) view.findViewById(R.id.CRUISE_SPEED_3);
		CRUISE_SPEED_4 = (CheckBox) view.findViewById(R.id.CRUISE_SPEED_4);
		CRUISE_SPEED_5 = (CheckBox) view.findViewById(R.id.CRUISE_SPEED_5);
		CRUISE_SPEED_6 = (CheckBox) view.findViewById(R.id.CRUISE_SPEED_6);
		CRUISE_SPEED_STATUS.setOnCheckedChangeListener(onCheckedChangeListener);
		CRUISE_SPEED_1.setOnCheckedChangeListener(onCheckedChangeListener);
		CRUISE_SPEED_2.setOnCheckedChangeListener(onCheckedChangeListener);
		CRUISE_SPEED_3.setOnCheckedChangeListener(onCheckedChangeListener);
		CRUISE_SPEED_4.setOnCheckedChangeListener(onCheckedChangeListener);
		CRUISE_SPEED_5.setOnCheckedChangeListener(onCheckedChangeListener);
		CRUISE_SPEED_6.setOnCheckedChangeListener(onCheckedChangeListener);

		CRUISE_SPEED_1_VALUE = (TextView) view
				.findViewById(R.id.CRUISE_SPEED_1_VALUE);
		CRUISE_SPEED_2_VALUE = (TextView) view
				.findViewById(R.id.CRUISE_SPEED_2_VALUE);
		CRUISE_SPEED_3_VALUE = (TextView) view
				.findViewById(R.id.CRUISE_SPEED_3_VALUE);
		CRUISE_SPEED_4_VALUE = (TextView) view
				.findViewById(R.id.CRUISE_SPEED_4_VALUE);
		CRUISE_SPEED_5_VALUE = (TextView) view
				.findViewById(R.id.CRUISE_SPEED_5_VALUE);
		CRUISE_SPEED_6_VALUE = (TextView) view
				.findViewById(R.id.CRUISE_SPEED_6_VALUE);

		CRUISE_SPEED_1_VALUE_S = (SeekBar) view
				.findViewById(R.id.CRUISE_SPEED_1_VALUE_S);
		CRUISE_SPEED_2_VALUE_S = (SeekBar) view
				.findViewById(R.id.CRUISE_SPEED_2_VALUE_S);
		CRUISE_SPEED_3_VALUE_S = (SeekBar) view
				.findViewById(R.id.CRUISE_SPEED_3_VALUE_S);
		CRUISE_SPEED_4_VALUE_S = (SeekBar) view
				.findViewById(R.id.CRUISE_SPEED_4_VALUE_S);
		CRUISE_SPEED_5_VALUE_S = (SeekBar) view
				.findViewById(R.id.CRUISE_SPEED_5_VALUE_S);
		CRUISE_SPEED_6_VALUE_S = (SeekBar) view
				.findViewById(R.id.CRUISE_SPEED_6_VALUE_S);

		CRUISE_SPEED_1_VALUE_S.setOnSeekBarChangeListener(seekBarListenenr);
		CRUISE_SPEED_2_VALUE_S.setOnSeekBarChangeListener(seekBarListenenr);
		CRUISE_SPEED_3_VALUE_S.setOnSeekBarChangeListener(seekBarListenenr);
		CRUISE_SPEED_4_VALUE_S.setOnSeekBarChangeListener(seekBarListenenr);
		CRUISE_SPEED_5_VALUE_S.setOnSeekBarChangeListener(seekBarListenenr);
		CRUISE_SPEED_6_VALUE_S.setOnSeekBarChangeListener(seekBarListenenr);

	}

	@Override
	public void serviceConnected() {
		// TODO Auto-generated method stub
		super.serviceConnected();
	}

	@Override
	public void show(CanInfo mCaninfo) {
		if (mCaninfo != null) {
			mCanInfo = mCaninfo;
		}
		try {
			super.show(mCaninfo);
			IsSync2 = true;
			syncView(mCaninfo);
			IsSync2 = false;
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
