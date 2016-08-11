package com.console.canreader.service;

import java.util.Arrays;

import android.os.Parcel;
import android.os.Parcelable;

public class CanInfo implements Parcelable {

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
	 * 方向盘按键
	 * STEERING_BUTTON_MODE
	 * 0：无按键或释放       1：vol+   2：vol-   3：menuup  4：menu down 
	 * 5： PHONE  6：mute   7：SRC  8：SPEECH/MIC
	 */
	public int STEERING_BUTTON_MODE = 0;
	public int STEERING_BUTTON_STATUS = 0;
	/*
	 * 空调信息
	 */
	//  空调状态都是on off
	public int AIR_CONDITIONER_STATUS = -1;    //空调开关
	public int AC_INDICATOR_STATUS = 0;        //AC状态
	public int CYCLE_INDICATOR = 0;            //内外循环指示   0不显示
	public int LARGE_LANTERN_INDICATOR = 0;    //大auto
	public int SMALL_LANTERN_INDICATOR = 0;     //小auto
	public int DAUL_LAMP_INDICATOR = 0;    
	public int MAX_FRONT_LAMP_INDICATOR = 0;   //前窗除雾
	public int REAR_LAMP_INDICATOR = 0;        //后窗除雾
	// 风速及风向信息
	public int UPWARD_AIR_INDICATOR = 0;       //吹前窗
	public int PARALLEL_AIR_INDICATOR = 0;     //吹身
	public int DOWNWARD_AIR_INDICATOR = 0;     //吹脚
	public int AIRCON_SHOW_REQUEST = 0;           
	public int AIR_RATE = 0;	             //风速        -1是自动 其他的都是数值
	public float DRIVING_POSITON_TEMP = 0;   // 驾驶位置处温度	  0xFE=low对应0  0xFF=high对应1 
	public float DEPUTY_DRIVING_POSITON_TEMP = 0; // 副驾驶位置处温度
	// 座椅加热信息

	public int AQS_CIRCLE = -1;

	
	public int LEFT_SEAT_TEMP = -1;          //左座椅温度
	public int RIGTHT_SEAT_TEMP = -1;       //右座椅温度
	

	/*
	 * 后雷达信息
	 */
	public int BACK_LEFT_DISTANCE = 0;                 //左后雷达
	public int BACK_MIDDLE_LEFT_DISTANCE = 0;           //左后中雷达
	public int BACK_MIDDLE_RIGHT_DISTANCE = 0;           //右后中雷达
	public int BACK_RIGHT_DISTANCE = 0;                 //右后雷达

	
	/*
	 * 前雷达信息
	 */
	public int FRONT_LEFT_DISTANCE = 0;                 //左前雷达
	public int FRONT_MIDDLE_LEFT_DISTANCE = 0;         //左前中雷达
	public int FRONT_MIDDLE_RIGHT_DISTANCE = 0;          //右前中雷达
	public int FRONT_RIGHT_DISTANCE = 0;                //右前雷达
	

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
	public int RADAR_ALARM_STATUS = 0;
	/*
	 * 方向盘转角
	 */
	public int STERRING_WHELL_STATUS = 0;                    //方向盘转角
	                                                        //ESP>0  左转        ESP<0 右转             
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
	public int SAFETY_BELT_STATUS = 0;                          //安全带状态     -1表示无此功能         0正常 1报警
	public int DISINFECTON_STATUS = 0;                          //清洁液状态     -1表示无此功能
	public int HANDBRAKE_STATUS = 0;                           //手刹状态          0正常 1报警
	
	public int HOOD_STATUS = 0;                                   //引擎盖未关
	public int TRUNK_STATUS = 0;                                 //后盖报警
	public int RIGHT_BACKDOOR_STATUS = 0;                       //右后门未关
	public int LEFT_BACKDOOR_STATUS = 0;                       //左后门未关
	public int RIGHT_FORONTDOOR_STATUS = 0;                   //右前门未关
	public int LEFT_FORONTDOOR_STATUS = 0;                   //左前门未关


	public int ENGINE_SPEED = 0;                           //发动机转速
	public float DRIVING_SPEED = 0;                        //瞬时车速
	public float BATTERY_VOLTAGE = 0;        //电池电量       -1表示无此功能 
	public float OUTSIDE_TEMPERATURE = 0;   //窗外温度
	public int DRIVING_DISTANCE = 0;             //行车里程
	public int REMAIN_FUEL = 0;                  //剩余油量     -1表示无此功能 

	public int FUEL_WARING_SIGN = 0;           //没油报警
	public int BATTERY_WARING_SIGN = 0;        //没电报警
	
	public CanInfo(){
		
	}

	@Override
	public String toString() {
		return "CanInfo [HEAD_CODE=" + HEAD_CODE + ", BACK_LIGHT_DATA="
				+ BACK_LIGHT_DATA + ", CAR_SPEED_DATA=" + CAR_SPEED_DATA
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
				+ ", HANDBRAKE_STATUS=" + HANDBRAKE_STATUS + ", TRUNK_STATUS="
				+ TRUNK_STATUS + ", RIGHT_BACKDOOR_STATUS="
				+ RIGHT_BACKDOOR_STATUS + ", LEFT_BACKDOOR_STATUS="
				+ LEFT_BACKDOOR_STATUS + ", RIGHT_FORONTDOOR_STATUS="
				+ RIGHT_FORONTDOOR_STATUS + ", LEFT_FORONTDOOR_STATUS="
				+ LEFT_FORONTDOOR_STATUS + ", ENGINE_SPEED=" + ENGINE_SPEED
				+ ", DRIVING_SPEED=" + DRIVING_SPEED + ", BATTERY_VOLTAGE="
				+ BATTERY_VOLTAGE + ", OUTSIDE_TEMPERATURE="
				+ OUTSIDE_TEMPERATURE + ", DRIVING_DISTANCE="
				+ DRIVING_DISTANCE + ", REMAIN_FUEL=" + REMAIN_FUEL
				+ ", FUEL_WARING_SIGN=" + FUEL_WARING_SIGN
				+ ", BATTERY_WARING_SIGN=" + BATTERY_WARING_SIGN + "]";
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
	}

}
