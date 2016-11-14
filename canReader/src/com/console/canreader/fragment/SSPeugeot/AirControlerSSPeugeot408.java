package com.console.canreader.fragment.SSPeugeot;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.console.canreader.R;
import com.console.canreader.activity.BaseActivity;
import com.console.canreader.service.CanInfo;
import com.console.canreader.utils.BytesUtil;

public class AirControlerSSPeugeot408 extends BaseActivity implements OnClickListener{
	
	private PopupWindow popupWindow;
	private View popupWindowView;
	
	private ImageView ic_menu;
	private ImageView LEFT_SEAT_TEMP;
	private ImageView MAX_FRONT_LAMP_INDICATOR;
	private ImageView CYCLE_INDICATOR;
	private ImageView REAR_LAMP_INDICATOR;
	private ImageView RIGTHT_SEAT_TEMP;
	private TextView OUTSIDE_TEMPERATURE;
	
	private ImageView left_temp_up;
	private TextView left_temp_value;
	private ImageView left_temp_down;
	
	private ImageView right_temp_up;
	private TextView right_temp_value;
	private ImageView right_temp_down;
	
	private ImageView UPWARD_AIR_INDICATOR;
	private ImageView PARALLEL_AIR_INDICATOR;
	private ImageView DOWNWARD_AIR_INDICATOR;

	private TextView AUTO_STATUS;
	private TextView AC_INDICATOR_STATUS;
	private TextView AC_MAX_STATUS;
	
	private ImageView AIR_RATE_UP;
	private TextView AIR_RATE;
	private ImageView AIR_RATE_DOWN;
	
