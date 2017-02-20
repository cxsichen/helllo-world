package com.console.canreader.fragment.SSPeugeot;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.console.canreader.R;
import com.console.canreader.activity.BaseFragment;
import com.console.canreader.fragment.SSTrumpchi.TrumpchiCarSettingsFragment;
import com.console.canreader.fragment.SSTrumpchi.TrumpchiCarSettingsFragment.SettingsFragment;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;

public class SSPeugeot408CarSpeed1Fragment extends BaseFragment {
	
	private CanInfo mCanInfo;
	private static boolean IsSync=true;
	
	private CheckBox REMEMBER_SPEED_STATUS_ENABLE;
	private CheckBox REMEMBER_SPEED_1_ENABLE;
	private CheckBox REMEMBER_SPEED_2_ENABLE;
	private CheckBox REMEMBER_SPEED_3_ENABLE;
	private CheckBox REMEMBER_SPEED_4_ENABLE;
	private CheckBox REMEMBER_SPEED_5_ENABLE;
	private CheckBox REMEMBER_SPEED_6_ENABLE;

	private CheckBox REMEMBER_SPEED_STATUS;
	private CheckBox REMEMBER_SPEED_1;
	private CheckBox REMEMBER_SPEED_2;
	private CheckBox REMEMBER_SPEED_3;
	private CheckBox REMEMBER_SPEED_4;
	private CheckBox REMEMBER_SPEED_5;
	private CheckBox REMEMBER_SPEED_6;

	private TextView REMEMBER_SPEED_1_VALUE;
	private TextView REMEMBER_SPEED_2_VALUE;
	private TextView REMEMBER_SPEED_3_VALUE;
	private TextView REMEMBER_SPEED_4_VALUE;
	private TextView REMEMBER_SPEED_5_VALUE;
	private TextView REMEMBER_SPEED_6_VALUE;

	private SeekBar REMEMBER_SPEED_1_VALUE_S;
	private SeekBar REMEMBER_SPEED_2_VALUE_S;
	private SeekBar REMEMBER_SPEED_3_VALUE_S;
	private SeekBar REMEMBER_SPEED_4_VALUE_S;
	private SeekBar REMEMBER_SPEED_5_VALUE_S;
	private SeekBar REMEMBER_SPEED_6_VALUE_S;
	
