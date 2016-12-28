package com.console.parking.control;

import com.console.canreader.service.CanInfo;
import com.console.canreader.service.ICanCallback;
import com.console.canreader.service.ICanService;
import com.console.parking.MainActivity;
import com.console.parking.R;
import com.console.parking.util.BytesUtil;
import com.console.parking.util.Contacts;
import com.console.parking.util.PreferenceUtil;
import com.console.parking.view.RailLineView;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SSVolkswagenGolfControl implements OnClickListener {

	private Context context;
	RelativeLayout layout;
	private MainActivity mMainActivity;

	private ICanService mISpService;
	CanInfo mCanInfo;
	private ImageView f1Rd;
	private ImageView f2Rd;
	private ImageView f3Rd;
	private ImageView f4Rd;

	private ImageView r1Rd;
	private ImageView r2Rd;
	private ImageView r3Rd;
	private ImageView r4Rd;

	private ImageView railLine;

	Button button1;
	Button button2;
	Button button3;
	Button button4;

	// private RailLineView railLineView;

	int[] f1Draws = { R.drawable.empty, R.drawable.rd_f_1_1,
			R.drawable.rd_f_1_2, R.drawable.rd_f_1_3, R.drawable.rd_f_1_4 };
	int[] f2Draws = { R.drawable.empty, R.drawable.rd_f_2_1,
			R.drawable.rd_f_2_2, R.drawable.rd_f_2_3, R.drawable.rd_f_2_4 };
	int[] f3Draws = { R.drawable.empty, R.drawable.rd_f_3_1,
			R.drawable.rd_f_3_2, R.drawable.rd_f_3_3, R.drawable.rd_f_3_4 };
	int[] f4Draws = { R.drawable.empty, R.drawable.rd_f_4_1,
			R.drawable.rd_f_4_2, R.drawable.rd_f_4_3, R.drawable.rd_f_4_4 };

	int[] r1Draws = { R.drawable.empty, R.drawable.rd_r_1_1,
			R.drawable.rd_r_1_2, R.drawable.rd_r_1_3, R.drawable.rd_r_1_4 };
	int[] r2Draws = { R.drawable.empty, R.drawable.rd_r_2_1,
			R.drawable.rd_r_2_2, R.drawable.rd_r_2_3, R.drawable.rd_r_2_4 };
	int[] r3Draws = { R.drawable.empty, R.drawable.rd_r_3_1,
			R.drawable.rd_r_3_2, R.drawable.rd_r_3_3, R.drawable.rd_r_3_4 };
	int[] r4Draws = { R.drawable.empty, R.drawable.rd_r_4_1,
			R.drawable.rd_r_4_2, R.drawable.rd_r_4_3, R.drawable.rd_r_4_4 };

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				// dealwithPacket(Volkswagen)msg.obj);
				updateView((CanInfo) msg.obj);
				break;
			default:
				break;
			}
		}
	};

	public SSVolkswagenGolfControl(MainActivity mMainActivity, RelativeLayout layout) {
		// TODO Auto-generated constructor stub
		this.mMainActivity = mMainActivity;
		this.context = mMainActivity;
		this.layout = layout;
		syncCanName();
		layout.findViewById(R.id.ssgolf_layout)
				.setVisibility(View.VISIBLE);
		initView();
		bindService();
	}

	private void initView() {
		// TODO Auto-generated method stub
		f1Rd = (ImageView) layout.findViewById(R.id.f1Rd);
		f2Rd = (ImageView) layout.findViewById(R.id.f2Rd);
		f3Rd = (ImageView) layout.findViewById(R.id.f3Rd);
		f4Rd = (ImageView) layout.findViewById(R.id.f4Rd);

		r1Rd = (ImageView) layout.findViewById(R.id.r1Rd);
		r2Rd = (ImageView) layout.findViewById(R.id.r2Rd);
		r3Rd = (ImageView) layout.findViewById(R.id.r3Rd);
		r4Rd = (ImageView) layout.findViewById(R.id.r4Rd);

		railLine = (ImageView) layout.findViewById(R.id.rail_line);

		button1 = (Button) layout.findViewById(R.id.ssgolf_button1);
		button2 = (Button) layout.findViewById(R.id.ssgolf_button2);
		button3 = (Button) layout.findViewById(R.id.ssgolf_button3);
		button4 = (Button) layout.findViewById(R.id.ssgolf_button4);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);
		button4.setOnClickListener(this);
	}

	int SterringSave = 0;
	int radarStatusSave = -1;

	private void updateView(CanInfo canInfo) {
		// TODO Auto-generated method stub
		if (canInfo.RADAR_ALARM_STATUS != radarStatusSave) {
			radarStatusSave = canInfo.RADAR_ALARM_STATUS;
			if (canInfo.RADAR_ALARM_STATUS == 0) {
				f1Rd.setVisibility(View.INVISIBLE);
				f2Rd.setVisibility(View.INVISIBLE);
				f3Rd.setVisibility(View.INVISIBLE);
				f4Rd.setVisibility(View.INVISIBLE);
				r1Rd.setVisibility(View.INVISIBLE);
				r2Rd.setVisibility(View.INVISIBLE);
				r3Rd.setVisibility(View.INVISIBLE);
				r4Rd.setVisibility(View.INVISIBLE);
			} else {
				f1Rd.setVisibility(View.VISIBLE);
				f2Rd.setVisibility(View.VISIBLE);
				f3Rd.setVisibility(View.VISIBLE);
				f4Rd.setVisibility(View.VISIBLE);
				r1Rd.setVisibility(View.VISIBLE);
				r2Rd.setVisibility(View.VISIBLE);
				r3Rd.setVisibility(View.VISIBLE);
				r4Rd.setVisibility(View.VISIBLE);
			}
		}
		try {
			switch (canInfo.CHANGE_STATUS) {
			case 11:
				updateView(f1Rd, f1Draws[canInfo.FRONT_LEFT_DISTANCE]);
				updateView(f2Rd, f2Draws[canInfo.FRONT_MIDDLE_LEFT_DISTANCE]);
				updateView(f3Rd, f3Draws[canInfo.FRONT_MIDDLE_RIGHT_DISTANCE]);
				updateView(f4Rd, f4Draws[canInfo.FRONT_RIGHT_DISTANCE]);

				updateView(r1Rd, r1Draws[canInfo.BACK_LEFT_DISTANCE]);
				updateView(r2Rd, r2Draws[canInfo.BACK_MIDDLE_LEFT_DISTANCE]);
				updateView(r3Rd, r3Draws[canInfo.BACK_MIDDLE_RIGHT_DISTANCE]);
				updateView(r4Rd, r4Draws[canInfo.BACK_RIGHT_DISTANCE]);
			case 5:
				updateView(f1Rd, f1Draws[canInfo.FRONT_LEFT_DISTANCE]);
				updateView(f2Rd, f2Draws[canInfo.FRONT_MIDDLE_LEFT_DISTANCE]);
				updateView(f3Rd, f3Draws[canInfo.FRONT_MIDDLE_RIGHT_DISTANCE]);
				updateView(f4Rd, f4Draws[canInfo.FRONT_RIGHT_DISTANCE]);
				break;
			case 4:
				updateView(r1Rd, r1Draws[canInfo.BACK_LEFT_DISTANCE]);
				updateView(r2Rd, r2Draws[canInfo.BACK_MIDDLE_LEFT_DISTANCE]);
				updateView(r3Rd, r3Draws[canInfo.BACK_MIDDLE_RIGHT_DISTANCE]);
				updateView(r4Rd, r4Draws[canInfo.BACK_RIGHT_DISTANCE]);
				break;
			case 8:
				int index = (((canInfo.STERRING_WHELL_STATUS + 540) * 38) / 1080) > 38 ? 38
						: (((canInfo.STERRING_WHELL_STATUS + 540) * 38) / 1080) < 0 ? 0
								: (((canInfo.STERRING_WHELL_STATUS + 540) * 38) / 1080);

				if (SterringSave != index) {
					SterringSave = index;
					railLine.setBackgroundResource((R.drawable.g39 - index));
				}
				break;
			default:
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void sendMsg(String msg) {
		try {
			if (mISpService != null) {
				switch (canFirtName) {
				case Contacts.CANFISRTNAMEGROUP.RAISE:
					mISpService.sendDataToSp(BytesUtil.addRZCCheckBit(msg));
					break;
				case Contacts.CANFISRTNAMEGROUP.HIWORLD:					
					mISpService.sendDataToSp(BytesUtil.addSSCheckBit(msg));
					break;
				default:
					break;
				}

			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	 public static String canName="";
	 public static String canFirtName="";
	
	private void syncCanName(){
		canName = PreferenceUtil.getCANName(mMainActivity);
		canFirtName=PreferenceUtil.getFirstTwoString(mMainActivity, canName);
	}

	private void updateView(ImageView iv, int i) {
		// TODO Auto-generated method stub
		if (iv.getTag() == null) {
			iv.setTag(i);
			iv.setImageResource(i);
			return;
		}
		if ((int) iv.getTag() != i) {
			iv.setTag(i);
			iv.setImageResource(i);
		}
	}

	private void bindService() {
		Intent intent = new Intent();
		intent.setClassName("com.console.canreader",
				"com.console.canreader.service.CanService");
		context.bindService(intent, mServiceConnection,
				Context.BIND_AUTO_CREATE);
	}

	public void unBindService() {
		try {
			if (mISpService != null)
				mISpService.removeCliend(mICallback);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		context.unbindService(mServiceConnection);
	}

	private ICanCallback mICallback = new ICanCallback.Stub() {

		@Override
		public void readDataFromServer(CanInfo canInfo) throws RemoteException {
			Message msg = new Message();
			msg.what = 1;
			msg.obj = canInfo;
			mHandler.sendMessage(msg);
		}
	};

	private ServiceConnection mServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mISpService = ICanService.Stub.asInterface(service);
			try {
				mISpService.addClient(mICallback);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			// sendMsg(Contacts.HEX_HOME_TO_FM);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mISpService = null;
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ssgolf_button1:
			sendMsg("5AA502F201FF");
			break;
		case R.id.ssgolf_button2:
			sendMsg("5AA502F202FF");
			break;
		case R.id.ssgolf_button3:
			sendMsg("5AA502F203FF");
			break;
		case R.id.ssgolf_button4:
			sendMsg("5AA502F209FF");
			break;
		default:
			break;
		}
	}
}
