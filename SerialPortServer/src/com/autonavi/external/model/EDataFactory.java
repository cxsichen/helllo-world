package com.autonavi.external.model;

import org.json.JSONException;
import org.json.JSONObject;
import android.text.TextUtils;

/**
 * 外部程序接受到业务广播后，解析业务数据，并转化为BaseEData类型数据的，工程方法类。
 * 
 * @author zhouzhongjiang.zzj
 *
 */
public class EDataFactory {
	
	public static BaseEData create(String data) {
		
		try {
			BaseEData result = null;
			JSONObject jData = new JSONObject(data);
			String EDataType = jData.optString(BaseEData.KEY_EDATA_TYPE);
			if (!TextUtils.isEmpty(EDataType)) {
				if (ENaviInfo.class.toString().equals(EDataType)) {
					result = new ENaviInfo();
				}
				else if(EPOI.class.toString().equals(EDataType)){
					result = new EPOI();
				}
				
				if(result != null){					
					return result.unwrapFromJson(data);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
