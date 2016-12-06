package cn.colink.serialport;

import cn.colink.serialport.utils.Constact;
import cn.colink.serialport.utils.Contacts;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class TailGateActivity extends Activity implements OnClickListener {

	TextView controlBtn;
	TextView finishBtn;

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
                if(msg.arg1==1){
                	controlBtn.setText(getResources().getString(R.string.close_gate));	
                }else{
                	controlBtn.setText(getResources().getString(R.string.open_gate));	
                	TailGateActivity.this.finish();
                }
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tailgate_layout);
		initView();
		dismissSysDialog();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getContentResolver().unregisterContentObserver(mTailDoorObserver);
	}

	private void initView() {
		// TODO Auto-generated method stub
		controlBtn = (TextView) findViewById(R.id.control_btn);
		finishBtn = (TextView) findViewById(R.id.finish_btn);
		
		controlBtn.setOnClickListener(this);
		finishBtn.setOnClickListener(this);
		
		int state = android.provider.Settings.System.getInt(
				getContentResolver(), Constact.TAILDOORSTATUS, 0);

		 if(state==1){
         	controlBtn.setText(getResources().getString(R.string.close_gate));	
         }else{
         	controlBtn.setText(getResources().getString(R.string.open_gate));	
         }
		getContentResolver()
				.registerContentObserver(
						android.provider.Settings.System
								.getUriFor(Constact.TAILDOORSTATUS),
						true, mTailDoorObserver); // Î²ÃÅ×´Ì¬

	}

	private TailDoorObserver mTailDoorObserver = new TailDoorObserver();

	public class TailDoorObserver extends ContentObserver {
		public TailDoorObserver() {
			super(null);
		}

		@Override
		public void onChange(boolean selfChange) {
			super.onChange(selfChange);
			int state = android.provider.Settings.System.getInt(
					getContentResolver(), Constact.TAILDOORSTATUS, 0);

			Message msg=new Message();
			msg.what=1;
			msg.arg1=state;
			mHandler.sendMessage(msg);
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.finish_btn:
			TailGateActivity.this.finish();
			break;
		case R.id.control_btn:
			handleTAILGATE_CHANGE(TailGateActivity.this);
			break;
		default:
			break;
		}
	}
	
	public static final String ACTION_TAILGATE_CHANGE = "com.console.TAILGATE_CHANGE";
	private void handleTAILGATE_CHANGE(Context context) {
	    // TODO Auto-generated method stub
	    Intent intent = new Intent();
	    intent.setClassName("cn.colink.serialport",
			"cn.colink.serialport.service.SerialPortService");
	    intent.putExtra("keyEvent", ACTION_TAILGATE_CHANGE);
	    context.startService(intent);
	}
	
	private static final String DIS_DIALOG = "com.inet.broadcast.no_disturb";
	
	private void dismissSysDialog() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(DIS_DIALOG);
		sendBroadcast(intent);
	}

}
