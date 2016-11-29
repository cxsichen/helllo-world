package com.console.canreader.fragment.SSGE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.console.canreader.R;
import com.console.canreader.activity.BaseFragment;
import com.console.canreader.service.CanInfo;

public class ConDisInfoFragment extends BaseFragment {

	private TextView tv1;
	private TextView tv2;
	private TextView tv3;
	private TextView tv4;
	private TextView tv5;
	private TextView tv6;

	private TextView tv7;
	private TextView tv8;
	private TextView tv9;
	private TextView tv10;
	String conUnit = "";
	String disUnit = "";
	String[] disGroup = { "mpg", "km/L", "L/100km", "L/H" };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.ssge_carinfo_layout, container,
				false);
		initView(view);
		return view;
	}

	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		super.show(mCaninfo);
		if (mCaninfo != null) {
			conUnit = mCaninfo.INSTANT_CONSUMPTION_UNIT == 0 ? "km" : "mile";
			disUnit = disGroup[mCaninfo.TRIP_A_UNIT];

			tv1.setText(mCaninfo.INSTANT_CONSUMPTION + conUnit);
			tv2.setText(mCaninfo.RANGE + disUnit);
			tv3.setText(mCaninfo.DRIVING_DISTANCE + disUnit);

			tv5.setText(mCaninfo.TRIP_A_1_AVERAGE_CONSUMPTION + conUnit);
			tv6.setText(mCaninfo.TRIP_A_1 + disUnit);
			tv7.setText(mCaninfo.TRIP_A_2_AVERAGE_CONSUMPTION + conUnit);
			tv8.setText(mCaninfo.TRIP_A_2 + disUnit);
			tv9.setText(mCaninfo.TRIP_A_3_AVERAGE_CONSUMPTION + conUnit);
			tv10.setText(mCaninfo.TRIP_A_3 + disUnit);
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
		tv9 = (TextView) view.findViewById(R.id.tv9);
		tv10 = (TextView) view.findViewById(R.id.tv10);

	}

}
