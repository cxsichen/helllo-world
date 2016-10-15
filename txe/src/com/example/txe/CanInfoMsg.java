package com.example.txe;

public class CanInfoMsg {

	String canTye;
	String carType;
	String sort;
	String configuration;
	String name;
	
	public String getCanTye() {
		return canTye;
	}
	public void setCanTye(String canTye) {
		this.canTye = canTye;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getConfiguration() {
		return configuration;
	}
	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "CanInfoMsg [canTye=" + canTye + ", carType=" + carType
				+ ", sort=" + sort + ", configuration=" + configuration
				+ ", name=" + name + "]";
	}

}
