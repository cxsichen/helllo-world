package com.console.launcher_console.control;

import com.console.launcher_console.R;
import com.console.launcher_console.util.BytesUtil;
import com.console.launcher_console.util.PreferenceUtil;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class OtherControl implements OnClickListener{

	private final Context mContext;
	private LinearLayout otherLayout;
	private FrameLayout videoLayout;
	private FrameLayout auxLayout;
	private SerialPortControl mSerialPortControl;
	

	public OtherControl(Context context, LinearLayout layout,SerialPortControl mSerialPortControl) {
		// TODO Auto-generated constructor stub
		mContext = context;
		otherLayout = layout;
		this.mSerialPortControl=mSerialPortControl;
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		videoLayout = (FrameLayout) otherLayout.findViewById(R.id.video_layout);
		videoLayout.setOnClickListener(this);
		auxLayout = (FrameLayout) otherLayout.findViewById(R.id.aux_layout);
		auxLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.video_layout:
			Intent videoIntent=new Intent();
			videoIntent.setClassName("com.mxtech.videoplayer.pro", "com.mxtech.videoplayer.pro.ActivityMediaList");
			videoIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(videoIntent);
			break;
		case R.id.aux_layout:
			Intent auxIntent=new Intent();
			auxIntent.setClassName("com.console.auxapp", "com.console.auxapp.MainActivity");
			auxIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(auxIntent);
			break;
		default:
			break;
		}
	}


}
