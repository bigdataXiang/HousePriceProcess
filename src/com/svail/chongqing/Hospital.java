package com.svail.chongqing;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.htmlparser.Attribute;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.google.gson.JsonSyntaxException;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.svail.util.FileTool;
import com.svail.util.HTMLTool;
import com.svail.util.Tool;

import net.sf.json.JSONObject;

public class Hospital {
	public static void main(String[] args) throws IOException {
		timingCrawl("16:15:00");
	}
	public static void timingCrawl(String time){		  
    	ScheduledExecutorService service = Executors.newScheduledThreadPool(4);          
    	long oneDay = 24 * 60 * 60 * 1000;
    	long timemillis=Tool.getTimeMillis(time);
    	long timenow=System.currentTimeMillis();
    	long initDelay = timemillis - timenow;  
    	initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;  

        Runnable trafficTask = new Runnable(){  
            public void run() { 
            	System.out.println("开始定期抓取");
            	Hospital.run();
            }  
        };  
    	service.scheduleAtFixedRate(trafficTask, initDelay, 24* 60 * 60 * 1000, TimeUnit.MILLISECONDS);//scheduleAtFixedRate(TimerTask task,long delay,long period) 方法用于安排指定的任务进行重复的固定速率执行，在指定的延迟后开始。
    }
	public static void run(){
		for(int i=1;i<=56;i++){
			String path="http://www.cqwsjsw.gov.cn/Search/?1=1&keywords=%D2%BD%D4%BA&pageNo="+i;
			getHospitalLink(path);
		}
	}
	public static void importMongoDB(BasicDBObject obj){
		try {
			Mongo mongo = new Mongo("192.168.6.9", 27017);
			DB db = mongo.getDB("chongqing");  // 数据库名称
			
			
			DBCollection coll = db.getCollection("HealthCommision");
			//coll.drop();//清空表
			
			try {
				  DBCursor rls =coll.find(obj);
				  if(rls == null || rls.size() == 0){
					coll.insert(obj);
				 }else{
					System.out.println("该数据已经存在!");
				 }		   				
			}catch (JsonSyntaxException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}catch (NullPointerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				//FileTool.Dump(photo.toString(), poiError, "utf-8");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}catch (UnknownHostException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (MongoException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	
	
	}
	
	public static void getHospitalInfo(JSONObject obj){

		BasicDBObject document = new BasicDBObject();
		document.put("title", obj.get("title"));
		
		String href=obj.getString("href");

	    String url ="";
	    if(!href.startsWith("http")){
			url = "http://www.cqwsjsw.gov.cn"+href;
		}else{
			url = href;
		}
	    obj.put("href", url);
	    document.put("href", url);
		
		
		try {
			//String content = Tool.fetchURL(url);
			String content = HTMLTool.fetchURL(url, "gb18030", "get");

			Parser parser= new Parser();
			if (content == null) {
				//FileTool.Dump(url, folder + "-Null.txt", "utf-8");
			} else {
				parser.setInputHTML(content);
				parser.setEncoding("gb18030");//gb18030
				NodeFilter filter = new AndFilter(new TagNameFilter("td"),new HasAttributeFilter("class", "list_textf14"));
				NodeList nodes = parser.extractAllNodesThatMatch(filter);
				if (nodes.size() != 0) {
					for (int n = 0; n < nodes.size(); n++) {
                        TagNode no = (TagNode) nodes.elementAt(n);
						String str=no.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "").replace("\n", "")
								      .replace("&nbsp;", "").replace("<?xml:namespaceprefix=\"o\"/>", "");
						//System.out.println(str);
						obj.put("content", str);
						document.put("content", str);
						
						importMongoDB(document);

					}
				}
			}

		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		  
	}
	public static void getHospitalLink(String url){

		try {
			String content = Tool.fetchURL(url);
			//System.out.println(content);
			Parser parser = new Parser(content);
			if (content == null) {
				
			} else {
				parser.setInputHTML(content);
				parser.setEncoding("gbk2312");
				HasParentFilter parentFilter1 = new HasParentFilter(new AndFilter(new TagNameFilter("ul"),new HasAttributeFilter("class", "classtext")));
				HasParentFilter parentFilter2 = new HasParentFilter(new AndFilter(new TagNameFilter("li"),parentFilter1));
				//HasParentFilter parentFilter3 = new HasParentFilter(new AndFilter(new TagNameFilter("li"),parentFilter2));
				NodeFilter filter = new AndFilter(new TagNameFilter("a"),parentFilter2);
				NodeList nodes = parser.extractAllNodesThatMatch(filter);
				
				System.out.println(nodes.size());
				if (nodes.size() != 0) {
					for (int n = 0; n < nodes.size(); n++) {
						JSONObject obj=new JSONObject();
						TagNode no = (TagNode) nodes.elementAt(n);	
						String title=no.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "").replace("\n", "");
						String tur = no.getAttribute("href");
						
						obj.put("title", title);
						obj.put("href", tur);
						
						getHospitalInfo(obj);
					}
				}
			}

		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
	
	}

}
