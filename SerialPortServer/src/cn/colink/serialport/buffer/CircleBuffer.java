package cn.colink.serialport.buffer;

import android.util.Log;
import cn.colink.serialport.utils.Contacts;

public class CircleBuffer{
    
    private byte[] mBuffer;
    private int mBufSize;
    private int mHeadIndex;
    private int mFootIndex;
    private int mDataSize;
    public static final int PACKET_LENGHT = 6;

    public CircleBuffer(int size) {    
        this.mBufSize = size;
        mBuffer = new byte[size];
    }
    
    
    public void put(byte[] data,int sizeOfByte){ 
        
        if(willBeOverFlow(1)){
            return;
        }

        int bufSizeOfBottom = mBufSize - mHeadIndex;
        if(bufSizeOfBottom < sizeOfByte){
            for (int i = 0; i < bufSizeOfBottom; i++) {
                mBuffer[mHeadIndex + i] = data[i];
            }
            for (int i = 0; i < (sizeOfByte - bufSizeOfBottom); i++) {
                mBuffer[i] = data[bufSizeOfBottom + i];
            }
        }else{
            for (int i = 0; i < sizeOfByte; i++) {
                mBuffer[mHeadIndex + i] = data[i];
            }
        }
        mHeadIndex += sizeOfByte;
        if(mHeadIndex >= mBufSize){
            mHeadIndex  -= mBufSize;
        }
        
        mDataSize += sizeOfByte;
    }
    
    public void read(byte[] data ,int sizeOfByte){
        if(willBeDataLack(sizeOfByte)){
            return;
        }
        int bufSizeOfBottom = mBufSize - mFootIndex;
        if(bufSizeOfBottom < sizeOfByte){
            for (int i = 0; i < bufSizeOfBottom; i++) {
                data[i] = mBuffer[mFootIndex + i];
            }
            for (int i = 0; i < (sizeOfByte - bufSizeOfBottom); i++) {
                data[bufSizeOfBottom + i] = mBuffer[i];
            }
        }else{
            for (int i = 0; i < sizeOfByte; i++) {
                data[i] = mBuffer[mFootIndex + i];
            }
        }
    }
    
    public int getDateSize(){
        return mDataSize;
    }
    
   public boolean getPacket(byte[] packet)
    {
        while(getDateSize() >= PACKET_LENGHT)
        {
            if(isAValidHead(readOffset(0)))
            {
                read(packet,PACKET_LENGHT);
                if(isAValidPacket(packet))
                {
                    Offset(PACKET_LENGHT);
                    return true;
                }
            }
            Log.i("packet", "error : " + mDataSize);
            Offset(1);
            Log.i("packet", "error : " + mDataSize);
        }
        return false;
    }
    
    private boolean willBeOverFlow(int sizeOfByte)
    {
        return ((mDataSize + sizeOfByte) > (mBufSize));
    }
    
    private boolean willBeDataLack(int sizeOfByte)
    {
        return (sizeOfByte > mDataSize);
    }
    
    private byte readOffset(int index)
    {
        return mBuffer[mFootIndex + index];
    }
    
    void Offset(int sizeOfByte)
    {
        mFootIndex += sizeOfByte;
        if(mFootIndex >= mBufSize){
            mFootIndex -= mBufSize;
        }
        
        mDataSize -= sizeOfByte;
    }
    
    boolean isAValidHead(byte head)
    {
        int intHead = head & 0xFF;
        if(!((intHead == Contacts.SWITCH_MODE)   ||
             (intHead == Contacts.KEY_CODE)      ||
             (intHead == Contacts.MODE_RADIO)    ||
             (intHead == Contacts.SYSTEM_TIME)   ||
             (intHead == Contacts.SYSTEM_INFO)   ||
             (intHead == Contacts.FACTORY_SETUP) ||
             (intHead == Contacts.MODE_AUDIO_5_1)||
             (intHead == Contacts.TOUCH_SEND)    ||
             (intHead == Contacts.FUNC_KEY)      ||
             (intHead == Contacts.SYSTEM_HW)     ||
             (intHead == Contacts.MODE_MSG)     ||
             (intHead == Contacts.VERSION_0)     ||
             (intHead == Contacts.VERSION_1)     ||
             (intHead == Contacts.VERSION_2)     ||
             (intHead == Contacts.VERSION_3)     ||
             (intHead == Contacts.VERSION_4)     ||
             (intHead == Contacts.BACKLIGHT)     ||
             (intHead == Contacts.VOLBAR))){
            return false;
        }
        return true;
    }
    
    private boolean isAValidPacket(final byte[] packet)
    {
        byte sum = 0;
        if(!isAValidHead(packet[0]))
        {
            return false;
        }
        for(int i = 0; i < packet.length-1; i++)
        {
            sum += packet[i];
        }
        sum = (byte) ((byte)0xff - sum);
        if (packet[packet.length - 1] == sum)
        {
            return true;
        }
        return false;
    }
    
    public void clearBuffer(){
        mHeadIndex = 0;
        mFootIndex = 0;
        mDataSize = 0;
        for (int i = 0; i < mBuffer.length; i++) {
            mBuffer[i] = (byte)0;
        }
    }

}
