package com.console.canreader;

import com.console.canreader.activity.baseActivity;
import com.console.canreader.activity.baseActivity.CanTypeObserver;
import com.console.canreader.activity.baseActivity.CarTypeObserver;
import com.console.canreader.service.CanService;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.PreferenceUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.os.Bundle;
import android.util.Log;
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

	private int canType = -1;
	private int carType = -1;
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
		initGuideView();
		
		canType = PreferenceUtil.getCANTYPE(this);
		carType = PreferenceUtil.getCANTYPE(this);
		getContentResolver().registerContentObserver(
				android.provider.Settings.System.getUriFor(Contacts.CANTYPE),
				true, mCanTypeObserver);
		getContentResolver().registerContentObserver(
				android.provider.Settings.System.getUriFor(Contacts.CARTYPE),
				true, mCarTypeObserver);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getContentResolver().unregisterContentObserver(mCanTypeObserver);
		getContentResolver().unregisterContentObserver(mCarTypeObserver);
	}
	
	private CanTypeObserver mCanTypeObserver = new CanTypeObserver();

	public class CanTypeObserver extends ContentObserver {
		public CanTypeObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			if (canType != PreferenceUtil.getCANTYPE(GuideActivity.this))
				finish();

		}
	}

	private CarTypeObserver mCarTypeObserver = new CarTypeObserver();

	public class CarTypeObserver extends ContentObserver {
		public CarTypeObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			if (carType != PreferenceUtil.getCARTYPE(GuideActivity.this))
				finish();
		}
	}


	private void initGuideView() {
		// TODO Auto-generated method stub
		canType = PreferenceUtil.getCANTYPE(this);
		carType = PreferenceUtil.getCARTYPE(this);

		Resources res = getResources();
		String[] carTypeName = res.getStringArray(R.array.CarType);
		String[] canTypeName = res.getStringArray(R.array.CanType);
		initListView(canTypeName[canType], carTypeName[carType]);
	}

	private void initListView(String Cantype, String Cartype) {
		
		try {
			Resources res = getResources();
			int itemsId = getResources().getIdentifier(
					Cantype + "_" + Cartype + "_items", "array", getPackageName());
			String[] items = res.getStringArray(itemsId);

			int activityId = getResources().getIdentifier(
					Cantype + "_" + Cartype + "_activity", "array",
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

}
