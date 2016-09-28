package com.svail.houseprice;
import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class RealEstateService {
	 public static void main(String[] args) {
		 getDBData();
	 }
	 /**
	  * 从mongodb中调用数据
	  */
	 public static void getDBData(){
		 try {
			 
			   Mongo mongo = new Mongo("192.168.6.9", 27017);
			 
			   DB db = mongo.getDB("test");
			 
			   DBCollection collection = db.getCollection("employees");
			 
			   BasicDBObject employee = new BasicDBObject();
			   employee.put("name", "Hannah");
			   employee.put("no", 3);
			 
			   collection.insert(employee);
			 
			   BasicDBObject searchEmployee = new BasicDBObject();
			   searchEmployee.put("no", 3);
			 
			   DBCursor cursor = collection.find(searchEmployee);
			 
			   while (cursor.hasNext()) {
			    System.out.println(cursor.next());
			   }
			 
			   System.out.println("The Search Query has Executed!");
			 
			  } catch (UnknownHostException e) {
			   e.printStackTrace();
			  } catch (MongoException e) {
			   e.printStackTrace();
			  }
	 }

}
