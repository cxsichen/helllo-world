package com.autonavi.external.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 导航信息数据类。
 * 
 * @author huafeng.hf
 *
 */
public class ENaviInfo extends BaseEData {
	
	private static final String KEY_TYPE = "type";
	private static final String KEY_CUR_ROAD_NAME = "curRoadName";
	private static final String KEY_NEXT_ROAD_NAME = "nextRoadName";
	private static final String KEY_SAPA_DIST = "SAPADist";
	private static final String KEY_SAPA_TYPE = "SAPAType";
	private static final String KEY_CAMERA_DIST = "cameraDist";
	private static final String KEY_CAMERA_TYPE = "cameraType";
	private static final String KEY_CAMERA_SPEED = "cameraSpeed";
	private static final String KEY_CAMERA_INDEX = "cameraIndex";
	private static final String KEY_ICON = "icon";
	private static final String KEY_ROUTE_REMAIN_DIS = "routeRemainDis";
	private static final String KEY_ROUTE_REMAIN_TIME = "routeRemainTime";
	private static final String KEY_SEG_REMAIN_DIS = "segRemainDis";
	private static final String KEY_SEG_REMAIN_TIME = "segRemainTime";
	private static final String KEY_CAR_DIRECTION = "carDirection";
	private static final String KEY_LONGITUDE = "longitude";
	private static final String KEY_LATITUDE = "latitude";
	private static final String KEY_LIMITED_SPEED = "limitedSpeed";
	private static final String KEY_CUR_SEG_NUM = "curSegNum";
	private static final String KEY_CUR_LINK_NUM = "curLinkNum";
	private static final String KEY_CUR_POINT_NUM = "curPointNum";
	private int type; // 更新类型,1 GPS导航更新,2 模拟导航更新
    private String curRoadName; // 当前道路名称
    private String nextRoadName; // 下条道路名称
    private int SAPADist; // 距离最近服务区的距离（单位米），若为-1则说明距离下一个服务区还远，或是路上没有服务区
    private int SAPAType; // 服务区类型　0 高速路服务区　1 其他服务区
    private int cameraDist; // 距离最近电子眼距离（单位米），若为-1则说明距离下一个电子眼还远，或是路上没有电子眼
    private int cameraType; // 电子眼类型，0 测试摄像头，1为监控摄像头
    private int cameraSpeed; // 电子眼限速，若无限速信息则为0
    private int cameraIndex; // 电子眼在路径上的编号, 总是指下一个将要路过的电子眼的编号，若为-1则路上没有电子眼
    /*
	    用于描述导航信息的结构体
	   导航段转向图标定义如下：
	* * 1        自车图标
	* * 2        左转图标
	* * 3        右转图标
	* * 4        左前方图标
	* * 5        右前方图标
	* * 6        左后方图标
	* * 7        右后方图标
	* * 8        左转掉头图标
	* * 9        直行图标
	* * 10       到达途经点图标
	* * 11       进入环岛图标
	* * 12       驶出环岛图标
	* * 13       到达服务区图标
	* * 14       到达收费站图标
	* * 15       到达目的地图标
	* * 16       进入隧道图标
	*/
    private int icon; // 导航段转向图标
    private int routeRemainDis; // 路径剩余距离（单位米）
    private int routeRemainTime; // 路径剩余时间（单位秒）
    private int segRemainDis; // 当前导航段剩余距离（单位米）
    private int segRemainTime; // 当前导航段剩余时间（单位秒）
    private int carDirection; // 自车方向（单位度），以正北为基准，顺时针增加
    private double longitude; // 自车经度
    private double latitude; // 自车纬度
    private int limitedSpeed; // 当前道路速度限制（单位公里每小时）
    private int curSegNum; // 当前自车所在segment段，从0开始
    private int curLinkNum; // 当前自车所在Link，从0开始
    private int curPointNum; // 当前位置的前一个形状点号，从0开始
    
    public ENaviInfo unwrapFromJson(String data) throws JSONException{
    	JSONObject jData = new JSONObject(data);
		setEDataType(jData.optString(KEY_EDATA_TYPE));
		setType(jData.optInt(KEY_TYPE));
		setCurRoadName(jData.optString(KEY_CUR_ROAD_NAME));
		setNextRoadName(jData.optString(KEY_NEXT_ROAD_NAME));
		setSAPADist(jData.optInt(KEY_SAPA_DIST));
		setSAPAType(jData.optInt(KEY_SAPA_TYPE));
		setCameraDist(jData.optInt(KEY_CAMERA_DIST));
		setCameraType(jData.optInt(KEY_CAMERA_TYPE));
		setCameraSpeed(jData.optInt(KEY_CAMERA_SPEED));
		setCameraIndex(jData.optInt(KEY_CAMERA_INDEX));
		setIcon(jData.optInt(KEY_ICON));
		setRouteRemainDis(jData.optInt(KEY_ROUTE_REMAIN_DIS));
		setRouteRemainTime(jData.optInt(KEY_ROUTE_REMAIN_TIME));
		setSegRemainDis(jData.optInt(KEY_SEG_REMAIN_DIS));
		setSegRemainTime(jData.optInt(KEY_SEG_REMAIN_TIME));
		setCarDirection(jData.optInt(KEY_CAR_DIRECTION));
		setLongitude(jData.optDouble(KEY_LONGITUDE));
		setLatitude(jData.optDouble(KEY_LATITUDE));
		setLimitedSpeed(jData.optInt(KEY_LIMITED_SPEED));
		setCurSegNum(jData.optInt(KEY_CUR_SEG_NUM));
		setCurLinkNum(jData.optInt(KEY_CUR_LINK_NUM));
		setCurPointNum(jData.optInt(KEY_CUR_POINT_NUM));
    	return this;
    }
    
