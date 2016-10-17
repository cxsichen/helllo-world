package com.console.canreader.activity;

import com.console.canreader.R;
import com.console.canreader.service.CanInfo;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class CarSettingAcitivity extends BaseActivity {
	BaseFragment mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ActionBar mActionBar=getActionBar();
		mActionBar.setTitle("³µÁ¾Éè¶¨");
		mActionBar.setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.fragment_activity_layout);
		initFragment();
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void initFragment() {
		// TODO Auto-generated method stub
		Resources res = getResources();
		int itemsId = getResources().getIdentifier(
				canName + "_SettingFragment", "array", getPackageName());
		String[] items = res.getStringArray(itemsId);

		if (items.length > 0) {
			try {
				Class classManager = Class.forName(items[0]);
				mFragment = (BaseFragment) classManager.newInstance();
				mFragment.setIndex(0,CarSettingAcitivity.this);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (mFragment != null) {
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.content_layout, mFragment).commit();
		}

	}

	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		super.show(mCaninfo);
		if(mFragment!=null){
			mFragment.show(mCaninfo);
		}
	}

	@Override
	public void serviceConnected() {
		// TODO Auto-generated method stub
		super.serviceConnected();
	}

}
