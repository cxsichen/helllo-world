package com.console.canreader.fragment.SSMGGS;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.Window;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.console.canreader.R;
import com.console.canreader.activity.AirConBaseActivity;
import com.console.canreader.activity.BaseActivity;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;

public class AirContorlActivity extends AirConBaseActivity implements OnClickListener {

	private PopupWindow popupWindow;
	private View popupWindowView;

	private ImageView ic_menu;
	private ImageView LEFT_SEAT_TEMP;
	private ImageView MAX_FRONT_LAMP_INDICATOR;
	private ImageView CYCLE_INDICATOR;
	private ImageView REAR_LAMP_INDICATOR;
	private ImageView RIGTHT_SEAT_TEMP;
	private TextView OUTSIDE_TEMPERATURE;

	private ImageView left_temp_up;
	private TextView left_temp_value;
	private ImageView left_temp_down;

	private ImageView right_temp_up;
	private TextView right_temp_value;
	private ImageView right_temp_down;

	private ImageView UPWARD_AIR_INDICATOR;
	private ImageView PARALLEL_AIR_INDICATOR;
	private ImageView DOWNWARD_AIR_INDICATOR;

	private TextView AUTO_STATUS;
	private TextView AC_INDICATOR_STATUS;

	private ImageView AIR_RATE_UP;
	private TextView AIR_RATE;
	private ImageView AIR_RATE_DOWN;

	private TextView Mono_STATUS;

	private Animation amt;
	private int airConStatus = -1;
	private ImageView AIR_RATE_iv;

