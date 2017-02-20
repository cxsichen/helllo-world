package com.console.canreader.utils;

import java.io.File;
import java.lang.reflect.Method;
import android.content.Context;
import dalvik.system.DexFile;

public class SystemPropertiesProxy {


	public static String get(Context context, String key) throws IllegalArgumentException {

		String ret = "";

		try {

			ClassLoader cl = context.getClassLoader();
			@SuppressWarnings("rawtypes")
			Class SystemProperties = cl.loadClass("android.os.SystemProperties");


			@SuppressWarnings("rawtypes")
			Class[] paramTypes = new Class[1];
			paramTypes[0] = String.class;

			Method get = SystemProperties.getMethod("get", paramTypes);

	
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


	public static String get(Context context, String key, String def) throws IllegalArgumentException {

		String ret = def;

		try {

			ClassLoader cl = context.getClassLoader();
			@SuppressWarnings("rawtypes")
			Class SystemProperties = cl.loadClass("android.os.SystemProperties");


			@SuppressWarnings("rawtypes")
			Class[] paramTypes = new Class[2];
			paramTypes[0] = String.class;
			paramTypes[1] = String.class;

			Method get = SystemProperties.getMethod("get", paramTypes);


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

	public static Integer getInt(Context context, String key, int def) throws IllegalArgumentException {

		Integer ret = def;

		try {

			ClassLoader cl = context.getClassLoader();
			@SuppressWarnings("rawtypes")
			Class SystemProperties = cl.loadClass("android.os.SystemProperties");

	
			@SuppressWarnings("rawtypes")
			Class[] paramTypes = new Class[2];
			paramTypes[0] = String.class;
			paramTypes[1] = int.class;

			Method getInt = SystemProperties.getMethod("getInt", paramTypes);


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


	public static Long getLong(Context context, String key, long def) throws IllegalArgumentException {

		Long ret = def;

		try {

			ClassLoader cl = context.getClassLoader();
			@SuppressWarnings("rawtypes")
			Class SystemProperties = cl.loadClass("android.os.SystemProperties");


			@SuppressWarnings("rawtypes")
			Class[] paramTypes = new Class[2];
			paramTypes[0] = String.class;
			paramTypes[1] = long.class;

			Method getLong = SystemProperties.getMethod("getLong", paramTypes);


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


	public static Boolean getBoolean(Context context, String key, boolean def) throws IllegalArgumentException {

		Boolean ret = def;

		try {

			ClassLoader cl = context.getClassLoader();
			@SuppressWarnings("rawtypes")
			Class SystemProperties = cl.loadClass("android.os.SystemProperties");

			@SuppressWarnings("rawtypes")
			Class[] paramTypes = new Class[2];
			paramTypes[0] = String.class;
			paramTypes[1] = boolean.class;

			Method getBoolean = SystemProperties.getMethod("getBoolean", paramTypes);

	
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

	public static void set(Context context, String key, String val) throws IllegalArgumentException {

		try {

			@SuppressWarnings("unused")
			DexFile df = new DexFile(new File("/system/app/Settings.apk"));
			@SuppressWarnings("unused")
			ClassLoader cl = context.getClassLoader();
			@SuppressWarnings("rawtypes")
			Class SystemProperties = Class.forName("android.os.SystemProperties");


			@SuppressWarnings("rawtypes")
			Class[] paramTypes = new Class[2];
			paramTypes[0] = String.class;
			paramTypes[1] = String.class;

			Method set = SystemProperties.getMethod("set", paramTypes);

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
