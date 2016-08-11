package com.autonavi.external.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 坐标点对象。 
 *  
 * @author zhouzhongjiang.zzj
 *
 */
public class EGeoPoint extends BaseEData{
	private static final String KEY_LONGITUDE = "longitude";
	private static final String KEY_LATITUDE = "latitude";
	
	private double latitude;
	private double longitude;
	
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	@Override
	public JSONObject wrapToJson() {
		JSONObject jResult = new JSONObject();
    	try {
    		jResult.put(KEY_EDATA_TYPE, getEDataType());
	    	jResult.put(KEY_LONGITUDE, longitude);
	    	jResult.put(KEY_LATITUDE, latitude);
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	return jResult;
	}
	
	@Override
	public BaseEData unwrapFromJson(String data) throws JSONException {
		JSONObject jData = new JSONObject(data);
		setLongitude(jData.optDouble(KEY_LONGITUDE));
		setLatitude(jData.optDouble(KEY_LATITUDE));
		return this;
	}
	
	@Override
	public String getEDataType(){
		return EGeoPoint.class.toString();
	}
}
