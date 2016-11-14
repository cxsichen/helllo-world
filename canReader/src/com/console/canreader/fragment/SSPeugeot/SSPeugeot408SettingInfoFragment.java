package com.console.canreader.fragment.SSPeugeot;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.console.canreader.R;
import com.console.canreader.activity.BaseFragment;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;

public class SSPeugeot408SettingInfoFragment extends BaseFragment implements OnItemSelectedListener{
	
	private static boolean isSync=false;
	private TextView SOS_STATUS;
	private CheckBox ENGINE_START_STATUS_ENABLE;
	private CheckBox ENGINE_START_STATUS;
	private CheckBox UNIT_TEMPERATURE_ENABLE;
	private CheckBox UNIT_CONSUMPTION_ENABLE;
	private Spinner UNIT_TEMPERATURE;
	private Spinner UNIT_CONSUMPTION;
	
	private Spinner LANGUAGE_CHANGE;
	private EditText TIME_YEAR;
	private EditText TIME_MONTH;
	private EditText TIME_DAY;
	private EditText TIME_HOUR;
	private EditText TIME_MINUTE;
	private Spinner TIME_FORMAT;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.ss_peugeot408_setting_info,
				container, false);
		
		initView(view);
		syncView(getCanInfo());
		return view;
	}
	
	private void syncView(CanInfo canInfo) {
		try {
			SOS_STATUS.setText(getResources().getStringArray(R.array.car_info_sos_status_entries)[canInfo.SOS_STATUS]);
//			ENGINE_START_STATUS.setChecked(canInfo.ENGINE_START_STATUS==1?true:false);
//			ENGINE_START_STATUS_ENABLE.setChecked(canInfo.ENGINE_START_STATUS_ENABLE==1?true:false);
			UNIT_TEMPERATURE_ENABLE.setChecked(canInfo.UNIT_TEMPERATURE_ENABLE==1?true:false);
			UNIT_CONSUMPTION_ENABLE.setChecked(canInfo.UNIT_CONSUMPTION_ENABLE==1?true:false);
//			UNIT_TEMPERATURE.setSelection(canInfo.UNIT_TEMPERATURE);
//			UNIT_CONSUMPTION.setSelection(canInfo.UNIT_CONSUMPTION);
			
//			LANGUAGE_CHANGE.setSelection(canInfo.LANGUAGE_CHANGE-1);
			TIME_YEAR.setText("20"+canInfo.TIME_YEAR);
			TIME_MONTH.setText(String.valueOf(canInfo.TIME_MONTH));
			TIME_DAY.setText(String.valueOf(canInfo.TIME_DAY));
			TIME_MINUTE.setText(String.valueOf(canInfo.TIME_MINUTE));
			TIME_FORMAT.setSelection(canInfo.TIME_FORMAT);
			if(canInfo.TIME_FORMAT==0&&canInfo.TIME_HOUR>11){
				TIME_HOUR.setText(String.valueOf((canInfo.TIME_HOUR-12)));
			}else{
				TIME_HOUR.setText(String.valueOf(canInfo.TIME_HOUR));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	private void initView(View view) {
		try {
			SOS_STATUS=(TextView) view.findViewById(R.id.SOS_STATUS);
//			ENGINE_START_STATUS=(CheckBox) view.findViewById(R.id.ENGINE_START_STATUS);
//			ENGINE_START_STATUS_ENABLE=(CheckBox) view.findViewById(R.id.ENGINE_START_STATUS_ENABLE);
			UNIT_TEMPERATURE_ENABLE=(CheckBox) view.findViewById(R.id.UNIT_TEMPERATURE_ENABLE);
			UNIT_CONSUMPTION_ENABLE=(CheckBox) view.findViewById(R.id.UNIT_CONSUMPTION_ENABLE);
			UNIT_TEMPERATURE=(Spinner) view.findViewById(R.id.UNIT_TEMPERATURE);
			UNIT_CONSUMPTION=(Spinner) view.findViewById(R.id.UNIT_CONSUMPTION);
			
			LANGUAGE_CHANGE=(Spinner) view.findViewById(R.id.LANGUAGE_CHANGE);
			TIME_YEAR=(EditText) view.findViewById(R.id.TIME_YEAR);
			TIME_MONTH=(EditText) view.findViewById(R.id.TIME_MONTH);
			TIME_DAY=(EditText) view.findViewById(R.id.TIME_DAY);
			TIME_HOUR=(EditText) view.findViewById(R.id.TIME_HOUR);
			TIME_MINUTE=(EditText) view.findViewById(R.id.TIME_MINUTE);
			TIME_FORMAT=(Spinner) view.findViewById(R.id.TIME_FORMAT);
			
			
			UNIT_TEMPERATURE.setOnItemSelectedListener(this);
			UNIT_CONSUMPTION.setOnItemSelectedListener(this);
			LANGUAGE_CHANGE.setOnItemSelectedListener(this);
			TIME_FORMAT.setOnItemSelectedListener(this);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void serviceConnected() {
		// TODO Auto-generated method stub
		super.serviceConnected();
	}
	
	
	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		try {
			isSync=true;
			syncView(mCaninfo);
			isSync=false;
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		Log.i("xxx", "position=="+position);
		try {
			if(isSync)
			return;
			
			switch (parent.getId()) {
			case R.id.UNIT_TEMPERATURE:
				sendMsg("5AA502CA03" + BytesUtil.intToHexString(position+1));
				break;

			case R.id.UNIT_CONSUMPTION:
				sendMsg("5AA502CA05" + BytesUtil.intToHexString(position+1));
				break;

			case R.id.LANGUAGE_CHANGE:
				sendMsg("5AA5029A01" + BytesUtil.intToHexString(position+1));
				break;
			case R.id.TIME_FORMAT:
				break;
			default:
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
}
