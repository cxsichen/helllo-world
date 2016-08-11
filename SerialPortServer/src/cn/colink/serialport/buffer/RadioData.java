package cn.colink.serialport.buffer;

import java.util.Arrays;

public class RadioData {
    public int  curFreq;
    public int  curBand;
    public int  curFavDown;
    public int[] FF = new int[6];
    
    public void clearData(){
        curFreq = 0;
        curBand = 0;
        curFavDown = 0;
        Arrays.fill(FF, 0);
    }
}
