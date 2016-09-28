package com.svail.gridprocess;

import java.util.Vector;

public class Price {
	Vector<String> address_vet=new Vector();
	public double code;
	Vector<Double> price_vet=new Vector();
	Vector<String> pois=new Vector();

	public void setPois(String poi){
		pois.add(poi);
	}
	public void setPrice(double price){
		price_vet.add(price);
		//this.code=code1;
	}
	public void setCode(double code2){
		this.code=code2;
	}
	public void setAddress(String address){
		address_vet.add(address);
	}


	public Vector<String> getPoi(int code1){
		return pois;
	}
	public Vector<Double> getPrice(int code1){
		return price_vet;

	}
	public Vector<String> getAddress(int code1){
		return  address_vet;
	}

}
