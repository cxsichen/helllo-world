package cn.colink.serialport.service;

interface ISerialPortCallback{
	void readDataFromServer(in byte[] bytes);
}