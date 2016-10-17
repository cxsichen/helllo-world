package com.console.parking.control;

import com.console.canreader.service.CanInfo;
import com.console.canreader.service.ICanCallback;
import com.console.canreader.service.ICanService;
import com.console.parking.R;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class dataControl {

	private Context context;
	LinearLayout layout;

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
				Log.i("cxs", "==cxs==hahah===Volkswagen========="
						+ ((CanInfo) msg.obj).toString());
				updateView((CanInfo) msg.obj);
				break;
			default:
				break;
			}
		}
	};

	public dataControl(Context context, LinearLayout layout) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.layout = layout;
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
		// railLineView = (RailLineView) layout.findViewById(R.id.rail_view);
	}

	int SterringSave = 0;

	private void updateView(CanInfo canInfo) {
		// TODO Auto-generated method stub
		
		if(canInfo.RADAR_ALARM_STATUS==0){			
			f1Rd.setVisibility(View.INVISIBLE);
			f2Rd.setVisibility(View.INVISIBLE);
			f3Rd.setVisibility(View.INVISIBLE);
			f4Rd.setVisibility(View.INVISIBLE);
			r1Rd.setVisibility(View.INVISIBLE);
			r2Rd.setVisibility(View.INVISIBLE);
			r3Rd.setVisibility(View.INVISIBLE);
			r4Rd.setVisibility(View.INVISIBLE);
		}else{
			f1Rd.setVisibility(View.VISIBLE);
			f2Rd.setVisibility(View.VISIBLE);
			f3Rd.setVisibility(View.VISIBLE);
			f4Rd.setVisibility(View.VISIBLE);
			r1Rd.setVisibility(View.VISIBLE);
			r2Rd.setVisibility(View.VISIBLE);
			r3Rd.setVisibility(View.VISIBLE);
			r4Rd.setVisibility(View.VISIBLE);
			try {
				switch (canInfo.CHANGE_STATUS) {
				case 11:
					f1Rd.setImageResource(f1Draws[canInfo.FRONT_LEFT_DISTANCE]);
					f2Rd.setImageResource(f2Draws[canInfo.FRONT_MIDDLE_LEFT_DISTANCE]);
					f3Rd.setImageResource(f3Draws[canInfo.FRONT_MIDDLE_RIGHT_DISTANCE]);
					f4Rd.setImageResource(f4Draws[canInfo.FRONT_RIGHT_DISTANCE]);
					r1Rd.setImageResource(r1Draws[canInfo.BACK_LEFT_DISTANCE]);
					r2Rd.setImageResource(r2Draws[canInfo.BACK_MIDDLE_LEFT_DISTANCE]);
					r3Rd.setImageResource(r3Draws[canInfo.BACK_MIDDLE_RIGHT_DISTANCE]);
					r4Rd.setImageResource(r4Draws[canInfo.BACK_RIGHT_DISTANCE]);
				case 5:
					f1Rd.setImageResource(f1Draws[canInfo.FRONT_LEFT_DISTANCE]);
					f2Rd.setImageResource(f2Draws[canInfo.FRONT_MIDDLE_LEFT_DISTANCE]);
					f3Rd.setImageResource(f3Draws[canInfo.FRONT_MIDDLE_RIGHT_DISTANCE]);
					f4Rd.setImageResource(f4Draws[canInfo.FRONT_RIGHT_DISTANCE]);
					break;
				case 4:
					r1Rd.setImageResource(r1Draws[canInfo.BACK_LEFT_DISTANCE]);
					r2Rd.setImageResource(r2Draws[canInfo.BACK_MIDDLE_LEFT_DISTANCE]);
					r3Rd.setImageResource(r3Draws[canInfo.BACK_MIDDLE_RIGHT_DISTANCE]);
					r4Rd.setImageResource(r4Draws[canInfo.BACK_RIGHT_DISTANCE]);
					break;
				case 8:
					int index = (((canInfo.STERRING_WHELL_STATUS + 540) * 38) / 1080) > 38 ? 38:(((canInfo.STERRING_WHELL_STATUS + 540) * 38) / 1080) < 0 ? 0
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
}
