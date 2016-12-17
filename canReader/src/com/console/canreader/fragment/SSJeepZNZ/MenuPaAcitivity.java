package com.console.canreader.fragment.SSJeepZNZ;

import com.console.canreader.R;
import com.console.canreader.activity.BaseActivity;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;
import com.console.canreader.utils.Contacts;
import com.console.canreader.view.CarSelectedView;
import com.console.canreader.view.CarSelectedView.OnPositionChangedListener;
import com.console.canreader.view.VerticalSeekBar;
import com.console.canreader.view.VerticalSeekBar.OnProgressChangedListener;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuPaAcitivity extends BaseActivity {

	private VerticalSeekBar seekBarTRE;
	private VerticalSeekBar seekBarMID;
	private VerticalSeekBar seekBarBAS;
	private VerticalSeekBar seekBarVOL;
	private VerticalSeekBar seekBarBal;
	private VerticalSeekBar seekBarFade;
	private TextView treTv;
	private TextView midTv;
	private TextView basTv;
	private TextView volTv;
	private TextView balTv;
	private TextView fadeTv;
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case Contacts.MSG_UPDATA_UI:
				try {
					CanInfo caninfo = getCanInfo();
					seekBarTRE.setNumProgress(caninfo.TRE_VOLUME - 1);
					seekBarMID.setNumProgress(caninfo.MID_VOLUME - 1);
					seekBarBAS.setNumProgress(caninfo.BAS_VOLUME - 1);
					seekBarFade.setNumProgress(caninfo.FB_BALANCE - 1);
					seekBarVOL.setNumProgress(caninfo.EQL_VOLUME);
					seekBarBal.setNumProgress(caninfo.LR_BALANCE - 1);
				} catch (Exception e) {
					// TODO: handle exception
				}

				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ssjeep_equalizer_layout);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub

		seekBarTRE = (VerticalSeekBar) findViewById(R.id.SeekBarTRE);
		seekBarMID = (VerticalSeekBar) findViewById(R.id.SeekBarMID);
		seekBarBAS = (VerticalSeekBar) findViewById(R.id.SeekBarBas);
		seekBarVOL = (VerticalSeekBar) findViewById(R.id.SeekBarVol);
		seekBarBal = (VerticalSeekBar) findViewById(R.id.SeekBarBal);
		seekBarFade = (VerticalSeekBar) findViewById(R.id.SeekBarFad);

		seekBarVOL.setusrProgress(0x26);
		seekBarTRE.setusrProgress(0x13 - 1);
		seekBarMID.setusrProgress(0x13 - 1);
		seekBarBAS.setusrProgress(0x13 - 1);
		seekBarBal.setusrProgress(0x13 - 1);
		seekBarFade.setusrProgress(0x13 - 1);

		treTv = (TextView) findViewById(R.id.tre_tv);
		midTv = (TextView) findViewById(R.id.mid_tv);
		basTv = (TextView) findViewById(R.id.bas_tv);
		volTv = (TextView) findViewById(R.id.vol_tv);
		balTv = (TextView) findViewById(R.id.balance_tv);
		fadeTv = (TextView) findViewById(R.id.fade_tv);
		
		seekBarMID
				.setOnProgressChangedListener(new OnProgressChangedListener() {

					@Override
					public void OnChange(int progress) {
						// TODO Auto-generated method stub
						midTv.setText(progress + 1 + "");
					}

					@Override
					public void OnStop(int progress) {
						// TODO Auto-generated method stub
						sendMsg("AA5502AD05" + BytesUtil.intToHexString(progress+1));

					}
				});

		seekBarTRE
				.setOnProgressChangedListener(new OnProgressChangedListener() {

					@Override
					public void OnChange(int progress) {
						// TODO Auto-generated method stub
						treTv.setText(progress + 1 + "");

					}

					@Override
					public void OnStop(int progress) {
						// TODO Auto-generated method stub
						sendMsg("AA5502AD06" + BytesUtil.intToHexString(progress+1));
					}
				});

		seekBarBAS
				.setOnProgressChangedListener(new OnProgressChangedListener() {

					@Override
					public void OnChange(int progress) {
						// TODO Auto-generated method stub
						basTv.setText(progress + 1 + "");
					}

					@Override
					public void OnStop(int progress) {
						// TODO Auto-generated method stub
						sendMsg("AA5502AD04" + BytesUtil.intToHexString(progress+1));

					}
				});

		seekBarBal
				.setOnProgressChangedListener(new OnProgressChangedListener() {

					@Override
					public void OnChange(int progress) {
						// TODO Auto-generated method stub
						balTv.setText(progress + 1 + "");
					}

					@Override
					public void OnStop(int progress) {
						// TODO Auto-generated method stub
						sendMsg("AA5502AD02" + BytesUtil.intToHexString(progress+1));

					}
				});
		seekBarFade
		.setOnProgressChangedListener(new OnProgressChangedListener() {

			@Override
			public void OnChange(int progress) {
				// TODO Auto-generated method stub
				fadeTv.setText(progress + 1 + "");
			}

			@Override
			public void OnStop(int progress) {
				// TODO Auto-generated method stub
				sendMsg("AA5502AD03" + BytesUtil.intToHexString(progress+1));

			}
		});

		seekBarVOL
				.setOnProgressChangedListener(new OnProgressChangedListener() {

					@Override
					public void OnChange(int progress) {
						// TODO Auto-generated method stub
						volTv.setText(progress + "");
					}

					@Override
					public void OnStop(int progress) {
						// TODO Auto-generated method stub
						sendMsg("AA5502AD01"+BytesUtil.intToHexString(progress));
					}
				});

	}

	private String adjustNum(int num) {
		String tmp = Integer.toHexString(num).toUpperCase();
		if (tmp.length() > 2) {
			tmp = tmp.substring(tmp.length() - 2, tmp.length());
		} else if (tmp.length() == 1) {
			tmp = "0" + tmp;
		}
		return tmp;
	}

	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		super.show(mCaninfo);
	//	 mHandler.sendEmptyMessageDelayed(Contacts.MSG_UPDATA_UI, 500);
	}

	@Override
	public void serviceConnected() {
		// TODO Auto-generated method stub
		super.serviceConnected();
		mHandler.sendEmptyMessageDelayed(Contacts.MSG_UPDATA_UI, 500);
	}

}