    @Override
    public JSONObject wrapToJson(){
    	JSONObject jResult = new JSONObject();
    	try {
    		jResult.put(KEY_EDATA_TYPE, getEDataType());
			jResult.put(KEY_TYPE, type);
			jResult.put(KEY_CUR_ROAD_NAME, curRoadName);
	    	jResult.put(KEY_NEXT_ROAD_NAME, nextRoadName);
	    	jResult.put(KEY_SAPA_DIST, SAPADist);
	    	jResult.put(KEY_SAPA_TYPE, SAPAType);
	    	jResult.put(KEY_CAMERA_DIST, cameraDist);
	    	jResult.put(KEY_CAMERA_TYPE, cameraType);
	    	jResult.put(KEY_CAMERA_SPEED, cameraSpeed);
	    	jResult.put(KEY_CAMERA_INDEX, cameraIndex);
	    	jResult.put(KEY_ICON, icon);
	    	jResult.put(KEY_ROUTE_REMAIN_DIS, routeRemainDis);
	    	jResult.put(KEY_ROUTE_REMAIN_TIME, routeRemainTime);
	    	jResult.put(KEY_SEG_REMAIN_DIS, segRemainDis);
	    	jResult.put(KEY_SEG_REMAIN_TIME, segRemainTime);
	    	jResult.put(KEY_CAR_DIRECTION, carDirection);
	    	jResult.put(KEY_LONGITUDE, longitude);
	    	jResult.put(KEY_LATITUDE, latitude);
	    	jResult.put(KEY_LIMITED_SPEED, limitedSpeed);
	    	jResult.put(KEY_CUR_SEG_NUM, curSegNum);
	    	jResult.put(KEY_CUR_LINK_NUM, curLinkNum);
	    	jResult.put(KEY_CUR_POINT_NUM, curPointNum);
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	return jResult;
    }
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getCurRoadName() {
		return curRoadName;
	}
	public void setCurRoadName(String curRoadName) {
		this.curRoadName = curRoadName;
	}
	public String getNextRoadName() {
		return nextRoadName;
	}
	public void setNextRoadName(String nextRoadName) {
		this.nextRoadName = nextRoadName;
	}
	public int getSAPADist() {
		return SAPADist;
	}
	public void setSAPADist(int sAPADist) {
		SAPADist = sAPADist;
	}
	public int getSAPAType() {
		return SAPAType;
	}
	public void setSAPAType(int sAPAType) {
		SAPAType = sAPAType;
	}
	public int getCameraDist() {
		return cameraDist;
	}
	public void setCameraDist(int cameraDist) {
		this.cameraDist = cameraDist;
	}
	public int getCameraType() {
		return cameraType;
	}
	public void setCameraType(int cameraType) {
		this.cameraType = cameraType;
	}
	public int getCameraSpeed() {
		return cameraSpeed;
	}
	public void setCameraSpeed(int cameraSpeed) {
		this.cameraSpeed = cameraSpeed;
	}
	public int getCameraIndex() {
		return cameraIndex;
	}
	public void setCameraIndex(int cameraIndex) {
		this.cameraIndex = cameraIndex;
	}
	public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}
	public int getRouteRemainDis() {
		return routeRemainDis;
	}
	public void setRouteRemainDis(int routeRemainDis) {
		this.routeRemainDis = routeRemainDis;
	}
	public int getRouteRemainTime() {
		return routeRemainTime;
	}
	public void setRouteRemainTime(int routeRemainTime) {
		this.routeRemainTime = routeRemainTime;
	}
	public int getSegRemainDis() {
		return segRemainDis;
	}
	public void setSegRemainDis(int segRemainDis) {
		this.segRemainDis = segRemainDis;
	}
	public int getSegRemainTime() {
		return segRemainTime;
	}
	public void setSegRemainTime(int segRemainTime) {
		this.segRemainTime = segRemainTime;
	}
	public int getCarDirection() {
		return carDirection;
	}
	public void setCarDirection(int carDirection) {
		this.carDirection = carDirection;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public int getLimitedSpeed() {
		return limitedSpeed;
	}
	public void setLimitedSpeed(int limitedSpeed) {
		this.limitedSpeed = limitedSpeed;
	}
	public int getCurSegNum() {
		return curSegNum;
	}
	public void setCurSegNum(int curSegNum) {
		this.curSegNum = curSegNum;
	}
	public int getCurLinkNum() {
		return curLinkNum;
	}
	public void setCurLinkNum(int curLinkNum) {
		this.curLinkNum = curLinkNum;
	}
	public int getCurPointNum() {
		return curPointNum;
	}
	public void setCurPointNum(int curPointNum) {
		this.curPointNum = curPointNum;
	}
	
	public String getEDataType(){
		return ENaviInfo.class.toString();
	}

}
