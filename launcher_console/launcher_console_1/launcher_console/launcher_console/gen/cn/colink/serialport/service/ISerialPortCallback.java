/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\workspace\\launcher_console\\launcher_console_1\\launcher_console\\launcher_console\\src\\cn\\colink\\serialport\\service\\ISerialPortCallback.aidl
 */
package cn.colink.serialport.service;
public interface ISerialPortCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements cn.colink.serialport.service.ISerialPortCallback
{
private static final java.lang.String DESCRIPTOR = "cn.colink.serialport.service.ISerialPortCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an cn.colink.serialport.service.ISerialPortCallback interface,
 * generating a proxy if needed.
 */
public static cn.colink.serialport.service.ISerialPortCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof cn.colink.serialport.service.ISerialPortCallback))) {
return ((cn.colink.serialport.service.ISerialPortCallback)iin);
}
return new cn.colink.serialport.service.ISerialPortCallback.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_readDataFromServer:
{
data.enforceInterface(DESCRIPTOR);
byte[] _arg0;
_arg0 = data.createByteArray();
this.readDataFromServer(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements cn.colink.serialport.service.ISerialPortCallback
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void readDataFromServer(byte[] bytes) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeByteArray(bytes);
mRemote.transact(Stub.TRANSACTION_readDataFromServer, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_readDataFromServer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public void readDataFromServer(byte[] bytes) throws android.os.RemoteException;
}
