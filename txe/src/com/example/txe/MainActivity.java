package com.example.txe;



import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	public static final String CAN_INFORMATON = "CAN_Informaion";
	public static final String CAN_CLASS_NAME = "CAN_Class_Name";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBar bar=getActionBar();
		bar.setTitle("π§≥ß…Ë÷√");
		Button button=(Button) findViewById(R.id.choose);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this, ChooseActivity.class);
				startActivity(intent);
			}
		});

	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try {
			((TextView)findViewById(R.id.can)).setText(Settings.System.getString(getContentResolver(),
					CAN_INFORMATON));
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
