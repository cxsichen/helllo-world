package com.console.canreader.view.PeugeotCitroen;

import java.text.DecimalFormat;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import com.console.canreader.R;
import com.console.canreader.service.CanInfo;
import com.console.canreader.view.ViewPageFactory;

public class PeuAirConView extends ViewPageFactory {


	Context mContext;
	DecimalFormat fnum = new DecimalFormat("##0.00");

	public PeuAirConView(Context context, int layout) {
		super(context, layout);
		// TODO Auto-generated constructor stub
		mContext = context;
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
	}

	@Override
	public void showView(CanInfo caninfo) {
		// TODO Auto-generated method stub


	}

}
