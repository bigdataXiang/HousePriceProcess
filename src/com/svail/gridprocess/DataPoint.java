package com.svail.gridprocess;

public class DataPoint {
	public double x;
    public double y;
    public double data;
    public String address;
    public double X_MAX;
    public double X_MIN;
    public double Y_MAX;
    public double Y_MIN;
    public String poi;
    
    public double getX() {
    	  return x;
    }
    public double getY() {
    	  return y;
    }
    public double getData() {
  	      return data;
  	}
    public String getAddress() {
	      return address;
	}
    public String getPoi(){
    	return poi;
    }
    
    public void setPoi(String poi){
    	this.poi=poi;
    }
    public void setX(double x){
          this.x=x;
    }
    public void setY(double y){
          this.y=y;
    }
    public void setData(double data){
          this.data=data;
    }
    public void setAddress(String address){
        this.address=address;
  }


}
