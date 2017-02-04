package com.console.dtv;

import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class ButtonControl implements OnClickListener {

	MainActivity activity;
	RelativeLayout controlLayout;

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				controlLayout.setVisibility(View.INVISIBLE);
				break;
			default:
				break;
			}
		};
	};

	public ButtonControl(MainActivity activity) {
		this.activity = activity;
		initButton();
	}

	private void initButton() {
		// TODO Auto-generated method stub
		activity.findViewById(R.id.bt_up).setOnClickListener(this);
		activity.findViewById(R.id.bt_left).setOnClickListener(this);
		activity.findViewById(R.id.bt_enter).setOnClickListener(this);
		activity.findViewById(R.id.bt_right).setOnClickListener(this);
		activity.findViewById(R.id.bt_down).setOnClickListener(this);

		activity.findViewById(R.id.bt_back).setOnClickListener(this);
		// activity.findViewById(R.id.bt_ok).setOnClickListener(this);
		activity.findViewById(R.id.bt_menu_down).setOnClickListener(this);
		activity.findViewById(R.id.bt_menu_up).setOnClickListener(this);
		activity.findViewById(R.id.bt_band).setOnClickListener(this);
		activity.findViewById(R.id.bt_mute).setOnClickListener(this);
		activity.findViewById(R.id.bt_title).setOnClickListener(this);
		activity.findViewById(R.id.layout1).setOnClickListener(this);
		controlLayout = (RelativeLayout) activity
				.findViewById(R.id.control_layout);
		activity.findViewById(R.id.dtv_layout).setOnClickListener(this);
	}

	String str = "";

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		str = "";
		switch (v.getId()) {
		case R.id.layout1:
			break;
		case R.id.dtv_layout:
			if (controlLayout.getVisibility() == View.INVISIBLE) {
				controlLayout.setVisibility(View.VISIBLE);
			} else {
				controlLayout.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.bt_up:
			str = "28";
			break;
		case R.id.bt_left:
			str = "10";
			break;
		case R.id.bt_enter:
			str = "15";
			break;
		case R.id.bt_right:
			str = "2e";
			break;
		case R.id.bt_down:
			str = "29";
			break;
		case R.id.bt_back:
			str = "0D";
			break;
		/*
		 * case R.id.bt_ok: str = "15"; break;
		 */
		case R.id.bt_menu_down:
			str = "1E";
			break;
		case R.id.bt_menu_up:
			str = "1C";
			break;
		case R.id.bt_band:
			str = "4B";
			break;
		case R.id.bt_mute:
			str = "10";
			break;
		case R.id.bt_title:
			str = "33";
			break;
		default:
			break;
		}
		if (activity != null && !str.equals("")) {
			if (activity.mSerialPortControl != null) {
				activity.mSerialPortControl.sendMsg("f00000" + str + "08");
			}
		}
		mHandler.removeMessages(1);
		mHandler.sendEmptyMessageDelayed(1, 1000 * 10);
	}
}
