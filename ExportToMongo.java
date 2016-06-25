package com.svail.houseprice;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import com.google.gson.JsonSyntaxException;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.svail.tomongo.Poi_resold_anjuke;
import com.svail.util.FileTool;
import com.svail.util.Tool;

import net.sf.json.JSONObject;

public class ExportToMongo {
	
	public static void main(String[] args) throws IOException {
		ToMongoDB("","");
	}
	public static String[] dates={""};
	public static BasicDBObject transfer(JSONObject poi,String source){
		BasicDBObject document = new BasicDBObject();
		
		if(poi.containsKey("title")){
			if( !poi.get("title").equals("null")){
				document.put("title", poi.get("title").toString().replace("\"", ""));
			}
			
		}
        if(poi.containsKey("longitude")){
        	if( !poi.get("longitude").equals("null")){
        		document.put("lng", poi.get("longitude"));
        	}
        	
		}
		if(poi.containsKey("latitude")){
			if( !poi.get("latitude").equals("null")){
				document.put("lat", poi.get("latitude"));
			}
			
		}
		if(poi.containsKey("region")){
			if( !poi.get("region").equals("null")){
				document.put("region", poi.get("region"));
			}
			
		}
        if(poi.containsKey("date")){
        	if( !poi.get("date").equals("null")){
        		JSONObject date=poi.getJSONObject("date");
            	String year=date.getString("year");
            	String month=date.getString("month");
            	String day=date.getString("day");
            	String time=year+"/"+month+"/"+day;
            	DateFormat format=null; 
    			Date time_date=null;
         		format= new SimpleDateFormat("yyyy/MM/dd"); 
           	try {  
    			    time_date= format.parse(time);  
    	        } catch (Exception e) {  
    	             // TODO Auto-generated catch block  
    	             e.printStackTrace();  
    	        }
            	document.put("time", time_date);
        	}
        	
		}
		if(poi.containsKey("price")){
			if(!poi.get("price").equals("null")){
				double price=Double.parseDouble(poi.get("price").toString().replace("万", "").replace("元", "").replace("/", "").replace("月", ""));
				document.put("price", price);
			}
			
		}
        if(poi.containsKey("down_payment")){
        	if(!poi.get("down_payment").equals("null")){
        		document.put("down_payment", poi.get("down_payment"));
        	}
        	
		}
        if(poi.containsKey("location")){
        	if(!poi.get("location").equals("null")){
        		document.put("location", poi.get("location"));
        	}
			
		}
        if(poi.containsKey("unit_price")){
        	if(!poi.get("unit_price").equals("null")){
        		document.put("unit_price", poi.get("unit_price"));
        	}
			
		}
        if(poi.containsKey("month_payment")){
        	if(!poi.get("month_payment").equals("null")){
        		document.put("month_payment", poi.get("month_payment"));
        	}
        	
		}
        if(poi.containsKey("community")){
        	if(!poi.get("community").equals("null")){
        		String community=poi.get("community").toString();
        		community=Tool.delect_content_inBrackets(community, "(", ")");
        		document.put("community", poi.get("community"));
        	}
			
		}
        if(poi.containsKey("address")){
        	if(!poi.get("address").equals("null")){
        		document.put("address", poi.get("address"));
        	}
        	
		}
        if(poi.containsKey("developer")){
        	if(!poi.get("developer").equals("null")){
        		document.put("developer", poi.get("developer"));
        	}
			
		}
        if(poi.containsKey("property_company")){
        	if(!poi.get("property_company").equals("null")){
        		document.put("property_company", poi.get("property_company"));
        	}
        	
		}
        if(poi.containsKey("property_fee")){
        	if(!poi.get("property_fee").equals("null")){
        		document.put("property_fee", poi.get("property_fee"));
        	}
        	
		}
        if(poi.containsKey("property")){
        	
        	if(!poi.get("property").equals("null")){
        		document.put("property", poi.get("property"));
        	}
			
		}
        if(poi.containsKey("house_type")){
        	if(!poi.get("house_type").equals("null")){
        		document.put("house_type", poi.get("house_type"));
        	}
        	
		}
        if(poi.containsKey("rent_type")){
        	if(!poi.get("rent_type").equals("null")){
        		document.put("rent_type", poi.get("rent_type"));
        	}
        	
		}
        if(poi.containsKey("pay_way")){
        	if(!poi.get("pay_way").equals("null")){
        		document.put("pay_way", poi.get("pay_way"));
        	}
        	
		}
        if(poi.containsKey("area")){
        	if(!poi.get("area").equals("null")){
        		document.put("area", poi.get("area"));
        	}
			
		}
        if(poi.containsKey("totalarea")){
        	if(!poi.get("totalarea").equals("null")){
        		document.put("totalarea", poi.get("totalarea"));
        	}
        	
		}
        if(poi.containsKey("direction")){
        	if(!poi.get("direction").equals("null")){
        		document.put("direction", poi.get("direction"));
        	}
			
		}
        if(poi.containsKey("structure")){
        	if(!poi.get("structure").equals("null")){
        		document.put("structure", poi.get("structure"));
        	}
        	
		}
        if(poi.containsKey("biult_type")){
        	if(!poi.get("biult_type").equals("null")){
        		document.put("biult_type", poi.get("biult_type"));
        	}
			
		}
        if(poi.containsKey("floor")){
        	if(!poi.get("floor").equals("null")){
        		document.put("floor", poi.get("floor"));
        	}
        	
		}
        if(poi.containsKey("fitment")){
        	if(!poi.get("fitment").equals("null")){
        		document.put("fitment", poi.get("fitment"));
        	}
        	
		}
        if(poi.containsKey("households")){
        	if(!poi.get("households").equals("null")){
        		document.put("households", poi.get("households"));
        	}
        	
		}
        if(poi.containsKey("built_year")){
        	if(!poi.get("built_year").equals("null")){
        		document.put("built_year", poi.get("built_year"));
        	}
        	
		}
        if(poi.containsKey("volume_rate")){
        	if(!poi.get("volume_rate").equals("null")){
        		document.put("volume_rate", poi.get("volume_rate"));
        	}
        	
		}
        if(poi.containsKey("park")){
        	if(!poi.get("park").equals("null")){
        		document.put("park", poi.get("park"));
        	}
        	
		}
        if(poi.containsKey("green_rate")){
        	if(!poi.get("green_rate").equals("null")){
        		document.put("green_rate", poi.get("green_rate"));
        	}
        	
		}
        if(poi.containsKey("facility")){
        	if(!poi.get("facility").equals("null")){
        		document.put("facility", poi.get("facility"));
        	}
        	
		}
        if(poi.containsKey("eqiupment")){
        	if(!poi.get("eqiupment").equals("null")){
        		document.put("facility", poi.get("eqiupment"));
        	}
        	
		}
        if(poi.containsKey("date")){
        	if(!poi.get("date").equals("null")){
        		JSONObject date=poi.getJSONObject("date");
            	if(!date.isEmpty()){
            		document.put("date", poi.get("date"));
            	}   
        	}
        	    	
		}
        if(poi.containsKey("layout")){
        	if(!poi.get("layout").equals("null")){
        		JSONObject layout=poi.getJSONObject("layout");
            	if(!layout.isEmpty()){
            		document.put("layout", poi.get("layout"));
            	}
        	}
        	
        	
		}
        if(poi.containsKey("storeys")){
        	if(!poi.get("storeys").equals("null")){
        		JSONObject storeys=poi.getJSONObject("storeys");
            	if(!storeys.isEmpty()){
            		document.put("storeys", poi.get("storeys"));
            	}
        	}
        	
        	
		}
        if(poi.containsKey("traffic")){
        	if(!poi.get("traffic").equals("null")){
        		document.put("traffic", poi.get("traffic"));
        	}
        	
		}
        if(poi.containsKey("heat_supply")){
        	if(!poi.get("heat_supply").equals("null")){
        		document.put("heat_supply", poi.get("heat_supply"));
        	}
        	
		}
        if(poi.containsKey("url")){
        	if(!poi.get("url").equals("null")){
        		document.put("url", poi.get("url"));
        	}
        	
		}
        
        document.put("source", source);
        //System.out.println(document);
		return document;
	}
	
