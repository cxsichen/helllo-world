package com.autonavi.external.model;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.text.TextUtils;

/**
 * POI数据类。
 * 
 * @author zhouzhongjiang.zzj
 *
 */
public class EPOI extends BaseEData{
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_PHONE = "phone";
	private static final String KEY_CITY_NAME = "cityName";
	private static final String KEY_CITY_CODE = "cityCode";
	private static final String KEY_ADDR = "addr";
	private static final String KEY_AD_CODE = "adCode";
	private static final String KEY_ICON_URL = "iconURL";
	private static final String KEY_MARKER_TYPE = "markerType";
	private static final String KEY_ENTRANCE_LIST = "entranceList";
	private static final String KEY_EXIT_LIST = "exitList";
	
	private String id = ""; // id
	private String name = ""; // 名称
	private String phone = ""; // 电话
	private String cityName = "";// 城市名称
	private String cityCode = "";// 城市区号
	private String addr = ""; // 地址
	private String adCode = ""; // 城市代码
	private String iconURL = ""; // icon网络地址
	private int markerType = 0; // 类型 ID
	private List<EGeoPoint> entranceList = new ArrayList<EGeoPoint>(); // 入口点列表
	private List<EGeoPoint> exitList = new ArrayList<EGeoPoint>(); // 出口点列表
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getAdCode() {
		return adCode;
	}
	public void setAdCode(String adCode) {
		this.adCode = adCode;
	}
	public String getIconURL() {
		return iconURL;
	}
	public void setIconURL(String iconURL) {
		this.iconURL = iconURL;
	}
	public int getMarkerType() {
		return markerType;
	}
	public void setMarkerType(int markerType) {
		this.markerType = markerType;
	}
	public List<EGeoPoint> getEntranceList() {
		return entranceList;
	}
	public void setEntranceList(List<EGeoPoint> entranceList) {
		this.entranceList = entranceList;
	}
	public List<EGeoPoint> getExitList() {
		return exitList;
	}
	public void setExitList(List<EGeoPoint> exitList) {
		this.exitList = exitList;
	}
	
	@Override
	public JSONObject wrapToJson() {

		JSONObject jResult = new JSONObject();
    	try {
    		jResult.put(KEY_EDATA_TYPE, getEDataType());
	    	jResult.put(KEY_ID, getId());
	    	jResult.put(KEY_NAME, getName());
	    	jResult.put(KEY_PHONE, getPhone());
	    	jResult.put(KEY_CITY_NAME, getCityName());
	    	jResult.put(KEY_CITY_CODE, getCityCode());
	    	jResult.put(KEY_ADDR, getAddr());
	    	jResult.put(KEY_AD_CODE, getAdCode());
	    	jResult.put(KEY_ICON_URL, getIconURL());
	    	jResult.put(KEY_MARKER_TYPE, getMarkerType());
	    	if(entranceList != null && entranceList.size() > 0){
	    		JSONArray jEntranceList = new JSONArray();
	    		for(EGeoPoint geoPoint : entranceList){
	    			jEntranceList.put(geoPoint);
	    		}
	    		jResult.put(KEY_ENTRANCE_LIST, jEntranceList);
	    	}
	    	if(exitList != null && exitList.size() > 0){
	    		JSONArray jExitList = new JSONArray();
	    		for(EGeoPoint geoPoint : exitList){
	    			jExitList.put(geoPoint);
	    		}
	    		jResult.put(KEY_EXIT_LIST, jExitList);
	    	}
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	return jResult;
	}
	
	@Override
	public BaseEData unwrapFromJson(String data) throws JSONException {
		
		if(!TextUtils.isEmpty(data)){
			JSONObject jData = new JSONObject();
			setId(jData.optString(KEY_ID));
			setName(jData.optString(KEY_NAME));
			setPhone(jData.optString(KEY_PHONE));
			setCityName(jData.optString(KEY_CITY_NAME));
			setCityCode(jData.optString(KEY_CITY_CODE));
			setAddr(jData.optString(KEY_ADDR));
			setAdCode(jData.optString(KEY_AD_CODE));
			setIconURL(jData.optString(KEY_ICON_URL));
			setMarkerType(jData.optInt(KEY_MARKER_TYPE));
			if(jData.optJSONArray(KEY_ENTRANCE_LIST) != null){
				JSONArray jEntranceList = jData.optJSONArray(KEY_ENTRANCE_LIST);
				List<EGeoPoint> entranceList = new ArrayList<EGeoPoint>();
				for(int i = 0; i < jEntranceList.length(); i++){
					EGeoPoint geoPoint = new EGeoPoint();
					entranceList.add((EGeoPoint)geoPoint.unwrapFromJson(jEntranceList.get(i).toString()));
				}
				setEntranceList(entranceList);
			}
			if(jData.optJSONArray(KEY_EXIT_LIST) != null){
				JSONArray jExitList = jData.optJSONArray(KEY_EXIT_LIST);
				List<EGeoPoint> exitList = new ArrayList<EGeoPoint>();
				for(int i = 0; i < jExitList.length(); i++){
					EGeoPoint geoPoint = new EGeoPoint();
					exitList.add((EGeoPoint)geoPoint.unwrapFromJson(jExitList.get(i).toString()));
				}
				setExitList(exitList);
			}
		}
		return this;
	}
	
	@Override
	public String getEDataType(){
		return EPOI.class.toString();
	}
	
}
