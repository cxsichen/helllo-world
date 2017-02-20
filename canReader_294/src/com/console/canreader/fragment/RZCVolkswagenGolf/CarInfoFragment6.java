package com.console.canreader.fragment.RZCVolkswagenGolf;

import java.util.ArrayList;
import java.util.List;

import com.console.canreader.R;
import com.console.canreader.activity.BaseFragment;
import com.console.canreader.activity.VolkswagenGolf.GolfView1;
import com.console.canreader.activity.VolkswagenGolf.GolfView4;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.Contacts;
import com.console.canreader.utils.DensityUtils;
import com.console.canreader.utils.ViewPagerAdapter;
import com.console.canreader.view.ObdView;
import com.console.canreader.view.ViewPageFactory;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.View.OnCreateContextMenuListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

public class CarInfoFragment6 extends BaseFragment {

	private LayoutInflater inflater;
	TextView tv;
	int[] intTemp = new int[6];

	public CarInfoFragment6() {

	}

	String[] str = {
			"Trailer mode 拖车模式",
			"Windscreen could become fogged 小心档风玻璃起雾 ",
			"Operating temperature not maintained 不能维持工作温度 ",
			" No driver detected 未发现驾驶员（driver 有驱动的意思，疑问：未 检测到驱动程序） ",
			"Gearbox requires engine to be running 操作变速箱",
			"Power consumption is high 功耗",
			"Starting start-stop system 启用启-停系统 ",
			"Engine must be running 引擎必须运行",
			"Gradient too steep 坡度太陡 ",
			"Start-stop system is currently not available 启-停系统当前不可用",
			"Emergency brake function intervention 紧急制动功能干预",
			"Brake requires engine to be running 制动器需要发动机运行 ",
			"Power consumption is high 功耗高",
			"Air conditioning requires engine to be running 空调开启需要发动机运行 ",
			"Steering angle too great 转向角太大",
			"Manoeuvering mode 操纵模式",
			"Defrost function is switched on 除霜功能开启 ",
			"Driver seatbelt not fastened 驾驶员未系安全带 ",
			"Driver door open 驾驶室门打开 ",
			"Accelerator is depressed 加速踏板被踩下 ",
			"Windscreen heating is switched on 前窗加热开启",
			"Maximum air conditioning  requires engine to be running 空调最大制冷需要发动机运行 ",
			"Bonnet has been opened 引擎盖未关好 ",
			"Engine stop currently not possible 发动机当前无法停止 ",
			"Off-road mode is activated 越野模式已启用 ",
			"System protection intervention Button/lever activated too often 手刹按钮/杆使用太频繁 ",
			"Gear is engaged 齿轮啮合 ", "Start-stop button pressed 启-停按钮已按下 ",
			"Air conditioning requires engine to be running 空调开启需要发 动机运行 ",
			"ACC intervention ACC 状态 ", "Park Assist is activated 驻车辅助已启动 ",
			"ESC is switched off ESC 关闭 ", "Power consumption is high 功耗高",
			"Start-stop system requires engine to be running 启-停系统需要发动机运行 " };

	@Override
	public void show(CanInfo mCaninfo) {
		// TODO Auto-generated method stub
		super.show(mCaninfo);
		if (mCaninfo != null) {
			if (mCaninfo.CHANGE_STATUS == 10) {
				if (mCaninfo.WARNING_MES_NUM == 0) {
					tv.setText(mCaninfo.START_STOP_MES == 1 ? "Start-stop系统已激活"
							: "Start-stop系统未激活");
				} else {
					String temp = "";
					intTemp[0] = mCaninfo.WARNING_MES_0;
					intTemp[1] = mCaninfo.WARNING_MES_1;
					intTemp[2] = mCaninfo.WARNING_MES_2;
					intTemp[3] = mCaninfo.WARNING_MES_3;
					intTemp[4] = mCaninfo.WARNING_MES_4;
					intTemp[5] = mCaninfo.WARNING_MES_5;
					for (int i = 0; i < mCaninfo.WARNING_MES_NUM; i++) {
						if (intTemp[i] >= str.length) {
							break;
						}
						temp = temp + str[intTemp[i]] + "\n";
					}
					tv.setText(temp);
				}
			}
		}
	}

	@Override
	public void serviceConnected() {
		// TODO Auto-generated method stub
		super.serviceConnected();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		inflater = LayoutInflater.from(getActivity());
		View view = inflater.inflate(R.layout.rzc_golf_layout_1, null);
		tv = (TextView) view.findViewById(R.id.golf_tv_1);
		return view;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();

	}

}
