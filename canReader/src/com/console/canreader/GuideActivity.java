package com.console.canreader;



import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.PreferenceUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class GuideActivity extends Activity {

	LinearLayout guideLayout;
	
	private int canType = -1; // 盒子厂家 0：睿志诚 1：尚摄
	private int carType = -1; // 车型 0:大众

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.guide_layout);
		guideLayout = (LinearLayout) findViewById(R.id.guide_layout);
		initGuideView();	
	}

	private void initGuideView() {
		// TODO Auto-generated method stub
		canType = PreferenceUtil.getCANTYPE(this);
		carType = PreferenceUtil.getCARTYPE(this);
		
		switch (canType) {
		case Contacts.CANTYPEGROUP.RAISE: // 睿志诚
			switch (carType) {
			case Contacts.CARTYPEGROUP.Volkswagen: // 大众
				initVolkswagenView();
				break;
			case Contacts.CARTYPEGROUP.PeugeotCitroen: // 标致
			case Contacts.CARTYPEGROUP.BESTURNX80:// 奔腾X80 海马M3
			case Contacts.CARTYPEGROUP.FHCM3:

				break;
			default:
				
				break;
			}
			break;
		case Contacts.CANTYPEGROUP.HIWORLD: // 尚摄
			
			break;
		default:
			
			break;
		}
				
	}

	private void initVolkswagenView() {
		// TODO Auto-generated method stub
		Resources res =getResources();
		String[] items=res.getStringArray(R.array.Volkswagen_items);
		String[] activity=res.getStringArray(R.array.Volkswagen_activity);
		
		for(int i=0;i<items.length||i<activity.length;i++){
			LayoutInflater inflater = this.getLayoutInflater();
			Button button = (Button) inflater.inflate(R.layout.button, null);
			button.setText(items[i]);
			button.setTag(activity[i]);
			button.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent();
					intent.setClassName("com.console.canreader",(String) v.getTag());
					startActivity(intent);
				}
			});
			guideLayout.addView(button);	
		}
	}
	


}
