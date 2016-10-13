package com.example.txe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.example.txe.ChooseFragment.CallBack;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

public class ChooseActivity extends Activity {

	private SQLiteDatabase db;
	private List<ChooseFragment> fragmentList;
	private int fragmentLevel = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide_layout);
		openDataBase();
		initFragment();
		fragmentList.get(0).setCanItem(queryByParentId(0), null);
		for (int i = 0; i < fragmentList.size(); i++) {
			fragmentList.get(i).setCallBack(new CallBack() {

				@Override
				public void setCallback(CanItem canItem) {
					// TODO Auto-generated method stub
					if (canItem.getLevel() < 4) { // 跳转到下一级
						goToNext(canItem);
					} else { //
						fragmentList.get(3).showDialog(
								fragmentList.get(0).getParentTitle(),
								fragmentList.get(1).getParentTitle(),
								fragmentList.get(2).getParentTitle(),
								fragmentList.get(3).getParentTitle(),
								canItem.getTitle(), canItem.getName());
					}
				}

				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
					ChooseActivity.this.finish();
				}

			});
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (fragmentLevel != 0) {
			goToPre();
		} else {
			super.onBackPressed();
		}
	}

	private void goToPre() {
		// TODO Auto-generated method stub
		switchContent(fragmentList.get(fragmentLevel),
				fragmentList.get(fragmentLevel - 1));
		fragmentLevel = fragmentLevel - 1;
	}

	private void goToNext(CanItem canItem) {
		// TODO Auto-generated method stub
		int level = canItem.getLevel();
		fragmentList.get(level).setCanItem(queryByParentId(canItem.getId()),
				canItem.getTitle());
		switchContent(fragmentList.get(level - 1), fragmentList.get(level));
		fragmentLevel = level;
	}

	private void initFragment() {
		// TODO Auto-generated method stub
		fragmentList = new ArrayList<ChooseFragment>();
		for (int i = 0; i < 4; i++) {
			ChooseFragment fragment = new ChooseFragment();
			fragmentList.add(fragment);
		}
		getFragmentManager().beginTransaction()
				.replace(R.id.fragment_layout, fragmentList.get(0)).commit();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		closeDataBase();
	}

	private List<CanItem> queryByParentId(int parentId) {
		if (db == null) {
			openDataBase();
		}
		List<CanItem> list = new ArrayList<CanItem>();
		Cursor cursor = db.rawQuery("select * from aa where parentId=?",
				new String[] { String.valueOf(parentId) });
		if (cursor != null) {
			while (cursor.moveToNext()) {
				CanItem canItem = new CanItem();
				canItem.setId(cursor.getInt(cursor.getColumnIndex("id")));
				canItem.setParentId(cursor.getInt(cursor
						.getColumnIndex("parentId")));
				canItem.setLevel(cursor.getInt(cursor.getColumnIndex("level")));
				canItem.setTitle(cursor.getString(cursor
						.getColumnIndex("title")));
				canItem.setName(cursor.getString(cursor.getColumnIndex("name")));
				list.add(canItem);
			}
			cursor.close();
		}
		return list;
	}

	private void openDataBase() {
		// TODO Auto-generated method stub
		// 打开数据库输出流
		SQLdm s = new SQLdm();
		try {
			db = s.openDatabase(getApplicationContext());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void closeDataBase() {
		if (db != null) {
			db.close();
		}
	}

	public void switchContent(Fragment from, Fragment to) {
		if (from != to) {
			if (!to.isAdded()) {
				getFragmentManager().beginTransaction().hide(from)
						.add(R.id.fragment_layout, to).commit();
			} else {
				getFragmentManager().beginTransaction().hide(from).show(to)
						.commit();
			}
		}
	}

}
