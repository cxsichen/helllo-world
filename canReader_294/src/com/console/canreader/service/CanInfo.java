package com.console.canreader.service;

import java.util.Arrays;

import android.os.Parcel;
import android.os.Parcelable;

public class CanInfo implements Parcelable {

	/**
	 * mCanInfo.CHANGE_STATUS表示更改的状态
	 * 
	 * STEERING_BUTTON_DATA--------------mCanInfo.CHANGE_STATUS = 2 按键状态
	 * AIR_CONDITIONER_DATA--------------mCanInfo.CHANGE_STATUS = 3 空调状态
	 * BACK_RADER_DATA-------------------mCanInfo.CHANGE_STATUS = 4 后雷达状态
	 * FRONT_RADER_DATA------------------mCanInfo.CHANGE_STATUS = 5 前雷达状态
	 * RADAR_DATA------------------------mCanInfo.CHANGE_STATUS = 11 雷达状态（前后都有）
	 * STEERING_TURN_DATA----------------mCanInfo.CHANGE_STATUS = 8 方向盘状态
	 * CAR_INFO_DATA---------------------mCanInfo.CHANGE_STATUS = 10
	 * 报警状态（车门报警，车身信息报警） 车身信息
	 * WARING_DIALOG_DATA----------------mCanInfo.CHANGE_STATUS = 12 408报警信息
	 * WARING_DIALOG_DATA----------------mCanInfo.CHANGE_STATUS = 13 报警信息
	 * PANORAMA_DATA---------------------mCanInfo.CHANGE_STATUS = 20 360全景
	 */
	

	public int CHANGE_STATUS = 8888;

	public int HEAD_CODE = 0;
	/*
	 * 背光
	 */
	public int BACK_LIGHT_DATA = -1;
	/*
	 * 车速
	 */
	public int CAR_SPEED_DATA = -1;
	/*
	 * 方向盘按键 STEERING_BUTTON_MODE 0：无按键或释放 1：vol+ 2：vol- 3：menuup 4：menu down 5：
	 * PHONE 6：mute 7：SRC 8：SPEECH/MIC
	 */
	public int STEERING_BUTTON_MODE = 0;
	public int STEERING_BUTTON_STATUS = 0;
	/*
	 * 空调信息
	 */
	// 空调状态都是on off
	public int AIR_CONDITIONER_STATUS = -1; // 空调开关
	public int AC_INDICATOR_STATUS = 0; // AC状态
	public int CYCLE_INDICATOR = 0; // 内外循环指示 2不显示 0外循环 1内循环
	public int LARGE_LANTERN_INDICATOR = 0; // 大auto
	public int SMALL_LANTERN_INDICATOR = 0; // 小auto
	public int DAUL_LAMP_INDICATOR = 0;
	public int MAX_FRONT_LAMP_INDICATOR = 0; // 前窗除雾
	public int REAR_LAMP_INDICATOR = 0; // 后窗除雾
	public int AC_MAX_STATUS = 0; 			   //AC MAX
	public int AUTO_STATUS = 0;			   //Auto开关
	public int Mono_STATUS = 0;			   //单一温区 1开 0关 默认关
	// 风速及风向信息
	public int UPWARD_AIR_INDICATOR = 0; // 吹前窗
	public int PARALLEL_AIR_INDICATOR = 0; // 吹身
	public int DOWNWARD_AIR_INDICATOR = 0; // 吹脚
	public int AIRCON_SHOW_REQUEST = 0;
	public int AIR_RATE = 0; // 风速 -1是自动 其他的都是数值
	public int AIR_STRENGTH = 0; // 风量强度等级  0低 1中 2高
	public float DRIVING_POSITON_TEMP = 0; // 驾驶位置处温度 low对应0 high对应255 -1 表示不显示
	public float DEPUTY_DRIVING_POSITON_TEMP = 0; // 副驾驶位置处温度 low对应0 high对应255
													// -1 表示不显示
	// 座椅加热信息

	public int AQS_CIRCLE = -1;

	public int LEFT_SEAT_TEMP = 0; // 左座椅温度
	public int RIGTHT_SEAT_TEMP = 0; // 右座椅温度

	/*
	 * 后雷达信息 0不显示 1最近（报警） 4最远  记得不要超过0~4 会报错不显示
	 */
	public int BACK_LEFT_DISTANCE = 0; // 左后雷达
	public int BACK_MIDDLE_LEFT_DISTANCE = 0; // 左后中雷达
	public int BACK_MIDDLE_RIGHT_DISTANCE = 0; // 右后中雷达
	public int BACK_RIGHT_DISTANCE = 0; // 右后雷达

	/*
	 * 前雷达信息
	 */
	public int FRONT_LEFT_DISTANCE = 0; // 左前雷达
	public int FRONT_MIDDLE_LEFT_DISTANCE = 0; // 左前中雷达
	public int FRONT_MIDDLE_RIGHT_DISTANCE = 0; // 右前中雷达
	public int FRONT_RIGHT_DISTANCE = 0; // 右前雷达

	/*
	 * 基本信息
	 */
	public int LIGHT_MSG = 0;
	public int STOPING_STATUS = 0;
	public int REVERSE_GEAR_STATUS = 0;
	/*
	 * 泊车辅助状态
	 */

	public int PARKING_ASSIT_STATUS = 0;
	public int RADAR_ALARM_STATUS = 1; // 雷达报警 1为显示 0为不显示
	public int RADAR_ALARM_STATUS_ENABLE=0;			
	/*
	 * 方向盘转角
	 */
	public int STERRING_WHELL_STATUS = 0; // 方向盘转角 -540 到 540
											// ESP>0 左转 ESP<0 右转
	/*
	 * 功放状态POWER_AMPLIFIER_DATA
	 */
	public int POWER_AMPLIFIER_TYPE = 0;
	public int POWER_AMPLIFIER_VOlUME = 0;
	public int POWER_AMPLIFIER_BALANCE = 0;
	public int POWER_AMPLIFIER_FADER = 0;
	public int POWER_AMPLIFIER_BASS = 0;
	public int POWER_AMPLIFIER_MIDTONE = 0;
	public int POWER_AMPLIFIER_TREBLE = 0;
	public int POWER_AMPLIFIER_CHANGE = 0;
	/*
	 * 车身信息CAR_INFO_DATA
	 */
	public int SAFETY_BELT_STATUS = -1; // 驾驶员安全带状态 -1表示无此功能 0正常 1报警
	public int DEPUTY_SAFETY_BELT_STATUS = -1; //副驾驶安全带状态 0正常 1报警
	public int BACK_LEFT_SAFETY_BELT_STATUS = -1; //后左安全带状态 0正常 1报警
	public int BACK_MIDDLE_SAFETY_BELT_STATUS = -1; //后中安全带状态 0正常 1报警
	public int BACK_RIGHT_SAFETY_BELT_STATUS = -1; //后右安全带状态 0正常 1报警
	public int DISINFECTON_STATUS = -1; // 清洁液状态 -1表示无此功能
	public int HANDBRAKE_STATUS = -1; // 手刹状态 0正常 1报警
 
	public int HOOD_STATUS = 0; // 引擎盖 0关 1开
	public int TRUNK_STATUS = 0; // 后盖报警
	public int RIGHT_BACKDOOR_STATUS = 0; // 右后门
	public int LEFT_BACKDOOR_STATUS = 0; // 左后门
	public int RIGHT_FORONTDOOR_STATUS = 0; // 右前门
	public int LEFT_FORONTDOOR_STATUS = 0; // 左前门

	public int ENGINE_SPEED = -1; // 发动机转速 -1表示无此功能
	public float DRIVING_SPEED = 0; // 瞬时车速
	public float BATTERY_VOLTAGE = -1; // 电池电量 -1表示无此功能
	public float OUTSIDE_TEMPERATURE = 0; // 窗外温度        -10000f显示“--”
	public int DRIVING_DISTANCE = 0; // 行车里程
	public int REMAIN_FUEL = -1; // 剩余油量 -1表示无此功能

	public int FUEL_WARING_SIGN = 0; // 没油报警
	public int BATTERY_WARING_SIGN = 0; // 没电报警

	/*-----------------------------------------------------*/

	/*-----------------油耗---------里程---------------------------*/
	public float INSTANT_CONSUMPTION = 0; // 瞬时油耗
	public int INSTANT_CONSUMPTION_UNIT = 0; // 瞬时油耗单位
	public float CURRENT_AVERAGE_CONSUMPTION = 0; // 当前平均油耗
	public float HISTORY_AVERAGE_CONSUMPTION = 0; // 历史平均油耗
	public int CUR_HIS_AVERAGE_CONSUMPTION_UNIT = 0; // 当前/历史油耗单位

