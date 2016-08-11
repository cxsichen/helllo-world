package cn.colink.serialport.service;

import cn.colink.serialport.service.ISerialPortCallback;

interface ISerialPortService {
	void addClient(ISerialPortCallback client);
	void removeCliend(ISerialPortCallback client);
	void sendDataToSp(String hexString);
}