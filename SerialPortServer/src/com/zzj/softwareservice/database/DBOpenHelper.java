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
public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = DBOpenHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 102;
    private static DBOpenHelper mInstance;

    private DBOpenHelper(Context context) {
        super(context, DBConstant.DB_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DBOpenHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DBOpenHelper(context);
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate---Create DB...");

        db.execSQL(DBConstant.NaviTable.SQL_CMD_CREATE_TABLE);
        ContentValues con = new ContentValues();
		con.put(DBConstant.NaviTable.ISNAVING, 0);
		long l = db.insert(DBConstant.NaviTable.TABLE_NAME, null, con);
		if(l < 0)
		 db.insert(DBConstant.NaviTable.TABLE_NAME, null, con);
		
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade---upgrading database from version " + oldVersion + " to"
                + newVersion + ", which will " + "destroy all old data");

        db.execSQL(DBConstant.NaviTable.SQL_CMD_DROP_TABLE);

        onCreate(db);
    }

}