	private OnSeekBarChangeListener seekBarListenenr=new OnSeekBarChangeListener() {
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			if(IsSync){
				return;
			}
			// TODO Auto-generated method stub
			String s1=BytesUtil.intToHexString(REMEMBER_SPEED_1_VALUE_S.getProgress());
			String s2=BytesUtil.intToHexString(REMEMBER_SPEED_2_VALUE_S.getProgress());
			String s3=BytesUtil.intToHexString(REMEMBER_SPEED_3_VALUE_S.getProgress());
			String s4=BytesUtil.intToHexString(REMEMBER_SPEED_4_VALUE_S.getProgress());
			String s5=BytesUtil.intToHexString(REMEMBER_SPEED_5_VALUE_S.getProgress());
			String s6=BytesUtil.intToHexString(REMEMBER_SPEED_6_VALUE_S.getProgress());
			Log.i("xxx", "s1="+s1);
			Log.i("xxx", "s2="+s2);
			Log.i("xxx", "s3="+s3);
			Log.i("xxx", "s5="+s5);
			Log.i("xxx", "s6="+s6);
			int v0=REMEMBER_SPEED_STATUS.isChecked()?1:0;
			int v1=REMEMBER_SPEED_1.isChecked()?1:0;
			int v2=REMEMBER_SPEED_2.isChecked()?1:0;
			int v3=REMEMBER_SPEED_3.isChecked()?1:0;
			int v4=REMEMBER_SPEED_4.isChecked()?1:0;
			int v5=REMEMBER_SPEED_5.isChecked()?1:0;
			int v6=REMEMBER_SPEED_6.isChecked()?1:0;
			int vC=(v0<<7)+(v1<<6)+(v2<<5)+(v3<<4)+(v4<<3)+(v5<<2)+(v6<<1);
			sendMsg("5AA50A8A"+BytesUtil.intToHexString(vC)+s1+s2+s3+s4+s5+s6+"000000");
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			try {
			REMEMBER_SPEED_1_VALUE.setText(""+REMEMBER_SPEED_1_VALUE_S.getProgress());
			REMEMBER_SPEED_2_VALUE.setText(""+REMEMBER_SPEED_2_VALUE_S.getProgress());
			REMEMBER_SPEED_3_VALUE.setText(""+REMEMBER_SPEED_3_VALUE_S.getProgress());
			REMEMBER_SPEED_4_VALUE.setText(""+REMEMBER_SPEED_4_VALUE_S.getProgress());
			REMEMBER_SPEED_5_VALUE.setText(""+REMEMBER_SPEED_5_VALUE_S.getProgress());
			REMEMBER_SPEED_6_VALUE.setText(""+REMEMBER_SPEED_6_VALUE_S.getProgress());
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	};

	private OnCheckedChangeListener onCheckedChangeListener=new OnCheckedChangeListener() {
		
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if(IsSync){
				return;
			}
			
			Log.i("xxx", "buttonView="+buttonView.getId());
			Log.i("xxx", "isChecked="+isChecked);
			int v0=REMEMBER_SPEED_STATUS.isChecked()?1:0;
			int v1=REMEMBER_SPEED_1.isChecked()?1:0;
			int v2=REMEMBER_SPEED_2.isChecked()?1:0;
			int v3=REMEMBER_SPEED_3.isChecked()?1:0;
			int v4=REMEMBER_SPEED_4.isChecked()?1:0;
			int v5=REMEMBER_SPEED_5.isChecked()?1:0;
			int v6=REMEMBER_SPEED_6.isChecked()?1:0;
			int vC=(v0<<7)+(v1<<6)+(v2<<5)+(v3<<4)+(v4<<3)+(v5<<2)+(v6<<1);
			String s1=BytesUtil.intToHexString(mCanInfo.REMEMBER_SPEED_1_VALUE);
			String s2=BytesUtil.intToHexString(mCanInfo.REMEMBER_SPEED_2_VALUE);
			String s3=BytesUtil.intToHexString(mCanInfo.REMEMBER_SPEED_3_VALUE);
			String s4=BytesUtil.intToHexString(mCanInfo.REMEMBER_SPEED_4_VALUE);
			String s5=BytesUtil.intToHexString(mCanInfo.REMEMBER_SPEED_5_VALUE);
			String s6=BytesUtil.intToHexString(mCanInfo.REMEMBER_SPEED_6_VALUE);
			Log.i("xxx", "string v=="+BytesUtil.intToHexString(vC));
			Log.i("xxx", "s1="+s1);
			Log.i("xxx", "s2="+s2);
			Log.i("xxx", "s3="+s3);
			Log.i("xxx", "s4="+s4);
			Log.i("xxx", "s5="+s5);
			Log.i("xxx", "s6="+s6);
			sendMsg("5AA50A8A"+BytesUtil.intToHexString(vC)+s1+s2+s3+s4+s5+s6+"000000");
			
		}
	};
		

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.ss_peugeot408_remember_speed,
				container, false);

		initView(view);
		syncView(getCanInfo());
		return view;
	}

	private void syncView(CanInfo canInfo) {
		try {

			REMEMBER_SPEED_STATUS_ENABLE
					.setChecked(canInfo.REMEMBER_SPEED_STATUS_ENABLE == 1 ? true
							: false);
			REMEMBER_SPEED_1_ENABLE
					.setChecked(canInfo.REMEMBER_SPEED_1_ENABLE == 1 ? true
							: false);
			REMEMBER_SPEED_2_ENABLE
					.setChecked(canInfo.REMEMBER_SPEED_2_ENABLE == 1 ? true
							: false);
			REMEMBER_SPEED_3_ENABLE
					.setChecked(canInfo.REMEMBER_SPEED_3_ENABLE == 1 ? true
							: false);
			REMEMBER_SPEED_4_ENABLE
					.setChecked(canInfo.REMEMBER_SPEED_4_ENABLE == 1 ? true
							: false);
			REMEMBER_SPEED_5_ENABLE
					.setChecked(canInfo.REMEMBER_SPEED_5_ENABLE == 1 ? true
							: false);
			REMEMBER_SPEED_6_ENABLE
					.setChecked(canInfo.REMEMBER_SPEED_6_ENABLE == 1 ? true
							: false);

			REMEMBER_SPEED_STATUS
					.setChecked(canInfo.REMEMBER_SPEED_STATUS == 1 ? true
							: false);
			REMEMBER_SPEED_1.setChecked(canInfo.REMEMBER_SPEED_1 == 1 ? true
					: false);
			REMEMBER_SPEED_2.setChecked(canInfo.REMEMBER_SPEED_2 == 1 ? true
					: false);
			REMEMBER_SPEED_3.setChecked(canInfo.REMEMBER_SPEED_3 == 1 ? true
					: false);
			REMEMBER_SPEED_4.setChecked(canInfo.REMEMBER_SPEED_4 == 1 ? true
					: false);
			REMEMBER_SPEED_5.setChecked(canInfo.REMEMBER_SPEED_5 == 1 ? true
					: false);
			REMEMBER_SPEED_6.setChecked(canInfo.REMEMBER_SPEED_6 == 1 ? true
					: false);
			
			REMEMBER_SPEED_1_VALUE.setText(""+canInfo.REMEMBER_SPEED_1_VALUE);
			REMEMBER_SPEED_2_VALUE.setText(""+canInfo.REMEMBER_SPEED_2_VALUE);
			REMEMBER_SPEED_3_VALUE.setText(""+canInfo.REMEMBER_SPEED_3_VALUE);
			REMEMBER_SPEED_4_VALUE.setText(""+canInfo.REMEMBER_SPEED_4_VALUE);
			REMEMBER_SPEED_5_VALUE.setText(""+canInfo.REMEMBER_SPEED_5_VALUE);
			REMEMBER_SPEED_6_VALUE.setText(""+canInfo.REMEMBER_SPEED_6_VALUE);
			
			REMEMBER_SPEED_1_VALUE_S.setProgress(canInfo.REMEMBER_SPEED_1_VALUE);
			REMEMBER_SPEED_2_VALUE_S.setProgress(canInfo.REMEMBER_SPEED_2_VALUE);
			REMEMBER_SPEED_3_VALUE_S.setProgress(canInfo.REMEMBER_SPEED_3_VALUE);
			REMEMBER_SPEED_4_VALUE_S.setProgress(canInfo.REMEMBER_SPEED_4_VALUE);
			REMEMBER_SPEED_5_VALUE_S.setProgress(canInfo.REMEMBER_SPEED_5_VALUE);
			REMEMBER_SPEED_6_VALUE_S.setProgress(canInfo.REMEMBER_SPEED_6_VALUE);
			
			if(REMEMBER_SPEED_STATUS.isChecked()){
				REMEMBER_SPEED_1.setEnabled(true);
				REMEMBER_SPEED_2.setEnabled(true);
				REMEMBER_SPEED_3.setEnabled(true);
				REMEMBER_SPEED_4.setEnabled(true);
				REMEMBER_SPEED_5.setEnabled(true);
				REMEMBER_SPEED_6.setEnabled(true);
				REMEMBER_SPEED_1_VALUE_S.setEnabled(REMEMBER_SPEED_1.isChecked());
				REMEMBER_SPEED_2_VALUE_S.setEnabled(REMEMBER_SPEED_2.isChecked());
				REMEMBER_SPEED_3_VALUE_S.setEnabled(REMEMBER_SPEED_3.isChecked());
				REMEMBER_SPEED_4_VALUE_S.setEnabled(REMEMBER_SPEED_4.isChecked());
				REMEMBER_SPEED_5_VALUE_S.setEnabled(REMEMBER_SPEED_5.isChecked());
				REMEMBER_SPEED_6_VALUE_S.setEnabled(REMEMBER_SPEED_6.isChecked());
			}else{
				REMEMBER_SPEED_1.setEnabled(false);
				REMEMBER_SPEED_2.setEnabled(false);
				REMEMBER_SPEED_3.setEnabled(false);
				REMEMBER_SPEED_4.setEnabled(false);
				REMEMBER_SPEED_5.setEnabled(false);
				REMEMBER_SPEED_6.setEnabled(false);
				REMEMBER_SPEED_1_VALUE_S.setEnabled(false);
				REMEMBER_SPEED_2_VALUE_S.setEnabled(false);
				REMEMBER_SPEED_3_VALUE_S.setEnabled(false);
				REMEMBER_SPEED_4_VALUE_S.setEnabled(false);
				REMEMBER_SPEED_5_VALUE_S.setEnabled(false);
				REMEMBER_SPEED_6_VALUE_S.setEnabled(false);
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void initView(View view) {
		REMEMBER_SPEED_STATUS_ENABLE = (CheckBox) view
				.findViewById(R.id.REMEMBER_SPEED_STATUS_ENABLE);
		REMEMBER_SPEED_1_ENABLE = (CheckBox) view
				.findViewById(R.id.REMEMBER_SPEED_1_ENABLE);
		REMEMBER_SPEED_2_ENABLE = (CheckBox) view
				.findViewById(R.id.REMEMBER_SPEED_2_ENABLE);
		REMEMBER_SPEED_3_ENABLE = (CheckBox) view
				.findViewById(R.id.REMEMBER_SPEED_3_ENABLE);
		REMEMBER_SPEED_4_ENABLE = (CheckBox) view
				.findViewById(R.id.REMEMBER_SPEED_4_ENABLE);
		REMEMBER_SPEED_5_ENABLE = (CheckBox) view
				.findViewById(R.id.REMEMBER_SPEED_5_ENABLE);
		REMEMBER_SPEED_6_ENABLE = (CheckBox) view
				.findViewById(R.id.REMEMBER_SPEED_6_ENABLE);

		REMEMBER_SPEED_STATUS = (CheckBox) view
				.findViewById(R.id.REMEMBER_SPEED_STATUS);
		REMEMBER_SPEED_1 = (CheckBox) view.findViewById(R.id.REMEMBER_SPEED_1);
		REMEMBER_SPEED_2 = (CheckBox) view.findViewById(R.id.REMEMBER_SPEED_2);
		REMEMBER_SPEED_3 = (CheckBox) view.findViewById(R.id.REMEMBER_SPEED_3);
		REMEMBER_SPEED_4 = (CheckBox) view.findViewById(R.id.REMEMBER_SPEED_4);
		REMEMBER_SPEED_5 = (CheckBox) view.findViewById(R.id.REMEMBER_SPEED_5);
		REMEMBER_SPEED_6 = (CheckBox) view.findViewById(R.id.REMEMBER_SPEED_6);
		REMEMBER_SPEED_STATUS.setOnCheckedChangeListener(onCheckedChangeListener);
		REMEMBER_SPEED_1.setOnCheckedChangeListener(onCheckedChangeListener);
		REMEMBER_SPEED_2.setOnCheckedChangeListener(onCheckedChangeListener);
		REMEMBER_SPEED_3.setOnCheckedChangeListener(onCheckedChangeListener);
		REMEMBER_SPEED_4.setOnCheckedChangeListener(onCheckedChangeListener);
		REMEMBER_SPEED_5.setOnCheckedChangeListener(onCheckedChangeListener);
		REMEMBER_SPEED_6.setOnCheckedChangeListener(onCheckedChangeListener);
		
		REMEMBER_SPEED_1_VALUE = (TextView) view
				.findViewById(R.id.REMEMBER_SPEED_1_VALUE);
		REMEMBER_SPEED_2_VALUE = (TextView) view
				.findViewById(R.id.REMEMBER_SPEED_2_VALUE);
		REMEMBER_SPEED_3_VALUE = (TextView) view
				.findViewById(R.id.REMEMBER_SPEED_3_VALUE);
		REMEMBER_SPEED_4_VALUE = (TextView) view
				.findViewById(R.id.REMEMBER_SPEED_4_VALUE);
		REMEMBER_SPEED_5_VALUE = (TextView) view
				.findViewById(R.id.REMEMBER_SPEED_5_VALUE);
		REMEMBER_SPEED_6_VALUE = (TextView) view
				.findViewById(R.id.REMEMBER_SPEED_6_VALUE);

		REMEMBER_SPEED_1_VALUE_S = (SeekBar) view
				.findViewById(R.id.REMEMBER_SPEED_1_VALUE_S);
		REMEMBER_SPEED_2_VALUE_S = (SeekBar) view
				.findViewById(R.id.REMEMBER_SPEED_2_VALUE_S);
		REMEMBER_SPEED_3_VALUE_S = (SeekBar) view
				.findViewById(R.id.REMEMBER_SPEED_3_VALUE_S);
		REMEMBER_SPEED_4_VALUE_S = (SeekBar) view
				.findViewById(R.id.REMEMBER_SPEED_4_VALUE_S);
		REMEMBER_SPEED_5_VALUE_S = (SeekBar) view
				.findViewById(R.id.REMEMBER_SPEED_5_VALUE_S);
		REMEMBER_SPEED_6_VALUE_S = (SeekBar) view
				.findViewById(R.id.REMEMBER_SPEED_6_VALUE_S);
		
		REMEMBER_SPEED_1_VALUE_S.setOnSeekBarChangeListener(seekBarListenenr);
		REMEMBER_SPEED_2_VALUE_S.setOnSeekBarChangeListener(seekBarListenenr);
		REMEMBER_SPEED_3_VALUE_S.setOnSeekBarChangeListener(seekBarListenenr);
		REMEMBER_SPEED_4_VALUE_S.setOnSeekBarChangeListener(seekBarListenenr);
		REMEMBER_SPEED_5_VALUE_S.setOnSeekBarChangeListener(seekBarListenenr);
		REMEMBER_SPEED_6_VALUE_S.setOnSeekBarChangeListener(seekBarListenenr);
		

	}

	@Override
	public void serviceConnected() {
		// TODO Auto-generated method stub
		super.serviceConnected();
	}

	@Override
	public void show(CanInfo mCaninfo) {
		if(mCaninfo!=null){
			mCanInfo=mCaninfo;
		}
		try {
			super.show(mCaninfo);
			IsSync=true;
			syncView(mCaninfo);
			IsSync=false;
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