	public float AVERAGE_CONSUMPTION = 0; // 平均油耗
	public int AVERAGE_CONSUMPTION_UNIT = 0; // 平均油耗单位

	public int CONSUMPTION_RANGE = 0; // 油耗量程
	public float TRIP_A = 0; // trip A
	public int TRIP_A_UNIT = 0; // trip A 单位

	public float TRIP_A_1 = 0;
	public float TRIP_A_1_AVERAGE_CONSUMPTION = 0;
	public float TRIP_A_2 = 0;
	public float TRIP_A_2_AVERAGE_CONSUMPTION = 0;
	public float TRIP_A_3 = 0;
	public float TRIP_A_3_AVERAGE_CONSUMPTION = 0;

	public int RANGE = 0; // 续航里程
	public int RANGE_UNIT = 0; // 续航里程单位
	public int RANGE_ADD = 0; // 累计里程

	public int DISTANCE_UNIT = 0; // 行驶里程单位
	public int CONSUMPTION_UNIT = 0; // 平均油耗单位
	public int AVERAGE_SPEED = 0; // 平均车速
	public int SPEED_UNIT = 0; // 平均车速单位

	public float DISTANCE_SINCE_START = 0; // 自启动行驶里程
	public float CONSUMPTION_SINCE_START = 0; // 自启动平均油耗
	public float SPEED_SINCE_START = 0; // 自启动平均车速
	public int TRAVELLINGTIME_SINCE_START = 0; // 自启动行驶时间
	public int RANGE_SINCE_START = 0; // 自启动续航里程

	public float DISTANCE_SINCE_REFUELING = 0; // 自加油行驶里程
	public float CONSUMPTION_SINCE_REFUELING = 0; // 自加油平均油耗
	public float SPEED_SINCE_REFUELINGT = 0; // 自加油平均车速
	public int TRAVELLINGTIME_SINCE_REFUELINGT = 0; // 自加油行驶时间
	public int RANGE_SINCE_REFUELINGT = 0; // 自加油续航里程

	public float DISTANCE_LONG_TERM = 0; // 长时间行驶里程
	public float CONSUMPTION_LONG_TERM = 0; // 长时间平均油耗
	public float SPEED_LONG_TERM = 0; // 长时间平均车速
	public int TRAVELLINGTIME_LONG_TERM = 0; // 长时间行驶时间
	public int RANGE_LONG_TERM = 0; // 长时间续航里程

	public String VEHICLE_NO = ""; // 汽车编号
		
	public int INSPECTON_DAYS_STATUS = 0; // 车况检查状态
	public int INSPECTON_DAYS = 0; // 车况检查天数

	public int INSPECTON_DISTANCE_UNIT = 0; // 车况检查里程单位
	public int INSPECTON_DISTANCE_STATUS = 0; // 车况检查里程状况
	public int INSPECTON_DISTANCE = 0; // 车况检查里程

	public int OILCHANGE_SERVICE_DAYS_STATUS = 0; // 更换机油保养天数状况
	public int OILCHANGE_SERVICE_DAYS = 0; // 更换机油保养天数

	public int OILCHANGE_SERVICE_DISTANCE_UNIT = 0; // 更换机油保养里程单位
	public int OILCHANGE_SERVICE_DISTANCE_STATUS = 0; // 更换机油保养里程状况
	public int OILCHANGE_SERVICE_DISTANCE = 0; // 更换机油保养里程

	public int TPMS_FL_TEMP = 0; // 前左车轮温度
	public int TPMS_FR_TEMP = 0; // 前右车轮温度
	public int TPMS_BL_TEMP = 0; // 后左车轮温度
	public int TPMS_BR_TEMP = 0; // 后右车轮温度

	public float TPMS_FL_PRESSUE = 0; // 前左车轮压力
	public float TPMS_FR_PRESSUE = 0; // 前右车轮压力
	public float TPMS_BL_PRESSUE = 0; // 后左车轮压力
	public float TPMS_BR_PRESSUE = 0; // 后右车轮压力

	/*
	 * 0x00：表示正常无警告。 0x01：红色警告，胎压出现异常。 0x02：红灯闪烁，胎压出现异常（漏气等）。
	 * 0x03：黄色警告，胎压偏高或者偏低。 其它值保留
	 */

	public int TPMS_FL_WARING = 0; // 前左车轮报警
	public int TPMS_FR_WARING = 0; // 前右车轮报警
	public int TPMS_BL_WARING = 0; // 后左车轮报警
	public int TPMS_BR_WARING = 0; // 后右车轮报警
	
	public String WARING_MSG = ""; // 报警字符串
	public int WARING_MSG_STATUS = 0; // 报警类型
	
	public String VERSION = ""; // 软件版本

	public int IS_POWER_MIXING = 0; // 汽车动力类型 1为油电混合
	public int BATTERY_LEVEL = 0; // 电池电量 无单位
	public int MOTOR_DRIVE_BATTERY = 0; // 马达驱动电池
	public int MOTOR_DRIVE_WHEEL = 0; // 马达驱动车轮
	public int ENGINE_DRIVE_MOTOR = 0; // 发动机驱动马达
	public int ENGINE_DRIVE_WHEEL = 0; // 发动机驱动车轮
	public int BATTERY_DRIVE_MOTOR = 0; // 电池驱动马达
	public int WHEEL_DRIVE_MOTOR = 0; // 车轮驱动马达
	
	/*----门锁 状态-----*/
	public int FRONT_LEFT_DOORLOCK = 0; // 左前门锁
	public int FRONT_RIGHT_DOORLOCK = 0; // 右前门锁
	public int BACK_LEFT_DOORLOCK = 0; // 左后门锁
	public int BACK_RIGHT_DOORLOCK = 0; // 右后门锁
	
	/*-----行程  油耗-----*/
	public float TRIP_OIL_CONSUMPTION_0 = 0;
	public float TRIP_OIL_CONSUMPTION_1 = 0;
	public float TRIP_OIL_CONSUMPTION_2 = 0;
	public float TRIP_OIL_CONSUMPTION_3 = 0;
	public float TRIP_OIL_CONSUMPTION_4 = 0;
	public float TRIP_OIL_CONSUMPTION_5 = 0;
	public int TRIP_OIL_CONSUMPTION_UNIT = 0;

	/*----历史  油耗-----*/
	public float HISTORY_OIL_CONSUMPTION_1 = 0;
	public float HISTORY_OIL_CONSUMPTION_2 = 0;
	public float HISTORY_OIL_CONSUMPTION_3 = 0;
	public float HISTORY_OIL_CONSUMPTION_4 = 0;
	public float HISTORY_OIL_CONSUMPTION_5 = 0;
	public float HISTORY_OIL_CONSUMPTION_6 = 0;
	public float HISTORY_OIL_CONSUMPTION_7 = 0;
	public float HISTORY_OIL_CONSUMPTION_8 = 0;
	public float HISTORY_OIL_CONSUMPTION_9 = 0;
	public float HISTORY_OIL_CONSUMPTION_10 = 0;
	public float HISTORY_OIL_CONSUMPTION_11 = 0;
	public float HISTORY_OIL_CONSUMPTION_12 = 0;
	public float HISTORY_OIL_CONSUMPTION_13 = 0;
	public float HISTORY_OIL_CONSUMPTION_14 = 0;
	public float HISTORY_OIL_CONSUMPTION_15 = 0;
	public int HISTORY_OIL_CONSUMPTION_UNIT = 0;

	/*----雷达信息-----*/
	public int IS_RADAR_SHOW = 0; // 雷达显示
	public int RADAR_WARING_VOLUME = 0; // 报警音量
	public int FRONT_RADAR_DISTANCE = 0; // 前雷达距离
	public int BACK_RADAR_DISTANCE = 0; // 后雷达距离

	/*----功放-----*/
	public int EQL_VOLUME = 0; // 音量
	public int LR_BALANCE = 0; // 左右平衡
	public int FB_BALANCE = 0; // 前后平衡
	public int BAS_VOLUME = 0; // 低音值
	public int MID_VOLUME = 0; // 中音值
	public int TRE_VOLUME = 0; // 高音值
	public int VOL_LINK_CARSPEED = 0; // 音量与车速联动
	public int DSP_SURROUND = 0; // DSP环绕
	public int EQL_MUTE = 0; // 静音
	public int SEAT_SOUND=0;  //驾驶座音场状态
	public int BOSE_CENTERPOINT=0; //BOSE CenterPoint状态
	public int SURROND_VOLUME=0; //环绕音量
	
	/*-----360全景摄像头------*/
	public int PANORAMA_STATUS=0;  //360全景状态


