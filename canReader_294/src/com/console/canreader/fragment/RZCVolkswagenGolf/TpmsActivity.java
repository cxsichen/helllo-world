package com.console.canreader.fragment.RZCVolkswagenGolf;

import java.util.ArrayList;
import java.util.List;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
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

public class TpmsActivity extends BaseActivity {

	private TextView pressure_fl;
	private TextView pressure_fr;
	private TextView pressure_bl;
	private TextView pressure_br;

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
		setContentView(R.layout.tryepressure_home_1);
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
			if (mCaninfo.TPMS_FL_WARING == 0) {
				pressure_fl.setText("左前胎压\n正常 ");
				pressure_fl.setTextColor(Color.WHITE);
			} else if (mCaninfo.TPMS_FL_WARING == 1) {
				pressure_fl.setText("左前胎压\n欠压 ");
				pressure_fl.setTextColor(Color.RED);
			} else if (mCaninfo.TPMS_FL_WARING == 2) {
				pressure_fl.setText("左前胎压\n过高 ");
				pressure_fl.setTextColor(Color.RED);
			}

			if (mCaninfo.TPMS_FR_WARING == 0) {
				pressure_fr.setText("右前胎压\n正常 ");
				pressure_fr.setTextColor(Color.WHITE);
			} else if (mCaninfo.TPMS_FR_WARING == 1) {
				pressure_fr.setText("右前胎压\n欠压 ");
				pressure_fr.setTextColor(Color.RED);
			} else if (mCaninfo.TPMS_FR_WARING == 2) {
				pressure_fr.setText("右前胎压\n过高 ");
				pressure_fr.setTextColor(Color.RED);
			}

			if (mCaninfo.TPMS_BL_WARING == 0) {
				pressure_bl.setText("左后胎压\n正常 ");
				pressure_bl.setTextColor(Color.WHITE);
			} else if (mCaninfo.TPMS_BL_WARING == 1) {
				pressure_bl.setText("左后胎压\n欠压 ");
				pressure_bl.setTextColor(Color.RED);
			} else if (mCaninfo.TPMS_BL_WARING == 2) {
				pressure_bl.setText("左后胎压\n过高 ");
				pressure_bl.setTextColor(Color.RED);
			}

			if (mCaninfo.TPMS_BR_WARING == 0) {
				pressure_br.setText("右后胎压\n正常 ");
				pressure_br.setTextColor(Color.WHITE);
			} else if (mCaninfo.TPMS_BR_WARING == 1) {
				pressure_br.setText("右后胎压\n欠压 ");
				pressure_br.setTextColor(Color.RED);
			} else if (mCaninfo.TPMS_BR_WARING == 2) {
				pressure_br.setText("右后胎压\n过高 ");
				pressure_br.setTextColor(Color.RED);
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
		pressure_fl = (TextView) findViewById(R.id.pressure_fl);
		pressure_fr = (TextView) findViewById(R.id.pressure_fr);
		pressure_bl = (TextView) findViewById(R.id.pressure_bl);
		pressure_br = (TextView) findViewById(R.id.pressure_br);

	}

}
