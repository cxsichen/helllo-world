package com.console.canreader.fragment.SSPeugeot;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.console.canreader.R;
import com.console.canreader.activity.BaseFragment;
import com.console.canreader.service.CanInfo;

public class SSPeugeot408CarInfoFragment extends BaseFragment {

	private TextView INSTANT_CONSUMPTION;
	private TextView RANGE;
	private TextView AVERAGE_CONSUMPTION;
	private TextView AVERAGE_SPEED;
	private TextView RANGE_ADD;
	private Button ENGINE_START_STATUS;
	private int QiTing;
	private Context mContext;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.ss_peugeot408_car_info,
				container, false);
		mContext=view.getContext();
		initView(view);
		syncView(getCanInfo());
		return view;
	}

	private void syncView(CanInfo canInfo) {
		try {
			if (canInfo != null) {

				if (canInfo.INSTANT_CONSUMPTION == 0xffff) {
					INSTANT_CONSUMPTION.setText("---");
				} else {
					INSTANT_CONSUMPTION.setText(""
							+ canInfo.INSTANT_CONSUMPTION);
				}
				if (canInfo.RANGE == 0xffff) {
					RANGE.setText("---");
				} else {
					RANGE.setText("" + canInfo.RANGE);
				}

				if (canInfo.AVERAGE_CONSUMPTION == 0xffff) {
					AVERAGE_CONSUMPTION.setText("---");
				} else {
					AVERAGE_CONSUMPTION.setText(""
							+ canInfo.AVERAGE_CONSUMPTION);
				}

				if (canInfo.AVERAGE_SPEED == 0xff) {
					AVERAGE_SPEED.setText("---");
				} else {
					AVERAGE_SPEED.setText("" + canInfo.AVERAGE_SPEED);
				}
				if (canInfo.RANGE_ADD > 9999) {
					RANGE_ADD.setText("---");
				} else {
					RANGE_ADD.setText("" + canInfo.RANGE_ADD);
				}
				QiTing = canInfo.ENGINE_START_STATUS;
				if (canInfo.ENGINE_START_STATUS == 1) {
					ENGINE_START_STATUS.getBackground().setAlpha(0);
				} else {
					ENGINE_START_STATUS.getBackground().setAlpha(255);
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void showWarningDialog(String warningStr) {
		Dialog alertDialog = new AlertDialog.Builder(mContext).setTitle("¾¯¸æ")
				.
				setMessage(warningStr).
				create();
		alertDialog.getWindow().setType(
				WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

		alertDialog.show();
	}
	
	
	
	private void initView(View view) {
		try {

			INSTANT_CONSUMPTION = (TextView) view
					.findViewById(R.id.INSTANT_CONSUMPTION);
			RANGE = (TextView) view.findViewById(R.id.RANGE);
			AVERAGE_CONSUMPTION = (TextView) view
					.findViewById(R.id.AVERAGE_CONSUMPTION);
			AVERAGE_SPEED = (TextView) view.findViewById(R.id.AVERAGE_SPEED);
			RANGE_ADD = (TextView) view.findViewById(R.id.RANGE_ADD);
			ENGINE_START_STATUS = (Button) view
					.findViewById(R.id.ENGINE_START_STATUS);
			ENGINE_START_STATUS.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					sendMsg("5AA5028C01FF");
				}
			});
			view.findViewById(R.id.peugeot_clean).setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					sendMsg("5AA5041B020201FF");
					sendMsg("5AA5041B030301FF");
					return true;
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		try {
			super.show(mCaninfo);
			syncView(mCaninfo);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
