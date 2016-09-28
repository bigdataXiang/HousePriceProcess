package com.svail.population_mobility;

import java.util.HashMap;
import java.util.Map;

public class CountyPopuRate {
	public String code;
	Map<String, Double> map = new HashMap<String, Double>();
	Map<Integer,Double> map_intkey=new HashMap<Integer,Double>();
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}	
	
	public void setMap(String key,double rate){
		map.put(key, rate);
	}
	
	public void setMap_intkey(int key,double rate){
		map_intkey.put(key, rate);
	}

}
