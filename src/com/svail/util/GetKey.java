package com.svail.util;


import java.util.*;

import com.svail.population_mobility.CountyPopuRate;
public class GetKey {
 Map<String, CountyPopuRate> map;
 public GetKey(Map<String, CountyPopuRate> map2) {                    //初始化操作
  this.map=map2;
 }
 public Object getKey(Object value) {
  Object o=null;
  ArrayList all=new ArrayList();    //建一个数组用来存放符合条件的KEY值
 

/* 这里关键是那个entrySet()的方法,它会返回一个包含Map.Entry集的Set对象. Map.Entry对象有getValue和getKey的方法,利用这两个方法就可以达到从值取键的目的了 **/  

 Set set=map.entrySet();
  Iterator it=set.iterator();
  while(it.hasNext()) {
   Map.Entry entry=(Map.Entry)it.next();
   if(entry.getValue().equals(value)) {
    o=entry.getKey();
    all.add(o);          //把符合条件的项先放到容器中,下面再一次性打印出
   }
  }
  return all;
 }
 
 public String getKey(int value) {
	  Object o=null;
	  String all="";    //建一个数组用来存放符合条件的KEY值
	 

	/* 这里关键是那个entrySet()的方法,它会返回一个包含Map.Entry集的Set对象. Map.Entry对象有getValue和getKey的方法,利用这两个方法就可以达到从值取键的目的了 **/  

	 Set set=map.entrySet();
	  Iterator it=set.iterator();
	  while(it.hasNext()) {
	   Map.Entry entry=(Map.Entry)it.next();
	   if(entry.getValue().equals(value)) {
	    o=entry.getKey();
	    all=o.toString();          //把符合条件的项先放到容器中,下面再一次性打印出
	   }
	  }
	  return all;
	 }
 public static void main(String[] args) {
  HashMap map=new HashMap();
  map.put("1","a");
  map.put("110109","4");
  map.put("3","c");
  map.put("4","c");
  map.put("5","e");
  Map_ValueGetKey mvg=new Map_ValueGetKey(map);
  System.out.println(mvg.getKey("4"));
  }

}
