package com.softwinner.un.tool.util;

import com.softwinner.un.tool.utilex.UNLog;

import java.io.FileDescriptor;

public class UNJni {

    private static final String TAG = "UNJni";

    static {

        UNLog.debug_print(UNLog.LV_DEBUG, TAG, "UNJni Load JNI...");

        try {
            System.loadLibrary("gnustl_shared");
            System.loadLibrary("ipcamera");
        } catch (UnsatisfiedLinkError e) {
            UNLog.debug_print(UNLog.LV_ERROR, TAG, "UNJni Load JNI exception " + e.getMessage());
        }

    }

    public native static int jni_initNetServer();
    public native static int jni_deInitNetServer();
    public native static int jni_startDisplay(Object winid);
    public native static int jni_stopDisplay();


}
