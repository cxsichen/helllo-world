/**
 * Copyright (c) 2012-2015 Beijing Unisound Information Technology Co., Ltd. All right reserved.
 * 
 * @FileName : DBOpenHelper.java
 * @ProjectName : uniCarGUI
 * @PakageName : com.unisound.unicar.gui.database
 * @Author : Xiaodong.He
 * @CreateDate : 2015-09-22
 */
package com.zzj.softwareservice.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/***
 * 
 * @author zzj
 * 
 */
public class WeatherDBOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = WeatherDBOpenHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;
    private static WeatherDBOpenHelper mInstance;

    private WeatherDBOpenHelper(Context context) {
        super(context, DBConstant.WEATHER_DB_NAME, null, DATABASE_VERSION);
    }

    public static synchronized WeatherDBOpenHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new WeatherDBOpenHelper(context);
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate---Create DB...");

        db.execSQL(DBConstant.WeatherTable.SQL_CMD_CREATE_TABLE);
        ContentValues con = new ContentValues();
		con.put(DBConstant.WeatherTable.CITY, "深圳");
		long l = db.insert(DBConstant.WeatherTable.TABLE_NAME, null, con);
		if(l < 0){
			db.insert(DBConstant.WeatherTable.TABLE_NAME, null, con);
		}
		
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade---upgrading database from version " + oldVersion + " to"
                + newVersion + ", which will " + "destroy all old data");

        db.execSQL(DBConstant.WeatherTable.SQL_CMD_DROP_TABLE);

        onCreate(db);
    }

}
