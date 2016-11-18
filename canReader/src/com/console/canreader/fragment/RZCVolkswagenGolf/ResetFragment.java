package com.console.canreader.fragment.RZCVolkswagenGolf;

import java.util.ArrayList;
import java.util.List;

import com.console.canreader.R;
import com.console.canreader.activity.BaseFragment;
import com.console.canreader.activity.VolkswagenGolf.GolfView1;
import com.console.canreader.activity.VolkswagenGolf.GolfView4;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.DensityUtils;
import com.console.canreader.utils.ViewPagerAdapter;
import com.console.canreader.view.ObdView;
import com.console.canreader.view.ViewPageFactory;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.View.OnCreateContextMenuListener;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

public class ResetFragment extends BaseFragment implements OnClickListener {

	private LayoutInflater inflater;
	Button button0;
	Button button1;
	Button button2;
	Button button3;
	Button button4;
	Button button5;
	Button button6;
	
	public ResetFragment() {

	}



	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		super.show(mCaninfo);
		if (mCaninfo != null) {

		}
	}

	@Override
	public void serviceConnected() {
		// TODO Auto-generated method stub
		super.serviceConnected();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		inflater = LayoutInflater.from(getActivity());
		View view = inflater.inflate(R.layout.rzc_golf_layout_3, null);
		button0=(Button) view.findViewById(R.id.golf_button0);
		button1=(Button) view.findViewById(R.id.golf_button1);
		button2=(Button) view.findViewById(R.id.golf_button2);
		button3=(Button) view.findViewById(R.id.golf_button3);
		button4=(Button) view.findViewById(R.id.golf_button4);
		button5=(Button) view.findViewById(R.id.golf_button5);
		button6=(Button) view.findViewById(R.id.golf_button6);
		
		button0.setOnClickListener(this);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);
		button5.setOnClickListener(this);
		button6.setOnClickListener(this);
		return view;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();

	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.golf_button0:
			sendMsg("2EC602C101" );
			break;
		case R.id.golf_button1:
			sendMsg("2EC602C201" );
			break;
		case R.id.golf_button2:
			sendMsg("2EC602C301" );
			break;
		case R.id.golf_button3:
			sendMsg("2EC602C401" );
			break;
		case R.id.golf_button4:
			sendMsg("2EC602C501" );
			break;
		case R.id.golf_button5:
			sendMsg("2EC602C601" );
			break;
		case R.id.golf_button6:
			sendMsg("2EC602C701" );
			break;
		default:
			break;
		}
	}

}
