package com.console.equalizer.util;

import java.io.File;
import java.lang.reflect.Method;
import android.content.Context;
import dalvik.system.DexFile;

public class SystemPropertiesProxy {

	/**
	 * 鏍规嵁缁欏畾Key鑾峰彇鍊�.
	 * 
	 * @return 濡傛灉涓嶅瓨鍦ㄨkey鍒欒繑鍥炵┖瀛楃涓�
	 * @throws IllegalArgumentException
	 *             濡傛灉key瓒呰繃32涓瓧绗﹀垯鎶涘嚭璇ュ紓甯�
	 */
	public static String get(Context context, String key) throws IllegalArgumentException {

		String ret = "";

		try {

			ClassLoader cl = context.getClassLoader();
			@SuppressWarnings("rawtypes")
			Class SystemProperties = cl.loadClass("android.os.SystemProperties");

			// 鍙傛暟绫诲瀷
			@SuppressWarnings("rawtypes")
			Class[] paramTypes = new Class[1];
			paramTypes[0] = String.class;

			Method get = SystemProperties.getMethod("get", paramTypes);

			// 鍙傛暟
			Object[] params = new Object[1];
			params[0] = new String(key);

			ret = (String) get.invoke(SystemProperties, params);

		} catch (IllegalArgumentException iAE) {
			throw iAE;
		} catch (Exception e) {
			ret = "";
		}

		return ret;

	}

	/**
	 * 鏍规嵁Key鑾峰彇鍊�.
	 * 
	 * @return 濡傛灉key涓嶅瓨鍦�, 骞朵笖濡傛灉def涓嶄负绌哄垯杩斿洖def鍚﹀垯杩斿洖绌哄瓧绗︿覆
	 * @throws IllegalArgumentException
	 *             濡傛灉key瓒呰繃32涓瓧绗﹀垯鎶涘嚭璇ュ紓甯�
	 */
	public static String get(Context context, String key, String def) throws IllegalArgumentException {

		String ret = def;

		try {

			ClassLoader cl = context.getClassLoader();
			@SuppressWarnings("rawtypes")
			Class SystemProperties = cl.loadClass("android.os.SystemProperties");

			// 鍙傛暟绫诲瀷
			@SuppressWarnings("rawtypes")
			Class[] paramTypes = new Class[2];
			paramTypes[0] = String.class;
			paramTypes[1] = String.class;

			Method get = SystemProperties.getMethod("get", paramTypes);

			// 鍙傛暟
			Object[] params = new Object[2];
			params[0] = new String(key);
			params[1] = new String(def);

			ret = (String) get.invoke(SystemProperties, params);

		} catch (IllegalArgumentException iAE) {
			throw iAE;
		} catch (Exception e) {
			ret = def;
		}

		return ret;

	}

	/**
	 * 鏍规嵁缁欏畾鐨刱ey杩斿洖int绫诲瀷鍊�.
	 * 
	 * @param key
	 *            瑕佹煡璇㈢殑key
	 * @param def
	 *            榛樿杩斿洖鍊�
	 * @return 杩斿洖涓�涓猧nt绫诲瀷鐨勫��, 濡傛灉娌℃湁鍙戠幇鍒欒繑鍥為粯璁ゅ��
	 * @throws IllegalArgumentException
	 *             濡傛灉key瓒呰繃32涓瓧绗﹀垯鎶涘嚭璇ュ紓甯�
	 */
	public static Integer getInt(Context context, String key, int def) throws IllegalArgumentException {

		Integer ret = def;

		try {

			ClassLoader cl = context.getClassLoader();
			@SuppressWarnings("rawtypes")
			Class SystemProperties = cl.loadClass("android.os.SystemProperties");

			// 鍙傛暟绫诲瀷
			@SuppressWarnings("rawtypes")
			Class[] paramTypes = new Class[2];
			paramTypes[0] = String.class;
			paramTypes[1] = int.class;

			Method getInt = SystemProperties.getMethod("getInt", paramTypes);

			// 鍙傛暟
			Object[] params = new Object[2];
			params[0] = new String(key);
			params[1] = new Integer(def);

			ret = (Integer) getInt.invoke(SystemProperties, params);

		} catch (IllegalArgumentException iAE) {
			throw iAE;
		} catch (Exception e) {
			ret = def;
		}
		return ret;
	}

