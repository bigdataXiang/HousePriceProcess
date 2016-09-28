package com.svail.houseprice;

import java.util.Vector;

public class TimeSeriesPrice {
    public String year;
    public String month;
    public String day;
    Vector<String> pois= new Vector<String>();
    Vector<Double> prices= new Vector<Double>();
    Vector<Double> unitprices= new Vector<Double>();

    public void setYear(String year){
        this.year=year;
    }
    public void setMonth(String month){
        this.month=month;
    }
    public void setDay(String day){
        this.day=day;
    }
    public void setPois(String poi){
        pois.add(poi);
    }
    public void setPrices(double price){
        prices.add(price);
    }
    public void setUnitPrices(double unitprice){
        unitprices.add(unitprice);
    }




}