	/*----遥控设定-----*/
	public int LAMP_WHEN_LOCK = 0; // 上锁开锁时紧急灯响应
	public int INTELLIGENT_LOCK = 0; // 智能车锁和一键启动
	public int TWICE_KEY_OPEN_LOCK = 0; // 钥匙两次按下解锁设定
	public int TWICE_BUTTON_OPEN_LOCK = 0; // 按钮两次按下解锁设定

	public int AUTOMATIC_CAP_SENSEITIVITY = 0; // 自动灯头灵敏度
	public int AUTOMATIC_LAMP_CLOSE = 0; // 车内照明关闭时间
	
	public int REMOTELOCK_BEEP_SIGN = 0; // 遥控门锁蜂鸣器提示
	public int REMOTELOCK_SIDELAMP_SIGN = 0; // 遥控门锁车边灯提示
	public int SPEECH_WARING_VOLUME = 0; // 语音报警系统的音量
	public int REMOTE_START_SYSTEM = 0; // 遥控启动系统

	public int REMOTECONTROL_LOCK_FEEDBACK=0; //遥控落锁反馈设定
	public int REMOTECONTROL_UNLOCK_FEEDBACK=0; //遥控解锁反馈设定
	public int REMOTECONTROL_UNLOCK=0; //遥控解锁设定
	public int REMOTECONTROL_UNLOCK_AUTORELOCK=0; //遥控解锁车门自动重锁设定
	public int REMOTECONTROL_RELOCK_DOOR=0; //重锁遥控打开的门的设定
	public int REMOTECONTROL_KEY_AUTORECOGNIZE=0; //驾驶员钥匙自动识别设定
	public int REMOTECONTROL_START=0; //远程启动设定
	public int REMOTECONTROL_NEAR_AUTOUNLOCK=0; //智能近车解锁设定
	
	public int REMOTECONTROL_AWAY_AUTOLOCK=0; //智能离车落锁设定
	public int REMOTECONTROL_KEYLEF_ALARM=0; //钥匙遗忘提醒设定
	public int REMOTECONTROL_MOVE_DOOR=0; //遥控滑移门设置
	public int REMOTECONTROL_WINDOW_CONTROL=0; //遥控车窗控制
	
	/*----照明设定-----*/
	public int LIGHT_CARLAMP_SETTING=0; //寻车灯功能设定
	public int LIGHT_LOCKLAMP_SETTING=0; //落锁大灯延时设定
	public int LIGHT_RIHGTTURN_LAMP_SETTING=0; //右转向灯
	public int LIGHT_LEFTTURN_LAMP_SETTING=0; //左转向灯
	
	/*----落锁设定 门锁设定状态-----*/
	public int LOCK_PERSONAL_SETTING = 0;   // 离开锁止个性化设定
	public int AUTO_LOCK_TIME = 0;   // 自动重锁时间
	public int REMOTE_LOCK_SIGN = 0;   // 遥控落锁提示 
	
	
	public int AUTO_OPEN_LOCK = 0; // 智能解锁设定
	public int DRIVER_LINK_LOCK = 0; // 驾驶员开门联动解锁设定
	public int AUTO_OPEN_LOCK_P = 0; // 自动解锁设定（P档）
	public int AUTO_LOCK_P = 0; // 自动落锁设定（P档）
	public int AIRCON_WITH_AUTO = 0; // 空调与auto键联动
	public int CYCLE_WITH_AUTO = 0; // 内外循环与auto键联动
	
	public int LOCK_OPENDOOR_WITHOUTLOCK=0; //防止开门自动自动落锁设定
	public int AUTO_LOCK_SETTING = 0; // 自动落锁设定
	public int AUTO_OPEN_LOCK_Z = 0; // 自动解锁设定（自动档）
	public int LOCK_DELAY_LOCK=0;   //延迟落锁设定
	public int AUTO_OPEN_LOCK_S = 0; // 自动解锁设定（手动档）
	
	/*--  原车屏显示-----*/
	public int CAR_SHWO_LIGHT=0; //原车屏亮度
	public int CAR_SHWO_CONTARST = 0; // 原车屏对比度
	public int CAR_SHWO_SATURATION = 0; // 原车屏饱和度
	/*--  蓝牙模式-----*/
	public String BLUETOOTH_PASSWARD="";      //蓝牙配对密码
	public String BLUETOOTH_NAME="";      //蓝牙电话名称
	public int BLUETOOTH_MODE=0;      //蓝牙电话名称
	
	/*--  摄像头模式-----*/
	public int BACK_CAMERA_MODE = 0; // 后摄像头模式
	public int LEFT_CAMERA_SWITCH = 0; // 右摄像头开关
	
	/*-- onstar 安吉星-----*/
	public int ONSTAR_STATUS= 0; // 安吉星状态
	public int ONSTAR_PHONE_TYPE = 0; // 安吉星通话类型
	public int ONSTAR_PHONE_SIGN = 0; // 安吉星通话标志
	
	public int ONSTAR_PHONE_HOUR = 0; // 通话时间-小时
	public int ONSTAR_PHONE_MINUTE = 0; // 通话时间-分钟
	public int ONSTAR_PHONE_SECOND = 0; // 通话时间-秒
	public int ONSTAR_LEFTIME_1 = 0; // 剩余时间高位
	public int ONSTAR_LEFTIME_2 = 0; // 剩余时间低位
	public int ONSTAR_EFFECTTIME_YEAR_1 = 0; // 有效期-年 高位
	public int ONSTAR_EFFECTTIME_YEAR_2 = 0; // 有效期-年 低位
	public int ONSTAR_EFFECTTIME_MOUTH = 0; // 有效期-月
	public int ONSTAR_EFFECTTIME_DAY = 0; // 有效期-日
	
	public int ONSTAR_WARING_STATUS = 0; // 警告信息状态
	public int ONSTAR_WARING_TYPE = 0; // 警告信息类型
	
	public String ONSTAR_RECEIVE_PHONE = ""; // 接收号码
	/*--  灯光设定-----*/
	public int WIPER_LINK_LAMP = 0; //雨刷和自动大灯联动个性化设定
	public int AUTO_LIGHT_SENSEITIVITY = 0; //自动车内照明灵敏度
	public int AUTO_LIGHTING_SENSEITIVITY = 0; //自动点灯灵敏度
	public int FRONT_LAMP_OFF_TIME = 0; //前大灯自动熄灭时间
	public int LAMP_TURN_DARK_TIME = 0; //车内灯光减光时间
	
	public int LOW_BEAM=0;//近光灯
	public int HIGH_BEAM=0;//远光灯
	public int CLEARANCE_LAMP=0;//示宽灯 
	public int FRONT_FOG_LAMP=0;//前雾灯 
	public int REAR_FOG_LAMP=0;//后雾灯 
	public int STOP_LAMP=0;//刹车灯
	public int PARKING_LAMP=0;//倒车灯
	public int DAYTIME_RUNNING_LAMP=0;//日间行车灯
	public int RIGHT_TURNING_SIGNAL_LAMP=0;//右转向灯
	public int LEFT_TURNING_SIGNAL_LAMP=0;//左转向灯
	public int DOUBLE_FLASH_LAMP=0;//双闪灯
	/*--  多媒体信息-----*/
	public int MULTI_MEIDA_SOURCE=0;           //当前源
	public int MULTI_MEIDA_PLAYING_NUM=0;       //当前播放曲目
	public int MULTI_MEIDA_WHOLE_NUM=0;       //总曲目
	public int MULTI_MEIDA_PLAYING_WHOLE_TIME=0;       //当前曲目总时长
	public int MULTI_MEIDA_PLAYING_TIME=0;       //当前CD曲目播放时间
	public int MULTI_MEIDA_PLAYING_MINUTE=0;       //当前播放时间 分钟
	public int MULTI_MEIDA_PLAYING_SECOND=0;       //当前播放时间 秒
	public int MULTI_MEIDA_PLAYING_PROGRESS=0;       //当前播放进度
	public int MULTI_MEIDA_PLAYING_STATUS=0;       //多媒体状态
	public int MULTI_MEIDA_CD_STATUS=0;       //碟状态
	/*--  车灯-----*/
	public int LIGHT_SWITCH_ON_TIME=0;       //接通时间
	public int LIGHT_AUTO_HEADLIGHT_RAIN=0;       //自动行车灯(雨天)
	public int LIGHT_LANE_CHANGE_FLASH=0;       //变道转向灯
	public int LIGHT_DAYTIME_RUNNING_LIGHT=0;       //日行灯
	public int LIGHT_SWITCH_LIGHTING=0;       //仪表/开关照明
	public int LIGHT_COMING_HOME=0;          //回家模式功能
	public int LIGHT_LEAVING_HOME=0;         //离家模式功能
	
