package com.console.video.bean;

public class StorageInfo {
	public String path;
	public String state;
	public boolean isRemoveable;
	
	final public int FLASH=0;
	final public int SDCARD=1;
	final public int USB=2;
	
	public StorageInfo(String path) {
		this.path = path;
	}
 
	public boolean isMounted() {
		return "mounted".equals(state);
	}
	
	public int getType(){
		if(path.indexOf("emulated")!=-1){
			return FLASH;
		}
		if(path.indexOf("sdcard")!=-1){
			return SDCARD;
		}
		if(path.indexOf("usb")!=-1){
			return USB;
		}
		return FLASH;
	}
}