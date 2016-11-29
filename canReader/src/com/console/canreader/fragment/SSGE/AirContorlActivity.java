package com.console.canreader.fragment.SSGE;

import java.util.ArrayList;
import java.util.List;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.FrameLayout.LayoutParams;
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

public class AirContorlActivity extends BaseActivity implements OnClickListener {

	TextView tv;
	TextView tv1;
	TextView tv2;
	TextView tv10;
	TextView tv11;
	TextView tv12;
	TextView tv13;

	ImageView iv1;
	ImageView iv2;
	ImageView iv3;
	ImageView iv4;
	ImageView iv5;
	ImageView iv6;
	ImageView iv7;
	ImageView iv8;
	ImageView iv9;
	ImageView iv10;
	ImageView iv11;
	CanInfo mCanInfo;
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Contacts.MSG_GET_MSG:

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
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ac_controler_ssge);
		initView();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// mHandler.sendEmptyMessageDelayed(Contacts.MSG_GET_MSG, 2000);
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
		if (mCaninfo != null) {
			this.mCanInfo = mCaninfo;
			if (mCaninfo.AC_INDICATOR_STATUS == 1) {
				tv.setBackgroundResource(R.drawable.bg_button_oval_on);
			} else {
				tv.setBackgroundResource(R.drawable.bg_button_oval);
			}

			if (mCaninfo.LARGE_LANTERN_INDICATOR == 1) {
				tv1.setBackgroundResource(R.drawable.bg_button_oval_on);
			} else {
				tv1.setBackgroundResource(R.drawable.bg_button_oval);
			}

			if (mCaninfo.SMALL_LANTERN_INDICATOR == 1) {
				tv2.setBackgroundResource(R.drawable.bg_button_oval_on);
			} else {
				tv2.setBackgroundResource(R.drawable.bg_button_oval);
			}

			tv10.setText(String.valueOf(mCanInfo.DRIVING_POSITON_TEMP) + "°„");

			tv11.setText(String.valueOf(mCanInfo.DEPUTY_DRIVING_POSITON_TEMP)
					+ "°„");

			if (mCanInfo.DRIVING_POSITON_TEMP == 0)
				tv10.setText("L0");
			if (mCanInfo.DEPUTY_DRIVING_POSITON_TEMP == 0)
				tv11.setText("L0");
			if (mCanInfo.DRIVING_POSITON_TEMP == 255)
				tv10.setText("HI");
			if (mCanInfo.DEPUTY_DRIVING_POSITON_TEMP == 255)
				tv11.setText("HI");

			if (mCanInfo.AIR_RATE == -1) {
				// tv12.setTextSize(22);
				tv12.setText("Auto");
			} else {
				// tv12.setTextSize(42);
				tv12.setText(String.valueOf(mCanInfo.AIR_RATE));
			}

			tv13.setText(mCanInfo.OUTSIDE_TEMPERATURE
					+ getString(R.string.temp_unit) + "\n" + "OUT");

			// ƒ⁄Õ‚—≠ª∑
			if (mCanInfo.CYCLE_INDICATOR == 0) {
				iv8.setAlpha(1f);
				iv8.setImageResource(R.drawable.stat_recirculation_outside);
			} else if (mCanInfo.CYCLE_INDICATOR == 1) {
				iv8.setAlpha(1f);
				iv8.setImageResource(R.drawable.stat_recirculation);
			} else if (mCanInfo.CYCLE_INDICATOR == 2) {
				iv8.setAlpha(0.12f);
			}

			if (mCaninfo.UPWARD_AIR_INDICATOR == 1) {
				iv9.setBackgroundResource(R.drawable.bg_button_oval_on);
			} else {
				iv9.setBackgroundResource(R.drawable.bg_button_oval);
			}

			if (mCaninfo.PARALLEL_AIR_INDICATOR == 1) {
				iv10.setBackgroundResource(R.drawable.bg_button_oval_on);
			} else {
				iv10.setBackgroundResource(R.drawable.bg_button_oval);
			}

			if (mCaninfo.DOWNWARD_AIR_INDICATOR == 1) {
				iv11.setBackgroundResource(R.drawable.bg_button_oval_on);
			} else {
				iv11.setBackgroundResource(R.drawable.bg_button_oval);
			}

		}

	}

	@Override
	public void serviceConnected() {
		// TODO Auto-generated method stub
		super.serviceConnected();
		// mHandler.sendEmptyMessageDelayed(Contacts.MSG_GET_MSG, 1000);
	}

	private void initView() {
		tv = (TextView) findViewById(R.id.ac_tv);
		tv.setOnClickListener(this);
		tv1 = (TextView) findViewById(R.id.auto_tv);
		tv1.setOnClickListener(this);
		tv2 = (TextView) findViewById(R.id.sync_tv);
		tv2.setOnClickListener(this);

		iv1 = (ImageView) findViewById(R.id.iv1);
		iv1.setOnClickListener(this);

		iv2 = (ImageView) findViewById(R.id.iv2);
		iv2.setOnClickListener(this);

		tv10 = (TextView) findViewById(R.id.tv10);

		iv3 = (ImageView) findViewById(R.id.iv3);
		iv3.setOnClickListener(this);

		iv4 = (ImageView) findViewById(R.id.iv4);
		iv4.setOnClickListener(this);

		tv11 = (TextView) findViewById(R.id.tv11);

		iv5 = (ImageView) findViewById(R.id.iv5);
		iv5.setOnClickListener(this);

		iv6 = (ImageView) findViewById(R.id.iv6);
		iv6.setOnClickListener(this);

		tv12 = (TextView) findViewById(R.id.tv12);
		tv13 = (TextView) findViewById(R.id.tv13);

		iv7 = (ImageView) findViewById(R.id.iv7);
		iv7.setOnClickListener(this);

		iv8 = (ImageView) findViewById(R.id.iv8);
		iv8.setOnClickListener(this);

		iv9 = (ImageView) findViewById(R.id.iv9);
		iv9.setOnClickListener(this);
		iv10 = (ImageView) findViewById(R.id.iv10);
		iv10.setOnClickListener(this);
		iv11 = (ImageView) findViewById(R.id.iv11);
		iv11.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (mCanInfo != null) {
			switch (v.getId()) {
			case R.id.ac_tv:
				sendMsg("5AA5023B02"
						+ ((mCaninfo.AC_INDICATOR_STATUS == 1) ? "00" : "01"));
				break;
			case R.id.auto_tv:
				sendMsg("5AA5023B04"
						+ ((mCaninfo.LARGE_LANTERN_INDICATOR == 1) ? "00"
								: "01"));
				break;
			case R.id.sync_tv:
				sendMsg("5AA5023B0F"
						+ ((mCaninfo.SMALL_LANTERN_INDICATOR == 1) ? "00"
								: "01"));
				break;
			case R.id.iv1:
				sendMsg("5AA5023B0C01");
				break;
			case R.id.iv2:
				sendMsg("5AA5023B0C02");
				break;
			case R.id.iv3:
				sendMsg("5AA5023B0D01");
				break;
			case R.id.iv4:
				sendMsg("5AA5023B0D02");
				break;
			case R.id.iv5:
				sendMsg("5AA5023B0B02");
				break;
			case R.id.iv6:
				sendMsg("5AA5023B0B01");
				break;
			case R.id.iv7:
				sendMsg("5AA5023B05FF");
				break;
			case R.id.iv8:
				sendMsg("5AA5023B07"
						+ ((mCaninfo.CYCLE_INDICATOR == 0) ? "00" : "01"));
				break;
			case R.id.iv9:
				sendMsg("5AA5023B22FF");
				break;
			case R.id.iv10:
				if (mCaninfo.DOWNWARD_AIR_INDICATOR == 1) {
					sendMsg("5AA5023B21FF");
				} else {
					sendMsg("5AA5023B09FF");
				}
				break;
			case R.id.iv11:
				if (mCaninfo.UPWARD_AIR_INDICATOR == 1) {
					sendMsg("5AA5023B22FF");
				} else if (mCaninfo.PARALLEL_AIR_INDICATOR == 1) {
					sendMsg("5AA5023B21FF");
				} else {
					sendMsg("5AA5023B0AFF");
				}
				break;
			default:
				break;
			}
		}
	}

}
