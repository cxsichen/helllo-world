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

public class CurrentDriveFragment extends BaseFragment implements
		View.OnClickListener {

	private LinearLayout trip_content;

	private TextView tv1;
	private TextView tv2;
	private TextView tv3;
	private TextView tv4;
	DecimalFormat df;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		df = new DecimalFormat(".00");
		View view = inflater.inflate(R.layout.sshonda_current_drive_layout,
				container, false);
		initView(view);
		return view;
	}

	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		super.show(mCaninfo);
		if (mCaninfo.CURRENT_AVERAGE_CONSUMPTION == 6553.5) {
			updateTextView(tv2, "--");
		} else {
			updateTextView(
					tv2,
					df.format(mCaninfo.CURRENT_AVERAGE_CONSUMPTION)
							+ (mCaninfo.CUR_HIS_AVERAGE_CONSUMPTION_UNIT == 0 ? "mpg"
									: mCaninfo.CUR_HIS_AVERAGE_CONSUMPTION_UNIT == 1 ? "km/L"
											: "L/100km") + "");
		}

		if (mCaninfo.HISTORY_AVERAGE_CONSUMPTION == 6553.5) {
			updateTextView(tv3, "--");
		} else {
			updateTextView(
					tv3,
					df.format(mCaninfo.HISTORY_AVERAGE_CONSUMPTION)
							+ (mCaninfo.CUR_HIS_AVERAGE_CONSUMPTION_UNIT == 0 ? "mpg"
									: mCaninfo.CUR_HIS_AVERAGE_CONSUMPTION_UNIT == 1 ? "km/L"
											: "L/100km") + "");
		}

		if (mCaninfo.RANGE == 65535) {
			updateTextView(tv4, "--");
		} else {

			updateTextView(tv4, mCaninfo.RANGE
					+ (mCaninfo.RANGE_UNIT == 1 ? "mile" : "km") + "");
		}
		updateTextView(tv1, df.format(mCaninfo.INSTANT_CONSUMPTION)
				+ (mCaninfo.INSTANT_CONSUMPTION_UNIT == 0 ? "mpg"
						: mCaninfo.INSTANT_CONSUMPTION_UNIT == 1 ? "km/L"
								: "L/100km") + "");

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
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}

}
