package com.console.canreader.fragment.SSHondaYG9;

import java.text.DecimalFormat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.console.canreader.R;
import com.console.canreader.activity.BaseFragment;
import com.console.canreader.service.CanInfo;

public class TpmsSettingsFragment extends BaseFragment implements
		View.OnClickListener {

	

	private Button button1;
	private Button button2;
	DecimalFormat df;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		df = new DecimalFormat(".00");
		View view = inflater.inflate(R.layout.sshondayg9_layout_1,
				container, false);
		initView(view);
		return view;
	}

	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		super.show(mCaninfo);

		

	}

	private void updateTextView(TextView tv, String str) {
		if (!tv.getText().equals(str)) {
			tv.setText(str);
		}
	}

	@Override
	public void serviceConnected() {
		// TODO Auto-generated method stub
		super.serviceConnected();
	}

	private void initView(View view) {
	
		button1 = (Button) view.findViewById(R.id.yg9_btn_1);
		button1.setOnClickListener(this);
		
		button2 = (Button) view.findViewById(R.id.yg9_btn_2);
		button2.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.yg9_btn_1:
			sendMsg("5AA5024B0400");
			break;
		case R.id.yg9_btn_2:
			sendMsg("5AA5024B0401");
			break;
		default:
			break;
		}
	}

}
