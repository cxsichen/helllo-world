package com.console.canreader.fragment.SSChery;

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

public class AirContorlActivity extends AirConBaseActivity implements OnClickListener,OnTouchListener {

	private PopupWindow popupWindow;
	private View popupWindowView;

	private ImageView ic_menu;
	private ImageView LEFT_SEAT_TEMP;
	private ImageView MAX_FRONT_LAMP_INDICATOR;
	private ImageView CYCLE_INDICATOR;
	private ImageView REAR_LAMP_INDICATOR;
	private ImageView RIGTHT_SEAT_TEMP;
	private TextView OUTSIDE_TEMPERATURE;
	private TextView SYNC;

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
		setContentView(R.layout.ac_controler_sscherry);
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
		Mono_STATUS.setOnTouchListener(this);

	}

	private void initView() {
		SYNC=(TextView) findViewById(R.id.SYNC);
		SYNC.setOnTouchListener(this);
		
		AIR_RATE_iv = (ImageView) findViewById(R.id.AIR_RATE_iv);

		AC_INDICATOR_STATUS = (TextView) findViewById(R.id.AC_INDICATOR_STATUS);
		AC_INDICATOR_STATUS.setOnTouchListener(this);

		CYCLE_INDICATOR = (ImageView) findViewById(R.id.CYCLE_INDICATOR);
		CYCLE_INDICATOR.setOnTouchListener(this);

		AUTO_STATUS = (TextView) findViewById(R.id.AUTO_STATUS);
		AUTO_STATUS.setOnTouchListener(this);

		MAX_FRONT_LAMP_INDICATOR = (ImageView) findViewById(R.id.MAX_FRONT_LAMP_INDICATOR);
		MAX_FRONT_LAMP_INDICATOR.setOnTouchListener(this);

		REAR_LAMP_INDICATOR = (ImageView) findViewById(R.id.REAR_LAMP_INDICATOR);
		REAR_LAMP_INDICATOR.setVisibility(View.INVISIBLE);

		UPWARD_AIR_INDICATOR = (ImageView) findViewById(R.id.UPWARD_AIR_INDICATOR);
		PARALLEL_AIR_INDICATOR = (ImageView) findViewById(R.id.PARALLEL_AIR_INDICATOR);
		DOWNWARD_AIR_INDICATOR = (ImageView) findViewById(R.id.DOWNWARD_AIR_INDICATOR);

		UPWARD_AIR_INDICATOR.setOnTouchListener(this);
		PARALLEL_AIR_INDICATOR.setOnTouchListener(this);
		DOWNWARD_AIR_INDICATOR.setOnTouchListener(this);

		AIR_RATE_UP = (ImageView) findViewById(R.id.AIR_RATE_UP);
		AIR_RATE = (TextView) findViewById(R.id.AIR_RATE);
		AIR_RATE_DOWN = (ImageView) findViewById(R.id.AIR_RATE_DOWN);

		AIR_RATE_UP.setOnTouchListener(this);
		AIR_RATE_DOWN.setOnTouchListener(this);

		left_temp_up = (ImageView) findViewById(R.id.left_temp_up);
		left_temp_value = (TextView) findViewById(R.id.left_temp_value);
		left_temp_down = (ImageView) findViewById(R.id.left_temp_down);

		right_temp_up = (ImageView) findViewById(R.id.right_temp_up);
		right_temp_value = (TextView) findViewById(R.id.right_temp_value);
		right_temp_down = (ImageView) findViewById(R.id.right_temp_down);

		left_temp_up.setOnTouchListener(this);
		left_temp_down.setOnTouchListener(this);

		right_temp_up.setOnTouchListener(this);
		right_temp_down.setOnTouchListener(this);

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

			SYNC
			.setBackgroundResource(mCaninfo.DAUL_LAMP_INDICATOR == 0 ? R.drawable.bg_button_oval
					: R.drawable.bg_button_oval_on);
			AUTO_STATUS
					.setBackgroundResource(mCaninfo.SMALL_LANTERN_INDICATOR == 0 ? R.drawable.bg_button_oval
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

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.Mono_STATUS:
			if(event.getAction()==MotionEvent.ACTION_DOWN)
			    sendMsg("5AA5023D0101");
			if(event.getAction()==MotionEvent.ACTION_UP)
				sendMsg("5AA5023D0100");
			break;
		case R.id.SYNC:
			if(event.getAction()==MotionEvent.ACTION_DOWN)
			    sendMsg("5AA5023D0301");
			if(event.getAction()==MotionEvent.ACTION_UP)
				sendMsg("5AA5023D0300");
			break;
		case R.id.AC_INDICATOR_STATUS:
			if(event.getAction()==MotionEvent.ACTION_DOWN)
			    sendMsg("5AA5023D0201");
			if(event.getAction()==MotionEvent.ACTION_UP)
				sendMsg("5AA5023D0200");
			break;
		case R.id.AUTO_STATUS:
			if(event.getAction()==MotionEvent.ACTION_DOWN)
			    sendMsg("5AA5023D0401");
			if(event.getAction()==MotionEvent.ACTION_UP)
				sendMsg("5AA5023D0400");
			break;
		case R.id.MAX_FRONT_LAMP_INDICATOR:
			if(event.getAction()==MotionEvent.ACTION_DOWN)
			    sendMsg("5AA5023D0501");
			if(event.getAction()==MotionEvent.ACTION_UP)
				sendMsg("5AA5023D0500");
			break;
		case R.id.CYCLE_INDICATOR:
			if(event.getAction()==MotionEvent.ACTION_DOWN)
			    sendMsg("5AA5023D0701");
			if(event.getAction()==MotionEvent.ACTION_UP)
				sendMsg("5AA5023D0700");
			break;
		case R.id.UPWARD_AIR_INDICATOR:
			if(event.getAction()==MotionEvent.ACTION_DOWN)
			    sendMsg("5AA5023D1B01");
			if(event.getAction()==MotionEvent.ACTION_UP)
				sendMsg("5AA5023D1B00");
			break;
		case R.id.PARALLEL_AIR_INDICATOR:
			if(event.getAction()==MotionEvent.ACTION_DOWN)
			    sendMsg("5AA5023D1901");
			if(event.getAction()==MotionEvent.ACTION_UP)
				sendMsg("5AA5023D1900");
			break;
		case R.id.DOWNWARD_AIR_INDICATOR:
			if(event.getAction()==MotionEvent.ACTION_DOWN)
			    sendMsg("5AA5023D1C01");
			if(event.getAction()==MotionEvent.ACTION_UP)
				sendMsg("5AA5023D1C00");
			break;
		case R.id.AIR_RATE_UP:
			if(event.getAction()==MotionEvent.ACTION_DOWN)
			    sendMsg("5AA5023D0B01");
			if(event.getAction()==MotionEvent.ACTION_UP)
				sendMsg("5AA5023D0B00");
			break;
		case R.id.AIR_RATE_DOWN:
			if(event.getAction()==MotionEvent.ACTION_DOWN)
			    sendMsg("5AA5023D0C01");
			if(event.getAction()==MotionEvent.ACTION_UP)
				sendMsg("5AA5023D0C00");
			break;
		case R.id.left_temp_up:
			if(event.getAction()==MotionEvent.ACTION_DOWN)
			    sendMsg("5AA5023D0D01");
			if(event.getAction()==MotionEvent.ACTION_UP)
				sendMsg("5AA5023D0D00");
			break;
		case R.id.left_temp_down:
			if(event.getAction()==MotionEvent.ACTION_DOWN)
			    sendMsg("5AA5023D0E01");
			if(event.getAction()==MotionEvent.ACTION_UP)
				sendMsg("5AA5023D0E00");
			break;
		case R.id.right_temp_up:
			if(event.getAction()==MotionEvent.ACTION_DOWN)
			    sendMsg("5AA5023D0F01");
			if(event.getAction()==MotionEvent.ACTION_UP)
				sendMsg("5AA5023D0F00");
			break;
		case R.id.right_temp_down:
			if(event.getAction()==MotionEvent.ACTION_DOWN)
			    sendMsg("5AA5023D1001");
			if(event.getAction()==MotionEvent.ACTION_UP)
				sendMsg("5AA5023D1000");
			break;
		default:
			break;
		}
		return true;
	}

}
