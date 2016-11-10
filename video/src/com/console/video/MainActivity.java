package com.console.video;

import com.console.video.manager.VideoManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {
	VideoManager mVideoManager;
	RelativeLayout mRelativeLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		init();
	}
	private void init() {
		// TODO Auto-generated method stub
		mRelativeLayout=(RelativeLayout) findViewById(R.id.main_layout);
		mVideoManager=new VideoManager(MainActivity.this,mRelativeLayout);
	}

}
