package com.example.txe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;






import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static final String CAN_INFORMATON = "CAN_Informaion";
	public static final String CAN_CLASS_NAME = "CAN_Class_Name";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBar bar = getActionBar();
		bar.setTitle("工厂设置");
		Button button = (Button) findViewById(R.id.choose);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						ChooseActivity.class);
				startActivity(intent);
			}
		});

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try {
			((TextView) findViewById(R.id.can)).setText(Settings.System
					.getString(getContentResolver(), CAN_INFORMATON));
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_import:
			Boolean ImportResult=false;
			try {
				ImportResult=xmlUtils.getSettings(MainActivity.this);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ImportResult=false;
			}
			Toast.makeText(getApplication(), ImportResult?"导入成功":"导入失败", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.action_export:
			Boolean ExportResult=xmlUtils.createADXML(MainActivity.this);
			Toast.makeText(getApplication(), ExportResult?"导出成功 \n 保存路径 "+"/sdcard/console_setting.xml":"导出失败", Toast.LENGTH_SHORT).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	
}
