package com.svail.tomongo;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector; 

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.svail.tomongo.Poi;
import com.svail.util.FileTool;

public class Mongo_58_resold {
	   
	public static BasicDBObject transfer(Poi_resold_58 poi)
	{
		
		BasicDBObject document = new BasicDBObject();
		try
		{
		if (poi.COORDINATE !=null)
		{
			String[] coordinate=poi.COORDINATE.split(";");
			document.put("lng", Double.parseDouble(coordinate[0]));
			document.put("lat", Double.parseDouble(coordinate[1]));
		}
		if (poi.TITLE != null)
			document.put("TITLE", poi.TITLE);
		if (poi.TIME != null)
		{
			DateFormat format= new SimpleDateFormat("yyyy/MM/dd hh:mm:ss"); 
			Date time_date=null;
			   try {  
				    time_date= format.parse(poi.TIME);  
		        } catch (Exception e) {  
		             // TODO Auto-generated catch block  
		             e.printStackTrace();  
		        }
			BasicDBObject time = new BasicDBObject("time",time_date);
			document.put("TIME", time);
		}
		if (poi.PRICE != null)
			document.put("PRICE", Double.parseDouble(poi.PRICE));
		if (poi.DOWN_PAYMENT != null)
			document.put("DOWN_PAYMENT", poi.DOWN_PAYMENT);
		if (poi.UNIT_PRICE != null)
			document.put("UNIT_PRICE", poi.UNIT_PRICE);
		if (poi.COMMUNITY != null)
			document.put("COMMUNITY", poi.COMMUNITY);
		if (poi.LOCATION != null)
			document.put("LOCATION", poi.LOCATION);
		if (poi.HOUSE_TYPE != null)
			document.put("HOUSE_TYPE", poi.HOUSE_TYPE);
		if (poi.AREA != null)
		{
			if(poi.AREA!="暂无")
			document.put("AREA", Double.parseDouble(poi.AREA));
			else
			document.put("AREA", poi.AREA);	
		}
		if (poi.BUILDING_TIME != null&&poi.BUILDING_TIME !="暂无")
			document.put("BUILDING_TIME", Integer.parseInt(poi.BUILDING_TIME));
		if (poi.ORIENTATION != null)
			document.put("ORIENTATION", poi.ORIENTATION);
		if (poi.FLOOR != null)
			document.put("FLOOR", poi.FLOOR);
		if (poi.FITMENT != null)
			document.put("FITMENT", poi.FITMENT);
		if (poi.PROPERTY_TYPE != null)
			document.put("PROPERTY_TYPE", poi.PROPERTY_TYPE);
		if (poi.BUILDING_OWNERTYPE != null)
			document.put("BUILDING_OWNERTYPE", poi.BUILDING_OWNERTYPE);
		if (poi. BUILDING_SERVICE != null)
			document.put(" BUILDING_SERVICE", poi. BUILDING_SERVICE);
		if (poi.TRAFFIC != null)
			document.put("TRAFFIC ", poi.TRAFFIC);
		if (poi.URL != null)
			document.put("URL ", poi.URL);
		if (poi.SOURCE != null)
			document.put("SOURCE ", poi.SOURCE);
		System.out.println(document);
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			System.out.println("发生异常的原因为 :"+e.getMessage());
			e.printStackTrace();
		}
			return document;
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Mongo m;
		try {
			System.out.println("运行开始:");
			m = new Mongo("192.168.6.168", 27017);
			//选择数据库，如果没有这个数据库的话，会自动建立  
			//m.dropDatabase("test"); 
			//System.out.println("数据库删除成功!");
			
			DB db = m.getDB("bjdata"); //相当于库名 :bjdata
			DBCollection coll = db.getCollection("resold_poi");//相当于表名 ,建立一个集合，和数据库一样，如果没有，会自动建立 
			Vector<String> rds = FileTool.Load("E:/crawldata_beijing/6_58_resold_result.txt","UTF-8");
			//E:/crawldata_beijing/anjuke/resold/anjuk_resold_result/1_anjuke_resold_result.txt
			List<DBObject> dbList = new ArrayList<DBObject>();  
			for (int n = 0; n < rds.size(); n ++)
			{
				String element=rds.elementAt(n);
				Poi_resold_58 poi = new Poi_resold_58(element);
				if(poi.COORDINATE!=null)
				{
				 BasicDBObject obj = transfer(poi);
				 dbList.add(obj);
				}
			}
			// 可以循环插入多条数据 
			coll.insert(dbList) ;
			System.out.println("数据插入成功!"); 
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			System.out.println("发生异常的原因为 :"+e.getMessage());
			e.printStackTrace();
		}
		  
		
		
	}

}
