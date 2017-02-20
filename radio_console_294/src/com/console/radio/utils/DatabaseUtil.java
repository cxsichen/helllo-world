package com.console.radio.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseUtil {
	private MyHelper helper;
	Context context;

	public DatabaseUtil(Context context) {
		super();
		helper = new MyHelper(context);
		this.context = context;
	}

	/**
	 * @param String
	 * */
	public boolean Insert(int freq) {
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "insert into " + MyHelper.TABLE_NAME + "(freq) values ("
				+ "'" + freq + "'" + ")";
		try {
			db.execSQL(sql);
			return true;
		} catch (SQLException e) {
			Log.e("err", "insert failed");
			return false;
		} finally {
			db.close();
		}

	}

	public void revertSeq() {
		helper.deleteDatabase(context);
	}

	public Set<Integer> queryAll() {
		SQLiteDatabase db = helper.getReadableDatabase();
		Set<Integer> list = new HashSet<Integer>();
		Cursor cursor = db.query(MyHelper.TABLE_NAME, null, null, null, null,
				null, null);

		while (cursor.moveToNext()) {
			// cursor.getInt(cursor.getColumnIndex("freq"));
			list.add(cursor.getInt(cursor.getColumnIndex("freq")));
		}
		db.close();
		return list;
	}

}