	private int[] leftSeatDraws = { R.drawable.stat_seat_heating_left_1,
			R.drawable.stat_seat_heating_left_2,
			R.drawable.stat_seat_heating_left_3 };
	private int[] rightSeatDraws = { R.drawable.stat_seat_heating_right_1,
			R.drawable.stat_seat_heating_right_2,
			R.drawable.stat_seat_heating_right_3 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_controler_ssmgge);
		amt = AnimationUtils.loadAnimation(this, R.anim.tip);
		amt.setInterpolator(new LinearInterpolator());
		initView();
		initPopuptWindow();
		try {
			syncView(mCaninfo);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 创建PopupWindow
	 */
	protected void initPopuptWindow() {
		// 获取自定义布局文件ac_more_peugeot_408.xml的视图
		popupWindowView = getLayoutInflater().inflate(R.layout.ac_more_ssmggs,
				null, false);
		// 点击其他地方消失
		popupWindowView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (popupWindow != null && popupWindow.isShowing()) {
					popupWindow.dismiss();
					popupWindow = null;
				}
				return false;
			}
		});
		Mono_STATUS = (TextView) popupWindowView.findViewById(R.id.Mono_STATUS);
		Mono_STATUS.setOnClickListener(this);

	}

	private void initView() {
		AIR_RATE_iv = (ImageView) findViewById(R.id.AIR_RATE_iv);

		AC_INDICATOR_STATUS = (TextView) findViewById(R.id.AC_INDICATOR_STATUS);
		AC_INDICATOR_STATUS.setOnClickListener(this);

		CYCLE_INDICATOR = (ImageView) findViewById(R.id.CYCLE_INDICATOR);
		CYCLE_INDICATOR.setOnClickListener(this);

		AUTO_STATUS = (TextView) findViewById(R.id.AUTO_STATUS);
		AUTO_STATUS.setOnClickListener(this);

		MAX_FRONT_LAMP_INDICATOR = (ImageView) findViewById(R.id.MAX_FRONT_LAMP_INDICATOR);
		MAX_FRONT_LAMP_INDICATOR.setOnClickListener(this);

		REAR_LAMP_INDICATOR = (ImageView) findViewById(R.id.REAR_LAMP_INDICATOR);
		REAR_LAMP_INDICATOR.setOnClickListener(this);

		UPWARD_AIR_INDICATOR = (ImageView) findViewById(R.id.UPWARD_AIR_INDICATOR);
		PARALLEL_AIR_INDICATOR = (ImageView) findViewById(R.id.PARALLEL_AIR_INDICATOR);
		DOWNWARD_AIR_INDICATOR = (ImageView) findViewById(R.id.DOWNWARD_AIR_INDICATOR);

		UPWARD_AIR_INDICATOR.setOnClickListener(this);
		PARALLEL_AIR_INDICATOR.setOnClickListener(this);
		DOWNWARD_AIR_INDICATOR.setOnClickListener(this);

		AIR_RATE_UP = (ImageView) findViewById(R.id.AIR_RATE_UP);
		AIR_RATE = (TextView) findViewById(R.id.AIR_RATE);
		AIR_RATE_DOWN = (ImageView) findViewById(R.id.AIR_RATE_DOWN);

		AIR_RATE_UP.setOnClickListener(this);
		AIR_RATE_DOWN.setOnClickListener(this);

		left_temp_up = (ImageView) findViewById(R.id.left_temp_up);
		left_temp_value = (TextView) findViewById(R.id.left_temp_value);
		left_temp_down = (ImageView) findViewById(R.id.left_temp_down);

		right_temp_up = (ImageView) findViewById(R.id.right_temp_up);
		right_temp_value = (TextView) findViewById(R.id.right_temp_value);
		right_temp_down = (ImageView) findViewById(R.id.right_temp_down);

		left_temp_up.setOnClickListener(this);
		left_temp_down.setOnClickListener(this);

		right_temp_up.setOnClickListener(this);
		right_temp_down.setOnClickListener(this);

		LEFT_SEAT_TEMP = (ImageView) findViewById(R.id.LEFT_SEAT_TEMP);
		RIGTHT_SEAT_TEMP = (ImageView) findViewById(R.id.RIGTHT_SEAT_TEMP);
		LEFT_SEAT_TEMP.setVisibility(View.INVISIBLE);
		RIGTHT_SEAT_TEMP.setVisibility(View.INVISIBLE);

		OUTSIDE_TEMPERATURE = (TextView) findViewById(R.id.OUTSIDE_TEMPERATURE);

		ic_menu = (ImageView) findViewById(R.id.ic_menu);
		ic_menu.setOnClickListener(this);
		/*--------------------------*/

	}

	public void syncView(CanInfo mCaninfo) {
		try {

			/*--------------------------------------*/
			Mono_STATUS
					.setBackgroundResource(mCaninfo.AIR_CONDITIONER_STATUS == 0 ? R.drawable.bg_button_oval
							: R.drawable.bg_button_oval_on);
			Mono_STATUS.setText(mCaninfo.AIR_CONDITIONER_STATUS == 0 ? "关"
					: "开");
			if (airConStatus != mCaninfo.AIR_CONDITIONER_STATUS) {
				airConStatus = mCaninfo.AIR_CONDITIONER_STATUS;
				if (airConStatus == 1) {
					AIR_RATE_iv.startAnimation(amt);
				} else {
					AIR_RATE_iv.clearAnimation();
				}
			}
			AC_INDICATOR_STATUS
					.setBackgroundResource(mCaninfo.AC_INDICATOR_STATUS == 0 ? R.drawable.bg_button_oval
							: R.drawable.bg_button_oval_on);

			AUTO_STATUS
					.setBackgroundResource(mCaninfo.AUTO_STATUS == 0 ? R.drawable.bg_button_oval
							: R.drawable.bg_button_oval_on);

			if (mCaninfo.CYCLE_INDICATOR == 0) {
				CYCLE_INDICATOR.setImageResource(R.drawable.stat_recirculation);
			} else {
				CYCLE_INDICATOR
						.setImageResource(R.drawable.stat_recirculation_outside);
			}
			MAX_FRONT_LAMP_INDICATOR
					.setAlpha((float) (mCaninfo.MAX_FRONT_LAMP_INDICATOR == 0 ? 0.3
							: 1));
			REAR_LAMP_INDICATOR
					.setAlpha((float) (mCaninfo.REAR_LAMP_INDICATOR == 0 ? 0.3
							: 1));
			UPWARD_AIR_INDICATOR
					.setBackgroundResource(mCaninfo.UPWARD_AIR_INDICATOR == 0 ? R.drawable.bg_button_oval
							: R.drawable.bg_button_oval_on);
			PARALLEL_AIR_INDICATOR
					.setBackgroundResource(mCaninfo.PARALLEL_AIR_INDICATOR == 0 ? R.drawable.bg_button_oval
							: R.drawable.bg_button_oval_on);
			DOWNWARD_AIR_INDICATOR
					.setBackgroundResource(mCaninfo.DOWNWARD_AIR_INDICATOR == 0 ? R.drawable.bg_button_oval
							: R.drawable.bg_button_oval_on);
			AIR_RATE.setText(String.valueOf(mCaninfo.AIR_RATE));

			if (mCaninfo.DRIVING_POSITON_TEMP == 0) {
				left_temp_value.setText("LOW");
			} else if (mCaninfo.DRIVING_POSITON_TEMP == 255) {
				left_temp_value.setText("HIGH");
			} else {
				left_temp_value.setText(mCaninfo.DRIVING_POSITON_TEMP + "°");
			}
			if (mCaninfo.DEPUTY_DRIVING_POSITON_TEMP == 0) {
				right_temp_value.setText("LOW");
			} else if (mCaninfo.DEPUTY_DRIVING_POSITON_TEMP == 255) {
				right_temp_value.setText("HIGH");
			} else {
				right_temp_value.setText(mCaninfo.DEPUTY_DRIVING_POSITON_TEMP
						+ "°");
			}
			OUTSIDE_TEMPERATURE
					.setText(mCaninfo.OUTSIDE_TEMPERATURE + "℃\nOUT");


		} catch (Exception e) {
			// TODO: handle exception
		}
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

	@Override
	public void onClick(View v) {
		try {
			switch (v.getId()) {
			case R.id.ic_menu:
				showPopupWindow();
				popupWindow.showAtLocation(v, Gravity.LEFT, 0, 0);
				break;
			case R.id.Mono_STATUS:
				sendMsg("5AA5023B10FF");
				break;
			case R.id.AC_INDICATOR_STATUS:
				int v1 = mCaninfo.AC_INDICATOR_STATUS == 0 ? 1 : 0;
				sendMsg("5AA5023B02" + BytesUtil.intToHexString(v1));
				break;
			case R.id.AUTO_STATUS:
				int v2 = mCaninfo.AUTO_STATUS == 0 ? 1 : 0;
				sendMsg("5AA5023B04" + BytesUtil.intToHexString(v2));
				break;
			case R.id.CYCLE_INDICATOR:
				int v6 = mCaninfo.CYCLE_INDICATOR == 0 ? 1 : 0;
				sendMsg("5AA5023B07" + BytesUtil.intToHexString(v6));
				break;
			case R.id.MAX_FRONT_LAMP_INDICATOR:
				int v3 = mCaninfo.MAX_FRONT_LAMP_INDICATOR == 0 ? 1 : 0;
				sendMsg("5AA5023B05" + BytesUtil.intToHexString(v3));
				break;
			case R.id.REAR_LAMP_INDICATOR:
				int v5 = mCaninfo.REAR_LAMP_INDICATOR == 0 ? 1 : 0;
				sendMsg("5AA5023B06" + BytesUtil.intToHexString(v5));
				break;
			case R.id.UPWARD_AIR_INDICATOR:
				sendMsg("5AA5023B0FFF");
				break;
			case R.id.PARALLEL_AIR_INDICATOR:
				sendMsg("5AA5023B0FFF");
				break;
			case R.id.DOWNWARD_AIR_INDICATOR:
				sendMsg("5AA5023B0FFF");
				break;
			case R.id.AIR_RATE_UP:
				sendMsg("5AA5023B0B01" );
				break;
			case R.id.AIR_RATE_DOWN:
				sendMsg("5AA5023B0B02" );
				break;
			case R.id.left_temp_up:
				sendMsg("5AA5023B0C01");
				break;
			case R.id.left_temp_down:
				sendMsg("5AA5023B0C02");
				break;
			case R.id.right_temp_up:
				sendMsg("5AA5023B0C01");
				break;
			case R.id.right_temp_down:
				sendMsg("5AA5023B0C02");
				break;
			default:
				break;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private void showPopupWindow() {
		// TODO Auto-generated method stub
		// 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
		popupWindow = new PopupWindow(popupWindowView, 200,
				LayoutParams.MATCH_PARENT, true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		// 设置动画效果
		popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
		popupWindow.setFocusable(true);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (AIR_RATE_iv != null)
			AIR_RATE_iv.clearAnimation();
	}

}
