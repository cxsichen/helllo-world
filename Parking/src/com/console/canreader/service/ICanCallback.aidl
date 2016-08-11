package com.console.canreader.service;
import com.console.canreader.service.CanInfo;

interface ICanCallback{
	void readDataFromServer(in CanInfo canInfo);
}