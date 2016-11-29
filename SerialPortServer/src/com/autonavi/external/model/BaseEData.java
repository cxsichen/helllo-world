package com.autonavi.external.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 业务广播附带数据的基类
 * 
 * @author zhouzhongjiang.zzj
 *
 */
public abstract class BaseEData {
	public static final String KEY_EDATA_TYPE = "EDataType";
	
	public static final String EDATA_TYPE_UNKNOWN = "edata_type_unknown";
	private String EDataType = EDATA_TYPE_UNKNOWN; // 数据类类型，一般为类名。

	public String getEDataType(){
		return EDataType;
	}
	
	public void setEDataType(String eDataType) {
		EDataType = eDataType;
	}
	
	public String toString(){
		return wrapToJson().toString();
	}
	
	public abstract JSONObject wrapToJson();
	
	public abstract BaseEData unwrapFromJson(String data) throws JSONException;
	
}
