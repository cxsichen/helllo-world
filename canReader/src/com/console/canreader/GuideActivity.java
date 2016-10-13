package com.console.canreader;

import com.console.canreader.activity.baseActivity;
import com.console.canreader.service.CanService;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.PreferenceUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

public class GuideActivity extends Activity {

	LinearLayout guideLayout;

    private String canName="";
    private String canFirtName="";
	ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.guide_layout);
		guideLayout = (LinearLayout) findViewById(R.id.guide_layout);
		listView = (ListView) findViewById(R.id.listView);
		startService(new Intent(this, CanService.class));		
		syncCanName();
		//carType = 21;
		initListView(canName);

		getContentResolver().registerContentObserver(
				android.provider.Settings.System.getUriFor(Contacts.CAN_CLASS_NAME),
				true, mCanNameObserver);
		
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getContentResolver().unregisterContentObserver(mCanNameObserver);
	}
	
	private CanNameObserver mCanNameObserver = new CanNameObserver();

	public class CanNameObserver extends ContentObserver {
		public CanNameObserver() {
			
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			if (!canName.equals(PreferenceUtil.getCANName(GuideActivity.this)))
				finish();

		}
	}

	private void initListView(String canName) {
		
		try {
			Resources res = getResources();
			int itemsId = getResources().getIdentifier(
					canName + "_items", "array", getPackageName());
			String[] items = res.getStringArray(itemsId);

			int activityId = getResources().getIdentifier(
					canName + "_activity", "array",
					getPackageName());
			final String[] activity = res.getStringArray(activityId);
			 
			if(items.length==0||activity.length==0){
				return;
			}	
			
			if(items.length==1||activity.length==1){
				Intent intent = new Intent();
				intent.setClassName("com.console.canreader", activity[0]);
				startActivity(intent);
				finish();
				return;
			}
				
			listView.setAdapter(new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, items));

			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setClassName("com.console.canreader", activity[position]);
					startActivity(intent);
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
			String[] items=new String[]{getString(R.string.not_more_offer)};
			listView.setAdapter(new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, items));
		}
		
	}
	
	private void syncCanName(){
		canName = PreferenceUtil.getCANName(this);
		canFirtName=PreferenceUtil.getFirstTwoString(this, canName);
	}
	

}
