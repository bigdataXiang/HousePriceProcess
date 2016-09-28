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

public class Mongo_58_newhouse {
	   
	public static BasicDBObject transfer(Poi_58_newhouse poi)
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
		if (poi.COMMUNITY!= null)
			document.put("COMMUNITY", poi.COMMUNITY);
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
			document.put("PRICE",poi.PRICE);
		if (poi.HOUSE_TYPE != null)
			document.put("HOUSE_TYPE", poi.HOUSE_TYPE);
		if (poi.ADDRESS!= null)
			document.put("ADDRESS", poi.ADDRESS);
		if (poi.OPENING_DATE!= null)
			document.put("OPENING_DATE", poi.OPENING_DATE);
		if (poi.BUILDING_CONDITION!= null)
			document.put("BUILDING_CONDITION", poi.BUILDING_CONDITION);
		if (poi.EQUILT_TIME!= null)
			document.put("EQUILT_TIME", poi.EQUILT_TIME);
		if (poi.PROSSESSION_DATE!= null)
			document.put("PROSSESSION_DATE", poi.PROSSESSION_DATE);
		if (poi.BUILDING_TYPE!= null)
			document.put("BUILDING_TYPE", poi.BUILDING_TYPE);
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
			DB db = m.getDB("test");
			//DB db = m.getDB("bjdata"); //相当于库名 :bjdata
			DBCollection coll = db.getCollection("newhouse_poi");//相当于表名 ,建立一个集合，和数据库一样，如果没有，会自动建立 
			Vector<String> rds = FileTool.Load("E:/crawldata_beijing/2_58_newhouse_result","UTF-8");
			List<DBObject> dbList = new ArrayList<DBObject>();  
			for (int n = 0; n < rds.size(); n ++)
			{
				String element=rds.elementAt(n);
				Poi_58_newhouse poi = new Poi_58_newhouse(element);
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
