package com.console.canreader.service;

import com.console.canreader.service.ICanCallback;
import com.console.canreader.service.CanInfo;

interface ICanService {
	void addClient(ICanCallback client);
	void removeCliend(ICanCallback client);
	void sendDataToSp(String hexString);
	CanInfo getCanInfo();
}