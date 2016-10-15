package com.console.canreader.activity.JAC;

import java.util.ArrayList;
import java.util.List;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
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

	private TextView temperature_fl;
	private TextView temperature_fr;
	private TextView temperature_bl;
	private TextView temperature_br;

	public int TPMS_FL_WARING = 0; // 前左车轮报警
	public int TPMS_FR_WARING = 0; // 前右车轮报警
	public int TPMS_BL_WARING = 0; // 后左车轮报警
	public int TPMS_BR_WARING = 0; // 后右车轮报警

	private AlphaAnimation alphaAnimation = null;
	
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
		setContentView(R.layout.tryepressure_home);
		initView();
		
		alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
		alphaAnimation.setDuration(500);
		alphaAnimation.setRepeatCount(Animation.INFINITE);
		alphaAnimation.setRepeatMode(Animation.REVERSE);
		alphaAnimation.start();
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
		stopAnim(pressure_fl);
		stopAnim(pressure_fr);
		stopAnim(pressure_bl);
		stopAnim(pressure_br);
	}

	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		super.show(mCaninfo);
		if (mCaninfo != null) {
			updateTextView(pressure_fl, mCaninfo.TPMS_FL_PRESSUE
					+ getString(R.string.pressure_unit));
			updateTextView(pressure_fr, mCaninfo.TPMS_FR_PRESSUE
					+ getString(R.string.pressure_unit));
			updateTextView(pressure_bl, mCaninfo.TPMS_BL_PRESSUE
					+ getString(R.string.pressure_unit));
			updateTextView(pressure_br, mCaninfo.TPMS_BR_PRESSUE
					+ getString(R.string.pressure_unit));

			updateTextView(temperature_fl, mCaninfo.TPMS_FL_TEMP
					+ getString(R.string.tpmstemp_unit));
			updateTextView(temperature_fr, mCaninfo.TPMS_FR_TEMP
					+ getString(R.string.tpmstemp_unit));
			updateTextView(temperature_bl, mCaninfo.TPMS_BL_TEMP
					+ getString(R.string.tpmstemp_unit));
			updateTextView(temperature_br, mCaninfo.TPMS_BR_TEMP
					+ getString(R.string.tpmstemp_unit));

			if (TPMS_FL_WARING != mCaninfo.TPMS_FL_WARING) {
				TPMS_FL_WARING = mCaninfo.TPMS_FL_WARING;
				syncWaringStatus(TPMS_FL_WARING,pressure_fl,temperature_fl);
			}
			
			if (TPMS_FR_WARING != mCaninfo.TPMS_FR_WARING) {
				TPMS_FR_WARING = mCaninfo.TPMS_FR_WARING;
				syncWaringStatus(TPMS_FR_WARING,pressure_fr,temperature_fr);
			}
			if (TPMS_BL_WARING != mCaninfo.TPMS_BL_WARING) {
				TPMS_BL_WARING = mCaninfo.TPMS_BL_WARING;
				syncWaringStatus(TPMS_BL_WARING,pressure_bl,temperature_bl);
			}
			if (TPMS_BR_WARING != mCaninfo.TPMS_BR_WARING) {
				TPMS_BR_WARING = mCaninfo.TPMS_BR_WARING;
				syncWaringStatus(TPMS_BR_WARING,pressure_br,temperature_br);
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

		temperature_fl = (TextView) findViewById(R.id.temperature_fl);
		temperature_fr = (TextView) findViewById(R.id.temperature_fr);
		temperature_bl = (TextView) findViewById(R.id.temperature_bl);
		temperature_br = (TextView) findViewById(R.id.temperature_br);

	}

	private void updateTextView(TextView textView, String str) {
		if (!textView.getText().equals(str)) {
			textView.setText(str);
		}
	}
	
	// 闪烁动画
	private void startAnim(View view) {
		if (alphaAnimation != null) {
			view.setAnimation(alphaAnimation);
		}
	}

	// 停止动画
	private void stopAnim(View view) {
		view.clearAnimation();
	}
	
	private void syncWaringStatus(int waringStatus,TextView pressure,TextView temp){
		stopAnim(pressure);
		switch (waringStatus) {
		case 0:
			pressure.setTextColor(getResources().getColor(R.color.colorText));
			temp.setTextColor(getResources().getColor(R.color.colorText));
			break;
		case 1:
			pressure.setTextColor(Color.RED);
			temp.setTextColor(Color.RED);					
			break;
		case 2:
			pressure.setTextColor(Color.RED);
			startAnim(pressure);
			temp.setTextColor(getResources().getColor(R.color.colorText));
			break;
		case 3:
			pressure.setTextColor(Color.YELLOW);
			temp.setTextColor(Color.YELLOW);	
			break;
		default:
			break;
		}
	}

}
