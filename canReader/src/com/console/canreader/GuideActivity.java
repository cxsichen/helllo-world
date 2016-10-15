package com.console.canreader;

import com.console.canreader.activity.BaseActivity;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GuideActivity extends Activity {

	LinearLayout guideLayout;

	private String canName = "";
	private String canFirtName = "";
	ListView listView;

	String[] activitiyGroup = {
			"com.console.canreader.activity.CarInfoActivity",
			"com.console.canreader.activity.CarSettingAcitivity",
			"com.console.canreader.activity.MenuAcAcitivity",
			"com.console.canreader.activity.MenuCdAcitivity",
			"com.console.canreader.activity.MenuPaAcitivity",
			"com.console.canreader.activity.MenuPanoramaAcitivity",
			"com.console.canreader.activity.MenuAboutAcitivity" };

	int[] itemIdGroup = { R.id.mCarInfoLayout, R.id.mCarSettingsLayout,
			R.id.mMenuAcLayout, R.id.mMenuCdLayout, R.id.mMenuPaLayout,
			R.id.mMenuPanoramaLayout, R.id.mMenuAboutLayout };

	private RelativeLayout mCarInfoLayout;
	private RelativeLayout mCarSettingsLayout;
	private RelativeLayout mMenuAcLayout;
	private RelativeLayout mMenuCdLayout;
	private RelativeLayout mMenuPaLayout;
	private RelativeLayout mMenuPanoramaLayout;
	private RelativeLayout mMenuAboutLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_menu);
		startService(new Intent(this, CanService.class));
		syncCanName();
		getContentResolver()
				.registerContentObserver(
						android.provider.Settings.System
								.getUriFor(Contacts.CAN_CLASS_NAME),
						true, mCanNameObserver);
		initView(canName);

	}
	/**
	 * 根据配置文件筛选可用的activity
	 * @param canName 
	 */

	private void initView(String canName) {
		// TODO Auto-generated method stub
		mCarInfoLayout = (RelativeLayout) findViewById(R.id.mCarInfoLayout);
		try {
			Resources res = getResources();
			int itemsId = getResources().getIdentifier(canName + "_main",
					"array", getPackageName());
			String[] items = res.getStringArray(itemsId);

			if (items.length == 0) {
				return;
			}

			if (items.length == 1) {
				Intent intent = new Intent();
				intent.setClassName("com.console.canreader", activitiyGroup[0]);
				startActivity(intent);
				finish();
				return;
			}
			for (int i = 0; i < items.length; i++) {
				if (items[i].equals("1")) {
					findViewById(itemIdGroup[i]).setVisibility(View.VISIBLE);
					findViewById(itemIdGroup[i]).setTag(i);
					findViewById(itemIdGroup[i]).setOnClickListener(
							new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									Intent intent = new Intent();
									intent.setClassName(
											"com.console.canreader",
											activitiyGroup[(int) v.getTag()]);
									startActivity(intent);
								}
							});
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getContentResolver().unregisterContentObserver(mCanNameObserver);
	}
	/**
	 * 检测can类型，如果有变化，则关闭apk，再开时重新初始化
	 */

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

	private void syncCanName() {
		canName = PreferenceUtil.getCANName(this);
		canFirtName = PreferenceUtil.getFirstTwoString(this, canName);
	}

}
