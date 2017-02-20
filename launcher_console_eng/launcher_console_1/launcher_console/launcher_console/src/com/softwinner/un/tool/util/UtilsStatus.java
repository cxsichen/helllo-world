package com.softwinner.un.tool.util;


/**
 * Created by yao.fu on 8/11/16.
 */
public class UtilsStatus {


    //主要给三个接口使用：

    //int setStatus(int index, int state);

    //int getStatus(int index);

    //void onStatusChange(int index, int state);

    //获取或者设置录像状态
    public static final int INDEX_RECORDING = 1;

    //拍照按钮控制
    public static final int INDEX_CAPTURE = 2;

    //文件枷锁控制
    public static final int INDEX_FILE_LOCK = 3;
    
    //ADAS 状态有三种: 0,关闭; 1,静音模式; 2,全功能模式;
    //ADAS Settings
    public static final int INDEX_ADAS = 4;

}

