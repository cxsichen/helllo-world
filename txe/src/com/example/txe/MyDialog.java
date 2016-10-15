package com.example.txe;



import com.example.txe.ChooseFragment.CallBack;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MyDialog extends Dialog {

	Context context;
	String canType;
	String carType;
	String sort;
	String configuration;
	String name;
	public static final String CAN_INFORMATON = "CAN_Informaion";
	public static final String CAN_CLASS_NAME = "CAN_Class_Name";
	
	public MyDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public MyDialog(Context context, CanInfoMsg canInfoMsg, int theme) {
		super(context, theme);
		this.context = context;
		this.canType = canInfoMsg.getCanTye();
		this.carType = canInfoMsg.getCarType();
		this.sort = canInfoMsg.getSort();
		this.name =canInfoMsg.getName();
		this.configuration = canInfoMsg.getConfiguration();
	}

	private View.OnClickListener clickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.dialog_cancle:
				dismiss();
				break;
			case R.id.dialog_save:
				Settings.System.putString(context.getContentResolver(),
						CAN_INFORMATON,canType+"-"+carType+"-"+sort+"-"+configuration);
				Settings.System.putString(context.getContentResolver(),
						CAN_CLASS_NAME,name);
				if(mCallBack!=null){
					mCallBack.onChange();
				}
				dismiss();
                break;
			default:
				break;
			}
		}
	};

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (isShowing() && event.getAction() == MotionEvent.ACTION_OUTSIDE) {
			return true;
		}
		return false;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_layout);
		((TextView) findViewById(R.id.canType_title)).setText(canType);
		((TextView) findViewById(R.id.carType_title)).setText(carType);
		((TextView) findViewById(R.id.sort_title)).setText(sort);
		((TextView) findViewById(R.id.configuration_title))
				.setText(configuration);
		findViewById(R.id.dialog_cancle).setOnClickListener(clickListener);
		findViewById(R.id.dialog_save).setOnClickListener(clickListener);
	}
	
	interface CallBack{
		void onChange();
	};
	
	CallBack mCallBack;
	
	public void setCallBack(CallBack callBack){
		mCallBack=callBack;
	}
}