	/**
	 * 将数据批量导入mongoDB
	 * @param collName ：数据库中表的名称（rentout、resold）
	 * @param source ： 数据的来源（fang、woaiwojia）
	 */
	public static void ToMongoDB(String collName,String source){
		Mongo m;
		try {
			System.out.println("运行开始:");
			m = new Mongo("192.168.6.9", 27017);
			//m.dropDatabase("test"); 
			DB db = m.getDB("houseprice");
			
			DBCollection coll = db.getCollection(collName);//coll.drop();
			//coll.drop();
			
			String path="E:/房地产可视化/近一年数据分类汇总/fang/rentout/json/fang_rentout_tidy/";//"E:/房地产可视化/近一年数据分类汇总/fang/resold/json/tidy/fang_resold_tidy/"
			Vector<String> filenames=FileTool.Load(path+"filename.txt","UTF-8");
			
			for(int i=0;i<filenames.size();i++){
				String filename=filenames.elementAt(i);
				
				System.out.println("开始导入第"+i+"个文件");
				Vector<String> rds = FileTool.Load(path+filename,"UTF-8");
				List<DBObject> dbList = new ArrayList<DBObject>();  
				for (int n = 0; n < rds.size(); n ++)
				{
					try{
						String element=rds.elementAt(n);
						JSONObject element_obj=JSONObject.fromObject(element);

						BasicDBObject obj = transfer(element_obj,source);
						//System.out.println(obj);
						dbList.add(obj);
					}catch(NumberFormatException e){
						e.printStackTrace();
					    System.out.println(n);
					}
					

				}
				coll.insert(dbList);
				System.out.println("第"+i+"个文件导入完毕");
				coll.createIndex(new BasicDBObject("lng", 1));
				coll.createIndex(new BasicDBObject("lat", 1));
				coll.createIndex(new BasicDBObject("time", 1));
				coll.createIndex(new BasicDBObject("price", 1));
			}
			
			
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