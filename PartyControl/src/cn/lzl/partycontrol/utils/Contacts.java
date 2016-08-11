package cn.lzl.partycontrol.utils;

public class Contacts {
    public static final int MSG_RADIO_DATA = 0x01;
    public static final int MSG_UPDATA_UI = 0x02;
    public static final int MSG_DATA = 0x03;
    public static final int MSG_UPDATE_TIME_LABEL = 0x04;
    public static final int MSG_UPDATE_DATE_AND_TIME = 0x05;
    
    public static final String HEX_AUTO_SCAN = "F506000019EB";
    public static final String HEX_START = "F0000000010E";
    public static final String HEX_ST = "F50600000DF7";
    public static final String HEX_LOC = "F50600000FF5";
    public static final String HEX_BAND = "F50600000AFA";
    public static final String HEX_PS = "F506000009FB";
    public static final String HEX_HOME = "F50600000EF6";
    public static final String HEX_ITEM_FIRST = "F50600000103";
    public static final String HEX_ITEM_SECOND = "F50600000202";
    public static final String HEX_ITEM_THIRTH = "F50600000301";
    public static final String HEX_ITEM_FOUR = "F50600000400";
    public static final String HEX_ITEM_FIVE = "F506000005FF";
    public static final String HEX_ITEM_SIXTH = "F506000006FE";
    public static final String HEX_NEXT_STEP_MOVE = "F50600000CF8";
    public static final String HEX_PRE_STEP_MOVE = "F50600000BF9";
    public static final String HEX_NEXT_FAST_MOVE = "F50600001CE8";
    public static final String HEX_PRE_FAST_MOVE = "F50600001BE9";
    public static final String HEX_RESET_BACK_TIME = "F000000014FB";
    public static final String HEX_HOME_TO_FM = "F50200000602";
    public static final String HEX_FM_TO_HOME = "F50600000EF6";
    public static final String HEX_HOME_TO_BT = "F50200000BFD";
    public static final String HEX_BT_TO_HOME = "F50B000000FF";
    public static final String HEX_HOME_TO_CAMERA = "F50200000DFB";
    /**sound start**/
    public static final String HEX_HOME_TO_SOUND = "F50200000503";
    public static final String HEX_SOUND_TO_HOME = "F50B0000FF00";
    public static final String HEX_SOUND_BAS_SUB = "F50500000104";
    public static final String HEX_SOUND_BAS_ADD = "F50500000203";
    public static final String HEX_SOUND_TRE_SUB = "F50500000302";
    public static final String HEX_SOUND_TRE_ADD = "F50500000401";
    public static final String HEX_SOUND_YAOGUN = "F50500000500";
    public static final String HEX_SOUND_LIUXING = "F505000006FF";
    public static final String HEX_SOUND_JIESHI = "F505000007FE";
    public static final String HEX_SOUND_JINDIAN = "F505000008FD";
    public static final String HEX_SOUND_CAR_UP = "F50500000DF8";
    public static final String HEX_SOUND_CAR_DOWN = "F50500000CF9";
    public static final String HEX_SOUND_CAR_LEFT = "F50500000AFB";
    public static final String HEX_SOUND_CAR_RIGHT = "F50500000BFA";
    public static final String HEX_SOUND_CAR_RESET = "F50500000EF7";
    /**sound end**/
    /**party controll start***/
    public static final String HEX_HOME_TO_PARTYCONTROLL = "F50200000503";
    public static final String HEX_HOME_TO_PARTYCONTROLL2 = "EF000000020E";
    public static final String HEX_HOME_TO_PARTYCONTROLL3 = "FC00090100F9";
    public static final String HEX_HOME_TO_PARTYCONTROLL4 = "FC020F611180";
    public static final String HEX_MODEL_USER = "EF1000000000";
    public static final String HEX_MODEL_BYD = "EF10000001FF ";
    public static final String HEX_MODEL_VIOS = "EF10000002FE";
    public static final String HEX_MODEL_FORD = "EF10000003FD";
    public static final String HEX_MODEL_CAMRY = "EF10000004FC";
    public static final String HEX_MODEL_ELANTR = "EF10000005FB";
    public static final String HEX_MODEL_CRV = "EF10000006FA";
    public static final String HEX_MODEL_PORWER = "EF1F000011E0";
    public static final String HEX_MODEL_MODE = "EF1F000012DF";
    public static final String HEX_MODEL_VOLUMN = "EF1F000013DE";
    public static final String HEX_MODEL_NEXT = "EF1F000014DD";
    public static final String HEX_MODEL_PRE = "EF1F000015DC";
    public static final String HEX_MODEL_VOLUMN_ADD = "EF1F000016DB";
    public static final String HEX_MODEL_VOLUMN_SUB = "EF1F000017DA";
    public static final String HEX_MODEL_PHONE_ANWSER = "EF1F000018D9";
    public static final String HEX_MODEL_PHONE_DROPED = "EF1F000019D8";
    public static final String HEX_MODEL_PAUSE = "EF1F00001AD7";
    public static final String HEX_MODEL_SAVE = "EF0000000010";
    public static final String HEX_MODEL_CONTROL = "EF00000002";
    public static final String HEX_RESET = "EF10000000";
    /**party controll end***/
    
    
    public static final int FM1_FREQ = 0x30;
    public static final int FM2_FREQ = 0x31;
    public static final int FM3_FREQ = 0x32;
    public static final int AM1_FREQ = 0x33;
    public static final int AM2_FREQ = 0x34;
    public static final int FM1_SELECT = 0x40;
    public static final int FM2_SELECT = 0x41;
    public static final int FM3_SELECT = 0x42;
    public static final int AM1_SELECT = 0x43;
    public static final int AM2_SELECT = 0x44;
    public static final int FM1_1 = 0x00;
    public static final int FM1_2 = 0x01;
    public static final int FM1_3 = 0x02;
    public static final int FM1_4 = 0x03;
    public static final int FM1_5 = 0x04;
    public static final int FM1_6 = 0x05;
    public static final int FM2_1 = 0x06;
    public static final int FM2_2 = 0x07;
    public static final int FM2_3 = 0x08;
    public static final int FM2_4 = 0x09;
    public static final int FM2_5 = 0x0A;
    public static final int FM2_6 = 0x0B;
    public static final int FM3_1 = 0x0C;
    public static final int FM3_2 = 0x0D;
    public static final int FM3_3 = 0x0E;
    public static final int FM3_4 = 0x0F;
    public static final int FM3_5 = 0x10;
    public static final int FM3_6 = 0x11;
    public static final int AM1_1 = 0x12;
    public static final int AM1_2 = 0x13;
    public static final int AM1_3 = 0x14;
    public static final int AM1_4 = 0x15;
    public static final int AM1_5 = 0x16;
    public static final int AM1_6 = 0x17;
    public static final int AM2_1 = 0x18;
    public static final int AM2_2 = 0x19;
    public static final int AM2_3 = 0x1A;
    public static final int AM2_4 = 0x1B;
    public static final int AM2_5 = 0x1C;
    public static final int AM2_6 = 0x1D;
    
