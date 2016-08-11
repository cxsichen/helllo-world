package com.zzj.softwareservice.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class DBConstant {

    public static final String DB_NAME = "navi.db";
    public static final String WEATHER_DB_NAME = "weather.db";

    public static final String AUTHORITY = "com.zzj.softwareservice.NaviProvider";
    public static final String WEATHER_AUTHORITY = "com.zzj.softwareservice.WeatherProvider";

    public static final class NaviTable implements BaseColumns {
        //
        // navi db
    	 public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/navi");
        //
        public static final String TABLE_NAME = "navi";
        public static final String MANEUVER_IMAGE = "maneuver_Image";
        public static final String NEXT_ROAD = "next_roadName";
        public static final String CURRENT_ROADNAME = "current_roadName";
        public static final String NEXT_ROAD_DISTANCE = "next_road_distance";
        public static final String TOTAL_REMAIN_TIME = "total_remain_time";
        public static final String TOTAL_REMAIN_DISTANCE = "total_remain_distance";
        public static final String ISNAVING = "is_naving";
        // public static final String DEFAULE_SORT_ORDER = "modified DESC";
        
        public static final String SQL_CMD_CREATE_TABLE = "create table "
                + TABLE_NAME + " (" + _ID + " integer primary key autoincrement, " + MANEUVER_IMAGE + " text, "
                + NEXT_ROAD + " text, " + CURRENT_ROADNAME + " text, " + NEXT_ROAD_DISTANCE + " integer, " + TOTAL_REMAIN_TIME
                + " integer, " + TOTAL_REMAIN_DISTANCE + " integer, " + ISNAVING+ " integer" + ")";

        public static final String SQL_CMD_DROP_TABLE = "drop table if exists " + TABLE_NAME;
    }
    
    public static final class WeatherTable implements BaseColumns {
    	  // weather db
    	public static final Uri CONTENT_URI = Uri.parse("content://" + WEATHER_AUTHORITY + "/weather");
    	
    	public static final String TABLE_NAME = "weather";
        public static final String CITY = "city";
        public static final String WEATHER = "weather";
        public static final String TEMPERATURE = "temperature";
        public static final String WIND = "wind";
        public static final String IMAGERES = "imageres";
        
        public static final String SQL_CMD_CREATE_TABLE = "create table "
                + TABLE_NAME + " (" + _ID + " integer primary key autoincrement, " + CITY + " text, "
                + WEATHER + " text, " + WIND + " text, " + TEMPERATURE + " text, " + IMAGERES
                + " text )";

        public static final String SQL_CMD_DROP_TABLE = "drop table if exists " + TABLE_NAME;
        
    }

}
