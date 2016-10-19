package com.console.canreader.activity;

import com.console.canreader.service.CanInfo;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BaseFragment extends Fragment {

	protected int index;
	private BaseActivity carInfoActivity;
	private Boolean ISRESUME = false;

	public BaseFragment() {

	}

	public CanInfo getCanInfo() {
		CanInfo mCanInfo = null;
		if (carInfoActivity != null) {
			try {
				mCanInfo = carInfoActivity.getCanInfo();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return mCanInfo;
	}

	public void setIndex(int index, BaseActivity carSettingAcitivity) {
		this.index = index;
		this.carInfoActivity = carSettingAcitivity;
		if (carInfoActivity != null && ISRESUME) {
			try {
				if (carInfoActivity.getCanInfo() != null)
					show(carInfoActivity.getCanInfo());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		TextView tv = new TextView(getActivity());
		tv.setText("MenuCdAcitivity");
		tv.setText("Fragment-" + index);
		return tv;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ISRESUME = true;
		if (carInfoActivity != null) {
			try {
				if (carInfoActivity.getCanInfo() != null)
					show(carInfoActivity.getCanInfo());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Boolean getResumeStatus() {
		return ISRESUME;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		ISRESUME = false;
	}

	/**
	 * 数据变化 需要改变界面的时候调用 界面获取焦点和有数据反馈的时候会调用
	 */
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
	}
	

	/**
	 * 服务链接时调用
	 */
	public void serviceConnected() {
		// TODO Auto-generated method stub
	}

	public void sendMsg(String str) {
		if (carInfoActivity != null) {
			carInfoActivity.sendMsg(str);
		}
	}

}