    public static final int SWITCH_MODE  =   0x70;//切换系统状�??
    public static final int KEY_CODE     =       0x71;//键盘按键
//  public static final int MODE_MAIN    =     0x02;//主界面的信息(备用)
    public static final int MODE_RADIO   =       0x76;//RADIO界面的信�??
//    public static final int MODE_TV      =         0x77;//TV界面的信�??(备用)
//    public static final int MODE_DVD     =     0x78;//DVD界面的信�??(备用)
//    public static final int MODE_AUX     =     0x79;//AUX界面的信�??(备用)
//    public static final int MODE_GPS     =     0x7A;//GPS界面的信�??(备用)//This is changed for CDC tianlai
//    public static final int MODE_CDC     =     0x7A;//CDC界面的信�??
    public static final int MODE_BLUETOOTH =        0x0B;//蓝牙界面的信�??
//    public static final int MODE_CDC       =   0x0C;//CDC界面的信�??
//    public static final int MODE_CONFIG_AUDIO  = 0x11;//声音设置信息
//    public static final int MODE_CONFIG_DISP =  0x12;//显示设置信息
    public static final int SYSTEM_TIME      =   0x7C;//系统时间
    public static final int SYSTEM_INFO     =    0x7D;//系统信息
    public static final int FACTORY_SETUP   =    0x6F;
    public static final int MODE_AUDIO_5_1  =    0x8C;//GPS音量
    public static final int TOUCH_SEND      =    0x72;//touch
    public static final int FUNC_KEY        =    0x88;
    public static final int SYSTEM_HW       =    0x80;
    public static final int VOLBAR          =    0x7E;
    
    public enum Action {
        LEFT(1), RIGHT(2);   
        private int value = 0;
        private Action(int value) {   
            this.value = value;
        }
        public static Action valueOf(int value) {    
            switch (value) {
            case 1:
                return LEFT;
            case 2:
                return RIGHT;
            default:
                return null;
            }
        }
        public int value() {
            return this.value;
        }
    };
    
}
