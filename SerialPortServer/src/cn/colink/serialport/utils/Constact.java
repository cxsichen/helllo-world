package cn.colink.serialport.utils;

import android.net.Uri;
import android.provider.BaseColumns;

public interface Constact  {
	public static final String ACTION_MENU_UP = "com.console.MENU_UP";
	public static final String ACTION_MENU_DOWN = "com.console.MENU_DOWN";
	public static final String ACTION_TEL_HANDUP = "com.console.TEL_HANDUP";
	public static final String ACTION_MENU_LONG_UP = "com.console.MENU_LONG_UP";
	public static final String ACTION_MENU_LONG_DOWN = "com.console.MENU_LONG_DOWN";
	public static final String ACTION_MUSIC_START = "com.console.MUSIC_START";
	public static final String ACTION_PLAY_PAUSE = "com.console.PLAY_PAUSE";
	public static final String ACTION_STOP_MUSIC = "com.console.STOP_MUSIC";
	public static final String ACTION_TAILGATE_CHANGE = "com.console.TAILGATE_CHANGE";
	public final static String TAILDOORSTATUS = "tail_door_status";
	
	public final static String STOPNAVI="com.inet.broadcast.stoptnavi";
	public final static String STARTNAVI="com.inet.broadcast.startnavi";
	public static final String ACC_STATE = "acc_state";
	public static final String ADAS_STATE = "adas_state";
	public static final String TTS_SHOW = "tts_show";
	public static final String BACK_CAR = "back_car_state";        //1 是�?�车  0是�?�完�?
	public static final String NAVING_STATUS = "naving_status";        
	
	public static final String NAVI_AUTHORITY = "com.zzj.softwareservice.NaviProvider";
    public static final Uri NAVI_CONTENT_URI = Uri.parse("content://" + NAVI_AUTHORITY + "/navi");
    public static final String MANEUVER_IMAGE = "maneuver_Image"; //图标
    public static final String NEXT_ROADNAME = "next_roadName"; //下一个道路名    string (名称详情)
    public static final String NEXT_ROAD_DISTANCE = "next_road_distance";  // 距离下一个机动点距离（以米为单位�?  int (距离)
    public static final String IS_NAVING = "is_naving"; // 0 表示导航结束  1表示正在导航
    public static final String TOTAL_REMAIN_TIME = "total_remain_time";
    public static final String TOTAL_REMAIN_DISTANCE = "total_remain_distance";
    public static final String MAP_INDEX = "MAP_INDEX";
    public final static String CAMAPPCHOOSE = "camAppChoose";
    public static final int MODE_RADIO = 0x76;
    public static final String MODE = "Console_mode";
    public static final String CHECKMODE = "Console_checkmode";
    public static final String APPLIST = "Console_applist";
    public static final String KEY_VOLUME_VALUE = "volume_value";
    public static final String MCU_VERSION = "mcu_version";
    public final static String FMSTATUS = "fmstatus";
    public final static String USER_SAVE_BRIGHTNESS= "user_brightness";
    public final static String FACTORY_SOUND="factory_sound";
    public final static int DEFAULT_BRIGHTNESS = 60;
    public final static boolean DEBUG = true;
    
    // weather db
    public static final String AUTHORITY = "com.zzj.softwareservice.NaviProvider";
    public static final String WEATHER_AUTHORITY = "com.zzj.softwareservice.WeatherProvider";
	public static final Uri CONTENT_URI = Uri.parse("content://" + WEATHER_AUTHORITY + "/weather");
	
	public static final String TABLE_NAME = "weather";
    public static final String CITY = "city";
    public static final String WEATHER = "weather";
    public static final String TEMPERATURE = "temperature";
    public static final String WIND = "wind";
    public static final String IMAGERES = "imageres";
    public static final String _ID = "_id";
    public static final String _COUNT = "_count";
    
    public static final String SQL_CMD_CREATE_TABLE = "create table "
            + TABLE_NAME + " (" + _ID + " integer primary key autoincrement, " + CITY + " text, "
            + WEATHER + " text, " + WIND + " text, " + TEMPERATURE + " text, " + IMAGERES
            + " text )";

    public static final String SQL_CMD_DROP_TABLE = "drop table if exists " + TABLE_NAME;

}
