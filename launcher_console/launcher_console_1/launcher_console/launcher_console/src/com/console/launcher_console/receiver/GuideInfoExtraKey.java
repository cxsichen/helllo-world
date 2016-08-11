package com.console.launcher_console.receiver;

public class GuideInfoExtraKey {
	/**
	 * 当前道路名称，对应的值为String类型
	 */
	public static final String CUR_ROAD_NAME = "CUR_ROAD_NAME";
	/**
	 * 下一道路名，对应的值为String类型
	 */
	public static final String NEXT_ROAD_NAME = "NEXT_ROAD_NAME";
	/**
	* 导航转向图标，对应的值为int类型
	*/
	public static final String ICON = "ICON";
	/**
	* 路径剩余距离，对应的值为int类型，单位：米
	*/
	public static final String ROUTE_REMAIN_DIS = "ROUTE_REMAIN_DIS";
	/**
	* 路径剩余时间，对应的值为int类型，单位：秒
	*/
	public static final String ROUTE_REMAIN_TIME = "ROUTE_REMAIN_TIME";
	/**
	* 当前导航段剩余距离，对应的值为int类型，单位：米
	*/
	public static final String SEG_REMAIN_DIS = "SEG_REMAIN_DIS";
	
	
	/**
	* 导航类型，对应的值为int类型<br>
	* 0：GPS导航<br>
	* 1：模拟导航
	*//*
	public static final String TYPE = "TYPE";
	*//**
	* 距离最近服务区的距离，对应的值为int类型，单位：米
	*//*
	public static final String SAPA_DIST = "SAPA_DIST";
	*//**
	* 服务区类型，对应的值为int类型<br>
	* 0：高速服务区<br>
	* 1：其他服务器
	*//*
	public static final String SAPA_TYPE = "SAPA_TYPE";
	*//**
	* 距离最近的电子眼距离，对应的值为int类型，单位：米<br>
	*//*
	public static final String CAMERA_DIST = "CAMERA_DIST";
	*//**
	* 电子眼类型，对应的值为int类型<br>
	* 0 测速摄像头，<br>
	* 1为监控摄像头，<br>
	* 2为闯红灯拍照，<br>
	* 3为违章拍照，<br>
	* 4为公交专用道摄像头
	*//*
	public static final String CAMERA_TYPE = "CAMERA_TYPE";
	*//**
	* 电子眼限速度，对应的值为int类型，无限速则为0，单位：公里/小时
	*//*
	public static final String CAMERA_SPEED = "CAMERA_SPEED";
	*//**
	* 下一个将要路过的电子眼编号，若为-1则对应的道路上没有电子眼，对应的值为int类型
	*//*
	public static final String CAMERA_INDEX = "CAMERA_INDEX";
	
	*//**
	* 当前导航段剩余时间，对应的值为int类型，单位：秒
	*//*
	public static final String SEG_REMAIN_TIME = "SEG_REMAIN_TIME";
	*//**
	* 自车方向，对应的值为int类型，单位：度，以正北为基准，顺时针增加
	*//*
	public static final String CAR_DIRECTION = "CAR_DIRECTION";
	*//**
	* 自车纬度，对应的值为double类型
	*//*
	public static final String CAR_LATITUDE = "CAR_LATITUDE";
	*//**
	* 自车经度，对应的值为double类型
	*//*
	public static final String CAR_LONGITUDE = "CAR_LONGITUDE";
	*//**
	* 当前道路速度限制，对应的值为int类型，单位：公里/小时
	*//*
	public static final String LIMITED_SPEED = "LIMITED_SPEED";
	*//**
	* 当前自车所在Link，对应的值为int类型，从0开始
	*//*
	public static final String CUR_SEG_NUM = "CUR_SEG_NUM";
	*//**
	* 当前位置的前一个形状点号，对应的值为int类型，从0开始
	*//*
	public static final String CUR_POINT_NUM = "CUR_POINT_NUM";
	*//**
	* 环岛出口序号，对应的值为int类型，从0开始，只有在icon为11和12时有效，其余为无效值0
	*//*
	public static final String ROUND_ABOUT_NUM = "ROUNG_ABOUT_NUM";
	*//**
	* 环岛出口个数，对应的值为int类型，只有在icon为11和12时有效，其余为无效值0
	*//*
	public static final String ROUND_ALL_NUM = "ROUND_ALL_NUM";
	*//**
	* 路径总距离，对应的值为int类型，单位：米
	*//*
	public static final String ROUTE_ALL_DIS = "ROUTE_ALL_DIS";
	*//**
	* 路径总时间，对应的值为int类型，单位：秒
	*//*
	public static final String ROUTE_ALL_TIME = "ROUTE_ALL_TIME";
	*//**
	* 当前车速，对应的值为int类型，单位：公里/小时
	*//*
	public static final String CUR_SPEED = "CUR_SPEED";
	*//**
	* 红绿灯个数，对应的值为int类型
	*//*
	public static final String TRAFFIC_LIGHT_NUM = "TRAFFIC_LIGHT_NUM";
	*//**
	* 服务区个数，对应的值为int类型
	*//*
	public static final String SAPA_NUM = "SAPA_NUM";
	*//**
	* 下一个服务区名称，对应的值为String类型
	*//*
	public static final String SAPA_NAME = "SAPA_NAME";
	*//**
	* 当前道路类型，对应的值为int类型
	* 0：高速公路
	* 1：国道
	* 2：省道
	* 3：县道
	* 4：乡公路
	* 5：县乡村内部道路
	* 6：主要大街、城市快速道
	* 7：主要道路
	* 8：次要道路
	* 9：普通道路
	* 10：非导航道路
	*//*
	public static final String ROAD_TYPE = "ROAD_TYPE";*/
}
