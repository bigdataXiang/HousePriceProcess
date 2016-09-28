package com.svail.population_mobility;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DateFormat;
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

public class Mongo_PoeplePoi {
	public static BasicDBObject transfer(PoeplePoi poi) {
		BasicDBObject document = new BasicDBObject();
		try {
			if (poi.Name != null)
				document.put("Name", poi.Name);
			if (poi.Code != null)
				document.put("Code", poi.Code);
			if (poi.CodeAddr != null)
				document.put("CodeAddr", poi.CodeAddr);
			if (poi.CodeCoor!= null) {
				String[] coordinate = poi.CodeCoor.split(";");
				document.put("lng", Double.parseDouble(coordinate[0]));
				document.put("lat", Double.parseDouble(coordinate[1]));
			}
			if (poi.CodeReg!= null)
				document.put("CodeReg", poi.CodeReg);
			
			if (poi.PostCoor!= null) {
				String[] coordinate = poi.PostCoor.split(";");
				document.put("lng", Double.parseDouble(coordinate[0]));
				document.put("lat", Double.parseDouble(coordinate[1]));
			}
			if (poi.PostReg!= null)
				document.put("PostReg", poi.PostReg);
			if (poi.CtfTp != null)
				document.put("CtfTp", poi.CtfTp);
			if (poi.CtfId!= null)
				document.put("CtfId", poi.CtfId);
			if (poi.Home!= null)
				document.put("Home", poi.Home);
			if (poi.Gender!= null)
				document.put("Gender", poi.Gender);
			if (poi.Birth!= null)
				document.put("Birth", poi.Birth);
			if (poi.PostAddr!= null)
				document.put("PostAddr", poi.PostAddr);
			if (poi.Mobile!= null)
				document.put("Mobile", poi.Mobile);
			if (poi.Nation!= null)
				document.put("Nation", poi.Nation);
			if (poi.Version!= null)
				document.put("Version", poi.Version);
			System.out.println(document);
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			System.out.println("发生异常的原因为 :" + e.getMessage());
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
			// 选择数据库，如果没有这个数据库的话，会自动建立
			// m.dropDatabase("test");
			// System.out.println("数据库删除成功!");

			DB db = m.getDB("PeopleMobility"); // 相当于库名 :bjdata
			DBCollection coll = db.getCollection("HotelData");// 相当于表名
																
			Vector<String> rds = FileTool.Load("", "UTF-8");// ,建立一个集合，和数据库一样，如果没有，会自动建立
			List<DBObject> dbList = new ArrayList<DBObject>();
			for (int n = 0; n < rds.size(); n++) {
				String element = rds.elementAt(n);
				PoeplePoi poi = new PoeplePoi(element);
				BasicDBObject obj = transfer(poi);
				dbList.add(obj);
			}
			// 可以循环插入多条数据
			coll.insert(dbList);
			System.out.println("数据插入成功!");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			System.out.println("发生异常的原因为 :" + e.getMessage());
			e.printStackTrace();
		}

	}

}
