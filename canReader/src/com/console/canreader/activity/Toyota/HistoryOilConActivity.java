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

public class HistoryOilConActivity extends BaseActivity implements OnClickListener {

	private TextView his1;
	private TextView his2;
	private TextView his3;
	private TextView his4;
	private TextView his5;
	
	private TextView his6;
	private TextView his7;
	private TextView his8;
	private TextView his9;
	private TextView his10;
	
	private TextView his11;
	private TextView his12;
	private TextView his13;
	private TextView his14;
	private TextView his15;

	private TextView unit;
	private TextView unit0;
	private TextView unit1;
	private TextView unit2;
	private TextView unit3;

	Button his_clear;

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
		setContentView(R.layout.history_oil_layout);
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
			switch (mCaninfo.HISTORY_OIL_CONSUMPTION_UNIT) {
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

			if (mCaninfo.HISTORY_OIL_CONSUMPTION_UNIT == 0) {
				changeTvHeight(his1,
						(int) (height * (mCaninfo.HISTORY_OIL_CONSUMPTION_1 / 60)));
				changeTvHeight(his2,
						(int) (height * (mCaninfo.HISTORY_OIL_CONSUMPTION_2 / 60)));
				changeTvHeight(his3,
						(int) (height * (mCaninfo.HISTORY_OIL_CONSUMPTION_3 / 60)));
				changeTvHeight(his4,
						(int) (height * (mCaninfo.HISTORY_OIL_CONSUMPTION_4 / 60)));
				changeTvHeight(his5,
						(int) (height * (mCaninfo.HISTORY_OIL_CONSUMPTION_5 / 60)));
				
				changeTvHeight(his6,
						(int) (height * (mCaninfo.HISTORY_OIL_CONSUMPTION_6 / 60)));
				changeTvHeight(his7,
						(int) (height * (mCaninfo.HISTORY_OIL_CONSUMPTION_7 / 60)));
				changeTvHeight(his8,
						(int) (height * (mCaninfo.HISTORY_OIL_CONSUMPTION_8 / 60)));
				changeTvHeight(his9,
						(int) (height * (mCaninfo.HISTORY_OIL_CONSUMPTION_9 / 60)));
				changeTvHeight(his10,
						(int) (height * (mCaninfo.HISTORY_OIL_CONSUMPTION_10 / 60)));
				
				changeTvHeight(his11,
						(int) (height * (mCaninfo.HISTORY_OIL_CONSUMPTION_11 / 60)));
				changeTvHeight(his12,
						(int) (height * (mCaninfo.HISTORY_OIL_CONSUMPTION_12 / 60)));
				changeTvHeight(his13,
						(int) (height * (mCaninfo.HISTORY_OIL_CONSUMPTION_13 / 60)));
				changeTvHeight(his14,
						(int) (height * (mCaninfo.HISTORY_OIL_CONSUMPTION_14 / 60)));
				changeTvHeight(his15,
						(int) (height * (mCaninfo.HISTORY_OIL_CONSUMPTION_15 / 60)));
				
			} else {
				changeTvHeight(his1,
						(int) (height * (mCaninfo.HISTORY_OIL_CONSUMPTION_1 / 30)));
				changeTvHeight(his2,
						(int) (height * (mCaninfo.HISTORY_OIL_CONSUMPTION_2 / 30)));
				changeTvHeight(his3,
						(int) (height * (mCaninfo.HISTORY_OIL_CONSUMPTION_3 / 30)));
				changeTvHeight(his4,
						(int) (height * (mCaninfo.HISTORY_OIL_CONSUMPTION_4 / 30)));
				changeTvHeight(his5,
						(int) (height * (mCaninfo.HISTORY_OIL_CONSUMPTION_5 / 30)));
				
				changeTvHeight(his6,
						(int) (height * (mCaninfo.HISTORY_OIL_CONSUMPTION_6 / 30)));
				changeTvHeight(his7,
						(int) (height * (mCaninfo.HISTORY_OIL_CONSUMPTION_7 / 30)));
				changeTvHeight(his8,
						(int) (height * (mCaninfo.HISTORY_OIL_CONSUMPTION_8 / 30)));
				changeTvHeight(his9,
						(int) (height * (mCaninfo.HISTORY_OIL_CONSUMPTION_9 / 30)));
				changeTvHeight(his10,
						(int) (height * (mCaninfo.HISTORY_OIL_CONSUMPTION_10 / 30)));
				
				changeTvHeight(his11,
						(int) (height * (mCaninfo.HISTORY_OIL_CONSUMPTION_11 / 30)));
				changeTvHeight(his12,
						(int) (height * (mCaninfo.HISTORY_OIL_CONSUMPTION_12 / 30)));
				changeTvHeight(his13,
						(int) (height * (mCaninfo.HISTORY_OIL_CONSUMPTION_13 / 30)));
				changeTvHeight(his14,
						(int) (height * (mCaninfo.HISTORY_OIL_CONSUMPTION_14 / 30)));
				changeTvHeight(his15,
						(int) (height * (mCaninfo.HISTORY_OIL_CONSUMPTION_15 / 30)));
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
		his1 = (TextView) findViewById(R.id.his1);
		his2 = (TextView) findViewById(R.id.his2);
		his3 = (TextView) findViewById(R.id.his3);
		his4 = (TextView) findViewById(R.id.his4);
		his5 = (TextView) findViewById(R.id.his5);
		
		his6 = (TextView) findViewById(R.id.his6);
		his7 = (TextView) findViewById(R.id.his7);
		his8 = (TextView) findViewById(R.id.his8);
		his9 = (TextView) findViewById(R.id.his9);
		his10 = (TextView) findViewById(R.id.his10);
		
		his11 = (TextView) findViewById(R.id.his11);
		his12 = (TextView) findViewById(R.id.his12);
		his13 = (TextView) findViewById(R.id.his13);
		his14 = (TextView) findViewById(R.id.his14);
		his15 = (TextView) findViewById(R.id.his15);


		unit = (TextView) findViewById(R.id.unit);
		unit0 = (TextView) findViewById(R.id.unit_0);
		unit1 = (TextView) findViewById(R.id.unit_1);
		unit2 = (TextView) findViewById(R.id.unit_2);
		unit3 = (TextView) findViewById(R.id.unit_3);

		trip_content = (LinearLayout) findViewById(R.id.trip_content);

		his_clear = (Button) findViewById(R.id.his_clear);

		his_clear.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.his_clear:
			sendMsg("5AA5036A040101");
			break;
		default:
			break;
		}
	}

}
