package com.svail.chongqing;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
import com.svail.util.Tool;

import net.sf.json.JSONObject;
//报名
public class Registration {
	
	public static void main(String[] args) throws IOException {
		timingCrawl("17:08:00");
	}
	public static void run(){
		for(int i=1;i<=4;i++){
			String path="http://www.cqedu.cn/search.aspx?searchtype=0&Keyword=%E6%8A%A5%E5%90%8D&page="+i;//报名
			getRecruitStudentsHref(path);
		}
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
            	Registration.run();
            }  
        };  
    	service.scheduleAtFixedRate(trafficTask, initDelay, 24* 60 * 60 * 1000, TimeUnit.MILLISECONDS);//scheduleAtFixedRate(TimerTask task,long delay,long period) 方法用于安排指定的任务进行重复的固定速率执行，在指定的延迟后开始。
        
    
	}
	public static void importMongoDB(BasicDBObject obj){
		try {
			Mongo mongo = new Mongo("192.168.6.9", 27017);
			DB db = mongo.getDB("chongqing");  // 数据库名称
			
			
			DBCollection coll = db.getCollection("Registration");//报名
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
	public static void getContent(JSONObject obj){

			BasicDBObject document = new BasicDBObject();
			document.put("title", obj.get("title"));
			
			String url= "http://www.cqedu.cn"+obj.getString("href");// /Item/19754.aspx
			obj.put("href", url);
			document.put("href", url);
			try {
				String content = Tool.fetchURL(url);
				//System.out.println(content);
				Parser parser = new Parser(content);
				if (content == null) {
					
				} else {
					parser.setInputHTML(content);
					parser.setEncoding("utf-8");

					NodeFilter filter = new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class", "c_content_overflow"));
					NodeList nodes = parser.extractAllNodesThatMatch(filter);
					
					//System.out.println(nodes.size());
					if (nodes.size() != 0) {
						for (int n = 0; n < nodes.size(); n++) {
							TagNode no = (TagNode) nodes.elementAt(n);	
							String contents=no.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "").replace("\n", "");						
							obj.put("content", contents);
							document.put("content", contents);
							importMongoDB(document);
							System.out.println(obj.toString());
						}
					}else{
						filter = new AndFilter(new TagNameFilter("td"),new HasAttributeFilter("class", "valignTop"));
						nodes = parser.extractAllNodesThatMatch(filter);
						
						if (nodes.size() != 0) {
							for (int n = 0; n < nodes.size(); n++) {
								TagNode no = (TagNode) nodes.elementAt(n);	
								String contents=no.toPlainTextString().replace(" ", "").replace("\r\n", "").replace("\t", "").replace("\n", "");						
								obj.put("content", contents);
								document.put("content", contents);
								importMongoDB(document);
								System.out.println(obj.toString());
							}
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

	public static void getRecruitStudentsHref(String url){

		
		try {
			String content = Tool.fetchURL(url);
			//System.out.println(content);
			Parser parser = new Parser(content);
			if (content == null) {
				
			} else {
				parser.setInputHTML(content);
				parser.setEncoding("utf-8");
				HasParentFilter parentFilter1 = new HasParentFilter(new AndFilter(new TagNameFilter("div"),new HasAttributeFilter("class", "c_article_list")));
				HasParentFilter parentFilter2 = new HasParentFilter(new AndFilter(new TagNameFilter("dl"),parentFilter1));
				HasParentFilter parentFilter3 = new HasParentFilter(new AndFilter(new TagNameFilter("dd"),parentFilter2));
				HasParentFilter parentFilter4 = new HasParentFilter(new AndFilter(new TagNameFilter("li"),parentFilter3));
				NodeFilter filter = new AndFilter(new TagNameFilter("a"),parentFilter4);
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
						
						getContent(obj);
						
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