	public int LIGHT_TRAVELING_MODE=0;      //旅行模式
	public int LIGHT_DOOR_AMBIENT=0;     //车门环境照明灯
	public int LIGHT_FOORWELL_LIGHT=0;   //脚步空间照明灯调节
	public int LIGHT_DYNAMIC_LIGHT_ASSIST=0; //动态灯光辅助
	public int LIGHT_MOTORWAY_LIGHT=0;   //动态大灯随动	
	public int LIGHT_TOP_LIGHT=0;   //车顶环境照明灯
	public int LIGHT_FRONT_LIGHT=0;   //前部环境照明灯
	public int LIGHT_LIGHT_COLOR=0;   //车灯颜色
	public int LIGHT_CAR_ENV_COLOR=0;   //车内氛围照明
	public int LIGHT_RIGHT_FRONT_COLOR=0;   //右前室调照明
	public int LIGHT_ALL_AREA=0;   //所有区域
	
	public int LIGHT_COMING_HOME_BACKUP=0;         //伴我回家倒车灯
	public int LIGHT_COMING_HOME_DIPPED=0;         //伴我回家近光灯
	public int LIGHT_COMING_HOME_REARFOG=0;         //伴我回家后雾灯
	public int LIGHT_SEEK_CAR_BACKUP=0;         //寻车灯指示倒车灯
	public int LIGHT_SEEK_CAR_DIPPED=0;         //寻车灯指示近光灯
	public int LIGHT_SEEK_CAR_REARFOG=0;         //寻车灯指示后雾灯
	
	public int LIGHT_COMING_HOME_TIME=0;         //伴我回家持续时间
	public int LIGHT_SEEK_CAR_TIME=0;         //寻车灯指示灯持续时间
	/*--  后视镜和刮水器-----*/
	public int MIRROR_SYNC_ADJUST=0;    //后视镜同步调节 
	public int MIRROR_LOWER_WHILE_REVERSING=0;   //倒车档时后视镜降低 
	public int WIPER_AUTO_IN_RAIN=0;   //雨天自动刮水 
	public int WIPER_REAR_WIPING_REVERSING=0;   //倒车档时后窗玻璃刮水 
	public int MIRROR_FOLD_PARKING=0;   //驻车时内折  
	/*-----驾驶辅助系统设定--------*/
	public int RADAR_PARK = 0;      //雷达泊车
	public int LANE_DEPARTURE = 0; //车道偏离辅助系统设定
	public int PAUSE_LKAS_SIGN = 0; //暂停LKAS提示音
	public int DETECT_FRONT_CAR = 0; //ACC前车探知提示音
	public int FRONT_DANGER_WAIRNG_DISTANCE = 0; //设定前方危险警告距离
	public int DRIVER_ALERT_SYSTEM = 0; //疲劳驾驶识别系统
	public int LAST_DISTANCE_SELECTED = 0; //上次选择的车距
	public int FRONT_ASSIST_ACTIVE = 0; //前部辅助系统激活
	public int FRONT_ASSIST_ADVANCE_WARNING = 0; //前部辅助系统预警
	public int FRONT_ASSIST_DISPLAY_DISTANCHE_WARNING = 0; //显示距离报警
	public int ACC_DRIVER_PROGRAM = 0; //ACC-行驶程序
	public int ACC_DISTANCE = 0; //ACC-车距
	/*-----驻车和调车--------*/
	public int FRONT_VOLUME = 0; //前部音量
	public int FRONT_FREQUNENCY = 0; //前部频率
	public int BACK_VOLUME = 0; //后部音量
	public int BACK_FREQUNENCY = 0; //后部频率
	public int PARKING_MODE = 0; //倒车模式
	/*-----打开和关闭-------*/
	public int CONV_OPENING = 0; //车窗便捷开启 
	public int DOOR_UNLOCKING = 0; //中央门锁（车门解锁） 
	public int AUTOMATIC_LOCKING = 0; //自动锁止 
	public int SEAT_KEY_REMOTE_FIX = 0; //座椅遥控钥匙记忆匹配
	public int INDUCTON_REAR_DOOR_COVER = 0; //感应式后尾箱盖
	
	/*-----MFD多功能显示-------*/
	public int MFD_CURRENT_CONSUMPTION = 0; //当前油耗  
	public int MFD_AVERAGE_CONSUMPTION = 0; //平均油耗
	public int MFD_CONVENIENCE_CONSUMERS = 0; //舒适性用电器 
	public int MFD_ECO_TIPS = 0; //经济运行提示
	public int MFD_TRAVELLING_TIME = 0; //行驶时间 
	public int MFD_DISTANCE_TRAVELED = 0; //行驶里程
	public int MFD_AVERAGE_SPEED= 0; //平均速度
	public int MFD_DIGITAL_SPEED_DISPLAY= 0; //数字式车速显示 
	public int MFD_SPEED_WARINING= 0; //车速报警
	public int MFD_OIL_TEMP= 0; //油温 
	
	/*------行车模式----------*/
	public int DRIVING_MODE_COMFORT= 0; //舒适
	public int DRIVING_MODE_NORMAL= 0; //标准
	public int DRIVING_MODE_SPORT= 0; //运动
	public int DRIVING_MODE_ECO= 0; //经济
	public int DRIVING_MODE_INDIVDUAL= 0; //个性化
	public int DRIVING_MODE=0; //模式
	public int DRIVING_MODE_INDIVDUAL_DCC= 0; //DCC
	public int DRIVING_MODE_INDIVDUAL_DBL= 0; //动态照明
	public int DRIVING_MODE_INDIVDUAL_Engine= 0; //发动机
	public int DRIVING_MODE_INDIVDUAL_ACC= 0; //ACC
	public int DRIVING_MODE_INDIVDUAL_AirCon= 0; //空调
	public int DRIVING_MODE_INDIVDUAL_Steering= 0; //方向盘
	/*-----单位-------*/
	public int UNIT_DISTANCE= 0; //里程 
	public int UNIT_SPEED= 0; //车速
//	public int UNIT_TEMPERATURE= 0; //温度
	public int UNIT_VOLUME= 0; //容积
//	public int UNIT_CONSUMPTION= 0; //油耗
	public int UNIT_PRESSURE= 0; //轮胎压力
	public int UNIT_TYPE= 0;     //单位制
	/*-----胎压设定-------*/
	public int TPMS_SHOW= 0; //轮胎压力监控显示 
	public int TPMS_WINTER_SPEED_WARNING= 0; //冬季轮胎车速报警
	public int TPMS_WINTER_SPEED_WARNING_VOL= 0; //冬季轮胎车速报警值
	/*-----个性化设置------*/
	public int PROFILE_INFORMATION= 0; //个人设置
	public int INDIVIDUAL_ENGINE= 0; //引擎
	public int PROFILE_STEERING= 0; //方向盘
	public int PROFILE_FRONT_LIGHT= 0; //前灯
	public int PROFILE_CLIMATE= 0; //气候
	
	public int REMOTE_KEY= 0; //遥控钥匙记忆匹配 
	public int KEY_ACTIVE= 0; //汽车钥匙已激活 
	/*-----显示屏状态--------*/
	public int RATATIONAL_RATE = 0; //转速提示
	public int MSG_NOTIFICATION = 0; //新消息提醒
	public int ENGINEE_AUTO_CONTROL = 0; //发动机节能自动启停提示
	public int ENERGY_BACKGROUND_LIGHT = 0; //节能模式的背景照明
	public int ADJUST_WARING_VOLUME = 0;   //调整报警音量  
	
	public int SWITCH_TRIPB_SETTING=0;     //里程B重设条件的切换
	public int SWITCH_TRIPA_SETTING=0;     //里程A重设条件的切换
	public int ADJUST_OUTSIDE_TEMP=0;     //调节外部气温显示
	
	/*------仪表显示设定信息-----*/
	public int DISPLAY_ECO_MIXPOWER=0;     //混合动力ECO指示设定
	public int DISPLAY_NAVI_MSG=0;     //仪表导航信息设定
	public int DISPLAY_SPEED_RANG=0;     //速度范围提示模式设定
	/*------运动模式-----*/
	public int SPORT_ENGINE_STATUS=0;     //运动模式发动机状态设定
	public int SPORT_BACKLIGHT_MODE=0;     //运动模式背光模式设定
	
	public int CAR_TYPE=0;   //车型设置
	
	public int AIR_CONDITIONER_CONTROL = 0; // 空调是否有界面控制，如果为1则有，不需要自动弹框
	
