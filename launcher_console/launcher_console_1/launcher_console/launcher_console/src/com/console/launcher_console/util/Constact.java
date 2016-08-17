package com.console.launcher_console.util;

import android.net.Uri;

public interface Constact {

	public final static String STOPNAVI="com.inet.broadcast.stoptnavi";
	public final static String STARTNAVI="com.inet.broadcast.startnavi";
	public static final String ACC_STATE = "acc_state";
	public static final String TTS_SHOW = "tts_show";
	public static final String BACK_CAR = "back_car_state";

	
	public static final String NAVI_AUTHORITY = "com.zzj.softwareservice.NaviProvider";
    public static final Uri NAVI_CONTENT_URI = Uri.parse("content://" + NAVI_AUTHORITY + "/navi");
    public static final String MANEUVER_IMAGE = "maneuver_Image"; //图标
    public static final String NEXT_ROADNAME = "next_roadName"; //下一个道路名    string (名称详情)
    public static final String NEXT_ROAD_DISTANCE = "next_road_distance";  // 距离下一个机动点距离（以米为单位）  int (距离)
    public static final String IS_NAVING = "is_naving"; // 0 表示导航结束  1表示正在导航
    public static final String TOTAL_REMAIN_TIME = "total_remain_time";
    public static final String TOTAL_REMAIN_DISTANCE = "total_remain_distance";
    public static final String MAP_INDEX = "MAP_INDEX";
    public static final int MODE_RADIO = 0x76;
    public static final String MODE = "Console_mode";
    public static final String CHECKMODE = "Console_checkmode";
    public static final String APPLIST = "Console_applist";
    public static final String KEY_VOLUME_VALUE = "volume_value";
    public static final String MCU_VERSION = "mcu_version";
    public final static String FMSTATUS = "fmstatus";
    public final static String USER_SAVE_BRIGHTNESS= "user_brightness";
    public final static int DEFAULT_BRIGHTNESS = 110;

}
