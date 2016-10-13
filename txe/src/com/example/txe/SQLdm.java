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
 * 这个类就是实现从assets目录读取数据库文件然后写入SDcard中,如果在SDcard中存在，就打开数据库，不存在就从assets目录下复制过去
 * 
 * @author Big_Adamapple
 *
 */
public class SQLdm {

	// 数据库存储路径
	String filePath = "data/data/com.example.txe/mydata.db";
	// 数据库存放的文件夹 data/data/com.main.jh 下面
	String pathStr = "data/data/com.example.txe";

	SQLiteDatabase database;
	public static final String KEY_VERSION = "key_version";

	private String getVersionName(Context context) throws Exception {
		// 获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
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
		// 查看数据库文件是否存在
		if (jhPath.exists()&&!n) {
			Log.i("test", "存在数据库");
			// 存在则直接返回打开的数据库
			return SQLiteDatabase.openOrCreateDatabase(jhPath, null);
		} else {
			// 不存在先创建文件夹
			File path = new File(pathStr);
			Log.i("test", "pathStr=" + path);
			if (path.mkdir()) {
				Log.i("test", "创建成功");
			} else {
				Log.i("test", "创建失败");
			}
			;
			try {
				// 得到资源
				AssetManager am = context.getAssets();
				// 得到数据库的输入流
				InputStream is = am.open("mydata.db");
				Log.i("test", is + "");
				// 用输出流写到SDcard上面
				FileOutputStream fos = new FileOutputStream(jhPath);
				Log.i("test", "fos=" + fos);
				Log.i("test", "jhPath=" + jhPath);
				// 创建byte数组 用于1KB写一次
				byte[] buffer = new byte[1024];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					Log.i("test", "得到");
					fos.write(buffer, 0, count);
				}
				// 最后关闭就可以了
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
			// 如果没有这个数据库 我们已经把他写到SD卡上了，然后在执行一次这个方法 就可以返回数据库了
			return openDatabase(context);
		}
	}
}