	/*----空调 设置-----*/
	public int AIRCON_AUTO_WIND=0;     //自动风量模式设定
	public int AIRCON_MODE=0;          //空调模式设定
	public int AIRCON_AIR_QUALITY=0;   //空气质量传感器设定
	public int AIRCON_AUTOZONE_TEMP=0; //自动区域温度设定
	public int AIRCON_SEAT_AUTOVENT=0;          //座椅自动通风设定
	public int AIRCON_SEAT_AUTOHEAT=0;          //座椅自动加热设定
	public int AIRCON_CONTORL_AUTOVENT=0;          //遥控启动座椅自动通风
	public int AIRCON_CONTORL_AUTOHEAT=0;          //遥控启动座椅自动加热
	
	public int AIRCON_BACKAREA_TEMP=0;          //后座区域温度
	public int AIRCON_FRONT_DEMIST=0;          //前窗自动去雾
	public int AIRCON_BACK_DEMIST=0;          //后窗自动去雾
	public int AIRCON_REMOTE_START=0;          //遥控启动空调
	public int AIRCON_AIR_QUALITY_1=0;          //空气质量传感器1设定
	public int AIRCON_CONTORL_AUTOHEAT_1=0;          //遥控启动座椅自动加热1
	
	/*----冲撞/监测系统----*/
	public int CRASHPROOF_SIDE_BLIND_AREA=0;          //侧盲区报警系统
	public int CRASHPROOF_WARING=0;          //防撞报警类型设定
	public int CRASHPROOF_PART_ASSIT=0;          //泊车辅助系统设定（带拖车补偿）
	public int CRASHPROOF_24GHZ_RADAR=0;          //24GHZ雷达设定
	public int CRASHPROOF_AUTO_READY=0;          //自动防撞准备
	public int CRASHPROOF_CARSTATUS_NOTIFY=0;          //汽车状态通知
	public int CRASHPROOF_RAMPWAY_ASSIT=0;          //坡道辅助系统
	/*----车辆提示信息----*/
	public int WARNING_MES_NUM=0;        //提示信息的数目   
	public int WARNING_MES_0=0;
	public int WARNING_MES_1=0; 
	public int WARNING_MES_2=0; 
	public int WARNING_MES_3=0; 
	public int WARNING_MES_4=0; 
	public int WARNING_MES_5=0; 
	public int START_STOP_MES=0;
	
	public int CONV_WARNING_MES_NUM=0;     
	public int CONV_WARNING_MES_0=0;
	public int CONV_WARNING_MES_1=0; 
	public int CONV_WARNING_MES_2=0;
	/*----舒适性/方便性设定----*/
	public int CONVENIENCE_SEAT_PARK_MOVE=0;     //驾驶员座椅停车移位设定
	public int CONVENIENCE_RIPE_PARK_MOVE=0;     //转向管柱离车移位设定
	public int CONVENIENCE_OUTERMIRROR_PARK_LEAN=0;     //外后视镜倒车自动倾斜设定
	public int CONVENIENCE_OUTERMIRROR_PARK_FOLD=0;     //外后视镜自动折叠设定
	public int CONVENIENCE_DRIVER_PRIVATE_SETTING=0;     //驾驶员个性设置设定
	public int CONVENIENCE_REVERSE_BACKWIPE_AUTO=0;     //倒档自动后窗雨刷
	public int CONVENIENCE_RIPE_PARK_LEAN=0;     //转向管柱离车倾斜设定
	public int CONVENIENCE_AUTO_WIPE=0;     //自动雨刮
	public int CONVENIENCE_CONSUMERS = 0; //舒适性用电器 
	public int CONVENIENCE_CONSUMERS_UNIT = 0; //舒适性用电器单位
	public int CONVENIENCE_PERCENT = 0; //负荷油耗刻度最大值
	public float FREQUENCY_VALUE=0;              //频率值
	public float TEMP_UNIT=0;              //温度单位
	
	public int ESC_SYSTEM=0;             //ESC系统
	public int TYPES_SPEED_WARNING=0;             //TYPES速度预警
	public int TYPES_SPEED_UNIT=0;             //TYPES速度单位
	public int TYPES_SPEED=0;             //TYPES速度
	
	public int THROTTLE_CONTROL=0;        //节气门位置 
	/*-------------------add by xyw start----------------------------------*/
	public int CAR_GEAR_STATUS = -1;							//车身档位，-1表示无效 1P 2N 3R 4D 5S
	public int CAR_BACK_STATUS = -1;							//倒车状态          0非倒档    1倒挡
	public int CAR_ILL_STATUS = -1;								//ILL灯光          0大灯关闭    1大灯打开
	public int CAR_VOLUME_KNOB=0;									//音量旋钮的值
	public int CAR_VOLUME_KNOB_UP=0;								//音量旋钮增加的值
	public int CAR_VOLUME_KNOB_DOWN=0;								//音量旋减少的值
	public int CAR_SELECTOR_KNOB=0;									//选择旋钮值
	public int CAR_SELECTOR_KNOB_UP=0;								//选择旋钮 增加的值
	public int CAR_SELECTOR_KNOB_DOWN=0;							//音量旋钮减少的值
	
	public int SOS_STATUS=0;									//SOS状态 0关闭 1呼叫 2已经连接
	public int LINK_SOS_STATUS=0;								//Link SoS通讯    0退出  1LINK  2SOS
	public int LANGUAGE_CHANGE=1;								//语言设置1中文 2 英文
	public int WARNING_ID=0;									//告警信息
	
	public int AUTO_COMPRESSOR_STATUS=0;						//AUTO时压缩机状态    1开 0关
	public int AUTO_CYCLE_STATUS=0;								//AUTO时内外循环控制方式   1自动  0手动
	public int AIR_COMFORTABLE_STATUS=0;						//空调舒适曲线设置  00缓慢 01正常 02快速
	public int AIR_ANION_STATUS=0;								//负离子模式  1开启  0关闭
	public int DRIVING_POSITION_SETTING=0;						//驾驶座椅自动加热设置 1开  0关
	public int DEPUTY_DRIVING_POSITION_SETTING=0;				//副驾驶座椅自动加热设置 1开  0关
	
	public int POSITION_WELCOME_SETTING=0;						//座椅迎宾功能设置  1开启  0关闭
	public int KEY_INTELLIGENCE=0;								//智能钥匙自动识别座椅  1开启 0关闭
	
	public int SPEED_OVER_SETTING=0;							//驾驶辅助超速报警  速度=Data3*10km/h
	public int WARNING_VOLUME=0;								//驾驶辅助组合仪表报警音量    0低   1中  2高
	public int REMOTE_POWER_TIME=0;								//驾驶辅助远程上电时间    0-30分钟
	public int REMOTE_START_TIME=0;								//驾驶辅助远程启动时间 0-30分钟
	public int DRIVER_CHANGE_MODE=0;							//驾驶辅助转向模式  0运动  1标准  2舒适

	public int REMOTE_UNLOCK=0;									//遥控解锁   1仅左前门  0所有车门
	public int SPEED_LOCK=0;									//车速上锁  1开 0关 
	public int AUTO_UNLOCK;										//自动解锁  1开 0关 
	public int CAR_LOCK_AUTO_STATUS=0;							//车门自动锁定 1启用 0停用	
	public int CAR_LOCK_STATUS=0;								//车门上锁 1启动 0停用
	public int CAR_LOCK_AUTO_STATUS_ENABLE;						//车门自动锁定 1有效 0无效
	public int CAR_LOCK_STATUS_ENABLE;							//车门上锁 1有效 0无效
	public int CAR_UNLOCK_STATUS_ENABLE;						//车门解锁 1有效 0无效
	
	public int REMOTE_FRONT_LEFT=0;								//遥控左前窗和天窗   1开 0关
	public int FRONT_WIPER_CARE=0;								//前雨刮维护功能  1开  0关
	public int REAR_WIPER_STATUS=0;								//后雨刮倒挡自动刮刷功能  1开 0关
	public int OUTSIDE_MIRROR_STATUS=0;							//外后视镜自动折叠  1开 0关
	public int REAR_WIPER_STATUS_ENABLE;						//倒车自动后雨刮 1有效 0无效
	