	private RelativeLayout AIR_STRENGTH_LOW_iv;
	private TextView AIR_STRENGTH_LOW_tv;
	private RelativeLayout AIR_STRENGTH_MIDDLE_iv;
	private TextView AIR_STRENGTH_MIDDLE_tv;
	private RelativeLayout AIR_STRENGTH_HIGH_iv;
	private TextView AIR_STRENGTH_HIGH_tv;
	private TextView Mono_STATUS;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_controler_peugeot_408);
		initView();
		initPopuptWindow();
		try {
			syncView(mCaninfo);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	/** 
     * 创建PopupWindow 
     */  
    protected void initPopuptWindow() {  
        // 获取自定义布局文件ac_more_peugeot_408.xml的视图  
         popupWindowView = getLayoutInflater().inflate(R.layout.ac_more_peugeot_408, null,  
                false);  
        // 点击其他地方消失  
         popupWindowView.setOnTouchListener(new OnTouchListener() {  
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				 if (popupWindow != null && popupWindow.isShowing()) {  
	                    popupWindow.dismiss();  
	                    popupWindow = null;  
	                }  
	                return false; 
			}  
        });
    	 AIR_STRENGTH_LOW_iv=  (RelativeLayout) popupWindowView.findViewById(R.id.AIR_STRENGTH_LOW_iv);
     AIR_STRENGTH_MIDDLE_iv=(RelativeLayout) popupWindowView.findViewById(R.id.AIR_STRENGTH_MIDDLE_iv);
    	 AIR_STRENGTH_HIGH_iv=(RelativeLayout) popupWindowView.findViewById(R.id.AIR_STRENGTH_HIGH_iv);
    	 Mono_STATUS=(TextView) popupWindowView.findViewById(R.id.Mono_STATUS);
    	 AIR_STRENGTH_LOW_iv.setOnClickListener(this);
    	 AIR_STRENGTH_MIDDLE_iv.setOnClickListener(this);
    	 AIR_STRENGTH_HIGH_iv.setOnClickListener(this);
    	 Mono_STATUS.setOnClickListener(this);
        
    }  
	private void initView() {
		
		ic_menu=(ImageView) findViewById(R.id.ic_menu);
		LEFT_SEAT_TEMP=(ImageView) findViewById(R.id.LEFT_SEAT_TEMP);
		MAX_FRONT_LAMP_INDICATOR=(ImageView) findViewById(R.id.MAX_FRONT_LAMP_INDICATOR);
		CYCLE_INDICATOR=(ImageView) findViewById(R.id.CYCLE_INDICATOR);
		REAR_LAMP_INDICATOR=(ImageView) findViewById(R.id.REAR_LAMP_INDICATOR);
		RIGTHT_SEAT_TEMP=(ImageView) findViewById(R.id.RIGTHT_SEAT_TEMP);
		OUTSIDE_TEMPERATURE=(TextView) findViewById(R.id.OUTSIDE_TEMPERATURE);
		LEFT_SEAT_TEMP.setVisibility(View.GONE);
		RIGTHT_SEAT_TEMP.setVisibility(View.GONE);
		CYCLE_INDICATOR.setVisibility(View.GONE);
		
		
		left_temp_up=(ImageView) findViewById(R.id.left_temp_up);
		left_temp_value=(TextView) findViewById(R.id.left_temp_value);
		left_temp_down=(ImageView) findViewById(R.id.left_temp_down);
		
		right_temp_up=(ImageView) findViewById(R.id.right_temp_up);
		right_temp_value=(TextView) findViewById(R.id.right_temp_value);
		right_temp_down=(ImageView) findViewById(R.id.right_temp_down);
		
		UPWARD_AIR_INDICATOR=(ImageView) findViewById(R.id.UPWARD_AIR_INDICATOR);
		PARALLEL_AIR_INDICATOR=(ImageView) findViewById(R.id.PARALLEL_AIR_INDICATOR);
		DOWNWARD_AIR_INDICATOR=(ImageView) findViewById(R.id.DOWNWARD_AIR_INDICATOR);
		
		AUTO_STATUS=(TextView) findViewById(R.id.AUTO_STATUS);
		AC_INDICATOR_STATUS=(TextView) findViewById(R.id.AC_INDICATOR_STATUS);
		AC_MAX_STATUS=(TextView) findViewById(R.id.AC_MAX_STATUS);
		
		AIR_RATE_UP=(ImageView) findViewById(R.id.AIR_RATE_UP);
		AIR_RATE=(TextView) findViewById(R.id.AIR_RATE);
		AIR_RATE_DOWN=(ImageView) findViewById(R.id.AIR_RATE_DOWN);
		
		ic_menu.setOnClickListener(this);
		LEFT_SEAT_TEMP.setOnClickListener(this);
		MAX_FRONT_LAMP_INDICATOR.setOnClickListener(this);
		CYCLE_INDICATOR.setOnClickListener(this);
		REAR_LAMP_INDICATOR.setOnClickListener(this);
		RIGTHT_SEAT_TEMP.setOnClickListener(this);
		
		left_temp_up.setOnClickListener(this);
		left_temp_down.setOnClickListener(this);
		
		right_temp_up.setOnClickListener(this);
		right_temp_down.setOnClickListener(this);
		
		UPWARD_AIR_INDICATOR.setOnClickListener(this);
		PARALLEL_AIR_INDICATOR.setOnClickListener(this);
		DOWNWARD_AIR_INDICATOR.setOnClickListener(this);

		AUTO_STATUS.setOnClickListener(this);
		AC_INDICATOR_STATUS.setOnClickListener(this);
		AC_MAX_STATUS.setOnClickListener(this);
		
		AIR_RATE_UP.setOnClickListener(this);
		AIR_RATE_DOWN.setOnClickListener(this);
		
	}
	
	
	
	public void syncView(CanInfo mCaninfo){
		try {
			MAX_FRONT_LAMP_INDICATOR.setAlpha((float)(mCaninfo.MAX_FRONT_LAMP_INDICATOR==0?0.3:1));
			if(mCaninfo.CYCLE_INDICATOR==0){
				CYCLE_INDICATOR.setImageResource(R.drawable.stat_recirculation);
			}else{
				CYCLE_INDICATOR.setImageResource(R.drawable.stat_recirculation_outside);
			}
			REAR_LAMP_INDICATOR.setAlpha((float)(mCaninfo.REAR_LAMP_INDICATOR==0?0.3:1));
			OUTSIDE_TEMPERATURE.setText(mCaninfo.OUTSIDE_TEMPERATURE+"℃\nOUT");
			
			if(mCaninfo.Mono_STATUS==1){
				if(mCaninfo.DRIVING_POSITON_TEMP==0){
					left_temp_value.setText("LOW");
				}else if(mCaninfo.DRIVING_POSITON_TEMP==255){
					left_temp_value.setText("HIGH");
				}else{
					left_temp_value.setText(mCaninfo.DRIVING_POSITON_TEMP+"°");
				}
				if(mCaninfo.DRIVING_POSITON_TEMP==0){
					right_temp_value.setText("LOW");
				}else if(mCaninfo.DRIVING_POSITON_TEMP==255){
					right_temp_value.setText("HIGH");
				}else{
					right_temp_value.setText(mCaninfo.DRIVING_POSITON_TEMP+"°");
				}

			}else{
				if(mCaninfo.DRIVING_POSITON_TEMP==0){
					left_temp_value.setText("LOW");
				}else if(mCaninfo.DRIVING_POSITON_TEMP==255){
					left_temp_value.setText("HIGH");
				}else{
					left_temp_value.setText(mCaninfo.DRIVING_POSITON_TEMP+"°");
				}
				if(mCaninfo.DEPUTY_DRIVING_POSITON_TEMP==0){
					right_temp_value.setText("LOW");
				}else if(mCaninfo.DEPUTY_DRIVING_POSITON_TEMP==255){
					right_temp_value.setText("HIGH");
				}else{
					right_temp_value.setText(mCaninfo.DEPUTY_DRIVING_POSITON_TEMP+"°");
				}
				
			}
			UPWARD_AIR_INDICATOR.setBackgroundResource(mCaninfo.UPWARD_AIR_INDICATOR==0?R.drawable.bg_button_oval:R.drawable.bg_button_oval_on);
			PARALLEL_AIR_INDICATOR.setBackgroundResource(mCaninfo.PARALLEL_AIR_INDICATOR==0?R.drawable.bg_button_oval:R.drawable.bg_button_oval_on);
			DOWNWARD_AIR_INDICATOR.setBackgroundResource(mCaninfo.DOWNWARD_AIR_INDICATOR==0?R.drawable.bg_button_oval:R.drawable.bg_button_oval_on);
			
			AUTO_STATUS.setBackgroundResource(mCaninfo.AUTO_STATUS==0?R.drawable.bg_button_oval:R.drawable.bg_button_oval_on);
			AC_INDICATOR_STATUS.setBackgroundResource(mCaninfo.AC_INDICATOR_STATUS==0?R.drawable.bg_button_oval:R.drawable.bg_button_oval_on);
			AC_MAX_STATUS.setBackgroundResource(mCaninfo.AC_MAX_STATUS==0?R.drawable.bg_button_oval:R.drawable.bg_button_oval_on);
			
			AIR_RATE.setText(String.valueOf(mCaninfo.AIR_RATE));
			
			if(mCaninfo.AIR_STRENGTH==0){
				AIR_STRENGTH_LOW_iv.setBackgroundResource(R.drawable.bg_button_oval_on);
				AIR_STRENGTH_MIDDLE_iv.setBackgroundResource(R.drawable.bg_button_oval);
				AIR_STRENGTH_HIGH_iv.setBackgroundResource(R.drawable.bg_button_oval);
			}else if(mCaninfo.AIR_STRENGTH==1){
				AIR_STRENGTH_LOW_iv.setBackgroundResource(R.drawable.bg_button_oval);
				AIR_STRENGTH_MIDDLE_iv.setBackgroundResource(R.drawable.bg_button_oval_on);
				AIR_STRENGTH_HIGH_iv.setBackgroundResource(R.drawable.bg_button_oval);
			}else if(mCaninfo.AIR_STRENGTH==2){
				AIR_STRENGTH_LOW_iv.setBackgroundResource(R.drawable.bg_button_oval);
				AIR_STRENGTH_MIDDLE_iv.setBackgroundResource(R.drawable.bg_button_oval);
				AIR_STRENGTH_HIGH_iv.setBackgroundResource(R.drawable.bg_button_oval_on);
			}
			Mono_STATUS.setBackgroundResource(mCaninfo.Mono_STATUS==0?R.drawable.bg_button_oval:R.drawable.bg_button_oval_on);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public void show(CanInfo mCaninfo) {
		try {
			super.show(mCaninfo);
			syncView(mCaninfo);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public void onClick(View v) {
	 try {
		switch (v.getId()) {
		case R.id.ic_menu:
			showPopupWindow();
			popupWindow.showAtLocation(v, Gravity.LEFT, 0, 0);
			break;
		case R.id.AC_INDICATOR_STATUS:
			int v1=mCaninfo.AC_INDICATOR_STATUS==0?1:0;
			sendMsg("5AA5023B02"+BytesUtil.intToHexString(v1));
			break;
		case R.id.AC_MAX_STATUS:
			int v2=mCaninfo.AC_MAX_STATUS==0?1:0;
			sendMsg("5AA5023B03"+BytesUtil.intToHexString(v2));
			break;
		case R.id.AUTO_STATUS:
			int v3=mCaninfo.AUTO_STATUS==0?1:0;
			sendMsg("5AA5023B04"+BytesUtil.intToHexString(v3));
			break;
		case R.id.MAX_FRONT_LAMP_INDICATOR:
			int v4=mCaninfo.MAX_FRONT_LAMP_INDICATOR==0?1:0;
			sendMsg("5AA5023B05"+BytesUtil.intToHexString(v4));
			break;
		case R.id.REAR_LAMP_INDICATOR:
			int v5=mCaninfo.REAR_LAMP_INDICATOR==0?1:0;
			sendMsg("5AA5023B06"+BytesUtil.intToHexString(v5));
			break;
		case R.id.CYCLE_INDICATOR:
			int v6=mCaninfo.CYCLE_INDICATOR==0?1:0;
			sendMsg("5AA5023B07"+BytesUtil.intToHexString(v6));
			Log.i("xyw", "CYCLE_INDICATOR="+"5AA5023B07"+BytesUtil.intToHexString(v6));
			break;
		case R.id.UPWARD_AIR_INDICATOR:
			int v7=mCaninfo.UPWARD_AIR_INDICATOR==0?1:0;
			sendMsg("5AA5023B080"+v7);
			break;
		case R.id.PARALLEL_AIR_INDICATOR:
			int v8=mCaninfo.PARALLEL_AIR_INDICATOR==0?1:0;
			sendMsg("5AA5023B09"+BytesUtil.intToHexString(v8));
			break;
		case R.id.DOWNWARD_AIR_INDICATOR:
			int v9=mCaninfo.DOWNWARD_AIR_INDICATOR==0?1:0;
			sendMsg("5AA5023B0A"+BytesUtil.intToHexString(v9));
			break;
		case R.id.AIR_RATE_UP:
			sendMsg("5AA5023B0B01");
			break;
		case R.id.AIR_RATE_DOWN:
			sendMsg("5AA5023B0B02");
			break;
		case R.id.left_temp_up:
			sendMsg("5AA5023B0C01");
			break;
		case R.id.left_temp_down:
			sendMsg("5AA5023B0C02");
			break;
		case R.id.right_temp_up:
			sendMsg("5AA5023B0D01");
			break;
		case R.id.right_temp_down:
			sendMsg("5AA5023B0D02");
			break;
		case R.id.AIR_STRENGTH_LOW_iv:
			Log.i("xyw", "AIR_STRENGTH_LOW_iv-");
			sendMsg("5AA5023B0E00");
			break;
		case R.id.AIR_STRENGTH_MIDDLE_iv:
			sendMsg("5AA5023B0E01");
			break;
		case R.id.AIR_STRENGTH_HIGH_iv:
			sendMsg("5AA5023B0E02");
			break;
		case R.id.Mono_STATUS:
			int v10=mCaninfo.Mono_STATUS==0?1:0;
			sendMsg("5AA5023B0A"+BytesUtil.intToHexString(v10));
			break;
		default:
			break;
		}
		 
		 
		 
	} catch (Exception e) {
		// TODO: handle exception
	}
		
	}
	
	private void showPopupWindow() {
		// TODO Auto-generated method stub
		  // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度  
        popupWindow = new PopupWindow(popupWindowView, 200, LayoutParams.MATCH_PARENT, true);  
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置动画效果  
        popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
        popupWindow.setFocusable(true);
	}

}
