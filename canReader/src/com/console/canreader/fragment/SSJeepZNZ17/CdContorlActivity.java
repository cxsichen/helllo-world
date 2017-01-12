package com.console.canreader.fragment.SSJeepZNZ17;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.console.canreader.R;
import com.console.canreader.activity.AirConBaseActivity;
import com.console.canreader.activity.BaseActivity;
import com.console.canreader.activity.CdConBaseActivity;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;

public class CdContorlActivity extends CdConBaseActivity implements
		OnClickListener, OnTouchListener, OnLongClickListener {

	String[] strG1 = { "ÎÞµú", "²¥·Å", "Èëµú", "ÕýÔÚ¶Áµú", "³öµú", "ÔÝÍ£", "Í£Ö¹", "ÎÞÐ§", "»»Çú",
			"´íÎó" };
	TextView tv0;
	TextView tv1;
	TextView tv2;
	TextView tv3;
	TextView tv4;
	SeekBar mSeekBar;

	ImageView iv1;
	ImageView iv2;
	ImageView iv3;
	ImageView iv4;
	
	Button btn1;
	Button btn2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ssjeep_cd_layout);
		initView();
	}

	private void initView() {
		tv0 = (TextView) findViewById(R.id.tv0);
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv3 = (TextView) findViewById(R.id.tv3);
		tv4 = (TextView) findViewById(R.id.tv4);
		mSeekBar = (SeekBar) findViewById(R.id.sb1);

		iv1 = (ImageView) findViewById(R.id.iv1);
		iv2 = (ImageView) findViewById(R.id.iv2);
		iv3 = (ImageView) findViewById(R.id.iv3);
		iv4 = (ImageView) findViewById(R.id.iv4);

		iv1.setOnClickListener(this);
		iv1.setOnLongClickListener(this);
		iv2.setOnClickListener(this);
		iv3.setOnClickListener(this);
		iv4.setOnClickListener(this);
		iv4.setOnLongClickListener(this);
		
		btn1=(Button) findViewById(R.id.button1);
		btn2=(Button) findViewById(R.id.button2);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
	}

	public void syncView(CanInfo mCaninfo) {
		try {
			if (mCaninfo.MULTI_MEIDA_CD_STATUS == -1) {
				tv0.setText("--");
			} else {
				tv0.setText(strG1[mCaninfo.MULTI_MEIDA_CD_STATUS]);
			}

			tv1.setText(mCaninfo.MULTI_MEIDA_PLAYING_STATUS == 2 ? "Ñ­»·"
					: mCaninfo.MULTI_MEIDA_PLAYING_STATUS == 1 ? "Ëæ»ú" : "--");

			if (mCaninfo.MULTI_MEIDA_WHOLE_NUM == -1) {
				tv2.setText("--");
			} else {
				tv2.setText(mCaninfo.MULTI_MEIDA_WHOLE_NUM + "");
			}

			if (mCaninfo.MULTI_MEIDA_PLAYING_NUM == -1) {
				tv3.setText("--");
			} else {
				tv3.setText(mCaninfo.MULTI_MEIDA_PLAYING_NUM + "");
			}

			if (mCaninfo.MULTI_MEIDA_PLAYING_TIME == -1) {
				tv4.setText("--");
			} else {
				tv4.setText(adjustStr(mCaninfo.MULTI_MEIDA_PLAYING_TIME));
			}

			mSeekBar.setMax(mCaninfo.MULTI_MEIDA_PLAYING_WHOLE_TIME);
			mSeekBar.setProgress((int) (mCaninfo.MULTI_MEIDA_PLAYING_TIME));
			Log.i("cxs","=====mCaninfo.MULTI_MEIDA_PLAYING_WHOLE_TIME=========="+mCaninfo.MULTI_MEIDA_PLAYING_WHOLE_TIME);
			Log.i("cxs","=====mCaninfo.MULTI_MEIDA_PLAYING_TIME=========="+mCaninfo.MULTI_MEIDA_PLAYING_TIME);
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("cxs","======e=========="+e);
		}
	}
	
	String adjustStr(int i){
		String temp1="00";
		String temp2="00";
		if(mCaninfo.MULTI_MEIDA_PLAYING_TIME/60>=1){
			if(mCaninfo.MULTI_MEIDA_PLAYING_TIME/60>=10){
				temp1=String.valueOf(mCaninfo.MULTI_MEIDA_PLAYING_TIME/60);
			}else{
				temp1="0"+String.valueOf(mCaninfo.MULTI_MEIDA_PLAYING_TIME/60);
				
			}
		}
		if(mCaninfo.MULTI_MEIDA_PLAYING_TIME%60>=10){
			temp2=String.valueOf(mCaninfo.MULTI_MEIDA_PLAYING_TIME%60);
		}else{
			temp2="0"+String.valueOf(mCaninfo.MULTI_MEIDA_PLAYING_TIME%60);
		}
		return temp1+":"+temp2;
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
			case R.id.iv1:
				sendMsg("5AA503AF030000");
				break;
			case R.id.iv2:
				sendMsg("5AA503AF010000");
				break;
			case R.id.iv3:
				sendMsg("5AA503AF020000");
				break;
			case R.id.iv4:
				sendMsg("5AA503AF040000");
				break;
			case R.id.button1:
				sendMsg("5AA503AF050100");
				break;
			case R.id.button2:
				sendMsg("5AA503AF060100");
				break;
			default:
				break;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		default:
			break;
		}
		return true;
	}

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		try {
			switch (v.getId()) {
			case R.id.iv1:
				sendMsg("5AA503AF030100");
				break;
			case R.id.iv4:
				sendMsg("5AA503AF040100");
				break;
			default:
				break;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return true;
	}

}
