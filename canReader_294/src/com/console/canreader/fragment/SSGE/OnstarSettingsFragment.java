package com.console.canreader.fragment.SSGE;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.console.canreader.R;
import com.console.canreader.activity.BaseFragment;
import com.console.canreader.activity.Toyota.OilEleActivity;
import com.console.canreader.activity.Toyota.SettingActivity;
import com.console.canreader.activity.Toyota.OilEleActivity.SettingsFragment;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;
import com.console.canreader.utils.PreferenceUtil;

public class OnstarSettingsFragment extends BaseFragment implements
		OnClickListener {

	TextView tv;
	TextView tv1;
	TextView tv2;
	TextView tv3;
	TextView tv4;
	TextView tv5;
	TextView tv6;
	TextView tv7;
	TextView tv8;

	Button button;
	Button button1;
	Button button2;
	Button button3;

	List<View> list = new ArrayList<View>();
	private SoundPool soundPool;
	private TextView phone_view;
	Context mcontext;

	public static final String NUMBER_0 = "0";
	public static final String NUMBER_1 = "1";
	public static final String NUMBER_2 = "2";
	public static final String NUMBER_3 = "3";
	public static final String NUMBER_4 = "4";
	public static final String NUMBER_5 = "5";
	public static final String NUMBER_6 = "6";
	public static final String NUMBER_7 = "7";
	public static final String NUMBER_8 = "8";
	public static final String NUMBER_9 = "9";

	String[] strGp1 = { "关闭", "来电中", "去电中", "已经连接", "空闲" };
	String[] strGp2 = { "普通通话", "碰撞通话", "紧急通话", "路旁协助" };
	String[] strGp3 = { "Disaster", "Amber", "Traffic", "Weather", "Generic",
			"Campaign", "Reminder" };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.ssge_onstar_layout, container,
				false);
		mcontext = getActivity();
		if (soundPool == null) {
			initSound();
		}
		initView(view);
		initFragment();
		return view;
	}

	public void initSound() {
		soundPool = new SoundPool(12, AudioManager.STREAM_DTMF, 2);
		soundPool.load(mcontext, R.raw.dtmf_0, 1);
		soundPool.load(mcontext, R.raw.dtmf_1, 1);
		soundPool.load(mcontext, R.raw.dtmf_2, 1);
		soundPool.load(mcontext, R.raw.dtmf_3, 1);
		soundPool.load(mcontext, R.raw.dtmf_4, 1);
		soundPool.load(mcontext, R.raw.dtmf_5, 1);
		soundPool.load(mcontext, R.raw.dtmf_6, 1);
		soundPool.load(mcontext, R.raw.dtmf_7, 1);
		soundPool.load(mcontext, R.raw.dtmf_8, 1);
		soundPool.load(mcontext, R.raw.dtmf_9, 1);
		soundPool.load(mcontext, R.raw.dtmf_pound, 1);
		soundPool.load(mcontext, R.raw.dtmf_star, 1);

	}

	private void initFragment() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		super.show(mCaninfo);
		try {
			if (mCaninfo != null) {
				tv.setText("状态：" + strGp1[mCaninfo.ONSTAR_STATUS]);
				tv1.setText("通话类型：" + strGp2[mCaninfo.ONSTAR_PHONE_TYPE]);
				tv2.setText("通话标志："
						+ ((mCaninfo.ONSTAR_PHONE_SIGN == 1) ? "静音" : "正常"));

				tv3.setText("通话时间：" + mCaninfo.ONSTAR_PHONE_HOUR + "小时"
						+ mCaninfo.ONSTAR_PHONE_MINUTE + "分钟"
						+ mCaninfo.ONSTAR_PHONE_SECOND + "秒");
				tv4.setText("剩余时间："
						+ (mCaninfo.ONSTAR_LEFTIME_1 * 256 + mCaninfo.ONSTAR_LEFTIME_2));
				tv5.setText("有效期："
						+ (mCaninfo.ONSTAR_EFFECTTIME_YEAR_1 * 256 + mCaninfo.ONSTAR_EFFECTTIME_YEAR_2)
						+ "年" + mCaninfo.ONSTAR_EFFECTTIME_MOUTH + "月"
						+ mCaninfo.ONSTAR_EFFECTTIME_DAY + "日");

				tv6.setText("信息状态："
						+ ((mCaninfo.ONSTAR_WARING_STATUS == 1) ? "Active"
								: "Off"));
				tv7.setText("信息类型：" + strGp3[mCaninfo.ONSTAR_WARING_TYPE]);
				tv8.setText(mCaninfo.ONSTAR_RECEIVE_PHONE);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (soundPool != null) {
			soundPool.release();
		}
	}

	@Override
	public void serviceConnected() {
		// TODO Auto-generated method stub
		super.serviceConnected();
	}

	private void initView(View view) {
		tv = (TextView) view.findViewById(R.id.tv0);
		tv1 = (TextView) view.findViewById(R.id.tv1);
		tv2 = (TextView) view.findViewById(R.id.tv2);
		tv3 = (TextView) view.findViewById(R.id.tv3);
		tv4 = (TextView) view.findViewById(R.id.tv4);
		tv5 = (TextView) view.findViewById(R.id.tv5);
		tv6 = (TextView) view.findViewById(R.id.tv6);
		tv7 = (TextView) view.findViewById(R.id.tv7);
		tv8 = (TextView) view.findViewById(R.id.tv8);

		button = (Button) view.findViewById(R.id.button);
		button1 = (Button) view.findViewById(R.id.button1);
		button2 = (Button) view.findViewById(R.id.button2);
		button3 = (Button) view.findViewById(R.id.button3);
		button.setOnClickListener(this);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
		button3.setOnClickListener(this);

		phone_view = (TextView) view.findViewById(R.id.dial_num);

		list.add(view.findViewById(R.id.a0));
		list.add(view.findViewById(R.id.a1));
		list.add(view.findViewById(R.id.a2));
		list.add(view.findViewById(R.id.a3));
		list.add(view.findViewById(R.id.a4));
		list.add(view.findViewById(R.id.a5));
		list.add(view.findViewById(R.id.a6));
		list.add(view.findViewById(R.id.a7));
		list.add(view.findViewById(R.id.a8));
		list.add(view.findViewById(R.id.a8));
		list.add(view.findViewById(R.id.a9));
		list.add(view.findViewById(R.id.a10));
		list.add(view.findViewById(R.id.a11));
		list.add(view.findViewById(R.id.dial_cancle));
		list.add(view.findViewById(R.id.dial_button));

		for (View view1 : list) {
			view1.setOnClickListener(this);
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button:
			sendMsg("5AA502BA0100");
			break;
		case R.id.button1:
			sendMsg("5AA502BA0200");
			break;
		case R.id.button2:
			sendMsg("5AA502BA0300");
			break;
		case R.id.button3:
			sendMsg("5AA502BA0500");
			break;
		case R.id.a0:
			if (soundPool != null)
				soundPool.play(1, 1, 1, 0, 0, 1);
			input(NUMBER_0);
			break;
		case R.id.a1:
			if (soundPool != null)
				soundPool.play(2, 1, 1, 0, 0, 1);
			input(NUMBER_1);
			break;
		case R.id.a2:
			if (soundPool != null)
				soundPool.play(3, 1, 1, 0, 0, 1);
			input(NUMBER_2);
			break;
		case R.id.a3:
			if (soundPool != null)
				soundPool.play(4, 1, 1, 0, 0, 1);
			input(NUMBER_3);
			break;
		case R.id.a4:
			if (soundPool != null)
				soundPool.play(5, 1, 1, 0, 0, 1);
			input(NUMBER_4);
			break;
		case R.id.a5:
			if (soundPool != null)
				soundPool.play(6, 1, 1, 0, 0, 1);
			input(NUMBER_5);
			break;
		case R.id.a6:
			if (soundPool != null)
				soundPool.play(7, 1, 1, 0, 0, 1);
			input(NUMBER_6);
			break;
		case R.id.a7:
			if (soundPool != null)
				soundPool.play(8, 1, 1, 0, 0, 1);
			input(NUMBER_7);
			break;
		case R.id.a8:
			if (soundPool != null)
				soundPool.play(9, 1, 1, 0, 0, 1);
			input(NUMBER_8);
			break;
		case R.id.a9:
			if (soundPool != null)
				soundPool.play(10, 1, 1, 0, 0, 1);
			input(NUMBER_9);
			break;
		case R.id.a10:
			if (soundPool != null)
				soundPool.play(12, 1, 1, 0, 0, 1);
			input("*");
			break;
		case R.id.a11:
			if (soundPool != null)
				soundPool.play(11, 1, 1, 0, 0, 1);
			input("#");
			break;
		case R.id.dial_cancle:
			delete();
			break;
		case R.id.dial_button:
			if (!TextUtils.isEmpty(phone_view.getText()))
				call(phone_view.getText().toString());
			break;
		default:
			break;
		}
	}

	private void input(String str) {
		String p = phone_view.getText().toString();
		phone_view.setText(p + str);
		sendMsg("5AA502BA04" + BytesUtil.parseAscii(str));

	}

	private void delete() {
		String p = phone_view.getText().toString();
		if (p.length() > 0) {
			phone_view.setText(p.substring(0, p.length() - 1));
		}
	}

	public void call(String phone) {
		if (phone.trim().length() == 13) {
			phone = "+" + phone;
		}
		int temp = phone.length();
		StringBuffer buffer = new StringBuffer();
		if (temp < 32) {
			for (int i = temp; i < 32; i++) {
				buffer.append("00");
			}
		}
		sendMsg("5AA520BB" + BytesUtil.parseAscii(phone) + buffer.toString());

	}

}
