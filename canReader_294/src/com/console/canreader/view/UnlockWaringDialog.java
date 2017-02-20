package com.console.canreader.view;

import com.console.canreader.R;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class UnlockWaringDialog extends Dialog {
	private View mRootlayout;
	Context context;
	private static CanInfo canInfo;
	private ImageView closeButton;
	private ImageView flDoor;
	private ImageView frDoor;
	private ImageView rlDoor;
	private ImageView rrDoor;
	private ImageView trunk;
	private ImageView hood;
	private TextView doorInfo;

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Contacts.MSG_DIGLOG_HIDE:
				dismiss();
				break;
			default:
				break;
			}
		};
	};

	public UnlockWaringDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public UnlockWaringDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
		getWindow().setWindowAnimations(R.style.DialogAnimation1);
	}

	private View.OnClickListener onClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.close_button:
				dismiss();
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
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mRootlayout = inflater.inflate(R.layout.door_open, null);
		setContentView(mRootlayout);
		closeButton = (ImageView) mRootlayout.findViewById(R.id.close_button);
		flDoor = (ImageView) mRootlayout.findViewById(R.id.flDoor);
		frDoor = (ImageView) mRootlayout.findViewById(R.id.frDoor);
		rlDoor = (ImageView) mRootlayout.findViewById(R.id.rlDoor);
		rrDoor = (ImageView) mRootlayout.findViewById(R.id.rrDoor);
		doorInfo = (TextView) mRootlayout.findViewById(R.id.door_info);
		trunk = (ImageView) mRootlayout.findViewById(R.id.trunk_status);
		hood = (ImageView) mRootlayout.findViewById(R.id.hood_status);
		closeButton.setOnClickListener(onClickListener);
	}

	public void setCanInfo(CanInfo canInfo) {
		this.canInfo = canInfo;
		showStatus(canInfo);
	}

	private void showStatus(CanInfo canInfo) {
		// TODO Auto-generated method stub
		flDoor.setVisibility(View.INVISIBLE);
		frDoor.setVisibility(View.INVISIBLE);
		rlDoor.setVisibility(View.INVISIBLE);
		rrDoor.setVisibility(View.INVISIBLE);
		trunk.setVisibility(View.INVISIBLE);
		hood.setVisibility(View.INVISIBLE);

		if (canInfo.RIGHT_BACKDOOR_STATUS == 1)
			rrDoor.setVisibility(View.VISIBLE);
		if (canInfo.LEFT_BACKDOOR_STATUS == 1)
			rlDoor.setVisibility(View.VISIBLE);
		if (canInfo.RIGHT_FORONTDOOR_STATUS == 1)
			frDoor.setVisibility(View.VISIBLE);
		if (canInfo.LEFT_FORONTDOOR_STATUS == 1)
			flDoor.setVisibility(View.VISIBLE);
		if (canInfo.TRUNK_STATUS == 1)
			trunk.setVisibility(View.VISIBLE);
		if (canInfo.HOOD_STATUS == 1)
			hood.setVisibility(View.VISIBLE);

		if (canInfo.RIGHT_BACKDOOR_STATUS == 0
				&& canInfo.LEFT_BACKDOOR_STATUS == 0
				&& canInfo.RIGHT_FORONTDOOR_STATUS == 0
				&& canInfo.LEFT_FORONTDOOR_STATUS == 0
				&& canInfo.TRUNK_STATUS == 0 && canInfo.HOOD_STATUS == 0) {
			mHandler.sendEmptyMessageDelayed(Contacts.MSG_DIGLOG_HIDE, 100);
		} else {
			mHandler.removeMessages(Contacts.MSG_DIGLOG_HIDE);
		}

	}

}
