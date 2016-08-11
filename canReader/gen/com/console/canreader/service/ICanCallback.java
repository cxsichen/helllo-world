/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: F:\\workapace\\canReader\\src\\com\\console\\canreader\\service\\ICanCallback.aidl
 */
package com.console.canreader.service;
public interface ICanCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.console.canreader.service.ICanCallback
{
private static final java.lang.String DESCRIPTOR = "com.console.canreader.service.ICanCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.console.canreader.service.ICanCallback interface,
 * generating a proxy if needed.
 */
public static com.console.canreader.service.ICanCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.console.canreader.service.ICanCallback))) {
return ((com.console.canreader.service.ICanCallback)iin);
}
return new com.console.canreader.service.ICanCallback.Stub.Proxy(obj);
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
com.console.canreader.service.CanInfo _arg0;
if ((0!=data.readInt())) {
_arg0 = com.console.canreader.service.CanInfo.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.readDataFromServer(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.console.canreader.service.ICanCallback
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
@Override public void readDataFromServer(com.console.canreader.service.CanInfo canInfo) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((canInfo!=null)) {
_data.writeInt(1);
canInfo.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
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
public void readDataFromServer(com.console.canreader.service.CanInfo canInfo) throws android.os.RemoteException;
}
