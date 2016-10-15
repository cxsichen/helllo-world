package com.example.txe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.util.Xml;

public class xmlUtils {

	public static final String SETTING_TAG = "console_setting";
	public static final String CAN_INFORMATON = "CAN_Informaion";
	public static final String CAN_CLASS_NAME = "CAN_Class_Name";

	final static String DIR_PATH = "/sdcard/console_setting.xml";
	static String[] group = { CAN_INFORMATON, CAN_CLASS_NAME };

	public static boolean createADXML(Context context) {
		boolean bFlag = false;
		FileOutputStream fileos = null;
		File newXmlFile = new File(DIR_PATH);
		try {
			if (newXmlFile.exists()) {
				bFlag = newXmlFile.delete();
			} else {
				bFlag = true;
			}

			if (bFlag) {
				if (newXmlFile.createNewFile()) {
					fileos = new FileOutputStream(newXmlFile);
					XmlSerializer serializer = Xml.newSerializer();
					serializer.setOutput(fileos, "UTF-8");
					serializer.startDocument("UTF-8", null);
					serializer.startTag(null, SETTING_TAG);
					for (String str : group) {
						serializer.startTag(null, str);
						serializer.text(Settings.System.getString(
								context.getContentResolver(), str));
						serializer.endTag(null, str);

					}
					serializer.endTag(null, SETTING_TAG);
					serializer.endDocument();
					serializer.flush();
					fileos.close();
				}
			}
		} catch (Exception e) {
			Log.i("cxs", "=====e=====" + e);
			bFlag = false;
		}
		return bFlag;
	}

	public static boolean getSettings(Context context) throws Exception {
		boolean bFlag = false;
		InputStream xml = new FileInputStream(DIR_PATH);
		XmlPullParser pullParser = Xml.newPullParser();
		pullParser.setInput(xml, "UTF-8"); // 为Pull解释器设置要解析的XML数据
		int event = pullParser.getEventType();
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			case XmlPullParser.START_DOCUMENT:
				break;
			case XmlPullParser.START_TAG:
				if (SETTING_TAG.equals(pullParser.getName())) {
					pullParser.nextTag();
					for (String str : group) {
						if (str.equals(pullParser.getName())) {
							String name = pullParser.nextText();
							Settings.System.putString(
									context.getContentResolver(), str,name);
							pullParser.nextTag();
							bFlag=true;
						}
					}
				}
				break;
			case XmlPullParser.END_TAG:
				break;
			}
			event = pullParser.next();
		}

		return bFlag;

	}
}
