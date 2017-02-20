package com.console.canreader.view;

import com.console.canreader.R;
import com.console.canreader.service.CanInfo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

public abstract class ViewPageFactory{

	public View pageView;
	private LayoutInflater inflater;


	public ViewPageFactory(Context context,int layout) {
		inflater = LayoutInflater.from(context);
		pageView = inflater.inflate(layout, null);
		initView();
	}
		
	public View  getView(){		
		return pageView;
	}
	
	public abstract void initView();
	
	public abstract void showView(CanInfo caninfo);

}