	public int FOG_LAMP_STATUS=0;								//雾灯转向辅助  1开 0关
	public int DAYTIME_LAMP_STATUS=0;							//日间行车灯  1开 0关
	public int AUTO_LAMP_STATUS;								//自动灯光灵敏度  0低  1中 2高
	public int GO_HOME_LAMP_STATUS=0;							//伴我回家照明  0关闭 1仅近光灯  2近光后雾
	public int WELCOME_PERSION_ILL_STATUS=0;					//迎宾照明 00取消 01b 15s 10b 30s 11b 60s
	public int ATMOSPHERE_ILL_STATUS=0;							//气氛照明 1启用 0停用
	public int ATMOSPHERE_ILL_VALUE=0;							//气氛照明值
	public int CHANGE_ILL_STATUS=0;								//随动转向大灯设置命令 1启动 0停用
	public int DAYTIME_LAMP_STATUS_ENABLE;						//日间行车灯 1有效 0无效
	public int GO_HOME_LAMP_STATUS_ENABLE;						//伴我回家照明 1有效 0无效
	public int WELCOME_PERSION_ILL_STATUS_ENABLE;				//迎宾照明 1有效 0无效
	public int ATMOSPHERE_ILL_STATUS_ENABLE;					//氛围照明 1有效 0无效
	public int CHANGE_ILL_STATUS_ENABLE;						//随动转向大灯设置命令 1有效0无效
	
	
	public int ESP_ENABLE=1;                                    //方向盘转角  1有效 0无效
	public int HOLOGRAM_ENABLE=1;								//全息影像设置 1有效 0无效
	public int CAT_SETTTING_ENABLE=1;							//车辆设置信息 1有限 0无效
	public int PANEL_SSETTING_ENABLE=1;							//面板按键信息 1有效 0无效
	public int AIR_INFO_ENABLE=1;								//空调信息  1有效 0无效
	
	public int CAMERA_DIAPLAY_ENABLE=0;							//摄像头显示开关  0关 1开
	public int CAMERA_MODE=1;									//全景摄像头模式 1前+全    2前+右侧     3后+全     4 水平停车+全     5垂直停车+全
	
	public int KEY_IN=0;										//钥匙插入状态 1插入 0拔出
	public int CAR_ACC_STATUS=0;								//ACC供电 1上电 0没上电
	
	public int AUTO_PARK_CAR_STATUS=0;							//自动驻车 1启动 0停用
	public int AUTO_PARK_CAR_STATUS_ENABLE=0;					//自动驻车 1有效 0无效
	public int ENGINE_START_STATUS_ENABLE=0;					//发动机启停停用功能使能 1有效 0无效
	public int ENGINE_START_STATUS=0;							//发动机启停停用设定
	
	public int PARKING_STATUS_ENABLE;							//驻车辅助 1有效 0无效
	public int TRUNK_UNLOCK_STATUS_ENABLE;						//仅行李箱解锁设置命令 1有效0无效
	public int CHANGE_LINE_STATUS_ENABLE;						//变道辅助 1有效0无效
	public int WELCOME_FUNTION_STATUS_ENABLE;					//迎宾功能 1有效 0无效
	
	public int PARKING_STATUS=0;								//驻车辅助 1启用 0停用
	public int TRUNK_UNLOCK_STATUS=0;							//仅行李箱解锁设置命令 1启用 0停用
	public int CHANGE_LINE_STATUS;								//变道辅助 1启用 0停用
	public int WELCOME_FUNTION_STATUS;							//迎宾功能 1启用 0停用
	
	public int REMEMBER_SPEED_STATUS=0;							//已记忆的速度值启用 1开 0关
	public int REMEMBER_SPEED_1=0;								//启用速度1  1选中 0未选中
	public int REMEMBER_SPEED_2=0;								//启用速度2  1选中 0未选中
	public int REMEMBER_SPEED_3=0;								//启用速度3  1选中 0未选中
	public int REMEMBER_SPEED_4=0;								//启用速度4  1选中 0未选中
	public int REMEMBER_SPEED_5=0;								//启用速度5  1选中 0未选中
	public int REMEMBER_SPEED_6=0;								//启用速度6  1选中 0未选中
	public int REMEMBER_SPEED_1_VALUE=0;								//启用速度值1  
	public int REMEMBER_SPEED_2_VALUE=0;								//启用速度值2  
	public int REMEMBER_SPEED_3_VALUE=0;								//启用速度值3  
	public int REMEMBER_SPEED_4_VALUE=0;								//启用速度值4  
	public int REMEMBER_SPEED_5_VALUE=0;								//启用速度值5  
	public int REMEMBER_SPEED_6_VALUE=0;								//启用速度值6  
	public int REMEMBER_SPEED_STATUS_ENABLE=0;					//已记忆的速度值使能 1有效 0无效
	public int REMEMBER_SPEED_1_ENABLE=0;						//使能速度1  1有效 0无效
	public int REMEMBER_SPEED_2_ENABLE=0;						//使能速度2  1有效 0无效
	public int REMEMBER_SPEED_3_ENABLE=0;						//使能速度3  1有效 0无效
	public int REMEMBER_SPEED_4_ENABLE=0;						//使能速度4  1有效 0无效
	public int REMEMBER_SPEED_5_ENABLE=0;						//使能速度5  1有效 0无效
	public int REMEMBER_SPEED_6_ENABLE=0;						//使能速度6  1有效 0无效
	
	public int CRUISE_SPEED_STATUS=0;							//巡航速度值启用 1开 0关
	public int CRUISE_SPEED_1=0;								//启用速度1  1选中 0未选中
	public int CRUISE_SPEED_2=0;								//启用速度2  1选中 0未选中
	public int CRUISE_SPEED_3=0;								//启用速度3  1选中 0未选中
	public int CRUISE_SPEED_4=0;								//启用速度4  1选中 0未选中
	public int CRUISE_SPEED_5=0;								//启用速度5  1选中 0未选中
	public int CRUISE_SPEED_6=0;								//启用速度6  1选中 0未选中
	public int CRUISE_SPEED_1_VALUE=0;								//启用速度值1  
	public int CRUISE_SPEED_2_VALUE=0;								//启用速度值2  
	public int CRUISE_SPEED_3_VALUE=0;								//启用速度值3  
	public int CRUISE_SPEED_4_VALUE=0;								//启用速度值4  
	public int CRUISE_SPEED_5_VALUE=0;								//启用速度值5 
	public int CRUISE_SPEED_6_VALUE=0;								//启用速度值6 
	public int CRUISE_SPEED_STATUS_ENABLE=0;					//巡航速度值使能 1有效 0无效
	public int CRUISE_SPEED_1_ENABLE=0;							//使能速度1  1有效 0无效
	public int CRUISE_SPEED_2_ENABLE=0;							//使能速度2  1有效 0无效
	public int CRUISE_SPEED_3_ENABLE=0;							//使能速度3  1有效 0无效
	public int CRUISE_SPEED_4_ENABLE=0;							//使能速度4  1有效 0无效
	public int CRUISE_SPEED_5_ENABLE=0;							//使能速度5  1有效 0无效
	public int CRUISE_SPEED_6_ENABLE=0;							//使能速度6  1有效 0无效
	
	public int UNIT_TEMPERATURE_ENABLE=1;						//Unit Step设定信息 Temperature 1有效 0无效
	public int UNIT_CONSUMPTION_ENABLE=1;						//Unit Step设定信息 Consumption 1有效 0无效
	public int UNIT_TEMPERATURE=1;								//Unit Step选项信息 Temperature 1:DegC  0:F
	public int UNIT_CONSUMPTION=1;								//Unit Step选项信息 Consumption  00:l/100km   01bkm/l  10b:mpg(us)
	
	public int TIME_YEAR=0;									//时间 年20xx
	public int TIME_MONTH=0;								//时间 月		
	public int TIME_DAY=0;									//时间 日
	public int TIME_HOUR=0;									//时间 时
	public int TIME_MINUTE;									//时间 分
	public int TIME_FORMAT;									//时间格式
	public int CRITICAL_PARK_ENABLE;						//紧急制动报使能
	public int AUTO_LOCK_SETTING_ENABLE;					//自动落锁使能
	public int FRONT_LAMP_DELAY_ENABLE;						//前照灯延迟使能
	public int TURN_START_AVM_ENABLE;						//转向启动AVM使能
	public int TURN_START_ANIMATION_ENABLE;						//转向启动动画使能
	public int SELECTOR_CAR_ASSIST_ENABLE; 						//选择车辆辅助线使能
	public int SPEED_OVER_SETTING_ENABLE; 						//超速报警使能
	
	public int CRITICAL_PARK_STATUS;						//紧急制动报警开关
	public int CRITICAL_PARK_MODE;							//紧急制动报警方式
	public int FRONT_LAMP_DELAY_STATUS;						//前照灯延迟
	public int TURN_START_AVM_STATUS;						//转向启动AVM
	public int TURN_START_ANIMATION_STATUS;						//转向启动动画
	public int SELECTOR_CAR_ASSIST_STATUS; 						//选择车辆辅助线
	/*-------------------add by xyw end----------------------------------*/

	
   
