// IVideoListener.aidl
package com.srtc.pingwang;

// Declare any non-default types here with import statements

interface IVideoListener {
    void onConnectStatusChange(int status);
    void onStatusChange(int index, int state);
}
