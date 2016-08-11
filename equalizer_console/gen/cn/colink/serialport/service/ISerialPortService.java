/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\workspace\\equalizer_console\\src\\cn\\colink\\serialport\\service\\ISerialPortService.aidl
 */
package cn.colink.serialport.service;
public interface ISerialPortService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements cn.colink.serialport.service.ISerialPortService
{
private static final java.lang.String DESCRIPTOR = "cn.colink.serialport.service.ISerialPortService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an cn.colink.serialport.service.ISerialPortService interface,
 * generating a proxy if needed.
 */
public static cn.colink.serialport.service.ISerialPortService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof cn.colink.serialport.service.ISerialPortService))) {
return ((cn.colink.serialport.service.ISerialPortService)iin);
}
return new cn.colink.serialport.service.ISerialPortService.Stub.Proxy(obj);
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
case TRANSACTION_addClient:
{
data.enforceInterface(DESCRIPTOR);
cn.colink.serialport.service.ISerialPortCallback _arg0;
_arg0 = cn.colink.serialport.service.ISerialPortCallback.Stub.asInterface(data.readStrongBinder());
this.addClient(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_removeCliend:
{
data.enforceInterface(DESCRIPTOR);
cn.colink.serialport.service.ISerialPortCallback _arg0;
_arg0 = cn.colink.serialport.service.ISerialPortCallback.Stub.asInterface(data.readStrongBinder());
this.removeCliend(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_sendDataToSp:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.sendDataToSp(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements cn.colink.serialport.service.ISerialPortService
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
@Override public void addClient(cn.colink.serialport.service.ISerialPortCallback client) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((client!=null))?(client.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_addClient, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void removeCliend(cn.colink.serialport.service.ISerialPortCallback client) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((client!=null))?(client.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_removeCliend, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void sendDataToSp(java.lang.String hexString) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(hexString);
mRemote.transact(Stub.TRANSACTION_sendDataToSp, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_addClient = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_removeCliend = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_sendDataToSp = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
}
public void addClient(cn.colink.serialport.service.ISerialPortCallback client) throws android.os.RemoteException;
public void removeCliend(cn.colink.serialport.service.ISerialPortCallback client) throws android.os.RemoteException;
public void sendDataToSp(java.lang.String hexString) throws android.os.RemoteException;
}
