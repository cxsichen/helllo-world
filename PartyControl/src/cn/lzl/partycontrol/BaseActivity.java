package cn.lzl.partycontrol;

import android.app.Activity;
import android.os.Bundle;

import cn.lzl.partycontrol.utils.Trace;


public abstract class BaseActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    Trace.i("onCreate");
		super.onCreate(savedInstanceState);
		setContentView();
		initializeView();
		initializeData();
	}
	
	protected abstract void setContentView();
	protected abstract void initializeView();
	protected abstract void initializeData();

	@Override
	protected void onResume() {
	    Trace.i("onResume");
		super.onResume();
	}
	
	@Override
	protected void onPause() {
	    Trace.i("onPause");
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
	    Trace.i("onDestroy");
	    super.onDestroy();
	}
	
	
	
}
