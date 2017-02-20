package com.console.canreader.fragment.SSPeugeot;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;

import com.console.canreader.R;
import com.console.canreader.activity.BaseFragment;
import com.console.canreader.service.CanInfo;

public class SSPeugeot408CarEnableFragment extends BaseFragment {

	private CheckBox AUTO_PARK_CAR_STATUS_ENABLE;
	private CheckBox RADAR_ALARM_STATUS_ENABLE;
	private CheckBox WELCOME_PERSION_ILL_STATUS_ENABLE;
	private CheckBox ATMOSPHERE_ILL_STATUS_ENABLE;
	private CheckBox REAR_WIPER_STATUS_ENABLE;

	private CheckBox PARKING_STATUS_ENABLE;
	private CheckBox CAR_LOCK_AUTO_STATUS_ENABLE;
	private CheckBox CAR_LOCK_STATUS_ENABLE;
	private CheckBox CAR_UNLOCK_STATUS_ENABLE;
	private CheckBox DAYTIME_LAMP_STATUS_ENABLE;

	private CheckBox GO_HOME_LAMP_STATUS_ENABLE;
	private CheckBox TRUNK_UNLOCK_STATUS_ENABLE;
	private CheckBox CHANGE_ILL_STATUS_ENABLE;
	private CheckBox CHANGE_LINE_STATUS_ENABLE;
	private CheckBox WELCOME_FUNTION_STATUS_ENABLE;

	private Button temp_enable;
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.ss_peugeot408_car_enable, container,
				false);

		initView(view);
		syncView(getCanInfo());
		return view;
	}

	private void syncView(CanInfo canInfo) {
		try {
			AUTO_PARK_CAR_STATUS_ENABLE
					.setChecked(canInfo.AUTO_PARK_CAR_STATUS_ENABLE == 1 ? true
							: false);
			RADAR_ALARM_STATUS_ENABLE
					.setChecked(canInfo.RADAR_ALARM_STATUS_ENABLE == 1 ? true
							: false);
			WELCOME_PERSION_ILL_STATUS_ENABLE
					.setChecked(canInfo.WELCOME_PERSION_ILL_STATUS_ENABLE == 1 ? true
							: false);
			ATMOSPHERE_ILL_STATUS_ENABLE
					.setChecked(canInfo.ATMOSPHERE_ILL_STATUS_ENABLE == 1 ? true
							: false);
			REAR_WIPER_STATUS_ENABLE
					.setChecked(canInfo.REAR_WIPER_STATUS_ENABLE == 1 ? true
							: false);

			PARKING_STATUS_ENABLE
					.setChecked(canInfo.PARKING_STATUS_ENABLE == 1 ? true
							: false);
			CAR_LOCK_AUTO_STATUS_ENABLE
					.setChecked(canInfo.CAR_LOCK_AUTO_STATUS_ENABLE == 1 ? true
							: false);
			CAR_LOCK_STATUS_ENABLE
					.setChecked(canInfo.CAR_LOCK_STATUS_ENABLE == 1 ? true
							: false);
			CAR_UNLOCK_STATUS_ENABLE
					.setChecked(canInfo.CAR_UNLOCK_STATUS_ENABLE == 1 ? true
							: false);
			DAYTIME_LAMP_STATUS_ENABLE
					.setChecked(canInfo.DAYTIME_LAMP_STATUS_ENABLE == 1 ? true
							: false);

			GO_HOME_LAMP_STATUS_ENABLE
					.setChecked(canInfo.GO_HOME_LAMP_STATUS_ENABLE == 1 ? true
							: false);
			TRUNK_UNLOCK_STATUS_ENABLE
					.setChecked(canInfo.TRUNK_UNLOCK_STATUS_ENABLE == 1 ? true
							: false);
			CHANGE_ILL_STATUS_ENABLE
					.setChecked(canInfo.CHANGE_ILL_STATUS_ENABLE == 1 ? true
							: false);
			CHANGE_LINE_STATUS_ENABLE
					.setChecked(canInfo.CHANGE_LINE_STATUS_ENABLE == 1 ? true
							: false);
			WELCOME_FUNTION_STATUS_ENABLE
					.setChecked(canInfo.WELCOME_FUNTION_STATUS_ENABLE == 1 ? true
							: false);

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private void initView(final View view) {
		AUTO_PARK_CAR_STATUS_ENABLE = (CheckBox) view
				.findViewById(R.id.AUTO_PARK_CAR_STATUS_ENABLE);
		RADAR_ALARM_STATUS_ENABLE = (CheckBox) view
				.findViewById(R.id.RADAR_ALARM_STATUS_ENABLE);
		WELCOME_PERSION_ILL_STATUS_ENABLE = (CheckBox) view
				.findViewById(R.id.WELCOME_PERSION_ILL_STATUS_ENABLE);
		ATMOSPHERE_ILL_STATUS_ENABLE = (CheckBox) view
				.findViewById(R.id.ATMOSPHERE_ILL_STATUS_ENABLE);
		REAR_WIPER_STATUS_ENABLE = (CheckBox) view
				.findViewById(R.id.REAR_WIPER_STATUS_ENABLE);

		PARKING_STATUS_ENABLE = (CheckBox) view
				.findViewById(R.id.PARKING_STATUS_ENABLE);
		CAR_LOCK_AUTO_STATUS_ENABLE = (CheckBox) view
				.findViewById(R.id.CAR_LOCK_AUTO_STATUS_ENABLE);
		CAR_LOCK_STATUS_ENABLE = (CheckBox) view
				.findViewById(R.id.CAR_LOCK_STATUS_ENABLE);
		CAR_UNLOCK_STATUS_ENABLE = (CheckBox) view
				.findViewById(R.id.CAR_UNLOCK_STATUS_ENABLE);
		DAYTIME_LAMP_STATUS_ENABLE = (CheckBox) view
				.findViewById(R.id.DAYTIME_LAMP_STATUS_ENABLE);

		GO_HOME_LAMP_STATUS_ENABLE = (CheckBox) view
				.findViewById(R.id.GO_HOME_LAMP_STATUS_ENABLE);
		TRUNK_UNLOCK_STATUS_ENABLE = (CheckBox) view
				.findViewById(R.id.TRUNK_UNLOCK_STATUS_ENABLE);
		CHANGE_ILL_STATUS_ENABLE = (CheckBox) view
				.findViewById(R.id.CHANGE_ILL_STATUS_ENABLE);
		CHANGE_LINE_STATUS_ENABLE = (CheckBox) view
				.findViewById(R.id.CHANGE_LINE_STATUS_ENABLE);
		WELCOME_FUNTION_STATUS_ENABLE = (CheckBox) view
				.findViewById(R.id.WELCOME_FUNTION_STATUS_ENABLE);
		temp_enable = (Button) view.findViewById(R.id.temp_enable);
		temp_enable.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog(view.getContext());
			}
		});
	}

	private void showDialog(Context context) {
		try {

			Dialog alertDialog = new AlertDialog.Builder(context)
					.setTitle(R.string.temp_open)
					.setPositiveButton("NO",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									sendMsg("5AA5027D030");
								}
							})
					.setNegativeButton("YES",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									sendMsg("5AA5027D0301");
								}
							}).create();

			alertDialog.getWindow().setType(
					WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

			alertDialog.show();
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
	public void show(CanInfo mCaninfo) {
		try {
			super.show(mCaninfo);
			syncView(mCaninfo);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
