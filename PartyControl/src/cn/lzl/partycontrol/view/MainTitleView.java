package cn.lzl.partycontrol.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import cn.lzl.partycontrol.R;
import cn.lzl.partycontrol.utils.Trace;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;


public class MainTitleView extends BaseView implements android.view.View.OnClickListener{
    
    private ImageButton mMainTitleHomeBtn;
    private ImageButton mMainTitleVoiceBtn;
    private ImageButton mMainTitleGpsBtn;
    private ImageButton mMainTitleCloseScreenBtn;
    private TextView mMainTitleTimeTv;
    private TextView mMainTitleWeekTv;
    private TextView mMainTitleDateTv;
    
    public MainTitleView(Context context) {
        super(context);
        setContentView(R.layout.activity_main_title);
        initViews();
        initDatas();
        updateDateAndTime();
    }
    

    public MainTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setContentView(R.layout.activity_main_title);
        initViews();
        initDatas();
    }



    private void initViews() {
        mMainTitleHomeBtn = (ImageButton)findViewById(R.id.mMainTitleHomeBtn);
        mMainTitleVoiceBtn = (ImageButton)findViewById(R.id.mMainTitleVoiceBtn);
        mMainTitleGpsBtn = (ImageButton)findViewById(R.id.mMainTitleGpsBtn);
        mMainTitleCloseScreenBtn = (ImageButton)findViewById(R.id.mMainTitleCloseScreenBtn);
        
        mMainTitleHomeBtn.setOnClickListener(this);
        mMainTitleVoiceBtn.setOnClickListener(this);
        mMainTitleGpsBtn.setOnClickListener(this);
        mMainTitleCloseScreenBtn.setOnClickListener(this);
        
        mMainTitleTimeTv = (TextView)findViewById(R.id.mMainTitleTimeTv);
        mMainTitleWeekTv = (TextView)findViewById(R.id.mMainTitleWeekTv);
        mMainTitleDateTv = (TextView)findViewById(R.id.mMainTitleDateTv);
    }
    
    private void initDatas() {
    }
    
    public void updateDateAndTime() {
        dateAndTimeData();
        mMainTitleTimeTv.setText(dateAndTimeMap.get(KEY_TIME));
        mMainTitleWeekTv.setText(dateAndTimeMap.get(KEY_WEEK));
        mMainTitleDateTv.setText(dateAndTimeMap.get(KEY_DATE));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mMainTitleHomeBtn:
                mActivity.finish();
                break;
            case R.id.mMainTitleVoiceBtn:
                setVoiceIntent();
                break;
            case R.id.mMainTitleCloseScreenBtn:
                break;
            case R.id.mMainTitleGpsBtn:
                break;

            default:
                break;
        }
    }
    
    
    public void setVoiceIntent(){
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.SoundSettings");
        intent.setComponent(comp);
        intent.setAction("android.intent.action.VIEW");
        mActivity.startActivity(intent);
    }
    
    private Map<String, String> dateAndTimeMap = new HashMap<String,String>();
    private DecimalFormat mDecimalFormat = new DecimalFormat("00");
    public static final String KEY_DATE = "date";
    public static final String KEY_TIME = "time";
    public static final String KEY_WEEK = "week";
    public void dateAndTimeData(){
        dateAndTimeMap.clear();
        final Calendar c = Calendar.getInstance();  
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));  
        String mYear = String.valueOf(c.get(Calendar.YEAR)); 
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        String mHour = mDecimalFormat.format(c.get(Calendar.HOUR_OF_DAY));
        String mMin = mDecimalFormat.format(c.get(Calendar.MINUTE));
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        dateAndTimeMap.put(KEY_DATE, mYear + "-" + mMonth + "-" + mDay);
        dateAndTimeMap.put(KEY_TIME, mHour + ":" + mMin);
        if("1".equals(mWay)){  
            mWay ="天";  
        }else if("2".equals(mWay)){  
            mWay ="一";  
        }else if("3".equals(mWay)){  
            mWay ="二";  
        }else if("4".equals(mWay)){  
            mWay ="三";  
        }else if("5".equals(mWay)){  
            mWay ="四";  
        }else if("6".equals(mWay)){  
            mWay ="五";  
        }else if("7".equals(mWay)){  
            mWay ="六";  
        } 
        dateAndTimeMap.put(KEY_WEEK, "星期"+mWay);
    } 
    
}