	public CanInfo() {

	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(CHANGE_STATUS);
		dest.writeInt(HEAD_CODE);
		dest.writeInt(BACK_LIGHT_DATA);
		dest.writeInt(CAR_SPEED_DATA);
		dest.writeInt(STEERING_BUTTON_MODE);
		dest.writeInt(STEERING_BUTTON_STATUS);
		dest.writeInt(AIR_CONDITIONER_STATUS);
		dest.writeInt(AC_INDICATOR_STATUS);
		dest.writeInt(CYCLE_INDICATOR);
		dest.writeInt(LARGE_LANTERN_INDICATOR);
		dest.writeInt(SMALL_LANTERN_INDICATOR);
		dest.writeInt(DAUL_LAMP_INDICATOR);
		dest.writeInt(MAX_FRONT_LAMP_INDICATOR);
		dest.writeInt(REAR_LAMP_INDICATOR);

		dest.writeInt(UPWARD_AIR_INDICATOR);
		dest.writeInt(PARALLEL_AIR_INDICATOR);
		dest.writeInt(DOWNWARD_AIR_INDICATOR);
		dest.writeInt(AIRCON_SHOW_REQUEST);
		dest.writeInt(AIR_RATE);

		dest.writeFloat(DRIVING_POSITON_TEMP);
		dest.writeFloat(DEPUTY_DRIVING_POSITON_TEMP);

		dest.writeInt(AQS_CIRCLE);
		dest.writeInt(LEFT_SEAT_TEMP);
		dest.writeInt(RIGTHT_SEAT_TEMP);

		dest.writeInt(BACK_LEFT_DISTANCE);
		dest.writeInt(BACK_MIDDLE_LEFT_DISTANCE);
		dest.writeInt(BACK_MIDDLE_RIGHT_DISTANCE);
		dest.writeInt(BACK_RIGHT_DISTANCE);

		dest.writeInt(FRONT_LEFT_DISTANCE);
		dest.writeInt(FRONT_MIDDLE_LEFT_DISTANCE);
		dest.writeInt(FRONT_MIDDLE_RIGHT_DISTANCE);
		dest.writeInt(FRONT_RIGHT_DISTANCE);

		dest.writeInt(LIGHT_MSG);
		dest.writeInt(STOPING_STATUS);
		dest.writeInt(REVERSE_GEAR_STATUS);

		dest.writeInt(PARKING_ASSIT_STATUS);
		dest.writeInt(RADAR_ALARM_STATUS);

		dest.writeInt(STERRING_WHELL_STATUS);

		dest.writeInt(POWER_AMPLIFIER_TYPE);
		dest.writeInt(POWER_AMPLIFIER_VOlUME);
		dest.writeInt(POWER_AMPLIFIER_BALANCE);
		dest.writeInt(POWER_AMPLIFIER_FADER);
		dest.writeInt(POWER_AMPLIFIER_BASS);
		dest.writeInt(POWER_AMPLIFIER_MIDTONE);
		dest.writeInt(POWER_AMPLIFIER_TREBLE);
		dest.writeInt(POWER_AMPLIFIER_CHANGE);

		dest.writeInt(SAFETY_BELT_STATUS);
		dest.writeInt(DISINFECTON_STATUS);
		dest.writeInt(HANDBRAKE_STATUS);
		dest.writeInt(TRUNK_STATUS);
		dest.writeInt(RIGHT_BACKDOOR_STATUS);
		dest.writeInt(LEFT_BACKDOOR_STATUS);
		dest.writeInt(RIGHT_FORONTDOOR_STATUS);
		dest.writeInt(LEFT_FORONTDOOR_STATUS);

		dest.writeInt(ENGINE_SPEED);
		dest.writeFloat(DRIVING_SPEED);
		dest.writeFloat(BATTERY_VOLTAGE);
		dest.writeFloat(OUTSIDE_TEMPERATURE);
		dest.writeInt(DRIVING_DISTANCE);
		dest.writeInt(REMAIN_FUEL);

		dest.writeInt(FUEL_WARING_SIGN);
		dest.writeInt(BATTERY_WARING_SIGN);
		dest.writeInt(CAMERA_MODE);	
	}

	public static final Parcelable.Creator<CanInfo> CREATOR = new Parcelable.Creator<CanInfo>() {
		public CanInfo createFromParcel(Parcel in) {
			return new CanInfo(in);
		}

		public CanInfo[] newArray(int size) {
			return new CanInfo[size];
		}
	};

	private CanInfo(Parcel in) {
		CHANGE_STATUS = in.readInt();

		HEAD_CODE = in.readInt();

		BACK_LIGHT_DATA = in.readInt();

		CAR_SPEED_DATA = in.readInt();

		STEERING_BUTTON_MODE = in.readInt();
		STEERING_BUTTON_STATUS = in.readInt();

		AIR_CONDITIONER_STATUS = in.readInt();
		AC_INDICATOR_STATUS = in.readInt();
		CYCLE_INDICATOR = in.readInt();
		LARGE_LANTERN_INDICATOR = in.readInt();
		SMALL_LANTERN_INDICATOR = in.readInt();
		DAUL_LAMP_INDICATOR = in.readInt();
		MAX_FRONT_LAMP_INDICATOR = in.readInt();
		REAR_LAMP_INDICATOR = in.readInt();
		// byte2风速及风向信息
		UPWARD_AIR_INDICATOR = in.readInt();
		PARALLEL_AIR_INDICATOR = in.readInt();
		DOWNWARD_AIR_INDICATOR = in.readInt();
		AIRCON_SHOW_REQUEST = in.readInt();
		AIR_RATE = in.readInt();
		// byte3驾驶位置处温度
		DRIVING_POSITON_TEMP = in.readFloat();
		// byte4副驾驶位置处温度
		DEPUTY_DRIVING_POSITON_TEMP = in.readFloat();
		// byte5座椅加热信息
		AQS_CIRCLE = in.readInt();
		LEFT_SEAT_TEMP = in.readInt();
		RIGTHT_SEAT_TEMP = in.readInt();
		/*
		 * 后雷达信息
		 */
		BACK_LEFT_DISTANCE = in.readInt();
		BACK_MIDDLE_LEFT_DISTANCE = in.readInt();
		BACK_MIDDLE_RIGHT_DISTANCE = in.readInt();
		BACK_RIGHT_DISTANCE = in.readInt();
		/*
		 * 前雷达信息
		 */
		FRONT_LEFT_DISTANCE = in.readInt();
		FRONT_MIDDLE_LEFT_DISTANCE = in.readInt();
		FRONT_MIDDLE_RIGHT_DISTANCE = in.readInt();
		FRONT_RIGHT_DISTANCE = in.readInt();
		/*
		 * 基本信息
		 */
		LIGHT_MSG = in.readInt();
		STOPING_STATUS = in.readInt();
		REVERSE_GEAR_STATUS = in.readInt();
		/*
		 * 泊车辅助状态
		 */
		PARKING_ASSIT_STATUS = in.readInt();
		RADAR_ALARM_STATUS = in.readInt();
		/*
		 * 方向盘转角
		 */
		STERRING_WHELL_STATUS = in.readInt();
		/*
		 * 功放状态POWER_AMPLIFIER_DATA
		 */
		POWER_AMPLIFIER_TYPE = in.readInt();
		POWER_AMPLIFIER_VOlUME = in.readInt();
		POWER_AMPLIFIER_BALANCE = in.readInt();
		POWER_AMPLIFIER_FADER = in.readInt();
		POWER_AMPLIFIER_BASS = in.readInt();
		POWER_AMPLIFIER_MIDTONE = in.readInt();
		POWER_AMPLIFIER_TREBLE = in.readInt();
		POWER_AMPLIFIER_CHANGE = in.readInt();
		/*
		 * 车身信息CAR_INFO_DATA
		 */
		SAFETY_BELT_STATUS = in.readInt();
		DISINFECTON_STATUS = in.readInt();
		HANDBRAKE_STATUS = in.readInt();
		TRUNK_STATUS = in.readInt();
		RIGHT_BACKDOOR_STATUS = in.readInt();
		LEFT_BACKDOOR_STATUS = in.readInt();
		RIGHT_FORONTDOOR_STATUS = in.readInt();
		LEFT_FORONTDOOR_STATUS = in.readInt();

		ENGINE_SPEED = in.readInt();
		DRIVING_SPEED = in.readFloat();
		BATTERY_VOLTAGE = in.readFloat();
		OUTSIDE_TEMPERATURE = in.readFloat();
		DRIVING_DISTANCE = in.readInt();
		REMAIN_FUEL = in.readInt();

		FUEL_WARING_SIGN = in.readInt();
		BATTERY_WARING_SIGN = in.readInt();
		CAMERA_MODE= in.readInt();
	}

