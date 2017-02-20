package com.console.canreader.fragment.SSHonda;

import java.text.DecimalFormat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.console.canreader.R;
import com.console.canreader.activity.BaseFragment;
import com.console.canreader.service.CanInfo;

public class HistoryDriveFragment extends BaseFragment implements
		View.OnClickListener {

	private LinearLayout trip_content;

	private TextView tv1;
	private TextView tv2;
	private TextView tv3;
	private TextView tv4;

	private TextView tv5;
	private TextView tv6;
	private TextView tv7;
	private TextView tv8;

	private Button button1;
	DecimalFormat df;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		df = new DecimalFormat(".00");
		View view = inflater.inflate(R.layout.sshonda_history_drive_layout,
				container, false);
		initView(view);
		return view;
	}

	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		super.show(mCaninfo);

		if (mCaninfo.TRIP_A == 1677721.5) {
			updateTextView(tv1, "--");
		} else {
			updateTextView(tv1, df.format(mCaninfo.TRIP_A)
					+ (mCaninfo.TRIP_A_UNIT == 0 ? "km" : "mile") + "");
		}

		if (mCaninfo.AVERAGE_CONSUMPTION == 6553.5) {
			updateTextView(tv2, "--");
		} else {
			updateTextView(tv2, df.format(mCaninfo.AVERAGE_CONSUMPTION)
					+ (mCaninfo.AVERAGE_CONSUMPTION_UNIT == 0 ? "mpg"
							: mCaninfo.AVERAGE_CONSUMPTION_UNIT == 1 ? "km/L"
									: "L/100km") + "");
		}

		if (mCaninfo.TRIP_A_1 == 1677721.5) {
			updateTextView(tv3, "--");
		} else {
			updateTextView(tv3, df.format(mCaninfo.TRIP_A_1)
					+ (mCaninfo.TRIP_A_UNIT == 0 ? "km" : "mile") + "");
		}

		if (mCaninfo.TRIP_A_1_AVERAGE_CONSUMPTION == 6553.5) {
			updateTextView(tv4, "--");
		} else {
			updateTextView(
					tv4,
					df.format(mCaninfo.TRIP_A_1_AVERAGE_CONSUMPTION)
							+ (mCaninfo.AVERAGE_CONSUMPTION_UNIT == 0 ? "mpg"
									: mCaninfo.AVERAGE_CONSUMPTION_UNIT == 1 ? "km/L"
											: "L/100km") + "");
		}

		if (mCaninfo.TRIP_A_2 == 1677721.5) {
			updateTextView(tv5, "--");
		} else {
			updateTextView(tv5, df.format(mCaninfo.TRIP_A_2)
					+ (mCaninfo.TRIP_A_UNIT == 0 ? "km" : "mile") + "");
		}

		if (mCaninfo.TRIP_A_2_AVERAGE_CONSUMPTION == 6553.5) {
			updateTextView(tv6, "--");
		} else {
			updateTextView(
					tv6,
					df.format(mCaninfo.TRIP_A_2_AVERAGE_CONSUMPTION)
							+ (mCaninfo.AVERAGE_CONSUMPTION_UNIT == 0 ? "mpg"
									: mCaninfo.AVERAGE_CONSUMPTION_UNIT == 1 ? "km/L"
											: "L/100km") + "");
		}

		if (mCaninfo.TRIP_A_3 == 1677721.5) {
			updateTextView(tv7, "--");
		} else {
			updateTextView(tv7, df.format(mCaninfo.TRIP_A_3)
					+ (mCaninfo.TRIP_A_UNIT == 0 ? "km" : "mile") + "");
		}

		if (mCaninfo.TRIP_A_3_AVERAGE_CONSUMPTION == 6553.5) {
			updateTextView(tv8, "--");
		} else {
			updateTextView(
					tv8,
					df.format(mCaninfo.TRIP_A_3_AVERAGE_CONSUMPTION)
							+ (mCaninfo.AVERAGE_CONSUMPTION_UNIT == 0 ? "mpg"
									: mCaninfo.AVERAGE_CONSUMPTION_UNIT == 1 ? "km/L"
											: "L/100km") + "");
		}

	}

	private void updateTextView(TextView tv, String str) {
		if (!tv.getText().equals(str)) {
			tv.setText(str);
		}
	}

	@Override
	public void serviceConnected() {
		// TODO Auto-generated method stub
		super.serviceConnected();
	}

	private void initView(View view) {
		tv1 = (TextView) view.findViewById(R.id.tv1);
		tv2 = (TextView) view.findViewById(R.id.tv2);
		tv3 = (TextView) view.findViewById(R.id.tv3);
		tv4 = (TextView) view.findViewById(R.id.tv4);

		tv5 = (TextView) view.findViewById(R.id.tv5);
		tv6 = (TextView) view.findViewById(R.id.tv6);
		tv7 = (TextView) view.findViewById(R.id.tv7);
		tv8 = (TextView) view.findViewById(R.id.tv8);

		button1 = (Button) view.findViewById(R.id.button1);
		button1.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:
			sendMsg("5AA502F206FF");
			break;

		default:
			break;
		}
	}

}
