package com.console.canreader.activity.Toyota;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.console.canreader.R;
import com.console.canreader.activity.BaseActivity;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.DensityUtils;
import com.console.canreader.utils.PreferenceUtil;
import com.console.canreader.utils.ViewPagerAdapter;
import com.console.canreader.view.ObdView;
import com.console.canreader.view.ViewPageFactory;

public class TripOilConActivity extends BaseActivity implements OnClickListener {

	private TextView trip0;
	private TextView trip1;
	private TextView trip2;
	private TextView trip3;
	private TextView trip4;
	private TextView trip5;

	private TextView unit;
	private TextView unit0;
	private TextView unit1;
	private TextView unit2;
	private TextView unit3;

	Button trip_update;
	Button trip_clear;

	private LinearLayout trip_content;
	private static int height = 0;

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Contacts.MSG_GET_MSG:
				// 主动获取数据
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
		setContentView(R.layout.trip_oil_layout);
		initView();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
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
		if (height == 0) {
			height = trip_content.getHeight();
		}

		if (mCaninfo != null) {
			switch (mCaninfo.TRIP_OIL_CONSUMPTION_UNIT) {
			case 0:
				unit.setText("MPG");
				unit0.setText("60");
				unit1.setText("40");
				unit2.setText("20");
				break;
			case 1:
				unit.setText("km/L");
				unit0.setText("30");
				unit1.setText("20");
				unit2.setText("10");
				break;
			case 2:
				unit.setText("L/100km");
				unit0.setText("30");
				unit1.setText("20");
				unit2.setText("10");
				break;
			default:
				break;
			}

			if (mCaninfo.TRIP_OIL_CONSUMPTION_UNIT == 0) {
				changeTvHeight(trip0,
						(int) (height * (mCaninfo.TRIP_OIL_CONSUMPTION_0 / 60)));
				changeTvHeight(trip1,
						(int) (height * (mCaninfo.TRIP_OIL_CONSUMPTION_1 / 60)));
				changeTvHeight(trip2,
						(int) (height * (mCaninfo.TRIP_OIL_CONSUMPTION_2 / 60)));
				changeTvHeight(trip3,
						(int) (height * (mCaninfo.TRIP_OIL_CONSUMPTION_3 / 60)));
				changeTvHeight(trip4,
						(int) (height * (mCaninfo.TRIP_OIL_CONSUMPTION_4 / 60)));
				changeTvHeight(trip5,
						(int) (height * (mCaninfo.TRIP_OIL_CONSUMPTION_5 / 60)));
			} else {
				changeTvHeight(trip0,
						(int) (height * (mCaninfo.TRIP_OIL_CONSUMPTION_0 / 30)));
				changeTvHeight(trip1,
						(int) (height * (mCaninfo.TRIP_OIL_CONSUMPTION_1 / 30)));
				changeTvHeight(trip2,
						(int) (height * (mCaninfo.TRIP_OIL_CONSUMPTION_2 / 30)));
				changeTvHeight(trip3,
						(int) (height * (mCaninfo.TRIP_OIL_CONSUMPTION_3 / 30)));
				changeTvHeight(trip4,
						(int) (height * (mCaninfo.TRIP_OIL_CONSUMPTION_4 / 30)));
				changeTvHeight(trip5,
						(int) (height * (mCaninfo.TRIP_OIL_CONSUMPTION_5 / 30)));
			}

		}

	}

	private void changeTvHeight(TextView trip0, int height) {
		if (height > this.height) {
			height = this.height;
		}
		RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) trip0
				.getLayoutParams(); // 取控件textView当前的布局参数
		linearParams.height = height;// 控件的高强制设成20
		trip0.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件</pre>
	}

	@Override
	public void serviceConnected() {
		// TODO Auto-generated method stub
		super.serviceConnected();

	}

	private void initView() {
		trip0 = (TextView) findViewById(R.id.trip0);
		trip1 = (TextView) findViewById(R.id.trip1);
		trip2 = (TextView) findViewById(R.id.trip2);
		trip3 = (TextView) findViewById(R.id.trip3);
		trip4 = (TextView) findViewById(R.id.trip4);
		trip5 = (TextView) findViewById(R.id.trip5);

		unit = (TextView) findViewById(R.id.unit);
		unit0 = (TextView) findViewById(R.id.unit_0);
		unit1 = (TextView) findViewById(R.id.unit_1);
		unit2 = (TextView) findViewById(R.id.unit_2);
		unit3 = (TextView) findViewById(R.id.unit_3);

		trip_content = (LinearLayout) findViewById(R.id.trip_content);

		trip_update = (Button) findViewById(R.id.trip_update);
		trip_clear = (Button) findViewById(R.id.trip_clear);

		trip_update.setOnClickListener(this);
		trip_clear.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.trip_update:
			sendMsg("5AA5036A040202");
			break;
		case R.id.trip_clear:
			sendMsg("5AA5036A040201");
			break;
		default:
			break;
		}
	}

}
