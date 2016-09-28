package com.svail.population_mobility;

import java.util.Vector;

public class CountyPopulation {
	public double longitude;
	public double latitude;
	public String code;
	public String countyname;
	public String codereg;
	public Vector<Integer> poiamounts=new Vector();
	public Vector<String> homepois=new Vector();
	public Vector<String> postpois=new Vector();
	public Vector<String> postcodes=new Vector();
	public Vector<String> homecodes=new Vector();

	
	public double getLongitude() {
		return longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public String getCode() {
		return code;
	}
	public String getCountyname(){
		return countyname;
	}
	public String getCodeReg(){
		return codereg;
	}
	

	public Vector<String> gethomePois(){
		return homepois;
	}
	public Vector<String> getpostPois(){
		return postpois;
	}
    public Vector<String> getpostCodes(){
    	return postcodes;
    }
    public Vector<String> gethomeCodes(){
    	return homecodes;
    }
    public Vector<Integer> getPoiamounts(){
		return poiamounts;
	}
	
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public void setCode(String code) {
		this.code = code;
	}	
	public void setCountyname(String countyname){
		this.countyname=countyname;
	}
	public void setCodeReg(String codereg){
		this.codereg=codereg;
	}
	
	
	public void sethomePois(String poi){
		homepois.add(poi);
    }
	public void setpostPois(String poi){
		postpois.add(poi);
    }
	public void setpostCodes(String poi){
		postcodes.add(poi);
	}
	public void sethomeCodes(String poi){
		homecodes.add(poi);
	}
	public void setPoiamounts(int i){
		poiamounts.add(i);
	}

}

	