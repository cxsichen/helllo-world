package com.console.buttoncontrol;

import com.console.buttoncontrol.utils.Constact;
import com.console.buttoncontrol.utils.Contacts;

import android.app.Activity;
import android.content.Intent;
import android.media.audiofx.BassBoost.Settings;
import android.os.Bundle;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends Activity implements
		OnClickListener {
	Spinner sp0;
	Spinner sp1;
	Spinner sp2;
	Spinner sp3;
	Spinner sp4;
	Spinner sp5;

	int[] keyGroup = new int[7];

	Button button1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent intent = new Intent(MainActivity.this, SerialPortService.class);
		startService(intent);
		initData();
		initView();
	}
	

	private void initData() {
		keyGroup[0] = android.provider.Settings.System.getInt(
				getContentResolver(), Contacts.DZ_KEYCODE0, 0);
		keyGroup[1] = android.provider.Settings.System.getInt(
				getContentResolver(), Contacts.DZ_KEYCODE1, 0);
		keyGroup[2] = android.provider.Settings.System.getInt(
				getContentResolver(), Contacts.DZ_KEYCODE2, 0);
		keyGroup[3] = android.provider.Settings.System.getInt(
				getContentResolver(), Contacts.DZ_KEYCODE3, 0);
		keyGroup[4] = android.provider.Settings.System.getInt(
				getContentResolver(), Contacts.DZ_KEYCODE4, 0);
		keyGroup[5] = android.provider.Settings.System.getInt(
				getContentResolver(), Contacts.DZ_KEYCODE5, 0);
		keyGroup[6] = android.provider.Settings.System.getInt(
				getContentResolver(), Contacts.DZ_KEYCODE6, 0);

	}

	private void initView() {
		// TODO Auto-generated method stub
		sp0 = (Spinner) findViewById(R.id.spinner1);
		sp1 = (Spinner) findViewById(R.id.spinner2);
		sp2 = (Spinner) findViewById(R.id.spinner3);
		sp3 = (Spinner) findViewById(R.id.spinner4);
		sp4 = (Spinner) findViewById(R.id.spinner5);
		sp5 = (Spinner) findViewById(R.id.spinner6);
		
		sp0.setTag(1);
		sp1.setTag(2);
		sp2.setTag(3);
		sp3.setTag(R.id.spinner4);
		sp4.setTag(R.id.spinner5);
		sp5.setTag(R.id.spinner6);

		button1 = (Button) findViewById(R.id.btn1);

		sp0.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				  keyGroup[0] = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		sp1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				   keyGroup[1] = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		sp2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				keyGroup[2] = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		sp3.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				keyGroup[3] = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		sp4.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				keyGroup[4] = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		sp5.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					keyGroup[5] = 0;
					keyGroup[6] = 0;
					break;
				case 1:
					keyGroup[5] = Contacts.K_VOLDN;
					keyGroup[6] = Contacts.K_VOLUP;
					break;
				case 2:
					keyGroup[5] = Contacts.K_PREV;
					keyGroup[6] = Contacts.K_NEXT;
					break;
				default:
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});

		button1.setOnClickListener(this);
		
		sp0.setSelection(keyGroup[0]);
		sp1.setSelection(keyGroup[1]);
		sp2.setSelection(keyGroup[2]);
		sp3.setSelection(keyGroup[3]);
		sp4.setSelection(keyGroup[4]);
		if(keyGroup[5]==0){
			sp5.setSelection(0);
		}else if(keyGroup[5]==Contacts.K_VOLDN){
			sp5.setSelection(1);
		}else if(keyGroup[5]==Contacts.K_PREV){
			sp5.setSelection(2);
		}
		

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn1:
			Log.i("cxs","=======btn1==");
			saveKeyCode();
			break;
		default:
			break;
		}
	}

	private void saveKeyCode() {
		// TODO Auto-generated method stub
		android.provider.Settings.System.putInt(getContentResolver(),
				Contacts.DZ_KEYCODE0, keyGroup[0]);
		android.provider.Settings.System.putInt(getContentResolver(),
				Contacts.DZ_KEYCODE1, keyGroup[1]);
		android.provider.Settings.System.putInt(getContentResolver(),
				Contacts.DZ_KEYCODE2, keyGroup[2]);
		android.provider.Settings.System.putInt(getContentResolver(),
				Contacts.DZ_KEYCODE3, keyGroup[3]);
		android.provider.Settings.System.putInt(getContentResolver(),
				Contacts.DZ_KEYCODE4, keyGroup[4]);
		android.provider.Settings.System.putInt(getContentResolver(),
				Contacts.DZ_KEYCODE5, keyGroup[5]);
		android.provider.Settings.System.putInt(getContentResolver(),
				Contacts.DZ_KEYCODE6, keyGroup[6]);
	}

}
