package com.console.launcher_console.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

public class CrashLogUtils {
	/**
	 * @param context
	 * @param ex
	 * @throws JSONException
	 * @throws UnsupportedEncodingException
	 * 上传log到服务器.
	 */
	public static void postCrashLog(Context context, Throwable ex) throws JSONException, UnsupportedEncodingException {
		String crashReportStr = getCrashReport(context, ex);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = df.format(new Date());
		JSONObject datanode = new JSONObject();
		datanode.put("androidVersion", android.os.Build.VERSION.RELEASE);
		datanode.put("androidBuild", android.os.Build.DISPLAY);
		datanode.put("versionName", context.getPackageName());
		datanode.put("versionCode", getVersion(context));
		datanode.put("crashReportStr", crashReportStr);
		datanode.put("date", date);
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost("http://alunchering/AndroidCrashCollector/crashlog");
		httpPost.setHeader("Content-Type", "application/json");
		httpPost.setEntity(new StringEntity(datanode.toString(), "UTF-8"));
		try {
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String msg = EntityUtils.toString(entity);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param context
	 * @param ex
	 * @return
	 */
	private static final String LOG_DIR_PATH = Environment.getExternalStorageDirectory().toString() + "/cxs_log/";
	public static void saveLogToFile(Context context, Throwable ex) throws JSONException, UnsupportedEncodingException {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED))
			return;
		File destDir = new File(LOG_DIR_PATH);
		if (!destDir.exists()) {
			destDir.mkdirs();
		}
		String crashReportStr = getCrashReport(context, ex);
		try {
			//保存名字为 包名 + 时间.
			String path = LOG_DIR_PATH + getAppInfo(context) + timeStr() + ".txt";
			FileWriter fw = new FileWriter(path);
			fw.write(crashReportStr);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String getCrashReport(Context context, Throwable ex) {
		StringBuffer exceptionStr = new StringBuffer();
		Writer w = new StringWriter();
		ex.printStackTrace(new PrintWriter(w));
		exceptionStr.append(w.toString());
		return exceptionStr.toString();
	}

	public static String getVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	private static String getAppInfo(Context context) {
		try {
			String pkName = context.getPackageName();
			String versionName = context.getPackageManager().getPackageInfo(pkName, 0).versionName;
	//		int versionCode = context.getPackageManager().getPackageInfo(pkName, 0).versionCode;
			return pkName + "_"+versionName;
		} catch (Exception e) {
		}
		return null;
	}

	public static String timeStr() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-MM-SS");//设置日期格式
		return df.format(new Date());
	}
}
