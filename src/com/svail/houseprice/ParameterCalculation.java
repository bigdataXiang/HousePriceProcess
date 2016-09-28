package com.svail.houseprice;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import com.svail.util.FileTool;

import net.sf.json.JSONObject;

public class ParameterCalculation {
	public static void main(String args[]){
		
		
	}
	public static void dataCalling(boolean mongo){
		
       if(mongo){
    	   
       }else{
    	   
       }

	}
	public static void getDataFromMongo(){
		 try {
			 Mongo mongo = new Mongo("192.168.6.9", 27017);
			 DB db = mongo.getDB("woaiwojia");
			 DBCollection collection = db.getCollection("resold");
			 
			 BasicDBObject searchEmployee = new BasicDBObject();
			 searchEmployee.put("week", 3);
			 DBCursor cursor = collection.find(searchEmployee);
			 
			 while (cursor.hasNext()) {
				    System.out.println(cursor.next());
			 }
				 
			 System.out.println("The Search Query has Executed!");
			 
		 }catch (UnknownHostException e) {
			   e.printStackTrace();
		 } catch (MongoException e) {
			   e.printStackTrace();
		}
		
	}
	public static void getDataFromLocal(String file){
		Vector<String> pois=FileTool.Load(file, "utf-8");
		JSONObject obj=new JSONObject();
		for(int i=0;i<pois.size();i++){
			String poi=pois.elementAt(i);
		}
	}
	public static void importDataIntoDB(String folder){
		 try {
			 Mongo mongo = new Mongo("192.168.6.9", 27017);
			//mongo.dropDatabase("test"); 
			//System.out.println("数据库删除成功!");
			 
			 DB db = mongo.getDB("woaiwojia");
			 DBCollection collection = db.getCollection("resold");
			 
			 Vector<String> rds = FileTool.Load(folder,"UTF-8");
			 List<DBObject> dbList = new ArrayList<DBObject>();
			 for(int i=0;i<rds.size();i++){
				 String element=rds.elementAt(i);
				 JSONObject jsonobj=JSONObject.fromObject(element);
				 BasicDBObject obj=transferToDB(jsonobj);
				 dbList.add(obj);				 
			 }
			 collection.insert(dbList) ;
			 System.out.println("数据导入完成!"); 
			 
		 }catch (UnknownHostException e) {
			   e.printStackTrace();
		 } catch (MongoException e) {
			   e.printStackTrace();
		}
	}
	public static BasicDBObject transferToDB(JSONObject obj){
		BasicDBObject document = new BasicDBObject();
		try{
			Object title=obj.get("title");
			document.put("title", title);
			
			Object longitude=obj.get("longitude");
			document.put("longitude",longitude);
			
			Object latitude=obj.get("latitude");
			document.put("latitude",latitude);
			
			Object region=obj.get("region");
			document.put("region",region);
			
			Object time=obj.get("time");
			document.put("time",time);
			
			Object price=obj.get("price");
			document.put("price",price);
			
			Object down_payment=obj.get("down_payment");
			document.put("down_payment",down_payment);
			
			Object month_payment=obj.get("month_payment");
			document.put("month_payment",month_payment);
			
			Object area=obj.get("area");
			document.put("area",area);
			
			Object unit_price=obj.get("unit_price");
			document.put("unit_price",unit_price);
			
			Object location=obj.get("location");
			document.put("location",location);
			
			Object community=obj.get("community");
			document.put("community",community);
			
			Object address=obj.get("address");
			document.put("address",address);
			
			Object property=obj.get("property");
			document.put("property",property);
			
			Object pay_way=obj.get("pay_way");
			document.put("pay_way",pay_way);
			
			Object house_type=obj.get("house_type");
			document.put("house_type",house_type);
			
			Object rent_type=obj.get("rent_type");
			document.put("rent_type",rent_type);
			
			Object fitment=obj.get("fitment");
			document.put("fitment",fitment);
			
			Object direction=obj.get("direction");
			document.put("direction",direction);
			
			Object floor=obj.get("floor");
			document.put("floor",floor);
			
			Object totalarea=obj.get("totalarea");
			document.put("totalarea",totalarea);
			
			Object developer=obj.get("developer");
			document.put("developer",developer);
			
			Object property_company=obj.get("property_company");
			document.put("property_company",property_company);
			
			Object property_fee=obj.get("property_fee");
			document.put("property_fee",property_fee);
			
			Object households=obj.get("households");
			document.put("households",households);
			
			Object built_year=obj.get("built_year");
			document.put("built_year",built_year);
			
			Object volume_rate=obj.get("volume_rate");
			document.put("volume_rate",volume_rate);
			
			Object green_rate=obj.get("green_rate");
			document.put("green_rate",green_rate);
			
			Object park=obj.get("park");
			document.put("park",park);
			
			Object heat_supply=obj.get("heat_supply");
			document.put("heat_supply",heat_supply);
			
			Object url=obj.get("url");
			document.put("url",url);
			
			Object code=obj.get("code");
			document.put("code",code);
			
		}catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			System.out.println("发生异常的原因为 :"+e.getMessage());
			e.printStackTrace();
		}
			return document;
	}

}