	/**
	 * 鏍规嵁缁欏畾鐨刱ey杩斿洖long绫诲瀷鍊�.
	 * 
	 * @param key
	 *            瑕佹煡璇㈢殑key
	 * @param def
	 *            榛樿杩斿洖鍊�
	 * @return 杩斿洖涓�涓猯ong绫诲瀷鐨勫��, 濡傛灉娌℃湁鍙戠幇鍒欒繑鍥為粯璁ゅ��
	 * @throws IllegalArgumentException
	 *             濡傛灉key瓒呰繃32涓瓧绗﹀垯鎶涘嚭璇ュ紓甯�
	 */
	public static Long getLong(Context context, String key, long def) throws IllegalArgumentException {

		Long ret = def;

		try {

			ClassLoader cl = context.getClassLoader();
			@SuppressWarnings("rawtypes")
			Class SystemProperties = cl.loadClass("android.os.SystemProperties");

			// 鍙傛暟绫诲瀷
			@SuppressWarnings("rawtypes")
			Class[] paramTypes = new Class[2];
			paramTypes[0] = String.class;
			paramTypes[1] = long.class;

			Method getLong = SystemProperties.getMethod("getLong", paramTypes);

			// 鍙傛暟
			Object[] params = new Object[2];
			params[0] = new String(key);
			params[1] = new Long(def);

			ret = (Long) getLong.invoke(SystemProperties, params);

		} catch (IllegalArgumentException iAE) {
			throw iAE;
		} catch (Exception e) {
			ret = def;
			// TODO
		}

		return ret;

	}

	/**
	 * 鏍规嵁缁欏畾鐨刱ey杩斿洖boolean绫诲瀷鍊�. 濡傛灉鍊间负 'n', 'no', '0', 'false' or 'off' 杩斿洖false.
	 * 濡傛灉鍊间负'y', 'yes', '1', 'true' or 'on' 杩斿洖true. 濡傛灉key涓嶅瓨鍦�, 鎴栬�呮槸鍏跺畠鐨勫��, 鍒欒繑鍥為粯璁ゅ��.
	 * 
	 * @param key
	 *            瑕佹煡璇㈢殑key
	 * @param def
	 *            榛樿杩斿洖鍊�
	 * @return 杩斿洖涓�涓猙oolean绫诲瀷鐨勫��, 濡傛灉娌℃湁鍙戠幇鍒欒繑鍥為粯璁ゅ��
	 * @throws IllegalArgumentException
	 *             濡傛灉key瓒呰繃32涓瓧绗﹀垯鎶涘嚭璇ュ紓甯�
	 */
	public static Boolean getBoolean(Context context, String key, boolean def) throws IllegalArgumentException {

		Boolean ret = def;

		try {

			ClassLoader cl = context.getClassLoader();
			@SuppressWarnings("rawtypes")
			Class SystemProperties = cl.loadClass("android.os.SystemProperties");

			// 鍙傛暟绫诲瀷
			@SuppressWarnings("rawtypes")
			Class[] paramTypes = new Class[2];
			paramTypes[0] = String.class;
			paramTypes[1] = boolean.class;

			Method getBoolean = SystemProperties.getMethod("getBoolean", paramTypes);

			// 鍙傛暟
			Object[] params = new Object[2];
			params[0] = new String(key);
			params[1] = new Boolean(def);

			ret = (Boolean) getBoolean.invoke(SystemProperties, params);

		} catch (IllegalArgumentException iAE) {
			throw iAE;
		} catch (Exception e) {
			ret = def;
			// TODO
		}

		return ret;

	}

	/**
	 * 鏍规嵁缁欏畾鐨刱ey鍜屽�艰缃睘鎬�, 璇ユ柟娉曢渶瑕佺壒瀹氱殑鏉冮檺鎵嶈兘鎿嶄綔.
	 * 
	 * @throws IllegalArgumentException
	 *             濡傛灉key瓒呰繃32涓瓧绗﹀垯鎶涘嚭璇ュ紓甯�
	 * @throws IllegalArgumentException
	 *             濡傛灉value瓒呰繃92涓瓧绗﹀垯鎶涘嚭璇ュ紓甯�
	 */
	public static void set(Context context, String key, String val) throws IllegalArgumentException {

		try {

			@SuppressWarnings("unused")
			DexFile df = new DexFile(new File("/system/app/Settings.apk"));
			@SuppressWarnings("unused")
			ClassLoader cl = context.getClassLoader();
			@SuppressWarnings("rawtypes")
			Class SystemProperties = Class.forName("android.os.SystemProperties");

			// 鍙傛暟绫诲瀷
			@SuppressWarnings("rawtypes")
			Class[] paramTypes = new Class[2];
			paramTypes[0] = String.class;
			paramTypes[1] = String.class;

			Method set = SystemProperties.getMethod("set", paramTypes);

			// 鍙傛暟
			Object[] params = new Object[2];
			params[0] = new String(key);
			params[1] = new String(val);

			set.invoke(SystemProperties, params);

		} catch (IllegalArgumentException iAE) {
			throw iAE;
		} catch (Exception e) {
			// TODO
		}

	}
}
