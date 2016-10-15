package com.console.canreader.activity;

import com.console.canreader.service.CanInfo;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BaseFragment extends Fragment {

	protected int index;
	private CarInfoActivity carInfoActivity;
	public BaseFragment() {

	}
	
	public void setIndex(int index,CarInfoActivity carInfoActivity) {
		this.index = index;
		this.carInfoActivity=carInfoActivity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		TextView tv = new TextView(getActivity());
		tv.setText("MenuCdAcitivity");
		tv.setText("Fragment-" + index);
		return tv;

	}
	
	/**
	 * ���ݱ仯 ��Ҫ�ı�����ʱ����� �����ȡ����������ݷ�����ʱ������
	 */
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
	
	}
	
	
	/**
	 * ��������ʱ����
	 */
	public void serviceConnected() {
		// TODO Auto-generated method stub
	}
	
	public void sendMsg(String str){
		if(carInfoActivity!=null){
			carInfoActivity.sendMsg(str);
		}
	}

}