	@Override
	public String toString() {
		return "CanInfo [CHANGE_STATUS=" + CHANGE_STATUS + ", HEAD_CODE="
				+ HEAD_CODE + ", BACK_LIGHT_DATA=" + BACK_LIGHT_DATA
				+ ", CAR_SPEED_DATA=" + CAR_SPEED_DATA
				+ ", STEERING_BUTTON_MODE=" + STEERING_BUTTON_MODE
				+ ", STEERING_BUTTON_STATUS=" + STEERING_BUTTON_STATUS
				+ ", AIR_CONDITIONER_STATUS=" + AIR_CONDITIONER_STATUS
				+ ", AC_INDICATOR_STATUS=" + AC_INDICATOR_STATUS
				+ ", CYCLE_INDICATOR=" + CYCLE_INDICATOR
				+ ", LARGE_LANTERN_INDICATOR=" + LARGE_LANTERN_INDICATOR
				+ ", SMALL_LANTERN_INDICATOR=" + SMALL_LANTERN_INDICATOR
				+ ", DAUL_LAMP_INDICATOR=" + DAUL_LAMP_INDICATOR
				+ ", MAX_FRONT_LAMP_INDICATOR=" + MAX_FRONT_LAMP_INDICATOR
				+ ", REAR_LAMP_INDICATOR=" + REAR_LAMP_INDICATOR
				+ ", UPWARD_AIR_INDICATOR=" + UPWARD_AIR_INDICATOR
				+ ", PARALLEL_AIR_INDICATOR=" + PARALLEL_AIR_INDICATOR
				+ ", DOWNWARD_AIR_INDICATOR=" + DOWNWARD_AIR_INDICATOR
				+ ", AIRCON_SHOW_REQUEST=" + AIRCON_SHOW_REQUEST
				+ ", AIR_RATE=" + AIR_RATE + ", DRIVING_POSITON_TEMP="
				+ DRIVING_POSITON_TEMP + ", DEPUTY_DRIVING_POSITON_TEMP="
				+ DEPUTY_DRIVING_POSITON_TEMP + ", AQS_CIRCLE=" + AQS_CIRCLE
				+ ", LEFT_SEAT_TEMP=" + LEFT_SEAT_TEMP + ", RIGTHT_SEAT_TEMP="
				+ RIGTHT_SEAT_TEMP + ", BACK_LEFT_DISTANCE="
				+ BACK_LEFT_DISTANCE + ", BACK_MIDDLE_LEFT_DISTANCE="
				+ BACK_MIDDLE_LEFT_DISTANCE + ", BACK_MIDDLE_RIGHT_DISTANCE="
				+ BACK_MIDDLE_RIGHT_DISTANCE + ", BACK_RIGHT_DISTANCE="
				+ BACK_RIGHT_DISTANCE + ", FRONT_LEFT_DISTANCE="
				+ FRONT_LEFT_DISTANCE + ", FRONT_MIDDLE_LEFT_DISTANCE="
				+ FRONT_MIDDLE_LEFT_DISTANCE + ", FRONT_MIDDLE_RIGHT_DISTANCE="
				+ FRONT_MIDDLE_RIGHT_DISTANCE + ", FRONT_RIGHT_DISTANCE="
				+ FRONT_RIGHT_DISTANCE + ", LIGHT_MSG=" + LIGHT_MSG
				+ ", STOPING_STATUS=" + STOPING_STATUS
				+ ", REVERSE_GEAR_STATUS=" + REVERSE_GEAR_STATUS
				+ ", PARKING_ASSIT_STATUS=" + PARKING_ASSIT_STATUS
				+ ", RADAR_ALARM_STATUS=" + RADAR_ALARM_STATUS
				+ ", STERRING_WHELL_STATUS=" + STERRING_WHELL_STATUS
				+ ", POWER_AMPLIFIER_TYPE=" + POWER_AMPLIFIER_TYPE
				+ ", POWER_AMPLIFIER_VOlUME=" + POWER_AMPLIFIER_VOlUME
				+ ", POWER_AMPLIFIER_BALANCE=" + POWER_AMPLIFIER_BALANCE
				+ ", POWER_AMPLIFIER_FADER=" + POWER_AMPLIFIER_FADER
				+ ", POWER_AMPLIFIER_BASS=" + POWER_AMPLIFIER_BASS
				+ ", POWER_AMPLIFIER_MIDTONE=" + POWER_AMPLIFIER_MIDTONE
				+ ", POWER_AMPLIFIER_TREBLE=" + POWER_AMPLIFIER_TREBLE
				+ ", POWER_AMPLIFIER_CHANGE=" + POWER_AMPLIFIER_CHANGE
				+ ", SAFETY_BELT_STATUS=" + SAFETY_BELT_STATUS
				+ ", DISINFECTON_STATUS=" + DISINFECTON_STATUS
				+ ", HANDBRAKE_STATUS=" + HANDBRAKE_STATUS + ", HOOD_STATUS="
				+ HOOD_STATUS + ", TRUNK_STATUS=" + TRUNK_STATUS
				+ ", RIGHT_BACKDOOR_STATUS=" + RIGHT_BACKDOOR_STATUS
				+ ", LEFT_BACKDOOR_STATUS=" + LEFT_BACKDOOR_STATUS
				+ ", RIGHT_FORONTDOOR_STATUS=" + RIGHT_FORONTDOOR_STATUS
				+ ", LEFT_FORONTDOOR_STATUS=" + LEFT_FORONTDOOR_STATUS
				+ ", ENGINE_SPEED=" + ENGINE_SPEED + ", DRIVING_SPEED="
				+ DRIVING_SPEED + ", BATTERY_VOLTAGE=" + BATTERY_VOLTAGE
				+ ", OUTSIDE_TEMPERATURE=" + OUTSIDE_TEMPERATURE
				+ ", DRIVING_DISTANCE=" + DRIVING_DISTANCE + ", REMAIN_FUEL="
				+ REMAIN_FUEL + ", FUEL_WARING_SIGN=" + FUEL_WARING_SIGN
				+ ", BATTERY_WARING_SIGN=" + BATTERY_WARING_SIGN + ", RANGE="
				+ RANGE + ", RANGE_UNIT=" + RANGE_UNIT + ", DISTANCE_UNIT="
				+ DISTANCE_UNIT + ", CONSUMPTION_UNIT=" + CONSUMPTION_UNIT
				+ ", SPEED_UNIT=" + SPEED_UNIT + ", DISTANCE_SINCE_START="
				+ DISTANCE_SINCE_START + ", CONSUMPTION_SINCE_START="
				+ CONSUMPTION_SINCE_START + ", SPEED_SINCE_START="
				+ SPEED_SINCE_START + ", TRAVELLINGTIME_SINCE_START="
				+ TRAVELLINGTIME_SINCE_START + ", DISTANCE_SINCE_REFUELING="
				+ DISTANCE_SINCE_REFUELING + ", CONSUMPTION_SINCE_REFUELING="
				+ CONSUMPTION_SINCE_REFUELING + ", SPEED_SINCE_REFUELINGT="
				+ SPEED_SINCE_REFUELINGT + ", TRAVELLINGTIME_SINCE_REFUELINGT="
				+ TRAVELLINGTIME_SINCE_REFUELINGT + ", DISTANCE_LONG_TERM="
				+ DISTANCE_LONG_TERM + ", CONSUMPTION_LONG_TERM="
				+ CONSUMPTION_LONG_TERM + ", SPEED_LONG_TERM="
				+ SPEED_LONG_TERM + ", TRAVELLINGTIME_LONG_TERM="
				+ TRAVELLINGTIME_LONG_TERM + ", VEHICLE_NO=" + VEHICLE_NO
				+ ", INSPECTON_DAYS_STATUS=" + INSPECTON_DAYS_STATUS
				+ ", INSPECTON_DAYS=" + INSPECTON_DAYS
				+ ", INSPECTON_DISTANCE_UNIT=" + INSPECTON_DISTANCE_UNIT
				+ ", INSPECTON_DISTANCE_STATUS=" + INSPECTON_DISTANCE_STATUS
				+ ", INSPECTON_DISTANCE=" + INSPECTON_DISTANCE
				+ ", OILCHANGE_SERVICE_DAYS_STATUS="
				+ OILCHANGE_SERVICE_DAYS_STATUS + ", OILCHANGE_SERVICE_DAYS="
				+ OILCHANGE_SERVICE_DAYS + ", OILCHANGE_SERVICE_DISTANCE_UNIT="
				+ OILCHANGE_SERVICE_DISTANCE_UNIT
				+ ", OILCHANGE_SERVICE_DISTANCE_STATUS="
				+ OILCHANGE_SERVICE_DISTANCE_STATUS
				+ ", OILCHANGE_SERVICE_DISTANCE=" + OILCHANGE_SERVICE_DISTANCE
				+ "]";
	}

}
