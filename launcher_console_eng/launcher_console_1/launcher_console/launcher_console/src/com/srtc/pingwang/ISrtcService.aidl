// ISrtcInterface.aidl
package com.srtc.pingwang;

import com.srtc.pingwang.IVideoListener;
// Declare any non-default types here with import statements

import android.os.ParcelFileDescriptor;

interface ISrtcService {

    void setVideoListener(in IVideoListener listener);

    void removeVideoListener();

    int startVideo();

    int stopVideo();

    int getConnectStatus();
    
    int setStatus(int index, int state);

    int getStatus(int index);
    
}
