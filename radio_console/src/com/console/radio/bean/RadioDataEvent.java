package com.console.radio.bean;

public class RadioDataEvent {
	public final byte[] mPacket;

	public RadioDataEvent(byte[] packet) {
		this.mPacket = packet;
	}
}
