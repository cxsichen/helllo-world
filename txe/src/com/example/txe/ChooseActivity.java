package com.example.txe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.example.txe.ChooseFragment.CallBack;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class ChooseActivity extends Activity {

	private SQLiteDatabase db;
	private List<ChooseFragment> fragmentList;
	private int fragmentLevel = 0;
    private String[] titles={"����","Ʒ��","�ͺ�","����"};
	private ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide_layout);
		openDataBase();
		actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		initFragment();
		fragmentList.get(0).setCanItem(queryByParentId(0));
		actionBar.setTitle(titles[0]);
		for (int i = 0; i < fragmentList.size(); i++) {
			fragmentList.get(i).setCallBack(new CallBack() {

				@Override
				public void setCallback(CanItem canItem) {
					// TODO Auto-generated method stub
					if (canItem.getLevel() < 4) { // ��ת����һ��
						goToNext(canItem);
					} else { //						
						 fragmentList.get(3).showDialog(queryCanInfoMsgById(canItem));
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
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (fragmentLevel != 0) {
			if (fragmentList.get(fragmentLevel).getCanItem() != null)
				if (fragmentList.get(fragmentLevel).getCanItem().size() > 0)
					goToPre(fragmentList.get(fragmentLevel).getCanItem().get(0));
		} else {
			super.onBackPressed();
		}
	}

	/**
	 * ��ת����һ��
	 * 
	 * @param canItem
	 */

	private void goToPre(CanItem canItem) {
		// TODO Auto-generated method stub
		int level = canItem.getLevel();
		fragmentList.get(level - 2).setCanItem(
				queryBychildId(canItem.getParentId()));
		switchContent(fragmentList.get(level - 1), fragmentList.get(level - 2));
		fragmentLevel = level - 2;
		actionBar.setTitle(titles[fragmentLevel]);
	}

	/**
	 * ��ת����һ��
	 * 
	 * @param canItem
	 */

	private void goToNext(CanItem canItem) {
		// TODO Auto-generated method stub
		int level = canItem.getLevel();
		fragmentList.get(level).setCanItem(queryByParentId(canItem.getId()));
		switchContent(fragmentList.get(level - 1), fragmentList.get(level));
		fragmentLevel = level;
		actionBar.setTitle(titles[fragmentLevel]);
	}

	/**
	 * ֱ�ӳ�ʼ��4��fragment����ʾʹ�� ��Ϊֻ��4����ֻ��Ҫ�ı�fragment�����ݾ�����
	 */

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

	/**
	 * ������һ����һ����itemѰ�Ҹ����Ľڵ�
	 * 
	 * @param childId
	 * @return
	 */

	private List<CanItem> queryBychildId(int childId) {
		if (db == null) {
			openDataBase();
		}
		List<CanItem> list = new ArrayList<CanItem>();
		Cursor cursor = db.rawQuery("select * from aa where id=?",
				new String[] { String.valueOf(childId) });
		int parentId = 0;
		if (cursor != null) {
			while (cursor.moveToNext()) {
				parentId = cursor.getInt(cursor.getColumnIndex("parentId"));
			}
			cursor.close();
		}
		Cursor parentIdCursor = db.rawQuery(
				"select * from aa where parentId=?",
				new String[] { String.valueOf(parentId) });

		if (parentIdCursor != null) {
			while (parentIdCursor.moveToNext()) {
				CanItem canItem = new CanItem();
				canItem.setId(parentIdCursor.getInt(parentIdCursor
						.getColumnIndex("id")));
				canItem.setParentId(parentIdCursor.getInt(parentIdCursor
						.getColumnIndex("parentId")));
				canItem.setLevel(parentIdCursor.getInt(parentIdCursor
						.getColumnIndex("level")));
				canItem.setTitle(parentIdCursor.getString(parentIdCursor
						.getColumnIndex("title")));
				canItem.setName(parentIdCursor.getString(parentIdCursor
						.getColumnIndex("name")));
				list.add(canItem);
			}
			cursor.close();
		}
		return list;
	}

	/**
	 * ����һ����itemѰ����һ��������
	 * 
	 * @param childId
	 * @return
	 */

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

	/**
	 * ����id����������Ϣ
	 * 
	 * @param id
	 * @return
	 */
	private CanInfoMsg queryCanInfoMsgById(CanItem canItem) {
		CanInfoMsg mCanInfoMsg = new CanInfoMsg();
		mCanInfoMsg.setName(canItem.getName());
		mCanInfoMsg.setConfiguration(canItem.getTitle());
		if (db == null) {
			openDataBase();
		}
		Cursor cursor = null;
		int id = canItem.getParentId();
		cursor = db.rawQuery("select * from aa where id=?",
				new String[] { String.valueOf(id) });
		if (cursor != null) {
			while (cursor.moveToNext()) {
				mCanInfoMsg.setSort(cursor.getString(cursor
						.getColumnIndex("title")));
				id=cursor.getInt(cursor
						.getColumnIndex("parentId"));
			}
			cursor.close();
		}
		
		cursor = db.rawQuery("select * from aa where id=?",
				new String[] { String.valueOf(id) });
		if (cursor != null) {
			while (cursor.moveToNext()) {
				mCanInfoMsg.setCarType(cursor.getString(cursor
						.getColumnIndex("title")));
				id=cursor.getInt(cursor
						.getColumnIndex("parentId"));
			}
			cursor.close();
		}
		
		cursor = db.rawQuery("select * from aa where id=?",
				new String[] { String.valueOf(id) });
		if (cursor != null) {
			while (cursor.moveToNext()) {
				mCanInfoMsg.setCanTye(cursor.getString(cursor
						.getColumnIndex("title")));
				id=cursor.getInt(cursor
						.getColumnIndex("parentId"));
			}
			cursor.close();
		}
		return mCanInfoMsg;
	}

	private void openDataBase() {
		// TODO Auto-generated method stub
		// �����ݿ������
		SQLdm s = new SQLdm();
		try {
			db = s.openDatabase(getApplicationContext());		
			if(db==null){
				Toast.makeText(this, "���ݿ��ʧ��", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(this, "���ݿ��ʧ��", Toast.LENGTH_LONG).show();
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
