package com.example.txe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * ��������ʵ�ִ�assetsĿ¼��ȡ���ݿ��ļ�Ȼ��д��SDcard��,�����SDcard�д��ڣ��ʹ����ݿ⣬�����ھʹ�assetsĿ¼�¸��ƹ�ȥ
 * 
 * @author Big_Adamapple
 *
 */
public class SQLdm {

	// ���ݿ�洢·��
	String filePath = "data/data/com.example.txe/mydata.db";
	// ���ݿ��ŵ��ļ��� data/data/com.main.jh ����
	String pathStr = "data/data/com.example.txe";

	SQLiteDatabase database;
	public static final String KEY_VERSION = "key_version";

	private String getVersionName(Context context) throws Exception {
		// ��ȡpackagemanager��ʵ��
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()���㵱ǰ��İ�����0�����ǻ�ȡ�汾��Ϣ
		PackageInfo packInfo = packageManager.getPackageInfo(
				context.getPackageName(), 0);
		String version = packInfo.versionName;
		return version;
	}

	private static SharedPreferences getPreferences(Context context) {
		return context.getSharedPreferences(context.getPackageName(), 0);
	}

	private static SharedPreferences.Editor getPreferencesEditor(Context context) {
		return context.getSharedPreferences(context.getPackageName(), 0).edit();
	}

	public SQLiteDatabase openDatabase(Context context) throws Exception {
		System.out.println("filePath:" + filePath);
		File jhPath = new File(filePath);
		String version = getVersionName(context);
		Boolean n =false;
		if (!version.equals(getPreferences(context).getString(KEY_VERSION, ""))) {
			getPreferencesEditor(context).putString(KEY_VERSION, version)
					.commit();
			n=true;
		}
		// �鿴���ݿ��ļ��Ƿ����
		if (jhPath.exists()&&!n) {
			Log.i("test", "�������ݿ�");
			// ������ֱ�ӷ��ش򿪵����ݿ�
			return SQLiteDatabase.openOrCreateDatabase(jhPath, null);
		} else {
			// �������ȴ����ļ���
			File path = new File(pathStr);
			Log.i("test", "pathStr=" + path);
			if (path.mkdir()) {
				Log.i("test", "�����ɹ�");
			} else {
				Log.i("test", "����ʧ��");
			}
			;
			try {
				// �õ���Դ
				AssetManager am = context.getAssets();
				// �õ����ݿ��������
				InputStream is = am.open("mydata.db");
				Log.i("test", is + "");
				// �������д��SDcard����
				FileOutputStream fos = new FileOutputStream(jhPath);
				Log.i("test", "fos=" + fos);
				Log.i("test", "jhPath=" + jhPath);
				// ����byte���� ����1KBдһ��
				byte[] buffer = new byte[1024];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					Log.i("test", "�õ�");
					fos.write(buffer, 0, count);
				}
				// ���رվͿ�����
				Log.i("cxs", "======hh===");
				fos.flush();
				fos.close();
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.i("cxs", "==========" + e);
				return null;
			}
			// ���û��������ݿ� �����Ѿ�����д��SD�����ˣ�Ȼ����ִ��һ��������� �Ϳ��Է������ݿ���
			return openDatabase(context);
		}
	}
}